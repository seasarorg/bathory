package examples.bathory.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import org.seasar.bathory.engine.Consumer;
import org.seasar.framework.beans.util.BeanUtil;

import examples.bathory.dao.EmpDao;
import examples.bathory.entity.Emp;

public class S2DaoExample1Consumer implements Consumer {

    public Long id;
    public Integer empNo;
    public String empName;
    public Integer mgrId;
    public Date hiredate;
    public BigDecimal sal;
    public Integer deptId;
    public Integer versionNo;

    public EmpDao dao;
    @Override
    public void consume() {
        // 自分自身に値が格納されているので、処理を行う。
        Emp data = new Emp();
        BeanUtil.copyProperties(this, data);
        
        // 業務処理を記述していく
        // 以下は仮想的な業務処理
        int rand = new Random(System.currentTimeMillis()).nextInt(100);
        if (rand % 5 == 0) {
            // 減給もアリ
            rand *= -1;
        }
        BigDecimal diff = BigDecimal.valueOf(rand);
        data.sal = (data.sal.add(diff));

        dao.update(data);
    }
}
