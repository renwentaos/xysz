package com.xws.xysz.exception;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

/**
 * 公用的runtime异常，如果抛出此异常无须捕获
 * Created by 杨光 on 2015/10/27.
 */
@Slf4j
public class ErrorException extends RuntimeException {

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ErrorException(String message, Throwable cause) {
        super(message, cause);
//        if(SystemConfig.IS_TEST){
//            String ip = SystemUtil.getLocalIP().substring(SystemUtil.getLocalIP().length()-3,SystemUtil.getLocalIP().length());
//            if(ip.equals("200")){
//                EMASend.sendErrorLog(SystemUtil.getLocalIP() + "-" + SystemConfig.workerId + "-" +message+"【币包】");
//            }
//        }else{
//            EMASend.sendErrorLog(SystemUtil.getLocalIP() + "-" + SystemConfig.workerId + "-" +message+"【币包】");
//        }
        log.error(message, cause);
    }


    public ErrorException(String message, Object...objects) {
        super(message);
        Gson gson = new Gson();
//        if(SystemConfig.IS_TEST){
//            String ip = SystemUtil.getLocalIP().substring(SystemUtil.getLocalIP().length()-3,SystemUtil.getLocalIP().length());
//            if(ip.equals("200")){
//                EMASend.sendErrorLog(SystemUtil.getLocalIP() + "-" + SystemConfig.workerId + "-" +message+"【币包】");
//            }
//        }else{
//            EMASend.sendErrorLog(SystemUtil.getLocalIP() + "-" + SystemConfig.workerId + "-" +message+"【币包】");
//        }
        StringBuilder sb = new StringBuilder("\r\n实体信息:");
        for (Object object : objects) {
            sb.append(gson.toJson(object));
            sb.append("\r\n");
        }
        log.error(message+sb.toString());
    }

    public ErrorException(String message, Object object, Throwable cause) {
        super(message);
//        if(SystemConfig.IS_TEST){
//            String ip = SystemUtil.getLocalIP().substring(SystemUtil.getLocalIP().length()-3,SystemUtil.getLocalIP().length());
//            if(ip.equals("200")){
//                EMASend.sendErrorLog(SystemUtil.getLocalIP() + "-" + SystemConfig.workerId + "-" +message+"【币包】");
//            }
//        }else{
//            EMASend.sendErrorLog(SystemUtil.getLocalIP() + "-" + SystemConfig.workerId + "-" +message+"【币包】");
//        }
        Gson gson = new Gson();
        log.error(message+"\r\n"+"实体信息:"+gson.toJson(object),cause);
    }


    /**
     * 检测行数是否合格，不合格抛出异常
     * @param row 要检查的行数
     * @param minRow 最小的行数（合格行数）
     * @param message 异常描述
     * @param object 异常实体
     */
    public static void rowCheck(int row,int minRow,String message,Object object){
        if(row < minRow){
            throw new ErrorException(message,object);
        }
    }

    /**
     * 检测行数是否达到一行以上，不合格抛出异常
     * @param row 要检查的行数
     * @param message 异常描述
     * @param object 异常实体
     */
    public static void oneRowCheck(int row,String message,Object object){
        rowCheck(row,1,message,object);
    }
}
