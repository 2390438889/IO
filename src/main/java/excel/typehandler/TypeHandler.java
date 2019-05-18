package excel.typehandler;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public interface TypeHandler<T> {

    void setCellValue(HSSFCell hssfCell,T value);

    T getCellValue(HSSFCell hssfCell);

}
