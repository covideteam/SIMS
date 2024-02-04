$("#barcode").focus();
var deepfreezerData;
var timePointsMap  = {};
var rackTimePointMap = {};

var timePointTimePointIdsMap = {};
$(document)
		.ready(
				function() {
					debugger;
					var centrifugationList;
					var url = mainUrl
							+ "/study/clinical/periodWiseSampleTimePoitns/"
							+ $("#studyName").val();
					var json = synchronousAjaxCall(url);
					if (json != null && json != "undefined" && json != "") {
						// debugger;
						deepfreezerData = json;
						timePointsMap  = deepfreezerData.timePointsMap;
						rackTimePointMap = deepfreezerData.rackTimePointMap;
						console.log(json);

						var select = $("<select name=\"periodId\" id=\"periodId\" class=\"form-control\" onchange=\"periodChange()\">");
						select.append($('<option>').val(-1).text("--Select--"));
						$.each(deepfreezerData.periods, function(id, value) {
							select.append($('<option>').val(value.id).text(
									value.periodName));
						});
						$('#periodtd').append(select);
						debugger;
						$('#periodtd')
								.append(
										"<font color=\"red\" id=\"periodIdMsg\"></font>");
						
						select = $("<select name=\"timepointId\" id=\"timepointId\" class=\"form-control\" onchange=\"timepointChange()\">");
						select.append($('<option>').val(-1).text("--Select--"));
						debugger;
						if(deepfreezerData.study.treatmentSpecificSampleTimepoints){
							$.each(deepfreezerData.onlytimePoints, function(id, value) {
								select.append($('<option>').val(value.id).text(
										value.sign + value.timePoint));
							});							
						}else{
							$.each(deepfreezerData.onlytimePoints, function(id, value) {
								select.append($('<option>').val(value.sign + value.timePoint).text(
										value.sign + value.timePoint));
							});
						}

						$('#timepointtd').append(select);
						debugger;
						$('#timepointtd')
								.append(
										"<font color=\"red\" id=\"timepointIdMsg\"  ></font>");
						$('#totalSample').append(
								deepfreezerData.study.noOfSubjects);

						select = $("<select name=\"aliquotId\" class=\"form-control\" id=\"aliquotId\" onchange=\"aliquotIdChange()\">");
						select.append($('<option>').val(-1).text("--Select--"));
						$.each(deepfreezerData.vialNos,
								function(id, value) {
									select.append($('<option>').val(value)
											.text(value));
								});
						$('#aliquottd').append(select);
						debugger;
						$('#aliquottd')
								.append(
										"<font color=\"red\" id=\"aliquotIdMsg\"></font>");
						$('#aliquotSubjectstd').append(
								deepfreezerData.study.noOfSubjects);

					}

				});

var shelfBarocode = "";
var shelfScanTime = "";
var rackBarocodes = [];
var rackBarocodesScanTimes = {};
$("#barcode")
		.on(
				'input',
				function(e) {
					var barcode = $("#barcode").val();
					$("#barcodeMsg").html("");
					var lastChar = barcode.substr(barcode.length - 1);
					if (lastChar == 'n') {
						// //debugger;
						$("#barcode").focus();
						$("#barcode").val("");
						var prefix = barcode.substr(0, 2);
						if (prefix == "09") { // shelf
							
								var shalfBarcodeSplit = barcode.substr(0,
										barcode.length - 1).split('a');
								var scanTime = ranningTime;
								debugger;
								var shelf = deepfreezerData.shelfs[barcode];
								if (shelf != undefined) {
									$("#shelftd").html(shelf.shelfNo);
									$("#deepfreezertd").html(
											shelf.instrument.instrumentNo);
									shelfBarocode = barcode;
									shelfScanTime = scanTime;
								} else {
									$("#barcodeMsg").html("In-valied Barcode");
								}
						} else if (prefix == "07") { // rack
							if(duplicatecheck(barcode, rackBarocodes, "barcodeMsg")){

//								var rackBarcodeSplit = barcode.substr(0,
//										barcode.length - 1).split('a');
								var scanTime = ranningTime;
								
								var rack = deepfreezerData.racks[barcode];
								if (rack != undefined) {
									var rackNo = rack.rackNo;
									var rackId = rack.id;
									var rackVials = deepfreezerData.rackVials;
									var rackFlag = 0;
									$.each(rackVials, function(rackKey, rackVialEach){
										if(rackKey == rackId){
											$.each(rackVialEach, function(key, value){
//												console.log(rackVials.timepoint.id)
												console.log($("#timepointId").val())
												
												var timeString = "";
												if(deepfreezerData.study.treatmentSpecificSampleTimepoints){
													timeString = timePointsMap[$("#timepointId").val()];
												}else{
													timeString = $("#timepointId").val();
												}
												if(rackTimePointMap[rackId] == timeString){
													if(value.vialNo == $("#aliquotId").val()){
														debugger;
														var string = "<tr id=\""+value.id+"tr\"><td>"+rackNo+"</td><td>"+value.sampleTimePoint+"</td>";
														string += "<td><input type=\"button\" onclick=\"removeRow('"+value.id+"', '"+barcode+"')\"/></td></tr>";
														$("#racksTable tbody").append(string);
														rackBarocodes.push(barcode);
														rackBarocodesScanTimes[barcode] = scanTime;
														rackFlag = 1;													
													}else{
														$("#barcodeMsg").html("Rack Vial No not macthced with selected Vital");
														rackFlag = 3;
													}
												}else{
													$("#barcodeMsg").html("Rack Vial Timepoint not macthced with selected Timepoint");
													rackFlag = 2;
												}
												return false;
											});
										}
									});
									if(rackFlag == 0)
										$("#barcodeMsg").html("In-valied rack");
								} else {
									$("#barcodeMsg").html("In-valied Barcode");
								}
							}
						  
						}
					}

				});
function removeRow(tdId, barcode){
	$("#"+tdId+"tr").remove();
	rackBarocodes = $.grep(rackBarocodes, function(key, value) {
		return value !== barcode;
	});
	rackBarocodesScanTimes = $.grep(rackBarocodesScanTimes, function(key, value) {
		return key !== barcode;
	});
}
function saveSampleStorage() {
	if (checkValidation()) {
		var rackScanTime = [];
		var fg = false;
		$.each(rackBarocodesScanTimes, function(barcode, time){
			rackScanTime.push(barcode+"_"+time);
//			if(fg){
//				rackScanTime = rackScanTime + "," +barcode+"_"+time;
//			}else{
//				rackScanTime = barcode+"_"+time;
//				fg = true;
//			}
		});
		var storateDate = [];
		$
				.ajax({
					url : $("#mainUrl").val() + '/administration/getCsrfToken',
					type : 'GET',
					success : function(data) {
						// debugger;
						storateDate.push({
							name : data.parameterName,
							value : data.token
						});
						storateDate.push({
							name : "studyId",
							value : $("#studyName").val()
						});
						storateDate.push({
							name : "periodId",
							value : $("#periodId").val()
						});
						storateDate.push({
							name : "timepointId",
							value : $("#timepointId").val()
						});
						storateDate.push({
							name : "aliquot",
							value : $("#aliquotId").val()
						});
						storateDate.push({
							name : "shelfBarocode",
							value : shelfBarocode
						});
						storateDate.push({
							name : "shelfScanTime",
							value : shelfScanTime
						});
						storateDate.push({
							name : "rackBarocodes",
							value : rackBarocodes
						});
						storateDate.push({
							name : "rackBarocodesScanTimes",
							value : rackScanTime
						});
						debugger;
						


						$
								.ajax({
									url : $("#mainUrl").val()
											+ '/study/clinical/sampleStorageSave',
									type : 'POST',
									data : storateDate,
									success : function(e) {
										console.log(e.success)
										if (e.Success === 'true'
												|| e.Success === true) {
											displayMessage("success", e.Message);
											resetForm();
										} else {
											displayMessage("error", e.Message);
										}
									},
									error : function(er) {
										debugger;
									}
								});
					},
					error : function(er) {
						debugger;
					}
				});
	}

}
function resetForm(){
	$("#barcodeMsg").html("");
	shelfBarocode = "";
	shelfScanTime = "";
	rackBarocodes = [];
	rackBarocodesScanTimes = {};
	$("#racksTable tbody tr").remove();
	$("#shelftd").html();
	$("#deepfreezertd").html();
	$("#periodId").val(-1)
	$("#timepointId").val(-1)
	$("#aliquotId").val(-1)

}

function checkValidation() {
	var flag = true;
	$("#periodIdMsg").html("");
	$("#timepointIdMsg").html("");
	$("#aliquotIdMsg").html("");
	if ($("#periodId").val() == -1) {
		$("#periodIdMsg").html("Required Field");
		flag = false;
	}
	if ($("#timepointId").val() == -1) {
		$("#timepointIdMsg").html("Required Field");
		flag = false;
	}
	if ($("#aliquotId").val() == -1) {
		$("#aliquotIdMsg").html("Required Field");
		flag = false;
	}
	if (shelfBarocode == "") {
		$("#shelftd").html("Shelf barcode scan required");
		flag = false;
	}
	return flag
}
function sampeleStorageReset(status) {
	debugger;
	var falg = true;
	if (status) {
		falg = confirm("Do, You wan't Reset with Samples Storage?");
	}
	if (falg) {
		$("#barcodeMsg").html("");
		$("#barcode").focus();
		$("#barcode").val("");
		$("#shelftd").html("");
		$("#deepfreezertd").html("");
		shelfBarocode = "";
		shelfScanTime = "";
		$("#periodId").val(-1);
		$("#timepointId").val(-1);
		$("#aliquotId").val(-1);
	}
}
function callCentrifugation(centrifugeId) {
	debugger;
	var url = mainUrl + "/study/clinical/cpuActivity/" + $("#studyName").val()
			+ "/CENTRIFUGATION";
	var result = synchronousAjaxCall(url);
	if (result != null && result != "undefined" && result != "") {
		$("#activityDiv").html(result);
		console.log(result)
	}
}
function aliquotIdChange(){
	$("#aliquotIdMsg").html("");
	var ealiquot = $("#aliquotId").val();
	if(ealiquot == -1){
		$("#aliquotIdMsg").html("Required Value");
	}
	$("#racksTable tbody").html("");
	rackBarocodes=[];
	rackBarocodesScanTimes ={};
}

function timepointChange(){
	$("#timepointIdMsg").html("");
	var timeid = $("#timepointId").val();
	if(timeid == -1){
		$("#timepointIdMsg").html("Required Value");
	}
	$("#aliquotId").val(-1);
	aliquotIdChange();
}
function periodChange(){
	$("#periodIdMsg").html("");
	var periodId = $("#periodId").val();
	if(periodId == -1){
		$("#periodIdMsg").html("Required Value");
	}
	$("#timepointId").val(-1);
	timepointChange();
}
