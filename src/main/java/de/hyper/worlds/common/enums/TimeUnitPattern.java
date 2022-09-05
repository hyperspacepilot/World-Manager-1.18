package de.hyper.worlds.common.enums;

public enum TimeUnitPattern {

    SECONDS("seconds", "secs"),
    MINUTES("minutes", "mins"),
    HOURS("hours", "hs"),
    DAYS("days", "ds"),
    WEEKS("weeks", "ws"),
    YEARS("years", "ys");

    protected String pattern;
    protected String shortPattern;

    private TimeUnitPattern(String pattern, String shortPattern) {
        this.pattern = pattern;
        this.shortPattern = shortPattern;
    }

    public boolean matches(String input) {
        if (this.pattern.toLowerCase().startsWith(input.toLowerCase())) {
            return true;
        }
        return (this.shortPattern.toLowerCase().startsWith(input.toLowerCase()));
    }
}
