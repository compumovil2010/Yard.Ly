package com.domicilio.yardly;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.IOException;
import java.util.List;

import Modelo.Domiciliario;
import Modelo.Pedido;
import Modelo.Restaurante;

public class domiEntrega extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SensorManager manager;
    private Sensor luz;
    private SensorEventListener list;
    public static final int  MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_CHECK_SETTINGS=5,RADIUS_OF_EARTH_KM = 6371;;
    private Geocoder geo;
    private FusedLocationProviderClient mfusedLoc;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Marker mipos=null;
    private Marker usupedido;
    private FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference lat,lo;
    private static final String PATH_PEDIDOS="pedido/";
    private Domiciliario d;
    private String s;
    private String direccion;
    private String r;
    private Restaurante direccionR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database= FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_domi_entrega);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent inte = new Intent(this, logActivity.class);
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inte);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        luz = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        geo = new Geocoder(getBaseContext());
        inicializarLoc();

        list= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(mMap!=null && luz!=null)
                {
                    if(event.values[0]<300)
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(domiEntrega.this, R.raw.dark_style_map));
                    else
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(domiEntrega.this, R.raw.light_style_map));

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void inicializarLoc() {
        Toast.makeText(this, "IniLOC", Toast.LENGTH_SHORT).show();
        mfusedLoc= LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = createLocationRequest();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    if(mipos!=null)
                        mipos.remove();
                    //actualizarRefEnBD
                    lat=database.getReference(Domiciliario.PATH_DOM+user.getUid()+"/lat");
                    lo=database.getReference(Domiciliario.PATH_DOM+user.getUid()+"/longi");
                    lat.setValue(location.getLatitude());
                    lo.setValue(location.getLongitude());
                    mipos=mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                }
            }
        };
    }

    private LocationRequest createLocationRequest() {
        Toast.makeText(this, "LocReq", Toast.LENGTH_SHORT).show();
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //tasa de refresco en milisegundos
        mLocationRequest.setFastestInterval(5000); //máxima tasa de refresco
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Aqui va acceso a BD con usuario
        obtenerDirCasa();

        LatLng position;

        String addressString = direccionR.getDireccion();
        position=obtenerLatLongR(addressString);
        if(position==null)
        {
            Toast.makeText(this,"Direccion de Restaurante no Disp.",Toast.LENGTH_SHORT).show();
        }

        position=obtenerLatLong(direccion);
        if(position==null)
        {
            Toast.makeText(this,"Direccion de Restaurante no Disp.",Toast.LENGTH_SHORT).show();
        }
    }

    private LatLng obtenerLatLongR(String addressString) {
        LatLng position = null;
        List<Address> addresses = null;
        if (addressString != null) {
            try {
                addresses = geo.getFromLocationName(addressString, 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                if (mMap != null) {
                    MarkerOptions myMarkerOptions = new MarkerOptions();
                    myMarkerOptions.position(position);
                    myMarkerOptions.title(direccionR.getNombreR());
                    myMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }
            }
        }
        return  position;
    }

    private LatLng obtenerLatLong(String addressString) {
        LatLng position = null;
        List<Address> addresses = null;
        if (addressString != null) {
            try {
                addresses = geo.getFromLocationName(addressString, 2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && !addresses.isEmpty()) {
                Address addressResult = addresses.get(0);
                position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                if (mMap != null) {
                    MarkerOptions myMarkerOptions = new MarkerOptions();
                    myMarkerOptions.position(position);
                    myMarkerOptions.title("Delivery");
                    myMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }
            }
        }
        return  position;
    }

    public void obtenerDirCasa() {
        DatabaseReference myRef = database.getReference(Domiciliario.PATH_DOM + user.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot= dataSnapshot.child("pedidoActual");
               s= dataSnapshot.getValue(String.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference myRef2 = database.getReference(Pedido.PATH_PEDIDO + s);
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                direccion= dataSnapshot.child("DirUsu").getValue(String.class);
                r=dataSnapshot.child("Empresa").getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference myRef3 = database.getReference(Restaurante.PATH_REST+r);
        myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                direccionR= dataSnapshot.getValue(Restaurante.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private String getNombre(LatLng latLng) throws IOException {
        List<Address> ad = geo.getFromLocation(latLng.latitude,latLng.longitude,1);
        return ad.get(0).getAddressLine(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
        startSetLocation();
        manager.registerListener(list, luz,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationS();
        manager.unregisterListener(list);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocation(); //Todas las condiciones para recibir localizaciones
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(domiEntrega.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                        } break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode == RESULT_OK) {
                    startLocation(); //Se encendió la localización!!!
                } else {
                    Toast.makeText(this,
                            "Sin acceso a localización, hardware deshabilitado!",
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private void startSetLocation() {
        Toast.makeText(this, "setLoc", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
            SettingsClient client = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    startLocation(); //Todas las condiciones para recibir localizaciones
                }
            });
            task.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(domiEntrega.this,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sendEx) {
                            } break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
        else
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "Es necesario para poder mostrarle la distancia", Toast.LENGTH_LONG).show();
                }
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }
        }
    }

    private void startLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mfusedLoc.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }

    private void stopLocationS() {
        mfusedLoc.removeLocationUpdates(mLocationCallback);
    }
}
