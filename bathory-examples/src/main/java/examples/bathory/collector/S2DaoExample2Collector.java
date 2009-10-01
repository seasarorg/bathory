package examples.bathory.collector;

import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.extentions.s2dao.FetchHandlerAdapter;

import examples.bathory.dao.S2DaoExample2Dao;

/**
 * S2Daoを使用したExampleのCollector.
 * @author toyokazu
 */
public class S2DaoExample2Collector implements Collector {
    /** Example2Dao. */
    public S2DaoExample2Dao dao;

    /**
     * データ収集.
     * @param casket Casket
     * @see org.seasar.bathory.engine.Collector#collect(bathory.engine.Basket)
     */
    @Override
    public void collect(final Casket casket) {
        // ここには、データ収集を行う処理を記述する
        String date = "19810131";
        dao.selectRecentHistory(date, new FetchHandlerAdapter(casket));
    }
}
