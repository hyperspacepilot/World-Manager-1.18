package de.hyper.worlds.common.enums;

import lombok.Getter;

import java.util.Comparator;

@Getter
public enum SortDirection {

    UP(Comparator.naturalOrder()), DOWN(Comparator.reverseOrder());

    private Comparator comparator;

    private SortDirection(Comparator comparator) {
        this.comparator = comparator;
    }

    public SortDirection next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public SortDirection last() {
        return values()[(this.ordinal() - 1 + values().length) % values().length];
    }
}
