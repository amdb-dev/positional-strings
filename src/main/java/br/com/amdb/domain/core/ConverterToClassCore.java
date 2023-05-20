package br.com.amdb.domain.core;

import br.com.amdb.domain.annotation.Positional;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConverterToClassCore {

    private final UtilsCore utilsCore;

    public ConverterToClassCore() {
        this.utilsCore = new UtilsCore();
    }

    public <T> T converter(String transcode, Class<T> dataClass) {
        try {
            if (Objects.isNull(transcode) || StringUtils.trimToEmpty(transcode).isEmpty())
                throw new RuntimeException("Transcode variable cannot be null or empty");

            if (Objects.isNull(dataClass))
                throw new RuntimeException("Return type not informed");

            List<Field> fields = utilsCore.getFields(dataClass);

            T t = dataClass.getDeclaredConstructor().newInstance();
            fields.forEach(field->{
                converter(transcode, t, field);
            });

            return t;
        }catch (Throwable t) {
            throw new RuntimeException("Error converting to Class", t);
        }
    }

    private <T> void converter(String transcode, T t, Field field) {
        try {
            field.setAccessible(true);
            Positional positional = field.getAnnotation(Positional.class);

            if (positional.isCollection()) {
                if (positional.collectionSize() == 0)
                    throw new RuntimeException(String.format("Need to inform the size of the list to be built for the collection %s", field.getName()));

                ParameterizedType type = (ParameterizedType) field.getGenericType();

                //List<Field> fields = utilsCore.getFields(type.getClass());
                Collection<Object> objects = (Collection<Object>) type.getClass().getDeclaredConstructor().newInstance();

                for (int i=0; i< positional.collectionSize(); i++) {
                    Object obj = converter(transcode, type.getClass());
                    objects.add(obj);
                }
                utilsCore.invokeSetter(t, field.getName(), objects);
            } else {
                String value = transcode.substring(0, positional.length());
                transcode = transcode.substring(positional.length());
                utilsCore.invokeSetter(t, field.getName(), value);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
