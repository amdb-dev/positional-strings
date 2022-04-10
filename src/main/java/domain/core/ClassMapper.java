package domain.core;

import domain.exception.PositionalException;

public class ClassMapper {

    public <T> T mapperToObject(String value, Class c) throws PositionalException {
        try {
            return null;
        }catch (Throwable throwable) {
            throw new PositionalException(throwable);
        }
    }
}
