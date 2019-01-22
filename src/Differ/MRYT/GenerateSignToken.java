package Differ.MRYT;

import javax.crypto.Cipher;
import java.security.Key; 
import java.security.KeyFactory; 
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Decoder; 
import sun.misc.BASE64Encoder;

public class GenerateSignToken {
    public static String KEY_ALGORITHM = "RSA";
    
    /**
     * 
     * @param sessionKey
     * @param fisrstPubKey
     * @param secondPubKey
     * @param thirdPubKey
     * @return
     * 0:public key is empty;
     * "":public key failed to generate.
     * @throws Exception
     */
    public String GetToken(String sessionKey, String fisrstPubKey, String secondPubKey, String thirdPubKey) {
        sessionKey = "50d2339380f1ef5697979269b156a5ba";
        fisrstPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHoBL/L5ApMPlXIe+QuYScL9gFUt70h0sxmTeb\n";
        secondPubKey = "NBhEZmmmSJgJFrz8dZFGyRNXy3FGOpIFAtO+ydYV26mwSQRD7Q8lPoEGeyup6BW4dt0LObt+0O1Y\n";
        thirdPubKey = "wS9Zsktxe5uIqnJP5BGySSH/iVNYGdzsaui1pb3x8CMiRR1jlFocSUj3mwIDAQAB";
        String token = fisrstPubKey + secondPubKey + thirdPubKey;
        System.out.print(token);

        if (token.length() == 0)
        {
            return "0";
        }

        String sign = "";

        try {
            byte[] encrypt = EncryptByPublicKey(sessionKey.getBytes(), token);
            sign = EncryptBASE64(encrypt).replaceAll("\n", "").replaceAll("\r", "");
            System.out.print(sign);
        } catch (Exception e) {
            sign = "";
        }
        
        return sign;
    }

    byte[] EncryptByPublicKey (byte[] data, String key) throws Exception {
        // Decrypt the session key.
        byte[] keyBytes = decryptBASE64(key);

        // Get the public key.
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec (keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // Encode the session key with public key by RSA.
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init (Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    byte[] decryptBASE64 (String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    String EncryptBASE64 (byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}