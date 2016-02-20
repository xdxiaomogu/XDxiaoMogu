package com.xidian.flow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by WJ on 2015/7/23.
 */
@Service("ocrkingUtility")
public class OcrkingUtility {

    public static String parseFileLocal(File file){

        try {

            String text = LocalOcr.getAllOcr(Constans.IMAGE_FILE_NAME);
            System.out.println(text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseFile(File file, String url){

        // the url below can not be changed !!
        String apiUrl = Constans.OcrKingUrl;
        // replace the word KEY below with your apiKey obtained by Email
        String apiKey = Constans.API_KEY;
        // you need to specify the full path of image you wanna OCR
        String filePath= Constans.IMAGE_FILE_NAME;

        Map<String, String> dataMap = new HashMap<String, String>();

        // you need to modify parameters according to OcrKing Api Document
        dataMap.put("service", "OcrKingForCaptcha");
        dataMap.put("language", "eng");
        dataMap.put("charset", "1");
        dataMap.put("type", url);
        // you need to modify parameters according to OcrKing Api Document

        dataMap.put("apiKey", apiKey);
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("ocrfile", filePath);
        String ret = postMultipart(apiUrl, dataMap, fileMap);
        //System.out.println(ret);

        Document document = Jsoup.parse(ret);
        String result = document.getElementsByTag("result").get(0).text();
        String status = document.getElementsByTag("Status").get(0).text();
        if(status.equals("true")){
            System.out.println("result = " + result);
            return result;
        }else{
            return null;
        }

    }


    /**
     * post data with file uploading
     * @param urlStr  the address to upload
     * @param dataMap post data
     * @param fileMap file to upload
     * @return xml result
     */
    public static String postMultipart(String urlStr, Map<String, String> dataMap, Map<String, String> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "----------------------------OcrKing_Client_Aven_s_Lab";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Referer", "http://lab.ocrking.com/?javaclient0.1)");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // data
            if (dataMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = dataMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:application/octet-stream\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // handle the response
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("error " + urlStr);
            //e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }


}
