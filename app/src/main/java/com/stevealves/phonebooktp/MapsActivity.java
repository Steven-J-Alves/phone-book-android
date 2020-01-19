package com.stevealves.phonebooktp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.stevealves.phonebooktp.utils.Common;
import com.stevealves.phonebooktp.utils.Permissoes;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //Adicionar permissoes
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    //
    private LocationManager locationManager;

    //conf location listener para ouvir as atualiazoes da localizacao do usuario
    private LocationListener locationListener;

    MarkerOptions meuEndereco, contactoEndereco;

    private double lat;
    private double longi;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Validar as permissoes
        Permissoes.validarPermissoes(permissoes, this, 1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // conf objecto de obter a localizacao do usuario
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // conf objecto de updates de localizacao do usuario
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("localizacao", "onLocationChanged: " + location.toString());

                double Mylatitude = location.getLatitude();
                double Mylongitude = location.getLongitude();

                LatLng meuEndereco = new LatLng(Mylatitude, Mylongitude);
                mMap.addMarker(new MarkerOptions().position(meuEndereco).title("Meu Endereço"));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        // quando o user ja permitiu o acesso a localizacao
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    locationListener
            );
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                lat = latLng.latitude;
                longi = latLng.longitude;

                Toast.makeText(getApplicationContext(), lat + "-" + longi, Toast.LENGTH_SHORT).show();

                // localizacao do contact
                LatLng enderecoContact = new LatLng(lat, longi);

                mMap.addMarker(new MarkerOptions().position(enderecoContact).title("Endereço do Contacto"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(enderecoContact, 10));

                Intent intent = new Intent(getApplicationContext(), New.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", longi);
                startActivity(intent);
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultados : grantResults) {
            if (permissaoResultados == PackageManager.PERMISSION_DENIED) {
                // ALERTA
                alerteValidacaoPermissao();
            } else if (permissaoResultados == PackageManager.PERMISSION_GRANTED) {
                // RECUPERAR LOCALIZACAO DO USUARIO
                /*
                 *  1 - provedor de localizacao
                 *  2 - tempo min entre updates de localizacao
                 *  3 - distantcia min entre updates de local...
                 *  4 - location listener (para receber as atualizacoes)
                 * */
                // quando o user permite o acesso a localizao
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0,
                            locationListener
                    );
                }
            }
        }
    }

    public void alerteValidacaoPermissao(){
        Toast.makeText(getApplicationContext(), "sem permisscao", Toast.LENGTH_SHORT).show();
        finish();
        // Implementar AlertDialog
    }

}
