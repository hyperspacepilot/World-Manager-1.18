package de.hyper.worlds.domain.inventories;

import de.hyper.inventory.InfinityInventory;
import de.hyper.inventory.Inventory;
import de.hyper.inventory.InventoryManager;
import de.hyper.inventory.buttons.Button;
import de.hyper.inventory.buttons.NoButton;
import de.hyper.inventory.buttons.OpenInventoryButton;
import de.hyper.inventory.designs.BottomLineBackGroundDesign;
import de.hyper.inventory.items.GlassPane;
import de.hyper.inventory.items.SimpleItemData;
import de.hyper.worlds.common.obj.ServerUser;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.util.TimeStampFormatting;
import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.SkullItemData;
import de.hyper.worlds.domain.WorldManagement;
import de.hyper.worlds.domain.using.Language;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryInventory extends InfinityInventory<List<CoreProtectAPI.ParseResult>> {

    protected ServerWorld serverWorld;
    protected ServerUser serverUser;
    Language lang = WorldManagement.get().getLanguage();
    InventoryManager invManager = WorldManagement.get().getInventoryManager();

    public UserHistoryInventory(Player player, ServerWorld serverWorld, ServerUser serverUser) {
        super(player, "History: " + serverUser.getName(), 6);
        this.setDesign(new BottomLineBackGroundDesign(this.getRows(), null, GlassPane.C7));
        this.serverWorld = serverWorld;
        this.serverUser = serverUser;
        this.list = combineParseResults(
                WorldManagement.get().getCoreProtectAPI().getBlockActionsFromUUIDInWorld(
                        serverUser.getUuid().toString(),
                        serverWorld.getWorldName()));
        this.maxPage = list.size() / 45;
        this.currentPage = 0;
    }

    @Override
    public Inventory fillInventory() {
        registerButtonAndItem(5, 1,
                new OpenInventoryButton(invManager, new HistoryInventory(player, serverWorld), player),
                new SimpleItemData(Material.IRON_DOOR)
                        .setDisplayName(
                                lang.getText("inventory.general.back"))
                        .addLore(
                                lang.getText("inventory.general.back.desc")));
        if (currentPage > 0) {
            registerLastPageButton(5, 0,
                    new SkullItemData(HDBSkulls.OAK_WOOD_ARROW_LEFT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.last")));
        }
        if (currentPage < maxPage) {
            registerNextPageButton(5, 8,
                    new SkullItemData(HDBSkulls.OAK_WOOD_ARROW_RIGHT)
                            .setDisplayName(
                                    lang.getText("inventory.general.pages.next")));
        }
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            int get = i + (currentPage * 45);
            if (get < list.size()) {
                List<net.coreprotect.CoreProtectAPI.ParseResult> parseResultList = list.get(get);
                registerButtonAndItem(row, slot, new Button() {
                    @Override
                    public void onClick(InventoryAction inventoryAction) {
                    }
                }, new SimpleItemData(parseResultList.get(0).getType())
                        .setAmount(parseResultList.size())
                        .setDisplayName("§b" + getAsText(parseResultList.get(0).getType()))
                        .addLore(
                                lang.getText(
                                        "inventory.playerhistory.parseresult.desc.1",
                                        getActionText(parseResultList.get(0).getActionString())),
                                (parseResultList.size() > 1 ?
                                        lang.getText(
                                                "inventory.playerhistory.parseresult.desc.3.1",
                                                TimeStampFormatting.getGermanDateAndTime(
                                                        parseResultList.get(parseResultList.size() - 1).getTimestamp()),
                                                TimeStampFormatting.getGermanDateAndTime(
                                                        parseResultList.get(0).getTimestamp()))
                                        : lang.getText(
                                        "inventory.playerhistory.parseresult.desc.3.2",
                                        TimeStampFormatting.getGermanDateAndTime(
                                                parseResultList.get(0).getTimestamp()))),
                                lang.getText(
                                        "inventory.playerhistory.parseresult.desc.4",
                                        (TimeStampFormatting.getDateDifference(
                                                parseResultList.get(parseResultList.size() - 1).getTimestamp(),
                                                System.currentTimeMillis(),
                                                "b"))),
                                " ",
                                lang.getText("inventory.playerhistory.parseresult.desc.info.1"),
                                lang.getText("inventory.playerhistory.parseresult.desc.info.2")));
                slot++;
                if (slot > 8) {
                    slot = 0;
                    row++;
                }
            } else break;
        }
        return this;
    }

    @Override
    public Inventory clearInventory() {
        int row = 0;
        int slot = 0;
        for (int i = 0; i != 45; i++) {
            registerButtonAndItem(row, slot, new NoButton(), null);
            slot++;
            if (slot > 8) {
                slot = 0;
                row++;
            }
        }
        registerButtonAndItem(5, 0, new NoButton(), GlassPane.C7);
        registerButtonAndItem(5, 8, new NoButton(), GlassPane.C7);
        return this;
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    public List<List<CoreProtectAPI.ParseResult>> combineParseResults(List<CoreProtectAPI.ParseResult> incomingList) {
        List<List<CoreProtectAPI.ParseResult>> outgoingList = new ArrayList<>();
        for (int i = 0; i < incomingList.size(); i++) {
            CoreProtectAPI.ParseResult parseResult = incomingList.get(i);
            List<CoreProtectAPI.ParseResult> subList = new ArrayList<>();
            subList.add(parseResult);
            int getNext = i + 1;
            int iterations = 0;
            boolean iterate = true;
            while (getNext < incomingList.size() && iterations < parseResult.getType().getMaxStackSize() && iterate) {
                CoreProtectAPI.ParseResult subResult = incomingList.get(getNext);
                boolean sameMaterialAndSameAction = parseResult.getType().equals(subResult.getType()) && parseResult.getActionId() == subResult.getActionId();
                if (sameMaterialAndSameAction) {
                    subList.add(subResult);
                    getNext++;
                } else {
                    iterate = false;
                    break;
                }
                iterations++;
            }
            outgoingList.add(subList);
            i = getNext - 1;
        }
        return outgoingList;
    }

    public String getAsText(Material material) {
        String[] strings = material.name().split("_");
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].toUpperCase().charAt(0) + strings[i].toLowerCase().substring(1, strings[i].length());
        }
        return String.join(" ", strings);
    }

    public String getActionText(String actionString) {
        return lang.getText("coreprotect.action." + actionString);
    }
}
