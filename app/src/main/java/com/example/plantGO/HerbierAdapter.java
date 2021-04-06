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

    // transforme la durée en minutes en durée heure+minutes au format h:mm, hh:mm, mm ou m
    private String formater(int duree) {
        int heures = 0,minutes = duree; String dureeFormatee="";
        if (minutes > 59) {
            while (minutes > 59) {minutes -= 60; heures++;}  // on compte 1h (-60min) à chaque tour de boucle, si le nombre de minute est < à 1h on s'arrête

            String minutesF = String.valueOf(minutes); if (minutes < 10) minutesF = '0' + minutesF; // afin d'avoir un format h:mm ou hh:mm dans tous les cas
            String heuresF = String.valueOf(heures);
            dureeFormatee = heuresF + 'h' + minutesF;

        }else{ // dans le cas où le film dure - d'une heure
            String minutesF = String.valueOf(minutes); if (minutes < 10) minutesF = '0' + minutesF;
            dureeFormatee = minutesF+"min";
        }

        return dureeFormatee;
    }
}