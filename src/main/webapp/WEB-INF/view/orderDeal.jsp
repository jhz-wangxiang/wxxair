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
<section class="order-header-banner">
  <img src="<%=basePath%>resources/image/order-deal-header-banner.jpg" alt="">
</section>
<section class="order-body">
  <h3 class="order-deal-header">行李信息</h3>
  <div class="order-deal-box">
    <div class="order-deal-item"><span class="label">航班号：</span><span class="content">CA1987</span></div>
    <div class="order-deal-item"><span class="label">日期：</span><span class="content">2017.09.18</span></div>
    <div class="order-deal-item"><span class="label">行李数量：</span><span class="content">2</span></div>
    <div class="order-deal-item"><span class="tip">行李内无贵重、易碎、违禁物品</span></div>
  </div>
  <h3 class="order-deal-header">收货地址</h3>
  <div class="order-deal-box">
    <div class="order-deal-item"><span class="label">收件人：</span><span class="content">麻花藤</span></div>
    <div class="order-deal-item"><span class="label">联系方式：</span><span class="content">15645552321</span></div>
    <div class="order-deal-item"><span class="label">详细地址：</span><span class="content">北京市海淀区西三环北路紫金大厦19号5单元1111</span></div>
  </div>
  <div class="fee-box"><span class="label">服务费：</span><span class="content">40元</span></div>
  <a href="javascript:;" class="btn btn-lg">立即下单</a>
</section>

<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js" type="text/javascript"></script>
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
    wx.config({
        debug: false,
        appId: "${appid}",
        timestamp: "${timestamp}",
        nonceStr: "${nonceStr}",
        signature: "${signature}",
        jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareQZone']
    });
    wx.ready(function(){
        wx.onMenuShareTimeline({
            title: "好奇心害死猫，快来看看你的2018升级关键词是什么？",
            link: "${WX}",
            imgUrl: "<%=basePath%>resources/chest/css/img/share.jpg"
        });
        wx.onMenuShareAppMessage({
            title: "好奇心害死猫，快来看看你的2018升级关键词是什么？",
            desc: "真是万万妹想到！",
            link: "${WX}",
            imgUrl: "<%=basePath%>resources/chest/css/img/share.jpg"
        });
        wx.onMenuShareQQ({
            title: "好奇心害死猫，快来看看你的2018升级关键词是什么？",
            desc: "真是万万妹想到！",
            link: '${WX}',
            imgUrl: '<%=basePath%>resources/chest/css/img/share.jpg',
        });
        wx.onMenuShareQZone({
            title: "好奇心害死猫，快来看看你的2018升级关键词是什么？",
            desc: "真是万万妹想到！",
            link: '${WX}',
            imgUrl: '<%=basePath%>resources/chest/css/img/share.jpg',
        });
    });
</script>
</body>
</html>
