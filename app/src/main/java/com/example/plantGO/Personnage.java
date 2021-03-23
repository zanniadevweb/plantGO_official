package com.example.plantGO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class Personnage extends AppCompatActivity {

    //creating object of ViewPager
    ViewPager mViewPagerHat;

    //creating object of ViewPager
    ViewPager mViewPagerTorso;

    //creating object of ViewPager
    ViewPager mViewPagerPants;

    //images array list
    public static ArrayList<Integer> imagesHat = new ArrayList<Integer>(); //{R.drawable.hat1, R.drawable.hat2};

    //images array list
    public static ArrayList<Integer> imagesTorso = new ArrayList<Integer>(); // {R.drawable.torso1, R.drawable.torso2};

    //images array list
    public static ArrayList<Integer> imagesPants = new ArrayList<Integer>(); //{R.drawable.hat1, R.drawable.hat2};

    //Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapterHat;

    //Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapterTorso;


    //Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapterPants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnage);

        if (Modele.unSetDeBase) {
            HataddElement(R.drawable.hat1);
            TorsoaddElement(R.drawable.haut_forestier_male);
            PantsaddElement(R.drawable.bas_forestier_male);
            Modele.unSetDeBase = false;
        }

        if (Modele.firstInventoryLook && Modele.jeuCoffreTresorGagne) {
            Personnage.HataddElement(R.drawable.hat2);
            Personnage.TorsoaddElement(R.drawable.torso2);
            Modele.firstInventoryLook = false;
        }

        //Initializing the ViewPager Object
        mViewPagerHat = (ViewPager)findViewById(R.id.viewPagerChapeau);

        //Initializing the ViewPager Object
        mViewPagerTorso = (ViewPager)findViewById(R.id.viewPagerHaut);

        //Initializing the ViewPager Object
        mViewPagerPants = (ViewPager)findViewById(R.id.viewPagerBas);

        //Initializing the ViewPagerAdapter
        mViewPagerAdapterHat = new ViewPagerAdapter(Personnage.this, imagesHat);

        //Initializing the ViewPagerAdapter
        mViewPagerAdapterTorso = new ViewPagerAdapter(Personnage.this, imagesTorso);

        //Initializing the ViewPagerAdapter
        mViewPagerAdapterPants = new ViewPagerAdapter(Personnage.this, imagesPants);

        //Adding the Adapter to the ViewPager
        mViewPagerHat.setAdapter(mViewPagerAdapterHat);

        //Adding the Adapter to the ViewPager
        mViewPagerTorso.setAdapter(mViewPagerAdapterTorso);

        //Adding the Adapter to the ViewPager
        mViewPagerPants.setAdapter(mViewPagerAdapterPants);

    }

    static void HataddElement(int element) {
        imagesHat.add(element);
    }

    static void TorsoaddElement(int element) {
        imagesTorso.add(element);
    }

    static void PantsaddElement(int element) {
        imagesPants.add(element);
    }


    public void displayProfile(View view) {
        Intent intent = new Intent(Personnage.this, ProfilActivity.class);
        Personnage.this.startActivity(intent);
    }
}