package it.mauro.dragoncorebungee.commands;

import it.mauro.dragoncorebungee.DragonCoreBungee;
import it.mauro.dragoncorebungee.api.Format;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class StaffChat extends Command {

    private final DragonCoreBungee pl;

    public StaffChat(DragonCoreBungee pl) {
        super("staffchat", "dragoncore.staffchat", "sc", "cs", "chatstaff");
        this.pl = pl;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Format.error("Usa: /staffchat <message>"));
            return;
        }

        String message = String.join(" ", args);

        pl.getProxy().getPlayers().stream()
                .filter(player -> player.hasPermission("dragoncore.staffchat"))
                .forEach(player -> player.sendMessage(ChatColor.RED + "[STAFF] " + ChatColor.GRAY + sender.getName() +ChatColor.DARK_GRAY + " » " +ChatColor.YELLOW + message));
    }
}

