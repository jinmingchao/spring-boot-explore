<script type="text/javascript">

function eclick(event){
	var e = event.data.e;
	var className =event.data.className;
    var inputName = event.data.inputName;
    showTitle(e,className);
	var id = $(e).attr("id");
	$(e).parent().parent().nextAll("input[id="+inputName+"]").val(id);	
}
function eeclick(event){
	var e = event.data.e;
	var className =event.data.className;
    var inputName = event.data.inputName;
    showTitle(e,className);
	var id = $(e).attr("id");
	$(e).parent().parent().nextAll("input[id="+inputName+"]").val(id);	
	$(e).parent().parent().parent().nextAll("#feelingBox:first").show();
	
}
  function getHtml(id,className){
	  var html = "";
		if (className == ".logistics") {
			if (id == 1)
				html = "不满意";
			else if (id == 2)
				html = "一般";
			else if (id == 3)
				html = "还不错";
			else if (id == 4)
				html = "满意";
			else if (id == 5)
				html = "非常满意";
			else
				html = "给供应商物流配送时效打分";
		} else if (className == ".package") {
			if (id == 1)
				html = "破损严重，引起产品损坏";
			else if (id == 2)
				html = "有破损但未引起产品损坏";
			else if (id == 3)
				html = "轻微破损";
			else if (id == 4)
				html = "无明显破损";
			else if (id == 5)
				html = "完好无缺";
			else
				html = "给供应商配送包装完好度打分";
		} else if (className == ".attitude") {
			if (id == 1)
				html = "恶劣";
			else if (id == 2)
				html = "一般";
			else if (id == 3)
				html = "还不错";
			else if (id == 4)
				html = "满意";
			else if (id == 5)
				html = "非常满意";
			else
				html = "给供应商配送人员态度打分";
		} else if (className == ".description") {
			if (id == 1)
				html = "很不相符";
			else if (id == 2)
				html = "不相符";
			else if (id == 3)
				html = "比较相符";
			else if (id == 4)
				html = "相符";
			else if (id == 5)
				html = "完全相符";
			else
				html = "给商品描述是否相符打分";
		} else {
			if (id == 1)
				html = "质量很差";
			else if (id == 2)
				html = "比较差";
			else if (id == 3)
				html = "一般般";
			else if (id == 4)
				html = "还不错";
			else if (id == 5)
				html = "很好";
			else
				html = "给商品质量打分";

		}
	  return html;
  }
  
	function showTitle(e, className) {
		var id = $(e).attr("id");
		var div = $(e).parent().parent();
		$(e).attr("class", "active");
		$(e).prevAll("li").attr("class", "active");
		$(e).nextAll("li").attr("class", "");
		var html = getHtml(id,className);
		div.next(className).html(html);

	}

	function emouseover(event) {
		var e = event.data.e;
		var className = event.data.className;
		showTitle(e, className);
	}
	function emouseout(event) {
		var p = $(this);
		var inputName = event.data.inputName;
		var className = event.data.className;
		var num = p.nextAll("input[id=" + inputName + "]").val()==''?0:p.nextAll("input[id=" + inputName + "]").val();    
		p.find("li").each(function() {
			var id = $(this).attr("id");
			if (id <= num)
				$(this).attr("class", "active");
			else
				$(this).attr("class", "");
		});
		var html =getHtml(num,className);
		p.next(className).html(html);
	}

	$(function() {
		$("#speedValuefiveStar").find("li").each(function() {
			$(this).on("click", {
				e : this,
				className : ".logistics",
				inputName : "speedValue"
			}, eclick);
			$(this).on("mouseover", {
				e : this,
				className : ".logistics"
			}, emouseover);
		});

		$("#speedValuefiveStar").on("mouseout", {className : ".logistics",
			inputName : "speedValue"}, emouseout);

		
		$("#fullValuefiveStar").find("li").each(function() {
			$(this).on("click", {
				e : this,
				className : ".package",
				inputName : "fullValue"
			}, eclick);
			$(this).on("mouseover", {
				e : this,
				className : ".package"
			}, emouseover);
		});
		$("#fullValuefiveStar").on("mouseout", {className : ".package",inputName : "fullValue"}, emouseout);
		
		
		$("#attitudeValuefiveStar").find("li").each(function() {
			$(this).on("click", {
				e : this,
				className : ".attitude",
				inputName : "attitudeValue"
			}, eclick);
			$(this).on("mouseover", {
				e : this,
				className : ".attitude"
			}, emouseover);
		});
		$("#attitudeValuefiveStar").on("mouseout", {className : ".attitude",inputName : "attitudeValue"}, emouseout);
		
		
		$("div[name=matchValuefiveStar]").each(function(index, e) {
			$(e).find("li").each(
					function(){
					$(this).on("click", {
						e : this,
						className : ".description",
						inputName : "matchValue*"
					}, eeclick);
					$(this).on("mouseover", {
						e : this,
						className : ".description"
					}, emouseover);
					
					//$div.parent().nextAll("#feelingBox").show();
				});
				$(e).on("mouseout", {className : ".description",inputName : "matchValue*"}, emouseout);
				
			});
		
		$("div[name=qualityValuefiveStar]").each(function(index, e) {
			$(e).find("li").each(
					function(){
					$(this).on("click", {
						e : this,
						className : ".quality",
						inputName : "qualityValue*"
					}, eeclick);
					$(this).on("mouseover", {
						e : this,
						className : ".quality"
					}, emouseover);
					
					//$div.parent().nextAll("#feelingBox").show();
				});
				$(e).on("mouseout", {className : ".quality",inputName : "qualityValue*"}, emouseout);
			});
		
		
	});
	
	function showTooltip(target) {  
	 	$(target).children(".close").show();
	}  
	function hideTooltip(target) {  
		$(target).children(".close").hide();
	}  
	function showReminder(ele,orderId){
		$("#objid").val(orderId);
        var winH = $(window).height();
        var winW = $(window).width();
        var left = winW/2;
        var top = winH/2 + 30;
        $.blockUI({
			message: $("#" + ele),
			css:{
	            border:'none',
	            width:'auto',
	            top:top,
	            left:left,
	            textAlign: '',
	            cursor:	'auto'
			}
		});
	}
	function alertRes(response,flag) {
	     if(flag){rebackfn(response);}else{alert(response);}
	 } 
	 // 选择完文件后。  
	 function fileChange(value,f_suffix) { 
	     $("#viewfile").text(value); 
	     // 判断文件的后缀名。 
	     var filesubfix = value.substring(value.lastIndexOf(".") + 1, value.length).toLowerCase(); 
	     var ext = f_suffix.toLowerCase(); //可以使用的后缀名 
	     if (ext.indexOf(filesubfix) == -1){ 
	        alert("只允许上传" + f_suffix + "格式的文件!"); 
	        return; 
	     } 
	 }  
	//上传文件后清空
	function clearFileForm(){	
		$("#objid").val("");
		$("#fileInput").val("");
		$("#viewfile").text("未选择图片");
	 }
	 // 上传按钮提交。 
	 function uploadsubmit() {
	     if($("#fileInput").val()==""){
	     	alert("请选择文件!")
	     	return ;
	     } 
		 $("#fileform").attr("target","uploadiframe"); 
		 var action ="${ctx}/uploadFileServlet?param=flag=|code=XPURCHASE_ORDER_EVAT_IMAGE|accUploader=1|waterconfig=WartMarkPath@WartMarkText|watertext=null|isInsert=null|customPath=null|isOriName=null";
		 action=action+"|objid="+$("#objid").val();
		 $("#fileform").attr("action",action);
		 $("#fileform").submit(); 	
	 } 
	  function rebackfn(response){
		 alert("文件上传成功！"); 
		 var orderId=$("#objid").val();
		 updateFileList(orderId); // 刷新列表。  
		 clearFileForm();        	
	  }
	  function deleteAtt(accessId,attId,orderId) { 
		     if(confirm("确认删除?")){  
		        $.ajax({ 
		 		   type: "GET", 
		          url: "${ctx}/myAccount/orders/deleteEvaluateAtt.html", 
		          data: "accessId="+accessId+"&attId="+attId, 
		 		   success: function(msg) {
		 			  if(msg =='0000'){
		              alert("图片删除成功！"); 
		 			  updateFileList(orderId); // 刷新列表。  
		 			   }else{
			        		 alert("图片删除失败！");  
			        	 }
		 		   } 
		    	}); 
		     }  
		 } 	
	  function updateFileList(orderId) {
			 hideReminder("popDiv");
			 var $attDiv =$("#attDiv"+orderId);
			 var $shaitDiv= $("#shait"+orderId);
			 var rand=Math.random();
			  $.post("${ctx}/myAccount/orders/getEvaluateAttList.html?rand="+rand,
			    		 {"orderId" : orderId},
			    		 function(data){
			    			 var att=eval("("+data+")");
			    			 var len= att.length;
			    			 var html="";
			    			 for(var i=0; i<len; i++){
			    				 html=html+"<dd onmouseover='showTooltip(this);' onmouseout='hideTooltip(this);'><img width='41' height='41'  src='"+att[i].accessUrl+"' /><div class='close' style='display:none'>";	
			    				 html=html+"<a href='#' onclick='deleteAtt(\""+att[i].accessId+"\",\""+att[i].id+"\",\""+orderId+"\")';><img src='${postCtx}/images/images/common/buy_closebtn.jpg'/></a>";
				    			 html=html+"</div></dd>";
				    			 }
			    			 var restLen= 5-len;
			    			 html+="<p class='shai_p'>共<span class='red'>"+len+"</span>张，还可上传<span class='red'>"+restLen+"</span>张</p>";
			    			 $attDiv.html(html);
			    			 if(parseInt(restLen) <= 0){
			    				 $shaitDiv.html("<a href='#'><img src='${postCtx}/images/images/common/shaitu.jpg'/></a>"); 	 
			    			 }else{
			    				 $shaitDiv.html("<a href='#' onclick=\"showReminder(\'fileDiv\',\'"+orderId+"\')\"><img src='${postCtx}/images/images/common/shaitu.jpg'/></a>"); 	
			    			 }
			    			});
		 } 
	    function hideReminder(ele){
			$("#" + ele).hide();
			$.unblockUI();
		}
	   	$(function(){
      		$(".ffii").hover(function(){
      			$(this).next(".f_big").show();
      		},function(){
      			$(this).next(".f_big").hide();
      		})
      	})
</script>
<div class="pageName">
	<span class="right">当前位置： <a
		href="${ctx}/myAccount/baseInfo/baseInfo.html">我的账户></a> <a
		href="${ctx}/myAccount/orders/ordersList.html">我的订单</a>
	</span><#if soExistFlag=1><span class="t16 pl15">我的订单——查看评价</span><#else><span
		class="t16 pl15">我的订单——提交评价</span></#if>
</div>

		
	<div class="reviews" id="reviews">
            
     <div class="re_top"></div>
     
     
     <form id="orderForm" name="orderForm"
	action="${ctx}/myAccount/orders/saveOrderEvaluate.html" method="post"
	style="display: inline;">
	<input id="soOrderId" name="soOrderId" type="hidden"
		value="${soOrderId}"> <input id="orderId" name="orderId"
		type="hidden" value="${orderId}"> <input id="soExistFlag"
		name="soExistFlag" type="hidden" value="${soExistFlag}">
		<input id="supId" name="supId" type="hidden" value="${supId}">
		<input id="supCode" name="supCode" type="hidden" value="${supCode}">
		<input id="pOrderId" name="pOrderId" type="hidden" value="${pOrderId}">
	<!--查询区域-->
	<div class="re_box">
	  <div class="re_box_bottom clx" style="margin-top:0px;">
		<#if orderSoEval??>
		<div class="re_edit">
			<div class="title">物流配送时效：</div>
			<div class="star">
			<ul>
				<li <#if orderSoEval.speedValue gte 1>class="active"</#if>></li>
				<li <#if orderSoEval.speedValue gte 2>class="active"</#if>></li>
				<li <#if orderSoEval.speedValue gte 3>class="active"</#if>></li>
				<li <#if orderSoEval.speedValue gte 4>class="active"</#if>></li>
				<li <#if orderSoEval.speedValue gte 5>class="active"</#if>></li>
			<ul>
			</div>
			<div class="logistics">
			<#if orderSoEval.speedValue??>
				<#if orderSoEval.speedValue=1>
			 		   不满意
				</#if>
				<#if orderSoEval.speedValue=2>
					一般
				</#if>
				<#if orderSoEval.speedValue=3>
					还不错
				</#if>
				<#if orderSoEval.speedValue=4>
					满意
				</#if>
				<#if orderSoEval.speedValue gte 5>
					非常满意
				</#if>
			</#if>
			</div>
		</div>


		<div class="re_edit">
			<div class="title">配送包装完好度：</div>
			<div class="star" >
			<ul>
				<li <#if orderSoEval.fullValue gte 1>class="active"</#if>></li>
				<li <#if orderSoEval.fullValue gte 2>class="active"</#if>></li>
				<li <#if orderSoEval.fullValue gte 3>class="active"</#if>></li>
				<li <#if orderSoEval.fullValue gte 4>class="active"</#if>></li>
				<li <#if orderSoEval.fullValue gte 5>class="active"</#if>></li>
				</ul>
			</div>
			<div class="package">
			<#if orderSoEval.fullValue??>
				<#if orderSoEval.fullValue=1>
			    	破损严重，引起产品损坏
				</#if>
				<#if orderSoEval.fullValue=2>
					有破损但未引起产品损坏
				</#if>
				<#if orderSoEval.fullValue=3>
					轻微破损
				</#if>
				<#if orderSoEval.fullValue=4>
					轻微破损
				</#if>
				<#if orderSoEval.fullValue gte 5>
					完好无缺
				</#if>
			</#if>
			</div>
		</div>


		<div class="re_edit">
			<div class="title">服务人员态度：</div>
			<div class="star">
			<ul>
			<li <#if orderSoEval.attitudeValue gte 1>class="active"</#if>></li>
				<li <#if orderSoEval.attitudeValue gte 2>class="active"</#if>></li>
				<li <#if orderSoEval.attitudeValue gte 3>class="active"</#if>></li>
				<li <#if orderSoEval.attitudeValue gte 4>class="active"</#if>></li>
				<li <#if orderSoEval.attitudeValue gte 5>class="active"</#if>></li>
				</ul>
			</div>
			<div class="attitude">
			<#if orderSoEval.attitudeValue??>
				<#if orderSoEval.attitudeValue=1>
			  		 恶劣
				</#if>
				<#if orderSoEval.attitudeValue=2>
					一般
				</#if>
				<#if orderSoEval.attitudeValue=3>
					还不错
				</#if>
				<#if orderSoEval.attitudeValue=4>
					满意
				</#if>
				<#if orderSoEval.attitudeValue gte 5>
					非常满意
				</#if>
			</#if>
			</div>
		</div>
		<#else>

		<div class="re_edit">
			<div class="title">物流配送时效：</div>
			<div class="star" id="speedValuefiveStar" name="speedValuefiveStar">
			<ul>
				<li id="1"></li>
				<li id="2"></li>
				<li id="3"></li>
				<li id="4"></li>
				<li id="5"></li>
				</ul>
			</div>
			<div class="logistics">给供应商物流配送时效打分</div>

			<input id="speedValue" name="speedValue" type="hidden" />
		</div>



		<div class="re_edit">
			<div class="title">配送包装完好度：</div>
			<div class="star" id="fullValuefiveStar" name="fullValuefiveStar">
				<ul>
				<li  id="1"></li>
				<li  id="2"></li>
				<li  id="3"></li>
				<li  id="4"></li>
				<li  id="5"></li>
				</ul>
			</div>
			
			<div class="package">给供应商配送包装完好度打分</div>
			<input id="fullValue" name="fullValue" type="hidden" />
		</div>


		<div class="re_edit">
			<div class="title">服务人员态度：</div>
			<div class="star" id="attitudeValuefiveStar" name="attitudeValuefiveStar">
			<ul>
				<li  id="1"></li>
				<li  id="2"></li>
				<li  id="3"></li>
				<li  id="4"></li>
				<li  id="5"></li>
			</ul>
			</div>
			 <div class="attitude">给供应商配送人员态度打分</div>
			<input id="attitudeValue" name="attitudeValue" type="hidden" />
		</div>
		</#if>
	</div>
	</div>
  <!--no end-->
	<#if orderEvalList??>
		<#list orderEvalList as orderEval> 
			<#list orderEval.subOrderInfo.goodsInfoList as goods> 
			<#if orderEval_index =1 && !packgeFinished>
          <div class="other_com"><img src="${postCtx}/images/img/starinfo.png" width="363" height="56" /></div>
	</#if>
	     <!--no.1-->
	     
        <div <#if orderEval_index =0 && !packgeFinished>class="re_box re_nbox"<#else>class="re_box"</#if>>
	    <!--box top-->
	    <div class="re_box_top clx">
		<div class="re_box_img">
			<a
				href="${ctx}/goods/product/goodsDetail.html?sn=${goods.pxPrdNo!''}" target="_blank">
				<img width="90" height="90"
				src="${imageCtx}n1/${goods.prdImgPath!''}"
				onerror="this.src='${postCtx}/images/images/common/default/50.jpg'">
			</a>
		</div>

		  <dl class="re_box_info">
		    	<dt>${goods.prdName!''}</dt>
                            <dd>[商品编号]  ${goods.pxPrdNo!''}</dd>
                            <dd>[子订单号]  ${orderEval.subOrderInfo.orderId!''}</dd>
                            <dd>[总计] ￥${goods.prdTotalPrice?string('0.00')}</dd>
           </dl>
      </div>
			
         <!--box top end-->
         <div class="re_box_bottom clx">
         
		<#if orderEval.orderEvaluate??>
	
            <div class="re_edit">
			<div class="title">商品描述相符：</div>
			<div class="star">
			    <ul>
				<li <#if orderEval.orderEvaluate.matchValue gte 1>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.matchValue gte 2>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.matchValue gte 3>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.matchValue gte 4>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.matchValue gte 5>class="active"</#if>></li>
				</ul>
			</div>
			<div class="description">
			<#if orderEval.orderEvaluate.matchValue??>
				<#if orderEval.orderEvaluate.matchValue=1>
			  		 很不相符
				</#if>
				<#if orderEval.orderEvaluate.matchValue=2>
					不相符
				</#if>
				<#if orderEval.orderEvaluate.matchValue=3>
					比较相符
				</#if>
				<#if orderEval.orderEvaluate.matchValue=4>
					相符
				</#if>
				<#if orderEval.orderEvaluate.matchValue gte 5>
					完全相符
				</#if>
			<#else>
				给商品描述是否相符打分
			</#if>
			</div>
		</div>
		
		  <div class="re_edit">
			<div class="title">商品质量：</div>
			<div class="star">
			 <ul>
				<li <#if orderEval.orderEvaluate.qualityValue gte 1>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.qualityValue gte 2>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.qualityValue gte 3>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.qualityValue gte 4>class="active"</#if>></li>
				<li <#if orderEval.orderEvaluate.qualityValue gte 5>class="active"</#if>></li>
			</ul>
			</div>
			<div class="quality">
			<#if orderEval.orderEvaluate.qualityValue??>
				<#if orderEval.orderEvaluate.qualityValue=1>
			 		 质量很差
				</#if>
				<#if orderEval.orderEvaluate.qualityValue=2>
					比较差
				</#if>
				<#if orderEval.orderEvaluate.qualityValue=3>
					一般般
				</#if>
				<#if orderEval.orderEvaluate.qualityValue=4>
					还不错
				</#if>
				<#if orderEval.orderEvaluate.qualityValue gte 5>
					很好
				</#if>
			<#else>
				给商品质量打分
				
			</#if>
			
			</div>
		</div>
		<div class="re_edit">
			<div class="title">使用感受：</div>
			<div class="text2">${orderEval.orderEvaluate.content!''}</div>	
		</div>
		
		<div class="re_edit">
		<div class="title">晒图片：</div>
		<div class="figure">
		<#if attMap??&&attMap[orderEval.subOrderInfo.orderId]??>
		<#list attMap[orderEval.subOrderInfo.orderId] as att>
		<dd>


		</dd>
		</#list>
		</#if>
		</div>
		</div>
		<#else> 
		<input id="orderIds" name="orderIds" type="hidden"
			value="${orderEval.subOrderInfo.orderId}"> <input
			type="hidden" id="pxPrdNo${orderEval.subOrderInfo.orderId}"
			name="pxPrdNo${orderEval.subOrderInfo.orderId}"
			value="${goods.pxPrdNo!''}" />
				
		  <div class="re_edit">
			<div class="title">商品描述相符：</div>
			<div class="star" id="matchValuefiveStar" name="matchValuefiveStar">
			   <ul>
				<li id="1"></li>
				<li id="2"></li>
				<li id="3"></li>
				<li id="4"></li>
				<li id="5"></li>
				</ul>
			</div>
			<div class="description">给商品描述是否相符打分</div>

			<input type="hidden" id="matchValue${orderEval.subOrderInfo.orderId}"
				name="matchValue${orderEval.subOrderInfo.orderId}" class="txt" value=""
				style="width: 220px" />
		</div>
		<div class="re_edit">
			<div class="title">商品质量：</div>
			<div class="star" id="qualityValuefiveStar" name="qualityValuefiveStar">
			<ul>
				<li id="1"></li>
				<li id="2"></li>
				<li id="3"></li>
				<li id="4"></li>
				<li id="5"></li>
				</ul>
			</div>
			<div class="quality">给商品质量打分</div>
			<input type="hidden" id="qualityValue${orderEval.subOrderInfo.orderId}"
				name="qualityValue${orderEval.subOrderInfo.orderId}" class="txt" value=""
				style="width: 220px" />
		</div>
		
		<div class="re_edit"  id="feelingBox" name="feelingBox" <#if orderEval_index gt 0>style="display:none;"</#if>>
			<div class="title">使用感受：</div>
           <div class="text2">
			<textarea class="tarea"
				id="content${orderEval.subOrderInfo.orderId}"
				name="content${orderEval.subOrderInfo.orderId}" rows="3" cols="20"
				maxlength="50" onkeydown="calChar(this);" onkeyup="calChar(this);" value=""></textarea>
			    <p>您还可以输入 <span id="descCharNum" style="font-color:red">50</span>个字</p></div>
		</div>
	 <div class="re_edit">
		<div class="title">晒图片：</div>
		<div class="figure">
		<dt>
		<div class="shait" id="shait${orderEval.subOrderInfo.orderId}">
			<a href="#" <#if attMap??&&attMap[orderEval.subOrderInfo.orderId]??&&attMap[orderEval.subOrderInfo.orderId]?size gt 4]><#else>onclick="showReminder('fileDiv','${orderEval.subOrderInfo.orderId}');"</#if>><img src="${postCtx}/images/images/common/shaitu.jpg" /></a>
		</div>
		<div id="attDiv${orderEval.subOrderInfo.orderId}">
		<#if attMap??&&attMap[orderEval.subOrderInfo.orderId]??>
		<#list attMap[orderEval.subOrderInfo.orderId] as att>
		<dd onmouseover="showTooltip(this);" onmouseout="hideTooltip(this);">
		<img width='40' height='40' src="${postCtx}/images/images/common/40.jpg"/>
		<div class="close">
			<a href="#" onclick="deleteAtt('${att.accessId}','${att.id}','${orderEval.subOrderInfo.orderId}');"><img src="${postCtx}/images/images/common/buy_closebtn.jpg" /></a>
		</div>
		</dd>
		</#list>
		</#if>
	 <p class="shai_p">
		共<span class="red"><#if attMap??&&attMap[orderEval.subOrderInfo.orderId]??>${attMap[orderEval.subOrderInfo.orderId]?size}<#else>0</#if></span>张，还可上传<span class="red"><#if attMap??&&attMap[orderEval.subOrderInfo.orderId]??>${5-attMap[orderEval.subOrderInfo.orderId]?size}<#else>5</#if></span>张
	</p>
	</div>
	</dt>
  </div>
		 </div>
		</#if>
	</div>
	</div>
	</#list> </#list> </#if>

    <div class="re_btn">
		<input type="button" onclick="javascript:cancle();"  class="re_tj re_qx mr10" value="返回"/>
	<#if finished != 1>
		<input type="button" onclick="javascript:saveEval();" class="re_tj"  value="提交评价" />
		<span class="ml20" style="color:red;"></span>
	</#if>
	</div>
</form>
</div>
<div id="fileDiv"  class="popDiv" style="width:700px;height:auto;margin-left:-350px;margin-top:-200px; display:none;">
     <div class="text_border" style="padding-bottom:0">
		 <h1 class="t14" style="text-align: center;"><span style="font-size:20px;">上传附件</span><a href="javascript:;" class="commonw_close" onclick="javascript:hideReminder('fileDiv');">X</a></h1>
			<div class="title">
			   <form action="${ctx}/uploadFileServlet?param=flag=|code=XPURCHASE_ORDER_EVAT_IMAGE|accUploader=1|waterconfig=WartMarkPath@WartMarkText|watertext=null|isInsert=null|customPath=null|isOriName=null" enctype="multipart/form-data" method="post" id="fileform">
					<input type="hidden" id="curPageInput" width="0">
					<input type="hidden" id="objid" name="objid">
					<div class="center">	
					<a href="javascript:;" class="file">
					<span style="font-size:16px;">选取文件</span>
				    <input type="file" name="fileInput" id="fileInput" onchange="fileChange(this.value,&quot;*.jpg,*.gif,*.png&quot;);">
				   </a>
				   <span id="viewfile" style="font-size:14px;">未选择文件</span>	
				   <br /><br />
					<p style="font-size:16px;">文件类型：<span style="font-size:14px;">jpg、gif、png</span></p>
				</form>
				<div class="footer">
						<input type="button" onclick="uploadsubmit();" value="上    传">
						<input type="button" onclick="javascript:hideReminder('fileDiv');" value="关    闭">
					</div>
			</div>
		</div>
	</div>
	      <iframe style="display: none" name="uploadiframe"> </iframe>
