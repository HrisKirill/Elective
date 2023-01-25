package com.hristoforov.elective.utils;

import com.hristoforov.elective.constants.CaptchaParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import static com.hristoforov.elective.constants.CaptchaParams.ACCEPT_LANGUAGE;

public class VerifyRecaptcha {
    private static final Logger LOGGER = LogManager.getLogger(VerifyRecaptcha.class);
    private final String url;
    private final String secretKey;
    private final String method;
    private final String acceptLanguage;


    public VerifyRecaptcha(String url, String secretKey, String method, String acceptLanguage) {
        this.url = url;
        this.secretKey = secretKey;
        this.method = method;
        this.acceptLanguage = acceptLanguage;
    }

    public boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }

        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Accept-Language", acceptLanguage);
            String postParams = "secret=" + secretKey + "&response=" + gRecaptchaResponse;
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postParams);
                wr.flush();
            }

            StringBuffer response = new StringBuffer();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            try (JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
                JsonObject jsonObject = jsonReader.readObject();
                return jsonObject.getBoolean("success");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
