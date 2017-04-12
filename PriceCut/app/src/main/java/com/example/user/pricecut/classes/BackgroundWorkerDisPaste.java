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
 * Created by user on 4/7/2017.
 */
public class BackgroundWorkerDisPaste extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    public BackgroundWorkerDisPaste(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("doIn","yes");
        String login_url = "http://192.168.97.1:8080/imagine/images.php";
        try{
            Log.d("Try","start");
            String dis_title = params[0];

            String dis_description = params[1];
            String dis_category = params[2];
            String dis_percent = params[3];
            String dis_proname = params[4];
            String dis_fromprice = params[5];
            String dis_afterprice = params[6];
            String encoded_string = params[7];
            String user_name;
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("encoded_string", "UTF-8")+"="+URLEncoder.encode(encoded_string,"UTF-8")+"&"
                +URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(dis_title,"UTF-8")+"&"
                    +URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(dis_description,"UTF-8")
                    +"&"+URLEncoder.encode("category","UTF-8")+"="+URLEncoder.encode(dis_category,"UTF-8")
                    +"&"+URLEncoder.encode("percent","UTF-8")+"="+URLEncoder.encode(dis_percent,"UTF-8")
                    +"&"+URLEncoder.encode("proname","UTF-8")+"="+URLEncoder.encode(dis_proname,"UTF-8")
                    +"&"+URLEncoder.encode("fromprice","UTF-8")+"="+URLEncoder.encode(dis_fromprice,"UTF-8")
                    +"&"+URLEncoder.encode("afterprice","UTF-8")+"="+URLEncoder.encode(dis_afterprice,"UTF-8");
            Log.d("encode",encoded_string);
            Log.d("encode",dis_afterprice);
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
        alertDialog.setTitle("Posting Discount Status");
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
