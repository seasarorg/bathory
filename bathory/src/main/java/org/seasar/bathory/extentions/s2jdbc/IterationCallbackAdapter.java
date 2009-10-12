package org.seasar.bathory.extentions.s2jdbc;

import java.util.Map;

import org.seasar.bathory.engine.Casket;
import org.seasar.extension.jdbc.IterationCallback;
import org.seasar.extension.jdbc.IterationContext;

/**
 * S2JDBCにてCollectorを作成するためのアダプタ.
 * @author toyokazu
 */
public class IterationCallbackAdapter implements IterationCallback<Map<String, Object>, Integer> {
    /** 処理結果件数. */
    private int count;
    /** Casket. */
    private Casket container;
    
    /**
     * 処理を委譲するCasketを指定してインスタンスを作成します.
     * @param casket Casket
     */
    public IterationCallbackAdapter(final Casket casket) {
        container = casket;
    }

    @Override
    public Integer iterate(final Map<String, Object> value, final IterationContext context) {
        container.put(value);
        ++count;
        return count;
    }
}
