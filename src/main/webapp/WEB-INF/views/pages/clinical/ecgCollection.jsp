<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<script type="text/javascript">
		var treatmentsIds = [];
	</script>
</head>
<body>
<input type="hidden" id="Type" value=""/> 
<div class="card">
	<div class="card-header">
		<h3 class="card-title">ECG Collection Form</h3>
	</div>
	<!-- /.card-header -->
	<div class="card-body">
		<c:url value="/study/clinical/stdClinicalECGCollectionSave" var="stdClinicalVitalCollectionSave" />
		<sf:form action="${stdClinicalVitalCollectionSave}" method="POST"  id="formsumit" >
			<input type ="hidden" name="subject" id="subject"/><br/>
			<input type ="hidden" name="timePoint" id="timePointId"/><br/>
			<input type ="hidden" name="subtimePointId" id="subtimePointId"/><br/>
			<input type ="hidden" name="ranningTime" id="ranningTime"/><br/>
			<input type ="hidden" name="ranningTime1" id="ranningTime1"/><br/>
				<table id="example1" class="table table-bordered table-striped">
					<tr>
						<td>Scan Barcode</td>
						<td><input type="text" name="barcode" id="barcode" onchange="barcodevalue(this.value)"/><font color="red" id="barcodeMsg"></font> </td>
						
					</tr>
					<c:if test="${ecgCollection}">
						<tr><td>Time Point : </td>
							<td colspan="7">
								<c:choose>
									<c:when test="${timeWisePoints == true }">
										<c:forEach items="${timePoints}" var="mp">
											<b>${mp.key.treatmentName}</b>
											<script type="text/javascript">
												treatmentsIds.push("timepoint_${mp.key.id}");
											</script>
												<c:forEach items="${mp.value}" var="t" varStatus="loop">
													<c:choose>
														<c:when test="${loop.count eq 1}">
															<input type="radio" name="timepoint_${mp.key.id}" id="timepoint_${t.id}" checked="checked" value="${t.id}">${t.timePoint}&nbsp;&nbsp;&nbsp;&nbsp;	
														</c:when>
														<c:otherwise>
															<input type="radio" name="timepoint_${mp.key.id}" id="timepoint_${t.id}"  value="${t.id}">${t.timePoint}&nbsp;&nbsp;&nbsp;&nbsp;
														</c:otherwise>
													</c:choose>
													<br/>
												</c:forEach>	
										</c:forEach>
									</c:when>
									<c:otherwise>
										<script type="text/javascript">
												treatmentsIds.push("timepoint_0");
											</script>
										<c:forEach items="${timePoints}" var="t" varStatus="loop">
											<c:choose>
												<c:when test="${loop.count eq 1}">
													<input type="radio" name="timepoint_0" id="timepoint_${t.id}" checked="checked" value="${t.id}">${t.timePoint}&nbsp;&nbsp;&nbsp;&nbsp;	
												</c:when>
												<c:otherwise>
													<input type="radio" name="timepoint_0" id="timepoint_${t.id}"  value="${t.id}">${t.timePoint}&nbsp;&nbsp;&nbsp;&nbsp;
												</c:otherwise>
											</c:choose>
											<br/>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								
								<font color="red" id="timepointMsg"></font>
							</td>
						</tr>			
					</c:if>

					<tr>
						<td>Subject: </td>
						<td colspan="7" id="subjectMsg">
						</td>
					</tr>
					<tr>
						<td id="ecgTestInfo" colspan="8"></td>
					</tr>
				</table>
				<table id="example2" class="table table-bordered table-striped">
					<tr>
						<td ><input type="button" value="Save" class="btn btn-primary" onclick="submitForm()"/></td>
						<td ><input type="button" value="Clear" class="btn btn-primary" onclick="clearData()"/></td>
					</tr>
				</table>
		</sf:form>
	</div>
</div>




	
<!-- 	<input type ="hidden" name="alcohol0" id="alcohol0"/><br/> -->
<!-- 	<input type ="hidden" name="drugAbuse0" id="drugAbuse0"/><br/> -->
<%-- </sf:form> --%>

</body>

<script type="text/javascript">
$("#barcode").focus();
var ecgCollection = ${ecgCollection};
var doseStatus = ${doseStatus};
var currentPeriod = ${currentPeriod};
var testConditions = "";
function clearForm(){
	$("#barcode").val("");
	$("#barcodeMsg").html("");
	$("#subjectMsg").html("");
	$("#subject").val("");	
	$("#ecgTestInfo").html("");
	$("#ranningTime").val("");
	$("#ranningTime1").val("");
	
	testConditions = "";
}
var pattrenstartfalg = true;
var pattrenendfalg = true;


$("#barcode").on('input',function(e){
	var barcode = $("#barcode").val();
	var length = barcode.length;
	if(length == 23){
		var prefix = barcode.substr(0,2);
		if(prefix == "01"){//subject
			clearForm();
			if(doseStatus  != true){
				var selecteTimePointIds = "";
				var status = false;
				$.each( treatmentsIds, function( index, value ){
					var  radioValue= $("input[name='"+value+"']:checked").val();
					if(typeof radioValue !== "undefined"){
						if(selecteTimePointIds != ''){
							selecteTimePointIds = selecteTimePointIds+","+radioValue;
						}else
							selecteTimePointIds = radioValue; 
						status = true; 
					} 				    
				});
				if(!status){
					$("#timepointMsg").html("Please, Select Timepoint")
				}else{
					$("#timePointId").val(selecteTimePointIds);
		 			alert(mainUrl+"/study/clinical/subjectEcgInfo/"+barcode+"/"+selecteTimePointIds+"/"+currentPeriod);
					var result = synchronousAjaxCall(mainUrl+"/study/clinical/subjectEcgInfo/"+barcode+"/"+selecteTimePointIds+"/"+currentPeriod);
					if(result != '' || result != 'undefined'){
						$("#ranningTime1").val(ranningTime);
						var c = result.split('--');
						if(c.length > 1){
		 					if(c[0] == '0'){
								var collectionStatus = c[1];
								var period = c[2];
								var seqno = c[3];
								$("#subject").val(barcode);
								$("#subjectMsg").html(c[4]);
								$("#subtimePointId").val(c[5]);
							}else if(c[0] == '1'){
								$("#barcodeMsg").html(c[1]);	
							}
						}
					}		
					
				}
			}

		}else{
			$("#barcodeMsg").html("In-Valied Barcode");
		}
		$("#barcode").val("");
	}
});
function displayVitalInf0(cvtppId, id){
	var result = synchronousAjaxCall(mainUrl+"/study/clinical/ecgTestInfo/"+cvtppId);
	if(result != '' || result != 'undefined'){
		$("#ecgTestInfo").html(result);
		$("#timePointId").val(cvtppId);
	}
}
</script>
<script type="text/javascript">
function submitForm(){
	$("#ranningTime").val(ranningTime);
	if($("#subject").val() != ''){
		$("#formsumit").submit();	
	}else{
		$("#barcodeMsg").html("Please, Scan Subject Barcodes.");
	}
	
	
}

function clearData(){
	$("#barcodeMsg").html("");
	$("#subjectMsg").html("");
	
	$("#vitalTimePointInfo").html("");
	
	$("#ecgTestInfo").html("");
	$("#timePoint").val("");
	
	$("#subject").val("");
	$("#timePoint").val("");
	$("#pulseRate").val("");
	$("#oralTemp").val("");
	$("#bp").val("");
	$("#respiratoryRate").val("");
	$("#wellbeingAscertained").val("");
	$("#alcohol0").val("");
	$("#drugAbuse0").val("");
	
	$("#pulseRate0Msg").html("");
	$("#oralTemp0Msg").html("");
	$("#bp0Msg").html("");
	$("#respiratoryRate0Msg").html("");
	$("#wellbeingAscertained0Msg").html("");
	
}

</script>

</html>

