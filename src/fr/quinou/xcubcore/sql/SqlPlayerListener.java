package fr.quinou.xcubcore.sql;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class SqlPlayerListener
{
    private Main main;


    public SqlPlayerListener(Main main)
    {
        this.main = main;
    }
    private Connection connection;

    public void createTable(){
        connection = main.getSqlManager().getConnection();
            PreparedStatement ps;
            try{
                ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players"
                + "(Pseudo VARCHAR(100), UUID VARCHAR(100), Position VARCHAR(100), Server VARCHAR(100), PRIMARY KEY (Pseudo))");
                ps.executeUpdate();

            } catch (SQLException e){
                e.printStackTrace();
        }

    }

    public void createPlayer(Player p){
        connection = main.getSqlManager().getConnection();
        if(main.sqlManager.isConnected()){
            try {
                UUID uuid = p.getUniqueId();
                if(!uuidExist(uuid)){
                    PreparedStatement ps2 = connection.prepareStatement("INSERT INTO players" +
                            "(Pseudo,UUID) VALUES (?,?)");
                    ps2.setString(1, p.getName());
                    ps2.setString(2, uuid.toString());
                    ps2.executeUpdate();
                    updatePosition(p);
                    getServer(p);



                }

            } catch (SQLException e){
                e.printStackTrace();
            }


        }


    }


    public int updatePosition(Player p){
        connection = main.getSqlManager().getConnection();
        if(main.sqlManager.isConnected()){
            try {
                UUID uuid = p.getUniqueId();
                PreparedStatement ps2 = connection.prepareStatement("UPDATE players SET Position=? WHERE UUID=?");
                Location pl = p.getLocation();
                Double x = pl.getX();
                Double y = pl.getY();
                Double z = pl.getZ();
                ps2.setString(1, "{x: " + x + "," + "y: " + y + "," + "z: " + z + "}");
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();


            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return 0;

    }

    public int setXcubCoin(){
        return 0;
    }


    public void getServer(Player p){
        sendPluginMessage("GetServer", p);
    }


    private void sendPluginMessage(String sub, Player p){
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(sub);

            if(p == null){
                p = Iterables.getFirst(Arrays.asList(Bukkit.getOnlinePlayers()), null);
            }

            p.sendPluginMessage(main, "BungeeCord", out.toByteArray());


    }




    public int getXcubCoin(){
        return 0;
    }


    public boolean uuidExist(UUID uuid){
        if(main.sqlManager.isConnected()){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM players WHERE UUID=?");
                ps.setString(1, uuid.toString());
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    return true;
                } return false;

            } catch (SQLException e){
                e.printStackTrace();
            }

        }
        return false;
    }
}
