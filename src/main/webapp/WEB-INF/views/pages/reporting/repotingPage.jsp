<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reporting Page</title>
 </head>
<body>

	<c:url value="/reporting/saveReportingVolunteerData" var="saveReportingData" />
	<input type='hidden' value="${saveReportingData}" id="hidReportingUrl"/>
	 <table style="width: 100%;">
    	<tr id="trReportingGender" style="display:none;">
			<td style="width:120px;"><spring:message code="label.reportGender"></spring:message></td>
			<td style="width:200px;">
				<select name="gender" id="gender" class="form-control validate" data-validate="required">
					<option value="">----<spring:message code="label.ruleSelect"></spring:message>----</option>
					<c:forEach items="${rdto.lsvList}" var="lsv">
						<option value="${lsv.id}">${lsv.name}</option>
					</c:forEach>
				</select>
			</td>
			<td></td>
		</tr>
		<tr align="center">
			<td colspan="3">
				<input type="button" value='<spring:message code="label.submit"></spring:message>' class="btn btn-primary btn-md" onclick="submitReportingForm()">
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		function studyExecutionCallback(data){
			if(data !== null){
				$("#trReportingGender").hide();
				$("#gender").val(data.genderId);
			}
			else{
				$("#gender").val("");
				$("#trReportingGender").show();
			}
		} 
		function submitReportingForm(){
			if($.trim($("#subName").val()).length === 0){
				if(!$("#subName").hasClass("validate")){
					$("#subName").addClass("validate");
					$("#subName").attr("data-validate",'required');
				}
			}
			if(validation.validate($("#frmActivityForm")[0])){
				var url = ajaxUrl+ $("#hidReportingUrl").val();
                $.ajax({
            		url:$("#mainUrl").val() +'/administration/getCsrfToken',
            		type:'GET',
            		success:function(data){
            			debugger;
            			 var formData = $('#frmActivityForm').serializeArray();
            			 formData.push({name: data.parameterName ,value: data.token});
            			 formData.push({name: 'volunteerId' ,value: $("#subName").val()});
            			 $.ajax({
                             type: "POST",
                             url: url,
                             data: formData,
                             success: function(data) {
                             	$('#subName').val("");
                             	$('#gender').val("");
                             	var obj = JSON.parse(data);
                                 if(obj.Success == "success")
                                 	displayMessage("success", obj.Message);
                                 else
                                 	displayMessage("error", obj.Message);
                             },
                             error: function(data) {
                             	var obj = JSON.parse(data);
                             	$('#studyName').val("");
                             	$('#subName').val("");
                             	$('#gender').val("");
                             	displayMessage("error", "Invalid Data");
                             }
                         });
            		},
            		error:function(er){
            			debugger;
            		}
            	 });
               
			}
		}
	</script>
	</body>
</html>
