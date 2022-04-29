package de.hyper.worlds.common.obj;

import de.hyper.worlds.common.enums.SortDirection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldHistory {

    private HashMap<Long, HistoryRecord> records;

    public WorldHistory() {
        this.records = new HashMap<>();
    }

    public void put(HistoryRecord historyRecord) {
        long happend = System.currentTimeMillis();
        while (records.containsKey(happend)) {
            happend++;
        }
        records.put(happend, historyRecord);
    }

    public Map<Long, HistoryRecord> sort(@NotNull SortDirection sortDirection, @Nullable UUID searchFor) {
        Map<Long, HistoryRecord> old = (HashMap<Long, HistoryRecord>) records.clone();
        Map<Long, HistoryRecord> filtered;
        if (searchFor != null) {
            Map<Long, HistoryRecord> finalSorted = new ConcurrentHashMap<>();
            old.forEach((happend, record) -> {
                if (record.getEditor().equals(searchFor)) {
                    finalSorted.put(happend, record);
                }
            });
            filtered = finalSorted;
        } else {
            filtered = old;
        }
        List<Long> sortedLongs = new ArrayList<>();
        filtered.forEach((happend, record) -> {
            sortedLongs.add(happend);
        });
        sortedLongs.sort(sortDirection.getComparator());
        Map<Long, HistoryRecord> result = new HashMap();
        sortedLongs.forEach(happend -> {
            result.put(happend, filtered.get(happend));
        });
        return result;
    }
}