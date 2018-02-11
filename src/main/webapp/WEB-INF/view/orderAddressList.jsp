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
  <title>air</title>
  <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0"name="viewport" />
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="black" name="apple-mobile-web-app-status-bar-style" />
  <meta content="telephone=no" name="format-detection" />
  <link rel="stylesheet" href="../../resources/css/style.css">
  <link rel="stylesheet" href="../../resources/css/orderList.css">
  <script type="text/javascript" src="../../resources/js/rem.js"></script>
</head>
<body>
<div class="wrapper">
	<section class="order-item">
      <div class="order-detail0209">
        <div class="detail-item0209">
          <em>收件人:</em><em>马化腾</em>
        </div>
        <div class="detail-item0209">
          <em>联系电话:</em><em>13011111111</em>
        </div>
        <div class="detail-item0209">
          <em>详细地址:</em><em>北京市海淀区北三环西路19号院xxxxx小区xxx号楼xxxx单元sssss说的是顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶</em>
        </div>
      </div>
      <p class="order-no0209 flex-p0209" style="padding:5px 0;">
      	<span>
      		<input type="checkbox" style="display: none;" id="address-radio" class="address-radio">
      		<label for="address-radio" class="address-radio-box">设置成默认地址</label>
      	</span>
        <a href="javascript:;" class="orderAddressEdit">编辑</a><a href="javascript:;" class="orderAddressEdit orderAddressDel">编辑</a></p>
    </section>
</div>
<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
<script>
$('.address-radio').change(function () {
	if($(this).prop('checked')){
		$(this).prop('checked',true);
		$(this).siblings('label').addClass('cur');
	}else{
		$(this).prop('checked',false);
		$(this).siblings('label').removeClass('cur');
	}
});
</script>
</body>
</html>