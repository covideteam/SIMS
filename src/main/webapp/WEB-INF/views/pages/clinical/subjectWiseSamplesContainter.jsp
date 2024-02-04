<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
input[type="file"]::file-selector-button {
  border: 1px solid #1F618D; 
  padding: 0.2em 0.4em;
  border-radius: 0.2em;
  background-color: #1F618D;
  transition: 1s;
  color:white;
  text:Attach;
}

/* input[type="file"]::file-selector-button:hover {
   background-color: #6c757d; 
  border: 1px solid #6c757d;
  color:white;
} */
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
							<td style="width: 200px;"><label for="dataLogger"class=" col-form-label" style="color: #212529;">DataLogger ID:</label></td>
							<td ><input type="text" class="form-control" id="dataLogger" name="dataLogger" style="width: 200px;"></td>
						</tr>
						<tr >
							<td style="width: 200px;"><label for="dataLoggerFile"class=" col-form-label" style="color:#212529;">DataLogger File:</label></td>
							<td ><label><input type="file" name="file" size="50" style="padding-top: 10px;"/></label></td>
						</tr>
						
						<tr>
							<td style="width: 300px;"><label for="dataLoggerFile"class=" col-form-label" style="color:#ffa500;">Subject Wise Samples Container</label></td>
							<td ><label><a href="#">Print Label</a></label><label><a style="color:#6fdae8;padding-left:10px" href="#">Manual Label</a></label></td>
						</tr>
						<tr>
							<td style="width: 200px;"><label for="dataLogger"class=" col-form-label" style="color: #212529;">Shipment</label></td>
							<td ><div class="form-check form-check-inline">
							<input type="radio" name="shipment" value="Yes" id="shipmentYes" class="form-check-input" required onchange="shipmentFunction('shipmentYes', 'shipmentMsg')" />
								<label class="form-check-label" for="shipmentYes">Yes</label>
								</div><div class="form-check form-check-inline">
								<input type="radio" name="shipment" value="Yes" id="shipmentNo" class="form-check-input" required onchange="shipmentFunction('shipmentNo', 'shipmentMsg')" />
								<label class="form-check-label" for="shipmentNo">No</label></div>
							</td>
						</tr>
						<tr><td><label class=" col-form-label" style="color: #212529;">Note</label></td><td>If Shipment is yes then shipment form will be opened else storage form will be opened</td></tr>
                   
					</table>

					<table id="sampleContainer" class="table table-striped table-bordered " style="width: 100%;">
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

					<!-- <table style="width: 70%; font-size: 15px;margin-top:5%;">
						<tr>
							<td style="width: 200px;"><label for="dataLogger"class=" col-form-label" style="color: #212529;">Shipment</label></td>
							<td ><div class="form-check form-check-inline">
							<input type="radio" name="shipment" value="Yes" id="shipmentYes" class="form-check-input" required onchange="shipmentFunction('shipmentYes', 'shipmentMsg')" />
								<label class="form-check-label" for="shipmentYes">Yes</label>
								</div><div class="form-check form-check-inline">
								<input type="radio" name="shipment" value="Yes" id="shipmentNo" class="form-check-input" required onchange="shipmentFunction('shipmentNo', 'shipmentMsg')" />
								<label class="form-check-label" for="shipmentNo">No</label></div>
							</td>
						</tr>
						<tr><td><label class=" col-form-label" style="color: #212529;">Note</label></td><td>If Shipment is yes then shipment form will be opened else storage form will be opened</td></tr>
                   </table> -->
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var table = $('#sampleContainer').DataTable({
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