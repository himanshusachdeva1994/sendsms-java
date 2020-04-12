package com.himanshu;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.Random;

public class SendSmsFast2Sms {

    public static String API_KEY;
    public static String SENDER_ID;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream input = new FileInputStream("src/main/resources/config/config-fast2sms.properties")) {
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and assign to variables
            API_KEY = prop.getProperty("api_key");
            SENDER_ID = prop.getProperty("sender_id");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendSms(String message, String number) {
        try {
            message = URLEncoder.encode(message, "UTF-8");
            String language = "english";
            String route = "p";
            String myUrl = "https://www.fast2sms.com/dev/bulk?authorization=" + API_KEY +
                    "&sender_id=" + SENDER_ID +
                    "&message=" + message +
                    "&language=" + language +
                    "&route=" + route +
                    "&numbers=" + number;

            URL url = new URL(myUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");
            System.out.println("Wait..............");

            int code = con.getResponseCode();
            System.out.println("Response code : " + code);
            StringBuffer response = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                response.append(line);
            }

            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sendSmsTo = "xxxxxxxxxx";

        // Generating 4-digit random OTP
        int otp = new Random().nextInt(9999);
        SendSmsFast2Sms.sendSms("Hi there. OTP is " + otp, sendSmsTo);
    }
}
