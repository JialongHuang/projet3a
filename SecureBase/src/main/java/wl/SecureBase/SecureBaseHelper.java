package wl.SecureBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huang and slavnic on 29/10/14.
 */

public class SecureBaseHelper extends SQLiteOpenHelper{
    private static final String TABLE = "secureT";
    private static final String COL_ID = "id";
    private static final String COL_KEY = "key";
    private static final String COL_DATA = "data";
    private static final String COL_IV = "IV";
    private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE + " ( "
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_KEY + " TEXT NOT NULL, "
            + COL_DATA + " TEXT NOT NULL, "+ COL_IV + " BLOB);";

    public SecureBaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLE + ";");
        onCreate(db);

    }

}


//TODO StackTrace, Challenge/Reponse, Donné static pour la cle, KeyChain