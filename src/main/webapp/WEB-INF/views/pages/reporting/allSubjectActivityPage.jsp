<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Subject Activity</title>
 </head>
<body>

	<c:url value="/reporting/saveAllSubjectActivityStart" var="saveReportingData" />
	<form:form action="${saveReportingData}" method="post" id="startForm" >
		<div style="width: 80%;">
		    <input type="text" name="astudyId" id="astudyId" value="${rdto.studyId }" >
		    <input type="text" name="agroupid" id="agroupid" value="${rdto.groupId}" >
		    <table style="width: 80%;">
				<tr>
				<c:choose>
				<c:when test="${start eq 'No'}">
				<td align="center"><input type="button" value="Start" class="btn btn-danger btn-sm" onclick="addStartButton()"></td>
				<td align="center"><input type="button" value="Stop" class="btn btn-danger btn-sm"  disabled ></td>
				</c:when>
				<c:otherwise>
			    <td align="center"><input type="button" value="Start" class="btn btn-danger btn-sm" disabled ></td>
				<td align="center"><input type="button" value="Stop" class="btn btn-danger btn-sm" onclick="addStopButton()"></td>
				</c:otherwise>
				</c:choose>
				
				
				</tr>
			</table>
		</div>
	</form:form>
	<c:url value="/reporting/saveAllSubjectActivityStop" var="saveReportingData" />
	<form:form action="${saveReportingData}" method="post" id="stopForm" >
	 <input type="hidden" name="bstudyId" id="studyId" value="${rdto.studyId }" >
     <input type="hidden" name="bgroupid" id="groupid" value="${rdto.groupId }" >
	</form:form>
	
	
	</body>
	<script type="text/javascript">
	function addStartButton(){
		alert("tryh");
		$("#startForm").submit();
	}
    function addStopButton(){
    	alert("tryh stop");
		//$("#stopForm").submit();
	}
	
	</script>
	
</html>
