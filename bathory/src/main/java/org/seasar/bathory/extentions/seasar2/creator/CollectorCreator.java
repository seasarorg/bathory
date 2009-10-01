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
public class CollectorCreator extends ComponentCreatorImpl {
    /**
     * 指定された{@link NamingConvention 命名規約}に従った{@link CollectorCreator}を作成します.
     * @param namingConvention 命名規約
     */
    public CollectorCreator(final NamingConvention namingConvention) {
        super(namingConvention);
        setNameSuffix(Constants.SUFFIX_OF_COLLECTOR);
        setInstanceDef(InstanceDefFactory.PROTOTYPE);
    }

    /**
     * Collector用の {@link ComponentCustomizer}を返します.
     *
     * @return コンポーネントカスタマイザ
     */
    public ComponentCustomizer getCollectorCustomizer() {
        return getCustomizer();
    }

    /**
     * Collector用の {@link ComponentCustomizer}を設定します.
     *
     * @param customizer コンポーネントカスタマイザ
     */
    public void setCollectorCustomizer(final ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }
}
