package org.daou.personaldashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
String usn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent usnIntent= getIntent();
       usn= usnIntent.getStringExtra("username");
        Toolbar Toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(Toolbar);

        DashboardFragment dashboardFragment = new DashboardFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout_items, dashboardFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                Toast.makeText(this, " My Location", Toast.
                        LENGTH_SHORT).show();

                LocationActivity locationActivity = new LocationActivity();
                Intent intLoc = new Intent(this, LocationActivity.class);
                startActivity(intLoc);
                return true;
            case R.id.action_notes:

                NoetesActivity nt = new NoetesActivity();
                Intent intent = new Intent(this, NoetesActivity.class);
                intent.putExtra("username",usn );
                startActivity(intent);

                return true;

            case R.id.action_email:
               // EmailFragment emailFragment = new EmailFragment();
               // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
             //   ft.replace(R.id.content_frame, emailFragment);
              //  ft.commit();
                EmailActivity emailActivity = new EmailActivity();
                Intent intent3 = new Intent(this, EmailActivity.class);
                startActivity(intent3);

                  Toast.makeText(this, "Send an Email", Toast
                 .LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:

                SettingsActivity settingsActivity= new SettingsActivity();
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);


                Toast.makeText(this, "Access Settings", Toast.
                        LENGTH_SHORT).show();


                return true;
            case R.id.action_logout:
                Toast.makeText(this, "You Logout", Toast.
                        LENGTH_SHORT).show();

                return true;

            case R.id.action_calendar:
                CalendarActivity calendarActivity = new CalendarActivity();
                Intent intent6 = new Intent(this, CalendarActivity.class);
                startActivity(intent6);

                Toast.makeText(this, "Calendar", Toast.
                        LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user’s action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
