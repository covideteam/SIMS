<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Crf Generation Page</title>
</head>
<body>
<div class="card">
  <div style="width: 85%; margin-left: 5%;">
	<table class="table table-bordered table-striped" style="width: 100%;">
		<tr>
			<td align="right">Select Project &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<div style="width: 70%;">
					<select name="projectName" id="projectName"  class="form-control" onchange="projectNameValidation('projectName', 'projectNameMsg')">
						<option value="">-----Select-----</option>
						<c:forEach items="${proList}" var="pro">
							<option value="${pro.projectId}">${pro.projectNo}</option>
						</c:forEach>
					</select>
					<div id="projectNameMsg" style="color: red;"></div>
				</div>
			</td>
			<td><input type="button" value="Generate Report" class="btn btn-primary" onclick="submitFunction()"></td>
		</tr>
	</table>
<%-- 	<c:url value="/projectCrf/generateCrfPdf" var="pdfFrom"></c:url> --%>
	<c:url value="/projectCrf/generateBlankCrf" var="pdfFrom"></c:url>
	<form:form action="${pdfFrom}" method="POST" id="pdfGenForm">
	    <input type="hidden" name="projectNo" id="projectNo">
	</form:form>
</div>
<script type="text/javascript">
function projectNameValidation(id, messageId){
	var flag = false;
	var value = $('#'+id).val();
	if(value == null || value == "" || value == "undefined"){
		/* $('#'+messageId).html("Required Field."); */
		$('#'+messageId).html('${validationText}');
		flag = false;
	}else{
		$('#'+messageId).html("");
		flag = true;
	}
	return flag;
}
function submitFunction(){
	var projectNameFlag = projectNameValidation('projectName', 'projectNameMsg');
	if(projectNameFlag){
		var projectNo = $('#projectName').val();
		$('#projectNo').val(projectNo);
		$('#pdfGenForm').submit();
// 		var valsResult = synchronousAjaxCall(mainUrl+"/projectCrf/generateCrfPdf/"+projectNo);
		
	}
}
</script>
</div>
</body>
</html>