package examples.bathory.dao;

import org.seasar.bathory.extentions.s2dao.FetchHandlerAdapter;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.SqlFile;

public interface S2DaoExample1Dao {
    @SqlFile
    @Arguments("targetDate")
    public void selectRecentHistory(String date, FetchHandlerAdapter adapter);
}
