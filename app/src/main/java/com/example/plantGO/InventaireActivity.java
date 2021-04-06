package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InventaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventaire);

        ArrayList<Integer> al_listeCosmetiques =  new ArrayList<Integer>();
        for (int photo : Modele.listeCosmetiques){ al_listeCosmetiques.add(photo); }

        ListView ls_cosmetique = findViewById(R.id.ls_listeCosmetique);

        InventaireAdapter adapter = new InventaireAdapter(getApplicationContext(), R.layout.activity_cosmetiqueinventaire, al_listeCosmetiques);
        ls_cosmetique.setAdapter(adapter);

    }
    public void displayProfile(View view) {
        Intent intent = new Intent(InventaireActivity.this, ProfilActivity.class);
        InventaireActivity.this.startActivity(intent);
    }

    public void displayCartePlante(View view) {
        Intent intent = new Intent(InventaireActivity.this, CartePlanteActivity.class);
        InventaireActivity.this.startActivity(intent);
    }
}
