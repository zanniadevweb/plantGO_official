package com.example.test_libgdxintoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HerbierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbier);
    }
    public void displayProfile(View view) {
        Intent intent = new Intent(HerbierActivity.this, ProfilActivity.class);
        HerbierActivity.this.startActivity(intent);
    }

    public void displayCartePlante(View view) {
        Intent intent = new Intent(HerbierActivity.this, cartePlanteActivity.class);
        HerbierActivity.this.startActivity(intent);
    }
}