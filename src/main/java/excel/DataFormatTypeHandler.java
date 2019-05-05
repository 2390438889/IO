package excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class DataFormatTypeHandler extends BaseTypeHandler<Date> {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Override
    public void setCellValue(HSSFCell hssfCell, Date value) {
        hssfCell.setCellValue(simpleDateFormat.format(value));
    }

    @Override
    public Date getCellValue(HSSFCell hssfCell) {

        return new Date(hssfCell.getStringCellValue());
    }
}
