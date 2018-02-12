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
  <title>地址列表</title>
  <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"name="viewport" />
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="black" name="apple-mobile-web-app-status-bar-style" />
  <meta content="telephone=no" name="format-detection" />
  <link rel="stylesheet" href="<%=basePath%>resources/css/style.css">
  <link rel="stylesheet" href="<%=basePath%>resources/css/orderList.css">
  <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body>
<div class="wrapper">
	<h3 style="color: #3974bb;font-size: .38rem;font-weight: normal; padding:15px;">常用收获地址</h3>
    <div id="table"></div>
</div>
<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
<script>
var basePath = "<%=basePath%>";
$.ajax({
	url: basePath+"v1/address/getAddressAll",
	type:"POST",
	success:function(data){
		var html = [];
		var c ;
		if(data.addressList == ""){
			html.push('<p style="font-size:.2rem; color:#666; text-align:center; padding:10px; margin-top:3rem">您还没有添加过地址，请点击按钮新建一个收货地址</p>');
			html.push('<div class="step-box" style="justify-content:center"><a href="'+basePath+'v1/page/orderAddressNew" class="btn btn-nm" style="width:2rem">新建收货地址</a></div>')
			$("#table").html(html.join(""));
			return false;
		}
		for(var i = 0;i<data.addressList.length;i++){
			
			html.push('<section class="order-item"><div class="order-detail0209" style="border-width: 0 0 1px 0;">');
			html.push('<div class="detail-item0209"><em>收件人:</em><em>'+data.addressList[i].consignee+'</em></div>');
			html.push('<div class="detail-item0209"><em>联系电话:</em><em>'+data.addressList[i].consigneePhone+'</em></div>');
			html.push('<div class="detail-item0209"><em>详细地址:</em><em>'+data.addressList[i].province+data.addressList[i].city+data.addressList[i].area+'</em></div></div>');
			if(data.addressList.length>1){
				c = "justify-content: space-between;"
			}else{
				c = "justify-content: flex-end;"
			}
			html.push('<p class="order-no0209 flex-p0209" style="padding:5px 0;'+c+'">');
			if(data.addressList.length>1){
				if(data.addressList[i].status){
					html.push('<span><input type="checkbox" style="display: none;" id="address-radio" checked class="address-radio" onchange="changeAdress(this,\''+data.addressList[i].id+'\',\''+data.addressList[i].userid+'\')"><label for="address-radio" class="cur address-radio-box">设置成默认地址</label></span>');
				}else{
					html.push('<span><input type="checkbox" style="display: none;" id="address-radio" class="address-radio" onchange="changeAdress(this,\''+data.addressList[i].id+'\',\''+data.addressList[i].userid+'\')"><label for="address-radio" class="address-radio-box">设置成默认地址</label></span>');
				}
			}
			html.push('<a href="javascript:;" class="orderAddressEdit">编辑</a><a href="javascript:delAddress(\''+data.addressList[i].id+'\');" class="orderAddressEdit orderAddressDel">删除</a>');
			html.push('</p></section>');
		}
		$("#table").html(html.join(""));
	}
});
var changeAdress = function(t,id,userid){
	if($(t).prop('checked')){
		$(t).prop('checked',true);
		$(t).siblings('label').addClass('cur');
		$.ajax({
			url:basePath+"v1/address/updateAddressStatus",
			type:"POST",
			data:{id:id,userid:userid},
			success:function(data){
				console.log(data)
				if (res.resultCode == "SUCCESS") {
					Common.alter(res.msg);
                }else{
                  Common.alter(res.msg);
                }
			}
		})
	}else{
		$(t).prop('checked',false);
		$(t).siblings('label').removeClass('cur');
		$.ajax({
			url:basePath+"v1/address/updateAddressStatus",
			type:"POST",
			data:{id:id,userid:userid},
			success:function(data){
				console.log(data)
				if (res.resultCode == "SUCCESS") {
					Common.alter(res.msg);
                }else{
                  Common.alter(res.msg);
                }
			}
		})
	}
}
var delAddress = function (id){
    var html = [];
    html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><p style="font-size: .38rem; color: #3974bb; text-align: center;">确认删除地址</p></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm" style="width: 40%;display: inline-block; margin-right: .6rem;">确认</a><a href="JavaScript:;" class="btn btn-nm" style="width: 40%;display: inline-block; background: #999999;">取消</a></div></div></div>')
    $('body').append(html.join(''));
    $('.modal-footer0211>a').eq(0).click(function () {
        $.ajax({
            url: basePath + 'v1/address/deletAddress',
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
var cancelSuccess = function () {
    var html = [];
    html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><div class="order-info-box order-cancle"><img src="<%=basePath%>resources/image/order-cancle.png" alt=""><p class="order-info-title">地址已经删除！</p></div></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm">确认</a></div></div></div>')
    $('body').append(html.join(''));
    $('.modal-footer0211>a').eq(0).click(function () {
        $('#modal').remove();
        window.location.reload();
    })
}
</script>
</body>
</html>