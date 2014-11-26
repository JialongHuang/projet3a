package wl.SecureModule;

import sun.security.provider.SHA;
import sun.security.util.BigInt;

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

    public Shamir(){
        t = 3;
        m = new BigInteger("200000000");
        _coeff = new BigInteger[t];
    }
    public Shamir(BigInteger secret){
        t = 3;
        m = secret.add(BigInteger.ONE);
        _coeff = new BigInteger[t];
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
