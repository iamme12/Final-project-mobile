package org.daou.personaldashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.PowerManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btLocation = findViewById(R.id.btn_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);


        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if (ActivityCompat.checkSelfPermission(LocationActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//When permission granted
                    getLocation();
                }else{
                    //when permission denied
                    ActivityCompat.requestPermissions(LocationActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //initialize location
                Location location = task.getResult();
                if (location != null){

                    try {
                        //Initialize geoCoder
                        Geocoder geocoder = new Geocoder(LocationActivity.this,
                                Locale.getDefault());
//Initialize address
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        //set latitude on textview
                        textView1.setText(Html.fromHtml(
                                "<font color='#d7bbff'><b>Latitude :</b><br></font>"
                                +addresses.get(0).getLatitude()
                        ));
                        //set Longitude
                        textView2.setText(Html.fromHtml(
                                "<font color='#d7bbff'><b>Longitude :</b><br></font>"
                                        +addresses.get(0).getLongitude()
                        ));

                        //set country name
                        textView3.setText(Html.fromHtml(
                                "<font color='#d7bbff'><b>Country Name :</b><br></font>"
                                        +addresses.get(0).getCountryName()
                        ));
                        //set locality
                        textView4.setText(Html.fromHtml(
                                "<font color='#d7bbff'><b>Locality :</b><br></font>"
                                        +addresses.get(0).getLocality()
                        ));

                        textView5.setText(Html.fromHtml(
                                "<font color='#d7bbff'><b>Address :</b><br></font>"
                                        +addresses.get(0).getAddressLine(0)
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}