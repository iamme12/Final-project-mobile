package org.daou.personaldashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText Username, email, bank, password;
    Button register;
      DashboardSQLiteOpenHelperr DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


          Username = (EditText) findViewById(R.id.editText_user);
       email = (EditText) findViewById(R.id.editText_email);
      bank=(EditText) findViewById(R.id.editText_bank);
       password=(EditText) findViewById(R.id.editText_pass);
       register = (Button) findViewById(R.id.btn_confim);
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           registeruser();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }).start();
           }
       });

//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String UNa = Username.getText().toString();
//                String EMAIL=  email.getText().toString();
//                String BANK=bank.getText().toString();
//                String PASSWORD=password.getText().toString();
//
//                if(UNa.equals("")) {
//                    Username.setError("Empty");
//                }
//                if(EMAIL.equals("")) {
//                    email.setError("Empty");
//                }
//                if(BANK.equals("")) {
//                    bank.setError("Empty");
//                }
//                if(PASSWORD.equals("")) {
//                    password.setError("Empty");
//                }
//                else {
//                    Boolean checkuser = DB.checkusername(UNa);
//                    if(checkuser==false){
//                        Boolean insert = DB.insertData(UNa,EMAIL, BANK, PASSWORD);
//                        if(insert==true){
//                            Toast.makeText(RegisterActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                            startActivity(intent);
//                        }else{
//                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    else{
//                        Toast.makeText(RegisterActivity.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });


    }
    public void registeruser() throws IOException {
        String UNa = Username.getText().toString();
        String EMAIL=  email.getText().toString();
        String BANK=bank.getText().toString();
        String PASSWORD=password.getText().toString();

        if(UNa.equals("") || EMAIL.equals("") || BANK.equals("") || PASSWORD.equals("")){
            Toast.makeText(getApplicationContext(), "One of the fields is empty", Toast.LENGTH_LONG).show();

            return;
        }
        adduser(UNa, EMAIL, BANK, BCrypt.hashpw(PASSWORD, BCrypt.gensalt()));


    }
    public void adduser(String username, String email, String bank, String password) throws IOException {

        String urlstring= "https://myapp-9f07f-default-rtdb.firebaseio.com/Users/" +username + ".json";
        URL url = new URL(urlstring);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json; utf-8");

        Map<String,String> map = new HashMap<>();

        map.put("username",username);
        map.put("email",email);
        map.put("bank",bank);
        map.put("password",password);



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

    public void confirm(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}