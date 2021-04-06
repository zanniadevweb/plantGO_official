package com.example.plantGO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class InventaireAdapter extends ArrayAdapter<Integer> {
    private final Context context;
    private ArrayList<Integer> cosmetique;

    public InventaireAdapter(Context context, int resource, ArrayList<Integer> cosmetique) {
        super(context, resource, cosmetique);
        this.context = context;
        this.cosmetique = cosmetique;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){ // pour chaque élément de la liste de séances, on crée une vue composé d'un linearlayout rempli avec une photo générique et du texte
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_cosmetiqueinventaire, parent, false);
        } else { convertView = (LinearLayout) convertView; }

        ImageView viewImage = convertView.findViewById(R.id.imgv_cosmetique);
        int image = cosmetique.get(position);
        viewImage.setImageResource(image);

        return convertView;
    }

}