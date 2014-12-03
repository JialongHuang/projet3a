package wl.SecureModule;

import wl.SecureBase.Data;
import wl.SecureBase.DataBase;
import wl.SecureBase.DisplayInfo;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by huang and slavnic on 29/10/14.
 */

public class CipherAlgo {

    private static String _algo;
    private static String _keyValue;
    private static String _encoding ="UTF-8";
    private static String _secretKeyMode = "AES";
    private static BigInteger _Combinekey;
    private static BigInteger[] _SecretKey;

    public CipherAlgo(){
        _algo = "AES/CBC/PKCS5Padding";

        _SecretKey = new BigInteger[3];
        _SecretKey[0] = Data.secret1;
        _SecretKey[1] = DataBase.secret2;
        _SecretKey[2] = DisplayInfo.secret3;

        Shamir shamir = new Shamir();
        _Combinekey  = shamir.combine(_SecretKey);

        //Master key made by xoring 3 different key
        //_Masterkey = Data.key1.xor(DataBase.key2.xor(DisplayInfo.key3));

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
    }

    //_keyValue.getBytes(_encoding)
    public byte[] encrypt(String plainText,byte[] IV) throws Exception {
        Cipher cipher = Cipher.getInstance(_algo);
        SecretKeySpec key = new SecretKeySpec(_Combinekey.toByteArray(), _secretKeyMode);
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV));
        byte[] encVal=cipher.doFinal(plainText.getBytes(_encoding));
        return encVal;
    }

    public String decrypt(byte[] cipherText,byte[] IV) throws Exception{
        Cipher cipher = Cipher.getInstance(_algo);
        SecretKeySpec key = new SecretKeySpec(_Combinekey.toByteArray(), _secretKeyMode);
        cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV));
        return new String(cipher.doFinal(cipherText),_encoding);

    }


    public String toBinary( byte[] bytes ){
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    public byte[] fromBinary( String s ){
        int sLen = s.length();
        byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
        char c;
        for( int i = 0; i < sLen; i++ )
            if( (c = s.charAt(i)) == '1' )
                toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
            else if ( c != '0' )
                throw new IllegalArgumentException();
        return toReturn;
    }
}

