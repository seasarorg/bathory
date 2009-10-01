package examples.bathory.dao;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

import examples.bathory.entity.Dept;

/**
 * .
 * @author toyokazu
 */
@S2Dao(bean=Dept.class)
public interface DeptDao {

    /**
     * @return
     */
    public Dept[] selectAll();

    /**
     * @param id
     * @return
     */
    @Arguments("ID")
    public Dept selectById(Long id);

    /**
     * @param dept
     * @return
     */
    public int insert(Dept dept);

    /**
     * @param dept
     * @return
     */
    public int update(Dept dept);

    /**
     * @param dept
     * @return
     */
    public int delete(Dept dept);

}
