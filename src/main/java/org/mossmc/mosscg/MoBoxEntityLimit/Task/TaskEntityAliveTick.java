package org.mossmc.mosscg.MoBoxEntityLimit.Task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxEntityLimit.BasicInfo;
import org.mossmc.mosscg.MoBoxEntityLimit.Cache.CacheEntity;
import org.mossmc.mosscg.MoBoxEntityLimit.Main;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskEntityAliveTick {
    public static BukkitTask task;
    public static void runTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                AtomicInteger count = new AtomicInteger();
                Bukkit.getWorlds().forEach(world -> {
                    try {
                        world.getEntities().forEach(entity -> {
                            if (CacheEntity.entityTickTypeList.contains(entity.getType())) {
                                if (entity.getTicksLived() >= CacheEntity.entityTickMap.get(entity.getType())) {
                                    entity.remove();
                                    count.getAndIncrement();
                                }
                            }
                        });
                    } catch (Exception e) {
                        if (BasicInfo.debug) {
                            e.printStackTrace();
                        }
                    }
                });
                if (BasicInfo.debug) {
                    Main.logger.info(ChatColor.GREEN+"本次定时清除，清除了"+count.get()+"个实体！");
                }
            }
        }.runTaskTimerAsynchronously(Main.instance, Main.getConfig.getLong("aliveTickCheckDelay"),Main.getConfig.getLong("aliveTickCheckDelay"));
    }
}
