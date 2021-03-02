package com.example.test_libgdxintoandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class APIActivity extends AppCompatActivity {
    /*
     * La photo prise par l'utilisateur se trouve à un emplacement défini en dur dans la code. ( -> Réécriture du fichier de destination à chaque prise de photo)
     * On appelle une méthode "uploadImage()" sans paramètre, avec le même lien écrit en dur en son sein.
     * Cette méthode upload une image en ligne (via firebase).
     * Ainsi, l'image étant accessible via un lien, il suffit de le passer à la méthode "recognizeImageThroughAPI()".
     * Elle envoie une demande de reconnaissance à l'api de PlantNet qui renvoie un JSON de data que l'on parse ensuite.
     */



    private StorageReference storageRef;
    private Uri uri = Modele.imageURI;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_p_i);


        storageRef = FirebaseStorage.getInstance().getReference();

        tv = findViewById(R.id.textView5);

        uploadImage(uri);
        // recognizeImageThroughAPI();


    }
/*
    private void onResponse(JSONObject response) {
        try {
            JSONObject resultatsDeLaRecherche = (JSONObject) response.getJSONArray("results").get(0); // L'api renvoie tous les résultats probables de la reconnaissance de plan
            JSONObject resultatLePlusProbable = resultatsDeLaRecherche.getJSONObject("species"); // nom scientifique, nom commun, famille
            // nomScientifique.set(resultatLePlusProbable.getString("scientificNameWithoutAuthor"));
            tv.setText(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/

    // upload l'image en ligne (pour que l'API de reconnaisance de plante puisse l'utiliser)
    protected void uploadImage(Uri imageURI){
        String link ="";

        StorageReference ref = storageRef.child("images/plante");

        // adding listeners on upload
        // or failure of image
        ref.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(APIActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", uri.toString());
                    }
                });
            }
        });


      //  recognizeImageThroughAPI();
    }

    // appeler l'API de reconnaisance de plante avec l'URL de l'image
    protected void recognizeImageThroughAPI(/*String imageLink*/){

        RequestQueue queue = Volley.newRequestQueue(APIActivity.this);
/*
        String url ="https://my-api.plantnet.org/v2/identify/all?images=" +
                    "https://media.gerbeaud.net/2014/02/640/chene-quercus-robur.jpg" +
                    "&organs=leaf&include-related-images=false&lang=fr" +
                    "&api-key=2a10dhqKV1csqtYS4gUnTxZ";
*/
        String url = "https://my-api.plantnet.org/v2/identify/all?images=https://media.gerbeaud.net/2014/02/640/chene-quercus-robur.jpg&organs=leaf&include-related-images=false&lang=en&api-key=2a10dhqKV1csqtYS4gUnTxZ";

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.GET,
                url,
                null,

                /* En cas de réponse de l'API */
                //   this::onResponse,
                reponse -> { tv.setText("OK"); },
                /* En cas d'erreur */
                error -> { tv.setText("Erreur envoie à l'API "); }
        );

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    // retour accueil
    public void displayAccueil() {
        Intent intent = new Intent(APIActivity.this, MainActivity.class);
        APIActivity.this.startActivity(intent);
    }

}
