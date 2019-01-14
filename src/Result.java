import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.net.URI;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

public class Result<T> {
    private T data;
    private int code = 0;
    private String msg = "";
    private static int success_code = 0;

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result (int code, String msg, T data) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static int getSuccessCode() {
        return success_code;
    }

}

class RSA {
    public static String KEY_ALGORITHM = "RSA";

    public static byte [] encryptByPublicKey (byte[] data, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec (keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init (Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static byte[] decryptBASE64 (String key) throws Exception {
        return (new BASE64Decoder()).decodeBuﬀer(key);
    }

    public static String encryptBASE64 (byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuﬀer(key);
    }
}
