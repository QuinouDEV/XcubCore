package fr.quinou.xcubcore.utils;

import fr.quinou.xcubcore.Main;
import fr.quinou.xcubcore.listeners.PluginMessageListener;
import fr.quinou.xcubcore.events.onPlayerJoin;
import org.bukkit.plugin.PluginManager;

public class SetupUtil
{
    private final Main main;
    public SetupUtil(Main main)
    {
        this.main = main;
    }
    public void mainEnablePluginFunction()
    {
        main.getSqlManager().connection();
        main.getServer().getMessenger().registerOutgoingPluginChannel(main, "BungeeCord");
        main.getServer().getMessenger().registerIncomingPluginChannel(main, "BungeeCord", new PluginMessageListener());
        enableListener();
    }
    public void mainDisablePluginFunction()
    {
        main.getSqlManager().disconnect();
    }
    public void enableListener()
    {
        PluginManager pm = getPluginManager();
        pm.registerEvents(new onPlayerJoin(main), main);

    }
    public PluginManager getPluginManager()
    {
        PluginManager pm = main.getServer().getPluginManager();

        return pm;
    }
}
