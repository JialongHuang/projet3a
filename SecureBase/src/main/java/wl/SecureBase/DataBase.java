package wl.SecureBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by huang and slavnic on 29/10/14.
 */
public class DataBase {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "SecureBase.db";

    public static final String TABLE="secureT";

    public static final String COL_KEY="key";
    public static final String COL_DATA="data";
    public static final String COL_ID="id";


    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_KEY = 1;
    private static final int NUM_COL_DATA = 2;

    private SQLiteDatabase _bdd;

    private SecureBaseHelper _myBaseSQLite;

    /*
    * DataBase creation with the table
    * */
    public DataBase(Context context){
        _myBaseSQLite = new SecureBaseHelper(context, NOM_BDD, null, VERSION_BDD);
    }
    /*
    * Opening db in writing mode
    * */
    public void open(){
        _bdd = _myBaseSQLite.getWritableDatabase();
    }
    /*
    * Closing db
    * */
    public void close(){
        _bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return _bdd;
    }

    public long insertData(Data d){
        //Creation of ContentValue (work like a HashMap)
        ContentValues values = new ContentValues();
        //Adding a value associate to a key (name of the column where the value is put)
        values.put(COL_KEY, d.getKey());
        values.put(COL_DATA, d.getData());
        //insert the object in the db with ContentValues
        Cursor cursor = _bdd.query(TABLE, new String[] {COL_ID, COL_KEY, COL_DATA}, COL_KEY + "=\"" + d.getKey() +"\" AND "+ COL_DATA +"=\""+d.getData()+"\"", null, null, null, null);
        if(cursor.getCount()==0){
            return _bdd.insert(TABLE, null, values); //return id
        }else{
            return -1;
        }
    }

    public long deleteDataByKey(String key){
        return _bdd.delete(TABLE, COL_KEY + "=" + key, null);
    }
    /*
    * TODO request searching the primary key
    * */
    public Data getDataByKey(String key){
        //Get the value ,in a Cursor, corresponding to a client in the db (here it's thanks to his name)
        Cursor c = _bdd.query(TABLE, new String[] {COL_ID, COL_KEY, COL_DATA}, COL_KEY + "=\"" + key +"\"", null, null, null, null);


        return cursorToData(c);
    }


    private Data cursorToData(Cursor c){
        //if none element was return in the request, return null
        if (c.getCount() == 0)
            return null;
        //else we move on the first element
        c.moveToFirst();
        //Creation of a client
        Data d = new Data(c.getString(NUM_COL_KEY),c.getString(NUM_COL_DATA));
        //Closing the cursor
        c.close();

        //returning the book
        return d;
    }

    public ArrayList<Data> getListData(){
        ArrayList<Data> list=new ArrayList<Data>();
        Cursor c = _bdd.query(TABLE, new String[] {COL_ID, COL_KEY, COL_DATA},null, null, null, null, null);
        if(c.getCount()==0)
            return list;
        c.moveToFirst();
        Data d = new Data(c.getString(NUM_COL_KEY),c.getString(NUM_COL_DATA));
        list.add(d);

        while(c.moveToNext()){
            Data d1 = new Data(c.getString(NUM_COL_KEY),c.getString(NUM_COL_DATA));
            list.add(d1);
        }

        return list;
    }




}
