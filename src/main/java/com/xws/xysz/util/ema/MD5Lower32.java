package com.xws.xysz.util.ema;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * @author  JasonLi
 * @date 2016年12月16 14:41:53 
 * @version 1.0 
 * @annotation MD5小写32位
 */
public class MD5Lower32 {
	public static String encryption(String plain) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0){
					i += 256;
				}
				if (i < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			re_md5 = buf.toString();
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
}
