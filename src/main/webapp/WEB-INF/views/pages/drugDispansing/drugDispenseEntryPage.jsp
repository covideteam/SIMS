<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Drug Dispensing Entry</title>
<script type="text/javascript">
var treatmentArr = {};
</script>
 </head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Drug Dispensing Entry</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
				   <div id="dispanceMsg" style="color: red; text-align: center; font-weight: bold;"></div>
				   <table style="width: 100%;">
			    		<tr>
			    			<td style="text-align: right;"><spring:message code="label.crfData.study"></spring:message> &nbsp;&nbsp;&nbsp;</td>
		    				<td>
		    					<div style="width: 45%;">
			   						<select name="projectName" id="projectName" class="form-control"  onchange="projectNameValidation('projectName', 'projectNameMsg')">
					    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
					    				<c:forEach items="${ddedDto.smList}" var="sm">
											<option value="${sm.id}">${sm.projectNo}</option>
										</c:forEach>
									</select>
								</div>
								<div id="projectNameMsg" style="color: red;"></div>
							</td>
						</tr>
		 			</table>
<!-- 		 			<table style="width: 100%;" id="drugEntryDetailsDiv"> -->
<!-- 		 			</table> -->
		 			<div id="drugEntryDetailsDiv" style="margin-right: 10%;"></div>
				</div>
			</div>
		</div>
	</div>
	
<c:url value="/drugDispanse/saveDrugDispanseInfoDetails" var="saveDDINFD"></c:url>
<form:form action="${saveDDINFD}" method="POST" id="saveDrugDispanseInfo">
<input type="hidden" name="noOfUnitsVal" id="noOfUnitsVal">
<input type="hidden" name="nameOfIpVal" id="nameOfIpVal">
<input type="hidden" name="batchNoVal" id="batchNoVal">
<input type="hidden" name="studyVal" id="studyVal">
<input type="hidden" name="expDateVal" id="expDateVal">
<input type="hidden" name="treatmentVal" id="treatmentVal">
</form:form>

<script type="text/javascript">

	var result = "";
	function projectNameValidation(id, messageId){
// 		debugger;
		var value = $('#'+id).val();
		var htmlStr = '';
		debugger;
		if(value != null && value != "" && value != "undefined"){
			var expDateName = "'"+"expDate"+"'";
			result = synchronousAjaxCall(mainUrl+ "/drugDispanse/drugDispanseDetails/" + $('#projectName').val());
			if(result != null && result != "" && result != "undefined"){
				var str = '<tr><td style="text-align: right;">Treatment :&nbsp;&nbsp;&nbsp;</td>'
						+'<td><div style="width: 50%;">'
						+'<select name="treatment" id="treatment" class="form-control" onchange="treatmentValidation()">'
						+'<option value="-1">----Select----</option>';
				for(var i=0; i<result.tinfoList.length; i++){
					str += '<option value="'+result.tinfoList[i].id+'">'+result.tinfoList[i].randamizationCode+'</option>';
					treatmentArr[result.tinfoList[i].id] = result.tinfoList[i];
				}
				str += '</select><div id="treatmentMsg" style="color: red;"></div></td></tr>';
				htmlStr += '<table style="width: 100%;">'
					+str
					+'<tr>'
			        +'<td style="text-align: right;">No. of Units :&nbsp;&nbsp;&nbsp;</td>'
			        +'<td><div style="width: 50%;">'
			        +'<input type="text" name="noOfUnits" id="noOfUnits" class="form-control" onblur="noOfUnitsValidation()"></div>'
			        +'<div id="noOfUnitsMsg" style="color: red;"></div></td></tr>'
			        +'<tr><td style="text-align: right;">Name of IP :&nbsp;&nbsp;&nbsp;</td>'
			        +'<td><div style="width: 50%;">'
			        +'<input type="text" name="nameOfIp" id="nameOfIp" class="form-control" onblur="nameOfIpValidation()" readonly="true"></div>'
			        +'<div id="nameOfIpMsg" style="color: red;"></div></td></tr>'
			        +'<tr><td style="text-align: right;">Batch No. :&nbsp;&nbsp;&nbsp;</td>'
			        +'<td><div style="width: 50%;">'
			        +'<input type="text" name="batchNo" id="batchNo" class="form-control" onblur="batchNoValidation()"></div>'
			        +'<div id="batchNoMsg" style="color: red;"></div></td></tr>'
			        +'<tr><td style="text-align: right;">Expiry Date :&nbsp;&nbsp;&nbsp;</td>'
			        +'<td><div style="width: 50%;">'
			        +'<input type="text" placeholder="Choose Date" name="expDate" class="form-control" id="expDate" onclick="expireDateFunction('+expDateName+')">'
		            +'</div>'
			        +'<div id="expiryDateMsg" style="color: red;"></div></td></tr>'
			        +'<tr align="center"><td colspan="2"><input type="submit" value="Submit" class="btn btn-primary btn-sm" onclick="submitDoseDetails()"></td></tr>'
			        +'</table>';
			}
			if(htmlStr != ""){
				$('#drugEntryDetailsDiv').empty();
				$('#drugEntryDetailsDiv').append(htmlStr);
				
			}
			
			
		}
	}
function treatmentValidation(){
	var flag = false;
	var value = $('#treatment').val();
	if(value != null && value != "-1" && value != "undefined"){
		flag = true;
		$('#treatmentMsg').html("");
		if(result != null && result != "" && result != "undefined"){
			var dispansLength =  Object.entries(result.dinfMap).length;
			if(dispansLength > 0){
				var disObj = result.dinfMap[value];
				if(disObj != null && disObj != "undefined"){
					var expiredOn = new Date(disObj.expDate);
					expiredOn = $.datepicker.formatDate('dd M yy', expiredOn);
					$('#expDate').val(expiredOn);
					$('#noOfUnits').val(disObj.noOfUnits);
					$('#nameOfIp').val(disObj.nameOfIp);
					$('#batchNo').val(disObj.batchNo);
					$('#treatment').val(disObj.tinfo.id);
				}else{
					var tinf = result.treatmentMap[value];
					if(tinf != null && tinf != "undefined")
						$('#nameOfIp').val(tinf.treatmentName);
					else $('#nameOfIp').val("");
					$('#expDate').val("");
					$('#noOfUnits').val("");
					$('#batchNo').val("");
				}
			}else{
				var tinf = treatmentArr[value];
				if(tinf != null && tinf != "undefined")
					$('#nameOfIp').val(tinf.treatmentName);
			}
		}
	}else{
		$('#treatmentMsg').html("Required Field.");
	    flag = false;
	}
	return flag;
}
function treatmentValidationFunction(){
	var flag = false;
	var value = $('#treatment').val();
	if(value != null && value != "-1" && value != "undefined"){
		flag = true;
		$('#treatmentMsg').html("");
	}else{
		$('#treatmentMsg').html("Required Field.");
	    flag = false;
	}
	return flag;
}

function noOfUnitsValidation(){
	var flag = false;
	var value = $('#noOfUnits').val();
	if(value != null && value != "" && value != "undefined"){
		flag = true;
		$('#noOfUnitsMsg').html("");
	}else{
		$('#noOfUnitsMsg').html("Required Field.");
	    flag = false;
	}
	return flag;
}
function nameOfIpValidation(){
	var flag = false;
	var value = $('#nameOfIp').val();
	if(value != null && value != "" && value != "undefined"){
		flag = true;
		$('#nameOfIpMsg').html("");
	}else{
		$('#nameOfIpMsg').html("Required Field.");
	    flag = false;
	}
	return flag;
}

function batchNoValidation(){
	var flag = false;
	var value = $('#batchNo').val();
	if(value != null && value != "" && value != "undefined"){
		flag = true;
		$('#batchNoMsg').html("");
	}else{
		$('#batchNoMsg').html("Required Field.");
	    flag = false;
	}
	return flag;
}
function expdateValidation(){
	var flag = false;
	var value = $('#expDate').val();
	if(value != null && value != "" && value != "undefined"){
		flag = true;
		$('#expiryDateMsg').html("");
	}else{
		$('#expiryDateMsg').html("Required Field.");
	    flag = false;
	}
	return flag;
}
function expireDateFunction(name){
	$("[name='" + name + "']").datepicker({
		  changeMonth: true,
		  changeYear: true,
		  dateFormat: 'd M yy'
  });
}
function submitDoseDetails(){
	$('#dispanceMsg').html("");
	var trFlag = treatmentValidationFunction();
	var noUnitsFlag = noOfUnitsValidation();
	var nameOfIpFlag = nameOfIpValidation();
	var batchFlag = batchNoValidation();
	var expDateFlag = expdateValidation();
	var doseCount = 0;
	if(trFlag && noUnitsFlag && nameOfIpFlag && batchFlag && expDateFlag){
		if(result != null && result != "" && result != "undefined")
			doseCount = result.dosecount;
		if(doseCount == 0){
			var studyId = $('#projectName').val();
			var expDate = $('#expDate').val();
			var noOfUnits = $('#noOfUnits').val();
			var ipName = $('#nameOfIp').val();
			var batchNo = $('#batchNo').val();
			var treatment = $('#treatment').val();
//		 	alert(noOfUnits+"::"+ipName);
			$('#studyVal').val(studyId);
			$('#expDateVal').val(expDate);
			$('#noOfUnitsVal').val(noOfUnits);
			$('#nameOfIpVal').val(ipName);
			$('#batchNoVal').val(batchNo);
			$('#treatmentVal').val(treatment);
// 			alert(expDate);
		 	$('#saveDrugDispanseInfo').submit();
		 }else{
			$('#dispanceMsg').html("Dosing Alredy Started. Drug Dispensing Entry Details are Not Allowed to Update.");
		} 
	}
}
</script>
 </body>
</html>