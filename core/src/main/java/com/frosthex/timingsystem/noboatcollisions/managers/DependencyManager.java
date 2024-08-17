package com.frosthex.timingsystem.noboatcollisions.managers;

import com.frosthex.timingsystem.noboatcollisions.TimingSystemNoBoatCollisionsPlugin;
import com.frosthex.timingsystem.noboatcollisions.utils.Messager;
import com.frosthex.timingsystem.noboatcollisions.utils.VersionComparator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class DependencyManager {

    private static boolean timingSystemInstalled = false;
    private static String timingSystemVersion = "null";

    private static final String timingSystemSupportedVersion = "2.3";
    private static final VersionComparator compare = new VersionComparator();

    private static final String[] supportedMinecraftVersions = {"1.20.1", "1.20.6", "1.21.1"};
    private static boolean minecraftVersionSupported = false;

    public static void checkDependancies() {
        PluginManager pm = Bukkit.getPluginManager();

        Plugin ts = pm.getPlugin("TimingSystem");
        if (ts != null) {
            timingSystemVersion = ts.getDescription().getVersion();

            int result = compare.compare(timingSystemSupportedVersion, timingSystemVersion);
                if (result < 0) {
                    // TS Version Greater Than Supported
                    timingSystemInstalled = true;
                    Messager.msgConsole("&c[WARN] TimingSystem " + timingSystemVersion + " is not officially supported. Attempting to start anyway...");
                    return;
                } else if (result > 0) {
                    // TS Version Less Than Supported
                    Messager.msgConsole("&c[CRITICAL] TimingSystem " + timingSystemVersion + " is not supported. The plugin will now be disabled.");
                } else {
                    // TS Version is the same as supported
                    timingSystemInstalled = true;
                    Messager.msgConsole("&a[INFO] TimingSystem " + timingSystemVersion + " is officially supported.");
                }
        }

    }

    public static void checkMinecraftVersion() {
        for (String supported : supportedMinecraftVersions) {
            if (supported.equalsIgnoreCase(TimingSystemNoBoatCollisionsPlugin.getInstance().getMinecraftVersion())) {
                minecraftVersionSupported = true;
                return;
            }
        }
        Messager.msgConsole("&c[CRITICAL] This minecraft version " + TimingSystemNoBoatCollisionsPlugin.getInstance().getMinecraftVersion() + " is not supported. Supported Versions: " + supportedMinecraftVersions.toString());
    }

    public static boolean isMinecraftVersionSupported() {
        return minecraftVersionSupported;
    }

    public static boolean isTimingSystemInstalled() {
        return timingSystemInstalled;
    }

    public static String getTimingSystemVersion() {
        return timingSystemVersion;
    }

    public static String getTimingSystemSupportedVersion() {
        return timingSystemSupportedVersion;
    }

    public static String[] getSupportedMinecraftVersions() {
        return supportedMinecraftVersions;
    }
}
