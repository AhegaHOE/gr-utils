package germanrputils.core;

import germanrputils.core.listener.NetworkPayloadListener;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget;
import germanrputils.core.widget.RoseHudWidget;
import germanrputils.core.widget.category.GRUtilsWidgetCategory;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GRUtilsAddon extends LabyAddon<GRUtilsConfiguration> {

  public static final String NAMESPACE = "germanrputils";

  private HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget;
  private RoseHudWidget roseHudWidget;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    registerWidgets();
    registerListener(heilkrautpflanzeHudWidget, roseHudWidget);
    registerCommands();

    this.logger().info("Enabled GermanRP Utils!");
  }

  @Override
  protected Class<GRUtilsConfiguration> configurationClass() {
    return GRUtilsConfiguration.class;
  }

  private void registerWidgets() {
    final HudWidgetRegistry widgetRegistry = this.labyAPI().hudWidgetRegistry();
    final HudWidgetCategory widgetCategory = new GRUtilsWidgetCategory();

    this.heilkrautpflanzeHudWidget = new HeilkrautpflanzeHudWidget(
        widgetCategory,
        Icon.texture(ResourceLocation.create(NAMESPACE, "images/heilkrautpflanze.png"))
    );
    this.roseHudWidget = new RoseHudWidget(
        widgetCategory,
        Icon.texture(ResourceLocation.create(NAMESPACE, "images/rose.png"))
    );

    widgetRegistry.categoryRegistry().register(widgetCategory);
    widgetRegistry.register(heilkrautpflanzeHudWidget);
    widgetRegistry.register(roseHudWidget);
  }

  private void registerListener(
      final HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget,
      final RoseHudWidget roseHudWidget
  ) {
    this.registerListener(new NetworkPayloadListener(
        this,
        heilkrautpflanzeHudWidget,
        roseHudWidget)
    );
  }

  private void registerCommands() {
    // Commands will go here
  }

}
