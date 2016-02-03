package com.example.muvr.muvr;

/**
 * Created by HP 15 on 1/22/2016.
 */

import com.example.muvr.R;
import com.google.android.gms.maps.CameraUpdateFactory;



        import android.Manifest;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.location.LocationManager;
        import android.os.AsyncTask;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.UiSettings;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.LatLngBounds;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.maps.model.PolylineOptions;

        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double slatitude= 0;
    double slongitude= 0;
    String start=null;
    String end =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setTrafficEnabled(true);


        UiSettings gmapsettings=mMap.getUiSettings();
        gmapsettings.setMyLocationButtonEnabled(true);
        gmapsettings.setCompassEnabled(true);
        gmapsettings.setZoomControlsEnabled(true);
        gmapsettings.setZoomControlsEnabled(true);
        gmapsettings.setScrollGesturesEnabled(true);
        gmapsettings.setRotateGesturesEnabled(true);



        Intent ri=getIntent();
        Bundle a=ri.getExtras();
        String start=a.getString("start");
        String end=a.getString("end");



        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses= null;
        try {
            addresses = geocoder.getFromLocationName(start, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        double slatitude= addresses.get(0).getLatitude();
        double slongitude= addresses.get(0).getLongitude();

        // Add a marker to start
        LatLng smarker = new LatLng(slatitude, slongitude);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(smarker).title("Pick Up"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(smarker));


        try {
            addresses = geocoder.getFromLocationName(end, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double elatitude= addresses.get(0).getLatitude();
        double elongitude= addresses.get(0).getLongitude();


        // Add a marker to end
        LatLng emarker = new LatLng(elatitude, elongitude);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(emarker).title("Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(emarker));

        LatLng origin = smarker;
        LatLng dest = emarker;

// Getting URL to the Google Directions API
        String url = getDirectionsUrl(origin, dest);

        DownloadTask downloadTask = new DownloadTask();

// Start downloading json data from Google Directions API
        downloadTask.execute(url);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(smarker));


    }




    private String getDirectionsUrl(LatLng origin,LatLng dest){

// Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

// Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

// Sensor enabled
        String sensor = "sensor=false";

// Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

// Output format
        String output = "json";

// Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{

            URL url = new URL(strUrl);

// Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

// Connecting to url
            urlConnection.connect();

// Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

// For storing data from web service
            String data = "";

            try{
// Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
// doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

// Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

// Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

// Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList();
                lineOptions = new PolylineOptions();

// Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

// Fetching all the points in i-th route
                for(int j=0;j <path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

// Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.BLUE);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);

            List<LatLng> pointis = lineOptions.getPoints(); // route is instance of PolylineOptions

            LatLngBounds.Builder bc = new LatLngBounds.Builder();

            for (LatLng item : pointis) {
                bc.include(item);
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
        }
    }
}




