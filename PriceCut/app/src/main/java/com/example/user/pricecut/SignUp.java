package com.example.user.pricecut;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.user.pricecut.classes.BackBuyerSign;
import com.example.user.pricecut.classes.BackSellerSign;
import com.example.user.pricecut.classes.GPSTracker;

public class SignUp extends AppCompatActivity {



    private EditText edit_full_name;
    private EditText edit_user_name;
    private EditText edit_password;
    private EditText edit_mobile_number;
    private EditText edit_email;
    private EditText edit_description;
    private CheckBox check_ifseller;
    private boolean seller;
    private Button locater;
    private LocationManager locationManager;
    private LocationListener listener;

    private String latitude;
    private String longitude;

    private String full_name;
    private String user_name;
    private String password;
    private String mobile_number;
    private String email;
    private String description;
    private boolean got_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        seller = false;
        got_location = false;
        //setActivityBackgroundColor(back_color);
        edit_full_name = (EditText) findViewById(R.id.full_name);
        edit_user_name = (EditText) findViewById(R.id.user_name);
        edit_password = (EditText) findViewById(R.id.password);
        edit_mobile_number = (EditText) findViewById(R.id.mobilenumber);
        edit_email = (EditText) findViewById(R.id.email);
        edit_description = (EditText) findViewById(R.id.description);
        locater = (Button) findViewById(R.id.locater);
        //seller_check
        check_ifseller = (CheckBox) findViewById(R.id.seller_check);
        check_ifseller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    seller = true;
                    edit_description.setVisibility(View.VISIBLE);
                    locater.setVisibility(View.VISIBLE);

                }
                else {
                    seller = false;
                    edit_description.setVisibility(View.INVISIBLE);
                    locater.setVisibility(View.INVISIBLE);
                }

            }
        });
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                got_location = true;
                //t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                Log.d("Longitude",String.valueOf(location.getLongitude()));
                Log.d("Latitude",String.valueOf(location.getLatitude()));
                latitude = String.valueOf(location.getLongitude());
                longitude = String.valueOf(location.getLatitude());
                stop_search();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        configure_button();
        int back_color = Color.parseColor("#184064");
        setActivityBackgroundColor(back_color);
    }
    public void stop_search(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        locationManager.removeUpdates(listener);
        locationManager = null;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }
    public void Join(View v){

        Log.d("Seller Value", String.valueOf(seller));
        if(seller==true){
            //seller registration
            full_name = edit_full_name.getText().toString();
            user_name = edit_user_name.getText().toString();
            password = edit_password.getText().toString();
            mobile_number = edit_mobile_number.getText().toString();
            email = edit_email.getText().toString();
            description = edit_description.getText().toString();
            if(got_location==true){
                BackSellerSign backgroundseller = new BackSellerSign(this);
                Log.d("Starting","Sending");
                backgroundseller.execute(full_name,user_name,password,mobile_number,email,description,longitude,latitude);

            }else{
                Log.d("Server error","can't update");
            }

        }
        else{
            //customer registration

            full_name = edit_full_name.getText().toString();
            user_name = edit_user_name.getText().toString();
            password = edit_password.getText().toString();
            mobile_number = edit_mobile_number.getText().toString();
            email = edit_email.getText().toString();
            Log.d("CustomerName",full_name);
            BackBuyerSign backgroundbuyer = new BackBuyerSign(this);
            backgroundbuyer.execute(full_name,user_name,password,mobile_number,email);




        }
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);

    }



    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }



    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        locater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
    }


}
