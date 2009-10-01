package org.seasar.bathory.extentions.seasar2.interceptor;

import java.sql.Connection;
import java.sql.Savepoint;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.exception.RecoverableException;
import org.seasar.extension.dbcp.impl.ConnectionWrapperImpl;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.SingletonS2Container;


/**
 * Savepointを実現するためのInterceptorです.
 * @author toyokazu
 */
public class SavepointInterceptor extends AbstractInterceptor  {
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * インターセプト処理を実行します.
     * @param invocation メソッド実行に対応するオブジェクト
     * @return 戻り値
     * @throws Throwable 発生した例外を通知します.
     */
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        DataSource dataSource = SingletonS2Container.getComponent(DataSource.class);
        Connection connection = ((ConnectionWrapperImpl) dataSource.getConnection())
                                    .getPhysicalConnection();
        Savepoint savepoint   = connection.setSavepoint("SavepointOfBathory");
        try {
            Object ret = invocation.proceed();
            return ret;
        } catch (RecoverableException e) {
            connection.rollback(savepoint);
            BathoryContext.getCurrentInstance().raizeWarning();
            return null;
        }
    }
}
