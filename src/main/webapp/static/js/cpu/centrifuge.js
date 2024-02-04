var instumentsData;
var vacutainerData;
var studyCentrifugeData;
var colectedSampleVacutainersData;
var vacutinerCount = 0;
var timePointsMap = {};
var vacutainerJson = "";
var colectedSampleVacutainersJson ="";
var instumentsJson = "";
var studyCentrifugeJson = "";
var runningTimeWithSeconds = "";

$(document).ready(function() {
	$("#barcode").focus();
	$(".loader").show();
	var value = $("#studyName").val();
//	 debugger;
	
	asynchronousAjaxCallBack(mainUrl+ "/study/clinical/cetrifugationPageData/" + value, cetrifugationPageDataBack);
	
//	
////	 *Instead of individual calls added single call for data fetching
//	vacutainerJson = synchronousAjaxCall(mainUrl+ "/study/clinical/fechtVacutainerBarcodes/"+ value);
//	if (vacutainerJson != null && vacutainerJson != "undefined" && vacutainerJson != "") {
//		vacutainerData = vacutainerJson;
//		timePointsMap = vacutainerData.timePointsMap;
//	}
//	studyCentrifugeJson = synchronousAjaxCall(mainUrl+ "/study/clinical/studyCentrifugeDetails/" + value);
//	if (studyCentrifugeJson != null && studyCentrifugeJson != "undefined" && vacutainerJson != "") {
//		studyCentrifugeData = studyCentrifugeJson;
//	}
//	instumentsJson = synchronousAjaxCall(mainUrl+ "/study/clinical/instumentInfo/Centrifuge");
//	if (instumentsJson != null && instumentsJson != "undefined" && instumentsJson != "") {
//		instumentsData = instumentsJson;
//	}
//	colectedSampleVacutainersJson = synchronousAjaxCall(mainUrl+ "/study/clinical/colectedSampleVacutainers/"+ value);
//	$(".loader").hide();
});

function cetrifugationPageDataBack(data){
//	debugger;
	vacutainerJson = data.separationVacutinerDto;
	if (vacutainerJson != null && vacutainerJson != "undefined" && vacutainerJson != "") {
		vacutainerData = vacutainerJson;
		timePointsMap = vacutainerData.timePointsMap;
	}
	studyCentrifugeJson = data.studySampleCentrifugation;
	if (studyCentrifugeJson != null && studyCentrifugeJson != "undefined" && vacutainerJson != "") {
		studyCentrifugeData = studyCentrifugeJson;
	}
	instumentsJson = data.storageVacutinerDto;
	if (instumentsJson != null && instumentsJson != "undefined" && instumentsJson != "") {
		instumentsData = instumentsJson;
	}
	colectedSampleVacutainersJson = data.centrificationDto;
	$(".loader").hide();	
}
function checkStudy(value) {
	$("#studyIdMsg").html("");
	if (value != -1) {
		$("#studyId").prop("disabled", 'disabled');
		return true;
	} else {
		$("#studyIdMsg").html("Required Field.");
		return false;
	}
}
var centrifugeScanTime = '';
var centrifugeBarcode = '';
var vacutainerScanTimes = {};

$("#barcode").on('input',function(e) {
    barcode = $("#barcode").val();
	$("#barcodeMsg").html("");
	$("#subjectMsg").html("");
	var lastChar = barcode.substr(barcode.length - 1);
	if (lastChar == 'n') {
		debugger;
		$("#barcode").focus();
		$("#barcode").val("");

		var prefix = barcode.substr(0, 2);
		if (prefix == "06") {// instrument
			centrifugeBarcode = "";
			$('#cetrifugeLabelDiv').show();
			$("#centrifugeMsg").html("");
			centrifugeScanTime = getServerEndTimeWithSeconds();
			var instrumentBarcodeSplit = barcode.substr(0,barcode.length - 1).split('a');
			var instruments = instumentsData.instruments;
			$.each(instruments,function(key, value) {
				if (value.id == instrumentBarcodeSplit[1]) {
					$("#centrifugeMsg").html("Instrument : "
											+ value.instrumentNo
											+ ", Model : "
											+ value.instrumentModel.instrumentModel);
					centrifugeBarcode = barcode;
					return false;
				}
			});
			if (centrifugeBarcode == "") {
				$("#centrifugeMsg").html("In-valied Barcode.");
			}
		} else if (prefix == "04") {// vacutainer
			if(centrifugeBarcode != ""){
				if(duplicatecheck(barcode, vacutinerBarcodes, "barcodeMsg")){
					if (checkStudy($("#studyId").val())) {
						var periodsArr = colectedSampleVacutainersJson.spmIdsList;
						var vacSplit = barcode.substr(0, barcode.length - 1).split('a');
						if(periodsArr != undefined){
							if(periodsArr.indexOf(parseInt(vacSplit[1])) != -1){
								if (checkSimileSubject(barcode)) {
									debugger;
									var countLength = Object.keys(colectedSampleVacutainersJson.ssctpMap).length;
									var tpScp = null;
									var vacutainerId = "";
									if(countLength > 0){
										var subScp = null;
										var tpScp = null;
										var vacSubNo = vacSplit[3];
										var vacstanByFlag = true;
										var finalSubNo = "";
										var printSubNo = "";
										var subRecord = vacutainerJson.subMap[vacSplit[3]];
										if(subRecord != null && subRecord != undefined){
											var subjectReplace = subRecord.subjectReplace;
											if(subjectReplace != null && subjectReplace != "" && subjectReplace != undefined && subjectReplace == "Replaced")
													vacstanByFlag = false;
										}
										if(vacstanByFlag){
											finalSubNo = subRecord.subjectNo;
											printSubNo = subRecord.reportingId.subjectNo;
										}else{
											var replaceSubNo = vacutainerJson.replaceSubMap[vacSplit[3]].subjectNo;
											var rpsRecord = vacutainerJson.subMap[replaceSubNo];
											finalSubNo = rpsRecord.subjectNo;
											printSubNo = rpsRecord.reportingId.subjectNo
										}
										if(vacSplit[2] != 0){
											subScp = colectedSampleVacutainersJson.ssctpMap[finalSubNo];
											if(subScp != undefined){
												var periodScp = subScp[vacSplit[1]];
												if(periodScp != undefined){
													tpScp = periodScp[vacSplit[4]];
													vacutainerId = vacSplit[4];
												}
											}
										}else{
											var subVacDataMap = colectedSampleVacutainersJson.ssctpMap[finalSubNo];
											if(subVacDataMap != undefined){
												 var periodVacDataMap = subVacDataMap[vacSplit[1]];
												 if(periodVacDataMap != undefined){
													 for (var tpKey in periodVacDataMap) {
														 if (periodVacDataMap.hasOwnProperty(tpKey)) {
															 var collectedTp = timePointsMap[tpKey];
															 var curTp = timePointsMap[vacSplit[4]];
															 if(collectedTp != undefined && curTp != undefined){
																if(collectedTp == curTp){
																	tpScp = periodVacDataMap[tpKey];
																	vacutainerId = tpKey;
																	break;
																	
																}
															}
														 }
													 } 
												 }
											}
										}
										var stpPojo = tpScp;
										if(stpPojo == null || stpPojo == undefined)
											stpPojo = stpPojo = vacutainerData.timeIdTimePointMap[vacSplit[4]];
										if(tpScp != null){
											var subNo = vacutainerJson.subMap[tpScp.subject.subjectNo].reportingId.subjectNo;
											var currentVacutinerStatus = false;
											var vacutainerScanTime = getServerEndTimeWithSeconds();
											var trData = "<tr id=\"" + barcode
													+ "tr\"><td><input type=\"hidden\" name=\"subjectimePoint\" value=\""
													+ barcode + "\">"
											trData += tpScp.sampleTimePoint.study.projectNo+ "</td>";
											trData += "<td>"+ tpScp.studyPeriodMaster.periodName+ "</td>";
											trData += "<td>"+ subNo+ "</td>";
											trData += "<td>"+ tpScp.sampleTimePoint.sign+tpScp.sampleTimePoint.timePoint+ "("
													+ tpScp.sampleTimePoint.timePointNo+ ")</td>";
											trData += "<td><input type=\"button\" id=\""+ barcode+ "Button\" class=\"btn btn-danger btn-sm\" onclick=\"removeVacutiner('"+ barcode
													+ "')\" value=\"Delete\"></td>";
											trData += "</tr>";
											$("#vacutinersTable").append(trData);
											$('#centrifugeDataDiv').show();
											currentVacutinerStatus = true;
											vacutainerScanTimes[barcode] = getServerTime();
											vacutinerBarcodes.push(barcode);
											
											if (currentVacutinerStatus == false) {
												$("#barcodeMsg").html("In-valied Barcode");
											}
									    }else $("#barcodeMsg").html("Sample Collection Not Done For Scanned Subject "+printSubNo+" Time Point :"+stpPojo.sign+stpPojo.timePoint);
									}else $("#barcodeMsg").html("Sample Collection Not Done.");
								} else $("#barcodeMsg").html("Allow only Same time point Subject vacutainers.");
							}else $("#barcodeMsg").html("Barcode does not belongs to Study :"+colectedSampleVacutainersJson.sm.projectNo);
						}
					}else $("#barcodeMsg").html("Please Select Study");
				}
			}else $("#barcodeMsg").html("Scan Instrument Barcode");
		}else{
			if(centrifugeBarcode == "")
				$("#barcodeMsg").html("Scan Instrument Barcode");
			else $("#barcodeMsg").html("Scan Vacutainer Barcode");
				
		}
	}
});

var centrifugeStartTime = "";
var centrifugeEndTime = "";
function centrifugeStartTimeAdd() {
	$("#startMsg").html("");
	if (checkCentrifugeFromValidation()) {
		centrifugeStartTime = getServerEndTimeWithSeconds();
		$("#startTimeTd").html(getServerTime());
		$("#endButton").prop("disabled", '');
		$("#barcode").prop("disabled", 'disabled');
		$("input[name='subjectimePoint']").each(function(i, v) {
			$("#" + this.value + "Button").prop("disabled", 'disabled');
		});
	}

}
var vacutinerBarcodes = [];
function checkCentrifugeFromValidation() {
	var flag = true;
	$("#centrifugeMsg").html("");
	if (centrifugeBarcode == '') {
		$("#centrifugeMsg").html("Required Field");
		flag = false;
	}
	if (flag) {
		if (vacutinerBarcodes.length > 0) {
			return flag;
		} else
			$("#centrifugeVacutinerMsg").html("Vacutainer Required");
	} else {
		$("#centrifugeVacutinerMsg").html("Vacutainer Required");
	}
	return flag;
}
function checkValidation(){
	$("#bufferStorageMsg").html("");
	if (!$('input[name="bufferStorage"]:checked').val()) {
		$("#bufferStorageMsg").html("Required Field");
		return false;
	}else
	return true;
}
function centrifugeEndTimeAdd() {
	if(checkValidation()){
		if (centrifugeStartTime != "") {
			centrifugeEndTime = getServerEndTimeWithSeconds();
			$("#endTimeTd").html(getServerTime());
			var vacutainerScanTimeskeyVal = [];
			var vacBarcodes = [];
			$.each(vacutainerScanTimes, function(k, v) {
				if (v != "") {
					vacutainerScanTimeskeyVal.push(k + "#" + v);
					vacBarcodes.push(k);
				}

			});
			var studyId = $("#studyName").val();
			var centrifugationDate = [];
			$
					.ajax({
						url : $("#mainUrl").val() + '/administration/getCsrfToken',
						type : 'GET',
						success : function(data) {

							centrifugationDate.push({
								name : data.parameterName,
								value : data.token
							});
							centrifugationDate.push({
								name : "studyId",
								value : studyId
							});
							// debugger;
							centrifugationDate.push({
								name : "centrifugeScanTime",
								value : centrifugeScanTime
							});
							centrifugationDate.push({
								name : "centrifugeBarcode",
								value : centrifugeBarcode
							});
							centrifugationDate.push({
								name : "centrifugeMissedSubjects",
								value : $("#centrifugeMissedSubjects").val()
							});
							debugger;
							centrifugationDate.push({
								name : "vacutinerBarcodes",
								value : vacutinerBarcodes
							});
							centrifugationDate.push({
								name : "vacutainerScanTimes",
								value : vacutainerScanTimeskeyVal
							});
							centrifugationDate.push({
								name : "bufferStorage",
								value : $("input[name='bufferStorage']:checked")
										.val()
							});

							centrifugationDate.push({
								name : "startTime",
								value : centrifugeStartTime
							});
							centrifugationDate.push({
								name : "endTime",
								value : centrifugeEndTime
							});

							$.ajax({
								url : $("#mainUrl").val()
										+ '/study/clinical/centrifugationSave',
								type : 'POST',
								data : centrifugationDate,
								success : function(e) {
									console.log(e.success)
									if (e.Success === 'true' || e.Success === true) {
										displayMessage("success", e.Message)
//										$('#crfName').val("");
//										$("#activityDiv").empty();
										$('#centrifugeDataDiv').html("");
										// debugger;
										/*if (e.centrifugeId > 0) {// studyCentrifugeData
											Swal.fire({
												  title: 'Are you sure?',
												  text:textMsg,
												  showDenyButton: true,
												  showCancelButton: false,
												  confirmButtonText: 'OK',
												  denyButtonText: 'NOT OK',
												}).then((result) => {
												   Read more about isConfirmed, isDenied below 
												  if (result.isConfirmed) {
													  callSampeleSeparation(e.centrifugeId)
												  } else if (result.isDenied) {
													  centrifugationReset(false);
													  regenarateData($("#studyName").val());
												  }
												});
										} else{
											centrifugationReset(false);
											regenarateData($("#studyName").val());
											$('#centrifugeDataDiv').html("");
										}*/
									} else {
										displayMessage("error", e.Message);
										centrifugationReset(false);
										regenarateData($("#studyName").val());
										$('#centrifugeDataDiv').html("");
									}
								},
								error : function(er) {
									centrifugationReset(false);
									regenarateData($("#studyName").val());
									$('#centrifugeDataDiv').html("");
								}
							});
						},
						error : function(er) {
							centrifugationReset(false);
							regenarateData($("#studyName").val());
							$('#centrifugeDataDiv').html("");
						}
					});

		} else {
			$("#startMsg").html("Required Field");
		}
	}
	

}
function regenarateData(value){
	vacutainerJson = synchronousAjaxCall(mainUrl+ "/study/clinical/fechtVacutainerBarcodes/"+ value);
	if (vacutainerJson != null && vacutainerJson != "undefined" && vacutainerJson != "") {
		vacutainerData = vacutainerJson;
		timePointsMap = vacutainerData.timePointsMap;
	}
	studyCentrifugeJson = synchronousAjaxCall(mainUrl+ "/study/clinical/studyCentrifugeDetails/" + value);
	if (studyCentrifugeJson != null && studyCentrifugeJson != "undefined" && vacutainerJson != "") {
		studyCentrifugeData = studyCentrifugeJson;
	}
	instumentsJson = synchronousAjaxCall(mainUrl+ "/study/clinical/instumentInfo/Centrifuge");
	if (instumentsJson != null && instumentsJson != "undefined" && instumentsJson != "") {
		instumentsData = instumentsJson;
	}
	colectedSampleVacutainersJson = synchronousAjaxCall(mainUrl+ "/study/clinical/colectedSampleVacutainers/"+ value);
}

/*function separation() {
	var continueStatus = confirm("Do, You wan't continue with Sample Separation?");
	if (continueStatus) {
		callSampeleSeparation(4)
	} else {
		centrifugationReset(false);
	}
}*/
function centrifugationReset(status) {
	$("#barcodeMsg").html("");
	$("#barcode").focus();
	$("#barcode").val("");
	$("#studyId").prop("disabled", '');
	centrifugeScanTime = '';
	entrifugeBarcode = '';
	$("#centrifugeMsg").html("");
	$("#vacutinersTable").html("");
	vacutainerScanTimes = {};
	vacutainerJson = "";
	colectedSampleVacutainersJson ="";
	instumentsJson = "";
	studyCentrifugeJson = "";
	timePointToAllow ="";
}
function callSampeleSeparation(centrifugeId) {
	var result = synchronousAjaxCall(mainUrl
			+ "/study/clinical/sampleSeparationPage/" + centrifugeId);
	if (result != null && result != "undefined" && result != "") {
		$("#activityDiv").html(result);
		console.log(result)

	}
}
function checkSampleCollectedOrNot(barcode) {
	var flag = false;
	$.each(colectedSampleVacutainersData, function(k, collectedSampleBarocode) {
		if (collectedSampleBarocode == barcode) {
			flag = true;
			return false;
		}
	});
	return flag;
}

var timePointToAllow = "";
function checkSimileSubject(vacBarcode) {
	if (vacutainerScanTimes.size == 0)
		timePointToAllow = "";
	var vacBarcodeSplit = vacBarcode.substr(0, vacBarcode.length - 1)
			.split('a');
	if (timePointToAllow == "") {
		timePointToAllow = timePointsMap[vacBarcodeSplit[4]];
		return true;
	} else {
		if (timePointToAllow == timePointsMap[vacBarcodeSplit[4]]) {
			return true;
		} else {
			return false;
		}

	}
}

function removeVacutiner(barcode) {
	/*vacutainerScanTimes = $.grep(vacutainerScanTimes, function(key, value) {
		return key !== barcode;
	});
	vacutinerBarcodes = $.grep(vacutinerBarcodes, function(key, value) {
		return value !== barcode;
	});*/
	var index = vacutinerBarcodes.indexOf(barcode);
	if(index != -1){
		vacutinerBarcodes.splice(index, 1);
		vacutainerScanTimes.splice(index, 1);
	}
	$("#" + barcode+"tr").remove();
	vacutinerCount--;
	if (vacutinerCount == 0)
		timePointToAllow = "";
}