package excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.Serializable;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
public class Title<T> extends Cell implements Serializable {

    public Title(Integer colIndex, Integer rowIndex, String name) {
        super(name,rowIndex,colIndex);
    }

}
