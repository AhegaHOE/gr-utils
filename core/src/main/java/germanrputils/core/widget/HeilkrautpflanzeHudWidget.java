package germanrputils.core.widget;

import germanrputils.api.models.Heilkrautpflanze;
import germanrputils.api.models.Plant;
import germanrputils.core.network.PlantPaket;
import germanrputils.core.widget.HeilkrautpflanzeHudWidget.HeilkrautpflanzeHudWidgetConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine.State;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.NotNull;

public class HeilkrautpflanzeHudWidget extends TextHudWidget<HeilkrautpflanzeHudWidgetConfig> {

  private static final Heilkrautpflanze DUMMY_PLANT = new Heilkrautpflanze(
      true,
      20,
      5,
      15
  );

  private TextLine heilkrautpflanzeProgress;
  private TextLine heilkrautpflanzeYield;

  private Heilkrautpflanze plant;

  public HeilkrautpflanzeHudWidget(final HudWidgetCategory category) {
    super("heilkrautpflanze", HeilkrautpflanzeHudWidgetConfig.class);

    this.bindCategory(category);
    this.setIcon(
        Icon.texture(
            ResourceLocation.create("germanrputils", "images/heilkrautpflanze.png")
        )
    );
  }

  @Override
  public void load(final HeilkrautpflanzeHudWidgetConfig config) {
    super.load(config);

    final String i18nProgressValue = I18n.getTranslation(PROGRESS_TRANSLATABLE_VALUE, 0, 0);
    final String i18nYieldValue = I18n.getTranslation(YIELD_TRANSLATABLE_VALUE, 0, 0);

    this.heilkrautpflanzeProgress = this.createLine(PROGRESS_KEY, i18nProgressValue);
    this.heilkrautpflanzeYield = this.createLine(YIELD_KEY, i18nYieldValue);
  }

  @Override
  public void onTick(final boolean isEditorContext) {
    super.onTick(isEditorContext);

    if (isEditorContext) {
      renderPlant(DUMMY_PLANT);
      return;
    }

    if (this.plant == null) {
      return;
    }

    renderPlant(this.plant);
  }

  private void renderPlant(final @NotNull Plant plant) {
    final State desiredHudState = plant.isActive() ? State.VISIBLE : State.HIDDEN;
    this.heilkrautpflanzeProgress.setState(State.HIDDEN);
    this.heilkrautpflanzeYield.setState(State.HIDDEN);

    if (desiredHudState == State.HIDDEN) {
      return;
    }

    if (Boolean.TRUE.equals(this.config.showTimer.get())) {
      this.heilkrautpflanzeProgress.updateAndFlush(
          I18n.getTranslation(PROGRESS_TRANSLATABLE_VALUE, DUMMY_PLANT.getCurrentTime(),
              DUMMY_PLANT.getMaxTime()));
    } else {
      this.heilkrautpflanzeProgress.setState(State.HIDDEN);
    }

    if (Boolean.TRUE.equals(this.config.showYield.get())) {
      this.heilkrautpflanzeYield.updateAndFlush(
          I18n.getTranslation(YIELD_TRANSLATABLE_VALUE, DUMMY_PLANT.getValue(),
              DUMMY_PLANT.getType().getIngameName()));
    } else {
      this.heilkrautpflanzeYield.setState(State.HIDDEN);
    }

  }

  public void onPaketReceive(final @NotNull PlantPaket plantPaket) {
    plant = new Heilkrautpflanze(
        plantPaket.isActive(),
        plantPaket.getValue(),
        plantPaket.getCurrentTime(),
        plantPaket.getMaxTime()
    );
  }

  public static class HeilkrautpflanzeHudWidgetConfig extends TextHudWidgetConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> showTimer = new ConfigProperty<>(true);

    @SwitchSetting
    private final ConfigProperty<Boolean> showYield = new ConfigProperty<>(true);

  }

}
