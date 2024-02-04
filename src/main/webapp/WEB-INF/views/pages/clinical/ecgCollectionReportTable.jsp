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
			<th colspan="6"><h6>ECG Record :</h6></th>
		</tr>
		<tr>
			<th>Time Point</th>
			<th>Date</th>
			<th>Scheduled time</th>
			<th colspan="2"><table>
					<tr>
						<td colspan="2">Actual time</td>
					</tr>
					<tr>
						<td>Start Time</td>
						<td>End Time</td>
				</table></th>
			<th>Done by (sign. & Date)</th>
		</tr>
		<c:forEach items="${ecgCollectionData}" var="v">
			<tr>
				<td>${v.sign}${v.timePoint}</td>
				<td><c:if test="${v.collectionStatus}">
							${v.scheduleDate}
						</c:if></td>
				<td><c:if test="${v.collectionStatus}">
							${v.scheduleTime}
						</c:if></td>
				<td><c:if test="${v.collectionStatus}">
							${v.subjectECGTimePointsData.startTime}
						</c:if></td>
				<td><c:if test="${v.collectionStatus}">
							${v.subjectECGTimePointsData.endTime}
						</c:if></td>
				<td><c:if test="${v.collectionStatus}">
							${v.subjectECGTimePointsData.collectedBy.username}/${v.subjectECGTimePointsData.actualDate}
						</c:if></td>
			</tr>
		</c:forEach>
	</table>
	Reviewed by: (PI/PIC/Designee)
</body>
</html>