$("#barcode").focus();
var vacutainerData;
var centrifugeData = {};
var periodCentrifugeData = {};
var timeIdTimePointMap = {};
var vacutinerVialBaroces = {};
var subjectsArr = [];
var vacutainerJson = "";
var colectedSampleVacutainersJson = "";
$(document).ready(
		function() {
			debugger;
			$(".loader").show();
			asynchronousAjaxCallBack(mainUrl
					+ "/study/clinical/sampleSeparationDetails/"
					+ $("#studyName").val(), sampleSeparationDetailsBack);

		});

function sampleSeparationDetailsBack(data) {
	debugger;
	colectedSampleVacutainersJson = data.centrificationDto;
	vacutainerJson = data.separationVacutinerDto;
	/*
	 * Instead of individual calls added single call for data fetching
	 * colectedSampleVacutainersJson = synchronousAjaxCall(mainUrl+"/study/clinical/colectedSampleVacutainers/"+ $("#studyName").val());
	 * vacutainerJson = synchronousAjaxCall(mainUrl+ "/study/clinical/fechtVacutainerBarcodes/"+ $("#studyName").val());*/
	if (vacutainerJson != null && vacutainerJson != "" && vacutainerJson != undefined) {
		vacutainerData = vacutainerJson;
		timeIdTimePointMap = vacutainerData.timeIdTimePointMap;
		var select = $("<select name=\"centrifugationDataMasterId\" id=\"centrifugationDataMasterId\" class=\"form-control\" onchange=\"centrufugeSubjects()\">");
		select.append($('<option>').val(-1).text("--Select--"));
		$.each(vacutainerData.centrifugationList, function(id, value) {

			centrifugeData[value.id] = value;
			var date = new Date(value.centrifugeStartTime);
			var hr = date.getHours();
			var min = date.getMinutes();
			var hrStr = "";
			var minStr = "";
			if (hr < 10)
				hrStr = "0" + hr;
			else
				hrStr = hr;
			if (min < 10)
				minStr = "0" + min;
			else
				minStr = min;
			let hoursMin = hrStr + ':' + minStr;
			var options = {
				year : 'numeric',
				month : '2-digit',
				day : '2-digit'
			};
			var dateString = date.toLocaleDateString('en-US', options);
			// $('#date').text(dateString);
			var option = "Study : " + value.study.projectNo + ",Period : "
					+ value.period.periodNo + ", Insturment : "
					+ value.instrument.instrumentNo + ", Time Point : "
					+ value.timePoitns + ", Start Time : " + hoursMin
					+ ", End Time : " + hoursMin;
			// debugger;
			select.append($('<option>').val(value.id).text(option));
		});
		console.log(select);
		$('#centifugeData').append(select);
		// debugger;
		$('#centifugeData')
				.append(
						"<font color=\"red\" id=\"centrifugationDataMasterIdMsg\"></font>");
	}
	debugger;
	centrifugeAndTimePointInfo();
	$(".loader").hide();
}
var centrifugeTimePointId = "";
var treatmentSpecific = false;
function centrifugeAndTimePointInfo() {
	debugger;
	if ($("#centrifugationDataMasterId").val() != -1) {
		var centrifuge = centrifugeData[$("#centrifugationDataMasterId").val()];
		if (centrifuge.study != undefined) {
			if (centrifuge.study.treatmentSpecificSampleTimepoints) {
				centrifugeTimePointId = centrifuge.id;
				treatmentSpecific = true;
			} else {
				centrifugeTimePointId = centrifuge.sampleTimePoints.sign
						+ centrifuge.sampleTimePoints.timePoint;
				treatmentSpecific = false;
			}
		}
	}

}
function centrufugeSubjects() {
	subjectsArr = [];
	$("#centrufugeSubjects").html("");
	// $("#centrufugeSubjects").append(centrifugeData[$("#centrifugationDataMasterId").val()].centrifugeSubjects);
	// $("#centrufugeSubjects").append(centrifugeData[$("#centrifugationDataMasterId").val()].centrifugeSubjects);
	var subs = centrifugeData[$("#centrifugationDataMasterId").val()].centrifugeSubjects;
	if (subs != null && subs != "" && subs != undefined) {
		// subjectsArr = subs.split(",");
		var subArr = subs.split(",");
		var finalSubStr = "";
		if (subArr.length > 0) {
			for (var i = 0; i < subArr.length; i++) {
				/*var subNo = vacutainerJson.subMap[subArr[i]].reportingId.subjectNo;
				subjectsArr.push(subNo);*/
				var vacstanByFlag = true;
				var psubNo = "";
				var subRecord = vacutainerJson.subMap[subArr[i]];
				if(subRecord != null && subRecord != undefined){
					var subjectReplace = subRecord.subjectReplace;
					if(subjectReplace != null && subjectReplace != "" && subjectReplace != undefined && subjectReplace == "Replaced")
							vacstanByFlag = false;
				}
				if(vacstanByFlag){
					psubNo = subRecord.reportingId.subjectNo;
				}else{
					var replaceSubNo = vacutainerJson.replaceSubMap[subArr[i]].subjectNo;
					var rpsRecord = vacutainerJson.subMap[replaceSubNo];
					psubNo = rpsRecord.reportingId.subjectNo
				}
				if(psubNo != ""){
					if(finalSubStr != "")
						finalSubStr = finalSubStr+","+psubNo;
					else finalSubStr = psubNo;
					subjectsArr.push(psubNo);
				}
				
			}
		}
	}
	$("#centrufugeSubjects").append(finalSubStr);
	centrifugeAndTimePointInfo();
}

function checkTimePoint(barcode) {
	debugger;
	var vacutainerBarcodeSplit = barcode.substr(0, barcode.length - 1).split(
			'a');
	if (treatmentSpecific) {
		if (vacutainerBarcodeSplit[4] == centrifugeTimePointId)
			return true;
		return false;
	} else {
		var timePointObj = timeIdTimePointMap[vacutainerBarcodeSplit[4]];
		var timePoint = timePointObj.sign + timePointObj.timePoint;
		if (timePoint == centrifugeTimePointId)
			return true;
		return false;
	}
}
var vialsBarcodes = {};
var vacutainerScanTimes = {};
var vialScanTimes = {};
var vialAliquot = {};
$("#barcode")
		.on(
				'input',
				function(e) {
					var barcode = $("#barcode").val();
					$("#barcodeMsg").html("");
					var lastChar = barcode.substr(barcode.length - 1);
					if (lastChar == 'n') {
						debugger;
						$("#barcode").focus();
						$("#barcode").val("");

						var prefix = barcode.substr(0, 2);
						if (prefix == "04") {// vacutainer
							var vacSplit = barcode
									.substr(0, barcode.length - 1).split('a');
							if (vacSplit[2] == 0)
								treatmentSpecific = false;
							else
								treatmentSpecific = true;
							var finalSubNo = "";
							var printSubNo = "";
							var vacstanByFlag = true;
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
//							var subNo = vacutainerJson.subMap[vacSplit[3]].reportingId.subjectNo;
							if (subjectsArr.indexOf(printSubNo) != -1) {
								if (duplicatecheck(barcode, vacutinerBarcodes,
										"barcodeMsg")) {
									if (checkCentrifuge()) {
										if (checkTimePoint(barcode)) {
											var currentVacutinerStatus = false;
											var vacutainerScanTime = getServerEndTimeWithSeconds();
											var subScp = null;
											var tpScp = null;
											var vacutainerId = "";
											var subVacDataMap = colectedSampleVacutainersJson.ssctpMap[finalSubNo];
											if (subVacDataMap != undefined) {
												var periodVacDataMap = subVacDataMap[vacSplit[1]];
												if (periodVacDataMap != undefined) {
													for ( var tpKey in periodVacDataMap) {
														if (periodVacDataMap
																.hasOwnProperty(tpKey)) {
															var collectedTp = timeIdTimePointMap[tpKey];
															var curTp = timeIdTimePointMap[vacSplit[4]];
															if (collectedTp != undefined
																	&& curTp != undefined) {
																if (collectedTp.sign
																		+ collectedTp.timePoint == curTp.sign
																		+ curTp.timePoint) {
																	tpScp = periodVacDataMap[tpKey];
																	vacutainerId = tpKey;
																	break;

																}
															}
														}
													}
												}
											}
											var stpPojo = tpScp;
											if (stpPojo == null
													|| stpPojo == undefined)
												stpPojo = stpPojo = timeIdTimePointMap[vacSplit[4]];
											if (tpScp != null) {
												var subjectNumber = vacutainerJson.subMap[tpScp.subject.subjectNo].reportingId.subjectNo;
												var trData = "<tr id=\""
														+ barcode
														+ "tr\"><td><input type=\"hidden\" name=\"subjectimePoint\" value=\""
														+ barcode + "\">"
												trData += tpScp.sampleTimePoint.study.projectNo
														+ "</td>";// project
												trData += "<td>"
														+ tpScp.studyPeriodMaster.periodName
														+ "</td>";// period
												trData += "<td>"// subject
														+ subjectNumber
														+ "</td>";
												trData += "<td>"// timepoint
														+ tpScp.sampleTimePoint.sign
														+ tpScp.sampleTimePoint.timePoint
														+ "("
														+ tpScp.sampleTimePoint.timePointNo
														+ ")</td>";
												trData += "<td><div id=\"aliquotVolum"
														+ barcode
														+ "\"></div></td>"; // Aliquot
												trData += "<td><div id=\"vials"
														+ barcode
														+ "\"></div><font color=\"red\" id=\""
														+ barcode
														+ "Msg\"/></td>";// volume
												trData += "<td><input type=\"button\" id=\"" // vital
														+ barcode
														+ "Button\" style=\"font-size:small;\" class=\"btn btn-danger bgn-sm\" onclick=\"removeVacutiner('"
														+ barcode
														+ "')\" value=\"Delete\"/></td>";
												trData += "</tr>";
												$("#vacutinersTable").append(
														trData);
												$('#sparationDiv').show();
												vacutinerBarcodes.push(barcode);
												currentVacutinerStatus = true;
												vacutainerScanTimes[barcode] = getServerEndTimeWithSeconds();
												vialsBarcodes[barcode] = [];
												vacutinerVialBaroces[barcode] = {};
											}
											if (!currentVacutinerStatus) {
												$("#barcodeMsg").html(
														"In-valied Barcode");
											}
										} else {
											$("#barcodeMsg").html("Centrifuge time point and vacutiner time point not matched");
										}

									}
								}
							} else {
								var finalSubStr = "";
								for (var i = 0; i < subjectsArr.length; i++) {
									var vacstanByFlag = true;
									var psubNo = "";
									var subRecord = vacutainerJson.subMap[subjectsArr[i]];
									if(subRecord != null && subRecord != undefined){
										var subjectReplace = subRecord.subjectReplace;
										if(subjectReplace != null && subjectReplace != "" && subjectReplace != undefined && subjectReplace == "Replaced")
											vacstanByFlag = false;
									}
									if(vacstanByFlag){
										psubNo = subRecord.reportingId.subjectNo;
									}else{
										var replaceSubNo = vacutainerJson.replaceSubMap[subjectsArr[i]].subjectNo;
										var rpsRecord = vacutainerJson.subMap[replaceSubNo];
										psubNo = rpsRecord.reportingId.subjectNo
									}
									if(finalSubStr != "")
										finalSubStr = finalSubStr+"(or)"+psubNo;
									else finalSubStr = psubNo;
								}
								if(finalSubStr != "")
									$("#barcodeMsg").html("Scanned Vacutainer Barcode does not belogs to "+ finalSubStr);
								else{
									var selBoxVal = $('#centrifugationDataMasterId').val();
									if(selBoxVal != "-1")
										$('#centrifugationDataMasterIdMsg').html("");
									else $('#centrifugationDataMasterIdMsg').html("Select Centrifuge Data.");
								}
							}
						} else if (prefix == "05") { // vial
							if (checkCentrifuge()) {
								var vialScanTime = getServerEndTimeWithSeconds();
								var vialBarcodeSplit = barcode.substr(0,barcode.length - 1).split('a');
								debugger;
								var finalSubNo = "";
								var printSubNo = "";
								var vacstanByFlag = true;
								var subRecord = vacutainerJson.subMap[vialBarcodeSplit[3]];
								if(subRecord != null && subRecord != undefined){
									var subjectReplace = subRecord.subjectReplace;
									if(subjectReplace != null && subjectReplace != "" && subjectReplace != undefined && subjectReplace == "Replaced")
										vacstanByFlag = false;
								}
								if(vacstanByFlag){
									finalSubNo = subRecord.subjectNo;
									printSubNo = subRecord.reportingId.subjectNo;
								}else{
									var replaceSubNo = vacutainerJson.replaceSubMap[vialBarcodeSplit[3]].subjectNo;
									var rpsRecord = vacutainerJson.subMap[replaceSubNo];
									finalSubNo = rpsRecord.subjectNo;
									printSubNo = rpsRecord.reportingId.subjectNo
								}
//								var subNo = vacutainerJson.subMap[finalSubNo].reportingId.subjectNo;
								if (subjectsArr.indexOf(printSubNo) != -1) {
									var vacutainerFromVial = "04a"
											+ vialBarcodeSplit[1] + "a"
											+ vialBarcodeSplit[2] + "a"
											+ vialBarcodeSplit[3] + "a"
											+ vialBarcodeSplit[4] + "n";
									var aliquot = vialBarcodeSplit[5].substr(0,2);
									var vacAvialbleFlag = false;
									$("input[name='subjectimePoint']").each(function(i, v) {
										if (this.value == vacutainerFromVial) {
											vialScanTimes[barcode] = vialScanTime;
											var vials = vialsBarcodes[this.value];
											var vialFlag = true;
											if (vials.length != 0) {
												$.each(vials,function(k,v) {
													if (v == barcode)
														vialFlag = false;
												});
											}
											if (vialFlag)
												vials.push(barcode);
											vialsBarcodes[this.value] = vials;
											vialAliquot[barcode] = aliquot;
											vacAvialbleFlag = true;
											var aliqutoArray = vacutinerVialBaroces[vacutainerFromVial];
											if (aliqutoArray.length == undefined)
												aliqutoArray = [];
											var vflag = true;
											$
													.each(
															aliqutoArray,
															function(
																	k,
																	ale) {
																if (ale == aliquot) {
																	vflag = false;
																	return false;
																}
															});
											if (vflag) {
												aliqutoArray
														.push(aliquot);
											}
											vacutinerVialBaroces[vacutainerFromVial] = aliqutoArray;
											return false;
										}
									});
									if (!vacAvialbleFlag) {
										$("#barcodeMsg").html(
												"Scan Vacutainer First");
										return false;
									}
									var vialsView = vialsBarcodes[vacutainerFromVial];
									var displayVial = [];
									if (vialsView != undefined) {
										$.each(vialsView, function(k, v) {
											console.log(v)
											displayVial.push("Vial-A"
													+ vialAliquot[v]);
										});
										// //debugger;
										$("#vials" + vacutainerFromVial).html(
												displayVial.join(', '));
									}
								} else {
									var subStr = "";
									for (var i = 0; i < subjectsArr.length; i++) {
										if (subStr == "")
											subStr = subjectsArr[i];
										else
											subStr = subStr + "(or)"
													+ subjectsArr[i];
									}
									$("#barcodeMsg").html(
											"Scanned Vial Barcode does not belogs to "
													+ subStr);
								}
							}
						}
					}
				});
function checkCentrifuge() {
	$("#centrifugationDataMasterIdMsg").html("");
	if ($("#centrifugationDataMasterId").val() == '-1') {
		$("#centrifugationDataMasterIdMsg").html("Required Field");
		return false;
	} else
		return true;
}

var separationStartTime = "";
var separationEndTime = "";
function separationStartTimeAdd() {
	if (checkSeparationFromValidation()) {
		separationStartTime = getServerEndTimeWithSeconds();
		$("#startTimeTd").html(getServerTime());
		$("#endButton").prop("disabled", '');
		$("#barcode").prop("disabled", 'disabled');
		$("input[name='subjectimePoint']").each(function(i, v) {
			$("#" + this.value + "Button").prop("disabled", 'disabled');
		});
	}
}

var vacutinerBarcodes = [];
function checkSeparationFromValidation() {

	var flag = true;
	$("#centrifugationDataMasterIdMsg").html("");
	if ($("#centrifugationDataMasterId").val() == '-1') {
		$("#centrifugationDataMasterIdMsg").html("Required Field");
		flag = false;
	}
	if (checkValidation()) {
		$
				.each(
						vacutinerBarcodes,
						function(index, barcode) {
							var vialsView = vialsBarcodes[barcode];
							if (vialsView != undefined && vialsView.length != 0) {
								var vacutainerBarcodeSplit = barcode.substr(0,
										barcode.length - 1).split('a');
								var vials = timeIdTimePointMap[vacutainerBarcodeSplit[4]].noOfVials;
								var aliqs = vacutinerVialBaroces[barcode];

								if (aliqs.length == undefined
										|| aliqs.length < vials) {
									debugger;
									flag = false;
									$("#" + barcode + "Msg").html(
											"Aliquots Required");
								}
							} else {
								$("#vials" + barcode)
										.html(
												"<font color='red'>Vials required</font>");
								flag = false;
							}
						});

		$("#barcodeMsg").html("");
		if (vacutinerBarcodes.length == 0) {
			$("#barcodeMsg").html("Vacuiner Required");
			flag = false;
		}
	} else
		flag = false;

	return flag;
}
function checkValidation() {
	var flag = true;
	$("#bufferStorageMsg").html("");
	if (!$('input[name="bufferStorage"]:checked').val()) {
		$("#bufferStorageMsg").html("Required Field");
		flag = false;
	}
	if (vacutinerBarcodes.length == 0) {
		flag = false;
		$("#barcodeMsg").html("Vacuiner Required");
	}
	return flag;
}
function vacutinerVialcheck() {
	var flag = true;
	$.each(vacutinerBarcodes, function(barcode, tp) {
		var vials = timeIdTimePointMap[barcode].noOfVials;
		var aliqs = vacutinerVialBaroces[barcode];
		if (aliqs.length < vials) {
			flag = false;
			$("#" + barcode + "Msg").html("Aliquots Required");
		}
	});
	return flag;
}

function removeVacutiner(barcode) {
	/*
	 * vacutinerBarcodes = $.grep(vacutinerBarcodes, function(key, value) {
	 * return key !== barcode; }); vacutainerScanTimes =
	 * $.grep(vacutainerScanTimes, function(key, value) { return key !==
	 * barcode; });
	 */
	var index = vacutinerBarcodes.indexOf(barcode);
	if (index != -1) {
		vacutinerBarcodes.splice(index, 1);
		vacutainerScanTimes.splice(index, 1);
	}
	vacutinerVialBaroces[barcode] = [];
	$("#" + barcode + "tr").remove();
}
function separationEndTimeAdd() {
	separationEndTime = getServerEndTimeWithSeconds();

	$("#endTimeTd").html(getServerTime());
	var vacutainerScanTimeskeyVal = [];
	// //debugger;
	$.each(vacutainerScanTimes, function(k, v) {
		vacutainerScanTimeskeyVal.push(k + "#" + v);
	});
	var vialScanTimeskeyVal = [];
	// //debugger;
	$.each(vialScanTimes, function(k, v) {
		vialScanTimeskeyVal.push(k + "#" + v);
	});

	var centrifugationDate = [];
	$.ajax({
		url : $("#mainUrl").val() + '/administration/getCsrfToken',
		type : 'GET',
		success : function(data) {

			centrifugationDate.push({
				name : data.parameterName,
				value : data.token
			});
			centrifugationDate.push({
				name : "studyId",
				value : $("#studyName").val()
			});
			debugger;
			centrifugationDate.push({
				name : "separationMissedSubjects",
				value : $("#separationMissedSubjects").val()
			});
			centrifugationDate.push({
				name : "vacutinerBarcodes",
				value : vacutinerBarcodes
			});
			centrifugationDate.push({
				name : "vacutainerScanTimes",
				value : vacutainerScanTimeskeyVal
			});
			centrifugationDate.push({
				name : "vialScanTimes",
				value : vialScanTimeskeyVal
			});
			// debugger;
			centrifugationDate.push({
				name : "bufferStorage",
				value : $("input[name='bufferStorage']:checked").val()
			});

			centrifugationDate.push({
				name : "startTime",
				value : separationStartTime
			});
			centrifugationDate.push({
				name : "endTime",
				value : separationEndTime
			});
			centrifugationDate.push({
				name : "centrifugationId",
				value : $("#centrifugationDataMasterId").val()
			});

			$.ajax({
				url : $("#mainUrl").val()
						+ '/study/clinical/sampleSeparationSave',
				type : 'POST',
				data : centrifugationDate,
				success : function(e) {
					console.log(e.success)
					if (e.Success === 'true' || e.Success === true) {
						// debugger;
						displayMessage("success", e.Message);
						vacutainerJson = synchronousAjaxCall(mainUrl
								+ "/study/clinical/fechtVacutainerBarcodes/"
								+ $("#studyName").val());
						sampeleSeparationReset(false);
						$('#vacutinersTable').html("");
						/*
						 * var continueStatus = confirm("Do, You wan't continue
						 * with Add Vials in Rack's?"); if (continueStatus) {
						 * callSampeleStorate(); } else {
						 * sampeleSeparationReset(false); }
						 */
						$('#crfName').val("");
						$("#activityDiv").empty();
					} else {
						displayMessage("error", e.Message);
						sampeleSeparationReset(false);
						vacutainerJson = synchronousAjaxCall(mainUrl
								+ "/study/clinical/fechtVacutainerBarcodes/"
								+ $("#studyName").val());
					}
				},
				error : function(er) {
					vacutainerJson = synchronousAjaxCall(mainUrl
							+ "/study/clinical/fechtVacutainerBarcodes/"
							+ $("#studyName").val());
				}
			});
		},
		error : function(er) {
			vacutainerJson = synchronousAjaxCall(mainUrl
					+ "/study/clinical/fechtVacutainerBarcodes/"
					+ $("#studyName").val());
		}
	});

}

function storage() {
	/*
	 * var continueStatus = confirm("Do, You wan't continue with Sample
	 * Separation?"); if (continueStatus) { callSampeleStorate(4) } else {
	 * centrifugationReset(false); }
	 */
	sampeleSeparationReset(true);
}
function sampeleSeparationReset(status) {
	var falg = true;
	/*
	 * if (statu) { falg = confirm("Do, You wan't Reset with Sample
	 * Separation?"); }
	 */
	if (falg) {
		$("#barcodeMsg").html("");
		$("#barcode").focus();
		$("#barcode").val("");
		vialsBarcodes = {};
		vacutainerScanTimes = {};
		vialScanTimes = {};
		vialAliquot = {};
		$("#barcodeMsg").html("");
		$("#vacutinersTable").html("");

	}
}
function callSampeleStorate() {
	var result = synchronousAjaxCall(mainUrl + "/study/clinical/cpuActivity/"
			+ $("#studyName").val() + "/VIALRACK/");
	if (result != null && result != "undefined" && result != "") {
		$("#activityDiv").html(result);
		console.log(result)
	}
}
