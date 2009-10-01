package org.seasar.bathory.extentions.seasar2.interceptor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.bathory.engine.statistics.StatisticsRecorder;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;


/**
 * 統計情報を設定するためのInterceptorです.
 * @author toyokazu
 */
public class StatisticsRegistInterceptor extends AbstractInterceptor  {
    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** クラス名を抽出するためのパターン. */
    private static final Pattern EXTRACT_PATTERN =
                    Pattern.compile("([^\\.]+)\\$\\$EnhancedByS2AOP");
    /**
     * インターセプト処理を実行します.
     * @param invocation メソッド実行に対応するオブジェクト
     * @return 戻り値
     * @throws Throwable 発生した例外を通知します.
     */
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        if (isObjectMethod(invocation)) {
            return invocation.proceed();
        }


        long before      = System.currentTimeMillis();
        Object ret       = null;
        String identName = getIdentString(invocation);
        try {
            ret = invocation.proceed();
            int count = getCount(ret);
            StatisticsRecorder.addCount(identName, count);
            return ret;
        } catch (Throwable t) {
            // エラー時はデフォルトで１を追加する
            int count = 1;
            StatisticsRecorder.addCount(identName, count);
            throw t;
        } finally {
            long after = System.currentTimeMillis();
            StatisticsRecorder.elapse(identName, after - before);
        }
    }

    /**
     * 戻り値より、統計件数を取得します.
     * @param ret 戻り値
     * @return 統計件数
     */
    @SuppressWarnings("unchecked")
    private int getCount(final Object ret) {
        if (ret instanceof Object[]) {
            return ((Object[]) ret).length;
        } else if (ret instanceof List) {
            return ((List) ret).size();
        } else if (ret instanceof Number) {
            return ((Number) ret).intValue();
        } else {
            return 0;
        }
    }
    /**
     * 対象を識別するための文字列表現を取得します.
     * @param invocation MethodInvocation
     * @return 対象を識別するための文字列表現
     */
    private String getIdentString(final MethodInvocation invocation) {
        String complexName = invocation.getClass().getName();
        String methodName  = invocation.getMethod().getName();

        Matcher matcher = EXTRACT_PATTERN.matcher(complexName);

        matcher.find();
        String className = matcher.group(1);

        StringBuilder sb = new StringBuilder();
        sb.append(className);
        sb.append("#");
        sb.append(methodName);

        return sb.toString();
    }

    /**
     * Objectのメソッドか否か.
     * @param invocation MethodInvocation
     * @return ログ書き込み対象か否か
     */
    private boolean isObjectMethod(final MethodInvocation invocation) {
        Class<?> clazz = invocation.getMethod().getDeclaringClass();
        return clazz.getPackage().getName().startsWith("java");
    }
}
