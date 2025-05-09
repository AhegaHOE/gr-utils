package germanrputils.core;

import germanrputils.core.Enum.FactionNameEnums;
import germanrputils.core.Enum.NameTagEnums;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class GRUtilsConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @DropdownSetting
  private final ConfigProperty<NameTagEnums> factioncolor;
  @DropdownSetting
  private final ConfigProperty<NameTagEnums> darklistcolor;
  @DropdownSetting
  private final ConfigProperty<NameTagEnums> bountycolor;
  @DropdownSetting
  private final ConfigProperty<FactionNameEnums> factionname;
  public GRUtilsConfiguration() {
    this.factioncolor = new ConfigProperty(NameTagEnums.NONE);
    this.darklistcolor = new ConfigProperty(NameTagEnums.NONE);
    this.bountycolor = new ConfigProperty(NameTagEnums.NONE);
    this.factionname = new ConfigProperty(FactionNameEnums.NONE);

  }

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
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
