<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Study Group</title>
</head>
<body>
	<div class="row">
		<input type="hidden" id="projectidval" value="${proidcr}">
		<c:url value="/studyGroups/saveOrUpdateStudyGroups" var="saveUrl"></c:url>
		<div class="form-container">
			<form:form action="${saveUrl}" id="saveFormVal" method="POST"
				modelAttribute="stugs">
				<form:hidden path="study.id" value="${proidcr}" />
				<form:hidden path="id" id="idval"/>
				<input type="hidden" name="typev"id="typevid" />
				<table
					style="width: 100%; height: 60%; margin-left: 30%; font-size: 13px;">

					<tr>
						<td><spring:message code="label.study.noofSubjects"></spring:message></td>
						<td><form:input type="number" path="noofSubjects"
								id="subjectsid" cssClass="form-control input-sm validate"
								onchange="subjectsidFunction('subjectsid','subjectsidMsg')" pattern="/^-?\d+\.?\d*$/" onKeyPress="if(this.value.length==3) return false;" />
							<div id="subjectsidMsg" style="color: red;"></div></td>
					<tr>
						<td><spring:message code="label.study.noofStandbys"></spring:message></td>
						<td><form:input type="number" path="noofStandbys"
								id="stabyid" cssClass="form-control input-sm validate"
								onchange="stabyidFunction('stabyid','stabyidMsg')"  pattern="/^-?\d+\.?\d*$/" onKeyPress="if(this.value.length==2) return false;"/>
							<div id="stabyidMsg" style="color: red;"></div></td>
					</tr>

				</table>
			</form:form>
		</div>
	</div>
</body>
<script type="text/javascript">
		function submitFormVal() {
			debugger;
			var flag = true;
			var sub = subjectsidFunction('subjectsid', 'subjectsidMsg');
			var stand = stabyidFunction('stabyid', 'stabyidMsg');
			if (sub && stand) {
				$('#typevid').val("creation");
				$("#saveFormVal").submit();
			}
		}
		function updateFormVal() {
			debugger;
			var flag = true;
			var sub = subjectsidFunction('subjectsid', 'subjectsidMsg');
			var stand = stabyidFunction('stabyid', 'stabyidMsg');
			if (sub && stand) {
				$('#typevid').val("update");
				$("#saveFormVal").submit();
			}
		}
		function subjectsidFunction(id, message) {
			var flagv = false;
			var value = $('#' + id).val();
			var proid = $('#projectidval').val();
			var sgid = $('#idval').val();
			if (parseInt(proid) > 0) {
				if (value == null || value == "" || value == "0" || value < 0
						|| value == "undefined") {

					/* $('#'+message).html("Required Field"); */
					$('#' + message).html('${validationText}');
					flagv = false;
				} else {
					debugger;
					if(sgid!="0"){
					var url = mainUrl
							+ "/studyGroups/getAvailableSubjectCount/" + proid+"/"+sgid;
					var result = synchronousAjaxCall(url);
					const myArray = result.split("##");
					if (value != null && value != "undefined") {
						if (parseInt(value) >= parseInt(result[0])) {
						if (parseInt(value) <= parseInt(myArray[1])) {
							$('#' + message).html("");
							flagv = true;
						} else {
							$('#' + message).html('${validationForCount}');
							flagv = false;
						}
						}else{
							$('#' + message).html('${validationBelowedCount}');
							flagv = false;
						}
					} else {
						$('#' + message).html('${internalIssues}');
						flagv = false;
					}
					}else{
						var url = mainUrl
						+ "/studyGroups/getAvailableSubjectCountTwo/"+ proid;
						var result = synchronousAjaxCall(url);
						if (value != null && value != "undefined") {
							if (parseInt(value) <= parseInt(result)) {
								$('#' + message).html("");
								flagv = true;
							} else {
								$('#' + message).html('${validationForCount}');
								flagv = false;
							}
						} else {
							$('#' + message).html('${internalIssues}');
							flagv = false;
						}
					}
				}
			} else {
				$('#projectidMsg').html('${validationText}');
			}
			return flagv;
		}
		function stabyidFunction(id, message) {
			var flagv = false;
			var value = $('#' + id).val();
			if (value == null || value == ""
					|| value == "undefined") {
				/* $('#'+message).html("Required Field"); */
				$('#' + message).html('${validationText}');
				flagv = false;
			} else {
				$('#' + message).html("");
				flagv = true;
			}
			return flagv;
		}
		
	</script>

</html>