package org.seasar.bathory.executer.impl;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.bathory.def.Application;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.statistics.StatisticsInfo;
import org.seasar.bathory.engine.statistics.StatisticsRepository;
import org.seasar.bathory.executer.BatchExecuter;
import org.seasar.bathory.executer.BatchInitializer;
import org.seasar.bathory.executer.BatchMain;
import org.seasar.bathory.executer.BatchTerminator;
import org.seasar.bathory.util.BathoryMessageUtil;


/**
 * 　バッチ実行を司るクラス.
 * @author toyokazu
 */
public class BatchExecuterImpl extends BaseBatch implements BatchExecuter {
    /** Log. */
    private static final Log LOG = LogFactory.getLog(BatchExecuterImpl.class);
    
    /** BatchInitializer. */
    private BatchInitializer initializer;
    /** BatchMain. */
    private BatchMain main;
    /** BatchTerminator. */
    private BatchTerminator terminator;
    
    
    /**
     * BatchInitializerを設定します.
     * @param batchInitializer BatchInitializer
     */
    public void setBatchInitializer(final BatchInitializer batchInitializer) {
        this.initializer = batchInitializer;
    }
    /**
     * BatchMainを設定します.
     * @param batchMain BatchMain
     */
    public void setBatchMain(final BatchMain batchMain) {
        this.main = batchMain;
    }

    /**
     * BatchTerminatorを設定します.
     * @param batchTerminator BatchTerminator
     */
    public void setBatchTerminator(final BatchTerminator batchTerminator) {
        this.terminator = batchTerminator;
    }

    /**
     * バッチ処理を実行します.
     * @param context BathoryContext
     * @return リターンコード
     */
    public int execute(final BathoryContext context) {
       try {
           runImpl(context);
       } catch (Throwable t) {
           handleException(t);
       }
       afterLog();
       int statusCode = context.getStatusCode();
       return statusCode;
    }

    /**
     * バッチ処理を実行します.
     * @param context BathoryContext
     */
    private void runImpl(final BathoryContext context) {
        initializer.initialize();
        if (context.getStatusCode() != Application.getApplication().getSuccessCode()) {
            return;
        }
        main.main();
        terminator.terminate();
    }

    /**
     * 事後ログを出力します.
     */
    private void afterLog() {
        Map<String, StatisticsInfo> statisticInfoMap =
                        StatisticsRepository.getCurrentStatisticsMap();
        if (statisticInfoMap != null) {
            for (Iterator<StatisticsInfo> it = statisticInfoMap.values().iterator();
                 it.hasNext();) {
                StatisticsInfo statisticsInfo = it.next();
                String message = BathoryMessageUtil.getMessage(
                                          "IBAT0001",
                                          statisticsInfo.getIdentName(),
                                          String.valueOf(statisticsInfo.getSuccessCount()),
                                          String.valueOf(statisticsInfo.getWarnCount()),
                                          String.valueOf(statisticsInfo.getErrorCount()),
                                          String.valueOf(statisticsInfo.getExecuteCount()),
                                          String.valueOf(statisticsInfo.getTotalElapsedTime())
                                      
                );
                LOG.debug(message);
            }
        }
        BathoryContext context = BathoryContext.getCurrentInstance();
        int statusCode = context.getStatusCode();
        LOG.debug("status code : " + statusCode);
    }
}
