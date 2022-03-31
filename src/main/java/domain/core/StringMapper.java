package domain.core;

import domain.exception.PositionalException;

public class StringMapper {

    public <T> String mapperToString(T t) {
       try {
           return null;
       }catch (Throwable throwable) {
           throw new PositionalException(throwable);
       }
    }
}
