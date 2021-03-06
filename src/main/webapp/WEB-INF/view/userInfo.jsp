<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.text.*"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
  <title>用户信息</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"name="viewport" />
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="black" name="apple-mobile-web-app-status-bar-style" />
  <meta content="telephone=no" name="format-detection" />
  <link rel="stylesheet" href="<%=basePath%>resources/css/style.css">
  <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body>
<section class="order-body">
  <form action="">
  	<div class="vspace"></div>
    <h3 class="user-info-header">个人信息设置</h3>
    <div class="vspace"></div>
    <div class="input-box"><label for="name">乘机人姓名</label><input type="text" id="name" value="${name}" name="name"></div>
   	<div class="input-box"><label for="phone">联系电话</label><input type="text" id="phone" value="${phone}" name="phone"></div>
	<div class="user-info-address"><p class="">如您需编辑常用地址，请点击按钮前往</p><a href="<%=basePath%>v1/page/orderAddressList" class="btn btn-xs" style="padding:5px">地址编辑</a></div>
  	<a class="btn btn-lg" >确认</a>
  </form>
</section>

<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
<script>
	var basePath = "<%=basePath%>";
    $(".btn-lg").click(function(){
    	var n = Common.ltrim($("input[name='name']").val());
    	var p = Common.ltrim($("input[name='phone']").val());
    	if(Common.isNull(n)){
			Common.alter('请填写乘客姓名！');
			return false;
		}
		if(Common.isNull(p)){
			Common.alter('请填写乘客手机号码！');
			return false;
		}
		$.ajax({
			url:basePath+'v1/user/insertUser?'+Math.random(),
			type:"POST",
			data:{name:n,phone:p},
			success:function(json){
				if(json.resultCode=="SUCCESS"){
					Common.alter(json.msg);
				}
			}
		})
    });
</script>
</body>
</html>

