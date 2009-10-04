package org.seasar.bathory.executer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.bathory.def.Constants;
import org.seasar.bathory.def.Constants.RunState;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.engine.handler.BaseHandler;
import org.seasar.bathory.engine.handler.CollectorHandler;
import org.seasar.bathory.engine.handler.ConsumerHandler;
import org.seasar.bathory.engine.statistics.StatisticsInfo;
import org.seasar.bathory.engine.statistics.StatisticsRepository;
import org.seasar.bathory.exception.SystemException;
import org.seasar.bathory.executer.BatchMain;


/**
 * {@link BatchMain}の実装クラス.
 * @author toyokazu
 */
public class BatchMainImpl extends BaseBatch implements BatchMain {
    /** 処理待ち時間. */
    private static final long WAIT_TIME = 1000L;
    /** 何件ごとにログを出力するか. */
    private static final long INTERVAL_OF_MARK = 1000L;
    /** 次にログを出力する件数. */
    private long nextMarkCount = INTERVAL_OF_MARK;
    /** Log. */
    private static final Log LOG = LogFactory.getLog(BatchMainImpl.class);

    /**
     * メイン処理を実行します.
     * @see org.seasar.bathory.executer.BatchMain#main()
     */
    @Override
    public void main() {
        try {
            mainImpl();
        } catch (Throwable t) {
            handleException(t);
        }
    }
    /**
     * メイン処理を実装.
     */
    public void mainImpl() {
        BathoryContext context = BathoryContext.getCurrentInstance();
        Casket casket          = new Casket();
        int parallelism        = context.getParallelism();

        Collector collector       = getCollector(context);
        CollectorHandler cHandler = new CollectorHandler();
        cHandler.setTarget(collector);
        cHandler.setCasket(casket);
        cHandler.setName("CollectorThread");
        cHandler.start();

        List<ConsumerHandler> consumers = new ArrayList<ConsumerHandler>(parallelism);
        for (int i = 0; i < parallelism; i++) {
            Consumer consumer       = getConsumer(context);
            ConsumerHandler handler = new ConsumerHandler();
            handler.setCasket(casket);
            handler.setTarget(consumer);
            handler.setName("ConsumerThread#" + String.valueOf(i));
            
            consumers.add(handler);
            handler.start();
        }


        List<BaseHandler> handlers = new ArrayList<BaseHandler>(parallelism + 1);
        handlers.add(cHandler);
        handlers.addAll(consumers);

        boolean isTerminated = false;
        while (!isTerminated) {
            // TODO ひとまず、ループとしておく
            // セマフォとどちらがよいか？
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                // 処理が中断されました
                throw new SystemException(e);
            }
            isTerminated = isTerminated(handlers);
        }
    }
    /**
     * 処理が終了したか確認します.
     * @param handlers BaseHandler
     * @return 処理が終了したか
     */
    protected boolean isTerminated(final List<BaseHandler> handlers) {
        boolean isTerminated = true;
        for (int i = 0; i < handlers.size(); i++) {
            BaseHandler handler = handlers.get(i);
            RunState runState = handler.getRunState();
            switch (runState) {
            case ACTIVE:
                isTerminated = false;
                break;
            default:
                break;
            }
        }
        log();
        return isTerminated;
    }
    
    /**
     * ログ出力を行います.
     */
    private void log() {
        Map<String, StatisticsInfo> statisticInfoMap =
                        StatisticsRepository.getCurrentStatisticsMap();
        if (statisticInfoMap == null) {
            return;
        }
        StatisticsInfo statisticsInfo = statisticInfoMap.get(Constants.CASKET_IDENT_NAME);
        if (statisticsInfo == null) {
            return;
        }
        if (statisticsInfo.getExecuteCount() > nextMarkCount) {
            LOG.info("EXCUTED COUNT : " + String.valueOf(nextMarkCount));
            nextMarkCount += INTERVAL_OF_MARK;
        }
    }
}
