package org.seasar.bathory.exception;

/**
 * 想定外のシステム的な例外が発生した際にスローされます.
 * @author toyokazu
 */
public class SystemException extends UnrecoverableException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ.
     */
    public SystemException() {
        super("EBAT0001");
    }

    /**
     * 原因および詳細メッセージを使用して新規例外を構築します.
     * @param cause 原因および詳細メッセージ
     */
    public SystemException(final Throwable cause) {
        super("EBAT0001", null, cause);
    }
}
