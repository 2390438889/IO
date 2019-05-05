package excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class IntegerTypeHandler extends BaseTypeHandler<Integer> {

    @Override
    public void setCellValue(HSSFCell hssfCell, Integer value) {
        hssfCell.setCellValue(value);
    }

    @Override
    public Integer getCellValue(HSSFCell hssfCell) {
        return Integer.valueOf((int)hssfCell.getNumericCellValue());
    }
}
