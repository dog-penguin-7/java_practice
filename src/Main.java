import self.practice.string.TernaryExpressionSplite;

import javax.crypto.Cipher; 
import java.io.IOException; 
import java.net.URI; 
import java.security.Key; 
import java.security.KeyFactory; 
import java.security.spec.X509EncodedKeySpec;

public class Main {

    public static void main(String[] args) {
        try {
            ApiDemo.getToken();
        } catch (Exception e) {
            System.out.println(e);
        }      
    }
}

class ApiDemo {    
	private static final String sessionKey = "2f917f47f1313aab021230ccfa470518";     
	private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCizghZPZG/TwFw7KiObw4o32vR tsjkr2AcNpXZ\n"
	 + "20KRaspiUQcl1Q7t+eIkfKemJm8brrwuWF8 bIpL1ITbTrEpZbohGpyT2wD8umS5D+PAq 30kevD\n"
	 + "ZNE8GD2FWnFznnQWPgl/gYIVac/H9Dh9sfZTii8s7Gii/XS8dQIpH2rZIQIDAQAB";     

 	public static void getToken() throws Exception {
          byte[] encrypt = RSA.encryptByPublicKey(sessionKey.getBytes(), publicKey);
          String sign = RSA.encryptBASE64(encrypt).replaceAll("\n", "").replaceAll("\r", "");
		  System.out.println("encryptStr: " + sign);
     }
 }

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