package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.icon.Icon;

@SuppressWarnings("java:S110")
public class RoseHudWidget extends PlantHudWidget {

  private static final Plant DUMMY_PLANT = PlantFactory.createPlant(PlantType.ROSE, true, 3, 3, 8);

  public RoseHudWidget(HudWidgetCategory category, Icon icon) {
    super("rose", category, icon, PlantHudWidget.PlantHudWidgetConfig.class);
  }

  @Override
  public Plant getDummyPlant() {
    return DUMMY_PLANT;
  }

}
