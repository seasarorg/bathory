package org.seasar.bathory.engine;

/**
 * データ収集モジュールが実装すべきインタフェース.
 * @author toyokazu
 */
public interface Collector {
    /**
     * 値を収集します.
     * @param basket Basket
     */
    void collect(Casket basket);
}
