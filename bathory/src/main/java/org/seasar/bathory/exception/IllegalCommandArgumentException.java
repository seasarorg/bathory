package org.seasar.bathory.exception;

/**
 * .
 * @author toyokazu
 */
public class IllegalCommandArgumentException extends UnrecoverableException {
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルトコンストラクタ.
     * @param itemKey 検証項目キー情報
     * @param itemName 検証項目名
     */
    public IllegalCommandArgumentException(final String itemKey,
                                           final String itemName) {
        super("EBAT0003", itemName, itemKey);
    }

    /**
     * 原因および詳細メッセージを使用して新規例外を構築します.
     * @param cause 原因および詳細メッセージ
     */
    public IllegalCommandArgumentException(final Throwable cause) {
        super("EBAT0003", null, cause);
    }
}
