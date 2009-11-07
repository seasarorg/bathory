package org.seasar.bathory.engine.datarouting;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.util.BathoryMessageUtil;

/**
 * 分割キー方式のDataRoutingRule.
 * 分割キーを指定してバッチを起動した際に選択されます.
 * 指定されたキーに対応する値が同一の場合同一のトランザクションで実行されます
 * @author toyokazu
 */
public class KeyDividedDataRoutingRule extends BaseDataRoutingRule {
    /** Log. */
    private static final Log LOG = LogFactory.getLog(KeyDividedDataRoutingRule.class);
    /** 並列度. */
    private int parallelism;

    /**
     * {@inheritDoc}
     * @see org.seasar.bathory.executer.DataRoutingRule#getTarget(java.util.Map)
     */
    @Override
    public int getTarget(final Map<String, Object> values) {
        if (parallelism <= 0) {
            BathoryContext context = BathoryContext.getCurrentInstance();
            parallelism = context.getParallelism();
        }
        
        
        String keyName = BathoryContext.getCurrentInstance().getDataDivideKey();
        Object keyValue = values.get(keyName);
        if (keyValue == null) {
            if (!values.containsKey(keyName)) {
                LOG.warn(BathoryMessageUtil.getMessage("WBAT0001", keyName));
            }
            return 0;
        }
        if (keyValue instanceof Long) {
            Long value = (Long) keyValue;
            return (int) (value.longValue() % (long) parallelism);
        }

        if (keyValue instanceof Integer) {
            Integer value = (Integer) keyValue;
            return value.intValue() % parallelism;
        }

        return (int) (keyValue.hashCode() % (long) parallelism);
    }
}