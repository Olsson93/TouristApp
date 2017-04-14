package grant.touristapp;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

import static android.R.attr.data;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AddEntryDialog.AddEntryDialogListener, GoogleMap.OnMarkerClickListener, MarkerDialog.MarkerDialogListener {

    private GoogleMap mMap;

    Button btn_addEntry, btn_editEntries;
    private String TAG = "EntryInfo";
    SQLiteDatabase dtb;
    DBHandler db;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;


    String imageString = "image";

    GoogleApiClient mClient;

    Location location;
    double latitude;
    double longitude;

    private static final LatLng EDINBURGH_CASTLE = new LatLng(55.9486, -3.1999);
    private static final LatLng MUSEUM = new LatLng(55.947914, -3.189114);
    private static final LatLng MARY_KINGS_CLOSE = new LatLng(55.9499,-3.1905);
    private static final LatLng DYNAMIC_EARTH = new LatLng(55.9505, -3.1744);
    private static final LatLng EDINBURGH_DUNGEON = new LatLng(55.9511, -3.1909);
    private static final LatLng THE_SCOTT_MONUMENT = new LatLng(55.9524, -3.1933);

    private Marker mEdinburghCastle;
    private Marker mMuseum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new DBHandler(this);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_addEntry = (Button) findViewById(R.id.btn_addEntry);
        btn_editEntries = (Button) findViewById(R.id.btn_edit);


        btn_addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddEntryDialog dialog = new AddEntryDialog();
                dialog.show(getFragmentManager(), TAG);


            }

        });

        btn_editEntries.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent myIntent = new Intent(MapsActivity.this, JournalEntryActivity.class);
                startActivity(myIntent);
            }
        });



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
        GoogleApiClient mClient;

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        latitude = location.getLatitude();
        longitude = location.getLongitude();





        // Add a marker in Sydney and move the camera
        LatLng edinburgh = new LatLng(55.95, -3.1883);




        mMap.moveCamera(CameraUpdateFactory.newLatLng(edinburgh));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        mEdinburghCastle = mMap.addMarker(new MarkerOptions()
                                .position(EDINBURGH_CASTLE)
                                .title("Edinburgh Castle")
                                .snippet("Edinburgh Castle is a fortress which dominates the skyline of the city of Edinburgh, Scotland, from its position on the Castle Rock")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mEdinburghCastle.setTag(0);



        mMuseum = mMap.addMarker(new MarkerOptions()
                        .position(MUSEUM)
                        .title("Museum")
                        .snippet("Explore world cultures and enthuse at treasures from around the world. Featuring a diverse range of art and deisgn exhibits, interactive displays and games, a visit to the National Museum of Scotland will appeal to all ages.")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMuseum.setTag(0);

        mMap.addMarker(new MarkerOptions()
                        .position(MARY_KINGS_CLOSE)
                        .title("Mary King's Close")
                        .snippet("Buried deep beneath Edinburgh's Royal Mile. The city's deepest secret; a warren of hidden streets that have remained there since the 17th Century. The hidden Closses of Old Town Edinburgh have been shrouded in myths and mysteries. ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mMap.addMarker(new MarkerOptions()
                        .position(DYNAMIC_EARTH)
                        .title("Dynamic Earth")
                        .snippet("Learn about the creation of our planet and travel through time as we explore Earth's past, present and future. Get thrust back in time to the very beginnings of our universe and witness first hand the big bang and the very first moments of our galaxy and solar system.")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mMap.addMarker(new MarkerOptions()
                        .position(EDINBURGH_DUNGEON)
                        .title("Edinburgh Dungeon")
                        .snippet("Uncover Edibburgh's grisly history at The Edinburgh Dungeon. You'll come face to face with Scotland's cannibals, discover the streets of the Green Lady and have a close encounter with Edinburgh infamous killer duo Burke and Hare.")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.addMarker(new MarkerOptions()
                        .position(THE_SCOTT_MONUMENT)
                        .title("The Scott Munument")
                        .snippet("The largest monument to a writer in the world commemorating the Scottish novelist Sir Walter Scott. Built in the 19th century, it is on of the most striking landmarks in Edinburgh. Climb the steps to the top and enjoy breathtaking views of Edinburgh and the surrounding countryside.")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        mMap.setOnMarkerClickListener(this);

//        Polyline line = mMap.addPolyline(new PolylineOptions()
//            .add(MUSEUM,EDINBURGH_CASTLE)
//            .width(5)
//            .color(Color.RED)
//            .geodesic(true));

        List<JournalEntry> journalsList = db.getAllEntries();

        for(JournalEntry journal : journalsList){

            mMap.addMarker(new MarkerOptions()
                .position(new LatLng(journal.getLAT(), journal.getLNG()))
                .title(journal.getTitle())
                .snippet(journal.getNotes()));

        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


    }

//    public void geoLocate(View view) throws IOException
//    {
//
//        EditText et = (EditText) findViewById(R.id.);
//        String location = et.getText().toString();
//
//        Geocoder gc = new Geocoder(this);
//        List<Address> list = gc.getFromLocationName(location, 1);
//        Address address = list.get(0);
//        String locality = address.getLocality();
//
//        Toast.makeText(this, locality,Toast.LENGTH_LONG).show();
//        double lat = address.getLatitude();
//        double lng = address.getLongitude();
//
//        LatLng ll = new LatLng(lat, lng);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
//        mMap.setMaxZoomPreference(100);
//
//        MarkerOptions options = new MarkerOptions()
//                .title(locality)
//                .position(new LatLng(lat, lng))
//                .snippet("I am here");
//        mMap.addMarker(options);
//
//
//
//
//
//    }

    @Override
    public void onSaveButtonClick(DialogFragment dialog){

        EditText edtEntryTitle = (EditText) dialog.getDialog().findViewById(R.id.edtEntryTitle);
        String entryTitle = edtEntryTitle.getText().toString();

        EditText edtNotes = (EditText) dialog.getDialog().findViewById(R.id.edtNotes);
        String notes = edtNotes.getText().toString();

        double sendLatitude = latitude;
        double sendLongtitude = longitude;

        int id = 1;
        int LAT = 122;
        int LNG = 213;

        boolean check_entryTitle = checkEntryTitle(entryTitle);

        boolean check_notes = checkNotes(notes);

        if(check_entryTitle == false || check_notes == false){
            Toast.makeText(getApplicationContext(), "Enter data again.", Toast.LENGTH_LONG).show();
        }else{
            db.addNewEntry(new JournalEntry(entryTitle, notes, latitude, longitude));

            Toast.makeText(getApplicationContext(), "Entry added.", Toast.LENGTH_LONG).show();
        }

    }

    public void onPhotoButtonClick(DialogFragment dialog){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }

    public boolean checkEntryTitle(String entryTitle){
        if(entryTitle == ""){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkNotes(String notes){
        if(notes == ""){
            return false;
        }else{
            return true;
        }
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer clickCount = (Integer) marker.getTag();
        MarkerDialog dialog = new MarkerDialog();
        dialog.setMarkerClicked(marker.getTitle());
        dialog.setMessage(marker.getSnippet());
        dialog.show(getFragmentManager(), TAG);

        if(clickCount != null){
            clickCount = clickCount +1;
            marker.setTag(clickCount);
            Toast.makeText(this, marker.getTitle() + " has been clicked " + clickCount + " times ", Toast.LENGTH_SHORT).show();


        }
        return false;
    }

    @Override
    public void onMarkerDialogClick(DialogFragment dialog) {

    }

    @Override
    public void onRouteClick(DialogFragment dialog) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 0:
                if(resultCode == RESULT_OK) {
                    AddEntryDialog dialog = new AddEntryDialog();
                    dialog.show(getFragmentManager(), TAG);
                    Uri selectedImage = data.getData();
                    Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_SHORT).show();

                    dialog.setImage(selectedImage);


                 //   mImageView.setImageURI(selectedImage);
                }
            break;
            case 1:
                if(resultCode == RESULT_OK){
                    AddEntryDialog dialog = new AddEntryDialog();
                    dialog.show(getFragmentManager(), TAG);
                    Uri selectedImage = data.getData();
                    Toast.makeText(this, selectedImage.toString(), Toast.LENGTH_SHORT).show();

                    dialog.setImage(selectedImage);
//                    edtImage = (EditText)findViewById(R.id.edtImage);
//                    imageString = data.toString();

                  //  mImageView.setImageURI(selectedImage);
                }
            break;
        }
    }



    }
