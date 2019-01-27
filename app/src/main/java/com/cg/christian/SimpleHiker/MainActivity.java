package com.cg.christian.SimpleHiker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Finding my location
    LocationManager locationManager;
    LocationListener locationListener;


    // FONT THINGY
    Typeface myFont;
    Typeface mySubFont;

    TextView hiThere;
    // TextView ivyCompanion;
    TextView latTextView;
    TextView longTextView;
    TextView accTextView;
    TextView altTextView;
    TextView addressTextView;

    // Date
    TextView dateTextView;

    //Doubles
   double latDouble;
   double longDouble;
   double altDouble;
   double accDouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





            // TEXT_VIEWS
        // ivyCompanion = findViewById(R.id.ivyTextView);
        latTextView = findViewById(R.id.latTextView);
        longTextView = findViewById(R.id.longTextView);
        accTextView = findViewById(R.id.accTextView);
        altTextView = findViewById(R.id.altTextView);
        addressTextView = findViewById(R.id.addressTextView);

        dateTextView = findViewById(R.id.dateTextView);

        // Date
        setDate(dateTextView);



        // FONT LOCATOR
        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Comfortaa-Regular.ttf");
        mySubFont = Typeface.createFromAsset(this.getAssets(), "fonts/Lato-Light.ttf");

        //FOR CUSTOM FONT

        // ivyCompanion.setTypeface(myFont);
        latTextView.setTypeface(mySubFont);
        longTextView.setTypeface(mySubFont);
        accTextView.setTypeface(mySubFont);
        altTextView.setTypeface(mySubFont);
        //addressTextView.setTypeface(mySubFont);
        dateTextView.setTypeface(mySubFont);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                buildAlertMessageNoGps();

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)  {
            startListening();
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void updateLocationInfo(Location location) {

        //Rounding doubles to make them look more presentable


        latDouble = location.getLatitude();
        latDouble = Math.round(latDouble*100.0)/100.0;
        longDouble = location.getLongitude();
        longDouble = Math.round(longDouble*100.0)/100.0;
        accDouble = location.getAccuracy();
        accDouble = Math.round(accDouble*100.0)/100.0;
        altDouble = location.getAltitude();
        altDouble = Math.round(altDouble*100.0)/100.0;




        latTextView.setText("  Latitude: " + Double.toString(latDouble));
        longTextView.setText("  Longitude: " + Double.toString(longDouble));
        accTextView.setText("  Accuracy: " + Double.toString(accDouble) + " Meters");
        altTextView.setText("  Altitude: " + Double.toString(altDouble));

        String address = "Could not find address";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());


        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (listAddresses != null && listAddresses.size() > 0) {
                address = "\n";

                if (listAddresses.get(0).getThoroughfare() != null) {
                    address += listAddresses.get(0).getThoroughfare() + "\n";
                }

                if (listAddresses.get(0).getLocality() != null) {
                    address += listAddresses.get(0).getLocality() + " ";
                }

                if (listAddresses.get(0).getPostalCode() != null) {
                    address += listAddresses.get(0).getPostalCode() + " ";
                }

                if (listAddresses.get(0).getAdminArea() != null) {
                    address += listAddresses.get(0).getAdminArea();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        addressTextView.setText(address);
    }

    public void onClickRefresh (View view) {
        finish();
        startActivity(getIntent());
    }

    public void onClickInfo(View view) {
        Intent intent = new Intent(this, infoActivity.class);
        startActivity(intent);
    }

    public void onClickGoogleMaps(View view) {
        Intent intentGoogleMaps = new Intent(this, MapsActivity.class);
        intentGoogleMaps.putExtra("currentLatitude", latDouble);
        intentGoogleMaps.putExtra("currentLongitude", longDouble);
        startActivity(intentGoogleMaps);
    }

    public void onClickCompass(View view){
        Intent intentCompass = new Intent(this, compassActivity.class);
        intentCompass.putExtra("currentLatitude", latDouble);
        intentCompass.putExtra("currentLongitude", longDouble);
        startActivity(intentCompass);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void setDate (TextView view){

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formating according to my need
        String date = formatter.format(today);
        view.setText(date);
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}


