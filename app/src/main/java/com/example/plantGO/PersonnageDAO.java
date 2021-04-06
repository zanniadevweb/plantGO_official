package com.example.plantGO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PersonnageDAO extends SQLiteOpenHelper {
    public PersonnageDAO(@Nullable Context context) {
        super(context, Param.base, null, Param.version);
    }
    /*int experience;
    int sexe;
    int couleaurPeau;
    int idQueteEnCours;*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Personnage ("
                + "experience INTEGER PRIMARY KEY,"
                + "sexe INTEGER NOT NULL,"
                + "couleurPeau INTEGER NOT NULL,"
                + "idQueteEnCours INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Personnage getPersonnage(int idPersonnage) {
        Personnage unPersonnage = null;
        Cursor curseur;
        curseur = this.getReadableDatabase().rawQuery("select * from Personnage where idPersonnage=?;", new String[]{idPersonnage+""});
        unPersonnage = cursorToPersonnage(curseur);

        return unPersonnage;
    }

    private Personnage cursorToPersonnage(Cursor curseur) {
        Personnage unPersonnage = null;
        int experience;
        int sexe;
        int couleaurPeau;
        int idQueteEnCours;

        if (curseur.getCount()>0 ) {
            curseur.moveToFirst();
            experience = curseur.getInt(0);
            sexe = curseur.getInt(1);
            couleaurPeau = curseur.getInt(2);
            idQueteEnCours = curseur.getInt(3);


            //unPersonnage = new Personnage(experience, sexe, couleaurPeau, idQueteEnCours);
        }
        return unPersonnage;
    }
}
