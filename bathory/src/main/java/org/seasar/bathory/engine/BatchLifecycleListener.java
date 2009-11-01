package org.seasar.bathory.engine;

/**
 * 初期化や終了時に処理が必要な場合に実装すべきインタフェース.
 * @author toyokazu
 */
public interface BatchLifecycleListener {
    /**
     * 初期化処理.
     * 一回のバッチ中で一度呼ばれます
     */
    void initilize();

    /**
     * 終了処理.
     * 一回のバッチ中で一度呼ばれます
     */
    void terminate();
}
