package br.com.amdb.domain.core;

import br.com.amdb.domain.annotation.Positional;
import br.com.amdb.domain.enumeration.Filler;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConverterToStringCore {

    private final ConverterUtilsCore converterUtilsCore;

    public ConverterToStringCore() {
        this.converterUtilsCore = new ConverterUtilsCore();
    }

    public <T> String converter(T data) {
        try {
            if (Objects.isNull(data))
                throw new RuntimeException("The object to converter is null");

            Class<?> clazz = data.getClass();

            StringBuilder sb = new StringBuilder();

            List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                    .filter(c->c.isAnnotationPresent(Positional.class))
                    .collect(Collectors.toList());

            if (fields.isEmpty())
                throw new RuntimeException("@Position annotation not present in class");

            fields.forEach(field->{
                converter(field, sb, data);
            });

            return sb.toString();
        }catch (Throwable t) {
            throw new RuntimeException("Error converting to String", t);
        }
    }

    @SneakyThrows
    private void converter(Field field, StringBuilder sb, Object data) {
        field.setAccessible(true);
        Positional positional = field.getAnnotation(Positional.class);
        if (converterUtilsCore.collectionTypes().containsKey(field.getType())) {
            Collection<Object> collection = (Collection<Object>) field.get(data);
            if (!Objects.isNull(collection)) {
                collection.forEach(t -> {
                    sb.append(converter(t));
                });
            }
        } else {
            String value = String.valueOf(field.get(data));

            if (value.length() > positional.length()) {
                value = value.substring(0, positional.length());
            }

            if (!positional.filler().equals(Filler.NONE)) {
                if (positional.filler().equals(Filler.ZEROS_LEFT) || positional.filler().equals(Filler.SPACES_LEFT))
                    value = StringUtils.leftPad(value, positional.length(), positional.filler().getValue());

                if (positional.filler().equals(Filler.ZEROS_RIGTH) || positional.filler().equals(Filler.SPACES_RIGTH))
                    value = StringUtils.rightPad(value, positional.length(), positional.filler().getValue());
            }
            sb.append(value).append(positional.separator());
        }
    }
}
