package org.seasar.bathory.exception;

import org.seasar.framework.exception.SRuntimeException;

/**
 * 回復可能な場合にスローされます.
 * @author toyokazu
 */
public class RecoverableException extends SRuntimeException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * メッセージコードを指定したコンストラクタ.
     * @param messageCode メッセージコード
     */
    public RecoverableException(final String messageCode) {
        super(messageCode);
    }

    /**
     * メッセージコード・引数の配列を指定したコンストラクタ.
     * @param messageCode メッセージコード
     * @param args 引数の配列
     */
    public RecoverableException(final String messageCode, final Object ... args) {
        super(messageCode, args);
    }

    /**
     * メッセージコード・引数の配列を指定したコンストラクタ.
     * @param messageCode メッセージコード
     * @param args 引数の配列
     * @param cause 原因例外
     */
    public RecoverableException(final String messageCode,
                                final Object[] args,
                                final Throwable cause) {
        super(messageCode, args, cause);
    }
}
