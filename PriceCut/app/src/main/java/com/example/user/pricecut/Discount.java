package com.example.user.pricecut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Discount extends AppCompatActivity {
    private int discount_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        Bundle b = getIntent().getExtras();
        int discount_num =-1;
        if(b!=null){
            discount_num = b.getInt("key");
            Log.d("discount_num",String.valueOf(discount_num));
        }

    }
}
