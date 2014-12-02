package com.vigilanteosu.cse4471.vigilanteosuapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


public class ViewIncidentActivity extends FragmentActivity {

    GoogleMap googleMap;

    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                LocationManager locMan = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

                Criteria crit = new Criteria();

                Location loc = locMan.getLastKnownLocation(locMan.getBestProvider(crit, false));



                CameraPosition camPos = new CameraPosition.Builder()

                        .target(new LatLng(loc.getLatitude(), loc.getLongitude()))

                        .zoom(12.8f)

                        .build();

                CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);

                googleMap.moveCamera(camUpdate);
                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_incident);
        createMapView();

        TextView title = (TextView)findViewById(R.id.report_title);
        TextView loc = (TextView)findViewById(R.id.report_location);
        TextView time = (TextView)findViewById(R.id.report_time);
        TextView desc = (TextView)findViewById(R.id.report_desc);

        int severity = getIntent().getExtras().getInt("severity");
        title.setText(getIntent().getExtras().getString("reportTitle"));
        loc.setText(getIntent().getExtras().getString("reportLocation"));
        time.setText(getIntent().getExtras().getString("reportTime"));
        desc.setText(getIntent().getExtras().getString("reportDesc"));

        ImageView severityIcon = (ImageView)findViewById(R.id.severity_icon);

        switch(severity){
            case 0:
                severityIcon.setImageResource(R.drawable.zeroseverity);
                break;
            case 1:
                severityIcon.setImageResource(R.drawable.oneseverity);
                break;
            case 2:
                severityIcon.setImageResource(R.drawable.twoseverity);
                break;
            case 3:
                severityIcon.setImageResource(R.drawable.threeseverity);
                break;
            case 4:
                severityIcon.setImageResource(R.drawable.fourseverity);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_compose:
                openCompose();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openCompose(){
        Intent intent = new Intent(this, NewReportStartActivity.class);
        startActivity(intent);
    }
}
