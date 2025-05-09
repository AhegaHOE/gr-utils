package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.icon.Icon;

@SuppressWarnings("java:S110")
public class HeilkrautpflanzeHudWidget extends PlantHudWidget {

    private static final Plant DUMMY_PLANT = PlantFactory.createPlant(
            PlantType.HEILKRAUTPFLANZE,
            true,
            20,
            5,
            15,
            PlantType.HEILKRAUTPFLANZE.getYieldUnit()
    );

    public HeilkrautpflanzeHudWidget(HudWidgetCategory category, Icon icon) {
        super("heilkrautpflanze", category, icon, PlantHudWidgetConfig.class);
    }

    @Override
    public Plant getDummyPlant() {
        return DUMMY_PLANT;
    }

}
