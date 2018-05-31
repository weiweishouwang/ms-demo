package com.zhw.ms.commons.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by 小殊 on 2016/3/11 10:53.
 */
public class AESUtil {

    public static String encrypt(String content, String key) {
        try {
            byte[] encryptBytes = aesEncrypt(content, key);

            Base64.Encoder encoder = Base64.getUrlEncoder();
            return encoder.encodeToString(encryptBytes);
        } catch (Exception ex) {
            return null;
        }

    }

    public static String decrypt(String data, String key) {
        try {
            Base64.Decoder decoder = Base64.getUrlDecoder();
            byte[] encryptBytes = decoder.decode(data);
            byte[] decryptBytes = aesDecrypt(encryptBytes, key);
            return new String(decryptBytes, "UTF-8");

        } catch (Exception ex) {

        }
        return null;
    }

    private static byte[] aesEncrypt(String content, String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(key.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    private static byte[] aesDecrypt(byte[] data, String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(key.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

        return cipher.doFinal(data);
    }


    private static final String IV = "1234567890abcdef";
    /**
     * 加密
     *
     * @param data the data
     * @return string
     * @throws Exception the exception
     */
    public static String encryptCBC(String data,String key) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength
                        + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new String(Base64.getEncoder().encode(encrypted));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     *
     * @param data the data
     * @return string
     * @throws Exception the exception
     */
    public static String desEncryptCBC(String data,String key) throws Exception {
        try {
            if (data.length() < 24 || (32 < data.length() && data.length() < 48)) {
                for (int i = 0; i <= 24 - data.length(); i++) {
                    data = data + "=";
                }
            }
            Base64.Decoder decoder = Base64.getUrlDecoder();
            byte[] encryptBytes = decoder.decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encryptBytes);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
