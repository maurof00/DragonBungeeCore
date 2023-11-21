package it.mauro.dragoncorebungee.commands;

import it.mauro.dragoncorebungee.DragonCoreBungee;
import it.mauro.dragoncorebungee.api.Format;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Msg extends Command {

    private final DragonCoreBungee pl;

    public Msg(DragonCoreBungee pl) {
        super("msg", null);
        this.pl = pl;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("Comando per giocatori!"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;



        if (args.length < 2) {
            player.sendMessage(new TextComponent(Format.error("Usa: /msg <player> <message>")));
            return;
        }



        ProxiedPlayer target = pl.getProxy().getPlayer(args[0]);

        if(target == player) {
            player.sendMessage(Format.error("Non puoi inviare messaggi a te stesso!"));
            return;
        } else {
            String message = String.join(" ", args).substring(args[0].length() + 1);

            target.sendMessage(new TextComponent(Format.msg("&8[&7sender &8-> &7You&8] &7%message%")
                    .replace("sender", sender.getName())
                    .replaceAll("%message%", message)
            ));

            player.sendMessage(new TextComponent(Format.msg("&8[&7You &8-> &7target&8] &7%message%")
                    .replace("target", target.getName())
                    .replaceAll("%message%", message)
            ));
        }

        if (target == null) {
            player.sendMessage(new TextComponent(Format.error("Giocatore player non e' online!").replace("player", args[0])));
            return;
        }
    }
}
