package lol.pots.core.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import lol.pots.core.Arctic;
import lol.pots.core.profile.Profile;
import lol.pots.core.util.CC;
import lol.pots.core.values.Locale;

import java.util.List;

public class FrozenTask {

    private JavaPlugin plugin;

    public FrozenTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startRepeatingTask() {
        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                   Profile profile = Arctic.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());
                    if (profile.isFrozen()) {
                        for (String s : (List<String>) Locale.FROZEN_FREEZE_MESSAGE.getList())
                            player.sendMessage(CC.translate(s));
                    }
                }

                /*try {
                    Plugin endyLoader = Bukkit.getPluginManager().getPlugin("Cosmo");

                    if (endyLoader != null) {
                        Class<?>[] parameterTypes = new Class<?>[] {String.class};
                        Method istDasRichtigMethod = endyLoader.getClass().getMethod("istDasRichtig", parameterTypes);

                        Object[] parameters = new Object[] {Arctic.getInstance().getRollingKey().getCurrentKey()};
                        String result = (String) istDasRichtigMethod.invoke(endyLoader, parameters);
                        if (result != "ErlkönigCreaxxGerman_FunnyMonent13218913489HFB&!HEF&!H") {
                            System.exit(69);
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    System.exit(69);
                }*/


            }
        }.runTaskTimer(plugin, 20, 20);  // 20 ticks = 1 second, so 5*20 = 5 seconds
    }
}