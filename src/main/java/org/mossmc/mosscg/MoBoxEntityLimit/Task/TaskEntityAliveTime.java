package org.mossmc.mosscg.MoBoxEntityLimit.Task;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxEntityLimit.BasicInfo;
import org.mossmc.mosscg.MoBoxEntityLimit.Cache.CacheEntity;
import org.mossmc.mosscg.MoBoxEntityLimit.Main;

public class TaskEntityAliveTime {
    public static BukkitTask task;
    public static void runTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (CacheEntity.entityMap.size() > 0) {
                    CacheEntity.entityMap.forEach((entity, time) -> {
                        try {
                            if (time+CacheEntity.entityTimeMap.get(entity.getType()) > System.currentTimeMillis()) {
                                entity.remove();
                            }
                        } catch (Exception e) {
                            if (BasicInfo.debug) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }.runTaskTimerAsynchronously(Main.instance, Main.getConfig.getLong("aliveTimeCheckDelay"),Main.getConfig.getLong("aliveTimeCheckDelay"));
    }
}
