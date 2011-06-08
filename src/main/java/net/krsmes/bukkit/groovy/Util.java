package net.krsmes.bukkit.groovy;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Util {

    static final int[] SEETHRU = new int[] {50, 55, 65, 66, 68, 78};


    public static Block lookingAtBlock(LivingEntity ent, double maxDist, double precision) {
        Location loc = ent.getLocation();
        World world = loc.getWorld();
        double eyePosX = loc.getX();
        double eyePosY = loc.getY() + ent.getEyeHeight();
        double eyePosZ = loc.getZ();

        Vector eyeDir = loc.getDirection();
        double eyeDirX = eyeDir.getX();
        double eyeDirY = eyeDir.getY();
        double eyeDirZ = eyeDir.getZ();

        Block result = null;
        double precIncr = precision / 10.0;
        int lastX = 0;
        int lastY = 0;
        int lastZ = 0;
        double dist = 0.0;
        while (dist < maxDist) {
            dist += precision;
            int x = (int) Math.floor(eyePosX + (eyeDirX * dist));
            int y = (int) Math.floor(eyePosY + (eyeDirY * dist));
            int z = (int) Math.floor(eyePosZ + (eyeDirZ * dist));
            if (x != lastX || y != lastY || z != lastZ) {
                result = world.getBlockAt(x, y, z);
                if (y == 128 || y == 1) {
                    break;
                }
                int typeId = result.getTypeId();
                if (typeId > 0 && Arrays.binarySearch(SEETHRU, typeId) < 0) {
                    break;
                }
                lastX = x;
                lastY = y;
                lastZ = z;
            }
            precision += precIncr;
        }
        return result;
    }


    public static String join(String glue, String... s) {
        StringBuilder out = new StringBuilder();
        int k = s.length;
        if (k > 0) {
            out.append(s[0]);
            for (int x = 1; x < k; ++x) {
                out.append(glue).append(s[x]);
            }
        }
        return out.toString();
    }


    public static void sendMessage(JavaPlugin plugin, final Player player, final String message) {
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin,
            new Runnable() {
                public void run() {
                    player.sendMessage(message);
                }
            }
        );
    }


    public static void teleport(GroovyPlugin plugin, final Player player, final Location dest) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                new Runnable() {
                    public void run() {
                        player.teleport(dest);
                    }
                }
        );
    }
}
