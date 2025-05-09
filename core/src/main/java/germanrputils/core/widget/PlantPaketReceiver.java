package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.api.network.PlantPaket;

public interface PlantPaketReceiver {

  void onPaketReceive(final PlantPaket paket);

  void reset();

  void updatePlant(final Plant plant);

}
