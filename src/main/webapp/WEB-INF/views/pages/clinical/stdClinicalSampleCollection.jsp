<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<HTML>
<head>
<link href="<c:url value="/static/sweetalert/sweetalert2.css"/>" rel="stylesheet">
	<script src="<c:url value='/static/sweetalert/sweetalert2.js'/>"></script>
	<script src='/SIMS/static/js/cpu/devidation.js'></script>
<script src='/SIMS/static/js/cpu/bloodSampleCollection.js'></script>
<script type="text/javascript">
$('#timePointDeviationtr').css("display", "none");;
$('#deviationsDiv').css("display", "none");
$('#modeOfCollectionDiv').css("display", "none");
$('#deviationsCommentsDiv').css("display", "none");
$('#reasonDiv').css("display", "none");
</script>
</head>
<body>
	
<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				
					<br>
				<table id="example1" style="width: 100%; font-size: 15px;height: 237;">
<!-- 					<tr><th colspan="2">Sample Collection Form</th></tr> -->
					<tr>
						<td><label for="barcode"class=" col-form-label" style="color: #212529;"><spring:message code="label.scanBarcode"></spring:message></label></td>
						<td>
							<input type="text" name="barcode" id="barcode" class="form-control" style="width:40%;display:inline;"/>
							<a class="fa fa-camera neo-qrcodescanner" style="margin-left:5px;font-size: 20px;"></a> 
							<div style="color: red" id="barcodeMsg"></div> 
							<input type="hidden" id="timePintDataAndTime" value="" /></td>
					</tr>

					<tr>
						<td><label for="subjectMsg"class=" col-form-label" style="color: #212529;"><spring:message code="label.samplecllectionSubject"></spring:message></label></td>
						<td id="subjectMsg"></td>
					</tr>
					<tr>
						<td><label for="vacutainerMsg"class=" col-form-label" style="color: #212529;"><spring:message code="label.samplecllectionVacutainer"></spring:message></label></td>
						<td id="vacutainerMsg"></td>
					</tr>
					<tr>
						<td><label for="collectionTimeTr"class=" col-form-label" style="color: #212529;"><spring:message code="label.samplecllectionBloodSample"></spring:message></label></td>
						<td id="collectionTimeTr"></td>
					</tr>
					<tr id="modeOfCollectionDiv">
						<td><label for="modeOfCollection"class=" col-form-label" style="color: #212529;">Collection : </label></td>
						<td>
							<input type="radio" name="modeOfCollection" id="modOfColl_cannula" onchange="ModeOfCollectionValiation()" value="Cannula"> &nbsp; Through Canula
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="modeOfCollection" id="modOfColl_tfp" onchange="modeOfCollectionValiation()" value="TFP"> &nbsp; Through Fresh Pick
							<div id="modeOfCollectionMsg" style="color: red;"></div>
						</td>
					</tr>
					<tr id="timePointDeviationtr">
						<td><label for="vacutainerDeviation"class=" col-form-label" style="color: #212529;">Reason </label></td>
						<td><select name="vacutainerDeviation"  class="form-control" style="width:45%;"
							id="vacutainerDeviation"
							onchange="checkDeviationMessage()">
								<option value="-1">--<spring:message code="label.sdSelect"></spring:message>--</option>
								<c:forEach items="${deviations}" var="devs">
								   <c:if test="${devs.code eq 'AOR' or devs.code eq 'CB' or devs.code eq 'DV' or devs.code eq 'SRL' or devs.code eq 'NILL'}">
								   		<option value="${devs.id}">${devs.message}</option>
								   </c:if>
								</c:forEach>
						</select> <font color="red" id="deviationVacutainerMessageAlert"></font></td>

					</tr>
					<%-- <tr id="deviationsCommentsDiv">
						<td><label for="deviationComments"class=" col-form-label" style="color: #212529;">Comments </label></td>
						<td><select name="deviationComments"  class="form-control" style="width:45%;"
							id="deviationComments"
							onchange="deviationComments()">
								<option value="-1">--<spring:message code="label.sdSelect"></spring:message>--</option>
								<c:forEach items="${deviations}" var="com">
								   <c:if test="${com.code eq 'NILL'}">
								   		<option value="${com.id}">${com.message}</option>
								   </c:if>
								</c:forEach>
						</select> <div id="devComMsg" style="color: red;"></div></td>

					</tr> --%>
					
					<!-- <tr id="reasonDiv">
						<td><label for="samleReason"class=" col-form-label" style="color: #212529;">Reason (if any) </label></td>
						<td>
							<textarea rows="5" cols="35" name="sampleReason" id="sampleReason" style="width: 45%;" onblur="sampleReasonValidation()"></textarea>
							<div id="sampleReasonMsg" style="color: red;"></div>
						</td>
					</tr> -->
					<tr>
						
						<td colspan="3" style="padding-left: 32%;padding-top: 10px;"><input type="button" value="<spring:message code="label.save"></spring:message>" class="btn btn-primary"
							onclick="submitForm()"  class="btn btn-primary"> 
							<%-- &nbsp;<input type="button"
							value="<spring:message code="label.samplecllectionClear"></spring:message>" class="btn btn-primary" onclick="clearForm()" /> --%></td>
					</tr>
				</table>
<%-- 			</sf:form> --%>
             </div>
           </div>
</body>

<script type="text/javascript">
	function subjectError(message, barcode) {
		$("#subjectMsg").html(message);
		$("#subjectMsg").css('background-color', 'red');
	}
	function vacutinerError(message, barcode) {
		$("#vacutainerMsg").html(message);
		$("#vacutainerMsg").css('background-color', 'red');
	}
</script>
<script>
	function addVacutainerData() {

		if ($("#contnueWithTimePoint").prop('checked') == true) {
			// 		alert(resultTemp);
			var c = resultTemp;
			// 		alert(c[2])
			$("#timePoint").val(c[2]);
			// 		alert(c[3])
			$("#vacutainerMsg").html(c[3]);
			$("#vacutainerMsg").css('background-color', 'white');
			// 		alert(barcodeTemp)
			$("#vacutainer").val(barcodeTemp);
			$("#collectionTimeTr")
					.html(
							'<input type="button" id = "collectionButton" value="Collect" style="padding: 0px;" class="btn btn-primary" onclick="sampleColletionTime()"/><font color=\"red\" id=\"clollectoinMesg\"></font>');
			$("#timePointDeviationtr").show();
			$("#deviationsCommentsDiv").show();
			$("#modeOfCollectionDiv").show();
			$("#reasonDiv").show();
			$("#deviationStatus").val(1);
			sampleType = c[4];
			scheduleTime = c[5];

			if (sampleType == '1') {
				var timePoint = scheduleTime;
				if (timePoint != '') {
					var time2 = ranningTime.split(":");
					var tp = (parseInt(timePoint[0]) * 60)
							+ parseInt(timePoint[1]);
					var t = (parseInt(time2[0]) * 60) + parseInt(time2[1]);
					// 				alert(tp+"     "+t);
					var sum = t - tp;
					if (sum > 5) {
						// 					$("#collectionButton").prop('disabled', true);
						setInterval(sampleCollectionSecondFunction, 1000);
					}
				}

			}
		} else {
			clearForVacutainer();
		}
	}
</script>

<script>
	function resetCollectionTime(messageId, ranningTimeId,
			runningTimeWithSecondsId) {
		$("#collectionTime").val('');
		$("#collectionTimeTr")
				.html(
						'<input type="button" value="Collect" class="btn btn-primary" style="padding: 0px;" onclick="sampleColletionTime()"/><font color=\"red\" id=\"clollectoinMesg\"></font>');
	}
	function checkDeviation() {
		var timePoint = scheduleTime;
		if (timePoint != '') {
			var time2 = ranningTime.split(":");
			var tp = (parseInt(timePoint[0]) * 60) + parseInt(timePoint[1]);
			var t = (parseInt(time2[0]) * 60) + parseInt(time2[1]);
			// 		alert(tp+"     "+t);
			if (tp >= t) {
				$("#timePointDeviationtr").show();
				$("#deviationStatus").val(1);
				var sum = t - tp;
				if (sum > 5) {
					$("#collectionButton").prop('disabled', true);
					setInterval(sampleCollectionSecondFunction, 1000);
				}
			}

		}
	}
	function sampleCollectionSecondFunction() {
		var time2 = ranningTime.split(":");
		var tp = (parseInt(timePoint[0]) * 60) + parseInt(timePoint[1]);
		var t = (parseInt(time2[0]) * 60) + parseInt(time2[1]);
		var sum = t - tp;
		if (sum <= 5) {
			$("#collectionButton").removeAttr('disabled');
		}
	}

</script>
</HTML>




