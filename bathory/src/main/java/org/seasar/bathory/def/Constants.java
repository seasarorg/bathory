package org.seasar.bathory.def;

import java.util.HashMap;
import java.util.Map;

/**
 * 定数定義クラス.
 * @author toyokazu
 */
public class Constants {
    /** Collectorのサフィックス. */
    public static final String SUFFIX_OF_COLLECTOR = "Collector";
    /** Consumerのサフィックス. */
    public static final String SUFFIX_OF_CONSUMER  = "Consumer";
    /** 処理が終了したことを通知するデータ. */
    public static final Map<String, Object> END_OF_DATA = new HashMap<String, Object>();
    /** デフォルトのメッセージファイル名. */
    public static final String DEFAULT_MESSAGE_FILE = "BATMessages";
    /** アプリケーションのメッセージファイル名. */
    public static final String APP_MESSAGE_FILE = "BATApplicationMessages";
    /** デフォルトのアプリケーション設定ファイル名. */
    public static final String DEFAULT_APPSTTING_FILE = "BATApplicationSetting";
    /** カスタムのアプリケーション設定ファイル名. */
    public static final String CUSTOM_APPSTTING_FILE = "BATApplicationSetting";

    /**
     * Contextのキー情報.
     * @author toyokazu
     */
    public static final class ContextKeys {
        /** 識別名キー. */
        public static final String IDENT_NAME         = "identName";
        /** ジョブIDキー. */
        public static final String JOBID              = "jobId";
        /** バッチIDキー. */
        public static final String BATCHID            = "batchId";
        /** 並列度指定キー. */
        public static final String PARALLELISM        = "parallelism";
        /** collector名指定キー. */
        public static final String COLLECTORNAME      = "collectorName";
        /** consumer名指定キー. */
        public static final String CONSUMERNAME       = "consumerName";
        /** 開始時間キー. */
        public static final String STARTDATETIME      = "startDatetime";

        /**
         * インスタンス化禁止.
         */
        private ContextKeys() {
        }
    }

    /**
     * Application設定ファイルに関するキー情報.
     * @author toyokazu
     */
    public static final class ApplicationKeys {
        /** 正常コード. */
        public static final String NORMAL_CODE = "normalCode";
        /** 警告コード. */
        public static final String WARN_CODE = "warnCode";
        /** 例外コード. */
        public static final String ERROR_CODE = "errorCode";
        /** Queueサイズ. */
        public static final String QUEUE_SIZE = "queueSize";
        /** 通常の並列度. */
        public static final String DEFAULT_PARALLELISM = "defaultParallelism";
        
        /**
         * インスタンス化禁止.
         */
        private ApplicationKeys() {
        }
    }
    /**
     * Collector/Consumerの実行状態.
     */
    public enum RunState {
        /** 実行中. */
        ACTIVE,
        /** 終了. */
        TERMINATE,
        /** 異常終了（他の状態にかかわらず終了）. */
        ABEND,
    }

    /**
     * 定数定義クラスのため、インスタンス化禁止.
     */
    private Constants() {
    }
}
