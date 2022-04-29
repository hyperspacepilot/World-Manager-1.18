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
}
