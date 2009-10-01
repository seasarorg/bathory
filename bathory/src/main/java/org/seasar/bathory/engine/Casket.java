package org.seasar.bathory.engine;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.seasar.bathory.def.Application;
import org.seasar.bathory.engine.statistics.StatisticsRecorder;
import org.seasar.bathory.exception.SystemException;


/**
 * データを格納するためのクラス.
 * @author toyokazu
 */
public class Casket {
    /** データを蓄積するためのQueue. */
    private BlockingQueue<Map<String, Object>> queue;
    /** データ収集が完了したか否か. */
    private boolean isCollectEnded = false;
    /** Queueのバッファ容量. */
    private static final int QUEUE_SIZE = Application.getApplication().getQueueSize();

    /**
     * コンストラクタ.
     */
    public Casket() {
        queue = new ArrayBlockingQueue<Map<String, Object>>(QUEUE_SIZE);
    }

    /**
     * 収集したデータを格納する.
     * @param value 収集データ
     */
    public void put(final Map<String, Object> value) {
        try {
            StatisticsRecorder.addCount("Casket", 1);
            queue.put(value);
        } catch (InterruptedException e) {
            // 収集が終了していないのにConsumerの処理が中断
            throw new SystemException();
        }
    }

    /**
     * 収集データを消費する.
     * @return 収集したデータ
     */
    public Map<String, Object> consume() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            // 収集が終了していないのにConsumerの処理が中断
            throw new SystemException();
        }
    }

    /**
     * 収集が終了したか否かを取得します.
     * @return 収集が終了したか否か
     */
    public boolean isCollectEnded() {
        return isCollectEnded;
    }

}
