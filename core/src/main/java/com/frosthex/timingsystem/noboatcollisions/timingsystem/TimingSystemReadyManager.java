package com.frosthex.timingsystem.noboatcollisions.timingsystem;

import com.frosthex.timingsystem.noboatcollisions.TimingSystemNoBoatCollisionsPlugin;
import me.makkuusen.timing.system.api.TimingSystemAPI;
import me.makkuusen.timing.system.boat.BoatSpawner;
import org.bukkit.Location;
import org.bukkit.entity.Boat;

public class TimingSystemReadyManager {

    /**
     * This method is called if everything appears to be working with the rest of the plugin.
     * This method creates the BoatSpawner expected by the TimingSystemAPI.
     */
    public static void setNMSBoatSpawner() {
        TimingSystemAPI.setBoatSpawner(new BoatSpawner() {
            @Override
            public Boat spawnBoat(Location location) {
                return TimingSystemNoBoatCollisionsPlugin.getNmsHandler().spawnBoat(location);
            }

            @Override
            public Boat spawnChestBoat(Location location) {
                return TimingSystemNoBoatCollisionsPlugin.getNmsHandler().spawnChestBoat(location);
            }
        });

    }

}
