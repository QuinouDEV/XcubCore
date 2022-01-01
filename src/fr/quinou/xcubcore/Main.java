package fr.quinou.xcubcore;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.listeners.PluginMessageListener;
import fr.quinou.xcubcore.sql.SqlInventoryListener;
import fr.quinou.xcubcore.sql.SqlPlayerListener;
import fr.quinou.xcubcore.utils.ItemUtil;
import fr.quinou.xcubcore.utils.SetupUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.quinou.xcubcore.sql.SqlManager;

public class Main extends JavaPlugin implements org.bukkit.plugin.messaging.PluginMessageListener {
    public SetupUtil setupUtil = new SetupUtil(this);
    public SqlManager sqlManager = new SqlManager(this, "jdbc:mysql://", "127.0.0.1", "xcub", "root", "");
    public SqlPlayerListener sqlPlayerListener = new SqlPlayerListener(this);
    public ItemUtil itemUtil = new ItemUtil();
    public SqlInventoryListener sqlInventoryListener = new SqlInventoryListener(this);
    public PluginMessageListener pluginMessageListener = new PluginMessageListener(this);


    public void onEnable()
    {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord",this);
        setupUtil.mainEnablePluginFunction();

        sqlPlayerListener.createTable();
        sqlInventoryListener.createTableI();
        getLogger().info("Plugin activé !");
    }



    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        System.out.println("Msg plugin recus!");
        p.sendMessage("t1est");
        if (!channel.equalsIgnoreCase("BungeeCord")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if(subChannel.equalsIgnoreCase("GetServer")) {
            System.out.println("Msg plugin en cours de sortie");
            String srv = in.readUTF();
            p.sendMessage("test" + srv);
        }


    }

    public void GetServer(Player p){
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        System.out.println("output avant!");
        output.writeUTF("GetServer");
        System.out.println("output aprs!");

        p.sendPluginMessage(this, "BungeeCord", output.toByteArray());
        System.out.println("output aprs2!");
    }

    @Override
    public void onLoad()
    {
        getLogger().info( "Plugin en cours de chargement...");
    }
    @Override
    public void onDisable()
    {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        setupUtil.mainDisablePluginFunction();
        getLogger().info( "Plugin désactivé !");
    }
    public SetupUtil getSetupUtil()
    {
        return setupUtil;
    }
    public SqlPlayerListener getSqlPlayerListener() {
        return sqlPlayerListener;
    }
    public SqlManager getSqlManager()
    {
        return sqlManager;
    }
    public ItemUtil getItemUtil()
    {
        return itemUtil;
    }

}
