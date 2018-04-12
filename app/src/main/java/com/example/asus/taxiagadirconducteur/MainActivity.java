package com.example.asus.taxiagadirconducteur;
/*
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    // Progress Dialog Object
    ProgressDialog prgDialog;

    private GoogleMap Map = null;
    LatLng Mypos;
    MarkerOptions MO;
    Marker m = null;

    private int IdTaxi;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        SetSupportActionBar(toolbar);
        mTabLayout=(TabLayout)findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("one"));
        mTabLayout.addTab(mTabLayout.newTab().setText("two"));
        mTabLayout.addTab(mTabLayout.newTab().setText("three"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager=(ViewPager)findViewById(R.id.pager);
        fragment_activity adapter=new fragment_activity(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position,0,true);
                mTabLayout.setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout =(TabLayout)findViewById(R.id.tabs) ;
        tabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        MO = new MarkerOptions().title("Votre Taxi").draggable(false);
        // Instantier Progress Dialog object
        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);
        IdTaxi = getIntent().getExtras().getInt("IdTaxi");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        new GetPosition().execute();
    }


    @Override
    public void onMapReady(GoogleMap map) {

        Map = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Map.setMyLocationEnabled(false);
        Map.getUiSettings().setZoomControlsEnabled(false);

    }

    public void EnvPosition() {
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();


        if (m.getPosition() != null) {
            LatLng pos = new LatLng(m.getPosition().latitude, m.getPosition().longitude);

            // Put Http parameter username with value of Email Edit View control
            params.put("IdTaxi", String.valueOf(IdTaxi));

            // Put Http parameter Latitude pour le client
            params.put("lat", String.valueOf(pos.latitude));
            // Put Http parameter Longitude pour le client
            params.put("log", String.valueOf(pos.longitude));

            invokeWS(params);

        } else
            Toast.makeText(getApplicationContext(), "Position n'est pas déterminée ... ", Toast.LENGTH_LONG).show();
    }


    public void invokeWS(RequestParams params) {
        String Adrress = "http://192.168.43.45:71";

        // Show Progress Dialog
        prgDialog.setMessage("Envoi de demande");
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Adrress + "/useraccount/localiser2/localiserTaxi", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable error) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Ressource n'est pas trouvée", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Serveur ne repond pas", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Problème de connexion", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                prgDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);

                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        Toast.makeText(getApplicationContext(), "Demande Reçu", Toast.LENGTH_LONG).show();


                        //Toast.makeText(getApplicationContext(), "Position Taxi Proche", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Lat :"+obj.getDouble("lat"), Toast.LENGTH_LONG);
                        //Toast.makeText(getApplicationContext(), "Log :"+obj.getDouble("log"), Toast.LENGTH_LONG);

                    }
                    // Else afficher message d'erreur
                    else {
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "JSON Invalide !", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }


        });
    }
/*


    class GetPosition extends AsyncTask<Void, Void, LatLng> {

        @SuppressLint("MissingPermission")
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            prgDialog.setMessage("Recherche Position");
            prgDialog.show();

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new MyLocationListener();

            assert locationManager != null;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        @Override
        protected LatLng doInBackground(Void... params) {
            while(Mypos==null){
            }


            return Mypos;
        }

        @Override
        protected void onPostExecute(LatLng result) {
            prgDialog.dismiss();

            if(Map!=null){

                    MO.position(result);
                    Map.addMarker(MO);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(result).zoom(17).build();
                    Map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),3000,null);
                    Toast.makeText(getApplicationContext(), "Glisser le pin et déterminer le point de rencontre ", Toast.LENGTH_LONG).show();
            }else Toast.makeText(getApplicationContext(), "Error Survenu", Toast.LENGTH_LONG).show();
      }

    }
     public void animateMarker(final Marker marker, final LatLng toPosition,
                final boolean hideMarker) {
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            GoogleMap mGoogleMapObject = null;
            Projection proj = mGoogleMapObject.getProjection();
            Point startPoint = proj.toScreenLocation(marker.getPosition());
            final LatLng startLatLng = proj.fromScreenLocation(startPoint);
            final long duration = 500;

            final Interpolator interpolator = new LinearInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed
                            / duration);
                    double lng = t * toPosition.longitude + (1 - t)
                            * startLatLng.longitude;
                    double lat = t * toPosition.latitude + (1 - t)
                            * startLatLng.latitude;
                    marker.setPosition(new LatLng(lat, lng));

                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    } else {
                        if (hideMarker) {
                            marker.setVisible(false);
                        } else {
                            marker.setVisible(true);
                        }
                    }
                }
            });
        }
    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            //Toast.makeText(getApplicationContext(), "Lat : "+location.getLatitude()+";"+"Lon : "+location.getLongitude(),Toast.LENGTH_LONG).show();
            Mypos = new LatLng(location.getLatitude(), location.getLongitude());
            if(Map!=null){
                if(m==null){
                    m=Map.addMarker(new MarkerOptions().position(Mypos).title("Votre Taxi").draggable(false));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(Mypos).zoom(17).build();
                    Map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),3000,null);
                }else{
                    m.setPosition(Mypos);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(Mypos).zoom(17).build();
                    Map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),3000,null);
                }

                EnvPosition();


            }else Toast.makeText(getApplicationContext(), "Error Survenu", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onProviderDisabled(String arg0) {
            Toast.makeText(getApplicationContext(), "Veuillez Activer le Service GPS",Toast.LENGTH_LONG).show();

            Intent intent= new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        @Override
        public void onProviderEnabled(String arg0) {
            Toast.makeText(getApplicationContext(), "Service GPS Activée",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }
    }
    public void ConsulterDemande(View v){
        Intent i = new Intent(getApplicationContext(),Consulter.class);
        i.putExtra("lat", Mypos.latitude);
        i.putExtra("log", Mypos.longitude);
       startActivity(i);
    }

}
*/