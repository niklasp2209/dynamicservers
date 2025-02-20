package de.bukkitnews.servermanager.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final @NotNull Map<String, Process> runningServers = new HashMap<>();
    private final @NotNull Map<String, Long> lastPlayerActivity = new HashMap<>();
    private final @NotNull ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Value("${server.auto-stop-delay:300000}")
    private long autoStopDelay;

    public ServerController() {
        scheduler.scheduleAtFixedRate(this::checkAndStopIdleServers, 1, 1, TimeUnit.MINUTES);
    }

    @PostMapping("/start")
    public @NotNull String startServer(@RequestParam @NotNull String name) {
        if (runningServers.containsKey(name)) {
            return "Server " + name + " already running!";
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-Xms256M", "-Xmx512M", "-jar", "server.jar", "nogui");
            pb.directory(new File("./servers/" + name));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            runningServers.put(name, process);
            lastPlayerActivity.put(name, System.currentTimeMillis());

            return "✅ Server " + name + " successfully started!";
        } catch (IOException e) {
            return "❌ error while starting: " + name + ": " + e.getMessage();
        }
    }

    @PostMapping("/stop")
    public @NotNull String stopServer(@RequestParam String name) {
        Process process = runningServers.get(name);
        if (process == null) {
            return "⚠ Server " + name + " is not running!";
        }

        process.destroy();
        runningServers.remove(name);
        lastPlayerActivity.remove(name);
        return "⛔ Server " + name + " has stopped!";
    }

    @GetMapping("/status")
    public @NotNull Map<String, String> getServerStatus(@RequestParam String name) {
        Map<String, String> status = new HashMap<>();
        status.put("name", name);
        status.put("status", runningServers.containsKey(name) ? "running" : "stopped");
        status.put("players", String.valueOf(getOnlinePlayers(name)));
        return status;
    }

    private int getOnlinePlayers(String serverName) {
        try (Socket socket = new Socket("localhost", getServerPort(serverName))) {
            return 1; //TODO: implement dynamic player counter
        } catch (IOException e) {
            return 0;
        }
    }

    private int getServerPort(String serverName) {
        return 25565; //TODO: implement dynamic ports
    }

    private void checkAndStopIdleServers() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<String, Long> entry : lastPlayerActivity.entrySet()) {
            String serverName = entry.getKey();
            long lastActivity = entry.getValue();
            if (getOnlinePlayers(serverName) == 0 && (currentTime - lastActivity) > autoStopDelay) {
                stopServer(serverName);
            }
        }
    }
}
