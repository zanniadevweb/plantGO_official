package com.example.plantGO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CartePlanteActivity extends AppCompatActivity {
    TextView tv_nomCommun, tv_descriptionBasique, tv_descriptionAvancee;
    int niveauDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_plante);

        TextView tv_nomCommun = findViewById(R.id.tv_nomCommun);
        TextView tv_DescriptionBasique = findViewById(R.id.tv_descriptionBasique);
        TextView tv_DescriptionAvancee = findViewById(R.id.tv_descriptionAvancee);

        tv_nomCommun.setText(tv_nomCommun.getText() + Modele.nomPlanteCliquee);

        // rechercher la plante ayant pour nom Modele.nomPlanteCliquee, en extraite DESCRIPTION_B & DESCRIPTION_A et niveauDescription
        // if(niveauDescription>=1){
            // tv_DescriptionBasique.setText(tv_DescriptionBasique.getText() + DESCRIPTION_B);
            // if(niveauDescription==2)
                // tv_DescriptionAvancee.setText(tv_DescriptionAvancee.getText() + DESCRIPTION_A);
        // }

    }

    public void displayHerbier(View view) {
        Intent intent = new Intent(CartePlanteActivity.this, HerbierActivity.class);
        CartePlanteActivity.this.startActivity(intent);
    }

    public void augmenterNiveauDescription(View view) {
        int exp = Modele.experienceTotaleActuelle;

        if (exp > 200 ) // Récupérer attribut : niveauDeDébloquage, au lieu de la valeur en dure
             { /* cartePlante.augmenterNiveauDec() */ }

        else {  Context context = getApplicationContext();
                CharSequence text = "Tu manques de niveau ... Retente une prochaine fois !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show(); }

    }
}