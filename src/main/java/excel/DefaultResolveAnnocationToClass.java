package excel;

import annotation.excel.*;
import annotation.excel.Column;
import annotation.excel.Sheet;
import annotation.excel.Title;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
public class DefaultResolveAnnocationToClass implements BaseResolveAnnocationToClass {


    @Override
    public boolean vadlite() {
        return false;
    }

    @Override
    public <T> Excel<T> parse(Class<T> clazz) {
        Excel<T> excel = new Excel<>(clazz);

        //注册Sheet
        annotation.excel.Excel excelAnnotation = clazz.getAnnotation(annotation.excel.Excel.class);
        Sheet[] sheets = excelAnnotation.sheets();
        for (Sheet sheet : sheets) {
            excel.registerSheet(sheet.name(),sheet.maxSize());
        }

        Title[] titles = excelAnnotation.titles();
        for (Title title : titles) {
            excel.registerTitle(new excel.Title(title.colIndex(),title.rowIndex(),title.name()));
        }

        //解析字段上的注解
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null){
                excel.registerColumn(new excel.Column(column.name(),column.rowIndex(),column.colIndex(),field,column.cellType(),column.typeHandler()));
            }
        }

        excel.excelInit();
        return excel;
    }
}
