package org.seasar.bathory.executer.impl;

import org.seasar.bathory.engine.BatchLifecycleListner;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.executer.BatchInitializer;


/**
 * {@link BatchInitializer}の実装クラス.
 * @author toyokazu
 */
public class BatchInitializerImpl extends BaseBatch implements BatchInitializer {

    /**
     * 初期化処理を行います.
     * @see org.seasar.bathory.executer.BatchInitializer#initialize()
     */
    @Override
    public void initialize() {
        try {
            BathoryContext context = BathoryContext.getCurrentInstance();
            Collector collector = getCollector(context);
            Consumer consumer = getConsumer(context);
            initilize(collector);
            initilize(consumer);
        } catch (Throwable t) {
            handleException(t);
        }
    }

    /**
     * 初期化処理を行います.
     * @param object 対象オブジェクト
     */
    private void initilize(final Object object) {
        if (object instanceof BatchLifecycleListner) {
            ((BatchLifecycleListner) object).initilize();
        }
    }
}
