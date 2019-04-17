package com.crsm.maker.crypto;

import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;

import java.io.ByteArrayOutputStream;

/**
 * creat by Ccr on 2019/4/11
 **/
public class ASECrypto extends BaseCrypto{

    public final static String CIPHER_AES_128_CFB = "aes-128-cfb";
    public final static String CIPHER_AES_192_CFB = "aes-192-cfb";
    public final static String CIPHER_AES_256_CFB = "aes-256-cfb";
    public final static String CIPHER_AES_128_OFB = "aes-128-ofb";
    public final static String CIPHER_AES_192_OFB = "aes-192-ofb";
    public final static String CIPHER_AES_256_OFB = "aes-256-ofb";

    private final static int IV_LENGTH = 16;


    public ASECrypto(String name, String password) throws CryptoException {
        super(name, password);
    }

    protected StreamBlockCipher getCipher(boolean isEncrypted) throws CryptoException
    {
        AESEngine engine = new AESEngine();
        StreamBlockCipher cipher;

        if (mName.equals(CIPHER_AES_128_CFB)) {
            cipher = new CFBBlockCipher(engine, getIVLength() * 8);
        }
        else if (mName.equals(CIPHER_AES_192_CFB)) {
            cipher = new CFBBlockCipher(engine, getIVLength() * 8);
        }
        else if (mName.equals(CIPHER_AES_256_CFB)) {
            cipher = new CFBBlockCipher(engine, getIVLength() * 8);
        }
        else if (mName.equals(CIPHER_AES_128_OFB)) {
            cipher = new OFBBlockCipher(engine, getIVLength() * 8);
        }
        else if (mName.equals(CIPHER_AES_192_OFB)) {
            cipher = new OFBBlockCipher(engine, getIVLength() * 8);
        }
        else if (mName.equals(CIPHER_AES_256_OFB)) {
            cipher = new OFBBlockCipher(engine, getIVLength() * 8);
        }
        else {
            throw new CryptoException("Invalid AlgorithmParameter: " + mName);
        }


        return cipher;
    }

    @Override
    protected StreamCipher createCipher(byte[] iv, boolean encrypt) throws CryptoException {
        return null;
    }

    @Override
    protected void process(byte[] in, ByteArrayOutputStream out, boolean encrypt) {
        int size;
        byte[] buffer = new byte[in.length];
        StreamBlockCipher cipher;
        if (encrypt){
            cipher = (StreamBlockCipher)mEncryptCipher;
        }else{
            cipher = (StreamBlockCipher)mDecryptCipher;
        }
        size = cipher.processBytes(in, 0, in.length, buffer, 0);
        out.write(buffer, 0, size);
    }

    @Override
    public int getIVLength() {
        return IV_LENGTH;
    }

    @Override
    public int getKeyLength() {
        if(mName.equals(CIPHER_AES_128_CFB) || mName.equals(CIPHER_AES_128_OFB)) {
            return 16;
        }
        else if (mName.equals(CIPHER_AES_192_CFB) || mName.equals(CIPHER_AES_192_OFB)) {
            return 24;
        }
        else if (mName.equals(CIPHER_AES_256_CFB) || mName.equals(CIPHER_AES_256_OFB)) {
            return 32;
        }
        return 0;
    }
}
