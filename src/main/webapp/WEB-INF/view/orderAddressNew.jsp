<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"name="viewport" />
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="black" name="apple-mobile-web-app-status-bar-style" />
  <meta content="telephone=no" name="format-detection" />
  <link rel="stylesheet" href="<%=basePath%>resources/css/style.css">
  <link rel="stylesheet" href="<%=basePath%>resources/css/orderList.css">
  <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body>
<section class="order-body">
  <h3 class="order-deal-header">新建收货地址</h3>
  <div class="input-box"><label for="name">收货人姓名</label><input type="text" id="consignee" name="consignee"></div>
  <div class="input-box"><label for="phone">联系电话</label><input type="text" id="consigneePhone" name="consigneePhone"></div>
  <p class="order-detail-tip">收货地址</p>
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
      <input type="hidden" id="areafrom" value="-1"/>
      <ul id='areaUl' style=" height: 200px;overflow-x: hidden;">
      </ul>
    </div>
  </div>
  <div class="input-box"><label for="phone">详细地址</label><input type="text" id="address" name="address"></div>
  <p class="order-detail-tip">注：本业务目前只支持北京地区六环以内的配送</p>
  <a href="JavaScript:addAddress();" class="btn btn-lg">确认添加</a>
</section>
<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
<script>
var basePath = "<%=basePath%>";
/**
 * 选择区
 */
 initArea();
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
	      $("#areaH").html($(this).html());
	      $('#areafrom').val($(this).attr('id'));
	      $('#area>ul').hide();
	    })
}
var addAddress = function(){
	var consignee = Common.ltrim($("input[name='consignee']").val());
	var consigneePhone = Common.ltrim($("input[name='consigneePhone']").val());
	var province = $("#province").html();
	var city = $("#city").html();
	var address = Common.ltrim($("input[name='address']").val());
	var area = $("#areaH").html();
	var areafrom = $("#areafrom").val();
	if(consignee ==""){
		Common.alter('请填写收货人的姓名。');
		return false;
	}
	if(consigneePhone==""){
		Common.alter('请填写收货人的电话。');
		return false;
	}
	if(consigneePhone.length!=11){
		Common.alter('请填写正确的收货人的11位的手机号。');
		return false;
	}
	if(areafrom=="-1"){
		Common.alter('请选择收货人的区域。');
		return false;
	}
	if(address==""){
		Common.alter('请填写收货人的详细地址。');
		return false;
	}
	$.ajax({
		url: basePath+"v1/addressinsertAddress",
		type:"POST",
		data:{
			consignee:consignee,
			consigneePhone:consigneePhone,
			province:province,
			city:city,
			address:address,
			area:area,
			areafrom:areafrom
		},
		success:function(json){
			if(json.resultCode == "SUCCESS"){
				location.href= basePath+"v1/page/orderAddressList"
			}else{
				Common.alter(json.msg);
			}
		}
	})
}
</script>
</body>
</html>