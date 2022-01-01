package fr.quinou.xcubcore.sql;

import fr.quinou.xcubcore.Main;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SqlInventoryListener {
    private Connection connection;
    private Main main;
    public SqlInventoryListener(Main main){
        this.main = main;
    }

    public void createTableI(){

        connection = main.getSqlManager().getConnection();
        PreparedStatement ps;
        try{
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players_inventory"
                    + "(Pseudo VARCHAR(100), UUID VARCHAR(100), Inventory VARCHAR(255), PRIMARY KEY (Pseudo))");
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
                    PreparedStatement ps2 = connection.prepareStatement("INSERT INTO players_inventory" +
                            "(Pseudo,UUID,Inventory) VALUES (?,?,?)");
                    ps2.setString(1, p.getName());
                    ps2.setString(2, uuid.toString());
                    ps2.setString(3, updateInventory(p));
                    ps2.executeUpdate();




                }

            } catch (SQLException e){
                e.printStackTrace();
            }


        }


    }

    public String updateInventory(Player p) {
        if(main.sqlManager.isConnected()){
            try{
                PreparedStatement ps = connection.prepareStatement("UPDATE players_inventory SET Inventory=? WHERE UUID=?");
                UUID uuid = p.getUniqueId();
                ps.setString(1, p.getInventory().getContents().toString());
                ps.setString(2, uuid.toString());
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    return p.getInventory().getContents().toString();
    }


    public boolean uuidExist(UUID uuid){
        if(main.sqlManager.isConnected()){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM players_inventory WHERE UUID=?");
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

