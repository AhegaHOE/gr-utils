package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import germanrputils.core.widget.RoseHudWidget.RoseHudWidgetConfig;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@SuppressWarnings("java:S110")
public class RoseHudWidget extends PlantHudWidget<RoseHudWidgetConfig> {

  private static final Plant DUMMY_PLANT = PlantFactory.createPlant(PlantType.ROSE, true, 3, 3, 8,
      PlantType.ROSE.getYieldUnit());

  public RoseHudWidget(HudWidgetCategory category, Icon icon) {
    super("rose", category, icon, RoseHudWidgetConfig.class);
  }

  @Override
  public Plant getDummyPlant() {
    return DUMMY_PLANT;
  }

  public static class RoseHudWidgetConfig extends TextHudWidgetConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> showTimer = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> showYield = new ConfigProperty<>(true);

    public ConfigProperty<Boolean> showTimer() {
      return this.showTimer;
    }

    public ConfigProperty<Boolean> showYield() {
      return this.showYield;
    }

  }

}
