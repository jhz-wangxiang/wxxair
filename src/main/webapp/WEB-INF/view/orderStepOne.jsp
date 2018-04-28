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
    <link rel="stylesheet" href="<%=basePath%>resources/css/orderList.css">
    <script type="text/javascript" src="<%=basePath%>resources/js/rem.js"></script>
</head>
<body>
<div id="order-deal">
    <section class="order-header-banner">
        <img src="<%=basePath%>resources/image/order-step-header-banner.jpg" alt="">
    </section>
    <section class="order-body">
        <form action="">
            <div class="step-one">
                <div class="btn-box"><a href="javascript:;" class="btn btn-xs" onclick="Insert.gotoUseIntro();"><i class="btn-icon">?</i>使用说明</a></div>
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
                <div class="protocol-box"><a href="javascript:;" onclick="Insert.gotoServiceProtocol()">委托服务协议条款</a></div>
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
                <div class="input-box"><label for="flightNum">航班号</label><input type="text" id="flightNum"
                                                                                   name="flightNum"></div>
                <div class="input-box"><label for="nowTimeStr">日期</label><input type="date" id="nowTimeStr"
                                                                                  name="nowTimeStr"></div>
                <div class="input-box"><label for="baggageNum">行李数量</label><input type="text" id="baggageNum"
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
                <div class="input-box"><label for="address">详细地址</label><input type="text" id="address" name="address"></div>
                <div class="fee-box"><span class="label">服务费：</span><span class="content" id="free">0元</span></div>
                <a href="javascript:;" class="btn btn-lg" id="sure">确认</a>
            </div>
        </form>
    </section>
    <section class="order-body" id="order-info" style="display:none">
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
    <section class="order-body" id="order-success" style="display: none;">
        <div class="order-info-box order-success">
            <img src="../../resources/image/order-success.png" alt="">
            <p class="order-info-title">恭喜您已下单成功！</p>
            <p class="order-info-warning">但流程未完</p>
            <p class="order-info-tip">下机后请前往行李送到家柜台，递交行李小票</p>
        </div>
        <div class="fee-box"><span class="label">服务费：</span><span class="content" id="serviceCost">0元</span></div>
        <a href="javascript:;" class="btn btn-lg" style="background: #009900;" id="payStep">微信支付</a>
    </section>
</div>
<div id="order-useIntro" style="display: none;">
    <section class="text-cont0212">
        <h1>使用说明</h1>
    </section>
    <section class="text-cont0212">
        <p style="font-size: .26rem">托运行李免提取直飞到家需要几个步骤？</p>
        <p style="font-size: .26rem">“行李送到家”的标准答案是：三步。</p>
    </section>
    <section class="text-cont0212">
        <p style="font-size: .26rem">第一步，使用“行李到家”公众号下单；</p>
        <p style="font-size: .26rem">第二步，抵达行李提取厅，到“行李送到家服务台”与服务人员完成身份验证；</p>
        <p style="font-size: .26rem">第三步，空手任性游，安心静候托运行李飞到家。</p>
        <p style="font-size: .26rem">详细操作说明如下</p>
    </section>
    <section class="text-cont0212" style="margin-top: .2rem;">
        <p class="text-subTitle0212" style="font-size: .26rem">第一步 使用“行李到家“微信公众号下单；</p>
        <p class="text-step"><i class="text-num">1</i>关注“行李到家”；</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img1.jpg" alt=""></p>
        <p class="text-step"><i class="text-num">2</i>选择左下角行李到家选项中的首都机场T2航站楼；</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img2.jpg" alt=""></p>
        <p class="text-step"><i class="text-num">3</i>输入姓名及联系电话；</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img3.jpg" alt=""></p>
        <p class="text-step"><i class="text-num">4</i>输入航班号、日期及委托运输行李数量；</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img4.jpg" alt=""></p>
        <p class="text-step"><i class="text-num">5</i>输入收货人、联系电话及地址，系统自动核算费用；</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img5.jpg" alt=""></p>
        <p class="text-step"><i class="text-num">6</i>确认订单；</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img6.jpg" alt=""></p>
        <p class="text-step"><i class="text-num">7</i>下单支付；</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img7.jpg" alt=""></p>
    </section>
    <section class="text-cont0212" style="margin-top: .2rem;">
        <p class="text-subTitle0212" style="font-size: .26rem">第二步 抵达行李提取厅，到“行李送到家服务台”与服务人员完成身份验证；</p>
        <p>抵达行李提取厅，将身份证和行李小票出示给扶梯下方的“行李送到家”服务台工作人员进行快速身份验证，确认无误后，工作人员将行李小票收存，身份证交还旅客，与旅客相关操作结束。</p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img8.jpg" alt=""></p>
        <p style="text-align: center;" class="text-img"><img src="<%=basePath%>resources/image/serviceIntro-img9.jpg" alt=""></p>
    </section>
    <section class="text-cont0212" style="margin-top: .2rem;">
        <p style="font-size: .26rem; color: red;">注：在提交行李小票前任意阶段均可取消服务，服务费用可在取消服务后一分钟内完成返还。</p>
    </section>
    <section class="text-cont0212" style="margin-top: .2rem;">
        <p class="text-subTitle0212" style="font-size: .26rem;">第三步 空手任性游，安心静候托运行李飞到家。</p>
        <p>“行李送到家”的运送小哥将在承诺时间内将您的行李快速、安全的送至指定地点~</p>
        <p>服务结束前，您可以通过“行李到家”公众号中“我的”-“我的订单”查询递送状态或修改订单。</p>
    </section>
    <section class="text-cont0212" style="margin-top: .2rem;">
        <p>在我们的任何服务阶段您有任何疑问，请随时拨打我们的客服电话：4000429358</p>
        <p>感谢您选择“行李送到家”。</p>
    </section>
    <section class="text-cont0212">
        <a href="javascript:;" class="btn btn-lg" style="margin-bottom:.4rem;" onclick="Insert.returnOrderDeal()">返回</a>
    </section>
</div>
<div id="order-serviceProtocol" style="display: none;">
    <section class="text-cont0212">
        <h1>委托服务协议条款</h1>
    </section>
    <section class="text-cont0212">
        <p>甲方（受托方）：北京安捷通航空地面服务股份有限公司　</p>
        <p>乙方（委托方）：委托服务旅客　</p>
    </section>
    <section class="text-cont0212">
        <p>第一条&nbsp;&nbsp;&nbsp;&nbsp;服务内容</p>
        <p>甲方接受乙方委托，向航空公司提取乙方行李，按照甲方公开承诺
            的服务内容及标准将乙方行李送达指定地点。　</p>
    </section>
    <section class="text-cont0212">
        <p>第二条&nbsp;&nbsp;&nbsp;&nbsp;甲方的权利与义务</p>
        <p>(一)甲方根据旅客指定时间（合理时段）、地点安排行李快递。</p>
        <p>(二)如在提取行李时，发现行李未到或破损，甲方需及时通知乙方。行李在航空运输过程中发生的丢失、破损、漏运等情况，属于乙方和航空公司的权利义务关系，甲方只负责协助处理。</p>
        <p>(三)如在行李快递过程中，发现行李丢失或破损，甲方负责寻找和由此发生的赔偿处理。</p>
        <p>(四)甲方在处理乙方行李时，需做行李保护。</p>
    </section>
    <section class="text-cont0212">
        <p>第三条&nbsp;&nbsp;&nbsp;&nbsp;乙方的权利与义务</p>
        <p>(一)乙方按照甲方标准支付快递费用。</p>
        <p>(二)乙方申明行李内无国家命令禁止运输的武器、弹药、危险品、非流通物品等。</p>
        <p>(三)乙方申明行李内无贵重及易碎物品，行李外观完好与否是行李处理过程中是否损害的唯一标准。</p>
        <p>(四)乙方申明行李内无现金、动物、毒品、产地来源不正确物品、腐蚀性或放射性等危险、易燃易爆品、白色粉末及一切有关法律法规禁止或限制运输的物品，如国家法律、法规关于禁止、限制运输的物品有新的规定的，从其规定。</p>
        <p>(五)除法律法规的强制性规定以外，甲方对于托寄物的安全、质量、环保、权利瑕疵等不承担保证责任，乙方需要确保托寄物的内容符合下列要求：</p>
        <p>1.符合国家法律法规的要求，不属于禁止生产、销售、传播的物品；</p>
        <p>2.符合国家规定的或者与第三方约定的质量、安全、环保等标准；</p>
        <p>3.托寄物不会造成收件人或其他第三方人身或财产的损害，也没有侵犯任何第三方的知识产权。</p>
        <p>(六)乙方按照甲方要求填写运输地址及相关信息，并确保所填写的运输地址及相关信息无误。</p>
    </section>
    <section class="text-cont0212">
        <p>第四条&nbsp;&nbsp;&nbsp;&nbsp;违约与责任</p>
        <p>(一)由于甲方发送快递过程中导致的行李破损及丢失，甲方需赔偿乙方行李实际损失，但最高赔偿限额为服务价格的3倍。</p>
        <p>(二)因甲方过失造成托寄物迟延派送的，甲方将免除本次运费。</p>
        <p>(三)由于乙方原因，如收件人地址不详或错误、收件人无法取得联系、收件人拒收或收件人拒付运费（收件人付费行李）等造成投递延误的，甲方不承担责任；导致二次运输的，乙方需承担由此产生的费用。</p>
        <p>(四)由于堵车、天气等不可抗因素造成的行李晚送情况，甲方不负赔偿责任。</p>
    </section>
    <section class="text-cont0212">
        <p>第五条&nbsp;&nbsp;&nbsp;&nbsp;关于费用和发票的约定</p>
        <p>(一)行李寄送服务费用按照甲方当日服务收费标准计算。</p>
        <p>(二)行李寄送服务费用由甲方通过微信公众号或在服务柜台现场收取，发票由甲方在服务柜台现场安排开具。</p>
    </section>
    <section class="text-cont0212">
        <p>第六条&nbsp;&nbsp;&nbsp;&nbsp;用户信息保护</p>
        <p>(一)甲方与乙方一同致力于用户个人信息（即能够独立或于其他信息结合后识别用户身份的信息）的保护。</p>
        <p>(二)甲方将运用与“行李到家”软件及相关服务相匹配的安全技术及其他安全措施并建立完善的管理制度来保护乙方的个人信息。</p>
        <p>(三)甲方不会将乙方的个人信息转移或披露给第三方，除非：</p>
        <p>1.事先获得乙方的明确授权；</p>
        <p>2.根据有关的法律法规或按照司法、行政等国家机关的要求；</p>
        <p>3.出于实现甲方为乙方提供服务所必需的目的，向合作单位分享提供服务所必需的个人信息，甲方及其合作伙伴无权将共享的个人信息用于任何其他用途；</p>
        <p>4.以维护公共利益或学术研究为目的；</p>
        <p>5.为维护甲方其他用户及其合作伙伴的合法权益，例如查找、预防、处理欺诈或安全方面的问题；</p>
        <p>6.甲方为维护合法权益而向乙方提起诉讼或仲裁；</p>
        <p>7.符合本协议相关条款的规定。</p>
    </section>
    <section class="text-cont0212">
        <p>第七条&nbsp;&nbsp;&nbsp;&nbsp;其他</p>
        <p>(一)本协议的成立、生效、履行、解释及争议的解决均应适用中华人民共和国大陆地区法律。倘本协议之任何规定因与中华人民共和国大陆地区法律抵触而无效，则这些条款将尽可能接近本协议原条文意旨重新解析，且本协议其它规定仍应具有完整的效力及效果。</p>
        <p>(二)本协议的签署地点为中华人民共和国北京市顺义区，若乙方与甲方发生争议的，双方应尽量友好协商解决，协商不成，双方均有权将争议提交至北京市顺义区法院管辖。</p>
        <p>(三)甲方有权依据国家政策、技术条件、产品功能等变化需要而进行修改本协议，甲方会将修改后的协议予以发布。前述内容一经正式发布，并以适当的方式送达用户（网站公布、系统通知等），即为本协议不可分割的组成部分，乙方应同样遵守。乙方对修改后的协议有异议的，请立即停止登录、使用“行李到家”软件及相关服务，若您登录或继续使用“行李到家”软件及相关服务，视为认可修改后的协议。</p>
        <p>本协议的版权为甲方所有，甲方保留一切解释和修改的权利。</p>
    </section>
    <section class="text-cont0212">
        <a href="javascript:;" class="btn btn-lg" style="margin-bottom:.4rem;" onclick="Insert.returnOrderDeal()">返回</a>
    </section>
</div>
<input type="hidden" id="orderNo" name="orderNo" value="">
<script src="<%=basePath%>resources/js/jq.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/common.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/insertOrder.js" type="text/javascript"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
<script>
var rootPath = '<%=basePath%>';
Insert.init();
</script>
</body>
</html>

