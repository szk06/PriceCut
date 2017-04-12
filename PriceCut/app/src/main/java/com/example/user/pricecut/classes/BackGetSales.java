package com.example.user.pricecut.classes;

import android.app.AlertDialog;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by user on 4/10/2017.
 */
public class BackGetSales extends AsyncTask<String,Void,String> {
    Context context;

    public BackGetSales (Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("doIn", "yes");
        String login_url = "http://192.168.97.1:8080/imagine/discounts_get.php";
        try{

            Log.d("Try","start");

            String user_name = params[0];
            String latitude = params[1] ;
            String longitude = params[2];
            String distance_thresh = params[3];

            Log.d("user_name_back",user_name);
            Log.d("latitude_back",latitude);
            Log.d("longitude_back",longitude);
            Log.d("distance_thresh",distance_thresh);

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")
                    +"&"+URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")
                    +"&"+URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8")
                    +"&"+URLEncoder.encode("distance_thresh","UTF-8")+"="+URLEncoder.encode(distance_thresh,"UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
                Log.d("In results",result);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}