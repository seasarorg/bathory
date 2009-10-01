package org.seasar.bathory.executer;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import org.seasar.bathory.def.Application;
import org.seasar.bathory.def.Constants;
import org.seasar.bathory.def.Constants.ContextKeys;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.exception.IllegalCommandArgumentException;
import org.seasar.framework.beans.util.BeanUtil;



/**
 * .
 * @author toyokazu
 */
@SuppressWarnings("unchecked")
public class BatchExecuterHelper {
    /** デフォルトの並列度. */
    private static final int DEFAULT_PARALLELISM
                = Application.getApplication().getDefaultParallelism();
    
    
    /** デフォルト値を格納するクラス. */
    private static final Map<String, Object> DEFAULT_VALUES = new HashMap<String, Object>();
    static {
        DEFAULT_VALUES.put(ContextKeys.PARALLELISM, DEFAULT_PARALLELISM);
    }

    /**
     * インスタンス化禁止.
     */
    private BatchExecuterHelper() {
    }

    /**
     * システムプロパティよりBathoryContextを取得します.
     * @return BathoryContext
     */
    public static BathoryContext getContextFromSystemProperties() {
        Map prop = System.getProperties();
        return getContext(prop);
    }

    /**
     * BathoryContextを取得します.
     * @param params パラメータMap
     * @return BathoryContext
     */
    private static BathoryContext getContext(final Map params) {
        validate(params);
        setDefaultValues(params);
        BathoryContext context = new BathoryContext();
        BathoryContext.setCurrentInstance(context);
        BeanUtil.copyProperties(params, context);
        context.setProperties((Map<String, Object>) params);

        return context;
    }

    /**
     * パラメータの妥当性を検証します.
     * @param params パラメータ
     */
    private static void validate(final Map params) {
        validate(params, ContextKeys.JOBID, "Job ID");
        validate(params, ContextKeys.BATCHID, "Batch ID");
    }

    /**
     * 妥当性を検証します.
     * @param params パラメータ
     * @param itemKey 検証項目キー情報
     * @param itemName 検証項目名
     */
    private static void validate(final Map params,
                                 final String itemKey,
                                 final String itemName) {
        if (params.get(itemKey) == null) {
            throw new IllegalCommandArgumentException(itemKey, itemName);
        }
    }

    /**
     * デフォルト値を設定します.
     * @param props プロパティファイル
     */
    private static void setDefaultValues(final Map props) {
        String batchId = props.get(ContextKeys.BATCHID).toString();
        String jobId   = props.get(ContextKeys.JOBID).toString();
        Date now       = new Date();

        Object collectorName = props.get(ContextKeys.COLLECTORNAME);
        if (collectorName == null) {
            props.put(ContextKeys.COLLECTORNAME, batchId + Constants.SUFFIX_OF_COLLECTOR);
        }

        Object consumerName = props.get(ContextKeys.CONSUMERNAME);
        if (consumerName == null) {
            props.put(ContextKeys.CONSUMERNAME, batchId + Constants.SUFFIX_OF_CONSUMER);
        }

        for (Iterator<Entry<String, Object>> it = DEFAULT_VALUES.entrySet().iterator();
             it.hasNext();) {
            Entry entry  = it.next();
            String key   = entry.getKey().toString();
            Object value = entry.getValue();
            if (!props.containsKey(key)) {
                props.put(key, value);
            }
        }
        
        props.put(ContextKeys.STARTDATETIME, now);
        
        Object identName = props.get(ContextKeys.IDENT_NAME);
        if (identName == null) {
            Format format = new SimpleDateFormat("yyyyMMddhh24mmssSSS");
            props.put(ContextKeys.IDENT_NAME, jobId + batchId + format.format(now));
        }
    }
}
