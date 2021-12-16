package fr.quinou.xcubcore.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.ChatColor;

import fr.quinou.xcubcore.Main;

public class SqlManager
{
    private String urlBase, host, database, user, password;
    private Connection connection;
    private Main main;

    public SqlManager(Main main, String urlBase, String host, String database, String user, String password)
    {
        this.main = main;
        this.urlBase = urlBase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;

    }

    public void connection()
    {
        if(!isConnected())
        {
            try
            {
                connection = DriverManager.getConnection(urlBase+host+"/"+database, user, password);
                main.getLogger().log(Level.INFO, ChatColor.GREEN+"Connexion SQL r��ussie !");
            }
            catch (SQLException e)
            {
                main.getLogger().log(Level.SEVERE, "Connnexion SQL ��chou��e !");
                e.printStackTrace();
            }
        }
    }
    public void disconnect()
    {
        if(isConnected())
        {
            try
            {
                connection.close();
                main.getLogger().log(Level.INFO, ChatColor.GREEN+"D��connexion SQL r��ussie !");

            }
            catch (SQLException e)
            {
                main.getLogger().log(Level.SEVERE, "D��connexion SQL ��chou��e !");
                e.printStackTrace();
            }
        }
    }
    public boolean isConnected()
    {
        return connection != null;
    }
    public Connection getConnection()
    {
        return connection;
    }
}
