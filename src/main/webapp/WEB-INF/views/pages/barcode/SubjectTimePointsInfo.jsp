<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<body>
	<select name="timePointId" class="form-control" id="timePointId" onchange="timePointIdVal(this.value)">
		<option value="-1">--Select--</option>
		<c:forEach items="${timePoints}" var="p">
			<option value="${p.subjectSampleCollectionTimePointId}">${p.timePoint} (${p.vacutainer})</option>	
		</c:forEach>
	</select>
	<font color="red" id="timePointIdMsg"></font>
</body>
</html>