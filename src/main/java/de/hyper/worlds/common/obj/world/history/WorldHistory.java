package de.hyper.worlds.common.obj.world.history;

import de.hyper.worlds.common.enums.SortDirection;
import de.hyper.worlds.domain.WorldManagement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class WorldHistory {

    private HashMap<Long, HistoryRecord> records;

    public WorldHistory() {
        this.records = new HashMap<>();
    }

    public void remove(HistoryRecord historyRecord) {
        this.records.remove(historyRecord.getTimeStamp(), historyRecord);
    }

    public void put(HistoryRecord historyRecord) {
        long happend = historyRecord.getTimeStamp();
        while (records.containsKey(happend)) {
            happend++;
        }
        historyRecord.setTimeStamp(happend);
        records.put(happend, historyRecord);
    }

    public void cleanUp() {
        long now = System.currentTimeMillis();
        long idle = WorldManagement.get().getConfiguration().getData("history-idle-time").getDataValueAsLong();
        List<Map.Entry<Long, HistoryRecord>> outsort = new ArrayList<>();
        for (Map.Entry<Long, HistoryRecord> entry : this.records.entrySet()) {
            if (entry.getKey() < (now - idle)) {
                outsort.add(entry);
            }
        }
        for (Map.Entry<Long, HistoryRecord> entry : outsort) {
            this.records.remove(entry.getKey(), entry.getValue());
        }
    }

    public List<HistoryRecord> sort(@NotNull SortDirection sortDirection, @Nullable UUID searchFor) {
        Map<Long, HistoryRecord> old = (HashMap<Long, HistoryRecord>) records.clone();
        Map<Long, HistoryRecord> filtered;
        if (searchFor != null) {
            Map<Long, HistoryRecord> finalSorted = new HashMap<>();
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
        List<HistoryRecord> result = new ArrayList<>();
        sortedLongs.forEach(happend -> {
            result.add(filtered.get(happend));
        });
        return result;
    }
}