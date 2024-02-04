<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
</head>
<body>
	<input type ="hidden" name="activeStudyNo" id="activeStudyNo" value="${activeStudyNo}"/><br/>
	<input type ="hidden" name="studybarcodeId" id="studybarcodeId" value="${studybarcodeId}"/><br/>
<%-- <c:url value="/study/clinical/stdClinicalSampleSaperationSave" var="stdClinicalSampleSaperationSave" /> --%>
<%-- <sf:form action="${stdClinicalSampleSaperationSave}" method="POST"  id="formsumit" > --%>
	<input type ="hidden" name="vacutainer" id="vacutainer"/><br/>
	<input type ="hidden" name="vacutainerScanTime" id="vacutainerScanTime"/><br/>
	<input type="hidden" name = "noOfVials" id="noOfVials" value="0"/>
	<div id="vialsDiv">
		

	</div>
	<div id="vialsScanTimeDiv">
		

	</div>
		
		<table id="example1" class="table table-bordered table-striped">
			<tr>
				<td>Scan Barcode</td>
				<td>
				<input type="text" name="barcode" class="form-control" id="barcode"/>
				<font style="color: red" id="barcodeMsg"></font>
				 </td>
			</tr>
			<tr><td>Vacutainer </td><td id="vacutainerMsg"></td></tr>
		</table>
<%-- 		</sf:form> --%>
<input type ="hidden" name="mainUrlTemp" id="mainUrlTemp" value="${mainUrlTemp}"/><br/>
</body>

<script type="text/javascript">
$("#barcode").focus();

function clearForm(){
	$("#pageMessage").html("");
	$("#pageError").html("");
	$("#pageWarning").html("");
	
	$("#vacutainer").val("");
	$("#vacutainerScanTime").val("");
	$("#noOfVials").val("");
	$("#vialsDiv").html("");
	$("#vialsScanTimeDiv").html("");
	/*$("#example1   tbody").html('<tr><td>Scan Barcode</td><input type="text" name="barcode" id="barcode"/><font style="color: red" id="barcodeMsg"></font></td>'
			+'</tr><tr><td>Vacutainer </td><td id="vacutainerMsg"></td></tr>');*/
}

$("#barcode").on('input',function(e){
	var barcode = $("#barcode").val();
	$("#barcodeMsg").html("");
	var length = barcode.length;
	if(length == 23){
		$("#barcode").val("");
		var prefix = barcode.substr(0,2);
			if(prefix == "04"){//vacutainer
				clearForm();
				var scanTime = ranningTime;
				var study = barcode.substring(2, 8);
				var studyid = $("#studyName").val();
				while(studyid.length() >= 6){
					studyid += "0"+studyid;
				}
// 				if(study == $("#studybarcodeId").val()){
				if(study == studyid){
					console.log(mainUrl+"/study/clinical/vacutainerForSampleSeparation/"+barcode);
						var result = synchronousAjaxCall(mainUrl+"/study/clinical/vacutainerForSampleSeparation/"+barcode);
						console.log(result);
						if(result != '' || result != 'undefined'){
							var c = result.split('--');
							if(c.length > 1){
								$("#vacutainerMsg").html(c[2]);
								if(c[0] == '0'){
									$("#vacutainerMsg").css('background-color', '');
									$("#vacutainer").val(barcode);
									$("#vacutainerScanTime").val(scanTime);
									
									var vials = c[1];
									var hiddenFields = "";
									var fields = "";
									var rows = "";
									$("#noOfVials").val(vials);
									var vialsScanTimes = "";
									for(var count = 1; count <= vials; count++){
										hiddenFields +="<input type ='hidden' name='vial"+count+"' id='vial"+count+"'/><br/>";
										vialsScanTimes += "<input type ='hidden' name='vialScan"+count+"' id='vialScan"+count+"'/><br/>";
										rows += "<tr><td>Vial-"+count+"</td><td id='vial"+count+"Msg'></td></tr>";
									}
									$("#example1  tbody").append(rows);
									$("#vialsDiv").html(hiddenFields);
									$("#vialsScanTimeDiv").html(vialsScanTimes);
								}else if(c[0] == '1'){
									$("#vacutainerMsg").css('background-color', 'red');
								}
							}else{
								$("#barcodeMsg").html(result);
							}
						}
				}else{
					message = "Vacutainer not belongs to Current Study : " + $("#activeStudyNo").val();
				}
			$("#barcode").val("");
		}else if(prefix == "05"){
			var scanTime = ranningTime;
// 			console.log(barcode);
			var v1 = barcode.substr(2);
// 			console.log(v1.substr(0,20));
				var vial = "04"+v1.substr(0,20)+"0";
// 				console.log(vial)
				var endChar = barcode.substr(22);
// 				console.log(endChar)
				var vacutainer =  $("#vacutainer").val();
				if(vacutainer == ""){
					$("#vacutainerMsg").html("<font color='red'>Please, scan vacutainer required</font>");
				}else{
					console.log(vacutainer);console.log(vial)
					if(vacutainer ==  vial){
						$("#vial"+endChar+"Msg").html($("#vacutainerMsg").html() + ", Vial-"+endChar);
						$("#vial"+endChar).val(barcode);
						$("#vialScan"+endChar).val(scanTime);
					}else{	
						$("#vial"+endChar+"Msg").html("");
						$("#vial"+endChar).val("");
						$("#barcodeMsg").html("Vial-"+endChar + " is not matched With Vacutainer");
					}
					autoSubmitForm();
				}
		}
	}else	if(length > 23)	$("#barcode").val("");
});
function autoSubmitForm(){
	if( $("#vacutainer").val()){
		var flag = true;
		for(var i=1; i<=$("#noOfVials").val(); i++){
			if($("#vial"+i).val() == ''){
				flag = false;
			}
		}
		if(flag){
			$("#formsumit").submit();				
		}
	}
}
function submitForm(){
	if($("#vacutainer").val()){
		var flag = true;
		for(var i=1; i<=$("#noOfVials").val(); i++){
			if($("#vial"+i).val() == ''){
				flag = false;
			}
		}
		if(flag){
			$("#formsumit").submit();				
		}else
			$("#barcodeMsg").html("Please scan all Barcodes");

	}else{
		$("#barcodeMsg").html("Please scan all Barcodes");
	}
}
</script>
</html>

