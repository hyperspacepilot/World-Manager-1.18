package de.hyper.worlds.common.obj.world.role;

import de.hyper.worlds.common.util.items.HDBSkulls;
import de.hyper.worlds.common.util.items.ItemBuilder;
import de.hyper.worlds.domain.WorldManagement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor @Getter
public class WorldRole implements Cloneable {

    @Setter private UUID uniqueID;
    @Setter private String name;
    private ArrayList<RoleAdmission> admissions;

    public WorldRole(UUID uniqueID,String name, List<RoleAdmission> admissions) {
        this.uniqueID = uniqueID;
        this.name = name;
        this.admissions = (ArrayList<RoleAdmission>) admissions;
    }



    public RoleAdmission getAdmission(String admissionKey) {
        for (RoleAdmission admission : this.admissions) {
            if (admission.getKey().equalsIgnoreCase(admissionKey)) {
                return admission;
            }
        }
        return null;
    }

    public boolean isAllowed(String admissionKey) {
        RoleAdmission admission = getAdmission(admissionKey);
        return admission != null ? admission.isAllowed() : false;
    }

    public ItemStack buildDisplayItem() {
        return buildDisplayItem(true);
    }

    public ItemStack buildDisplayItem(boolean forEditing) {
        ItemBuilder itemBuilder = new ItemBuilder(HDBSkulls.BACKPACK_BROWN);
        itemBuilder.setDisplayName("§b" + name);
        if (forEditing) {
            itemBuilder.setLore(WorldManagement.get().getLanguage().getText("inventory.items.worldrole.display"));
        }
        return itemBuilder.hideAttributes().getItem();
    }

    @Override
    public WorldRole clone() {
        try {
            WorldRole clone = (WorldRole) super.clone();
            clone.uniqueID = this.uniqueID;
            clone.name = this.name;
            clone.admissions = (ArrayList<RoleAdmission>) WorldManagement.get().getLoadHelper().cloneOf(
                    this.admissions,
                    RoleAdmission.class,
                    new ArrayList<RoleAdmission>());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
