package com.frosthex.timingsystem.noboatcollisions.commands;

import com.frosthex.timingsystem.noboatcollisions.TimingSystemNoBoatCollisionsPlugin;
import com.frosthex.timingsystem.noboatcollisions.managers.DependencyManager;
import com.frosthex.timingsystem.noboatcollisions.utils.Messager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class NoBoatCollisionsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("timingsystemnoboatcollisions.noboatcollisions")) {
            Messager.msgSender("&cSorry you don't have permission for this command.", sender);
            return true;
        }

        Messager.msgSender("&aVersion: " + TimingSystemNoBoatCollisionsPlugin.getPluginVersion() + " Supports TimingSystem " + DependencyManager.getTimingSystemSupportedVersion()
                + " Supports MC " + Arrays.toString(DependencyManager.getSupportedMinecraftVersions()), sender);

        return true;
    }
}
