package examples.bathory.collector;

import java.util.HashMap;
import java.util.Map;

import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.Collector;

public class HelloWorldCollector implements Collector {
    @Override
    public void collect(final Casket casket) {
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("sysout", "hello world!");

        casket.put(value);
    }
}