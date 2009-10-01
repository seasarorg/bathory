package org.seasar.bathory.def;

import org.seasar.bathory.def.Constants.ApplicationKeys;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.message.MessageResourceBundleFactory;


/**
 * アプリケーションの設定情報を取得するためのクラス.
 * @author toyokazu
 */
public class Application {
    /**
     * Applicationインスタンスを取得します.
     * @return Applicationインスタンス
     */
    public static Application getApplication() {
        return SingletonS2Container.getComponent(Application.class);
    }
    
    /**
     * 正常コードを取得します.
     * @return 正常コード
     */
    public int getNormalCode() {
        return getIntValue(ApplicationKeys.NORMAL_CODE);
    }

    /**
     * 警告コードを取得します.
     * @return 警告コード
     */
    public int getWarnCode() {
        return getIntValue(ApplicationKeys.WARN_CODE);
    }

    /**
     * 例外コードを取得します.
     * @return 例外コード
     */
    public int getErrorCode() {
        return getIntValue(ApplicationKeys.ERROR_CODE);
    }

    /**
     * Queueサイズを取得します.
     * @return Queueサイズ
     */
    public int getQueueSize() {
        return getIntValue(ApplicationKeys.QUEUE_SIZE);
    }

    /**
     * 通常の並列度を取得します.
     * @return 通常の並列度
     */
    public int getDefaultParallelism() {
        return getIntValue(ApplicationKeys.DEFAULT_PARALLELISM);
    }

    
    /**
     * 設定値を取得します.
     * @param key 設定キー
     * @return 設定値
     */
    private String getValue(final String key) {
        String value = null;
        MessageResourceBundle appBundle =
            MessageResourceBundleFactory.getNullableBundle(Constants.CUSTOM_APPSTTING_FILE);
        if (appBundle != null) {
            value = appBundle.get(key);
        }
        
        if (value == null) {
            MessageResourceBundle defaultBundle =
                MessageResourceBundleFactory.getBundle(Constants.DEFAULT_APPSTTING_FILE);
            value = defaultBundle.get(key);
        }
        return value;
    }
    /**
     * 設定値を数値として取得します.
     * @param key 設定キー
     * @return 設定値
     */
    private int getIntValue(final String key) {
        return Integer.parseInt(getValue(key));
    }
}
