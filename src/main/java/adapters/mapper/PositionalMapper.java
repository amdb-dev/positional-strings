package adapters.mapper;

import domain.core.ClassMapper;
import domain.core.StringMapper;
import ports.Mapper;

public class PositionalMapper implements Mapper {

    private final ClassMapper classMapper;
    private final StringMapper stringMapper;

    public PositionalMapper() {
        this.classMapper = new ClassMapper();
        this.stringMapper = new StringMapper();
    }

    @Override
    public <T> String mapper(T t) {
        return stringMapper.mapperToString(t);
    }

    @Override
    public <T> T mapper(String value, Class c) {
        return classMapper.mapperToObject(value, c);
    }
}
