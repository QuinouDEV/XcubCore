package fr.quinou.xcubcore.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
    private Connection connection;
    private Main main;

    public PluginMessageListener(Main main){
        this.main = main;
    }

    public void onPluginMessageReceived(String channel, Player p, byte[] bytes) {
        String uuido, playername, uuid, srvname, serverlist[], playerlist[];
        int playercount;

        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();

        String server = null;
        String ip = null;
        int port = 0;

        switch (subChannel) {
            case "IP":
                ip = in.readUTF();
                port = in.readInt();

                Bukkit.broadcastMessage("L'ip est " + ip + "le port est:" + port);
                return;
            case "PlayerCount":
                server = in.readUTF();
                playercount = in.readInt();

                Bukkit.broadcastMessage("Le Serveur est " + server + "son nombre de joueur est:" + playercount);
                return;
            case "PlayerList":
                server = in.readUTF();
                playerlist = in.readUTF().split(", ");

                Bukkit.broadcastMessage("Liste des joueurs du serveur: " + server);
                for (String player : playerlist) {
                    Bukkit.broadcastMessage(player);
                }
                return;
            case "GetServers":
                serverlist = in.readUTF().split(", ");

                Bukkit.broadcastMessage("Listes des serveurs: " + serverlist);
                for (String s : serverlist) {
                    Bukkit.broadcastMessage(s);
                }
                return;
            case "GetServer":
                srvname = in.readUTF();
                connection = main.getSqlManager().getConnection();
                if (this.main.sqlManager.isConnected()) {
                    try {
                        UUID uuid2 = p.getUniqueId();
                        PreparedStatement ps2 = this.connection.prepareStatement("UPDATE players SET Server=? WHERE UUID=?");
                        ps2.setString(1, srvname);
                        ps2.setString(2, uuid2.toString());
                        ps2.executeUpdate();

                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return;
            case "UUID":
                uuid = in.readUTF();

                Bukkit.broadcastMessage("UUid is  " + uuid);
                return;
            case "UUIDOther":
                playername = in.readUTF();
                uuido = in.readUTF();

                Bukkit.broadcastMessage("l'UUID de" + playername + "personne est  " + uuido);
                return;
            case "ServerIP":
                server = in.readUTF();
                ip = in.readUTF();
                port = in.readUnsignedShort();

                Bukkit.broadcastMessage("Le server demand� est:" + server + ip + port);
                return;
        }
        p.sendMessage("SOus channel inexistant");
    }
}
