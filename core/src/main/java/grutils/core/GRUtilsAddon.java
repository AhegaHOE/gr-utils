package grutils.core;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GRUtilsAddon extends LabyAddon<GRUtilsConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    registerListeners();
    registerCommands();

    this.logger().info("Enabled GRUP!");
  }

  @Override
  protected Class<GRUtilsConfiguration> configurationClass() {
    return GRUtilsConfiguration.class;
  }

  private void registerCommands() {

  }

  private void registerListeners() {

  }

}
