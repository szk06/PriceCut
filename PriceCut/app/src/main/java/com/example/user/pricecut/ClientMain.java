package com.example.user.pricecut;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.pricecut.classes.Session;

public class ClientMain extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private EditText search;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Session session;
    private SQLiteDatabase mydatabase;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public String user_name_sec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main3);
        Log.d("I_am_here","On Create client");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        mydatabase = openOrCreateDatabase("App_Users",MODE_PRIVATE,null);
        //Create the database
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS users(Username VARCHAR);");


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        session = new Session(this);
        Log.d("I_am_here","Before_session");
        if(!session.loggedin()){
            Log.d("I_am_here","NOt_session");
            logout();
        }
        Log.d("I_am_here","After_NO_session");
        //Log.d("user_name_sec",user_name_sec);
        //user_name_sec = session.Signed_username;
        Cursor resultSet = mydatabase.rawQuery("Select * from users", null);
        resultSet.moveToFirst();
        user_name_sec = resultSet.getString(0);

        Log.d("session.Signed_username",user_name_sec);

        int back_color = Color.parseColor("#184064");

        setActivityBackgroundColor(back_color);
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_main, menu);
        return true;
    }
    public void performSearch(){
        Log.d("Search button", "clicked");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1Discounts tab1 = new Tab1Discounts(user_name_sec);
                    return tab1;
                case 1:
                    Tab2Sellers tab2 = new Tab2Sellers();
                    return tab2;
                case 2:
                    Tab3Me tab3 = new Tab3Me();
                    return tab3;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Discounts";
                case 1:
                    return "Sellers";
                case 2:
                    return "Me";
            }
            return null;
        }
    }
    private void logout(){
        session.setLoggedin(false);

        finish();
        Intent myIntent = new Intent(ClientMain.this, MainActivity.class);
        ClientMain.this.startActivity(myIntent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

}
