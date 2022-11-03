package org.mossmc.mosscg.MoBoxEntityLimit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mossmc.mosscg.MoBoxCore.Info.InfoGroupBackend;
import org.mossmc.mosscg.MoBoxEntityLimit.Cache.CacheEntity;
import org.mossmc.mosscg.MoBoxEntityLimit.Task.TaskEntityCount;
import org.mossmc.mosscg.MoBoxEntityLimit.Task.TaskEntityKill;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static Configuration getConfig;
    public static Logger logger;
    public static Main instance;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        logger = getLogger();
        instance = this;
    }

    @Override
    public void onEnable() {
        InfoGroupBackend.sendPluginStartGroup(BasicInfo.pluginName, BasicInfo.pluginVersion);
        logger.info(ChatColor.GREEN+"正在启动插件");
        BasicVoid.load();
        logger.info(ChatColor.GREEN+"插件启动完成");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED+"未知指令！");
            return false;
        }
        switch (args[0]) {
            case "help":
                if (!sender.hasPermission("moboxentitylimit.help")) {
                    sender.sendMessage(ChatColor.RED+"你没有权限执行这个指令！");
                    return false;
                }
                sender.sendMessage(ChatColor.DARK_GREEN+"MoBoxEntityLimit指令帮助");
                sender.sendMessage(ChatColor.GREEN+"/mbel kill <实体类型> - 清除某类实体");
                sender.sendMessage(ChatColor.GREEN+"/mbel count - 统计全部世界实体数量");
                sender.sendMessage(ChatColor.GREEN+"/mbel list - 显示限制信息");
                sender.sendMessage(ChatColor.GREEN+"/mbel debug - 开关debug信息");
                sender.sendMessage(ChatColor.GREEN+"/mbel reload - 重载插件");
                break;
            case "debug":
                if (!sender.hasPermission("moboxentitylimit.debug")) {
                    sender.sendMessage(ChatColor.RED+"你没有权限执行这个指令！");
                    return false;
                }
                if (BasicInfo.debug) {
                    BasicInfo.debug = false;
                    sender.sendMessage(ChatColor.GREEN+"已关闭debug模式！");
                } else {
                    BasicInfo.debug = true;
                    sender.sendMessage(ChatColor.GREEN+"已开启debug模式！");
                }
                break;
            case "kill":
                if (!sender.hasPermission("moboxentitylimit.kill")) {
                    sender.sendMessage(ChatColor.RED+"你没有权限执行这个指令！");
                    return false;
                }
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED+"指令提示：/mbel kill <生物类型（全大写）>");
                    return false;
                }
                TaskEntityKill.runTask(sender,args[1]);
                break;
            case "count":
                if (!sender.hasPermission("moboxentitylimit.count")) {
                    sender.sendMessage(ChatColor.RED+"你没有权限执行这个指令！");
                    return false;
                }
                TaskEntityCount.runTask(sender);
                break;
            case "list":
                if (!sender.hasPermission("moboxentitylimit.list")) {
                    sender.sendMessage(ChatColor.RED+"你没有权限执行这个指令！");
                    return false;
                }
                sender.sendMessage(CacheEntity.getTypeListString());
                break;
            case "reload":
                if (!sender.hasPermission("moboxentitylimit.reload")) {
                    sender.sendMessage(ChatColor.RED+"你没有权限执行这个指令！");
                    return false;
                }
                BasicVoid.unload();
                BasicVoid.load();
                sender.sendMessage(ChatColor.GREEN+"MoBoxEntityLimit重载完成！");
                break;
            default:
                sender.sendMessage(ChatColor.RED+"未知指令！请使用/mbel help查看帮助");
                return false;
        }
        return true;
    }
}
