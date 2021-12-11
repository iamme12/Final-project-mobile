package org.daou.personaldashboard;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageNotesActivity extends AppCompatActivity {

String usname;
    List<String> notesofapi= new ArrayList();

    String[] arr= {"w","s","a"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_notes);

        Intent intent =getIntent();
        usname= intent.getStringExtra("username");
                ListView notesList= findViewById(R.id.list);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    getNotes(usname);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] apinotes= new String[notesofapi.size()];
                            for (int i=0; i<notesofapi.size(); i++){
                                apinotes[i]=notesofapi.get(i);

                            }
                        ArrayAdapter adapter= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,(String[])apinotes);
                            notesList.setAdapter(adapter);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
    public void getNotes(String username) throws IOException, JSONException {
       String urll="https://myapp-9f07f-default-rtdb.firebaseio.com/Users/" +username+"/notes.json";
        URL url = new URL(urll);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //Reading the response
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        String content = "";
        while ((inputLine = in.readLine()) != null) {
            content = content + inputLine;
        }
        in.close();
        con.disconnect();


        JSONObject object = new JSONObject(content);

        // iteration between note keys and put them in an array
        Iterator<String> keysIterator = object.keys();
        String[] keys = new String[object.length()];
        String[] notesdis= new String[object.length()];

        int j=0;

        while (keysIterator.hasNext())
        {
            keys[j] = keysIterator.next();

            notesdis[j]= object.getString(keys[j]);
            System.out.println(notesdis[j]);
            notesofapi.add(notesdis[j]);


            j++;
        }
        System.out.println(notesdis.toString());
    }
}