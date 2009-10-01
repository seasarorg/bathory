package examples.bathory.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.exception.RecoverableException;
import org.seasar.framework.beans.util.BeanUtil;

import examples.bathory.dao.EmpDao;
import examples.bathory.entity.Emp;


/**
 * Example2のConsumer.
 * @author toyokazu
 */
public class S2DaoExample2Consumer implements Consumer {

    /** id. */
    public Long id;
    /** empNo. */
    public Integer empNo;
    /** empName. */
    public String empName;
    /** mgrId. */
    public Integer mgrId;
    /** hiredate. */
    public Date hiredate;
    /** sal. */
    public BigDecimal sal;
    /** deptId. */
    public Integer deptId;
    /** versionNo. */
    public Integer versionNo;
    /** EmpDao. */
    public EmpDao dao;
    /**
     * データ処理.
     * @see org.seasar.bathory.engine.Consumer#consume()
     */
    @Override
    public void consume() {
        // 自分自身に値が格納されているので、処理を行う。
        Emp data = new Emp();
        BeanUtil.copyProperties(this, data);
        
        // 業務処理を記述していく
        // 業績を計算するetc
        int rand = new Random(System.currentTimeMillis()).nextInt(100);
        if (rand % 5 == 0) {
            // 減給もアリ
            rand *= -1;
        }
        BigDecimal diff = BigDecimal.valueOf(rand);
        data.sal = (data.sal.add(diff));

        dao.update(data);
        
        if (data.id % 5 == 0) {
            // TODO メッセージコードに関する機能を実装する
            throw new RecoverableException("");
        }
    }
}
