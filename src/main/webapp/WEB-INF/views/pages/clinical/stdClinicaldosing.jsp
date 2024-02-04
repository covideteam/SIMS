<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script src='/SIMS/static/js/cpu/dosing.js'></script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h3 class="card-title">Dosing Form</h3>
		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<input type="hidden" name="subject" id="subject" /> <input
				type="hidden" name="subjectTime" id="subjectTime" /> <input
				type="hidden" name="sachet" id="sachet" /> <input type="hidden"
				name="sachetTime" id="sachetTime" /> <input type="hidden"
				name="collectionTime" id="collectionTime" /> <input type="hidden"
				name="radioButtonValues" id="radioButtonValues" value="" /> <input
				type="hidden" id="appropriatecount" value="0" />
			<table class="ds-table col-6" style="margin: auto;">

				<tr>	
					<td>Scan Barcode</td>
					<td><input type="text" name="barcode" id="barcode"
						class="form-control" /> <font style="color: red" id="barcodeMsg"></font>
						<input type="hidden" id="timePintDataAndTime" value="" /></td>
				</tr>
				<tr>
					<td>Sachet</td>
					<td id="sachetMsg"></td>
				</tr>
				<tr>
					<td>Subject</td>
					<td id="subjectMsg"></td>
				</tr>
				<tr>
					<td>Dose Time</td>
					<td id="collectionTimeTr"></td>
				</tr>
				<tr id="appropriateBox">
					<td colspan='2' id="appropriateBox1"></td>
				</tr>
				<tr>
					<td>Comment</td>
					<td><select name="message" id="message" class="form-control">
							<option value="-1">-Select-</option>
							<c:forEach items="${doseComments}" var="c">
							   <c:if test="${c.code eq 'NILL'}">
							   		<option value="${c.id}">${c.code}</option>
							   </c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;"><input
						type="button" value="Dose" class="btn btn-primary"
						onclick="submitForm()" /> &nbsp;<input type="button"
						value="Clear" class="btn btn-primary" onclick="clearForm()" /></td>
				</tr>
			</table>
		</div>
	</div>
	
</body>
</html>