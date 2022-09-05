package de.hyper.worlds.common.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Dependency {

    private String mainClass;

    public boolean isEnabled() {
        try {
            Class.forName(mainClass);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}