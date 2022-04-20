package de.hyper.worlds.common.enums;

import com.google.gson.annotations.SerializedName;
import de.hyper.worlds.common.util.items.HDBSkulls;
import lombok.Getter;


public enum CategoryType {

    @SerializedName("cat-user")USER("User", 1,HDBSkulls.GRAY_U, "user", FilterCategoryType.USER),
    @SerializedName("cat-team")TEAM("Team", 1, HDBSkulls.RED_T, "team", FilterCategoryType.TEAM),
    @SerializedName("cat-build") BUILDER("Builder", 1, HDBSkulls.YELLOW_B, "builder", FilterCategoryType.BUILDER),
    @SerializedName("cat-copy")COPY("Copy", 2, HDBSkulls.BROWN_C, "copy", FilterCategoryType.COPY),
    @SerializedName("cat-finished")FINISHED("Finished", 2, HDBSkulls.BROWN_F, "finished", FilterCategoryType.FINISHED),
    @SerializedName("cat-order")ORDER("Ordered", 2, HDBSkulls.BROWN_O, "ordered", FilterCategoryType.ORDER),
    @SerializedName("cat-other")OTHER("Other", 3, HDBSkulls.BLUE_O, "other", FilterCategoryType.OTHER),
    @SerializedName("cat-admin") ADMIN("Administration", 3, HDBSkulls.RED_A, "administration", FilterCategoryType.ADMIN),
    @SerializedName("cat-secret")SECRET("Secret", 3, HDBSkulls.BLACK_S, "secret", FilterCategoryType.SECRET);

    private String label;
    @Getter private int weight;
    @Getter private HDBSkulls itemStack;
    @Getter private String permission;
    @Getter private FilterCategoryType filter;

    private CategoryType(String label, int weight, HDBSkulls itemStack, String permission, FilterCategoryType filter) {
        this.label = label;
        this.weight = weight;
        this.itemStack = itemStack;
        this.permission = permission;
        this.filter = filter;
    }

    public String getRawLabel() {
        return label;
    }

    public String getLabel() {
        return label + "-Worlds";
    }

    public CategoryType next() {
        int order = this.ordinal();
        int newSpot = 0;
        CategoryType result = this;
        if (order == values().length - 1)
            newSpot = 0;
        else
            newSpot = order + 1;
        for (CategoryType val : values())
            if (val.ordinal() == newSpot)
                result = val;
        return result;
    }
    public CategoryType last() {
        int order = this.ordinal();
        int newSpot = 0;
        CategoryType result = this;
        if (order == 0)
            newSpot = values().length - 1;
        else
            newSpot = order - 1;
        for (CategoryType val : values())
            if (val.ordinal() == newSpot)
                result = val;
        return result;
    }
}