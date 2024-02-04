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
			<th colspan="6"><h6>Vital Signs Record for Discharge :</h6></th>
			<th colspan="2">Date :</th>
		</tr>
		<tr>
			<th>Time Point</th>
			<th>Start Time</th>
			<c:forEach items="${test}" var="t">
				<th>${t.testId.testName}</th>
			</c:forEach>
			<th>End Time</th>
			<th>Recorded by (Sign & Date)</th>
		</tr>
		<c:forEach items="${vitalCollectionData}" var="v">
			<tr>
				<th>${v.sign}${v.timePoint}</th>
				<c:choose>
						<c:when test="${v.subjectVitalTimePointsData ne null}">
							<td>${v.startTime}</td>
							<c:forEach items="${v.test}" var="t">
								<td>${t.vitalTestValue}</td>
							</c:forEach>
							<td>${v.endTime}</td>
							<td>${v.subjectVitalTimePointsData.collectedBy.username}/${v.subjectVitalTimePointsData.actualDate}</td>
						</c:when>
						<c:otherwise>
							<td />
							<c:forEach items="${v.test}" var="t">
								<td>${t.vitalTestValue}</td>
							</c:forEach>
							<td />
							<td />
						</c:otherwise>
					</c:choose>
			</tr>
		</c:forEach>
	</table>
	Temp = Temperature, BP = Blood Pressure, RR=Respiratory Rate, PR=
	Radial Pulse Rate.
</body>
</html>