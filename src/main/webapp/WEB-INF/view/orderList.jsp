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
  <div class="wrapper" id="order-list"></div>
  <script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
  <script>
      $(function () {
          var basePath = "<%=basePath %>";
          $.ajax({
              url: basePath + 'v1/order/micro/getOrderList',
              type: "POST",
              success: function (res) {
                  console.log(res);
                  if(res.resultCode == 'SUCCESS'){
                      var html = [];
                      for (var i = 0; i<res.orderList.length; i++){
                          var _item = res.orderList[i];
                          html.push('<section class="order-item"><p class="order-no0209"><span>订单编号: <em>'+_item.orderNo+'</em></span></p><div class="order-detail0209"><div class="detail-item0209"><em>收件人:</em><em>'+_item.consignee+'</em></div><div class="detail-item0209"><em>详细地址:</em><em>'+_item.province+''+_item.city+''+_item.area+''+_item.address+'</em></div></div><p class="order-no0209 flex-p0209"><span>订单状态:<em class="orange">'+_item.describe+'</em></span><a href="<%=basePath %>v1/page/orderDetail?id='+_item.id+'">查看详情></a></p></section>')
                          //<div class="detail-item0209"><em>预计送达时间:</em><em>2017-02-07 10:20</em></div>
                      }
                      $('#order-list').html(html.join(''));
                  }else{
                      Common.alter(res.msg);
                  }
              }
          })
      })

  </script>
</body>
</html>

