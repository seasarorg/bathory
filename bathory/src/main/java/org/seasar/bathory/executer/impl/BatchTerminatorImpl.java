package org.seasar.bathory.executer.impl;

import org.seasar.bathory.engine.BatchLifecycleListner;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.executer.BatchTerminator;


/**
 * {@link BatchTerminator}の実装クラス.
 * @author toyokazu
 */
public class BatchTerminatorImpl extends BaseBatch implements BatchTerminator {

    /**
     * 終了処理を実行します.
     * @see org.seasar.bathory.executer.BatchTerminator#terminate()
     */
    @Override
    public void terminate() {
        try {
            BathoryContext context = BathoryContext.getCurrentInstance();
            Collector collector    = getCollector(context);
            Consumer consumer      = getConsumer(context);
            terminate(collector);
            terminate(consumer);
        } catch (Throwable t) {
            handleException(t);
        }
    }

    /**
     * 終了処理を行います.
     * @param object 対象オブジェクト
     */
    private void terminate(final Object object) {
        if (object instanceof BatchLifecycleListner) {
            ((BatchLifecycleListner) object).terminate();
        }
    }
}
