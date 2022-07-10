package de.hyper.worlds.common.obj.world.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@AllArgsConstructor @Getter
public class RoleAdmission implements Cloneable {

    private String display;
    private String key;
    @Setter private boolean allowed;

    public void switchAllowed() {
        this.allowed = !allowed;
    }

    @Override
    public RoleAdmission clone() {
        try {
            RoleAdmission clone = (RoleAdmission) super.clone();
            clone.display = this.display;
            clone.key = this.key;
            clone.allowed = this.allowed;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Material getMaterial() {
        if (isAllowed()) {
            return Material.ENCHANTED_BOOK;
        }
        return Material.BOOK;
    }
}