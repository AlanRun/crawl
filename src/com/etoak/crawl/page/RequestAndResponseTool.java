package com.etoak.crawl.page;

import com.etoak.crawl.util.HttpRespons;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class RequestAndResponseTool {

	public static TeamInfo getTeamImg(String url) throws Exception {
		String defUrl = "http://i.sporttery.cn/api/fb_match_info/get_team_data/?f_callback=footb_info&tid=";
		Date d = new Date();
		TeamInfo info = new TeamInfo();

		if (url.contains("tid=")) {
			url = defUrl + url.split("=")[1] + "&_=" + d.getTime();
		} else {
			return null;
		}
		
		HttpRequester request = new HttpRequester();
		request.setDefaultContentEncoding("gbk");
		HttpRespons hr = request.sendGet(url);
		String json = decodeUnicode(hr.getContent());
		json = json.trim();
		json = json.substring(11, json.length() -2);
		
		JSONObject obj = JSONObject.fromObject(json);
		
		JSONObject result = obj.getJSONObject("result");
		String competition_abbr_name = result.getString("competition_abbr_name");
		String club_name = result.getString("club_name");
		String team_pic = result.getString("team_pic");

		info.setClub_name(club_name);
		info.setCompetition_abbr_name(competition_abbr_name);
		info.setTeam_pic(team_pic);
		
		return info;
	}

	/**
	 * unicode 转换成 中文
	 * 
	 * @author leon 2016-3-15
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	public static Page sendRequstAndGetResponse(String url) throws InterruptedException {
		Page page = null;
		// 1.生成 HttpClinet 对象并设置参数
		HttpClient httpClient = new HttpClient();
		// 设置 HTTP 连接超时 5s
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		// 2.生成 GetMethod 对象并设置参数
		GetMethod getMethod = new GetMethod(url);
		// 设置 get 请求超时 5s
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 3.执行 HTTP GET 请求
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			// httpClient.wait(2000);
			// 判断访问的状态码
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			// 4.处理 HTTP 响应内容
			byte[] responseBody = getMethod.getResponseBody();// 读取为字节 数组
			String contentType = getMethod.getResponseHeader("Content-Type").getValue(); // 得到当前返回类型
			page = new Page(responseBody, url, contentType); // 封装成为页面
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
		return page;
	}
}
