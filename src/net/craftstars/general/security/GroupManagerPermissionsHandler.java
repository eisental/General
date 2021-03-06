package net.craftstars.general.security;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.craftstars.general.General;
import net.craftstars.general.security.PermissionsHandler;

public class GroupManagerPermissionsHandler implements PermissionsHandler {
    private WorldsHolder wd;
    private final String version;
    private boolean wasLoaded = false;
    
    public GroupManagerPermissionsHandler() {
        Plugin p = General.plugin.getServer().getPluginManager().getPlugin("GroupManager");
        if (p != null) {
            try {
                if (!General.plugin.getServer().getPluginManager().isPluginEnabled(p)) {
                    General.plugin.getServer().getPluginManager().enablePlugin(p);
                }
                wd = ((GroupManager) p).getWorldsHolder();
                wasLoaded = true;
            } catch(Error e) {
                General.plugin.logger.error("Error loading GroupManager. Please report to the GroupManager dev.");
                e.printStackTrace();
            }
            version = p.getDescription().getVersion();
        } else {
            version = "0.0";
        }
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public boolean hasPermission(Player who, String what) {
        return wd.getWorldPermissions(who).has(who, what);
    }

    @Override
    public boolean inGroup(Player who, String which) {
        if(which == ".isop") return who.isOp();
        return wd.getWorldPermissions(who).inGroup(who.getName(), which);
    }

    @Override
    public boolean wasLoaded() {
        return wasLoaded;
    }

    @Override
    public String getName() {
        return "GroupManager";
    }

}
