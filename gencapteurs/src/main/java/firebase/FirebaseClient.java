package firebase;

import model.LightCapteur;
import model.TemperatureCapteur;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FirebaseClient {

    private static final String firebase_baseUrl = "https://console.firebase.google.com/u/0/project/smarthouse-31e81";

    private Firebase firebase;
    private FirebaseResponse response;

    public FirebaseClient() {
    }

    public void initFirebaseClient() {
        try {
            firebase = new Firebase(firebase_baseUrl);
        } catch (FirebaseException e) {
            e.printStackTrace();
        }
    }

    public void sentPutRequet(LightCapteur lightCapteur) {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        try {
            dataMap.put("capteurs", lightCapteur);
            response = firebase.put(dataMap);
            System.out.println("\n\nResult of PUT (for the test-PUT to fb4jDemo-root):\n" + response);
        } catch (JacksonUtilityException | FirebaseException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sentPutRequet(TemperatureCapteur temperatureCapteur) {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        try {
            dataMap.put("capteurs", temperatureCapteur);
            response = firebase.put(dataMap);
            System.out.println("\n\nResult of PUT (for the test-PUT to fb4jDemo-root):\n" + response);
        } catch (JacksonUtilityException | FirebaseException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}