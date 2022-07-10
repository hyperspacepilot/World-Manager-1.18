package de.hyper.worlds.common.enums;

import de.hyper.worlds.common.util.items.HDBSkulls;
import lombok.Getter;

@Getter
public enum RecordType {
    BLOCK_PLACE("history.recordtype.block.place", HDBSkulls.GRASS_BLOCK),
    BLOCK_BREAK("history.recordtype.block.break", HDBSkulls.PICKAXE_MINECON);

    private String lKey;
    private HDBSkulls hdbSkull;

    private RecordType(String lKey, HDBSkulls hdbSkull) {
        this.lKey = lKey;
        this.hdbSkull = hdbSkull;
    }
}