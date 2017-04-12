package com.example.user.pricecut;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.pricecut.classes.BackSignIN;
import com.example.user.pricecut.classes.Session;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public Button sign_in;
    private Session session;
    private String user_name;
    private String passowrd_str;
    private EditText edit_username;
    private EditText edit_password;
    private SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        int back_color = Color.parseColor("#184064");

        edit_username = (EditText) findViewById(R.id.editText);

        edit_password = (EditText) findViewById(R.id.editText2);

        mydatabase = openOrCreateDatabase("App_Users",MODE_PRIVATE,null);

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS users(Username VARCHAR);");

        sign_in = (Button) findViewById(R.id.button);

        session = new Session(this);//This session is used to preserve the user login
        setActivityBackgroundColor(back_color);
        if(session.loggedin()){

            Log.d("Going to app", "Going");
            if(session.seller){
                Intent intentLog = new Intent(MainActivity.this, SellerActivity.class);
                MainActivity.this.startActivity(intentLog);
            }
            else{

                Intent intentLog = new Intent(MainActivity.this, ClientMain.class);
                MainActivity.this.startActivity(intentLog);
            }

        }

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    public void OnSignIn(View v){

        Log.d("Button Action","Clicked");
        user_name = edit_username.getText().toString();
        passowrd_str = edit_password.getText().toString();
        BackSignIN sign_in_background = new BackSignIN(this);
        Log.d("user",user_name);
        Log.d("pass",passowrd_str);
        sign_in_background.execute(user_name,passowrd_str);
        String out ="";
        try{

            out = sign_in_background.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Out_value_Login", out);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(Objects.equals(out, "1")) {

                Log.d("Going_to_app", "Going_to_Seller");
                session.setLoggedin(true);
                mydatabase.execSQL("DELETE FROM users");

                mydatabase.execSQL("INSERT INTO users VALUES('" + user_name + "');");

                session.Signed_username = user_name;
                session.seller = true;
                Intent intentLog = new Intent(MainActivity.this, SellerActivity.class);
                MainActivity.this.startActivity(intentLog);

            } else if(Objects.equals(out, "0")){
                Log.d("Going_to_app","Going_to_Client");

                mydatabase.execSQL("DELETE FROM users" );
                mydatabase.execSQL("INSERT INTO users VALUES('" + user_name + "');");
                Log.d("Going_to_app","after database");
                session.setLoggedin(true);

                session.Signed_username = user_name;
                session.seller = false;


                Log.d("Name",session.Signed_username);
                Log.d("IS_Seller",String.valueOf(session.seller));
                Intent intentLog = new Intent(MainActivity.this, ClientMain.class);
                MainActivity.this.startActivity(intentLog);
            }
        }
    }

    public void SignUp(View v){
        Intent myIntent = new Intent(this,SignUp.class);
        startActivity(myIntent);
    }

}
