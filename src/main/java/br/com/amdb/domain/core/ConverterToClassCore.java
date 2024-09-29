package br.com.amdb.domain.core;

import br.com.amdb.domain.annotation.Positional;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ConverterToClassCore {

    private final ConverterUtilsCore converterUtilsCore;

    public ConverterToClassCore() {
        this.converterUtilsCore = new ConverterUtilsCore();
    }

    public <T> T converter(String transcode, Class<T> dataClass) {
        try {
            if (Objects.isNull(transcode) || StringUtils.trimToEmpty(transcode).isEmpty())
                throw new RuntimeException("Transcode variable cannot be null or empty");

            if (Objects.isNull(dataClass))
                throw new RuntimeException("Return type not informed");

            List<Field> fields = Arrays.stream(dataClass.getDeclaredFields())
                    .filter(c->c.isAnnotationPresent(Positional.class))
                    .collect(Collectors.toList());

            if (fields.isEmpty())
                throw new RuntimeException("@Position annotation not present in class");

            T t = dataClass.getDeclaredConstructor().newInstance();
            AtomicInteger position = new AtomicInteger();
            fields.forEach(field->{
                field.setAccessible(true);
                Positional positional = field.getAnnotation(Positional.class);
                int length = positional.length();
                converter(transcode, t, field, position.get(),  positional);
                position.addAndGet(length);
            });

            return t;
        }catch (Throwable t) {
            throw new RuntimeException("Error converting to Class", t);
        }
    }

    private <T> void converter(String transcode, T t, Field field, int position, Positional positional) {
        try {
            if (converterUtilsCore.collectionTypes().containsKey(field.getType())) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                Collection<Object> collection = converterUtilsCore.collectionTypes().get(field.getType());

                for (int i=0; i<positional.collectionSize(); i++) {
                    Class<?> clazz = (Class<?>) typeArguments[0];
                    String segment = transcode.substring(position, position + positional.length());
                    Object obj = converter(segment, clazz);
                    collection.add(obj);
                    //TODO: corrigir posicionamento, listas quando encadeadas, estao salvando o mesmo valor
                    //position += positional.length();
                }
                field.set(t, collection);
            } else {
                String segment = transcode.substring(position, Math.min(position + positional.length(), transcode.length())).trim();
                field.set(t, converterUtilsCore.converter(segment, field.getType()));
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
