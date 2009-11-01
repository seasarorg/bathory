package examples.bathory.consumer;

import org.seasar.bathory.engine.Consumer;

public class HelloWorldConsumer implements Consumer {

    public String sysout;

    @Override
    public void consume() {
        System.out.println(sysout);
    }
}
