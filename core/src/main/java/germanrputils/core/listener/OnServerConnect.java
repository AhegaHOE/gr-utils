package germanrputils.core.listener;

import germanrputils.core.GRUtilsAddon;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.util.GsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OnServerConnect {
  private static int emptyMessages = 0;
  private boolean justJoined = false;
  private boolean faction;
  private boolean bounty;
  public boolean isGR = false;
  private final List<String> memberList = new ArrayList<>();
  private final List<String> darklistList = new ArrayList<>();
  private final List<String> bountyList = new ArrayList<>();

  private static final Pattern DARKLIST_PATTERN = Pattern.compile(
      "^► \\[Darklist\\] - (\\w{3,16}|\\[GR\\]\\w{3,16})",
      Pattern.CANON_EQ
  );
  private static final Pattern Bounty_Member_Pattern = Pattern.compile(
      "^»     [►»] ((?:\\[GR\\\\])?\\w{3,16})",
      Pattern.CANON_EQ
  );



  public OnServerConnect(GRUtilsAddon addon) {
  }

  @Subscribe
  public void onServerJoin(ServerJoinEvent event) {
    String ip = String.valueOf(event.serverData().address());

    if (ip.toLowerCase().contains("germanrp.eu") || ip.contentEquals("91.218.66.124")) {
      this.isGR = true;
      this.justJoined =  true;
      Laby.references().chatExecutor().chat("/darklist");
      Laby.references().chatExecutor().chat("/members");
      Laby.references().chatExecutor().chat("/kopfgelder");
    }
  }
  @Subscribe
  public void onChatReceive(ChatReceiveEvent event) {
    if (this.isGR && this.justJoined) {
      String message = event.chatMessage().getPlainText();


      if (message.startsWith("► [Darklist] ")) {
        event.setCancelled(true);
        final Matcher matcher = DARKLIST_PATTERN.matcher(message);
        if (!matcher.find()) {
          return;
        }
        this.darklistList.add(matcher.group(1));
        return;
      }
      if (message.contentEquals("                 ► Fraktionsmember online ◄")) {
        event.setCancelled(true);
        this.faction = true;
        return;
      }
      if (this.faction || this.faction && message.isEmpty()){
        event.setCancelled(true);
        final Matcher matcher = Bounty_Member_Pattern.matcher(message);
        if(!matcher.find()) {
          if (message.startsWith("        (Insgesamt ") && message.endsWith(" verfügbar)")){
            this.faction = false;
            return;
          }
          return;
        }
        this.memberList.add(matcher.group(1));
        return;
      }
      if (message.contentEquals("            KOPFGELDER")) {
        event.setCancelled(true);
        this.bounty = true;
        return;
      }
      if (this.bounty) {
        event.setCancelled(true);
        final Matcher matcher = Bounty_Member_Pattern.matcher(message);
        if (!matcher.find()) {
          if (message.startsWith("        (Insgesamt ") && message.endsWith(" verfügbar)")) {
            this.bounty = false;
            return;
          }
          return;
        }
        this.bountyList.add(matcher.group(1));
      }
    }
  }
}