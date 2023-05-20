package br.com.amdb.domain.annotation;

import br.com.amdb.domain.enumeration.Filler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Positional {
    int length() default 0;
    Filler filler() default Filler.NONE;
    boolean isCollection() default false;
    int collectionSize() default 0;
    String separator() default "";
}
