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
  <script type="text/javascript" src="<%=basePath%>resources/chest/js/rem.js"></script>
</head>
<body>
  <section>
    <img src="<%=basePath%>resources/image/order-step-header-banner.jpg" alt="">
  </section>
  <section>

  </section>

<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js" type="text/javascript"></script>
<script>
    var backMusic = document.getElementById("backMusic");
    backMusic.play();
    document.addEventListener("WeixinJSBridgeReady", function () {
        backMusic.play();
    }, false);
    document.querySelector(".audio_box").onclick = function (event) {
        if(this.dataset.f==0){
            backMusic.play();
            this.querySelector("img").src = "<%=basePath%>resources/chest/css/img/audio_open.png";
            this.dataset.f = 1;
        }else{
            backMusic.pause();
            this.querySelector("img").src = "<%=basePath%>resources/chest/css/img/audio_close.png";
            this.dataset.f = 0;
        }
    }

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
