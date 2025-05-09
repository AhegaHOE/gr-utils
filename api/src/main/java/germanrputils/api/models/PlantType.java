package germanrputils.api.models;

public enum PlantType {

    HEILKRAUTPFLANZE("Heilkrautpflanze"),
    ROSE("Rose");

    private final String displayName;

    PlantType(String displayName) {
        this.displayName = displayName;
    }

    public static PlantType fromPaketName(final String type) {
        return switch (type) {
            case "Heilkrautpflanze" -> HEILKRAUTPFLANZE;
            case "Rose" -> ROSE;
            default -> null;
        };
    }

    public String getDisplayName() {
        return displayName;
    }

}
