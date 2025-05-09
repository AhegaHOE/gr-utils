package germanrputils.core.listener;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
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

public class PlantListener {

  private static final Pattern HARVEST_PATTERN = Pattern.compile(
      "^► Du hast \\w* (\\w*) mit \\d*[x|g] Erlös geerntet\\.$", Pattern.CANON_EQ);
  private static final String HEILKRAUTPFLANZE_SOW_MESSAGE = "► Du hast eine Heilkrautpflanze ausgelegt.";
  private static final String ROSE_SOW_MESSAGE = "► Du hast einen Rosenstrauch angepflanzt.";

  private final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget;
  private final RoseHudWidget roseHudWidget;

  public PlantListener(
      final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget,
      final RoseHudWidget roseHudWidget
  ) {
    this.heilkrautpflanzeHudWidget = heilkrautpflanzeHudWidget;
    this.roseHudWidget = roseHudWidget;
  }

  @Subscribe
  public void onChatReceiveEvent(final ChatReceiveEvent event) {
    final String message = event.chatMessage().getPlainText();

    // This is to make the widget displays the plant right after planting it
    // because the server does not send a packet of the plant until it first ticks
    switch (message) {
      case HEILKRAUTPFLANZE_SOW_MESSAGE -> {
        final Plant plant = PlantFactory.createPlant(PlantType.HEILKRAUTPFLANZE, true, 0, 0,
            PlantType.HEILKRAUTPFLANZE.getMaxTime());
        this.heilkrautpflanzeHudWidget.beginPlant(plant);
        return;
      }
      case ROSE_SOW_MESSAGE -> {
        final Plant plant = PlantFactory.createPlant(PlantType.ROSE, true, 0, 0,
            PlantType.ROSE.getMaxTime());
        this.roseHudWidget.beginPlant(plant);
        return;
      }
      default -> {
        // Ignore unknown messages
      }
    }

    final Matcher matcher = HARVEST_PATTERN.matcher(message);

    if (!matcher.find()) {
      return;
    }

    PlantType.fromDisplayName(HARVEST_PATTERN.matcher(message).group(1))
        .ifPresent(plantType -> {
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

}
