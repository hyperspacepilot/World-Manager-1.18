package de.hyper.worlds.common.obj.world;

import de.hyper.worlds.common.enums.FilterCategoryType;
import de.hyper.worlds.common.enums.FilterGeneratorType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WorldFilter {

    @Setter private FilterCategoryType categoryType;
    @Setter private FilterGeneratorType generatorType;
    private String name;

    public WorldFilter() {
        this.categoryType = FilterCategoryType.ALL;
        this.generatorType = FilterGeneratorType.ALL;
        this.name = "#all";
    }

    public void setName(String s) {
        this.name = s;
    }

    public List<ServerWorld> filter(List<ServerWorld> original) {
        List<ServerWorld> list = new ArrayList<>();
        original.forEach(world -> {
            boolean similarName = false;
            if (name.equals("#all") || world.getWorldName().toLowerCase().contains(name.toLowerCase())) {
                similarName = true;
            }
            boolean similarCategory = false;
            if (categoryType.equals(FilterCategoryType.ALL) || world.getCategoryType().getFilter().equals(categoryType)) {
                similarCategory = true;
            }
            boolean similarGenerator = false;
            if (generatorType.equals(FilterGeneratorType.ALL) || world.getGeneratorType().getFilter().equals(generatorType)) {
                similarGenerator = true;
            }
            if (similarName && similarCategory && similarGenerator) {
                list.add(world);
            }
        });
        return list;
    }
}
