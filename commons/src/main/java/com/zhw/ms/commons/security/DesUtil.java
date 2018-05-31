package com.zhw.ms.commons.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * DES加密解密共通
 */
public class DesUtil {

    private final static String DES = "DES";

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("10000", "4d98d69a-148b-4297-8ec3-b5f0738251a8"));
        System.out.println(decrypt("ue1SD5E3HUA=", "4d98d69a-148b-4297-8ec3-b5f0738251a8"));
        System.out.println(System.currentTimeMillis());
        //System.out.println(CryptoUtil.md5("1458108755190" + "1" + "10000" + "4d98d69a-148b-4297-8ec3-b5f0738251a9", "bd560e3a-f5f4-404e-835a-07bf3db14baf"));

        //String data = "\"status_code\":901,\"user_code\":\"10161434607670602510\",\"user_id\":QMQcBf7uCmw=,\"authentic_name\":\"++maaC9g+cmjZDZ1/3ENpA==\",\"is_VIP\":0,\"id_card\":\"+d1OVDBrickPYh+RlmZL2j+gz6nrxVxY\",\"trade_ever\":0";
        //JsonUtil.json2Entity(data, Map.class);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @param key  加密键byte数组
     * @return 密文
     */
    public static String encrypt(String data, String key) {
        try {
            byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes());
            return new String(Base64.getEncoder().encode(bt));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 明文
     */
    public static String decrypt(String data, String key) {
        try {
            if (data == null) {
                return null;
            }


            byte[] buf = Base64.getDecoder().decode(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data 明文
     * @param key  加密键byte数组
     * @return 密文
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new byte[0];
        }

    }


    /**
     * Description 根据键值进行解密
     *
     * @param data 密文
     * @param key  加密键byte数组
     * @return 明文
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new byte[0];
        }
    }
}
