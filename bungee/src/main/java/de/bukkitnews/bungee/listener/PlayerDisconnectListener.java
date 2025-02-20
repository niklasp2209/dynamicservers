package de.bukkitnews.bungee.listener;

import de.bukkitnews.bungee.DynamicBungee;
import de.bukkitnews.bungee.handler.ServerHandler;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PlayerDisconnectListener implements Listener {

    private final @NotNull ServerHandler serverHandler;
    private final @NotNull DynamicBungee dynamicBungee;

    @EventHandler
    public void handleDisconnect(ServerDisconnectEvent event) {
        dynamicBungee.getProxy().getScheduler().runAsync(dynamicBungee, () -> {
            String serverName = event.getTarget().getName();
            if (dynamicBungee.getProxy().getPlayers().stream().noneMatch(p ->
                    p.getServer().getInfo().getName().equals(serverName))) {
                serverHandler.stopServer(serverName);
            }
        });
    }
}
