package germanrputils.api.models;

public non-sealed class PlantStoff extends Plant {

  protected PlantStoff(
      boolean active,
      int value,
      int currentTime,
      int maxTime,
      String yieldUnit
  ) {
    super(PlantType.STOFF, active, value, currentTime, maxTime, yieldUnit);
  }

}
