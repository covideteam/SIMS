<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Data CrfPage</title>
<script type="text/javascript">
$( document ).ready(function() {
	$('#periodsDiv').hide();
// 	$('#subjectDiv').hide();
	$('#activityDiv').hide();
	$('#generateDiv').hide();
// 	$('#volunteerDiv').hide();
});
var volIds = [];
</script>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Data Crf</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li><a class="close-link"><i class="fa fa-close"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
						
					<form id="dataCrfForm">
						<input type="hidden" name="selectVal" id="selectVal" value="<spring:message code="label.ruleSelect"></spring:message>" />
						<input type="hidden" name="allTextVal" id="allTextVal" value="<spring:message code="label.crfData.all"></spring:message>" />
						<table class="table table-striped">
							<tr>
								<td>
									<div class="form-group row">
										<label for="subjectReplaceDiv" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.crfData.study"></spring:message></label>
										<div class="col-sm-5">
												<select name="studyName" id="studyName" class="form-control" onchange="studyNameValidation('studyName', 'studyNameMsg')">
														<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
														<c:forEach items="${dcpDto.smList}" var="sm" varStatus="st">
															<option value="${sm.id}" id="sm_${st.count}">${sm.projectNo}</option>
														</c:forEach>
												</select>
												<div id="studyNameMsg" style="color: red;"></div>
											</div>
									</div>
								</td>
								
							<%-- 	<td id="periodsDiv">
<!-- 									<div id="periodsDiv"> -->
										<div class="form-group row">
											<label for="periodName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.crfData.period"></spring:message></label>
											<div class="col-sm-8">
												<select name="periodName" id="periodName" class="form-control" onchange="periodNameValidation('periodName', 'periodNameMsg')">
														<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
												</select>
												<div id="periodNameMsg" style="color: red;"></div>
											</div>
										</div>
<!-- 									</div> -->
								</td> --%>
								<td id="subjectDiv" style="display: none;">
<!-- 									<div id="subjectDiv"> -->
										<div class="form-group row">
											<label for="subject" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.crfData.subject"></spring:message></label>
											<div class="col-sm-5">
												<select name="subject" id="subject" class="form-control" onchange="subjectValidation('subject', 'subjectMsg')">
														<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
												</select>
												<div id="subjectMsg" style="color: red;"></div>
											</div>
										</div>
<!-- 									</div> -->
								</td>
								<td id="volunteerDiv" style="display: none;">
<!-- 									<div id="subjectDiv"> -->
										<div class="form-group row">
											<label for="subject" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.crfData.voluteers"></spring:message></label>
											<div class="col-sm-5">
												<select name="volunteer" id="volunteer" class="form-control" onchange="volunteerValidation('volunteer', 'volunteerMsg')">
														<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
												</select>
												<div id="volunteerMsg" style="color: red;"></div>
											</div>
										</div>
<!-- 									</div> -->
								</td>
							<%-- 	<td id="activityDiv">
<!-- 									<div id="activityDiv"> -->
										<div class="form-group row">
											<label for="activity" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.crfData.activity"></spring:message></label>
											<div class="col-sm-5">
												<select name="activity" id="activity" class="form-control" onchange="activityValidation('activity', 'activityMsg')">
														<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
												</select>
												<div id="activityMsg" style="color: red;"></div>
											</div>
										</div>
<!-- 									</div> -->
								</td> --%>
								<td id="generateDiv">
<!-- 									<div id="generateDiv"> -->
										<div class="form-group row">
											<div class="col-sm-5">
												<input type="button" onclick="generateDataPdf()" value='<spring:message code="label.crfData.generate"></spring:message>' class="btn btn-warning btn-sm">
											</div>
										</div>
<!-- 									</div> -->
								</td>
								
							</tr>
						</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	<c:url value="/crfData/generateDataCrf" var="pdfFrom"></c:url>
 	<form:form action="${pdfFrom}" method="GET" id="pdfGenForm" target="_blank">
	    <input type="hidden" name="studyId" id="studyId">
	     <input type="hidden" name="periodId" id="periodId">
	      <input type="hidden" name="subjectId" id="subjectId">
	       <input type="hidden" name="activityId" id="activityId">
	        <input type="hidden" name="reportType" id="reportType">
	        <input type="hidden" name="volIds" id="volIds">
	        
	</form:form>
	<script type="text/javascript">
	function studyNameValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value != null && value != "-1" && value != "undefined"){
			var crfData = synchronousAjaxCall(mainUrl+ "/crfData/getStudyCrfDataDetails/" + value);
			//alert(crfData);
			debugger;
			if(crfData != null && crfData != "" && crfData != "undefined"){
				$('#periodsDiv').show();
				$('#subjectDiv').show();
				$('#activityDiv').show();
				$('#generateDiv').show();
				$('#volunteerDiv').show();
				
				$('#periodName').empty();
				$('#subject').empty();
				$('#activity').empty();
				
				var selectVal = $('#selectVal').val();
				var allText = $('#allTextVal').val();
// 				var d = JSON.parse(crfData);
// 					$('#periodName').append('<option value="-1">---'+selectVal+'---</option>');
					$('#subject').append('<option value="-1">---'+selectVal+'---</option>');
// 					$('#volunteer').append('<option value="-1">---'+selectVal+'---</option>');
// 					$('#activity').append('<option value="-1">---'+selectVal+'---</option>'); 
					
// 					$('#periodName').append('<option value="0">'+allText+'</option>');
                    if(crfData.studySubList.length > 0)
						$('#subject').append('<option value="0">'+allText+'</option>');
                    if(crfData.stdVolList.length > 0)
						$('#volunteer').append('<option value="0">'+allText+'</option>');
// 					$('#activity').append('<option value="0">'+allText+'</option>');
				/* for(var i=0; i<crfData.spmList.length; i++){
					$('#periodName').append('<option value='+crfData.spmList[i].id+'>'+crfData.spmList[i].periodName+'</option>');
				} */
				for(var j=0; j<crfData.studySubList.length; j++){
					/* if(crfData.studySubList[j].stdSubjectId != null)
						$('#subject').append('<option value='+crfData.studySubList[j].stdSubjectId.reportingId.id+'>'+crfData.studySubList[j].stdSubjectId.subjectNo+'</option>');
					else 
						$('#subject').append('<option value='+crfData.studySubList[j].reportingId.id+'>'+crfData.studySubList[j].subjectNo+'</option>');
				 */
					$('#subject').append('<option value='+crfData.studySubList[j].reportingId.id+'>'+crfData.studySubList[j].subjectNo+'</option>');
					
				}
				for(var j=0; j<crfData.stdVolList.length; j++){
					/* if(crfData.studySubList[j].stdSubjectId != null)
						$('#subject').append('<option value='+crfData.studySubList[j].stdSubjectId.reportingId.id+'>'+crfData.studySubList[j].stdSubjectId.subjectNo+'</option>');
					else 
						$('#subject').append('<option value='+crfData.studySubList[j].reportingId.id+'>'+crfData.studySubList[j].subjectNo+'</option>');
				 */
					$('#volunteer').append('<option value='+crfData.stdVolList[j].id+'>'+crfData.stdVolList[j].volunteerId+'</option>');
					volIds.push(crfData.stdVolList[j].id);
				}
				/* for(var k=0; k<crfData.lspgaList.length; k++){
					$('#activity').append('<option value='+crfData.lspgaList[k].globalActivity.id+'>'+crfData.lspgaList[k].name+'</option>');
					
				} */
			}
			flag = true;
		}else{
			$('#'+messageId).val('${validationText}');
			flag = false;
		}
		return flag;
	}
	function periodNameValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value != null && value != "" && value != "-1" && value != "undefined"){
			$('#'+messageId).val('');
			flag = true;
		}else{
			$('#'+messageId).val('${validationText}');
			flag = false;
		}
		return flag;
	}
	function subjectValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value != null && value != "" && value != "-1" && value != "undefined"){
			$('#'+messageId).val('');
			$('#volunteer').prop("disabled", true);
			$('#reportType').val("subjects");
			flag = true;
		}else{
			$('#'+messageId).val('${validationText}');
			$('#volunteer').removeAttr("disabled");
			flag = false;
		}
		return flag;
	}
	function volunteerValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value != null && value != "" && value != "-1" && value != "undefined"){
			$('#'+messageId).val('');
			$('#subject').prop("disabled", true);
			$('#reportType').val("volunteersReport");
			flag = true;
		}else{
			$('#'+messageId).val('${validationText}');
			$('#subject').removeAttr("disabled");
			flag = false;
		}
		return flag;
	}
	function activityValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value != null && value != "" && value != "-1" && value != "undefined"){
			$('#'+messageId).val('');
			flag = true;
		}else{
			$('#'+messageId).val('${validationText}');
			flag = false;
		}
		return flag;
	}
	function selectBoxValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value != null && value != "" && value != "-1" && value != "undefined"){
			$('#'+messageId).val('');
			flag = true;
		}else{
			$('#'+messageId).val('${validationText}');
			flag = false;
		}
		return flag;
	}
	
	function generateDataPdf(){
		var stdFlag = selectBoxValidation('studyName', 'studyNameMsg');
// 		var priodFlag = periodNameValidation('periodName', 'periodNameMsg');
		var subFlag = subjectValidation('subject', 'subjectMsg');
		var volFlag = false;
		if(subFlag == false)
			volFlag = volunteerValidation('volunteer', 'volunteerMsg');
// 		var actFlag = activityValidation('activity', 'activityMsg');
		var priodFlag = true;
		var actFlag = true;
		var regFlag = false;
		if(subFlag || volFlag)
			regFlag = true;
// 		alert("flag values : "+stdFlag +"::"+ priodFlag +"::"+ regFlag +"::"+ actFlag);
		if(stdFlag && priodFlag && regFlag && actFlag){
			$('#studyId').val($('#studyName').val());
// 			$('#periodId').val($('#periodName').val());
			$('#periodId').val(0);
			if(subFlag){
				$('#subjectId').val($('#subject').val());
				$('#volIds').val(0);
			}else {
				if($('#volunteer').val() == "0")
					$('#volIds').val(volIds);
				else $('#volIds').val($('#volunteer').val());	
			}
// 			$('#activityId').val($('#activity').val());
			$('#activityId').val(0);
			$('#pdfGenForm').submit();
// 			synchronousAjaxCall($("#mainUrl").val() +'/crfData/generateDataCrf/'+ +"/"+$('#periodName').val()+"/"+$('#subject').val()+"/"+$('#activity').val());
		}
	}
	</script>
	
</body>
</html>