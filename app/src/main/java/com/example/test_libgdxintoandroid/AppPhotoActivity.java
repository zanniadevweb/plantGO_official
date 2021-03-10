package com.example.test_libgdxintoandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;

public class AppPhotoActivity extends AppCompatActivity {

    private static final int REQUEST_ID_READ_WRITE_PERMISSION = 99;
    private static final int CAPTURE_IMAGE = 100;
    private File fichier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_photo);
        overridePendingTransition(0,0); //supprimer l'animation au changement d'activité
    }

    public void takePicture(View view) {
        askPermissionAndCapturePhoto();
    }

    private void askPermissionAndCapturePhoto() {
        // With Android Level >= 23, you have to ask the user
        // for permission to read/write data on the device.
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have read/write permission
            int readPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED
                    ||
                    readPermission  != PackageManager.PERMISSION_GRANTED)
            {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_ID_READ_WRITE_PERMISSION
                );
                return;
            }
        }
        this.capturePhoto();
    }

    private void capturePhoto() {
        try {
            // Create an implicit intent, for video capture.
            Intent fairePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // The external storage directory.
            File directory = new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getPackageName());
            if (!directory.exists())
                directory.mkdirs();

            // file:///storage/emulated/0/myphoto.png
            fichier = new File(directory.getPath() + "/myphoto.jpg");
            Uri fichierUri = Uri.fromFile(fichier);
            Log.d("Repertoire fichier","avant la photographie (fichier pas encore créé) : " + fichierUri);

            // Specify where to save video files.
            fairePhoto.putExtra(MediaStore.EXTRA_OUTPUT, fichierUri);

            // ================================================================================================
            // To Fix Error :
            // You may get an Error If your app targets API 24+
            // "android.os.FileUriExposedException: file:///storage/emulated/0/xxx exposed beyond app through.."
            //  Explanation: https://stackoverflow.com/questions/38200282
            // ================================================================================================
            fairePhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            // ================================================================================================

            // Start camera and wait for the results.
            startActivityForResult(fairePhoto, CAPTURE_IMAGE); // (**)

        } catch(Exception e)  {
            Toast.makeText(this, "Erreur capture photo: " +e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    // When you have the request results
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        switch (requestCode) {
            case CAPTURE_IMAGE: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (read/write).
                if (resultCode == RESULT_OK){
                    Log.d("Repertoire fichier","après photographie (prouve que le fichier a été créé) : " + Uri.fromFile(fichier));
                    Modele.imageURI = Uri.fromFile(fichier);
                }
                // Cancelled or denied.
                else if ( resultCode == RESULT_CANCELED)
                {   Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_LONG).show(); }

                else
                {   Toast.makeText(this, " Picture was not taken ", Toast.LENGTH_SHORT).show(); }

                break;
            }
        }
        sendToApi();
    }

    // Envoyer l'image à l'api : récupérer le résultat
    // image : directory.getPath() + "/myphoto.jpg"


    // Envoyer l'image à l'API de reconnaissance
    public void sendToApi() {
        Intent intent = new Intent(AppPhotoActivity.this, APIActivity.class);
        AppPhotoActivity.this.startActivity(intent);
    }

    // retour accueil
    public void displayAccueil(View view) {
        Intent intent = new Intent(AppPhotoActivity.this, MainActivity.class);
        AppPhotoActivity.this.startActivity(intent);
    }
}