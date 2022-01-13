package fr.quinou.xcubcore;

import fr.quinou.xcubcore.listeners.PluginMessageListener;
import fr.quinou.xcubcore.sql.SqlManager;
import fr.quinou.xcubcore.sql.SqlPlayerListener;
import fr.quinou.xcubcore.utils.ItemUtil;
import fr.quinou.xcubcore.utils.SetupUtil;
import java.sql.Connection;
import org.bukkit.plugin.java.JavaPlugin;


public class Main
        extends JavaPlugin
{
    public SetupUtil setupUtil = new SetupUtil(this);
    public SqlManager sqlManager = new SqlManager(this, "jdbc:mysql://", "127.0.0.1", "xcub", "root", "");
    public SqlPlayerListener sqlPlayerListener = new SqlPlayerListener(this);
    public ItemUtil itemUtil = new ItemUtil();

    private Connection connection;

    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListener());
        this.setupUtil.mainEnablePluginFunction();
        this.sqlPlayerListener.createTable();
        getLogger().info("Plugin activ� !");
    }



    public void onLoad() { getLogger().info("Plugin en cours de chargement..."); }



    public void onDisable() {
        this.setupUtil.mainDisablePluginFunction();
        getLogger().info("Plugin d�sactiv� !");
    }

    public SetupUtil getSetupUtil() { return this.setupUtil; }


    public SqlPlayerListener getSqlPlayerListener() { return this.sqlPlayerListener; }



    public SqlManager getSqlManager() { return this.sqlManager; }



    public ItemUtil getItemUtil() { return this.itemUtil; }
}
