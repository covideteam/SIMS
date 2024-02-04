$("#studyName").change(function(e) {
	if ($(this).val() === "") {
		$("#barcodeType").val(-1);
	}
});

$("#barcodeType").change(function(e) {
	$("#activityDiv").html('');
	if ($(this).val() === "-1") {
		$("#activityDiv").html('');
	} else {
		var studyname = $("#studyName").val();
		debugger;
		//alert(studyname)
		if(studyname != ""){
			$.ajax({
				url : $("#mainUrl").val()+ "/barcodes/getbarcodePage/"+ $("#studyName").val() + "/"+ $("#barcodeType").val(),
				type : 'GET',
				success : function(d) {
					$("#activityDiv").append(d);
				}
			});
		}else{
			bs4Toast.warning('warning', 'Please Select Project');
		}
		
	}
});

function singleSubjectBarocode(type) {
	debugger;
	window.location = $("#mainUrl").val()+ "/barcodes/stdSubjectBarcodePrint/" + $("#studyName").val()+ "/"+type+"/"+ $("#subject").val()+"/"+$("#periodId").val();
}
function stdSachetBarcodesPrint() {
	window.location = $("#mainUrl").val()+ "/barcodes/stdSachetBarcodesPrint/" +$("#studyName").val()+"/"+ $("#period").val();
}
function periodVacutainers() {
	window.location = $("#mainUrl").val()+ "/barcodes/stdVacutainerBarcodePrint/" +$("#studyName").val()+"/"+ $("#period1").val();
}
function periodVacutainersForAll() {
	window.location = $("#mainUrl").val()+ "/barcodes/stdVacutainerBarcodePrint/"+$("#studyName").val()+"/0";
}

function submitFormTimePointWise() {
	window.location = $("#mainUrl").val()+ "/barcodes/stdVacutainerBarcodePrintTimePointWise/"+$("#studyName").val()+"/"+ $("#period3").val() + "/0/" + $("#timePoint2").val();
}
function submitFormSubjectWise() {
	var periodFlag = requiredValidation('period3', 'period3Msg', -1);
	var subjectFlag = requiredValidation('subject3', 'subject3Msg', -1);
	if(periodFlag && subjectFlag)
		window.location = $("#mainUrl").val()+ "/barcodes/stdVacutainerBarcodePrintTimePointWise/"+$("#studyName").val()+"/"+ $("#period3").val() + "/" + $("#subject3").val()+"/0";
}

function submitFormSubjectWiseForTimePoint() {
	var periodFlag = requiredValidation('period4', 'period4Msg', -1);
	var subjectFlag = requiredValidation('subject4', 'subject4Msg', -1);
	var timePointFlag = requiredValidation('timePoint4', 'timePoint4Msg', -1);
	if(periodFlag && subjectFlag && timePointFlag)
		window.location = $("#mainUrl").val()+ "/barcodes/stdVacutainerBarcodePrintTimePointWise/"+$("#studyName").val()
		+"/"+ $("#period4").val() + "/" + $("#subject4").val() + "/"+ $("#timePoint4").val();
}
function generateVialBarcode(withOutSubject, withOutTimePoint) {
//	debugger;
	if(withOutSubject && withOutTimePoint){
		if(requiredValidation('period', 'periodMsg', -1))
			window.location = $("#mainUrl").val()+ "/barcodes/stdVialBarcodesPrint/"+$("#studyName").val()+"/"+ $("#period").val()+"/0/0";		
	}else if(withOutTimePoint){
		var periodFlag = requiredValidation('period3', 'period3Msg', -1);
		var subjectFlag = requiredValidation('subject3', 'subject3Msg', -1);
		if(periodFlag && subjectFlag)
			window.location = $("#mainUrl").val()+ "/barcodes/stdVialBarcodesPrint/"+$("#studyName").val()+"/"+ $("#period3").val()+"/"+$("#subject3").val()+"/0";		
	}else if(!withOutSubject && !withOutTimePoint){
		debugger;
		var periodFlag = requiredValidation('period4', 'period4Msg', -1);
		var subjectFlag = requiredValidation('subject4', 'subject4Msg', -1);
		var timePointFlag = requiredValidation('timePoint4', 'timePoint4Msg', -1);
		if(periodFlag && subjectFlag && timePointFlag)
			window.location = $("#mainUrl").val()+ "/barcodes/stdVialBarcodesPrint/"+$("#studyName").val()
			+"/"+ $("#period4").val()+"/"+$("#subject4").val()+"/"+$("#timePoint4").val();		
	}
}
function resetSubjectAndTimePoitn(subjectId, timePointId){
	$('#'+subjectId).val(-1);
	$('#'+timePointId).empty();
	$('#'+timePointId).append("<option value='-1'>----Select----</option>");
}
function subjectTimePoints(subjectId, periodId){
	$('#timePoint4').empty();
	$('#timePoint4').append("<option value='-1'>----Select----</option>");
	var periodFlag = requiredValidation(periodId, periodId+'Msg', -1);
	var subjectFlag = requiredValidation(subjectId, subjectId+'Msg', -1);
	if(!periodFlag){
		$('#'+subjectId).val(-1);
	}else
		if(periodFlag && subjectFlag)
		$.ajax({
			url : $("#mainUrl").val()+ "/barcodes/subjectSampleTimePoitns/"+$("#studyName").val()+"/"+ $("#"+periodId).val() + "/"+ $("#"+subjectId).val(),
			type : 'GET',
			success : function(data) {
				$('#timePoint4').empty();
				$('#timePoint4').append("<option value='-1'>----Select----</option>");
				if(data.length > 0){
					for(var i = 0;i < data.length;i++){
						$("#timePoint4").append("<option value='" + data[i].id + "'>" + data[i].timePoint + "</option>");
					}					
				}else{
					$("#timePoint4Msg").html("No more time points for the subject.")
				}

			}
		});
}
//function generateVialAllBarcode() {
//	window.location = $("#mainUrl").val()+ "/barcodes/stdVialBarcodesPrint/"+$("#studyName").val()+"/0/0/0";
//}


function requiredValidation(id, messageId, defaultValue){
//	debugger;
	$("#"+messageId).html("");
	if($("#"+id).val() == defaultValue){
		$("#"+messageId).html("Required Field");
		return false;
	}else
		return true;
}

/*function submitAllBarocode(type) {
	window.location = $("#mainUrl").val()
			+ "/barcodelabels/stdSubjectBarcodePrint/" + $("#studyName").val()
			+ "/1/0/"+$("#periodId").val();
}*/


