$(".loader").show();
var scData = "";
var barcode = "";
var subjectBarcode = "";
var scanTime = "";
var tpSkipDeviation = false;
var timeDeviation = false;
var deviationTime = "";
var skipedTpIds = [];
var collectionTime = "";
var vacutainer = "";
var vacutainerTime ="";
var skipDeviationCode ="";
var timeDeviationCode ="";
var subPeriod = "";
var treatmentId ="";
var timePointId = "";
var timeDeviation = true;
var timeDeviationTime ="";
var startTime = "";
var sampleDevCodeId = "0";
var barcodeTreatmentId = "";
var checkingSub = "";
var finalSubject = "";
var sampleDevCodeCommentsId ="0";
var doseDone = "";
var modeOfCollection = "";
var devComId = "";
$(function() {
	var appServerTime = $("#servertime").html();
	var tempTimeArr = appServerTime.split(":");
	var date = new Date(getServerDate());
	date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	if(hour < 10)
		hour = "0"+hour;
	if(minutes < 10)
		minutes = "0"+minutes;
	startTime = hour+":"+minutes+":00";
	asynchronousAjaxCallBack(mainUrl+ "/study/clinical/sampleCollectionDataDetails/" + $("#studyName").val(), sampleCollectionDataCallBack);
	function sampleCollectionDataCallBack(data){
		scData = data;
		$(".loader").hide();
//		registerSampleCollectionSocket();
	}

});
function registerSampleCollectionSocket(){
	var url = mainUrl + "/testWebSocket/sampleCollectionData";
	var eventSource = new EventSource(url);
	eventSource.addEventListener("rtcSampleCollectionData", function(event) {
		////debugger;
		console.log("Sample collection Data");
		var rtcData = JSON.parse(event.data);
		console.log("Event : " + event.data);
		var subjSdMap = scData.sampColDataMap;
		if(subjSdMap != null && subjSdMap != undefined){
			var subSampleDataMap = scData.sampColDataMap[rtcData.subjectNo];
			if(subSampleDataMap != null && subSampleDataMap != undefined){
				var sscpData = subSampleDataMap[rtcData.periodId];
				if(sscpData != null && sscpData != undefined){
					var ssctrMap = sscpData[rtcData.treatmentId];
					if(ssctrMap != null && ssctrMap != undefined){
						var ssctpData = ssctrMap[rtcData.timePointId];
						if(ssctpData == null || ssctpData == undefined){
							scData.sampColDataMap[rtcData.subjectNo][rtcData.periodId][rtcData.treatmentId][rtcData.timePointId] = rtcData;
						}
					}else{
						var twvData = new Map();
						twvData.set(rtcData.timePointId, rtcData);
						const twvDataobj = Object.fromEntries(twvData);
						scData.sampColDataMap[rtcData.subjectNo][rtcData.periodId][rtcData.treatmentId] = twvDataobj;
					}
				}else{
					var twvData = new Map();
					twvData.set(rtcData.timePointId, rtcData);
					const twvDataobj = Object.fromEntries(twvData);
					
					var trMap = new Map();
					trMap.set(rtcData.treatmentId,twvDataobj);
					const trMapobj = Object.fromEntries(trMap);
					
					scData.sampColDataMap[rtcData.subjectNo][rtcData.periodId] = trMapobj;
				}
			}else{
				var twvData = new Map();
				twvData.set(rtcData.timePointId, rtcData);
				const twvDataobj = Object.fromEntries(twvData);
				
				var trMap = new Map();
				trMap.set(rtcData.treatmentId,twvDataobj);
				const trMapobj = Object.fromEntries(trMap);
				
				var scpMap = new Map();
				scpMap.set(rtcData.periodId, trMapobj);
				const scpMapobj = Object.fromEntries(scpMap);
		
				scData.sampColDataMap[rtcData.subjectNo] = scpMapobj;
			}
		}else{
			var twvData = new Map();
			twvData.set(rtcData.timePointId, rtcData);
			const twvDataobj = Object.fromEntries(twvData);
			
			var trMap = new Map();
			trMap.set(rtcData.treatmentId,twvDataobj);
			const trMapobj = Object.fromEntries(trMap);
			
			var scpMap = new Map();
			scpMap.set(rtcData.periodId, trMapobj);
			const scpMapobj = Object.fromEntries(scpMap);
			
			subSampleDataMap = new Map();
			subSampleDataMap.set(rtcData.subjectNo, scpMapobj);
			const obj = Object.fromEntries(subSampleDataMap);
			scData.sampColDataMap = obj;
		}
		
	});
}
$(document).ready(function() {
	$("#barcode").focus();
	$("#timePointDeviationtr").hide();
	$('#timePointDeviationtr').css("display", "none");;
	$('#deviationsDiv').css("display", "none");
	$('#modeOfCollectionDiv').css("display", "none");
	$('#deviationsCommentsDiv').css("display", "none");
	$('#reasonDiv').css("display", "none");
});

function onBarcodeScanned(){
	var crfName = $('#crfName').val();
	if(crfName != null && crfName != "" && crfName != undefined){
		barcode = $("#barcode").val();
		$("#barcodeMsg").html("");
		var lastChar = barcode.substr(barcode.length - 1);
		if (lastChar == 'n') {
			$("#barcodeMsg").html("");
			$("#barcode").focus();
			$("#barcode").val("");
			var prefix = barcode.substr(0, 2);
			scanTime = getServerEndTimeWithSeconds();
			if (prefix == "02") {
				////debugger; 
				$('#collectionTimeTr').html("");
				$('#modeOfCollectionDiv').hide();
				$('#timePointDeviationtr').hide();
				$('#deviationsCommentsDiv').hide();
				var subjectBarcodeSplit = barcode.substr(0,barcode.length - 1).split('a');
				if(subjectBarcodeSplit[2] == scData.ctpDto.study.id){
					if(scData.ctpDto.subMap[subjectBarcodeSplit[1]]){
						var subWithDraw = scData.ctpDto.subWithdrawnMap[subjectBarcodeSplit[1]];
						var withDrawn = "";
						if(subWithDraw != undefined)
							withDrawn = scData.ctpDto.subWithdrawnMap[subjectBarcodeSplit[1]].withdrawType;
						if(withDrawn != "Withdraw" && withDrawn != "Withdrawal" && withDrawn != "DroupOut"){
							if(scData.ctpDto.subMap[subjectBarcodeSplit[1]].subjectDiscontinue != "Yes" && scData.ctpDto.subMap[subjectBarcodeSplit[1]].subjectDiscontinue != "No"){
								checkingSub = subjectBarcodeSplit[1];
								finalSubject = subjectBarcodeSplit[1];
								subjectBarcode = barcode;
								subjectTime = startTime;
								var subNo = scData.ctpDto.subMap[subjectBarcodeSplit[1]].reportingId.subjectNo;
								//StandBy checking
								var subjectNo = subjectBarcodeSplit[1];
								var standbyFlag = false;
								if(isNaN(subjectNo))
									standbyFlag = true;
								if(standbyFlag){
									var standBySub = scData.ctpDto.subMap[subjectNo];
									if(standBySub != undefined){
										var replaceSub = standBySub.stdSubjectId;
										if(replaceSub != null && replaceSub != undefined){
											subjectNo = replaceSub.subjectNo;
											subPeriod = scData.ctpDto.stdPeriodMap[subjectBarcodeSplit[1]].id;
											var treatmetObjLength = Object.keys(scData.ctpDto.subperiodwiseTreatMap).length;
											if(treatmetObjLength > 0){
												var subtrMap = scData.ctpDto.subperiodwiseTreatMap[subjectNo];
												if(subtrMap != null && subtrMap != undefined){
													var treatMentArr = subtrMap[subPeriod];
													if(treatMentArr != null && treatMentArr != undefined && treatMentArr.length > 0)
														treatmentId = treatMentArr[0].id;
													else treatmentId = scData.ctpDto.minTreatmentId;
												}else treatmentId = scData.ctpDto.minTreatmentId;
											}else treatmentId = scData.ctpDto.minTreatmentId;
											checkingSub = replaceSub.subjectNo; 
										}else
											treatmentId = scData.ctpDto.minTreatmentId;
										
									}else treatmentId = scData.ctpDto.minTreatmentId;
								}else{
									subPeriod = scData.ctpDto.stdPeriodMap[subjectNo].id
									var treatmetObjLength = Object.keys(scData.ctpDto.subperiodwiseTreatMap).length;
									if(treatmetObjLength > 0){
										var subTrMap = scData.ctpDto.subperiodwiseTreatMap[subjectNo];
										if(subTrMap != null && subTrMap != undefined){
											var treatMentArr = subTrMap[subPeriod];
											if(treatMentArr != null && treatMentArr != undefined && treatMentArr.length > 0)
												treatmentId = treatMentArr[0].id;
											else treatmentId = scData.ctpDto.minTreatmentId;
										}else treatmentId = scData.ctpDto.minTreatmentId;
									}else treatmentId = scData.ctpDto.minTreatmentId;
								}
								$('#subjectMsg').html(subNo);
								$('#vacutainerMsg').html("");
							}else{
								subjectBarcode ="";
								startTime = "";
								$("#barcodeMsg").html("Subject Droped.");
								$('#subjectMsg').html("");
								$('#vacutainerMsg').html("");
								$('#collectionTimeTr').html("");
							}
						}else{
							if(withDrawn != "Withdraw" || withDrawn != "Withdrawal")
								$("#barcodeMsg").html("Subject Withdrawn From Study");
							else $("#barcodeMsg").html("Subject DropOut From Study.");
							subjectBarcode ="";
							$('#collectionTimeTr').html("");
						}
					}else{
						subjectBarcode = "";
						startTime ="";
						$("#barcodeMsg").html("Subject Not Exists.");
						$('#subjectMsg').html("");
						$('#vacutainerMsg').html("");
						$('#collectionTimeTr').html("");
					}
				}else{
					$("#barcodeMsg").html("Subject Barcode does not belongs to Study : "+scData.ctpDto.study.projectNo);
					$('#subjectMsg').html("");
					$('#vacutainerMsg').html("");
					subjectBarcode ="";
					$('#collectionTimeTr').html("");
				}
			}else if (prefix == "04") {
				//debugger;
				if(subjectBarcode != ""){
					$("#barcodeMsg").html("");
					var subbarcodeSplit = subjectBarcode.substr(0,subjectBarcode.length - 1).split('a');
					var vacutainerBarcodeSplit = barcode.substr(0, barcode.length - 1).split('a');
					subPeriod = scData.ctpDto.stdPeriodMap[subbarcodeSplit[1]];
					if(vacutainerBarcodeSplit[1] == subPeriod.id){
						var treatmentFlag = true;
						if(vacutainerBarcodeSplit[2] != 0){
							if(treatmentId != vacutainerBarcodeSplit[2])
								treatmentFlag = false;
						}
						
						if(treatmentFlag){
							if(checkingSub == vacutainerBarcodeSplit[3]){
									//debugger;
									barcodeTreatmentId = vacutainerBarcodeSplit[2];
									if(barcodeTreatmentId ==0 || barcodeTreatmentId == treatmentId){
										timePointId = vacutainerBarcodeSplit[4];
										var scPojo = scData.samplesMap[vacutainerBarcodeSplit[4]];
										if(scPojo != null && scPojo != undefined){
											var scDataPojo = null;
											var countLength = Object.keys(scData.sampColDataMap).length;
											if(countLength > 0){
												var subCol = scData.sampColDataMap[subbarcodeSplit[1]];
												if(subCol != undefined){
													var periodCol = subCol[subPeriod.id];
													if(periodCol != undefined){
														if(barcodeTreatmentId != 0){
															var trinfCol = periodCol[treatmentId];
															if(trinfCol != undefined){
																scDataPojo = trinfCol[vacutainerBarcodeSplit[4]];
															}
														}else{
															for (var key in periodCol) {
															  if (periodCol.hasOwnProperty(key)) {
															    var objArr = periodCol[key];
															    for (var keyVal in objArr) {
																  if (objArr.hasOwnProperty(keyVal)) {
																	var keyValPojo = scData.samplesMap[keyVal];
																	var curTpPojo = scData.samplesMap[vacutainerBarcodeSplit[4]];
																	if(keyValPojo != undefined && curTpPojo != undefined){
																		if((keyValPojo.sign+keyValPojo.timePoint) == (curTpPojo.sign+curTpPojo.timePoint)){
																			scDataPojo = keyValPojo;
																		}
																	}
																    /*if(keyVal == vacutainerBarcodeSplit[4])
																    	scDataPojo = objArr[vacutainerBarcodeSplit[4]];*/
																    }
																}
															  }
															}
														}
													}
												}
//												scDataPojo = scData.sampColDataMap[subbarcodeSplit[1]][subPeriod.id][treatmentId][vacutainerBarcodeSplit[4]];
											}
											if(scDataPojo == null || scDataPojo == undefined){
												if(scPojo.sign.trim() != "-"){
													//postDose timepoints
													var subDosePojo = null;
													var pwdosecountLength = Object.keys(scData.ctpDto.pwdoseMap).length;
													if(pwdosecountLength > 0){
														var subjMap = scData.ctpDto.pwdoseMap[subbarcodeSplit[1]];
														if(subjMap != undefined){
															var pwSdMap = subjMap[subPeriod.id];
															if(pwSdMap != undefined)
																subDosePojo = pwSdMap[treatmentId];
														}
//														subDosePojo = scData.ctpDto.pwdoseMap[subbarcodeSplit[1]][subPeriod.id][treatmentId];
													}
													if(subDosePojo != null && subDosePojo != undefined){
	                                                       //continue printing
//	                                                        var subNo = scData.ctpDto.subMap[vacutainerBarcodeSplit[3]].reportingId.subjectNo;
//	                                                        var subNo = scData.ctpDto.subMap[finalSubject].reportingId.subjectNo;
	                                                        var subNo = "";
	                                                        var displaySubNo = "";
	                                                        var subPojo = subDosePojo.studySubjects;
	                                                        if(subPojo != null && subPojo != "undefined"){
	                                                            if(subPojo.stdSubjectId != null && subPojo.stdSubjectId != undefined){
	                                                                subNo = subPojo.subjectNo;
	                                                                displaySubNo = subPojo.reportingId.subjectNo;
	                                                            }else {
	                                                                subNo = subPojo.subjectNo;
	                                                                displaySubNo = subNo;
	                                                            }
	                                                        }
	                                                        doseDone = "Done";
	                                                        displayVacutainerDetails(subNo, scPojo, displaySubNo);
	                                                    }else{
	                                                        $("#barcodeMsg").html("Subject Dose Not Done");
	                                                        $('#vacutainerMsg').html("");
	                                                        $('#collectionTimeTr').html("");
	                                                    }
												}else{
													//Pre dose Timepoints
													//timepoints Order for Deviation
													var samplesOrderIds = [];
													var twsampMap = scData.twSamplesMap[treatmentId];
													$.each(twsampMap, function(key, value) {
														samplesOrderIds.push(key);
													});
													samplesOrderIds.sort();
													doseDone = "NotDone";
													var tporderNo = samplesOrderIds.indexOf(vacutainerBarcodeSplit[4]);
													if(tporderNo == 0){
														//continue printing
														displayVacutainerDetails(vacutainerBarcodeSplit[3], scPojo, vacutainerBarcodeSplit[3]);
													}else{
														var pendingTpArr = [];
														for(i=parseInt(tporderNo); i>=0; i--){
															var scId = samplesOrderIds[i];
															var sctpPojo = scData.samplesMap[scId];
															if(scId != scPojo.id){
																var sccpData = null;
																var scdLengthVal = Object.keys(scData.sampColDataMap).length;
																if(scdLengthVal > 0)
																  sccpData = scData.sampColDataMap[subbarcodeSplit[1]][subPeriod.id][treatmentId][scId];
																if(sccpData == null || sccpData == undefined){
																	pendingTpArr.push(sctpPojo.sign+sctpPojo.timePoint+"("+sctpPojo.vacutainerNo+")");
																	tpSkipDeviation = true;
																	skipedTpIds.push(sctpPojo.id);
																}else{
																	tpSkipDeviation = false;
																	break;
																}
															}
														}
														if(tpSkipDeviation){
															//continue with deviation
															var textMsg = "";
															if(pendingTpArr.length > 1)
															      textMsg = "Samples Not Collected For Timepoints :"+pendingTpArr+".";
															else 
																textMsg = "Samples Not Collected For Timepoint :"+pendingTpArr+".";
															
															 Swal.fire({
																  title: 'Are you sure?',
																  text:textMsg,
																  showDenyButton: true,
																  showCancelButton: false,
																  confirmButtonText: 'OK',
																  denyButtonText: 'NOT OK',
																}).then((result) => {
																  /* Read more about isConfirmed, isDenied below */
																  if (result.isConfirmed) {
																	  swal.fire({
																			title: 'Are you sure?',
																			icon: 'warning',
																			text:textMsg
																		});
																	  displayVacutainerDetails(vacutainerBarcodeSplit[3], scPojo, vacutainerBarcodeSplit[3]);
																  } else if (result.isDenied) {
																    Swal.fire('Changes are not saved', '', 'info')
																  }
																})
															
														}else{
															//continue without deviation
															displayVacutainerDetails(vacutainerBarcodeSplit[3], scPojo, vacutainerBarcodeSplit[3]);
														}
													}
												}
											}else{
												$("#barcodeMsg").html("Subject Sample Collection Done For Timepoint :"+scPojo.sign+scPojo.timePoint);
												$('#vacutainerMsg').html("");
												$('#collectionTimeTr').html("");
											}
										}else{
											$("#barcodeMsg").html("Invalid Vacutainer barcode");
											$('#vacutainerMsg').html("");
											$('#collectionTimeTr').html("");
										}
									}else{
										var subNo = scData.ctpDto.subMap[subbarcodeSplit[1]].reportingId.subjectNo;
										$("#barcodeMsg").html("Scanned Vacutainer does not belongs "+subNo+" treatment.");
										$('#vacutainerMsg').html("");
										$('#collectionTimeTr').html("");
									}
								}else{
									var subNo = scData.ctpDto.subMap[subbarcodeSplit[1]].reportingId.subjectNo;
									$("#barcodeMsg").html("Scanned Vacutainer does not belongs to subject :"+subNo);
									$('#vacutainerMsg').html("");
									$('#collectionTimeTr').html("");
								}
						}else{
							$("#barcodeMsg").html("Scanned Vacutainer does not belongs to subject treatment :"+scData.ctpDto.treatmentMap[treatmentId].randamizationCode);
							$('#vacutainerMsg').html("");
							$('#collectionTimeTr').html("");
						}	
					}else{
						$("#barcodeMsg").html("Scanned Vacutainer does not belongs to period :"+subPeriod.periodName);
						$('#vacutainerMsg').html("");
						$('#collectionTimeTr').html("");
					}
				}else{
					$("#barcodeMsg").html("Scan Subject Barcode First");
					$('#subjectMsg').html("");
					$('#vacutainerMsg').html("");
					$('#collectionTimeTr').html("");
				}
			}else 
				$("#barcodeMsg").html("Invalid Barcode.");
		}
	}else $("#barcodeMsg").html("Select Activity.");
}
$("#barcode").on('input',function(e) {
	onBarcodeScanned();
});
function checkStudy(studyId) {
	$("#subjectMsg").html("");
	if (studyId == $("#studyName").val()) {
		return true;
	} else {
		$("#subjectMsg").html("Subject Barcode Required");
		return false;
	}

}
function checkSubjectBarcode(subjectBarcode) {
	//debugger;
	var flag = false;
	$("#subjectMsg").html("");
	$.each(data.subjectBarocodes, function(index, barcode) {
		if (barcode == subjectBarcode) {
			$.each(data.droppedSubjects, function(droppedSubjectIndex,droppedSubject) {
				if (barcode == droppedSubject) {
					$("#subjectMsg").html("Subject Has Drouped");
					return false;
				}
			});
			$.each(data.replacedSubjects, function(replacedSubjectIndex,
					replacedSubject) {
				if (barcode == replacedSubject) {
					$("#subjectMsg").html("Subject Has Replaced");
					return false;
				}
			});
			flag = true;
			return false;
		}
	});
	if (!flag)
		$("#subjectMsg").html("In-valied Subject Barcode");
	return flag;
}

function clearForm() {
	$("#barcode").val("");
	$('#subjectMsg').html("");
	$('#vacutainerMsg').html("");
	$("#barcodeMsg").html("");
	$("#subjectMsg").css('background-color', '');
	$("#vacutainerMsg").css('background-color', '');
	subjectBarcode = "";
	scanTime = "";
	tpSkipDeviation = false;
	timeDeviation = false;
	deviationTime = "";
	skipedTpIds = [];
	collectionTime = "";
	vacutainer = "";
	vacutainerTime ="";
	skipDeviationCode ="";
	timeDeviationCode ="";
	clearForVacutainer();
	subPeriod = "";
	treatmentId ="";
	timePointId = "";
	timeDeviation = true;
	timeDeviationTime ="";
	sampleDevCodeId = "0";
	doseDone = "";
}
function clearForVacutainer() {
	
	
//	sampleType = 0;
//	scheduleTime = "";
//	barcodeTemp = "";
	$("#barcode").val("");
	vacutainer = "";
	vacutainerTime = "";
	$("#vacutainerMsg").html("");
	$('#timePointDeviationtr').hide();
	$('#timePointDeviationtr').css("display", "none");;
	$('#deviationsDiv').css("display", "none");
	$('#modeOfCollectionDiv').css("display", "none");
	$('#deviationsCommentsDiv').css("display", "none");
	$('#reasonDiv').css("display", "none");
	$("#vacutainerMsg").css('background-color', '');
//	collectionTime = "";
	deviationStatus = "0";

//	deviationStatusMsg = "";
	$("#collectionTimeTr").html("");
	vacutainerDeviation = "";
	doseDone = "";

}
function resetCollectionTime(messageId, ranningTimeId, runningTimeWithSecondsId) {
	$("#timePointDeviationtr").hide();
	$('#timePointDeviationtr').css("display", "none");;
	$('#deviationsDiv').css("display", "none");
	$('#modeOfCollectionDiv').css("display", "none");
	$('#deviationsCommentsDiv').css("display", "none");
	$('#reasonDiv').css("display", "none");
	$("#vacutainerDeviation").val("-1");
	$("#deviationVacutainerMessageAlert").html("");
	collectionTime = "";
	$("#collectionTimeTr").html(
					'<input type="button" value="Collect" class="btn btn-primary" style="padding: 0px;" onclick="sampleColletionTime()"/><font color=\"red\" id=\"clollectoinMesg\"></font>');
}
function sampleColletionTime() {
	var subBar = subjectBarcode.substr(0,subjectBarcode.length - 1).split('a');
	var dosedLength = Object.keys(scData.ctpDto.dosedMap).length;
	timeDeviation = false;
	var devStr = "";
	debugger;
	if(dosedLength > 0){
		var dosePojo = null;
		var doseTime = getPlanedTimeForOtherPeriods(subPeriod.id, subBar[1]);
		/*var doseTime = scData.ptdDto.plannedDate;
		  if(barcodeTreatmentId != "0"){
			var subdoseVal = scData.ctpDto.dosedMap[subBar[1]];
			if(subdoseVal != undefined){
				var periodDoseVal = subdoseVal[subPeriod.id];
				if(periodDoseVal != undefined){
					dosePojo = periodDoseVal[treatmentId];
					if(dosePojo != null && dosePojo != undefined)
						doseTime = dosePojo.actualTime;
				}
			}
		}else{
			var subdoseVal = scData.ctpDto.dosedWithoutTreatmentMap[subBar[1]];
			if(subdoseVal != undefined){
				dosePojo = subdoseVal[subPeriod.id];
				if(dosePojo != null && dosePojo != undefined)
					doseTime = dosePojo.actualTime;
			}
		}*/
		if(doseTime != null && doseTime !="" && doseTime != undefined){
			var sctp = scData.samplesMap[timePointId];
			devStr = calculateDeviation(doseTime, sctp.sign+sctp.timePoint,sctp.windowPeriod, sctp.windowPeriodSign , sctp.windowPeriodType.code);
		}
		
	}
	if(devStr != ""){
		timeDeviation = true;
		timeDeviationTime = devStr;
	}
	if(timeDeviation){
		if(tpSkipDeviation){
			if(scData.ctpDto.dsDto != null && scData.ctpDto.dsDto != undefined){
				if(scData.ctpDto.dsDto.devionCodeMap != null && scData.ctpDto.dsDto.devionCodeMap != undefined){
					sampleDevCodeId = scData.ctpDto.dsDto.devionCodeMap['TPSKIPANDTP'];
				}
			}
		}else{
			if(scData.ctpDto.dsDto != null && scData.ctpDto.dsDto != undefined){
				if(scData.ctpDto.dsDto.devionCodeMap != null && scData.ctpDto.dsDto.devionCodeMap != undefined){
					sampleDevCodeId = scData.ctpDto.dsDto.devionCodeMap['TPD'];
				}
			}
		}
		Swal.fire({
		  title: '',
		  text:'Time Deviation Happened : '+timeDeviationTime
		});
	}else {
		if(tpSkipDeviation){
			if(scData.ctpDto.dsDto != null && scData.ctpDto.dsDto != undefined){
				if(scData.ctpDto.dsDto.devionCodeMap != null && scData.ctpDto.dsDto.devionCodeMap != undefined){
					sampleDevCodeId = scData.ctpDto.dsDto.devionCodeMap['TPSKIP'];
				}
			}
		}
	}
	collectionTime = getServerTime();
	collectionTimeOnly = getServerTime();
	$("#collectionTimeTr").html(getServerTime);
	
}
function getPlanedTimeForOtherPeriods(periodno, subjNo){
	//debugger;
	var reportingDate = null;
	 var doseDate = null;
	if(periodno != null && periodno != undefined){
		var periodOneId = scData.ptdDto.priodIdsMap['P1'];
		if(periodOneId == periodno){
			  doseDate = scData.ptdDto.plannedDate;
			  var dosePojo = null;
			  if(barcodeTreatmentId != "0"){
				var subdoseVal = scData.ctpDto.dosedMap[subjNo];
				if(subdoseVal != undefined){
					var periodDoseVal = subdoseVal[subPeriod.id];
					if(periodDoseVal != undefined){
						dosePojo = periodDoseVal[treatmentId];
						if(dosePojo != null && dosePojo != undefined)
							doseDate = dosePojo.actualTime;
					}
				}
			}else{
				var subdoseVal = scData.ctpDto.dosedWithoutTreatmentMap[subjNo];
				if(subdoseVal != undefined){
					dosePojo = subdoseVal[subPeriod.id];
					if(dosePojo != null && dosePojo != undefined)
						doseDate = dosePojo.actualTime;
				}
			}
		}else{
			var firstPeriodReportingDate = scData.ptdDto.repotingDateMap[periodOneId];
		    var periodName = scData.ptdDto.priodNamesMap[periodno];
			if(periodno != null && periodno != undefined){
				reportingDate = scData.ptdDto.repotingDateMap[periodno];
				if(reportingDate != null && reportingDate != undefined){
					var firstPdriodDoseDate = scData.ptdDto.periodWiseDoseMap[periodOneId];
					if(firstPeriodReportingDate != null && firstPeriodReportingDate != undefined){
						var diffDays = getNumberOfDaysDifferece(new Date(firstPdriodDoseDate), new Date(firstPeriodReportingDate));
						var minutes = diffDays * 24 * 60;
						var ptime = scData.ptdDto.plannedDate;
						if(reportingDate != null && reportingDate != undefined){
							var periodNo = scData.ptdDto.periodNoIdsMap[periodno];
							var periodDoseDate = null;
							if(periodNo != 1){
								var prevDosePeriodId = scData.ptdDto.periodNoMap[periodNo-1]
								periodDoseDate = scData.ptdDto.periodWiseDoseMap[prevDosePeriodId];
							}else periodDoseDate = firstPeriodReportingDate;
							if(periodDoseDate != null){
								periodDoseDate = new Date(periodDoseDate);
								var min =0;
								var washoutDays = scData.ptdDto.washoutDays;
								if(washoutDays != null && washoutDays != undefined)
									min = min + (washoutDays * 24 * 60);
								periodDoseDate = new Date(periodDoseDate.getTime() + min*60000);
							//	debugger;
								var dayDiff = getNumberOfDaysDifferece(new Date(getServerDate()), periodDoseDate);
								if(dayDiff >= 0){
									doseDate = removeTime(periodDoseDate);
									minutes = getTotalMinutes(ptime, minutes);
									doseDate = new Date(doseDate.getTime() + minutes*60000);
								}
							}
						}
					}
				}
			}
		}
	}
	return doseDate;
		
}
function getNumberOfDaysDifferece(date2, date1){
	/*const dt2 = new Date(date2);
	const dt1 = new Date(date1);*/
	var sec_num = date2.getTime() - date1.getTime();
   /* var daysStr = (sec_num / (1000 * 3600 * 24)).toString();*/
	var daysStr = (sec_num / (1000 * 3600 * 24));
   // var daysArr = daysStr.split(".");
    var days = Math.floor(daysStr);
	return days;
}
function getTotalMinutes(date, min){
	const dt = new Date(date); 
	min = min + (dt.getHours() * 60);
	min = min + dt.getMinutes();
	min = min +(dt.getSeconds()/60);
	return min;
}
function removeTime(date) {
	const dt = new Date(date); 
  return new Date(dt.getFullYear(), dt.getMonth(),dt.getDate());
}
function minutesDiff(dateTimeValue2, dateTimeValue1) {
   var differenceValue =(dateTimeValue2.getTime() - dateTimeValue1.getTime()) / 1000;
   return differenceValue /= 60;
}
function submitForm() {
	//debugger;
	$('#save_btn').attr("disabled", true);
	if (checkFromValidation()) {
		var sampleCollectionDate = [];
		$.ajax({
					url : $("#mainUrl").val() + '/administration/getCsrfToken',
					type : 'GET',
					success : function(data) {
						sampleCollectionDate.push({
							name : data.parameterName,
							value : data.token
						});
						sampleCollectionDate.push({
							name : "studyId",
							value : $("#studyName").val()
						});
						sampleCollectionDate.push({
							name : "skipDeviation",
							value : tpSkipDeviation
						});
						if(skipedTpIds.length > 0){
							sampleCollectionDate.push({
								name : "skipedTpIds",
								value : skipedTpIds
							});
						}
						sampleCollectionDate.push({
							name : "timeDeviation",
							value : timeDeviation
						});
						sampleCollectionDate.push({
							name : "deviationTime",
							value : deviationTime
						});
						sampleCollectionDate.push({
							name : "subject",
							value : subjectBarcode
						});
						sampleCollectionDate.push({
							name : "subjectTime",
							value : subjectTime
						});
						sampleCollectionDate.push({
							name : "timeDeviationCode",
							value : timeDeviationCode
						});
						sampleCollectionDate.push({
							name : "timeDeviationTime",
							value : timeDeviationTime
						});
						sampleCollectionDate.push({
							name : "timeDeviation",
							value : timeDeviation
						});
						sampleCollectionDate.push({
							name : "sampleDevCodeId",
							value : sampleDevCodeId 
						});
					
						sampleCollectionDate.push({
							name : "vacutainer",
							value : vacutainer
						});
						if(skipDeviationCode != ""){
							sampleCollectionDate.push({
								name : "skipDeviationCode",
								value : skipDeviationCode
							});
						}
						sampleCollectionDate.push({
							name : "vacutainerTime",
							value : vacutainerTime
						});
						sampleCollectionDate.push({
							name : "collectionTime",
							value : collectionTime
						});
						sampleCollectionDate.push({
							name : "deviationStatusMsg",
							value : $("#vacutainerDeviation").val()
						});
						if(sampleDevCodeCommentsId != null && sampleDevCodeCommentsId != "" &&sampleDevCodeCommentsId != "0" && sampleDevCodeCommentsId != undefined){
							sampleCollectionDate.push({
								name : "sampleDevCodeCommentsId",
								value : sampleDevCodeCommentsId
							});
						}
						sampleCollectionDate.push({
							name : "modeOfCollection",
							value : modeOfCollection
						});
						if(devComId != null && devComId != "" &&devComId != "0" && devComId != undefined){
							sampleCollectionDate.push({
								name : "deviationCommentsId",
								value : devComId
							});
						}
						
						/*if($('#sampleReason').val() != null || $('#sampleReason').val() != "" || $('#sampleReason').val() != undefined){
							sampleCollectionDate.push({
								name : "sampleReason",
								value : $('#sampleReason').val()
							});
						}*/
						
						$.ajax({
									url : $("#mainUrl").val()
											+ '/study/clinical/stdClinicalSampleCollectionSave',
									type : 'POST',
									data : sampleCollectionDate,
									success : function(e) {
										console.log(e.success)
										$('#crfName').val("");
										$("#activityDiv").empty();
										//debugger;
										if (e.msgFlag === true) 
											displayMessage("success", e.mealsMsg);
										else 
											displayMessage("error", e.mealsMsg);
										clearForm();
										$('#save_btn').removeAttr("disabled");
									},
									error : function(er) {
										$('#save_btn').removeAttr("disabled");
										$('#crfName').val("");
										$("#activityDiv").empty();
										//debugger;
									}
								});
					},
					error : function(er) {
						//debugger;
					}
				});

	}
}
function displayVacutainerDetails(subNo, scPojo, displaySubject){
	debugger;
	//For Schedule time display with time point
	var doseOrPlanTime = null;
	if(doseDone == 'Done')
		doseOrPlanTime = scData.ptdDto.subjectDoseMap[subNo][subPeriod.id];
	else doseOrPlanTime = scData.ptdDto.plannedDate;
	if(doseOrPlanTime != null && doseOrPlanTime != undefined){
		var scheduleTime = calculateScheduleTimeToTimePoint(doseOrPlanTime, scPojo.timePoint, scPojo.sign);
		if(scheduleTime != null && scheduleTime != "" && scheduleTime != undefined)
			scPojo.tpWithScheduleTime = scPojo.timePoint+"("+scheduleTime+")";
		else scPojo.tpWithScheduleTime = scPojo.timePoint;
	  var displaymessage = "Subject : "+displaySubject
							 + ",\tPeriod : "+ subPeriod.periodName
							 + ",\tTimepoint : "+scPojo.sign+scPojo.tpWithScheduleTime
							 + ",\t Vacutainer No. : "+scPojo.vacutainerNo

		$("#vacutainerMsg").html(displaymessage);
		vacutainer = barcode;
		vacutainerTime = scanTime;
		$("#collectionTimeTr").html('<input type="button" id = "collectionButton" class="btn btn-warning btn-md" value="Collect" style="padding: 0px; font-size :small;" onclick="sampleColletionTime()"/>'
							+ '<font color=\"red\" id=\"clollectoinMesg\"></font>');
		$('#vacutainerDeviation').val(-1);
		$('#deviationComments').val(-1);
		$('#timePointDeviationtr').show();
		$('#timePointDeviationtr').show();
		$('#deviationsDiv').show();
		$('#modeOfCollectionDiv').show();
		$('#deviationsCommentsDiv').show();
		$('#reasonDiv').show();
	}else $('#barcodeMsg').html("Planned dose time is not available. Please contact project in-charge or PI");
}
function calculateScheduleTimeToTimePoint(podDate, tpStr, sign){
	//debugger;
	var hours = 0;
	var minutes = 0;
	const dt = new Date(podDate); 
	var firsDate = removeTime(dt);
	var currnetDate = removeTime(new Date(getServerDate()));
	var Difference_In_Time = firsDate.getTime() - currnetDate.getTime();
    var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
   // Difference_In_Days =  Math.round(Math.abs(Difference_In_Days));
    Difference_In_Days =  Math.round(Difference_In_Days);
	if(tpStr != null && tpStr != "" && tpStr != undefined){
		var tpStrArr = tpStr.split(".");
		if(tpStrArr.length > 0){
			if(Difference_In_Days > 0){
				if(dt.getHours() == 0){
					hours = (Difference_In_Days * 24) + 24;
				}else hours = (Difference_In_Days * 24) + dt.getHours();
			}else{
				if(dt.getHours() == 0)
					hours = 24;
				else hours = dt.getHours();
			}
			minutes = dt.getMinutes();
			minutes = minutes + (hours * 60);
			var tpMin =0;
			if(tpStrArr.length ==1){
				tpMin = (Number(tpStrArr[0]) * 60);
			}else if(tpStrArr.length ==2){
				if(tpStrArr[0].trim() == ""){
					tpMin = parseFloat("0."+tpStrArr[1]);
					tpMin = tpMin*60;
				}else
					tpMin = (Number(tpStrArr[0]) * 60) + (parseFloat("0."+tpStrArr[1]) * 60);
			}
			if(sign != null && sign != "" && sign != undefined && sign.trim() == "-"){
				minutes = minutes - tpMin;
			}else {
				minutes = minutes + tpMin;
			}
			hours = parseInt(minutes /60);
			minutes = parseInt(minutes % 60);
			
		}
	}
//	hours = hours.replace('-', '');
//	minutes = minutes.replace('-', '');
	/*dt.setHours(dt.getHours() + parseInt(hours));
	dt.setMinutes(dt.getMinutes() + parseInt(minutes));
	hours = dt.getHours();
	minutes = dt.getMinutes();*/
	hours = Math.abs(hours);
	minutes = Math.abs(minutes);
	if(hours < 10)
		hours = "0"+hours;
	if(minutes < 10)
		minutes = "0"+minutes;
	return hours+":"+minutes;
	
}
function checkDeviationMessage(){
	var value = $('#vacutainerDeviation').val();
	var flag = false;
	if(value == null || value == "-1" || value == undefined){
		$('#deviationVacutainerMessageAlert').html("Required Field.");
		flag = false;
	}else {
		$('#deviationVacutainerMessageAlert').html("");
		sampleDevCodeCommentsId = value;
		flag = true;
	}
	return flag;
}
function removeTime(date) {
	const dt = new Date(date); 
  return new Date(dt.getFullYear(), dt.getMonth(),dt.getDate());
}
function modeOfCollectionValiation(){
	var flag = false;
	modeOfCollection = "";
	var cannulaRadioFlag = false;
	var tfpRadioFlag = false;
	if ($("#modOfColl_cannula").prop("checked")) {
		cannulaRadioFlag = true;
		modeOfCollection = $("#modOfColl_cannula").val();
	}
	if ($("#modOfColl_tfp").prop("checked")) {
		tfpRadioFlag = true;
		modeOfCollection = $("#modOfColl_tfp").val();
	}
	if(cannulaRadioFlag || tfpRadioFlag){
		flag = true;
		$('#modeOfCollectionMsg').html("");
	}else{
		$('#modeOfCollectionMsg').html("Required Field.");
		flag = false;
	}
	return flag;
}
/*function deviationComments(){
	var flag = false;
	var value = $('#deviationComments').val();
	if(value != null && value != "-1" && value != undefined){
		$('#devComMsg').html("");
		flag = true;
		devComId = value;
	}else{
		$('#devComMsg').html("Required Field");
		flag = false;
	}
	return flag;
}*/

function checkFromValidation() {
	var flag = false;
	var subjectFlag = false;
	var vacFalg = false;
	var collectionFlag = false;
	var deviationflag = false;
	var modOfColFlag = false;
	var devComFlag = false;
	if (subjectBarcode == '') {
		$("#subjectMsg").html("<font color='red'>Subject Barcode Required</font>");
		subjectFlag = false;
	}else subjectFlag = true;
	
	if (vacutainer == '') {
		$("#vacutainerMsg").html("<font color='red'>Vacutainer Barcode Required</font>");
		vacFalg = false;
	}else vacFalg = true;
	
	if (collectionTime == '') {
		$("#clollectoinMesg").html("<font color='red'>Collection Time Required</font>");
		collectionFlag = false;
	}else{
		$("#clollectoinMesg").html("");
		collectionFlag = true;
	}
	modOfColFlag = modeOfCollectionValiation();
	var cannulaInsertion = "";
	debugger;
	if(modOfColFlag){
		var barcodSplit = subjectBarcode.substr(0,subjectBarcode.length - 1).split('a');
		var subPeriod = scData.ctpDto.stdPeriodMap[barcodSplit[1]].id;
		var volId = scData.ctpDto.subVolIdsMap[barcodSplit[1]];
		if(volId != null && volId != undefined){
			var subCannulaLength = Object.keys(scData.ctpDto.subCannulaMap).length;
			if(subCannulaLength > 0){
				var cannulaMap = scData.ctpDto.subCannulaMap[volId];
				if(cannulaMap != null && cannulaMap != undefined){
					cannulaInsertion = cannulaMap[subPeriod];
				}
			}
		}
	}
	if((cannulaInsertion == "Done" && modeOfCollection == "TFP") ||  timeDeviation){
//		devComFlag = deviationComments();
		devComFlag = true;
		deviationflag = checkDeviationMessage();
	}else{
		deviationflag = true;
		devComFlag = true;
	}
	if(subjectFlag && vacFalg && collectionFlag  && deviationflag && modOfColFlag && devComFlag)
		flag = true;
	return flag;
}