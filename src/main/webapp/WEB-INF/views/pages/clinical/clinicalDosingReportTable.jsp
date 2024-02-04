<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
	<table border="1" style="width: 90%">
		<tr>
			<th>Study No.: ${study.projectNo}</th>
			<th>Subject No. ${vol.subjectNo}</th>
			<th>Period : ${period.periodNo}</th>
		</tr>
	</table>
	<table border="1" style="width: 90%">
		<tr>
			<th colspan="5"><h6>DOSING RECORD :</h6></th>
		</tr>
		<tr>
			<th colspan="2">Dosage Form: ${study.dosageFrom.fieldName}</th>
			<th colspan="3">Subject Number is verified: <c:if
					test="${dose.subjectScanTime != null}">Yes</c:if></th>
		</tr>
		<tr>
			<th>Date</th>
			<c:choose>
				<c:when test="${dose.timePointNo eq 1 }">
					<th>Time Point</th>
				</c:when>
				<c:otherwise>
					<th>Schedule Point</th>
				</c:otherwise>
			</c:choose>
			<th>Actual Dosing time</th>
			<th>Number of Units Dosed</th>
			<th>Duplicate IP label is verified with the Container/Sachet IP
				label</th>
		</tr>
<%-- 		<c:forEach items="${dosingData}" var="v"> --%>
			<tr>
				<th><c:if test="${dose.actualDate ne null}">${dose.actualDate}</c:if>
				</th>
				<c:choose>
					<c:when test="${dose.timePointNo eq 1 }">
						<th>${dose.timePoint}</th>
					</c:when>
					<c:otherwise>
						<th>${dose.scheduleTime}</th>
					</c:otherwise>
				</c:choose>
				<th><c:if test="${dose.actualtime ne null}">${dose.actualtime}</c:if>
				</th>
				<th>${dose.treatmentInfo.noOfSachetLables}</th>
				<c:choose>
					<c:when test="${dose.actualtime ne null and dose.actualtime ne '' }">
						<td>Yes</td>
					</c:when>
					<c:otherwise>
						<td />
					</c:otherwise>
				</c:choose>
			</tr>
<%-- 		</c:forEach> --%>
	</table>
	<table border="1" style="width: 90%">
		<c:forEach items="${dosePerameters}" var="dp" varStatus="st">
			<tr><td>${st.count}</td>
				<td>${dp.dosePerameter.label}</td>
				<td>${dp.perameterValue}</td>
			</tr>
		</c:forEach>
	</table>
	<table border="1" style="width: 90%">
		<tr>
			<td>Dosed by: ${dose.doseDoneBy.username}/ ${dose.actualDate}</td>
			<td>Supervised by: ${dose.supervisedBy.username}/ ${dose.supervisedOn}</td>
		</tr>
		<tr>
			<td colspan="2">Reviewed by (PI/Designee):</td>
		</tr>
	</table>
</body>
</html>