package me.allen.aidoru;

import me.allen.aidoru.connection.AidoruPlayerConnection;
import me.allen.aidoru.handler.ExitHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Aidoru extends JavaPlugin {

    private static Aidoru instance;

    private ExitHandler exitHandler = null;

    @Override
    public void onEnable() {
        instance = this;
        this.bindListener();
    }

    @Override
    public void onDisable() {

    }

    public ExitHandler getExitHandler() {
        return this.exitHandler;
    }

    public void setExitHandler(ExitHandler exitHandler) {
        this.exitHandler = exitHandler;
    }

    public static Aidoru getInstance() {
        return instance;
    }

    private void bindListener() {
        Bukkit.getPluginManager()
                .registerEvents(new Listener() {
                    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
                    public void onLogin(PlayerLoginEvent event) {
                        if (event.getResult().equals(PlayerLoginEvent.Result.ALLOWED))
                            AidoruPlayerConnection.injectConnection(event.getPlayer());
                    }
                }, this);
    }
}
