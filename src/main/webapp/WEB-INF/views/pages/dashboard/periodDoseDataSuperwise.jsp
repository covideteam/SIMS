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
		<tr>
			<th>Subject No</th>
			<c:forEach items="${heading}" var="d">
				<th id="${d.key }">${d.key }-${d.value }</th>
			</c:forEach>
		</tr>
		<c:forEach items="${data}" var="d">
			<tr>
				<th>${d.treatment}${d.timepoint}</th>
				<c:forEach items="${d.timePointSubjectData}" var="m">
					<c:choose>
						<c:when test="${m.value.supervisedBy ne null}">
							<td bgcolor="#33d7ff" id="${m.value.id}">
								${m.value.actualtime}</td>
						</c:when>
						<c:otherwise>
							<td id="${m.value.id}">${m.value.actualtime}</td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	<div id="doseDetails">
		<c:forEach items="${needToSupervice}" var="doseTimePoint">
			<div id="div_${doseTimePoint.id}">
				<table border="1">
					<tr><th>Subject</th><td>${doseTimePoint.subjectNo}</td></tr>
					<tr><th>Dose Time</th><td>${doseTimePoint.actualtime}</td></tr>
					<tr><th>Dose Date</th><td>${doseTimePoint.actualDate}</td></tr>
					<tr><th>Done By</th><td>${doseTimePoint.doseDoneBy.username}</td></tr>
					<tr><th>Subject</th><td>${doseTimePoint.subjectNo}</td></tr>
					<c:if test="${doseTimePoint.subjectDosePerametres ne null}">
						<tr><th colspan="2">Dose Parameters</th> </tr>
						<c:forEach items="${doseTimePoint.subjectDosePerametres}" var="peram"></c:forEach>
						<tr><th>${peram.dosePerameter.label }</th><td>${peram.perameterValue}</td></tr>
					</c:if>
					<tr><td colspan="2"><input type="button" value="Save" onclick="saveDoseSupervise('${doseTimePoint.id}')"/>
				</table>
			</div>
		</c:forEach>
	</div>


</body>
<script type="text/javascript">
	function saveDoseSupervise(id) {
		$('#div_' + id).remove();
		var url = mainUrl + '/study/clinical/saveDoseSuperviseInfo/' + id;
		synchronousAjaxCall(url);
	}
// 		function saveDoseSupervise(id) {
// 			$('#div_' + id).remove();
// 			var url = mainUrl + '/testController/saveDoseSuperviseInfoTest/' + id;
// 			synchronousAjaxCall(url);
// 		}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		var url = mainUrl + "/testWebSocket/dosingSuperwise";
		var eventSource = new EventSource(url);
		eventSource.addEventListener("dosingSuperwise", function(event) {
			var articalData = JSON.parse(event.data);
			console.log("Event : " + event.data);
			var id = articalData.id;
			var data = articalData.data;
			// 			alert(data);
			addTable(id, data);
		});
	});

	function addTable(id, data) {
		var oldData = $("#doseDetails").html();
		$("#doseDetails").html(oldData + data);
	}

	$(document).ready(function() {
		var url = mainUrl + "/testWebSocket/dosingAfterSave";
		var eventSource = new EventSource(url);
		eventSource.addEventListener("dosingMap", function(event) {
			var articalData = JSON.parse(event.data);
			console.log("Event : " + event.data);
			var id = articalData.id;
			var data = articalData.data;
			var color = articalData.color;
// 						alert(data);
			addBlock(id, data, color);
		});
	});

	function addBlock(id, data, color) {
		if (id != '0') {
			if (color == 'blue')
				$("#" + id).html('<font color="blue">' + data + '</font>')
			else
				$("#" + id).html('<font color="red">' + data + '</font>')
		}
	}
</script>
</html>