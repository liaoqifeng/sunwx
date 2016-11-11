<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.gson.MenuSet"%>
<%
	Boolean rs = MenuSet.createMenu();
	if(rs){
		out.print("菜单重置成功");
	}else{
		out.print("菜单重置失败");
	}
%>
