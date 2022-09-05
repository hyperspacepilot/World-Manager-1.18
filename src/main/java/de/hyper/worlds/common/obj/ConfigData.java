package de.hyper.worlds.common.obj;

import de.hyper.worlds.common.util.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfigData {

    private final String name;
    private final String description;
    private Object dataValue;


    public String getDataValueAsString() {
        return dataValue.toString();
    }

    public int getDataValueAsInteger() {
        return Converter.getInteger(asString());
    }

    public int getDataValueAsPositiveInteger() {
        return Converter.getPositiveInteger(asString());
    }

    public double getDataValueAsDouble() {
        return Converter.getDouble(asString());
    }

    public double getDataValueAsPositiveDouble() {
        return Converter.getPositiveDouble(asString());
    }

    public long getDataValueAsLong() {
        return Converter.getLong(asString());
    }

    public long getDataValueAsPositiveLong() {
        return Converter.getPositiveLong(asString());
    }

    public float getDataValueAsFloat() {
        return Converter.getFloat(asString());
    }

    public float getDataValueAsPositiveFloat() {
        return Converter.getPositiveFloat(asString());
    }

    public boolean getDataValueAsBoolean() {
        return Converter.getBoolean(asString());
    }

    public String asString() {
        return this.getDataValueAsString();
    }

    public int asInteger() {
        return this.getDataValueAsInteger();
    }

    public int asPositiveInteger() {
        return this.getDataValueAsPositiveInteger();
    }

    public double asDouble() {
        return this.getDataValueAsDouble();
    }

    public double asPositiveDouble() {
        return this.getDataValueAsPositiveDouble();
    }

    public long asLong() {
        return this.getDataValueAsLong();
    }

    public long asPositiveLong() {
        return this.getDataValueAsPositiveLong();
    }

    public float asFloat() {
        return this.getDataValueAsFloat();
    }

    public float asPositiveFloat() {
        return this.getDataValueAsPositiveFloat();
    }

    public boolean asBoolean() {
        return this.getDataValueAsBoolean();
    }
}