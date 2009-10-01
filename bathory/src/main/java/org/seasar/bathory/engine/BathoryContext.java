package org.seasar.bathory.engine;

import java.util.Date;
import java.util.Map;

import org.seasar.bathory.def.Application;


/**
 * バッチ実行アプリケーションがバッチコンテナと通信する際に使用するクラス.
 * @author toyokazu
 */
public class BathoryContext {
    /** 並行度. */
    private int parallelism;

    /** バッチに引き渡したいパラメータ. */
    private Map<String, Object> properties;
    /** 識別名. */
    private String identName;
    /** ジョブID. */
    private String jobId;
    /** バッチID. */
    private String batchId;
    /** バッチ開始時間. */
    private Date startDate;
    /** 収集モジュール名. */
    private String collectorName;
    /** 処理モジュール名. */
    private String consumerName;
    /** 処理開始日時. */
    private Date startDatetime;
    
    
    
    /** ステータスコード. */
    private int statusCode = Application.getApplication().getNormalCode();

    /** ThreadLocal. */
    private static ThreadLocal<BathoryContext> threadLocal = new ThreadLocal<BathoryContext>();

    /**
     * コンストラクタ.
     */
    public BathoryContext() {
    }

    /**
     * 並列度を取得します.
     * @return 並列度
     */
    public int getParallelism() {
        return parallelism;
    }

    /**
     * 並列度を設定します.
     * @param size 並列度
     */
    public void setParallelism(final int size) {
        this.parallelism = size;
    }

    /**
     * 実行しているバッチに指定されたプロパティを取得します.
     * @return バッチに指定されたプロパティ
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * バッチにプロパティを設定します.
     * @param values プロパティ
     */
    public void setProperties(final Map<String, Object> values) {
        this.properties = values;
    }

    /**
     * 識別名を取得します.
     * @return 識別名
     */
    public String getIdentName() {
        return identName;
    }

    /**
     * 識別名を設定します.
     * @param name 識別名
     */
    public void setIdentName(final String name) {
        this.identName = name;
    }

    /**
     * 実行するジョブIDを取得します.
     * @return 実行するジョブID
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * 実行するジョブIDを設定します.
     * @param id ジョブID
     */
    public void setJobId(final String id) {
        this.jobId = id;
    }

    /**
     * バッチIDを取得します.
     * @return バッチID
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * バッチIDを設定します.
     * @param id バッチID
     */
    public void setBatchId(final String id) {
        this.batchId = id;
    }

    /**
     * バッチを開始した時間を取得します.
     * @return バッチ開始時間
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * バッチを開始した日時を設定します.
     * @param datetime バッチ開始日時
     */
    public void setStartDate(final Date datetime) {
        this.startDate = datetime;
    }

    
    /**
     * BatchContextを取得します.
     * @return BatchContext
     */
    public static BathoryContext getCurrentInstance() {
        return threadLocal.get();
    }

    /**
     * BatchContextをリリースします.
     */
    public final void release() {
        threadLocal.set(null);
    }

    /**
     * BatchContextを設定します.
     * @param context BatchContext
     */
    public static void setCurrentInstance(final BathoryContext context) {
        threadLocal.set(context);
    }
    
    /**
     * 収集モジュール名を取得します.
     * @return 収集モジュール名
     */
    public String getCollectorName() {
        return collectorName;
    }

    /**
     * 収集モジュール名を設定します.
     * @param name 収集モジュール名
     */
    public void setCollectorName(final String name) {
        collectorName = name;
    }

    /**
     * 処理モジュール名を取得します.
     * @return 処理モジュール名
     */
    public String getConsumerName() {
        return consumerName;
    }

    /**
     * 処理モジュール名を設定します.
     * @param name 処理モジュール名
     */
    public void setConsumerName(final String name) {
        consumerName = name;
    }
    

    
    
    
    
    /**
     * 処理開始日時を取得します.
     * @return 処理開始日時
     */
    protected Date getStartDatetime() {
        return startDatetime;
    }

    /**
     * 処理開始日時を設定します.
     * @param datetime 処理開始日時
     */
    protected void setStartDatetime(final Date datetime) {
        this.startDatetime = datetime;
    }

    /**
     * ステータスコードを設定します.
     * 一度設定した値より低い数字の場合は無視されます.
     * @param code コード
     */
    public void setStatusCode(final int code) {
        if (code > statusCode) {
            statusCode = code;
        }
    }

    /**
     * ステータスコードを取得します.
     * @return ステータスコード
     */
    public int getStatusCode() {
        return statusCode;
    }

    /** 警告が発生. */
    public void raizeWarning() {
        setStatusCode(Application.getApplication().getWarnCode());
    }

    /** 例外が発生. */
    public void raizeError() {
        setStatusCode(Application.getApplication().getErrorCode());
    }
}
