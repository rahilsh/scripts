package in.rsh.script.kubernetes;

import in.rsh.script.Main;
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

/** @author Rahil */
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
      while (true) {
        String podName = "scribe-lightning-shard-a-ljw74";
        System.out.printf("Checking pod: %s%n", podName);
        V1Pod v1Pod = api.readNamespacedPodStatus(podName, "rigel-clusters-prod", null);
        V1PodStatus status = v1Pod.getStatus();
        String phase = status.getPhase();
        System.out.printf("Pod in %s phase%n", phase);
        if (phase.equals("Running")) {
          Thread.sleep(60000);
          continue;
        } else if (phase.equals("Failed")) {
          sendMessage(String.format("Job % failed", podName));
          break;
        } else if (phase.equals("Succeeded")) {
          sendMessage(String.format("Job % completed", podName));
          break;
        } else {
          sendMessage("In phase: " + phase);
          break;
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
            .url(
                "https://chat.googleapis.com/v1/spaces/AAAApPzIfDU/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=bVekREAtZVvc18y8smZSKyvEKhA1CCmzK7VsDOQ1EbI%3D")
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
            "rigel-clusters-prod", null, null, null, null, null, null, null, null, null);
    for (V1Pod item : list.getItems()) {
      System.out.println(item.getMetadata().getName());
    }
  }
}
