package de.bukkitnews.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class DockerService {

    private final @NotNull DockerClient dockerClient;

    public DockerService() {
        this.dockerClient = DockerClientBuilder.getInstance().build();
    }

    public @NotNull String startServer(String serverName) {
        try {
            dockerClient.createContainerCmd("itzg/minecraft-server")
                    .withName(serverName)
                    .exec();
            dockerClient.startContainerCmd(serverName).exec();
            return "✅ Docker container for server " + serverName + " started.";
        } catch (Exception e) {
            return "❌ Error starting Docker container for server " + serverName + ": " + e.getMessage();
        }
    }

    public @NotNull String stopServer(String serverName) {
        try {
            dockerClient.stopContainerCmd(serverName).exec();
            dockerClient.removeContainerCmd(serverName).exec();
            return "⛔ Docker container for server " + serverName + " stopped.";
        } catch (Exception e) {
            return "❌ Error stopping Docker container for server " + serverName + ": " + e.getMessage();
        }
    }

    public @NotNull String getServerStatus(String serverName) {
        try {
            InspectContainerResponse container = dockerClient.inspectContainerCmd(serverName).exec();
            return "Server " + serverName + " is " + container.getState().getStatus();
        } catch (Exception e) {
            return "❌ Error fetching Docker container status for server " + serverName + ": " + e.getMessage();
        }
    }
}
