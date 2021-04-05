package com.example.plantGO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QueteDAO extends SQLiteOpenHelper {
    public QueteDAO(@Nullable Context context) {
        super(context, Param.base, null, Param.version);
    }
    /*int idQuete;
    double latPosition;
    double lngPosition;
    int rayon;
    boolean estFini;
    double latCoffre;
    double lngCoffre;*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Quete ("
                + "idTP INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "latPosition DOUBLE NOT NULL,"
                + "lngPosition DOUBLE NOT NULL,"
                + "rayon INTEGER NOT NULL,"
                + "estFini BOOLEAN NOT NULL,"
                + "latCoffre DOUBLE NOT NULL,"
                + "latCoffre DOUBLE NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Quete getQuete(int idQuete) {
        Quete uneQuete = null;
        Cursor curseur;
        curseur = this.getReadableDatabase().rawQuery("select * from Quete where idQuete=?;", new String[]{idQuete+""});
        uneQuete = cursorToQuete(curseur);

        return uneQuete;
    }

    private Quete cursorToQuete(Cursor curseur) {
        Quete uneQuete = null;
        int idQuete;
        double latPosition;
        double lngPosition;
        int rayon;
        boolean estFini;
        double latCoffre;
        double lngCoffre;

        if (curseur.getCount()>0 ) {
            curseur.moveToFirst();
            idQuete = curseur.getInt(0);
            latPosition = curseur.getDouble(1);
            lngPosition = curseur.getDouble(2);
            rayon = curseur.getInt(3);
            estFini = curseur.getInt(4)==1;
            latCoffre = curseur.getDouble(5);
            lngCoffre = curseur.getDouble(6);

            uneQuete = new Quete(idQuete, latPosition, lngPosition, rayon, estFini, latCoffre, lngCoffre);
        }
        return uneQuete;
    }
}
