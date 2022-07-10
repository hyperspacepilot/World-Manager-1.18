package de.hyper.worlds.domain.using;

import de.hyper.worlds.common.enums.FilterCategoryType;
import de.hyper.worlds.common.enums.FilterGeneratorType;
import de.hyper.worlds.common.enums.SortDirection;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.ServerWorldSettingChangedEvent;
import de.hyper.worlds.common.obj.world.WorldFilter;
import de.hyper.worlds.common.obj.world.history.HistoryDisplay;
import de.hyper.worlds.common.obj.world.history.HistoryRecord;
import de.hyper.worlds.common.obj.world.history.WorldHistory;
import de.hyper.worlds.common.obj.world.role.RoleAdmission;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import de.hyper.worlds.common.util.items.GlassPane;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.common.util.items.SkullBuilder;
import de.hyper.worlds.common.util.minventorry.ChooseMinventorry;
import de.hyper.worlds.common.util.minventorry.InventoryManager;
import de.hyper.worlds.common.util.minventorry.Minventorry;
import de.hyper.worlds.common.util.minventorry.buttons.*;
import de.hyper.worlds.common.util.minventorry.designs.BottomDesign;
import de.hyper.worlds.common.util.minventorry.designs.CornerDesign;
import de.hyper.worlds.common.util.minventorry.designs.SimpleDesign;
import de.hyper.worlds.common.util.minventorry.designs.TopandBottomDesign;
import de.hyper.worlds.domain.WorldManagement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Inventories {

    Language lang = WorldManagement.get().getLanguage();
    CacheSystem cache = WorldManagement.get().getCacheSystem();
    Performance performance = WorldManagement.getInstance().getPerformance();
    private final WorldManagement plugin;

    public Inventories(WorldManagement plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new InventoryManager(), this.plugin);
    }

    public void mainInventory(Player player, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("Worlds", 5) : minv;
            CornerDesign.middle(inv, GlassPane.C7, GlassPane.C15, null);
            inv.setSlot(3, 2, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    specificSortedServerWorlds(player, cache.getAllServerWorlds(), null, 0);
                }
            }, new ItemBuilder(Material.CAULDRON).setDisplayName(lang.getText("inventory.main.all.name")).setLore(lang.getText("inventory.main.all.desc.1")).getItem());
            inv.setSlot(3, 4, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    worldInventory(player, cache.getServerWorld(player.getWorld().getName()), null);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_DOWN).setDisplayName(lang.getText("inventory.main.current.name")).setLore(lang.getText("inventory.main.current.desc.1")).getItem());
            inv.setSlot(3, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    filterInventory(player, null, new WorldFilter());
                }
            }, new ItemBuilder(Material.SPYGLASS).setDisplayName(lang.getText("inventory.main.filter.name")).setLore(lang.getText("inventory.main.filter.desc.1")).getItem());
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void filterInventory(Player player, Minventorry minv, WorldFilter filter) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("Filter", 3) : minv;
            inv.setDesign(new TopandBottomDesign(GlassPane.C7, null));
            inv.setSlot(2, 1, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    ChooseMinventorry<FilterCategoryType> subInv = new ChooseMinventorry<FilterCategoryType>(4, FilterCategoryType.list()) {
                        @Override
                        public void selected(FilterCategoryType s) {
                            filter.setCategoryType(s);
                            filterInventory(player, null, filter);
                        }

                        @Override
                        public ItemStack buildItem(FilterCategoryType s) {
                            return new ItemBuilder(s.getItemStack(s.getCategoryType())).setDisplayName(lang.getText("inventory.filter.subinv.cat.each.name", s.getLabel())).setLore(lang.getText("inventory.filter.subinv.cat.each.desc.1", s.getLabel())).getItem();
                        }
                    };
                    subInv.buildInventory();
                    subInv.setPlayer(player);
                }
            }, new ItemBuilder(filter.getCategoryType().getItemStack(filter.getCategoryType().getCategoryType())).setDisplayName(lang.getText("inventory.filter.category.name")).setLore(lang.getText("inventory.filter.category.desc.1")).getItem());
            inv.setSlot(2, 3, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    ChooseMinventorry<FilterGeneratorType> subInv = new ChooseMinventorry<FilterGeneratorType>(3, FilterGeneratorType.list()) {
                        @Override
                        public void selected(FilterGeneratorType s) {
                            filter.setGeneratorType(s);
                            filterInventory(player, null, filter);
                        }

                        @Override
                        public ItemStack buildItem(FilterGeneratorType s) {
                            return new ItemBuilder(s.getItemStack()).setDisplayName(lang.getText("inventory.filter.subinv.gen.each.name", s.getLabel())).setLore(lang.getText("inventory.filter.subinv.gen.each.desc.1", s.getLabel())).getItem();
                        }
                    };
                    subInv.buildInventory();
                    subInv.setPlayer(player);
                }
            }, new ItemBuilder(filter.getGeneratorType().getItemStack()).setDisplayName(lang.getText("inventory.filter.generator.name")).setLore(lang.getText("inventory.filter.generator.desc.1")).getItem());
            inv.setSlot(2, 5, new FetchChatMessageButton(player) {
                @Override
                public void onReceiveMessage(String message) {
                    filter.setName(message.replace(" ", ""));
                    filterInventory(player, null, filter);
                }
            }, new ItemBuilder(Material.PAPER).setDisplayName(lang.getText("inventory.filter.name.name")).setLore(lang.getText("inventory.filter.name.desc.1"), lang.getText("inventory.filter.name.desc.2", filter.getName().replace("#", ""))).getItem());
            inv.setSlot(2, 7, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    player.closeInventory();
                    specificSortedServerWorlds(player, filter.filter(cache.getAllServerWorlds()), null, 0);
                }
            }, new ItemBuilder(HDBSkulls.GREEN_CHECKMARK).setDisplayName(lang.getText("inventory.filter.success.name")).setLore(lang.getText("inventory.filter.success.desc.1")).getItem());
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void specificSortedServerWorlds(Player player, List<ServerWorld> list, Minventorry minv, int page) {
        performance.async(() -> {
            double maxPage = (list.size() / 45);
            Minventorry inv = minv == null ? new Minventorry("Worlds: " + list.size(), 6) : minv;
            inv.setDesign(new BottomDesign(GlassPane.C7, null));
            inv.setSlot(6, 0, new LastPageButton(page) {
                @Override
                public void lastPage() {
                    specificSortedServerWorlds(player, list, minv, page - 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_LEFT).setDisplayName(lang.getText("inventory.general.pages.last")).getItem());
            inv.setSlot(6, 8, new NextPageButton(page, maxPage) {
                @Override
                public void nextPage() {
                    specificSortedServerWorlds(player, list, minv, page + 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_RIGHT).setDisplayName(lang.getText("inventory.general.pages.next")).getItem());

            inv.setSlot(6, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    mainInventory(player, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());
            int slot = 0;
            int row = 1;
            for (int b = 0; b != 45; b++) {
                inv.setSlot(row, slot, new NoButton(), null);
            }
            slot = 0;
            row = 1;
            for (int a = 0; a != 45; a++) {
                int get = a + (page * 45);
                if (get < list.size()) {
                    ServerWorld serverWorld = list.get(get);
                    if (serverWorld.isAllowedToSee(player)) {
                        inv.setSlot(row, slot, new Button() {
                            @Override
                            public void onClick(InventoryAction event) {
                                worldInventory(player, serverWorld, null);
                            }
                        }, new ItemBuilder(HDBSkulls.EARTH).setDisplayName("§b" + serverWorld.getWorldName()).setLore(lang.getText("inventory.listed.each.desc.1")).getItem());
                    } else {
                        inv.setSlot(row, slot, new NoButton(), new ItemBuilder(HDBSkulls.BARRIER).setDisplayName(
                                lang.getText("inventory.listed.notavailable.name")).setLore(lang.getText("inventory.listed.notavailable.desc.1")).getItem());
                    }
                    slot++;
                    if (slot > 8) {
                        slot = 0;
                        row++;
                    }
                } else
                    break;
            }
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void loadedWorlds(Player player, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("Worlds", 6) : minv;
            inv.setDesign(new TopandBottomDesign(GlassPane.C7, null));
            int slot = 0;
            int row = 2;
            for (World world : Bukkit.getWorlds()) {
                inv.setSlot(row, slot, new NoButton(), new ItemBuilder(HDBSkulls.EARTH).setDisplayName("§b" + world.getName()).setLore("§7Seed: §b" + world.getSeed(), "§7Players: §b" + world.getPlayers().size()).getItem());
                slot++;
                if (slot >= 8) {
                    slot = 0;
                    row++;
                }
            }
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void worldInventory(Player player, ServerWorld serverWorld, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("World: " + serverWorld.getWorldName(), 5) : minv;
            CornerDesign.middle(inv, GlassPane.C15, GlassPane.C7, null);
            inv.setSlot(3, 4, new NoButton(), new ItemBuilder(HDBSkulls.EARTH).setDisplayName("§b" + serverWorld.getWorldName()).getItem());
            inv.setSlot(1, 4, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    if (serverWorld.isAllowed(player, "enter")) {
                        performance.sync(() -> {
                            if (!WorldManagement.get().getCacheSystem().isLoadedWorld(serverWorld.getWorldName())) {
                                serverWorld.load();
                            }
                            player.teleport(serverWorld.getSpawnLocation());
                        });
                    }
                }
            }, new ItemBuilder(HDBSkulls.EYE_OF_ENDER).setDisplayName(lang.getText("inventory.world.item.teleport.name")).setLore(lang.getText("inventory.world.item.teleport.desc")).getItem());
            inv.setSlot(2, 2, new Button() {
                        @Override
                        public void onClick(InventoryAction event) {
                            attributeInventory(player, serverWorld, null);
                        }
                    },
                    new ItemBuilder(HDBSkulls.MONITOR_I).
                            setDisplayName(lang.getText("inventory.world.item.info.name")).
                            setLore(
                                    lang.getText("inventory.world.item.info.desc.1", serverWorld.getWorldName()),
                                    lang.getText("inventory.world.item.info.desc.2", cache.getServerUser(serverWorld.getOwnerUUID()).getName()),
                                    lang.getText("inventory.world.item.info.desc.3", ((serverWorld.isAllowed(player, "seeseed") ? serverWorld.getRealSeedOfBukkitWorld() : lang.getText("inventory.world.item.info.cannotseeseed")))),
                                    lang.getText("inventory.world.item.info.desc.4", lang.getText("inventory.attributes.difficulty.desc." + serverWorld.getDifficulty().getLKey())),
                                    lang.getText("inventory.world.item.info.desc.5", serverWorld.getGeneratorType().getName()),
                                    lang.getText("inventory.world.item.info.desc.6", serverWorld.getCategoryType().getLabel()), " ",
                                    lang.getText("inventory.world.item.info.desc.7")
                            ).getItem());
            inv.setSlot(4, 2, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    rolesInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(HDBSkulls.COMPUTER_1).setDisplayName(lang.getText("inventory.world.item.roles.name")).setLore(lang.getText("inventory.world.item.roles.desc")).getItem());
            inv.setSlot(4, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    settingsInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(HDBSkulls.COMPUTER_2).setDisplayName(lang.getText("inventory.world.settings.name")).setLore(lang.getText("inventory.world.settings.desc.1")).getItem());
            inv.setSlot(2, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    usersInventory(player, serverWorld, null, 0);
                }
            }, new ItemBuilder(HDBSkulls.MINER_NOTCH).setDisplayName(lang.getText("inventory.world.users.name")).setLore(lang.getText("inventory.world.users.desc.1")).getItem());
            inv.setSlot(5, 4, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    historyInventory(serverWorld, player, null, 0);
                }
            }, new ItemBuilder(HDBSkulls.CLOCK).setDisplayName(lang.getText("inventory.world.history.name")).setLore(lang.getText("inventory.world.history.desc.1")).getItem());

            if (minv == null) {
                inv.setPlayer(player);
            }
            performance.async(() -> {
                serverWorld.getHistory().cleanUp();
            });
        });
    }

    public void attributeInventory(Player player, ServerWorld serverWorld, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("Attribute", 3) : minv;
            inv.setDesign(new SimpleDesign(GlassPane.C7, GlassPane.C7));
            inv.setSlot(3, 0, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    worldInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());

            inv.setSlot(2, 2, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    if (serverWorld.isAllowed(player, "attributes.difficulty.change")) {
                        if (event.equals(InventoryAction.PICKUP_ALL)) {
                            serverWorld.setDifficulty(serverWorld.getDifficulty().next());
                        } else if (event.equals(InventoryAction.PICKUP_HALF)) {
                            serverWorld.setDifficulty(serverWorld.getDifficulty().last());
                        }
                        attributeInventory(player, serverWorld, minv);
                    }
                }
            }, serverWorld.getDifficulty().buildItemStack());
            inv.setSlot(2, 3, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    if (player.hasPermission("worldmanager.admin.bypass.category.change"))
                        if (event.equals(InventoryAction.PICKUP_ALL)) {
                            serverWorld.setCategoryType(serverWorld.getCategoryType().next());
                        } else if (event.equals(InventoryAction.PICKUP_HALF)) {
                            serverWorld.setCategoryType(serverWorld.getCategoryType().last());
                        }
                    attributeInventory(player, serverWorld, minv);
                }
            }, new ItemBuilder(serverWorld.getCategoryType().getItemStack()).setDisplayName("§b" + serverWorld.getCategoryType().getLabel()).setLore(lang.getText("inventory.attributes.category.desc.1")).getItem());
            inv.setSlot(2, 5, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    if (player.hasPermission("worldmanager.admin.bypass.generator.change")) {
                        if (event.equals(InventoryAction.PICKUP_ALL)) {
                            serverWorld.setGeneratorType(serverWorld.getGeneratorType().next());
                        } else if (event.equals(InventoryAction.PICKUP_HALF)) {
                            serverWorld.setGeneratorType(serverWorld.getGeneratorType().last());
                        }
                        attributeInventory(player, serverWorld, minv);
                    }
                }
            }, new ItemBuilder(HDBSkulls.GRASS_BLOCK).setDisplayName("§b" + serverWorld.getGeneratorType().getName()).setLore(lang.getText("inventory.attributes.generator.desc.1")).getItem());
            inv.setSlot(2, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    if (player.hasPermission("worldmanager.admin.bypass.ignoration.change")) {
                        serverWorld.setIgnoreGeneration(!serverWorld.isIgnoreGeneration());
                        attributeInventory(player, serverWorld, minv);
                    }
                }
            }, new ItemBuilder(Material.REDSTONE_TORCH).setDisplayName(lang.getText("inventory.attributes.ignoration.name")).setLore(lang.getText("inventory.attributes.ignoration.desc.1"), lang.getText("inventory.attributes.ignoration.desc.2", serverWorld.isIgnoreGeneration())).getItem());
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void settingsInventory(Player player, ServerWorld serverWorld, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("Settings", 6) : minv;
            inv.setDesign(new SimpleDesign(GlassPane.C7, GlassPane.C7));
            inv.setSlot(6, 0, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    worldInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());

            int slot = 1;
            int row = 2;
            for (WorldSetting setting : serverWorld.getSettings()) {
                inv.setSlot(row, slot, new Button() {
                    @Override
                    public void onClick(InventoryAction event) {
                        boolean allowed = false;
                        if (setting.isAdminSetting()) {
                            allowed = player.hasPermission("worldmanager.admin.adminsettings");
                        } else {
                            allowed = serverWorld.isAllowed(player, "settings." + setting.getName().replace("-", "").toLowerCase() + ".change");
                        }
                        if (allowed) {
                            setting.change(event);
                            performance.sync(() -> {
                                Bukkit.getPluginManager().callEvent(new ServerWorldSettingChangedEvent(serverWorld, player, serverWorld.getBukkitWorld(), setting.getType()));
                            });
                            settingsInventory(player, serverWorld, minv);
                        }
                    }
                }, setting.buildDisplayItem());
                slot++;
                if (slot == 4) {
                    slot++;
                }
                if (slot > 7) {
                    slot = 1;
                    row++;
                }
            }
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void usersInventory(Player player, ServerWorld serverWorld, Minventorry minv, int page) {
        performance.async(() -> {
            List<ServerUser> list = serverWorld.getMembers();
            double maxPage = (list.size() / 45);
            Minventorry inv = minv == null ? new Minventorry("Members", 6) : minv;
            inv.setDesign(new BottomDesign(GlassPane.C7, null));
            inv.setSlot(6, 0, new LastPageButton(page) {
                @Override
                public void lastPage() {
                    usersInventory(player, serverWorld, minv, page - 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_LEFT).setDisplayName(lang.getText("inventory.general.pages.last")).getItem());
            inv.setSlot(6, 8, new NextPageButton(page, maxPage) {
                @Override
                public void nextPage() {
                    usersInventory(player, serverWorld, minv, page + 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_RIGHT).setDisplayName(lang.getText("inventory.general.pages.next")).getItem());

            inv.setSlot(6, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    worldInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());

            int slot = 0;
            int row = 1;
            for (int b = 0; b != 45; b++) {
                inv.setSlot(row, slot, new NoButton(), null);
            }
            slot = 0;
            row = 1;
            for (int a = 0; a != 45; a++) {
                int get = a + (page * 45);
                if (get < list.size()) {
                    ServerUser user = list.get(get);
                    inv.setSlot(row, slot, new Button() {
                        @Override
                        public void onClick(InventoryAction event) {
                            userInventory(player, serverWorld, user, null);
                        }
                    }, new ItemBuilder(SkullBuilder.getSkullByPlayerName(user.getName())).setDisplayName("§b" + user.getName()).setLore("§7UUID: §b" + user.getUuid().toString(), lang.getText("inventory.members.each.desc.1", user.getWorldRole(serverWorld).getName()), lang.getText("inventory.members.each.desc.2"))
                            .getItem());
                    slot++;
                    if (slot > 8) {
                        slot = 0;
                        row++;
                    }
                } else
                    break;
            }

            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void userInventory(Player player, ServerWorld serverWorld, ServerUser serverUser, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("§9" + serverUser.getName(), 3) : minv;
            inv.setDesign(new TopandBottomDesign(GlassPane.C7, null));
            inv.setSlot(3, 0, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    usersInventory(player, serverWorld, null, 0);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());

            inv.setSlot(2, 2, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    if (serverWorld.isAllowed(player, "users.role.change")) {
                        changeRoleInventory(player, serverWorld, serverUser, null);
                    }
                }
            }, new ItemBuilder(HDBSkulls.CHANGE_IRON_BLUE).setDisplayName(lang.getText("inventory.user.change.name")).setLore(lang.getText("inventory.user.change.desc.1")).getItem());

            inv.setSlot(2, 4, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    roleInventory(player, serverWorld, serverUser.getWorldRole(serverWorld), null, 0);
                }
            }, serverUser.getWorldRole(serverWorld).buildDisplayItem());

            inv.setSlot(2, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    if (serverWorld.isAllowed(player, "users.remove")) {
                        serverUser.removeRole(serverWorld);
                        usersInventory(player, serverWorld, null, 0);
                    }
                }
            }, new ItemBuilder(HDBSkulls.RED_MINUS).setDisplayName(lang.getText("inventory.user.remove.name")).setLore(lang.getText("inventory.user.remove.desc.1")).getItem());
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void changeRoleInventory(Player player, ServerWorld serverWorld, ServerUser serverUser, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("Change Role", 5) : minv;
            inv.setDesign(new SimpleDesign(GlassPane.C7, null));
            inv.setSlot(5, 0, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    userInventory(player, serverWorld, serverUser, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());

            for (int r = 2; r < 5; r++) {
                inv.setSlot(r, 1, new NoButton(), GlassPane.C7);
            }
            inv.setSlot(3, 1, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    serverUser.put(serverWorld, serverWorld.getDefaultRole());
                    userInventory(player, serverWorld, serverUser, null);
                }
            }, serverWorld.getDefaultRole().buildDisplayItem());
            int slot = 2;
            int row = 2;
            for (WorldRole role : serverWorld.getRoles()) {
                inv.setSlot(row, slot, new Button() {
                    @Override
                    public void onClick(InventoryAction event) {
                        serverUser.put(serverWorld, role);
                        userInventory(player, serverWorld, serverUser, null);
                    }
                }, role.buildDisplayItem());
                slot++;
                if (slot > 7) {
                    slot = 2;
                    row++;
                }
            }
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void rolesInventory(Player player, ServerWorld serverWorld, Minventorry minv) {
        performance.async(() -> {
            Minventorry inv = minv == null ? new Minventorry("Roles", 5) : minv;
            inv.setDesign(new SimpleDesign(GlassPane.C7, null));
            inv.setSlot(5, 0, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    worldInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());
            for (int r = 2; r < 5; r++) {
                inv.setSlot(r, 1, new NoButton(), GlassPane.C7);
            }
            inv.setSlot(3, 1, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    roleInventory(player, serverWorld, serverWorld.getDefaultRole(), null, 0);
                }
            }, serverWorld.getDefaultRole().buildDisplayItem());
            int slot = 2;
            int row = 2;
            for (WorldRole role : serverWorld.getRoles()) {
                inv.setSlot(row, slot, new Button() {
                    @Override
                    public void onClick(InventoryAction event) {
                        roleInventory(player, serverWorld, role, null, 0);
                    }
                }, role.buildDisplayItem());
                slot++;
                if (slot > 7) {
                    slot = 2;
                    row++;
                }
            }
            if (serverWorld.isAllowed(player, "roles.add")) {
                inv.setSlot(5, 3, new FetchChatMessageButton(player) {
                    @Override
                    public void onReceiveMessage(String message) {
                        if (!serverWorld.existsRoleWithName(message.replaceAll(" &", "")))
                            serverWorld.addRole(WorldManagement.get().getLoadHelper().cloneOfDefaultRole(message.replaceAll(" &", "")));
                        rolesInventory(player, serverWorld, null);
                    }
                }, new ItemBuilder(HDBSkulls.GREEN_PLUS).setDisplayName(lang.getText("inventory.roles.addrole.name")).setLore(
                        lang.getText("inventory.roles.addrole.desc.1"),
                        lang.getText("inventory.roles.addrole.desc.2"),
                        lang.getText("inventory.roles.addrole.desc.3"),
                        lang.getText("inventory.roles.addrole.desc.4", WorldManagement.get().getMaxRoles()),
                        lang.getText("inventory.roles.addrole.desc.5")).getItem());
            }
            if (serverWorld.isAllowed(player, "roles.reset")) {
                inv.setSlot(5, 5, new Button() {
                    @Override
                    public void onClick(InventoryAction event) {
                        serverWorld.resetRoles();
                        rolesInventory(player, serverWorld, minv);
                    }
                }, new ItemBuilder(Material.LAVA_BUCKET).setDisplayName(lang.getText("inventory.roles.resetroles.name")).setLore(lang.getText("inventory.roles.resetroles.desc.1")).getItem());
            }
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    public void roleInventory(Player player, ServerWorld serverWorld, WorldRole role, Minventorry minv, int page) {
        performance.async(() -> {
            double maxPage = (role.getAdmissions().size() / 45);
            Minventorry inv = minv == null ? new Minventorry("Role " + role.getName(), 6) : minv;
            inv.setDesign(new BottomDesign(GlassPane.C7, null));
            inv.setSlot(6, 0, new LastPageButton(page) {
                @Override
                public void lastPage() {
                    roleInventory(player, serverWorld, role, minv, page - 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_LEFT).setDisplayName(lang.getText("inventory.general.pages.last")).getItem());
            inv.setSlot(6, 8, new NextPageButton(page, maxPage) {
                @Override
                public void nextPage() {
                    roleInventory(player, serverWorld, role, minv, page + 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_RIGHT).setDisplayName(lang.getText("inventory.general.pages.next")).getItem());

            inv.setSlot(6, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    rolesInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());
            if (serverWorld.isAllowed(player, "roles.delete")) {
                if (!role.getUniqueID().equals(serverWorld.getDefaultRole().getUniqueID())) {
                    inv.setSlot(6, 2, new Button() {
                        @Override
                        public void onClick(InventoryAction event) {
                            serverWorld.deleteRole(role);
                            rolesInventory(player, serverWorld, null);
                        }
                    }, new ItemBuilder(HDBSkulls.RED_MINUS).setDisplayName(lang.getText("inventory.role.delete.name")).setLore(lang.getText("inventory.role.delete.desc.1")).getItem());
                }
            }
            int slot = 0;
            int row = 1;
            for (int b = 0; b != 45; b++) {
                inv.setSlot(row, slot, new NoButton(), null);
            }
            slot = 0;
            row = 1;
            for (int a = 0; a != 45; a++) {
                int get = a + (page * 45);
                if (get < role.getAdmissions().size()) {
                    RoleAdmission admission = role.getAdmissions().get(get);
                    inv.setSlot(row, slot, new Button() {
                        @Override
                        public void onClick(InventoryAction event) {
                            if (serverWorld.isAllowed(player, "roles.edit")) {
                                admission.switchAllowed();
                                roleInventory(player, serverWorld, role, minv, page);
                            }
                        }
                    }, new ItemBuilder(admission.getMaterial()).setDisplayName("§b" + admission.getDisplay()).setLore("§7Key: §b" + admission.getKey(), lang.getText("inventory.role.each.desc.1", admission.isAllowed()), lang.getText("inventory.role.each.desc.2")).hideAttributes().getItem());
                    slot++;
                    if (slot > 8) {
                        slot = 0;
                        row++;
                    }
                } else
                    break;
            }
            if (minv == null)
                inv.setPlayer(player);
        });
    }

    private void historyInventory(ServerWorld serverWorld, Player player, Minventorry minv, int page) {
        performance.async(() -> {
            WorldHistory history = serverWorld.getHistory();
            List<HistoryRecord> list = history.sort(SortDirection.DOWN, null);
            double maxPage = (list.size() / 45);
            Minventorry inv = minv == null ? new Minventorry("History", 6) : minv;
            inv.setDesign(new BottomDesign(GlassPane.C7, null));
            inv.setSlot(6, 0, new LastPageButton(page) {
                @Override
                public void lastPage() {
                    historyInventory(serverWorld, player, minv, page - 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_LEFT).setDisplayName(lang.getText("inventory.general.pages.last")).getItem());
            inv.setSlot(6, 8, new NextPageButton(page, maxPage) {
                @Override
                public void nextPage() {
                    historyInventory(serverWorld, player, minv, page + 1);
                }
            }, new ItemBuilder(HDBSkulls.OAK_WOOD_ARROW_RIGHT).setDisplayName(lang.getText("inventory.general.pages.next")).getItem());

            inv.setSlot(6, 6, new Button() {
                @Override
                public void onClick(InventoryAction event) {
                    worldInventory(player, serverWorld, null);
                }
            }, new ItemBuilder(Material.IRON_DOOR).setDisplayName(lang.getText("inventory.general.back")).setLore(lang.getText("inventory.general.back.desc")).getItem());

            for (int b = 0; b != 45; b++) {
                inv.setSlot(b, new NoButton(), null);
            }
            int slot = 0;
            int row = 1;
            for (int a = 0; a != 45; a++) {
                int get = a + (page * 45);
                if (get < list.size()) {
                    HistoryRecord record = list.get(get);
                    HistoryDisplay display = record.getHistoryDisplay(serverWorld, record);
                    inv.setSlot(row, slot, new Button() {
                        @Override
                        public void onClick(InventoryAction event) {
                            if (serverWorld.isAllowed(player, "edithistory")) {
                                if (event.equals(InventoryAction.DROP_ONE_SLOT)) {
                                    record.undo(serverWorld);
                                    history.remove(record);
                                    historyInventory(serverWorld, player, inv, page);
                                } else if (event.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                                    performance.sync(() -> {
                                        player.closeInventory(InventoryCloseEvent.Reason.TELEPORT);
                                        player.teleport(record.getLocation().getLocation().add(0.5, 0.5, 0.5));
                                    });
                                }
                            }
                        }
                    }, new ItemBuilder(record.getRecordType().getHdbSkull()).setDisplayName(display.getTitle()).setLore(display.getLores()).getItem());
                    slot++;
                    if (slot > 8) {
                        slot = 0;
                        row++;
                    }
                } else
                    break;
            }
            if (minv == null)
                inv.setPlayer(player);
        });
    }
}