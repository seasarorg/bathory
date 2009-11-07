package org.seasar.bathory.engine.datarouting;

import java.util.List;

import org.seasar.bathory.engine.handler.ConsumerHandler;
import org.seasar.bathory.executer.DataRoutingRule;

/**
 * DataRoutingRuleのベースクラス.
 * @author toyokazu
 */
public abstract class BaseDataRoutingRule implements DataRoutingRule {
    /** 全てのConsumerHandler. */
    private List<ConsumerHandler> consumers;

    /**
     * {@inheritDoc}
     * @see org.seasar.bathory.executer.DataRoutingRule#setConsumers(java.util.List)
     */
    @Override
    public void setConsumers(final List<ConsumerHandler> consumerList) {
        consumers = consumerList;
    }

    /**
     * ConsumerHandler一覧を取得します.
     * @return ConsumerHandler一覧
     */
    protected List<ConsumerHandler> getConsumers() {
        return consumers;
    }
}
