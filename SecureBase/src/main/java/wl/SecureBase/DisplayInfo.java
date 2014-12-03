package wl.SecureBase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by huang and slavnic on 29/10/14.
 */
public class DisplayInfo extends Activity implements OnClickListener{
    private static String string3 = "DISPLAYINFODISPL";
    public static BigInteger key3 = new BigInteger(string3.getBytes());
    public static BigInteger secret3;

    private Button _back;
    private DataBase _db;
    private ArrayList<Data> _listData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayinfo);
        _back=(Button)findViewById(R.id.back);
        _back.setOnClickListener(this);
        _db=new DataBase(this);
        _db.open();



        _listData=_db.getListData();
        final ArrayList<String> list = new ArrayList<String>();
        final ListView listview = (ListView) findViewById(R.id.listView1);

        Data d;
        for ( int i=0;i<_listData.size();++i){
            d=_listData.get(i);
            list.add("KEY: "+d.getKey()+" \n DATA:"+d.getData());
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
    }




    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DisplayInfo.this, MainActivity.class);
        startActivity(intent);

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }






}
