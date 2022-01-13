package fr.quinou.xcubcore.utils;

import fr.quinou.xcubcore.Main;
import fr.quinou.xcubcore.commands.CommandPD;
import fr.quinou.xcubcore.events.onPlayerJoin;
import org.bukkit.plugin.PluginManager;


public class SetupUtil
{
    private final Main main;

    public SetupUtil(Main main) { this.main = main; }

    public void mainEnablePluginFunction() {
        this.main.getSqlManager().connection();
        enableListener();
    }

    public void mainDisablePluginFunction() { this.main.getSqlManager().disconnect(); }

    public void enableListener() {
        PluginManager pm = getPluginManager();
        pm.registerEvents(new onPlayerJoin(this.main), this.main);
        this.main.getCommand("pm").setExecutor(new CommandPD(this.main));
    }

    public PluginManager getPluginManager() { return this.main.getServer().getPluginManager(); }
}
