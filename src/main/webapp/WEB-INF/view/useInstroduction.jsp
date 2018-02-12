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
  <title>使用说明</title>
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
<section class="text-cont0212">
  <h1>使用说明</h1>
</section>
<section class="text-cont0212">
  <p>“行李到家”的标准答案是：三步</p>
</section>
<section class="text-cont0212">
  <p>第一步，使用“行李到家”公众号下单；</p>
  <p>第二步，抵达行李提取厅，到“行李到家服务台”与服务人员完成身份验证；</p>
  <p>第三步，空手任性游，安心静候托运行李飞到家；</p>
  <p>详细操作说明如下</p>
</section>
<section class="text-cont0212" style="margin-top: .2rem;">
  <p class="text-subTitle0212">第一步 使用“行李到家“微信公众号下单；</p>
  <p class="text-step"><i class="text-num">1</i>关注“行李到家”</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img1.jpg" alt=""></p>
  <p class="text-step"><i class="text-num">2</i>选择左下角行李到家选项中的首都机场T2航站楼；</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img2.jpg" alt=""></p>
  <p class="text-step"><i class="text-num">3</i>输入姓名及联系电话；</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img3.jpg" alt=""></p>
  <p class="text-step"><i class="text-num">4</i>输入航班号、日期及委托运输行李数量；</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img4.jpg" alt=""></p>
  <p class="text-step"><i class="text-num">5</i>选择收货人及地址；</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img5.jpg" alt=""></p>
  <p class="text-step"><i class="text-num">6</i>确认订单；</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img6.jpg" alt=""></p>
  <p class="text-step"><i class="text-num">7</i>下单支付；</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img7.jpg" alt=""></p>
</section>
<section class="text-cont0212" style="margin-top: .2rem;">
  <p class="text-subTitle0212">第二步 抵达行李提取厅，到“行李送到家服务台”与服务人员完成身份验证；</p>
  <p>抵达行李提取厅，将身份证和行李小票出示给扶梯下方的“行李送到家服务台”工作人员进行快速身份验证，确认无误后，工作人员将行李小票收存，身份证交还旅客，与旅客相关操作结束。</p>
  <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img8.jpg" alt=""></p>
</section>
<section class="text-cont0212" style="margin-top: .2rem;">
  <p class="text-subTitle0212">第三步 空手任性游，安心静候托运行李飞到家。</p>
  <p>“行李到家”的运送小哥将在承诺时间内将您的行李快速、安全的送至指定地点。服务结束前，您可以通过“行李到家”公众号中“我的”-“我的订单”查询递送状态或修改订单。</p>
</section>
<%--<section class="text-cont0212">--%>
  <%--<a href="<%=basePath %>v1/page/orderStepOne" class="btn btn-lg" style="margin-bottom:.4rem;">返回</a>--%>
<%--</section>--%>

</body>
</html>

