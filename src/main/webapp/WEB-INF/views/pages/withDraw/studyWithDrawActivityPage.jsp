<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
var valIdsArr = [];
var paramIdsArr = [];
var withdrawnFromVal = "";
var withdrawType ="";
</script>
<style>
#withdrawFromDiv{
  display:none;
}
</style>
</head>
<body>
<div class="row">
	<div class="col-md-12 col-sm-12 ">
		<div class="x_panel">
			<div class="x_title">
				<h2></h2>
					<!-- <ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li class="dropdown"></li>
					</ul> -->
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<br>
				<table class="table table-bordered" style="width: 75%; border: none;">
			       <tr>
			       	   <td style="padding: 0%;"> Subject  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       	   		<input type="radio" name="rdbAction" id="withdrawalRadio" value="Withdrawal" onchange="radioButtonAction('Withdrawal')">Withdrawal
			       	   		&nbsp;&nbsp;&nbsp;&nbsp;
			       	   		<input type="radio" name="rdbAction" id="dropoutRadio" value="DroupOut" onchange="radioButtonAction('DroupOut')">Dropout
			       	   </td>
				   </tr>	
				</table>
				<div id="withdrawFromDiv">
					<table class="table table-bordered" style="width: 75%; border: none;padding: 0;"  >
				       <tr>
				       	   <td style="padding: 0%;">Withdrawn From &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				       	   		<input type="radio" name="withDrawnFromRadio" id="withDrawnFromStudy" value="Study" onchange="withdrawnRadioValidation()">Study
				       	   		&nbsp;&nbsp;&nbsp;&nbsp;
				       	   		<input type="radio" name="withDrawnFromRadio" id="withDrawnFromCurPeriod" value="Current Period" onchange="withdrawnRadioValidation()">Current Period
				       	   <div id="radioMessage" style="color: red; margin-left: 25%;"></div>
				       	   </td>
					   </tr>	
					</table>
				</div>
				<div id="promptMsg" style="color: red; text-align: center; font-size: medium;  font-weight: bold;"></div>
				<table class="table table-bordered" style="width: 100%; border: none; font-size: 15.5px;  padding: 0;" id="actParmDiv">
			       
				</table>
           </div>
	    </div>
	   </div>     
	   <form id="withdrawDataForm"></form>       
 </div>
 <script type="text/javascript">
 function radioButtonAction(value){
	 $('#promptMsg').html("");
	 $('#commentsMsg').html("");
	 $('#withdrawFromDiv').show();
	 withdrawnRadioValidation();
}
 function withdrawnRadioValidation(){
	 withdrawType ="";
	 $('#promptMsg').html("");
	 $('#commentsMsg').html("");
	 var radioFlag = false;
	 withdrawnFromVal = "";
	 if($('#withDrawnFromStudy').is(':checked') || $('#withDrawnFromCurPeriod').is(':checked')){
		 if($('#withDrawnFromStudy').is(':checked'))
			 withdrawnFromVal = $('#withDrawnFromStudy').val();
		 
		 if($('#withDrawnFromCurPeriod').is(':checked'))
			 withdrawnFromVal = $('#withDrawnFromCurPeriod').val();
		 
		 radioFlag = true;
	 }
	 if(radioFlag){
		 $('#radioMessage').html("");
		 $('#actParmDiv').empty();
		 var value ="";
		 if($('#withdrawalRadio').is(':checked'))
			 value = $('#withdrawalRadio').val();
		 if($('#dropoutRadio').is(':checked'))
			 value = $('#dropoutRadio').val();
		 
		 paramIdsArr = [];
		 valIdsArr = [];
		 var dataObj = JSON.parse('${jsonStr}');
		 debugger;
		 var htmlstr = "";
		 if(dataObj != null && dataObj != "undefined"){
			 withdrawType = value;
	    	 var dataList = dataObj.actMap[value];
	    	 if(dataList != null &&  dataList != "undefined" && dataList.length > 0 ){
	    		 for(var i=0; i<dataList.length; i++){
	    			 var paramName = dataList[i].queryName;
	    			 var parmId = dataList[i].id;
	    			 var parmValsArr = dataObj.actValMap[parmId];
	    			 paramIdsArr.push(parmId);
	    			 if(parmValsArr != null && parmValsArr != "undefinde" && parmValsArr.length > 0){
	    				 var valIds = "";
	    				 for(var j=0; j<parmValsArr.length; j++){
	    					 if(j==0){
	    						 htmlstr += '<tr><td>'+paramName+'</td><td>';
	    							htmlstr += '<input type="radio" name="parmValRadio" id="parmaValRadio_'+parmValsArr[j].id+'">'+parmValsArr[j].valueName;
	        						
	    					 }else{
	    						 if(j != parmValsArr.length){
	 								htmlstr += '&nbsp;&nbsp;&nbsp;<input type="radio" name="parmValRadio" id="parmaValRadio_'+parmValsArr[j].id+'">'+parmValsArr[j].valueName;
	 							}else{
	 								htmlstr += '&nbsp;&nbsp;&nbsp;<input type="radio" name="parmValRadio" id="parmaValRadio_'+parmValsArr[j].id+'">'+parmValsArr[j].valueName
	 	    	    				+'</td></tr>';
	 							}
	 	 					}
	    					 if(valIds == "")
	    						 valIds = parmValsArr[j].id;
	    					 else{
	    						 if(valIds != "" && valIds != ",")
	    						 	valIds = valIds +","+parmValsArr[j].id;
	    					 }
	        			 }
	    				 if(valIds != "")
	    				 	valIdsArr[parmId] = valIds;
	    			 }else{
	    				 htmlstr += '<tr>'
	    				         +'<td><input type="checkbox" name="paramName_'+parmId+'" id="paramName_'+parmId+'"> &nbsp;&nbsp;'+paramName+'</td>'
	    				 		 +'</tr>';
	    			 }
	    		 }
	    	 }
	     } 
		 if(htmlstr != ""){
			 htmlstr +='<tr><td colspan=2;>Comments &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea cols="20" rows="5" name="comments" id="comments" style="width:65%;"></textarea><div id="commentsMsg" style="color:red; margin-left: 15%;"></div></td></tr>'
			 		 +'<tr><td colspan=2; align="center"><input type="button" value="Submit" class="btn btn-primary btn-md" onclick="submitFrom()"></td></tr>';
			 $('#actParmDiv').append(htmlstr);
		 }
	 }else $('#radioMessage').html("Please Select One.");
 }
 function submitFrom(){
     var checkedParmsArr = [];
     var checkedParamValArr = [];
	 var subjectVal = $('#subName').val();
	 var subFlag = false;
	 var paramFlag = false;
	 var commentsFlag = false;
	 if(subjectVal == null || subjectVal == "" || subjectVal == "undefined"){
		 $('#subNameMsg').html("Required Field.");
		 subFlag = false;
		 $(window).scrollTop(0);
	 }else{
		 $('#subNameMsg').html("");
		 subFlag = true;
	 }
	 //Check parameters checked or not
	 for(var i=0; i<paramIdsArr.length; i++){
		 if($('#paramName_'+paramIdsArr[i]).is(':checked')){
			 paramFlag = true;
			 checkedParmsArr.push(paramIdsArr[i]);
		 }
		 if(valIdsArr.length > 0){
			 if(valIdsArr[paramIdsArr[i]] != null && valIdsArr[paramIdsArr[i]] != "" && valIdsArr[paramIdsArr[i]] != "," && valIdsArr[paramIdsArr[i]] != "undefined"){
				 var valIds = valIdsArr[paramIdsArr[i]].split(',');
				 if(valIds.length > 0){
					 var chkPram = paramIdsArr[i];
					 for(j=0; j<valIds.length; j++){
						 if(valIds[j].trim() != ","){
							 if($('#parmaValRadio_'+valIds[j]).is(':checked')){
								 checkedParamValArr.push(chkPram+"@@@"+valIds[j]);
								 paramFlag = true;
							 } 
						 }
					 } 
				 }
			 }
		 }
	 }
	 if(paramFlag == false){
		 $('#promptMsg').html("Please Select at least one reason.");
		 $(window).scrollTop(0);
	 }else{
		 $('#promptMsg').html("");
	 }
	 var comments = $('#comments').val();
	 if(comments != null && comments != "" && comments != "undefined"){
		 $('#commentsMsg').html("");
		 commentsFlag = true;
	 }else{
		 $('#commentsMsg').html("Required Field.");
		 commentsFlag = false;
	 }
// 	 alert(subFlag +"::"+ paramFlag +"::"+ commentsFlag);
	 if(subFlag && paramFlag && commentsFlag){
		 var formData = $('#withdrawDataForm .exclude').serializeArray();
		 var csrfToken = getCsrfToken();
		 formData.push({name: csrfToken.parameterName ,value: csrfToken.token});
		 if(checkedParmsArr.length == 0)
			 formData.push({name: "parameterIds" ,value:0});
		 else formData.push({name: "parameterIds" ,value:checkedParmsArr});
		 
		 if(checkedParamValArr.length == 0)
			 formData.push({name: "parameterValueIds" ,value:0});
		 else formData.push({name: "parameterValueIds" ,value:checkedParamValArr});
		 formData.push({name: "comments" ,value:comments});
		 formData.push({name: "volunteer" ,value:$("#subName").attr('data-value')});
		 formData.push({name: "projectId" ,value:$('#studyName').val()});
		 formData.push({name: "activityId" ,value:$('#crfName').val()});
		 formData.push({name: "withdrawLevel" ,value:withdrawnFromVal}); 
		 formData.push({name: "type" ,value:withdrawType});
		 
		 var urlVal = $("#mainUrl").val() +'/disocontinue/saveWithdrawOrDropOutData';
		 $.ajax({
			 	url: urlVal,
				type:'POST',
				data: formData,
				success:function(e){
					if(e.Success === true){
						 displayMessage("success",e.Message);
						 $('#actParmDiv').empty();
						 $('#withdrawFromDiv').hide();
						 $('#crfName').val("");
						 $('#subName').val("");
					}else{
						displayMessage("error",e.Message);
						$('#actParmDiv').empty();
						$('#withdrawFromDiv').hide();
						$('#crfName').val("");
						$('#subName').val("");
					}
					$(".loader").hide();
				},
				error:function(e){
					displayMessage("success",e.Message);
					$(".loader").hide();
				}
		});
	 }
 }
 </script>
</body>
</html>