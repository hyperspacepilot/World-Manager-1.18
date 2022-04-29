package de.hyper.worlds.common.obj;

import de.hyper.worlds.common.enums.RecordType;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class HistoryRecord {

    private RecordType recordType;
    private UUID editor;
    private String before;
    private String after;

    private String parseTime(long l) {
        Date date = new Date(l);
        String format = "DD.MM.YYYY hh:mm:ss";
        String result = format;
        String gmtString = date.toGMTString();
        result = result.replace("DD", gmtString.split(" ")[2]); //days
        result = result.replace("MM", gmtString.split(" ")[1]); //months
        result = result.replace("YYYY", gmtString.split(" ")[5]); //years
        result = result.replace("hh", gmtString.split(" ")[3].split(":")[0]); //hours
        result = result.replace("hh", gmtString.split(" ")[3].split(":")[1]); //minutes
        result = result.replace("hh", gmtString.split(" ")[3].split(":")[2]); //seconds
        return result;
    }
}
