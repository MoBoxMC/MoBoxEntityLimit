package org.mossmc.mosscg.MoBoxEntityLimit.Task;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxEntityLimit.BasicInfo;
import org.mossmc.mosscg.MoBoxEntityLimit.Cache.CacheEntity;
import org.mossmc.mosscg.MoBoxEntityLimit.Main;

import java.util.ArrayList;
import java.util.List;

public class TaskEntityAliveTime {
    public static BukkitTask task;
    public static void runTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (CacheEntity.entityMap.size() > 0) {
                    List<Entity> killList = new ArrayList<>();
                    CacheEntity.entityMap.forEach((entity, time) -> {
                        try {
                            if (time + CacheEntity.entityTimeMap.get(entity.getType()) < System.currentTimeMillis()) {
                                killList.add(entity);
                            }
                        } catch (Exception e) {
                            if (BasicInfo.debug) {
                                e.printStackTrace();
                            }
                        }
                    });
                    if (killList.size() > 0) {
                        killList.forEach(entity -> {
                            CacheEntity.entityMap.remove(entity);
                            entity.remove();
                        });
                        if (BasicInfo.debug) {
                            Main.logger.info(ChatColor.GREEN+"本次定时清除，清除了"+killList.size()+"个实体！");
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.instance, Main.getConfig.getLong("aliveTimeCheckDelay"),Main.getConfig.getLong("aliveTimeCheckDelay"));
    }
}
