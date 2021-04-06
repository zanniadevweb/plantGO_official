package com.example.plantGO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AppartientDAO extends SQLiteOpenHelper {
    public AppartientDAO(@Nullable Context context) {
        super(context, Param.base, null, Param.version);
    }
    /*int idQuete;
    int idPlante;
    boolean estTrouve;*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Appartient ("
                + "idQuete INTEGER REFERENCES Quete(idQuete),"
                + "idPlante INTEGER REFERENCES Plante(idPlante),"
                + "estTrouve BOOLEAN NOT NULL,"
                + "PRIMARY KEY (idQuete, idPlante));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Appartient getAppartient(int idQuete) {
        Appartient ilAppartient = null;
        Cursor curseur;
        curseur = this.getReadableDatabase().rawQuery("select * from Appartient where idQuete=?;", new String[]{idQuete+""});
        ilAppartient = cursorToAppartient(curseur);

        return ilAppartient;
    }

    private Appartient cursorToAppartient(Cursor curseur) {
        Appartient ilAppartient= null;
        int idQuete;
        int idPlante;
        boolean estTrouve;

        if (curseur.getCount()>0 ) {
            curseur.moveToFirst();
            idQuete = curseur.getInt(0);
            idPlante = curseur.getInt(1);
            estTrouve = curseur.getInt(2)==1;

            ilAppartient = new Appartient(idQuete, idPlante, estTrouve);
        }
        return ilAppartient;
    }
}
