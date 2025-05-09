package germanrputils.api.models;

public non-sealed class Heilkrautpflanze extends Plant {


    protected Heilkrautpflanze(boolean active, int value, int currentTime, int maxTime) {
        super(PlantType.HEILKRAUTPFLANZE, active, value, currentTime, maxTime);
    }

}
