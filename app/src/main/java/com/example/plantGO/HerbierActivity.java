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

        // changer par un récup de la bd
        listeNomPlantes.add("Tito");
        listeNomPlantes.add("222");
        listeNomPlantes.add("dvw");

        ListView ls_seances = findViewById(R.id.ls_listePlantes);

        HerbierAdapter adapter = new HerbierAdapter(getApplicationContext(), R.layout.activity_planteherbier, listeNomPlantes);
        ls_seances.setAdapter(adapter);

        ls_seances.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
/*
package com.example.tp;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;

public class SeancesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seances);

        Modele.listeSeances.add(new Seance("Joker","x",72,"VF","12h00")); // BATMAN
        Modele.listeSeances.add(new Seance("Joker","x",72,"VF","12h00")); // BATMAN
        Modele.listeSeances.add(new Seance("Batman","y",12,"VO","18h00")); // BATMAN
        Modele.listeSeances.add(new Seance("Robin","z",180,"VF","2h00")); // BATMAN
        Modele.listeSeances.add(new Seance("Mr.Penguin","a",160,"VOSTFR","13h00")); // BATMAN
        Modele.listeSeances.add(new Seance("Crackito","b",59,"VF","11h00")); // BATMAN

        ListView ls_seances = findViewById(R.id.ls_seances);

        SeanceAdaptater adapter = new SeanceAdaptater(getApplicationContext(), R.layout.activity_seance, Modele.listeSeances);
        ls_seances.setAdapter(adapter);

        ls_seances.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("click","je clic");
                // naviguer vers l'autre activité
                Modele.filmCourant = Modele.listeSeances.get(position);
                startActivity(new Intent(SeancesActivity.this,ReservationActivity.class));
            }
        });
    }*/

