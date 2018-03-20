package com.cetc.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpClient {
	/**
	 * get请求
	 * 
	 * @param url
	 *            请求网址
	 * @param cookies
	 *            map形式的cookies
	 * @return
	 */
	public static Document get(String url, Map<String, String> cookies) {
		Document doc = null;
		try {
			// 随机延迟
			int i = (int) (Math.random() * 10.0D * 1000.0D);
			while (i != 0) {
				i--;
			}
			Connection con = Jsoup.connect(url);
			if (cookies != null) {
				con.cookies(cookies);
			}
			doc = con.data("query", "Java").userAgent("Mozilla").timeout(300000).get();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				doc = Jsoup.connect(url).timeout(5000000).get();
			} catch (IOException e1) {
				return doc;
			}
		}
		return doc;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求url
	 * @param cookies
	 * @param paras
	 *            post参数
	 * @return
	 */
	public static Document post(String url, Map<String, String> cookies, Map<String, String> paras) {
		Document doc = null;
		try {
			int i = (int) (Math.random() * 10.0D * 1000.0D);
			while (i != 0) {
				i--;
			}
			Connection con = Jsoup.connect(url);
			if (paras != null) {
				for (Map.Entry<String, String> entry : paras.entrySet()) {
					con.data((String) entry.getKey(), (String) entry.getValue());
				}
			}
			if (cookies != null) {
				con.cookies(cookies);
			}
			doc = con.data("query", "Java").userAgent("Mozilla").timeout(300000).post();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				doc = Jsoup.connect(url).timeout(5000000).get();
			} catch (IOException e1) {
				return doc;
			}
		}
		return doc;
	}
	/**
	 * 下载文件
	 * @param urlStr 下载地址
	 * @param fileName 文件名
	 * @param savePath 保存地址
	 * @return
	 * @throws IOException
	 */
	public static boolean download(String urlStr, String fileName, String savePath){
		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((url == null) || (url.equals(""))) {
			return false;
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		conn.setConnectTimeout(100000);

		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		// 将收到的输入流写入文件
		InputStream inputStream = null;
		try {
			inputStream = conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((inputStream == null) || (inputStream.equals(""))) {
			return false;
		}
		// 得到输入流
		byte[] getData = readInputStream(inputStream);
		
		// 如果文件不存在就创建文件
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		// 创建文件
		File file = new File(saveDir + File.separator + fileName);
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(getData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	/**
	 * 读取输入流
	 * @param inputStream
	 * @return
	 */
	public static byte[] readInputStream(InputStream inputStream){
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
}
