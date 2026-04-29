package com.nguyenkhanhduy.restaurant_app.Utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class GoogleTokenVerifier {

    private static final String CLIENT_ID = "1025657299481-drpdf0f8s7ndgeffb9r2ona2uo2ju4ud.apps.googleusercontent.com";

    private static final GoogleIdTokenVerifier verifier =
            new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    new GsonFactory()
            )
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

    public static GoogleIdToken.Payload verify(String idTokenString) {

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                throw new RuntimeException("Invalid Google ID token");
            }

            return idToken.getPayload();

        } catch (Exception e) {
            throw new RuntimeException("Failed to verify Google token", e);
        }
    }



}
