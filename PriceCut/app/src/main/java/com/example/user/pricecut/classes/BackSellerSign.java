package com.example.user.pricecut.classes;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

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
 * Created by Sami Kanafani on 4/7/2017.
 */
public class BackSellerSign extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    public BackSellerSign (Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("doIn","yes");
        String login_url = "http://192.168.97.1:8080/imagine/sign_seller.php";
        try{
            Log.d("Try","start");
            String full_name = params[0];
            String user_name = params[1];
            String password = params[2];
            String mobile_number = params[3];
            String email = params[4];
            String description = params[5];
            String latitude = params[6];
            String longitude = params[7];

            Log.d("Email",email);
            Log.d("full_name",full_name);
            Log.d("user_name",user_name);
            Log.d("mobile_number",mobile_number);
            Log.d("password",password);

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_data = URLEncoder.encode("full_name", "UTF-8")+"="+URLEncoder.encode(full_name,"UTF-8")+"&"
                    +URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")
                    +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")
                    +"&"+URLEncoder.encode("mobile_number","UTF-8")+"="+URLEncoder.encode(mobile_number,"UTF-8")
                    +"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")
                    +"&"+URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(description,"UTF-8")
                    +"&"+URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")
                    +"&"+URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8");

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
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Register Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
