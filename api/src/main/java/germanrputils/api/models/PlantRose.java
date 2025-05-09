package germanrputils.api.models;

public non-sealed class PlantRose extends Plant {

  protected PlantRose(boolean active, int value, int currentTime, int maxTime, char yieldUnit) {
    super(PlantType.ROSE, active, value, currentTime, maxTime, yieldUnit);
  }

}
