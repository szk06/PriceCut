package com.example.user.pricecut;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.pricecut.classes.AdapterDiscount;
import com.example.user.pricecut.classes.BackGetSales;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by Sami Kanafani on 4/9/2017.
 */
public class Tab1Discounts extends Fragment {
    private EditText search;
    private String user_name;
    private LocationManager locationManager;
    private LocationListener listener;
    private boolean got_location;
    Button getfromServer;
    private String latitude;
    private String longitude;
    private ArrayList<discountobj> list_discounts;
    private boolean display;
    private AdapterDiscount myAdapter;
    private ListView listView;
    private String max_distance;
    private Button disco;
    private EditText edit_dis_thresh;
    public Tab1Discounts(String user){
        user_name =user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab1discounts, container, false);
        display =false;
        edit_dis_thresh = (EditText) rootView.findViewById(R.id.max_distance);
        //Get the information from the server
        disco = (Button) rootView.findViewById(R.id.submit_discounts);
        disco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Ane", "Buttonn Clicked");
                max_distance = edit_dis_thresh.getText().toString();
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                listener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        got_location = true;
                        //t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                        Log.d("Longitude", String.valueOf(location.getLongitude()));

                        Log.d("Latitude", String.valueOf(location.getLatitude()));
                        latitude = String.valueOf(location.getLongitude());
                        longitude = String.valueOf(location.getLatitude());
                        triggerd_afterlocation(rootView);

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

            }
        });


        list_discounts = new ArrayList<discountobj>();
        myAdapter = new AdapterDiscount(getActivity(), android.R.layout.simple_expandable_list_item_1,list_discounts);

        ListView listView = (ListView) rootView.findViewById(R.id.mobile_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Log.d("Pos",String.valueOf(i));
                Intent intent = new Intent(getActivity(), Discount.class);
                Bundle b = new Bundle();
                b.putInt("key", i);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        Log.d("Username_in_discounts", user_name);

        //Code for getting the GPS location





        return rootView;
    }

    public View triggerd_afterlocation(View v){
        if(got_location){
            Log.d("GPS coordinates", "Captured");

            if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                            ,10);
                }
                return v;
            }
            locationManager.removeUpdates(listener);
            locationManager = null;
            Log.d("user_name", user_name);
            Log.d("latitude",latitude);
            Log.d("longitude", longitude);
            BackGetSales backme = new BackGetSales(this.getContext());
            backme.execute(user_name, latitude, longitude,max_distance);
            Log.d("Afterexec","Yes");
            String out="";
            try{

                out = backme.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("Out_value_GetSales", out);
            if(out!= null){
                String dis_id;
                String discount_title ;
                String discount_category;
                String seller_name;
                String dis_percent;
                String sale_rating;
                String path_image;
                String distance_from;
                try{
                    JSONObject jsonObj = new JSONObject(out);
                    JSONArray user_discounts = jsonObj.getJSONArray("discounts");
                    for (int i=0;i<user_discounts.length(); i++){
                        JSONObject c = user_discounts.getJSONObject(i);
                        dis_id = c.getString("dis_id");
                        discount_title = c.getString("sale_title");
                        discount_category = c.getString("sale_cat");
                        seller_name = c.getString("seller_name");
                        dis_percent =c.getString("sale_perc");
                        sale_rating = c.getString("sale_rating");
                        path_image = c.getString("path");
                        distance_from = c.getString("distance");
                        Log.d("Discount_id",dis_id);
                        Log.d("discount_title",discount_title);
                        Log.d("discount_category",discount_category);
                        Log.d("seller_name",seller_name);
                        Log.d("dis_percent",dis_percent);
                        Log.d("sale_rating",sale_rating);
                        Log.d("path",path_image);
                        Log.d("distance",distance_from);


                        discountobj ss = new discountobj("Discount Title: "+discount_title,"Discount Category: " +discount_category,"Store Name: "+seller_name
                                ,"Rating: "+sale_rating,"Sale Percentage: "+dis_percent+"%",path_image,"Distance from here: "+distance_from+" KM");
                        list_discounts.add(ss);



                    }


                    myAdapter = new AdapterDiscount(getActivity(), android.R.layout.simple_expandable_list_item_1,list_discounts);

                    ListView listView = (ListView) v.findViewById(R.id.mobile_list);
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_expandable_list_item_1
                , mobileArray);
        */

                    listView.setAdapter(myAdapter);
                    display =true;


                }catch (final JSONException e){
                    Log.d("JSON error","Occured");
                }


            }
        }else {

            Log.d("Error","can't contact server");
        }


        return v;
    }
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

            locationManager.requestLocationUpdates("gps", 5000, 0, listener);
    }

}
