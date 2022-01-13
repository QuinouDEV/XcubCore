package fr.quinou.xcubcore.commands;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPD implements CommandExecutor {
    private Main main;
    private final String[] subs;

    public CommandPD(Main main) {
        this.subs = new String[] { "Connect", "ConnectOther", "IP", "PlayerCount", "PlayerList", "GetServer", "GetServers", "Message", "Forward", "ForwardToPlayer", "UUID", "UUIDOther", "ServerIP", "KickPlayer" };





        this.main = main;
    }




    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        if (sender instanceof Player)
        {

            if (args.length >= 1) {
                String subChannel = args[0];

                if (isSubChannel(subChannel)) {
                    String[] arguments = (String[])Arrays.copyOfRange(args, 1, args.length);

                    sendPluginMessage(subChannel, p, arguments);
                } else {
                    sender.sendMessage("Ce sous channel n'existe pas dans Bungee");
                }

            }
            else {

                sender.sendMessage(ChatColor.RED + "La commande doit avoir des arguments");
            }
        }

        return false;
    }


    private void sendPluginMessage(String sub, Player p, String[] args) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(sub);

        for (String arg : args) {
            out.writeUTF(arg);
        }

        if (p == null) {
            p = (Player)Iterables.getFirst(Arrays.asList(Bukkit.getOnlinePlayers()), null);
        }


        p.sendPluginMessage(this.main, "BungeeCord", out.toByteArray());
    }



    private boolean isSubChannel(String subChannel) {
        for (String sub : this.subs) {
            if (subChannel.equals(sub)) {
                return true;
            }
        }
        return false;
    }
}
