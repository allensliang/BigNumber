package com.example.aliang.bignumber;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public LocationManager service;
public static int uu = 0;

    public void onProviderDisabled(String provider) {
        System.out.println("onProviderDisabled is called");
    }
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        System.out.println("onStatusChanged is called");

        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
  System.out.println("out of service");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
 System.out.println("temp unavailable");
                break;
            case LocationProvider.AVAILABLE:
 System.out.println("available");
                break;

                default:
                    System.out.println("status=" + status);
        }

    }

    public void onLocationChanged(Location location) {
        System.out.println("onLocationChanged is called");

//        location.
        int latitude = (int) (location.getLatitude());
        int longitude = (int) (location.getLongitude());
        System.out.println(String.valueOf(latitude));
        System.out.println(String.valueOf(longitude));

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
uu++;
            TextView textView = findViewById(R.id.textView);

            textView.setText("update number=" + uu + address);

            System.out.println("address=" + address);
            System.out.println("city=" + city);
            System.out.println("state=" + state);
            System.out.println("country=" + country);
            System.out.println("postalCode=" + postalCode);
            System.out.println("knownName=" + knownName);
        }
        catch (Exception e)
        {
System.out.println(e.getMessage());
        }

    }

    public void onProviderEnabled(String provider) {
        System.out.println(provider + " enabled");

    }

    public void onButtonSmallClick(View button)
    {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        switch (status)
        {
            case ConnectionResult.SUCCESS:
                break;

            case ConnectionResult.SERVICE_DISABLED:
                break;

                case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED

            default:

                break;
        }

//        ConnectionResult: SUCCESS, SERVICE_MISSING, SERVICE_UPDATING, SERVICE_VERSION_UPDATE_REQUIRED, SERVICE_DISABLED, SERVICE_INVALID
    }

    public void onButtonBigClick(View button)
    {
        System.out.println("here");

        service = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (enabled)
        {
            System.out.println("222");

            List<String> allproviders = service.getAllProviders();

            for (String pp : allproviders)
            {
                System.out.println(pp);
            }

            int per = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            System.out.println(per);
            if (per != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        33);
            }
            Criteria criteria = new Criteria();

                    String provider = service.getBestProvider(criteria, true);

  //          String provider="passive";

            System.out.println(provider);
            Location location = service.getLastKnownLocation(provider);

            service.requestLocationUpdates(provider, 400, 1, this);

            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");

                int latitude = (int) (location.getLatitude());
                int longitude = (int) (location.getLongitude());
                System.out.println(String.valueOf(latitude));
                System.out.println(String.valueOf(longitude));

                float acc = location.getAccuracy();

                System.out.println("accu=" + acc);


                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss",
                        Locale.ENGLISH);
                String format = s.format(location.getTime());

                System.out.println("time=" + s);

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                    TextView textView = findViewById(R.id.textView);

                    textView.setText(address);

                    System.out.println("address=" + address);
                    System.out.println("city=" + city);
                    System.out.println("state=" + state);
                    System.out.println("country=" + country);
                    System.out.println("postalCode=" + postalCode);
                    System.out.println("knownName=" + knownName);
                }
                catch (Exception e)
                {

                }

            }

        }
        else
        {
            System.out.println("dfs");

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  //      setSupportActionBar(toolbar);

    //    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      //  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } ); */
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    }*/
}
