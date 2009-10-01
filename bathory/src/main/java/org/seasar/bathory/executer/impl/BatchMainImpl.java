package org.seasar.bathory.executer.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.bathory.def.Constants.RunState;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.engine.handler.BaseHandler;
import org.seasar.bathory.engine.handler.CollectorHandler;
import org.seasar.bathory.engine.handler.ConsumerHandler;
import org.seasar.bathory.exception.SystemException;
import org.seasar.bathory.executer.BatchMain;


/**
 * {@link BatchMain}の実装クラス.
 * @author toyokazu
 */
public class BatchMainImpl extends BaseBatch implements BatchMain {
    /** 処理待ち時間. */
    private static final long WAIT_TIME = 1000;

    /**
     * メイン処理を実行します.
     * @see org.seasar.bathory.executer.BatchMain#terminate()
     */
    @Override
    public void main() {
        try {
            mainImpl();
        } catch (Throwable t) {
            handleException(t);
        }
    }
    /**
     * メイン処理を実装.
     */
    public void mainImpl() {
        BathoryContext context = BathoryContext.getCurrentInstance();
        Casket casket          = new Casket();
        int parallelism        = context.getParallelism();

        Collector collector       = getCollector(context);
        CollectorHandler cHandler = new CollectorHandler();
        cHandler.setTarget(collector);
        cHandler.setCasket(casket);
        cHandler.start();

        List<ConsumerHandler> consumers = new ArrayList<ConsumerHandler>(parallelism);
        for (int i = 0; i < parallelism; i++) {
            Consumer consumer       = getConsumer(context);
            ConsumerHandler handler = new ConsumerHandler();
            handler.setCasket(casket);
            handler.setTarget(consumer);
            
            consumers.add(handler);
            handler.start();
        }


        List<BaseHandler> handlers = new ArrayList<BaseHandler>(parallelism + 1);
        handlers.add(cHandler);
        handlers.addAll(consumers);

        boolean isEnded = false;
        while (!isEnded) {
            // TODO ひとまず、ループとしておく
            // セマフォとどちらがよいか？
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                // 処理が中断されました
                throw new SystemException(e);
            }

            isEnded = true;
            for (int i = 0; i < handlers.size(); i++) {
                BaseHandler handler = handlers.get(i);
                RunState runState = handler.getRunState();
                switch (runState) {
                case ACTIVE:
                    isEnded = false;
                    break;
                case ABEND:
                    // 即時処理終了
                    isEnded = true;
                    i = handlers.size();
                default:
                    break;
                }
            }
        }
    }
}
