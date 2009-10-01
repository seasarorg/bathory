package org.seasar.bathory.executer;

/**
 * バッチ終了処理を行うコンポーネントが実装すべきインタフェース.
 * @author toyokazu
 */
public interface BatchTerminator {
    /**
     * 終了処理.
     */
    void terminate();
}
