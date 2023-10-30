package eu.egilbert.poc.filler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Filler {
    public <T, V> T fill(T target, FieldPath path, V value) {
        try {
            return this.fill(target, (Class<T>) target.getClass(), path, value);
        } catch (IllegalFieldTypeException e) {
            throw new IllegalFieldTypeException(target.getClass(), path, value);
        }
    }

    private <T, V> T fill(T target, Class<? extends T> targetClass, FieldPath path, V value) {

        if (path.isTarget()) {
            if (targetClass.isInstance(value)) {
                return (T) value;
            } else {
                throw new IllegalFieldTypeException(targetClass, value);
            }
        } else {
            return fillSubObject(target, targetClass, path, value);
        }
    }

    private <T, V> T fillSubObject(T target, Class<? extends T> targetClass, FieldPath path, V value) {
        try {
            FieldId currentId = path.getCurrentFieldId();

            if (target == null) {
                target = targetClass.getConstructor().newInstance();
            }

            Method getter = getter(targetClass, currentId);
            Class<?> fieldClass = getter.getReturnType();
            Object currentFieldValue = getter.invoke(target);

            Object newFieldValue = fill(currentFieldValue, fieldClass, path.getSubPath(), value);

            Method setter = setter(targetClass, fieldClass, currentId);
            setter.invoke(target, newFieldValue);

            return target;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e); // TODO Proper exception management and error messages
        }
    }

    private <T> Method getter(Class<? extends T> targetClass, FieldId currentId) {
        try {
            return targetClass.getMethod(currentId.getterName());
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No getter for field " + currentId.getName() + " in class" + targetClass.getName(),  e);
        }
    }

    private <T, V> Method setter(Class<? extends T> targetClass, Class<? extends V> valueClass, FieldId currentId) {
        try {
            return targetClass.getMethod(currentId.setterName(), valueClass);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No Setter for field " + currentId.getName() + " in class" + targetClass.getName(),  e);
        }
    }
}
