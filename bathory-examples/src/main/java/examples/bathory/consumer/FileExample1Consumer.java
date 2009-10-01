package examples.bathory.consumer;

import org.seasar.bathory.engine.Consumer;

import examples.bathory.dao.EmpDao;
import examples.bathory.dxo.FileExample1Dxo;
import examples.bathory.entity.Emp;

/**
 * .
 * @author toyokazu
 */
public class FileExample1Consumer implements Consumer {
    /** 従業員ID. */
    public String empNo;
    /** 従業員名. */
    public String empName;
    /** マネジャーID. */
    public String mgrId;
    /** 雇用年月日. */
    public String hiredate;
    /** 賃金. */
    public String sal;
    /** 支社ID. */
    public String deptId;
    /** 顧客Dao. */
    public EmpDao empDao;
    /** FileExample1Dxo. */
    public FileExample1Dxo dxo;
    /**
     * @see org.seasar.bathory.engine.Consumer#consume()
     */
    @Override
    public void consume() {
        Emp emp = dxo.convert(this);
        empDao.insert(emp);
    }
}
