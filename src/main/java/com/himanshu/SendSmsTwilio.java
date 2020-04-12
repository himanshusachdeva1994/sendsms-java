package com.himanshu;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class SendSmsTwilio {

    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;
    public static String SENDER_MOBILE_NUMBER;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream input = new FileInputStream("src/main/resources/config/config-twilio.properties")) {
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and assign to variables
            ACCOUNT_SID = prop.getProperty("account_sid");
            AUTH_TOKEN = prop.getProperty("auth_token");
            SENDER_MOBILE_NUMBER = prop.getProperty("sender_mobile_number");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String sendSmsTo = "+91xxxxxxxxxx";

        // Generating 4-digit random OTP
        int otp = new Random().nextInt(9999);

        Message message = Message.creator(new PhoneNumber(sendSmsTo),
                new PhoneNumber(SENDER_MOBILE_NUMBER),
                "Hi there. OTP is " + otp).create();

        System.out.println("SID: " + message.getSid() + ", OTP: " + otp);
    }
}
