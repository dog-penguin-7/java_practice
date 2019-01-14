import com.alibaba.fastjson.JSON; 
import org.apache.http.HttpEntity; 
import org.apache.http.HttpResponse; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.client.utils.URIBuilder; 
import org.apache.http.entity.StringEntity; 
import org.apache.http.impl.client.CloseableHttpClient; 
import org.apache.http.impl.client.HttpClients; 
import org.apache.http.util.EntityUtils; 
import sun.misc.BASE64Decoder; 
import sun.misc.BASE64Encoder;
 
import javax.crypto.Cipher; 
import java.io.IOException; 
import java.net.URI; 
import java.security.Key; 
import java.security.KeyFactory; 
import java.security.spec.X509EncodedKeySpec;

public class ApiDemo {    
	private static ?nal CloseableHttpClient client = HttpClients.createDefault(); ? ? 
	private static ?nal String HOST = "apiopen4-staging.mryitao.cn"; ? ? 
	private static ?nal String SCHEME = "http"; ? ? 
	private static ?nal String appId = "7cc450b754968297d0b6a62fe3c0?35"; ? ? 
	private static ?nal String sessionKey = "2f917f47f1313aab021230ccfa470518"; ? ? 
	private static ?nal String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCizghZPZG/TwFw7KiObw4o32vR tsjkr2AcNpXZ\n" + ? ? ? ? ? ? "20KRaspiUQcl1Q7t+eIkfKemJm8brrwuWF8?bIpL1ITbTrEpZbohGpyT2wD8umS5D+PAq 30kevD\n" + ? ? ? ? ? ? "ZNE8GD2FWnFznnQWPgl/gYIVac/H9Dh9sfZTii8s7Gii/XS8dQIpH2rZIQIDAQAB"; ? ? 
	private static String TOKEN = "";
 
? ? public static void main(String[] args) throws IOException {
 ? ? ? ? try {
  ? ? ? ? ? ? getToken();
  ? ? ? ? ? ? productOrderList();
   ? ? ? ? ? ?export();
  ? ? ? ? ? ? importOrder();
  ? ? ? ? ? ? orderList();
  ? ? ? ? } catch (Exception e) {
  ? ? ? ? ? ? e.printStackTrace();
  ? ? ? ? } ?nally {
  ? ? ? ? ? ? client.close();
  ? ? ? ? }
 ? ? }
 
? ? private static HttpPost createHttpPost(String path) throws Exception { ? ? ? ? URIBuilder builder = new URIBuilder(); ? ? ? ? URI uri = builder.setScheme(SCHEME) ? ? ? ? ? ? ? ? .setHost(HOST) ? ? ? ? ? ? ? ? .setPath(path) ? ? ? ? ? ? ? ? .build(); ? ? ? ? HttpPost post = new HttpPost(uri); ? ? ? ? post.setHeader("Content-Type", "application/json"); ? ? ? ? post.setHeader("Authentication", TOKEN); ? ? ? ? return post; ? ? }

 	private static void getToken() throws Exception {
  ? ? ? ? byte[] encrypt = RSA.encryptByPublicKey(sessionKey.getBytes(), publicKey);
  ? ? ? ? String sign = RSA.encryptBASE64(encrypt).replaceAll("\n", "").replaceAll("\r", "");
  ? ? ? ? System.out.println("appId: " + appId); ? ? ? ? System.out.println("encryptStr: " + sign);
  ? ? ? ? String result = null;
  ? ? ? ? String path = "/api/auth/token";
  ? ? ? ? try {
 ? ? ? ? ? ? HttpPost post = createHttpPost(path);
 ? ? ? ? ? ? String body = "{\"appId\":\"" + appId + "\",\"sign\":\"" + sign + "\"}";
 ? ? ? ? ? ? result = executeRequest(post, body);
 ? ? ? ? ? ? ApiResult object = JSON.parseObject(result, ApiResult.class);
 ? ? ? ? ? ? TOKEN = (String) object.getData();
 ? ? ? ? } catch (Exception e) {
 ? ? ? ? ? ? e.printStackTrace();
 ? ? ? ? ? ? System.out.println("接?请求失败" + e.getStackTrace());
 ? ? ? ? }
 ? ? ? ? System.out.println("getToken result:" + result);
 ? ? }
 
 
? ? /** ? ? ?
	* 订单查询(商品维度，不会改变发货导出状态)
 ? ? ?*/ ? ? 
	private static void productOrderList() throws IOException {
	 ? ? ? ? String result = null;
	 ? ? ? ? String path = "/api/order/product/list";
	 ? ? ? ? try {
	 ? ? ? ? ? ? HttpPost post = createHttpPost(path);
	 ? ? ? ? ? ? String body = " {\"requestId\":\"\",\"pageSize\":2,\"orderNo\":\"\",\"orderStatus\":\"\",\"startOrderTi me\":\"\",\"endOrderTime\":\"\"}";
	 ? ? ? ? ? ? result = executeRequest(post, body);
	 ? ? ? ? } catch (Exception e) {
	 ? ? ? ? ? ? e.printStackTrace();
	 ? ? ? ? ? ? System.out.println("接?请求失败" + e.getStackTrace());
	 ? ? ? ? } ? ? ? ? 
			System.out.println("productOrderList result:" + result);
	 ? ? }
 

	 private static void orderList() throws IOException { ? ? ? ? 
	 	String result = null;
	  ? String path = "/api/order/list";
	 	try {
	  ? ? ? HttpPost post = createHttpPost(path);
	   ? ? ?String body = " {\"requestId\":\"\",\"orderNo\":\"\",\"pageSize\":2,\"orderStatus\":\"\",\"name\":\"\",\ "startOrderTime\":\"\",\"endOrderTime\":\"\"}"; ? ? ? ? ? ? 
	   		result = executeRequest(post, body); ? ? ? ? 
	   	} catch (Exception e) {
	   	e.printStackTrace(); ? ? ? ? ? ? 
	   	System.out.println("接?请求失败" + e.getStackTrace()); ? ? ? ? 
	   } ? ? ? ? 
	   System.out.println("orderList result:" + result); ? ? 
	}
 }