package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget.HeilkrautpflanzeHudConfig;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@SuppressWarnings("java:S110")
public class HeilkrautpflanzeHudWidget extends PlantHudWidget<HeilkrautpflanzeHudConfig> {

    private static final Plant DUMMY_PLANT = PlantFactory.createPlant(
            PlantType.HEILKRAUTPFLANZE,
            true,
            20,
            5,
            15,
            PlantType.HEILKRAUTPFLANZE.getYieldUnit()
    );

    public HeilkrautpflanzeHudWidget(HudWidgetCategory category, Icon icon) {
      super("heilkrautpflanze", category, icon, HeilkrautpflanzeHudConfig.class);
    }

    @Override
    public Plant getDummyPlant() {
        return DUMMY_PLANT;
    }

  public static class HeilkrautpflanzeHudConfig extends TextHudWidgetConfig {

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
