package wl.SecureBase;

import android.content.Context;
import wl.SecureModule.CipherAlgo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import wl.SecureModule.Shamir;
import wl.SecureBase.DisplayStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;


/**
 * Created by huang and slavnic on 29/10/14.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private Button _info= null,_ok=null,_delete=null,_clearBase=null;
    private EditText _key,_data,_deleteKey;
    private DataBase _db;
    private CipherAlgo _cipher;
    private SecureRandom _prng;
    private byte[] _IV;

    private StackTraceElement[] _st;
    public static FileOutputStream fos;
    public static String FILENAME = "stack";



    private String testkey = "ENSICAENENSICAEN";




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
        _clearBase=(Button)findViewById(R.id.buttonClearBase);
        _clearBase.setOnClickListener(this);

        _key =(EditText)findViewById(R.id.textKey);
        _data =(EditText)findViewById(R.id.textData);
        _deleteKey =(EditText)findViewById(R.id.textDelete);
        _db = new DataBase(this);
        _db.open();

        Shamir shamir = new Shamir();
        shamir.split();

        _cipher=new CipherAlgo();
        try {
            _prng = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        _IV = new byte[16];



        //testShamir();
    }





    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonOk:

                try {
                    testEncryption();
                    _st=Thread.currentThread().getStackTrace();
                    _key.setText(_st[2].getClassName());
                    try {
                        fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    String s= _st[2].getMethodName()+"\n";
                    fos.write(s.getBytes());
                    fos.write(_st[2].getClassName().getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.buttonInfo:
                Intent intent = new Intent(MainActivity.this, DisplayInfo.class);
                startActivity(intent);
                break;

            case R.id.buttonClearBase:
                //_db.clearBase();
                Intent intent1 = new Intent(MainActivity.this, DisplayStack.class);
                startActivity(intent1);
                break;

            /*case R.id.buttonDelete:
                _db.deleteDataByKey(_deleteKey.getText().toString());
                _deleteKey.setText("");
                break;
            */
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        _db.close();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        _db.open();
    }

    private void add() throws Exception{
        _prng.nextBytes(_IV);
        String plainText = _key.getText().toString();
        byte[] encKey = _cipher.encrypt(plainText,_IV);
        byte[] encData =_cipher.encrypt(_data.getText().toString(), _IV);
        _key.setText("");
        _data.setText("");
        Data data = new Data(_cipher.toBinary(encKey),_cipher.toBinary(encData),_IV);
        _db.insertData(data);

    }
    private void testEncryption(){

        _prng.nextBytes(_IV);
        try {
            byte[] encKey = _cipher.encrypt(_key.getText().toString(),_IV);
            byte[] encData =_cipher.encrypt(_data.getText().toString(),_IV);
            _key.setText("");
            _data.setText("");
            Data data = new Data(_cipher.toBinary(encKey),_cipher.toBinary(encData),_IV);
            _db.insertData(data);
            Data d = _db.getDataByKey(_cipher.toBinary(encKey));
            String decData=_cipher.decrypt(_cipher.fromBinary(d.getData()), d.getIV());
            _data.setText(decData);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    /*private void testShamir(){
        Random rnd = new Random();
        BigInteger SecretEnsi = new BigInteger(testkey.getBytes());// Ascii

        BigInteger Secret = new BigInteger(128,rnd);


        Shamir shamir = new Shamir(SecretEnsi);
        shamir.split(SecretEnsi);
        BigInteger sommecoeff = shamir.combine(shamir.get_coeff());

        System.out.println("Secret ="+SecretEnsi+" et Shamir = "+sommecoeff);
    }
    */

}