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
    public static final String CUSTOM_APPSTTING_FILE = "BATSetting";
    /** Casketの識別名. */
    public static final String CASKET_IDENT_NAME = "Casket";

    /**
     * Contextのキー情報.
     * @author toyokazu
     */
    public static final class ContextKeys {
        /** 識別名キー. */
        public static final String IDENT_NAME              = "identName";
        /** ジョブIDキー. */
        public static final String JOBID                   = "jobId";
        /** バッチIDキー. */
        public static final String BATCHID                 = "batchId";
        /** 並列度指定キー. */
        public static final String PARALLELISM             = "parallelism";
        /** リトライ回数指定キー. */
        public static final String RETRY_COUNT             = "retryCount";
        /** 分割コミットキー. */
        public static final String COMMIT_COUNT            = "commitCount";
        /** collector名指定キー. */
        public static final String COLLECTORNAME           = "collectorName";
        /** consumer名指定キー. */
        public static final String CONSUMERNAME            = "consumerName";
        /** 開始時間キー. */
        public static final String STARTDATETIME           = "startDatetime";
        /** 分割キー. */
        public static final String DATA_DIVIDE_KEY         = "dataDivideKey";
        /** データルーティングクラス名. */
        public static final String DATA_ROUTING_RULE_NAME  = "dataRoutingRuleClassName";
        /** データルーティングクラス. */
        public static final String DATA_ROUTING_RULE_CLASS = "dataRoutingRuleClass";

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
        public static final String SUCESS_CODE = "successCode";
        /** 警告コード. */
        public static final String WARN_CODE = "warnCode";
        /** 例外コード. */
        public static final String ERROR_CODE = "errorCode";
        /** ロールバックを行う閾値コード. */
        public static final String ROLLBACK_BORDER = "rollbackBorder";
        /** Queueサイズ. */
        public static final String QUEUE_SIZE = "queueSize";
        /** 通常の並列度. */
        public static final String DEFAULT_PARALLELISM = "defaultParallelism";
        /** 通常のリトライ回数. */
        public static final String DEFAULT_RETRY_COUNT = "defaultRetryCount";
        /** 通常の分割コミット回数. */
        public static final String DEFAULT_COMMIT_COUNT = "defaultCommitCount";

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
