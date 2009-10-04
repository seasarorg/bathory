package org.seasar.bathory.engine.handler;

import java.util.Map;

import javax.transaction.UserTransaction;

import org.seasar.bathory.def.Constants;
import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.exception.SystemException;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.exception.SRuntimeException;


/**
 * Consumerを処理するためのクラス.
 * @author toyokazu
 */
public class ConsumerHandler extends BaseHandler {
    /** Consumer. */
    private Consumer target;

    /**
     * 処理を実行します.
     * @throws Exception 例外を通知
     * @see org.seasar.bathory.engine.handler.BaseHandler#execute()
     */
    @Override
    void execute() throws Exception {
        UserTransaction ut = SingletonS2Container.getComponent(UserTransaction.class);
        try {
            ut.begin();
        } catch (Exception e) {
            // String message = "トランザクション開始に失敗しました";
            throw new SystemException(e);
        }
        try {
            main();
            try {
                if (getContext().isRollbackOnly()) {
                    ut.rollback();
                } else {
                    ut.commit();
                }
            } catch (Exception e) {
                // String message = "コミットに失敗しました";
                throw new SystemException(e);
            }
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception e2) {
                // String message = "ロールバックに失敗しました";
                throw new SystemException(e2);
            }
            if (e instanceof SRuntimeException) {
                throw (SRuntimeException) e;
            }
            throw new SystemException(e);
        }
    }

    /**
     * メイン処理.
     */
    private void main() {
        boolean isEnded = false;
        while (!isEnded) {
            Map<String, Object> values = getCasket().consume();
            if (Constants.END_OF_DATA.equals(values)) {
                isEnded = true;
            } else if (getContext().isRollbackOnly()) {
                // 全体にロールバックが必要な場合は処理を中断する
                isEnded = true;
            } else  {
                if (values != null) {
                    BeanUtil.copyProperties(values, target);
                    target.consume();
                }
            }
        }
    }

    /**
     * Consumerを設定します.
     * @param consumer Consumer
     */
    public void setTarget(final Consumer consumer) {
        this.target = consumer;
    }

}
