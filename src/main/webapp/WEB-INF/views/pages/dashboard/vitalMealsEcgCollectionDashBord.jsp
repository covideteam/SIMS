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
										<th colspan="6" bgcolor="#33d7ff">${t}</th>
									</c:when>
									<c:otherwise>
										<th colspan="6">${t}</th>
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
										<th colspan="6" bgcolor="#33d7ff">${t}</th>
									</c:when>
									<c:otherwise>
										<th colspan="6">${t}</th>
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
										<th bgcolor="#33d7ff">Schedule</th>
										<c:choose>
											<c:when
												test="${(type eq 'mealCollectionDashBord') or (type eq 'ecgCollectionDashBord') or (type eq 'vitalCollectionDashBord')}">
												<th bgcolor="#33d7ff">Start</th>
												<th bgcolor="#33d7ff">End</th>
											</c:when>
											<c:otherwise>
												<th bgcolor="#33d7ff" colspan="2">Actual</th>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<th>Schedule</th>
										<c:choose>
											<c:when
												test="${(type eq 'mealCollectionDashBord') or (type eq 'ecgCollectionDashBord') or (type eq 'vitalCollectionDashBord')}">
												<th bgcolor="#33d7ff">Start</th>
												<th bgcolor="#33d7ff">End</th>
											</c:when>
											<c:otherwise>
												<th bgcolor="#33d7ff" colspan="2">Actual</th>
											</c:otherwise>
										</c:choose>
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
								<th bgcolor="#33d7ff">${t.scheduleTime}</th>
								<c:choose>
									<c:when
										test="${(type eq 'mealCollectionDashBord') or type eq ('ecgCollectionDashBord') or (type eq 'vitalCollectionDashBord')}">
										<th bgcolor="#33d7ff" id="start_${t.id}"><c:choose>
												<c:when test="${t.color eq 'red' }">
													<font color="red">${t.start}</font>
												</c:when>
												<c:otherwise>
													<font color="blue">${t.start}</font>
												</c:otherwise>
											</c:choose></th>
										<th bgcolor="#33d7ff" id="end_${t.id}"><c:choose>
												<c:when test="${t.color eq 'red' }">
													<font color="red">${t.end}</font>
												</c:when>
												<c:otherwise>
													<font color="blue">${t.end}</font>
												</c:otherwise>
											</c:choose></th>
									</c:when>
									<c:otherwise>
										<th bgcolor="#33d7ff" id="${t.id}" colspan="2"><c:choose>
												<c:when test="${t.color eq 'red' }">
													<font color="red">${t.collected}</font>
												</c:when>
												<c:otherwise>
													<font color="blue">${t.collected}</font>
												</c:otherwise>
											</c:choose></th>
									</c:otherwise>
								</c:choose>


							</c:when>
							<c:otherwise>
								<th>${t.scheduleTime}</th>
								<c:choose>
									<c:when
										test="${(type eq 'mealCollectionDashBord') or type eq ('ecgCollectionDashBord') or (type eq 'vitalCollectionDashBord')}">
										<th bgcolor="#33d7ff" id="start_${t.id}"><c:choose>
												<c:when test="${t.color eq 'red' }">
													<font color="red">${t.start}</font>
												</c:when>
												<c:otherwise>
													<font color="blue">${t.start}</font>
												</c:otherwise>
											</c:choose></th>
										<th bgcolor="#33d7ff" id="end_${t.id}"><c:choose>
												<c:when test="${t.color eq 'red' }">
													<font color="red">${t.end}</font>
												</c:when>
												<c:otherwise>
													<font color="blue">${t.end}</font>
												</c:otherwise>
											</c:choose></th>
									</c:when>
									<c:otherwise>
										<th id="${t.id}" colspan="2"><c:choose>
												<c:when test="${t.color eq 'red' }">
													<font color="red">${t.collected}</font>
												</c:when>
												<c:otherwise>
													<font color="blue">${t.collected}</font>
												</c:otherwise>
											</c:choose></th>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript">
	$(document).ready(
			function() {
				if ($("#type").val() == 'sampleCollectionDashBord') {
					var url = mainUrl + "/testWebSocket/sampleCollection";
					var eventSource = new EventSource(url);
					eventSource.addEventListener("sampleCollection", function(
							event) {
						var articalData = JSON.parse(event.data);
						console.log("Event : " + event.data);
						//	 				alert(articalData.id +" - "+ articalData.actualTime +" - "+ articalData.color)
						addBlock(articalData.id, articalData.actualTime,
								articalData.color)
					});
				} else if ($("#type").val() == 'sampleSapartionDashBord') {
					var url = mainUrl + "/testWebSocket/sampleSaparation";
					var eventSource = new EventSource(url);
					eventSource.addEventListener("sampleSaparation", function(
							event) {
						var articalData = JSON.parse(event.data);
						console.log("Event : " + event.data);
						//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
						addBlock(articalData.id, articalData.actualTime,
								articalData.color)
					});
				} else if ($("#type").val() == 'mealCollectionDashBord') {
					var url = mainUrl + "/testWebSocket/mealCollectionStart";
					var eventSource = new EventSource(url);
					eventSource.addEventListener("mealCollectionStart",
							function(event) {
								var articalData = JSON.parse(event.data);
								console.log("Event : " + event.data);
								//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
								addMealBlock(articalData.id)
							});
					var url2 = mainUrl + "/testWebSocket/mealCollectionEnd";
					var eventSource2 = new EventSource(url2);
					eventSource.addEventListener("mealCollectionEnd", function(
							event) {
						var articalData = JSON.parse(event.data);
						console.log("Event : " + event.data);
						//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
						addBlock(articalData.id, articalData.actualTime,
								articalData.color)
					});
				} else if ($("#type").val() == 'vitalCollectionDashBordTable') {
					var url = mainUrl + "/testWebSocket/vitalCollectitonStart";
					var eventSource = new EventSource(url);
					eventSource.addEventListener("vitalCollectionStart",
							function(event) {
								var articalData = JSON.parse(event.data);
								console.log("Event : " + event.data);
								//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
								addBlock(articalData.id, articalData.actualTime,
								articalData.color)
							});
					var url2 = mainUrl + "/testWebSocket/vitalCollectionEnd";
					var eventSource2 = new EventSource(url2);
					eventSource.addEventListener("vitalCollectionEnd", function(
							event) {
						var articalData = JSON.parse(event.data);
						console.log("Event : " + event.data);
						//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
						addBlock(articalData.id, articalData.actualTime,
								articalData.color)
					});
				} else if ($("#type").val() == 'ecgCollectionDashBord') {
					var url = mainUrl + "/testWebSocket/ecgCollectionDashStart";
					var eventSource = new EventSource(url);
					eventSource.addEventListener("ecgCollectionDashStart",
							function(event) {
								var articalData = JSON.parse(event.data);
								console.log("Event : " + event.data);
								//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
								addBlock(articalData.id, articalData.actualTime,
								articalData.color)
							});
					var url2 = mainUrl + "/testWebSocket/ecgCollectionDashEnd";
					var eventSource2 = new EventSource(url2);
					eventSource.addEventListener("ecgCollectionDashEnd", function(
							event) {
						var articalData = JSON.parse(event.data);
						console.log("Event : " + event.data);
						//		 				alert(articalData.id +" - "+ articalData.collected +" - "+ articalData.color)
						addBlock(articalData.id, articalData.actualTime,
								articalData.color)
					});
				} 
				
				
				
			});
	function addBlock(id, actualTime, color) {
		if (id != '0') {
			if (color == 'blue')
				$("#" + id)
						.html('<font color="blue">' + actualTime + '</font>')
			else
				$("#" + id).html('<font color="red">' + actualTime + '</font>')
		}
	}
	function addMealBlock(id) {
		var data = id.split(",,");
		$.each(data, function(index, value) {
			var fields = value.split(",");
			if (fields[2] == 'blue')
				$("#" + fields[0]).html(
						'<font color="blue">' + fields[1] + '</font>')
			else
				$("#" + fields[0]).html(
						'<font color="red">' + fields[0] + '</font>')
		});
	}
</script>
</html>
