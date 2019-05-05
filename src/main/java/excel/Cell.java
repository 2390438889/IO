package excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.junit.Assert;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
public class Cell {

    protected String name;

    protected Integer rowIndex;

    protected Integer colIndex;

    public Cell(String name, Integer rowIndex, Integer colIndex) {
        this.name = name;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public void validle(HSSFSheet hssfSheet){
        HSSFRow hssfRow = hssfSheet.getRow(rowIndex);
        HSSFCell hssfCell = hssfRow.getCell(colIndex);
        Assert.assertEquals("title "+name+" not match",hssfCell.getStringCellValue(),name);
    }

    public void createCell(HSSFSheet sheet){
        HSSFRow hssfRow = null;

        if (sheet.getRow(rowIndex) == null){
            hssfRow = sheet.createRow(rowIndex);
        }else{
            hssfRow = sheet.getRow(rowIndex);
        }
        HSSFCell hssfCell = hssfRow.createCell(colIndex);
        hssfCell.setCellValue(name);
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public Integer getColIndex() {
        return colIndex;
    }
}
