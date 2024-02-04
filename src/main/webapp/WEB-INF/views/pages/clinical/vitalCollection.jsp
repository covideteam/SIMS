<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<script src='/SIMS/static/js/cpu/devidation.js'></script>
	<script src='/SIMS/static/js/cpu/validation.js'></script>
	<script src='/SIMS/static/js/cpu/vitalCollection.js'></script>
	<script type="text/javascript">
$(function(){
	$("#collectionTr").css("display","none");
});
</script>
</head>
<body>
<input type="hidden" id="Type" value=""/>
 
				<div class="row">
	                 <div class="col-sm-3">
	                   	<label>Scan Barcode</label>
	                </div>
	                <div class="col-sm-9">
	                   <input type="text" name="barcode" id="barcode" class="form-control"  style="width:40%;display:inline;"/>
					 	 <a class="fa fa-camera neo-qrcodescanner" style="margin-left:5px;font-size: 20px;"></a>
					 	 <div style="color: red;" id="barcodeMsg"></div> 
				    </div>
               </div>
	               
             <div class="row">
                	<div class="col-sm-3">
	                  <label>Subject</label>
                  </div>
                  <div class="col-sm-9">
                  		<div id="subjectMsg"></div>
                  </div>
                </div>
              <div class="row">
                <div class="col-sm-3">
	                 	<label>Timepoint</label>
                  </div>
                  <div class="col-sm-9">
                  		<div id="timepointsTd"></div>
                  </div>
                </div>
             <div class="row">
				<div class="col-sm-3">
				    <div id="posTextDiv">
				    	<label>Positions</label>
				    </div>
				</div>
				<div class="col-sm-9">
					<div id="tpPositionDiv"></div>
					<div id="tpPositionDivMsg" style="color: red;"></div>
				</div>
			</div>
            <div class="row">
             	<div class="col-sm-12">
	                	<div id="vitalTestInfo"></div>
                </div>
             </div>
       		 
              

              
              <div class="mt-4 pt-2" align="center">
                	<input type="button" value="Save" id="save_btn" class="btn btn-primary btn-sm" style="font-size: small;" onclick="saveVitals()"/>
              </div>
     

<!-- <div class="card"> -->
	<!-- <table id="example1" class="table table-bordered">
		<tr>
			<td style="text-align: center; width: 30%;">Scan Barcode</td>
			<td>
				<div style="width: 40%;">
					<input type="text" name="barcode" id="barcode" class="form-control"/>
				</div>
				<div style="color: red;" id="barcodeMsg"></div> 
			</td>
		</tr>
		<tr>
			<td>Subject: </td>
			<td id="subjectMsg"></td>
		</tr>
		<tr>
			<td>Time Point : </td>
			<td id="timepointsTd"></td>
		</tr>	
		<tr style="border: none;">
			<td colspan="2"><div id="tpPositionDiv"></div><div id="tpPositionDivMsg" style="color: red;"></div></td>
		</tr>
		<tr>
			<td id="vitalTestInfo" colspan="2"></td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<input type="button" value="Save" id="save_btn" class="btn btn-primary btn-sm" style="font-size: small;" onclick="saveVitals()"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="Clear" class="btn btn-primary btn-sm" style="font-size: small;" onclick="clearData()"/>
			</td>
			
		</tr>
	</table> -->
<!-- 	</div> -->
</body>


</html>

