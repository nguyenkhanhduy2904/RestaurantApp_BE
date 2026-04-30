package com.nguyenkhanhduy.restaurant_app.Utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.nguyenkhanhduy.restaurant_app.DeviceToken.DeviceTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
    private final DeviceTokenService deviceTokenService;

    @Autowired
    public FcmService(DeviceTokenService deviceTokenService) {
        this.deviceTokenService = deviceTokenService;
    }


    public String getAccessToken() throws IOException {

        InputStream serviceAccount;

        String json = System.getenv("FIREBASE_CONFIG_JSON");

        if (json != null && !json.isEmpty()) {
            // Cloud / production
            serviceAccount = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        } else {
            // Local dev fallback (classpath resource)
            serviceAccount = getClass()
                    .getClassLoader()
                    .getResourceAsStream("firebase-service-account.json");

            if (serviceAccount == null) {
                throw new RuntimeException("firebase-service-account.json not found in resources");
            }
        }

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

            Message message = new Message(fcmToken, notification, data);
            FcmRequest requestBody = new FcmRequest(message);

            HttpEntity<FcmRequest> request = new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(url, request, String.class);

            System.out.println("FCM response: " + response);

        } catch (HttpClientErrorException e) {

            String body = e.getResponseBodyAsString();
            System.err.println("FCM failed: " + body);

            // 🔥 IMPORTANT: handle invalid token
            if (e.getStatusCode().value() == 404 ||
                    body.contains("UNREGISTERED")) {

                System.out.println("Deleting invalid FCM token: " + fcmToken);

                deviceTokenService.deleteToken(fcmToken);
            }

            // DO NOT rethrow → order must continue

        } catch (IOException e) {
            System.err.println("FCM auth failed: " + e.getMessage());
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
    public void sendNewOrderNotification(String fcmToken){
        sendMessage(
                fcmToken,
                new Notification("NEW_ORDER", "A new order just arrived"),
                Map.of(
                        "title", "New Order",
                        "body", "A new order just arrived",
                        "type", "NEW_ORDER"
                )
        );
    }
}
