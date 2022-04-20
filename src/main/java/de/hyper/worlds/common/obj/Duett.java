package de.hyper.worlds.common.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor@Getter
public class Duett<V1, V2> {
    private V1 value1;
    private V2 value2;
}
