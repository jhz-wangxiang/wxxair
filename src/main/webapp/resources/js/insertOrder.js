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
			$('.order-body').css("display","block");
			
			
			return false;
		});
	}
	function cancelSuccess() {
	      var html = [];
	      html.push('<div id="modal"><div class="cover"></div><div class="modal"><div class="modal-cont0211"><div class="order-info-box order-success"><img src="../../resources/image/order-success.png" alt=""><p class="order-info-title">恭喜您已下单成功！</p><p class="order-info-warning">但流程未完</p><p class="order-info-tip">您需要抵达机场后，将行李小票在行李柜台递交给工作人员，并支付费用</p></div></div><div class="modal-footer0211"><a href="JavaScript:;" class="btn btn-nm">确认</a></div></div></div>')
	      $('body').append(html.join(''));
	      $('.modal-footer0211>a').eq(0).click(function () {
	          $('#modal').remove();
	          window.location.href = rootPath+ "v1/page/orderList"
	      })
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
						cancelSuccess();
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
	return {
		init:function(){
			selXieYi();//是否阅读协议
			stepOne();//填写第一步信息
			stepTwo();//填写第二步信息
			stepThree();//填写第三部
			initArea();//初识话地址栏
			lastStep();//最后一步
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