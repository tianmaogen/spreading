;(function(x){
if(top!=self && x.sitetype!=1){document.body.innerHTML+='iframe not allowed!';return false;};
var doc = document,d=navigator.userAgent;
var isChrome= _getBrowserIsChrome();//navigator.userAgent.toLowerCase().indexOf('chrome')>0?true:false;
function _getBrowserIsChrome(){
	var _userAgent = navigator.userAgent.toLowerCase();
	if(_userAgent.indexOf('edge')>=0){
		return false;
	}else if(_userAgent.indexOf('chrome')>=0){
		return true;
	}else{
		return false;
	}
}
var rich ={img_dot : 'http://img.twcczhu.com/s/img/dot.gif',img_logo  : 'http://www.37cs.com/html/rich/logo.gif',comurl : 'http://www.37cs.com/',img_close : 'http://img.twcczhu.com/s/img/close.gif',
	ffb_url   : 'http://'+x.domain+'/rich/ffb.php?ep=',track_url : 'http://'+x.domain+'/f/track.php?step=1&ext=',
	curBdy    : (doc.compatMode.toLowerCase() == 'css1compat') ? doc.documentElement : doc.body,
	getVer    : function(){if (/msie (\d+\.\d)/i.test(d)){return parseFloat(RegExp.$1);}else{return 0;}}, 
	other     : /firefox/i.test(d) || /webkit/i.test(d),
	isOpera   : /opera/i.test(d),
	getCookie : function(n){var sRE = '(?:; )?'+n+'=([^;]*);?';var oRE = new RegExp(sRE);if(oRE.test(doc.cookie)){return decodeURIComponent(RegExp['$1']);}return '';},
	setCookie : function(n,v,e){doc.cookie=n+'='+escape(v)+';expires='+e.toGMTString()+';path=/';},
	addEvent  : function(eventName,element,fn){element.attachEvent ? element.attachEvent("on"+eventName,fn) : element.addEventListener(eventName,fn,false)},
	creEle : function(stype,atts){
			var ele = null;
			if(typeof(stype)=='undefined' || stype=='') stype='div';
			try{ele = doc.createElement(stype);
				if(typeof(atts) != 'undefined' && atts!=null && typeof(atts)=='object'){
					for(var attr in atts){
						if(attr=='class'){
							ele.setAttribute('className',atts[attr]);
							ele.setAttribute('class',atts[attr]);
						}else if(attr=='style'){
							ele.style.cssText=ele.style.cssText+atts[attr];
						}else{
							ele.setAttribute(attr,atts[attr]);
						}
					}
				}
			}
			catch(e){
				alert(e.name + ':' + e.message);
			}
			return ele;
	},
	addChild : function(pe,ce){pe.appendChild(ce);},
	player : function(id,src,w,h,extra_px){	
			var thtml="";
			if(isChrome){
				thtml="<object id='_cs_rich_flash_"+id+"' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,24,0'";
				thtml+=" width='"+(w)+"' height='"+(h)+"' align='middle'><param name='movie' value='"+src+"'><param name='quality' value='high'>";
				thtml+="<param name='wmode' value='transparent'><param name='scale' value='exactfit'><embed pluginspage='http://www.macromedia.com/go/getflashplayer'";
				thtml+=" width='"+(w+extra_px)+"' height='"+(h+extra_px)+"' align='middle' type='application/x-shockwave-flash' src='"+src+"' scale='exactfit' quality='high' wmode='transparent'></embed></object>";
			
			}else{
				thtml="<object id='_cs_rich_flash_"+id+"' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,24,0'";
				thtml+=" width='"+(w)+"' height='"+(h)+"' align='middle'><param name='movie' value='"+src+"'><param name='quality' value='high'>";
				thtml+="<param name='wmode' value='transparent'><param name='scale' value='exactfit'><embed pluginspage='http://www.macromedia.com/go/getflashplayer'";
				thtml+=" width='"+w+"' height='"+h+"' align='middle' type='application/x-shockwave-flash' src='"+src+"' scale='exactfit' quality='high' wmode='transparent'></embed></object>";
			}
			return thtml;
	},
	show : function(e,p){
		var bd = doc.getElementsByTagName('body')[0];
		if(p!=null && p=='start')
			bd.insertBefore(e, bd.firstChild);
		else
			bd.appendChild(e);
	},
	isExist : function(id){
		var obj = doc.getElementById(id);
		return (obj == null || obj == undefined) ? false : true;
	},
	reSetPos : function(sid,offset){
		if(sid == undefined || sid == '' || sid == null) return;
		var bdy = (doc.compatMode.toLowerCase() == 'css1compat') ? doc.documentElement:doc.body;
		var obj=doc.getElementById(sid);
		if(obj!=null){
			var mh = obj.offsetHeight;
			var mw = obj.offsetWidth;
			var bw = (offset == undefined || offset==null) ? 0 : offset;
			obj.style.top = bdy.scrollTop+bdy.clientHeight-mh-2*bw +'px';
			obj.style.left= bdy.scrollLeft+bdy.clientWidth-mw-2*bw +'px';
		}
	},
	isTop : function(myself){
		var _check_other = ['div','iframe'];
		for(var t=0; t<_check_other.length; t++){
			var divs = doc.getElementsByTagName(_check_other[t]);
			var len = divs.length;
			for(var i=0;i<len;i++){
			    var flag = false;
			    for(var self in myself){
					if(myself[self]==divs[i].id){
						flag = true;break;
					}
				}
				if (flag){ 
					//divs[i].style.zIndex = 2147483647;
				}else if(divs[i].style.zIndex >= 2147483000){ 
					divs[i].style.zIndex=divs[i].style.zIndex - 100 ; 
					//divs[i].style.zIndex  = 1000; 
				}
			}
		}
	},
	stopPropagation : function(e) {  
		e = e || window.event;  
		if(e.stopPropagation) {  
			e.stopPropagation();  
		} else {  
			e.cancelBubble = true;  
		}
	}
};
rich.isFixed = function(){var q=rich.getVer();return (rich.other||(q>=7 && doc.compatMode!='BackCompat')) ? true : false;};
rich.ffb = function(a){try{var img = new Image();img.src = rich.ffb_url+a;}catch(e){alert(e.name + ':' + e.message);}};
rich.crePlayer = function( id ,src,w,h,dst,ep,extra_px){
	var css = {
		id		:	'_cs_rich_flash_div_'+id ,
		style	:	isChrome?( 'margin:0;padding:0;width:'+(w+extra_px)+'px;height:'+(h+extra_px)+'px;') : ( 'margin:0;padding:0;width:'+w+'px;height:'+h+'px;')
	};
	var div = rich.creEle('div',css);
	var css_tran = {style : 'position:absolute;z-index:1'};
	var div_sub = rich.creEle("div",css_tran);
	rich.addChild(div,div_sub);
	//click-img
	var clickId = "cs_click_"+parseInt(Math.random()*1000);
	var alink = rich.creEle('a',{href : dst+'&ext='+ep,target : '_blank',id: clickId });
	rich.addChild(div_sub,alink);
	var image = rich.creEle('img',{src : rich.img_dot,border : 0,width : w,height : h,style:'background-color:transparent'});
	rich.addChild(alink,image);
	///
	div.innerHTML = div.innerHTML + rich.player(id,src,w ,h , extra_px);
	rich.addEvent('click',div,function(e){var img=new Image();img.src=rich.track_url+ep;});
	rich.addEvent('mouseup',div,function(e){if (e.button != 0x0 && e.button != 0x1) {try{document.getElementById(id).click();}catch(e){var event = document.createEvent("MouseEvents");event.initEvent("click",true,true);document.getElementById(id).dispatchEvent(event);}}});
	return div;
};
rich.creClose=function(css,maxhour){
	var div = rich.creEle('div',css);
	if(css.closetype){
		var a = rich.creEle('a',{href : css.dsturl+'&ext='+css.eps,target : '_blank'});
	}else{
		var a = rich.creEle('a',{href : 'javascript:;',target : '_self'});
	}
	var img = rich.creEle('img',{style : 'width:'+div.style.width+'; height:'+div.style.height+'; cursor:pointer',src : rich.img_close,ck : css.id+'_hide',border:0});

	rich.addChild(a,img);
	rich.addChild(div,a);
	img.ck = css.id+'_hide';
	img.keepclose = css.kc;
	img.onclick = function(e){
		e = e || window.event; 
		var src  = e.target || e.srcElement; 
		if(eval(src.keepclose)==true || eval(src.keepclose)=='true'){   
			var ex=new Date();
			ex.setTime(ex.getTime()+((maxhour==null)? 3600000 : 1000*maxhour));
			rich.setCookie(src.ck,1,ex);
		}
		src.parentNode.parentNode.parentNode.style.display="none";
	};
	return div;	
};
rich.creLogo = function(css){
    var div = rich.creEle('div',css);
	var alink = rich.creEle('a',{href : rich.comurl,target : '_blank'});
	var img = rich.creEle('img',{style:'width:60px; height:16px;border-style:none',src : rich.img_logo});
	rich.addChild(alink,img);
	rich.addChild(div,alink);
	return div;
};
rich.init=function(info){
    if(info.keepClose && rich.getCookie(info.div_close_id+'_hide')){
		return;
	}
	var idx  = parseInt(rich.getCookie(info.cookiehead+'_fidx'));	
	if(!idx || idx>=info.asdata.length) idx=0;
	var oItem=info.asdata[idx];idx++;
	var ex=new Date();
	ex.setTime(ex.getTime()+172800000);
	rich.setCookie(info.cookiehead+'_fidx',idx,ex);
	
	var FB = rich.ffb(oItem["eps"]); 
	var itop = rich.curBdy.scrollTop+rich.curBdy.clientHeight-info.h;
	var div_attrs={'id' : info.rich_div_id,'style' :  (rich.isFixed() ? 'position:fixed;bottom:'+info.offset+'px;' : 'position:absolute;top:'+itop+'px;')+'right:'+info.offsetx+'px;overflow:hidden'};
	var _cs_task_   = setInterval(function(){
		if(!rich.isExist(info.rich_div_id)){
			var div_p      = rich.creEle('div',div_attrs);  
			var div_player = rich.crePlayer(info.rich_div_id,oItem['swfurl'],info.w,info.h,oItem['dsturl'],oItem['eps'],info.extra_px);
			var close      = rich.creClose({id : info.div_close_id,style : "z-index:1999999;top:"+info.close_top+"px;left:"+info.close_left+"px;width:"+info.close_width+"px;height:"+info.close_height+"px;position:absolute;display:none;",kc : info.keepClose,dsturl : oItem['dsturl'],eps : oItem['eps'],closetype : info.closetype},info.duration);
			rich.addChild(div_p,div_player);			
			if (!info.keepClose||info.keepClose!=2){rich.addChild(div_p,close)}
			if(info.showlogo){
				var logocss    = {style : 'z-index:1999999;top:'+(info.h-16)+'px;left:'+(info.w-60)+'px; width: 60px; height: 16px; position:absolute;'};
				var logo       = rich.creLogo(logocss);
				rich.addChild(div_p,logo);
			}
			rich.show(div_p);
			document.getElementById(info.rich_div_id).onclick = function(e) {  
				rich.stopPropagation(e);  
			}
			setTimeout(function(){try{document.getElementById(info.div_close_id).style.display='block';}catch(q){}},info.delaytime);
			if(isChrome){
				var _flashPlay = function(step){
					if(step>10){
						return ;
					}
					var _flashObjs = document.getElementById('_cs_rich_flash_'+info.rich_div_id);
					if(_flashObjs!=undefined){
						for(var i in _flashObjs.children){
							if(_flashObjs.children[i]!=undefined && _flashObjs.children[i]['type']=='application/x-shockwave-flash'){
								var _this = _flashObjs.children[i];
								_this['width']	= info.w;
								_this['height']	= info.h;
								break;
							}	
						}
						document.getElementById('_cs_rich_flash_div_'+info.rich_div_id).style.width  = info.w +'px';
						document.getElementById('_cs_rich_flash_div_'+info.rich_div_id).style.height = info.h +'px';
					}else{
						step++;
						setTimeout(function(){
							_flashPlay(step);
						},100);
					}
				};
				setTimeout(function(){
					_flashPlay(1);
				},100);
			}
			
			if(info.flash){
				setTimeout(function(){try{setInterval(function(){var _mystyle=document.getElementById(info.rich_div_id).style;_mystyle.borderStyle='solid';if(_mystyle.borderColor!='red'){_mystyle.borderColor='red'}else{_mystyle.borderColor='yellow'}},400)}catch(q){}},5000);
			}
		}
		if(doc.body) {
			clearInterval(_cs_task_);
		}
	},1000);
	if(!rich.isFixed()){
		setInterval(function(){rich.reSetPos(info.rich_div_id,info.offset);},info.interval);
	}
	var _cs_task_settop = setInterval(function(){rich.isTop(info.self_list)},info.interval);
};
rich.init(x);
})(__cs_rich_info__);