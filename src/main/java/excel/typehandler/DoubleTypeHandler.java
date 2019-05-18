package excel.typehandler;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class DoubleTypeHandler extends BaseTypeHandler<Double> {


    @Override
    public void setCellValue(HSSFCell hssfCell, Double value) {
        hssfCell.setCellValue(value);
    }

    @Override
    public Double getCellValue(HSSFCell hssfCell) {
        return Double.valueOf(hssfCell.getNumericCellValue());
    }
}
