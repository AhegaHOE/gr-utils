package germanrputils.core;

import germanrputils.core.listener.NameTagListener;
import germanrputils.core.listener.OnServerConnect;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class GRUtilsAddon extends LabyAddon<GRUtilsConfiguration> {

  private OnServerConnect onServerConnect;
  private GRUtilsConfiguration GRUtilsConfiguration;

  @Override
  protected void enable() {
    this.registerSettingCategory();
    this.onServerConnect = new OnServerConnect(this);
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
    this.labyAPI().eventBus().registerListener(this.onServerConnect);
    this.labyAPI().eventBus().registerListener(new NameTagListener(this));

  }

  private void registerServices() {

  }
  public OnServerConnect OnServerConnect() {
    return this.onServerConnect;
  }
  public GRUtilsConfiguration GRUtilsConfiguration(){
    return this.GRUtilsConfiguration;
  }
}
