package examples.bathory.dao;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

import examples.bathory.entity.Emp;

/**
 * .
 * @author toyokazu
 */
@S2Dao(bean=Emp.class)
public interface EmpDao {

    /**
     * @return
     */
    public Emp[] selectAll();

    /**
     * @param id
     * @return
     */
    @Arguments("ID")
    public Emp selectById(Long id);

    /**
     * @param emp
     * @return
     */
    public int insert(Emp emp);

    /**
     * @param emp
     * @return
     */
    public int update(Emp emp);

    /**
     * @param emp
     * @return
     */
    public int delete(Emp emp);

}
