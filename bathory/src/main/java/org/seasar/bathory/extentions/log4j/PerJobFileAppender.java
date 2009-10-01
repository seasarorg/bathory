package org.seasar.bathory.extentions.log4j;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.seasar.bathory.engine.BathoryContext;


/**
 * ジョブごとにファイルを保持するAppenderクラス.
 * @author toyokazu
 */
public class PerJobFileAppender extends FileAppender {

    /** ベースとなるディレクトリ名. */
    private String baseDirName;

    /**
     * コンストラクタ.
     */
    public PerJobFileAppender() {
        setFile(getFile());
    }
    /**
     * コンストラクタ.
     *
     * @param layout レイアウト
     * @param filename ログファイル名
     * @throws IOException IOException
     */
    public PerJobFileAppender(final Layout layout, final String filename) throws IOException {
        setBaseDirName(fileName);
        setLayout(layout);
        setFile(getFile());
    }

    /**
     * コンストラクタ.
     *
     * @param layout レイアウト
     * @param filename ログファイル名
     * @param append ログファイル追記有無
     * @throws IOException IOException
     */
    public PerJobFileAppender(final Layout layout, final String filename, final boolean append)
            throws IOException {
        setBaseDirName(fileName);
        setAppend(append);
        setLayout(layout);
        setFile(getFile());
    }


    /**
     * ファイル名を取得します.<br/>
     * {@link FileAppender#getFile()}
     * @see org.apache.log4j.FileAppender#getFile()
     * @return ファイル名
     */
    @Override
    public String getFile() {
        BathoryContext context = BathoryContext.getCurrentInstance();
        DateFormat format      = new SimpleDateFormat("yyyyMMdd_HHmmss");

        StringBuilder sb = new StringBuilder();
        sb.append(baseDirName);
        sb.append(context.getJobId());
        sb.append("_");
        sb.append(context.getBatchId());
        sb.append(format.format(context.getStartDate()));

        return sb.toString();
    }

    /**
     * ベースとなるディレクトリ名を設定します.
     * @param baseName ベースとなるディレクトリ名
     */
    protected void setBaseDirName(final String baseName) {
        if (baseName.endsWith("/") || baseName.endsWith("\\")) {
            baseDirName = baseName;
        } else {
            baseDirName = baseName + "/";
        }
    }

    /**
     * @return the baseDirName
     */
    protected String getBaseDirName() {
        return baseDirName;
    }
}
