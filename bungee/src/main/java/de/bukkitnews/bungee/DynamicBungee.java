package de.bukkitnews.bungee;

import de.bukkitnews.bungee.handler.ServerHandler;
import de.bukkitnews.bungee.listener.PlayerDisconnectListener;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DynamicBungee extends Plugin {

    private final @NotNull ServerHandler serverHandler = new ServerHandler(this);

    @Override
    public void onEnable(){
        loadConfig();
        initListener();

        getLogger().info("DynamicServers BungeeCord Plugin activated");
    }

    @Override
    public void onDisable(){
        getLogger().info("DynamicServers BungeeCord Plugin deactivated");
    }

    private void initListener(){
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener(serverHandler, this));
    }

    private void loadConfig(){

    }
}
