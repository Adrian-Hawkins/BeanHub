package client.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class Auth {
    private String ClientId;

    private static String BASE_URL;

    private String username;
    private static String accessToken;

    public Auth(String ClientId, String burl) {
        BASE_URL = burl;
        this.ClientId = ClientId;
    }

    public Boolean loginFlow() {
        try {
            System.out.print(Colors.RED);
            this.username = GetCode();
            Map<String, String> body = new HashMap<String, String>();
            if(this.username == null)
                return false;
            body.put("username", this.username);

            Gson gson = new Gson();
            String jsonBody = gson.toJson(body);
            String url = BASE_URL + "/auth/login";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody)) 
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (IOException | InterruptedException e) {
            return false;
        } finally {
            System.out.println(Colors.RESET);
        }
    }
    public String GetCode() throws IOException, InterruptedException {
        String url = "https://github.com/login/device/code";
        String requestBody = "{\"client_id\": \"" + ClientId + "\", \"scope\": \"user\"}";
        if(ClientId == null) {
            System.out.println("Client ID is not set. Please set the Client ID to continue.");
            return null;
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        String userCode = jsonObject.get("user_code").getAsString();
        String deviceCode = jsonObject.get("device_code").getAsString();
        Integer expiresIn = jsonObject.get("expires_in").getAsInt();
        System.out.println("Please go to https://github.com/login/device and type in the below user code");
        System.out.println("User Code: " + userCode);
        String accessToken = ValidateLogin(expiresIn, deviceCode, this.ClientId);
        if(accessToken != null) {
            String login = GetLogin(accessToken);
            System.out.println("Logged in as: " + login);
            return login;
        }
        return null;
    }

    private static String ValidateLogin(Integer expiresIn, String deviceCode, String CID) throws IOException, InterruptedException {
        String url = "https://github.com/login/oauth/access_token";
        Integer time = expiresIn;
        do {
            String requestBody = String.format("client_id=%s&device_code=%s&grant_type=urn:ietf:params:oauth:grant-type:device_code", CID, deviceCode);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            if (jsonObject.get("access_token") != null) {
                accessToken = jsonObject.get("access_token").getAsString();
                return jsonObject.get("access_token").getAsString();
            }
            Thread.sleep(5000);
            time -= 5;
        } while(time > 0);
        return null;
    }

    private static String GetLogin(String accessToken) throws IOException, InterruptedException {
        String url = "https://api.github.com/user";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        return jsonObject.get("login").getAsString();       
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
