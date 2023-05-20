package br.com.amdb.domain.core;

import br.com.amdb.domain.annotation.Positional;
import br.com.amdb.domain.enumeration.Filler;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConverterToStringCore {

    private final UtilsCore utilsCore;
    private Object otherField;

    public ConverterToStringCore() {
        this.utilsCore = new UtilsCore();
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

    private void converter(Field field, StringBuilder sb, Object data) {
        field.setAccessible(true);
        Positional positional = field.getAnnotation(Positional.class);
        if (positional.isCollection()) {
            Collection<Object> collection = (Collection<Object>) utilsCore.invokeGetter(data, field.getName());
            collection.forEach(this::converter);
        }
        String value = String.valueOf(utilsCore.invokeGetter(data, field.getName()));

        if (!positional.filler().equals(Filler.NONE)) {
            if (positional.filler().equals(Filler.ZEROS_LEFT) || positional.filler().equals(Filler.SPACES_LEFT))
                value = StringUtils.leftPad(value, positional.length(), positional.filler().getValue());

            if (positional.filler().equals(Filler.ZEROS_RIGTH) || positional.filler().equals(Filler.SPACES_RIGTH))
                value = StringUtils.rightPad(value, positional.length(), positional.filler().getValue());
        }

        sb.append(value);
    }

}
