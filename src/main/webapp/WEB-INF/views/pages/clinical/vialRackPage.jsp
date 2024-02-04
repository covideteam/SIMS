<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<script src='/SIMS/static/js/cpu/vialRackCollection.js'></script>
	<script src='/SIMS/static/js/cpu/validation.js'></script>
	<script type="text/javascript">
	$( document ).ready(function() {
		$('#example2').hide();
	});
	</script>
</head>
<body>
<input type="hidden" id="Type" value=""/>
 <table id="example1" class="table table-bordered" style="width: 100%; border: none;">
	<tr>
		<td align="center">Scan Barcode</td>
		<td>
			<div style="width: 45%;">
				<input type="text" name="barcode" id="barcode" class="form-control" onchange="barcodevalue(this.value)"/>
			</div>
			<div style="color: red;" id="barcodeMsg"></div> 
		</td>
	</tr>
	<tr>
		<td>Rack No : </td>
		<td colspan="7" id="deefreeDataTd"></td>
	</tr>	
</table>
<table  class="table table-bordered" style="width: 100%; border: none;">	
	<thead>
		<tr>
			<th>Subject</th>
			<th>Aliquot</th>
			<th>Time Point</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody id="vialRackInfo">
	</tbody>
</table>
<table id="example2" class="table table-bordered" style="width: 100%; border: none;">
	<tr align="center">
		<td >
			<input type="button" value="Save" class="btn btn-primary btn-sm" onclick="saveVialData()"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="Clear" class="btn btn-primary btn-sm" onclick="clearData()"/>
		</td>
	</tr>
</table>
</body>


</html>

