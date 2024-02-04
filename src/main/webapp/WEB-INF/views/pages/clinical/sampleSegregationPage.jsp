<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<script src='/SIMS/static/js/cpu/segregation.js'></script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h4>Sample Segregation Form</h4>
		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<table class="table table-bordered table-striped">
				<tr>
					<td><input type="button" id="reset" value="Reset" /> <!-- Add The Reset code -->
					</td>
				</tr>
			</table>
			<table id="maintable">
				<tr>
					<td>Scan Barcode :</td>
					<td><input type="text" name="barcode" id="barcode"
						class="form-control" /> <font style="color: red" id="barcodeMsg"></font>
						<input type="hidden" id="timePintDataAndTime" value="" /></td>
					<td colspan="4" />
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td>Period :</td>
					<td><select id="period" name="period" class="form-control"
						onchange="showRacks()">
							<!-- Current Project Period Count -->
							<option value="-1">---Select---</option>
							<c:forEach items="${periodList}" var="pno">
								<option value="${pno.id}">${pno.periodName}</option>
							</c:forEach>
					</select><font color="red" id="periodMsg"></font></td>
				</tr>
				<tr>
					<td>Clean Area :</td>
					<td><select id="cleanArea" name="cleanArea"
						class="form-control">
							<option value="-1">---Select---</option>
							<option value="Yes">Yes</option>
							<option value="No">No</option>
					</select><font color="red" id="cleanAreaMsg"></font></td>
				</tr>
				<tr>
					<td>Dry Ice && Data logger :</td>
					<td><select id="dataLogger" name="dataLogger"
						class="form-control">
							<option value="-1">---Select---</option>
							<option value="Yes">Yes</option>
							<option value="No">No</option>
					</select><font color="red" id="dataLoggerMsg"></font></td>
				</tr>
				<tr>
					<td>Aliquot :</td>
					<td><select id="aliquot" name="aliquot" class="form-control"
						onchange="showRacks()">
							<option value="-1">--Select--</option>
							<c:forEach items="${noOfVials}" var="no">
								<option value="${no}">${no}</option>.
						</c:forEach>
					</select><font color="red" id="aliquotMsg"></font></td>
					<td>All Subject : Yes<input type="radio" name="allSubject"
						id="Yes" value="Yes"> No<input type="radio"
						name="allSubject" id="No" value="No"><font color="red"
						id="allSubjectMsg"></font></td>
					<td>Subject :</td>
					<td><input type="text" name="subject" id="subject"
						class="form-control"></td>
				</tr>
				<tr>
					<td>Aliquot Shelfs :</td>
					<td id="aliquotShelfs"></td>
				</tr>
				<tr>
					<td colspan="6">
						<table border="1" style="width: 100%">
							<thead>
								<tr>
									<th>RACK NUMBER</th>
									<th>Time Point</th>
									<th>Subjects</th>
								</tr>
							</thead>
							<tbody id="rackTable">

							</tbody>
						</table> <font color="red" id="rackTableMsg"></font>
					</td>
				</tr>
				<tr>
					<td>Subject Sample Container :</td>
					<td><input type="text" id="sampleContainer"
						name="sampleContainer" class="form-control" /></td>

					<td>&nbsp;&nbsp;All Samples Verified : Yes<input type="radio"
						name="allSamplesVerified" id="Yes" value="Yes"> No<input
						type="radio" name="allSamplesVerified" id="No" value="No"></td>
					<td><input type="button" value="Button"
						onclick="printSubjectSamplesContainter()" class="btn btn-primary" /></td>
				</tr>
				<tr>
					<td colspan="6">
						<table border="1" style="width: 100%">
							<thead>
								<tr>
									<th>SAMPLE NUMBER</th>
									<th>TIME POINT</th>
									<th>ALIQUET VOLUEM</th>
								</tr>
							</thead>
							<tbody id="dataTable">

							</tbody>
						</table> <font color="red" id="dataTableMsg"></font>
					</td>
				</tr>
			</table>

		</div>
	</div>
	<c:url value="/condition/saveConditionForm" var="saveValues" />
	<form:form action="${saveValues}" method="post" id="form">
		<input type="hidden" name="studyId" id="studyId"/>
		<input type="hidden" name="periodId" id="periodIdb"/>
		<input type="hidden" name="cleanArea" id="cleanAreab"/>
		<input type="hidden" name="dataLogger" id="dataLoggerb"/>
		<input type="hidden" name="aliquot" id="aliquotb"/>
		<input type="hidden" name="allSubject" id="allSubjectb"/>
		<input type="hidden" name="subject" id="subjectb"/>
		<input type="hidden" name="centrifugeEndTime" id="centrifugeEndTime"/>
		<div id="formFields"></div> 
	</form:form>
					
</body>
<!-- js link not add  -->

</html>