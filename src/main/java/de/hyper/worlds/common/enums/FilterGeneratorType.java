package de.hyper.worlds.common.enums;

import de.hyper.worlds.common.util.items.HDBSkulls;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum FilterGeneratorType {

    NORMAL("Normal"),
    LARGE_BIOMES("LargeBiomes"),
    FLAT("Flat"),
    VOID("Void"),
    END("End"),
    NETHER("Nether"),
    OCEAN("Ocean"),
    ALL("All");

    private String label;

    private FilterGeneratorType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label + "-Worlds";
    }

    public HDBSkulls getItemStack() {
        if (this.equals(ALL)) {
            return HDBSkulls.YELLOW_A;
        } else {
            return HDBSkulls.GRASS_BLOCK;
        }
    }

    public GeneratorType getGeneratorType() {
        if (this.equals(ALL)) {
            return null;
        } else {
            return GeneratorType.valueOf(this.name());
        }
    }

    public static List<FilterGeneratorType> list() {
        List<FilterGeneratorType> list = new ArrayList<>();
        for (FilterGeneratorType f : FilterGeneratorType.values()) {
            list.add(f);
        }
        return list;
    }
}
