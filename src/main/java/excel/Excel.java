package excel;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import util.ObjectUtil;

import java.io.Serializable;
import java.util.*;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
public class Excel<T> implements Serializable{

    private final Class<T> clazz;

    private List<Sheet<T>> sheets;

    private List<Title<T>> titles;

    private List<Column<T>> columns;

    private HSSFWorkbook hssfWorkbook;

    public Excel(Class<T> clazz) {
        this.clazz = clazz;
        sheets = new ArrayList<>();
        titles = new ArrayList<>();
        columns = new ArrayList<>();
    }

    public void registerSheet(String sheetName,Integer maxSize,Integer dataStartRowIndex){
        sheets.add(new Sheet<T>(clazz,sheetName,titles,columns,maxSize,dataStartRowIndex));
    }

    public void registerTitle(Title title){
        titles.add(title);
    }

    public void registerColumn(Column column){
        columns.add(column);
    }

    public void createNewExcel(){
        setHssfWorkbook(new HSSFWorkbook());
        for (Sheet<T> sheet : sheets) {
            sheet.createHssfSheet(this.hssfWorkbook);
        }
    }

    private  void setHssfWorkbook(HSSFWorkbook hssfWorkbook){
        this.hssfWorkbook = hssfWorkbook;
    }

    private void refresh(HSSFWorkbook hssfWorkbook){
        setHssfWorkbook(hssfWorkbook);
        for (Sheet<T> sheet : sheets) {
            sheet.refrensh(hssfWorkbook.getSheet(sheet.getName()));
        }
    }


    public void validle(HSSFWorkbook hssfWorkbook){
        //验证所有的sheet
        for (Sheet<T> sheet : sheets) {
            sheet.validle(hssfWorkbook);
        }
    }
    public void parseToExcel(final List<T> datas){
        boolean isFill = true;
        List<T> cap = datas;
        //创建每一个工作簿,如果达到最大容量则将数据填充到下一个工作簿,如果每个工作簿都填充满了,则抛出异常
        for (Sheet<T> sheet : sheets) {
            if (sheet.canParse()){
                isFill = false;
                if (sheet.surplusCapacity() >= cap.size()){
                    sheet.parseToExcel(cap);
                    break;
                }else{
                    sheet.parseToExcel(cap.subList(0,sheet.surplusCapacity()));
                    isFill = true;
                }
            }
        }
        if (isFill){
            throw new RuntimeException("All Sheet is fill !!!");
        }
    }

    public void importExcel(HSSFWorkbook hssfWorkbook){

        validle(hssfWorkbook);

        refresh(hssfWorkbook);

    }

    public List<List<T>> getExcelData(HSSFWorkbook hssfWorkbook){

        //校验文档格式是否匹配
        validle(hssfWorkbook);

        List<List<T>> lists = new ArrayList<>();

        for (Sheet<T> sheet : sheets) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheet(sheet.getName());
            lists.add(sheet.getSheetData(hssfSheet));
        }

        return lists;






    }

    public List<Title<T>> getTitles() {
        return Collections.unmodifiableList(titles);
    }

    public List<Column<T>> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Sheet<T>> getSheets() {
        return Collections.unmodifiableList(sheets);
    }

    public HSSFWorkbook getHssfWorkbook(){
        return this.hssfWorkbook;
    }
}
