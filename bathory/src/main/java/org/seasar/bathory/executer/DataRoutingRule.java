package org.seasar.bathory.executer;

import java.util.List;
import java.util.Map;

import org.seasar.bathory.engine.handler.ConsumerHandler;

/**
 * Collectorから取得したデータをConsumerへ引き渡すルーティングを行うルーティングルール.
 * @author toyokazu
 */
public interface DataRoutingRule {

    /**
     * ConsumerHandlerを設定します.
     * @param consumerList 全てのConsumerHandler
     */
    void setConsumers(List<ConsumerHandler> consumerList);

    /**
     * 対象ConsumerHandlerの順番を返します.
     * @param values Collectorから引き渡されたデータ
     * @return 対象ConsumerHandlerの順番
     */
    int getTarget(Map<String, Object> values);
}
