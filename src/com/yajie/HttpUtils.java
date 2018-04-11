
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static String postText(String url, String text) {
		String result = "";

		try {
			//第一步：创建HttpClient对象  
			CloseableHttpClient httpClient = HttpClients.createDefault();
			httpClient = HttpClients.createDefault();

			//第二步：创建httpPost对象  
			HttpPost httpPost = new HttpPost(url);

			//第三步：给httpPost设置text/plain格式的参数  
			StringEntity requestEntity = new StringEntity(text, "utf-8");
			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "text/plain");
			httpPost.setEntity(requestEntity);

			//第四步：发送HttpPost请求，获取返回值  
			//			         returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法  
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);// 关闭HttpEntity流
			response.close();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
