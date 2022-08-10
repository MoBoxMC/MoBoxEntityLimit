package org.mossmc.mosscg.MoBoxEntityLimit.Task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxEntityLimit.Main;

import java.util.concurrent.atomic.AtomicReference;

public class TaskEntityKill {
    public static BukkitTask task;
    public static void runTask(CommandSender sender, String type) {
        if (Main.getConfig.getBoolean("asyncTask")) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid(sender, type);
                }
            }.runTaskAsynchronously(Main.instance);
        } else {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid(sender, type);
                }
            }.runTask(Main.instance);
        }
    }

    public static void taskVoid(CommandSender sender, String type) {
        EntityType entityType;
        try {
            entityType = EntityType.valueOf(type);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED+"无法执行操作：未知的生物类型");
            return;
        }
        Bukkit.getWorlds().forEach(world -> {
            AtomicReference<Integer> count = new AtomicReference<>(0);
            world.getEntities().forEach(entity -> {
                if (entity.getType().equals(entityType)) {
                    count.getAndSet(count.get() + 1);
                    entity.remove();
                }
            });
            sender.sendMessage(ChatColor.GREEN+"已清除"+world.getName()+"世界的"+count.get()+"个"+type);
        });
    }
}
