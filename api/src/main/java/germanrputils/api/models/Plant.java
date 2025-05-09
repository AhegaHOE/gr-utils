package germanrputils.api.models;

public sealed class Plant permits PlantHeilkraut, PlantRose {

    protected final PlantType type;
    protected final boolean active;
    protected final int value;
    protected final int currentTime;
    protected final int maxTime;
    protected final char yieldUnit;

    protected Plant(
            final PlantType type,
            final boolean active,
            final int value,
            final int currentTime,
            final int maxTime,
            char yieldUnit
    ) {
        this.type = type;
        this.active = active;
        this.value = value;
        this.currentTime = currentTime;
        this.maxTime = maxTime;
        this.yieldUnit = yieldUnit;
    }

    public PlantType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public int getValue() {
        return value;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public char getYieldUnit() {
        return yieldUnit;
    }

}
