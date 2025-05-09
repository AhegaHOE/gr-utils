package germanrputils.api.models;

public final class PlantFactory {

  private PlantFactory() {
    // Hide public constructor
  }

  public static Plant createPlant(
      final PlantType type,
      final boolean active,
      final int value,
      final int currentTime,
      final int maxTime,
      final char yieldUnit
  ) {
    return switch (type) {
      case HEILKRAUTPFLANZE -> new PlantHeilkraut(active, value, currentTime, maxTime, yieldUnit);
      case ROSE -> new PlantRose(active, value, currentTime, maxTime, yieldUnit);
    };
  }

}
