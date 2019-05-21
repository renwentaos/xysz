package com.xws.xysz.util;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.xws.xysz.config.SystemConfig;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @program: yunshuidi-PushUtil
 * @description: 极光推送
 * @author: rwt
 * @create: 2019-04-04 09:18
 **/

public class PushUtil {

    static JPushClient jpushClient = new JPushClient(SystemConfig.MASTER_SECRET, SystemConfig.PUSH_KEY, 3);
    /**
     * 个推
     * @param pushId
     * @param push
     */
    public static void push(String pushId,JSONObject push){
        try {
            PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android())//只给 用户推送
                    .setOptions(Options.newBuilder().setApnsProduction(SystemConfig.IS_TEST).build())//苹果端生产环境推送和开发环境修改参数
                    	.setAudience(Audience.registrationId(pushId))
//                    .setAudience(Audience.tag(pushuserid))
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder().setAlert(push.getString("description"))
                                    .setTitle(push.getString("title"))
                                    .addExtra("push", "")
                                    .addExtra("pushid", "")
                                    .addExtra("type", "0").build())
                            .addPlatformNotification(
                                    IosNotification.newBuilder()
                                            .addExtra("type", "0")
                                            .addExtra("push", "")
                                            .addExtra("pushid", "")
                                            .setAlert(push.getString("description"))
                                            .setBadge(0)
                                            .setSound("default")
                                            .build())
                            .build())
                    .build();

            PushResult result = jpushClient.sendPush(payload);
        } catch (Exception e1) {
            e1.printStackTrace();
            e1.getMessage();
        }

    }

    /**
     * 群推
     * @param aliasList
     * @param extras
     */
    public static void pushAll(List<String> aliasList, Map<String, String> extras) {
        //
        PushPayload payload = PushPayload
                .newBuilder()
                .setPlatform(Platform.android())  //指定平台
                .setAudience(Audience.alias(aliasList))    //用户指定
                .setNotification(
                        Notification.newBuilder()
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder().addExtras(extras).build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder().addExtras(extras).build())
                                .build())
                .setOptions(Options.newBuilder().setApnsProduction(SystemConfig.IS_TEST).build()).build();
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println("Got result - " + result);
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            System.out.print("Connection error, should retry later "+e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            System.out.println("根据返回的错误信息核查请求是否正确"+e);
            System.out.println("HTTP 状态信息码: " + e.getStatus());
            System.out.println("JPush返回的错误码: " + e.getErrorCode());
            System.out.println("JPush返回的错误信息: " + e.getErrorMessage());
        }
    }




    public static void main(String[] args) {
        JSONObject json_ = new JSONObject();
        json_.put("type","0");
        json_.put("title", "测试行游神州");
        json_.put("description", "贷款申请还有好多那测试111");
        json_.put("obj", "");
        json_.put("data", "");
        // 点击通知后的行为(1：打开Url; 2：自定义行为；);
//        json_.put("open_type", 0);
//        json_.put("url", "");
        push("120c83f7607b49c4606",json_);//极光推送
    }
}
