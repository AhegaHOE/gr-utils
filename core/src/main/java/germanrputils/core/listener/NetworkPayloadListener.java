package germanrputils.core.listener;

import germanrputils.core.GRUtilsAddon;
import germanrputils.core.Utils;
import germanrputils.core.network.PaketFactory;
import germanrputils.core.network.PlantPaket;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget;
import germanrputils.core.widget.RoseHudWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkPayloadEvent;
import net.labymod.api.util.GsonUtil;

public class NetworkPayloadListener {

    private final GRUtilsAddon addon;
    private final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget;
    private final RoseHudWidget roseHudWidget;

    public NetworkPayloadListener(
            final GRUtilsAddon addon,
            final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget,
            final RoseHudWidget roseHudWidget
    ) {
        this.addon = addon;
        this.heilkrautpflanzeHudWidget = heilkrautpflanzeHudWidget;
        this.roseHudWidget = roseHudWidget;
    }

    @Subscribe
    public void onNetworkPayloadEvent(NetworkPayloadEvent event) {
        if (!Utils.isLegacyAddonPacket(event.identifier())) {
            return;
        }

        PaketFactory.createPaket(event.getPayload()).ifPresent(paket -> {
            // Ignore unknown pakets
            if (!(paket instanceof PlantPaket plantPaket)) {
                return;
            }

            this.addon.logger().info("Received GR Paket: " + GsonUtil.DEFAULT_GSON.toJson(paket));

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
