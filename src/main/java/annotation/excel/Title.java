package annotation.excel;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import java.lang.annotation.*;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {})
public @interface Title {
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
     * 标题起始行索引
     * @return
     */
    int rowIndex() default 0;

}
