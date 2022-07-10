package de.hyper.worlds.common.obj.world.history;

import de.hyper.worlds.common.enums.RecordType;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.sLocation;
import de.hyper.worlds.common.util.TimeStampFormatting;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class HistoryRecord {

    private RecordType recordType;
    private UUID editor;
    @Setter
    private long timeStamp;
    private sLocation location;
    private Material before;
    private Material after;

    public void undo(ServerWorld serverWorld) {
        Block block = location.getLocation().getBlock();
        block.setType(this.before);
    }

    public HistoryDisplay getHistoryDisplay(ServerWorld serverWorld, HistoryRecord record) {
        Language lang = WorldManagement.get().getLanguage();
        String title = lang.getText(record.getRecordType().getLKey(), getMaterialName((this.recordType == RecordType.BLOCK_BREAK ? this.before : this.after)));
        String lore1 = lang.getText("history.change.display.timestamp", TimeStampFormatting.getGermanDateAndTime(record.getTimeStamp()));
        String lore2 = lang.getText("history.change.display.editor", WorldManagement.get().getName(record.getEditor()));
        String lore3 = lang.getText("history.change.display.where", "X: " + location.getX() + " Y: " + location.getY() + " Z: " + location.getZ());
        String lore4 = "   ";
        String lore5 = lang.getText("history.change.display.info.1");
        String lore6 = lang.getText("history.change.display.info.2");
        return HistoryDisplay.b(title,
                lore1, lore2, lore3, lore4, lore5, lore6);
    }

    public String getMaterialName(Material material) {
        String result = material.name().replace("_", "").toLowerCase();
        result = result.replaceFirst(result.substring(0, 1), material.name().substring(0, 1));
        return result;
    }

    public String getTime() {
        return TimeStampFormatting.getGermanDateAndTime(timeStamp);
    }
}
