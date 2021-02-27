package com.example.test_libgdxintoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class APIActivity extends AppCompatActivity {
    /*
     * La photo prise par l'utilisateur se trouve à un emplacement défini en dur dans la code. ( -> Réécriture du fichier de destination à chaque prise de photo)
     * On appelle une méthode "uploadImage()" sans paramètre, avec le même lien écrit en dur en son sein.
     * Cette méthode upload une image en ligne (via firebase).
     * Ainsi, l'image étant accessible via un lien, il suffit de le passer à la méthode "recognizeImageThroughAPI()".
     * Elle envoie une demande de reconnaissance à l'api de PlantNet qui renvoie un JSON de data que l'on parse ensuite.
     */

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_p_i);

        tv = findViewById(R.id.textView5);

        // uploadImage();
        recognizeImageThroughAPI();

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
    protected void uploadImage(){
        String link ="";
        recognizeImageThroughAPI();
    }

    // appeler l'API de reconnaisance de plante avec l'URL de l'image
    protected void recognizeImageThroughAPI(/*String imageLink*/){

        RequestQueue queue = Volley.newRequestQueue(APIActivity.this);
/*
        String url ="https://my-api.plantnet.org/v2/identify/all?images=" +
                    "https://media.gerbeaud.net/2014/02/640/chene-quercus-robur.jpg" +
                    "&organs=leaf&include-related-images=false&lang=en" +
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
