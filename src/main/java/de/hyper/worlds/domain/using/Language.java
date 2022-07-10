package de.hyper.worlds.domain.using;

import de.hyper.worlds.domain.WorldManagement;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class Language {

    public static String PREFIX = "§b§lWorlds §8┃ §7";
    public static String ENGLISH = "english";
    public static String GERMAN = "german";

    public static String ERROR_MESSAGE = "Error in language-system, please contact an administrator!";

    private String prefix;
    private final String fallBackLanguage = Language.ENGLISH;
    private String activeLanguage;
    private HashMap<String, HashMap<String, String>> languagesAndKeysAndTexts;

    public Language(String language) {
        load(language);
        setup();
    }

    public void load(String language) {
        this.languagesAndKeysAndTexts = WorldManagement.get().getSaveSystem().getMessages();
        this.prefix = WorldManagement.get().getConfiguration().getData("prefix").getDataValueAsString();
        if (this.languagesAndKeysAndTexts == null) {
            this.languagesAndKeysAndTexts = new HashMap<>();
            this.languagesAndKeysAndTexts.put(ENGLISH, new HashMap<>());
            this.languagesAndKeysAndTexts.put(GERMAN, new HashMap<>());
        }
        if (!languagesAndKeysAndTexts.containsKey(language)) {
            this.activeLanguage = this.fallBackLanguage;
        }
        this.activeLanguage = language;
    }

    public void setup() {
        registerText("general.permission.lacking", "§7Dir fehlt die Permission§c {0}§7.", "§7You're lacking the permission§c {0}§7.");
        registerText("general.nouser", "§7Nur Spieler können diesen Command nutzen.", "§7Only players can execute that command.");
        registerText("general.boolean.true", "§aAktiv", "§aActive");
        registerText("general.boolean.false", "§cInaktiv", "§cInactive");
        registerText("general.worldnotregistered", "§7Es ist keine Welt namens §9{0} §7in dem System registriert.", "§7There is no world registered in the system called §9{0}§7.");


        registerText("command.world.createdworld", "§7Welt §9{0} §7erstellt und geladen, dauerte §9{1}ms§7.", "§7World §9{0} §7created and loaded, took §9{1}ms§7.");
        registerText("command.world.alreadyexisting", "§7Es gibt bereits eine Welt namens §9{0}§7.", "§7There's already a world called §9{0}§7.");
        registerText("command.world.add.notallowed", "§7Du hast in dieser Welt keinen Zugriff auf das Hinzufügen von Spielern.", "§7You don't have admission in this world to add players.");
        registerText("command.world.add.noroleexisting", "§7In dieser Welt gibt es keine Rolle namens §9{0}§7.", "§7There's no role in this world called §9{0}§7.");
        registerText("command.world.add.success", "§7Du hast §9{0} §7zur Welt §9{1} §7der Rolle §9{2} §7hinzugefügt.", "§7You've added §9{0} §7to the world §9{1} §7to the role §9{2}§7.");
        registerText("command.world.remove.notallowed", "§7Du hast in dieser Welt keinen Zugriff auf das Entfernen von Spielern.", "§7You don't have admission in this world to remove players.");
        registerText("command.world.remove.nomemberofworld", "§7In dieser Welt ist kein Spieler namens §9{0} §7registriert.", "§7There's no player called §9{0} §7registered in this world.");
        registerText("command.world.remove.success", "§7Du hast §9{0} §7aus der Welt §9{1} §7entfernt.", "§7You've removed §9{0} §7from the world called §9{1}§7.");
        registerText("command.world.setowner.success", "§7Du hast den Owner der Welt §9{0} §7auf §9{1} §7gesetzt.", "§7You've set the owner of the world §9{0} §7to §9{1}§7.");
        registerText("command.world.setowner.notallowed", "§7Du hast in dieser Welt keinen Zugriff auf das Setzen des Welten-Owners.", "§7You don't have admission in this world to set the world owner.");
        registerText("command.world.save.started", "System wird gespeichert...", "System is saving...");
        registerText("command.world.save.finished", "System wurde gespeichert. Dauer: §9{0}ms", "System saved. Took §9{0}ms");
        registerText("command.world.rename.success", "Du hast die Welt §9{0} §7zu §9{1} §7umbenannt.", "You've changed the name of the world §9{0} §7to §9{1}§7.");
        registerText("command.world.rename.failed", "Du kannst die Welt §9{0} §7nicht umbennen. Es dürfen keine Spieler in der Welt sein.", "You can't change the name of the world §9{0}§7. There have to be no players in it.");
        registerText("command.world.setspawn.success", "§7Der Spawn-Punkt der Welt wurde §aerfolgreich §7umgesetzt.", "§7The spawnpoint of the world was changed §asuccessfully§7.");
        registerText("command.world.setspawn.failed", "§7Du kannst in dieser Welt den Spawn-Punkt §cnicht §7umsetzen.", "§7You §ccan't §7change the spawnpoint of this world.");
        registerText("command.world.reload.success", "§7Das System wurde neugeladen. Dauer: §9{0}ms", "§7System reloaded. Took: §9{0}ms");

        registerText("inventory.world.item.info.cannotseeseed", "§cNicht sichtbar", "§cNot visible");

        registerText("inventory.general.pages.next", "§bNächste Seite", "§bNext page");
        registerText("inventory.general.pages.last", "§bVorherige Seite", "§bLast page");
        registerText("inventory.general.back", "§bVorheriges Menü", "§bLast menu");
        registerText("inventory.general.back.desc", "§7§oKlicke um zum vorherigen Menü zu kommen.", "§7§oClick to go to last menu.");
        registerText("inventory.general.fetchchatmessage", "§7Schicke jetzt deinen gewünschten Wert in den Chat.", "§7Send your wanted value inside the chat now.");

        registerText("inventory.main.all.name", "§bAlle Welten", "§bAll worlds");
        registerText("inventory.main.all.desc.1", "§7§oKlicke um alle Welten zu sehen.", "§7§oClick to see all worlds.");
        registerText("inventory.main.filter.name", "§bFilter", "§bFilter");
        registerText("inventory.main.filter.desc.1", "§7§oKlicke um nach Welten mit bestimmten Filtern zu suchen.", "§7§oClick to search worlds with specific filters.");
        registerText("inventory.main.current.name", "§bAktuelle Welt", "§bCurrent World");
        registerText("inventory.main.current.desc.1", "§7§oKlicke um das Menü für deine aktuelle Welt zu öffnen.", "§7§oClick to open menu for your current world.");

        registerText("inventory.filter.name.name", "§bNamensfilterung", "§bName filter");
        registerText("inventory.filter.name.desc.1", "§7§oKlicke um einen Filter nach Namen zu setzen.", "§7§oClick to set filter for name.");
        registerText("inventory.filter.name.desc.2", "§7§oAktueller Namensfilter: {0}", "§7§oCurrent name filter: {0}");
        registerText("inventory.filter.subinv.cat.each.name", "§b{0}", "§b{0}");
        registerText("inventory.filter.subinv.cat.each.desc.1", "§7§oKlicke um nach {0} zu filtern.", "§7§oClick to filter for {0}.");
        registerText("inventory.filter.category.name", "§bWähle eine Kategorie", "§bChoose a category");
        registerText("inventory.filter.category.desc.1", "§7§oKlicke um eine Kategorie zum Filtern auszuwählen.", "§7§oClick to select a category for filtering.");
        registerText("inventory.filter.subinv.gen.each.name", "§b{0}", "§b{0}");
        registerText("inventory.filter.subinv.gen.each.desc.1", "§7§oKlicke um nach {0} zu filtern.", "§7§oClick to filter for {0}.");
        registerText("inventory.filter.generator.name", "§bWähle einen Generator", "§bChoose a generator");
        registerText("inventory.filter.generator.desc.1", "§7§oKlicke um einen Generator zum Filtern auszuwählen.", "§7§oClick to select a generator for filtering.");
        registerText("inventory.filter.success.name", "§bFiltern", "§bFilter");
        registerText("inventory.filter.success.desc.1", "§7§oKlicke um alle Welten zu filtern.", "§7§oClick to filter through all worlds.");

        registerText("inventory.listed.each.desc.1", "§7§oKlicke um das Menü dieser Welt zu öffnen.", "§7§oClick to open the menu of this world.");
        registerText("inventory.listed.notavailable.name", "§4Nicht erreichbar", "§4Not available");
        registerText("inventory.listed.notavailable.desc.1", "§c§oDiese Welt ist nicht für dich erreichbar.", "§c§oThis world is not available for you.");

        registerText("inventory.items.worldrole.display", "§7§oKlicke um Rolle zu bearbeiten.", "§7§oClick to edit role.");

        registerText("inventory.world.item.teleport.name", "§bTeleportieren", "§bTeleport");
        registerText("inventory.world.item.teleport.desc", "§7§oKlicke um dich in die Welt zu teleportieren.", "§7§oClick to teleport into world.");
        registerText("inventory.world.item.roles.name", "§bRollen", "§bRoles");
        registerText("inventory.world.item.roles.desc", "§7§oKlicke um Rollen zu verwalten.", "§7§oClick to manage roles.");
        registerText("inventory.world.item.info.name", "§bInformationen", "§bInformation");
        registerText("inventory.world.item.info.desc.1", "§7Name: §b{0}", "§7Name: §b{0}");
        registerText("inventory.world.item.info.desc.2", "§7Besitzer: §b{0}", "§7Owner: §b{0}");
        registerText("inventory.world.item.info.desc.3", "§7Seed: §b{0}", "§7Seed: §b{0}");
        registerText("inventory.world.item.info.desc.4", "§7Schwierigkeit: §b{0}", "§7Difficulty: §b{0}");
        registerText("inventory.world.item.info.desc.5", "§7Generator: §b{0}", "§7Generator: §b{0}");
        registerText("inventory.world.item.info.desc.6", "§7Kategorie: §b{0}", "§7Category: §b{0}");
        registerText("inventory.world.item.info.desc.7", "§7§oKlicke um Werte zu bearbeiten.", "§7§oClick to edit attributes.");
        registerText("inventory.world.users.name", "§bMitglieder", "§bMembers");
        registerText("inventory.world.users.desc.1", "§7§oKlicke um die Mitglieder der Welt anzuzeigen.", "§7§oClick to show world's members.");
        registerText("inventory.world.settings.name", "§bEinstellungen", "§bSettings");
        registerText("inventory.world.settings.desc.1", "§7§oKlicke um die Einstellungen zu öffnen.", "§7§oClick to open settings menu.");
        registerText("inventory.world.history.name", "§bHistory", "§bHistory");
        registerText("inventory.world.history.desc.1", "§7§oKlicke um die History der Welt zu sehen.", "§7§oClick to view world history.");

        registerText("inventory.attributes.difficulty.name", "§bSchwierigkeit", "§bDifficulty");
        registerText("inventory.attributes.difficulty.desc.1", "§7§oStelle auf welcher Schwierigkeit die Welt sein soll.", "§7§oSet the difficulty the world shall have.");
        registerText("inventory.attributes.difficulty.desc.2", "§7§oNutze Linksklick um auf nächste Schwierigkeit zu schalten.", "§7§oUse leftclick fpr next difficulty.");
        registerText("inventory.attributes.difficulty.desc.3", "§7§oNutze Rechtsklick um auf vorherige Schwierigkeit zu schalten.", "§7§oUse rightclick for last difficulty.");
        registerText("inventory.attributes.difficulty.desc.peaceful", "§a§lFriedlich", "§a§lPeaceful");
        registerText("inventory.attributes.difficulty.desc.easy", "§9§lEinfach", "§9§lEasy");
        registerText("inventory.attributes.difficulty.desc.normal", "§5§lNormal", "§5§lNormal");
        registerText("inventory.attributes.difficulty.desc.hard", "§c§lSchwer", "§c§lHard");
        registerText("inventory.attributes.category.desc.1", "§7§oKlicke um die Kategorie zu ändern.", "§7§oKlick to change category.");
        registerText("inventory.attributes.generator.desc.1", "§7§oKlicke um den Generator zu ändern.", "§7§oKlick to change generator.");
        registerText("inventory.attributes.ignoration.name", "§bIgnoration", "§bIgnoration");
        registerText("inventory.attributes.ignoration.desc.1", "§7§oStelle ein ob die Welten-Generation ignoriert werden soll.", "§7§oSet if the world generation should be ignored.");
        registerText("inventory.attributes.ignoration.desc.2", "§7§oIgnoriert: {0}", "§7§oIgnoring: {0}");

        registerText("inventory.members.each.desc.1", "§7Rolle: §9{0}", "§7Role: §9{0}");
        registerText("inventory.members.each.desc.2", "§7§oKlicke um den Spieler zu bearbeiten.", "§7§oClick to edit user.");

        registerText("inventory.user.remove.name", "§bSpieler entfernen", "§bRemove user");
        registerText("inventory.user.remove.desc.1", "§7§oKlicke um den Spieler aus deiner Welt zu entfernen.", "§7Click to remove player from your world.");
        registerText("inventory.user.change.name", "§bRolle ändern", "§bChange Role");
        registerText("inventory.user.change.desc.1", "§7§oKlicke um die Rolle des Spielers zu ändern.", "§7§oClick to change player's role.");

        registerText("inventory.roles.addrole.name", "§bRolle hinzufügen", "§bAdd role");
        registerText("inventory.roles.addrole.desc.1", "§7§oKlicke um eine Rolle hinzuzufügen.", "§7§oClick to add role.");
        registerText("inventory.roles.addrole.desc.2", "§7§oSchreibe nach dem Klicken den Namen der Rolle in den Chat.", "§7§oWrite after clicking the name of the role.");
        registerText("inventory.roles.addrole.desc.3", "§7§oDie neue Rolle wird dieselben Zugriffe wie die Visitor/Default Gruppe haben.", "§7§oThe new role will have the same admissions like the visitor/default group.");
        registerText("inventory.roles.addrole.desc.4", "§c§oMaximal sind {0} Rollen möglich.", "§c§oThe limit for roles is {0}.");
        registerText("inventory.roles.addrole.desc.5", "§c§oRollen können nicht dieselben Namen haben.", "§c§oRoles can not have the same names.");
        registerText("inventory.roles.resetroles.name", "§bRollen zurücksetzen", "§bReset roles");
        registerText("inventory.roles.resetroles.desc.1", "§7§oKlicke um Rollen auf Standart zurückzusetzen.", "§7§oClick to reset roles to default.");

        registerText("inventory.role.each.desc.1", "§7Zugriff: {0}", "§7Admission: {0}");
        registerText("inventory.role.each.desc.2", "§7§oKlicke um Zugriff zu ändern.", "§7§oClick to change admission.");
        registerText("inventory.role.delete.name", "§bRolle löschen", "§bDelete role");
        registerText("inventory.role.delete.desc.1", "§7§oKlicke um Rolle zu löschen.", "§7§oClick to delete role.");


        registerText("settings.blockphysics.desc", "§7§oStelle ein ob Block-Physics aktiv sind.", "§7§oSet if Block-Physics are enabled.");
        registerText("settings.blockphysics.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.blockphysics.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.leafdecay.desc", "§7§oStelle ein ob Leaf-Decay aktiv ist.", "§7§oSet if Leaf-Decay is enabled.");
        registerText("settings.leafdecay.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.leafdecay.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.randomtickspeed.desc", "§7§oStelle die Geweschwindigkeit des RandomTickSpeeds ein.", "§7§oSet the speed of the randomtickspeed.");
        registerText("settings.randomtickspeed.stopped", "§a§lAngehalten", "§a§lStopped");
        registerText("settings.randomtickspeed.slow", "§c§lLangsam", "§c§lSlow");
        registerText("settings.randomtickspeed.normal", "§d§lNormal", "§d§lNormal");
        registerText("settings.randomtickspeed.fast", "§e§lSchnell", "§e§lFast");
        registerText("settings.randomtickspeed.ultra", "§3§lUltra", "§3§lUltra");
        registerText("settings.randomtickspeed.highspeed", "§6§lHochgeschwindigkeit", "§6§lHighspeed");
        registerText("settings.randomtickspeed.overkill", "§9§lOverkill", "§9§lOverkill");
        registerText("settings.redstone.desc", "§7§oStelle ein ob Redstone aktiv ist.", "§7§oSet if Redstone is enabled.");
        registerText("settings.redstone.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.redstone.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.size.desc", "§7§oStelle ein wie groß die Welt ist.", "§7§oSet how big the world is.");
        registerText("settings.size.small", "§d§lKlein§7: 500x500", "§d§lSmall§7: 500x500");
        registerText("settings.size.normal", "§a§lNormal§7: 1.000x1.000", "§a§lNormal§7: 1.000x1.000");
        registerText("settings.size.big", "§c§lGroß§7: 2.500x2.500", "§c§lBig§7: 2.500x2.500");
        registerText("settings.size.huge", "§e§lRiesig§7: 5.000x5.000", "§e§lHuge§7: 5.000x5.000");
        registerText("settings.size.enormously", "§3§lEnorm§7: 10.000x10.000", "§3§lEnormously§7: 10.000x10.000");
        registerText("settings.size.immense", "§5§lImmens§7: 50.000x50.000", "§5§lImmense§7: 50.000x50.000");
        registerText("settings.size.gigantic", "§6§lGigantisch§7: 100.000x100.000", "§6§lGigantic§7: 100.000x100.000");
        registerText("settings.size.oversized", "§9§lÜbergroß§7: 1.000.000x1.000.000", "§9§lOversized§7: 1.000.000x1.000.000");
        registerText("settings.time.desc", "§7§oStelle die Zeit in der Welt ein.", "§7§oSet the time of the world.");
        registerText("settings.time.day", "§a§lTag", "§a§lDay");
        registerText("settings.time.midday", "§c§lMittag", "§c§lMidday");
        registerText("settings.time.night", "§d§lNacht", "§d§lNight");
        registerText("settings.time.midnight", "§e§lMitternacht", "§e§lMidnight");
        registerText("settings.time.running", "§3§lZyklus", "§3§lCycle");
        registerText("settings.unloading.desc", "§7§oStelle ein ob die Welt entladen wird, wenn sie leer ist.", "§7§oSet if the world is going to be unloaded when emtpy.");
        registerText("settings.unloading.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.unloading.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.weather.desc", "§7§oStelle das Wetter in der Welt ein.", "§7§oSet the weather of the world.");
        registerText("settings.weather.sun", "§a§lSonne", "§a§lSun");
        registerText("settings.weather.rain", "§c§lRegen", "§c§lRain");
        registerText("settings.weather.storm", "§d§lSturm", "§d§lStorm");
        registerText("settings.weather.running", "§e§lZyklus", "§e§lCycle");
        registerText("settings.explosion.desc", "§7§oStelle ein ob Explosionen aktiv sind.", "§7§oSet if explosions are enabled.");
        registerText("settings.explosion.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.explosion.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.potion.desc", "§7§oStelle ein ob Tränke benutzt werden können.", "§7§oSet if potions are useable.");
        registerText("settings.potion.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.potion.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.hunger.desc", "§7§oStelle ein ob Hunger aktiv ist.", "§7§oSet if hunger is active.");
        registerText("settings.hunger.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.hunger.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.mobdrops.desc", "§7§oStelle ein ob Mobs Loot droppen.", "§7§oSet if mobs drop loot.");
        registerText("settings.mobdrops.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.mobdrops.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.mobspawn.desc", "§7§oStelle ein ob Mobs spawnen.", "§7§oSet if mobs are spawning.");
        registerText("settings.mobspawn.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.mobspawn.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.spreading.desc", "§7§oStelle ein ob Blöcke sich verbreiten können.", "§7§oSet if blocks are spreading.");
        registerText("settings.spreading.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.spreading.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.entityformblock.desc", "§7§oStelle ein ob Mobs Blöcke verändern können.", "§7§oSet if mobs can form blocks.");
        registerText("settings.entityformblock.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.entityformblock.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.blockform.desc", "§7§oStelle ein ob sich Blöcke verändern können.", "§7§oSet if blocks can change themself.");
        registerText("settings.blockform.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.blockform.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.blockfromto.desc", "§7§oStelle ein ob Blöcke sich verbreiten können.", "§7§oSet if blocks can expand.");
        registerText("settings.blockfromto.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.blockfromto.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.blockgrow.desc", "§7§oStelle ein ob Blöcke wachsen können.", "§7§oSet if blocks can grow.");
        registerText("settings.blockgrow.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.blockgrow.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.blockfertilize.desc", "§7§oStelle ein ob Pflanzen mit Knochenmehl wachsen können.", "§7§oSet if plants can grow by using bone meal.");
        registerText("settings.blockfertilize.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.blockfertilize.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.blockburn.desc", "§7§oStelle ein ob Blöcke verbrennen können.", "§7§oSet if blocks can burn down.");
        registerText("settings.blockburn.on", "§a§lAktiv", "§a§lActive");
        registerText("settings.blockburn.off", "§c§lInaktiv", "§c§lInactive");
        registerText("settings.gamemode.desc", "§7§oStelle ein ob Blöcke verbrennen können.", "§7§oSet if blocks can burn down.");
        registerText("settings.gamemode.keep", "§a§lBehalten", "§a§lKeep");
        registerText("settings.gamemode.survival", "§6§lÜberleben", "§6§lSurvival");
        registerText("settings.gamemode.creative", "§e§lCreative", "§e§lCreative");
        registerText("settings.gamemode.spectator", "§5§lZuschauer", "§5§lSpectator");
        registerText("settings.gamemode.adventure", "§c§lAbenteuer", "§c§lAventure");

        registerText("settings.general.leftclick", "§7§oNutze Linksklick um den nächsten Modus zu wählen.", "§7§oUse leftclick to switch to the next mode.");
        registerText("settings.general.rightclick", "§7§oNutze Rechtsklick um den vorherigen Modus zu wählen.", "§7§oUse rightclick to switch to the last mode.");
        registerText("settings.general.adminsetting", "§c§oKann nur von Administratoren verändert werden.", "§c§oCan only be changed by administrators.");

        registerText("history.recordtype.block.place", "§7Block platziert: §b{0}", "§7Block placed: §b{0}");
        registerText("history.recordtype.block.break", "§7Block zerstört: §b{0}", "§7Block breaked: §b{0}");
        registerText("history.recordtype.role.delete", "§7Rolle gelöscht: §b{0}", "§7Role deleted: §b{0}");
        registerText("history.recordtype.role.add", "§7Rolle hinzugefügt: §b{0}", "§7Role added: §b{0}");
        registerText("history.recordtype.role.admission", "§7Rollen-Zugriff verändert: §b{0}", "§7Role-Admission changed: §b{0}");
        registerText("history.recordtype.setting", "§7Einstellung verändert: §b{0}", "§7Setting changed: §b{0}");
        registerText("history.change.display.timestamp", "§7Wann: §b{0}", "§7When: §b{0}");
        registerText("history.change.display.editor", "§7Wer: §b{0}", "§7Who: §b{0}");
        registerText("history.change.display.where", "§7Wo: §b{0}", "§7Where: §b{0}");
        registerText("history.change.display.info.1", "§7§oDrücke §9Q §7§oum diese Aktion rückgängig zu machen.", "§7§oPress §9Q §7to undo this action.");
        registerText("history.change.display.info.2", "§7§oDrücke §9Shift + Linksklick §7§oum diese Aktion rückgängig zu machen.", "§7§oPress §9Shift + Leftclick §7to undo this action.");


        WorldManagement.get().getSaveSystem().saveMessages(this.languagesAndKeysAndTexts);
    }

    public String getText(String key, Object... values) {
        if (!getLanguage(this.activeLanguage).containsKey(key)) {
            return ERROR_MESSAGE;
        }
        String text = getLanguage(this.activeLanguage).get(key);
        int i = 0;
        for (Object value : values) {
            text = text.replace("{" + i + "}", ((value instanceof Boolean) ? (((boolean) value) ? getText("general.boolean.true") : getText("general.boolean.false")) : value.toString()));
            i++;
        }
        return text;
    }

    public HashMap<String, String> getLanguage(String language) {
        return this.languagesAndKeysAndTexts.get(language);
    }

    public void save() {
        WorldManagement.get().getSaveSystem().saveMessages(this.languagesAndKeysAndTexts);
    }

    public void send(Player player, String key, Object... values) {
        player.sendMessage(this.prefix + getText(key, values));
    }

    public void send(CommandSender player, String key, Object... values) {
        player.sendMessage(getText(key, values));
    }

    public void registerText(String key, String germanText, String englishText) {
        registerTextInLanguage(GERMAN, key, germanText);
        registerTextInLanguage(ENGLISH, key, englishText);
    }

    private void registerTextInLanguage(String language, String key, String text) {
        if (getLanguage(language) != null) {
            if (!getLanguage(language).containsKey(key)) {
                getLanguage(language).put(key, text);
            }
        }
    }
}