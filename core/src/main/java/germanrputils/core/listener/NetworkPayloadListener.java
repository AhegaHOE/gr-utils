package germanrputils.core.listener;

import germanrputils.core.GRUtilsAddon;
import germanrputils.core.Utils;
import germanrputils.core.network.PaketFactory;
import germanrputils.core.network.PlantPaket;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.util.GsonUtil;

public class NetworkPayloadListener {

  private final GRUtilsAddon addon;
  private final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget;

  public NetworkPayloadListener(
      final GRUtilsAddon addon,
      final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget) {
    this.addon = addon;
    this.heilkrautpflanzeHudWidget = heilkrautpflanzeHudWidget;
  }

  @Subscribe
  public void onNetworkPayloadEvent(NetworkPayloadEvent event) {
    if (!Utils.isLegacyAddonPacket(event.identifier())) {
      return;
    }

    PaketFactory.createPaket(event.getPayload()).ifPresent(paket -> {
      switch (paket) {

        case PlantPaket plantPaket -> this.heilkrautpflanzeHudWidget.onPaketReceive(plantPaket);

        default -> {
          // Ignore unknown pakets
        }
      }

      this.addon.logger().info("Received GR Paket: " + GsonUtil.DEFAULT_GSON.toJson(paket));
    });
  }

}
