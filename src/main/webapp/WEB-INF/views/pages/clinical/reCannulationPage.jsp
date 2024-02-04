<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Re Cannulation Page</title>
<script src='/SIMS/static/js/cpu/reCannulation.js'></script>
<script type="text/javascript">
$('#reCannulaDataDiv').css("display", "none");;
</script>
</head>
<body>
	<div class="col-md-12 col-sm-12 ">
		<div class="x_panel">
			<br>
			<div class="row">
				<div class="col-sm-12">
					<table class="table" style="width: 100%;">
						<tr>
							<td style="width: 35%;">Scan Subject :</td>
							<td>
								<input type="text" name="barcode" id="barcode" class="form-control" style="width:40%;display:inline;"/>
							    <div id="barcodeMsg" style="color: red;"></div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<div id="reCannulaDataDiv"></div>
				</div>			
			</div>
		</div>
	</div>
</body>
</html>