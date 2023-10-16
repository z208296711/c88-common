package com.c88.common.core.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {

    private AESUtil() {
        throw new IllegalStateException("Utility class");
    }

    // transformation form:algorithm/mode/padding
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static Key genKey() {
        int keySize = 256; // 密鑰長度
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES"); // 取得AES密鑰產生器實例
            keyGen.init(keySize, new SecureRandom()); // 密鑰產生器初始化
            return keyGen.generateKey(); // 產生密鑰
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 產生初始向量
     *
     * @return 初始向量
     */
    public static IvParameterSpec genIv() {
        byte[] ivBytes = new byte[16]; // 128/8=16, AES block size is 128bits
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(ivBytes); // 隨機生成bytes內容
        return new IvParameterSpec(ivBytes); // initialization vector
    }

    /**
     * AES CBC mode PKCS5Padding加密
     *
     * @param str 未加密資料
     * @param key 密鑰
     * @param iv  初始向量
     * @return 密文 (hex string)
     */
    public static String encrypt(String str, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION); // 取得AES CBC mode, PKCS5Padding的Cipher實例
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(key), getIv(iv)); // Cipher初始化為加密模式
            byte[] encryptDataBytes = cipher.doFinal(str.getBytes()); // 加密資料

//            return Hex.encodeHexString(encryptDataBytes); // Apache Commons Codec轉hex字串方便傳輸
            return Base64.getEncoder().encodeToString(encryptDataBytes); // 轉Base64編碼方便傳輸
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException e
        ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES CBC mode PKCS5Padding解密
     *
     * @param str 密文 (hex string)
     * @param key 密鑰
     * @param iv  初始向量
     * @return 解密資料
     */
    public static String decrypt(String str, String key, String iv) {
        try {
//            byte[] encryptDataBytes = Hex.decodeHex(encryptData); // 解碼hex字串為bytes
            byte[] encryptDataBytes = Base64.getDecoder().decode(str); // 解碼Base64編碼的字串為bytes
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(key), getIv(iv)); // Cipher初始化為解密模式
            byte[] decryptData = cipher.doFinal(encryptDataBytes); // 解密資料

            return new String(decryptData);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidKeyException
                | InvalidAlgorithmParameterException e
        ) {
            e.printStackTrace();
            return null;
        }
    }

    private static IvParameterSpec getIv(String iv) {
        return new IvParameterSpec(iv.getBytes());
    }

    private static SecretKeySpec getSecretKeySpec(String key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        return secretKeySpec;
    }

}
