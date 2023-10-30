package eu.egilbert.poc.filler;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public class FieldId {
    private String name;

    public String getterName() {
        return "get" + StringUtils.capitalize(name);
    }

    public String setterName() {
        return "set" + StringUtils.capitalize(name);
    }
}
