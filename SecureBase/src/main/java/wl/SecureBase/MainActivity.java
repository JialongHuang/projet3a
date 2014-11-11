package wl.SecureBase;

import wl.SecureModule.CipherAlgo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by huang and slavnic on 29/10/14.
 */
public class MainActivity  extends Activity implements View.OnClickListener {
    private Button _info= null,_ok=null,_delete=null;
    private EditText _key,_data,_deleteKey;
    private DataBase _bd;
    private CipherAlgo _cipher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _info=(Button)findViewById(R.id.buttonInfo);
        _info.setOnClickListener(this);
        _ok=(Button)findViewById(R.id.buttonOk);
        _ok.setOnClickListener(this);
        _delete=(Button)findViewById(R.id.buttonDelete);
        _delete.setOnClickListener(this);
        _key =(EditText)findViewById(R.id.textKey);
        _data =(EditText)findViewById(R.id.textData);
        _deleteKey =(EditText)findViewById(R.id.textDelete);
        _bd = new DataBase(this);
        _bd.open();
        _cipher=new CipherAlgo();
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonOk:

                try {
                    String encKey = _cipher.encrypt(_key.getText().toString());
                    String encData=_cipher.encrypt(_data.getText().toString());
                    byte[] IV = _cipher.getIV();
                    _key.setText("");
                    _data.setText("");

                    Data data = new Data(encKey,encData,IV);
                    _bd.insertData(data);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.buttonInfo:
                Intent intent = new Intent(MainActivity.this, DisplayInfo.class);
                startActivity(intent);
                break;

            case R.id.buttonDelete:
                _bd.deleteDataByKey(_deleteKey.getText().toString());
                _deleteKey.setText("");
                break;

        }
    }



}
