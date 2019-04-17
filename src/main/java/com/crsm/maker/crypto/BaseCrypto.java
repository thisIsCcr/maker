package com.crsm.maker.crypto;

import org.bouncycastle.crypto.StreamCipher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * creat by Ccr on 2019/4/11
 **/
public abstract class BaseCrypto implements SSCrypto {

    protected abstract StreamCipher createCipher(byte[] iv, boolean encrypt) throws CryptoException;

    protected abstract void process(byte[] in, ByteArrayOutputStream out, boolean encrypt);

    protected final String mName;

    protected final byte[] mKey;

    protected final int mIVLength;

    protected final int mKeyLength;

    protected StreamCipher mEncryptCipher = null;

    protected StreamCipher mDecryptCipher = null;

    protected byte[] mEncryptIV;

    protected byte[] mDecryptIV;

    protected ByteArrayOutputStream mData;

    protected final byte[] mLock = new byte[0];

    public BaseCrypto(String name, String password) throws CryptoException {
        mName = name.toLowerCase();
        mIVLength = getIVLength();
        mKeyLength = getKeyLength();
        if (mKeyLength == 0) {
            throw new CryptoException("Unsupport method:" + mName);
        }
        mKey = Utils.getKey(password, mKeyLength, mIVLength);
        mData = new ByteArrayOutputStream();
    }

    private byte[] encryptLocked(byte[] data) throws CryptoException {
        mData.reset();
        if (mEncryptCipher == null) {
            mEncryptIV = getIV(true);
            mEncryptCipher=createCipher(mEncryptIV,true);
            try {
                mData.write(mEncryptIV);
            } catch (IOException e) {
                throw new CryptoException(e);
            }
        }
        process(data,mData,true);
        return mData.toByteArray();
    }

    @Override
    public byte[] encrypt(byte[] in, int length) throws CryptoException {
        synchronized (mLock) {
            if (in.length != length) {
                byte[] data = new byte[length];
                //将数据复制到data中
                System.arraycopy(in, 0, data, 0, length);
                return encryptLocked(data);
            } else {
                return encryptLocked(in);
            }
        }
    }

    private byte[] decryptLocked(byte[] in) throws CryptoException {
        byte[] data;
        mData.reset();
        if (mDecryptCipher == null) {
            mDecryptCipher = createCipher(in, false);
            mDecryptIV = new byte[mIVLength];
            data = new byte[in.length - mIVLength];
            System.arraycopy(in, 0, mDecryptIV, 0, mIVLength);
            System.arraycopy(in, mIVLength, data, 0, in.length - mIVLength);
        } else {
            data = in;
        }
        process(data, mData, false);
        return mData.toByteArray();
    }

    @Override
    public byte[] decrypt(byte[] in, int length) throws CryptoException {
        synchronized (mLock) {
            if (length != in.length) {
                byte[] data = new byte[length];
                System.arraycopy(in, 0, data, 0, length);
                return decryptLocked(data);
            } else {
                return decryptLocked(in);
            }
        }
    }


    @Override
    public byte[] getIV(boolean encrypt) {
        if (encrypt) {
            if (mEncryptIV == null) {
                mEncryptIV = Utils.randomBytes(mIVLength);
            }
            return mEncryptIV;
        } else
            return mDecryptIV;
    }

    @Override
    public byte[] getKey() {
        return mKey;
    }
}
