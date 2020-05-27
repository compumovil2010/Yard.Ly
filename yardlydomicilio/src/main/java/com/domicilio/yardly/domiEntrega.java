package com.domicilio.yardly;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Modelo.Domiciliario;
import Modelo.Usuario;


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
    private String s;
    private String direccion;
    private String r;
    private Restaurant direccionR;
    private Domiciliario domi;
    private Button chat;
    private TextView nomD, dirD,dist ,nombreDomici;
    private ImageView img;
    public static final double lowerLeftLatitude = 1.396967;
    public static final double lowerLeftLongitude= -78.903968;
    public static final double upperRightLatitude= 11.983639;
    public static final double upperRigthLongitude= -71.869905;
    private boolean primeraVez= false;
    private LatLng positionR;
    private Pedido pedido;
    private Marker marcador;
    private Marker dejar;
    private Marker restaurante;
    private boolean yafue;
    private TextView tiempo;
    private String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domi_entrega);
        database= FirebaseDatabase.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        yafue=false;
        if(user == null){
            Intent inte = new Intent(this, logActivity.class);
            Log.i("FDS","ENTRA");
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inte);
        }

            DatabaseReference refAux = database.getReference(Domiciliario.PATH_DOM + user.getUid());
        refAux.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    Toast.makeText(getBaseContext(),"Usted no es domiciliario",Toast.LENGTH_LONG).show();
                    Intent i= new Intent(getBaseContext(),logActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            Log.i("ERRORMIO","MIRE "+refAux);
        chat= findViewById(R.id.btChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(getBaseContext(),Chat.class);
                if (pedido != null){
                    chat.putExtra("pedido",pedido);
                    startActivity(chat);
                }
            }
        });
          nomD= findViewById(R.id.nombreD);
          dirD= findViewById(R.id.direcD);
        dist= findViewById(R.id.tiempo);
        tiempo= findViewById(R.id.dist);
          img= findViewById(R.id.fotoD);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        luz = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        geo = new Geocoder(getBaseContext());

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
        try {
            llenarGUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void llenarGUI() throws IOException {
        final File localFile = File.createTempFile("images", "jpg");
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child(Usuario.PATH_PORFILE_PHOTO+"/"+user.getUid()+".png");

        imageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        img.setImageBitmap(myBitmap);
                        Log.i("IMG", "succesfully downloaded");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("IMG", "No hay Imagen");
            }
        });

    }

    private void inicializarLoc() {
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
                    mipos=mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("Su posicion"));

                    if(!yafue)
                    {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                        obtenerDirCasa();
                        yafue=true;
                    }

                }
            }
        };

    }
    public double distancia(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(long1 - long2);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;
    }
    private LocationRequest createLocationRequest() {
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        inicializarLoc();

        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }


    private LatLng obtenerLatLongR(String addressString) {
        LatLng position = null;
        List<Address> addresses = null;
        if (addressString != null) {
            try {
                addresses = geo.getFromLocationName(addressString, 2,lowerLeftLatitude, lowerLeftLongitude,
                        upperRightLatitude, upperRigthLongitude);
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
                    myMarkerOptions.visible(true);
                    restaurante=mMap.addMarker(myMarkerOptions);
                }
            }
        }
        return  position;
    }

    private LatLng obtenerLatLong(String addressString) {
        LatLng position = null;
        List<Address> addresses = null;
        if (addressString != null) {
            Log.i("AKA","0");
            try {
                addresses = geo.getFromLocationName(addressString, 2,lowerLeftLatitude, lowerLeftLongitude,
                        upperRightLatitude, upperRigthLongitude);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && !addresses.isEmpty()) {
                    Log.i("AKA","1");
                Address addressResult = addresses.get(0);
                position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                if (mMap != null) {
                    Log.i("AKA","2");
                    MarkerOptions myMarkerOptions = new MarkerOptions();
                    myMarkerOptions.position(position);
                    myMarkerOptions.title("Delivery");
                    myMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    myMarkerOptions.visible(true);
                    dejar = mMap.addMarker(myMarkerOptions);
                }
            }
        }
        return  position;
    }

    public void obtenerDirCasa() {
        if(user == null){
            Intent inte = new Intent(this, logActivity.class);
            inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(inte);
        }

        if(database==null)
            database=FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Domiciliario.PATH_DOM + user.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String aux=dataSnapshot.child("nombre").getValue(String.class);
                nomD.setText(aux);
                dataSnapshot= dataSnapshot.child("pedidoActual");
               s= dataSnapshot.getValue(String.class);
               if(s!=null && !s.isEmpty())
               {
                   DatabaseReference myRef2 = database.getReference(Pedido.PATH_PEDIDO + s);
                   myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           Pedido p= dataSnapshot.getValue(Pedido.class);
                           pedido = p;
                           if(p != null)
                           {
                               dirD.setText("Direccion: "+p.getDirUsu());
                               obtenerDirCasa3(p.getDirUsu(),p.getEmpresa());
                           }
                       }
                       @Override
                       public void onCancelled(DatabaseError databaseError) {
                       }
                   });
               }
               else
               {
                   Toast.makeText(getBaseContext(),"No tiene domicilios",Toast.LENGTH_LONG).show();
                   FirebaseAuth.getInstance().signOut();
                   Intent i = new Intent(getBaseContext(),logActivity.class);
                   i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(i);
               }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void obtenerDirCasa3(final String direccione, final String re)
    {
        DatabaseReference myRef3 = database.getReference(Restaurant.PATH_REST);
        myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LatLng position;
                for(DataSnapshot s: dataSnapshot.getChildren())
                {
                    direccionR= s.getValue(Restaurant.class);
                    if(direccionR.getNombreR().equalsIgnoreCase(re))
                    {
                        positionR=obtenerLatLongR(direccionR.getDireccion());
                        if(positionR==null)
                        {
                            Toast.makeText(getBaseContext(),"Direccion de Restaurante no Disp.",Toast.LENGTH_SHORT).show();
                        }

                        position=obtenerLatLong(direccione);
                        if(position==null)
                        {
                            Toast.makeText(getBaseContext(),"Direccion de Usuario no Disp.",Toast.LENGTH_SHORT).show();
                        }
                        pintarRuta();
                        break;
                    }
                }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void pintarRuta() {
        LatLng domic=mipos.getPosition(), resta=restaurante.getPosition(), deja=dejar.getPosition();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dev.virtualearth.net/";
        String path = "REST/v1/Routes";
        String query = "?wayPoint.1={wayPoint1}&viaWaypoint.2={viaWaypoint2}&waypoint.3={waypoint3}&maxSolutions=1&routeAttributes=routePath,excludeItinerary";
        query=query+"&key="+getString(R.string.mapsKey);
        String s=""+domic.latitude+","+domic.longitude;
        query=query.replace("{wayPoint1}",s );
         s=""+resta.latitude+","+resta.longitude;
        query=query.replace("{viaWaypoint2}",s );
         s=""+deja.latitude+","+deja.longitude;
        query=query.replace("{waypoint3}",s);
        Log.i("COSOTA",query);
        StringRequest req = new StringRequest(Request.Method.GET, url+path+query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONArray jsonA = json.getJSONArray("resourceSets");
                            json=jsonA.getJSONObject(0);
                            jsonA = json.getJSONArray("resources");
                            json=jsonA.getJSONObject(0);
                            double distanc =json.getDouble("travelDistance"),tt=Math.ceil(json.getDouble("travelDuration")/60);
                            json=json.getJSONObject("routePath");
                            json=json.getJSONObject("line");
                            jsonA = json.getJSONArray("coordinates");
                            if(positionR!= null)
                            {
                                if(!primeraVez)
                                {
                                    DatabaseReference dista = database.getReference(Domiciliario.PATH_DOM + user.getUid() + "/dist");
                                    dista.setValue(distanc);
                                    dista = database.getReference(Domiciliario.PATH_DOM + user.getUid() + "/tiempo");
                                    dista.setValue(tt);
                                    primeraVez=true;

                                }
                                String s="Distancia Estimada: "+distanc;
                                dist.setText(s);
                                s="Tiempo Estimado: "+tt+" minutos aprox.";
                                tiempo.setText(s);
                            }
                            drawNow(jsonA);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("COSOTA", "Error handling rest invocation"+error.getCause());
                    }
                }
        );
        queue.add(req);

    }

    private void drawNow(JSONArray coord) throws JSONException {


        for(int z = 0; z<coord.length()-1;z++){
            JSONArray src = coord.getJSONArray(z);
            JSONArray dest = coord.getJSONArray(z + 1);

            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(src.getDouble(0), src.getDouble(1)), new LatLng(dest.getDouble(0), dest.getDouble(1)))
                    .width(6)
                    .color(Color.BLUE).geodesic(true));
        }

    }

    private String getNombre(LatLng latLng) throws IOException {
        List<Address> ad = geo.getFromLocation(latLng.latitude,latLng.longitude,1);
        return ad.get(0).getAddressLine(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
