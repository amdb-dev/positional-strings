package br.com.amdb.domain.core;

import br.com.amdb.domain.annotation.Positional;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class UtilsCore {

    protected void invokeSetter(Object obj, String variableName, Object variableValue)
    {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(variableName, obj.getClass());
            Method setter = pd.getWriteMethod();
            setter.invoke(obj,variableValue);
        } catch (Throwable t) {
            throw new RuntimeException(String.format("Error when invoking variable's set method %s", variableName), t);
        }
    }

    protected Object invokeGetter(Object obj, String variableName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(variableName, obj.getClass());
            Method getter = pd.getReadMethod();
            return getter.invoke(obj);
        } catch (Throwable t) {
            throw new RuntimeException(String.format("Error when invoking variable's get method %s", variableName), t);
        }
    }

    protected <T> List<Field> getFields(Class<T> dataClass) {
        List<Field> fields = Arrays.stream(dataClass.getDeclaredFields())
                .filter(c -> c.isAnnotationPresent(Positional.class))
                .collect(Collectors.toList());

        if (fields.isEmpty())
            throw new RuntimeException(String.format("@Position annotation not present in class %s", dataClass.getTypeName()));
        return fields;
    }
}
