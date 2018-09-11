package com.soecode.lyf.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxl on 2018/6/1.
 */
public class RsaJsUtils {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMD+xpF7NECyyom8vW1XS77CcgiMyxgHgIixmp\n" +
            "ySYNWGOuSm3H79xrT3LSDm/RGyzzfBBZLYljQSSovjTOfhdinmhYMeHT0jBXZQ9HVC5GjEIpkPHo\n" +
            "OpnraWUfBP6o8fLPUQwD0cJt/HQhuHnISDfw/mzaL7Au9KG0FvlSvIYIEwIDAQAB";
    private static final String PRIVATE_KEY = "MIICoTAbBgkqhkiG9w0BBQMwDgQIuEjBiVNeHegCAggABIICgCGFysyQhqFBC20h\n" +
            "S2z5Ct/eWVsPvZeO0msBNSEMV4GJBaHpV47Ln6juQr4D4XRDVbPpdBrDGYLKnrdp\n" +
            "PjPUo2vQqjdKa+GcUxcgnAvOBqwPQekKcyucLWsZG21O/wQMts7qIZUlTZBZN+TP\n" +
            "kIcoa6pdIY87uoRa2PoXIrVwREwUciSnWbtQ5n9kBf7Mm5iWzdY1dNmcwymmioH7\n" +
            "aWKVDv1Gk0oaDGGZUMPtnKsbKxzwLGdjOdTJd/RpKrLqQA2Yuw+jIU46tObBgj7I\n" +
            "ghdOYz8KrgTXZ/08iu9eTYYSCOcE1gyZnjgjF97JzvIOY1KltlAMkaq53Y1XpQEa\n" +
            "exJMtgF8lZmuQEU1fdsjkouiKjZaRZUv/4XVakKviC+MrjJvUXHLBBI23RYiyMuS\n" +
            "U7F4dEA36q1JiRIQRev6lroAv/vwvouyQk5QvnTIdQl2MlE0asW9djZAUp/NlJTv\n" +
            "MwVPUWtk2NZ9BoZIo6tljkmt1YKYuA9rmfKTHvomTuciWHLlZrB5LlREIfubaxEo\n" +
            "PXGeoJ6clOvjbTix1smd7ndhb6uIZbohY7/nrxyBkO/X3/fEvOXYT8aUq0G2EXdM\n" +
            "1y6Q8NsfVMSTTjrbyADLYWRAbJs9BlwI8cRvbCJy+yNz2xnW1m3WqHI0PeZeNMzE\n" +
            "YnpiGYbH+zKf4jHao3X6XrirpII7jUhd6CdUQtPge+WnGWNa7wHRDmaGnp8wBw15\n" +
            "+GugA9H2L4G4/01Z2FYbjIA+wOQrhDQui/OBdpTzsNI83S9c0s9iLLx5pD333Jus\n" +
            "MzS4DfivWeAdcKN6g8dzJ/B69pX+V2NwfureJzHo0yKuZlchMlnQHoFgSAHI1enX\n" +
            "u0TwklU=";

    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }


    /**
     * 解密
     * @param encodedData
     * @param
     * @return
     * @throws Exception
     */
    public static String  decryptByPrivateKeyData(String encodedData)throws Exception{
        byte[] decodedData = decryptByPrivateKey(encodedData, RsaJsUtils.PRIVATE_KEY);
        String jsonStr = new String(decodedData);
        return jsonStr;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data    加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data   加密数据
     * @param publicKey 公钥
     * @param sign   数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception{
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return decryptByPrivateKey(decryptBASE64(data),key);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }

    public static void main(String[] args) throws Exception{
        Map<String, Key> keyMap = RsaJsUtils.initKey();
        //"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDbQPesw6//kWjGGxwmt1cJWCIHUKIM4QDiOmNyYhtMzOloTunHNHBZTBSSKqXaHT5rGAWLrzSg+UFS9Awbt66ucB3vljMiNGBmdxuDdbBpM75OomXyw0R6zrwif2JUm3kLtJVERyiidqNFyzDLNbqfwRkhx7wl5Ms99+nkRu8liwIDAQAB";//
       String publicKey = RsaJsUtils.getPublicKey(keyMap);
        String privateKey = RsaJsUtils.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);

        System.err.println("公钥加密——私钥解密");
        String inputStr = "nihao-abcd121212";
        byte[] encodedData = encryptByPublicKey(inputStr, publicKey);
        String ee = new String(encodedData);
        byte[] decodedData = decryptByPrivateKey(encodedData,
                privateKey);
        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);



     /*   System.err.println("私钥加密——公钥解密");
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();
        byte[] encodedData1 = .encryptByPrivateKey(data, privateKey);
        byte[] decodedData1= .decryptByPublicKey(encodedData, publicKey);
        String outputStr1 = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        (inputStr, outputStr);
        System.err.println("私钥签名——公钥验证签名");
        // 产生签名
        String sign = .sign(encodedData, privateKey);
        System.err.println("签名:" + sign);
        // 验证签名
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("状态:" + status);
        assertTrue(status);*/
    }
}
