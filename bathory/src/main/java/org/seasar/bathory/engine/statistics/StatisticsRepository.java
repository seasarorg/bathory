package org.seasar.bathory.engine.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.exception.SystemException;
import org.seasar.framework.util.SerializeUtil;


/**
 * 統計情報を収集するためのクラス.
 * @author toyokazu
 */
public class StatisticsRepository {

    /** ThreadLocal. */
    private static ThreadLocal<Map<String, TempStatisticsInfo>> threadLocal
                                = new ThreadLocal<Map<String, TempStatisticsInfo>>();
    /** 統計情報を格納するMap. */
    private static Map<String, Map<String, StatisticsInfo>> statisticsMap
                    = new HashMap<String, Map<String, StatisticsInfo>>();
    /** 追加種別. */
    private enum ADD_TYPE { SUCCESS, WARN, ERROR };
    
    /**
     * インスタンス化禁止.
     */
    private StatisticsRepository() {
    }

    /**
     * 処理件数を追加します.
     * @param identName 識別名称
     * @param num 処理件数
     */
    public static void addCount(final String identName, final int num) {
        Map<String, TempStatisticsInfo> infos = threadLocal.get();
        if (infos == null) {
            infos = new HashMap<String, TempStatisticsInfo>();
            threadLocal.set(infos);
        }
        TempStatisticsInfo info = infos.get(identName);
        if (info == null) {
            info = new TempStatisticsInfo(identName);
            infos.put(identName, info);
        }
        info.addCount(num);
    }

    /**
     * 処理時間を計上します.
     * @param identName 識別名称
     * @param time 処理時間
     */
    public static void elapse(final String identName, final long time) {
        Map<String, StatisticsInfo> infoMap   = getStatisticsInfo();
        StatisticsInfo info = getStatisticsInfo(identName, infoMap);
        info.addTotalElapsedTime(time);
        info.addExecuteCount();
    }

    /**
     * 処理件数を正常処理件数として計上します.
     */
    public static void markAsSuccess() {
        markImpl(ADD_TYPE.SUCCESS);
    }

    /**
     * 処理件数を警告処理件数として計上します.
     */
    public static void markAsWarn() {
        markImpl(ADD_TYPE.WARN);
    }
    
    /**
     * 処理件数を異常処理件数として計上します.
     */
    public static void markAsError() {
        markImpl(ADD_TYPE.ERROR);
    }

    /**
     * 統計情報を取得します.
     * @return 統計情報
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Map<String, StatisticsInfo>> getStatisticsMap() {
        synchronized (statisticsMap) {
            return (Map<String, Map<String, StatisticsInfo>>)
                SerializeUtil.serialize(statisticsMap);
        }
    }

    /**
     * 統計情報を取得します.
     * @param identName 識別名
     * @return 統計情報
     */
    public static Map<String, StatisticsInfo> getStatisticsMap(final String identName) {
        return getStatisticsMap().get(identName);
    }

    /**
     * 現在実行中の統計情報を取得します.
     * @return 統計情報
     */
    public static Map<String, StatisticsInfo> getCurrentStatisticsMap() {
        BathoryContext context = BathoryContext.getCurrentInstance();
        return getStatisticsMap(context.getIdentName());
    }

    /**
     * 計上処理実装メソッド.
     * @param addType 追加種別
     */
    private static void markImpl(final ADD_TYPE addType) {
        Map<String, TempStatisticsInfo> infos = threadLocal.get();
        if (infos == null) {
            return;
        }

        Map<String, StatisticsInfo> infoMap   = getStatisticsInfo();

        for (Iterator<Entry<String, TempStatisticsInfo>> it = infos.entrySet().iterator();
                            it.hasNext();) {
            Entry<String, TempStatisticsInfo> entry = it.next();
            String key = entry.getKey();
            TempStatisticsInfo value = entry.getValue();
            StatisticsInfo info = getStatisticsInfo(key, infoMap);
            switch (addType) {
            case SUCCESS:
                info.addSuccessCount(value.getCount());
                break;
            case WARN:
                info.addWarnCount(value.getCount());
                break;
            case ERROR:
                info.addErrorCount(value.getCount());
                break;
            default:
                throw new SystemException();
            }
        }
        threadLocal.set(null);
    }

    /**
     * 統計情報を格納するMapを取得します.
     * @return 統計情報を格納するMap
     */
    private static Map<String, StatisticsInfo> getStatisticsInfo() {
        BathoryContext context = BathoryContext.getCurrentInstance();
        String identName = context.getIdentName();
        
        Map<String, StatisticsInfo> result = statisticsMap.get(identName);
        if (result == null) {
            synchronized (statisticsMap) {
                Map<String, StatisticsInfo> info = statisticsMap.get(identName);
                if (info == null) {
                    info = new HashMap<String, StatisticsInfo>();
                    statisticsMap.put(identName, info);
                }
                result = info;
            }
        }
        return result;
    }

    /**
     * 識別情報に紐づくStatisticsInfoを取得します.
     * @param identName 識別情報
     * @param infoMap StatisticsInfoが格納されたMap
     * @return StatisticsInfo
     */
    private static StatisticsInfo getStatisticsInfo(final String identName,
                        final Map<String, StatisticsInfo> infoMap) {
        StatisticsInfo result = infoMap.get(identName);
        if (result == null) {
            synchronized (infoMap) {
                StatisticsInfo info = infoMap.get(identName);
                if (info == null) {
                    info = new StatisticsInfo(identName);
                    infoMap.put(identName, info);
                }
                result = info;
            }
        }
        
        return result;
    }
}
