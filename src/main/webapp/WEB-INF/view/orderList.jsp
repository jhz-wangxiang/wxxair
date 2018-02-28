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
  <title>订单列表</title>
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
      var basePath = "<%=basePath %>";
      $(function () {
          $.ajax({
              url: basePath + 'v1/order/micro/getOrderList',
              type: "POST",
              success: function (res) {
                  console.log(res);
                  var html = [];
                  if(res.resultCode == 'SUCCESS'){
                      if(!res.orderList){
                          html.push('<p style="font-size:.2rem; color:#666; text-align:center; padding:10px; margin-top:3rem">您还没有订单，请点击按钮新建订单</p>');
                          html.push('<div class="step-box" style="justify-content:center"><a href="'+basePath+'v1/page/orderStepOne" class="btn btn-nm" style="width:2rem">新建订单</a></div>')
                      }else{
                          for (var i = 0; i<res.orderList.length; i++){
                              var _item = res.orderList[i];
                              html.push('<section class="order-item"><p class="order-no0209"><span>订单编号: <em>'+_item.orderNo+'</em></span></p><div class="order-detail0209"><div class="detail-item0209"><em>收件人:</em><em>'+_item.consignee+'</em></div><div class="detail-item0209"><em>详细地址:</em><em>'+_item.province+''+_item.city+''+_item.area+''+_item.address+'</em></div></div>')
                              switch (_item.orderStatus){
                                  case 10:
                                      html.push('<p class="order-no0209 flex-p0209"><span>订单状态:<em class="cancel">'+_item.button+'</em></span><a href="<%=basePath %>v1/page/orderDetail?id='+_item.id+'">查看详情></a></p>')
                                      break;
                                  case 6:
                                      html.push('<p class="order-no0209 flex-p0209"><span>订单状态:<em class="success">'+_item.button+'</em></span><a href="<%=basePath %>v1/page/orderDetail?id='+_item.id+'">查看详情></a></p>')
                                      break;
                                  case 1:
                                      html.push('<p class="order-no0209 flex-p0209"><span>订单状态:<em class="orange">'+_item.button+'</em></span><a href="javascript:;" class="btn btn-xs" style="color: #ffffff;height: .4rem;line-height: .4rem;padding: .06rem .2rem;" onclick="pay(\''+_item.orderNo+'\')">支付</a><a href="<%=basePath %>v1/page/orderDetail?id='+_item.id+'">查看详情></a></p>');
                                      break;
                                  default:
                                      html.push('<p class="order-no0209 flex-p0209"><span>订单状态:<em class="orange">'+_item.button+'</em></span><a href="<%=basePath %>v1/page/orderDetail?id='+_item.id+'">查看详情></a></p>');
                              }
                              html.push('</section>')
                              //<div class="detail-item0209"><em>预计送达时间:</em><em>2017-02-07 10:20</em></div>
                          }
                      }
                      $('#order-list').html(html.join(''));
                  }else{
                      Common.alter(res.msg);
                  }
              }
          })
      })
      //支付按钮事件
      function pay(orderNo){
          /* appId:wxcf9a42f63c23a637,timeStamp:1449319601,nonceStr:20151205236247,package:prepay_id=,signType:MD5,paySign:7579772D56139215721D5CB803A3DA22,orderNum:1245124521*/
          var appId = null;
          var timeStamp = null;
          var nonceStr = null;
          var package_var = null;
          var signType = null;
          var paySign = null;
          var orderNum = null;
          $.ajax({
              async:true,
              type : "POST",
              url: basePath+'/v1/page/getPackage?orderNo='+orderNo,
              dataType:'text',
              success: function(msg){
                  var arr = msg.split(",");
                  appId = arr[0].split(":")[1];
                  timeStamp = arr[1].split(":")[1];
                  nonceStr = arr[2].split(":")[1];
                  package_var = arr[3].split(":")[1];
                  signType = arr[4].split(":")[1];
                  paySign = arr[5].split(":")[1];
                  orderNum = arr[6].split(":")[1];
                  if (typeof WeixinJSBridge == "undefined"){
                      if( document.addEventListener ){
                          document.addEventListener('WeixinJSBridgeReady', onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum), false);
                      }else if (document.attachEvent){
                          document.attachEvent('WeixinJSBridgeReady', onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum));
                          document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum));
                      }
                  }else{
                      onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum);
                  }
              }
          });
      }
      function onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum){
          WeixinJSBridge.invoke(
              'getBrandWCPayRequest', {
                  "appId" : appId,     //公众号名称，由商户传入
                  "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数
                  "nonceStr" : nonceStr, //随机串
                  "package" : package_var,
                  "signType" :signType,         //微信签名方式：
                  "paySign" : paySign //微信签名
              },
              function(res){
                  if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                      paySuccess();
                  }else if(res.err_msg === 'get_brand_wcpay_request:cancel'){
                      window.location.href = basePath+ "v1/page/orderList";
                  } else{
                  }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
              }
          );
      }
      function paySuccess() {
          var html = [];
          html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><div class="order-info-box order-success"><img src="../../resources/image/order-success.png" alt=""><p class="order-info-title">恭喜您已支付成功！</p><p class="order-info-warning">但流程未完</p><p class="order-info-tip">下机后请前往行李送到家柜台，递交行李小票</p></div></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm">确认</a></div></div></div>')
          $('body').append(html.join(''));
          $('.modal-footer0211>a').eq(0).click(function () {
              $('#modal').remove();
              window.location.href = basePath+ "v1/page/orderList"
          })
      }
  </script>
</body>
</html>

