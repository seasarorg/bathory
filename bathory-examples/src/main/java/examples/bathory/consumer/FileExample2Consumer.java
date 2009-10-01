package examples.bathory.consumer;

import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.seasar.bathory.engine.BatchLifecycleListner;
import org.seasar.bathory.engine.BathoryContext;
import org.seasar.bathory.engine.Consumer;
import org.seasar.bathory.exception.SystemException;

/**
 * .
 * @author toyokazu
 */
public class FileExample2Consumer implements Consumer, BatchLifecycleListner {
    /** StringWriterを格納するキー名. */
    private static final String STRING_WRITER = "StringWriter";
    /** XMLStreamWriterを格納するキー名. */
    private static final String XMLSTREAM_WRITER = "XMLStreamWriter";

    
    /** 従業員ID. */
    public String empNo;
    /** 従業員名. */
    public String empName;
    /** マネジャーID. */
    public String mgrId;
    /** 雇用年月日. */
    public String hiredate;
    /** 賃金. */
    public String sal;
    /** 支社ID. */
    public String deptId;

    /**
     * Consumer初期化時一回のみ呼ばれる.
     * @see org.seasar.bathory.engine.BatchLifecycleListner#initilize()
     */
    @Override
    public void initilize() {
        // BathoryContextの取得
        BathoryContext context     = BathoryContext.getCurrentInstance();
        // XMLOutputFactoryの生成
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter stWriter  = null;


        StringWriter writer = new StringWriter();
        context.getProperties().put(STRING_WRITER, writer);

        try {
            stWriter = factory.createXMLStreamWriter(writer);
            context.getProperties().put(XMLSTREAM_WRITER, stWriter);


            // XMLドキュメント記述
            stWriter.writeStartDocument("UTF-8", "1.0");
            stWriter.writeCharacters("\n");

            stWriter.writeStartElement("data");
            stWriter.writeCharacters("\n  ");

            // title要素
            stWriter.writeStartElement("title");
            stWriter.writeCharacters("XXX company");
            stWriter.writeEndElement();
            stWriter.writeCharacters("\n  ");


            // credit要素
            stWriter.writeStartElement("description");
            stWriter.writeCharacters("list of XXX company's new employees");
            stWriter.writeEndElement();
            stWriter.writeCharacters("\n  ");

            // songs要素
            stWriter.writeStartElement("employees");

        } catch (Exception e) {
            throw new SystemException(e);
        }

    }

    /**
     * メインの処理.
     * @see org.seasar.bathory.engine.Consumer#consume()
     */
    @Override
    public void consume() {
        // BathoryContextの取得
        BathoryContext context     = BathoryContext.getCurrentInstance();
        XMLStreamWriter stWriter = (XMLStreamWriter) context.getProperties().get(XMLSTREAM_WRITER);
        try {
            // song要素
            stWriter.writeCharacters("\n    ");
            stWriter.writeEmptyElement("employee");
            stWriter.writeAttribute("empNo", empNo);
            stWriter.writeAttribute("empName", empName);
            stWriter.writeAttribute("mgrId", mgrId);
            stWriter.writeAttribute("hiredate", hiredate);
            stWriter.writeAttribute("sal", sal);
        } catch (XMLStreamException e) {
            throw new SystemException(e);
        }
    }



    /**
     * Consumer終了時一回のみ呼ばれる.
     * @see org.seasar.bathory.engine.handlar.BatchLifecycleListner#terminate()
     */
    @Override
    public void terminate() {
        // BathoryContextの取得
        BathoryContext context     = BathoryContext.getCurrentInstance();
        XMLStreamWriter stWriter = (XMLStreamWriter) context.getProperties().get(XMLSTREAM_WRITER);

        try {
            // songs要素の終了
            stWriter.writeCharacters("\n  ");
            stWriter.writeEndElement();

            // album要素の終了
            stWriter.writeCharacters("\n");
            stWriter.writeEndElement();
        } catch (Exception e) {
            throw new SystemException(e);
        } finally {
            if (stWriter != null) {
                try {
                    stWriter.close();
                } catch (XMLStreamException e) {
                    throw new SystemException(e);
                }
            }
        }
        
        StringWriter writer = (StringWriter) context.getProperties().get(STRING_WRITER);
        System.out.println(writer.toString());
    }
    
}
