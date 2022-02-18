package fr.quinou.xcubcore.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;


public class onPlayerJoin implements Listener {

    private Main main;

    public onPlayerJoin(Main main) {
        this.main = main;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if (!p.hasPlayedBefore() || !main.sqlPlayerListener.uuidExist(uuid)) {
            main.sqlPlayerListener.createPlayer(p);

        } else {
            main.sqlPlayerListener.updatePosition(p);
            main.sqlPlayerListener.updateServer(p);
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        main.sqlPlayerListener.updatePosition(p);
        main.sqlPlayerListener.updateServer(p);
    }

}

