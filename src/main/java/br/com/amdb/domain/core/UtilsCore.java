package br.com.amdb.domain.core;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class UtilsCore {

    public void invokeSetter(Object obj, String variableName, Object variableValue)
    {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(variableName, obj.getClass());
            Method setter = pd.getWriteMethod();
            setter.invoke(obj,variableValue);
        } catch (Throwable t) {
            throw new RuntimeException(String.format("Error when invoking variable's set method %s", variableName), t);
        }
    }

    public Object invokeGetter(Object obj, String variableName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(variableName, obj.getClass());
            Method getter = pd.getReadMethod();
            return getter.invoke(obj);
        } catch (Throwable t) {
            throw new RuntimeException(String.format("Error when invoking variable's get method %s", variableName), t);
        }
    }
}
