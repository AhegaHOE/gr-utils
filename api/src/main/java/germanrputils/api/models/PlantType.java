package germanrputils.api.models;

import java.util.Optional;

public enum PlantType {

  HEILKRAUTPFLANZE("Heilkrautpflanze"),
  ROSE("Rose");

  private final String ingameName;

  PlantType(String ingameName) {
    this.ingameName = ingameName;
  }

  public String getIngameName() {
    return ingameName;
  }

  public PlantType fromIngameName(final String ingameName) {
    if (ingameName == null) {
      return null;
    }

    for (PlantType type : values()) {
      if (type.getIngameName().equals(ingameName)) {
        return Optional.of(type);
      }
    }

    return Optional.empty();
  }

}
