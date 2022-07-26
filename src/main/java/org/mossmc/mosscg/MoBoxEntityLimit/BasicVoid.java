package org.mossmc.mosscg.MoBoxEntityLimit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.mossmc.mosscg.MoBoxEntityLimit.Cache.CacheEntity;
import org.mossmc.mosscg.MoBoxEntityLimit.Listeners.ListenerEntitySpawn;
import org.mossmc.mosscg.MoBoxEntityLimit.Task.TaskEntityAliveTick;
import org.mossmc.mosscg.MoBoxEntityLimit.Task.TaskEntityAliveTime;

public class BasicVoid {
    public static void load() {
        Main.logger.info(ChatColor.GREEN+"正在加载配置文件");
        Main.instance.reloadConfig();
        Main.getConfig = Main.instance.getConfig();
        CacheEntity.loadCache();
        BasicInfo.debug = Main.getConfig.getBoolean("debug");
        Main.logger.info(ChatColor.GREEN+"配置文件加载完成");

        Main.logger.info(ChatColor.GREEN+"正在注册事件监听器");
        if (Main.getConfig.getBoolean("enableAliveTimeTask")) {
            Bukkit.getPluginManager().registerEvents(new ListenerEntitySpawn(),Main.instance);
        }
        Main.logger.info(ChatColor.GREEN+"事件监听器注册完成");

        Main.logger.info(ChatColor.GREEN+"正在启动任务");
        if (Main.getConfig.getBoolean("enableAliveTimeTask")) {
            TaskEntityAliveTime.runTask();
        }
        if (Main.getConfig.getBoolean("enableAliveTickTask")) {
            TaskEntityAliveTick.runTask();
        }
        Main.logger.info(ChatColor.GREEN+"任务启动完成");
    }

    public static void unload() {
        Main.logger.info(ChatColor.GREEN+"正在结束任务");
        if (Main.getConfig.getBoolean("enableAliveTimeTask")) {
            TaskEntityAliveTime.task.cancel();
        }
        if (Main.getConfig.getBoolean("enableAliveTickTask")) {
            TaskEntityAliveTick.task.cancel();
        }
        Main.logger.info(ChatColor.GREEN+"任务结束完成");

        Main.logger.info(ChatColor.GREEN+"正在清理缓存");
        CacheEntity.entityTickTypeList.clear();
        CacheEntity.entityTickMap.clear();
        CacheEntity.entityTimeTypeList.clear();
        CacheEntity.entityTimeMap.clear();
        CacheEntity.entityMap.clear();
        Main.logger.info(ChatColor.GREEN+"缓存清理完成");
    }
}
