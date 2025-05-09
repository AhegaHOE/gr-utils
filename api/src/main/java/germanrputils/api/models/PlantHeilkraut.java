package germanrputils.api.models;

public non-sealed class PlantHeilkraut extends Plant {

  protected PlantHeilkraut(boolean active, int value, int currentTime, int maxTime,
      char yieldUnit) {
    super(PlantType.HEILKRAUTPFLANZE, active, value, currentTime, maxTime, yieldUnit);
    }

}
