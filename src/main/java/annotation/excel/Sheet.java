package annotation.excel;

import java.lang.annotation.*;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
@Target(value = {})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Sheet {

    String name() default "sheet";

    int maxSize() default 1000;

    int dataStartRowIndex() default 0;

}
