package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantType;
import germanrputils.core.network.PlantPaket;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.NotNull;

public abstract class PlantHudWidget
        extends TextHudWidget<PlantHudWidget.PlantHudWidgetConfig>
        implements PlantPaketReceiver {

    private static final Component PROGRESS_KEY = Component.translatable(
            "germanrputils.widget.plant.progressKey");
    private static final Component YIELD_KEY = Component.translatable(
            "germanrputils.widget.plant.yieldKey");
    private static final String PROGRESS_TRANSLATABLE_VALUE = "germanrputils.widget.plant.progressValue";
    private static final String YIELD_TRANSLATABLE_VALUE = "germanrputils.widget.plant.yieldValue";

    protected TextLine progressLine;
    protected TextLine yieldLine;

    protected Plant plant;

    protected PlantHudWidget(
            final String id,
            final HudWidgetCategory category,
            final Icon icon
    ) {
        super(id, PlantHudWidgetConfig.class);
        this.bindCategory(category);
        this.setIcon(icon);
    }

    @Override
    public void load(final PlantHudWidgetConfig config) {
        super.load(config);

        final String i18nProgressValue = I18n.getTranslation(PROGRESS_TRANSLATABLE_VALUE, 0, 0);
        final String i18nYieldValue = I18n.getTranslation(YIELD_TRANSLATABLE_VALUE, 0, 0);

        this.progressLine = this.createLine(PROGRESS_KEY, i18nProgressValue);
        this.yieldLine = this.createLine(YIELD_KEY, i18nYieldValue);
    }

    public abstract void renderDummyPlant(final Plant plant);

    protected void renderPlant(
            final @NotNull Plant plant,
            final boolean isEditorContext
    ) {
        if (isEditorContext) {
            renderDummyPlant(plant);
            return;
        }

        final boolean showTimer = this.config.showTimer.get().equals(Boolean.TRUE) && plant.isActive();
        final boolean showYield = this.config.showYield.get().equals(Boolean.TRUE) && plant.isActive();

        if (showTimer) {
            this.progressLine.updateAndFlush(
                    I18n.getTranslation(
                            PROGRESS_TRANSLATABLE_VALUE, plant.getCurrentTime(),
                            plant.getMaxTime()
                    ));
        } else {
            this.progressLine.setState(TextLine.State.HIDDEN);
        }

        if (showYield) {
            this.yieldLine.updateAndFlush(
                    I18n.getTranslation(
                            YIELD_TRANSLATABLE_VALUE, plant.getValue(),
                            plant.getType().getIngameName()
                    ));
        } else {
            this.yieldLine.setState(TextLine.State.HIDDEN);
        }

    }

    @Override
    public void onPaketReceive(final @NotNull PlantPaket paket) {
        if (!paket.isActive()) {
            this.plant = null;
        }

        switch (paket.getType()) {
            case "Heilkrautpflanze" -> this.plant = new Plant(
                    PlantType.HEILKRAUTPFLANZE,
                    paket.isActive(),
                    paket.getValue(),
                    paket.getCurrentTime(),
                    paket.getMaxTime()
            );
            case "Rose" -> this.plant = new Plant(
                    PlantType.ROSE,
                    paket.isActive(),
                    paket.getValue(),
                    paket.getCurrentTime(),
                    paket.getMaxTime()
            );
            default -> {
                // Hide
            }
        }

    }

    public static class PlantHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchSetting
        private final ConfigProperty<Boolean> showTimer = new ConfigProperty<>(true);

        @SwitchSetting
        private final ConfigProperty<Boolean> showYield = new ConfigProperty<>(true);

    }

}
