

var timePointPk = "";
var endTime = "";
var periodId="";
var subjectsJson = "";
var subjectBarcode = "";
var subjectScanTime ="";
var crrentVitalIds = [];
var perameterIds = [];
var perameterIdsAndType = [];
var perams = [];
var gvIds = [];
var treatmentId = "";
var tpSkipDeviation = false;
var skipedTpIds = [];
var timeDeviation = false;
var timeDeviationTime = "";
var timeDeviationCode ="";
var startTime = "";
var timeDeviationId = "0";
var vitalsTreatmentSpecific = "No";
var positionArr = [];
var positionVal = "";
var collectedRecordsArr = [];
var mandatoryParamsArr = [];
$(function() {
	$(".loader").show();
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
	//startTime = hour+":"+minutes+":00";
	asynchronousAjaxCallBack(mainUrl+ "/study/clinical/vitalCollectionDetails/" + $("#studyName").val(), vitalsDataCallBack);
});

function vitalsDataCallBack(data){
	subjectsJson = data;
	$(".loader").hide();
	registerVitalCollectionSocket();
}

function registerVitalCollectionSocket(){
	var url = mainUrl + "/testWebSocket/vitalCollectionData";
	var eventSource = new EventSource(url);
	eventSource.addEventListener("rtcvitalCollectionData", function(event) {
//		debugger;
		console.log("Vital collection Data");
		var rtcData = JSON.parse(event.data);
		if(rtcData != null && rtcData != undefined){
//			collectedRecordsArr.push(rtcData.collectedPosition);
			var jsonString = JSON.stringify(Object.assign({}, rtcData)) 
			var json_obj = JSON.parse(jsonString);
			
			console.log("Event : " + event.data);
			var subjVdMap = subjectsJson.svtpData;
			if(subjVdMap != null && subjVdMap != undefined){
				var subVitalDataMap = subjectsJson.svtpData[rtcData.subjectNo];
				if(subVitalDataMap != null && subVitalDataMap != undefined){
					var svpData = subVitalDataMap[rtcData.periodId];
					if(svpData != null && svpData != undefined){
						var svtrMap = svpData[rtcData.treatmentId];
						if(svtrMap != null && svtrMap != undefined){
							var svtpData = svtrMap[rtcData.timePointId];
							if(svtpData == null || svtpData == undefined){
								var vcdTempArr = [];
								vcdTempArr.push(json_obj);
								subjectsJson.svtpData[rtcData.subjectNo][rtcData.periodId][rtcData.treatmentId][rtcData.timePointId] = vcdTempArr;
							}else{
								var vcdTempArr = subjectsJson.svtpData[rtcData.subjectNo][rtcData.periodId][rtcData.treatmentId][rtcData.timePointId];
								vcdTempArr.push(json_obj);
								subjectsJson.svtpData[rtcData.subjectNo][rtcData.periodId][rtcData.treatmentId][rtcData.timePointId] = vcdTempArr;
							}
						}else{
							var vcdTempArr = [];
							vcdTempArr.push(json_obj);
							var twvData = new Map();
							twvData.set(rtcData.timePointId, vcdTempArr);
							const twvDataobj = Object.fromEntries(twvData);
							subjectsJson.svtpData[rtcData.subjectNo][rtcData.periodId][rtcData.treatmentId] = twvDataobj;
						}
					}else{
						var vcdTempArr = [];
						vcdTempArr.push(json_obj);
						var twvData = new Map();
						twvData.set(rtcData.timePointId, vcdTempArr);
						const twvDataobj = Object.fromEntries(twvData);
						
						var trMap = new Map();
						trMap.set(rtcData.treatmentId,twvDataobj);
						const trMapobj = Object.fromEntries(trMap);
						
						subjectsJson.svtpData[rtcData.subjectNo][rtcData.periodId] = trMapobj;
					}
				}else{
					var vcdTempArr = [];
					vcdTempArr.push(json_obj);
					var twvData = new Map();
					twvData.set(rtcData.timePointId, vcdTempArr);
					const twvDataobj = Object.fromEntries(twvData);
					
					var trMap = new Map();
					trMap.set(rtcData.treatmentId,twvDataobj);
					const trMapobj = Object.fromEntries(trMap);
					
					var vpMap = new Map();
					vpMap.set(rtcData.periodId, trMapobj);
					const vpMapobj = Object.fromEntries(vpMap);
			
					subjectsJson.svtpData[rtcData.subjectNo] = vpMapobj;
					
					
				}
			}else{
				var twvData = new Map();
				twvData.set(rtcData.timePointId, rtcData);
				const twvDataobj = Object.fromEntries(twvData);
				
				var trMap = new Map();
				trMap.set(rtcData.treatmentId,twvDataobj);
				const trMapobj = Object.fromEntries(trMap);
				
				var vpMap = new Map();
				vpMap.set(rtcData.periodId, trMapobj);
				const vpMapobj = Object.fromEntries(vpMap);
				
				subVitalDataMap = new Map();
				subVitalDataMap.set(rtcData.subjectNo, vpMapobj);
				const obj = Object.fromEntries(subVitalDataMap);
				subjectsJson.svtpData = obj;
			}
		}
	});
}

function clearData() {
	// debugger;
	$("#subjectMsg").html("");
	$("#timepointsTd").html("");
	$("#vitalTestInfo").html("");
	timePointPk = "";
	startTime = "";
	endTime = "";
	subjectBarcode = "";
	subjectScanTime ="";
	crrentVitalIds = [];
	perameterIds = [];
	perameterIdsAndType = [];
	perams = [];
	gvIds = [];
	$("#collectionTr").hide();
	$('#barcodeMsg').html("");
	$('#timepointsTd').html("");
	$('#timePointMsg').html("");
	$('#subjectMsg').html("");
	$('#startButtonMsg').html("");
	$('#endButtonMsg').html("");
	$('#vitalTestInfo').html("");
	$('#tpPositionDiv').html("");
	$('#tpPositionDivMsg').html("");
	$('#posTextDiv').html("");
	treatmentId = "";
	tpSkipDeviation = false;
	skipedTpIds = [];
	timeDeviation = false;
	timeDeviationCode ="";
	timeDeviationId = "0";
}
function clearDetails(){
	$('#timepointsTd').html("");
	$("#subjectMsg").html("");
	$("#vitalTestTable").empty();
	subjectScanTime = "";
	subjectBarcode = "";
}

function barcodeField() {
	$("#barcode").focus();
	$("#barcode").val("");
}
function onBarcodeScanned(){
	var crfName = $('#crfName').val();
	if(crfName != null && crfName != "" && crfName != undefined){
		var barcode = $("#barcode").val();
		$("#barcodeMsg").html("");
		var lastChar = barcode.substr(barcode.length - 1);
		if (lastChar == 'n') {
			$("#barcode").focus();
			$("#barcode").val("");
			var subjectBarcodeStudy = barcode.substr(0,barcode.length - 1).split('a');
			var prefix = barcode.substr(0, 2);
			if (prefix == "02") {
				$('#save_btn').removeAttr("disabled");
				$('#posTextDiv').hide();
				$('#tpPositionDiv').html("");
				$("#vitalTestTable").html("");
				$('#tpPositionDivMsg').html("");
				startTime = "";
				endTime="";
//				debugger;
				if(subjectBarcodeStudy[2] == subjectsJson.ctpDto.study.id) {
					startTime = getServerTime();
					if(subjectsJson.ctpDto.subMap[subjectBarcodeStudy[1]]){
						if(subjectsJson.ctpDto.subMap[subjectBarcodeStudy[1]].subjectDiscontinue != "Yes" && (subjectsJson.ctpDto.subMap[subjectBarcodeStudy[1]].subjectDiscontinue == null || subjectsJson.ctpDto.subMap[subjectBarcodeStudy[1]].subjectDiscontinue.trim() == "" || subjectsJson.ctpDto.subMap[subjectBarcodeStudy[1]].subjectDiscontinue == "No")){
							subjectBarcode = barcode;
							subjectScanTime = getServerEndTimeWithSeconds();
							var periodName = subjectsJson.ctpDto.stdPeriodMap[subjectBarcodeStudy[1]].periodName;
							var subNo = subjectsJson.ctpDto.subMap[subjectBarcodeStudy[1]].reportingId.subjectNo;
							$("#subjectMsg").html(subNo+", Period :"+periodName);
							showTimePoints(subjectBarcodeStudy[1]);
						}else{
							$("#barcodeMsg").html("Subject Droped.");
							clearDetails();
						}
					}else{
						$("#barcodeMsg").html("Subject Not Exists.");
						clearDetails();
					}
				}else{ 
					$("#barcodeMsg").html("Subject Barcode does not belongs To Study :"+subjectsJson.ctpDto.study.projectNo);
					clearDetails();
				}
			}else{
				$('#barcodeMsg').html("Invalid Subject Barcode.");
				clearDetails();
			}
		}
	}else{
		$("#barcodeMsg").html("Select Activity");
	}
}
$("#barcode").on('input', function(e) {
	onBarcodeScanned();
});
var collType = "Pre";
function showTimePoints(subjectNo){
//	debugger;
	var actualSubNo = subjectNo;
	crrentVitalIds = [];
	var htmlStr = '';
	var doseDone = "NotDone";
	collType = "Pre";
	var vitalTimePoints = null;
	var subDose = null;
	var printsubNo = subjectNo;
	periodId = subjectsJson.ctpDto.stdPeriodMap[subjectNo].id;
	//StandBy checking
	var standbyFlag = true;
	var replaceSub;
	if(parseInt(subjectNo))
		standbyFlag = false;
	
	if(standbyFlag){
		var standBySub = subjectsJson.ctpDto.subMap[subjectNo];
		if(standBySub != undefined){
			printsubNo = standBySub.reportingId.subjectNo;
			replaceSub = standBySub.stdSubjectId;
			if(replaceSub != null && replaceSub != undefined){
				subjectNo = replaceSub.subjectNo;
				var treatmentMapLength = Object.entries(subjectsJson.ctpDto.subperiodwiseTreatMap).length;
				if(treatmentMapLength > 0){
					var subTrMap = subjectsJson.ctpDto.subperiodwiseTreatMap[subjectNo];
					if(subTrMap != null && subTrMap != undefined){
						var treatMentArr = subTrMap[periodId];
						if(treatMentArr != null && treatMentArr != undefined && treatMentArr.length > 0)
							treatmentId = treatMentArr[0].id;
						else subjectsJson.ctpDto.minTreatmentId;
					}else subjectsJson.ctpDto.minTreatmentId;
				}else treatmentId = subjectsJson.ctpDto.minTreatmentId;
			}else
				treatmentId = subjectsJson.ctpDto.minTreatmentId;
		}else treatmentId = subjectsJson.ctpDto.minTreatmentId;
	}else{
		var trMapLength = Object.entries(subjectsJson.ctpDto.subperiodwiseTreatMap).length;
		if(trMapLength > 0){
			var subTrMap = subjectsJson.ctpDto.subperiodwiseTreatMap[subjectNo];
			if(subTrMap != null && subTrMap != undefined){
				var treatMentArr = subTrMap[periodId];
				if(treatMentArr != null && treatMentArr != undefined && treatMentArr.length > 0)
					treatmentId = treatMentArr[0].id;
				else treatmentId = subjectsJson.ctpDto.minTreatmentId;
			}else treatmentId = subjectsJson.ctpDto.minTreatmentId;
		}else treatmentId = subjectsJson.ctpDto.minTreatmentId;
	}
//	var treatmentArr = subjectsJson.ctpDto.subperiodwiseTreatMap[subjectNo][periodId];
//	var treatmentId = treatmentArr[0].id;
	debugger;
	var doseLength = Object.entries(subjectsJson.ctpDto.pwdoseMap).length;
	if(doseLength > 0){
		var subjMap = subjectsJson.ctpDto.pwdoseMap[actualSubNo];
		if(subjMap != undefined){
			subDose = subjMap[periodId];
		}
		if(subDose != null && subDose != undefined)
			doseDone = "Done";
	}
	
	var plannedDoseTime = subjectsJson.ctpDto.plannedDoseTime;
	if(plannedDoseTime != null){
		vitalTimePoints = [];
		vitalTimePoints = getCollectedTimePoitns(treatmentId, actualSubNo, periodId, "pre", doseDone, subDose, vitalTimePoints, plannedDoseTime);
		debugger;
		if(standbyFlag){
			if(replaceSub != null && replaceSub != undefined)
				vitalTimePoints = getCollectedTimePoitns(treatmentId, actualSubNo, periodId, "post", doseDone, subDose, vitalTimePoints, plannedDoseTime);	
		}else
			vitalTimePoints = getCollectedTimePoitns(treatmentId, actualSubNo, periodId, "post", doseDone, subDose, vitalTimePoints, plannedDoseTime);
		if(vitalTimePoints.length > 0){
			if(doseDone == "Done"){
//				vitalTimePoints = subjectsJson.postDoseVtpMap[treatmentId];
				collType = "Post";
			}
//			else vitalTimePoints = subjectsJson.preDoseVtpMap[treatmentId];
			var subTreatment = subjectsJson
			if(vitalsTreatmentSpecific == "Yes")
				htmlStr = htmlStr+ 'Treatment Code :'+subjectsJson.ctpDto.treatmentMap[treatmentId].randamizationCode;
			var i=0;
			collectedRecordsArr = [];
			$.each(vitalTimePoints, function(index, val) {
				i++;
				var subvital = null;
//				var svtpMapLength = Object.entries(subjectsJson.svtpData).length;
//				var svtpMapLength = Object.entries(subjectsJson.ctpDto.dosedMap).length;
//				debugger;
				var svtpMapLength = Object.entries(subjectsJson.svtpData).length;
				if(svtpMapLength > 0){
					var subjsvt = subjectsJson.svtpData[actualSubNo];
					if(subjsvt != undefined){
						var periodsvt = subjsvt[periodId];
						if(periodsvt != undefined){
							var trinfsvt = periodsvt[treatmentId];
							if(trinfsvt != undefined){
//								subvital = trinfsvt[val.id];
								var scvtpArr = trinfsvt[val.id];
								if(scvtpArr != undefined){
									if(scvtpArr.length >0){
										for(var f=0; f<scvtpArr.length; f++){
											if(scvtpArr[f].collectedPosition != null && scvtpArr[f].collectedPosition != undefined)
												collectedRecordsArr.push(scvtpArr[f].collectedPosition);
										}
										var actualPosArr = getCurrentTimePointPositions(val.id);
										if(actualPosArr.length > 0){
											if(collectedRecordsArr.length != actualPosArr.length)
												subvital = null;
											else{
												subvital = scvtpArr;
											}
										}
									}
								} 
							}
						}
					}
				}
//					subvital = subjectsJson.svtpData[subjectNo][periodId][treatmentId][val.id];
				debugger;
				if(subvital == null && (subDose != null && subDose != undefined)){
					crrentVitalIds.push(val.id) ;
					if(i==3){
						htmlStr += '<br/>  ';
						i=1;
					}
					var sign = "&nbsp;";
					if(val.sign != null && val.sign != "")
						sign = val.sign;
					htmlStr += '&nbsp;&nbsp;<input type="radio" name="timePoint", id="timepoint_'+val.id+'" value="'+val.id+'" onchange="radiobuttonOnclickValidation('+val.id+')">'+"&nbsp;&nbsp;"+sign+val.tpWithScheduleTime+'&nbsp;';
				}else{
//					debugger;
					if(doseDone == "NotDone"){
						if((subvital ==null || subvital == undefined) && (subDose == null || subDose == undefined) ){
							crrentVitalIds.push(val.id) ;
							if(i==3){
								htmlStr += '<br/> ';	
								i=1;
							}
							var sign = "";
							if(val.sign != null && val.sign != "")
								sign = val.sign;
							htmlStr += '&nbsp;<input type="radio" name="timePoint", id="timepoint_'+val.id+'" value="'+val.id+'" onchange="radiobuttonOnclickValidation('+val.id+')"> '+sign+val.tpWithScheduleTime+'&nbsp;';
						}else{
							var dosedVitalsLength = Object.entries(subjectsJson.svtpData[actualSubNo][periodId][treatmentId]).length;
							var predoseVitalLength = subjectsJson.preDoseVtpMap[treatmentId].length;
							if(dosedVitalsLength == predoseVitalLength){
								var subNo = subjectsJson.ctpDto.subMap[actualSubNo].reportingId.subjectNo;
								$('#barcodeMsg').html("Subject "+subjectNo+" Dosing Not Done For Post Vital Collection.");
							}
								
						}
					}else{
						var dosedVitalsLength = Object.entries(subjectsJson.svtpData[actualSubNo][periodId][treatmentId]).length;
						var predoseVitalLength = subjectsJson.preDoseVtpMap[treatmentId].length;
						var postDoseVitalLength = subjectsJson.postDoseVtpMap[treatmentId].length;
						if(dosedVitalsLength == parseInt(postDoseVitalLength)+parseInt(predoseVitalLength)){
							var subNo = subjectsJson.ctpDto.subMap[actualSubNo].reportingId.subjectNo;
							$('#barcodeMsg').html("Subject "+subNo+" Vital Collection Done.");
						}
							
					}
						
				}
			});
			$('#timepointsTd').html(htmlStr);
		}else{
			$('#barcodeMsg').html("There are no scheduled timepoints found for the subject: " + printsubNo);
		}
	}else{
		// MSG -> Study Planned dosing time no available
		$('#barcodeMsg').html("Planned dose time is not available. Please contact project in-charge or PI");
	}
	
	
}
function getCurrentTimePointPositions(vitalTpId){
	debugger
	var posArr = [];
	var vitalPojo = subjectsJson.vitalMap[vitalTpId];
	if(vitalPojo != undefined){
		if(vitalPojo.orthostaticPosition != null && vitalPojo.orthostaticPosition != undefined){
			posArr.push(vitalPojo.orthostaticPosition.fieldValue);
		}
		if(vitalPojo.vitalPosition != null && vitalPojo.vitalPosition != undefined){
			if(posArr.indexOf(vitalPojo.vitalPosition.fieldValue) == -1)
				posArr.push(vitalPojo.vitalPosition.fieldValue);
		}
	}
	return posArr;
}
function radiobuttonOnclickValidation(vitalTimePoint) {
	debugger;
	//startTime = getServerTime();
	positionArr = [];
	positionVal = "";
	$('#tpPositionDiv').html("");
	$('#tpPositionDivMsg').html("");
	$('#save_btn').removeAttr("disabled");
	
	positionArr = getCurrentTimePointPositions(vitalTimePoint);
	if(positionArr.length > 1){
		$('#posTextDiv').show();
		var htmlStr = '';
		for(var s=0; s<positionArr.length; s++){
			htmlStr += '<input type="radio" name="tpPosition" id="tppostion_'+positionArr[s]+'" onchange="checkTpPositionCondition()">'+positionArr[s];
		}	   
		$('#tpPositionDiv').append(htmlStr);
		checkTpPositionCondition();
	}else{
		$('#posTextDiv').hide();
		$('#tpPositionDiv').html("");
		$('#tpPositionDivMsg').html("");
			checkTpPositionCondition();
	}
}
function checkTpPositionCondition(){
	debugger;
	$('#tpPositionDivMsg').html("");
	var flag = false;
	if(positionArr.length > 1){
		for(var t=0; t<positionArr.length; t++){
			if ($("#tppostion_"+positionArr[t]).prop("checked")){
				flag = true;
				positionVal = positionArr[t];
				var ccrFlag = checkingCollectionRecord(collectedRecordsArr, positionVal);
				if(ccrFlag)
					showSelectedTpDetails();
				else $('#barcodeMsg').html(positionVal +" Position Vitals Collection Done For Current Time Point.");
			}
		}
	}else{
		flag = true;
		positionVal = positionArr[0];
		var ccrFlag = checkingCollectionRecord(collectedRecordsArr, positionVal);
		if(ccrFlag)
			showSelectedTpDetails();
		else $('#barcodeMsg').html(positionVal +" Position Vitals Collection Done For Current Time Point.");
	}
	if(flag == false)
		$('#tpPositionDivMsg').html("Select Position");
}
function checkingCollectionRecord(vitalDataRecordArr, position){
	var flag = true;
	$('#barcodeMsg').html("");
	if(vitalDataRecordArr.length > 0){
		for(var i=0; i<vitalDataRecordArr.length; i++){
			var colPosition = vitalDataRecordArr[i];
			if(colPosition == positionVal)
				flag = false;
		}
	}else flag = true;
	return flag;
}

function showSelectedTpDetails(){
	for(var j=0; j<crrentVitalIds.length; j++){
		if ($("#timepoint_"+crrentVitalIds[j]).prop("checked")){
			var subjectBarcodeSplit = subjectBarcode.substr(0,subjectBarcode.length - 1).split('a');
			periodId = subjectsJson.ctpDto.stdPeriodMap[subjectBarcodeSplit[1]].id;
//			var treatmentArr = subjectsJson.ctpDto.subperiodwiseTreatMap[subjectBarcodeSplit[1]][periodId];
			//StandBy checking
			var subjectNo = subjectBarcodeSplit[1];
			var standbyFlag = true;
			if(parseInt(subjectNo))
				standbyFlag = false;
			if(standbyFlag){
				var standBySub = subjectsJson.ctpDto.subMap[subjectNo];
				if(standBySub != undefined){
					var replaceSub = standBySub.stdSubjectId;
					if(replaceSub != null && replaceSub != undefined){
						subjectNo = replaceSub.subjectNo;
						var subTrlength  = Object.keys(subjectsJson.ctpDto.subperiodwiseTreatMap).length;
						if(subTrlength != null && subTrlength != undefined){
							var subtrmap = subjectsJson.ctpDto.subperiodwiseTreatMap[subjectNo];
							if(subtrmap != null && subtrmap != undefined){
								var treatMentArr = subtrmap[periodId];
								if(treatMentArr != null && treatMentArr != undefined && treatMentArr.length > 0)
									treatmentId = treatMentArr[0].id;
								else treatmentId = subjectsJson.ctpDto.minTreatmentId;
							}else treatmentId = subjectsJson.ctpDto.minTreatmentId;
								
						}else treatmentId = subjectsJson.ctpDto.minTreatmentId;
					}else
						treatmentId = subjectsJson.ctpDto.minTreatmentId;
				}else treatmentId = subjectsJson.ctpDto.minTreatmentId;
			}else{
				var subTrMapLength  = Object.keys(subjectsJson.ctpDto.subperiodwiseTreatMap).length;
				if(subTrMapLength > 0){
					var subtrMap = subjectsJson.ctpDto.subperiodwiseTreatMap[subjectNo];
					if(subtrMap != null && subtrMap != undefined){
						var treatMentArr = subtrMap[periodId];
						if(treatMentArr != null && treatMentArr != undefined && treatMentArr.length > 0)
							treatmentId = treatMentArr[0].id;
						else subjectsJson.ctpDto.minTreatmentId;
					}else subjectsJson.ctpDto.minTreatmentId;
				}else subjectsJson.ctpDto.minTreatmentId;
			}
//			treatmentId = treatmentArr[0].id;
			var timPointId = $("#timepoint_"+crrentVitalIds[j]).val();
			timePointPk = timPointId;
			var parametersLength = Object.entries(subjectsJson.parameterMap).length;
//			var startButton = "<input type=\"button\" id = \"startButton\" value=\"Start\" style=\"font-size:small;\" class=\"btn btn-warning btn-sm\" onclick=\"currentTime('startButton')\"/>";
//			var endButton = "<input type=\"button\" id = \"endButton\" value=\"End\" style=\"font-size:small;\" class=\"btn btn-warning btn-sm\" onclick=\"currentTime('endButton')\"/>";
			var vitalTestInfo = "<div id='vitalTestTable'><div class='row'><div class='col-sm-12' style='text-align:center; font-weight:bold;'>Parameters</div></div></div>";
			$("#vitalTestInfo").html(vitalTestInfo);
			if(parametersLength > 0){
				var perameters = subjectsJson.parameterMap[timPointId];
				if (perameters.gpdDtoList.length > 0) {
					// console.log(data.perameters.gpdDtoList.length);
					for (var groupDtoIndex = 0; groupDtoIndex < perameters.gpdDtoList.length; groupDtoIndex++) {
						var value = perameters.gpdDtoList[groupDtoIndex];
						var paramVals = value.parameterDto;
						if(paramVals != null && paramVals != "undefined"){
							mandatoryParamsArr = [];
							$.each(paramVals,function(index, peramObject) {
								// debugger;
								perams[peramObject.parameterId] = peramObject.controlType.contentCode;
								var parmMandatory = peramObject.mandatory;
								if(parmMandatory)
									mandatoryParamsArr.push(peramObject.parameterId);
								/*console.log(peramObject.parameterId+ "   "+ perams[peramObject.parameterId]);*/
								var element = "<div class='row'><div class='col-sm-6'>"+ peramObject.parameterName+ "</div>";
								var controlCode = peramObject.controlType.contentCode;
								if (peramObject.controlType.contentCode == 'TB') {
									element += "<div class='col-sm-6'><input name=\""
											+ peramObject.parameterId
											+ "\" id=\""
											+ peramObject.parameterId
											+ "\" "
											+"onblur=\"parametersValidation('" + peramObject.parameterId + "', '" + controlCode + "')\" "
											+"class=\"form-control\" ><div style='color:red' id='"+peramObject.parameterId+"_Msg'></div></div>"
								}
								if (peramObject.controlType.contentCode == 'TA') {
									element += "<div class='col-sm-6'><textarea name=\""
											+ peramObject.parameterId
											+ "\" id=\""
											+ peramObject.parameterId
											+ "\" "
											+"onblur=\"parametersValidation('" + peramObject.parameterId + "', '" + controlCode + "')\" "
											+ "class=\"form-control\"></textarea>"
											+"<div style='color:red' id='"+peramObject.parameterId+"_Msg'></div></div>";
											
								} else if (peramObject.controlType.contentCode == 'RB') {
									element +="<div class='col-sm-6'>";
									for (var index = 0; index < peramObject.controlType.valuesDto.length; index++) {
										var gvVal = gvIds[peramObject.parameterId];
										if(gvVal != null && gvVal != "" && gvVal != "undefined")
											gvIds[peramObject.parameterId] = gvVal+","+peramObject.controlType.valuesDto[index].valueId;
										else gvIds[peramObject.parameterId] = peramObject.controlType.valuesDto[index].valueId;
										element += "<input type=\"radio\" name=\""
												+ peramObject.parameterId
												+ "\" id=\""
												+ peramObject.controlType.valuesDto[index].valueId
												+ "\" "
												+ " value=\""
												+ peramObject.controlType.valuesDto[index].valueId
												+ "\" "
												+"onchange=\"parametersValidation('" + peramObject.parameterId + "', '" + controlCode + "')\" "
												+ "\" />"
												+ peramObject.controlType.valuesDto[index].valueName
												+ "&nbsp;&nbsp;&nbsp;";
									}
									element += "<div style='color:red' id='"+peramObject.parameterId+"_Msg'></div></div>";
								} else if (peramObject.controlType.contentCode == 'CB') {
									element +="<div class='col-sm-6'>";
									for (var index = 0; index < peramObject.controlType.valuesDto.length; index++) {
										var gvVal = gvIds[peramObject.parameterId];
										if(gvVal != null && gvVal != "" && gvVal != "undefined")
											gvIds[peramObject.parameterId] = gvVal+","+peramObject.controlType.valuesDto[index].valueId;
										else gvIds[peramObject.parameterId] = peramObject.controlType.valuesDto[index].valueId;
										
										element += "<input type=\"checkbox\" name=\""
												+ peramObject.parameterId
												+ "\" id=\""
												+ peramObject.controlType.valuesDto[index].valueId
												+ "\" "
												+ " value=\""
												+ peramObject.controlType.valuesDto[index].valueId
												+ "\" "
												+"onchange=\"parametersValidation('" + peramObject.parameterId + "', '" + controlCode + "')\" "
												+ "\" />"
												+ peramObject.controlType.valuesDto[index].valueName
												+ "&nbsp;&nbsp;&nbsp;";
									}
									element += "<div style='color:red' id='"+peramObject.parameterId+"_Msg'></div></div>";
								} else if (peramObject.controlType.contentCode == 'SB') {
									gvIds[peramObject.parameterId] = peramObject.controlType.valuesDto[index].valueId;
									element += "<div class='col-sm-6'><select  name=\""
											+ peramObject.parameterId
											+ "\" id=\""
											+ peramObject.parameterId
											+ "\" "
											+"onchange=\"parametersValidation('" + peramObject.parameterId + "', '" + controlCode + "')\" "
											+ "\" />"
									element += "<option value=\"-1\">--Select--</option>";
									for (var index = 0; index < peramObject.controlType.valuesDto.length; index++) {
										element += "<option value=\""
												+ peramObject.controlType.valuesDto[index].valueId
												+ ">"
												+ peramObject.controlType.valuesDto[index].valueName
												+ "</option>";
									}
									element += "</select><div style='color:red' id='"+peramObject.parameterId+"_Msg'></div></div>";
								} else if (peramObject.controlType.contentCode == 'DP') {
									element += "<div class='col-sm-6'><input type=\"text\" name=\""
											+ peramObject.parameterId
											+ "\" id=\""
											+ peramObject.parameterId
											+"onchange=\"parametersValidation('" + peramObject.parameterId + "', '" + controlCode + "')\" "
											+ "\" /><div style='color:red' id='"+peramObject.parameterId+"_Msg'></div></div>";
								} else if (peramObject.controlType.contentCode == 'TP') {
									element += "<div class='col-sm-6'><input type=\"text\" name=\""
											+ peramObject.parameterId
											+ "\" id=\""
											+ peramObject.parameterId
											+ "\" "
											+"onblur=\"parametersValidation('" + peramObject.parameterId + "', '" + controlCode + "')\" >"
											+ "\" /><div style='color:red' id='"+peramObject.parameterId+"_Msg'></div></div>";
								}
								elementFlag = true;
								perameterIds[peramObject.parameterId] = peramObject.parameterId
								perameterIdsAndType[peramObject.parameterId] = peramObject.controlType.contentCode;
								$("#vitalTestTable").append(element);
							});
						}
					}
				}
				/*var btnStr = "<th>Start Time :</th><td id=\"startButtontd\">"
							+ startButton+ "<font color=\"red\" id=\"startButtonMsg\"></font></td>";
				btnStr += "<th>End Time :</th><td id=\"endButtontd\">"+ endButton
							+ "<font color=\"red\" id=\"endButtonMsg\"></font></td>";
				$("#vitalTestTable").append(btnStr);*/
			}else{
//				$("#vitalTestInfo").html(vitalTestInfo);
				$("#vitalTestTable").append("<div class='row'><div class='col-sm-6'>No Data Avaliable</div></div>");
				/*var btnStr = "<th>Start Time :</th><td id=\"startButtontd\">"
					+ startButton+ "<font color=\"red\" id=\"startButtonMsg\"></font></td>";
				btnStr += "<th>End Time :</th><td id=\"endButtontd\">"+ endButton
					+ "<font color=\"red\" id=\"endButtonMsg\"></font></td>";
				$("#vitalTestTable").append(btnStr);*/
			}
		}
	}
	currentTime('startButton');
}

function currentTime(id) {
	var flag = false;
// debugger;
	 var subBarcodeSplit = subjectBarcode.substr(0,subjectBarcode.length - 1).split('a');
	if (id == 'startButton') {
		var selectedTimePointId = $("input[name='timePoint']:checked").val();
		var stTime = getServerTime();
		var currentDate = removeTime(new Date(getServerDate()));
		var sysdate = currentDate + ' ' + stTime;
		
		var vitalOrderIds = [];
		
		//Pre dose Timepoints
		//timepoints Order for Deviation
		var crrentvtpIdsOrder = [];
		$.each(crrentVitalIds, function( index, value ) {
			if(collType == "Pre")
				crrentvtpIdsOrder.push(subjectsJson.previtalOrderMap[value]);
			else 
				crrentvtpIdsOrder.push(subjectsJson.postvitalOrderMap[value]);
		});
		crrentvtpIdsOrder.sort();
		if(crrentvtpIdsOrder.length > 0){
			var tporderNo = "";
			if(collType == "Pre")
				tporderNo = crrentvtpIdsOrder.indexOf(subjectsJson.previtalOrderMap[timePointPk]);
			if(tporderNo == 0 || tporderNo == "0"){
				//continue printing
				flag = true;
			}else{
				var pendingTpArr = [];
				for(i=parseInt(tporderNo); i<=0; i--){
//				for(i=parseInt(tporderNo); i>=0; i--){
					var vcIdOrder = crrentvtpIdsOrder[i];
					var vcId = null;
					var vctpPojo = null;
					if(collType == "Pre")
						vcId = subjectsJson.preorderVitalIdsMap[vcIdOrder];
					if(vcId != null)
						vctpPojo = subjectsJson.vitalMap[vcId];
					if(vcId != subjectsJson.vitalMap[timePointPk].id){
						var vccpData = null;
						var vcdLengthVal = Object.keys(subjectsJson.svtpData).length;
						if(vcdLengthVal > 0){
							var vccSub = subjectsJson.svtpData[subBarcodeSplit[1]];
							if(vccSub != undefined){
								var vccSubperiod =  vccSub[periodId];
								if(vccSubperiod != undefined){
									var vccTrinf = vccSubperiod[treatmentId];
									if(vccTrinf != undefined){
										vccpData = vccTrinf[vctpPojo.id];
									}
								}
							}
						}
//						  vccpData = subjectsJson.svtpData[subBarcodeSplit[1]][periodId][treatmentId][vctpPojo.id];
						if(vccpData == null || vccpData == undefined){
							pendingTpArr.push(vctpPojo.sign+vctpPojo.timePoint);
							tpSkipDeviation = true;
							skipedTpIds.push(vctpPojo.id);
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
					      textMsg = "Vitals Not Collected For Timepoints :"+pendingTpArr+".";
					else 
						textMsg = "Vital Not Collected For Timepoint :"+pendingTpArr+".";
					
					 Swal.fire({
						  title: 'Are you sure?',
						  text:textMsg,
						  showDenyButton: false,
						  showCancelButton: true,
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
							  flag = true;
						  } else if (result.isDenied) {
						    Swal.fire('Changes are not saved', '', 'info');
						    flag = false;
						  }
						})
					flag = true;
				}else{
					//continue without deviation
					flag = true;
				}
			}
		}
		
		//Time deviation
		var dosedLength = Object.keys(subjectsJson.ctpDto.dosedMap).length;
		timeDeviation = false;
		var devStr = "";
		var vitalPlanOrdoseTime = subjectsJson.ptdDto.plannedDate;
		if(dosedLength > 0){
			var dosePojo =  null;
			if(vitalsTreatmentSpecific == "Yes"){
				var subdoseMap = subjectsJson.ctpDto.dosedMap[subBarcodeSplit[1]];
				if(subdoseMap != undefined){
					var periodDoseMap = subdoseMap[periodId];
					if(periodDoseMap != undefined){
						dosePojo = periodDoseMap[treatmentId];
						if(dosePojo != null && dosePojo != undefined)
							vitalPlanOrdoseTime = dosePojo.actualTime;
					}
				}
				
			}else{
				var subdoseMap = subjectsJson.ctpDto.dosedWithoutTreatmentMap[subBarcodeSplit[1]];
				if(subdoseMap != undefined){
					dosePojo = subdoseMap[periodId];
					if(dosePojo != null && dosePojo != undefined)
						vitalPlanOrdoseTime = dosePojo.actualTime;
				}
			}
			if(vitalPlanOrdoseTime != null && vitalPlanOrdoseTime != "" && vitalPlanOrdoseTime != undefined){
				var vctp = subjectsJson.vitalMap[timePointPk];
				devStr = calculateDeviation(vitalPlanOrdoseTime, vctp.sign+vctp.timePoint,vctp.windowPeriod, vctp.windowPeriodSign , vctp.windowPeriodType.code);
			}
		}
		if(devStr != ""){
			timeDeviation = true;
			timeDeviationTime = devStr;
		}
		if(timeDeviation){
			if(tpSkipDeviation){
				if(subjectsJson.ctpDto.dsDto != null && subjectsJson.ctpDto.dsDto != undefined){
					if(subjectsJson.ctpDto.dsDto.devionCodeMap != null && subjectsJson.ctpDto.dsDto.devionCodeMap != undefined){
						timeDeviationId = subjectsJson.ctpDto.dsDto.devionCodeMap['TPSKIPANDTP'];
					}
				}
			}else{
				if(subjectsJson.ctpDto.dsDto != null && subjectsJson.ctpDto.dsDto != undefined){
					if(subjectsJson.ctpDto.dsDto.devionCodeMap != null && subjectsJson.ctpDto.dsDto.devionCodeMap != undefined){
						timeDeviationId = subjectsJson.ctpDto.dsDto.devionCodeMap['TPD'];
					}
				}
			}
			Swal.fire({
			  title: '',
			  confirmButtonText: "Yes",
			  text:'Time Deviation Happened : '+timeDeviationTime
			});
		}else{
			if(tpSkipDeviation){
				if(subjectsJson.ctpDto.dsDto != null && subjectsJson.ctpDto.dsDto != undefined){
					if(subjectsJson.ctpDto.dsDto.devionCodeMap != null && subjectsJson.ctpDto.dsDto.devionCodeMap != undefined){
						timeDeviationId = subjectsJson.ctpDto.dsDto.devionCodeMap['TPSKIP'];
					}
				}
			}
		}
	}
	if (id == 'endButton') {
		$("#startButtonMsg").html("");
		if (startTime != '') {
			endTime = getServerTime();
			flag = true;
		} else {
			$("#startButtonMsg").html("Start Time Required");
		}

	}else{
		endTime = getServerTime(); 
		flag = true;
	}
	if (flag){
		$("#"+ id + "td").html("<input type=\"text\" class=\"form-control\" value=\""+ endTime+ "\""
				+ " size=\"8\" readonly=\"readonly\"  maxlength=\"8\"/>");
		/*$("#"+ id + "td").html("<input type=\"text\" value=\""+ runningTimeWithSeconds+ "\""
				+ " size=\"8\" readonly=\"readonly\"  maxlength=\"8\"/>"
				+ "<input type=\"button\" style=\"font-size:small;\" class=\"btn btn-primary brn-sm\" value=\"Reset\" onclick=\"resetCollectionTime('"
				+ id + "')\" >");*/
	}
		
}
function resetCollectionTime(id) {
	if (id == 'startButton') {
		$("#startButtonMsg").html("");
		startTime = "";
		$("#"+id+"td")
				.html(
						"<input type=\"button\" id = \"startButton\" value=\"Start\" class=\"form-control\"  style=\"font-size:small;\" class=\"btn btn-warning brn-sm\"  onclick=\"currentTime('startButton')\"/>");
	}
	if (id == 'endButton') {
		$("#endButtonMsg").html("");
		endTime = "";
		$("#" + id+"td")
				.html(
						"<input type=\"button\" id = \"endButton\" class=\"form-control\"  style=\"font-size:small;\" class=\"btn btn-warning brn-sm\"  value=\"End\" onclick=\"currentTime('endButton')\"/>");
	}

}
function saveVitals() {
//	debugger;
	currentTime('endButton');
	if (checkValidations()) {
		 debugger;
		$('#save_btn').attr("disabled", true);
		perameterIds = [];
		$.each(perameterIdsAndType, function(id, type) {
			perameterIds.push(id);
		});
		var vitalCollectionDate = [];
		$.ajax({
			url : $("#mainUrl").val() + '/administration/getCsrfToken',
			type : 'GET',
			success : function(data) {
				vitalCollectionDate.push({
					name:"positionType",
					value:positionVal
				});
				vitalCollectionDate.push({
					name : data.parameterName,
					value : data.token
				});
				vitalCollectionDate.push({
					name : "studyId",
					value : $("#studyName").val()
				});
				vitalCollectionDate.push({
					name : "subject",
					value : subjectBarcode
				});
				vitalCollectionDate.push({
					name : "subjectTime",
					value : subjectScanTime
				});
				vitalCollectionDate.push({
					name : "perodId",
					value : periodId
				});
				vitalCollectionDate.push({
					name : "timePointPk",
					value : timePointPk
				});
				vitalCollectionDate.push({
					name : "timeDeviation",
					value : timeDeviation
				});
				vitalCollectionDate.push({
					name : "timeDeviationTime",
					value : timeDeviationTime
				});
				vitalCollectionDate.push({
					name : "timeDeviationCode",
					value : timeDeviationCode
				});
				vitalCollectionDate.push({
					name : "timeDeviationId",
					value : timeDeviationId
				});
				
				vitalCollectionDate.push({
					name : "tpSkipDeviation",
					value : tpSkipDeviation
				});
				vitalCollectionDate.push({
					name : "startTime",
					value : startTime
				});
				vitalCollectionDate.push({
					name : "endTime",
					value : endTime
				});
				vitalCollectionDate.push({
					name : "perameterIds",
					value : perameterIds
				});
				var perameterValues = [];
				if(perams.length > 0){
					perams.forEach(function callback(value, index) {
						var type = value;
						if(type == "RB" || type == "CB" || type == "SB"){
							if(type == "RB" || type == "CB"){
								gvIds.forEach(function callback(v, ind) {
									var gv = v;
									var gVal = v.split(',');
									if(gVal.length > 0){
										Object.values(gVal).forEach(val => {
											if($("#"+val).is(":checked")){
												perameterValues.push(ind+"@@@"+$('#'+val).val());
											}
										});
									}
								});
							}else{
								if($('#'+index).val() != null && $('#'+index).val() != "" && $('#'+index).val() != undefined)
									perameterValues.push(index+"@@@"+$('#'+index).val());
							}
						}else{
							if($('#'+index).val() != null && $('#'+index).val() != "" && $('#'+index).val() != undefined)
								perameterValues.push(index+"@@@"+$('#'+index).val());
						}
					});
				}
			 	if(perameterValues.length > 0){
					vitalCollectionDate.push({
						name : "perameterValue",
						value : perameterValues
					});
				}else{
					vitalCollectionDate.push({
						name : "perameterValue",
						value : 0
					});
				}
			 	// debugger;
				$.ajax({
					url : $("#mainUrl").val()
							+ '/study/clinical/vitalCollectionSave',
					type : 'POST',
					data : vitalCollectionDate,
					success : function(e) {
						// debugger;
						$('#save_btn').removeAttr("disabled");
						console.log(e.success)
						if (e.msgFlag === true){ 
							displayMessage("success", e.mealsMsg);
							$('#timepointsTd').html("");
						}
						else 
							displayMessage("error", e.mealsMsg);
						
//							subjectsJson = synchronousAjaxCall(mainUrl+ "/study/clinical/vitalCollectionDetails/" + $("#studyName").val());
						clearData();
//							clearDataActivity();
//						$('#crfName').val("");
//						$("#activityDiv").empty();
					},
					error : function(er) {
//							debugger;
						$('#save_btn').removeAttr("disabled");
						clearData();
//						$('#crfName').val("");
//						$("#activityDiv").empty();
					}
				});
				
			},
			error : function(er) {
//				debugger;
				$('#save_btn').removeAttr("disabled");
//				$('#crfName').val("");
//				$("#activityDiv").empty();
			}
		});
	}
}
function clearDataActivity(){
//	$("#crfName").val("");
}
function parametersValidation(pid, type){
	debugger;
	if(type == "TB" || type == "TA" || type == "TP"){
		if ($("#" + pid).val() != '') 
			$('#'+pid+"_Msg").html("");
	}else if(type == "CB" || type == "RB" || type == "SB" || type == "DP"){
		if(type == "RB"){
			if ($("input:radio[name='" + pid + "']").is(":checked"))
				$('#'+pid+"_Msg").html("");
		}else if(type == "CB"){
			if (!$("input:checkbox[name='" + pid + "']").is(":checked"))
				$('#'+pid+"_Msg").html("");
		}else{
			if ($("#" + pid).val() != '-1')
				$('#'+pid+"_Msg").html("");
		}
		
	}
}
function checkValidations() {
//	debugger;
	$("#startButtonMsg").html("");
	$("#endButtonMsg").html("");
	var flag = true;
	if (subjectBarcode == null || subjectBarcode == "" || subjectBarcode == undefined ) {
		$("#subjectMsg").html("Subject Barocode Required");
		flag = false;
	}
	if (timePointPk == null || timePointPk == "" || timePointPk == undefined ) {
		$("#timePointMsg").html("Select Subject Timepoint");
		flag = false;
	}
	if (startTime == null || startTime == "" || startTime == undefined ) {
		$("#startButtonMsg").html("Start Time Required.");
		flag = false;
	}
	if (endTime == null || endTime == "" || endTime == undefined ) {
		$("#endButtonMsg").html("End Time Required.");
		flag = false;
	}
	
	if (flag) {
		$.each(perameterIds, function(k, peramId) {
			var peramflag = true;
			$("#" + peramId + "msg").html("");
			if (perameterIdsAndType[peramId] == 'RB'
					|| perameterIdsAndType[peramId] == 'CB'
					|| perameterIdsAndType[peramId] == 'CB') {
				if (perameterIdsAndType[peramId] != 'SB') {
					if(perameterIdsAndType[peramId] == 'RB'){
						if (!$("input:radio[name='" + peramId + "']")
								.is(":checked")) {
							if(mandatoryParamsArr.indexOf(peramId) != -1){
								$('#'+peramId+"_Msg").html("Required Field.");
								peramflag = false;
							}
						}						
					}else if(perameterIdsAndType[peramId] == 'CB'){
						if (!$("input:checkbox[name='" + peramId + "']")
								.is(":checked")) {
							if(mandatoryParamsArr.indexOf(peramId) != -1){
								$('#'+peramId+"_Msg").html("Required Field.");
								peramflag = false;
							}
						}	
					}

				} else if ($("#" + peramId).val() == '-1') {
					if(mandatoryParamsArr.indexOf(peramId) != -1){
						$('#'+peramId+"_Msg").html("Required Field.");
						peramflag = false;
					}
				}
			} else if ($("#" + peramId).val() == '') {
				if(mandatoryParamsArr.indexOf(peramId) != -1){
					$('#'+peramId+"_Msg").html("Required Field.");
					peramflag = false;
				}
			}
			if (!peramflag) 
				flag = false;
		});
	}
	return flag;
}

function getCollectedTimePoitns(treatmentId, actualSubNo, periodId, type, doseDone, subDose, nonCollectedTimePoitns, plannedDoseTime){
//	debugger;
	var allTimePoints = null;
	if(type == 'post'){
		if(doseDone == 'Done')
			allTimePoints = subjectsJson.postDoseVtpMap[treatmentId];
	}else
		allTimePoints = subjectsJson.preDoseVtpMap[treatmentId];
	$.each(allTimePoints, function(index, val) {
		var timepointFalg = true;
		var svtpMapLength = Object.entries(subjectsJson.svtpData).length;
		if(svtpMapLength > 0){
			var subjsvt = subjectsJson.svtpData[actualSubNo];
			if(subjsvt != undefined){
				var periodsvt = subjsvt[periodId];
				if(periodsvt != undefined){
					var trinfsvt = periodsvt[treatmentId];
					if(trinfsvt != undefined){
						var collectedPositonArr = [];
						var scvtpArr = trinfsvt[val.id];
						if(scvtpArr != undefined){
							if(scvtpArr.length > 0){
								for(var f=0; f<scvtpArr.length; f++){
									if(scvtpArr[f].collectedPosition != null && scvtpArr[f].collectedPosition != undefined)
										collectedPositonArr.push(scvtpArr[f].collectedPosition);
								}
								var actualPosArr = getCurrentTimePointPositions(val.id);
								if(actualPosArr.length > 0){
									if(collectedPositonArr.length == actualPosArr.length)
										timepointFalg = false;
								}
							}
						}
					}
				}
				
			}
		}
		if(timepointFalg){
			var doseTime = "";
//			if(subDose != undefined && subDose[1]){
//				console.log(subDose[1].actualTime);
//				doseTime = subDose[1].actualTime;
//			}
			if(subDose != undefined && subDose[treatmentId]){
				console.log(subDose[treatmentId].actualTime);
				doseTime = subDose[treatmentId].actualTime;
			}else doseTime = subjectsJson.ptdDto.plannedDate;
//			var dev = calculateDeviation(doseTime, val.timePoint, val.windowPeriod, val.windowPeriodSign , val.windowPeriodType.code, type, plannedDoseTime);
			var dev = vitalTimePointScheduleTimeCalculatesForViewTps(doseTime, val.timePoint, val.windowPeriod, val.windowPeriodSign , val.windowPeriodType.code, type, periodId);
//			
//			debugger;
			if(dev >= 0){
//				debugger;
				//For Schedule time display with time point
				var doseOrPlanTime = null;
				if(doseDone == 'Done')
					doseOrPlanTime = subjectsJson.ptdDto.subjectDoseMap[actualSubNo][periodId];
				else doseOrPlanTime = subjectsJson.ptdDto.plannedDate;
				var scheduleTime = calculateScheduleTimeToTimePoint(doseOrPlanTime, val.timePoint, val.sign);
				if(scheduleTime != null && scheduleTime != "" && scheduleTime != undefined)
					val.tpWithScheduleTime = val.timePoint+"("+scheduleTime+")";
				else val.tpWithScheduleTime = val.timePoint;
				nonCollectedTimePoitns.push(val);
				console.log("ID:" + val.id + ",Type:" + type + "," + val.sign+""+val.timePoint+" "+val.tpWithScheduleTime)
			}else{
				val.tpWithScheduleTime = val.timePoint;
				nonCollectedTimePoitns.push(val);
			}
									
		}
	});	
	
	return nonCollectedTimePoitns;
}
function calculateScheduleTimeToTimePoint(podDate, tpStr, sign){
//	debugger;
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
function vitalTimePointScheduleTimeCalculatesForViewTps(doseTime, timePoint, window, sign , windowTimeType, timPointType, periodid){
	window = Number(window);
	var currentDate = removeTime(new Date(getServerDate()));
	var currentTime = removeTime(new Date(getServerDate()));
	var runningTime = $("#servertime").html();
	if(runningTime != null && runningTime != "" && runningTime != undefined){
		var timeArr = runningTime.split(":");
		if(timeArr.length > 0){
			currentTime.setHours(timeArr[0]);
			currentTime.setMinutes(timeArr[1]);
			currentTime.setSeconds(timeArr[2]);
		}
	}
	var timePointArray = timePoint.split(".");
	var totalMin = (Number(timePointArray[0]) * 60) + (Number("0."+timePointArray[1]) * 60);
	var doseDate = "";
//	debugger;
	var planedTime = null;
	var periodName = subjectsJson.ptdDto.priodNamesMap[periodid];
	if(periodName == "P1")
		planedTime = subjectsJson.ptdDto.plannedDate;
	else planedTime = getPlanedTimeForOtherPeriods(periodid);
	if(planedTime != null && planedTime != undefined){
		if(timPointType == 'pre'){
			doseDate = new Date(planedTime);
		}else{
			doseDate = new Date(doseTime);
		}
		
		if(timPointType == 'pre')
			doseDate = new Date(doseDate.getTime() - (totalMin * 60000)); 
		else
			doseDate = new Date(doseDate.getTime() + (totalMin * 60000)); 
		if(windowTimeType != 'MINUTES'){
			if(windowTimeType == 'HOURS')
				window = window * 60;
			else
				window = window * 60 * 24;
		}
		var start;
	    if(sign == 'PLUS')
	        start = new Date(doseDate.getTime());
	    else 
	        start = new Date(doseDate.getTime() - (window * 60000));  

		return minutesDiff(currentTime, start);
	}else return -1;
}
function getPlanedTimeForOtherPeriods(periodno){
	debugger;
	var reportingDate = null;
	var periodOneId = subjectsJson.ptdDto.priodIdsMap['P1'];
	var firstPeriodReportingDate = subjectsJson.ptdDto.repotingDateMap[periodOneId];
    var doseDate = null;
	var periodName = subjectsJson.ptdDto.priodNamesMap[periodno];
	if(periodno != null && periodno != undefined){
		reportingDate = subjectsJson.ptdDto.repotingDateMap[periodno];
		if(reportingDate != null && reportingDate != undefined){
			var firstPdriodDoseDate = subjectsJson.ptdDto.periodWiseDoseMap[periodOneId];
			if(firstPeriodReportingDate != null && firstPeriodReportingDate != undefined){
				var diffDays = getNumberOfDaysDifferece(new Date(firstPdriodDoseDate), new Date(firstPeriodReportingDate));
				var minutes = diffDays * 24 * 60;
				var ptime = subjectsJson.ptdDto.plannedDate;
				if(reportingDate != null && reportingDate != undefined){
					var periodNo = subjectsJson.ptdDto.periodNoIdsMap[periodno];
					var periodDoseDate = null;
					if(periodNo != 1){
						var prevDosePeriodId = subjectsJson.ptdDto.periodNoMap[periodNo-1]
						periodDoseDate = subjectsJson.ptdDto.periodWiseDoseMap[prevDosePeriodId];
					}else periodDoseDate = firstPeriodReportingDate;
					if(periodDoseDate != null){
						periodDoseDate = new Date(periodDoseDate);
						var min =0;
						var washoutDays = subjectsJson.ptdDto.washoutDays;
						if(washoutDays != null && washoutDays != undefined)
							min = min + (washoutDays * 24 * 60);
						periodDoseDate = new Date(periodDoseDate.getTime() + min*60000);
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
	return doseDate;
		
}
function getNumberOfDaysDifferece(date2, date1){
	/*const dt2 = new Date(date2);
	const dt1 = new Date(date1);*/
	var sec_num = date2.getTime() - date1.getTime();
    /*var daysStr = (sec_num / (1000 * 3600 * 24)).toString();*/
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