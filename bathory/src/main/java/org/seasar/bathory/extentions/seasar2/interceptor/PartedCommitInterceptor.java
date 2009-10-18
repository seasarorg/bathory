package org.seasar.bathory.extentions.seasar2.interceptor;

import java.sql.Connection;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.extension.dbcp.impl.ConnectionWrapperImpl;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.SingletonS2Container;


/**
 * 分割コミットを実現するためのInterceptorです.
 * @author toyokazu
 */
public class PartedCommitInterceptor extends AbstractInterceptor  {
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** ThreadLocal. */
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    /**
     * インターセプト処理を実行します.
     * @param invocation メソッド実行に対応するオブジェクト
     * @return 戻り値
     * @throws Throwable 発生した例外を通知します.
     */
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        BathoryContext context = BathoryContext.getCurrentInstance();
        long commitCount = context.getCommitCount();
        if (commitCount > 0L) {
            countUp(commitCount);
        }
        return invocation.proceed();
    }

    /**
     * カウントアップ処理を実行します.
     * @param commitCount コミット件数
     * @throws Throwable 発生した例外を通知します.
     */
    private void countUp(final long commitCount) throws Throwable {
        Long current = threadLocal.get();

        if (current == null) {
            current = Long.valueOf(1L);
        }
        if (current.longValue() >= commitCount) {
            commit();
            current = Long.valueOf(0L);
        } 
        Long next = Long.valueOf(current.longValue() + 1L);
        threadLocal.set(next);
    }

    /**
     * コミット処理を実行します.
     * @throws Throwable 発生した例外を通知します.
     */
    private void commit() throws Throwable {
        DataSource dataSource = SingletonS2Container.getComponent(DataSource.class);
        Connection connection = ((ConnectionWrapperImpl) dataSource.getConnection())
                                    .getPhysicalConnection();
        connection.commit();
    }
}
