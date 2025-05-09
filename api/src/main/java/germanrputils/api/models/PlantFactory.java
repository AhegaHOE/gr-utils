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
      final int maxTime
  ) {
    return switch (type) {
      case HEILKRAUTPFLANZE -> new Heilkrautpflanze(active, value, currentTime, maxTime);
      case ROSE -> new Rose(active, value, currentTime, maxTime);
    };
  }

}
