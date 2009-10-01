package org.seasar.bathory.extentions.s2dao;

import java.util.Map;

import org.seasar.bathory.engine.Casket;
import org.seasar.dao.tiger.FetchHandler;


/**
 * FetchHandlerをBathory内で扱うためのアダプタクラス.
 * @author toyokazu
 */
public class FetchHandlerAdapter implements FetchHandler<Map<String, Object>> {
    /** Casket. */
    private Casket container;
    
    /**
     * 処理を委譲するCasketを指定してインスタンスを作成します.
     * @param casket Casket
     */
    public FetchHandlerAdapter(final Casket casket) {
        container = casket;
    }

    /**
     * 一行の処理を行う際に呼び出されるコールバックメソッド.
     * @param bean 一行分の情報を格納したMap
     * @return 処理を継続するか否か
     * @see org.seasar.dao.tiger.FetchHandler#execute(java.lang.Object)
     */
    @Override
    public boolean execute(final Map<String, Object> bean) {
        container.put(bean);
        return true;
    }
}
