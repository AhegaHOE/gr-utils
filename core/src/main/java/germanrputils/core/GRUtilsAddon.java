package germanrputils.core;

import germanrputils.core.listener.OnServerConnect;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GRUtilsAddon extends LabyAddon<GRUtilsConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    registerListener();
    registerCommands();

    this.logger().info("Enabled GRUP!");
  }

  @Override
  protected Class<GRUtilsConfiguration> configurationClass() {
    return GRUtilsConfiguration.class;
  }

  private void registerCommands() {

  }

  private void registerListener() {
    this.labyAPI().eventBus().registerListener(new OnServerConnect(this));

  }

  private void registerServices() {

  }

}
