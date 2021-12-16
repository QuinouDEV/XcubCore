package fr.quinou.xcubcore.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil
{
    public ItemStack getServerSelector()
    {
        ItemStack item = new ItemStack(Material.COMPASS, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("��6Mode de jeu");
        List<String> lore = new ArrayList<>();
        lore.add("��7Choisissez votre mode de jeu !");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
    public ItemStack getFactionButton()
    {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("��cPvP/Faction");
        List<String> lore = new ArrayList<>();
        lore.add("��7Ce mode de jeu est ��6modd����7.");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
    public void giveMenu(Player p)
    {
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(4, getServerSelector());
    }
}
