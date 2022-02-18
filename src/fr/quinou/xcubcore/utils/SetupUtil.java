package fr.quinou.xcubcore.utils;

import fr.quinou.xcubcore.Main;
import fr.quinou.xcubcore.commands.MoneySystem.GetMoney;
import fr.quinou.xcubcore.commands.MoneySystem.GiveMoney;
import fr.quinou.xcubcore.commands.MoneySystem.PayCommand;
import fr.quinou.xcubcore.commands.MoneySystem.SetMoney;
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
        main.getCommand("getcoin").setExecutor(new GetMoney(main));
        main.getCommand("givemoney").setExecutor(new GiveMoney(main));
        main.getCommand("setmoney").setExecutor(new SetMoney(main));
        main.getCommand("pay").setExecutor(new PayCommand(main));
    }

    public PluginManager getPluginManager() { return this.main.getServer().getPluginManager(); }
}
