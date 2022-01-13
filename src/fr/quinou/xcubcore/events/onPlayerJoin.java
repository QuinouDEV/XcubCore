package fr.quinou.xcubcore.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;


public class onPlayerJoin implements Listener {
    private Main main;

    public onPlayerJoin(Main main) { this.main = main; }



    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if (!p.hasPlayedBefore()) {
            this.main.sqlPlayerListener.createPlayer(p);

        }
        else if (!this.main.sqlPlayerListener.uuidExist(uuid)) {
            this.main.sqlPlayerListener.createPlayer(p);
        } else {
            this.main.sqlPlayerListener.updatePosition(p);
        }
    }
}
