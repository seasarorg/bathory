package org.seasar.bathory.engine.statistics;


/**
 * 計上前の統計情報.
 * @author toyokazu
 */
public class TempStatisticsInfo {
    /** 識別情報. */
    private String identName;
    /** 処理件数. */
    private long count = 0L;
    
    /**
     * 識別情報を指定してインスタンスを生成します.
     * @param name 識別情報
     */
    public TempStatisticsInfo(final String name) {
        setIdentName(name);
    }
    /**
     * 識別情報を取得します.
     * @return 識別情報
     */
    protected String getIdentName() {
        return identName;
    }
    /**
     * 識別情報を設定します.
     * @param name 識別情報
     */
    protected void setIdentName(final String name) {
        identName = name;
    }

    /**
     * 処理件数を取得します.
     * @return 処理件数
     */
    protected long getCount() {
        return count;
    }

    /**
     * 処理件数を追加します.
     */
    protected void addCount() {
        addCount(1L);
    }

    /**
     * 処理件数を追加します.
     * @param num 処理件数
     */
    protected void addCount(final long num) {
        count += num;
    }
}
