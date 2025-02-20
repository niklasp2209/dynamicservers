package de.bukkitnews.docker.service;

import de.bukkitnews.docker.kubernetes.KubernetesManager;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KubernetesService {

    private final @NotNull KubernetesManager kubernetesManager;


    public @NotNull String startServer(@NotNull String serverName) {
        return kubernetesManager.startMinecraftServerPod(serverName);
    }

    public @NotNull String stopServer(@NotNull String serverName) {
        return kubernetesManager.stopMinecraftServerPod(serverName);
    }

    public @NotNull String getServerStatus(@NotNull String serverName) {
        return kubernetesManager.getServerStatus(serverName);
    }
}
