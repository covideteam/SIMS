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
			<th colspan="11">Blood Sample Collection Record:</th>
		</tr>
		<tr>
			<th colspan="3">Dosing Date: ${dose.actualDate}</th>
			<th colspan="3">Dosing Time: ${dose.actualtime}</th>
			<th colspan="5">Light Condition:Normal Light</th>
		</tr>
		<tr>
			<td colspan="6">Pre and Post Dose Blood Volume: 04 mL each</td>
			<td colspan="5">Type of Vacutainer Used: K2EDTA</td>
		</tr>
		<tr>
			<th>Date</th>
			<th>Time Point (Hours)</th>
			<th>Scheduled Time</th>
			<th colspan="2"><table>
					<tr>
						<td colspan="2">I.D check</td>
					</tr>
					<tr>
						<td>Sub ID</td>
						<td>Tube ID</td>
					</tr>
				</table></th>
			<th colspan="2"><table>
					<tr>
						<td colspan="2">Sample Collected on Scheduled Time</td>
					</tr>
					<tr>
						<td>Yes</td>
						<td>No</td>
					</tr>
				</table></th>
			<th>Actual time</th>
			<th>Code for Deviation/Comments</th>
			<th>Deviation</th>
			<th>Collected by(Initial & Date)</th>
		</tr>
		<c:forEach items="${sampleCollectionData}" var="v">
			<tr>
				<th><c:if test="${v.scheduleDate ne null}">${v.scheduleDate}</c:if> </th>
				<th>${v.sign}${v.timePoint}</th>
				<th><c:if test="${v.scheduleTime ne null}">${v.scheduleTime}</c:if> </th>
				<th>${v.subjectOrder}</th>
				<th>${v.timePointNo}</th>
				<c:choose>
					<c:when test="${v.scheduleTime ne null}">
						<c:choose>
							<c:when
								test="${v.subjectSampleCollectionTimePointsData.deviation ne null}">
								<td>Yes</td>
								<td />
							</c:when>
							<c:otherwise>
								<td />
								<td>No</td>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<td />
						<td />
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${v.subjectSampleCollectionTimePointsData ne null}">
						<th>${v.subjectSampleCollectionTimePointsData.actualtime}</th>
						<c:choose>
							<c:when
								test="${v.subjectSampleCollectionTimePointsData.diviationMessage ne null}">
								<th>${v.subjectSampleCollectionTimePointsData.diviationMessage.code}</th>
							</c:when>
							<c:otherwise>
								<td />
							</c:otherwise>
						</c:choose>
						<th>${v.subjectSampleCollectionTimePointsData.deviation}</th>
						<th>${v.subjectSampleCollectionTimePointsData.collectedBy.username}/${v.subjectSampleCollectionTimePointsData.actualDate}</th>
						
					</c:when>
					<c:otherwise>
						<td />
						<td />
						<td />
						<td />
					</c:otherwise>
				</c:choose>

			</tr>
		</c:forEach>

	</table>
</body>
</html>