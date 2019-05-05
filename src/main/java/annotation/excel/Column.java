package annotation.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;

import java.lang.annotation.*;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    /**
     * 标题
     * @return
     */
    String name();

    /**
     * 标题起始列索引
     * @return
     */
    int colIndex() default 0;

    /**
     * 起始行索引
     * @return
     */
    int rowIndex() default 0;

    /**
     * 该列值类型
     * @return
     */
    CellType cellType() default CellType.STRING;

    String typeHandler() default "";

}
