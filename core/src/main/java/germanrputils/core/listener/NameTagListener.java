package germanrputils.core.listener;

import germanrputils.core.GRUtilsAddon;
import germanrputils.core.listener.OnServerConnect;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent.Context;
import java.util.List;


public class NameTagListener {

  private final GRUtilsAddon addon;

  public NameTagListener(GRUtilsAddon grUtilsAddon) {
    this.addon = grUtilsAddon;
  }

  @Subscribe
  public void onNameTag(PlayerNameTagRenderEvent event) {
    PlayerNameTagRenderEvent.Context context = event.context();
    NetworkPlayerInfo NetworkPlayerInfo = event.getPlayerInfo();
    if(NetworkPlayerInfo != null){
      String playerName = NetworkPlayerInfo.profile().getUsername();

      if (context.equals(Context.PLAYER_RENDER)) {
        if(this.addon.getOnServerConnect().getMemberList() != null){
          List<String> memberlist = this.addon.getOnServerConnect().getMemberList();
          if(memberlist.contains(playerName)){
            event.setNameTag(Component.text("§1" + playerName));
          }
        }
      }

    }

  }

}
