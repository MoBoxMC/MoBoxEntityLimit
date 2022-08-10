package org.mossmc.mosscg.MoBoxEntityLimit.Cache;

import com.alibaba.fastjson.JSONObject;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.mossmc.mosscg.MoBoxEntityLimit.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheEntity {
    public static ConcurrentHashMap<Entity,Long> entityMap = new ConcurrentHashMap<>();

    public static Map<EntityType,Long> entityTimeMap = new HashMap<>();
    public static List<EntityType> entityTimeTypeList = new ArrayList<>();

    public static Map<EntityType,Integer> entityTickMap = new HashMap<>();
    public static List<EntityType> entityTickTypeList = new ArrayList<>();

    public static void loadCache() {
        if (Main.getConfig.getBoolean("enableAliveTimeTask")) {
            String jsonString = Main.getConfig.getString("entityAliveTime");
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            jsonObject.forEach((name, object) -> {
                try {
                    EntityType type = EntityType.valueOf(name.toUpperCase());
                    Long time = Integer.parseInt(object.toString())*1000L;
                    entityTimeTypeList.add(type);
                    entityTimeMap.put(type,time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if (Main.getConfig.getBoolean("enableAliveTickTask")) {
            String jsonString = Main.getConfig.getString("entityAliveTick");
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            jsonObject.forEach((name, object) -> {
                try {
                    EntityType type = EntityType.valueOf(name.toUpperCase());
                    Integer time = Integer.parseInt(object.toString());
                    entityTickTypeList.add(type);
                    entityTickMap.put(type,time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static String getTypeListString() {
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.DARK_GREEN).append("MoBoxEntityLimit计时清理实体名单");
        builder.append(ChatColor.GOLD).append("\n计时清理：").append(ChatColor.GREEN);
        entityTimeMap.forEach((type, time) -> {
            builder.append("\n");
            builder.append(type.name()).append(" - ");
            builder.append(time).append("毫秒");
        });
        builder.append(ChatColor.GOLD).append("\n定时清理：").append(ChatColor.GREEN);
        entityTickMap.forEach((type, time) -> {
            builder.append("\n");
            builder.append(type.name()).append(" - ");
            builder.append(time).append("Tick");
        });
        return builder.toString();
    }
}
