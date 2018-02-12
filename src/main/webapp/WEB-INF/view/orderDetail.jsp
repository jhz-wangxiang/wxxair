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
  <title>订单详情</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"name="viewport" />
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="black" name="apple-mobile-web-app-status-bar-style" />
  <meta content="telephone=no" name="format-detection" />
  <link rel="stylesheet" href="<%=basePath%>resources/css/style.css">
  <link rel="stylesheet" href="<%=basePath%>resources/css/orderList.css">
  <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
  <script type="text/javascript" src="<%=basePath%>resources/js/common.js"></script>
</head>
<body>
<div class="order-body" style="padding: 0;" id="order-detail">
  <%--<section class="order-item">
    <p class="order-no0209" style="display: flex; display: -webkit-flex; justify-content: space-between;"><span>订单编号：<em>201709093123</em></span><span>订单状态：<em class="orange">已支付</em></span></p>
    <div class="order-detail0209">
      <p class="order-detail-tip">请前往XXXXX处提交行李小票，办理托运业务</p>
      <h3 class="order-deal-header">行李信息</h3>
      <div class="order-deal-box" style="border: 0;">
        <div class="order-deal-item"><span class="label">航班号：</span><span class="content">CA1987</span></div>
        <div class="order-deal-item"><span class="label">日期：</span><span class="content">2017.09.18</span></div>
        <div class="order-deal-item"><span class="label">行李数量：</span><span class="content">2</span></div>
        <div class="order-deal-item"><span class="tip">行李内无贵重、易碎、违禁物品</span></div>
      </div>
      <h3 class="order-deal-header">收货地址</h3>
      <div class="order-deal-box" style="border: 0; margin-bottom: 0;">
        <div class="order-deal-item"><span class="label">收件人：</span><span class="content">麻花藤</span></div>
        <div class="order-deal-item"><span class="label">联系方式：</span><span class="content">15645552321</span></div>
        <div class="order-deal-item"><span class="label">详细地址：</span><span class="content">北京市海淀区西三环北路紫金大厦19号5单元1111</span></div>
      </div>
    </div>
    <a href="javascript:;" class="btn btn-lg" style="margin-top: .2rem;margin-bottom: .5rem;">取消订单</a>
  </section>--%>
</div>
<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script>
    var basePath = "<%=basePath %>";
  $(function () {

      var id = Common.GetUrlRequest()['id'];
      $.ajax({
          url: basePath + 'v1/order/getOrderDetail',
          data: {"id":id},
          type: "POST",
          success: function (res) {
              alert(1);
              var order = res.order;
              var html = [];
              html.push('<section class="order-item">')
              html.push('<p class="order-no0209"><span>订单编号：<em>'+order.orderNo+'</em></span></p>')
              html.push('<p class="order-no0209"><span>订单状态：<em class="orange">'+order.describe+'</em></span></p>')
              html.push('<div class="order-detail0209">')
              html.push('<p class="order-detail-tip">请前往柜台处提交行李小票，办理托运业务</p>')
              html.push('<h3 class="order-deal-header">行李信息</h3>')
              html.push('<div class="order-deal-box" style="border: 0;">')
              html.push('<div class="order-deal-item"><span class="label">航班号：</span><span class="content">'+order.flightNum+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">日期：</span><span class="content">'+Common.getLocalDate(order.nowTime)+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">行李数量：</span><span class="content">'+order.baggageNum+'</span></div>')
              html.push('<div class="order-deal-item"><span class="tip">行李内无贵重、易碎、违禁物品</span></div>')
              html.push('</div>')
              html.push('<h3 class="order-deal-header">收货地址</h3>')
              html.push('<div class="order-deal-box" style="border: 0; margin-bottom: 0;">')
              html.push('<div class="order-deal-item"><span class="label">收件人：</span><span class="content">'+order.consignee+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">联系方式：</span><span class="content">'+order.consigneePhone+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">详细地址：</span><span class="content">'+order.province+''+order.city+''+order.area+''+order.address+'</span></div>')
              html.push('</div>')
              html.push('</div>')
              if(order.orderStatus == 1){
                html.push('<a href="javascript:;" class="btn btn-lg" style="margin-top: .2rem;margin-bottom: .5rem;" onclick="cancelOrder('+order.id+')">取消订单</a>');
              }
              html.push('</section>')
              $('#order-detail').html(html.join(''));
              alert(2);
          }
      })
  })
  function cancelOrder(id) {
      var html = [];
      html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><p style="font-size: .38rem; color: #3974bb; text-align: center;">确认取消订单</p></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm" style="width: 40%;display: inline-block; margin-right: .6rem;">确认</a><a href="JavaScript:;" class="btn btn-nm" style="width: 40%;display: inline-block; background: #999999;">取消</a></div></div></div>')
      $('body').append(html.join(''));
      $('.modal-footer0211>a').eq(0).click(function () {
          $.ajax({
              url: basePath + 'v1/order/updateOrderCancel',
              data: {"id": id},
              type: "POST",
              success: function (res) {
                  if (res.resultCode == "SUCCESS") {
                      $('#modal').remove();
                      cancelSuccess();
                  }else{
                    Common.alter(res.msg);
                  }
              }
          });
      });
      $('.modal-footer0211>a').eq(1).click(function () {
          $('#modal').remove();
      });
  }
  function cancelSuccess() {
      var html = [];
      html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><div class="order-info-box order-cancle"><img src="'+basePath+'resources/image/order-cancle.png" alt=""><p class="order-info-title">您的订单已取消！</p></div></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm">确认</a></div></div></div>')
      $('body').append(html.join(''));
      $('.modal-footer0211>a').eq(0).click(function () {
          $('#modal').remove();
          window.location.href = "<%=basePath %>v1/page/orderList"
      })
  }
</script>
</body>
</html>

