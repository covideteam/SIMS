<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Study Crfs Page</title>
</head>
<body>
	<table class="table table-bordered">
		<tr>
			<td><spring:message code="label.seativities"></spring:message></td>
			<td>
				 <select name="crfName" id="crfName" onchange="crfNameFucntion('crfName', 'crfNameMsg')" class="form-control">
					<option value="">----Select----</option>
					<c:forEach items="${activities}" var="sta">
						<option value="${sta.activityId.id}">${sta.activityId.name}</option>
					</c:forEach>
				</select>
				<div id="crfNameMsg" style="color: red;"></div>
				
				
			</td>
			<td><spring:message code="label.sevolunteer"></spring:message></td>
			<td>
				<select name="subName" id="subName" onchange="subNameFucntion('subName', 'subNameMsg')" class="form-control">
					<option value="">----Select----</option>
					<c:forEach items="${volList}" var="sub">
					    <c:if test="${sub.volunreerId != null && not empty sub.volunreerId}">
					    	<option value="${sub.id}">${sub.volunreerId}</option>
					    </c:if>
					</c:forEach>
				</select>
				<div id="subNameMsg" style="color: red;"></div>
			</td>
		</tr>
	</table>
	
</body>
</html>