var XMLHttp = {
    _objPool: [],
    
    _getInstance: function ()
    {
        for (var i = 0; i < this._objPool.length; i ++)
        {
            if (this._objPool[i].readyState == 0 || this._objPool[i].readyState == 4)
            {
                return this._objPool[i];
            }
        }
        // IE5中不支持push方法
        this._objPool[this._objPool.length] = this._createObj();
        return this._objPool[this._objPool.length - 1];
    },

    _createObj: function ()
    {
        if (window.XMLHttpRequest)
        {
            var objXMLHttp = new XMLHttpRequest();
        }
        else
        {
            //var MSXML = ['MSXML2.XMLHTTP.5.0', 'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
            var MSXML = ['Microsoft.XMLHTTP', 'MSXML2.XMLHTTP', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.5.0'];
            for(var n = 0; n < MSXML.length; n ++)
            {
                try
                {
                    var objXMLHttp = new ActiveXObject(MSXML[n]);        
                    break;
                }
                catch(e)
                {
                }
            }
         }          
		
		// mozilla某些版本没有readyState属性
        if (objXMLHttp.readyState == null)
        {
            objXMLHttp.readyState = 0;
            objXMLHttp.addEventListener("load", function ()
                {
                    objXMLHttp.readyState = 4;
                    
                    if (typeof objXMLHttp.onreadystatechange == "function")
                    {
                        objXMLHttp.onreadystatechange();
                    }
                },  false);
        }
        return objXMLHttp;
    },
    
	// 发送请求(方法[post,get], 地址, 数据, 回调函数, 区域对象ID)
    sendReq: function (method, url, data, callback, divId)
    {
        var objXMLHttp = this._getInstance();
        with(objXMLHttp)
        {
            try
            {
             	 // 加随机数防止缓存
                if (url.indexOf("?") > 0)
                {
                    url += "&randnum=" + Math.random();
                }
                else
                {
                    url += "?randnum=" + Math.random();
                }

                open(method, url, true);                
               	// 设定请求编码方式
                setRequestHeader('Content-Type', 'text/html;charset=GBK');
                setRequestHeader("Pragma","No-cache");
                setRequestHeader("Expires",0);
                setRequestHeader("Cache-Control","no-cache");                	
				setRequestHeader("If-Modified-Since","0");
				
                send(data);                
                onreadystatechange = function ()
                {                      	             
                    if (objXMLHttp.readyState == 4 && (objXMLHttp.status == 200 || objXMLHttp.status == 304))
                    {
                        callback(objXMLHttp, divId);
                    }                     
                }
            }
            catch(e)
            {
                alert(e);
            }
        }
    }
};
/** 发送Request请求 **/
function mulitHttpReq(dwrUrl,dwrId) 
{ 
	var plIdObj = window.document.getElementById(dwrId)
	if(plIdObj != null && plIdObj)
	{	
		plIdObj.innerHTML =	"<font color='red'>请稍等，正在获取数据...</font>";			
		XMLHttp.sendReq('GET', dwrUrl, '', httpReqCallback, dwrId);
	}
} 

/** Request请求回调 **/
function httpReqCallback(httpReq, divId) { 
	var plIdObj = window.document.getElementById(divId)
	//var vjson=eval('('+httpReq.responseText+')');
	eval("var retData = "+'('+httpReq.responseText+')');
	/**
	if(plIdObj != null && plIdObj)
	{
		if(httpReq.responseText.length > 0)
		{
			//_xml_htmlParse(plIdObj, httpReq.responseText);
			
			alert(vjson.result.msg);
			window.location.href = "index.html";
			httpReq.responseText = "";
		}
		else
			plIdObj.style.display = "none";
	}
	**/
	var msg = retData.msg;
		
		//判断msg
		if (msg == "1") {
			
			Cookies.set('_un',retData.result.userName);
			Cookies.set('_ln',retData.result.loginName);
			Cookies.set('_orgId',retData.result.hosOrgId);
			Cookies.set('_ordName',retData.result.hosOrgName);
			window.location.href = "http://192.168.1.114:8200/ehealthdc/EHR/index.html";
		}else if (msg == "0") {
			alert("用户或密码错误，登录失败！");
		}
} 


/** 脚本识别 **/
function _xml_htmlParse(partId, viewData) { 
	var regexp1 = /<script(.|\n)*?>(.|\n|\r\n)*?<\/script>/ig; 
	var regexp2 = /<script(.|\n)*?>((.|\n|\r\n)*)?<\/script>/im; 

	/* draw the html first */ //alert(viewData.replace(regexp1, ""))
	partId.innerHTML = viewData.replace(regexp1, "");
	var result = viewData.match(regexp1); 
	if (result) 
	{ 
		for (var i = 0; i < result.length; i++) 
		{ 
			var realScript = result[i].match(regexp2); 
			//_xml_executeScript(realScript[2], partId); 
			eval(realScript[2])
		} 
	}
}

function show()
{
    divObj = document.getElementById("Layer2");
  var medicalTD = document.getElementById("medicalTD");
    divObj.style.width =(medicalTD.offsetWidth ) + "px";
	if(divObj != null && divObj)
	{
	    if(divObj.style.display == "none")
		{
			divObj.style.display = "";
			var x = medicalTD.offsetLeft;
			 while(medicalTD=medicalTD.offsetParent) 
   			{ 
     		 		x += medicalTD.offsetLeft;   
   			} 
   			divObj.style.left = x + "px";
		}			
	}
}

function hiden()
{
    divObj = document.getElementById("Layer2");
	if(divObj != null && divObj)
	{
	    if(divObj.style.display == "")
			divObj.style.display = "none";
			
	}
}

function changeDiv()
{
	//alert("cookieddd");

	var allcookies = document.cookie;
var cookie_pos = allcookies.indexOf("username");

// 如果找到了索引，就代表cookie存在，
// 反之，就说明不存在。
if (cookie_pos != -1)
{
  document.getElementById("welcome").innerText = " 已登录 ";
}

   var divObj = document.getElementById("Layer2");
   var medicalTD = document.getElementById("medicalTD");
   var x = medicalTD.offsetLeft;
   while(medicalTD=medicalTD.offsetParent) 
   { 
      x += medicalTD.offsetLeft;   
   } 
   if(divObj != null && divObj) 
   {        
   		divObj.style.left = x + "px";
   }
}
function changeDiv1(tag){
   var tag1=tag;
  // if(tag !=null){
      document.getElementById("welcome").innerText = "内容2";
  // }

}
function submsg()
{   
	 
		var xmlhttp = new ActiveXObject("Msxml2.XMLHTTP.3.0");
		var obj1,obj2,obj3,str,obj4;
		obj1 =document.getElementById("hospital").value ;
		   
		obj2 =document.getElementById("password") ;
		str = "http://vitaphone.cn:9090/login_t.aspx?";
		str +="card=";
		str +=obj1;
		 
		str +="&phone=";
		str +=obj2.value;
		xmlhttp.open("GET",str, false);
		xmlhttp.send();

		//var   str1   =   <%=session.getAttribute("username")%>
		//document.getElementById("welcome").innerHTML = "str1"; 
		 alert("yanzhneg ");
		document.getElementById("welcome").innerHTML = " 已登录 ";
	 
		document.cookie = "username" + escape(obj2);


}
function submsg_doctor()
{   
	 
	var xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        
	var obj1,obj2,obj3,str,obj4;
        obj1 =document.getElementById("hospital").value ;
	obj2 =document.getElementById("username") ;
	obj3 =document.getElementById("password") ;
        str = "http://vitaphone.cn:9000/login_t.aspx?";
	str +="hospital=";
	str +=obj1;
	str +="&username=";
	str +=obj2.value;
	str +="&password=";
	str +=obj3.value;
	
      try 
{ 
    xmlhttp.open("GET",str, true);
} 
catch (e) 
{ 
    alert(e.message); 
} 
  xmlhttp.send();



	//var   str1   =   <%=session.getAttribute("username")%>
	//document.getElementById("welcome").innerHTML = "str1"; 
    document.getElementById("welcome").innerText = " 已登录 ";

	document.cookie = "username" + escape(obj2);


}