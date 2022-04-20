package de.hyper.worlds.common.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@AllArgsConstructor @Getter
public class sLocation {

    private String world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public sLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.pitch = loc.getPitch();
        this.yaw = loc.getYaw();
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world),x,y,z,yaw,pitch);
    }
}
