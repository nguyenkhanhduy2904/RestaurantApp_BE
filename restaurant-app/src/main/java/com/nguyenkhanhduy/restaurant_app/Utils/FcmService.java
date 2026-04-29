package com.nguyenkhanhduy.restaurant_app.Utils;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FcmService {


    public String getAccessToken() throws IOException {

        String json = System.getenv("FIREBASE_CONFIG_JSON");

        if (json == null || json.isEmpty()) {
            throw new RuntimeException("FIREBASE_CONFIG_JSON env variable not set");
        }

        InputStream serviceAccount =
                new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(serviceAccount)
                .createScoped(List.of("https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }

    public void sendMessage(String fcmToken, Notification notification, Map<String, String> data) {
        try {
            String url = "https://fcm.googleapis.com/v1/projects/fir-cloudmessage-c32d6/messages:send";

            String accessToken = getAccessToken();

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper mapper = new ObjectMapper();

            Message message = new Message(fcmToken, notification, data);
            FcmRequest requestBody = new FcmRequest(message);

            String json = mapper.writeValueAsString(requestBody);
            System.out.println("FCM JSON: " + json);

            HttpEntity<FcmRequest> request = new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(url, request, String.class);

            System.out.println("FCM response: " + response);

        } catch (IOException e) {
            System.err.println("FCM send failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendUpdateOrderNotification(String fcmToken){
        sendMessage(
                fcmToken,
                new Notification("ORDER_UPDATE", "Your order have been updated"),
                Map.of(
                        "title", "Order Updated",
                        "body", "Your Order have been updated",
                        "type", "ORDER_UPDATED"
                )
        );
    }
}
