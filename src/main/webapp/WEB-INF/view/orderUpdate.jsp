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
    <title>修改订单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <link rel="stylesheet" href="<%=basePath%>resources/css/style.css">
    <link rel="stylesheet" href="<%=basePath%>resources/css/orderList.css">
    <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body class="order-update">
<div id="order-deal">
    <section class="order-body">
        <form action="">
            <div class="step-one">
                <div class="step-box">
                    <div class="title-box">
                        <p class="title">身份信息验证</p>
                    </div>
                </div>
                <div class="input-box">
                    <label for="name">乘机人姓名</label>
                    <input type="tuserext" id="name" name="name" value=""></div>
                <div class="input-box">
                    <label for="phone">联系电话</label>
                    <input type="text" id="phone" name="phone" value="">
                </div>
            </div>
            <div class="step-two">
                <div class="step-box">
                    <div class="title-box">
                        <p class="title">航班及行李信息</p>
                    </div>
                </div>
                <div class="input-box"><label for="flightNum">航班号</label><input type="text" id="flightNum" name="flightNum"></div>
                <div class="input-box"><label for="nowTimeStr">日期</label><input type="date" id="nowTimeStr" name="nowTimeStr"></div>
                <div class="input-box"><label for="baggageNum">行李数量</label><input type="text" id="baggageNum" name="baggageNum" onblur="getPriceByAreaId()"></div>
                <div class="baggage-box"><span>行李内无贵重、易碎、违禁物品</span></div>
            </div>
            <div class="step-three">
                <div class="step-box">
                    <div class="title-box">
                        <p class="title">收货地址</p>
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
                        <input type="hidden" id="areaId" name="areaId"/>
                        <ul id='areaUl'>
                            <!-- <li>区</li>
                            <li>海淀区</li>
                            <li>昌平区</li>
                            <li>朝阳区</li> -->
                        </ul>
                    </div>
                </div>
                <div class="input-box"><label for="phone">详细地址</label><input type="text" id="address" name="address"></div>
                <div class="fee-box"><span class="label">服务费：</span><span class="content" id="free">0元</span></div>
                <a href="javascript:;" class="btn btn-lg" id="sure">确认修改</a>
            </div>
        </form>
    </section>
</div>
<input type="hidden" id="orderNo" name="orderNo" value="">
<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script>
var basePath = "<%=basePath %>";
var id = Common.GetUrlRequest()['orderId'];
$(function () {
    initArea();
    $.ajax({
        url: basePath + 'v1/order/getOrderDetail',
        data: {"id":id},
        type: "POST",
        success: function (res) {
            var order = res.order;
            $('input[name="name"]').val(order.name);
            $('input[name="phone"]').val(order.phone);
            $('input[name="flightNum"]').val(order.flightNum);
            $('input[name="nowTimeStr"]').val(Common.getLocalDate(order.nowTime));
            $('input[name="baggageNum"]').val(order.baggageNum);
            $('input[name="consignee"]').val(order.consignee);
            $('input[name="consigneePhone"]').val(order.consigneePhone);
            $('input[name="address"]').val(order.address);
            $('input[name="areaId"]').val(order.areaId);
            $('#free').html(order.paidFee+'元');
            $('#areaH').html(order.area);
        }
    })
    $('#sure').bind('click',function(event){
        var name = $('#name').val(),
            phone = $('#phone').val(),
            flightNum = $('#flightNum').val(),
            nowTime = $('#nowTimeStr').val(),
            baggageNum = $('#baggageNum').val(),
            consignee = $('#consignee').val(),
            consigneePhone = $('#consigneePhone').val(),
            areaId = $('#areaId').val(),
            province = $('#province').html(),
            city = $('#city').html(),
            area = $('#areaH').html(),
            address = $('#address').val();
        if(Common.isNull(name)){
            Common.alter('请填写乘客姓名！');
            return false;
        }
        if(Common.isNull(phone)){
            Common.alter('请填写乘客手机号码！');
            return false;
        }
        if(phone.length!=11){
            Common.alter('请填写正确的手机号码！');
            return false;
        }
        if(Common.isNull(flightNum)){
            Common.alter('请填写航班号');
            return false;
        }
        if(Common.isNull(nowTime)){
            Common.alter('请填写日期');
            return false;
        }
        if(Common.isNull(baggageNum)){
            Common.alter('请填写行李数量');
            return false;
        }
        if(!/^[1-9]\d*$/.test(baggageNum)){
            Common.alter('请正确填写行李数量,只能为正整数。');
            return false;
        }
        if(Common.isNull(consignee)){
            Common.alter('请填写收货人的姓名。');
            return false;
        }
        if(Common.isNull(consigneePhone)){
            Common.alter('请填写收货人的电话。');
            return false;
        }
        if(consigneePhone.length!=11){
            Common.alter('请填写正确的收货人的11位的手机号。');
            return false;
        }
        console.log(areaId)
        if(!areaId){
            Common.alter('请填写收货区域。');
            return false;
        }
        if(Common.isNull(address)){
            Common.alter('请填写收货详细的收获地址。');
            return false;
        }
        $.ajax({
            type:"post",
            url:basePath+'v1/page/checkflightNum?'+Math.random(),
            data:{"flightNum":flightNum,"nowTimeStr":nowTime.replace(/\-/g,'/'),"baggageNum":baggageNum},
            success:function(res){
                if(res.resultCode=="SUCCESS"){
                    var json = {};
                    json.id = id;
                    json.name = name;
                    json.phone = phone;
                    json.baggageNum = baggageNum;
                    json.flightNum = flightNum;
                    json.nowTimeStr = nowTime.replace(/\-/g,'/');
                    json.consignee = consignee;
                    json.consigneePhone = consigneePhone;
                    json.areaId = areaId;
                    json.province = province;
                    json.city = city;
                    json.area = area;
                    json.address = address;
                    $.ajax({
                        type:"post",
                        url:basePath+'v1/order/updateOrder?'+Math.random(),
                        data:json,
                        success:function(res){
                            if(res.resultCode=='SUCCESS'){
                                var html = [];
                                html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><div class="order-info-box order-cancle"><img src="'+basePath+'resources/image/order-cancle.png" alt=""><p class="order-info-title">您的订单修改成功！</p></div></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm">确认</a></div></div></div>')
                                $('body').append(html.join(''));
                                $('.modal-footer0211>a').eq(0).click(function () {
                                    $('#modal').remove();
                                    window.location.href = basePath+ "v1/page/orderDetail?id="+id;
                                })
                            }else{
                                Common.alter(res.msg);
                            }
                        },
                        error:function(){
                            Common.alter('服务器异常，请稍后再试！');
                        }
                    })
                }else{
                    Common.alter(res.msg);
                }
            },
            error:function(){
                Common.alter('服务器异常，请稍后再试。');
            }
        });
    })
})
function initArea(){
    $.ajax({
        type:"post",
        url:basePath+'v1/area/getArea?'+Math.random(),
        success:function(res){
            var html = [];
            for(var i = 0;i<res.length;i++){
                html.push('<li id="'+res[i].id+'">'+res[i].area+'</li>');
            }
            $('#areaUl').append(html.join(''));
            selAddressFn();
        }
    })
}
var selAddressFn = function(){
    $('#area>span').click(function () {
        $('#areaUl').toggle()
        //$(this).next('ul').toggle();
    })
    $('#area>ul>li').click(function () {
        $(this).parents('#area').children('span').html($(this).html());
        $('#areaId').val($(this).attr('id'));
        $('#area>ul').hide();
        getPriceByAreaId($(this).attr('id'));
    })
}
/**
 * 根据选择的区域获取对应的价格
 */
var getPriceByAreaId = function(id){
    if(!$('#baggageNum').val()){
        Common.alter('行李数量为空无法计算服务费');
        return false;
    }
    var areaId = id ? id : $('#areaId').val();
    $.ajax({
        type:"post",
        url:basePath+'v1/page/getPrice?'+Math.random(),
        data:{"areaId":areaId,"baggageNum":$('input[name="baggageNum"]').val()},
        success:function(res){
            $('#free').html(res.paid+'元');
        }
    })
}
</script>
</body>
</html>

