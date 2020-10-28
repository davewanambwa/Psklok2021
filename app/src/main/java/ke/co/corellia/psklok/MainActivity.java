package ke.co.corellia.psklok;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int SPLASH = 1;
    private static final int LOGIN_SCREEN = 3;
    private static final int ENDME = 100;
    private static final int RIDE_CHOOSE = 4;
    private static final int RIDE_PACKAGE = 5;
    private static final int PACKAGE_SIZE = 6;
    private static final int RIDE_OPTIONS = 8;
    private static final int INSURANCE_PACKAGE = 7;
    private static final int WALLET_UPDATE = 9;
    private static final int CHOOSE_RECEPT = 20;
    private static final int UPLOAD = 21;
    private int cid;
    private Timer myTimer, myTimer2;
    private ProgressBar spinner;


    private String customerid, clat, clong, mode, destination, rlat, rlong, Source, Insurance, insuranceAmount, ParcelValue, Passwordgen, tripID;

    String rider, RiderLat, RiderLong, packages, DriverRating, RequestState, jsonStr,CountryCode,HomeLocation;


    private String minbasefare, perkmfare, perminfare;
    private String rrPhoneNumber;
    AutocompleteSupportFragment autocompleteFragment,autocompleteFragment2;
    LazyInitializedSingletonExample lz;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker m3;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    DrawerLayout dLayout;

    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet, foundride, arrival;

    private double distance, fare;
    Button btntrip ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btntrip = findViewById(R.id.btntrip);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        foundride = findViewById(R.id.foundride);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        foundride.setVisibility(View.GONE);



      /*  if( ActivityCompat.checkSelfPermission( this,Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 0);

        }*/
        setNavigationDrawer();

       /* try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(nmMap..getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
                placeName.setText("Waiting for Location");
            }
            else {
                if (addresses.size() > 0) {
                    placeName.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());

                    Toast.makeText(this, "No Location Name Found",Toast.LENGTH_LONG).show();
                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "No Location Name Found",Toast.LENGTH_LONG).show();
        }
*/

        // LinearLayout ltend =findViewById(R.id.ltend);
        // ltend.setVisibility(View.GONE);
        RequestState = "0";

        ImageButton btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout dLayout = findViewById(R.id.drawer_layout);
// initiate a DrawerLayout
                dLayout.openDrawer(GravityCompat.START);
// close all the Drawers
            }
        });


        btntrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent2 = new Intent(MainActivity.this, packagedetails.class);

                Intent intent2 = new Intent(MainActivity.this, Camera.class);
                startActivity(intent2);// Activity is started with requestCode 2btn
            }
        });


        Button btn3 = findViewById(R.id.btncancelreq);


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout lt = findViewById(R.id.ltconfirm);
                LinearLayout lt2 = findViewById(R.id.ltestimate);
                LinearLayout lt3 = findViewById(R.id.ltdriverfind);
                lt.setVisibility(View.VISIBLE);
                lt2.setVisibility(View.GONE);
                lt3.setVisibility(View.GONE);
                mMap.clear();

                try {
                    myTimer.cancel();
                    cpkgchk myTask = new cpkgchk(lz.getCustomerid());
                    myTask.execute();


                } catch (Exception c) {

                }
                Intent intent2 = new Intent(MainActivity.this, ridechooser.class);
                startActivityForResult(intent2, 9);// Activity is started with requestCode 2btn


            }
        });


        Button btn4 = findViewById(R.id.btncancelselect);


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout lt = findViewById(R.id.ltconfirm);
                LinearLayout lt2 = findViewById(R.id.ltestimate);
                LinearLayout lt3 = findViewById(R.id.ltdriverfind);
                lt.setVisibility(View.VISIBLE);
                lt2.setVisibility(View.GONE);
                lt3.setVisibility(View.GONE);
                mMap.clear();
                Intent intent2 = new Intent(MainActivity.this, ridechooser.class);
                startActivityForResult(intent2, 9);// Activity is started with requestCode 2btn


            }
        });

        Button btnridecancel = findViewById(R.id.btncancelride);


        btnridecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout lt = findViewById(R.id.ltconfirm);
                LinearLayout lt2 = findViewById(R.id.ltestimate);
                LinearLayout lt3 = findViewById(R.id.ltdriverfind);
                lt.setVisibility(View.VISIBLE);
                lt2.setVisibility(View.GONE);
                lt3.setVisibility(View.GONE);
                mMap.clear();
                try {
                    myTimer.cancel();
                }
                catch (Exception e)
                {

                }
                Intent intent2 = new Intent(MainActivity.this, ridechooser.class);
                startActivityForResult(intent2, 9);// Activity is started with requestCode 2btn
                cridecancel myTask = new cridecancel(tripID);
                myTask.execute();

            }
        });


        Button btntrip2 = findViewById(R.id.btnokconfirm);


        btntrip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout lt = findViewById(R.id.ltconfirm);
                LinearLayout lt2 = findViewById(R.id.ltestimate);
                LinearLayout lt3 = findViewById(R.id.ltdriverfind);
                lt.setVisibility(View.GONE);
                lt2.setVisibility(View.GONE);
                lt3.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                foundride.setVisibility(View.GONE);
                ctrip myTask = new ctrip(String.valueOf(lz.getWeight()));
                myTask.execute();
                //mMap.clear();
                //Intent intent2 = new Intent(MainActivity.this, ridechooser.class);
                // startActivityForResult(intent2,9 );// Activity is started with requestCode 2btn

            }
        });


        Button btnokdriver = findViewById(R.id.btnokdriver);


        btnokdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lz.getPhoneofdriver()));
                startActivity(intent);
                //mMap.clear();
                //Intent intent2 = new Intent(MainActivity.this, ridechooser.class);
                // startActivityForResult(intent2,9 );// Activity is started with requestCode 2btn

            }
        });


        Button btn1 = findViewById(R.id.btnokestimate);
        btn1.setEnabled(false);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LinearLayout lt = findViewById(R.id.ltconfirm);
                    LinearLayout lt2 = findViewById(R.id.ltestimate);
                    LinearLayout lt3 = findViewById(R.id.ltdriverfind);
                    lt3.setVisibility(View.GONE);
                    lt.setVisibility(View.GONE);
                    lt2.setVisibility(View.VISIBLE);
                    mMap.clear();

                    LatLng home2 = lz.getLfrom();
                    LatLng sydney = lz.getLto();

                    LatLng k =new LatLng(0,0);

                    TextView ewallet = findViewById(R.id.txtewallet);
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String yourFormattedString = formatter.format(lz.getWallet());

                    ewallet.setText(yourFormattedString);
                    // LatLng home = new LatLng(vlat, vlon);
                    // LatLng sydney = new LatLng(lat, lon);


                Marker m1 = mMap.addMarker(new MarkerOptions().position(home2).title("home").icon(BitmapDescriptorFactory.fromResource(R.drawable.hu)));
                //
                   Marker m2 = mMap.addMarker(new MarkerOptions().position(sydney).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.loca)));


                    //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));


                // LatLngBounds AUSTRALIA = new LatLngBounds( sydney, home2);

// Set the camera to the greatest possible zoom level that includes the
// bounds
                //  mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AUSTRALIA, 10));


                LatLngBounds.Builder builder = new LatLngBounds.Builder();

//the include method will calculate the min and max bound.
                builder.include(m1.getPosition());
                builder.include(m2.getPosition());


                LatLngBounds bounds = builder.build();

                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.29); // offset from edges of the map 10% of screen

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);


                CameraUpdate cu2 = CameraUpdateFactory.newLatLngBounds(bounds, 25, 25, 5);
                mMap.animateCamera(cu);


                String url = getDirectionsUrl(sydney, home2);

                DownloadTask downloadTask = new DownloadTask();

                    downloadTask.execute(url).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        lz = LazyInitializedSingletonExample.getInstance();
//allow location
        if (!checkLocationPermission()) {
            finish();//end if no permssion
        } else {
            //splash
            Intent i = new Intent(getBaseContext(), splashscreen.class);
            startActivityForResult(i, SPLASH);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkLocationPermission();
            }
            SupportMapFragment mapFragment = (SupportMapFragment)
                    getSupportFragmentManager()
                            .findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);
        }

        String apiKey = getString(R.string.api_key);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        //autocompleteFragment.setCountry("KE");
        autocompleteFragment.setCountries("KE","ZA");
// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setHint("Where from?");

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.


                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                instance1.setSource(place.getName());
                instance1.setLfrom(place.getLatLng());
                Log.i("4", "Place: " + place.getName() + ", " + place.getId());
                instance1.setFromlat(place.getLatLng().latitude);
                instance1.setFromlon(place.getLatLng().longitude);
                btn1.setEnabled(true);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.


                Log.i("4", "An error occurred: " + status);
            }

        });


        //--------------

        // Initialize the AutocompleteSupportFragment.
       autocompleteFragment2 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment2);

// Specify the types of place data to return.
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment2.setHint("Where To?");
       // autocompleteFragment2.setCountry("KE");
        autocompleteFragment2.setCountries("KE","ZA");
// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("4", "Place: " + place.getName() + ", " + place.getId());

                LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                instance1.setLto(place.getLatLng());
                instance1.setDes(place.getName());
                instance1.setTolat(place.getLatLng().latitude);
                instance1.setTolon(place.getLatLng().longitude);

                btn1.setEnabled(true);
                //btn.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("4", "An error occurred: " + status);
            }
        });


    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyBaG5F4icbXMtc6vBVFJHGsHr4J4wPfpBA";


        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
                String d = data.toLowerCase();
                int distanceo = d.indexOf("distance");
                String resto = d.substring(distanceo, distanceo + 50);
                resto = resto.replace("\"", "");

                int distanceb = resto.indexOf("text");
                resto = resto.substring(distanceb + 6);
                int distancec = resto.indexOf("km");
                resto = resto.substring(0, distancec);

                resto = resto.trim();
                distance = Double.valueOf(resto);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            TextView edistance = findViewById(R.id.txtedistance);
            edistance.setText(String.valueOf(distance));
            Button btn1 = findViewById(R.id.btnokestimate);

            TextView ecost = findViewById(R.id.ecost);
            Double f = (distance * Double.valueOf(perkmfare)) + Double.valueOf(minbasefare) + lz.getInsurancecost();
            fare = f;
            int i = f.intValue();
            btn1.setEnabled(true);

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String yourFormattedString = formatter.format(i);

            ecost.setText(yourFormattedString);
            if (lz.getWallet() < fare) {


                btn1.setEnabled(false);
                Intent intentsf = new Intent(MainActivity.this, wallet.class);
                //Activity is started with requestCode 2

                startActivity(intentsf);
                //Toast.makeText(getApplicationContext(),"Select wallet to use or top up",Toast.LENGTH_LONG).show();
            }

            parserTask.execute(result);

        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }


                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.YELLOW);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);

        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
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
        mMap.setMyLocationEnabled(true);
        //Initialize Google Play Services

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }

        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    private void checkuser() {
        //chek login
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //SharedPreferences.Editor editor = pref.edit();
        LatLng k = new LatLng(0,0);
        m3 = mMap.addMarker(new MarkerOptions().position(k).title("Rider").icon(BitmapDescriptorFactory.fromResource(R.drawable.bikeo)));

        if (pref.contains("id")) {
            cid = pref.getInt("id", -1);

            // Intent intent2 = new Intent(MainActivity.this, Main_exam_details.class);
            // startActivityForResult(intent2, 2);// Activity is started with requestCode 2btn
            Intent intent2 = new Intent(MainActivity.this, ridechooser.class);
            startActivityForResult(intent2, 9);// Activity is started with requestCode 2btn
            lz.setCustomerid(String.valueOf(cid));


            cSignin myTask = new cSignin(String.valueOf(cid));
            myTask.execute();
            myTimer2 = new Timer();
            myTimer2.schedule(new TimerTask() {
                @Override
                public void run() {

                    cpkgchk myTask = new cpkgchk(lz.getCustomerid());
                    myTask.execute();

                }

            }, 0, 30000);

        } else {
            Intent intent2 = new Intent(MainActivity.this, Login.class);
            startActivityForResult(intent2, LOGIN_SCREEN);// Activity is started with requestCode 2btn
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SPLASH:
                //check user
                checkuser();

                cfare myTask = new cfare(String.valueOf(lz.getWeight()));
                myTask.execute();
                break;
            case 40:
                Intent intent2n = new Intent(MainActivity.this, ridechooser.class);
                startActivityForResult(intent2n, 9);// Activity is started with requestCode 2btn
                break;


            default: //Oh no, it's working day
                //This code is executed when value of variable 'day'
                //doesn't match with any of case above
                break;
        }


        switch (resultCode) {
            case ENDME:
                //Woohoo, it's weekend time
                finish();   // stop chronometer here
                System.exit(0);

                break;

            case RIDE_CHOOSE:

                Intent intent2n = new Intent(MainActivity.this, ridechooser.class);
                startActivityForResult(intent2n, 9);// Activity is started with requestCode 2btn

                break;
            case UPLOAD:

                Intent intent2nV = new Intent(MainActivity.this, uploadz.class);
                startActivityForResult(intent2nV, 9);// Activity is started with requestCode 2btn

                break;

            case CHOOSE_RECEPT:

                Intent intent2m = new Intent(MainActivity.this, recepiant.class);
                startActivityForResult(intent2m, 9);// Activity is started with requestCode 2btn

                break;

            case INSURANCE_PACKAGE:
                Intent intent10 = new Intent(MainActivity.this, insurancec.class);
                startActivityForResult(intent10, 9);// Activity is started with requestCode 2btn
                cfare myTask = new cfare(String.valueOf(lz.getWeight()));
                myTask.execute();

                break;
            case PACKAGE_SIZE:

                Intent intent2b = new Intent(MainActivity.this, packaagechooser.class);
                startActivityForResult(intent2b, 9);// Activity is started with requestCode 2btn

                break;

            case WALLET_UPDATE:
                TextView ewallet = findViewById(R.id.txtewallet);
                ewallet.setText(lz.getWallet().toString());
                break;

            default: //Oh no, it's working day
                //This code is executed when value of variable 'day'
                //doesn't match with any of case above
                break;
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }


    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
               // placeName.setText("Waiting for Location");
            }
            else {
                if (addresses.size() > 0) {

                    String mz;
                   HomeLocation= addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
CountryCode= addresses.get(0).getCountryCode();
autocompleteFragment.setCountry(CountryCode);
autocompleteFragment2.setCountry(CountryCode);
                    autocompleteFragment.setText(HomeLocation);


                    LatLng homel = new LatLng(location.getLatitude(), location.getLongitude());

                    LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                    instance1.setSource(HomeLocation);
                    instance1.setLfrom(homel);

                    instance1.setFromlat(homel.latitude);
                    instance1.setFromlon(homel.longitude);


                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "No Location Name Found",Toast.LENGTH_LONG).show();
        }


        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    //  String country = listAddresses.get(0).getCountryName();
                    String country = listAddresses.get(0).getSubLocality();

                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title(subLocality);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.human));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mCurrLocationMarker.showInfoWindow();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("PICK FROM HERE");

                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.human));
                mCurrLocationMarker = mMap.addMarker(markerOptions);
                mCurrLocationMarker.showInfoWindow();
                autocompleteFragment.setText("On Map");


                ///gecode

                Geocoder geocoder = new Geocoder(getApplicationContext(),
                        Locale.getDefault());
                try {
                    List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude,
                            latLng.longitude, 1);
                    if (null != listAddresses && listAddresses.size() > 0) {
                        String state = listAddresses.get(0).getAdminArea();
                        String country = listAddresses.get(0).getCountryName();
                        String subLocality = state + " : " + listAddresses.get(0).getThoroughfare();
                        markerOptions.title(subLocality);
                        autocompleteFragment.setText(subLocality);


                        LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
                        instance1.setSource(subLocality);
                        instance1.setLfrom(latLng);

                        instance1.setFromlat(latLng.latitude);
                        instance1.setFromlon(latLng.longitude);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                RelativeLayout rr = findViewById(R.id.hidden_panel);
                rr.setVisibility(View.VISIBLE);
            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
            //  LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(mGoogleApiClient)

        }
    }


    //back



    private class cSignin extends AsyncTask<String, Void, String> {


        private String cid, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            TextView tc = findViewById(R.id.user);
            JSONObject reader = null;
            String id = "0";
            String wallet = "0";
            String fname = "0";
            String fcode = "0", femail = "0", ftelephone = "0", fcccode = "0";
            try {
                reader = new JSONObject(result);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys = sysa.getJSONObject(0);
                id = sys.getString("id");
                wallet = sys.getString("wallet");
                fname = sys.getString("fullname");
                fcode = sys.getString("promocode");
                fcccode = sys.getString("Country");
                femail = sys.getString("Email");
                ftelephone = sys.getString("Telephone");
                TextView ewallet = findViewById(R.id.txtewallet);
                ewallet.setText(wallet);


            } catch (JSONException e) {
                e.printStackTrace();

            }


            Double rp = 0.0;
            rp = Double.valueOf(id);
            Intent intent = getIntent();


            if (rp > 0) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id", rp.intValue());
                editor.putString("fname", fname);
                editor.putString("femail", femail);
                editor.putString("fcccode", fcccode);
                editor.putString("ftelephone", ftelephone);
                editor.putString("fcode", fcode);
                lz.setFcode(fcode);


                try {

                tc.setText(fname);
                TextView tc2 = findViewById(R.id.useremail);
                tc2.setText(femail);
                 }
                 catch (Exception c) {
    c.printStackTrace();
                 }

                editor.commit(); // commit changes

                lz.setWallet(Double.valueOf(wallet));
                lz.setFullname(fname);
                TextView ewallet = findViewById(R.id.txtewallet);
                ewallet.setText(wallet);

                //  setResult(4, intent);
                //finish();


            } else {
                setResult(0, intent);
                finish();
            }

        }

        public cSignin(String cid) {


            this.cid = cid;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("cid", cid);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://www.psklok.com/klok/two.php?action=getuserid2";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }


    private void setNavigationDrawer() {
        dLayout = findViewById(R.id.drawer_layout); // initiate a DrawerLayout
        NavigationView navView = findViewById(R.id.navigation); // initiate a Navigation View
// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int itemId = menuItem.getItemId(); // get selected menu item's id
// check selected menu item's id and replace a Fragment Accordingly
                if (itemId == R.id.wallet) {

                    Intent intent2 = new Intent(MainActivity.this, Walletshare.class);
                    startActivity(intent2);// Activity is started with requestCode 2btn

                } else if (itemId == R.id.loff) {

                   // SharedPreferences settings = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
                    SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPref", 0);
                    settings.edit().clear().commit();
                    Intent intent2 = new Intent(MainActivity.this, Login.class);
                    startActivityForResult(intent2,9);// Activity is started with requestCode 2btn
                } else if (itemId == R.id.mybox) {
                    Intent intent2 = new Intent(MainActivity.this, mydelivery.class);
                    startActivity(intent2);// Activity is started with requestCode 2btn


                } else if (itemId == R.id.triphistory) {
                    Intent intent2 = new Intent(MainActivity.this, triphistory.class);
                    startActivity(intent2);// Activity is started with requestCode 2btn


                } else if (itemId == R.id.onpoint) {
                    Intent intent2 = new Intent(MainActivity.this, mypoints.class);
                    startActivity(intent2);// Activity is started with requestCode 2btn


                } else if (itemId == R.id.sharedwithme) {
                    Intent intent2 = new Intent(MainActivity.this, walletwithme.class);
                    startActivity(intent2);// Activity is started with requestCode 2btn

                } else if (itemId == R.id.sharecode) {
                    Intent intent2 = new Intent(MainActivity.this, sharepromo.class);
                    startActivity(intent2);// Activity is started with requestCode 2btn

                } else if (itemId == R.id.Myprofile) {
                    Intent intent2 = new Intent(MainActivity.this, myprof.class);
                    startActivity(intent2);// Activity is started with requestCode 2btn

                } else if (itemId == R.id.ex) {
                    finish();
                }
// display a toast message with menu item's title

                dLayout.closeDrawers(); // close the all open Drawer Views

                return false;
            }
        });


    }

    private class cfare extends AsyncTask<String, Void, String> {


        private String pack, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            JSONObject reader = null;
            perminfare = "0";
            minbasefare = "0";
            perkmfare = "0";
            try {
                reader = new JSONObject(result);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys = sysa.getJSONObject(0);
                perminfare = sys.getString("perminfare");
                minbasefare = sys.getString("minbasefare");
                perkmfare = sys.getString("perkmfare");


            } catch (JSONException e) {
                e.printStackTrace();

            }


        }

        public cfare(String pPackage) {


            this.pack = pPackage;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("pack", pack);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://www.psklok.com/klok/two.php?action=getfare";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private class ctrip extends AsyncTask<String, Void, String> {


        private String cid, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {


            tripID = result.trim();
            lz.setTripid(tripID);


            myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    ctripstate myTask = new ctripstate(tripID);
                    myTask.execute();

                }

            }, 0, 3000);
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            //  JSONObject reader = null;
            // String id ="0";
            // TextView tx =findViewById(R.id.txtwarn);
            //tx.setVisibility(View.GONE);
           /* try {
                reader = new JSONObject(result);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys  = sysa.getJSONObject(0);
                id= sys.getString("id");






            } catch (JSONException e) {
                e.printStackTrace();

            }
*/

            // Walletshare.cSignin myTask = new Walletshare.cSignin(String.valueOf(lz.getCustomerid()));

            //  myTask.execute();


        }

        public ctrip(String cid) {


            this.cid = cid;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("customerid", lz.getCustomerid());
                jsonObject.put("clat", String.valueOf(lz.getLfrom().latitude));
                jsonObject.put("clong", String.valueOf(lz.getLfrom().longitude));
                jsonObject.put("distance", String.valueOf(distance));
                jsonObject.put("fare", String.valueOf(fare));
                jsonObject.put("mode", String.valueOf(lz.isRide()));
                jsonObject.put("destination", lz.getDes());
                jsonObject.put("rlat", String.valueOf(lz.getLto().latitude));
                jsonObject.put("rlong", String.valueOf(lz.getLto().longitude));
                jsonObject.put("Source", lz.getSource());
                jsonObject.put("receiver", lz.getPdestination());
                jsonObject.put("packages", lz.getWeight());
                String i = "0";
                if (lz.isInsured())
                    i = "1";
                jsonObject.put("Insurance", i);

                jsonObject.put("insuranceAmount", lz.getInsurancecost());
                jsonObject.put("ParcelValue", lz.getInsurancevalue());
                Passwordgen = String.valueOf(lockkety());
                lz.setPassword(Passwordgen);
                jsonObject.put("Passwordgen", Passwordgen);


                //  jsonObject.put("cid", lz.getCustomerid());


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://www.psklok.com/klok/two.php?action=ride";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private class ctripstate extends AsyncTask<String, Void, String> {


        private String cid, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {


            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            JSONObject reader = null;
            // String id ="0";
            // TextView tx =findViewById(R.id.txtwarn);
            //tx.setVisibility(View.GONE);
            try {
                reader = new JSONObject(rep);

                JSONArray sysa = reader.getJSONArray("contacts");

                JSONObject sys = sysa.getJSONObject(0);

                rider = sys.getString("rider");
                RiderLat = sys.getString("RiderLat");
                RiderLong = sys.getString("RiderLong");
                DriverRating = sys.getString("DriverRating");
                RequestState = sys.getString("status");
                rrPhoneNumber = sys.getString("PhoneNumber");

                lz.setBoxlock(sys.getString("Passwordgen"));
                String BikeMake = sys.getString("BikeMake");
                String Registration = sys.getString("Registration");

                lz.setPhoneofdriver(rrPhoneNumber);
                AppCompatTextView txrider = findViewById(R.id.myridertxt);
                 txrider.setText("FINDING RIDER");
                 //txrider.setVisibility(View.GONE);

                AppCompatTextView txtrating = findViewById(R.id.txtrating);
                txtrating.setText("RATING : "+DriverRating);


                TextView txriders = findViewById(R.id.txtdrivers);
                txriders.setText(( BikeMake + " | " + Registration).toUpperCase());


            } catch (JSONException e) {
                e.printStackTrace();

            } catch (Exception c) {
                c.printStackTrace();
            }


            if (!RequestState.equals("0")) {

                spinner.setVisibility(View.GONE);
                foundride.setVisibility(View.VISIBLE);
                if (lz.getWeight()==4) {
                    btntrip.setEnabled(false);
                    btntrip.setVisibility(View.GONE);
                }
                else {
                    btntrip.setEnabled(true);
                    btntrip.setVisibility(View.VISIBLE);
                }
                AppCompatTextView txrider = findViewById(R.id.myridertxt);
              //  txrider.setText("FINDING YOUR DRIVER ...");
                //  LinearLayout ltend =findViewById(R.id.ltend);
                //  ltend.setVisibility(View.GONE);

            }
            if (RequestState.equals("0")) {

                foundride.setVisibility(View.GONE);
                AppCompatTextView txrider = findViewById(R.id.myridertxt);

                txrider.setText("FINDING YOUR DRIVER ...");
                //  LinearLayout ltend =findViewById(R.id.ltend);
                //  ltend.setVisibility(View.GONE);
                //LatLng m = new LatLng(0,0);
             //   m3.setPosition(m);

                if(m3!=null){
                    m3.remove();
                }
                LatLng mLatLng = new LatLng(0, 0);

                m3 = mMap.addMarker(new MarkerOptions().position(mLatLng).title("rider").snippet("On the way").icon(BitmapDescriptorFactory.fromResource(R.drawable.bikeo)));


            }
            if (RequestState.equals("1")) {
                AppCompatTextView txrider = findViewById(R.id.myridertxt);
                txrider.setText(rider);
                LatLng dr = new LatLng(Double.valueOf(RiderLat) ,Double.valueOf(RiderLong));
                m3.setPosition(dr);

                if(m3!=null){
                    m3.remove();
                }

                LatLng mLatLng = new LatLng(Double.valueOf(RiderLat), Double.valueOf(RiderLong));

                m3 = mMap.addMarker(new MarkerOptions().position(mLatLng).title(rider).snippet("Enroute").icon(BitmapDescriptorFactory.fromResource(R.drawable.bikeo)));

                //  LinearLayout ltend =findViewById(R.id.ltend);
                //  ltend.setVisibility(View.GONE);

            }
            if (RequestState.equals("5")) {

                spinner.setVisibility(View.GONE);
                foundride.setVisibility(View.GONE);
                Intent intent2 = new Intent(MainActivity.this, ratehim.class);
                startActivityForResult(intent2, 40);// Activity is started with requestCode 2btn
                myTimer.cancel();
                //  LinearLayout ltend =findViewById(R.id.ltend);
                //  ltend.setVisibility(View.VISIBLE);

            }
            // Walletshare.cSignin myTask = new Walletshare.cSignin(String.valueOf(lz.getCustomerid()));

            //  myTask.execute();


        }

        public ctripstate(String cid) {


            this.cid = cid;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("rideid", tripID);


                //  jsonObject.put("cid", lz.getCustomerid());


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://www.psklok.com/klok/two.php?action=getridestate";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }


    int lockkety() {

        final int min = 1000;
        final int max = 9999;
        final int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }


    void msgbox(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    private class cridecancel extends AsyncTask<String, Void, String> {


        private String cid, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);


        }

        public cridecancel(String cid) {


            this.cid = cid;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("rideid", cid);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://www.psklok.com/klok/two.php?action=cancelride";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }

    private void jlist() {

        String jsonStr = getListData();
        try {
            ArrayList<HashMap<String, String>> userList = new ArrayList<>();
            //   ListView lv = (ListView) findViewById(R.id.lspeople);
            JSONObject jObj = new JSONObject(jsonStr);
            JSONArray jsonArry = jObj.getJSONArray("contacts");
            for (int i = 0; i < jsonArry.length(); i++) {
                HashMap<String, String> user = new HashMap<>();
                JSONObject obj = jsonArry.getJSONObject(i);
                user.put("id", obj.getString("id"));
                user.put("id2", String.valueOf(i + 1));
                user.put("FullName", "FROM :" + obj.getString("fullname").toUpperCase());
                user.put("Telephone", "DATE SENT :" + obj.getString("starts"));
                user.put("ShareDate", "SAFE CODE :" + obj.getString("Passwordgen"));
                userList.add(user);
                notify("PACKAGE SENT", "FROM :" + obj.getString("fullname").toUpperCase());
            }
            // ListAdapter adapter = new SimpleAdapter(MainActivity.this, userList, R.layout.list_row, new String[]{"id2", "FullName", "Telephone","ShareDate","id"}, new int[]{R.id.id2, R.id.fullname, R.id.Telephone,R.id.ShareDate,R.id.id});
            //lv.setAdapter(adapter);
        } catch (JSONException ex) {
            Log.e("JsonParser Example", "unexpected JSON exception", ex);
        }
    }


    private void notify(String tit, String msg) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.icon_awesome_gifts);
        mBuilder.setContentTitle(tit);
        mBuilder.setContentText(msg);
        Intent resultIntent = new Intent(this, mydelivery.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(mydelivery.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        int notificationID = 9;
        mNotificationManager.notify(notificationID, mBuilder.build());

    }


    private String getListData() {

        return jsonStr;
    }


    private class cpkgchk extends AsyncTask<String, Void, String> {


        private String cid, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            JSONObject reader = null;
            String id = "0";
            String wallet = "0";
            String fname = "0";
            try {
                reader = new JSONObject(result);
                jsonStr = result;
                jlist();


            } catch (JSONException e) {
                e.printStackTrace();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }

        public cpkgchk(String cid) {


            this.cid = cid;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("cid", cid);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://www.psklok.com/klok/two.php?action=mybox&d=" + cid;
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }


    private class cancelreq extends AsyncTask<String, Void, String> {


        private String cid, rep;


        @Override
        protected String doInBackground(String... params) {


            try {
                whenSendPostRequest_thenCorrect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // adding each child node to HashMap key => value


            String h = rep;

            return h;

        }

        @Override
        protected void onPostExecute(String result) {
            //TextView txt = (TextView) findViewById(R.id.txres);
            //   tc.setText(result);
            JSONObject reader = null;
            String id = "0";
            String wallet = "0";
            String fname = "0";
            try {
                reader = new JSONObject(result);
                jsonStr = result;
                jlist();


            } catch (JSONException e) {
                e.printStackTrace();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }

        public cancelreq(String cid) {


            this.cid = cid;


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


        public void whenSendPostRequest_thenCorrect()
                throws IOException {

            //  LazyInitializedSingletonExample instance1 = LazyInitializedSingletonExample.getInstance();
            //String rid= instance1.();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            //String json ="jj";
            //
            JSONObject jsonObject = new JSONObject();
            try {

                jsonObject.put("cid", cid);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = jsonObject.toString();

            RequestBody body = RequestBody.create(json, JSON); // new
            // RequestBody body = RequestBody.create(JSON, json); // old
            String url = "https://www.psklok.com/klok/two.php?action=cancelreq&d=" + cid;
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // String ridu = URLEncoder.encode(rid,"utf-8");

            // String url=  "https://www.psklok.com/klok/one.php?action=getdr";


            // okhttp3.Request request = new okhttp3.Request.Builder().get().url(url).build();


            try {
                //  Call call = client.newCall(request);
                Response response = client.newCall(request).execute();
                // Response response = call.execute();

                rep = response.body().string();
                rep = rep.replaceAll("\\n", "");
                rep = rep.replaceAll("\\r", "");

                Log.d("dd", rep);
            } catch (Exception e) {
                Log.d("dd", e.toString());
            }


            //assertThat(response.code(), equalTo(200));
        }

    }


    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent(MainActivity.this, ridechooser.class);
        startActivityForResult(intent2, 9);// Activity is started with requestCode 2btn

        }

}