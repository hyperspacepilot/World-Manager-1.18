package de.hyper.worlds.domain.commands;

import de.hyper.worlds.common.enums.GeneratorType;
import de.hyper.worlds.common.obj.Duple;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author hyperspace_pilot
 */
public class WorldConsoleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender sender = (ConsoleCommandSender) commandSender;
            if (args.length == 5 || args.length == 6) {
                if (args[0].equalsIgnoreCase("createFor")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    String worldName = args[2];
                    GeneratorType generator = GeneratorType.getFromString(args[3]);
                    boolean ignoreGeneration = Converter.getBoolean(args[4]);
                    long seed = -1;
                    if (args.length == 6) {
                        seed = Converter.getLong(args[5]);
                    }
                    ServerWorld serverWorld = WorldManagement.get().getLoadHelper().createNewServerWorld(
                            worldName, player, generator,
                            ignoreGeneration, seed);
                    Runnable run = new Thread(() -> {
                        sender.sendMessage("World " + worldName + " will be created for the player " + player.getName());
                        Duple<World, Long> result = serverWorld.load();
                        sender.sendMessage("World were created in " + result.getValue2() + "ms");
                    });
                    WorldManagement.get().getPerformance().sync(run);
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("notify")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    String worldName = args[2];
                    Language lang = WorldManagement.get().getLanguage();
                    TextComponent textComponent = new TextComponent(lang.getText("command.world.finishedcreating.teleport"));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/world teleport " + worldName));
                    player.spigot().sendMessage(new TextComponent(TextComponent.fromLegacyText(lang.getPrefix())), new TextComponent(TextComponent.fromLegacyText(lang.getText("command.world.finishedcreating", worldName, 0))), textComponent);
                    sender.sendMessage("Notified " + player.getName() + " about " + worldName);
                }
            }
        }
        return false;
    }
}