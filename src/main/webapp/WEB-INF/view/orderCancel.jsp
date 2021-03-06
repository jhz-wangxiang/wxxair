<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <link rel="stylesheet" href="<%=basePath%>resources/css/style.css">
  <link rel="stylesheet" href="<%=basePath%>resources/css/orderList.css">
  <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body>
<section class="order-body">
  <div class="order-info-box order-cancle">
    <img src="<%=basePath%>resources/image/order-cancle.png" alt="">
    <p class="order-info-title">您的订单已取消！</p>
    <p class="order-info-warning" style="padding: 0 .4rem .8rem;">您支付的金额将原路返回您支付订单时所用的卡中，需要15个工作日</p>
  </div>
  <a href="javascript:;" class="btn btn-lg">确定</a>
  <!--<a href="javascript:;" class="btn btn-lg" style="background: #009900;">微信支付</a>-->
</section>
</body>
</html>