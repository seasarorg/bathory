package org.seasar.bathory.engine;

/**
 * 収集したデータを処理するクラスが実装すべきインタフェース.
 * @author toyokazu
 */
public interface Consumer {
    /**
     * データの消費を行います.
     */
    void consume();
}
