package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.icon.Icon;

public class StoffHudWidget extends PlantHudWidget {

  private static final Plant DUMMY_PLANT = PlantFactory.createPlant(PlantType.STOFF, true, 1, 5,
      PlantType.STOFF.getMaxTime(), PlantType.STOFF.getYieldUnit());

  public StoffHudWidget(
      HudWidgetCategory category,
      Icon icon
  ) {
    super("stoff", category, icon);
  }

  @Override
  public Plant getDummyPlant() {
    return DUMMY_PLANT;
  }

}
