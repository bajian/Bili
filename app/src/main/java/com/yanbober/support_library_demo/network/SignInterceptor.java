package com.yanbober.support_library_demo.network;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.yanbober.support_library_demo.persistence.Secret;
import com.yanbober.support_library_demo.utils.UrlUtil;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * BILI SIGN
 * Created by Administrator on 2015/10/16.
 */
public class SignInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request != null) {
            URL requestURL = request.url();
            if (requestURL.getHost().contains("www.bilibili.com")) {
                return chain.proceed(request);
            }

            String baseURL = requestURL.getProtocol() + "://" + requestURL.getHost() + requestURL.getPath();
            Log.d("SignInterceptor", "baseURL=" + baseURL);
            Map<String, String> queryMap = UrlUtil.splitQuery(requestURL);
            String paraUri = UrlUtil.getParaUriNoSigned(queryMap);
            paraUri += "&sign=" + getSign(paraUri);

            Request.Builder signedRequestBuilder = request.newBuilder();
            signedRequestBuilder.url(baseURL + "?" + paraUri);
            request = signedRequestBuilder.build();
        }
        return chain.proceed(request);
    }


    /**
     * 获取签名串
     */
    private static String getSign(String paraUri) {

        MessageDigest md = null;
        paraUri += Secret.App_Secret;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        byte[] Md5 = md.digest(paraUri.getBytes());
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : Md5) {
            int bt = b & 0xff;
            if (bt < 16) {
                stringBuffer.append(0);
            }
            stringBuffer.append(Integer.toHexString(bt));
        }
        String sign = stringBuffer.toString();
        Log.d("getsign", "sign=" + sign + ",paraUri=" + paraUri);
        return sign;
    }
}
