package org.seasar.bathory.executer;

import org.seasar.bathory.engine.BathoryContext;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;


/**
 * mainメソッドによるシンプルなエントリポイントクラス.
 * @author toyokazu
 */
public class SimpleCommandExecutor {

    /**
     * インスタンス化禁止.
     */
    private SimpleCommandExecutor() {
        
    }

    /**
     * mainメソッドによるエントリポイント.
     * @param args 引数
     */
    public static void main(final String[] args) {
        SingletonS2ContainerFactory.init();
        BathoryContext context = BatchExecuterHelper.getContextFromSystemProperties();
        BatchExecuter executer = SingletonS2Container.getComponent(BatchExecuter.class);
        int result = executer.execute(context);
        SingletonS2ContainerFactory.destroy();
        System.exit(result);
    }
}
