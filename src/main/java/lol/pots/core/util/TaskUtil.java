package lol.pots.core.util;

import org.bukkit.scheduler.BukkitRunnable;
import lol.pots.core.Arctic;

public class TaskUtil {

    public static void runTaskAsync(Runnable runnable) {
        Arctic.getInstance().getServer().getScheduler().runTaskAsynchronously(Arctic.getInstance(), runnable);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        Arctic.getInstance().getServer().getScheduler().runTaskLater(Arctic.getInstance(), runnable, delay);
    }

    public static void runTaskTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(Arctic.getInstance(), delay, timer);
    }

    public static void runTaskTimer(Runnable runnable, long delay, long timer) {
        Arctic.getInstance().getServer().getScheduler().runTaskTimer(Arctic.getInstance(), runnable, delay, timer);
    }

    public static void runTask(Runnable runnable) {
        Arctic.getInstance().getServer().getScheduler().runTask(Arctic.getInstance(), runnable);
    }

}
