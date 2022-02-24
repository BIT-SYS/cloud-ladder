package cloudladder.std;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CLBuiltinFuncAnnotation {
    String[] value() default {};
    String name() default "";
    String tag() default "";
}
