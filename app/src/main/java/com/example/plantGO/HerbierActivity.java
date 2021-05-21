package com.example.plantGO;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HerbierActivity extends AppCompatActivity {

    ArrayList<String> listeNomPlantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbier);

        listeNomPlantes = new ArrayList<String>();

        // changer par un récup de la bd
        listeNomPlantes.add("Saule pleureur");
        listeNomPlantes.add("Pin des Landes");
        listeNomPlantes.add("Fougère");

        ListView ls_plantes = findViewById(R.id.ls_listePlantes);

        HerbierAdapter adapter = new HerbierAdapter(getApplicationContext(), R.layout.activity_planteherbier, listeNomPlantes);
        ls_plantes.setAdapter(adapter);

        ls_plantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Log.v("click","je clic");
              // naviguer vers l'autre activité
              Modele.nomPlanteCliquee = listeNomPlantes.get(position);
              startActivity(new Intent(HerbierActivity.this,CartePlanteActivity.class));
            }
        });
    }
    public void displayProfile(View view) {
        Intent intent = new Intent(HerbierActivity.this, ProfilActivity.class);
        HerbierActivity.this.startActivity(intent);
    }

    public void displayCartePlante(View view) {
        Intent intent = new Intent(HerbierActivity.this, CartePlanteActivity.class);
        HerbierActivity.this.startActivity(intent);
    }
}