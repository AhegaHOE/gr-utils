package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
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

    private static final Component PROGRESS_KEY = Component.translatable("germanrputils.widget.plant.progressKey");
    private static final Component YIELD_KEY = Component.translatable("germanrputils.widget.plant.yieldKey");
    private static final String PROGRESS_TRANSLATABLE_VALUE = "germanrputils.widget.plant.progressValue";
    private static final String YIELD_TRANSLATABLE_VALUE = "germanrputils.widget.plant.yieldValue";

    private TextLine progressLine;
    private TextLine yieldLine;

    private Plant plant;
    private boolean hudNeedsUpdate = true;

    protected PlantHudWidget(
            final String id,
            final HudWidgetCategory category,
            final Icon icon,
            final Class<PlantHudWidgetConfig> configClass
    ) {
        super(id, configClass);
        this.bindCategory(category);
        this.setIcon(icon);
    }

    @Override
    public void load(final PlantHudWidgetConfig config) {
        super.load(config);

        final String i18nProgressValue = I18n.getTranslation(PROGRESS_TRANSLATABLE_VALUE, 0, 0);
        final String i18nYieldValue = I18n.getTranslation(YIELD_TRANSLATABLE_VALUE, 0, "", 0);

        this.progressLine = this.createLine(PROGRESS_KEY, i18nProgressValue);
        this.yieldLine = this.createLine(YIELD_KEY, i18nYieldValue);
    }

    @Override
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);

        if (isEditorContext) {
            renderPlant(getDummyPlant());
            return;
        }

        if (this.plant == null) {
            this.progressLine.setState(TextLine.State.HIDDEN);
            this.yieldLine.setState(TextLine.State.HIDDEN);
            return;
        }

        if (!hudNeedsUpdate) {
            return;
        }

        renderPlant(this.plant);
    }

    public abstract Plant getDummyPlant();

    public void reset() {
        this.plant = null;
      this.hudNeedsUpdate = true;
    }

    public void updatePlant(final Plant plant) {
        this.plant = plant;
        this.progressLine.setState(TextLine.State.VISIBLE);
        this.yieldLine.setState(TextLine.State.VISIBLE);
        this.hudNeedsUpdate = true;
    }

    protected void renderPlant(final @NotNull Plant plant) {
        hudNeedsUpdate = false;

        final boolean showTimer;
        final boolean showYield;

        switch (plant.getType()) {
            case HEILKRAUTPFLANZE -> {
                showTimer = this.config.showTimerHeilkrautpflanze.get().equals(Boolean.TRUE) && plant.isActive();
                showYield = this.config.showYieldHeilkrautpflanze.get().equals(Boolean.TRUE) && plant.isActive();
            }
            case ROSE -> {
                showTimer = this.config.showTimerRose.get().equals(Boolean.TRUE) && plant.isActive();
                showYield = this.config.showYieldRose.get().equals(Boolean.TRUE) && plant.isActive();
            }
            default -> {
                showTimer = false;
                showYield = false;
            }
        }

        if (showTimer) {
            this.progressLine.updateAndFlush(I18n.getTranslation(
                    PROGRESS_TRANSLATABLE_VALUE,
                    plant.getCurrentTime(),
                    plant.getMaxTime()
            ));
        } else {
            this.progressLine.setState(TextLine.State.HIDDEN);
        }

        if (showYield) {
            this.yieldLine.updateAndFlush(I18n.getTranslation(
                    YIELD_TRANSLATABLE_VALUE,
                    plant.getValue(),
                    plant.getYieldUnit(),
                    plant.getType().getDisplayName()
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
            case HEILKRAUTPFLANZE -> updatePlant(PlantFactory.createPlant(
                    PlantType.HEILKRAUTPFLANZE,
                    paket.isActive(),
                    paket.getValue(),
                    paket.getCurrentTime(),
                    paket.getMaxTime(),
                    PlantType.HEILKRAUTPFLANZE.getYieldUnit()
            ));
            case ROSE -> updatePlant(PlantFactory.createPlant(
                    PlantType.ROSE,
                    paket.isActive(),
                    paket.getValue(),
                    paket.getCurrentTime(),
                    paket.getMaxTime(),
                    PlantType.ROSE.getYieldUnit()
            ));
        }

    }

    public static class PlantHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchSetting
        private final ConfigProperty<Boolean> showTimerHeilkrautpflanze = new ConfigProperty<>(true);

        @SwitchSetting
        private final ConfigProperty<Boolean> showYieldHeilkrautpflanze = new ConfigProperty<>(true);

        @SwitchSetting
        private final ConfigProperty<Boolean> showTimerRose = new ConfigProperty<>(true);

        @SwitchSetting
        private final ConfigProperty<Boolean> showYieldRose = new ConfigProperty<>(true);

    }

}
