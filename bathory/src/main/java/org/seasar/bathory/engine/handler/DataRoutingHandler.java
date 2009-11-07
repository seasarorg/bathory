package org.seasar.bathory.engine.handler;

import java.util.List;
import java.util.Map;

import org.seasar.bathory.def.Constants;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.datarouting.KeyDividedDataRoutingRule;
import org.seasar.bathory.engine.datarouting.LeastConnectionsDataRoutingRule;
import org.seasar.bathory.executer.DataRoutingRule;
import org.seasar.framework.container.SingletonS2Container;


/**
 * データをルーティング処理するためのクラス.
 * @author toyokazu
 */
public class DataRoutingHandler extends BaseHandler {
    /** データ格納クラス. */
    private Casket container;
    /** ConsumerHandlerの一覧. */
    private List<ConsumerHandler> consumerList;
    /** データルーティングルール. */
    private DataRoutingRule rule;
    /** 並列度. */
    private int parallelism;

    /**
     * 処理を実行します.
     * @throws Exception 例外を通知
     * @see org.seasar.bathory.engine.handler.BaseHandler#execute()
     */
    @Override
    void execute() throws Exception {
        prepareDataRoutingRule();
        BathoryContext context = getContext();
        parallelism = context.getParallelism();
        try {
            executeImpl();
        } finally {
            // 終了通知
            for (int i = 0, iMax = consumerList.size(); i < iMax; i++) {
                consumerList.get(i).feed(Constants.END_OF_DATA);
            }
        }
    }

    /**
     * 処理を実行します.
     * @throws Exception 例外を通知
     * @see org.seasar.bathory.engine.handler.BaseHandler#execute()
     */
    void executeImpl() throws Exception {
        // 例外発生時に、処理を終了させる
        while (true) {
            Map<String, Object> value = container.violate();
            if (Constants.END_OF_DATA.equals(value)) {
                break;
            }
            ConsumerHandler consumerHandler = getConsumerHandler(value);
            consumerHandler.feed(value);
        }
    }

    /**
     * Casketを設定します.
     * @param casket Casket
     */
    public void setCasket(final Casket casket) {
        container = casket;
    }

    /**
     * ConsumerHandler一覧を設定します.
     * @param list ConsumerHandler一覧
     */
    public void setConsumerHandlers(final List<ConsumerHandler> list) {
        consumerList = list;
    }

    /**
     * DataRoutingRuleを設定します.
     */
    private void prepareDataRoutingRule() {
        Class<? extends DataRoutingRule> ruleClass;

        BathoryContext context = BathoryContext.getCurrentInstance();
        ruleClass = context.getDataRoutingRuleClass();

        if (ruleClass == null) {
            String divideKey = context.getDataDivideKey();
            if (divideKey == null) {
                ruleClass = LeastConnectionsDataRoutingRule.class;
            } else {
                ruleClass = KeyDividedDataRoutingRule.class;
            }
        }
        rule = SingletonS2Container.getComponent(ruleClass);
        rule.setConsumers(consumerList);
    }

    /**
     * ConsumerHandlerを取得します.
     * @param value Collectorから取得した値
     * @return ConsumerHandler
     */
    private ConsumerHandler getConsumerHandler(final Map<String, Object> value) {
        int target = rule.getTarget(value);
        if (target > parallelism) {
            target = target % parallelism;
        }
        return consumerList.get(target);
    }
}
