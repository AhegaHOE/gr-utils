package germanrputils.api.models;

public class Plant {

    protected final PlantType type;
    protected final boolean active;
    protected final int value;
    protected final int currentTime;
    protected final int maxTime;

    public Plant(
            final PlantType type,
            final boolean active,
            final int value,
            final int currentTime,
            final int maxTime
    ) {
        this.type = type;
        this.active = active;
        this.value = value;
        this.currentTime = currentTime;
        this.maxTime = maxTime;
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
}
