package eu.egilbert.poc.filler;

import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

public abstract class FieldPath {

    public abstract boolean isTarget();

    public abstract FieldPath getSubPath();

    public abstract FieldId getCurrentFieldId();

    private static class Target extends FieldPath {
        @Override
        public boolean isTarget() {
            return true;
        }

        @Override
        public FieldPath getSubPath() {
            throw new IllegalStateException(); // FIXME not very Liskov substitution principle
        }

        @Override
        public FieldId getCurrentFieldId() {
            throw new IllegalStateException(); // FIXME not very Liskov substitution principle
        }

        public String toString() {
            return "";
        }
    }

    @Value
    private static class ContinuedPath extends FieldPath {

        private FieldId currentFieldId;

        private FieldPath subPath;

        @Override
        public boolean isTarget() {
            return false;
        }

        @Override
        public String toString() {
            return currentFieldId.getName() + "." + subPath.toString();
        }
    }

    private final static FieldPath TARGET = new Target();

    private static FieldPath fromFieldsId(Deque<FieldId> fieldIds) {
        if (fieldIds.isEmpty()) {
            return TARGET;
        } else {
            return new ContinuedPath(fieldIds.pop(), fromFieldsId(fieldIds));
        }
    }

    public static FieldPath fromFieldsId(FieldId... fieldIds) {
        return fromFieldsId(Arrays.stream(fieldIds).collect(Collectors.toCollection(ArrayDeque::new)));
    }

    public static FieldPath fromFields(String... fieldsName) {
        return fromFieldsId(Arrays.stream(fieldsName).map(FieldId::new).collect(Collectors.toCollection(ArrayDeque::new)));
    }
}