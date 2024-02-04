<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Drug Dispensing PDF Page</title>
<script type="text/javascript">
 var periodsArr = [];
</script>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Drug Dispensing PDF Page</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
				   <div id="dispanceMsg" style="color: red; text-align: center; font-weight: bold;"></div>
				   <div class="row">
				   		<div class="col-sm-12">
				   			<table style="width: 100%;">
					    		<tr>
					    			<td style="text-align: right;"><spring:message code="label.crfData.study"></spring:message> &nbsp;&nbsp;&nbsp;</td>
				    				<td>
				    					<div>
					   						<select name="projectName" id="projectName" class="form-control" onchange="projectNameValidation('projectName', 'projectNameMsg')">
							    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
							    				<c:forEach items="${ddpDto.smList}" var="sm">
													<option value="${sm.id}">${sm.projectNo}</option>
												</c:forEach>
											</select>
										</div>
										<div id="projectNameMsg" style="color: red;"></div>
									</td>
									<td style="text-align: right;">Select Period &nbsp;&nbsp;&nbsp;</td>
									<td>
				    					<div >
					   						<select name="priodName" id="priodName" class="form-control" onchange="periodValidation('priodName', 'periodNameMsg')">
							    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
							    			</select>
										</div>
										<div id="periodNameMsg" style="color: red;"></div>
									</td>
								</tr>
								<tr align="center">
									<td colspan="4"><input type="button" value="Generate PDF" class="btn btn-warning btn-sm" onclick="generatePdf()"></td>
								</tr>
				 			</table>
				   		</div>
				   </div>
				</div>
			</div>
		</div>
		<c:forEach items="${ddpDto.spmList}" var="spm">
			<script type="text/javascript">
			    var obj = {id :'${spm.id}', periodName : '${spm.periodName}', periodNo:'${spm.periodNo}', studyId:'${spm.studyId}'};
				periodsArr.push(obj);
			</script>
		</c:forEach>
	</div>
	
<c:url value="/drugDispansePdf/downloadDrugDispansePdf" var="downloadDrugDispPdf"></c:url>
<form:form action="${downloadDrugDispPdf}" method="POST" id="downloadDDPDFForm">
	<input type="hidden" name="studyVal" id="studyVal">
	<input type="hidden" name="periodVal" id="periodVal">
</form:form>

<script type="text/javascript">
function projectNameValidation(id, messageId){
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "undefined"){
		$('#projectNameMsg').html("");
		$('#studyVal').val(value);
// 		$('#priodName').remove();
		$('#priodName').empty();
		var str = '<option value="">----Select----</option>';
		$('#priodName').append(str);
        debugger;
		if(periodsArr != null && periodsArr != "undefined"){
// 			periodsArr = strData.split(',');
			if(periodsArr.length > 0){
				debugger;
// 				var myObject = Object.assign({}, periodsArr);
				$.each(periodsArr, function(index, val) {
					if(val.studyId == value){
						var str = '<option value="'+val.id+'">'+val.periodName+'</option>';
						$('#priodName').append(str);
					}
				});
			}
		}
	}else{
		$('#'+messageId).html("Required Field.");
// 		$('#priodName').remove();
		$('#priodName').empty();
		var str = '<option value="">----Select----</option>';
		$('#priodName').append(str);
		$('#priodName').val("");
	}
}
function projectValidation(){
	var flag = false;
	var value = $('#projectName').val();
	if(value != null && value != "" && value != "undefined"){
		$('#projectNameMsg').html("");
		flag = true;
	}else{
		$('#projectNameMsg').html("Required Field.");
		flag = false;
	}
	return flag;
}
function periodValidation(id, messageId){
	var flag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "undefined"){
		$('#periodVal').val(value);
		$('#'+messageId).html("");
		flag = true;
	}else{
		$('#'+messageId).html("Required Field.");
		flag = false;
	}
	return flag;
}

function generatePdf(){
	var projectFlag = projectValidation();
	var periodFlag = periodValidation('priodName', 'periodNameMsg');
	if(projectFlag && periodFlag){
		$('#downloadDDPDFForm').submit();
	}
}
</script>
</body>
</html>