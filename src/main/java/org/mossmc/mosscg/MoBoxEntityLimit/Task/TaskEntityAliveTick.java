package org.mossmc.mosscg.MoBoxEntityLimit.Task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxEntityLimit.BasicInfo;
import org.mossmc.mosscg.MoBoxEntityLimit.Cache.CacheEntity;
import org.mossmc.mosscg.MoBoxEntityLimit.Main;

public class TaskEntityAliveTick {
    public static BukkitTask task;
    public static void runTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getWorlds().forEach(world -> {
                    try {
                        world.getEntities().forEach(entity -> {
                            if (CacheEntity.entityTickTypeList.contains(entity.getType())) {
                                if (entity.getTicksLived() >= CacheEntity.entityTickMap.get(entity.getType())) {
                                    entity.remove();
                                }
                            }
                        });
                    } catch (Exception e) {
                        if (BasicInfo.debug) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.runTaskTimerAsynchronously(Main.instance, Main.getConfig.getLong("aliveTickCheckDelay"),Main.getConfig.getLong("aliveTickCheckDelay"));
    }
}
