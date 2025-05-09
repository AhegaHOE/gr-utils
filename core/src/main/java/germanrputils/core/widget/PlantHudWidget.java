package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.models.PlantFactory;
import germanrputils.api.models.PlantType;
import germanrputils.core.network.PlantPaket;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget.HeilkrautpflanzeHudConfig;
import germanrputils.core.widget.RoseHudWidget.RoseHudWidgetConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine.State;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.NotNull;

public abstract class PlantHudWidget<T extends TextHudWidgetConfig>
    extends TextHudWidget<T>
    implements PlantPaketReceiver {

  private static final Component PROGRESS_KEY = Component.translatable(
      "germanrputils.widget.plant.progressKey");
  private static final Component YIELD_KEY = Component.translatable(
      "germanrputils.widget.plant.yieldKey");
  private static final String PROGRESS_TRANSLATABLE_VALUE = "germanrputils.widget.plant.progressValue";
  private static final String YIELD_TRANSLATABLE_VALUE = "germanrputils.widget.plant.yieldValue";

  private TextLine progressLine;
  private TextLine yieldLine;

  private Plant plant;

  protected PlantHudWidget(
      final String id,
      final HudWidgetCategory category,
      final Icon icon,
      final Class<T> configClass
  ) {
    super(id, configClass);
    this.bindCategory(category);
    this.setIcon(icon);
  }

  @Override
  public void load(final T config) {
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
      this.progressLine.setState(State.HIDDEN);
      this.yieldLine.setState(State.HIDDEN);
      return;
    }

    renderPlant(this.plant);
  }

  public abstract Plant getDummyPlant();

  public void reset() {
    this.plant = null;
  }

  public void updatePlant(final Plant plant) {
    this.plant = plant;
    this.progressLine.setState(State.VISIBLE);
    this.yieldLine.setState(State.VISIBLE);
  }

  protected void renderPlant(final @NotNull Plant plant) {

    final boolean showTimer;
    final boolean showYield;

    switch (plant.getType()) {
      case HEILKRAUTPFLANZE -> {
        final HeilkrautpflanzeHudConfig heilkrautpflanzeHudWidgetConfig = (HeilkrautpflanzeHudConfig) this.config;
        showTimer = heilkrautpflanzeHudWidgetConfig.showTimer().get().equals(Boolean.TRUE)
            && plant.isActive();
        showYield = heilkrautpflanzeHudWidgetConfig.showYield().get().equals(Boolean.TRUE)
            && plant.isActive();
      }

      case ROSE -> {
        final RoseHudWidgetConfig roseHudWidgetConfig = (RoseHudWidgetConfig) this.config;
        showTimer = roseHudWidgetConfig.showTimer().get().equals(Boolean.TRUE) && plant.isActive();
        showYield = roseHudWidgetConfig.showYield().get().equals(Boolean.TRUE) && plant.isActive();
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
      this.progressLine.setState(State.DISABLED);
    }

    if (showYield) {
      this.yieldLine.updateAndFlush(I18n.getTranslation(
          YIELD_TRANSLATABLE_VALUE,
          plant.getValue(),
          plant.getYieldUnit(),
          plant.getType().getDisplayName()
      ));
    } else {
      this.yieldLine.setState(State.DISABLED);
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

}
