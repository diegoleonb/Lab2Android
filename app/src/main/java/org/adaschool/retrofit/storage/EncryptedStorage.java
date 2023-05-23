package org.adaschool.retrofit.storage;

import android.content.SharedPreferences;

import org.adaschool.retrofit.storage.Encript.Encript;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class EncryptedStorage implements Storage{

    final String TOKEN_KEY= "token_key";

    SecretKey key;
    IvParameterSpec ivParameterSpec;
    String algorithm = "AES/CBC/PKCS5Padding";

    private SharedPreferences sharedPreferences;


    public EncryptedStorage(SharedPreferences sharedPreferences) throws NoSuchAlgorithmException {
        this.sharedPreferences = sharedPreferences;
        this.key = Encript.generateKey(128);
        this.ivParameterSpec = Encript.generateIv();
    }

    @Override
    public void saveToken(String token) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        sharedPreferences.edit()
                .putString(TOKEN_KEY, Encript.encrypt(algorithm, token, key, ivParameterSpec))
                .apply();
    }

    @Override
    public String getToken() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return Encript.decrypt(algorithm,sharedPreferences.getString(TOKEN_KEY,""),key,ivParameterSpec);
    }

    @Override
    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
