package com.stevealves.phonebooktp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stevealves.phonebooktp.utils.Common;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    int id;
    private Double lat;
    private Double log;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getData();

        // Add a marker in address and move the camera
        LatLng enderecoContact = new LatLng(lat, -log);

        mMap.addMarker(new MarkerOptions().position(enderecoContact).title("Endereço de " + nome));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(enderecoContact, 10 ));
    }

    public void getData(){
        id = getIntent().getIntExtra("id", 0);
        lat = Common.listaContactos.get(id).getLatitude();
        log = Common.listaContactos.get(id).getLongitude();
        nome = Common.listaContactos.get(id).getFullName();
    }

}
