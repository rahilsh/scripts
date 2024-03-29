package in.rsh.scripts.kubernetes;

import in.rsh.scripts.Main;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1PodStatus;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Rahil
 */
public class K8sScript {

  public static void main(String[] args) throws IOException, ApiException {
    K8sScript k8sScript = Main.getInjector().getInstance(K8sScript.class);
    k8sScript.watchPodStatusAndSendAlert();
  }

  private void watchPodStatusAndSendAlert() throws IOException {
    try {
      ApiClient client = Config.defaultClient();
      Configuration.setDefaultApiClient(client);

      CoreV1Api api = new CoreV1Api();
      label:
      while (true) {
        String podName = "pod-name";
        System.out.printf("Checking pod: %s%n", podName);
        V1Pod v1Pod = api.readNamespacedPodStatus(podName, "namespace", null);
        V1PodStatus status = v1Pod.getStatus();
        String phase = status.getPhase();
        System.out.printf("Pod in %s phase%n", phase);
        switch (phase) {
          case "Running":
            Thread.sleep(60000);
            continue;
          case "Failed":
            sendMessage(String.format("Job % failed", podName));
            break label;
          case "Succeeded":
            sendMessage(String.format("Job %s completed", podName));
            break label;
          default:
            sendMessage("In phase: " + phase);
            break label;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      sendMessage("Error while watching pod status");
    }
  }

  private void sendMessage(String message) throws IOException {
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType, "{\n    \"text\":\"" + message + "\"\n}");
    Request request =
        new Request.Builder()
            .url("<incoming webhook url>")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build();
    Response response = client.newCall(request).execute();
    System.out.println("Response: " + (response.body() != null ? response.body().string() : ""));
  }

  private void getAllPodsOfNamespace() throws IOException, ApiException {
    ApiClient client = Config.defaultClient();
    Configuration.setDefaultApiClient(client);

    CoreV1Api api = new CoreV1Api();
    V1PodList list =
        api.listNamespacedPod(
            "namespace", null, null, null, null, null, null, null, null, null, null);
    for (V1Pod item : list.getItems()) {
      System.out.println(item.getMetadata().getName());
    }
  }
}
