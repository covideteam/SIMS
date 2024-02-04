var data;
var studyActivityId ="";
var subjectsJson = "";
var startTime ="";
$(function() {
//	alert("Server Time is :"+$("#servertime").html());
//	var appServerTime = $("#servertime").html();
	var appServerTime = $('#serverDate').val();
//	var tempTimeArr = appServerTime.split(":");
	var date = new Date(appServerTime);
	/*date.setHours(tempTimeArr[0]);
	date.setMinutes(tempTimeArr[1]);
	date.setSeconds(tempTimeArr[2]);*/
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	var hour = date.getHours();
	if(hour < 10)
		hour = "0"+hour;
	if(minutes < 10)
		minutes = "0"+minutes;
	startTime = hour+":"+minutes+":00";
	asynchronousAjaxCallBack(mainUrl+ "/study/clinical/getDosingCollectionDetails/" + $("#studyName").val(), dosingCallBack);
	function dosingCallBack(doseData){
		data = doseData;
		$(".loader").hide();
//		registerDosingCollectionSocket();
	}
	function registerDosingCollectionSocket(){
		var url = mainUrl + "/testWebSocket/dosingCollection";
		var eventSource = new EventSource(url);
		eventSource.addEventListener("rtcDosingCollectionData", function(event) {
			debugger;
			console.log("Dosing collection Data");
			var rtcData = JSON.parse(event.data);
			console.log("Event : " + event.data);
			var rtcDataStr = {};
			if(rtcData != null && rtcData != undefined){
				rtcDataStr["subjectNo"] = rtcData.subjectNo;
				rtcDataStr["periodId"] = rtcData.periodId;
				rtcDataStr["treatmentId"] = rtcData.treatmentId;
				rtcDataStr["timePointId"] = rtcData.timePointId;
				rtcDataStr["subjectVitalId"] = rtcData.subjectVitalId;
				rtcDataStr["timePoint"] = rtcData.timePoint;
				rtcDataStr["actualTime"] = rtcData.actualTime;
				
				var jsonString = "";
				var json_obj = null;
				if(rtcDataStr != null && rtcDataStr != undefined){
					jsonString = JSON.stringify(Object.assign({}, rtcDataStr)) 
					json_obj = JSON.parse(jsonString);
				}
				var dosedDataObj = data.sdtpMap;
				if(dosedDataObj != null && dosedDataObj != undefined){
					var dosSubObj = dosedDataObj[rtcData.subjectNo];
					if(dosSubObj != null && dosSubObj != undefined){
						var doseIdObj = dosSubObj[rtcData.timePointId];
						if(doseIdObj != null && doseIdObj != undefined){
							var flag = true;
							var cArr = Object.values(doseIdObj);
							for(var i=0; i<cArr.length; i++){
								if(rtcData.subjectVitalId == cArr[i].subjectVitalId){
									flag = false;
									break;
								}
								if(flag){
									var arrList = Object.values(doseIdObj);
									arrList.push(json_obj);
									doseIdObj = arrList;
									data.sdtpMap[rtcData.subjectNo] = doseIdObj;
								}
							}
						}else{
							var dsTempArr = [];
							dsTempArr.push(json_obj);
							var doseIdMap = new Map();
							doseIdMap.set(rtcData.timePointId, dsTempArr);
							const dosedIdMapObj = Object.fromEntries(doseIdMap);
							data.sdtpMap[rtcData.subjectNo] = dosedIdMapObj;
						}
					}else{
						var doseIdMap = new Map();
						var dsTempArr = [];
						dsTempArr.push(json_obj);
						doseIdMap.set(rtcData.timePointId, dsTempArr);
						const dosedIdMapObj = Object.fromEntries(doseIdMap);
						data.sdtpMap[rtcData.subjectNo] = dosedIdMapObj;
					}
				}else{
					var doseMap = new Map();
					var doseIdMap = new Map();
					var dsTempArr = [];
					dsTempArr.push(json_obj);
					doseIdMap.set(rtcData.timePointId, dsTempArr);
					const dosedIdMapObj = Object.fromEntries(doseIdMap);
					doseMap.set(rtcData.subjectNo, dosedIdMapObj);
					const doseMapObj = Object.fromEntries(doseMap);
					data.sdtpMap = doseMapObj;
				}
				debugger;
				if(rtcData.replacedSubjectId != null && rtcData.replacedSubjectId != undefined){
				    if(data.replaceAvailSubjects != null && data.replaceAvailSubjects != undefined){
				    	var sub = data.subMap[rtcData.subjectNo];
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
				    	delete data.replaceAvailSubjects[rtcData.replacedSubjectId];
				    }
				}else{
					
				}
			}
		});
	}
	/*if (subjectsJson != null && subjectsJson != "undefined"
			&& subjectsJson != "") {
		data = subjectsJson;
	}*/
	$("#barcode").focus();
});

var perodId = "";
var periodNo = "";
var sachetBarcode = "";
var sachetBarcodeScanTime = "";
var subjectBarcode = "";
var subjectBarcodeScanTime = "";
var collectionTime = "";
var fastCriteraComments = "";
var feadCriteraComments = "";
var scanTime = "";
var timePointPk = "";
var perameterIds = {};
var perameterIdsAndType = {};
var activityName = "";
var activityId = "";
var activityCode = "";
// oprational
var replaceStatus = false;
var perams = [];
var gvIds = [];
function errorBarcode(){
	perams = [];
	gvIds = [];
	barcodeField();
	$("#appropriateBox1").html("");
	$("#appropriatecount").val("0");
	$('#subjectReplaceTr').hide();
	$('#subjectReplaceDiv').val(0);
	$('#standByBarcode').val("");
	$('#collectionTimeDiv').hide();
	checkboxTemp = "";
	periodno = 0;
	sachetBarcode = "";
	sachetBarcodeScanTime = "";
	$("#sachetMsg").html("");
	$("#sachetMsg").css('background-color', '');
}
function clearForm() {
	perams = [];
	gvIds = [];
	barcodeField();
	$("#appropriateBox1").html("");
	$("#appropriatecount").val("0");
	$('#subjectReplaceTr').hide();
	$('#subjectReplaceDiv').val(0);
	$('#standByBarcode').val("");
	checkboxTemp = "";
	periodno = 0;
	sachetBarcode = "";
	sachetBarcodeScanTime = "";
	$("#sachetMsg").html("");
	$("#sachetMsg").css('background-color', '');
	afterSubjectBarcode();
}
function afterSubjectBarcode() {
	perams = [];
	gvIds = [];
	subjectBarcode = "";
	subjectBarcodeScanTime = "";
	$("#subjectMsg").html("");
	$("#subjectMsg").css('background-color', '');
	collectionTime = "";
	$("#collectionTimeTr").html("");
	$("#doseDetailsDiv").html("");

	$("#message").val(-1);
	$("#radioButtonValues").html("");
	$("#appropriateBox1").html(checkboxTemp);
	replaceStatus = false;
	fastCriteraComments = "";
	feadCriteraComments = "";
}
function barcodeField() {
	$("#barcode").focus();
	$("#barcode").val("");
}
function onBarcodeScanned(){
	var barcode = $("#barcode").val();
	$("#barcodeMsg").html("");
	var activity = $('#crfName').val();
	if(activity != null && activity != "" && activity != undefined){
		if(barcode != null && barcode != "" && barcode !="undefined"){
			var lastChar = barcode.substr(barcode.length - 1);
			if (lastChar == 'n') {
				var standBybarCode = $('#standByBarcode').val();
				var standByBarcodeSplit = standBybarCode.substr(0, standBybarCode.length - 1).split('a');
				if(standBybarCode != null && standBybarCode != "" && standBybarCode != "undefined"){
					if(parseInt(standByBarcodeSplit[2]) == data.study.id){
						var standByFlag = selectBoxValidation('subjectReplaceDiv', 'subjectReplaceDivMsg');
						if(standByFlag){
							var standByRepSubNo = $('#replacedSubjectNo').val();
							if(standByRepSubNo != null && standByRepSubNo != "0" && standByRepSubNo != "undefined"){
								barcodeChecking(barcode, "StandBy");
							}else
							   $('#subjectReplaceDivMsg').html('Required Field');
						}
					}else{
						$('#barcodeMsg').html("Scanned barcode does not belongs to project: " + data.study.projectNo);
						clearForm();
			    	}
				}else {
					barcodeChecking(barcode, "NormalSubject");
				}
			}else
				$('#barcodeMsg').html("Invalid barcode");
		}else{
			$('#barcodeMsg').html("Scan Barcode");
			clearForm();
		}
	}else{
		$('#activityMsg').html("Select activity");
	}	
}
$("#barcode").on('input', function(e) {
	onBarcodeScanned();
});
function barcodeChecking(barcode, type){
	$("#barcode").focus();
	$("#barcode").val("");
	var prefix = barcode.substr(0, 2);
	
//	scanTime = runningTimeWithSeconds;
	scanTime = getServerEndTimeWithSeconds();
	if(subjectBarcode == null || subjectBarcode == "" || subjectBarcode == "undefined"){
		if (prefix == "02") {
			$('#subjectReplaceDiv').empty();
			$('#subjectReplaceTr').hide();
			$('#sachetMsg').empty();
			$('#collectionTimeTr').hide();
			$('#collectionTimeWithSec').val("");
			$('#message').val("-1");
			$('#replacedSubjectNo').val(0);
			if (checkSubjectBarcode(barcode)) {
				subjectBarcode = barcode;
				var subjectBarcodeSplit = subjectBarcode.substr(0, barcode.length - 1).split('a');
				if(parseInt(subjectBarcodeSplit[2]) == data.study.id){
					subjectBarcodeScanTime = scanTime;
					var subNo = data.subMap[subjectBarcodeSplit[1]].reportingId.subjectNo;
					$("#subjectMsg").html("Subject : "+ subNo);
//								compareBarcodes();
				}else{
					/*Swal.fire({
					  title: '',
					  text:'Scanned barcode not belogs to project :'+data.study.projectNo
					});*/
					$('#barcodeMsg').html("Scanned barcode does not belongs to project: " + data.study.projectNo);
					clearForm();
				}
			}
		}else{
			$('#barcodeMsg').html("Scan subject barcode");
			clearForm();
		}
	}else{
		if(type != "NormalSubject"){
			if (prefix == "02") {
				var sbrSubNo = $('#replacedSubjectNo').val();
//				Swal.fire('Scan Subject '+sbrSubNo+' Sachet Barcode');
				$('#barcodeMsg').html("Scan subject " + sbrSubNo + " IP barcode");
//				errorBarcode();
			}else if (prefix == "03") {
				if (checkSachetarcode(barcode)) {
					$('#collectionTimeTr').show();
					sachetBarcode = barcode;
//					sachetBarcodeScanTime = scanTime;
					sachetBarcodeScanTime = getServerEndTimeWithSeconds();
				}
			}
		}else{
			if (prefix == "02") {
				$('#subjectReplaceDiv').empty();
				$('#subjectReplaceTr').hide();
				$('#sachetMsg').empty();
				$('#collectionTimeTr').hide();
				$('#collectionTimeWithSec').val("");
				$('#message').val("-1");
				$('#replacedSubjectNo').val(0);
				if (checkSubjectBarcode(barcode)) {
					subjectBarcode = barcode;
					var subjectBarcodeSplit = subjectBarcode.substr(0, barcode.length - 1).split('a');
					if(parseInt(subjectBarcodeSplit[2]) == data.study.id){
						subjectBarcodeScanTime = getServerEndTimeWithSeconds();
						var subNo = data.subMap[subjectBarcodeSplit[1]].reportingId.subjectNo;
						$("#subjectMsg").html(subNo);
					}else{
						$('#barcodeMsg').html("Scanned barcode does not belongs to project: " + data.study.projectNo);
						clearForm();
					}
				}
			}else if (prefix == "03") {
				if (checkSachetarcode(barcode)) {
					$('#collectionTimeTr').show();
					sachetBarcode = barcode;
					sachetBarcodeScanTime = getServerEndTimeWithSeconds();
//									compareBarcodes();
				}
			}
		}
		 
	}
	
}


function checkSubjectBarcode(subjectBarcode) {
	$("#subjectMsg").html("");
	$('#standByBarcode').val("");
	/*$('#subjectReplaceDiv').empty();
	$('#subjectReplaceTr').hide();*/
	perodId = "";
	//debugger;
	var flag = true;
	var subjectBarcodeSplit = subjectBarcode.substr(0,
			subjectBarcode.length - 1).split('a');
	var subj = data.subMap[subjectBarcodeSplit[1]];
	if(subj.subjectStatus != "Withdraw" && subj.subjectStatus != "DroupOut"){
		var subject = data.subjectTypes[subjectBarcodeSplit[1]];
		if(subject != null && subject != "" && subject != "undefined"){
			if(subject == "not"){
				if(data.droppedSubjects.length > 0){
					for(var i=0; i<data.droppedSubjects.length; i++){
						if(data.droppedSubjects[i] == subjectBarcodeSplit[1]){
							$('#barcodeMsg').html("Subject has discontinued from the project");
							flag = false;
						}
					}
				}
			}else {
				if(subj.subjectStatus != "Replaced"){
					replaceStatus = true;
					//Stand by code
					$('#standByBarcode').val(subjectBarcode);
					$('#subjectReplaceTr').show();
					$('#subjectReplaceDiv').empty();
					
					var count = Object.keys(data.replaceAvailSubjects).length;
					if(count > 0){
						var selectVal = $('#selectVal').val();
						$('#subjectReplaceDiv').append('<option value="0">---'+selectVal+'---</option>');
						for (const [key, value] of Object.entries(data.replaceAvailSubjects)) {
							  console.log(key +":"+ value);
							 $('#subjectReplaceDiv').append('<option value='+value+'>'+value+'</option>');
						}
					}else{
						replaceStatus = false;
						
						Swal.fire({
							  title: '',
							  text:'Subjects to replace are not available. Do you want to continue scan other subject barcode?',
							  showDenyButton: false,
							  showCancelButton: true,
							  confirmButtonText: 'OK',
							  denyButtonText: 'NOT OK',
							}).then((result) => {
							  
								Swal.fire({
									  title: '',
									  text:'Replacing Subjects are not Available. Do you want to continue scan other subject barcodes?',
									  showDenyButton: false,
									  showCancelButton: true,
									  confirmButtonText: 'OK',
									  denyButtonText: 'NOT OK',
									}).then((result) => {
									  /* Read more about isConfirmed, isDenied below */
									  if (result.isConfirmed) {
									    Swal.fire('Continue Scaning', '', 'success');
									    $('#standByBarcode').val("");
										$('#subjectReplaceTr').hide();
										$('#subjectReplaceDiv').empty();
										clearForm();
									  } else if (result.isDenied) {
									    Swal.fire('Changes are not saved', '', 'info')
									  }
									})
							})
					}
				}
			}
		}else{
			$('#barcodeMsg').html("Subject does not exists");
			flag = false;
		}
	}else{
		var subNo = data.subMap[subjectBarcodeSplit[1]].reportingId.subjectNo;
		if(subj.subjectStatus == "Withdraw")
			$('#barcodeMsg').html("Subject " + subNo + " has withdrawn from the project");
		else $('#barcodeMsg').html("Subject " + subNo + " has dropped out from the project");
		flag = false;
	}
	return flag;
}
var doseMap = null;
//debugger;
function checkSachetarcode(sachetBarcode) {
	var flag = true;
	collectionTime ="";
	var sachetBarcodeSplit = sachetBarcode.substr(0, sachetBarcode.length - 1).split('a');
	if(subjectBarcode == null || subjectBarcode == "" || subjectBarcode == "undefined"){
    	$('#barcodeMsg').html("Scan subject barcode first");
		flag = false;
    }else{
    	//debugger;
    	var subBarcodeSplit = subjectBarcode.substr(0, subjectBarcode.length - 1).split('a');
    	if(parseInt(subBarcodeSplit[2]) == data.study.id){
    		var sbrSubNo = $('#replacedSubjectNo').val();
    		if(sbrSubNo == null || sbrSubNo == "" || sbrSubNo == "0" || sbrSubNo == "undefined"){
    			var subj = data.subMap[subBarcodeSplit[1]];
    			if(subj.stdSubjectId != null && subj.stdSubjectId != undefined){
    				sbrSubNo = subj.stdSubjectId.subjectNo;
    			}else 
    				sbrSubNo =  subj.subjectNo;
//    			sbrSubNo =  subBarcodeSplit[1];
    		}
    			
    		
    		if(sbrSubNo == sachetBarcodeSplit[3]){
    			if(data.subjectPeriodsMap[subBarcodeSplit[1]].id == sachetBarcodeSplit[1]){
    				var subTreatments = data.subwTrMap[sachetBarcodeSplit[3]];
    				if(subTreatments != null && subTreatments != "" && subTreatments != "undefined"){
    					var subjectTreatment = subTreatments[sachetBarcodeSplit[1]] //subject period treatment
    					if(subjectTreatment != null && subjectTreatment != "" && subjectTreatment != "undefined"){
    						var twdoseIds = data.trwDoseIdsMap[subjectTreatment];
    						var fdtpId = data.firstDoseMap[subjectTreatment];
    						var fdTimePoint = data.dpMap[fdtpId].timePoint; 
    						var doseTimePoint ="";
    						if(twdoseIds.length > 0){
    							var seqArr = [];
    							var seqDoseArr = [];
    							for (const [key, value] of Object.entries(twdoseIds)) {
    								seqArr.push(data.orderedDoseMap[value]);
    								seqDoseArr[data.orderedDoseMap[value]] = value;
    							}
    							seqArr.sort();
    							if(seqArr.length > 0){
    								var curDoseId = sachetBarcodeSplit[4];
    								var curSequence = data.orderedDoseMap[curDoseId];
    								var curdoseTreatment = data.dpMap[curDoseId];
    								doseMap = data.dpMap[curDoseId];
    								doseTimePoint = doseMap.timePoint;
    								if(curdoseTreatment.treatmentInfo.id != subjectTreatment){
    									$('#barcodeMsg').html("IP barcode does not belongs to subject treatment");
    									errorBarcode();
    								}else{
//        								var curdoseIndex = seqArr.indexOf(curSequence);
        								timePointPk = sachetBarcodeSplit[4];
    									perodId = data.subjectPeriodsMap[subBarcodeSplit[1]].id;
                        	    		periodNo = data.subjectPeriodsMap[subBarcodeSplit[1]].id;
        								var conFlag = true;
        								var doseIdVal = seqDoseArr[curSequence];
        								var treatment = ""; var displayStr = ""; 
        								var dosedTpId = ""; var dosedTp = ""; var dosedActualTime = "";
        								/*for (const [key, value] of Object.entries(data.firstDoseMap)) {
                							fdtpId = key;
                							fdTimePoint = value;
                						}
        								*/var dosedIdsArr = [];
        								var dosedTpsArr = data.sdtpMap[subBarcodeSplit[1]];
        								if(dosedTpsArr != undefined){
        									var dosedTimePointArr = dosedTpsArr[curDoseId];
            								if(dosedTimePointArr != undefined){
            									for(var g=0; g<dosedTimePointArr.length; g++){
//        											dosedIdsArr.push(dosedTimePointArr[g].id);
        											dosedIdsArr.push(dosedTimePointArr[g].subjectVitalId);
        										}
            								}
        								}else{
    										if(parseInt(fdtpId) == parseInt(curDoseId))
    											conFlag = true;
    										else{
    											var tmap = data.sdtpMap[subBarcodeSplit[1]];
    											if(tmap != undefined){
    												var tArr = dosedTpsArr[fdtpId];
//        											var fitdp = data.sdtpMap[fdtpId];
        											var fitdp = dosedTpsArr[fdtpId];
//        											if(doseIdVal == "" && dosedTimePointArr == undefined){
        												if(parseInt(doseIdVal) == parseInt(curDoseId) && fitdp != undefined){
            												conFlag = true;
            											}else{
            												conFlag = false;
            												$('#barcodeMsg').html("Subject first dose not completed");
            												errorBarcode();
            											}
//        											}else conFlag = true;
    											}else{
    												conFlag = false;
    												$('#barcodeMsg').html("Subject first dose not completed");
    												errorBarcode();
    											}
    										}
                						}
        								if(dosedIdsArr.length > 0){
        									for(var p=1; p<=parseInt(curSequence); p++){
            									doseIdVal = seqDoseArr[p];
        										if(dosedIdsArr.indexOf(curDoseId) == -1){
        											if(parseInt(doseIdVal) == parseInt(curDoseId)){
        												var subNo = data.subMap[subBarcodeSplit[1]].reportingId.subjectNo;
        							    				$('#barcodeMsg').html("Subject " + subNo + " is already dosed");
        												conFlag = false;
        												errorBarcode();
        											}
        										}else{
        											if(parseInt(doseIdVal) == parseInt(curDoseId)){
        												conFlag = true;
        											}else{
        												errorBarcode();
        												conFlag = false;
        												$('#barcodeMsg').html("Subject is not dosed for timepoint ("+data.allDoseMap[doseIdVal] + ")");
        											}
        										}
            								}
        								}/*else{
        									conFlag = true;
        									errorBarcode();
        								}*/
        								if(conFlag){
    										if(parseInt(fdtpId) == parseInt(curDoseId)){
    											flag = displaySachetDetails(sachetBarcodeSplit, treatment, displayStr, doseTimePoint);
                								if(flag)
                									$('#criteriaDeviation').val();
                							}else{
                								var dosedTpId = "";
                								var dosedTp = "";
                								var dosedActualTime = "";
                								var dtArr = data.sdtpMap[subBarcodeSplit[1]];
                								var dtpCount = Object.keys(dtArr[fdtpId]).length;
                        						if(dtpCount > 0){
                        							for (const [key, value] of Object.entries(dtArr[fdtpId])) {
                        								dosedTpId = key;
//                        								dosedTp = value.doseTimePoints.timePoint;
                        								dosedTp = value.timePoint;
                        								dosedActualTime = value.actualTime;
                        							}
                        						}
                								//Second dose or not check and  devidation
                        						var devTpStr = dosedTp.split(".");
                							    var cruTPStr = data.allDoseMap[curDoseId].split(".");
                							    var hrDiff = parseInt(cruTPStr[0]) - parseInt(devTpStr[1]);
                							    var minDiff = parseInt(cruTPStr[1]) - parseInt(devTpStr[1]);
                							    
                								var doseDate = new Date(dosedActualTime);
            									let doseTime = doseDate.toLocaleTimeString('it-IT'); //en-US it can print Am also
            									var currentDate = new Date($('#serverDate').val());
            									let currentDateTime = currentDate.toLocaleTimeString('it-IT');
            									let doseHrs = "0"; let doseMin = "0"; let doseSec = "0"; let preSentHrs = "0";
            									let preMin ="0"; let preSec ="0"; var curdosewinPeriod = "0"; var curdosewinSign = "";
            									var curdoseType = ""; var cdateHr = "0"; var cdateMin = "0";
            									var tpdeviation = false;
            									if(doseTime != null && doseTime != "" && doseTime != "undefined"){
            										var doseStr = doseTime.split(":"); 
            										var curDateStr = currentDateTime.split(":");
            										if(doseStr.length > 0 && currentDateTime.length > 0){
            											var curHr = parseInt(doseStr[0]) + parseInt(hrDiff);
            											var curMin = parseInt(doseStr[1]) + parseInt(minDiff);
            											cdateHr = curDateStr[0];
            											cdateMin = curDateStr[1];
            											
            											curdosewinPeriod = doseMap.windowPeriod;
            											curdosewinSign = doseMap.windowPeriodSign;
            											curdoseType = doseMap.windowPeriodType.code;
            											if(curdosewinSign == "" || curdosewinSign == "PLUS"){
            												if(curdoseType == "HOURS"){
            													preSentHrs = parseInt(curHr) + parseInt(curdosewinPeriod);
            												}else if(curdoseType == "MINUTES"){
            													preMin = parseInt(curMin) + parseInt(curdosewinPeriod);
            													if(preMin > 59){
            														var hrVal = Math.floor(preMin/60);
            														var remainder = preMin%60;
            														preSentHrs = preSentHrs + hrVal;
            														preMin = remainder;
            													}
            												}else if(curdoseType == "DAYS"){
            													var diffDate = new Date(doseDate);
            													diffDate.setDate(diffDate.getDate() + curdosewinPeriod); 
            													var dateFormated = diffDate.toISOString().substr(0,10);
            													let diffDateTime = diffDate.toLocaleTimeString('it-IT');
            													var diffDateArr = diffDateTime.split(":");
            													var formatedCurDate = currentDate.toISOString().substr(0,10);
            													var dateCompareFlag = false;
            													var Difference_In_Time = formatedCurDate.getTime() - dateFormated.getTime();
            												    var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
            												    if(Difference_In_Days <= curdosewinPeriod){
            												    	$('#timeDeviationTime').val("0");
            												    	dateCompareFlag = false;
            												    }else{
            												    	var daysLength = Difference_In_Days.length;
            												    	if(daysLength <= 10)
            													    	$('#timeDeviationTime').val("+"+Difference_In_Days +" Day");
            												    	else
            												    		$('#timeDeviationTime').val("+"+Difference_In_Days +" Days");
            												    	dateCompareFlag = true;
            												    }
            													if(dateCompareFlag){
            														if(diffDateArr.length > 0){
            															if(diffDateArr[0] == doseStr[0]){
            																if(diffDateArr[1] == doseStr[1]){
            																	if(diffDateArr[2] == doseStr[2])
            																		tpdeviation = false;
            																	else{
            																		var deviationMins = parseInt(diffDateArr[2]) - parseInt(doseStr[2]);
            																		$('#timeDeviationTime').val("+"+deviationMins +" Sec");
            																		tpdeviation = true;
            																	}
            																}else{
            																	var deviationMins = parseInt(diffDateArr[1]) - parseInt(doseStr[1]);
            																	$('#timeDeviationTime').val("+"+deviationMins +" Minutes");
            																	tpdeviation = true;
            																}
            															}else{ 
            																var deviationHrs = parseInt(diffDateArr[0]) - parseInt(doseStr[0]);
            																$('#timeDeviationTime').val("+"+deviationHrs +" Hrs");
            															    tpdeviation = true;
            															}
            														}
            													}
            												}
            												if(curdoseType != "DAYS"){
            													if(cdateHr == preSentHrs){
            														if(preMin <= cdateMin){
            															tpdeviation = false;
            														}else{
            															var deviationMins = parseInt(cdateMin) - parseInt(preMin);
            															$('#timeDeviationTime').val("+"+deviationMins +" Minutes");
            															tpdeviation = true;
            														}
            													}else{
            														var deviationHrs = parseInt(cdateHr) - parseInt(preSentHrs);
            														$('#timeDeviationTime').val("+"+deviationHrs +" Hrs");
            														tpdeviation = true;
            													}
            												}
            											
            												if(tpdeviation){
            													if(data.dsDto != null && data.dsDto != undefined){
            														if(data.dsDto.devionCodeMap != null && data.dsDto.devionCodeMap != undefined){
            															$('#timeDeviationCode').val(data.dsDto.devionCodeMap['TPD']);
            														}
            													}
            													$('#timeDeviation').val("true");
            													Swal.fire({
            				  									  title: '',
            				  									  text:'There is a deviation of '+$('#timeDeviationTime').val()
            				  									});
            												}
            												flag = displaySachetDetails(sachetBarcodeSplit, treatment, displayStr, doseTimePoint);
            											}else if(curdosewinSign == "MINUS"){
            												if(curdoseType == "HOURS"){
            													preSentHrs = parseInt(curHr) - parseInt(curdosewinPeriod);
            												}else if(curdoseType == "MINUTES"){
            													preMin = parseInt(curMin) - parseInt(curdosewinPeriod);
            													if(preMin < 0){
            														preSentHrs = parseInt(preSentHrs -1);
            													}
            												}else if(curdoseType == "DAYS"){
            													var diffDate = new Date(doseDate);
            													diffDate.setDate(diffDate.getDate() - curdosewinPeriod); 
            													var dateFormated = diffDate.toISOString().substr(0,10);
            													let diffDateTime = diffDate.toLocaleTimeString('it-IT');
            													var diffDateArr = diffDateTime.split(":");
            													var formatedCurDate = currentDate.toISOString().substr(0,10);
            													var dateCompareFlag = false;
            													var Difference_In_Time = formatedCurDate.getTime() - dateFormated.getTime();
            												    var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
            												    if(Difference_In_Days <= curdosewinPeriod){
            												    	$('#timeDeviationTime').val("0");
            												    	dateCompareFlag = false;
            												    }else{
            												    	var daysLength = Difference_In_Days.length;
            												    	if(daysLength <= 10)
            													    	$('#timeDeviationTime').val("-"+Difference_In_Days +" Day");
            												    	else
            												    		$('#timeDeviationTime').val("-"+Difference_In_Days +" Days");
            												    	dateCompareFlag = true;
            												    }
            													if(dateCompareFlag){
            														if(diffDateArr.length > 0){
            															if(diffDateArr[0] == doseStr[0]){
            																if(diffDateArr[1] == doseStr[1]){
            																	if(diffDateArr[2] == doseStr[2])
            																		tpdeviation = false;
            																	else{
            																		var deviationMins = parseInt(diffDateArr[2]) - parseInt(doseStr[2]);
            																		$('#timeDeviationTime').val("-"+deviationMins +" Sec");
            																		tpdeviation = true;
            																	}
            																}else{
            																	var deviationMins = parseInt(diffDateArr[1]) - parseInt(doseStr[1]);
            																	$('#timeDeviationTime').val("-"+deviationMins +" Minutes");
            																	tpdeviation = true;
            																}
            															}else{ 
            																var deviationHrs = parseInt(diffDateArr[0]) - parseInt(doseStr[0]);
            																$('#timeDeviationTime').val("-"+deviationHrs +" Hrs");
            															    tpdeviation = true;
            															}
            														}
            													}
            												}
            												if(curdoseType != "DAYS"){
            													if(cdateHr == preSentHrs){
            														if(preMin <= cdateMin){
            															tpdeviation = false;
            														}else{
            															var deviationMins = parseInt(cdateMin) - parseInt(preMin);
            															$('#timeDeviationTime').val("-"+deviationMins +" Minutes");
            															tpdeviation = true;
            														}
            													}else{
            														var deviationHrs = parseInt(cdateHr) - parseInt(preSentHrs);
            														$('#timeDeviationTime').val("-"+deviationHrs +" Hrs");
            														tpdeviation = true;
            													}
            												}
            												if(tpdeviation){
            													if(data.dsDto != null && data.dsDto != undefined){
            														if(data.dsDto.devionCodeMap != null && data.dsDto.devionCodeMap != undefined){
            															$('#timeDeviationCode').val(data.dsDto.devionCodeMap['TPD']);
            														}
            													}
            													$('#timeDeviation').val("true");
            													Swal.fire({
            				  									  title: '',
            				  									  text:'There is a deviation of '+$('#timeDeviationTime').val()
            				  									});
            													
            												}
            												flag = displaySachetDetails(sachetBarcodeSplit, treatment, displayStr, doseTimePoint);
            											}else{
            												var postitiveFlag = false;
            												var negitiveFlag = false;
            												var postiveHrs = "0";
            												var positiveMins = "0";
            												var positiveDaysDate = "0";
            												var negitiveHrs = "0";
            												var negitiveMin = "0";
            												var negitiveDaysDate = "0";
            												//Positive time
            												if(curdoseType == "HOURS"){
            													postiveHrs = parseInt(curHr) + parseInt(curdosewinPeriod);
            												}else if(curdoseType == "MINUTES"){
            													positiveMins = parseInt(curMin) + parseInt(curdosewinPeriod);
            													if(positiveMins > 59){
            														var hrVal = Math.floor(positiveMins/60);
            														var remainder = preMin%60;
            														postiveHrs = postiveHrs + hrVal;
            														positiveMins = remainder;
            													}
            												}else if(curdoseType == "DAYS"){
            													var diffDate = new Date(doseDate);
            													diffDate.setDate(diffDate.getDate() + curdosewinPeriod); 
            													positiveDaysDate = diffDate;
            													/*var dateFormated = diffDate.toISOString().substr(0,10);
            													let diffDateTime = diffDate.toLocaleTimeString('it-IT');
            													var diffDateArr = diffDateTime.split(":");
            													var formatedCurDate = currentDate.toISOString().substr(0,10);
            													var dateCompareFlag = false;
            													var Difference_In_Time = formatedCurDate.getTime() - dateFormated.getTime();
            												    var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
            												    if(Difference_In_Days <= curdosewinPeriod){
            												    	$('#timeDeviationTime').val("0");
            												    	dateCompareFlag = false;
            												    }else{
            												    	if(Difference_In_Days <= 10)
            													    	$('#timeDeviationTime').val(Difference_In_Days +" Day");
            												    	else
            												    		$('#timeDeviationTime').val(Difference_In_Days +" Days");
            												    	dateCompareFlag = true;
            												    }
            													if(dateCompareFlag){
            														if(diffDateArr.length > 0){
            															if(diffDateArr[0] == doseStr[0]){
            																if(diffDateArr[1] == doseStr[1]){
            																	if(diffDateArr[2] == doseStr[2])
            																		tpdeviation = false;
            																	else{
            																		var deviationMins = parseInt(diffDateArr[2]) - parseInt(doseStr[2]);
            																		$('#timeDeviationTime').val(deviationHrs +" Sec");
            																		tpdeviation = true;
            																	}
            																}else{
            																	var deviationMins = parseInt(diffDateArr[1]) - parseInt(doseStr[1]);
            																	$('#timeDeviationTime').val(deviationHrs +" Minutes");
            																	tpdeviation = true;
            																}
            															}else{ 
            																var deviationHrs = parseInt(diffDateArr[0]) - parseInt(doseStr[0]);
            																$('#timeDeviationTime').val(deviationHrs +" Hrs");
            															    tpdeviation = true;
            															}
            														}
            													}*/
            												}
            												//Negitive test case
            												if(curdoseType == "HOURS"){
            													negitiveHrs = parseInt(curHr) - parseInt(curdosewinPeriod);
            												}else if(curdoseType == "MINUTES"){
            													negitiveMin = parseInt(curMin) + parseInt(curdosewinPeriod);
            													if(negitiveMin < 0){
            														negitiveHrs = parseInt(negitiveHrs -1);
            													}
            												}else if(curdoseType == "DAYS"){
            													var diffDate = new Date(doseDate);
            													diffDate.setDate(diffDate.getDate() - curdosewinPeriod); 
            													negitiveDaysDate = diffDate;
            												}
            												if(curdoseType != "DAYS"){
            													if((negitiveHrs >= cdateHr) || (cdateHr  <= postiveHrs)){
            														if((negitiveMin  >= cdateMin) || (negitiveMin <= cdateMin)){
            															tpdeviation = false;
            														}else{
            															if(negitiveMin  >= cdateMin){
            																var deviationMins = parseInt(cdateMin) - parseInt(negitiveMin);
            																$('#timeDeviationTime').val("-"+deviationMins +" Minutes");
            																tpdeviation = true;
            															}else{
            																var deviationMins = parseInt(cdateMin) - parseInt(postiveHrs);
            																$('#timeDeviationTime').val("+"+deviationMins +" Minutes");
            																tpdeviation = true;
            															}
            														}
            													}else {
            														if(negitiveHrs >= cdateHr){
            															var deviationHrs = parseInt(cdateHr) - parseInt(negitiveHrs);
            															$('#timeDeviationTime').val("-"+deviationHrs +" Hrs");
            															tpdeviation = true;
            														}else{
            															var deviationHrs = parseInt(cdateHr) - parseInt(postiveHrs);
            															$('#timeDeviationTime').val("+"+deviationHrs +" Hrs");
            															tpdeviation = true;
            														}
            													}
            												}else{
            													var posdate = positiveDaysDate.toISOString().substr(0,10);
            													var negdate = negitiveDaysDate.toISOString().substr(0,10);
            													var formatedCurDate = currentDate.toISOString().substr(0,10);
            													let posdateTime = positiveDaysDate.toLocaleTimeString('it-IT');
            													var posdateTimeArr = posdateTime.split(":");
            													let negdateTime = negitiveDaysDate.toLocaleTimeString('it-IT');
            													var negdateTimeArr = negdateTime.split(":");
            													
            													var pos_difference_in_time = formatedCurDate.getTime() - posdate.getTime();
            												    var pos_difference_in_days = pos_difference_in_time / (1000 * 3600 * 24);
            												    
            												    var neg_difference_in_time = formatedCurDate.getTime() - negdate.getTime();
            												    var neg_difference_in_days = neg_difference_in_time / (1000 * 3600 * 24);
            												    
            												    if((neg_difference_in_days >= curdosewinPeriod) || (curdosewinPeriod <= pos_difference_in_days)){
            												    	$('#timeDeviationTime').val("0");
            												    	tpdeviation = true;
            												    }else{
            												    	if((neg_difference_in_days >= curdosewinPeriod)){
            												    		//Negitive time checking
            												    		if(negdateTimeArr.length > 0){
            												    			if(negdateTimeArr[0] == doseStr[0]){
            																	if(negdateTimeArr[1] == doseStr[1]){
            																		if(negdateTimeArr[2] == doseStr[2])
            																			tpdeviation = false;
            																		else{
            																			var deviationMins = parseInt(negdateTimeArr[2]) - parseInt(doseStr[2]);
            																			$('#timeDeviationTime').val("-"+deviationMins +" Sec");
            																			tpdeviation = true;
            																		}
            																	}else{
            																		var deviationMins = parseInt(negdateTimeArr[1]) - parseInt(doseStr[1]);
            																		$('#timeDeviationTime').val("-"+deviationMins +" Minutes");
            																		tpdeviation = true;
            																	}
            																}else{ 
            																	var deviationHrs = parseInt(diffDateArr[0]) - parseInt(doseStr[0]);
            																	$('#timeDeviationTime').val("-"+deviationHrs +" Hrs");
            																    tpdeviation = true;
            																}
            												    		}
            												    		//Positive time checking
            												    		if(posdateTimeArr.length > 0){
            												    			if(posdateTimeArr[0] == doseStr[0]){
            																	if(posdateTimeArr[1] == doseStr[1]){
            																		if(posdateTimeArr[2] == doseStr[2])
            																			tpdeviation = false;
            																		else{
            																			var deviationMins = parseInt(posdateTimeArr[2]) - parseInt(doseStr[2]);
            																			$('#timeDeviationTime').val("+"+deviationMins +" Sec");
            																			tpdeviation = true;
            																		}
            																	}else{
            																		var deviationMins = parseInt(posdateTimeArr[1]) - parseInt(doseStr[1]);
            																		$('#timeDeviationTime').val("+"+deviationMins +" Minutes");
            																		tpdeviation = true;
            																	}
            																}else{ 
            																	var deviationHrs = parseInt(posdateTimeArr[0]) - parseInt(doseStr[0]);
            																	$('#timeDeviationTime').val("+"+deviationHrs +" Hrs");
            																    tpdeviation = true;
            																}
            												    		}
            												    	}
            												    }
            												}
            												if(tpdeviation){
            													if(data.dsDto != null && data.dsDto != undefined){
            														if(data.dsDto.devionCodeMap != null && data.dsDto.devionCodeMap != undefined){
            															$('#timeDeviationCode').val(data.dsDto.devionCodeMap['TPD']);
            														}
            													}
            													$('#timeDeviation').val("true");
            													Swal.fire({
            				  									  title: '',
            				  									  text:'There is a deviation of '+$('#timeDeviationTime').val()
            				  									});
            													
            												}
            												flag = displaySachetDetails(sachetBarcodeSplit, treatment, displayStr, doseTimePoint);
            											}
            										}
            									}
                							}
    									}
    								}
								}else{
									var subNo = data.subMap[subBarcodeSplit[1]].reportingId.subjectNo;
									$('#barcodeMsg').html("Subject :"+subNo+" "+p+"st Dose not completed.");
									errorBarcode();
								}
    						}else{
    							$('#barcodeMsg').html("IP details not available");
    							errorBarcode();
    						}
    					}else{
    						$('#barcodeMsg').html("Invalid treatment IP barcode");
    						errorBarcode();
    					}
					}else{
						var subNo = data.subMap[subBarcodeSplit[1]].reportingId.subjectNo;
						$('#barcodeMsg').html("IP details not available for subject "+subNo);
						errorBarcode();
					}
				}else{
					$('#barcodeMsg').html("Scanned IP barcode does not belong to period "+data.subjectPeriodsMap[sachetBarcodeSplit[3]].periodName);
					errorBarcode();
				}
    		}else{
    			/*Swal.fire({
					  title: '',
					  text:'Scanned Sachet barcode not belogs to period :'+data.subjectPeriodsMap[sachetBarcodeSplit[3]].periodName
					});*/
    			var subNo = data.subMap[subBarcodeSplit[1]].reportingId.subjectNo;
    			$('#barcodeMsg').html("Scanned IP barcode does not belong to subject "+subNo);
//    			errorBarcode();
        		flag = false;
    		}
		}else{
			/*Swal.fire({
				  title: '',
				  text:'Scanned Sachet barcode not belogs to subject :'+subBarcodeSplit[1]
				});*/
			$('#barcodeMsg').html("Scanned barcode does not belong to project " + data.study.projectNo);
    		flag = false;
		}
  }
	return flag;
}
	

 
function displaySachetDetails(sachetBarcodeSplit, treatment, displayStr, doseTimePoint){
	if ('${treatmentShow eq true}') {
		treatment = data.treatment[sachetBarcodeSplit[2]];
		displayStr = "Period : "
			+ data.subjectPeriodsMap[sachetBarcodeSplit[3]].periodName
			+ ", Timepoint : " + doseTimePoint
			+", Treatment : "+ treatment
	}else{
		displayStr = "Period : "
				+ data.subjectPeriodsMap[sachetBarcodeSplit[3]].periodName
				+ ", Timepoint : "
				+ data.timPoints[sachetBarcodeSplit[4]].timePoint
	}
	$("#sachetMsg").html(displayStr);
	$('#collectionTimeDiv').show();
	$('#doseDetailsDiv').show();
	var dtimePoint = "'"+doseTimePoint+"'"
	var trCode = data.treatment[sachetBarcodeSplit[2]];
	//DrugDetails
	var noOfUnits = "";
	var nameOfIp = "";
	var batchNo = "";
	var expDate = "";
	var drugInfMap = data.drugInfoMap[trCode];
	if(drugInfMap != null && drugInfMap != undefined){
		noOfUnits = drugInfMap.noOfUnits;
		nameOfIp = drugInfMap.nameOfIp;
		batchNo = drugInfMap.batchNo
		expDate = new Date(drugInfMap.expDate);
		expDate = $.datepicker.formatDate('yy-mm-dd', expDate);
	}
	var str = '<table border="1" style="margin-left:26%;">'
		     +'<tr><td>Study No :</td>'
		     +'<td style="font-weight:bold;" colspan="2">'+data.subjectPeriodsMap[sachetBarcodeSplit[3]].study.projectNo+'</td></tr>'
			 +'<tr><td>Period No :</td>'
		    +'<td>'+data.subjectPeriodsMap[sachetBarcodeSplit[3]].periodNo+'</td><td rowspan="3" style="font-size: 160%; text-align:center; font-weight:bold;">'+trCode+'</td></tr>'
		    +'<tr><td>Subject No :</td>'
		    +'<td style="font-weight:bold;">'+sachetBarcodeSplit[3]+'</td></tr>'
		    +'<tr><td>No. of Units :</td>'
		    +'<td>'+noOfUnits+'</td></tr>'
		    +'<tr><td>Name of IP :</td>'
		    +'<td colspan="2" style="word-wrap:break-word; width:70%;" >' +  data.treatmentProductInformation[sachetBarcodeSplit[2]] + '</td></tr>'
		    +'<tr><td>Batch No :</td>'
		    +'<td colspan="2">'+batchNo+'</td></tr>'
		    +'<tr><td>Expiry Date :</td>'
		    +'<td colspan="2">'+expDate+'</td></tr></table><br/>';
	$("#doseDetailsDiv").html(str);
	$("#collectionTimeTr").html('<input type="button" id = "collectionButton" value="Dose" class="btn btn-warning btn-sm" onclick="colletionTime('+dtimePoint+')"/><font color=\"red\" id=\"clollectoinMesg\"></font>');
	dispalyeDoisePerameters(sachetBarcodeSplit);
	return true;
}

function dispalyeDoisePerameters(sachetBarcodeSplit) {
	perams = [];
	var elementFlag = false;
	timePointPk = sachetBarcodeSplit[4];
	if (data.perameters.gpdDtoList.length > 0) {
		// console.log(data.perameters.gpdDtoList.length);
		for (var groupDtoIndex = 0; groupDtoIndex < data.perameters.gpdDtoList.length; groupDtoIndex++) {
			var value = data.perameters.gpdDtoList[groupDtoIndex];
			if(groupDtoIndex = 0){
				activityName = value.activityName;
				activityId = value.activityId;
			}
			var dosePerameterInfo = "<table id='dosePerameterTable' style='width:100%;'><tr><th>Dose Parameter</th>"
					+ "<th>Value</th><td></td><tr></table>";
			$("#appropriateBox1").html(dosePerameterInfo);
			var paramVals = value.parameterDto;
			if(paramVals != null && paramVals != "undefined"){
				$.each(paramVals,function(index, peramObject) {
					var ctType = peramObject.controlType.contentCode;
					if(ctType == "RB" || ctType == "CB"){
						if(perams.indexOf(ctType) == -1)
							perams[peramObject.parameterId] = peramObject.controlType.contentCode;
					}else
						perams[peramObject.parameterId] = peramObject.controlType.contentCode;
					var element = "<tr><th>"
						+ peramObject.parameterName
						+ "</th><td>";
					if (peramObject.controlType.contentCode == 'TA') {
						element += "<textarea name=\""
								+ peramObject.parameterId
								+ "\" id=\""
								+ peramObject.parameterId
								+ "\"></textarea>";
					} else if (peramObject.controlType.contentCode == 'RB') {
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
									+ "\" >"
									+ peramObject.controlType.valuesDto[index].valueName
									+ "&nbsp;&nbsp;&nbsp;";
						}
					} else if (peramObject.controlType.contentCode == 'CB') {
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
									+ "\" >"
									+ peramObject.controlType.valuesDto[index].valueName
									+ "&nbsp;&nbsp;&nbsp;";
						}
					} else if (peramObject.controlType.contentCode == 'SB') {
						gvIds[peramObject.parameterId] = peramObject.controlType.valuesDto[index].valueId;
						element += "<select  name=\""
								+ peramObject.parameterId
								+ "\" id=\""
								+ peramObject.parameterId
								+ "\" >";
						element += "<option value=\"-1\">--Select--</option>";
						for (var index = 0; index < peramObject.controlType.valuesDto.length; index++) {
							element += "<option value=\""
									+ peramObject.controlType.valuesDto[index].valueId
									+ ">"
									+ peramObject.controlType.valuesDto[index].valueName
									+ "</option>";
						}
						element += "</select>";
					} else if (peramObject.controlType.contentCode == 'DP') {
						element += "<input type=\"text\" name=\""
								+ peramObject.parameterId
								+ "\" id=\""
								+ peramObject.parameterId
								+ "\" />";
					} else if (peramObject.controlType.contentCode == 'TP') {
						element += "<input type=\"text\" name=\""
								+ peramObject.parameterId
								+ "\" id=\""
								+ peramObject.parameterId
								+ "\" />";
					}

					$("#dosePerameterTable")
							.append(
									element
											+ "</td><td><font color='red' id=\""
											+ peramObject.parameterId
											+ "msg\"></font></td></tr>");
					elementFlag = true;
					perameterIds[peramObject.parameterId] = peramObject.parameterId
					perameterIdsAndType[peramObject.parameterId] = peramObject.controlType.contentCode;
				});
			}
		}
	}

	if (elementFlag) {
		timePointPk = sachetBarcodeSplit[4];
	} else {
		/*$("#appropriateBox1").append(
				"Dose Perameters not found for the timepoint : "
						+ data.timPoints[sachetBarcodeSplit[4]].timePoint
						+ " Treatment : "
						+ data.treatment[sachetBarcodeSplit[2]].treatmentNo);*/
	}
}



function colletionTime(timePoint) {
	$('#save_btn').removeAttr("disabled");
	var flag = false;
	fastCriteraComments = "";
	feadCriteraComments = "";
	var sachetBarcodeSplit = sachetBarcode.substr(0, sachetBarcode.length - 1).split('a');
	var subBarcodeSplit = subjectBarcode.substr(0, subjectBarcode.length - 1).split('a');
	var fastCriteriaFlag = false;
	var fedCriteriaFlag = false;
	var meals = data.mealsDetials[sachetBarcodeSplit[1]+","+subBarcodeSplit[1]];
	
	//Fasting Criteria Checking
	var fastCriteriaTimeVal = "";
	var fedCriteriaTimeVal = "";
	var fastingCriteriaVal = doseMap.fastingCriteria;
	var criteriaType = data.projectType;
	var fastCrFlag = false;
	var fedCrFlag = false;
	if(criteriaType == "FAST"){
		fastCrFlag = true;
	}else if(criteriaType == "FED"){
		fedCrFlag = true;
	}else if(criteriaType == "FASTANDFED"){
		fastCrFlag = true;
		fedCrFlag = true;
	}
	if(fastCrFlag){
		if(fastingCriteriaVal != null && fastingCriteriaVal != "" && fastingCriteriaVal != "00:00"){
			fastCriteriaFlag = checkMealsCriteria(criteriaType, meals, "startTime", fastingCriteriaVal);
			if(fastCriteriaFlag == false)
				fastCriteriaTimeVal = $('#criteriaDeviationTime').val();
		}else fastCriteriaFlag = true;
	}else fastCriteriaFlag = true; 
	
	if(fedCrFlag){
		//Fed Criteria Checking
		var fedCriteriaVal = doseMap.fedCriteria;
		if(fedCriteriaVal != null && fedCriteriaVal != "" && fedCriteriaVal != "00:00"){
			fedCriteriaFlag = checkMealsCriteria(criteriaType, meals, "endTime", fedCriteriaVal);
			if(fedCriteriaFlag == false)
				fedCriteriaTimeVal = $('#criteriaDeviationTime').val();
		}else fedCriteriaFlag = true;
	}else fedCriteriaFlag = true;
	
	
	
	
	if(fastCriteriaFlag == false && fedCriteriaFlag){
		var textMsg = "Fasting criteria failed with "+$('#criteriaDeviationTime').val()+" (minutes). Do you want to continue?";
		Swal.fire({
				title: 'Are you sure?',
				icon: 'warning',
				text:textMsg
			});
		/*Swal.fire({
			  title: 'Are you sure?',
			  text: textMsg,
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes'
			}).then((result) => {
			  if (result.isConfirmed) {
			   new swal({
					  title: 'Comments',
					  input: 'textarea'
					}).then(function(result) {
					  if (result.value != null && result.value != "" && result.value != "undefined"){ 
						  fastCriteraComments = result.value;
						  flag = true;
					  }else flag = false;
				});
			  }
			});*/
		$('#criteriaDeviation').val(true);
		if(data.dsDto != null && data.dsDto != undefined){
			if(data.dsDto.devionCodeMap != null && data.dsDto.devionCodeMap != undefined){
				var timeDiv = $('#timeDeviation').val();
				if(timeDiv == "true")
					$('#criteriaDeviationTimeCode').val(data.dsDto.devionCodeMap['FEDCANDTPD']);
				else $('#criteriaDeviationTimeCode').val(data.dsDto.devionCodeMap['FASTCD']);
			}
		}
		fastCriteraComments = "Fasting criteria failed with " + $('#criteriaDeviationTime').val() + " (minutes)";
		flag = true; 
	}else if(fastCriteriaFlag && fedCriteriaFlag == false){
		var devtMsg = $('#criteriaDeviationTime').val();
		var textMsg = "";
		if(devtMsg.indexOf(',') != -1)
			textMsg = "Fed criteria failed with deviation Dinner, BreakFast: "+$('#criteriaDeviationTime').val()+". Do you want to continue?";
		else textMsg = "Fed criteria failed with deviation Dinner/BreakFast: "+$('#criteriaDeviationTime').val()+". Do you want to continue?";
		Swal.fire({
			title: 'Are you sure?',
			icon: 'warning',
			text:textMsg
		});
		/*Swal.fire({
			  title: 'Are you sure?',
			  text: textMsg,
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes'
			}).then((result) => {
			  if (result.isConfirmed) {
			   new swal({
					  title: 'Comments',
					  input: 'textarea'
					}).then(function(result) {
					  if (result.value != null && result.value != "" && result.value != "undefined"){ 
						  feadCriteraComments = result.value;
						  flag = true;
					  }else flag = false;
				});
			  }
			});*/
		$('#criteriaDeviation').val(true);
		if(data.dsDto != null && data.dsDto != undefined){
			if(data.dsDto.devionCodeMap != null && data.dsDto.devionCodeMap != undefined){
				var timeDiv = $('#timeDeviation').val();
				if(timeDiv == "true")
					$('#criteriaDeviationTimeCode').val(data.dsDto.devionCodeMap['FEDCANDTPD']);
				else $('#criteriaDeviationTimeCode').val(data.dsDto.devionCodeMap['FEDCD']);
			}
		}
		feadCriteraComments = "Fed criteria failed with " + $('#criteriaDeviationTime').val() + ' (minutes)';
		flag = true;
		
	}else if(fastCriteriaFlag == false && fedCriteriaFlag == false){
		var textMsg = "Meals record not available, criteria failed. Do you want continue?";
		Swal.fire({
			title: 'Are you sure?',
			icon: 'warning',
			text:textMsg
		});
		
		/*Swal.fire({
			  title: 'Are you sure?',
			  text: textMsg,
			  icon: 'warning',
			  showCancelButton: false,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes'
			}).then((result) => {
			  if (result.isConfirmed) {
			   new swal({
					  title: 'Comments',
					  input: 'textarea'
					}).then(function(result) {
					  if (result.value != null && result.value != "" && result.value != "undefined"){ 
						  fastCriteraComments = result.value;
						  feadCriteraComments = result.value;
						  flag = true;
					  }else flag = false;
				});
			  }
			});*/
		$('#criteriaDeviation').val(true);
		if(data.dsDto != null && data.dsDto != undefined){
			if(data.dsDto.devionCodeMap != null && data.dsDto.devionCodeMap != undefined){
				var timeDiv = $('#timeDeviation').val();
				if(timeDiv == "true")
					$('#criteriaDeviationTimeCode').val(data.dsDto.devionCodeMap['FASTANDFEDCDANDTD']);
				else $('#criteriaDeviationTimeCode').val(data.dsDto.devionCodeMap['FASTANDFEDCD']);
			}
		}
		fastCriteraComments = "Fasting criteria failed with " + fastCriteriaTimeVal + " (minutes)";
		feadCriteraComments = "Fed criteria failed with " + fedCriteriaTimeVal + " (minutes)";
		flag = true;
	}else{
		fastCriteraComments = "";
		feadCriteraComments = "";
		flag = true;
	}
	if(flag){
		collectionTime = getServerEndTimeWithSeconds();
		/*$("#collectionTimeTr").html('<input type="text" name=\"collectionTimeWithSec\" id=\"collectionTimeWithSec\" value="'
								+ collectionTime
								+ '" size="8" readonly="readonly"  maxlength="8"/>'
								+'          '+ '<input type=\"button\" value=\"Reset\" class="btn btn-danger btn-sm" onclick=\"resetCollectionTime()\" >');	*/	
		$("#collectionTimeTr").html('<input type="text" name=\"collectionTimeWithSec\" class=\"form-control\" id=\"collectionTimeWithSec\" value="'
				+ getServerTime()
				+ '" size="8" readonly="readonly"  maxlength="8"/>');		
	}

}
function resetCollectionTime() {
	collectionTime = '';
	$("#collectionTimeTr").html('<input type="button" id = "collectionButton" value="Dose" class="btn btn-warning btn-sm" onclick="colletionTime()"/><font color=\"red\" id=\"clollectoinMesg\"></font>');
}

function submitForm() {
	
//	$('#save_btn').attr("disabled", true);
	var validationFlag = checkFromValidation();
	if (validationFlag) {
		var doseData = [];
		$.ajax({
			url : $("#mainUrl").val() + '/administration/getCsrfToken',
			type : 'GET',
			success : function(data) {
				//debugger;
				doseData.push({
					name : data.parameterName,
					value : data.token
				});
				doseData.push({
					name : "studyId",
					value : $("#studyName").val()
				});
				doseData.push({
					name : "activityId",
					value : $('#crfName').val()
				});
				doseData.push({
					name : "studyActivityId",
					value : studyActivityId
				});
				doseData.push({
					name : "perodId",
					value : perodId
				});
				doseData.push({
					name : "sachetBarcode",
					value : sachetBarcode
				});
				doseData.push({
					name : "sachetBarcodeScanTime",
					value : sachetBarcodeScanTime
				});
				doseData.push({
					name : "subjectBarcode",
					value : subjectBarcode
				});
				doseData.push({
					name : "subjectBarcodeScanTime",
					value : subjectBarcodeScanTime
				});
				doseData.push({
					name : "collectionTime",
					value : collectionTime
				});
				doseData.push({
					name : "devationMsgId",
					value : $("#message").val()
				});
				if (replaceStatus) {
					doseData.push({
						name : "replaceSubject",
						value : $("#replacedSubjectNo").val()
					});
					doseData.push({
						name : "replaceStatus",
						value : replaceStatus
					});
				}

				doseData.push({
					name : "timePointPk",
					value : timePointPk
				});
				doseData.push({
					name : "fastCriteraComments",
					value : fastCriteraComments
				});
				doseData.push({
					name : "feadCriteraComments",
					value : feadCriteraComments
				});
				doseData.push({
					name : "timeDeviation",
					value : $('#timeDeviation').val()
				});
				doseData.push({
					name : "timeDeviationTime",
					value : $('#timeDeviationTime').val()
				});
				doseData.push({
					name : "timeDeviationCodeId",
					value : $('#timeDeviationCode').val()
				});
				doseData.push({
					name : "criteriaDeviation",
					value : $('#criteriaDeviation').val()
				});
				doseData.push({
					name : "criteriaDeviationTime",
					value : $('#criteriaDeviationTime').val()
				});
				doseData.push({
					name : "criteriaDeviationTimeCodeId",
					value : $('#criteriaDeviationTimeCode').val()
				});
				
				var perameterValues = [];
				perams = $.grep(perams,function(n){
			        return(n);
			    });
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
											if($("#"+val).is(":checked"))
												perameterValues.push(ind+"@@@"+$('#'+val).val());
										});
									}
								});
							}else{
								perameterValues.push(index+"@@@"+$('#'+index).val());
							}
						}else
							perameterValues.push(index+"@@@"+$('#'+index).val());
					
					});
				}
				if(perameterValues.length > 0){
					doseData.push({
						name : "perameterValue",
						value : perameterValues
					});
				}else{
					doseData.push({
						name : "perameterValue",
						value : 0
					});
				}
				
				$.ajax({
					url : $("#mainUrl").val()
							+ '/study/clinical/saveDosingCollectionData',
					type : 'POST',
					data : doseData,
					success : function(e) {
						console.log(e.success)
						if (e.Success === 'true' || e.Success === true)
							displayMessage("success", e.Message);
						else 
							displayMessage("error", e.Message);
						
//						subjectsJson = synchronousAjaxCall(mainUrl+ "/study/clinical/getDosingCollectionDetails/" + $("#studyName").val());
//						data = subjectsJson;
						$('#save_btn').removeAttr("disabled");
						$('#crfName').val("");
						$("#activityDiv").empty();
						clearForm();
					},
					error : function(er) {
						//debugger;
						$('#save_btn').removeAttr("disabled");
						$('#crfName').val("");
						$("#activityDiv").empty();
					}
				});
			},
			error : function(er) {
				//debugger;
				$('#save_btn').removeAttr("disabled");
				/*$('#crfName').val("");
				$("#activityDiv").empty();*/
			}
		});

	}
}

function checkFromValidation() {
	var flag = true;
	$('#commentsMsg').html("");
	$("#clollectoinMesg").html("");
	if (sachetBarcode == '') {
		$("#sachetMsg").html("Sachet Barcode Required")
		flag = false;
	}
	if (subjectBarcode == '') {
		$("#subjectMsg").html("Subject Barcode Required");
		flag = false;
	}
	var timeDevFlag = $('#timeDeviation').val();
	var criteriaFlag = $('#criteriaDeviation').val();
	if(timeDevFlag || criteriaFlag){
		var messageVal = $('#message').val();
		if(messageVal == "-1"){
			flag = messageValidation('message', 'commentsMsg');
		}
	}
	if (sachetBarcode != '' && subjectBarcode != '') {
		$("#clollectoinMesg").html("");
		if (replaceStatus) {
			var subNo = $('#replacedSubjectNo').val();
			if(subNo == "0")
				flag = selectBoxValidation('subjectReplaceDiv', 'subjectReplaceDivMsg');
		}
	}
	if(collectionTime == null || collectionTime == "" || collectionTime == undefined){
		$("#clollectoinMesg").html("Please Click Dose Button. Before Submit.");
		flag = false;
	}
	if (flag) {
		/*$.each(perameterIds, function(k, peramId) {
			var peramflag = true;
			$("#" + peramId + "msg").html("");
			if (perameterIdsAndType[peramId] == 'RB'
					|| perameterIdsAndType[peramId] == 'CB'
					|| perameterIdsAndType[peramId] == 'CB') {
				if (perameterIdsAndType[peramId] != 'SB') {
					if (!$("input:radio[name='" + peramId + "']")
							.is(":checked")) {
						peramflag = false;
					}
				} else if ($("#" + peramId).val() == '-1') {
					peramflag = false;
				}
			} else if ($("#" + peramId).val() == '') {
				peramflag = false;
			}
			if (!peramflag) {
				$("#" + peramId + "msg").html("Required Field");
				flag = false;
			}
		});*/
	}
	return flag;
}
/*function getComments(textMessage){
	var comMsg = "";
	Swal.fire({
		  title: 'Are you sure?',
		  text: textMessage,
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes'
		}).then((result) => {
		  if (result.isConfirmed) {
		   new swal({
				  title: 'Comments',
				  input: 'textarea'
				}).then(function(result) {
				  if (result.value != null && result.value != "" && result.value != "undefined") 
					  comMsg = result.value;
			});
		  }
		});
   return comMsg;
}*/
function checkMealsCriteria(criteriaType, meals, timeType, criteriaTime){
	debugger;
	$('#criteriaDeviationTime').val(0);
    var cmcFlag = true;
//	var mealType = doseMap.criteriaType;
    var mtype = data.mealType;
    var mealType = "";
    /*if(mtype == "test"){
	    if(criteriaType == "fastingMelaType")
	    	mealType = "DINNER";
	    else mealType = "NORMALBREAKFAST";
    }else{
    	mealType = doseMap.criteriaType;
    }*/
    var dinnerArr = [];
    var dinnerFlag = false;
    var breakFastFlag = false;
    if(criteriaType == "FAST"){
    	mealType = "DINNER";
    	dinnerArr = meals[mealType];
    	dinnerFlag = calculateCriteriaForTimePoint(dinnerArr, mealType, criteriaType, meals, criteriaTime, timeType);
    }else if(criteriaType == "FED" || criteriaType == "FASTANDFED"){
    	var mTypeArr = ['DINNER', 'NORMALBREAKFAST'];
    	if(meals != undefined){
    		dinnerFlag = calculateCriteriaForTimePoint(meals[mTypeArr[0]], mTypeArr[0], criteriaType, meals, criteriaTime, timeType);
    		breakFastFlag = calculateCriteriaForTimePoint(meals[mTypeArr[1]], mTypeArr[1], criteriaType, meals, criteriaTime, timeType);
    	}else{
    		dinnerFlag = true;
    		breakFastFlag = true;
    	}
   }
    if(criteriaType == "FAST"){
    	cmcFlag = dinnerFlag;
    }else if(dinnerFlag == false || breakFastFlag == false)
    	cmcFlag = false;
    
    return cmcFlag;
}
function calculateCriteriaForTimePoint(criteriaArr, mtype, criteriaType, meals, criteriaTime, timeType){
	var flag = true;
	var timeArr = criteriaTime.split(":");
	var dinnerMinutes = "";
	if(timeArr.length > 0){
		dinnerMinutes = parseInt(timeArr[0] * 60) + parseInt(timeArr[1]);
	}
	if(criteriaArr != null && criteriaArr != "" && criteriaArr != "undefined"){
		var mealPojo = null;
		var dinnerTime = null;
		if(meals != null && meals != undefined)
			mealPojo = meals[mtype];
		if(mealPojo != null && mealPojo != undefined){
			if(timeType == "startTime")
				dinnerTime = new Date(mealPojo.startTime);
			else 
				dinnerTime = new Date(mealPojo.endTime);
		}
		if(dinnerTime != null && dinnerTime != "" && dinnerTime != "undefined"){
			debugger;
			var currentDate = new Date(getServerDate());
			var milSec = currentDate.getTime() - dinnerTime.getTime();
			var minDiff = Math.floor((milSec / (1000 * 60)));
			var hoursDiff = Math.floor((minDiff /60));
			var totalMinutes = minDiff;
			if(criteriaType == "fastingMelaType" || criteriaType == "FAST"){
				if(totalMinutes < dinnerMinutes){
					flag = false;
					var diffVal = Math.abs((totalMinutes - dinnerMinutes));
					var timeStr = convertHoursTime(diffVal);
					var cdtime = $('#criteriaDeviationTime').val();
					if(cdtime != null && cdtime != "" && cdtime != "0" && cdtime != undefined)
						$('#criteriaDeviationTime').val(cdtime +","+"-"+timeStr);
					else
						$('#criteriaDeviationTime').val("-"+timeStr);
				}
			}else{
				if(totalMinutes > dinnerMinutes){
					flag = false;
					var diffVal = Math.abs((dinnerMinutes - totalMinutes));
					var timeStr = convertHoursTime(diffVal);
					var cdtime = $('#criteriaDeviationTime').val();
					if(cdtime != null && cdtime != "" & cdtime != "0" && cdtime != undefined)
						$('#criteriaDeviationTime').val(cdtime +","+timeStr);
					else
						$('#criteriaDeviationTime').val(timeStr);
					
				}
			}
		}else{
			flag = false;
			var timeStr = convertHoursTime(dinnerMinutes);
			var cdtime = $('#criteriaDeviationTime').val();
			if(cdtime != null && cdtime != "" & cdtime != "0" && cdtime != undefined)
				$('#criteriaDeviationTime').val(cdtime +","+"-"+timeStr);
			else
				$('#criteriaDeviationTime').val("-"+timeStr);
		}
	}else{
		flag = false;
		var timeStr = convertHoursTime(dinnerMinutes);
		var cdtime = $('#criteriaDeviationTime').val();
		if(cdtime != null && cdtime != "" & cdtime != "0" && cdtime != undefined)
			$('#criteriaDeviationTime').val(cdtime +","+"-"+timeStr);
		else
			$('#criteriaDeviationTime').val("-"+timeStr);
	}
	return flag;
}
function selectBoxValidation(id, messageId){
	$('#replacedSubjectNo').val("0");
	var value = $('#'+id).val();
	var flag = false;
	if(value == null || value == "" || value == "0" || value == "undefined"){
		$('#'+messageId).html('Select Replacing Subject Number.');
		flag = false;
	}else{
		$('#save_btn').removeAttr("disabled");
		$('#replacedSubjectNo').val(value);
		$('#'+messageId).html('');
		flag = true;
	}
	return flag;
}
function messageValidation(id, messageId){
	  var flag = false;
	  var value = $('#'+id).val();
	  if(value == null || value == "" || value == "-1" || value == "undefined"){
		  $('#'+messageId).html("Deviation Comments Required");
		  flag = false;
	  }else{
		  $('#save_btn').removeAttr("disabled");
		  $('#'+messageId).html("");
		  flag = true;
	  }
	  return flag;
}