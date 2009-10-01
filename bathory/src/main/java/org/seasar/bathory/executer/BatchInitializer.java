package org.seasar.bathory.executer;

/**
 * バッチ初期処理を行うコンポーネントが実装すべきインタフェース.
 * @author toyokazu
 */
public interface BatchInitializer {
    /**
     * 初期処理.
     */
    void initialize();
}
