package germanrputils.core;

import germanrputils.core.Enum.FactionNameEnums;
import germanrputils.core.Enum.NameTagEnums;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("Nametags")
public class NameTagSubConfig extends Config {
  @DropdownSetting
  private final ConfigProperty<NameTagEnums> factioncolor;
  @DropdownSetting
  private final ConfigProperty<NameTagEnums> darklistcolor;
  @DropdownSetting
  private final ConfigProperty<NameTagEnums> bountycolor;
  @DropdownSetting
  private final ConfigProperty<FactionNameEnums> factionname;

  public NameTagSubConfig(){
    this.factioncolor = new ConfigProperty(NameTagEnums.NONE);
    this.darklistcolor = new ConfigProperty(NameTagEnums.NONE);
    this.bountycolor = new ConfigProperty(NameTagEnums.NONE);
    this.factionname = new ConfigProperty(FactionNameEnums.NONE);
  }
  public ConfigProperty<NameTagEnums> factiontag() {
    return this.factioncolor;
  }

  public ConfigProperty<NameTagEnums> darklisttag() {
    return this.darklistcolor;
  }

  public ConfigProperty<NameTagEnums> bountytag() {
    return this.bountycolor;
  }


  public ConfigProperty<FactionNameEnums> factionname() {
    return factionname;
  }
}
