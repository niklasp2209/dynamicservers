package de.bukkitnews.bungee.handler;

import de.bukkitnews.bungee.DynamicBungee;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class ServerHandler {

    private final @NotNull DynamicBungee dynamicBungee;
    private static final @NotNull String SERVER_MANAGER_URL = "http://localhost:8080/server";

    public boolean startServer(String serverName) {
        try {
            URL url = new URL(SERVER_MANAGER_URL + "/start?name=" + serverName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            return connection.getResponseCode() == 200;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean stopServer(String serverName) {
        try {
            URL url = new URL(SERVER_MANAGER_URL + "/stop?name=" + serverName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            return connection.getResponseCode() == 200;
        } catch (IOException ex){
            return false;
        }
    }

    private @NotNull String getSubdomain(ProxiedPlayer player) {
        return player.getPendingConnection().getVirtualHost().getHostString().split("\\.")[0];
    }

    private void waitForServerAndConnect(ProxiedPlayer player, String serverName) {
        dynamicBungee.getProxy().getScheduler().schedule(dynamicBungee, () -> {
            if (dynamicBungee.getProxy().getServerInfo(serverName) != null) {
                player.connect(dynamicBungee.getProxy().getServerInfo(serverName));
            } else {
                waitForServerAndConnect(player, serverName);
            }
        }, 3, TimeUnit.SECONDS);
    }
}
