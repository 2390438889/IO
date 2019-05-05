package annotation.excel;

import java.lang.annotation.*;

/**
 * @author Hearts
 * @date 2019/5/4
 * @desc
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Excel {
    Sheet[] sheets() default {@Sheet};

    Title[] titles() default {};
}
