var validateReg = {
		isNumCross:"/^[1-9]\d*$/"  //正整数
};
var Common ={
		isNull: function (str) {
	        return (str == "" || typeof str != "string");
	    },
	    ltrim:function (text){
			return (text || "").replace(/^\s+/g,"").replace(/\s+$/g,"");
		},
	    //正整数
	    isNumCross:function(str){
	    	console.log(validateReg.isNumCross)
	    	return new RegExp(validateReg.isNumCross).test(str);
	    },
		alter:function(msg){
			var html = [];
			html.push('<div id="modal">');
			html.push('<div class="cover"></div>');
			html.push('<div class="modal">');
			html.push('<div class="modal-title0211"></div>');
			html.push('<div class="modal-cont0211">');
			html.push('<p class="info0211">'+msg+'</p>');
			html.push('</div>');
			html.push('<div class="modal-footer0211">');
			html.push('<a href="JavaScript:;" class="btn btn-nm" onclick="Common.removeModal()">关闭</a>');
			html.push('</div>');
			html.push('</div></div>');
			$('body').append(html.join(''));
		},
		removeModal:function(){
			$('#modal').remove();
		},
		GetUrlRequest:function () {
			var url = location.search;
			var theRequest = new Object();
			if (url.indexOf("?") != -1) {
				var str = url.substr(1);
				strs = str.split("&");
				for(var i = 0; i < strs.length; i ++) {
					theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
				}
			}
			return theRequest;
		}
};