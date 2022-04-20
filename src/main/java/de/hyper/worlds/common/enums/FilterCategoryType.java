package de.hyper.worlds.common.enums;

import de.hyper.worlds.common.util.items.HDBSkulls;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum FilterCategoryType {
    USER("User"),
    TEAM("Team"),
    BUILDER("Builder"),
    COPY("Copy"),
    FINISHED("Finished"),
    ORDER("Ordered"),
    OTHER("Other"),
    ADMIN("Admin"),
    SECRET("Secret"),
    ALL("All");

    private String label;

    private FilterCategoryType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label + "-Worlds";
    }

    public HDBSkulls getItemStack(CategoryType cat) {
        if (this.equals(ALL)) {
            return HDBSkulls.YELLOW_A;
        } else {
            return cat.getItemStack();
        }
    }

    public CategoryType getCategoryType() {
        if (this.equals(ALL)) {
            return null;
        } else {
            return CategoryType.valueOf(this.name());
        }
    }

    public static List<FilterCategoryType> list() {
        List<FilterCategoryType> list = new ArrayList<>();
        for (FilterCategoryType f : FilterCategoryType.values()) {
            list.add(f);
        }
        return list;
    }
}
