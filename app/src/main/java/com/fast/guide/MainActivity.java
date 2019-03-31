package com.fast.guide;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.fast.guide.Connection.CheckNetwork;
import com.fast.guide.Utilities.LocaleHelper;
import com.fast.guide.Utilities.settings;
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    settings st;
    TextView langtxt;
    CheckNetwork chknet=new CheckNetwork();


    int tab=0;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new NearbyFragment());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(this);

         st = new settings(getApplicationContext());
        if( st.getValue("lang").length()==0||st.getValue("lang").equals(null) ){
            st.setValue("lang","ku");
        }




    }



    private boolean loadFragment(Fragment fragment){
        if(ShowAlert()){

            if (fragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return true;
            }else {
                return false;
            }
        }else {
            return false;

        }
    }


    boolean ShowAlert(){

        if(!chknet.isInternetAvailable(this)) //returns true if internet available
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.drawable.icon);
            builder.setMessage("No Internet Connection..")
                    .setCancelable(false)
                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ShowAlert();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return false;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lang, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.langEN:  st.setValue("lang","en");

                break;

            case R.id.langAR:  st.setValue("lang","ar");

                break;

            case R.id.langKU:  st.setValue("lang","ku");break;

            case R.id.langtxt:break;

            default:
                st.setValue("lang","ku");break;
        }
        ChangeLang();

        return super.onOptionsItemSelected(item);

    }

    void changelabel(Context context,Resources resources){
        BottomNavigationView nav=(BottomNavigationView)findViewById(R.id.navigation);
        langtxt=(TextView) findViewById(R.id.langtxt);

        langtxt.setText( resources.getString(R.string.btnTextkey) );
        nav.getMenu().getItem(0).setTitle(resources.getString(R.string.title_nearby));
        nav.getMenu().getItem(1).setTitle(resources.getString(R.string.title_list));
        nav.getMenu().getItem(2).setTitle(resources.getString(R.string.title_search));
        nav.getMenu().getItem(3).setTitle(resources.getString(R.string.title_about));

    }

    void ChangeLang( ){

        Context context;
        Resources resources;



        switch (st.getValue("lang")){
            case "en":
                context = LocaleHelper.setLocale(MainActivity.this, "en");
                resources = context.getResources();
                changelabel( context, resources);
                break;
            case "ar":
                context = LocaleHelper.setLocale(MainActivity.this, "ar");
                resources = context.getResources();
                changelabel( context, resources);
                break;
            case "ku":
                context = LocaleHelper.setLocale(MainActivity.this, "ku");
                resources = context.getResources();
                changelabel( context, resources);
                break;
        }




        switch (tab) {
            case 0:
                fragment = new NearbyFragment();
                break;
            case 1:
                fragment = new ListFragment();
                break;
            case 2:
                fragment = new SearchFragment();
                break;
            case 3:
                fragment = new AboutFragment();
                break;
        }
        loadFragment(fragment);

        //finish();
        //startActivity(getIntent());

    }

    Fragment fragment = null;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_nearby:
                fragment = new NearbyFragment();
                tab=0;
                break;
            case R.id.navigation_list:
                fragment = new ListFragment();
                tab=1;
                break;
            case R.id.navigation_search:
                fragment = new SearchFragment();
                tab=2;
                break;
            case R.id.navigation_about:
                fragment = new AboutFragment();
                tab=3;
                break;
        }


        return loadFragment(fragment);
    }
}
