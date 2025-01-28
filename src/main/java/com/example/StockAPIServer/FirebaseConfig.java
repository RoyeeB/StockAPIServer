package com.example.StockAPIServer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Bean
    public Firestore getFirestore() throws Exception {
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/main/resources/stockapiserver-b1e5e-firebase-adminsdk-yf15l-0c4d2856be.json")))
                .build();

        logger.info("Successfully connected to Firestore!"); // הודעה בלוג
        return firestoreOptions.getService();
    }
}
