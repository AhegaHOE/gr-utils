package germanrputils.core;

import germanrputils.core.listener.NetworkPayloadListener;
import germanrputils.core.widget.category.GRUtilsWidgetCategory;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GRUtilsAddon extends LabyAddon<GRUtilsConfiguration> {

  private HeilkrautpflanzeHudWidget heilkrautpflanzeHudWidget;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    registerWidgets();
    registerListener();
    registerCommands();
    registerServices();

    this.logger().info("Enabled GermanRP Utils!");
  }

  @Override
  protected Class<GRUtilsConfiguration> configurationClass() {
    return GRUtilsConfiguration.class;
  }

  private void registerWidgets() {
    final HudWidgetRegistry widgetRegistry = this.labyAPI().hudWidgetRegistry();
    final HudWidgetCategory widgetCategory = new GRUtilsWidgetCategory();

    this.heilkrautpflanzeHudWidget = new HeilkrautpflanzeHudWidget(widgetCategory);

    widgetRegistry.categoryRegistry().register(widgetCategory);
    widgetRegistry.register(heilkrautpflanzeHudWidget);
  }

  private void registerListener() {
    this.registerListener(new NetworkPayloadListener(this, heilkrautpflanzeHudWidget));
  }

  private void registerCommands() {

  }

  private void registerServices() {

  }

}
