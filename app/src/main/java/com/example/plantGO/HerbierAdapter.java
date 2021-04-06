package com.example.plantGO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class HerbierAdapter extends ArrayAdapter<String> {
    private final Context context;
    private ArrayList<String> plante;

    public HerbierAdapter(Context context, int resource, ArrayList<String> plante) {
        super(context, resource, plante);
        this.context = context;
        this.plante = plante;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){ // pour chaque élément de la liste de séances, on crée une vue composé d'un linearlayout rempli avecune photo générique et du texte
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_planteherbier, parent, false);
        } else { convertView = (LinearLayout) convertView; }

        TextView viewNomPlante = (TextView) convertView.findViewById(R.id.tv_nomPlante);
        viewNomPlante.setText(plante.get(position));

        return convertView;
    }

}