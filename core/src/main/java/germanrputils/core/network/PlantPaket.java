package germanrputils.core.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import germanrputils.api.models.PlantType;
import net.labymod.api.util.GsonUtil;

public class PlantPaket implements GRPaket {

    private final boolean active;
    private final PlantType type;
    private final int value;
    private final int currentTime;
    private final int maxTime;

    public PlantPaket(final String paketContent) {
        try {
            final JsonObject jsonObject = GsonUtil.parse(paketContent).getAsJsonObject();

            this.active = jsonObject.get("active").getAsBoolean();
            this.type = PlantType.fromPaketType(jsonObject.get("type").getAsString()).orElseThrow();
            this.value = jsonObject.get("value").getAsInt();
            final JsonObject time = jsonObject.getAsJsonObject("time");
            this.currentTime = time.get("current").getAsInt();
            this.maxTime = time.get("max").getAsInt();

        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Invalid packet!");
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Malformed packet!");
        }
    }

    public boolean isActive() {
        return active;
    }

    public PlantType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getMaxTime() {
        return maxTime;
    }

}
