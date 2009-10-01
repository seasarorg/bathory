package org.seasar.bathory.util;

import java.text.MessageFormat;

import org.seasar.bathory.def.Constants;
import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.message.MessageResourceBundleFactory;


/**
 * メッセージに関するユーティリティクラス.
 * @author toyokazu
 */
public final class BathoryMessageUtil {
    /**
     * インスタンス化禁止.
     */
    private BathoryMessageUtil() {
    }
    
    /**
     * メッセージを取得します.
     * @param messageCode メッセージコード
     * @param arguments 置換文字列
     * @return メッセージ
     */
    public static String getMessage(final String messageCode, final String ... arguments) {
        String message = null;
        
        // TODO 複数ファイル設置できるよう検討する
        MessageResourceBundle appBundle =
            MessageResourceBundleFactory.getNullableBundle(Constants.APP_MESSAGE_FILE);
        if (appBundle != null) {
            message = appBundle.get(messageCode);
        }

        if (message == null) {
            MessageResourceBundle defaultBundle =
                MessageResourceBundleFactory.getBundle(Constants.DEFAULT_MESSAGE_FILE);
            message = defaultBundle.get(messageCode);
        }
        if (message != null) {
            message = getFormattedMessage(message, arguments);
        }
        
        return message;
    }
    /**
     * メッセージの置換文字列を変換した文字列表現を取得します.
     * @param message メッセージ文字列
     * @param arguments 置換文字列
     * @return 置換後文字列
     */
    private static String getFormattedMessage(final String message,
                                              final String ... arguments) {
        if (message == null || arguments.length == 0) {
            return message;
        }
        return new MessageFormat(message).format(arguments);
    }
}
