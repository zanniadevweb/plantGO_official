package com.example.test_libgdxintoandroid;

//* Import propre à Android *//
import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import java.util.Random;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import com.google.android.material.snackbar.Snackbar;

//* Import propre à la lecture / écriture de fichiers *//
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.os.Environment;

//* Import propre à Google Maps *//
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity<LocationRequest> extends AppCompatActivity implements OnMapReadyCallback {

    /** --------------------------------------------------- Attributs pour localisation -------------------------------------------------- **/
    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Provides access to the Location Settings API.
     */
    private SettingsClient mSettingsClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private com.google.android.gms.location.LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;

    /**
     * Represents a geographical location.
     */
    private Location mCurrentLocation;

    // UI Widgets.
    private TextView mLastUpdateTimeTextView;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;

    // Labels.
    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private String mLastUpdateTimeLabel;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    private String mLastUpdateTime;


    private String SAVE = "sauvegarde.txt"; // resultatJeuCoffreTresor[0] | experienceActuelle[1] | queteAcceptee[2]
    private String resultatJeuCoffreTresor = String.valueOf(Modele.jeuCoffreTresorGagne);
    private String queteEstAcceptee = String.valueOf(Modele.queteAcceptee);
    private String experienceActuelle = String.valueOf(Modele.experienceTotaleActuelle);
    private final String pipeSeparation = "|";
    private File mFile = new File(SAVE);
    Boolean coffreEnCours = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0,0); //supprimer l'animation au changement d'activité

        String nomScientifique = Modele.planteCourante;

        TextView tv8 = findViewById(R.id.planteQuete1);
        tv8.setText(Modele.plantesQueteCourante[0]);

        /* ATTENTION, il faudra faire en sorte que le code résultant de ces conditions
        ne s'exécute que si TOUTES les plantes de la quête en cours ont été identifiées */
        if (Modele.isInTheWeeklyQuest(nomScientifique) && Modele.queteAcceptee) {
            Modele.queteTerminee = true;
            Modele.queteAcceptee = false;
        }
        /* ATTENTION, il faudra faire en sorte que le code résultant de ces conditions
        ne s'exécute que si UNE plante a été reconnue ==> lancer de dé pour jouer ou non */
        if (Modele.queteTerminee && Modele.resultatpartie.equals("Partie non déterminée") && !Modele.lancerDeDejaFait) {
            Modele.lancerDeDejaFait = true;
            lancerUnDe();
        }
        if (Modele.queteTerminee && !Modele.queteAcceptee) {
            if (!coffreEnCours)
            {
                coffreEnCours=true;
                apparitionCoffre();
            }
        }
        if (Modele.queteTerminee && !Modele.popUpActif) {
            Modele.popUpActif = true;

            TextView tv6 = findViewById(R.id.textViewQueteEnCours);
            ImageView img2 = findViewById(R.id.fondMessage);
            Button bt8 = findViewById(R.id.terminerQuete);

            View[] views = {img2, bt8, tv6};

            for (View view : views) {
                view.setVisibility(View.VISIBLE);
            }

            tv6.setText("La quête est terminée");
        }

        if (!Modele.resultatpartie.equals("Partie indeterminée")) {
            testValeurRetourJeu();
        }

        /** --------------------------------------------------- Méthodes pour Google Maps -------------------------------------------------- **/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /** --------------------------------------------------- Méthodes pour Google Maps -------------------------------------------------- **/

        /** --------------------------------------------------- Méthodes pour localisation -------------------------------------------------- **/

        // Locate the UI widgets.
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);

        // Set labels.
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);

        mRequestingLocationUpdates = true;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();

        ImageView img1 = findViewById(R.id.jetDe);
        Button bt4 = findViewById(R.id.button);
        Button bt5 = findViewById(R.id.button2);
        Button bt6 = findViewById(R.id.gameHorizontal);
        Button bt7 = findViewById(R.id.gameVertical);
        TextView tv0 = findViewById(R.id.textView);
        TextView tv3 = findViewById(R.id.tempsDeJeu);
        TextView tv4 = findViewById(R.id.planteAChercher);
        TextView tv5 = findViewById(R.id.planteQuete1);
        TextView tv7 = findViewById(R.id.jeJoue);

        View[] views1 = {img1, bt4, bt6, bt5, bt7, tv0, tv3, tv4, tv5,tv0, tv7};

        for (View view : views1) {
            view.setVisibility(View.INVISIBLE);
        }

        if (!Modele.firstLoadingApplication) {
            View[] views2 = { bt4, bt5, bt6, bt7, tv0, tv3, tv4, tv5 };

            for (View view : views2) {
                view.setVisibility(View.VISIBLE);
            }
        }

        if (Modele.firstLoadingApplication) {
            chargerFichier();
            accepterQuete();
        }
        if (!Modele.firstLoadingApplication) {
            sauvegarderFichier();
        }
    }

    public void terminerQuete(View view) {
        TextView tv6 = findViewById(R.id.textViewQueteEnCours);
        ImageView img2 = findViewById(R.id.fondMessage);
        Button bt8 = findViewById(R.id.terminerQuete);

        View[] views = {img2, bt8, tv6};

        for (View view2 : views) {
            view.setVisibility(View.INVISIBLE);
        }

        tv6.setText("");
    }

    public void launchGameHorizontal(View view) {
        Modele.firstLoadingApplication = false;
        Modele.testBoolean = false;
        remettreZeroParametresJeu();
        lancerConsignesJeu();
    }

    public void launchGameVertical(View view) {
        Modele.firstLoadingApplication = false;
        Modele.testBoolean = true;
        remettreZeroParametresJeu();
        lancerConsignesJeu();
    }

    public void testValeurRetourJeu() {
        TextView tv1 = findViewById(R.id.textView);
        tv1.setVisibility(View.VISIBLE);
        TextView tv2 = findViewById(R.id.tempsDeJeu);
        tv2.setVisibility(View.VISIBLE);

        Integer tempsJeu = Modele.tempsPartie;

        if (Modele.resultatpartie.equals("Partie gagnée")) {
            Integer experienceJeuGagne = 50;
            Integer experienceSupplementaireTemps = 0;

            // Récupération du temps de jeu
            tv2.setText("Temps de jeu = " + String.valueOf(tempsJeu) + " secondes");

            if (tempsJeu > 200) { // Temps Jeu compris entre 200 et 300 s
                experienceSupplementaireTemps = 50;
            }
            if (tempsJeu > 150 && tempsJeu < 200) { // Temps Jeu compris entre 150 et 200 s
                experienceSupplementaireTemps = 25;
            }
            if (tempsJeu > 250 && tempsJeu < 150) { // Temps Jeu compris entre 150 et 250 s
                experienceSupplementaireTemps = 10;
            }

            Modele.experienceTotale = experienceJeuGagne + experienceSupplementaireTemps;
            if (Modele.pasEncoreAjoutExperience) {
                Modele.experienceTotaleActuelle = Modele.experienceTotaleActuelle + Modele.experienceTotale;
                Modele.pasEncoreAjoutExperience = false;
            }

            tv1.setText( "Partie gagnée. Action : gain expérience = " + experienceJeuGagne + " xp" +
                    "\navec un bonus de " + experienceSupplementaireTemps + " xp. Expérience totale = " + Modele.experienceTotale + " xp");

        }
        if (Modele.resultatpartie == "Partie perdue") {
            tv1.setText( "Partie perdue. Action : PAS de gain expérience");
            tv2.setText("Temps PAS PRIS en compte");
        }
    }

    public void remettreZeroParametresJeu() {
        Modele.resultatpartie = "Partie non déterminée"; // Permet au retour d'une partie (gagnée ou perdue) de remettre à zéro l'activité "ConsignesDeJeu"
        Modele.jetonRejouer = 1; // Permet au retour d'une partie (gagnée ou perdue) de remettre à zéro le jeton pour rejouer à un jeu
    }

    public void lancerConsignesJeu() {
        Intent intent = new Intent(MainActivity.this, ConsignesDeJeu.class);
        MainActivity.this.startActivity(intent);
    }


    public void accepterQuete() {
        Modele.queteAcceptee = true;
    }


    // afficher profil
    public void displayProfile(View view) {
        Modele.firstLoadingApplication = false;
        Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
        MainActivity.this.startActivity(intent);
    }

    // afficher app. photo
    public void displayAppPhoto(View view) {
        Modele.firstLoadingApplication = false;
        Intent intent = new Intent(MainActivity.this, AppPhotoActivity.class);
        MainActivity.this.startActivity(intent);
    }


    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);

            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            updateUI();
        }
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new com.google.android.gms.location.LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();
            }
        };
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        updateUI();
                        break;
                }
                break;
        }
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        updateUI();
                    }
                });
    }

    /**
     * Updates all UI fields.
     */
    private void updateUI() {
        updateLocationUI();
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            mLatitudeTextView.setText(String.format(Locale.ENGLISH, "%s: %f", mLatitudeLabel,
                    mCurrentLocation.getLatitude()));
            mLongitudeTextView.setText(String.format(Locale.ENGLISH, "%s: %f", mLongitudeLabel,
                    mCurrentLocation.getLongitude()));
            mLastUpdateTimeTextView.setText(String.format(Locale.ENGLISH, "%s: %s",
                    mLastUpdateTimeLabel, mLastUpdateTime));

            double latitude = mCurrentLocation.getLatitude();
            double longitude = mCurrentLocation.getLongitude();

            // The computed distance is stored in results[0].
            //If results has length 2 or greater, the initial bearing is stored in results[1].
            //If results has length 3 or greater, the final bearing is stored in results[2]
            float[] distance = new float[1];

            Location.distanceBetween(
                    latitude, // Latitude de départ (Moi)
                    longitude, // Longitude de départ (Moi)
                    Modele.marqueurQuete.latitude, // Latitude de fin (marqueurQuete)
                    Modele.marqueurQuete.longitude, // Longitude de fin (marqueurQuete)
                    distance); // Résultat = distance

            String updatetime = mLastUpdateTime;

            Modele.latitudeTempsT = latitude;
            Modele.longitudeTempsT = longitude;

            // Au départ le marqueur est null et il est initialisé à une valeur non null dans le IF. Le marqueur n'étant plus nul, il peut être enlevé et mis à null dans le ELSE.
            if (Modele.MyMarker == null)
            {
                Modele.maPosition = new LatLng(Modele.latitudeTempsT, Modele.longitudeTempsT);
                Modele.MyMarker = mMap.addMarker(new MarkerOptions().position(Modele.maPosition).title("Moi").icon(BitmapDescriptorFactory.fromResource((R.drawable.moi))));
            }
            else
            {
                Modele.MyMarker.remove();
                Modele.MyMarker = null;

                if (distance[0] < 210) { // Si distance est inférieure à 210 mètres
                    TextView tv5 = findViewById(R.id.marqueur_quete_text);
                    String nom_plante1 = getString(R.string.nom_plante1);
                    tv5.setText("Vous êtes dans la zone de quête (+ ou - 210 mètres) du centre. C'est ici que vous trouverez un(e) " + nom_plante1);
                } else {
                    TextView tv5 = findViewById(R.id.marqueur_quete_text);
                    tv5.setText("(Hors zone de quête)");
                }
            }

            if (Modele.marqueurCoffre != null) //rajouté si quete finie
            {

                float[] distance2 = new float[1];

                Location.distanceBetween(
                        latitude, // Latitude de départ (Moi)
                        longitude, // Longitude de départ (Moi)
                        Modele.marqueurCoffre.latitude, // Latitude de fin (marqueurQuete)
                        Modele.marqueurCoffre.longitude, // Longitude de fin (marqueurQuete)
                        distance2); // Résultat = distance

                if (distance2[0] < 20) { // Si distance est inférieure à 20 mètres
                    TextView tv5 = findViewById(R.id.marqueur_quete_text);
                    tv5.setText("Vous êtes proche du coffre ");
                    //lancer le mini-jeu du coffre
                    Intent intent = new Intent(MainActivity.this, JeuCoffreTresor.class);
                    MainActivity.this.startActivity(intent);
                } else {
                    TextView tv5 = findViewById(R.id.marqueur_quete_text);
                    tv5.setText("Vous êtes loin du coffre");
                }
            }
            //mMap.clear();
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(Modele.moi)); // Suit en permanance (chaque fois que la localisation est actualisée) ma position

            Log.d("loc", "latitude " + latitude);
            Log.d("loc", "longitude " + longitude);
            if (Modele.queteAcceptee) {
                Log.d("loc", "distance " + distance[0]);
            }
            Log.d("time", "time " + updatetime);

            // Il faudrait que cette instruction ne s'exécute qu'une seule fois (sinon surcharge la mémoire pour rien : car un point chargé/créé le reste pour toujours) ==> Le false / true ne marche pas
            boolean marqueurQueteDejaAjoute = false;
            if (Modele.queteAcceptee && !marqueurQueteDejaAjoute) {
                mMap.addMarker(new MarkerOptions().position(Modele.marqueurQuete).title("Quête").icon(BitmapDescriptorFactory.fromResource((R.drawable.plantequete))));
                marqueurQueteDejaAjoute = true; // dit "redondant" ???
                Modele.circle.setVisible(true);
            }

        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we remove location updates. Here, we resume receiving
        // location updates if the user has requested them.
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            requestPermissions();
        }

        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove location updates to save battery.
        stopLocationUpdates();
    }

    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mRequestingLocationUpdates) {
                    Log.i(TAG, "Permission granted, updates requested, starting location updates");
                    startLocationUpdates();
                }
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    /** --------------------------------------------------- Méthodes pour localisation -------------------------------------------------- **/




    /** --------------------------------------------------- Méthodes pour Google Maps -------------------------------------------------- **/

    private GoogleMap mMap; // Attribut propre à Google Maps

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

        // Add a marker in Anglet and move the camera
        Modele.marqueurQuete = new LatLng(43.47834075276044, -1.5079883577079218);
        //Marker marqueurQuete = mMap.addMarker(new MarkerOptions().position(Modele.marqueurQuete).title("Quête").icon(BitmapDescriptorFactory.fromResource((R.drawable.plantequete))));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Modele.marqueurQuete));
        Modele.circle = mMap.addCircle(new CircleOptions()
                .center(Modele.marqueurQuete)
                .radius(210)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));

        // Rendre invisible au départ
        Modele.circle.setVisible(false);

    }

    public Integer timeCountInMilliSeconds = 4000;

    private void lancerUnDe() {
        View mapFragment = findViewById(R.id.map);
        mapFragment.setVisibility(View.INVISIBLE);

        ImageView img1 = findViewById(R.id.jetDe);
        TextView tv1 = findViewById(R.id.resultatJetDe);

        View[] views2 = {img1, tv1 };
        for (View view : views2) {
            view.setVisibility(View.VISIBLE);
        }

        Integer randomJouerOuNon = new Random().nextInt(6) + 1; // [0, 1] + 1 => [1, 6] : Minimum 1 (si [0] + 1) et maximum 6 (si [1] + 1)
        tv1.setText(String.valueOf(randomJouerOuNon));

        if (randomJouerOuNon > 3) {
            timerAvantJeu();
        }
        if (randomJouerOuNon <= 3) {
            timerSiPasJeu();
        }
    }

    private void timerAvantJeu() {
        CountDownTimer countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                Button bt4 = findViewById(R.id.button);
                Button bt5 = findViewById(R.id.button2);
                Button bt6 = findViewById(R.id.gameHorizontal);
                Button bt7 = findViewById(R.id.gameVertical);
                TextView tv0 = findViewById(R.id.textView);
                TextView tv3 = findViewById(R.id.tempsDeJeu);
                TextView tv4 = findViewById(R.id.planteAChercher);
                TextView tv5 = findViewById(R.id.planteQuete1);
                TextView tv6 = findViewById(R.id.textViewQueteEnCours);
                TextView tv7 = findViewById(R.id.jeJoue);
                TextView tv8 = findViewById(R.id.longitude_text);
                TextView tv9 = findViewById(R.id.latitude_text);
                TextView tv10 = findViewById(R.id.last_update_time_text);
                TextView tv12 = findViewById(R.id.marqueur_quete_text);

                View[] views = {bt4,bt5,bt6,bt7, tv0,tv3,tv4,tv5,tv6, tv7, tv8,tv9,tv10,tv12 };

                for (View view : views) {
                    view.setVisibility(View.INVISIBLE);
                }
                ImageView img1 = findViewById(R.id.jetDe);
                img1.setVisibility(View.VISIBLE);
                tv7.setVisibility(View.VISIBLE);
                tv7.setText("Vous allez jouer !");

            }
            @Override
            public void onFinish() {
                // Lance un nombre aléatoire compris entre 1 (Jeu Horizontal) et 2 (Jeu Vertical)
                Modele.random = new Random().nextInt(2) + 1; // [0, 1] + 1 => [1, 2] : Minimum 1 (si [0] + 1) et maximum 2 (si [1] + 1)
                remettreZeroParametresJeu();
                lancerConsignesJeu();
            }
        }.start();
        countDownTimer.start();
    }

    private void timerSiPasJeu() {
        CountDownTimer countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                Button bt4 = findViewById(R.id.button);
                Button bt5 = findViewById(R.id.button2);
                Button bt6 = findViewById(R.id.gameHorizontal);
                Button bt7 = findViewById(R.id.gameVertical);
                TextView tv0 = findViewById(R.id.textView);
                TextView tv3 = findViewById(R.id.tempsDeJeu);
                TextView tv4 = findViewById(R.id.planteAChercher);
                TextView tv5 = findViewById(R.id.planteQuete1);
                TextView tv6 = findViewById(R.id.textViewQueteEnCours);
                TextView tv7 = findViewById(R.id.jeJoue);
                TextView tv8 = findViewById(R.id.longitude_text);
                TextView tv9 = findViewById(R.id.latitude_text);
                TextView tv10 = findViewById(R.id.last_update_time_text);
                TextView tv12 = findViewById(R.id.marqueur_quete_text);

                View[] views = {bt4,bt5,bt6,bt7, tv0,tv3,tv4,tv5,tv6, tv8,tv9,tv10,tv12 };

                for (View view : views) {
                    view.setVisibility(View.INVISIBLE);
                }
                ImageView img1 = findViewById(R.id.jetDe);
                img1.setVisibility(View.VISIBLE);
                tv7.setVisibility(View.VISIBLE);
                tv7.setText("Vous ne jouerez pas cette fois-ci !");

            }
            @Override
            public void onFinish() {
                View mapFragment = findViewById(R.id.map);
                Button bt4 = findViewById(R.id.button);
                Button bt5 = findViewById(R.id.button2);
                Button bt6 = findViewById(R.id.gameHorizontal);
                Button bt7 = findViewById(R.id.gameVertical);
                TextView tv4 = findViewById(R.id.planteAChercher);
                TextView tv5 = findViewById(R.id.planteQuete1);
                TextView tv6 = findViewById(R.id.textViewQueteEnCours);
                TextView tv8 = findViewById(R.id.longitude_text);
                TextView tv9 = findViewById(R.id.latitude_text);
                TextView tv10 = findViewById(R.id.last_update_time_text);
                TextView tv12 = findViewById(R.id.marqueur_quete_text);

                View[] views1 = {mapFragment, bt4, bt5, bt6,bt7, tv4, tv5, tv6, tv8, tv9, tv10, tv12 };

                for (View view : views1) {
                    view.setVisibility(View.VISIBLE);
                }

                ImageView img1 = findViewById(R.id.jetDe);
                TextView tv7 = findViewById(R.id.jeJoue);
                TextView tv11 = findViewById(R.id.resultatJetDe);

                View[] views2 = {img1, tv7, tv11 };

                for (View view : views2) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }.start();
        countDownTimer.start();
    }

    private void apparitionCoffre() {
        //lat : 43.47834-43.47645=0,00189=210m // min 43.47645, max 43,48023
        //lng : -1.50798+1.50538=0,0026=210m // min -1,51058, max -1.50538
        Modele.randomLat = Math.random()* 0.00378 + (Modele.marqueurQuete.latitude-0.00189); //créer une longitude aléatoire<420m (diametre zone quete) qu'on ajoute une extrémité minimal de cette zone
        Modele.randomLng = Math.random()* 0.0052 +(Modele.marqueurQuete.longitude-0.0026); //créer une latitude aléatoire<420m (diametre zone quete) qu'on ajoute une extrémité minimal de cette zone
        Modele.marqueurCoffre = new LatLng(Modele.randomLat,Modele.randomLng);
        //mMap.addMarker(new MarkerOptions().position(Modele.marqueurCoffre ).title("Coffre").icon(BitmapDescriptorFactory.fromResource((R.drawable.tresor)))); ---- lIGNE QUI CRASHE

    }

    public void chargerFichier() {
        try {

            if (Modele.firstLoadingApplication) {
                Button bt4 = findViewById(R.id.button);
                Button bt5 = findViewById(R.id.button2);
                Button bt6 = findViewById(R.id.gameHorizontal);
                Button bt7 = findViewById(R.id.gameVertical);
                TextView tv4 = findViewById(R.id.planteAChercher);
                TextView tv5 = findViewById(R.id.planteQuete1);
                TextView tv6 = findViewById(R.id.textViewQueteEnCours);

                View[] views = {bt4, bt5, bt6, bt7, tv4, tv5, tv6 };

                for (View viewButton : views) {
                    viewButton.setVisibility(View.VISIBLE);
                }
            }

            FileInputStream input = openFileInput(SAVE);
            int value;
            // On utilise un StringBuffer pour construire la chaîne au fur et à mesure
            StringBuffer lu = new StringBuffer();
            // On lit les caractères les uns après les autres
            while ((value = input.read()) != -1) {
                // On écrit dans le fichier le caractère lu
                lu.append((char) value);
            }
            String strFields0 = lu.toString().split("\\|")[0];
            String strFields1 = lu.toString().split("\\|")[1];
            String strFields2 = lu.toString().split("\\|")[2];

            Toast.makeText(MainActivity.this, "Lecture sauvegarde Interne : " + lu.toString(), Toast.LENGTH_SHORT).show();
            Modele.jeuCoffreTresorGagne = Boolean.valueOf(strFields0); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
            Modele.experienceTotaleActuelle = Integer.valueOf(strFields1); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
            Modele.queteAcceptee = Boolean.valueOf(strFields2); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
            Log.d("s0", "split0 : " + strFields0);
            Log.d("s1", "split1 : " + strFields1);
            Log.d("s2", "split2 : " + strFields2);
            Log.d("qload", "quete en cours ? " + Modele.queteAcceptee);
            if (input != null)
                input.close();

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                lu = new StringBuffer();
                input = new FileInputStream(mFile);
                while ((value = input.read()) != -1)
                    lu.append((char) value);
                Toast.makeText(MainActivity.this, "Lecture sauvegarde Externe : " + lu.toString(), Toast.LENGTH_SHORT).show();
                Modele.jeuCoffreTresorGagne = Boolean.valueOf(strFields0); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                Modele.experienceTotaleActuelle = Integer.valueOf(strFields1); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                Modele.queteAcceptee = Boolean.valueOf(strFields2); // VARIABLE A CHANGER SELON LA DONNEE A LIRE POUR INITIALISER LA VARIABLE LOCALE
                Log.d("s0", "split0 : " + strFields0);
                Log.d("s1", "split1 : " + strFields1);
                Log.d("s2", "split2 : " + strFields2);
                Log.d("qload", "quete en cours ? " + Modele.queteAcceptee);
                if (input != null)
                    input.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void sauvegarderFichier () {
        // On crée un fichier qui correspond à l'emplacement extérieur
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName());
        if (!directory.exists())
            directory.mkdirs();

        mFile = new File(directory.getPath() + SAVE);
        try {
            // Flux interne
            FileOutputStream output = openFileOutput(SAVE, MODE_PRIVATE);

            // On écrit dans le flux interne
            output.write(resultatJeuCoffreTresor.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
            output.write(pipeSeparation.getBytes());
            output.write(experienceActuelle.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
            output.write(pipeSeparation.getBytes());
            output.write(queteEstAcceptee.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
            Log.d("qsave", "quete en cours ? " +  Modele.queteAcceptee);

            if(output != null)
                output.close();

            // Si le fichier est lisible et qu'on peut écrire dedans
            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
                // On crée un nouveau fichier. Si le fichier existe déjà, il ne sera pas créé
                mFile.createNewFile();
                output = new FileOutputStream(mFile);
                output.write(resultatJeuCoffreTresor.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                output.write(pipeSeparation.getBytes());
                output.write(experienceActuelle.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                output.write(pipeSeparation.getBytes());
                output.write(queteEstAcceptee.getBytes()); // VARIABLE A CHANGER SELON LA DONNEE A ECRIRE
                Log.d("qsave", "quete en cours ? " +  Modele.queteAcceptee);
                if(output != null)
                    output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

