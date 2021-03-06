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
    var id = Common.GetUrlRequest()['id'];
  $(function () {
      $.ajax({
          url: basePath + 'v1/order/getOrderDetail',
          data: {"id":id},
          type: "POST",
          success: function (res) {
              var order = res.order;
              var html = [];
              html.push('<section class="order-item" style="padding-bottom: .2rem;">')
              html.push('<p class="order-no0209"><span>订单编号：<em>'+order.orderNo+'</em></span></p>')
              html.push('<p class="order-no0209"><span>订单状态：<em class="orange">'+order.button+'</em></span></p>')
              html.push('<p class="order-no0209"><span>服务费：<em>'+order.paidFee+'元</em></span></p>')
              html.push('<div class="order-detail0209">')
              html.push('<p class="order-detail-tip">下机后请前往<a href="http://mp.weixin.qq.com/mp/homepage?__biz=MzIxNzcwMTkwNw==&hid=3&sn=688a92d3760081181b4e96e892191341#wechat_redirect" style="color: #3974bb">行李送到家柜台</a>，递交<a href="https://mp.weixin.qq.com/mp/homepage?__biz=MzIxNzcwMTkwNw==&hid=4&sn=0c8580fcebe0b96ee422e4ba2bf43239&uin=&key=&devicetype=Windows+10&version=6206014b&lang=zh_CN&ascene=1&winzoom=1" style="color: #3974bb">行李小票</a></p>')
              html.push('<h3 class="order-deal-header">行李信息</h3>')
              html.push('<div class="order-deal-box" style="border: 0;">')
              html.push('<div class="order-deal-item"><span class="label">航班号：</span><span class="content">'+order.flightNum+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">日期：</span><span class="content">'+Common.getLocalDate(order.nowTime)+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">行李数量：</span><span class="content">'+order.baggageNum+'</span></div>')
              html.push('<div class="order-deal-item"><span class="tip">行李内无贵重、易碎、违禁物品</span></div>')
              html.push('</div>')

              html.push('<h3 class="order-deal-header">乘机人信息</h3>')
              html.push('<div class="order-deal-box" style="border: 0;">')
              html.push('<div class="order-deal-item"><span class="label">姓名：</span><span class="content">'+order.name+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">联系方式：</span><span class="content">'+order.phone+'</span></div>')
              html.push('</div>')

              html.push('<h3 class="order-deal-header">收货地址</h3>')
              html.push('<div class="order-deal-box" style="border: 0; margin-bottom: 0;">')
              html.push('<div class="order-deal-item"><span class="label">收件人：</span><span class="content">'+order.consignee+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">联系方式：</span><span class="content">'+order.consigneePhone+'</span></div>')
              html.push('<div class="order-deal-item"><span class="label">详细地址：</span><span class="content">'+order.province+''+order.city+''+order.area+''+order.address+'</span></div>')
              html.push('</div>')
              html.push('</div>')
              if(order.orderStatus == 1){
                  html.push('<div style="display: flex; display: -webkit-flex; margin: .2rem 0;"><a href="<%=basePath %>v1/page/orderUpdate?orderId='+order.id+'" class="btn btn-lg" style="flex:1; margin-right: .2rem;">修改信息</a><a href="javascript:;" class="btn btn-lg" style="flex:1; background: #999999;" onclick="cancelOrder('+order.id+')">取消订单</a></div>')
                  html.push('<a href="javascript:;" class="btn btn-lg" style="background: #009900;" onclick="pay(\''+order.orderNo+'\')">支付</a>');
                html.push('');
              }else if(order.orderStatus == 2){
                  html.push('<a href="javascript:;" class="btn btn-lg" style="flex:1; background: #999999;" onclick="cancelOrder('+order.id+',\''+order.paidFee+'\')">取消订单</a></div>')
              }
              html.push('</section>')
              $('#order-detail').html(html.join(''));
          }
      })
  })
  function cancelOrder(id,payFee) {
      var html = [];
      html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><p style="font-size: .38rem; color: #3974bb; text-align: center;">确认取消订单</p>');
      if(payFee){
          html.push('<p>退款金额：'+payFee+'元(退款稍后退到你的微信帐号中)</p>');
      }
      html.push('</div><div class="modal-footer0211" style="margin-top: 0;"><a href="JavaScript:;" class="btn btn-nm" style="width: 40%;display: inline-block; margin-right: .6rem;">确认</a><a href="JavaScript:;" class="btn btn-nm" style="width: 40%;display: inline-block; background: #999999;">取消</a></div></div></div>')
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
                    window.location.reload();
                } else{
                }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            }
        );
    }
    function paySuccess() {
        var html = [];
        html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><div class="order-info-box order-success"><img src="../../resources/image/order-success.png" alt=""><p class="order-info-title">支付成功！</p><p class="order-info-warning">但流程未完</p><p class="order-info-tip order-info-warning">下机后请前往行李送到家柜台，递交行李小票</p></div></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm">确认</a></div></div></div>')
        $('body').append(html.join(''));
        $('.modal-footer0211>a').eq(0).click(function () {
            $('#modal').remove();
            window.location.href = basePath+ "v1/page/orderList"
        })
    }
</script>
</body>
</html>

