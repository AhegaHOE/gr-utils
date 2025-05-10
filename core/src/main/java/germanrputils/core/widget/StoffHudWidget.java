package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import germanrputils.core.widget.StoffHudWidget.StoffHudWidgetConfig;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

public class StoffHudWidget extends PlantHudWidget<StoffHudWidgetConfig> {

  private static final Plant DUMMY_PLANT = PlantFactory.createPlant(PlantType.STOFF, true, 1, 5,
      PlantType.STOFF.getMaxTime(), PlantType.STOFF.getYieldUnit());

  public StoffHudWidget(
      HudWidgetCategory category,
      Icon icon
  ) {
    super("stoff", category, icon, StoffHudWidgetConfig.class);
  }

  @Override
  public Plant getDummyPlant() {
    return DUMMY_PLANT;
  }

  public static class StoffHudWidgetConfig extends TextHudWidgetConfig {

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
