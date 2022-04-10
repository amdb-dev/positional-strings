package domain.core;

import adapters.annotations.Position;
import domain.enumeration.Filler;
import domain.exception.PositionalException;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class StringMapper {

    private final String ANNOTATION_NOT_PRESENT = "Position annotation not present in field: %s";
    private final String FILED_FORMAT = "Failed to format value: %s";

    public <T> String mapperToString(T t) throws PositionalException {
       try {
           Field[] fields = t.getClass().getDeclaredFields();
           StringBuilder builder = new StringBuilder();

           for (Field field: fields) {
               if(!field.isAnnotationPresent(Position.class))
                   throw new RuntimeException(String.format(ANNOTATION_NOT_PRESENT, field.getName()));
               setTranscode(field, t, builder);
           }

           return builder.toString();
       }catch (Throwable throwable) {
           throw new PositionalException(throwable);
       }
    }

    private <T> void setTranscode(Field field, T t, StringBuilder builder ) {
        try {
            if (field.getType().isArray()) {
                Arrays.stream(field.getClass().getDeclaredFields()).forEach(f->{
                    setTranscode(f, t, builder);
                });
            }

            Position position = field.getAnnotation(Position.class);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), t.getClass());
            Method getter = pd.getReadMethod();
            String value = String.valueOf(getter.invoke(t));

            formatValue(position, builder, value);

        }catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private void formatValue(Position position, StringBuilder builder, String value) {
        try {
            if (value.length() > position.length())
                throw new RuntimeException("Field value greater than the entered length.");

            if (position.filler().equals(Filler.SPACES_TO_LEFT))
                value = StringUtils.leftPad(value, position.length(), " ");

            if (position.filler().equals(Filler.SPACES_TO_RIGHT))
                value = StringUtils.rightPad(value, position.length(), " ");

            if (position.filler().equals(Filler.ZEROS_TO_LEFT))
                value = StringUtils.leftPad(value, position.length(), "0");

            if (position.filler().equals(Filler.ZEROS_TO_RIGHT))
                value = StringUtils.rightPad(value, position.length(), "0");

            builder.append(value).append(position.separator());
        }catch (Throwable throwable) {
            throw new RuntimeException(String.format(FILED_FORMAT, value), throwable);
        }
    }
}
