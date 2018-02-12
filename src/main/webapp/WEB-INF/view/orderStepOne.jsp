<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" import="java.text.*"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>创建订单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <link rel="stylesheet" href="<%=basePath%>resources/css/style.css">
    <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body>
<section class="order-header-banner">
    <img src="<%=basePath%>resources/image/order-step-header-banner.jpg" alt="">
</section>
<section class="order-body">
    <form action="">
        <div class="step-one">
            <div class="btn-box"><a href="javascript:;" class="btn btn-xs"><i class="btn-icon">?</i>使用说明</a></div>
            <div class="step-box">
                <div class="number">1</div>
                <div class="title-box">
                    <p class="title">身份信息验证</p>
                    <p class="sub-title">为防止您的行李丢失，请检查您的个人信息</p>
                </div>
            </div>
                <div class="input-box">
                    <label for="name">乘机人姓名</label>
                    <input type="tuserext" id="name" name="name" value="${name}"></div>
                <div class="input-box">
                    <label for="phone">联系电话</label>
                    <input type="text" id="phone" name="phone" value="${phone}">
                </div>
            <div class="protocol-box"><a href="javascript:;">委托服务协议条款</a></div>
            <div class="protocol-box"><input type="checkbox" style="display: none;" id="protocol-radio"><label
                    for="protocol-radio"></label><span>我已阅读并遵守委托服务协议中的内容</span></div>
            <a href="javascript:;" class="btn btn-lg" id="oneStep">下一步</a>
        </div>
        <div class="step-two" style="display: none;">
            <div class="step-box">
                <div class="number">2</div>
                <div class="title-box">
                    <p class="title">航班及行李信息</p>
                    <p class="sub-title">为防止您的行李丢失，请检查您航班及行李信息</p>
                </div>
            </div>
            <div class="input-box"><label for="registerName">航班号</label><input type="text" id="flightNum"
                                                                               name="flightNum"></div>
            <div class="input-box"><label for="registerName">日期</label><input type="date" id="nowTimeStr"
                                                                              name="nowTimeStr"></div>
            <div class="input-box"><label for="registerName">行李数量</label><input type="text" id="baggageNum"
                                                                                name="baggageNum"></div>
            <div class="baggage-box"><input type="checkbox" style="display: none;" id="baggage-checkbox"><label
                    for="baggage-checkbox"></label><span>行李内无贵重、易碎、违禁物品</span></div>
            <div class="tip-box">温馨提示：为避免损失，贵重物品、易碎物品请勿托运</div>
            <a href="javascript:;" class="btn btn-lg" id="stepTwo">下一步</a>
        </div>
        <div class="step-three" style="display:none">
            <div class="step-box">
                <div class="number">3</div>
                <div class="title-box">
                    <p class="title">收货地址</p>
                    <p class="sub-title" >请填写或选择您的收货地址</p>
                </div>
            </div>
            <div class="input-box"><label for="consignee">收货人</label><input type="text" id="consignee" name="consignee"></div>
            <div class="input-box"><label for="consigneePhone">联系电话</label><input type="text" id="consigneePhone" name="consigneePhone"></div>
            <div class="order-address-select-box">
                <div class="order-address-select-item">
                    <span id="province">北京市</span>
                    <ul>
                        <li>省</li>
                        <li>北京市</li>
                    </ul>
                </div>
                <div class="order-address-select-item">
                    <span id="city">北京市</span>
                    <ul>
                        <li>市</li>
                        <li>北京市</li>
                    </ul>
                </div>
                <div class="order-address-select-item" id="area">
                    <span id="areaH">区</span>
                    <input type="hidden" id="areaId"/>
                    <ul id='areaUl'>
                        <!-- <li>区</li>
                        <li>海淀区</li>
                        <li>昌平区</li>
                        <li>朝阳区</li> -->
                    </ul>
                </div>
            </div>
            <div class="input-box"><label for="phone">详细地址</label><input type="text" id="address" name="address"></div>
            <div class="fee-box"><span class="label">服务费：</span><span class="content" id="free">40元</span></div>
            <a href="javascript:;" class="btn btn-lg" id="sure">确认</a>
        </div>
    </form>
</section>
<section class="order-body" style="display:none">
  <h3 class="order-deal-header">行李信息</h3>
  <div class="order-deal-box">
    <div class="order-deal-item"><span class="label">航班号：</span><span class="content" id="sFNum">CA1987</span></div>
    <div class="order-deal-item"><span class="label">日期：</span><span class="content" id="sFDate">2017.09.18</span></div>
    <div class="order-deal-item"><span class="label">行李数量：</span><span class="content" id="sFBag">2</span></div>
    <div class="order-deal-item"><span class="tip">行李内无贵重、易碎、违禁物品</span></div>
  </div>
  <h3 class="order-deal-header">收货地址</h3>
  <div class="order-deal-box">
    <div class="order-deal-item"><span class="label">收件人：</span><span class="content" id="sName">麻花藤</span></div>
    <div class="order-deal-item"><span class="label">联系方式：</span><span class="content" id="sphone">15645552321</span></div>
    <div class="order-deal-item"><span class="label">详细地址：</span><span class="content" id="sAddress">北京市海淀区西三环北路紫金大厦19号5单元1111</span></div>
  </div>
  <div class="fee-box"><span class="label">服务费：</span><span class="content" id="sCost">0元</span></div>
  <a href="javascript:;" class="btn btn-lg" id="lastStep">立即下单</a>
</section>
<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/insertOrder.js" type="text/javascript"></script>
<script>
var rootPath = '<%=basePath%>';
Insert.init();
</script>
</body>
</html>

