package wl.SecureModule;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * Created by huang and slavnic on 29/10/14.
 */
public class CipherAlgo {
    private static String _IV;

    private static byte[] _IValea;
    private static byte[] _bytePlainText;
    private static byte[] _byteIV;
    private static String _algo;
    private static String _keyValue;

    public CipherAlgo(){
        _algo = "AES/CBC/PKCS5Padding";
        _keyValue = "ENSICAENENSICAEN";
        _IV ="1234567890000000";
        // TODO gere la taille
        _IValea = new byte[16];
        _byteIV = new byte[16];
        _bytePlainText = new byte [16];

    }

    /**
     *
     * @param algo
     * algorithms supported:
     * AES/CBC/NoPadding (128)
     * AES/CBC/PKCS5Padding (128)
     * AES/ECB/NoPadding (128)
     * AES/ECB/PKCS5Padding (128)
     * DES/CBC/NoPadding (56)
     * DES/CBC/PKCS5Padding (56)
     * DES/ECB/NoPadding (56)
     * DES/ECB/PKCS5Padding (56)
     */
    public CipherAlgo(String algo){
        _algo=algo;
        _keyValue = "ENSICAENMONETIQUE";
        _IV ="informatique";
    }


    public String encrypt(String plainText) throws Exception {
        int j;
        Cipher cipher = Cipher.getInstance(_algo);
        SecretKeySpec key = new SecretKeySpec(_keyValue.getBytes("UTF-8"), "AES");

        //IV made with by XOR static value and plain Text
        _bytePlainText= plainText.getBytes("UTF-8");
        _byteIV = _IV.getBytes("UTF-8");
        for(j=0 ;j<16;j++) {
            _IValea[j] = (byte) (0xff & ((int)_bytePlainText[j] ^ (int)_byteIV[j]));
        }

        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(_IValea));
        byte[] encVal=cipher.doFinal(plainText.getBytes("UTF-8"));
        String s ="";
        for(int i=0;i<encVal.length;i++){
            s+=String.format("%8s", Integer.toBinaryString(encVal[i] & 0xFF)).replace(' ', '0');
        }
        return s;
    }
/*
* TODO add the IV for decrypting
*
* */
    public String decrypt(String cipherText) throws Exception{
        Cipher cipher = Cipher.getInstance(_algo);
        SecretKeySpec key = new SecretKeySpec(_keyValue.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(_IV.getBytes("UTF-8")));
        byte[] val = new BigInteger(cipherText,2).toByteArray();
        return new String(cipher.doFinal(val),"UTF-8");
    }
    public byte[] getIV(){
        return _IValea;
    }
}
