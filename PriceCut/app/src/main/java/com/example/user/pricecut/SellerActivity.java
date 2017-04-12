package com.example.user.pricecut;


import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.pricecut.classes.BackgroundWorkerDisPaste;
import com.example.user.pricecut.classes.Session;

import org.w3c.dom.Text;

public class SellerActivity extends AppCompatActivity {

    private Button button;
    private String encoded_string, image_name;
    private String dis_title, dis_description,dis_category,dis_percent,dis_proname,dis_fromprice,dis_afterprice;
    private Bitmap bitmap;
    private File file;

    private Uri file_uri;
    private EditText edit_dis_title;
    private EditText edit_dis_description;
    private EditText edit_dis_category;
    private EditText  edit_dis_percent;
    private EditText edit_dis_proname;
    private EditText edit_dis_fromprice;
    private EditText edit_dis_afterprice;
    private TextView  image_name_display_text;
    private Session session;
    private Button publish;



    static  final int REQUEST_IMAGE_CAPTURE =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        image_name_display_text = (TextView) findViewById(R.id.image_name_display_id);
        edit_dis_title = (EditText) findViewById(R.id.editText3);
        edit_dis_description= (EditText) findViewById(R.id.editText4);
        edit_dis_category = (EditText) findViewById(R.id.editText5);
        edit_dis_percent = (EditText) findViewById(R.id.rate_discount);
        edit_dis_proname = (EditText) findViewById(R.id.editText6);
        edit_dis_fromprice = (EditText) findViewById(R.id.from_price);
        edit_dis_afterprice = (EditText) findViewById(R.id.to_price);
        publish = (Button) findViewById(R.id.publish);



        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
            }
        });

        int back_color = Color.parseColor("#20B2AA");
        setActivityBackgroundColor(back_color);
        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }






    }



    private void logout(){
        session.setLoggedin(false);
        
        finish();
        Intent myIntent = new Intent(SellerActivity.this, MainActivity.class);
        SellerActivity.this.startActivity(myIntent);
    }
    public void myFancyMethod(View v) {
        Log.d("Pusblish","Button Clicked");
        dis_title = edit_dis_title.getText().toString();
        dis_description = edit_dis_description.getText().toString();
        dis_category = edit_dis_category.getText().toString();
        dis_percent = edit_dis_percent.getText().toString();
        dis_proname = edit_dis_proname.getText().toString();
        dis_fromprice = edit_dis_fromprice.getText().toString();
        dis_afterprice = edit_dis_afterprice.getText().toString();

        //Send all the data to the server

        BackgroundWorkerDisPaste backgroundworker = new BackgroundWorkerDisPaste(this);

        backgroundworker.execute(dis_title, dis_description, dis_category, dis_percent, dis_proname, dis_fromprice, dis_afterprice, encoded_string);


        //new Encode_image().execute();
    }
    private void getFileUri(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        image_name = "PriceCut"+date+".jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+File.separator+ image_name);
        file_uri = Uri.fromFile(file);
    }
    private void previewCapturedName(){

        try {
            Log.d("Image Name",image_name);
            image_name_display_text.setText(image_name);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE  && resultCode == RESULT_OK) {

            encoder_new();
            //image_name_display.setText(image_name);
            previewCapturedName();

        }
    }
    private void encoder_new(){
        bitmap = BitmapFactory.decodeFile(file_uri.getPath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        bitmap.recycle();

        byte[] array = stream.toByteArray();
        encoded_string = Base64.encodeToString(array, 0);

    }
    public void clicklogout(View v){
        logout();
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}
