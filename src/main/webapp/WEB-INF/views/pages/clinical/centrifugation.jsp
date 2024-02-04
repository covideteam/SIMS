<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<style type="text/css">
/* #centrifuge_wrapper{
margin-top:5%;
} */
#cetrifugeLabelDiv{
   display: none;
}
#centrifugeDataDiv{
  display: none;
}
</style>
<script src='/SIMS/static/js/cpu/validation.js'></script>
</head>
<body>
	
					<div class="form-group row">
						
						<label for="centrifugeId" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.instrumentBarcode"></spring:message></label>
						
						<div class="col-sm-5">
							<input type="text" name="barcode" id="barcode" class="form-control" style="width:220px"/>
							<input type="hidden" style="width:220px" id="timePintDataAndTime" value="" />
							<div id="barcodeMsg" style="color: red;"></div>
						</div>
					</div>
					<div class="form-group row" id="cetrifugeLabelDiv">
						<label for="barcode" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.centrifugeId"></spring:message></label>
						<div class="col-sm-5">
							<div id="centrifugeMsg"></div>
						</div>
					</div>
					
					<%-- <div class="form-group row">
						<div class="col-sm-12">
							<table class="table table-bordered" style="width: 100%; border: none;">
								<tr>
									<td><spring:message code="label.centrifugeSpeed"></spring:message></td>
									<td><input type="text" id="speed" name="speed" class="form-control" style="width:150px"
										readonly="readonly" /></td>
									<td><spring:message code="label.centrifugeProcessTime"></spring:message></td>
									<td><input type="text" id="processTime" name="processTime" class="form-control" style="width:150px"
										readonly="readonly" /></td>
									<td><spring:message code="label.centrifugeTemperature"></spring:message></td>
									<td><input type="text" id="temperature" name="temperature" class="form-control" style="width:150px"
										readonly="readonly" /></td>
								</tr>
							</table>
						</div>
					</div> --%>
					<div class="form-group row">
						<div class="col-sm-5">
							<div id="centrifugeVacutinerMsg" style="color: red;"></div>
						</div>
					</div>
					<div id="centrifugeDataDiv">
					<div class="form-group row">
						<label for="conditions" class="col-sm-3 col-form-label" style="color: #212529;"><b><spring:message code="label.centrifugeConditions"></spring:message>:</b></label>
					</div>
					<div class="form-group row">
						<div class="col-sm-12">
								<table class="table table-bordered" style="width: 100%; border: none;">
									<tr>
										<td><spring:message code="label.centrifugebufferWaterStorage"></spring:message></td>
										<td>
											<input type="radio" name="bufferStorage" value="Yes" class="form-check-input" id="bufferStorageYes" checked="checked">
											<spring:message code="label.gaYes"></spring:message>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="radio" name="bufferStorage" id="bufferStorageNo" class="form-check-input" value="No">
			    							<spring:message code="label.gaNo"></spring:message>
										</td>
										<td>
											<spring:message code="label.centrifugeMissedSubj"></spring:message>
										</td>
										<td>
											<input type="text" name="centrifugeMissedSubjects" class="form-control" style="width:220px" id="centrifugeMissedSubjects"/>
										</td>
									</tr>
								</table>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-sm-5">
							<div id="vacutinersTableMsg"></div>
						</div>
					</div>
					<div class="form-group row">
						<div class="col-sm-12">
						    <div >
						    	<table class="table table-bordered" style="width: 100%; border: none;">
						    	<tr>
						    		<td><spring:message code="label.centrifugeStartTime"></spring:message></td>
						    		<td id="startTimeTd">
						    			<input type="button" id="startButton" class="btn btn-primary" value="Start" onclick="centrifugeStartTimeAdd()" />
						    			<div style="color: red;" id="startMsg"></div>
						    		</td>
						    		<td><spring:message code="label.centrifugeEndTime"></spring:message></td>
						    		<td id="endTimeTd">
						    			<input type="button" id="endButton" class="btn btn-primary" disabled="disabled" value="End" onclick="centrifugeEndTimeAdd()" />
						    			<div style="color: red;" id="endMsg"></div>
									</td>
						    	</tr>
						    </table>
							
						</div>
					</div>
					</div>
					<table id="centrifuge" class="table table-striped table-bordered " style="width: 100%;">
							<thead>
							<tr>
								<th><spring:message code="label.centrifugeProjectNo"></spring:message></th>
								<th><spring:message code="label.centrifugePeriod"></spring:message></th>
								<th><spring:message code="label.centrifugeSubject"></spring:message></th>
								<th><spring:message code="label.centrifugeTimepoint"></spring:message></th>
								<th><spring:message code="label.action"></spring:message></th>
							</tr>
							</thead>
							<tbody id="vacutinersTable">
							
							</tbody>
						</table>
				</div>		
			<!-- <font color="red" id="vacutinersTableMsg"></font> -->
	
</body>

<script src='/SIMS/static/js/cpu/centrifuge.js'></script>
<!-- <script type="text/javascript">
var table = $('#centrifuge').DataTable({
	
    searchBuilder: true,
    "searching": false,
    paging: false,
    ordering: false,
    info: false,
    /* "language": {
        "lengthMenu": "${showLabel} _MENU_ ${entriesLabel}",
		 "search": "${searchLabel}:",
		 "zeroRecords": "${noDtaAvlLabel}",
	     "info": "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
	     "infoEmpty": "${noRcdsLabel}",
	     "infoFiltered": "(${filterLabel} _MAX_ ${totRcdsLabel})",
	     "paginate": {
	         "previous": " ${prevPgLabel}",
	        	 "next": "${nextLabel}"
	       }
		 } */
});
</script> -->
</html>