package com.matrix.proxy.auth.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author luqiang
 */
public class RsaKeyHelper {

    private final static Logger logger = LoggerFactory.getLogger(RsaKeyHelper.class);
    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static Optional<PublicKey> getPublicKey(byte[] publicKey) {


        PublicKey pubKey = null;
        try {
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(publicKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            pubKey = keyFactory.generatePublic(keySpecX509);
        } catch (Exception e) {
            logger.error("RsaKeyHelper getPublicKey  error:",e);

        }

        return Optional.of(pubKey);
    }

    /**
     * 获取密钥
     *
     * @param privateKey
     * @return
     * @throws Exception
     */

    public static PrivateKey getPrivateKey(byte[] privateKey)  {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            logger.error("RsaKeyHelper getPrivateKey  error:",e);
        }
        return null;
    }


    /**
     * 生存rsa公钥
     *
     * @param password
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] generatePrivateKey(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        return keyPair.getPrivate().getEncoded();
    }

    public static Map<String, byte[]> generateKey(String password) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        Map<String, byte[]> map = new HashMap<>(16);
        map.put("pub", keyPair.getPublic().getEncoded());
        map.put("pri", keyPair.getPrivate().getEncoded());
        return map;
    }


//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        SecureRandom secureRandom = new SecureRandom("123".getBytes());
//        keyPairGenerator.initialize(1024, secureRandom);
//        KeyPair keyPair = keyPairGenerator.genKeyPair();
//        System.out.println( new BASE64Encoder().encode(keyPair.getPrivate().getEncoded()));
//    }


}

