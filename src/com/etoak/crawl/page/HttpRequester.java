package com.etoak.crawl.page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import com.etoak.crawl.util.HttpRespons;

/**
 * HTTP请求对象
 * 
 * @author Alan Huang
 */
public class HttpRequester {
	private String defaultContentEncoding;
	private String cookie = "";

	public HttpRequester() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString) throws IOException {
		return this.send(urlString, "GET", null, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params) throws IOException {
		return this.send(urlString, "GET", params, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params, Map<String, String> propertys)
			throws IOException {
		return this.send(urlString, "GET", params, propertys);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString) throws IOException {
		return this.send(urlString, "POST", null, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params) throws IOException {
		return this.send(urlString, "POST", params, null);
	}

	public HttpRespons sendPost(String urlString, String ParamsString) throws IOException {
		// 创建url资源
		URL url = new URL(urlString);
		// 建立http连接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置允许输出
		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		// 设置不用缓存
		urlConnection.setUseCaches(true);
		// 设置传递方式
		urlConnection.setRequestMethod("POST");
		// 设置维持长连接
		urlConnection.setRequestProperty("Connection", "Keep-Alive");
		// 设置文件字符集:
		urlConnection.setRequestProperty("Charset", "UTF-8");
		// 转换为字节数组
		byte[] data = ParamsString.getBytes();
		// 设置文件长度
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

		urlConnection.setRequestProperty("accept", "*/*");
		// 设置文件类型:
		urlConnection.setRequestProperty("Content-Type", "application/json");

		// 开始连接请求
		urlConnection.connect();
		OutputStream out = urlConnection.getOutputStream();
		// 写入请求的字符串
		out.write(ParamsString.getBytes());
		out.flush();
		out.close();

		return this.makeContent(urlString, urlConnection);
	}
	
	public HttpRespons sendPostUseAuthorization(String urlString, String ParamsString, String Authorization) throws IOException {
		// 创建url资源
		URL url = new URL(urlString);
		// 建立http连接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置允许输出
		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		// 设置不用缓存
		urlConnection.setUseCaches(true);
		// 设置传递方式
		urlConnection.setRequestMethod("POST");
		// 设置维持长连接
		urlConnection.setRequestProperty("Connection", "Keep-Alive");
		// 设置文件字符集:
		urlConnection.setRequestProperty("Charset", "UTF-8");
		// 转换为字节数组
		byte[] data = ParamsString.getBytes();
		// 设置文件长度
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
		urlConnection.setRequestProperty("Authorization", Authorization);
		urlConnection.setRequestProperty("App-Version", "2.9.4.1");
		urlConnection.setRequestProperty("App-Channel", "100001");
		urlConnection.setRequestProperty("Repeated-Submission", "" + new Date().getTime());
		urlConnection.setRequestProperty("accept", "*/*");
		// 设置文件类型:
		urlConnection.setRequestProperty("Content-Type", "application/json");

		// 开始连接请求
		urlConnection.connect();
		OutputStream out = urlConnection.getOutputStream();
		// 写入请求的字符串
		out.write(ParamsString.getBytes());
		out.flush();
		out.close();

		return this.makeContent(urlString, urlConnection);
	}
	
	
	public HttpRespons sendOptions(String urlString, String ParamsString, String Authorization) throws IOException {
		// 创建url资源
		URL url = new URL(urlString);
		// 建立http连接
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 设置允许输出
		urlConnection.setDoOutput(true);

		urlConnection.setDoInput(true);

		// 设置不用缓存
		urlConnection.setUseCaches(true);
		// 设置传递方式
		urlConnection.setRequestMethod("POST");
		// 设置维持长连接
		urlConnection.setRequestProperty("Connection", "Keep-Alive");
		// 设置文件字符集:
		urlConnection.setRequestProperty("Charset", "UTF-8");
		// 转换为字节数组
		byte[] data = ParamsString.getBytes();
		// 设置文件长度
		urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
		urlConnection.setRequestProperty("Authorization", Authorization);
		urlConnection.setRequestProperty("App-Version", "2.9.4.1");
		urlConnection.setRequestProperty("App-Channel", "100001");
		urlConnection.setRequestProperty("Repeated-Submission", "" + new Date().getTime());

		urlConnection.setRequestProperty("accept", "*/*");
		// 设置文件类型:
		urlConnection.setRequestProperty("Content-Type", "application/json");

		// 开始连接请求
		urlConnection.connect();
		OutputStream out = urlConnection.getOutputStream();
		// 写入请求的字符串
		out.write(ParamsString.getBytes());
		out.flush();
		out.close();

		return this.makeContent(urlString, urlConnection);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params, Map<String, String> propertys)
			throws IOException {
		return this.send(urlString, "POST", params, propertys);
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 * @return 响映对象
	 * @throws IOException
	 */
	private HttpRespons send(String urlString, String method, Map<String, String> parameters,
			Map<String, String> propertys) throws IOException {
		HttpURLConnection urlConnection = null;

		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		
		do {
			URL url = new URL(urlString);
//			HttpURLConnection.setFollowRedirects(true);
			urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.setRequestMethod(method);
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(true);
			urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");
			
			if (cookie.length() != 0) {
				urlConnection.setRequestProperty("Cookie", cookie);
			}
			
		    urlConnection.setInstanceFollowRedirects(false);
		    int code = urlConnection.getResponseCode();
		    if(code == HttpURLConnection.HTTP_MOVED_TEMP) {
		        cookie += urlConnection.getHeaderField("Set-Cookie") + ";";
		    }
		    if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
		    	break;
		    }
		} while(true);
		
//		
//		URL url = new URL(urlString);
//		HttpURLConnection.setFollowRedirects(true);
//		urlConnection = (HttpURLConnection) url.openConnection();
//
//		urlConnection.setRequestMethod(method);
//		urlConnection.setDoOutput(true);
//		urlConnection.setDoInput(true);
//		urlConnection.setUseCaches(true);
//		urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36");
//		
//		if (cookie.length() != 0) {
//			urlConnection.setRequestProperty("Cookie", cookie);
//		}
//		
//		int code = urlConnection.getResponseCode();
//		if(code == HttpURLConnection.HTTP_MOVED_TEMP) {
//            cookie += urlConnection.getHeaderField("Set-Cookie") + ";";
//        }
//		urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
//		urlConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		
		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}

		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
			urlConnection.setReadTimeout(500000);
		}

		return this.makeContent(urlString, urlConnection);
	}

	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return 响应对象
	 * @throws IOException
	 */
	private HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
		HttpRespons httpResponser = new HttpRespons();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, getDefaultContentEncoding()));
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			httpResponser.urlString = urlString;

			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();

			httpResponser.content = new String(temp.toString().getBytes());
			httpResponser.contentEncoding = defaultContentEncoding;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}

	/**
	 * 默认的响应字符集
	 */
	public String getDefaultContentEncoding() {
		return this.defaultContentEncoding;
	}

	/**
	 * 设置默认的响应字符集
	 */
	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}
}
