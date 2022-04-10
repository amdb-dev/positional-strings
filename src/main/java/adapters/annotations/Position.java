package adapters.annotations;

import domain.enumeration.Filler;
import domain.enumeration.FormatDate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Position {

    int length();
    String separator() default "";
    Filler filler() default Filler.NONE;
    int occurs() default 0;
    String fieldOccurs() default "";
    FormatDate formatDate() default FormatDate.YYYY_MM_DD;
}
