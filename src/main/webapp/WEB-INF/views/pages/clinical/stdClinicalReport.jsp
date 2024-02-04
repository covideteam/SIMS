<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="card">
	<div class="card-header">
		<h3 class="card-title">
			<c:choose>
				<c:when test="${type eq 'sampleCollection' }">Sample Collection Report</c:when>
				<c:when test="${type eq 'dosing' }">Dosing Report</c:when>
				<c:when test="${type eq 'vitalCollection' }">Vital Collection Report</c:when>
				<c:when test="${type eq 'sampleSeparation' }">Sample Separation Report</c:when>
				<c:when test="${type eq 'mealCollection' }">Meal Collection Report</c:when>
				<c:when test="${type eq 'ecgCollection' }">ECG Report</c:when>
				<c:when test="${type eq 'dosingDashBord' }">Dosing Dash board</c:when>
				<c:when test="${type eq 'sampleCollectionDashBord' }">Sample Collection Dash board</c:when>
				<c:when test="${type eq 'sampleSapartionDashBord' }">Sample Separation Dash board</c:when>
				<c:when test="${type eq 'mealCollectionDashBord' }">Meal Collection Dash board</c:when>
				<c:when test="${type eq 'ecgCollectionDashBord' }">ECG Time Dash board</c:when>
				<c:when test="${type eq 'vitalCollectionDashBord' }">Vital Collection Dash board</c:when>




				<c:otherwise></c:otherwise>

			</c:choose>
		</h3>
	</div>
	<!-- /.card-header -->
	<div class="card-body">
		<input type="hidden" name="type" id="type" value="${type}" />
		<table class="table table-bordered table-striped">
			<tr>
				<td>Select Period</td>
				<td><select name="period" class="form-control" id="period">
						<option value="-1">--Select--</option>
						<c:forEach items="${periods}" var="p">
							<option value="${p.id}">P-${p.periodNo}</option>
						</c:forEach>
				</select></td>
			</tr>
			<c:if
				test="${(type ne 'sampleCollectionDashBord') && (type ne 'dosingDashBord') && (type ne 'sampleSapartionDashBord') 
					&& (type ne 'mealCollectionDashBord') && (type ne 'vitalCollectionDashBord') && (type ne 'ecgCollectionDashBord')}">
				<tr>
					<td>Select Subject</td>
					<td><select name="subject" class="form-control" id="subject"
						onchange="dosesingReport(this.value)">
							<c:forEach var="v" items="${vols}">
								<option value="${v.seqNo}">${v.subjectNo}</option>
							</c:forEach>
					</select></td>
				</tr>
			</c:if>

			<tr>
				<td colspan="2"><input type="button" value="View"
					class="btn btn-primary" onclick="submitForm()" /></td>
			</tr>
		</table>

		<div id="report"></div>

	</div>
</div>


<script>
	function submitForm() {
		var periodId = $("#period").val();

		var subject = "";
		if ($("#type").val() != 'sampleCollectionDashBord')
			subject = $("#subject").val();
		if (periodId != -1) {
			var result = "";
			if ($("#type").val() == 'sampleCollection')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/stdClinicalSampleCollectionReportTable/"
						+ periodId + "/" + subject);
			else if ($("#type").val() == 'dosing')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/clinicalDosingReportTable/"
						+ periodId + "/" + subject);
			else if ($("#type").val() == 'vitalCollection')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/clinicalVitalReportTable/"
						+ periodId + "/" + subject);
			else if ($("#type").val() == 'sampleSeparation')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/sampleSeparation/" + periodId + "/"
						+ subject);
			else if ($("#type").val() == 'mealCollection')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/mealCollection/" + periodId + "/"
						+ subject);
			else if ($("#type").val() == 'ecgCollection')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/ecgCollection/" + periodId + "/"
						+ subject);
			else if ($("#type").val() == 'sampleCollectionDashBord')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/sampleCollectionDashBordTable/"
						+ periodId);
			else if ($("#type").val() == 'dosingDashBord')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/dosingDashBordTable/" + periodId);
			else if ($("#type").val() == 'sampleSapartionDashBord')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/sampleSapartionDashBordTable/"
						+ periodId);
			else if ($("#type").val() == 'mealCollectionDashBord')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/mealCollectionDashBordTable/"
						+ periodId + "/" + $("#type").val());
			else if ($("#type").val() == 'vitalCollectionDashBord')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/vitalCollectionDashBordTable/"
						+ periodId + "/" + $("#type").val());
			else if ($("#type").val() == 'ecgCollectionDashBord')
				result = synchronousAjaxCall(mainUrl
						+ "/clinicalReport/ecgCollectionDashBordTable/"
						+ periodId + "/" + $("#type").val());
			
			
			if (result != '' || result != 'undefined') {
				$("#report").html(result);
			}
		} else {
			$("#report").html("");
		}

	}
</script>
