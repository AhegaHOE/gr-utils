package germanrputils.core;

import germanrputils.core.widget.GRUtilsWidgetCategory;
import germanrputils.core.widget.PlantWidget;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GRUtilsAddon extends LabyAddon<GRUtilsConfiguration> {

    @Override
    protected void enable() {
        this.registerSettingCategory();

        registerListener();
        registerCommands();
        registerServices();
        registerWidgets();

        this.logger().info("Enabled GermanRP Utils!");
    }

    @Override
    protected Class<GRUtilsConfiguration> configurationClass() {
        return GRUtilsConfiguration.class;
    }

    private void registerCommands() {

    }

    private void registerListener() {

    }

    private void registerServices() {

    }

    private void registerWidgets() {
        final HudWidgetRegistry widgetRegistry = this.labyAPI().hudWidgetRegistry();
        final HudWidgetCategory widgetCategory = new GRUtilsWidgetCategory();

        widgetRegistry.categoryRegistry().register(widgetCategory);
        widgetRegistry.register(new PlantWidget(this, widgetCategory));
    }

}
