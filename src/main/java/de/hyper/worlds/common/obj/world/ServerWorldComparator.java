package de.hyper.worlds.common.obj.world;

import de.hyper.worlds.common.enums.SortDirection;
import lombok.AllArgsConstructor;

import java.util.Comparator;

@AllArgsConstructor
public class ServerWorldComparator implements Comparator<ServerWorld> {

    protected SortDirection sortDirection;

    @Override
    public int compare(ServerWorld o1, ServerWorld o2) {
        if (o1.getWorldName().equals(o2.getWorldName())) {
            return 0;
        }
        if (sortDirection == SortDirection.UP) {
            return o1.getWorldName().compareToIgnoreCase(o2.getWorldName());
        }
        if (sortDirection == SortDirection.DOWN) {
            return (-1 * (o1.getWorldName().compareToIgnoreCase(o2.getWorldName())));
        }
        return 0;
    }
}
