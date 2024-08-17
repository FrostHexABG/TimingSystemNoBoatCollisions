package com.frosthex.timingsystem.noboatcollisions;

import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.ChestBoat;

public interface NMSHandler {

    public Boat spawnBoat(Location location);

    public ChestBoat spawnChestBoat(Location location);

}
