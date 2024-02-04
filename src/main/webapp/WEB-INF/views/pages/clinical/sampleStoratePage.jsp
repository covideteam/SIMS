<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
<script src='/SIMS/static/js/cpu/validation.js'></script>
<script src='/SIMS/static/js/cpu/sampleStorate.js'></script>
<style type="text/css">
	td{
	width:220px;
	}
	</style>
</head>
<body>
	<div class="col-md-12 col-sm-12 ">
		<div class="x_panel">
			<br>
			<table id="maintable" style="width: 50%; font-size: 15px;">
				<tr>
					<td><label for="barcode"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageScanBarcode"></spring:message></label></td>
					<td><input type="text" name="barcode" id="barcode" style="width:220px" class="form-control" /> <font
						style="color: red" id="barcodeMsg"></font> <input type="hidden"
						id="timePintDataAndTime" value="" /></td>
				</tr>
				<tr>
					<td><label for="periodtd"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStoragePeriodNo"></spring:message></label></td>
					<td id="periodtd"></td>
				</tr>
				<tr>
					<td><label for="timepointtd"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageTimePoint"></spring:message></label></td>
					<td id="timepointtd"></td>
				</tr>
				<tr>
					<td><label for="totalSample"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageTotalStorage"></spring:message></label></td>
					<td id="totalSample"></td>
				</tr>
				<tr>
					<td><label for="aliquottd"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageAliquot"></spring:message></label></td>
					<td id="aliquottd"></td>
				</tr>
				<tr>
					<td><label for="aliquotSubjectstd"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageTotalSample"></spring:message></label></td>
					<td id="aliquotSubjectstd"></td>
				</tr>
				<tr>
					<td><label for="shelftd"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageShelfId"></spring:message></label></td>
					<td id="shelftd"></td>
				</tr>
				<tr>
					<td><label for="deepfreezertd"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageDeepfreezerId"></spring:message></label></td>
					<td id="deepfreezertd"></td>
				</tr>
				<tr>
					<td><label for="racksTable"class=" col-form-label" style="color: #212529;"><spring:message code="label.sampleStorageRackLockCover"></spring:message></label></td>
					<td>
						<table id="racksTable" class="table table-bordered table-striped"> 
							<thead><tr><th><spring:message code="label.sampleStorageRack"></spring:message></th><th><spring:message code="label.sampleStorageTimepnt"></spring:message></th></tr></thead>					
							<tbody>
							</tbody>
						</table>
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td>Missed Subjects:</td> -->
<!-- 					<td><input type="text" name="storageMissedSubjects" id="storageMissedSubjects" /></td> -->
<!-- 				</tr> -->
				
				
				<tr>
					<td colspan="2" style="text-align:center"><input type="button" class="btn btn-primary" value="<spring:message code="label.confirm"></spring:message>" id="conform" onclick="saveSampleStorage()"/>
					<input type="button" style="padding-left:10px" class="btn btn-primary" value="<spring:message code="label.reSet"></spring:message>" id="reset" onclick="resetForm()"/></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>