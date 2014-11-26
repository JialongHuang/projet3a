package wl.SecureBase;

/**
 * Created by huang and slavnic  on 29/10/14.
 */
public class Data {
    /*
    * provisional implementation, everything depend on the length of data
    *
    * */
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