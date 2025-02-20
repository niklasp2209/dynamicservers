package de.bukkitnews.docker.kubernetes;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.openapi.*;
import io.kubernetes.client.util.Config;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class KubernetesManager {

    private final @NotNull CoreV1Api api;

    public KubernetesManager() throws IOException {
        this.api = new CoreV1Api();
        KubernetesClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
    }

    public @NotNull String startMinecraftServerPod(@NotNull String serverName) {
        V1Pod pod = new V1Pod();
        pod.setApiVersion("v1");
        pod.setKind("Pod");

        V1ObjectMeta metadata = new V1ObjectMeta();
        metadata.setName(serverName);
        pod.setMetadata(metadata);

        V1Container container = new V1Container();
        container.setName(serverName);
        container.setImage("itzg/minecraft-server");
        container.setEnv(Arrays.asList(new V1EnvVar().name("SERVER_NAME").value(serverName)));

        V1PodSpec podSpec = new V1PodSpec();
        podSpec.setContainers(Arrays.asList(container));
        pod.setSpec(podSpec);

        try {
            api.createNamespacedPod("default", pod, null, null, null);
            return "✅ Minecraft Server pod " + serverName + " started in Kubernetes!";
        } catch (ApiException e) {
            return "❌ Error starting pod in Kubernetes: " + e.getMessage();
        }
    }

    public @NotNull String stopMinecraftServerPod(@NotNull String serverName) {
        try {
            api.deleteNamespacedPod(serverName, "default", null, null, null, null, null, null);
            return "⛔ Minecraft Server pod " + serverName + " stopped in Kubernetes!";
        } catch (ApiException e) {
            return "❌ Error stopping pod in Kubernetes: " + e.getMessage();
        }
    }

    public @NotNull String getServerStatus(@NotNull String serverName) {
        try {
            V1Pod pod = api.readNamespacedPod(serverName, "default", null, null, null);
            return pod.getStatus().getPhase();
        } catch (ApiException e) {
            return "❌ Error fetching pod status in Kubernetes: " + e.getMessage();
        }
    }
}
