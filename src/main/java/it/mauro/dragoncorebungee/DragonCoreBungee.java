package it.mauro.dragoncorebungee;

import it.mauro.dragoncorebungee.api.ConfigManager;
import it.mauro.dragoncorebungee.api.Format;
import it.mauro.dragoncorebungee.commands.Msg;
import it.mauro.dragoncorebungee.commands.StaffChat;
import it.mauro.dragoncorebungee.events.StaffChatEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

public final class DragonCoreBungee extends Plugin implements Listener {

    private List<String> blacklist;

    private ConfigManager configManager;


    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.getDefaultBlacklist();
        saveDefaultConfiguration();


        getProxy().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new StaffChat(this));
        getProxy().getPluginManager().registerListener(this, new StaffChatEvent(this));
        getProxy().getPluginManager().registerCommand(this, new Msg(this));
    }

    private void saveDefaultConfiguration() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");


        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void OnChat(ChatEvent e) {
        if(e.isCommand()) {
            return;
        }

        String message = e.getMessage();
        String lowerCaseMessage = message.toLowerCase();

        // Check if the message contains any blacklisted words

        TextComponent mess = new TextComponent(Format.msg("&c&lMUTA"));
        mess.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/mute {sender} Parole proibite in chat -s"
                .replace("{sender}", e.getSender().toString())));
        for (String word : blacklist) {
            if (lowerCaseMessage.contains(word)) {
                getProxy().getPlayers().stream()
                        .filter(player -> player.hasPermission("dragoncore.staffchat"))
                        .forEach(player -> player.sendMessage(ChatColor.RED + "[CHAT-FILTER] " + ChatColor.DARK_RED + e.getSender() +ChatColor.DARK_GRAY + " » " +ChatColor.RED + message));
                return;
            }
        }
    }

}
