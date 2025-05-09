package germanrputils.core.listener;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import germanrputils.core.GRUtilsAddon;
import germanrputils.core.Utils;
import germanrputils.core.network.PaketFactory;
import germanrputils.core.network.PlantPaket;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget;
import germanrputils.core.widget.RoseHudWidget;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.util.GsonUtil;

public class PlantListener {

  private static final Pattern HARVEST_PATTERN = Pattern.compile(
      "^► Du hast \\w* (\\w*) mit \\d*[x|g] Erlös geerntet\\.$", Pattern.CANON_EQ);
  private static final String HEILKRAUTPFLANZE_SOW_MESSAGE = "► Du hast eine Heilkrautpflanze ausgelegt.";
  private static final String ROSE_SOW_MESSAGE = "► Du hast einen Rosenstrauch angepflanzt.";

  private final GRUtilsAddon addon;
  private final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget;
  private final RoseHudWidget roseHudWidget;

  public PlantListener(final GRUtilsAddon addon,
      final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget,
      final RoseHudWidget roseHudWidget) {
    this.addon = addon;
    this.heilkrautpflanzeHudWidget = heilkrautpflanzeHudWidget;
    this.roseHudWidget = roseHudWidget;
  }

  @Subscribe
  public void onChatReceiveEvent(final ChatReceiveEvent event) {
    final String message = event.chatMessage().getPlainText();

    if (beginPlantIfSowMessage(message)) {
      return;
    }

    final Matcher matcher = HARVEST_PATTERN.matcher(message);

    if (!matcher.find()) {
      return;
    }

    final String displayName = matcher.group(1);
    PlantType.fromDisplayName(displayName).ifPresent(plantType -> {
      switch (plantType) {
        case HEILKRAUTPFLANZE -> this.heilkrautpflanzeHudWidget.reset();
        case ROSE -> this.roseHudWidget.reset();
      }
    });
  }

  @Subscribe
  public void onNetworkPayloadEvent(final NetworkPayloadEvent event) {
    if (!Utils.isLegacyAddonPacket(event.identifier())) {
      return;
    }

    PaketFactory.createPaket(event.getPayload()).ifPresent(paket -> {

      this.addon.logger().debug("Received packet: " + GsonUtil.DEFAULT_GSON.toJson(paket));

      // Ignore unknown pakets
      if (!(paket instanceof PlantPaket plantPaket)) {
        return;
      }

      switch (plantPaket.getType()) {
        case HEILKRAUTPFLANZE -> this.heilkrautpflanzeHudWidget.onPaketReceive(plantPaket);
        case ROSE -> this.roseHudWidget.onPaketReceive(plantPaket);
        default -> {
          // Ignore unknown packets
        }
      }

    });
  }

  /**
   * Checks if the given message corresponds to a sow action for a specific plant type and
   * initializes the respective HUD widget if applicable.
   *
   * @param message the chat message to be checked for sow actions
   * @return true if the message corresponds to a sow action and the HUD widget was updated; false
   * otherwise
   */
  private boolean beginPlantIfSowMessage(final String message) {
    // This is to makes the widget display the plant right after planting it
    // because the server does not send a packet of the plant until it first ticks
    switch (message) {
      case HEILKRAUTPFLANZE_SOW_MESSAGE -> {
        final Plant plant = PlantFactory.createPlant(PlantType.HEILKRAUTPFLANZE, true, 0, 0,
            PlantType.HEILKRAUTPFLANZE.getMaxTime(), PlantType.HEILKRAUTPFLANZE.getYieldUnit());
        this.heilkrautpflanzeHudWidget.updatePlant(plant);
        return true;
      }
      case ROSE_SOW_MESSAGE -> {
        final Plant plant = PlantFactory.createPlant(PlantType.ROSE, true, 0, 0,
            PlantType.ROSE.getMaxTime(), PlantType.ROSE.getYieldUnit());
        this.roseHudWidget.updatePlant(plant);
        return true;
      }
      default -> {
        return false;
      }
    }
  }

}
