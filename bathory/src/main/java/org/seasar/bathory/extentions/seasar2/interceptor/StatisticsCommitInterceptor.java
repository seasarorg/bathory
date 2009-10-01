package org.seasar.bathory.extentions.seasar2.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.bathory.engine.statistics.StatisticsRecorder;
import org.seasar.bathory.exception.RecoverableException;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;


/**
 * 統計情報を取得するためのInterceptorです.
 * @author toyokazu
 */
public class StatisticsCommitInterceptor extends AbstractInterceptor  {
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * インターセプト処理を実行します.
     * @param invocation メソッド実行に対応するオブジェクト
     * @return 戻り値
     * @throws Throwable 発生した例外を通知します.
     */
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        try {
            Object ret = invocation.proceed();
            StatisticsRecorder.markAsNormal();
            return ret;
        } catch (RecoverableException e) {
            StatisticsRecorder.markAsWarn();
            throw e;
        } catch (Throwable t) {
            StatisticsRecorder.markAsError();
            throw t;
        }
    }
}
