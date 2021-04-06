/*package com.example.plantGO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlanteDAO extends SQLiteOpenHelper {
    public PlanteDAO(@Nullable Context context) {
        super(context, Param.base, null, Param.version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Plante ("
                + "idPlante INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nomCommun STRING NOT NULL,"
                + "nomScientifique STRING NOT NULL,"
                + "famille STRING NOT NULL,"
                + "inventeur STRING NOT NULL,"
                + "niveauDescription INTEGER NOT NULL,"
                + "estDebloque BOOLEAN NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Plante getPlante(int idPlante) {
        Plante unePlante = null;
        Cursor curseur;
        curseur = this.getReadableDatabase().rawQuery("select * from Plante where idPlante=?;", new String[]{idPlante+""});
        unePlante = cursorToPlante(curseur);

        return unePlante;
    }

    private Plante cursorToPlante(Cursor curseur) {
        Plante unePlante = null;
        int idPlante;
        String nomCommun;
        String nomScientifique;
        String famille;
        String inventeur;
        int niveauDescription;
        boolean estDebloque;

        if (curseur.getCount()>0 ) {
            curseur.moveToFirst();
            idPlante = curseur.getInt(0);
            nomCommun = curseur.getString(1);
            nomScientifique = curseur.getString(2);
            famille = curseur.getString(3);
            inventeur = curseur.getString(4);
            niveauDescription = curseur.getInt(5);
            estDebloque = curseur.getInt(6)==1;

            unePlante = new Plante(idPlante, nomCommun, nomScientifique, famille, inventeur, niveauDescription, estDebloque);
        }
        return unePlante;
    }
}*/
