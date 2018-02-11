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
  <h3 class="order-deal-header">新建收货地址</h3>
  <div class="input-box"><label for="name">收货人姓名</label><input type="text" id="name" name="name"></div>
  <div class="input-box"><label for="phone">联系电话</label><input type="text" id="phone" name="phone"></div>
  <p class="order-detail-tip">收货地址</p>
  <div class="order-address-select-box">
    <div class="order-address-select-item">
      <span>省</span>
      <ul>
        <li>省</li>
        <li>北京市</li>
      </ul>
    </div>
    <div class="order-address-select-item">
      <span>市</span>
      <ul>
        <li>市</li>
        <li>北京市</li>
      </ul>
    </div>
    <div class="order-address-select-item">
      <span>区</span>
      <ul>
        <li>区</li>
        <li>海淀区</li>
        <li>昌平区</li>
        <li>朝阳区</li>
      </ul>
    </div>
  </div>
  <div class="input-box"><label for="phone">详细地址</label><input type="text" id="address" name="address"></div>
  <p class="order-detail-tip">注：本业务目前只支持北京地区六环以内的配送</p>
  <a href="JavaScript:;" class="btn btn-lg">确认添加</a>
</section>
</body>
</html>