package wl.SecureModule;

import sun.security.provider.SHA;
import sun.security.util.BigInt;
import wl.SecureBase.Data;
import wl.SecureBase.DataBase;
import wl.SecureBase.DisplayInfo;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by slavnic on 19/11/14.
 */

// Simplified version of Shamir's secret sharing
public class Shamir {
    private int t; // number of secret
    private BigInteger m; // the field Zm
    private BigInteger[] _coeff;
    private Random random = new Random();
    private BigInteger r;
    private BigInteger _Masterkey;

    public Shamir(){
        t = 3;
        _Masterkey = Data.key1.xor(DataBase.key2.xor(DisplayInfo.key3));
        m = _Masterkey.add(BigInteger.ONE);
        _coeff = new BigInteger[t];
    }

    public Shamir(BigInteger secret){
        t = 3;
        m = secret.add(BigInteger.ONE);
        _coeff = new BigInteger[t];
    }
    public void split(){

        BigInteger somme = BigInteger.ZERO;

        r = new BigInteger(127,random);
        Data.secret1 = r.mod(m);
        r = new BigInteger(127,random);
        DataBase.secret2 = r.mod(m);

        somme = somme.add(Data.secret1).mod(m);
        somme = somme.add(DataBase.secret2).mod(m);

        DisplayInfo.secret3 = _Masterkey.subtract(somme).mod(m);

    }
    public void split(BigInteger Secret){
        int i;
        BigInteger somme = BigInteger.ZERO;
        for(i = 0;i<t-1;i++){

            r = new BigInteger(127,random); // r is in Zm

            _coeff[i] = r.mod(m);



            somme = somme.add(_coeff[i]).mod(m);

        }
        _coeff[t-1] = Secret.subtract(somme).mod(m) ;

    }
    public BigInteger combine(BigInteger[] coeff){
        BigInteger somme = BigInteger.ZERO;
        int i;
        for(i = 0;i<t;i++){
            somme = somme.add(coeff[i]).mod(m) ;
        }
        return somme;
    }
    public BigInteger[] get_coeff(){
        return _coeff;
    }
}
