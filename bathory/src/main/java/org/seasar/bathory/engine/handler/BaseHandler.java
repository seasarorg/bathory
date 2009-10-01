package org.seasar.bathory.engine.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.bathory.def.Constants.RunState;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.exception.ExitException;
import org.seasar.bathory.exception.RecoverableException;


/**
 * Handlerの基底クラス.
 * @author toyokazu
 */
public abstract class BaseHandler extends Thread {
    /** 戻り値. */
    private RunState runState;
    /** BathoryContext. */
    private BathoryContext context;
    /** データ格納クラス. */
    private Casket container;
    /** Log. */
    private static final Log LOG = LogFactory.getLog(BaseHandler.class);

    /**
     * コンストラクタ.
     */
    public BaseHandler() {
        runState   = RunState.ACTIVE;
        context    = BathoryContext.getCurrentInstance();
    }

    /**
     * スレッドを開始します.
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        BathoryContext.setCurrentInstance(context);

        try {
            execute();
            runState = RunState.TERMINATE;
            context.release();
        } catch (Exception e) {
            context.release();
            handleException(e);
            runState = RunState.ABEND;
        }
    }


    /**
     * 戻り値を取得します.
     * @return 戻り値
     */
    public RunState getRunState() {
        return runState;
    }

    /**
     * Handlerを実行します.
     * @throws Exception 例外を通知
     */
    abstract void execute() throws Exception;

    /**
     * BathoryContextを取得します.
     * @return BathoryContext
     */
    protected BathoryContext getContext() {
        return context;
    }

    /**
     * Casketを設定します.
     * @param casket Casket
     */
    public void setCasket(final Casket casket) {
        container = casket;
    }

    /**
     * Casketを取得します.
     * @return Casket
     */
    public Casket getCasket() {
        return container;
    }

    /**
     * 例外を処理します.
     * TODO 例外処理クラスを作成し、処理を委譲可能にする
     * @param t 発生した例外
     */
    private void handleException(final Throwable t) {
        if (t instanceof RecoverableException) {
            LOG.info(t, t);
            runState   = RunState.TERMINATE;
            context.raizeWarning();
        } else {
            LOG.error(t, t);
            if (t instanceof ExitException) {
                ExitException e = (ExitException) t;
                context.setStatusCode(e.getStatusCode());
            } else {
                context.raizeError();
            }
        }
        
    }
}