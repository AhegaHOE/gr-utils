package germanrputils.api.models;

public non-sealed class Rose extends Plant {

  protected Rose(boolean active, int value, int currentTime, int maxTime) {
    super(PlantType.ROSE, active, value, currentTime, maxTime);
  }

}
