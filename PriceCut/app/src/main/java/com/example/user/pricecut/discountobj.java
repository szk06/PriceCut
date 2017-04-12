package com.example.user.pricecut;

/**
 * Created by user on 4/10/2017.
 */
public class discountobj {

    public String dis_title;
    public String dis_category;
    public String path_image;
    public String seller_name;
    public String rating;
    public String percentage;
    public String distance;
    public discountobj(String title,String category,String name,String r,String per,String path,String mydis){

        path_image =path;
        dis_title = title;
        dis_category=category;
        seller_name =name;
        rating = r;
        distance = mydis;
        percentage = per;
    }
}
