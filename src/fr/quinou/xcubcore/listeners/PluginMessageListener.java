package fr.quinou.xcubcore.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.quinou.xcubcore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PluginMessageListener {


    private Connection connection;

    private Main main;
    public PluginMessageListener(Main main) {
        this.main = main;
    }




    public void GetServer(Player p){
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        System.out.println("output avant!");
        output.writeUTF("GetServer");
        System.out.println("output aprs!");

        p.sendPluginMessage(this.main, "BungeeCord", output.toByteArray());
        System.out.println("output aprs2!");
    }
}
