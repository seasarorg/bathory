package org.seasar.bathory.engine.handler;

import org.seasar.bathory.def.Constants;
import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.Collector;


/**
 * Collectorを処理するためのクラス.
 * @author toyokazu
 */
public class CollectorHandler extends BaseHandler {
    /** 処理するCollectorクラス. */
    private Collector target;
    /** データ格納クラス. */
    private Casket container;

    /**
     * 処理を実行します.
     * @throws Exception 例外を通知
     * @see org.seasar.bathory.engine.handler.BaseHandler#execute()
     */
    @Override
    void execute() throws Exception {
        try {
            target.collect(container);
        } finally {
            // Consumerに終了を通知
            for (int i = 0, iMax = getContext().getParallelism(); i < iMax; i++) {
                container.put(Constants.END_OF_DATA);
            }
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
     * Collectorを設定します.
     * @param collector Collector
     */
    public void setTarget(final Collector collector) {
        target = collector;
    }
}
