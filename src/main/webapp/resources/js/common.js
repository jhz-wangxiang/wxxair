var validateReg = {
		isNumCross:"/^[1-9]\d*$/"  //正整数
};
var Common ={
		isNull: function (str) {
	        return (str == "" || typeof str != "string");
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
		}
};