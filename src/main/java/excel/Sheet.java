package excel;

import junit.framework.Assert;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
public class Sheet<T> implements Serializable {

    private final Class<T> clazz;

    private String name;

    private List<Title<T>> titles;

    private List<Column<T>> columns;

    private Integer maxSize;

    private Integer size;

    private HSSFSheet hssfSheet;

    private Integer dataInitRowIndex;


    public Sheet(Class<T> clazz, String name, List<Title<T>> titles, List<Column<T>> columns, Integer maxSize, Integer dataStartRowIndex) {
        this.clazz = clazz;
        this.size = 0;
        this.name = name;
        this.titles = titles;
        this.columns = columns;
        this.maxSize = maxSize;
        this.dataInitRowIndex = dataStartRowIndex;
    }


    public void setHssfSheet(HSSFSheet hssfSheet) {
        this.hssfSheet = hssfSheet;
    }

    public void createHssfSheet(HSSFWorkbook hssfWorkbook){

        this.hssfSheet = hssfWorkbook.createSheet(name);

        for (Title<T> title : titles) {
            title.createCell(hssfSheet);
        }

        for (Column<T> column : columns) {
            column.createCell(hssfSheet);
        }

        dataInitRowIndex = this.hssfSheet.getLastRowNum();

    }

    public void parseToExcel(List<T> datas){
        for (T data : datas) {
            HSSFRow hssfRow = hssfSheet.createRow(nextRowIndex());
            for (Column<T> column : columns) {
                column.parseToCell(hssfRow,data);
                hssfSheet.autoSizeColumn(column.getColIndex());
            }
        }
    }

    public void validle(HSSFWorkbook hssfWorkbook){
        HSSFSheet hssfSheet = hssfWorkbook.getSheet(getName());
        Assert.assertNotNull(getName() +" sheet is lost!",hssfSheet);
        for (Title<T> title : titles) {
            title.validle(hssfSheet);
        }

        for (Column<T> column : columns) {
            column.validle(hssfSheet);
        }
    }

    public List<T> getSheetData(HSSFSheet hssfSheet){
        List<T> list = new ArrayList<>();
        try {
            for (int i = dataInitRowIndex+1; i <= hssfSheet.getLastRowNum(); i++) {
                Object obj = clazz.newInstance();
                HSSFRow hssfRow = hssfSheet.getRow(i);
                for (Column<T> column : columns) {
                    column.setObjectValue(hssfRow, (T) obj);
                }
                list.add((T) obj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getName() {
        return name;
    }

    private Integer nextRowIndex(){
        return dataInitRowIndex + (++size);
    }

    public boolean canParse(){
        return surplusCapacity() > 0;
    }

    public Integer surplusCapacity(){
        return maxSize - size;
    }

    public void refrensh(HSSFSheet sheet) {
        size = sheet.getLastRowNum() - dataInitRowIndex;
        this.setHssfSheet(sheet);
    }
}
