package examples.bathory.dao;

import org.seasar.bathory.extentions.s2dao.FetchHandlerAdapter;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.SqlFile;


/**
 * Example2のDaoクラス.
 * @author toyokazu
 */
public interface S2DaoExample2Dao {
    /**
     * 最近のデータを取得するメソッド.
     * @param date 日付
     * @param adapter FetchHandlerAdapter
     */
    @SqlFile
    @Arguments("targetDate")
    public void selectRecentHistory(String date, FetchHandlerAdapter adapter);
}
