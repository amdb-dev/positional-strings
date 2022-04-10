package adapters.mapper;

import domain.core.ClassMapper;
import domain.core.StringMapper;
import domain.exception.PositionalException;
import ports.Mapper;

public class PositionalMapper implements Mapper {

    private final ClassMapper classMapper;
    private final StringMapper stringMapper;

    public PositionalMapper() {
        this.classMapper = new ClassMapper();
        this.stringMapper = new StringMapper();
    }

    @Override
    public <T> String mapperToString(T t) throws PositionalException {
        return stringMapper.mapperToString(t);
    }

    @Override
    public <T> T mapperToObject(String value, Class c) throws PositionalException {
        return classMapper.mapperToObject(value, c);
    }
}
