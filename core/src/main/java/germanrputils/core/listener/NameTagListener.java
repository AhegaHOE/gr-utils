package germanrputils.core.listener;

import com.llamalad7.mixinextras.lib.apache.commons.ObjectUtils.Null;
import germanrputils.core.Enum.NameTagEnums;
import germanrputils.core.GRUtilsAddon;
import germanrputils.core.GRUtilsConfiguration;
import germanrputils.core.listener.OnServerConnect;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
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
    if(NetworkPlayerInfo != null) {
      String playerName = NetworkPlayerInfo.profile().getUsername();
      ScoreboardTeam team = NetworkPlayerInfo.getTeam();
      if (team == null) {
        return;
      }
      String prefix = team.getPrefix().toString().replace("literal{", "").replace("}", "");
      boolean gr = prefix.contains("[GR]");
      if (prefix.equals("§c") || prefix.equals("§8")) {
        return;
      }
      if (this.addon.OnServerConnect().getMemberList() != null) {
        List<String> memberlist = this.addon.OnServerConnect().getMemberList();
        NameTagEnums factiontag = this.addon.configuration().NameTagSubConfig().factiontag().get();
        if (memberlist.contains(playerName) && factiontag != NameTagEnums.NONE) {
          String var17 = this.enumToColor(factiontag.toString());
          prefix = var17 + (gr ? "[GR]" : "");
          event.setNameTag(Component.text(prefix + playerName));
          return;
        }
      }
      if (this.addon.OnServerConnect().getBountyList() != null) {
        List<String> bountylist = this.addon.OnServerConnect().getBountyList();
        NameTagEnums bountytag = this.addon.configuration().NameTagSubConfig().bountytag().get();
        if (bountylist.contains(playerName) && bountytag != NameTagEnums.NONE) {
          String var17 = this.enumToColor(bountytag.toString());
          prefix = var17 + (gr ? "[GR]" : "");
          event.setNameTag(Component.text(prefix + playerName));
          return;
        }
      }
      if (this.addon.OnServerConnect().getDarklistList() != null) {
        List<String> darklist = this.addon.OnServerConnect().getDarklistList();
        NameTagEnums darklisttag = this.addon.configuration().NameTagSubConfig().darklisttag().get();
        if (darklist.contains(playerName) && darklisttag != NameTagEnums.NONE) {
          String var17 = this.enumToColor(darklisttag.toString());
          prefix = var17 + (gr ? "[GR]" : "");
          event.setNameTag(Component.text(prefix + playerName));
        }
      }

    }
  }
  private String enumToColor(String color) {
    String var10000;
    switch (color) {
      case "GRAY" -> var10000 = "§7";
      case "DARKGRAY" -> var10000 = "§8";
      case "BLACK" -> var10000 = "§0";
      case "GREEN" -> var10000 = "§a";
      case "DARKGREEN" -> var10000 = "§2";
      case "LIGHTBLUE" -> var10000 = "§b";
      case "BLUE" -> var10000 = "§9";
      case "DARKBLUE" -> var10000 = "§1";
      case "CYAN" -> var10000 = "§3";
      case "YELLOW" -> var10000 = "§e";
      case "ORANGE" -> var10000 = "§6";
      case "PINK" -> var10000 = "§d";
      case "PURPLE" -> var10000 = "§5";
      default -> var10000 = "§f";
    }
    return var10000;
  }
}
