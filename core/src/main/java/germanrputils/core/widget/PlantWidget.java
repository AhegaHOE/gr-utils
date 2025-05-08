package germanrputils.core.widget;

import germanrputils.core.GRUtilsAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.I18n;

public class PlantWidget extends TextHudWidget<TextHudWidgetConfig> {

    private final GRUtilsAddon addon;

    private TextLine progressLine;
    private TextLine yieldLine;

    public PlantWidget(GRUtilsAddon addon, HudWidgetCategory category) {
        super("plant");

        this.addon = addon;

        this.bindCategory(category);
        this.setIcon(Icon.texture(ResourceLocation.create("germanrputils", "images/plant.png")));
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.progressLine = super.createLine(
                Component.translatable("germanrputils.widget.plant.progressKey"),
                I18n.getTranslation("germanrputils.widget.plant.progressValue", 0, 0)
        );
        this.yieldLine = super.createLine(
                Component.translatable("germanrputils.widget.plant.yieldKey"),
                I18n.getTranslation("germanrputils.widget.plant.yieldValue", 0, "Testplantage")
        );
    }

    @Override
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);
    }

}