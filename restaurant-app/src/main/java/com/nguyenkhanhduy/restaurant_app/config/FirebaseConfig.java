package com.nguyenkhanhduy.restaurant_app.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    public void initializeFirebase() throws Exception{
        FileInputStream serviceAccount = new FileInputStream("config/seriveAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).setStorageBucket("my-bucket.app").build();

        FirebaseApp.initializeApp();
    }
}
