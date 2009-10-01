package org.seasar.bathory.executer;

import org.seasar.bathory.engine.BathoryContext;

/**
 * バッチ実行コンポーネントが実装すべきインタフェース.
 * @author toyokazu
 */
public interface BatchExecuter {
    /**
     * バッチ処理を実行します.
     * @param context BathoryContext
     * @return リターンコード
     */
    int execute(BathoryContext context);
}
