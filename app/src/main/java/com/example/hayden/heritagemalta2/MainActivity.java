package com.example.hayden.heritagemalta2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchActivityfrag.OnFragmentInteractionListener, MuseumDetailsFrag.OnFragmentInteractionListener,QRScannerFragment.OnFragmentInteractionListener, AudioTourFragment.OnFragmentInteractionListener, MapsFragment.OnFragmentInteractionListener{
private ZXingScannerView mScannerView;
    public final int REQUEST_PERMISSION_CAMERA=1;
    public final int REQUEST_PERMISSION_LOCATION=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            //Get data from intents sent from fragments
            Bundle data1 = getIntent().getExtras();
        if(data1 != null) {

            int activitytype = data1.getInt("ActivityType");
            //If the intent came from MuseumDetails
            if(activitytype == 1) {
                //Gets the Museum object from the previous intent
                Museum mus = (Museum) data1.getParcelable("ITEM_EXTRA");
                Bundle bundle = new Bundle();
                //Puts the Museum object into the bundle for the next fragment
                bundle.putParcelable("message", mus);
                Fragment fragment = new MuseumDetailsFrag();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.setArguments(bundle);
                //Opens the next fragment
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();}
            //If the intent came from QRScanner
           else if(activitytype == 2){

                Bundle b = new Bundle();
                //puts the results of the QRScanner in the bundle for the next fragment
                String qrResult = data1.getString("STRING_EXTRA");
                b.putString("qrResult",qrResult);

                Fragment fragment = new AudioTourFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.setArguments(b);
                //Opens the next fragment
                fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
            }
            else if(activitytype == 3){
                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

                if(permissionCheck == PackageManager.PERMISSION_GRANTED){Context context = getApplicationContext();

                    //Opens Maps fragment
                    Bundle b = new Bundle();
                    Museum mus = data1.getParcelable("ITEM_EXTRA");
                    b.putParcelable("message",mus);

                    Fragment fragment = new MapsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragment.setArguments(b);
                    fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
                }
                else{
                    //Asks for permissions
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCATION);
                }


            }


        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_valetta) {
            Fragment fragment = new SearchActivityfrag();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();

        } else if (id == R.id.nav_northmalta) {
            Fragment fragment = new SearchActivityfrag();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        } else if (id == R.id.nav_southmalta) {
            Fragment fragment = new SearchActivityfrag();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        } else if (id == R.id.nav_gozo) {
            Fragment fragment = new SearchActivityfrag();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
        } else if (id == R.id.nav_qrscanner) {

            //Stores permission status
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            //Checks for permissions
            if(permissionCheck == PackageManager.PERMISSION_GRANTED){Context context = getApplicationContext();

                //Opens QRScanner fragment
                Fragment fragment = new QRScannerFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent,fragment).commit();
                }
            else{
                //Asks for permissions
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
            }





        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case REQUEST_PERMISSION_CAMERA: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, QRScannerFragment.class);

                    startActivity(intent);
                }

            }
            case REQUEST_PERMISSION_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Bundle data1 = getIntent().getExtras();
                    Bundle b = new Bundle();
                    Museum mus = data1.getParcelable("ITEM_EXTRA");
                    b.putParcelable("MusMessage", mus);

                    Fragment fragment = new MapsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragment.setArguments(b);
                    fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                }
            }

        }



    }

    //Fragment Interaction Listeners
    @Override
    public void onSearchFragmentInteraction (Uri uri){

    }

    @Override
    public void onMuseumDetailsFragmentInteraction (Uri uri){

    }

    @Override
    public void onAudioTourFragmentInteraction (Uri uri){

    }

    @Override
    public void onQRScannerFragmentInteraction (Uri uri){

    }

    @Override
    public void onMapsFragmentInteraction (Uri uri){

    }
}

