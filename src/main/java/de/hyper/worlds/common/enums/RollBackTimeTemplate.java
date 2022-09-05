package de.hyper.worlds.common.enums;

import de.hyper.worlds.common.util.TimeStampFormatting;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import lombok.Getter;

@Getter
public enum RollBackTimeTemplate {

    FIVE_MINUTES(300000L, "rollback.fiveminutes"),
    TEN_MINUTES(600000L, "rollback.tenminutes"),
    TWENTY_MINUTES(1200000L, "rollback.twentyminutes"),
    THIRTY_MINUTES(1800000L, "rollback.thirtyminutes"),
    SIXTY_MINUTES(3600000L, "rollback.sixtyminutes"),
    NINTY_MINUTES(5400000L, "rollback.nintyminutes"),
    TWO_HOURS(7200000L, "rollback.twohours"),
    THREE_HOURS(10800000L, "rollback.threehours"),
    SIX_HOURS(21600000L, "rollback.sixhours"),
    TWELVE_HOURS(43200000L, "rollback.twelvehours"),
    EIGHTTEEN_HOURS(64800000L, "rollback.eightteenhours"),
    TWENTYFOUR_HOURS(86400000L, "rollback.twentyfourhours");

    protected long time;
    protected String lKey;

    private RollBackTimeTemplate(long time, String lKey) {
        this.time = time;
        this.lKey = lKey;
    }

    public RollBackTimeTemplate next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public RollBackTimeTemplate last() {
        return values()[(this.ordinal() - 1 + values().length) % values().length];
    }

    public String[] getItemLore(long currentDuration) {
        Language lang = WorldManagement.get().getLanguage();
        String[] r = new String[10 + values().length];
        int a = 0;
        r[a++] = lang.getText("inventory.rollback.templatetime.desc.1.1");
        r[a++] = lang.getText("inventory.rollback.templatetime.desc.1.2");
        r[a++] = "   ";
        r[a++] = lang.getText(
                "inventory.rollback.templatetime.desc.2",
                TimeStampFormatting.formatLong(currentDuration, "b"));
        r[a++] = " ";
        for (RollBackTimeTemplate d : values()) {
            r[a++] = ((d.equals(this)) ? "§7➙ §b§l" : "    §9") + lang.getText(d.getLKey());
        }
        r[a++] = "  ";
        r[a++] = lang.getText("inventory.rollback.templatetime.desc.3");
        r[a++] = lang.getText("inventory.rollback.templatetime.desc.4");
        r[a++] = lang.getText("inventory.rollback.templatetime.desc.5.1");
        r[a++] = lang.getText("inventory.rollback.templatetime.desc.5.2");
        return r;
    }
}
