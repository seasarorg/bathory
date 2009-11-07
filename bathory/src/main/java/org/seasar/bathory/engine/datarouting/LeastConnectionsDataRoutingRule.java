package org.seasar.bathory.engine.datarouting;

import java.util.List;
import java.util.Map;

import org.seasar.bathory.engine.handler.ConsumerHandler;

/**
 * 最少接続方式のDataRoutingRule.
 * バッチ起動時、DataRoutingRuleに対する指定を行った際に使用されます.
 * 一番負荷が低いConsumerへデータを割り当てるDataRutingRule.
 * ロードバランサにおける最小接続方式と同様の動作を行います.
 * @author toyokazu
 */
public class LeastConnectionsDataRoutingRule extends BaseDataRoutingRule {
    /**
     * {@inheritDoc}
     * @see org.seasar.bathory.executer.DataRoutingRule#getTarget(java.util.Map)
     */
    @Override
    public int getTarget(final Map<String, Object> values) {
        List<ConsumerHandler> consumers = getConsumers();
        int target   = 0;
        int leastSize = Integer.MAX_VALUE;
        for (int i = 0, iMax = consumers.size(); i < iMax; i++) {
            int undigestedFoodSize = consumers.get(i).getUndigestedFoodSize();
            if (undigestedFoodSize < leastSize) {
                target = i;
                leastSize = undigestedFoodSize;
            }
        }
        return target;
    }
}
