package com.xws.xysz.util.ema;



import com.xws.xysz.config.SystemConfig;
import com.xws.xysz.enums.SMSEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @author  JasonLi
 * @date 2016年12月16日 14:23:40 
 * @version 1.0 
 * @annotation 短信
 */
public class EMASend {
	
	/**
	 * 发送状态码
	 */
	public final static String success = "100"; //发送成功
	public final static String failV = "101"; //验证失败
	public final static String insufficient = "102"; //短信不足
	public final static String failO = "103"; //操作失败
	public final static String illegalChar = "104"; //非法字符
	public final static String contentMore = "105"; //内容过多
	public final static String mobileMore = "106"; //号码过多
	public final static String fast = "107"; //频率过快
	public final static String contentIsNull = "108"; //号码内容为空（用了UTF-8，不支持，改成GBK即可）
	public final static String freeze = "109"; //账号冻结
	public final static String ontFast = "110"; //禁止频繁单条发送
	public final static String suspended = "111"; //系统暂定发送
	public final static String sonMobileFail = "112"; //子号不正确
	public final static String illegal = "120"; //非法信息拒绝验证码炸弹或签名黑名单或双签名
	
	//营销
//	private String uid = "600687";
//	private String pwd = "yuche123";
//	private final static String url = "http://202.85.221.42:9885/c123/sendsms";
	
	/**
	 * 行业 
	 */
	private final static String url = "http://202.85.221.42:9885/c123";
	
	/**
	 * url key
	 */
	private final static String send = "/sendsms"; //发送
	private final static String recv = "/recvsms"; //接收回复
	private final static String statu = "/statusreport"; //获取状态报告
	private final static String queryMoney = "/querymoney"; //查询余额


	/**
	 * 发送
	 * @param mobile
	 * @return
	 */
	public static String sendSms(String mobile,String captcha,String type,String merchantName,String repayDate) {
		String content="";
		if(type.equals(SMSEnum.SMS.注册.getCode())){
			content="【行游神州】验证码 "+captcha+"，您正在注册成为新用户，请妥善保管账户信息，请勿向他人泄露，感谢您的支持！";
		}else if(type.equals(SMSEnum.SMS.找回密码.getCode())){
			content="【行游神州】验证码 "+captcha+"，您正在尝试修改登录密码，请妥善保管账户信息，请勿向他人泄露。如非本人操作，请忽略。";
		}else if(type.equals(SMSEnum.SMS.额度批复.getCode())){
			content="【"+merchantName+"】恭喜您，您提交的资料已经审核通过。";
		}else if(type.equals(SMSEnum.SMS.放款.getCode())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());
			String year = date.substring(0,4);
			String month = date.substring(5,7);
			String day = date.substring(8,10);
			content="【"+merchantName+"】您申请的一笔金额为人民币"+captcha+"元已于"+year+"年"+month+"月"+day+"日成功转账至您的预留银行账户，请注意查看您的账户资金变动情况";
		}else if(type.equals(SMSEnum.SMS.还款.getCode())){
			content="【"+merchantName+"】还款：尊敬的用户，您办理的业务已于"+repayDate+"到期，请登录APP及时处理。如已还款请忽略，回T退订 ";
		}else if(type.equals(SMSEnum.SMS.拒批额度.getCode())){
			content="【行游神州】温馨提示：您在水滴分期提交的资料审核未通过，但我们会不定期为您推送其他产品，请您随时关注行游神州APP动态。";
		}else if(type.equals(SMSEnum.SMS.推新.getCode())){
			content="【行游神州】温馨提示：有新的产品可供您申请额度，请打开行游神州APP查看。";
		}
		StringBuilder sb = new StringBuilder();
			sb.append("uid=" + SystemConfig.UID + "&pwd=" + MD5Lower32.encryption(SystemConfig.PWD)+ "&mobile=");
			sb.append(mobile);
			sb.append("&content=");
		try {
			sb.append(URLEncoder.encode(content,"GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return HttpSend.postSend(url+send, sb.toString());
	}

//	public static void main(String[] args) {
//		System.out.println(sendSms("18803881577","1500",SMSEnum.SMS.额度批复.getCode(),"金钱豹",null));
//	}
}
