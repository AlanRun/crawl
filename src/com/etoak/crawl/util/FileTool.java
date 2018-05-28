package com.etoak.crawl.util;

import com.etoak.crawl.page.Page;
import com.etoak.crawl.page.TeamInfo;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/*  本类主要是 下载那些已经访问过的文件*/
public class FileTool {

	private static String dirPath;

	/**
	 * getMethod.getResponseHeader("Content-Type").getValue() 根据 URL
	 * 和网页类型生成需要保存的网页的文件名，去除 URL 中的非文件名字符
	 */
	private static String getFileNameByUrl(String url, String contentType) {
		// 去除 http://
		url = url.substring(7);
		// text/html 类型
		if (contentType.indexOf("html") != -1) {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
			return url;
		}
		// 如 application/pdf 类型
		else {
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	/*
	 * 生成目录
	 */
	private static void mkdir() {
		if (dirPath == null) {
			dirPath = Class.class.getClass().getResource("/").getPath() + "temp\\";
		}
		File fileDir = new File(dirPath);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
	}

//	/*
//	 * 生成目录
//	 */
//	private static void mkdir(String folderName) {
//		if (dirPath == null) {
//			dirPath = Class.class.getClass().getResource("/").getPath() + "temp\\" + folderName + "\\";
//		}
//		File fileDir = new File(dirPath);
//		if (!fileDir.exists()) {
//			fileDir.mkdir();
//		}
//	}

	/**
	 * 保存网页字节数组到本地文件，filePath 为要保存的文件的相对地址
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public static void saveToLocal(TeamInfo info) throws InterruptedException, IOException {

		// 构造URL
		if (info.getTeam_pic().equals("") || info.getTeam_pic() == null) {
			return;
		}
		
		URL url = new URL(info.getTeam_pic());
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(5 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		
		dirPath =  Class.class.getClass().getResource("/").getPath() + "temp/" + info.getCompetition_abbr_name() + "/";;
		
		File sf = new File(dirPath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		String fileName = info.getClub_name() + ".png";
		String filePath = dirPath + "/" +fileName;
		
		if (new File(filePath).exists()) {
			return;
		}
		
		OutputStream os = new FileOutputStream(filePath);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();

		System.out.println("文件：" + fileName + "已经被存储在" + filePath);
	}

	/**
	 * 保存网页字节数组到本地文件，filePath 为要保存的文件的相对地址
	 */

	public static void saveToLocal(Page page) {
		mkdir();
		String fileName = getFileNameByUrl(page.getUrl(), page.getContentType());
		String filePath = dirPath + fileName;
		byte[] data = page.getContent();
		try {
			// Files.lines(Paths.get("D:\\jd.txt"),
			// StandardCharsets.UTF_8).forEach(System.out::println);
			DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
			for (int i = 0; i < data.length; i++) {
				out.write(data[i]);
			}
			out.flush();
			out.close();
			System.out.println("文件：" + fileName + "已经被存储在" + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
