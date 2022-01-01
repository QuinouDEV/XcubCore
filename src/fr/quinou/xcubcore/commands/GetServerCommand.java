package fr.quinou.xcubcore.commands;

import fr.quinou.xcubcore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetServerCommand implements CommandExecutor {

    private Main main;
    public GetServerCommand(Main main){
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            main.pluginMessageListener.GetServer(p);
        }
        return false;
    }
}
