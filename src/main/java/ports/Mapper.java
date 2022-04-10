package ports;

import domain.exception.PositionalException;

public interface Mapper {
    <T> String mapperToString(T t) throws PositionalException;
    <T> T mapperToObject(String value, Class c) throws PositionalException;
}
