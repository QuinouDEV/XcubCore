package fr.quinou.xcubcore.sql;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class SqlPlayerListener
{
    private final String[] subs;
    private Main main;
    private Connection connection;

    public SqlPlayerListener(Main main) {
        this.main = main;
        this.subs = new String[] { "Connect", "ConnectOther", "IP", "PlayerCount", "PlayerList", "GetServer", "GetServers", "Message", "Forward", "ForwardToPlayer", "UUID", "UUIDOther", "ServerIP", "KickPlayer" };
    }



    public void createTable() {
        this.connection = this.main.getSqlManager().getConnection();

        try {
            PreparedStatement ps = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS players(Pseudo VARCHAR(100), UUID VARCHAR(100), Position VARCHAR(100), Server VARCHAR(100), XCoin INT(100) DEFAULT '0' NOT NULL, PRIMARY KEY (Pseudo))");

            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createPlayer(Player p) {
        this.connection = this.main.getSqlManager().getConnection();
        if (this.main.sqlManager.isConnected()) {
            try {
                UUID uuid = p.getUniqueId();
                if (!uuidExist(uuid)) {
                    PreparedStatement ps2 = this.connection.prepareStatement("INSERT INTO players(Pseudo,UUID) VALUES (?,?)");
                    ps2.setString(1, p.getName());
                    ps2.setString(2, uuid.toString());
                    ps2.executeUpdate();
                    updatePosition(p);
                    updateServer(p);

                }


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public int updatePosition(Player p) {
        this.connection = this.main.getSqlManager().getConnection();
        if (this.main.sqlManager.isConnected()) {
            try {
                UUID uuid = p.getUniqueId();
                PreparedStatement ps2 = this.connection.prepareStatement("UPDATE players SET Position=? WHERE UUID=?");
                Location pl = p.getLocation();
                Double x = Double.valueOf(pl.getX());
                Double y = Double.valueOf(pl.getY());
                Double z = Double.valueOf(pl.getZ());
                ps2.setString(1, "{x: " + x + "," + "y: " + y + "," + "z: " + z + "}");
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public boolean updateServer(final Player p){

        final String subChannel = "GetServer";

        if (isSubChannel(subChannel)) {
            new BukkitRunnable(){
                @Override
                public void run(){
                    sendPluginMessage(subChannel, p);
                }
            }.runTaskLater(main, 20*1);

        } else {
            p.sendMessage("Ce sous channel n'existe pas dans Bungee");
        }
        return false;
    }

    private void sendPluginMessage(String sub, Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(sub);

        if (p == null) {
            p = (Player)Iterables.getFirst(Arrays.asList(Bukkit.getOnlinePlayers()), null);
        }


        p.sendPluginMessage(this.main, "BungeeCord", out.toByteArray());
    }



    private boolean isSubChannel(String subChannel) {
        for (String sub : this.subs) {
            if (subChannel.equals(sub)) {
                return true;
            }
        }
        return false;
    }

    public int getXCoins(UUID uuid){

        try {
            connection = main.getSqlManager().getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement("SELECT XCoin FROM players WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();
            int coins;
            if (rs.next()){
                coins = rs.getInt("XCoin");
                return coins;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addXCoin(UUID uuid, int coins){

        try {
            connection = main.getSqlManager().getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement("UPDATE players SET XCoin=? WHERE UUID=?");
            int res = getXCoins(uuid) + coins;
            ps.setInt(1, res);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeXCoin(UUID uuid, int coins){

        try {
            connection = main.getSqlManager().getConnection();
            PreparedStatement ps;
            ps = connection.prepareStatement("UPDATE players SET XCoin=? WHERE UUID=?");
            int res = getXCoins(uuid) - coins;
            ps.setInt(1, res);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void setXCoins(UUID uuid, int coins){
        connection = main.getSqlManager().getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE players SET XCoin=? WHERE UUID=?");
            ps.setInt(1, coins);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public boolean uuidExist(UUID uuid) {
        if (this.main.sqlManager.isConnected()) {
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM players WHERE UUID=?");
                ps.setString(1, uuid.toString());
                ResultSet results = ps.executeQuery();
                if (results.next())
                    return true;
                return false;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
