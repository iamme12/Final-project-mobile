package org.daou.personaldashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    DashboardSQLiteOpenHelperr DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.UserName);
        password = (EditText) findViewById(R.id.Password);
        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
//        DB = new DashboardSQLiteOpenHelperr(this);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String user = username.getText().toString();
//                String pass = password.getText().toString();
//
//                if(user.equals("")) {
//                    username.setError("Empty Username");
//                }
//                if(pass.equals("")) {
//                    password.setError("Empty Pass!");
//                }
//                else{
//                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
//                    if(checkuserpass==true){
//                        Toast.makeText(MainActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
//                        Intent intent  = new Intent(getApplicationContext(), MenuActivity.class);
//                        startActivity(intent);
//                    }else{
//                        Toast.makeText(MainActivity.this, "Invalid!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });




    }

    public void login(){
       if(username.getText().toString().equals("") || password.getText().toString().equals("")){
           Toast.makeText(getApplicationContext(), "One of the fields is empty", Toast.LENGTH_LONG).show();
           return;

       }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    authenticate();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void authenticate() throws IOException, JSONException {

       String authentic= "https://myapp-9f07f-default-rtdb.firebaseio.com/Users/" +username.getText().toString() + ".json";
        URL url = new URL(authentic);
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

        if(content.equals("null")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Username not correct", Toast.LENGTH_LONG).show();
                }
            });


            return;
        }
        System.out.println(content);
        JSONObject object = new JSONObject(content);

        System.out.println(object);



        if(BCrypt.checkpw(password.getText().toString(),object.getString("password"))){
            gotomenu(object);
        }
        else{

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_LONG).show();
                }
            });

            return;
        }
    }
    public void gotomenu(JSONObject object) throws JSONException {
        Intent intent = new Intent(this, MenuActivity.class);
        System.out.println(object.getString("username"));
        intent.putExtra("username", object.getString("username"));
        startActivity(intent);
    }
    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}