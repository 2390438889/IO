package excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setCellValue(HSSFCell hssfCell, Date value) {

        hssfCell.setCellValue(value);
    }

    @Override
    public Date getCellValue(HSSFCell hssfCell) {
        return hssfCell.getDateCellValue();
    }
}
