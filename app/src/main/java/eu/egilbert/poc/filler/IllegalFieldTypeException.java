package eu.egilbert.poc.filler;

import lombok.Getter;
import lombok.Value;

@Getter
public class IllegalFieldTypeException extends RuntimeException {

    private Class<?> targetClass;

    private Class<?> valueClass;
    private Object value;

    private FieldPath targetPath;

    public <T, V> IllegalFieldTypeException(Class<? extends T> targetClass, FieldPath path, V value) {
        this.targetClass = targetClass;
        this.targetPath = path;
        this.valueClass = value.getClass();
        this.value = value;
    }

    public <T, V> IllegalFieldTypeException(Class<? extends T> targetClass, V value) {
        this(targetClass, null, value);
    }
}
