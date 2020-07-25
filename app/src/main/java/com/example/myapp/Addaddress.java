package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class Addaddress extends AppCompatActivity implements OnMapReadyCallback{

    private static final int REQUEST_LOCATION_PERMISSION =1;
    private Button add;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private SearchView search;
    private String location;
    private List<Address> addressList;
    private Geocoder geocoder;
    private Address address;
    private LatLng latLng;
    private DatabaseReference ref;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);
        setTitle("Enter Location");
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setup();
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                location=search.getQuery().toString();
                addressList=null;
                address=null;
                if(location!=null || !location.equals("")){
                    geocoder=new Geocoder(Addaddress.this);
                    try {
                        addressList=geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addressList.isEmpty()){
                        Toast.makeText(Addaddress.this,"Please Enter Whole Address",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    address=addressList.get(0);
                    location=address.getAddressLine(0);
                    Toast.makeText(Addaddress.this,address.getLocality(),Toast.LENGTH_SHORT).show();
                    latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    map.clear();
                    map.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.0f));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressList!=null){
                    double lat=address.getLatitude();
                    double Log=address.getLongitude();
                    AddressSetup a=new AddressSetup(location,lat,Log);
                    String key=ref.push().getKey();
                    ref.child(key).setValue(a);
                    startActivity(new Intent(Addaddress.this,Manageaddress.class));
                    finish();
                }
            }
        });

    }

    private void setup(){
        add=(Button) findViewById(R.id.btnsave);
        mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        search=(SearchView) findViewById(R.id.svLocation);
        addressList=null;
        ref= FirebaseDatabase.getInstance().getReference("Addresses").child(FirebaseAuth.getInstance().getUid());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Manageaddress.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        latLng=new LatLng(21.204963, 72.885694);
        map.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
    }

}
