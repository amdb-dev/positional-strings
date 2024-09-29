package br.com.amdb.domain.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;

class ConverterUtilsCore {
    protected Map<Class<?>, Collection<Object>> collectionTypes() {
        Map<Class<?>, Collection<Object>> map = new HashMap<>();
        map.put(List.class, new ArrayList<>());
        map.put(Set.class, new LinkedHashSet<>());
        return map;
    }

    protected <T> T converter(String value, Class<T> targetType) {
        Map<Class<?>, Function<String, ?>> converters = new HashMap<>();
        converters.put(String.class, s -> s);
        converters.put(Integer.class, Integer::valueOf);
        converters.put(int.class, Integer::parseInt);
        converters.put(Double.class, Double::valueOf);
        converters.put(double.class, Double::parseDouble);
        converters.put(BigDecimal.class, BigDecimal::new);
        converters.put(LocalDate.class, s -> {
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Date in incorrect format. Use the YYYY-MM-DD format.", e);
            }
        });

        Function<String, ?> converter = converters.get(targetType);

        if (Objects.isNull(converter))
            throw new IllegalArgumentException("Unsupported conversion type: " + targetType.getName());

        return (T) converter.apply(value);
    }

}
