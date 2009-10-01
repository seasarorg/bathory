package org.seasar.bathory.extentions.seasar2.creator;

import org.seasar.bathory.def.Constants;
import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;


/**
 * Collectorを作成するCreatorクラス.
 * @author toyokazu
 */
public class ConsumerCreator extends ComponentCreatorImpl {

    /**
     * 指定された{@link NamingConvention 命名規約}に従った{@link ConsumerCreator}を作成します.
     * @param namingConvention 命名規約
     */
    public ConsumerCreator(final NamingConvention namingConvention) {
        super(namingConvention);
        setNameSuffix(Constants.SUFFIX_OF_CONSUMER);
        setInstanceDef(InstanceDefFactory.PROTOTYPE);
    }

    /**
     * Collector用の {@link ComponentCustomizer}を返します.
     *
     * @return コンポーネントカスタマイザ
     */
    public ComponentCustomizer getConsumerCustomizer() {
        return getCustomizer();
    }

    /**
     * Collector用の {@link ComponentCustomizer}を設定します.
     *
     * @param customizer コンポーネントカスタマイザ
     */
    public void setConsumerCustomizer(final ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }
}