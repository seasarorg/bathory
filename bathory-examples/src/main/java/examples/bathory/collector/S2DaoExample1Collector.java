package examples.bathory.collector;

import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.extentions.s2dao.FetchHandlerAdapter;

import examples.bathory.dao.S2DaoExample1Dao;

public class S2DaoExample1Collector implements Collector {
    public S2DaoExample1Dao dao;

    @Override
    public void collect(final Casket casket) {
        String date = "19810131";
        dao.selectRecentHistory(date, new FetchHandlerAdapter(casket));
    }
}
