<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>air</title>
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

            <c:choose>
                　　<c:when test="${not empty  }">
                <div class="input-box">
                    <label for="registerName">乘机人姓名</label>
                    <input type="tuserext" id="registerName" name="registerName" value="${user.name}"></div>
                <div class="input-box">
                    <label for="registerPhone">联系电话</label>
                    <input type="text" id="registerPhone" name="registerPhone" value="${user.phone}">
                </div>
            </c:when>
                　　<c:otherwise>
                <div class="input-box">
                    <label for="registerName">乘机人姓名</label>
                    <input type="text" id="registerName" name="registerName"></div>
                <div class="input-box">
                    <label for="registerPhone">联系电话</label>
                    <input type="text" id="registerPhone" name="registerPhone">
                </div>

            </c:otherwise>
            </c:choose>


            <div class="protocol-box"><a href="javascript:;">委托服务协议条款</a></div>
            <div class="protocol-box"><input type="checkbox" style="display: none;" id="protocol-radio" checked="false"><label
                    for="protocol-radio"></label><span>我已阅读并遵守委托服务协议中的内容</span></div>
            <a href="javascript:;" class="btn btn-lg">下一步</a>
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
            <div class="input-box"><label for="registerName">日期</label><input type="text" id="nowTimeStr"
                                                                              name="nowTimeStr"></div>
            <div class="input-box"><label for="registerName">行李数量</label><input type="text" id="baggageNum"
                                                                                name="baggageNum"></div>
            <div class="baggage-box"><input type="checkbox" style="display: none;" id="baggage-checkbox" checked="true"><label
                    for="baggage-checkbox"></label><span>行李内无贵重、易碎、违禁物品</span></div>
            <div class="tip-box">温馨提示：为避免损失，贵重物品、易碎物品请勿托运</div>
            <a href="javascript:;" class="btn btn-lg">下一步</a>
        </div>
        <div class="step-three" style="display: none;">
            <div class="step-box">
                <div class="number">3</div>
                <div class="title-box">
                    <p class="title">收货地址</p>
                    <p class="sub-title">请填写或选择您的收货地址</p>
                </div>
            </div>
            <div class="display-box"><span class="label">收货人</span><span class="content">麻花藤</span></div>
            <div class="display-box"><span class="label">联系电话</span><span class="content">11016566123</span></div>
            <div class="display-box"><span class="label">收货地址</span><span
                    class="content">北京市海淀区北三环西路19号XXX小区XX号楼XX单元</span></div>
            <div class="fee-box"><span class="label">服务费：</span><span class="content">40元</span></div>
            <a href="javascript:;" class="btn btn-lg">确认</a>
        </div>
    </form>
</section>

<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script>
    $(function () {
        $('#protocol-radio').change(function () {
            if ($(this).prop("checked") == true) {
                $(this).siblings('label').removeClass('cur');
            } else {
                $(this).siblings('label').addClass('cur');
            }
        })
        $('#baggage-checkbox').change(function () {
            if ($(this).prop("checked") == true) {
                $(this).siblings('label').removeClass('cur');
            } else {
                $(this).siblings('label').addClass('cur');
            }
        })
    })
</script>
</body>
</html>

