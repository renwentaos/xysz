<%--
  Created by IntelliJ IDEA.
  User: yangg_000
  Date: 2015/8/5
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ page import="com.qiniu.util.*" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="java.io.PrintWriter" %>
<%
  request.setCharacterEncoding("utf-8");
  response.setCharacterEncoding("utf-8");
  String upload_ret = request.getParameter("upload_ret");

  StringMap map = Json.decode(com.tkq.guanwang.util.Base64.decode(upload_ret));
  JsonObject jsonObject = new JsonObject();
  if(map.map().containsKey("error")){
    jsonObject.addProperty("state",map.get("error").toString());
  }else{
    jsonObject.addProperty("state","SUCCESS");
    jsonObject.addProperty("original",map.get("key").toString());
    jsonObject.addProperty("url",map.get("key").toString());

  }
  PrintWriter pw =  response.getWriter();
  pw.print(jsonObject.toString());
  pw.flush();
  pw.close();
%>
