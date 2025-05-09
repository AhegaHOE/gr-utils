package germanrputils.api.models;

import java.util.Optional;

public enum PlantType {

    HEILKRAUTPFLANZE("Heilkrautpflanze", "Heilkrautpflanze", 15),
    ROSE("Rosenstrauch", "Rose", 8);

    private final String displayName;
    private final String paketType;
    private final int maxTime;

    PlantType(String displayName, String paketType, int maxTime) {
        this.displayName = displayName;
        this.paketType = paketType;
        this.maxTime = maxTime;
    }

    /**
     * This method takes in the display name sent to the player
     * in the chat when mentioning the plant
     *
     * @param displayName the display name
     * @return the associated {@link PlantType}
     */
    public static Optional<PlantType> fromDisplayName(final String displayName) {
        for (final PlantType value : values()) {
            if (value.displayName.equals(displayName)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * This method takes in a given type which is parsed
     * from the packets that are sent to the client.
     *
     * @param type the type that is mentioned in the packet
     * @return the associated {@link PlantType}
     */
    public static Optional<PlantType> fromPaketType(final String type) {
        for (PlantType value : values()) {
            if (value.paketType.equals(type)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxTime() {
        return maxTime;
    }

}
