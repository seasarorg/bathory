package org.seasar.bathory.engine.statistics;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 統計情報.
 * @author toyokazu
 */
public class StatisticsInfo implements Serializable {
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** 識別情報. */
    private String identName;
    /** 正常処理件数. */
    private AtomicLong normalCount = new AtomicLong();
    /** 警告処理件数. */
    private AtomicLong warningCount = new AtomicLong();
    /** 例外処理件数. */
    private AtomicLong errorCount = new AtomicLong();
    /** 全体処理件数. */
    private AtomicLong executeCount = new AtomicLong();
    /** 全体処理時間. */
    private AtomicLong totalElapsedTime = new AtomicLong();

    /**
     * 識別情報を指定してインスタンスを生成します.
     * @param name 識別情報
     */
    public StatisticsInfo(final String name) {
        setIdentName(name);
    }
    /**
     * 識別情報を取得します.
     * @return 識別情報
     */
    public String getIdentName() {
        return identName;
    }
    /**
     * 識別情報を設定します.
     * @param name 識別情報
     */
    public void setIdentName(final String name) {
        identName = name;
    }

    /**
     * 正常処理件数を取得します.
     * @return 正常処理件数
     */
    public long getNormalCount() {
        return normalCount.longValue();
    }

    /**
     * 正常処理件数を追加します.
     * @param num 正常処理件数
     */
    public void addNormalCount(final long num) {
        normalCount.addAndGet(num);
    }

    /**
     * 警告処理件数を取得します.
     * @return 警告処理件数
     */
    public long getWarnCount() {
        return warningCount.longValue();
    }

    /**
     * 警告処理件数を追加します.
     * @param num 警告処理件数
     */
    public void addWarnCount(final long num) {
        warningCount.addAndGet(num);
    }

    /**
     * 例外処理件数を取得します.
     * @return 例外処理件数
     */
    public long getErrorCount() {
        return errorCount.longValue();
    }

    /**
     * 例外処理件数を追加します.
     * @param num 例外処理件数
     */
    public void addErrorCount(final long num) {
        errorCount.addAndGet(num);
    }

    /**
     * 全体処理件数を取得します.
     * @return 全体処理件数
     */
    public long getExecuteCount() {
        return executeCount.longValue();
    }

    /**
     * 全体処理件数を追加します.
     */
    public void addExecuteCount() {
        executeCount.addAndGet(1L);
    }

    /**
     * 例外処理件数を取得します.
     * @return 例外処理件数
     */
    public long getTotalElapsedTime() {
        return totalElapsedTime.longValue();
    }

    /**
     * 全体処理時間を追加します.
     * @param time 全体処理時間
     */
    public void addTotalElapsedTime(final long time) {
        totalElapsedTime.addAndGet(time);
    }
}
