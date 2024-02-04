<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>

</head>
<body>
	<table id="example1" border="1">
		<thead>
			<c:forEach items="${heading}" var="d">
				<c:choose>
					<c:when test="${d.subjectNo eq 'Order No'}">
						<tr>
							<th>${d.subjectNo}</th>
							<c:forEach items="${d.timePoint}" var="t" varStatus="st">
								<c:choose>
									<c:when test="${st.count%2 eq 0 }">
										<th bgcolor="#33d7ff">${t}</th>
									</c:when>
									<c:otherwise>
										<th>${t}</th>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:when>
					<c:when test="${d.subjectNo eq 'Time Point'}">
						<tr>
							<th>Time Point - ${d.treatmentName}</th>
							<c:forEach items="${d.timePoint}" var="t" varStatus="st">
								<c:choose>
									<c:when test="${st.count%2 eq 0 }">
										<th bgcolor="#33d7ff">${t}</th>
									</c:when>
									<c:otherwise>
										<th >${t}</th>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th>Subject No</th>
							<c:forEach items="${d.timePoint}" var="t" varStatus="st">
								<c:choose>
									<c:when test="${st.count%2 eq 0 }">
										<th  bgcolor="#33d7ff">Dose Time</th>
									</c:when>
									<c:otherwise>
										<th>Dose Time</th>
									</c:otherwise>
								</c:choose>

							</c:forEach>
						</tr>
					</c:otherwise>
				</c:choose>

			</c:forEach>
		</thead>
		<tbody>
			<c:forEach items="${data}" var="d">
				<tr>
					<td>${d.subjectNo}</td>
					<c:forEach items="${d.timePointInfo}" var="t" varStatus="st">

						<c:choose>
							<c:when test="${st.count%2 eq 0 }">
								<th bgcolor="#33d7ff" id="${t.id}"><c:choose>
										<c:when test="${t.color eq 'red' }">
											<font color="red">${t.collected}</font>
										</c:when>
										<c:otherwise>
											<font color="blue">${t.collected}</font>
										</c:otherwise>
									</c:choose></th>
							</c:when>
							<c:otherwise>
								<th id="${t.id}"><c:choose>
										<c:when test="${t.color eq 'red' }">
											<font color="red">${t.collected}</font>
										</c:when>
										<c:otherwise>
											<font color="blue">${t.collected}</font>
										</c:otherwise>
									</c:choose></th>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var url = mainUrl + "/testWebSocket/dosingDashBorad";
		var eventSource = new EventSource(url);
		eventSource.addEventListener("doseTimes", function(event) {
			var articalData = JSON.parse(event.data);
			console.log("Event : " + event.data);
			//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
			addBlock(articalData.id, articalData.actualTime, articalData.color)
		});

	});
	function addBlock(id, actualTime, color) {
		if (id != '0') {
			if (color == 'blue')
				$("#" + id).html('<font color="blue">' + actualTime + '</font>')
			else
				$("#" + id).html('<font color="red">' + actualTime + '</font>')
		}
	}
</script>
</html>
