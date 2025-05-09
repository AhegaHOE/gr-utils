package germanrputils.core.widget;

import germanrputils.api.models.Plant;
import germanrputils.core.network.PlantPaket;

public interface PlantPaketReceiver {

  void onPaketReceive(final PlantPaket paket);

  void reset();

  void beginPlant(final Plant plant);

}
