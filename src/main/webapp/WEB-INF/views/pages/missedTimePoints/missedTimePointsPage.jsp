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
#missedTpsReportDiv {
	  display: none;
	}
	#pdfIconDiv{
	   display: none;
	}
</style>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.missedTps.title"></spring:message></h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<input type="hidden" name="projectVal" id="projectVal">
				    <input type="hidden" name="allName" id="allName" value='<spring:message code="label.melaMenuReport.all"></spring:message>'>
					<input type="hidden" name="selectVal" id="selectVal" value='<spring:message code="label.sdSelect"></spring:message>'>
					<div class='row' style="margin-left: 5%; margin-right: 15%;">
						<div class='col-sm-2' style="margin-top: 8px;">
							<spring:message code="label.melaMenuReport.study"></spring:message> &nbsp;&nbsp;&nbsp;
						</div>
						<div class='col-sm-2'>
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
						<div class='col-sm-2'>
							<select name="priodName" id="priodName" class="form-control validate" onchange="priodNameValidation('priodName')">
			    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
			    			</select>
						</div>
						<div class='col-sm-2' style="margin-top: 8px;">
							<spring:message code="label.missedTps.activity"></spring:message> &nbsp;&nbsp;&nbsp;
						</div>
						<div class='col-sm-2'>
							<select name="activity" id="activity" class="form-control validate" onchange="activityValidation('activity')">
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
				   <div class="col-md-12" id="missedTpsReportDiv">
				  </div>
				</div>
			</div>
		</div>
		<c:url value="/missedtpsReports/generateMealsMenuPdf" var="mealReport"></c:url>
		<form:form action="${mealReport}" method="POST" id="mealReportFrom">
		    <input type="hidden" name="studyId" id="studyId">
		    <input type="hidden" name="periodId" id="periodId">
		    <input type="hidden" name="activityCode" id="activityCode">
		</form:form>
	</div>
	<script type="text/javascript">
	var mtpsData = null;
	var previousStudy = "";
	var selectValue = "";
    function projectNameValidation(id){
		$('#projectVal').val("");
		selectValue = $('#selectVal').val();
		$('#activity').empty();
		$('#activity').append('<option value="">---'+selectValue+'---</option>');
		$('#activity').empty();
		$('#activity').append('<option value="">---'+selectValue+'---</option>');
		$('#missedTpsReportDiv').hide();
		$('#missedTpsReportData').empty();
		$('#pdfIconDiv').hide();
		 var value= $('#'+id).val();
		  if(value != null && value != "" && value != "undefined"){
			  $('#projectVal').val(value);
			  $("#"+id).attr('data-isvalid','true');
			  mtpsData = synchronousAjaxCall(mainUrl+ "/missedtpsReports/getMissedTpsReportDetails/" + $("#projectName").val());
			  generatePeriodsData();
		  }else{
			  $("#"+id).attr('data-errormessage','Select Project.');
			  $("#"+id).attr('data-isvalid','false');
		  }
	}
    function priodNameValidation(id){
    	selectValue = $('#selectVal').val();
    	$('#activity').empty();
		$('#activity').append('<option value="">---'+selectValue+'---</option>');
		$('#missedTpsReportDiv').hide();
   	 	$('#pdfIconDiv').hide();
   	    $('#missedTpsReportDiv').hide();
		$('#missedTpsReportData').empty();
    	var value = $('#'+id).val();
    	if(value == null || value == "" && value == undefined){
    		 $("#"+id).attr('data-errormessage','Select Project.');
			 $("#"+id).attr('data-isvalid','false');
		}else{
    		$("#"+id).attr('data-errormessage','');
			 $("#"+id).attr('data-isvalid','true');
			 generateActivities();
    	}
    }
    
	function activityValidation(id){
		debugger;
	   	var value = $('#'+id).val();
	   	if(value == null || value == "" && value == undefined){
	   		 $("#"+id).attr('data-errormessage','Select Project.');
			 $("#"+id).attr('data-isvalid','false');
			 $('#missedTpsReportDiv').hide();
	    	 $('#pdfIconDiv').hide();
	   }else{
	   		$("#"+id).attr('data-errormessage','');
			 $("#"+id).attr('data-isvalid','true');
			 var periodId = $('#priodName').val();
	    	 var actCode = value;
	    	 showMissedTimePointsData(periodId, actCode);
	   	}
	}
	function generatePeriodsData(){
		var allDataStr = $('#allName').val();
    	selectValue = $('#selectVal').val();
		  $('#priodName').empty();
		  $('#priodName').append('<option value="">---'+selectValue+'---</option>');
    	if(mtpsData.spmList != null && mtpsData.spmList != undefined && mtpsData.spmList.length > 0){
    		$('#priodName').append('<option value="0">'+allDataStr+'</option>');
    		for(var i=0; i<mtpsData.spmList.length; i++){
    			$('#priodName').append('<option value="'+mtpsData.spmList[i].id+'">'+mtpsData.spmList[i].periodName+'</option>');
    		}
    	}
	}
	function generateActivities(){
		if(mtpsData != null && mtpsData != undefined){
			var allDataStr = $('#allName').val();
	    	var selectValue = $('#selectVal').val();
			  $('#activity').empty();
			  $('#activity').append('<option value="">---'+selectValue+'---</option>');
	    	if(mtpsData.actList != null && mtpsData.actList!= undefined && mtpsData.actList.length > 0){
// 	    		$('#activity').append('<option value="0">'+allDataStr+'</option>');
	    		for(var i=0; i<mtpsData.actList.length; i++){
	    			$('#activity').append('<option value="'+mtpsData.actList[i].activityCode+'">'+mtpsData.actList[i].actName+'</option>');
	    		}
	    	}
		}
	}
	function showMissedTimePointsData(pid, actvity){
		debugger;
		var htmlStr = '';
		var tbodyStr = '';
		$('#pdfIconDiv').hide();
		$('#missedTpsReportDataTable').empty();
		$('#missedTpsReportData').empty();
		$('#missedTpsReportDataTable').dataTable().fnClearTable();
		$('#missedTpsReportDataTable').dataTable().fnDraw();
		$('#missedTpsReportDataTable').dataTable().fnDestroy(); 
		if(mtpsData != null && mtpsData != undefined){
			var periodsArr = [];
			if(pid != 0)
				periodsArr.push(pid);
			else{
				$.each(mtpsData.priodNamesMap, function(periodId, periodName) {
					periodsArr.push(periodId);
				});
				
			}
			if(periodsArr.length > 0){
				for(var i=0; i<periodsArr.length; i++){
					if(actvity == "DosingCollection" || actvity == "MealsCollection"){
						htmlStr += '<table class="table table-bordered table-striped" style="width: 100%;" id="missedTpsReportDataTable">'
						    +'<thead>'
								+'<tr>'
									+'<th>Subject Number</th>'
									+'<th>Period</th>'
									+'<th>Time Point</th>'
									+'<th>Date</th>'
									+'<th>Schedule Time</th>'
								+'</tr>'
							+'</thead>'
							+'<tbody id="missedTpsReportData">'
							+'</tbody>'
		 				+'</table>';
					}
					if(actvity == "DosingCollection"){
							var doseMapLength = Object.keys(mtpsData.dosetpMap).length;
							if(doseMapLength > 0){
								var periodMap = mtpsData.dosetpMap[periodsArr[i]];
								if(periodMap != null && periodMap != undefined){
									$.each(periodMap, function(subject, value) {
										tbodyStr +='<tr>';
										$.each(value, function(tp, valList) {
											$.each(valList, function( index, tpDto ){
												var tpDate = "";
												if(tpDto.tpDate != null && tpDto.tpDate != undefined)
													tpDate = tpDto.tpDate;
												tbodyStr +='<td>'+subject+'</td>'
														 +'<td>'+mtpsData.priodNamesMap[periodsArr[i]]+'</td>'
														 +'<td>'+tpDto.timePoint+'</td>'
														 +'<td>'+tpDate+'</td>'
														 +'<td>'+tpDto.time+'</td></tr>';
											});
										});
									});
								}
							}
					}else if(actvity == "MealsCollection"){
						var mealsMapLength = Object.keys(mtpsData.mtpMap).length;
						if(mealsMapLength > 0){
							var periodMap = mtpsData.mtpMap[periodsArr[i]];
							if(periodMap != null && periodMap != undefined){
								$.each(periodMap, function(subject, value) {
									tbodyStr +='<tr>';
									$.each(value, function(tp, valList) {
										$.each(valList, function( index, tpDto ){
											var tpDate = "";
											if(tpDto.tpDate != null && tpDto.tpDate != undefined)
												tpDate = tpDto.tpDate;
											tbodyStr +='<td>'+subject+'</td>'
													 +'<td>'+mtpsData.priodNamesMap[periodsArr[i]]+'</td>'
													 +'<td>'+tpDto.timePoint+'</td>'
													 +'<td>'+tpDate+'</td>'
													 +'<td>'+tpDto.time+'</td></tr>';
										});
									});
								});
							}
						}
					}else if(actvity == "SampleCollection"){
						htmlStr += '<table class="table table-bordered table-striped" style="width: 100%;" id="missedTpsReportDataTable">'
						    +'<thead>'
								+'<tr>'
									+'<th>Subject Number</th>'
									+'<th>Period</th>'
									+'<th>Time Point</th>'
									+'<th>Vacutainer No.</th>'
									+'<th>Date</th>'
									+'<th>Schedule Time</th>'
								+'</tr>'
							+'</thead>'
							+'<tbody id="missedTpsReportData">'
							+'</tbody>'
		 				+'</table>';
		 				
						var samplesMapLength = Object.keys(mtpsData.stpMap).length;
						if(samplesMapLength > 0){
							var periodMap = mtpsData.stpMap[periodsArr[i]];
							if(periodMap != null && periodMap != undefined){
								$.each(periodMap, function(subject, value) {
									tbodyStr +='<tr>';
									$.each(value, function(tp, valList) {
										$.each(valList, function( index, tpDto ){
											var tpDate = "";
											if(tpDto.tpDate != null && tpDto.tpDate != undefined)
												tpDate = tpDto.tpDate;
												tbodyStr +='<td>'+subject+'</td>'
													 +'<td>'+mtpsData.priodNamesMap[periodsArr[i]]+'</td>'
													 +'<td>'+tpDto.timePoint+'</td>'
													 +'<td>'+tpDto.vacutainerNo+'</td>'
													 +'<td>'+tpDate+'</td>'
													 +'<td>'+tpDto.time+'</td></tr>';
										});
									});
								});
							}
						}
					}else if(actvity == "StudyExecutionVitals"){
						htmlStr += '<table class="table table-bordered table-striped" style="width: 100%;" id="missedTpsReportDataTable">'
						    +'<thead>'
								+'<tr>'
									+'<th>Subject Number</th>'
									+'<th>Period</th>'
									+'<th>Time Point</th>'
									+'<th>Position</th>'
									+'<th>Date</th>'
									+'<th>Schedule Time</th>'
								+'</tr>'
							+'</thead>'
							+'<tbody id="missedTpsReportData">'
							+'</tbody>'
		 				+'</table>';
		 				
						var vitalMapLength = Object.keys(mtpsData.vtpMap).length;
						if(vitalMapLength > 0){
							var periodMap = mtpsData.vtpMap[periodsArr[i]];
							if(periodMap != null && periodMap != undefined){
								$.each(periodMap, function(subject, value) {
									tbodyStr +='<tr>';
									$.each(value, function(tp, valList) {
										$.each(valList, function( index, tpDto ){
											var tpDate = "";
											if(tpDto.tpDate != null && tpDto.tpDate != undefined)
												tpDate = tpDto.tpDate;
												tbodyStr +='<td>'+subject+'</td>'
													 +'<td>'+mtpsData.priodNamesMap[periodsArr[i]]+'</td>'
													 +'<td>'+tpDto.timePoint+'</td>'
													 +'<td>'+tpDto.position+'</td>'
													 +'<td>'+tpDate+'</td>'
													 +'<td>'+tpDto.time+'</td></tr>';
										});
									});
								});
							}
						}
					}
					if(tbodyStr != "")
						$('#pdfIconDiv').show();
		    		$('#missedTpsReportDiv').html(htmlStr);
					$('#missedTpsReportDiv').show();
					$('#missedTpsReportData').append(tbodyStr);
					$('#missedTpsReportDataTable').DataTable();
				}
			}
		}
	}
	function generateMealReportPdf(){
		
	}
	</script>
</body>
</html>