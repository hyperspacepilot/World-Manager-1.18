package de.hyper.worlds.common.util.inventory.buttons;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class MultiButton<T> extends Button {

    protected Map<T, Button> buttons;

}
