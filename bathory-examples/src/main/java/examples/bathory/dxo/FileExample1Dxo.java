package examples.bathory.dxo;

import org.seasar.extension.dxo.annotation.DatePattern;

import examples.bathory.consumer.FileExample1Consumer;
import examples.bathory.entity.Emp;

/**
 * .
 * @author toyokazu
 */
@DatePattern("MMddyyyy")
public interface FileExample1Dxo {
    Emp convert(FileExample1Consumer foo);
}
