
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	// post发送text/plain请求
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
	// post发送表单提交请求
	public static String post(String url, Map<String, String> params, String charset, Map<String, String> headers)
			throws IOException {
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
			}

			if (headers != null && headers.size() > 0) {
				Set<Map.Entry<String, String>> entrys = headers.entrySet();
				for (Map.Entry<String, String> entry : entrys) {
					httpPost.addHeader(entry.getKey(), entry.getValue());
				}
			}

			CloseableHttpClient httpClient;
			RequestConfig config = RequestConfig.custom().setConnectTimeout(125000).setSocketTimeout(30000).build();
			httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	// get请求
	public static String get(String url, String charset, Map<String, String> headers) throws Exception {
		// get请求返回结果  
		String strResult = "";
		HttpGet request = null;

		try {
			CloseableHttpClient client = HttpClients.createDefault();
			// 发送get请求  
			request = new HttpGet(url);
			// 设置请求和传输超时时间  
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(125000).setConnectTimeout(30000)
					.build();
			request.setConfig(requestConfig);

			if (headers != null && headers.size() > 0) {
				Set<Map.Entry<String, String>> entrys = headers.entrySet();
				for (Map.Entry<String, String> entry : entrys) {
					request.addHeader(entry.getKey(), entry.getValue());
				}
			}

			CloseableHttpResponse response = client.execute(request);
			//请求发送成功，并得到响应  
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				//读取服务器返回过来的json字符串数据  
				HttpEntity entity = response.getEntity();
				strResult = EntityUtils.toString(entity, charset);
				return strResult;
			} else {
				request.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (request != null) {
				request.releaseConnection();
			}
		}
	}
}
