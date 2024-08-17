package com.frosthex.timingsystem.noboatcollisions;

import com.frosthex.timingsystem.noboatcollisions.commands.NoBoatCollisionsCommand;
import com.frosthex.timingsystem.noboatcollisions.managers.DependencyManager;
import com.frosthex.timingsystem.noboatcollisions.timingsystem.TimingSystemReadyManager;
import com.frosthex.timingsystem.noboatcollisions.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimingSystemNoBoatCollisionsPlugin extends JavaPlugin {

    public static ConsoleCommandSender clogger = Bukkit.getConsoleSender();
    public static Logger log;
    public static String prefix = Messager.color("&8[&bTS&fNoBoatCollisions&8] &f");

    private String minecraftVersion;
    private static NMSHandler nmsHandler;
    private static TimingSystemNoBoatCollisionsPlugin instance;
    private static String pluginVersion;

    @Override
    public void onEnable() {
        instance = this;
        log = this.getLogger();
        pluginVersion = getDescription().getVersion();

        Messager.msgConsole("&6TimingSystemNoBoatCollisions version " + pluginVersion + " was created by JustBru00 for &bFrost&fHex.com&6.");
        Messager.msgConsole("&6This plugin is an add-on for the TimingSystem plugin. Find it at: https://github.com/FrostHexABG/TimingSystem");
        Messager.msgConsole("&6See https://github.com/FrostHexABG/TimingSystemNoBoatCollisions for details and updates.");
        Messager.msgConsole("&6This plugin is licensed under the MPL v2.0 LICENSE.");

        Messager.msgConsole("Checking if a compatible version of TimingSystem is installed...");
        DependencyManager.checkDependancies();
        if (!DependencyManager.isTimingSystemInstalled()) {
            // TimingSystem is not installed or the version is not supported.
            Bukkit.getPluginManager().disablePlugin(instance);
            return;
        }

        Messager.msgConsole("Checking if this server is running a supported Minecraft version...");
        DependencyManager.checkMinecraftVersion();
        if (!DependencyManager.isMinecraftVersionSupported()) {
            // Minecraft version is not in the supported list in DependencyManager
            Bukkit.getPluginManager().disablePlugin(instance);
            return;
        }

        nmsHandler = createNMSHandler();

        if (nmsHandler == null) {
            // Something went really wrong. Disable this plugin.
            Bukkit.getPluginManager().disablePlugin(instance);
            return;
        }

        TimingSystemReadyManager.setNMSBoatSpawner();

        getCommand("noboatcollisions").setExecutor(new NoBoatCollisionsCommand());

        Messager.msgConsole("&aSuccessfully hooked into TimingSystem. All boats should now have no boat collisions on the server side.");
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static NMSHandler getNmsHandler() {
        return nmsHandler;
    }

    /**
     * Returns the actual running Minecraft version, e.g. 1.20 or 1.16.5
     *
     * @return Minecraft version
     */
    public String getMinecraftVersion() {
        if (minecraftVersion != null) {
            return minecraftVersion;
        } else {
            String bukkitGetVersionOutput = Bukkit.getVersion();
            Matcher matcher = Pattern.compile("\\(MC: (?<version>[\\d]+\\.[\\d]+(\\.[\\d]+)?)\\)").matcher(bukkitGetVersionOutput);
            if (matcher.find()) {
                return minecraftVersion = matcher.group("version");
            } else {
                throw new RuntimeException("Could not determine Minecraft version from Bukkit.getVersion(): " + bukkitGetVersionOutput);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private NMSHandler createNMSHandler() {
        String clazzName = "com.frosthex.timingsystem.noboatcollisions.nms_" + getMinecraftVersion()
                .replace(".", "_") + ".NMSHandlerImpl";
        try {
            Class<? extends NMSHandler> clazz = (Class<? extends NMSHandler>) Class.forName(clazzName);
            return clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException exception) {
            Messager.msgConsole("&c[CRITICAL] Can't instantiate NMSHandlerImpl for version " + getMinecraftVersion() +
                    " (class " + clazzName + " not found. This usually means that this Minecraft version is not " +
                    "supported by this version of the plugin.)");
            exception.printStackTrace();
            return null;
        } catch (InvocationTargetException exception) {
            Messager.msgConsole("&c[CRITICAL] Can't instantiate NMSHandlerImpl for version " + getMinecraftVersion() +
                    " (constructor in class " + clazzName + " threw an exception)");
            exception.printStackTrace();
            return null;
        } catch (InstantiationException exception) {
            Messager.msgConsole("&c[CRITICAL] Can't instantiate NMSHandlerImpl for version " + getMinecraftVersion() +
                    " (class " + clazzName + " is abstract)");
            exception.printStackTrace();
            return null;
        } catch (IllegalAccessException exception) {
            Messager.msgConsole("&c[CRITICAL] Can't instantiate NMSHandlerImpl for version " + getMinecraftVersion() +
                    " (no-args constructor in class " + clazzName + " is not accessible)");
            exception.printStackTrace();
            return null;
        } catch (NoSuchMethodException exception) {
            Messager.msgConsole("&c[CRITICAL] Can't instantiate NMSHandlerImpl for version " + getMinecraftVersion() +
                    " (no no-args constructor found in class " + clazzName + ")");
            exception.printStackTrace();
            return null;
        }
    }

    public static TimingSystemNoBoatCollisionsPlugin getInstance() {
        return instance;
    }

    public static String getPluginVersion() {
        return pluginVersion;
    }
}
