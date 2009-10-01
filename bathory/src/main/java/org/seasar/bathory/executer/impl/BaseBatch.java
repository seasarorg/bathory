package org.seasar.bathory.executer.impl;

import static org.seasar.framework.container.SingletonS2Container.getComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.bathory.def.Application;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.exception.ExitException;
import org.seasar.bathory.exception.RecoverableException;


/**
 * バッチ実装クラスのベースクラス.
 * @author toyokazu
 */
public abstract class BaseBatch {
    /** Log. */
    private static final Log LOG = LogFactory.getLog(BaseBatch.class);
    /**
     * Collectorを取得します.
     * @param context BathoryContext
     * @return Collector
     */
    protected Collector getCollector(final BathoryContext context) {
        Collector collector = getComponent(context.getCollectorName());
        return collector;
    }

    /**
     * Consumerを取得します.
     * @param context BathoryContext
     * @return Consumer
     */
    protected Consumer getConsumer(final BathoryContext context) {
        Consumer consumer = getComponent(context.getConsumerName());
        return consumer;
    }

    /**
     * 例外を処理します.
     * @param t 発生した例外
     */
    protected void handleException(final Throwable t) {
        LOG.error(t, t);
        int result;
        if (t instanceof RecoverableException) {
            result = Application.getApplication().getWarnCode();
        } else {
            if (t instanceof ExitException) {
                ExitException e = (ExitException) t;
                result = e.getStatusCode();
            } else {
                result = Application.getApplication().getErrorCode();
            }
        }
        BathoryContext.getCurrentInstance().setStatusCode(result);
    }
}
