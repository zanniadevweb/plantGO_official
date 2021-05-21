package com.example.plantGO;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.example.plantGO.databinding.ActivityAPIBinding;

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

    // Attribut permet d'utiliser le ViewBinding (c'est un databinding dynamique, au lieu du classique mais statique setContentView(R.layout.toto). Permet d'enlever tous les findviewbyid !
    private @NonNull
    com.example.plantGO.databinding.ActivityAPIBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout as per google doc instructions
        binding = ActivityAPIBinding.inflate(getLayoutInflater());
        // add the inflated view to the Included view.
        setContentView(binding.getRoot());
        //supprimer l'animation au changement d'activité
        overridePendingTransition(0,0);

        storageRef = FirebaseStorage.getInstance().getReference();

        uploadImage(uri);

    }

    // upload l'image en ligne (pour que l'API de reconnaisance de plante puisse l'utiliser)
    protected void uploadImage(Uri imageURI){

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
                        try { recognizeImageThroughAPI(uri.toString()); }
                        catch (UnsupportedEncodingException e) { e.printStackTrace(); }
                    }
                });
            }
        });
    }

    // appeler l'API de reconnaisance de plante avec l'URL de l'image
    protected void recognizeImageThroughAPI(String imageLink) throws UnsupportedEncodingException {

        imageLink = formater(imageLink);
        RequestQueue queue = Volley.newRequestQueue(APIActivity.this);

        String url ="https://my-api.plantnet.org/v2/identify/all?images=" +
                    imageLink +
                    "&organs=leaf&include-related-images=false&lang=fr" +
                    "&api-key=2a10dhqKV1csqtYS4gUnTxZ";

        Log.d("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", url);

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.GET,
                url,
                null,

                /* En cas de réponse de l'API */
                this::onResponse,

                /* En cas d'erreur */
                error -> { binding.resultatRequeteAPI.setText("Plante non reconnue ou impossibilité d'envoi à l'API. Retentez de prendre une photo."); }
        );


        // Add the request to the RequestQueue.
        queue.add(request);
    }

    private void onResponse(JSONObject response) {
        try {
            JSONObject resultatLePlusProbable = (JSONObject) response.getJSONArray("results").get(0); // L'api renvoie tous les résultats probables de la reconnaissance de plan
            JSONObject espece = resultatLePlusProbable.getJSONObject("species"); // nom scientifique, nom commun, famille
            String nomScientifique = espece.getString("scientificNameWithoutAuthor");

            Log.v("xxxxxxxxxxx",espece.getJSONArray("commonNames").get(0).toString());

            String nomCommun = espece.getJSONArray("commonNames").get(0).toString();

            Modele.planteCourante = new Plante(nomScientifique,nomCommun);
            Log.v("xxxxxxxxxxx",Modele.planteCourante.toString());

            Log.v("xxxxxxxxxxx",Modele.planteCourante.getNomCommun()+" & "+Modele.planteCourante.getNomScientifique());
            if (!Modele.planteCourante.estDansLaQuete())
                binding.resultatRequeteAPI.setText("Pas dans la quête hebdomadaire ! :" + nomScientifique);
            else
                binding.resultatRequeteAPI.setText(nomScientifique);

            // récupérer description
            getDescriptionThroughAPI(nomScientifique);
        }
        catch (JSONException e)
            { e.printStackTrace(); }
    }

    private void getDescriptionThroughAPI(String nomScientifique) {
        RequestQueue queue = Volley.newRequestQueue(APIActivity.this);

        String url ="https://trefle.io/api/v1/plants/search?token=cI3tP70z6kyRlggY_QPA3bFkDfzjz_K3ME8ggdHhm-4&q=" + nomScientifique.replaceAll("\\s","-");

        Log.d("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", url);

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.GET,
                url,
                null,

                /* En cas de réponse de l'API */
                this::onResponse2,

                /* En cas d'erreur */
                error -> { binding.resultatRequeteAPI.setText("Plante inconnue du moteur de description."); }
        );


        // Add the request to the RequestQueue.
        queue.add(request);
    }

    private void onResponse2(JSONObject response) {

        // créer carte plante avec description
        try {

            JSONObject planteLaPlusProbable = (JSONObject) response.getJSONArray("data").get(0);
            String nomCommun = planteLaPlusProbable.getString("common_name");
            String anneeDecouverte = planteLaPlusProbable.getString("year");
            String inventeur = planteLaPlusProbable.getString("author");
            String famille = planteLaPlusProbable.getString("family_common_name");
            String idFamille = planteLaPlusProbable.getString("genus_id");
            String photoGenerique = planteLaPlusProbable.getString("image_url");
            String biblio = planteLaPlusProbable.getString("bibliography");

            String description1 = "nom courant : "+nomCommun+" | famille : "+famille+"\n"+"\n";
            String description2 = "Inventeur : "+inventeur+" | annee de decouverte : "+anneeDecouverte+" | id de la famille de plantes : "+idFamille+" | bibliographie : "+biblio+"\n"+"\n";

            String descriptionComplete = description1+description2+photoGenerique;

            TextView tv_description = findViewById(R.id.tv_description);
            tv_description.setText(descriptionComplete);

            // créer carte
                // assigner nomScientifique + nom commum
                // assigner niveau 1
                // assigner niveau 2
                // assigner image ??

            // plante trouvée
            Modele.queteCourante.aTrouve(nomCommun.toString());
        }
        catch (JSONException e)
            { e.printStackTrace(); }
    }


    private String formater(String url) throws UnsupportedEncodingException {

        String encoded = URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        return encoded;
    }


    // retour accueil
    public void displayAccueil(View view) {
        Intent intent = new Intent(APIActivity.this, MainActivity.class);
        APIActivity.this.startActivity(intent);
    }

}
