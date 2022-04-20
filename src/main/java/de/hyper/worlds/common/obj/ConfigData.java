package de.hyper.worlds.common.obj;

import de.hyper.worlds.common.util.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @AllArgsConstructor
public class ConfigData {

    private final String name;
    private final String description;
    private Object dataValue;

    public int getDataValueAsInteger() {
        return Converter.getInteger(getDataValueAsString());
    }

    public int getDataValueAsPositiveInteger() {
        return Converter.getPositiveInteger(getDataValueAsString());
    }

    public String getDataValueAsString() {
        return dataValue.toString();
    }

    public double getDataValueAsDouble() {
        return Converter.getDouble(getDataValueAsString());
    }

    public long getDataValueAsLong() {
        return Converter.getLong(getDataValueAsString());
    }

    public long getDataValueAsPositiveLong() {
        return Converter.getPositiveLong(getDataValueAsString());
    }

    public boolean getDataValueAsBoolean() {
        return Converter.getBoolean(getDataValueAsString());
    }
}
