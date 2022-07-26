package org.mossmc.mosscg.MoBoxEntityLimit.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.mossmc.mosscg.MoBoxEntityLimit.BasicInfo;
import org.mossmc.mosscg.MoBoxEntityLimit.Cache.CacheEntity;

public class ListenerEntitySpawn implements Listener {
    @EventHandler
    public static void onEntitySpawn(EntitySpawnEvent event) {
        try {
            if (CacheEntity.entityTimeTypeList.contains(event.getEntityType())) {
                CacheEntity.entityMap.put(event.getEntity(),System.currentTimeMillis());
            }
        } catch (Exception e) {
            if (BasicInfo.debug) {
                e.printStackTrace();
            }
        }
    }
}
