<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>air</title>
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
    <h3 class="user-info-header"><i class="avator"></i>个人信息设置</h3>
    <div class="input-box"><label for="registerName">乘机人姓名</label><input type="text" id="registerName" name="registerName"></div>
    <div class="input-box"><label for="registerPhone">身份证号码</label><input type="text" id="registerPhone" name="registerPhone"></div>
    <div class="input-box"><label for="registerPhone">联系电话</label><input type="text" id="registerPhone" name="registerPhone"></div>
  </form>
</section>

<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script>
    $(function () {
        $('#protocol-radio').change(function () {
            if($(this).prop("checked")==true){
                $(this).siblings('label').removeClass('cur');
            }else{
                $(this).siblings('label').addClass('cur');
            }
        })
        $('#baggage-checkbox').change(function () {
            if($(this).prop("checked")==true){
                $(this).siblings('label').removeClass('cur');
            }else{
                $(this).siblings('label').addClass('cur');
            }
        })
    })
</script>
</body>
</html>

