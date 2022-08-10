package org.mossmc.mosscg.MoBoxEntityLimit.Task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mossmc.mosscg.MoBoxEntityLimit.Main;

import java.util.*;

public class TaskEntityCount {
    public static BukkitTask task;
    public static void runTask(CommandSender sender) {
        if (Main.getConfig.getBoolean("asyncTask")) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid(sender);
                }
            }.runTaskAsynchronously(Main.instance);
        } else {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    taskVoid(sender);
                }
            }.runTask(Main.instance);
        }
    }

    public static void taskVoid(CommandSender sender) {
        sender.sendMessage("正在统计，请稍后......");
        Map<EntityType,Integer> countMap = new HashMap<>();
        Map<EntityType,Integer> allMap = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.DARK_GREEN).append("MoBoxEntityLimit实体统计名单");
        Bukkit.getWorlds().forEach(world -> {
            builder.append(ChatColor.GOLD).append("\n世界：").append(world.getName()).append(ChatColor.GREEN);
            world.getEntities().forEach(entity -> {
                EntityType type = entity.getType();
                if (countMap.containsKey(type)) {
                    countMap.replace(type,countMap.get(type)+1);
                } else {
                    countMap.put(type,1);
                }
            });
            Map<Integer,String> rankMap = new HashMap<>();
            List<Integer> rankList = new ArrayList<>();
            countMap.forEach((type, count) -> {
                if (allMap.containsKey(type)) {
                    allMap.replace(type,allMap.get(type)+count);
                } else {
                    allMap.put(type,count);
                }
                if (!rankList.contains(count)) {
                    rankList.add(count);
                }
                if (!rankMap.containsKey(count)) {
                    rankMap.put(count,type.name());
                } else {
                    rankMap.replace(count,rankMap.get(count)+"、"+type);
                }
            });
            countMap.clear();
            Collections.sort(rankList);
            int rankTime = 0;
            while (rankTime < 10) {
                int maxRank = rankList.size()-1-rankTime;
                int maxCount = rankList.get(maxRank);
                builder.append("\n");
                builder.append(rankMap.get(maxCount)).append(" - ");
                builder.append(maxCount).append("个");
                rankTime++;
                if (rankList.size() <= rankTime) {
                    break;
                }
            }
        });
        builder.append(ChatColor.GOLD).append("\n统计：").append(ChatColor.COLOR_CHAR).append("b");
        Map<Integer,String> rankMap = new HashMap<>();
        List<Integer> rankList = new ArrayList<>();
        allMap.forEach((type, count) -> {
            if (!rankList.contains(count)) {
                rankList.add(count);
            }
            if (!rankMap.containsKey(count)) {
                rankMap.put(count,type.name());
            } else {
                rankMap.replace(count,rankMap.get(count)+"、"+type);
            }
        });
        Collections.sort(rankList);
        int rankTime = 0;
        while (rankTime < 10) {
            int maxRank = rankList.size()-1-rankTime;
            int maxCount = rankList.get(maxRank);
            builder.append("\n");
            builder.append(rankMap.get(maxCount)).append(" - ");
            builder.append(maxCount).append("个");
            rankTime++;
            if (rankList.size() <= rankTime) {
                break;
            }
        }
        sender.sendMessage(builder.toString());
    }
}
