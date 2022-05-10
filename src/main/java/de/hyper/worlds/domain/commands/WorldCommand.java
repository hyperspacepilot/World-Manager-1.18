package de.hyper.worlds.domain.commands;

import de.hyper.worlds.common.enums.GeneratorType;
import de.hyper.worlds.common.obj.Duett;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.ServerWorld;
import de.hyper.worlds.common.obj.WorldRole;
import de.hyper.worlds.common.util.Converter;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.CacheSystem;
import de.hyper.worlds.domain.using.Inventories;
import de.hyper.worlds.domain.using.Language;
import de.hyper.worlds.domain.using.Messages;
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
    Inventories invHelper = WorldManagement.get().getInventories();
    CacheSystem cache = WorldManagement.get().getCacheSystem();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        WorldManagement.get().getPerformance().async(() -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String perm = "worldmanager.command.world";
                if (player.hasPermission(perm)) {
                    if (args.length == 0) {
                        WorldManagement.get().getInventories().mainInventory(player, null);
                        return;
                    }
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("info")) {
                            invHelper.worldInventory(player, cache.getServerWorld(player.getWorld().getName()), null);
                            return;
                        }
                        if (args[0].equalsIgnoreCase("help")) {
                            sendSyntax(player, label, null);
                            return;
                        }
                        if (cache.existsServerWorld(args[0])) {
                            ServerWorld serverWorld = cache.getServerWorld(args[0]);
                            invHelper.worldInventory(player, serverWorld, null);
                            return;
                        }
                        if (args[0].equalsIgnoreCase("help")) {
                            sendSyntax(player, label, null);
                        }
                        sendSyntax(player, label, null);
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
                                    ServerWorld serverWorld = WorldManagement.get().getLoadHelper().createNewServerWorld(worldName, player, generatorType, ignoreGeneration);
                                    WorldManagement.get().getPerformance().sync(() -> {
                                        Duett<World, Long> result = serverWorld.load();
                                        lang.send(player, "command.world.createdworld", worldName, result.getValue2());
                                        teleport(player, serverWorld.getSpawnLocation());
                                    });
                                } else {
                                    lang.send(player, "general.permission.lacking", perm);
                                }
                                return;
                            }
                            if (args[1].equalsIgnoreCase("loadedworlds")) {
                                invHelper.loadedWorlds(player, null);
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
                                            lang.send(player, "command.world.add.success", args[1], world.getWorldName(), role.getName());
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
        }
        player.sendMessage(Messages.SYNTAX(label, "remove", "Remove a player from a world.", "player"));
        player.sendMessage(Messages.SYNTAX(label, "add", "Add a player to a world.", "player", "role"));
        player.sendMessage(Messages.SYNTAX(label, "setowner", "Set the new owner of the world.", "player"));
        player.sendMessage(Messages.SYNTAX(label, "info", "Opens the inventory of the world you're standing in."));
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
            list.add("info");
            list.add("help");
            for (String s : cache.getServerWorlds().keySet()) {
                list.add(s);
            }
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("admin")) {
                list.add("create");
                list.add("loadedworlds");
            }
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                for (ServerUser user : cache.getServerUsers()) {
                    list.add(user.getName());
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("create")) {
                if (sender.hasPermission("worldmanager.admin")) {
                    list.add("worldname");
                }
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("create")) {
                if (sender.hasPermission("worldmanager.admin")) {
                    for (GeneratorType generatorType : GeneratorType.values()) {
                        list.add(generatorType.name());
                    }
                }
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ServerWorld serverWorld = cache.getServerWorld(player.getWorld().getName());
                    if (serverWorld != null) {
                        for (WorldRole role : serverWorld.getRoles()) {
                            list.add(role.getName());
                        }
                    }
                }
            }
        }
        if (args.length == 5) {
            if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("create")) {
                if (sender.hasPermission("worldmanager.admin")) {
                    list.add("true");
                    list.add("false");
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