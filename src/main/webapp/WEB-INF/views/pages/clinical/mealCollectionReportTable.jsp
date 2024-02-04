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
			<th colspan="6"><h6>Meal Record :</h6></th>
		</tr>
		<tr>
			<th>Date</th>
			<th>Meal Type</th>
			<th>Scheduled time</th>
			<th colspan="2"><table>
					<tr>
						<td colspan="2">Actual time</td>
					</tr>
					<tr>
						<td>Start Time</td>
						<td>End Time</td>
				</table></th>
			<th>Consumed as per meal menu</th>
			<th>Comments</th>
			<th>Supervised by (Sign. & Date)</th>
		</tr>
		<c:forEach items="${mealCollectionData}" var="v">
			<tr>
				<td><c:if test="${v.timePointCollectionStatus}">
							${v.scheduleDate}
						</c:if></td>
				<td>${v.meal}</td>
				<td><c:if test="${v.timePointCollectionStatus}">
							${v.scheduleTime}
						</c:if></td>
				<td><c:if test="${v.timePointCollectionStatus}">
							${v.subjectMealsTimePointsData.startTime}
						</c:if></td>
				<td><c:if test="${v.timePointCollectionStatus}">
							${v.subjectMealsTimePointsData.endTime}
						</c:if></td>
				<td><c:if test="${v.timePointCollectionStatus}">
						<c:choose>
							<c:when
								test="${v.subjectMealsTimePointsData.completion.code eq 'FULL'}">Yes</c:when>
							<c:otherwise>No</c:otherwise>
						</c:choose>
					</c:if></td>
				<td><c:if test="${v.timePointCollectionStatus}">
							${v.subjectMealsTimePointsData.comments}
						</c:if></td>
				<td><c:if test="${v.timePointCollectionStatus}">
							${v.subjectMealsTimePointsData.endBy.username}/${v.subjectMealsTimePointsData.actualDate}
						</c:if></td>
			</tr>
		</c:forEach>
	</table>
	Reviewed by: (PI/PIC/Designee)
</body>
</html>