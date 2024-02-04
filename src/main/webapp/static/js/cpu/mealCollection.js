$(".loader").show();
var subjectBarocodes = {};
var replacedSubjects = {};
var droppedSubjects = [];
var subjectPerods = {};
var subjectDoseTimes = {};
var periods = {};
var timPoints = {};
var collectedData = {};
var subjectPeriodTimePointCollectedData = {};
var timePointCollectedData = {};
var stTime = "";
var mealsTreatmentSpecific = "No";
var startTimeValArr = {};
var endTimeValArr = {};
var subjectsArr = [];
var previousSubTpArr = {};
var subjectsWiseIdsArr = {};
var perDoseCompArr = {};
var postDoseCompArr = {};
var preDoseTpsLength = 0;
var postDoseTpsLength = 0;

$(document).ready(function() {
	resetMealsTable();
//	debugger;
	checkFunction();
	var appServerTime = $("#servertime").html();
	var tempTimeArr = appServerTime.split(":");
	var date = new Date(getServerDate());
	date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	stTime = hour+":"+minutes+":00";
	
});
var timePoints = {};
var timeDesimal = {};
var timePointsTypes = {};
var mealTypes = {};
var timePointNo = {};
var subjectsPeriod = {};
var subjectsPeriodNo = {};

var subjectStartTimes = {};
var endTimePointIds = {};
var cosumption = {};
var message = {};
var stringTimePointIdsMap = {}; // Map<String, List<Long>>
var treatmentWiseMealsTimepoints;

var treatmentWiseTimePoitns = {}; // Map<Long, Map<String, Long>>
var subjectTreatment = {}; // Map<String, Long>

var subjectBarcodesStart = [];
var subjectBarcodesSEnd = [];
var mealDataJson = "";
var crrentMealIds = [];
var subjectBarcodeSplit ="";
var barcode = "";
var mealData = "";
var timPointId = "";
var periodPk = "";
var treatmentId ="";
var timeDeviation = false;
var timeDeviationTime = [];
var subjectBrcode ="";
var chkedRdId = "";
var checkedTpStr ="";
var scanTime = "";
$(document).ready(function() {
		$("#barcode").focus();
		var value = $("#studyName").val();
		asynchronousAjaxCallBack(mainUrl+ "/study/clinical/measlCollection/" + value, mealsCallBack);
		function mealsCallBack(mealsData){
			$(".loader").hide();
			if (mealsData != null && mealsData != "undefined" && mealsData != "") {
				mealDataJson = mealsData;
				assignMealsData(mealDataJson);
				$(".loader").hide();
				registerMealsCollectionSocket();
			}
		}
		
	});

	
	

function registerMealsCollectionSocket(){
	var url = mainUrl + "/testWebSocket/mealsCollectionSocket";
	var eventSource = new EventSource(url);
	eventSource.addEventListener("rtcMealsCollectionData", function(event) {
	//	debugger;
		console.log("Meals collection Data");
		var rtcData = JSON.parse(event.data);
		console.log("Event : " + event.data);
		var rtcDataStr = {};
		//debugger;
		if(rtcData != null && rtcData != undefined){
			if(rtcData.rtcDtoList != null && rtcData.rtcDtoList != undefined){
				for (var i = 0; i < rtcData.rtcDtoList.length; i++) {
					/*var	jsonString = JSON.stringify(Object.assign({}, rtcData.rtcDtoList)) 
					var	json_obj = JSON.parse(jsonString);*/
					var	json_obj = rtcData.rtcDtoList[i];
						
					if(rtcData.rtcDtoList[i].mealsEndTime != null && rtcData.rtcDtoList[i].mealsEndTime != undefined){
						var stdMealTpData = mealDataJson.smtpMap;
						if(stdMealTpData != undefined){
							var periodMap = mealDataJson.smtpMap[rtcData.rtcDtoList[i].periodId];
							if(periodMap != undefined){
								var mealsIdMap = periodMap[rtcData.rtcDtoList[i].timePointId];
								if(mealsIdMap != undefined){
									var subMtpMap =  mealsIdMap[rtcData.rtcDtoList[i].subjectNo];
									if(subMtpMap == null || subMtpMap == undefined){
										mealsIdMap[rtcData.rtcDtoList[i].subjectNo] = json_obj;
									}
								}else{
									var subMtpMap = new Map();
									subMtpMap.set(rtcData.rtcDtoList[i].subjectNo, json_obj);
									var subjMtpMapObj = Object.fromEntries(subMtpMap);
									periodMap[rtcData.rtcDtoList[i].timePointId] = subjMtpMapObj;
								}
							}else{
								var mealsIdMap = new Map();
								var subMtpMap = new Map();
								subMtpMap.set(rtcData.rtcDtoList[i].subjectNo, json_obj);
								
								const subjMtpMapObj = Object.fromEntries(subMtpMap);
								mealsIdMap.set(rtcData.rtcDtoList[i].timePointId, subjMtpMapObj);
								
								const mealsIdMapObj = Object.fromEntries(mealsIdMap);
								mealDataJson.smtpMap[rtcData.rtcDtoList[i].periodId] = mealsIdMapObj;
								
							}
						}else{
							var periodMap = new Map();
							var mealsIdMap = new Map();
							var subMtpMap = new Map();
							subMtpMap.set(rtcData.rtcDtoList[i].subjectNo, json_obj);
							
							const subjMtpMapObj = Object.fromEntries(subMtpMap);
							mealsIdMap.set(rtcData.rtcDtoList[i].timePointId, subjMtpMapObj);
							
							periodMap.set(rtcData.rtcDtoList[i].periodId, mealsIdMap);
							const mealPeriodMapObj = Object.fromEntries(periodMap);
							mealDataJson.smtpMap = mealPeriodMapObj;
						}
						var curTreatment = mealDataJson.mealsTimpointsMap[rtcData.rtcDtoList[i].timePointId].treatmentInfo.id;
						var value1 = rtcData.rtcDtoList[i].timePointId + "_" + rtcData.rtcDtoList[i].subjectNo;
						var value2 = rtcData.rtcDtoList[i].subjectNo + "," + rtcData.rtcDtoList[i].periodId + "," + rtcData.rtcDtoList[i].timePointId;
						var rowId = "row"+ rtcData.rtcDtoList[i].timePointId + "_" +rtcData.rtcDtoList[i].subjectNo+"_"+curTreatment+"_tr";
						var generationType = "endTime";
						deleteRow(rowId,  value1, value2 ,generationType);
						$('[href="#nav-profile"]').tab('show');
						$("#nav-profile-tab").css("background","#fa8e02");
						$("#nav-home-tab").css("background","#272e38");
					}else{
						$('#example_start_body').html("");
						$('[href="#nav-home"]').tab('show');
						$("#nav-home-tab").css("background","#fa8e02");
						$("#nav-profile-tab").css("background","#272e38");
					}
					var collectdMap = mealDataJson.collectedDataMap;
					if(collectdMap != null && collectdMap != undefined){
						mealDataJson.collectedDataMap[rtcData.rtcDtoList[i].subjectNo+","+rtcData.rtcDtoList[i].periodId+","+rtcData.rtcDtoList[i].treatmentId+","+rtcData.rtcDtoList[i].timePointId] = json_obj;
					}else{
						var colMap = new Map();
						colMap.set(rtcData.rtcDtoList[i].subjectNo+","+rtcData.rtcDtoList[i].periodId+","+rtcData.rtcDtoList[i].treatmentId+","+rtcData.rtcDtoList[i].timePointId, json_obj);
						const obj = Object.fromEntries(colMap);
						mealDataJson.collectedDataMap = obj;
						
					}
					
					//Start
					var stpfiIndex = startTimepointsFieldIds.indexOf(rtcData.rtcDtoList[i].timePointId+ "_" +rtcData.rtcDtoList[i].subjectNo);
					if(stpfiIndex != -1){
						startTimepointsFieldIds.splice(stpfiIndex, 1);
					}
					
					var stpIdsIndex = startTpIds.indexOf(rtcData.rtcDtoList[i].timePointId+ "_" +rtcData.rtcDtoList[i].subjectNo+"_"+rtcData.rtcDtoList[i].periodId);
					if(stpIdsIndex != -1){
						startTpIds.splice(stpIdsIndex, 1);
					}
					
					var subjbarcodeStartIndex = subjectBarcodesStart.indexOf(rtcData.rtcDtoList[i].timePointId+ "_" +rtcData.rtcDtoList[i].subjectNo);
					if(subjbarcodeStartIndex != -1){
						subjectBarcodesStart.splice(subjbarcodeStartIndex, 1);
					}
					
					
					var stpIdsIndex = startTimepointsIds.indexOf(rtcData.rtcDtoList[i].subjectNo + "," + rtcData.rtcDtoList[i].periodId + "," + rtcData.rtcDtoList[i].timePointId);
					if(stpIdsIndex != -1){
						startTimepointsIds.splice(stpIdsIndex, 1);
					}
					
					//End
					var endIdsIndex = endTpIds.indexOf(rtcData.rtcDtoList[i].timePointId+ "_" +rtcData.rtcDtoList[i].subjectNo+"_"+rtcData.rtcDtoList[i].periodId);
					if(endIdsIndex != -1){
						endTpIds.splice(endIdsIndex, 1);
					}
					
					var subjbarcodeEndIndex = subjectBarcodesSEnd.indexOf(rtcData.rtcDtoList[i].timePointId+ "_" +rtcData.rtcDtoList[i].subjectNo);
					if(subjbarcodeEndIndex != -1){
						subjectBarcodesSEnd.splice(subjbarcodeEndIndex, 1);
					}
					barcode = "02a"+rtcData.rtcDtoList[i].subjectNo+"a"+rtcData.rtcDtoList[i].studyId+"n";
					subjectBrcode =barcode;
					showTimePoints(rtcData.rtcDtoList[i].subjectNo);
					
					//Fro Replaced Subject
					if(rtcData.rtcDtoList[i].replacedSubjectId != null && rtcData.rtcDtoList[i].replacedSubjectId != undefined){
						var sub = mealDataJson.subMap[rtcData.rtcDtoList[i].subjectNo];
				    	if(sub != null && sub != undefined){
				    		//Standby pojo
				    		sub.subjectStatus = "Replaced";
				    		sub.stdSubjectId = rtcData.replacedSubjectId;
				    		//Replaced Subject
				    		var repSubNo = data.replaceAvailSubjects[rtcData.replacedSubjectId];
				    		if(repSubNo != null && repSubNo != "" && repSubNo != undefined){
				    			var repSubPojo = data.subMap[repSubNo];
				    			if(repSubPojo != null && repSubPojo != undefined){
				    				repSubPojo.subjectReplace = "Replaced";
				    				sub.stdSubjectId = repSubPojo;
				    			}
				    		}
				    		data.subMap[rtcData.subjectNo] = sub;
				    	}
					}
				}
			}
		}
	});
}
function assignMealsData(mdata){
	mealData = mdata;
	console.log(mealData);
	collectedData = mealData.collectedDataMap;
	$.each(collectedData, function(key, value) {
		if (value.startTimeOnly != null)
			subjectStartTimes[key] = value.startTimeOnly;
	});
	dataSubjects = mealData.subjects;
	subjectBarocodes = mealData.subjectBarocodes;
	replacedSubjects = mealData.replacedSubjects;
	droppedSubjects = mealData.droppedSubjects;
	subjectPerods = mealData.subjectPerods;
	subjectDoseTimes = mealData.subjectDoseTimes;
	periods = mealData.periods;
	timPoints = mealData.timPoints;
	
	subjectPeriodTimePointCollectedData = subjectPeriodTimePointCollectedData;
	timePointCollectedData = mealData.timePointCollectedData;
	stringTimePointIdsMap = mealData.mealTimePointIds;
	treatmentWiseMealsTimepoints = mealData.treatmentWiseMealsTimepoints;
	treatmentWiseTimePoitns = mealData.treatmentWiseTimePoitns;
	subjectTreatment = mealData.subjectTreatment;
	
	$.each(subjectPerods, function(key, value) {
		subjectsPeriod[key] = value.id;
		subjectsPeriodNo[key] = value.periodName;
	});
}

function onBarcodeScanned(){
	barcode = $("#barcode").val();
	$("#barcodeMsg").html("");
	$("#subjectMsg").html("");
	var lastChar = barcode.substr(barcode.length - 1);
	if (lastChar == 'n') {
		$("#barcode").focus();
		$("#barcode").val("");
		var prefix = barcode.substr(0, 2);
		if (prefix == "02") {
			subjectBarcodeSplit = barcode.substr(0,barcode.length - 1).split('a');
			if(subjectBarcodeSplit[2] == mealDataJson.study.id){
//				debugger;
				var subj = mealDataJson.subMap[subjectBarcodeSplit[1]];
				if(subj != null && subj != undefined){
					if(subj.subjectDiscontinue == null || subj.subjectDiscontinue == "" || subj.subjectDiscontinue == "No"){
						subjectBrcode = barcode;
						showTimePoints(subjectBarcodeSplit[1]);
						scanTime = getServerEndTimeWithSeconds();
					}else{
						showMessage("Subject Droped.");
						isValid = false;
						subjectBrcode ="";
						$('#mealTimePointsDiv').html("");
					}
					
					var radioFlag = true;
					if(chkedRdId != "")
						radioFlag = false;
					if (radioFlag) {
						showTimePoints(subjectBarcodeSplit[1]);
//						showMessage("Please select Timepoint");
						isValid = false;
					}
				}else{
					showMessage("Subject "+subjectBarcodeSplit[1]+" Not Exists");
					isValid = false;
					$('#mealTimePointsDiv').html("");
				}
			}else{
				showMessage("Subject Barcode Not belogs to Project :"+mealDataJson.study.projectNo);
				isValid = false;
				$('#mealTimePointsDiv').html("");
			}
		}else{
			showMessage("Scan Subject Barcode");
			isValid = false;
			$('#mealTimePointsDiv').html("");
		}
	}
}


$("#barcode").on('input',function(e) {
	onBarcodeScanned();
});

function radiobuttonOnclickValidation(){
	for(var j=0; j<crrentMealIds.length; j++){
		if ($("#timepoint_"+crrentMealIds[j]).prop("checked")){
			var subjectBarcodeSplit = barcode.substr(0,barcode.length - 1).split('a');
			periodPk = mealDataJson.subjectPerods[subjectBarcodeSplit[1]].id;
			timPointId = $("#timepoint_"+crrentMealIds[j]).val();
			chkedRdId = timPointId;
			var tpStrPojo = mealDataJson.mealsTimpointsMap[chkedRdId];
			if(tpStrPojo != null && tpStrPojo != undefined)
				checkedTpStr = tpStrPojo.sign+tpStrPojo.timePoint;
			if (duplicatecheck(subjectBarcodesStart,subjectBarcodesSEnd, "barcodeMsg", timPointId, subjectBarcodeSplit[1])) {
				var subjectTreatmentkey = subjectBarcodeSplit[1]+ "," + periodPk;
				/*var treament = mealDataJson.twsubMap[subjectBarcodeSplit[1]];
				var treatMentArr = treament[periodPk];
				treatmentId = treatMentArr[0];*/
				var map = mealDataJson.mealsTimpointsMap[treatmentId];
				if (checkCollectionStatus(subjectBarcodeSplit[1],periodPk, treatmentId, timPointId)) {
					if (checkTimePointPreOrPost(timPointId)) {
						var pretpTreatment = mealDataJson.mealsTimpointsMap[timPointId].treatmentInfo.id;
						if (checkstartTime(barcode,timPointId,periodPk, pretpTreatment)) {
							createRow(barcode,timPointId,'startTime',periodPk, treatmentId);
							$('[href="#nav-home"]').tab('show');
							$("#nav-home-tab").css("background","#fa8e02");
							$("#nav-profile-tab").css("background","#272e38");
								
						} else {
							createRow(barcode,timPointId,'endTime',periodPk, treatmentId);
							$('[href="#nav-profile"]').tab('show');
							$("#nav-profile-tab").css("background","#fa8e02");
							$("#nav-home-tab").css("background","#272e38");
						}
						generateEndTimeRecords(timPointId, periodPk);
					} else {
//						var doseLength = Object.entries(mealDataJson.dosedMap).length;
						var doseLength = Object.entries(mealDataJson.subjectDoseMap).length;
						if(doseLength > 0){
							createRow(barcode,timPointId,'startTime',periodPk);
							$('[href="#nav-home"]').tab('show');
							$("#nav-home-tab").css("background","#fa8e02");
							$("#nav-profile-tab").css("background","#272e38");
						}else{
							$("#barcodeMsg").html("Dosing Not done");
						}
						generateEndTimeRecords(timPointId, periodPk);
					}
				} else {
					createRow(barcode, timPointId,
							'endTime', periodPk, treatmentId);
					$('[href="#nav-profile"]').tab('show');
					$("#nav-profile-tab").css("background","#fa8e02");
					$("#nav-home-tab").css("background","#272e38");
					generateEndTimeRecords(timPointId, periodPk);
				}
				
			}
		}
	}
	//debugger;
	if(subjectBarcodesStart.length > 0){
		$('.startBtnTR').show();
		$('#subStart_btn').removeAttr("disabled");
	}else $('.startBtnTR').hide();
}
function generateEndTimeRecords(tpId, pid){
	var subEndTpsLength = Object.entries(mealDataJson.subEndTpsMap).length;
	if(subEndTpsLength > 0){
		var mealTpsLength = Object.entries(mealDataJson.mealsTimpointsMap).length;
		if(mealTpsLength > 0){
			var meaalTimePoint = mealDataJson.mealsTimpointsMap[tpId];
			var tpStr = "";
			if(meaalTimePoint != null && meaalTimePoint != undefined)
				tpStr = meaalTimePoint.sign+meaalTimePoint.timePoint;
			if(tpStr != null && tpStr != "" && tpStr != undefined){
				var subEndTpMapLength = Object.entries(mealDataJson.subEndTpsMap).length;
				if(subEndTpMapLength > 0){
					var subEndTpMap = mealDataJson.subEndTpsMap[tpStr];
					if(subEndTpMap != null && subEndTpMap != undefined){
						var subPeriodEndMap = subEndTpMap[pid];
						if(subPeriodEndMap != null && subPeriodEndMap != undefined){
							var subEndMap = new Map(Object.entries(subPeriodEndMap));
							for (let [key, value] of subEndMap) {
								var bcSplit = key.substr(0,key.length - 1).split('a');
								var endTpsKey = tpId + "_" + bcSplit[1]+"_"+pid;
								if(endTpIds.indexOf(endTpsKey) == -1)
									createRow(key, tpId,'endTime', pid, value);
							}
						}
					}
				}
			}
		}
	}
}
function duplicatecheck(startArr,endArr, barcodeMsg, tpId, subjectId){
	var flag = true;
	var selectedTp ="";
	if((tpId != null && tpId != "" && tpId != undefined) && (subjectId != null && subjectId != "" && subjectId != undefined))
		selectedTp = tpId+"_"+subjectId;
	if(selectedTp != ""){
		if(startArr.indexOf(selectedTp) != -1){
			flag = false;
			$('[href="#nav-home"]').tab('show');
			$("#nav-home-tab").css("background","#fa8e02");
			$("#nav-profile-tab").css("background","#272e38");
		}if(endArr.indexOf(selectedTp) != -1){
			flag = false;
			$('[href="#nav-profile"]').tab('show');
			$("#nav-profile-tab").css("background","#fa8e02");
			$("#nav-home-tab").css("background","#272e38");
		}
	}
	return flag;
	
}
function checkstartTime(subjectBrcode, timepoint, periodPk, treatment) {
	var subjectBarcodeSplit = subjectBrcode.substr(0, subjectBrcode.length - 1).split('a');
	var key = subjectBarcodeSplit[1] + "," + periodPk + ","+treatment+"," + timepoint;
	if (subjectStartTimes[key] != undefined && subjectStartTimes[key] != '') {
		return false;
	} else
		return true;
}

function checkTimePointPreOrPost(timePointId) {
	var flag = false;
	for (const [key, value] of Object.entries(mealDataJson.postDoseMap)) {
		for (const [key, val] of Object.entries(value)) {
			if(timePointId == val.id){
				flag = false;
				break;
			}
		}
	}
	for (const [key, value] of Object.entries(mealDataJson.preDoseMap)) {
		for (const [key, val] of Object.entries(value)) {
			if(timePointId == val.id){
				flag = true;
				break;
			}
		}
	}
	return flag;
}
function createRow(subjectBrcode, timPointId, type, periodPk, treatmentId) {
//	debugger;
	$("#mealDetailsTab").show();
	var timePointTreatment = mealDataJson.mealsTimpointsMap[timPointId].treatmentInfo.id;
	if (type == 'endTime') {
		$("#example_end_body").prepend(generateEndTime(subjectBrcode, timPointId, periodPk, treatmentId,"endTime"));
	} else if (type == 'startTime') {
		$("#example_start_body").prepend(generateStartTime(subjectBrcode, timPointId, periodPk, treatmentId, 'startTime'));
		$("#example_start"+timePointTreatment).show();
	}
	$("#example_end").show();
	$('#example_start').show();
	$('#subEnd').show();
	$('#subStart').show();
	$('#subStart_btn').show();
	$('#subEnd_btn').show();
}
var startTimepointsIds = [];
var startTimepointsFieldIds = [];
var startTpIds = [];
var endTpIds = [];
function generateStartTime(subjectBrcode, timepoint, periodPk, treatmentId, generationType) {
	var curtpTreatmentId = mealDataJson.mealsTimpointsMap[timepoint].treatmentInfo.id;
	var subjectBarcodeSplit = subjectBrcode.substr(0, subjectBrcode.length - 1)
			.split('a');
	var value1 = timepoint + "_" + subjectBarcodeSplit[1];
	var value2 = subjectBarcodeSplit[1] + "," + periodPk + "," + timepoint;
	var flag = true;
	$.each(startTimepointsIds, function(k, v) {
		if (v == (parseInt(subjectBarcodeSplit[1]) + "," + periodPk + "," + timepoint)) {
			flag = false;
			return false;
		}
	});
//	debugger;
	if(subjectsArr.length > 0){
		var subrowId = subjectsWiseIdsArr[subjectBarcodeSplit[1]];
		if(subrowId != null && subrowId != "" && subrowId != undefined){
			var previousSubTp = previousSubTpArr[subjectBarcodeSplit];
			deleteRow(subrowId,  previousSubTp, value2 ,generationType);
		}
	}
	if (flag) {
		var sign = mealDataJson.mealsTimpointsMap[timepoint].sign;
		if(sign == null || sign == undefined)
			sign = "";
		var subNo = mealDataJson.subMap[subjectBarcodeSplit[1]].reportingId.subjectNo;
		var row = "<tr align='center' id=\"row" + timepoint + "_" +subjectBarcodeSplit[1]+"_"+curtpTreatmentId
				+ "_tr\">" + "<th>" + subNo + "</tH>";
		row += "<th>" + mealDataJson.mealsTimpointsMap[timepoint].mealsType.fieldValue + "</th>";
		row += "<th>" + sign+mealDataJson.mealsTimpointsMap[timepoint].timePoint
				+ "<input type='hidden' name='subjectInfo' value=\""
				+ subjectBrcode + "\" />"
				+ "<input type='hidden' name='period' value=\""
				+ subjectsPeriod[subjectBarcodeSplit[1]] + "\" />";
		row += "<td><input type=\"button\" style='padding: 0px 20px; font-size:small;' class='btn btn-danger btn-md' onclick=\"deleteRow('row"
				+ timepoint + "_" +subjectBarcodeSplit[1]+"_"+curtpTreatmentId+ "_tr', '"
				+ value1 + "', '" + value2
				+ "', '"+periodPk+"', '"+generationType+"')\" value=\"Remove\" /> </td></tr>";
		console.log(row);
		
		startTimepointsFieldIds.push(timepoint + "_" +subjectBarcodeSplit[1]);
//		startTpIds.push(timepoint + "_" + subjectBarcodeSplit[1]+"_"+curtpTreatmentId);
		startTpIds.push(timepoint + "_" + subjectBarcodeSplit[1]+"_"+periodPk);
		subjectBarcodesStart.push(timepoint + "_" +subjectBarcodeSplit[1]);
		//debugger;
		if(startTimepointsIds.indexOf(value2) == -1)
			startTimepointsIds.push(value2);
		if(subjectsArr.indexOf(subjectBarcodeSplit[1]) == -1)
			subjectsArr.push(subjectBarcodeSplit[1]);
		subjectsWiseIdsArr[subjectBarcodeSplit[1]] = 'row'+timepoint + '_' +subjectBarcodeSplit[1]+'_'+curtpTreatmentId	+ '_tr';
		previousSubTpArr[subjectBarcodeSplit] = timepoint + "_" +subjectBarcodeSplit[1];
		return row;
	} else
		return "";
}
var endTimePointIdsTemp = {};

function generateEndTime(subjectBrcode, timepoint, periodPk, treatmentId, generationType) {
	var curtpTreatmentId = mealDataJson.mealsTimpointsMap[timepoint].treatmentInfo.id;
	var subjectBarcodeSplit = subjectBrcode.substr(0, subjectBrcode.length - 1)
			.split('a');
	var key = subjectBarcodeSplit[1] + "," + periodPk + ","+curtpTreatmentId+","+ timepoint;
	var value1 = timepoint + "_" +subjectBarcodeSplit[1];
	var value2 = subjectBarcodeSplit[1] + "," + periodPk + ","+ timepoint;
//	var tpStartTime = new Date(mealDataJson.collectedDataMap[key].startTime);
	var tpStartTime = new Date(mealDataJson.collectedDataMap[key].mealsSatartTime);
	const startTimehoursAndMinutes = tpStartTime.getHours() + ':' + tpStartTime.getMinutes();
	if (endTimePointIdsTemp[key] == undefined) {
		var sign = mealDataJson.mealsTimpointsMap[timepoint].sign;
		if(sign == null || sign == undefined)
			sign = "";
		var subjectPeriodTimePointCollectedDataId = mealDataJson.collectedDataMap[key];
		var subNo = mealDataJson.subMap[subjectBarcodeSplit[1]].reportingId.subjectNo;
		
		var row = "<tr  align='center' id=\"row" + timepoint + "_" +subjectBarcodeSplit[1]+"_"+curtpTreatmentId
				+ "_tr\">" + "<th>" + subNo + "</th>"
		 		+"<th>" + mealDataJson.mealsTimpointsMap[timepoint].mealsType.fieldValue + "</th>"
		 		+"<th>" + sign+mealDataJson.mealsTimpointsMap[timepoint].timePoint
				+ "<input type='hidden' name='subjectInfo' value=\""
				+ subjectBrcode + "\" />"
				+ "<input type='hidden' name='period' value=\""
				+ subjectsPeriod[subjectBarcodeSplit[1]] + "\" />  </th>"
		        +"<td>" + startTimehoursAndMinutes+ "</td>"
		        +"<td><input type='radio' name='consumption_Radio' id=\"cosumption_"
				+ timepoint + "_" +subjectBarcodeSplit[1]+"_"+curtpTreatmentId+ "_Yes\" /> Yes"
				+"<input type='radio' name='consumption_Radio' id=\"cosumption_"
				+ timepoint + "_" +subjectBarcodeSplit[1]+"_"+curtpTreatmentId+ "_No\" /> No "
				+"<font color='red' id=\"cosumptionMsg_" + timepoint + "_"
				+ subjectBarcodeSplit[1]+"_"+curtpTreatmentId
				+ "\" ></font></td>"
				+ "<td><input type='text' id=\"message_"
				+ timepoint + "_" +subjectBarcodeSplit[1]+"_"+curtpTreatmentId
				+ "\" name=\"message" + timepoint + "_"
				+ subjectBarcodeSplit[1] + "\" />" +
				"<font color='red' id=\"messageMsg_"
				+ timepoint+ "_"+subjectBarcodeSplit[1]+"_"+curtpTreatmentId
				+ "\" ></font></td>"
				+ "<td><input type='button' style='padding: 0px 20px; font-size:small;' class='btn btn-warning btn-md' value = 'End' onclick=\"endTime('"
				+ timepoint + "_" +subjectBarcodeSplit[1]+ "', '"+ curtpTreatmentId+ "')\" />"
				+"<font color=\"red\" id=\"\endTimeMsg"
				+ timepoint + "_" + subjectBarcodeSplit[1]
				+ "\"></font></td></tr>" ;
		
		mealDataJson.collectedDataMap[key].endTimeOnly  = "scaned";
//		endTpIds.push(timepoint + "_" + subjectBarcodeSplit[1]+"_"+curtpTreatmentId);
		endTpIds.push(timepoint + "_" + subjectBarcodeSplit[1]+"_"+periodPk);
		subjectBarcodesSEnd.push(timepoint + "_" +subjectBarcodeSplit[1]);
		if(startTimepointsIds.indexOf(value2) == -1)
			startTimepointsIds.push(value2);
		startTimepointsFieldIds.push(timepoint + "_" +subjectBarcodeSplit[1]);
		return row;
	} else {
		return "";
	}
}
function deleteRow(id, value1, value2, treatmentId, genarateType) {
//	debugger;
	$("#" + id).remove();
	var removeId = value1;
	if(removeId != null && removeId != ""){
		var startIndex = subjectBarcodesStart.indexOf(removeId);
		if(startIndex != -1){
			subjectBarcodesStart.splice(startIndex, 1);
		}
		var endIndex = subjectBarcodesSEnd.indexOf(removeId);
		if(endIndex != -1){
			subjectBarcodesSEnd.splice(endIndex, 1);
		}
	}
	var ind = startTimepointsIds.indexOf(value2);
	if(ind != -1){
		startTimepointsIds.splice(ind, 1);
	}
	var newArray = jQuery.grep(startTimepointsFieldIds, function(value) {
		return value != value1;
	});
	startTimepointsFieldIds = newArray;
	if(subjectBarcodesStart.length > 0){
		$('.startBtnTR').show();
		$('#subStart_btn').removeAttr("disabled");
	}else $('.startBtnTR').hide();
}
function deleteRowEndTime(id, key, treatmentId, genratedType, value1, value2) {
//	debugger;
	$("#" + id).remove();
	var tempArr = key.split(",");
	var removeId = value1;
	if(removeId != null && removeId != ""){
		var startIndex = subjectBarcodesStart.indexOf(removeId);
		if(startIndex != -1){
			subjectBarcodesStart.splice(startIndex, 1);
		}
		var endIndex = subjectBarcodesSEnd.indexOf(removeId);
		if(endIndex != -1){
			subjectBarcodesSEnd.splice(endIndex, 1);
		}
	}
	var ind = startTimepointsIds.indexOf(value2);
	if(ind != -1){
		startTimepointsIds.splice(ind, 1);
	}
	endTimePointIdsTemp[key] = undefined;
}
function mealTimes(treatment, type){
	startTimeValArr = {};
//	debugger;
	var stm = getServerTime();
	if(type == "start"){
		if(startTpIds != undefined && startTpIds.length >0){
			if(startTpIds != undefined && startTpIds.length >0){
				for(var k=0; k<startTpIds.length; k++){
					var tempArr = startTpIds[k].split("_");
					if(tempArr != null && tempArr.length > 0){
//						if(tempArr[2] == treatment){
							var id = tempArr[0]+"_"+tempArr[1];
							startTimeValArr[id] = stm;
//						}
					}
				}
			}
			mealTimePointDeviations();
		}
	}
}
function mealTimePointDeviations() {
//	debugger;
	if(doseDone == "Done"){
		var subSplit = subjectBrcode.substr(0, subjectBrcode.length - 1).split('a');
		var dosedLength = Object.keys(mealDataJson.dosedMap).length;
		timeDeviation = false;
		var devStr = "";
		var dosePlanOrDoseTime = mealDataJson.ptdDto.plannedDate;
		if(dosedLength > 0){
			var subdoseMealDoseMap = mealDataJson.dosedMap[subSplit[1]];
			if(subdoseMealDoseMap != null && subdoseMealDoseMap != undefined){
				var subperiodMealDoseMap = subdoseMealDoseMap[periodPk];
				if(subperiodMealDoseMap != null && subperiodMealDoseMap != undefined){
					var dosePojo = subperiodMealDoseMap[treatmentId];
					if(dosePojo != null && dosePojo != undefined)
						dosePlanOrDoseTime = dosePojo.actualTime;
				}
			}
		}
		if(dosePlanOrDoseTime != null && dosePlanOrDoseTime != "" && dosePlanOrDoseTime != undefined){
			var mealPojo = mealDataJson.mealsTimpointsMap[timPointId];
			devStr = calculateDeviation(dosePlanOrDoseTime, mealPojo.sign+mealPojo.timePoint,mealPojo.windowPeriod, mealPojo.windowPeriodSign , "MINUTES");
		}
		if(devStr != ""){
			timeDeviation = true;
			var deviationMsgId = "0";
			if(mealDataJson.dsDto != null && mealDataJson.dsDto != undefined){
				if(mealDataJson.dsDto.devionCodeMap != null && mealDataJson.dsDto.devionCodeMap != undefined){
					deviationMsgId = mealDataJson.dsDto.devionCodeMap['TPD'];
				}
			}
			timeDeviationTime.push(subSplit[1]+"@@@"+timPointId+"@@@"+devStr+"@@@"+deviationMsgId);
		}
		if(timeDeviation){
			Swal.fire({
			  title: '',
			  confirmButtonText: "Yes",
			  text:'Time Deviation Happened : '+devStr
			});
		}
	}
}
function endTime(id, treatment) {
	endTimeValArr  = {};
	var stm = getServerTime();
	endTimeValArr[id] = stm;
	submitForm("end", treatment, id);
}

function submitForm(submitMealType, treatment, id) {
	// $(".loader").show();
	var mealsCollectionDate = [];
	var startTimemealsData = [];
	var endTimemealsData = [];
	var subNoArr = [];
	var periodsArr = [];
	var timePointIdsArr = [];
	var consumptionFlag = false;
	var messageFlag = false;
	var submitingStartIds = [];
	var submitingEndIds = [];
	var subjetIdsDeletionArr = [];
//	debugger;
	if(submitMealType == "start"){
		mealTimes(treatment, 'start');
		$('#subStart_btn').attr("disabled", true);
		$.each(startTimepointsFieldIds, function(index, fieldId) {
//			var value = $('#startWithSec'+fieldId+"_"+treatment).val();
			var value = startTimeValArr[fieldId];
			if(value != null && value != "" && value != undefined){
				var temp = fieldId.split("_");
				if(temp.length > 0){
					var subjNo = "";
					if(temp[1].length == 1)
						subjNo = "0"+temp[1];
					else subjNo = temp[1];
					var periodid = mealDataJson.subjectPerods[subjNo].id
					startTimemealsData.push(temp[0]+"@@@"+subjNo+"@@@"+value+"@@@"+periodid); //timePoint, subject, value, periodId
					if(subNoArr.indexOf(subjNo) == -1)
						subNoArr.push(subjNo);
					if(periodsArr.indexOf(periodid) == -1)
						periodsArr.push(periodid);
					if(timePointIdsArr.indexOf(temp[0]) == -1)
						timePointIdsArr.push(temp[0]);
					if(submitingStartIds.indexOf(temp[0]+"_"+subjNo) == -1)
						submitingStartIds.push(temp[0]+"_"+subjNo);
					if(subjetIdsDeletionArr.indexOf(subjNo+","+periodid+","+temp[0]) == -1)
						subjetIdsDeletionArr.push(subjNo+","+periodid+","+temp[0]);
					
				}
			}
		});
		consumptionFlag = true;
		messageFlag = true;
	}else{
//		$.each(startTimepointsFieldIds, function(index, fieldId) {
//			var value = $('#endWithSec'+fieldId+"_"+treatment).val();
			var value = endTimeValArr[id];
			if(value != null && value !="" && value != undefined){
				var consumptionVal = "";
				var commentsVal = $('#message_'+id+"_"+treatment).val();
				if ($('#cosumption_'+id+"_"+treatment+'_Yes').is(":checked")) {
					consumptionVal = "Yes";
				}
				if ($('#cosumption_'+id+"_"+treatment+'_No').is(":checked")) {
					consumptionVal = "No";
				}
				
				var conFlag = false;
				var comFlag = true;
				if(consumptionVal != null && consumptionVal != "" && consumptionVal != undefined){
					$('#cosumptionMsg_'+id+"_"+treatment).html("");
					conFlag = true;
				}else{
					$('#cosumptionMsg_'+id+"_"+treatment).html("Required Field.");
					consumptionFlag = false;
					conFlag = false;
				}
				/*if(commentsVal != null && commentsVal != "" && commentsVal != undefined){
					$('#messageMsg_'+id+"_"+treatment).html("");
					comFlag = true;
				}else{
					$('#messageMsg_'+id+"_"+treatment).html("Required Field.");
					messageFlag = false;
					comFlag = false;
				}*/
				if(commentsVal == null || commentsVal == "" || commentsVal == undefined){
					commentsVal ="0";
				}
				if(comFlag && conFlag){
					var temp = id.split("_");
					if(temp.length > 0){
						var subjNo = "";
						if(temp[1].length == 1)
							subjNo = "0"+temp[1];
						else subjNo = temp[1];
						var periodid = mealDataJson.subjectPerods[subjNo].id;
						endTimemealsData.push(temp[0]+"@@@"+subjNo+"@@@"+value+"@@@"+periodid+"@@@"+consumptionVal+"@@@"+commentsVal); //timePoint, subjectno, value, periodId, consumptionMsg, comments
						if(subNoArr.indexOf(subjNo) == -1)
							subNoArr.push(subjNo);
						if(periodsArr.indexOf(periodid) == -1)
							periodsArr.push(periodid);
						if(timePointIdsArr.indexOf(temp[0]) == -1)
							timePointIdsArr.push(temp[0]);
						if(submitingEndIds.indexOf(temp[0]+"_"+subjNo) == -1)
							submitingEndIds.push(temp[0]+"_"+subjNo);
						if(subjetIdsDeletionArr.indexOf(subjNo+","+periodid+","+temp[0]) == -1)
							subjetIdsDeletionArr.push(subjNo+","+periodid+","+temp[0]);
					}
				}else{
					consumptionFlag = false;
					messageFlag = false;
				}
			}
//		});
	}
//	alert("startTimemealsData :"+startTimemealsData);
//	alert("endTimemealsData :"+endTimemealsData);
	if(startTimemealsData.length > 0 || endTimemealsData.length > 0){
//		if(consumptionFlag  && messageFlag){
			$.ajax({
				url : $("#mainUrl").val() + '/administration/getCsrfToken',
				type : 'GET',
				success : function(data) {
					mealsCollectionDate.push({
						name : data.parameterName,
						value : data.token
					});
					mealsCollectionDate.push({
						name : "studyId",
						value : $("#studyName").val()
					});
					mealsCollectionDate.push({
						name : "timeDeviation",
						value : timeDeviation
					});
					mealsCollectionDate.push({
						name : "timeDeviationTime",
						value : timeDeviationTime
					});
					if(startTimemealsData.length > 0){
						mealsCollectionDate.push({
							name : "startMealsData",
							value : startTimemealsData
						});
					}
					if(endTimemealsData.length > 0){
						mealsCollectionDate.push({
							name : "endMealsData",
							value : endTimemealsData
						});
					}
					mealsCollectionDate.push({
						name : "subNoList",
						value : subNoArr
					});
					mealsCollectionDate.push({
						name : "timePointIds",
						value : timePointIdsArr
					});
					if(periodPk == "")
						periodPk = 0;
					mealsCollectionDate.push({
						name : "currentPeriod",
						value : periodPk
					});
					
					$.ajax({
						url : $("#mainUrl").val()
								+ '/study/clinical/mealsDataSave',
						type : 'POST',
						data : mealsCollectionDate,
						success : function(e) {
							$('#subStart_'+treatment+'_btn').removeAttr("disabled");
							console.log(e)
							if (e.msgFlag === true)
								displayMessage("success", e.mealsMsg);
							else 
								displayMessage("error", e.mealsMsg);
							if(submitMealType == "start"){
						    	$("#example_"+treatment).hide();
								$("#subStart_"+treatment).hide();
								$("#examplebody_"+treatment).empty();
						    }else{
						    	$("#example2_"+treatment).hide();
								$("#subEnd_"+treatment).hide();
								$("#example2body_"+treatment).empty();
						    }
							$("#barcode").focus();
							var studyVal = $("#studyName").val();
							var barcodeVal = barcode;
							/*mealDataJson = synchronousAjaxCall(mainUrl+ "/study/clinical/measlCollection/" + studyVal);
							assignMealsData(mealDataJson);*/
							var subBarcodeSplit = barcodeVal.substr(0, barcodeVal.length - 1).split('a');
							$.each(submitingStartIds, function(key, value) {
							   var subStrIndex = subjectBarcodesStart.indexOf(value);
							   if(subStrIndex != -1)
								   subjectBarcodesStart.splice(subStrIndex, 1);
							});
							if(subjectBarcodesStart.length > 0){
								$('.startBtnTR').show();
								$('#subStart_btn').removeAttr("disabled");
							}else $('.startBtnTR').hide();
							$.each(submitingEndIds, function(k, v) {
								 var seIndex = subjectBarcodesSEnd.indexOf(v);
								   if(seIndex != -1)
									   subjectBarcodesSEnd.splice(seIndex, 1);
							});
							if(subjectBarcodesSEnd.length == 0){
								$('[href="#nav-home"]').tab('show');
								$("#nav-home-tab").css("background","#fa8e02");
								$("#nav-profile-tab").css("background","#272e38");
							}
							$.each(subjetIdsDeletionArr, function(k, v) {
								 var sid = startTimepointsIds.indexOf(v);
								   if(sid != -1)
									  startTimepointsIds.splice(sid, 1);
							});
							showTimePoints(subBarcodeSplit[1]);
							subjectsWiseIdsArr = {};
							subjectsArr = [];
							previousSubTpArr = {};
							perDoseCompArr = {};
							postDoseCompArr = {};
						},
						error : function(er) {
							$('#subStart_btn').removeAttr("disabled");
						}
					});
				},
				error : function(er) {
					$('#subStart_btn').removeAttr("disabled");
				}
			});
//		}
	}else{
		$('#subStart_btn').removeAttr("disabled");
	}
	// $(".loader").hide();
}
var doseDone = "NotDone";
function showTimePoints(subjectNo){
	//debugger;
	crrentMealIds = [];
	var actualSubNo = subjectNo;
	var periodId = mealDataJson.subjectPerods[subjectNo].id;
	var treament = "";
	//StandBy checking
	var standbyFlag = true;
	var printSubNo = subjectNo;
	if(parseInt(subjectNo))
		standbyFlag = false;
	if(standbyFlag){
		var standBySub = mealDataJson.subMap[subjectNo];
		if(standBySub != undefined){
			printSubNo = standBySub.reportingId.subjectNo;
			var replaceSub = standBySub.stdSubjectId;
			if(replaceSub != null && replaceSub != undefined){
				subjectNo = replaceSub.subjectNo;
				treament = mealDataJson.twsubMap[subjectNo];
				if(treament != null && treament != undefined){
					var treatMentArr = treament[periodId];
					treatmentId = treatMentArr[0];
				}else treatmentId = mealDataJson.minTreatmentId;
			}else
				treatmentId = mealDataJson.minTreatmentId;
		}else treatmentId = mealDataJson.minTreatmentId;
	}else{
		treament = mealDataJson.twsubMap[subjectNo];
		if(treament != null && treament != undefined){
			var treatMentArr = treament[periodId];
			treatmentId = treatMentArr[0];
		}else treatmentId = mealDataJson.minTreatmentId;
	}
	var doseMapLength = Object.keys(mealDataJson.subjectDoseMap).length;
	var subDose = null;
	if(doseMapLength > 0){
		doseDone = mealDataJson.subjectDoseMap[actualSubNo][periodId];
		var subDoseMap = mealDataJson.dosedMap[actualSubNo];
		if(subDoseMap != null && subDoseMap != undefined){
			var pdDoseMap = subDoseMap[periodId];
			if(pdDoseMap != null && pdDoseMap != undefined){
				subDose = pdDoseMap[treatmentId];
			}
		}
	}
	var htmlStr = '';
	var plannedDoseTime = mealDataJson.ptdDto.plannedDate;
	var mealsTimepoints = null;
//	debugger;
	if(plannedDoseTime != null && plannedDoseTime != undefined){
		htmlStr = htmlStr+ '<table>';
		mealsTimepoints = [];
		mealsTimepoints = getCollectedTimePoitns(treatmentId, actualSubNo, periodId, doseDone, subDose, mealsTimepoints, plannedDoseTime);
		var i=0;
		if(mealsTimepoints.length > 0){
			for (const [key, val] of Object.entries(mealsTimepoints)) {
//				if(key == treatmentId){
					if(mealsTreatmentSpecific == "Yes")
			    		htmlStr = htmlStr+ '<tr><td> Treatment Code :'+mealDataJson.treatmentMap[key].randamizationCode+' </td><td><table><tr>';
//					if(actualSubNo != subMeals.subjectNo){
						crrentMealIds.push(val.id) ;
						if(i==4){
							i==0;
							htmlStr = htmlStr+ '</tr><tr>';	
						}
						var sign = "";
						if(val.sign != null && val.sign != "")
							sign = val.sign;
						htmlStr = htmlStr +'<td><input type="radio" name="timepoint", id="timepoint_'+val.id+'" value="'+val.id+'" onchange="radiobuttonOnclickValidation()"> '+sign+val.tpWithScheduleTime+'</td>';
						i++;
//					}
//				}
			   
			}
			htmlStr = htmlStr+'</table>';
			$('#mealTimePointsDiv').html(htmlStr);
		}else{
			$('#barcodeMsg').html("There are no scheduled timepoints found for the subject: " + printSubNo);
			$('#mealTimePointsDiv').html("");
		}
	}else{
		// MSG -> Study Planned dosing time no available
		$('#barcodeMsg').html("Planned dose time is not available. Please contact project in-charge or PI");
		$('#mealTimePointsDiv').html("");
	}
	debugger;
	if(chkedRdId != ""){
		if(crrentMealIds.indexOf(parseInt(chkedRdId)) != -1){
			$("#timepoint_"+parseInt(chkedRdId)).attr('checked', true);
			radiobuttonOnclickValidation();
		}else{
			if(crrentMealIds.length > 0){
				$.each(crrentMealIds, function(index, tpId) {
					var tpIdPojo = mealDataJson.mealsTimpointsMap[tpId];
					if(tpIdPojo != null && tpIdPojo != undefined){
						var tpIdStr = tpIdPojo.sign+tpIdPojo.timePoint;
						if(tpIdStr == checkedTpStr){
							$("#timepoint_"+parseInt(tpId)).attr('checked', true);
							radiobuttonOnclickValidation();
//							break;
						}
					}
				});
			}
		}
	}
	//debugger;
	var preFlag = false;
	var postFlag = false;
	var perDoseCompArrgth = Object.keys(perDoseCompArr).length;
	if(perDoseCompArrgth > 0){
		var preComTpArr = perDoseCompArr[actualSubNo];
		if(preComTpArr != null && preComTpArr != undefined){
			if(preDoseTpsLength == preComTpArr.length)
				preFlag = true;
		}
	}
	var postDoseCompArrgth = Object.keys(postDoseCompArr).length;
	if(postDoseCompArrgth > 0){
		var postComTpArr = postDoseCompArr[actualSubNo];
		if(postComTpArr != null && postComTpArr != undefined){
			if(postDoseTpsLength == postComTpArr.length)
				postFlag = true;
		}	
	}
	if(preFlag && postFlag && doseDone == "Done"){
		$("#barcodeMsg").html("Meals Completed For Subject :"+printSubNo);
		$('#mealTimePointsDiv').html("");
//		showTimePoints(rtcData.rtcDtoList[i].subjectNo);
	}else if(preFlag && doseDone != "Done"){
		$("#barcodeMsg").html("Pre Dose Meals Completed For Subject :"+printSubNo);
		$('#mealTimePointsDiv').html("");
	}
	
}
function getCollectedTimePoitns(treatmentId, actualSubNo, periodId, doseDone, subDose, nonCollectedTimePoitns, plannedDoseTime){
	debugger;
	var allTimePoints = null;
	var predoseMapLength = Object.keys(mealDataJson.preDoseMap).length;
	var postdoseMapLength = Object.keys(mealDataJson.postDoseMap).length;
	if(predoseMapLength > 0 )
		preDoseTpsLength = mealDataJson.preDoseMap[treatmentId].length;
	if(postdoseMapLength > 0 )
		postDoseTpsLength = mealDataJson.postDoseMap[treatmentId].length;
	if(doseDone == 'Done')
		allTimePoints = mealDataJson.postDoseMap[treatmentId];
	else
		allTimePoints = mealDataJson.preDoseMap[treatmentId];
	$.each(allTimePoints, function(index, val) {
		var timepointFalg = true;
		var subMeals = null;
		var smtpMapLength = Object.entries(mealDataJson.smtpMap).length;
		if(smtpMapLength > 0){
			var periodMealMap = mealDataJson.smtpMap[periodId];
			if(periodMealMap != undefined){
				var subMealMap = mealDataJson.smtpMap[periodId][val.id];
				if(subMealMap != undefined)
					subMeals = subMealMap[actualSubNo];
			}
		}
		if(subMeals != null){
			if(doseDone == 'Done'){
				var postLength = Object.keys(postDoseCompArr).length;
				if(postLength > 0){
					var arr = postDoseCompArr[actualSubNo];
					if(arr != null && arr != undefined){
						if(arr.indexOf(subMeals.timePointId) == -1){
							arr.push(subMeals.timePointId);
							postDoseCompArr[actualSubNo] = arr;
						}
					}else{
						var arr = [];
						arr.push(subMeals.timePointId);
						postDoseCompArr[actualSubNo] = arr;
					}
				}else{
					var arr = [];
					arr.push(subMeals.timePointId);
					postDoseCompArr[actualSubNo] = arr;
				}
			}else{
				var preLength = Object.keys(perDoseCompArr).length;
				if(preLength > 0){
					var arr = perDoseCompArr[actualSubNo];
					if(arr != null && arr != undefined){
						if(arr.indexOf(subMeals.timePointId) == -1){
							arr.push(subMeals.timePointId);
							perDoseCompArr[actualSubNo] = arr;
						}
					}else{
						var arr = [];
						arr.push(subMeals.timePointId);
						perDoseCompArr[actualSubNo] = arr;
					}
				}else{
					var arr = [];
					arr.push(subMeals.timePointId);
					perDoseCompArr[actualSubNo] = arr;
				}
			}
			timepointFalg = false;
		}
//		debugger;
		if(timepointFalg){
			var doseTime = "";
			if(subDose != null && subDose != undefined){
				console.log(subDose.actualTime);
				doseTime = subDose.actualTime;
			}else doseTime = mealDataJson.ptdDto.plannedDate;
			var dev = mealsTimePointScheduleDeviationCalculation(doseTime, val.timePoint, val.windowPeriod, val.windowPeriodSign, doseDone, periodId, "MINUTES");
//			
			debugger;
			var allowMealFlag = false;
			if(dev >= 0){
				allowMealFlag = true;
			}else {
				if(doseDone == 'Done'){
					var allowMealMapLength = Object.keys(mealDataJson.pwMealAllowMap).length;
					if(allowMealMapLength > 0){
						var allowMealPeriodMap = mealDataJson.pwMealAllowMap[periodId];
						if(allowMealPeriodMap != null && allowMealPeriodMap != undefined){
							var allowMealTpMap = allowMealPeriodMap[val.sign+val.timePoint];
							if(allowMealTpMap != null && allowMealTpMap != undefined){
								var allowSubMap = new Map(Object.entries(allowMealTpMap));
								var allowMealSubArr = [];
								var allowTime = 0;
								for (let [key, value] of allowSubMap) {
									var alsubMealArr = key.split(',');
									if(alsubMealArr != undefined && alsubMealArr.length > 0){
										$.each(alsubMealArr, function(key, alsubVal) {
											allowMealSubArr.push(alsubVal);
										});
									}
									allowTime = value;
								}
								if(dev >= 0){
									allowMealFlag = true;
								}else{
									if(allowMealSubArr.indexOf('All') != -1 || allowMealSubArr.indexOf(actualSubNo) != -1){
										dev = dev + allowTime;
										if(dev >= 0)
											allowMealFlag = true;
										else allowMealFlag = false;
									}
								}
							}
						}
					}else allowMealFlag = false;
				}else {allowMealFlag = false; 
				val.tpWithScheduleTime = val.timePoint;
				nonCollectedTimePoitns.push(val);}
			}
			if(allowMealFlag){
//				debugger;
				//For Schedule time display with time point
				var doseOrPlanTime = null;
				if(doseDone == 'Done')
					doseOrPlanTime = mealDataJson.ptdDto.subjectDoseMap[actualSubNo][periodId];
				else{
					var periodName = mealDataJson.ptdDto.priodNamesMap[periodId];
					if(periodName == "P1")
						doseOrPlanTime = mealDataJson.ptdDto.plannedDate;
					else doseOrPlanTime = getPlanedTimeForOtherPeriods(periodId);
				}
				var scheduleTime = calculateScheduleTimeToTimePoint(doseOrPlanTime, val.timePoint, val.sign);
				if(scheduleTime != null && scheduleTime != "" && scheduleTime != undefined)
					val.tpWithScheduleTime = val.timePoint+"("+scheduleTime+")";
				else val.tpWithScheduleTime = val.timePoint;
				nonCollectedTimePoitns.push(val);
			}
									
		}
	});	
	
	return nonCollectedTimePoitns;
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
function mealsTimePointScheduleDeviationCalculation(doseTime, timePoint, window, sign , doneDone, periodid, windowTimeType){
//	debugger;
	window = Number(window);
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
	var totalMin = 0;
	var timePointArray = timePoint.split(".");
	if(timePointArray != null && timePointArray != undefined){
		if(timePointArray.length == 1){
			totalMin = (Number(timePointArray[0]) * 60);
		}else if(timePointArray.length ==2){
			if(timePointArray[0].trim() != "")
				totalMin = (Number(timePointArray[0]) * 60) + (Number("0."+timePointArray[1]) *60);
			else totalMin =  (Number("0."+timePointArray[1]) *60);
		}
	}
	var doseDate = "";
	var planedTime = null;
	var periodName = mealDataJson.ptdDto.priodNamesMap[periodid];
	if(periodName == "P1")
		planedTime = mealDataJson.ptdDto.plannedDate;
	else planedTime = getPlanedTimeForOtherPeriods(periodid);
	if(planedTime != null && planedTime != undefined){
		if(doseDone != 'Done'){
			doseDate = new Date(planedTime);
		}else{
			doseDate = new Date(doseTime);
		}
		
		if(doseDone != 'Done')
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
	//debugger;
	var reportingDate = null;
	var periodOneId = mealDataJson.ptdDto.priodIdsMap['P1'];
	var firstPeriodReportingDate = mealDataJson.ptdDto.repotingDateMap[periodOneId];
    var doseDate = null;
	var periodName = mealDataJson.ptdDto.priodNamesMap[periodno];
	if(periodno != null && periodno != undefined){
		reportingDate = mealDataJson.ptdDto.repotingDateMap[periodno];
		if(reportingDate != null && reportingDate != undefined){
			var firstPdriodDoseDate = mealDataJson.ptdDto.periodWiseDoseMap[periodOneId];
			if(firstPeriodReportingDate != null && firstPeriodReportingDate != undefined){
				var diffDays = getNumberOfDaysDifferece(new Date(firstPdriodDoseDate), new Date(firstPeriodReportingDate));
				var minutes = diffDays * 24 * 60;
				var ptime = mealDataJson.ptdDto.plannedDate;
				if(reportingDate != null && reportingDate != undefined){
					var periodNo = mealDataJson.ptdDto.periodNoIdsMap[periodno];
					var periodDoseDate = null;
					if(periodNo != 1){
						var prevDosePeriodId = mealDataJson.ptdDto.periodNoMap[periodNo-1]
						periodDoseDate = mealDataJson.ptdDto.periodWiseDoseMap[prevDosePeriodId];
					}else periodDoseDate = firstPeriodReportingDate;
					if(periodDoseDate != null){
						periodDoseDate = new Date(periodDoseDate);
						var min =0;
						var washoutDays = mealDataJson.ptdDto.washoutDays;
						if(washoutDays != null && washoutDays != undefined)
							min = min + (washoutDays * 24 * 60);
						periodDoseDate = new Date(periodDoseDate.getTime() + min*60000);
						//debugger;
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
function checkCollectionStatus(subjectNo, periodPk, treatmentId, timepointId) {
	var flag = true;
	var key = subjectNo + "," + periodPk + ","+ treatmentId+ ","+ timepointId;
	var mealData = mealDataJson.collectedDataMap[key];
	if(mealData != undefined)
		flag = false;
	return flag;
}
function resetMealsTable() {
	$("#barcode").focus();
	$("#example1  tbody").html("");
	$("#example3  tbody").html("");
	$("#example2").hide();
	$("#example3").hide();
	$("#barcodeMsg").html("");
	timPointId ="";
	periodPk = "";
	treatmentId ="";
	timeDeviation = false;
	timeDeviationTime = [];
	startTimepointsIds = [];
	startTimepointsFieldIds = []
	endTimePointIdsTemp = {};
	subjectBrcode ="";
	chkedRdId = "";
	checkedTpStr = "";
}