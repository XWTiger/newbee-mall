package ltd.newbee.mall.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ObjectScan {

    String value() default "";
}
