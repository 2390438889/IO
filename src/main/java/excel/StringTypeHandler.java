package excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class StringTypeHandler extends BaseTypeHandler<String>{
    @Override
    public void setCellValue(HSSFCell hssfCell, String value) {
        hssfCell.setCellValue(value);
    }

    @Override
    public String getCellValue(HSSFCell hssfCell) {
        return hssfCell.getStringCellValue();
    }
}
