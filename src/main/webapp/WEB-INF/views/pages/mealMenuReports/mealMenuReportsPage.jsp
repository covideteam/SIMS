<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Meal Menu</title>
<style type="text/css">
#mealReportDiv {
	  display: none;
	}
	#pdfIconDiv{
	   display: none;
	}
</style>
<script type="text/javascript">
var mealDietIdsArr = [];
var malDietIdsInfoArr = {};
</script>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.mealMenuReport"></spring:message></h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<input type="hidden" name="projectVal" id="projectVal">
				    <input type="hidden" name="allName" id="allName" value='<spring:message code="label.melaMenuReport.all"></spring:message>'>
					<input type="hidden" name="selectVal" id="selectVal" value='<spring:message code="label.sdSelect"></spring:message>'>
					<div class='row' style="margin-left: 15%; margin-right: 25%;">
						<div class='col-sm-2' style="margin-top: 8px;">
							<spring:message code="label.melaMenuReport.study"></spring:message> &nbsp;&nbsp;&nbsp;
						</div>
						<div class='col-sm-4'>
							<select name="projectName" id="projectName" class="form-control validate" onchange="projectNameValidation('projectName')">
			    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
			    				<c:forEach items="${smList}" var ="sm">
									<option value="${sm.id}">${sm.projectNo}</option>
								</c:forEach>
							</select>
						</div>
						<div class='col-sm-2' style="margin-top: 8px;">
							<spring:message code="label.melaMenuReport.priod"></spring:message> &nbsp;&nbsp;&nbsp;
						</div>
						<div class='col-sm-4'>
							<select name="priodName" id="priodName" class="form-control validate" onchange="priodNameValidation('priodName')">
			    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
			    			</select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12"><br/></div>
					</div>
					<div class="row">
						<div class="col-sm-12"><div id="pdfIconDiv"><a href="#" onclick="generateMealReportPdf()"><i class="fa fa-file-pdf-o" style="font-size:40px; color:red;" id="pdfIcon"></i></a></div>
						<div id="pdfMsg" style="color: red; text-align: center; font-weight: bold;"></div>
					</div>
					</div>
					<div class="row">
						<div class="col-sm-12"><br/></div>
					</div>
				   <div class="col-md-12" id="mealReportDiv">
						<table class="table table-bordered table-striped" style="width: 100%;" id="mealReportDataTable">
							<thead>
								<tr>
									<th><spring:message code="label.melaMenuReport.mealId"></spring:message></th>
									<th><spring:message code="label.melaMenuReport.timePoint"></spring:message></th>
									<th><spring:message code="label.melaMenuReport.malType"></spring:message></th>
									<th><spring:message code="label.melaMenuReport.priod"></spring:message></th>
									<th><spring:message code="label.melaMenuReport.menuId"></spring:message></th>
									<th><spring:message code="label.melaMenuReport.pdrOrder"></spring:message></th>
									<th><input type="checkbox" name="selectAll" id="selectAll" onclick="selectAllCheckBoxFunction()"></th>
								</tr>
							</thead>
						<tbody id="mealReportData">
						
						</tbody>
					 </table>
			      </div>
				</div>
			</div>
		</div>
		<c:url value="/mealMenuReports/generateMealsMenuPdf" var="mealReport"></c:url>
		<form:form action="${mealReport}" method="POST" id="mealReportFrom">
		    <input type="hidden" name="studyId" id="studyId">
		    <input type="hidden" name="dietIds" id="dietIds">
		    <input type="hidden" name="melsReportInfo" id="melsReportInfo">
		</form:form>
	</div>
	<script type="text/javascript">
	var mealReportData = null;
    function projectNameValidation(id){
		$('#projectVal').val("");
		 var value= $('#'+id).val();
		  if(value != null && value != "" && value != "undefined"){
			  $('#projectVal').val(value);
			  $("#"+id).attr('data-isvalid','true');
			  mealReportData = synchronousAjaxCall(mainUrl+ "/mealMenuReports/getStduyMealReportDetails/" + $("#projectName").val());
			  showPeriodsData();
		  }else{
			  $("#"+id).attr('data-errormessage','Select Project.');
			  $("#"+id).attr('data-isvalid','false');
			  var selectValue = $('#selectVal').val();
			  $('#priodName').empty();
			  $('#priodName').append('<option value="">---'+selectValue+'---</option>');
		  }
	}
    function priodNameValidation(id){
    	var value = $('#'+id).val();
    	if(value != null && value != "" && value != undefined){
    		$("#"+id).attr('data-errormessage','');
			  $("#"+id).attr('data-isvalid','true');
			  generateMealReportDataTable(value);
    	}else{
    		$('#mealReportDiv').hide();
    		$('#pdfIconDiv').hide();
    	}
    }
    function showPeriodsData(){
    	var allDataStr = $('#allName').val();
    	var selectValue = $('#selectVal').val();
		  $('#priodName').empty();
		  $('#priodName').append('<option value="">---'+selectValue+'---</option>');
    	if(mealReportData.spmList != null && mealReportData.spmList != undefined && mealReportData.spmList.length > 0){
    		$('#priodName').append('<option value="All">'+allDataStr+'</option>');
    		for(var i=0; i<mealReportData.spmList.length; i++){
    			$('#priodName').append('<option value="'+mealReportData.spmList[i].id+'">'+mealReportData.spmList[i].periodName+'</option>');
    		}
    	}
    }
    function generateMealReportDataTable(periodValue){
    	var htmlStr = "";
    	$("#selectAll").prop('checked', false);
    	$('#pdfIconDiv').hide();
		$('#mealReportData').empty();
		$('#mealReportDataTable').dataTable().fnClearTable();
		$('#mealReportDataTable').dataTable().fnDraw();
		$('#mealReportDataTable').dataTable().fnDestroy(); 
		if(mealReportData != null && mealReportData != undefined){
			if(mealReportData.slcmdMap != null && mealReportData.slcmdMap != undefined){
    			var mapLength = Object.keys(mealReportData.slcmdMap).length;
				if(mapLength > 0){
					var melaReportMap = new Map(Object.entries(mealReportData.slcmdMap));
					for (let [key, value] of melaReportMap) {
						if(periodValue == "All" || periodValue == key){
							var mealsReportDataArr = value;
							if(mealsReportDataArr != undefined && mealsReportDataArr.length > 0){
								for(var i=0; i<mealsReportDataArr.length; i++){
									htmlStr +='<tr>'
										 +'<td>'+mealReportData.tpOrderMap[mealsReportDataArr[i].tpSign+mealsReportDataArr[i].timePoint]+'</td>'
										 +'<td>'+mealsReportDataArr[i].tpSign+mealsReportDataArr[i].timePoint+'</td>'
										 +'<td>'+mealsReportDataArr[i].mealType+'</td>'
										 +'<td>'+mealsReportDataArr[i].periodName+'</td>'
										 +'<td>'+mealsReportDataArr[i].mealTitle+'</td>'
										 +'<td><input type="number" name="pdfOrder_'+mealsReportDataArr[i].id+'" id="pdfOrder_'+mealsReportDataArr[i].id+'" style="width:35%; height:40%;" class="form-control"></td>'
										 +'<td><input type="checkbox" name="mealReport_'+mealsReportDataArr[i].id+'" id="mealReport_'+mealsReportDataArr[i].id+'" value="'+mealsReportDataArr[i].mealDietId+'"></td>'
										 +'</tr>';
									mealDietIdsArr.push(mealsReportDataArr[i].id);
									malDietIdsInfoArr[mealsReportDataArr[i].id] = mealsReportDataArr[i].id+"@@@"+mealsReportDataArr[i].periodName+"@@@"+mealReportData.tpOrderMap[mealsReportDataArr[i].tpSign+mealsReportDataArr[i].timePoint]+"@@@"+
																					mealsReportDataArr[i].mealType+"@@@"+mealsReportDataArr[i].mealTitle+"@@@"+mealsReportDataArr[i].mealDietId;
								}
							}
						}
					}
				}
    		}
    	}
    	if(htmlStr != ""){
    		$('#pdfIconDiv').show();
    		$('#mealReportData').append(htmlStr);
			$('#mealReportDataTable').DataTable();
			$('#mealReportDiv').show();
		}else {
			$('#pdfIconDiv').hide();
			$('#mealReportDataTable').DataTable();
			$('#mealReportDiv').show();
		}
    }
    function selectAllCheckBoxFunction(){
    	debugger;
    	if($("#selectAll").prop('checked') == true){
    	    if(mealDietIdsArr.length > 0){
    	    	$.each(mealDietIdsArr, function(key, value) {
    	    		$("#mealReport_"+value).prop('checked', true);
				});
    	    }
    	}else{
    		if(mealDietIdsArr.length > 0){
    	    	$.each(mealDietIdsArr, function(key, value) {
    	    		$("#mealReport_"+value).prop('checked', false);
				});
    	    }
    	}
    }
    
    function generateMealReportPdf(){
    	$('#pdfMsg').html("");
    	var dietIdsArr = [];
    	var mealDietDataInfoArr = [];
    	$.each(mealDietIdsArr, function(key, dietId) {
    		var tempArr = {};
    		if($("#mealReport_"+dietId).prop('checked') == true){
    			var reportOrder = $('#pdfOrder_'+dietId).val();
    			dietIdsArr.push(dietId);
    			if(reportOrder == null || reportOrder == "" || reportOrder == undefined){
    				reportOrder = 0;
    			}
    			mealDietDataInfoArr.push(malDietIdsInfoArr[dietId]+"@@@"+reportOrder);
    		}
		}); 
    	debugger;
    	$('#melsReportInfo').val(mealDietDataInfoArr);
    	$('#dietIds').val(dietIdsArr);
    	$('#studyId').val($('#projectVal').val());
    	if(dietIdsArr.length > 0){
    		$('#mealReportFrom').submit();
    	}else $('#pdfMsg').html("Please Select At least one Record.");
    	

    }
	</script>
</body>
</html>