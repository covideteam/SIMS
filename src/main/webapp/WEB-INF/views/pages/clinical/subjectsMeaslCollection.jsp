<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script src='/SIMS/static/js/cpu/validation.js'></script>
<script src='/SIMS/static/js/cpu/mealCollection.js'></script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h3 class="card-title">Meals Collection Form</h3>
		</div>
		<!-- /.card-header -->
		<div class="card-body">
			<table id="example0" class="table table-bordered table-striped">
				<tr>
					<td>Scan Barcode</td>
					<td><input type="text" name="barcode" id="barcode"
						onchange="barcodevalue(this.value)" /><font color="red"
						id="barcodeMsg"></font></td>
				</tr>
				<tr>
						<td>Time Point :</td>
						<td></td>
				</tr>
						
			</table>
		</div>
	</div>

</body>
</html>