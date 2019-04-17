package com.crsm.maker.crypto;



/**
 * creat by Ccr on 2019/4/11
 **/
public interface SSCrypto {

    byte[] encrypt(byte[] data,int length)throws CryptoException;

    byte[] decrypt(byte[] data,int lenth)throws CryptoException;

    int getIVLength();

    int getKeyLength();

    byte[] getIV(boolean encrypt);

    byte[] getKey();



}
