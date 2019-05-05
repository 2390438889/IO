package excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class DefaultResolveAnocationToClassTest {

    @Test
    public void resolveTest(){
        BaseResolveAnnocationToClass baseResolveAnnocationToClass = new DefaultResolveAnnocationToClass();

        Excel<User> excel = baseResolveAnnocationToClass.parse(User.class);

        List<User> users = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            users.add(new User(i,"hello",new Date()));
        }

        excel.parseToExcel(users);

        HSSFWorkbook hssfWorkbook = excel.getHssfWorkbook();

        final CellStyle titleCellStyle = hssfWorkbook.createCellStyle();

        final CellStyle colNameCellStyle = hssfWorkbook.createCellStyle();

        final CellStyle colDataCellStyle = hssfWorkbook.createCellStyle();

        titleCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);

        colNameCellStyle.setFillForegroundColor(IndexedColors.AQUA.index);

        HSSFFont font = hssfWorkbook.createFont();
        font.setFontName("宋体");
        font.setColor(Font.COLOR_RED);
        font.setBold(true);
        colNameCellStyle.setFont(font);
        colNameCellStyle.setAlignment(HorizontalAlignment.CENTER);

        colDataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        colDataCellStyle.setFont(font);

        final List<Sheet<User>> sheets = excel.getSheets();

        final List<Title<User>> titles = excel.getTitles();

        final List<Column<User>> columns = excel.getColumns();

        for (Sheet<User> sheet : sheets) {

            HSSFSheet hssfSheet = hssfWorkbook.getSheet(sheet.getName());

            setTitleCellStyle(hssfSheet, titleCellStyle, titles);

            setColumnCellStyle(hssfSheet, colNameCellStyle, columns);

            setColDataCellStyle(hssfSheet, colDataCellStyle,columns);
        }



        try {
            hssfWorkbook.write(new File("F:\\a.xls"));
            Runtime.getRuntime().exec("cmd /c start F:\\a.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setColDataCellStyle(HSSFSheet hssfSheet, CellStyle colDataCellStyle, List<Column<User>> columns) {
        for (Column<User> column : columns) {
            for (int i = column.getRowIndex()+1; i <= hssfSheet.getLastRowNum(); i++) {
                hssfSheet.getRow(i)
                        .getCell(column.getColIndex())
                        .setCellStyle(colDataCellStyle);
            }
        }
    }

    private void setColumnCellStyle(HSSFSheet hssfSheet, CellStyle colNameCellStyle, List<Column<User>> columns) {
        for (Column<User> column : columns) {
            HSSFCell hssfCell = hssfSheet.getRow(column.rowIndex)
                    .getCell(column.colIndex);
            hssfCell.setCellStyle(colNameCellStyle);
            hssfSheet.setColumnWidth(column.getColIndex(),10000);

        }
    }

    private void setTitleCellStyle(HSSFSheet hssfSheet, CellStyle titleCellStyle, List<Title<User>> titles) {
        for (Title<User> title : titles) {
            hssfSheet.getRow(title.rowIndex)
                    .getCell(title.colIndex)
                    .setCellStyle(titleCellStyle);
        }
    }

    @Test
    public void getDataTest(){
        BaseResolveAnnocationToClass baseResolveAnnocationToClass = new DefaultResolveAnnocationToClass();

        Excel<User> excel = baseResolveAnnocationToClass.parse(User.class);

        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream("F:\\a.xls"));
            List<List<User>> lists = excel.getExcelData(hssfWorkbook);
            for (List<User> list : lists) {
                for (User user : list) {
                    System.out.println(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
