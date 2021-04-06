/*package com.example.plantGO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CosmetiqueDAO extends SQLiteOpenHelper {
    public CosmetiqueDAO(@Nullable Context context) {
        super(context, Param.base, null, Param.version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Cosmetique ("
                + "idCosmetique INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nom STRING NOT NULL,"
                + "type STRING NOT NULL,"
                + "nomImage STRING NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cosmetique getCosmetique(int idCosmetique) {
        Cosmetique unCosmetique = null;
        Cursor curseur;
        curseur = this.getReadableDatabase().rawQuery("select * from Cosmetique where idCosmetique=?;", new String[]{idCosmetique+""});
        unCosmetique = cursorToCosmetique(curseur);

        return unCosmetique;
    }

    private Cosmetique cursorToCosmetique(Cursor curseur) {
        Cosmetique unCosmetique = null;
        int idCosmetique;
        String nom;
        String type;
        String nomImage;

        if (curseur.getCount()>0 ) {
            curseur.moveToFirst();
            idCosmetique = curseur.getInt(0);
            nom = curseur.getString(1);
            type = curseur.getString(2);
            nomImage = curseur.getString(3);


            unCosmetique = new Cosmetique(idCosmetique, nom, type, nomImage);
        }
        return unCosmetique;
    }
}*/
