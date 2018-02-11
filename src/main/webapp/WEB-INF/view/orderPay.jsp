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
  <div class="order-info-box order-success">
    <img src="../../resources/image/order-success.png" alt="">
    <p class="order-info-title">恭喜您已下单成功！</p>
    <p class="order-info-warning">但流程未完</p>
    <p class="order-info-tip">您需要抵达机场后，将行李小票在XXXX位置行李柜台递交给工作人员，并支付费用</p>
  </div>
  <div class="fee-box"><span class="label">服务费：</span><span class="content">40元</span></div>
  <a href="javascript:;" class="btn btn-lg" style="background: #009900;">确定</a>
  <!--<a href="javascript:;" class="btn btn-lg" style="background: #009900;">微信支付</a>-->
</section>
</body>
</html>