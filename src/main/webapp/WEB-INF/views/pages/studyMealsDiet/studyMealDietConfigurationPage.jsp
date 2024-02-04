<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Study Meal Diet Configuration </title>
<script type="text/javascript">
var mealTypeArr = [];
var periodIdsArr = {};
var mealMenuIds = [];
</script>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.study.meal.diet.config"></spring:message></h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div class='row' style="margin-right: 40%;">
						<div class='col-sm-6'>
							<spring:message code="label.crfData.study"></spring:message> &nbsp;&nbsp;&nbsp;
						</div>
						<div class='col-sm-6'>
							<select name="projectName" id="projectName" class="form-control validate" onchange="projectNameValidation('projectName')">
			    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
			    				<c:forEach items="${smList}" var ="sm">
									<option value="${sm.id}">${sm.projectNo}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				    <div class="col-md-12" id="periodMealsDiv">
					
			      	</div>
				</div>
			</div>
		</div>
	</div>
	<c:url value="/stdMealDietConfig/saveStudyMealDietCofigurationData" var="saveMealDietconfigData"></c:url>
	<form:form action="${saveMealDietconfigData}" method="POST" id="mealDietConfigFrom">
		<input type="hidden" name="projectId" id="projectId">
		<input type="hidden" name="mealDietConfigData" id="mealDietConfigData">
	</form:form>
	<script type="text/javascript">
	  function projectNameValidation(id){
		  var value= $('#'+id).val();
		  if(value != null && value != "" && value != "undefined"){
			  $("#"+id).attr('data-isvalid','true');
				asynchronousAjaxCallBack(mainUrl+ "/stdMealDietConfig/getStudyRelatedMealsDietDetails/" + $("#projectName").val(), studyMealsConfigFunction);
		  }else{
			  $("#"+id).attr('data-errormessage','Select Project.');
			  $("#"+id).attr('data-isvalid','false');
		  }
		  checkElementValidation($("#"+id));
		}
	  
	  function studyMealsConfigFunction(data){
		  	$(".loader").hide();
		  	generatePeriodWiseMealsTablesFunction(data);
	  }	  
	  function generatePeriodWiseMealsTablesFunction(stdMealConfigData){
		  debugger;
		  var htmlStr ='';
		  if(stdMealConfigData.studyPeriods.length > 0){
			   for(var j=0; j<stdMealConfigData.studyPeriods.length; j++){
				  var periodName = stdMealConfigData.studyPeriods[j].periodName;
				  var periodNo = stdMealConfigData.studyPeriods[j].periodNo;
				  periodIdsArr[stdMealConfigData.studyPeriods[j].periodName]= stdMealConfigData.studyPeriods[j].id;
				  if(periodNo != null && periodNo !="" && periodNo != undefined){
					  if(parseInt(periodNo) == 1){
						  htmlStr +='<div class="row"><div class="col-sm-12" style="font-weight:bold; font-size:15px;">'
					          +'<table class="table" style="font-family:"Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;"><thead><tr style="background-color:DodgerBlue; color:white;"><th>'
					          +periodName
					          +'</th>'
					          +'</tr></thead>';
					  }else{
						  htmlStr +='<div class="row"><div class="col-sm-12" style="font-weight:bold; font-size:15px;">'
					          +'<table class="table" style="font-family:"Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;"><thead><tr style="background-color:DodgerBlue; color:white;"><th>'
					          +periodName
					          +'</th>'
					          +'<th style="text-align:right;">'
					          +'<input type="checkbox" name="dietChk" id="dietChk_'+(periodNo-1)+'" onclick="checkBoxClickEvent('+"'"+(periodNo-1)+"'"+')"> Copy From P'+(periodNo-1)
					          +'</th>'
					          +'</tr></thead>';
					  }
				  }
				  htmlStr +='<tbody></tbody></table>'
				          +'</div></div>'
				          +'<div class="row"> <div class="col-sm-12">'
				          +'<table class="table" style="font-size:13px; font-family:"Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;"><thead><tr><th>Time Point</th>'
				          +'<th>Meal Type</th>'
				          +'<th>Meal Menu</th></tr></thead><tbody>'
				          
				   if(stdMealConfigData.mealDtoList.length > 0){
					  for(var i=0; i<stdMealConfigData.mealDtoList.length; i++){
						  var mealId = stdMealConfigData.mealDtoList[i].id+'_'+stdMealConfigData.mealDtoList[i].mealType;
						  mealTypeArr.push(mealId);
						  var mealDietArr = null;
						  var dietLength = Object.entries(stdMealConfigData.mealPlanMap).length;
						  if(dietLength > 0)
						  	mealDietArr = stdMealConfigData.mealPlanMap[stdMealConfigData.mealDtoList[i].mealType];
						  htmlStr += '<tr><td style="vertical-align: middle; padding : 0.35rem;">'
						         			 +stdMealConfigData.mealDtoList[i].sign+stdMealConfigData.mealDtoList[i].timePoint
						          		 +'</td>'
						         		 +'<td style="vertical-align: middle; padding : 0.35rem;">'
						        			  +stdMealConfigData.mealDtoList[i].mealType
						        	     +'</td>'
						        	     +'<td style="padding : 0.35rem;">'
					         			 +'<select name="mealTitle" id="mealTitle_'+periodName+'_'+mealId+'" onchange="mealTitleValidation('+"'mealTitle_"+periodName+'_'+mealId+"'"+')" class="form-control">'
					          					+'<option value="">------Select---------</option>';
					          					if(mealDietArr != null && mealDietArr != "undefined" && mealDietArr.length > 0){
					        	 					 for(var k=0; k<mealDietArr.length; k++){
					        	 						htmlStr +='<option value="'+mealDietArr[k].id+'">'+mealDietArr[k].mealTitle+'</option>';
					        	 					 }
					          					}
					      htmlStr +='</select>'
					      			+'<div style="color:red;" id="mealTitle_'+periodName+'_'+mealId+'_Msg"></div>'
					          		+'</td>'
						            +'</tr>';
					      mealMenuIds.push('mealTitle_'+periodName+'_'+mealId);
					  }
					  htmlStr += '</tbody></table></div></div>'
				  }  
			  }
		  }
		  htmlStr += '<table class="table" style="font-size:13px; font-family:"Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;">'
		          +'<tr align="center"><td colspan="2"><input type="button" id="submitBtn" onclick="submitFunction()" value="Submit" class="btn btn-primary btn-md">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="clearBtn" value="Clear" onclick="clearFormData()" class="btn btn-primary btn-md">'
		  $('#periodMealsDiv').html(htmlStr);
		  generateExistingStudyMealConfigData(stdMealConfigData);
	  }
	  /* if(validateElements($('#form')) */
    function generateExistingStudyMealConfigData(stdMealConfigData){
    	 //Set Existing meal diet values
		  debugger;
		  var mealTpDietLength = Object.entries(stdMealConfigData.mealTPDietMap).length;
		  if(mealTpDietLength > 0){
			  for(var j=0; j<stdMealConfigData.studyPeriods.length; j++){
				  var periodName = stdMealConfigData.studyPeriods[j].periodName;
				  for(var i=0; i<stdMealConfigData.mealDtoList.length; i++){
					  var mealId = stdMealConfigData.mealDtoList[i].id+'_'+stdMealConfigData.mealDtoList[i].mealType;
					  var periodMap = stdMealConfigData.mealTPDietMap[stdMealConfigData.studyPeriods[j].id];
					  if(periodMap != null && periodMap != "undefined"){
						  var dietId = periodMap[mealId];
						  if(dietId != null && dietId != "undefined"){
							  var keyId ='mealTitle_'+periodName+'_'+mealId;
							  $('#'+keyId).val(dietId);
						  }
					  }
				  }
			  }
		  }
	  }
	function clearFormData(){
		debugger;
		$.each(mealMenuIds, function( mindex, midvalue ) {
			$('#'+midvalue).val("");
		});
	}
	function mealTitleValidation(id){
		  var flag = false;
		  var value = $('#'+id).val();
		  if(value == null || value == "" || value == "undefined"){
			  $('#'+id+"_Msg").html("Required Field");
			  flag = false;
		  }else {
			  $('#'+id+"_Msg").html("");
			  flag = true;
		  }
		  return flag;
	}
	function checkBoxClickEvent(period){
		if($('#dietChk_'+period).prop('checked')){
			if(mealTypeArr.length > 0){
				for(var i=0; i<mealTypeArr.length; i++){
					var keyval = 'mealTitle_P'+period+'_'+mealTypeArr[i];
					var nextKey = 'mealTitle_P'+(parseInt(period)+1)+'_'+mealTypeArr[i];
					var value = $('#'+keyval).val();
					if(value != null && value != "" || value != "undefined")
						$('#'+nextKey).val(value);
					else
						$('#'+nextKey).val("");
				}
			}
		}
	}
	var finalDataArr = [];
	function submitFunction(){
		debugger;
		finalDataArr = [];
		$('#mealDietConfigData').val("");
		$('#projectId').val($("#projectName").val());
		var validationFlag = true;
		$.each(mealMenuIds, function( mindex, midvalue ) {
			var mtvFlag = mealTitleValidation(midvalue);
			if(mtvFlag == false)
				validationFlag = false;
		});
		if(mealTypeArr.length > 0){
			$.each(periodIdsArr, function( pindex, pvalue ) {
				$.each(mealTypeArr, function( index, value ) {
					var keyval = 'mealTitle_'+pindex+'_'+value;
					var dietval = $('#'+keyval).val();
					if(dietval != null && dietval != "" && dietval != "undefined"){
						var mealArr = value.split("_");
						var finaldata = pvalue+"@@@"+mealArr[0]+"@@@"+dietval;
						if(finalDataArr.indexOf(finaldata) == -1)
							finalDataArr.push(finaldata);
					}
				});
			});
		}
		$('#mealDietConfigData').val(finalDataArr);
		var mealConfData = $('#mealDietConfigData').val();
		var projectVal = $('#projectId').val();
// 		alert("Finally :"+projectVal+"\n"+mealConfData);
		alert(validationFlag);
        if(validationFlag){
        	if((projectVal != null && projectVal != "" && projectVal != "undefined") && (mealConfData != null && mealConfData != "" && mealConfData != "undefined"))
    			$('#mealDietConfigFrom').submit();
        }
	}
	</script>
</body>
</html>