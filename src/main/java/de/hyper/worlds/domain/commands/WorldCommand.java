package de.hyper.worlds.domain.commands;

import de.hyper.worlds.common.enums.GeneratorType;
import de.hyper.worlds.common.obj.Duple;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.obj.world.sLocation;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.inventories.LoadedWorldsInventory;
import de.hyper.worlds.domain.inventories.MainInventory;
import de.hyper.worlds.domain.inventories.ServerWorldInventory;
import de.hyper.worlds.domain.using.Cache;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabExecutor {

    Language lang = WorldManagement.get().getLanguage();
    Cache cache = WorldManagement.get().getCache();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        WorldManagement.get().getPerformance().async(() -> {
            if (sender instanceof Player player) {
                String perm = "worldmanager.command.world";
                if (player.hasPermission(perm)) {
                    if (args.length == 0) {
                        MainInventory mainInventory = new MainInventory(player);
                        WorldManagement.get().getInventoryBuilder().buildInventory(mainInventory);
                        return;
                    }
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("info")) {
                            ServerWorldInventory serverWorldInventory = new ServerWorldInventory(player, cache.getServerWorld(player.getWorld()));
                            WorldManagement.get().getInventoryBuilder().buildInventory(serverWorldInventory);
                            return;
                        }
                        if (args[0].equalsIgnoreCase("help")) {
                            sendSyntax(player, label, null);
                            return;
                        }
                        if (args[0].equalsIgnoreCase("setspawn")) {
                            ServerWorld serverWorld = cache.getServerWorld(player.getWorld());
                            if (serverWorld != null) {
                                if (serverWorld.isAllowed(player, "setspawn")) {
                                    serverWorld.setSpawnLocation(new sLocation(player.getLocation()));
                                    serverWorld.getBukkitWorld().setSpawnLocation(player.getLocation());
                                    lang.send(player, "command.world.setspawn.success");
                                    return;
                                }
                            }
                            lang.send(player, "command.world.setspawn.failed");
                            return;
                        }
                        if (cache.existsServerWorld(args[0])) {
                            ServerWorldInventory serverWorldInventory = new ServerWorldInventory(player, cache.getServerWorld(args[0]));
                            WorldManagement.get().getInventoryBuilder().buildInventory(serverWorldInventory);
                            return;
                        }
                        sendSyntax(player, label, null);
                        return;
                    }
                    if (args.length >= 2) {
                        String subCMD = args[0];
                        if (subCMD.equalsIgnoreCase("admin")) {
                            if (args[1].equalsIgnoreCase("create") && args.length >= 3) {
                                perm = "worldmanager.command.world.create";
                                if (player.hasPermission(perm)) {
                                    String worldName = args[2];
                                    if (cache.existsServerWorld(worldName)) {
                                        teleport(player, Bukkit.getWorld(worldName).getSpawnLocation());
                                        lang.send(player, "command.world.alreadyexisting", worldName);
                                        return;
                                    }
                                    GeneratorType generatorType = GeneratorType.FLAT;
                                    if (args.length >= 4) {
                                        generatorType = GeneratorType.getFromString(args[3]);
                                    }
                                    boolean ignoreGeneration = false;
                                    if (args.length >= 5) {
                                        ignoreGeneration = Converter.getBoolean(args[4]);
                                    }
                                    long seed = -1;
                                    if (args.length >= 6) {
                                        seed = Converter.getLong(args[5]);
                                    }
                                    ServerWorld serverWorld = WorldManagement.get().getLoadHelper().createNewServerWorld(
                                            worldName,
                                            player,
                                            generatorType,
                                            ignoreGeneration, seed);
                                    Runnable run = new Thread(() -> {
                                        lang.send(player, "command.world.creatingworld", worldName);
                                        Duple<World, Long> result = serverWorld.load();
                                        TextComponent textComponent = new TextComponent(lang.getText("command.world.finishedcreating.teleport"));
                                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/world teleport " + worldName));
                                        player.spigot().sendMessage(new TextComponent(TextComponent.fromLegacyText(lang.getPrefix())), new TextComponent(TextComponent.fromLegacyText(lang.getText("command.world.finishedcreating", worldName, result.getValue2()))), textComponent);
                                    });
                                    //try {
                                    //    WorldManagement.get().getPerformance().async(run);
                                    //} catch (Exception e) {
                                    WorldManagement.get().getPerformance().sync(run);
                                    //}
                                } else {
                                    lang.send(player, "general.permission.lacking", perm);
                                }
                                return;
                            }
                            if (args[1].equalsIgnoreCase("loadedworlds")) {
                                LoadedWorldsInventory loadedWorldsInventory = new LoadedWorldsInventory(player);
                                WorldManagement.get().getInventoryBuilder().buildInventory(loadedWorldsInventory);
                                return;
                            }
                            if (args[1].equalsIgnoreCase("delete")) {
                                if (args.length == 3) {
                                    perm = "worldmanager.command.world.delete";
                                    if (player.hasPermission(perm)) {
                                        ServerWorld targetWorld = cache.getServerWorld(args[2]);
                                        if (targetWorld != null) {
                                            cache.remove(targetWorld);
                                        } else {
                                            lang.send(player, "general.worldnotregistered", args[2]);
                                        }
                                    } else {
                                        lang.send(player, "general.permission.lacking", perm);
                                    }
                                    return;
                                }
                            }
                            if (args[1].equalsIgnoreCase("rename")) {
                                perm = "worldmanager.command.world.rename";
                                if (player.hasPermission(perm)) {
                                    if (args.length == 4) {
                                        String oldName = args[2];
                                        String newName = args[3];
                                        ServerWorld serverWorld = cache.getServerWorld(oldName);
                                        if (serverWorld != null) {
                                            if (serverWorld.rename(newName)) {
                                                lang.send(player, "command.world.rename.success", oldName, newName);
                                            } else {
                                                lang.send(player, "command.world.rename.failed", oldName, newName);
                                            }
                                        } else {
                                            lang.send(player, "general.worldnotregistered", oldName);
                                        }
                                        return;
                                    }
                                } else {
                                    lang.send(player, "general.permission.lacking", perm);
                                }
                            }
                            if (args[1].equalsIgnoreCase("reload")) {
                                perm = "worldmanager.command.world.reload";
                                if (player.hasPermission(perm)) {
                                    long started = System.currentTimeMillis();
                                    WorldManagement.get().getConfiguration().load();
                                    WorldManagement.get().getLanguage().load(
                                            WorldManagement.get().getConfiguration()
                                                    .getData("language").getDataValueAsString());
                                    WorldManagement.get().getCache().save();
                                    lang.send(player, "command.world.reload.success", (System.currentTimeMillis() - started));
                                    return;
                                } else {
                                    lang.send(player, "general.permission.lacking", perm);
                                }
                            }
                            if (args[1].equalsIgnoreCase("save")) {
                                perm = "worldmanager.command.world.save";
                                if (player.hasPermission(perm)) {
                                    lang.send(player, "command.world.save.started");
                                    long current = System.currentTimeMillis();
                                    WorldManagement.get().getCache().save();
                                    WorldManagement.get().getLanguage().save();
                                    WorldManagement.get().getConfiguration().save();
                                    lang.send(player, "command.world.save.finished", (System.currentTimeMillis() - current));
                                } else {
                                    lang.send(player, "general.permission.lacking", perm);
                                }
                                return;
                            }
                            sendSyntax(player, label, args[0]);
                            return;
                        }
                        if (subCMD.equalsIgnoreCase("teleport")) {
                            String worldName = args[1];
                            World world = Bukkit.getWorld(worldName);
                            if (world != null) {
                                Location spawnLocation = world.getSpawnLocation();
                                if (cache.existsServerWorld(worldName)) {
                                    spawnLocation = cache.getServerWorld(world).getSpawnLocation();
                                }
                                teleport(player, spawnLocation);
                                return;
                            }
                            sendSyntax(player, label, args[0]);
                            return;
                        }
                        if (subCMD.equalsIgnoreCase("add")) {
                            if (args.length == 3) {
                                ServerUser user = cache.getServerUser(args[1]);
                                ServerWorld world = cache.getServerWorld(player.getWorld().getName());
                                if (world != null) {
                                    if (world.isAllowed(player, "users.addrole")) {
                                        if (world.existsRoleWithName(args[2])) {
                                            WorldRole role = world.getRole(args[2]);
                                            user.put(world, role);
                                            lang.send(player,
                                                    "command.world.add.success",
                                                    args[1],
                                                    world.getWorldName(),
                                                    role.getName());
                                        } else {
                                            lang.send(player, "command.world.add.noroleexisting", args[2]);
                                        }
                                    } else {
                                        lang.send(player, "command.world.add.notallowed", args[2]);
                                    }
                                } else {
                                    lang.send(player, "general.worldnotregistered", player.getWorld().getName());
                                }

                                return;
                            }
                        }
                        if (subCMD.equalsIgnoreCase("remove")) {
                            if (args.length == 2) {
                                ServerUser user = cache.getServerUser(args[1]);
                                ServerWorld world = cache.getServerWorld(player.getWorld().getName());
                                if (world != null) {
                                    if (world.isAllowed(player, "users.remove")) {
                                        if (world.getRoleOfHolder(user.getUuid()) != null) {
                                            user.removeRole(world);
                                            lang.send(player, "command.world.remove.success", args[1], world.getWorldName());
                                        } else {
                                            lang.send(player, "command.world.remove.nomemberofworld", args[1]);
                                        }
                                    } else {
                                        lang.send(player, "command.world.remove.notallowed", args[2]);
                                    }
                                } else {
                                    lang.send(player, "general.worldnotregistered", player.getWorld().getName());
                                }
                                return;
                            }
                        }
                        if (subCMD.equalsIgnoreCase("setowner")) {
                            if (args.length == 2) {
                                ServerUser user = cache.getServerUser(args[1]);
                                ServerWorld world = cache.getServerWorld(player.getWorld());
                                if (world != null) {
                                    if (world.isAllowed(player, "setowner")) {
                                        world.setOwnerUUID(user.getUuid());
                                        lang.send(player, "command.world.setowner.success", world.getWorldName(), user.getName());
                                    } else {
                                        lang.send(player, "command.world.setowner.notallowed");
                                    }
                                } else {
                                    lang.send(player, "general.worldnotregistered", player.getWorld().getName());
                                }
                                return;
                            }
                        }
                        sendSyntax(player, label, subCMD);
                    }
                } else {
                    lang.send(player, "general.permission.lacking", perm);
                }
            } else {
                lang.send(sender, "general.nouser");
            }
        });
        return false;
    }

    public void teleport(Player player, Location loc) {
        WorldManagement.get().getPerformance().sync(() -> {
            player.teleport(loc);
        });
    }

    private void sendSyntax(Player player, String label, String subCMD) {
        if (player.hasPermission("worldmanager.admin")) {
            player.sendMessage(Messages.SYNTAX(label, "admin create", "Create a world.", "name", "generator", "ignoreGeneration"));
            player.sendMessage(Messages.SYNTAX(label, "admin loadedworlds", "Shows all the worlds, which are currently loaded."));
            player.sendMessage(Messages.SYNTAX(label, "admin delete", "Removes a world from the system.", "worldname"));
            player.sendMessage(Messages.SYNTAX(label, "admin rename", "Saves the system.", "oldName", "newName"));
            player.sendMessage(Messages.SYNTAX(label, "admin reload", "Reloads the system."));
            player.sendMessage(Messages.SYNTAX(label, "admin save", "Saves the system."));
        }
        player.sendMessage(Messages.SYNTAX(label, "remove", "Remove a player from a world.", "player"));
        player.sendMessage(Messages.SYNTAX(label, "add", "Add a player to a world.", "player", "role"));
        player.sendMessage(Messages.SYNTAX(label, "setowner", "Set the new owner of the world.", "player"));
        player.sendMessage(Messages.SYNTAX(label, "info", "Opens the inventory of the world you're standing in."));
        player.sendMessage(Messages.SYNTAX(label, "setspawn", "Set the new spawnpoint of the current world."));
        player.sendMessage(Messages.SYNTAX(label, null, "Opens the inventory of specific world.", "world"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String complete = args[args.length - 1];
        ArrayList<String> completes = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("worldmanager.admin")) {
                list.add("admin");
            }
            list.add("help");
            list.add("info");
            list.add("add");
            list.add("remove");
            list.add("setowner");
            list.add("setspawn");
            for (String s : cache.getServerWorlds().keySet()) {
                list.add(s);
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                for (ServerUser user : cache.getServerUsers()) {
                    list.add(user.getName());
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("add")) {
                if (sender instanceof Player player) {
                    ServerWorld serverWorld = cache.getServerWorld(player.getWorld().getName());
                    if (serverWorld != null) {
                        for (WorldRole role : serverWorld.getRoles()) {
                            list.add(role.getName());
                        }
                    }
                }
            }
        }
        if (args.length >= 2 && args.length <= 6) {
            if (args[0].equalsIgnoreCase("admin")) {
                if (sender.hasPermission("worldmanager.admin")) {
                    if (args.length == 2) {
                        list.add("create");
                        list.add("loadedworlds");
                        list.add("delete");
                        list.add("rename");
                        list.add("reload");
                        list.add("save");
                    } else if (args.length == 3) {
                        switch (args[1].toLowerCase()) {
                            case "create":
                                if (sender.hasPermission("worldmanager.command.world.create")) {
                                    list.add("worldname");
                                }
                                break;
                            case "delete":
                                if (sender.hasPermission("worldmanager.command.world.delete")) {
                                    for (String s : cache.getServerWorlds().keySet()) {
                                        list.add(s);
                                    }
                                }
                                break;
                            case "rename":
                                if (sender.hasPermission("worldmanager.command.world.rename")) {
                                    for (String s : cache.getServerWorlds().keySet()) {
                                        list.add(s);
                                    }
                                }
                                break;
                        }
                    } else if (args.length == 4) {
                        switch (args[1].toLowerCase()) {
                            case "create":
                                if (sender.hasPermission("worldmanager.command.world.create")) {
                                    for (GeneratorType generatorType : GeneratorType.values()) {
                                        list.add(generatorType.name());
                                    }
                                }
                                break;
                            case "rename":
                                if (sender.hasPermission("worldmanager.command.world.rename")) {
                                    list.add("newName");
                                }
                                break;
                        }
                    } else if (args.length == 5) {
                        if (args[1].equalsIgnoreCase("create")) {
                            if (sender.hasPermission("worldmanager.command.world.create")) {
                                list.add("true");
                                list.add("false");
                            }
                        }
                    } else if (args.length == 6) {
                        if (args[1].equalsIgnoreCase("create")) {
                            if (sender.hasPermission("worldmanager.command.world.create")) {
                                list.add("seed");
                            }
                        }
                    }
                }
            }
        }
        for (String s : list) {
            if (s.toLowerCase().startsWith(complete.toLowerCase())) {
                completes.add(s);
            }
        }
        return completes;
    }
}