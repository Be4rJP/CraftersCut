package be4rjp.crafterscut.impl;

import be4rjp.crafterscut.api.CCPlayer;
import org.bukkit.entity.Player;

public class ImplCCPlayer extends CCPlayer {
    
    public static CCPlayer onJoin(Player player){return playerMap.computeIfAbsent(player, ImplCCPlayer::new);}
    
    public static void onQuit(Player player){playerMap.remove(player);}
    
    public ImplCCPlayer(Player player) {
        super(player);
    }
    
}
