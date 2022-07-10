package de.hyper.worlds.domain.using;

import de.hyper.worlds.common.enums.CategoryType;
import de.hyper.worlds.common.enums.GeneratorType;
import de.hyper.worlds.common.obj.world.ServerWorld;
import de.hyper.worlds.common.obj.world.role.RoleAdmission;
import de.hyper.worlds.common.obj.world.role.WorldRole;
import de.hyper.worlds.common.obj.world.setting.WorldSetting;
import de.hyper.worlds.common.obj.world.setting.settings.*;
import de.hyper.worlds.domain.WorldManagement;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadHelper {

    private final List<WorldRole> defaultRoles;

    public LoadHelper() {
        this.defaultRoles = new ArrayList<>();

        initDefaultRoles();
    }

    public ArrayList<WorldSetting> getDefaultWorldSettings() {
        ArrayList<WorldSetting> list = new ArrayList<>();
        list.add(new TimeSetting());
        list.add(new WeatherSetting());
        list.add(new BlockPhysicsSetting());
        list.add(new LeafDecaySetting());
        list.add(new RandomTickSpeedSetting());
        list.add(new RedstoneSetting());
        list.add(new SizeSetting());
        list.add(new UnloadingSetting());
        list.add(new ExplosionSetting());
        list.add(new PotionSetting());
        list.add(new HungerSetting());
        list.add(new MobDropsSetting());
        list.add(new MobSpawnSetting());
        list.add(new BlockSpreadSetting());
        list.add(new BlockFormSetting());
        list.add(new EntityBlockFormSetting());
        list.add(new BlockFromToSetting());
        list.add(new BlockGrowSetting());
        list.add(new BlockFertilizeSetting());
        list.add(new BlockBurnSetting());
        list.add(new GameModeSetting());
        return list;
    }

    public ServerWorld createNewServerWorld(String name, Player player, GeneratorType generatorType, boolean ignoreGeneration) {
        ServerWorld serverWorld = new ServerWorld(createSaveUUID(), name, player.getUniqueId(), ignoreGeneration, -1, generatorType, CategoryType.OTHER);
        WorldManagement.get().getCacheSystem().addServerWorld(serverWorld);
        return serverWorld;
    }

    public List<WorldRole> cloneOfDefaultRoles() {
        return cloneOf(defaultRoles, WorldRole.class, new ArrayList<WorldRole>());
    }

    @SneakyThrows
    public <T extends Object, Cloneable> List<T> cloneOf(List<T> oldList, Class<? extends T> elementClass, List<T> newList) {
        for (T t : oldList) {
            Method cloneMethod = t.getClass().getMethod("clone");
            Object clone = cloneMethod.invoke(t);
            T castedClone = elementClass.cast(clone);
            newList.add(castedClone);
        }
        return newList;
    }

    public UUID createSaveUUID() {
        return UUID.nameUUIDFromBytes((System.currentTimeMillis() + UUID.randomUUID().toString()).getBytes(StandardCharsets.UTF_8));
    }

    public <T extends Object> List<T> toList(List<T> newList, T... t) {
        for (T a : t) {
            newList.add(a);
        }
        return newList;
    }

    public RoleAdmission admission(String display, String key, boolean allowed) {
        return new RoleAdmission(display, key, allowed);
    }

    public WorldRole getDefaultRole() {
        return new WorldRole(createSaveUUID(), "Visitor", toList(new ArrayList<RoleAdmission>(),
                admission("Enter World", "enter", true),
                admission("Use WorldEdit", "worldedit", false),
                admission("Place Blocks", "blocks.place", false),
                admission("Break Blocks", "blocks.break", false),
                admission("Harvest Blocks", "blocks.harvest", false),
                admission("Fish", "fish", false),
                admission("Drop Items", "item.drop", true),
                admission("PickUp Items", "item.pickup", true),
                admission("Interact General", "interact.general", false),
                admission("Interact At Entity", "interact.at.entity", false),
                admission("Add Roles", "roles.add", false),
                admission("Delete Roles", "roles.delete", false),
                admission("Edit Roles", "roles.edit", false),
                admission("Reset Roles", "roles.reset", false),
                admission("Add Role To Player", "users.addrole", false),
                admission("Remove Player", "users.remove", false),
                admission("Change Role Of User", "users.role.change", false),
                admission("Change Block-Physics Setting", "settings.blockphysics.change", false),
                admission("Change Leaf-Decay Setting", "settings.leafdecay.change", false),
                admission("Change Redstone Setting", "settings.redstone.change", false),
                admission("Change Time Setting", "settings.time.change", false),
                admission("Change Weather Setting", "settings.weather.change", false),
                admission("Change Explosion Setting", "settings.explosion.change", false),
                admission("Change Potion Setting", "settings.potion.change", false),
                admission("Change Hunger Setting", "settings.hunger.change", false),
                admission("Change Mob-Spawn Setting", "settings.mobspawn.change", false),
                admission("Change Block-Spread Setting", "settings.spreading.change", false),
                admission("Change Block-Form Setting", "settings.blockform.change", false),
                admission("Change Entity-Form-Block Setting", "settings.entityformblock.change", false),
                admission("Change Block-From-To Setting", "settings.blockfromto.change", false),
                admission("Change Block-Grow Setting", "settings.blockgrow.change", false),
                admission("Change Block-Fertilize Setting", "settings.blockfertilize.change", false),
                admission("Change Block-Burn Setting", "settings.blockburn.change", false),
                admission("Change GameMode Setting", "settings.gamemode.change", false),
                admission("Change Difficulty", "attributes.difficulty.change", false),
                admission("Edit History", "edithistory", false),
                admission("See World-Seed", "seeseed", false)));
    }
    public WorldRole cloneOfDefaultRole(String name) {
        WorldRole role = getDefaultRole();
        role.setUniqueID(createSaveUUID());
        role.setName(name);
        return role;
    }

    private void initDefaultRoles() {
        defaultRoles.add(new WorldRole(createSaveUUID(), "Member", toList(new ArrayList<RoleAdmission>(),
                admission("Enter World", "enter", true),
                admission("Use WorldEdit", "worldedit", false),
                admission("Place Blocks", "blocks.place", true),
                admission("Break Blocks", "blocks.break", true),
                admission("Harvest Blocks", "blocks.harvest", false),
                admission("Fish", "fish", false),
                admission("Drop Items", "item.drop", true),
                admission("PickUp Items", "item.pickup", true),
                admission("Interact General", "interact.general", false),
                admission("Interact At Entity", "interact.at.entity", false),
                admission("Add Roles", "roles.add", false),
                admission("Delete Roles", "roles.delete", false),
                admission("Edit Roles", "roles.edit", false),
                admission("Reset Roles", "roles.reset", false),
                admission("Add Role To Player", "users.addrole", false),
                admission("Remove Player", "users.remove", false),
                admission("Change Role Of User", "users.role.change", false),
                admission("Change Block-Physics Setting", "settings.blockphysics.change", false),
                admission("Change Leaf-Decay Setting", "settings.leafdecay.change", false),
                admission("Change Redstone Setting", "settings.redstone.change", false),
                admission("Change Time Setting", "settings.time.change", false),
                admission("Change Weather Setting", "settings.weather.change", false),
                admission("Change Explosion Setting", "settings.explosion.change", false),
                admission("Change Potion Setting", "settings.potion.change", false),
                admission("Change Hunger Setting", "settings.hunger.change", false),
                admission("Change Mob-Spawn Setting", "settings.mobspawn.change", false),
                admission("Change Block-Spread Setting", "settings.spreading.change", false),
                admission("Change Block-Form Setting", "settings.blockform.change", false),
                admission("Change Entity-Form-Block Setting", "settings.entityformblock.change", false),
                admission("Change Block-From-To Setting", "settings.blockfromto.change", false),
                admission("Change Block-Fertilize Setting", "settings.blockfertilize.change", false),
                admission("Change Block-Burn Setting", "settings.blockburn.change", false),
                admission("Change GameMode Setting", "settings.gamemode.change", false),
                admission("Change Difficulty", "attributes.difficulty.change", false),
                admission("Edit History", "edithistory", false),
                admission("See World-Seed", "seeseed", false))));

        defaultRoles.add(new WorldRole(createSaveUUID(), "Admin", toList(new ArrayList<RoleAdmission>(),
                admission("Enter World", "enter", true),
                admission("Use WorldEdit", "worldedit", true),
                admission("Place Blocks", "blocks.place", true),
                admission("Break Blocks", "blocks.break", true),
                admission("Harvest Blocks", "blocks.harvest", true),
                admission("Fish", "fish", true),
                admission("Drop Items", "item.drop", true),
                admission("PickUp Items", "item.pickup", true),
                admission("Interact General", "interact.general", true),
                admission("Interact At Entity", "interact.at.entity", true),
                admission("Add Roles", "roles.add", false),
                admission("Delete Roles", "roles.delete", false),
                admission("Edit Roles", "roles.edit", true),
                admission("Reset Roles", "roles.reset", false),
                admission("Add Role To Player", "users.addrole", true),
                admission("Remove Player", "users.remove", true),
                admission("Change Role Of User", "users.role.change", true),
                admission("Change Block-Physics Setting", "settings.blockphysics.change", true),
                admission("Change Leaf-Decay Setting", "settings.leafdecay.change", true),
                admission("Change Redstone Setting", "settings.redstone.change", true),
                admission("Change Time Setting", "settings.time.change", true),
                admission("Change Weather Setting", "settings.weather.change", true),
                admission("Change Explosion Setting", "settings.explosion.change", true),
                admission("Change Potion Setting", "settings.potion.change", true),
                admission("Change Hunger Setting", "settings.hunger.change", true),
                admission("Change Mob-Spawn Setting", "settings.mobspawn.change", true),
                admission("Change Block-Spread Setting", "settings.spreading.change", true),
                admission("Change Block-Form Setting", "settings.blockform.change", true),
                admission("Change Entity-Form-Block Setting", "settings.entityformblock.change", true),
                admission("Change Block-From-To Setting", "settings.blockfromto.change", true),
                admission("Change Block-Fertilize Setting", "settings.blockfertilize.change", true),
                admission("Change Block-Burn Setting", "settings.blockburn.change", true),
                admission("Change GameMode Setting", "settings.gamemode.change", true),
                admission("Change Difficulty", "attributes.difficulty.change", true),
                admission("Edit History", "edithistory", true),
                admission("See World-Seed", "seeseed", false))));
    }
}