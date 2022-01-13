package fr.quinou.xcubcore.sql;

import fr.quinou.xcubcore.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

















public class SqlPlayerListener
{
    private Main main;
    private Connection connection;

    public SqlPlayerListener(Main main) { this.main = main; }



    public void createTable() {
        this.connection = this.main.getSqlManager().getConnection();

        try {
            PreparedStatement ps = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS players(Pseudo VARCHAR(100), UUID VARCHAR(100), Position VARCHAR(100), Server VARCHAR(100), PRIMARY KEY (Pseudo))");

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
