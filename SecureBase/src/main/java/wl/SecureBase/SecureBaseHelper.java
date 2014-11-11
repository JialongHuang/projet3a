package wl.SecureBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huang and slavnic on 29/10/14.
 */
public class SecureBaseHelper extends SQLiteOpenHelper {
    private static final String TABLE = "secureT";
    private static final String COL_ID = "id";
    private static final String COL_KEY = "key";
    private static final String COL_DATA = "data";
    private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE + " ( "
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_KEY + " TEXT NOT NULL, "
            + COL_DATA + " TEXT NOT NULL);";

    public SecureBaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creation of the table with the request writen in CREAT_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Can do anything here, but we just delete the table and recreate it after
        //the id can begin in 0 again
        db.execSQL("DROP TABLE " + TABLE + ";");
        onCreate(db);

    }


}
