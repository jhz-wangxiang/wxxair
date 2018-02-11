var Insert = function(){
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
    		$('.step-one').css("display","none");
			$('.step-two').css("display","block");
    		/*$.ajax({
    			type:"post",
    			url:rootPath+'v1/page/insertUser?'+Math.random(),
    			data:{"name":name,"phone":phone},
    			success:function(res){
    				if(res.resultCode==='SUCCESS'){
    					$('.step-one').css("display","none");
    					$('.step-two').css("display","block");
    				}else{
    					Common.alter(res.msg);
    				}
    			},
    			error:function(){
    				Common.alter('服务器异常，请稍后再试！');
    			}
    		})*/
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
			$('.step-two').css("display","none");
			$('.step-three').css("display","block");
			/*$.ajax({
				type:"post",
				url:rootPath+'v1/page/checkflightNum?'+Math.random(),
				data:{"flightNum":flightNum,"nowTime":nowTime,"baggageNum":baggageNum},
				success:function(res){
					if(res.resultCode=="SUCCESS"){
						$('.step-two').css("display","none");
						$('.step-three').css("display","block");
					}else{
						Common.alter(res.msg);
					}
				},
				error:function(){
					Common.alter('服务器异常，请稍后再试。');
				}
			});*/
			
			return false;
		});
		
	}
	/**
	 * 选择区
	 */
	var selAddressFn = function(){
		$('#area>span').click(function () {
		      $(this).next('ul').toggle();
		    })
		    $('#area>ul>li').click(function () {
		      $(this).parents('#area').children('span').html($(this).html());
		      $('#area>ul').hide();
		    })
	}
	var stepThree = function(){
		
	}
	return {
		init:function(){
			selXieYi();//是否阅读协议
			stepOne();//填写第一步信息
			stepTwo();//填写第二步信息
			stepThree();//填写第三部
			selAddressFn();
		}
	}
}();