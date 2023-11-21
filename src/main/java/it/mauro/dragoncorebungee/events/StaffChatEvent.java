package it.mauro.dragoncorebungee.events;

import it.mauro.dragoncorebungee.DragonCoreBungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class StaffChatEvent implements Listener {

    private final DragonCoreBungee pl;

    public StaffChatEvent(DragonCoreBungee pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onStaffConnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (player.hasPermission("dragoncore.staffchat")) {
            String message = ChatColor.RED + "[STAFF] " +ChatColor.GRAY + player.getName() +ChatColor.GRAY +"("+event.getTarget().getName()+")"+ChatColor.DARK_GRAY + " » " +ChatColor.GRAY + event.getTarget().getName();

            pl.getProxy().getPlayers().stream()
                    .filter(staff -> staff.hasPermission("dragoncore.staffchat"))
                    .forEach(staff -> staff.sendMessage(message));

            pl.getLogger().info(message);
        }
    }
    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (player.hasPermission("dragoncore.staffchat")) {
            String joinMessage = ChatColor.RED + "[STAFF] " +ChatColor.DARK_GREEN+"+ " +ChatColor.GRAY + player.getName();

            // Broadcast the message to staff
            pl.getProxy().getPlayers().stream()
                    .filter(staff -> staff.hasPermission("dragoncore.staffchat"))
                    .forEach(staff -> staff.sendMessage(joinMessage));

            // Log the message to console
            pl.getLogger().info(joinMessage);
        }
    }

}
