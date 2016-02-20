package com.xidian.weichat;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 * ΢��ͨ�ýӿ�ƾ֤
 * 
 * @author liufeng
 * @date 2013-08-08
 */

public class AccessToken {

	// ��ȡ����ƾ֤

	private String token;

	// ƾ֤��Чʱ�䣬��λ����

	private int expiresIn;

	public String getToken() {

		return token;

	}

	public void setToken(String token) {

		this.token = token;

	}

	public int getExpiresIn() {

		return expiresIn;

	}

	public void setExpiresIn(int expiresIn) {

		this.expiresIn = expiresIn;

	}
	
	private String APP_ID;
	private String APPSECRET;
	
	public String getAccess_token() {

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="

				+ APP_ID + "&secret=" + APPSECRET;

		String accessToken = null;

		try {

			URL urlGet = new URL(url);

			HttpURLConnection http = (HttpURLConnection) urlGet

			.openConnection();

			http.setRequestMethod("GET"); // ������get��ʽ����

			http.setRequestProperty("Content-Type",

			"application/x-www-form-urlencoded");

			http.setDoOutput(true);

			http.setDoInput(true);

			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// ���ӳ�ʱ30��

			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // ��ȡ��ʱ30��

			http.connect();

			InputStream is = http.getInputStream();

			int size = is.available();

			byte[] jsonBytes = new byte[size];

			is.read(jsonBytes);

			String message = new String(jsonBytes, "UTF-8");

			JSONObject demoJson = new JSONObject(message);

			accessToken = demoJson.getString("access_token");

			System.out.println(accessToken);

			is.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return accessToken;

	}

}