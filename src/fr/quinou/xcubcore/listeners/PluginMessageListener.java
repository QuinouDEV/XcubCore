package fr.quinou.xcubcore.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {


    private Connection connection;



    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        System.out.println("Recieved message on channel " + channel + ", " + message.toString());
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }

        final ByteArrayDataInput in = ByteStreams.newDataInput(message);
        final String subChannel = in.readUTF();

        switch (subChannel){
            case "GetServer":
                String srv = in.readUTF();
                Bukkit.broadcastMessage(ChatColor.RED + srv);
                break;
            default:
                System.out.println("Sous-channel inexistant");
        }




            try {
                String subchannel = in.readUTF();
                if (subchannel.equals("GetServer")) {
                    String srv = in.readUTF();
                    UUID uuid = player.getUniqueId();
                    PreparedStatement ps = connection.prepareStatement("UPDATE players SET Server=? WHERE UUID=?");
                    ps.setString(1, srv);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
