package com.example.myapp;

import android.content.Intent;
import android.os.Looper;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Handler;

public class PushNotification {

    public void RequestNotification( final String title, final String Body, final String Token)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                pushNotification(title,Body,Token);
            }
        }).start();
    }


    private void pushNotification(String title,String Body,String Token) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData = new JSONObject();
        try {
            try {
                jNotification.put("title",title);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                jNotification.put("body",Body);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                jNotification.put("sound", "R.raw.notificationsound");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                jNotification.put("click_action", "OPEN_ACTIVITY_1");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                jNotification.put("badge", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                jNotification.put("icon", "R.mipmap.ic_launcher");
            } catch (JSONException e) {
                e.printStackTrace();
            }



            JSONArray ja = new JSONArray();
            ja.put(Token);
            jPayload.put("registration_ids", ja);



            jData.put("Message", Body);
            jData.put("Title",title);
            jData.put("Sound","notificationsound");
            jPayload.put("notification",jNotification);
            jPayload.put("priority", "high");
            jPayload.put("data", jData);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","key=AAAA33gNHGw:APA91bF_D5Kg9MfHL8OOWLMC-5vbLSPPYnWT3rws6hoXMF2pwOKWuCH3Xr11qUEAfKixDVdvCubj6-jzJzb6MHtoTSyAml58CxdE7bU-o2xMH1rvfiC4Hqf3wtu7atZ0beGzDTCsYneX");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream IS) {
        Scanner s = new Scanner(IS).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

}