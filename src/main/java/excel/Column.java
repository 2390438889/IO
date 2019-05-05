package excel;

import annotation.excel.*;
import annotation.excel.Excel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
public class Column<T> extends Cell implements Serializable {

    private Field field;

    private CellType cellType;

    private TypeHandler typeHandler;

    public Column(Integer rowIndex, Integer colIndex, Field field, String name, CellType cellType) {
        super(name,rowIndex,colIndex);
        this.field = field;
        this.cellType = cellType;
    }

    public Column(String name, Integer rowIndex, Integer colIndex, Field field, CellType cellType, String typeHandler) {
        super(name, rowIndex, colIndex);
        this.field = field;
        this.cellType = cellType;
        initTypeHandler();
        typehandlerHander(typeHandler);
    }

    private void typehandlerHander(String typeHandler) {
        if (typeHandler.trim().length() == 0){
            return;
        }
        try{
            Class typeHandlerClass = Class.forName(typeHandler);
            Object typeHandlerObj = typeHandlerClass.newInstance();
            if (typeHandlerObj instanceof  TypeHandler){
                this.typeHandler = (TypeHandler) typeHandlerObj;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setTypeHandler(TypeHandler typeHandler) {
        this.typeHandler = typeHandler;
    }

    public void parseToCell(HSSFRow hssfRow,T obj){
        field.setAccessible(true);
        try {
            HSSFCell hssfCell = hssfRow.createCell(colIndex);
            hssfCell.setCellType(cellType);
            typeHandler.setCellValue(hssfCell, field.get(obj));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setObjectValue(HSSFRow hssfRow,T object){
        field.setAccessible(true);
        try {
            field.set(object,typeHandler.getCellValue(hssfRow.getCell(colIndex)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e){

        }
    }

    private void initTypeHandler(){
        typeHandler = TypeHandlerFacotry.getTypeHandler(field.getType());
    }



}
