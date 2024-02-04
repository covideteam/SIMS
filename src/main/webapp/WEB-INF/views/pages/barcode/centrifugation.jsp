<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h3 class="card-title">Centrifuge</h3>
		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<table id="instrumentTable">
				<tr>
					<th>Instrument Name</th>
					<td><input type="text" name="name" id="name" /></td>
				</tr>
				<tr>
					<th>Code</th>
					<td><input type="text" name="code" id="code" /></td>
				</tr>
				<tr>
					<th>Details</th>
					<td><textarea rows="5" cols="30" name="details" id="details"></textarea></td>
				</tr>
				<tr>
					<td><input type="button" value="Save"
						onclick="saveInstrument()" /></td>
					<td><input type="button" value="Clear"
						onclick="clearForm()" /></td>
				</tr>
				<tr>
			</table>
			<table id="maintable" class="table table-bordered table-striped" border="1" style="width: 80%">
				<tr>
					<th>Instrument Name</th>
					<th>Code</th>
					<th>Details</th>
					<th>Active Status</th>
					<th>Print Bar-code</th>
				</tr>
				<c:forEach items="${centrifugations}" var="cen">
					<tr>
						<td>${cen.name}</td>
						<td>${cen.code}</td>
						<td>${cen.insturmentDesc}</td>
						<td>${cen.activeStatus}</td>
						<td><input type="button" value="Print"
							onclick="printBarcode('${cen.id}')"></td>
					</tr>
				</c:forEach>
			</table>

		</div>
	</div>
	<c:url value="/barcodelabels/centrifugationBarcodesPrint"
		var="barcodelable" />
	<sf:form action="${barcodelable}" method="POST" id="formsubmit">
		<input type="hidden" name="barcodeId" id="barcodeId" />
	</sf:form>
</body>
<script type="text/javascript">
	function printBarcode(id) {
		$("#barcodeId").val(id);
		$("#formsubmit").submit();
	}
	function saveInstrument(){
		var name= $("#name").val();
		var code = $("#code").val();
		var details = $("#details").val();
		console.log(details);
		var url = mainUrl+"/barcodelabels/saveCentrifugation/"+name+"/"+code+"/"+details;
		console.log(mainUrl+"/barcodelabels/saveCentrifugation/"+name+"/"+code+"/"+details);
		var result = synchronousAjaxCall(url);
		if(result != '' || result != 'undefined'){
			$("#maintable tbody").append(result);
		}
		
	}
</script>
</html>