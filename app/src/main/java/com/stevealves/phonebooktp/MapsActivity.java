package com.stevealves.phonebooktp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

    int id;
    private Double lat;
    private Double log;
    private String nome;

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
                //Log.d("localizacao", "onLocationChanged: " + location.toString());
                //Toast.makeText(getApplicationContext(), "hereeeeee", Toast.LENGTH_LONG).show();

                double Mylatitude = location.getLatitude();
                double Mylongitude= location.getLongitude();

                //mMap.clear();
                LatLng meuEndereco = new LatLng(Mylatitude, Mylongitude);
                mMap.addMarker(new MarkerOptions().position(meuEndereco).title("Meu Endereço"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meuEndereco, 10));

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

        // get contact data by position
        getData();

        // localizacao do contact
        // mMap.clear();
        LatLng enderecoContact = new LatLng(lat, -log);
        mMap.addMarker(new MarkerOptions().position(enderecoContact).title("Endereço de " + nome));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(enderecoContact, 10));

        //create route between me and the conctact
        //"https://www.journaldev.com/13373/android-google-map-drawing-route-two-points"
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

    public void getData(){
        id = getIntent().getIntExtra("id", 0);
        lat = Common.listaContactos.get(id).getLatitude();
        log = Common.listaContactos.get(id).getLongitude();
        nome = Common.listaContactos.get(id).getFullName();
    }

}
