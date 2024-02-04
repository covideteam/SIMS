<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
#commentsList_wrapper{
margin-top:5%;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>SUBJECT WISE SAMPLES CONTAINTER</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					
					<table style="width: 70%; font-size: 15px;">
						<tr>
							<td style="width: 200px;"><label for="scanBarcode"class=" col-form-label" style="color: #212529;">Scan Barcode:</label></td>
							<td ><input type="text" class="form-control" id="scanBarcode" name="scanBarcode" style="width: 200px;"></td>
						</tr>
						<tr >
							<td style="width: 200px;"><label for="projectNo"class=" col-form-label" style="color:#212529;">Project No</label></td>
							<td ><label>As/Wa/00912</label><input type="hidden" name="projectNo" id="projectNo"></td>
						</tr>
						<tr >
							<td style="width: 200px;"><label for="period"class=" col-form-label" style="color:#212529;">Period</label></td>
							<td ><label >As/Wa/00912</label><input type="hidden" name="period" id="period"></td>
						</tr>
						<tr >
							<td style="width: 200px;"><label for="sampleStatus"class=" col-form-label" style="color:#212529;">Sample Status</label></td>
							<td ><select name="sampleStatus" id="sampleStatus" class="form-control" style="width: 200px;"><option value="0">---select----</option></select></td>
						</tr>
						<tr >
							<td style="width: 200px;"><label for="containerStatus"class=" col-form-label" style="color:#212529;">Cotainer Status</label></td>
							<td ><select name="containerStatus" id="containerStatus" class="form-control" style="width: 200px;"><option value="0">---select----</option></select></td>
						</tr>
						<tr>
							<td style="width: 200px;"><label for="dataLogger"class=" col-form-label" style="color: #212529;">DataLogger ID:</label></td>
							<td ><label>1234</label><input type="hidden" class="form-control" id="dataLogger" name="dataLogger"></td>
						</tr>
						<tr >
							<td style="width: 200px;"><label for="frequencyOfMonitoring"class=" col-form-label" style="color:#212529;">Frequency of Monitoring</label></td>
							<td ><input type="text" class="form-control" id="frequencyOfMonitoring" name="frequencyOfMonitoring" style="width: 200px;"></td>
						</tr>
						<tr >
							<td style="width: 200px;"><label for="frequencyOfMonitoring"class=" col-form-label" style="color:#212529;">Comments</label></td>
							<td ><textarea  class="form-control" id="frequencyOfMonitoring" name="frequencyOfMonitoring" style="width: 200px;height:20%"></textarea></td>
						</tr>
						
						</table>
					<table id="shipmentList" class="table table-striped table-bordered " style="width: 100%;">
						<thead>
							<tr>
								<th>Subject Wise Samples Container</th>
								<th>Subject Numbers</th>
								<th>Aliquot</th>
								<th>No.of Samples</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>01</td>
								<td>S01-S05</td>
								<td>A1</td>
								<td>20</td>
								<td><i class="fa fa-trash" aria-hidden="true" title="Delete"></i></td>
							</tr>
						</tbody>
					</table>
					<table id="commentsList" class="table table-striped table-bordered " style="width: 100%;">
						<thead>
							<tr>
								<th>Subject Number</th>
								<th>Timepoint</th>
								<th>Comment</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>01</td>
								<td>10.00hr</td>
								<td>Missing</td>
								<td><i class="fa fa-trash" aria-hidden="true" title="Delete"></i></td>
							</tr>
						</tbody>
					</table>
					
					
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">

var table = $('#shipmentList').DataTable({
    searchBuilder: true,
    "language": {
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
		 }
});
var table = $('#commentsList').DataTable({
    searchBuilder: true,
    "language": {
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
		 }
});
</script>
</html>