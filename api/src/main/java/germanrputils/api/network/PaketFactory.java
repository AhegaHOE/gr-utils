package germanrputils.api.network;

import java.util.Optional;
import net.labymod.serverapi.api.payload.io.PayloadReader;

public final class PaketFactory {

  private PaketFactory() {
    throw new UnsupportedOperationException("This class should not be instantiated");
  }

  public static Optional<GRPaket> createPaket(byte[] payload) {
    final PayloadReader payloadReader = new PayloadReader(payload);
    final String header = payloadReader.readString();

    if (!header.startsWith("GRAddon-")) {
      return Optional.empty();
    }

    return switch (header) {

      case "GRAddon-Plant" -> Optional.of(new PlantPaket(payloadReader.readString()));

      default -> Optional.empty();

    };

  }

}
