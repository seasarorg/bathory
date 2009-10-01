package examples.bathory.collector;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.seasar.bathory.engine.Casket;
import org.seasar.bathory.engine.Collector;
import org.seasar.bathory.exception.SystemException;
import org.seasar.framework.beans.util.BeanUtil;


/**
 * Fileを使用したExample2のCollector.
 * @author toyokazu
 */
public class FileExample2Collector implements Collector {

    /** JAXBContextを作成するためのパッケージ. */
    private static final String CONTEXT_PACKAGE
                        = "examples.bathory.dto.fileExample1";
    /** 読み込むファイル名. */
    private static final String FILE_NAME
                    = "/examples/bathory/collector/ExampleFile1Collector.xml";

    /**
     * データ収集.
     * @param casket Casket
     * @see org.seasar.bathory.engine.Collector#collect(bathory.engine.Basket)
     */
    @Override
    public void collect(final Casket casket) {
        XMLStreamReader reader = null;
        InputStream stream = null;
        try {
            // JAXBContextオブジェクトの生成
            JAXBContext context = JAXBContext.newInstance(CONTEXT_PACKAGE);

            // Unmarsallerオブジェクトの取得
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // XMLInputFactoryオブジェクトの取得
            XMLInputFactory factory = XMLInputFactory.newInstance();

            stream = this.getClass().getResourceAsStream(FILE_NAME);

            reader = factory.createXMLStreamReader(stream);

            while (reader.hasNext()) {
                int eventType = reader.next();
                if (eventType == XMLStreamReader.START_ELEMENT) {
                    // タグ名がemployeeかどうか調べる
                    if ("employee".equals(reader.getLocalName())) {
                        // song タグの場合JAXBでアンマーシャル
                        Map<String, Object> value = new HashMap<String, Object>();
                        Object obj = unmarshaller.unmarshal(reader);
                        BeanUtil.copyProperties(obj, value);
                        casket.put(value);
                    }
                }
            }
        } catch (Exception e) {
            throw new SystemException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                throw new SystemException(e);
            }
        }
    }
}
