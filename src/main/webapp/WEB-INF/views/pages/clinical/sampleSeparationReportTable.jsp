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
			<th colspan="6"><h6>Sample Separation :</h6></th>
		</tr>
		<tr>
			<th>Time Point</th>
			<th>VacutinerScanTime</th>
			<th>VialScanTimetime</th>
			<th>Vital No</th>
			<th>Separated by (Sign & Date)</th>
		</tr>
		<c:forEach items="${sampleSeparationData}" var="v">
			<tr>
				<td>${v.subjectSampleCollectionTimPoints.sign}${v.subjectSampleCollectionTimPoints.timePoint}</td>
				<td>${v.vacutinerScanTime}</td>
				<td>${v.vialScanTimetime}</td>
				<td>${v.vialNo}</td>
				<td>${v.separatedBy.username}/${v.separationDate}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>