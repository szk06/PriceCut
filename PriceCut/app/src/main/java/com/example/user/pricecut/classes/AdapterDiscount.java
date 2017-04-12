package com.example.user.pricecut.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.pricecut.R;
import com.example.user.pricecut.discountobj;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/10/2017.
 */
public class AdapterDiscount extends ArrayAdapter<discountobj> {

    ArrayList<discountobj> discountList = new ArrayList<>();




    public AdapterDiscount(Context context, int resource, List objects) {
        super(context, resource, objects);
        discountList = (ArrayList<discountobj>) objects;
    }



    @Override
    public int getCount() {
        return super.getCount();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.activity_listview, null);
        Log.d("I_am_here","Adapter");
        TextView title_textview = (TextView) v.findViewById(R.id.discount_title);
        TextView category_textview = (TextView) v.findViewById(R.id.discount_category);
        TextView name_textView = (TextView) v.findViewById(R.id.name);
        ImageView img = (ImageView) v.findViewById(R.id.discount_image);
        String path_to_image = discountList.get(position).path_image;
        Log.d("I_am_here", "ane hon");
        /*
        byte[] decodedString = Base64.decode(image64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);
        */
        /*
        URL url = null;
        try {
            url = new URL("http://192.168.97.1:8080/imagine/images/books.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        img.setImageBitmap(bmp);
        */
        String URL1 = "http://192.168.97.1:8080/imagine/"+path_to_image;
        img.setTag(URL1);
        DownloadImagesTask dd = new DownloadImagesTask();
        Log.d("Exec","mExec");
        dd.execute(img);

        TextView distance_textView = (TextView) v.findViewById(R.id.rating);
        TextView to_distance_textView = (TextView) v.findViewById(R.id.distance_to);
        TextView perc_textView = (TextView) v.findViewById(R.id.percentage);

        title_textview.setText(discountList.get(position).dis_title);
        category_textview.setText(discountList.get(position).dis_category);
        name_textView.setText(discountList.get(position).seller_name);
        distance_textView.setText(discountList.get(position).rating);
        perc_textView.setText(discountList.get(position).percentage);
        to_distance_textView.setText(discountList.get(position).distance);
        return v;
    }



}
