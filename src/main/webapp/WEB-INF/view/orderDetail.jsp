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
  <link rel="stylesheet" href="<%=basePath%>resources/css/orderList.css">
  <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body>
  <div class="wrapper">
    <section class="order-item">
     <p class="order-no0209"><span>订单编号: <em>201709093123</em></span><span>订单状态：<em class="orange">已支付</em></span></p>
      <div class="order-detail0209">
        <div class="detail-item0209">
          <em>收件人:</em><em>马化腾</em>
        </div>
        <div class="detail-item0209">
          <em>详细地址:</em><em>北京市海淀区北三环西路19号院xxxxx小区xxx号楼xxxx单元sssss说的是顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶</em>
        </div>
        <div class="detail-item0209">
          <em>预计送达时间:</em><em>2017-02-07 10:20</em>
        </div>
      </div>
      <p class="order-no0209 flex-p0209"><span>订单状态:<em class="orange">待支付状态</em></span><a href="javascript:;">查看详情></a></p>
    </section>
    <section class="order-item">
      <p class="order-no0209"><span>订单编号: <em>201709093123</em></span></p>
      <div class="order-detail0209">
        <div class="detail-item0209">
          <em>收件人:</em><em>马化腾</em>
        </div>
        <div class="detail-item0209">
          <em>详细地址:</em><em>北京市海淀区北三环西路19号院xxxxx小区xxx号楼xxxx单元sssss</em>
        </div>
        <div class="detail-item0209">
          <em>预计送达时间:</em><em>2017-02-07 10:20</em>
        </div>
      </div>
      <p class="order-no0209 flex-p0209"><span>订单状态:<em class="orange">待支付状态</em></span><a href="javascript:;">查看详情></a></p>
    </section>
    <section class="order-item">
     <p class="order-no0209"><span>订单编号: <em>201709093123</em></span></p>
      <div class="order-detail0209">
        <div class="detail-item0209">
          <em>收件人:</em><em>马化腾</em>
        </div>
        <div class="detail-item0209">
          <em>详细地址:</em><em>北京市海淀区北三环西路19号院xxxxx小区xxx号楼xxxx单元sssss</em>
        </div>
        <div class="detail-item0209">
          <em>预计送达时间:</em><em>2017-02-07 10:20</em>
        </div>
      </div>
      <p class="order-no0209 flex-p0209"><span>订单状态:<em class="orange">待支付状态</em></span><a href="javascript:;">查看详情></a></p>
    </section>
    <section class="order-item">
     <p class="order-no0209"><span>订单编号: <em>201709093123</em></span></p>
      <div class="order-detail0209">
        <div class="detail-item0209">
          <em>收件人:</em><em>马化腾</em>
        </div>
        <div class="detail-item0209">
          <em>详细地址:</em><em>北京市海淀区北三环西路19号院xxxxx小区xxx号楼xxxx单元sssss</em>
        </div>
        <div class="detail-item0209">
          <em>预计送达时间:</em><em>2017-02-07 10:20</em>
        </div>
      </div>
      <p class="order-no0209 flex-p0209"><span>订单状态:<em class="warn">未交行李</em></span><a href="javascript:;">查看详情></a></p>
    </section>
    <section class="order-item">
     <p class="order-no0209"><span>订单编号: <em>201709093123</em></span></p>
      <div class="order-detail0209">
        <div class="detail-item0209">
          <em>收件人:</em><em>马化腾</em>
        </div>
        <div class="detail-item0209">
          <em>详细地址:</em><em>北京市海淀区北三环西路19号院xxxxx小区xxx号楼xxxx单元sssss</em>
        </div>
        <div class="detail-item0209">
          <em>预计送达时间:</em><em>2017-02-07 10:20</em>
        </div>
      </div>
      <p class="order-no0209 flex-p0209"><span>订单状态:<em class="success">支付成功</em></span><a href="javascript:;">查看详情></a></p>
    </section>
  </div>
</body>
</html>

