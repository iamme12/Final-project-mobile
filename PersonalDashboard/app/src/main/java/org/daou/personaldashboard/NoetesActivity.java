package org.daou.personaldashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NoetesActivity extends AppCompatActivity {
    String un;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        un= intent.getStringExtra("username");
        setContentView(R.layout.activity_noetes);
        Button savenote= findViewById(R.id.post);
        Button viewnotes= findViewById(R.id.view);
        viewnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getApplicationContext(),PageNotesActivity.class);
                intent.putExtra("username", un);
                startActivity(intent);
            }
        });
        EditText titlenote= findViewById(R.id.titlenote);
        EditText description= findViewById(R.id.description);
        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            saveNote(titlenote.getText().toString(),description.getText().toString(), un);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


    }

     public void saveNote( String title, String disc, String username) throws IOException, JSONException {
        System.out.println(username);
        String urll="https://myapp-9f07f-default-rtdb.firebaseio.com/Users/"+ username+"/notes.json";
        URL url = new URL(urll);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PATCH");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; utf-8");

        Map<String,String> map = new HashMap<>();

        map.put(title,disc);



        String jsonString = new JSONObject(map).toString();




        System.out.println(jsonString);


        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }

    }


}