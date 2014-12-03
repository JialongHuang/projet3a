package wl.SecureBase;

import java.math.BigInteger;

/**
 * Created by huang and slavnic  on 29/10/14.
 */
public class Data {
    /*
    * provisional implementation, everything depend on the length of data
    *
    * */
    /**
     * TODO change the name key1 and replace string1 by data from the phone
     * TODO use the class TelephonyManager to get data
     */
    private static String string1 = "ENSICAENENSICAEN";
    public static BigInteger key1 = new BigInteger(string1.getBytes());
    public static BigInteger secret1;

    private String _key;
    private String _data;
    private byte[] _IV;

    public Data(String key,String data,byte[] IV){
        _key=key;
        _data=data;
        _IV = IV;

    }
    public Data(String key,String data){
        _key=key;
        _data=data;
        _IV=null;
    }

    public String getKey() {
        return _key;
    }
    public String getData() {
        return _data;
    }
    public byte[] getIV(){
        return _IV;
    }
}