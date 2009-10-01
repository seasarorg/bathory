package org.seasar.bathory.exception;

/**
 * 終了ステータスコードを指定して終了したい際にスローします.
 * @author toyokazu
 */
public class ExitException extends UnrecoverableException {
    
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /**
     * 終了ステータスコード.
     */
    private int statusCode;
    /**
     * 終了ステータスコードを指定したコンストラクタ.
     * @param code 終了ステータスコード
     */
    public ExitException(final int code) {
        super("EBAT0002");
        statusCode = code;
    }
    /**
     * 終了ステータスコードを取得します.
     * @return 終了ステータスコード
     */
    public int getStatusCode() {
        return statusCode;
    }

}
