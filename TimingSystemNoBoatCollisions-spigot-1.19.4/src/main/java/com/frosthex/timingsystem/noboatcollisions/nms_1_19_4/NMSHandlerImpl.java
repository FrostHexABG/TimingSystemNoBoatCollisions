package com.frosthex.timingsystem.noboatcollisions.nms_1_19_4;

import com.frosthex.timingsystem.noboatcollisions.NMSHandler;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.Boat;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftBoat;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftChestBoat;
import org.bukkit.event.entity.CreatureSpawnEvent;


public class NMSHandlerImpl implements NMSHandler {

    @Override
    public org.bukkit.entity.Boat spawnBoat(Location location) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        CollisionlessBoat boat = new CollisionlessBoat(level, location.getX(), location.getY(), location.getZ());
        float yaw = Location.normalizeYaw(location.getYaw());
        boat.setYRot(yaw);
        boat.yRotO = yaw;
        boat.setYHeadRot(yaw);
        level.addFreshEntity(boat, CreatureSpawnEvent.SpawnReason.COMMAND);
        boat.setVariant(Boat.Type.OAK);
        return new CraftBoat((CraftServer) Bukkit.getServer(), boat);
    }

    @Override
    public org.bukkit.entity.ChestBoat spawnChestBoat(Location location) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        CollisionlessChestBoat boat = new CollisionlessChestBoat(level, location.getX(), location.getY(), location.getZ());
        float yaw = Location.normalizeYaw(location.getYaw());
        boat.setYRot(yaw);
        boat.yRotO = yaw;
        boat.setYHeadRot(yaw);
        level.addFreshEntity(boat, CreatureSpawnEvent.SpawnReason.COMMAND);
        boat.setVariant(Boat.Type.OAK);
        return new CraftChestBoat((CraftServer) Bukkit.getServer(), boat);
    }
}
