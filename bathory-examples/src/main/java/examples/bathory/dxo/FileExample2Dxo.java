package examples.bathory.dxo;

import org.seasar.extension.dxo.annotation.DatePattern;

import examples.bathory.consumer.FileExample2Consumer;
import examples.bathory.entity.Emp;

/**
 * .
 * @author toyokazu
 */
@DatePattern("MMddyyyy")
public interface FileExample2Dxo {
    Emp convert(FileExample2Consumer foo);
}
