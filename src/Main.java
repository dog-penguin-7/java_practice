import javax.crypto.Cipher; 
import java.security.Key; 
import java.security.KeyFactory; 
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Decoder; 
import sun.misc.BASE64Encoder;

public class Main {
    public static String KEY_ALGORITHM = "RSA";
    
    public static void main(String[] args) {
        args = new String[2];
        args[0] = "50d2339380f1ef5697979269b156a5ba";
        args[1] = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHoBL/L5ApMPlXIe+QuYScL9gFUt70h0sxmTeb\n"
        + "NBhEZmmmSJgJFrz8dZFGyRNXy3FGOpIFAtO+ydYV26mwSQRD7Q8lPoEGeyup6BW4dt0LObt+0O1Y\n"
        + "wS9Zsktxe5uIqnJP5BGySSH/iVNYGdzsaui1pb3x8CMiRR1jlFocSUj3mwIDAQAB";

        try {
            GetToken(args[0], args[1]);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void GetToken(String sessionKey, String publicKey) throws Exception {
        byte[] encrypt = EncryptByPublicKey(sessionKey.getBytes(), publicKey);
        String sign = EncryptBASE64(encrypt).replaceAll("\n", "").replaceAll("\r", "");
        System.out.println("encryptStr: " + sign);
    }

    static byte [] EncryptByPublicKey (byte[] data, String key) throws Exception {
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

    static byte[] decryptBASE64 (String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    static String EncryptBASE64 (byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}