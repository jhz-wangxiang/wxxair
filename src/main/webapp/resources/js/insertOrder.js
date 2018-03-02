var Insert = function(){
	var json = {};//存储最后需要提交时的数据
	//第一步是否阅读协议
	var selXieYi = function(){
		$('#protocol-radio').change(function () {
        	if($(this).prop('checked')){
        		$(this).prop('checked',true);
        		$(this).siblings('label').addClass('cur');
        	}else{
        		$(this).prop('checked',false);
        		$(this).siblings('label').removeClass('cur');
        	}
        });
		//选择是否有贵重物品
		$('#baggage-checkbox').change(function () {
			console.log($(this).prop("checked"))
            if ($(this).prop("checked")) {
            	$(this).prop('checked',true);
                $(this).siblings('label').addClass('cur');
            } else {
            	$(this).prop('checked',false);
                $(this).siblings('label').removeClass('cur');
            }
        })
	}
	//第一步操作
	var stepOne = function(){
		$('#oneStep').click(function(event){
    		event.stopPropagation();
    		var ck = $('#protocol-radio').prop('checked'),
    		name = $('#name').val(),
    		phone = $('#phone').val();
    		//判断是否阅读协议条款
    		if(!ck){//未阅读
    			Common.alter('请先阅读服务条款协议，并同意后，才可以进行下一步');
    			return false;
    		}
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
    		$.ajax({
    			type:"post",
    			url:rootPath+'v1/page/insertUser?'+Math.random(),
    			data:{"name":name,"phone":phone},
    			success:function(res){
    				if(res.resultCode=='SUCCESS'){
    					$('.step-one').css("display","none");
    					$('.step-two').css("display","block");
    					json.name = name;
    					json.phone = phone;
    				}else{
    					Common.alter(res.msg);
    				}
    			},
    			error:function(){
    				Common.alter('服务器异常，请稍后再试！');
    			}
    		});
    		return false;
    	})
	}
	//第二部操作
	var stepTwo = function(){
		$('#stepTwo').bind('click',function(event){
			event.stopPropagation();
			var flightNum = $('#flightNum').val(),
			nowTime = $('#nowTimeStr').val(),
			baggageNum = $('#baggageNum').val(),
			ck = $('#baggage-checkbox').prop("checked");
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
			if(!ck){
				Common.alter('请勾选行李内是否有贵，易碎，违禁物品。');
				return false;
			}
			$.ajax({
				type:"post",
				url:rootPath+'v1/page/checkflightNum?'+Math.random(),
				data:{"flightNum":flightNum,"nowTimeStr":nowTime.replace(/\-/g,'/'),"baggageNum":baggageNum},
				success:function(res){
					if(res.resultCode=="SUCCESS"){
						json.baggageNum = baggageNum;
						$('.step-two').css("display","none");
						$('.step-three').css("display","block");
						//第三里面初始化默认地址
						initDefaultAdd();
						json.flightNum = flightNum;
						json.nowTimeStr = nowTime.replace(/\-/g,'/');
						//核对订单
						$('#sFNum').html(flightNum);
						$('#sFDate').html(nowTime.replace(/\-/g,'/'));
						$('#sFBag').html(baggageNum);
					}else{
						Common.alter(res.msg);
					}
				},
				error:function(){
					Common.alter('服务器异常，请稍后再试。');
				}
			});
			
			return false;
		});
		
	}

    /**
	 * 初始化默认地址
     */
	function initDefaultAdd() {
// v1/address
        $.ajax({
            type:"post",
            url:rootPath+'v1/address/getAddress?'+Math.random(),

            success:function(res){
                if(res.resultCode=="SUCCESS"){
                	if(res.address){//默认地址存在
						$('#consignee').val(res.address.consignee);
                        $('#consigneePhone').val(res.address.consigneePhone);
                        $('#areaH').html(res.address.area);
                        $('#areaId').val(res.address.areafrom);
                        $('#address').val(res.address.address);
                        json.id  = res.address.id;
                        getPriceByAreaId(res.address.areafrom);
					}
                }else{
                    Common.alter(res.msg);
                }
            },
            error:function(){
                Common.alter('服务器异常，请稍后再试。');
            }
        });
    }
	/**
	 * 选择区
	 */
	function initArea(){
		$.ajax({
			type:"post",
			url:rootPath+'v1/area/getArea?'+Math.random(),
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
		$.ajax({
			type:"post",
			url:rootPath+'v1/page/getPrice?'+Math.random(),
			data:{"areaId":id,"baggageNum":json.baggageNum},
			success:function(res){
				$('#free').html(res.paid+'元');
				//json.paid = res.paid;
				//核对订单
				$('#sCost').html(res.paid+'元');
			}
		})
	}
	var stepThree = function(){
		$('#sure').bind('click',function(event){
			event.stopPropagation();
			var consignee = $('#consignee').val(),
			consigneePhone = $('#consigneePhone').val(),
			areaId = $('#areaId').val(),
			province = $('#province').html(),
			city = $('#city').html(),
			area = $('#areaH').html(),
			address = $('#address').val();
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
			json.consignee = consignee;
			json.consigneePhone = consigneePhone;
			json.areaId = areaId;
			json.province = province;
			json.city = city;
			json.area = area;
			json.address = address;
			//核对订单
			$('#sName').html(consignee);
			$('#sphone').html(consigneePhone);
			$('#sAddress').html(province+city+area+address);
			
			$('.step-three').css("display","none");
			$('#order-info').css("display","block");
			
			
			return false;
		});
	}
	function paySuccess() {
	      var html = [];
	      html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><div class="order-info-box order-success"><img src="../../resources/image/order-success.png" alt=""><p class="order-info-title">支付成功！</p><p class="order-info-warning">但流程未完</p><p class="order-info-tip order-info-warning">下机后请前往行李送到家柜台，递交行李小票</p></div></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm">确认</a></div></div></div>')
	      $('body').append(html.join(''));
	      $('.modal-footer0211>a').eq(0).click(function () {
	          $('#modal').remove();
	          window.location.href = rootPath+ "v1/page/orderList"
	      })
	  }
	//支付按钮事件
	function pay(orderNo){	
		/* appId:wxcf9a42f63c23a637,timeStamp:1449319601,nonceStr:20151205236247,package:prepay_id=,signType:MD5,paySign:7579772D56139215721D5CB803A3DA22,orderNum:1245124521*/
		var appId = null;
		var timeStamp = null;
		var nonceStr = null;
		var package_var = null;
		var signType = null;
		var paySign = null;
		var orderNum = null;
		$.ajax({
	   		async:true, 
	   		type : "POST",
	   		url: rootPath+'/v1/page/getPackage?orderNo='+orderNo,
	   		dataType:'text',
	   		success: function(msg){
	   			var arr = msg.split(",");
		        appId = arr[0].split(":")[1];
		        timeStamp = arr[1].split(":")[1];
		        nonceStr = arr[2].split(":")[1];
		        package_var = arr[3].split(":")[1];
		        signType = arr[4].split(":")[1];
		        paySign = arr[5].split(":")[1];
		        orderNum = arr[6].split(":")[1];
		         if (typeof WeixinJSBridge == "undefined"){
				   if( document.addEventListener ){
				       document.addEventListener('WeixinJSBridgeReady', onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum), false);
				   }else if (document.attachEvent){
				       document.attachEvent('WeixinJSBridgeReady', onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum)); 
				       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum));
				   }
				}else{
				   onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum);
				} 
	    	} 
	   	});
	}
	function onBridgeReady(appId,timeStamp,nonceStr,package_var,signType,paySign,orderNum){
		   WeixinJSBridge.invoke(
		       'getBrandWCPayRequest', {
		           "appId" : appId,     //公众号名称，由商户传入     
		           "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数     
		           "nonceStr" : nonceStr, //随机串     
		           "package" : package_var,     
		           "signType" :signType,         //微信签名方式：     
		           "paySign" : paySign //微信签名 
		       },
		       function(res){
		           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                       paySuccess();
		           }else if(res.err_msg === 'get_brand_wcpay_request:cancel'){
                       window.location.href = rootPath+ "v1/page/orderList";
		           } else{
		           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		       }
		   ); 
		}
	var lastStep  =function(){
		$('#lastStep').bind('click',function(event){
			event.stopPropagation();
			$.ajax({
				type:"post",
				url:rootPath+'v1/page/insertOrder?'+Math.random(),
				data:json,
				success:function(res){
					if(res.resultCode=='SUCCESS'){
						$('#serviceCost').html($('#sCost').html());
                        $('#order-info').css("display","none");
                        $('#order-success').css("display","block");
                        $('input[name="orderNo"]').val(res.orderNo)
					}else{
						Common.alter(res.msg);
					}
				},
				error:function(){
					Common.alter('服务器异常，请稍后再试！');
				}
			})
			return false;
		});
		
	}
	var payStep = function () {
        $('#payStep').bind('click',function(event){
            event.stopPropagation();
            pay($('input[name="orderNo"]').val());
            return false;
        });
    }
	return {
		init:function(){
			selXieYi();//是否阅读协议
			stepOne();//填写第一步信息
			stepTwo();//填写第二步信息
			stepThree();//填写第三部
			initArea();//初识话地址栏
			lastStep();//最后一步
            payStep();//支付
		},
        gotoUseIntro: function () {
			$('#order-deal').hide();
			$('#order-useIntro').show();
			$('#order-serviceProtocol').hide();
        },
        gotoServiceProtocol: function () {
            $('#order-deal').hide();
            $('#order-useIntro').hide();
            $('#order-serviceProtocol').show();
        },
        returnOrderDeal: function () {
            $('#order-deal').show();
            $('#order-useIntro').hide();
            $('#order-serviceProtocol').hide();
        }
	}
}();