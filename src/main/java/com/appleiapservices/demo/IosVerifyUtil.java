package com.appleiapservices.demo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 苹果IAP内购验证工具类
 * @ClassName: IosVerify
 * @Description:Apple Pay
 */
public class IosVerifyUtil {

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static final String url_sandbox = "https://sandbox.itunes.apple.com/verifyReceipt";
    private static final String url_verify = "https://buy.itunes.apple.com/verifyReceipt";

    /**
     * 苹果服务器验证
     *
     * @param receipt
     *            账单
     * @url 要验证的地址
     * @return null 或返回结果 沙盒 https://sandbox.itunes.apple.com/verifyReceipt
     *
     */
    public static String buyAppVerify(String receipt,int type) {
        //环境判断 线上/开发环境用不同的请求链接
        String url = "";
        if(type==0){
            url = url_sandbox; //沙盒测试
        }else{
            url = url_verify; //线上测试
        }
        //String url = EnvUtils.isOnline() ?url_verify : url_sandbox;

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-type", "text/json");
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            BufferedOutputStream hurlBufOus = new BufferedOutputStream(conn.getOutputStream());

            // 内购需要带上password,在appstore中获取共享密钥，地址在这里：https://appstoreconnect.apple.com/apps/1483566411/appstore/addons?m=
            String str = String.format(Locale.CHINA,"{\"receipt-data\":\"" + receipt + "\",\"password\""+":"+"\"726be3fda0c84fcb8d8792344a1e53c2\"}");//拼成固定的格式传给平台

            hurlBufOus.write(str.getBytes());
            hurlBufOus.flush();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (Exception ex) {
            System.out.println("苹果服务器异常");
            ex.printStackTrace();
        }
        return null;
    }

    public static String createRandomStr(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }

    public static String GenGid(String randStr) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(randStr.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static int sendPost(String to,String gid ,int amount) throws Exception {
        String url = "http://3.81.161.170:8787/tx";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //添加请求头
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        String urlParameters = "{\"data\": {" +
                "\"to\": \"" + to + "\"," +
                " \"amount\": " + amount + "," +
                "\"gid\": \"" + gid + "\"}}";

        //发送Post请求
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //打印结果

        System.out.println(responseCode);
        System.out.println(response.toString());
        return responseCode;
    }

}