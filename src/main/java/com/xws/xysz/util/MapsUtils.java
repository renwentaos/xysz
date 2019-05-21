package com.xws.xysz.util;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.xws.xysz.exception.WarnException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @Author rwt
 * @Date 2018/11/23 13:46
 **/
public class MapsUtils {
    static String baiduKey = "ZWUUkyZvPGxkTapaaQXbq6EL2yEhZLWD";
    static String gaodeKey = "180704c357c493c23855332230bc1ba2";
    /**
     * @param
     * @return
     * @throws IOException
     */
    public static Map<String,String> getCity(String lat,String lng)throws WarnException {

//        String key = "ZWUUkyZvPGxkTapaaQXbq6EL2yEhZLWD";
        String url = "http://api.map.baidu.com/geocoder/v2/?ak="+baiduKey+"&location="+lat+","+lng+"&output=json&pois=2";
        String result = null;
        try {
            result = HttpUtil.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        HashMap<String, Object>  map = gson.fromJson(result,HashMap.class);
        String status = map.get("status").toString();
        if(!status.equals("0.0")){
           throw new WarnException("地理位置获取错误");
        }
        LinkedTreeMap<String, Object> addressComponent = (LinkedTreeMap) map.get("result");
        LinkedTreeMap<String, Object> lt = (LinkedTreeMap)addressComponent.get("addressComponent");
        Map<String,String> newMap = new HashMap<>();
        if(StringUtils.isBlank(lt.get("province").toString())  || StringUtils.isBlank(lt.get("city").toString())){
            throw new WarnException("地理位置获取错误");
        }
        newMap.put("province",lt.get("province").toString());
        newMap.put("city",lt.get("city").toString());
        return newMap;

    }

    private static Map<String,String> getCityByBaidu(String ip)throws WarnException{
        String url = "http://api.map.baidu.com/location/ip?ip="+ip+"&ak="+baiduKey;
        String result = null;
        try {
            result = HttpUtil.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        HashMap<String, Object>  map = gson.fromJson(result,HashMap.class);
        String status = map.get("status").toString();
        if(!status.equals("0.0")){
            throw new WarnException("地理位置获取错误");
        }
        LinkedTreeMap<String, Object> addressComponent = (LinkedTreeMap) map.get("content");
        LinkedTreeMap<String, Object> lt = (LinkedTreeMap)addressComponent.get("address_detail");
        System.out.println(lt);
        Map<String,String> newMap = new HashMap<>();
        if(StringUtils.isBlank(lt.get("province").toString())  || StringUtils.isBlank(lt.get("city").toString())){
            throw new WarnException("地理位置获取错误");
        }
        newMap.put("province",lt.get("province").toString());
        newMap.put("city",lt.get("city").toString());
        return newMap;
    }

    private static Map<String,String> getCityByGaode(String ip)throws WarnException{
        String url = "https://restapi.amap.com/v3/ip?ip="+ip+"&key="+gaodeKey;
        String result = null;
        try {
            result = HttpUtil.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
        Gson gson = new Gson();
        HashMap<String, Object>  map = gson.fromJson(result,HashMap.class);
        String status = map.get("status").toString();
        if(!status.equals("1")){
            throw new WarnException("地理位置获取错误");
        }
        Map<String,String> newMap = new HashMap<>();
        if(StringUtils.isBlank(map.get("province").toString())  || StringUtils.isBlank(map.get("city").toString())){
            throw new WarnException("地理位置获取错误");
        }
        newMap.put("province",map.get("province").toString());
        newMap.put("city",map.get("city").toString());
        return newMap;
    }


    public static Map<String,String> getCityBy(String ip)throws WarnException{
        return getCityByGaode(ip);
    }

    public static void main(String[] args) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String aa = "2018-11-29 10:47:00";
//        try {
//            Date date = sdf.parse(aa);
//            long bb = date.getTime();
//            long cc = System.currentTimeMillis();
//            long dd = 24*60*60*1000;
//            double ee = (double)(cc - bb)/dd;
//
////            System.out.println(ee.getClass().getName());
//            int nn = (int)ee;
//            System.out.println("nn"+nn);
//            int ff = 28;
//            if(ff>ee){
//                System.out.println("ee="+ee);
//            }else {
//                System.out.println("ff="+ff);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String url = "http://lundroid.market.alicloudapi.com/lianzhuo/verifi" +
//                "?acct_name=任文涛&acct_pan=6210812430011156169&cert_id=411424199205146216&phone_num=18803881577&AppCode=02f8bb13aef6495bb1edd1255299d9a8";

        try {
            getCityBy("123.13.246.43");
        } catch (WarnException e) {
            e.printStackTrace();
        }

    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
//            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
//            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
//            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
//            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
//            System.out.println("getRemoteAddr ip: " + ip);
        }
//        System.out.println("获取客户端ip: " + ip);
        return ip;
    }

}
