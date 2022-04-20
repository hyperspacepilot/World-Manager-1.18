package de.hyper.worlds.common.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class StatePart {

    private String nameKey;
    private String value;

    public String getNameKey() {
        return "settings." + nameKey;
    }
}
