package com.example.rakeshvasal.myapplication.ServiceCalls;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 9/17/2016.
 */
public class MakeServiceCall {

    public String makeServiceCall(String url) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("user-key","905eec17a259d75ba116b8467219d657");
            urlConnection.setRequestMethod("POST");


            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exceptionreadingurl", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    public String makeServiceCall(String url, String jsonObject) {

        String status="";
        HttpURLConnection urlConnection = null;

        try {

            URL url_main = new URL(url);
            urlConnection = (HttpURLConnection) url_main.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", " application/json; charset=utf-8");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(jsonObject);
            writer.close();
            int serverResponseCode =urlConnection.getResponseCode();
            String serverResponseMessage = urlConnection.getResponseMessage();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String Data;
            StringBuffer buffer = new StringBuffer();
            if (reader != null) {

                while ((Data = reader.readLine()) != null)
                    buffer.append(Data + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                return buffer.toString();
            }
            // int serverResponseCode =urlConnection.getResponseCode();
            //String serverResponseMessage = urlConnection.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);

            if (serverResponseCode == 200) {
                status = "Success";
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }


        return status;
    }

}
