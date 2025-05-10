package germanrputils.api.models;

public final class PlantFactory {

  private PlantFactory() {
    // Hide public constructor
  }

  public static Plant createPlant(final PlantType type) {
    return createPlant(type, true, 0, 0, type.getMaxTime(), type.getYieldUnit());
  }

  public static Plant createPlant(
      final PlantType type,
      final boolean active,
      final int value,
      final int currentTime,
      final int maxTime,
      final String yieldUnit
  ) {
    return switch (type) {
      case HEILKRAUTPFLANZE -> new PlantHeilkraut(active, value, currentTime, maxTime, yieldUnit);
      case ROSE -> new PlantRose(active, value, currentTime, maxTime, yieldUnit);
      case STOFF -> new PlantStoff(active, value, currentTime, maxTime, yieldUnit);
    };
  }

}
