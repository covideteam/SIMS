var deefreeser = ""; // instrumentDesc
var rackDesc = ""; // shelfsDesc
var deefreeserId = ""; // instrumentId
var rackId = ""; // shelfsId

var timePointncheck = ""; // fixedTimePoint
var aliquedUniq = "";
var timePointSing = "";

var sampleIdmap = {}
var vialbarcode = {}
var timePointnval = {}
var subjectval = {};
var periodid = {};
var vialno = {};
var vialDatInfoTable = [];
var rackScanTime = "";

var sampleTimePointCollectedData = {};
var samplecollectedData = {};
var timePointsMap  = {};
var aliquot = "";
var rackBarcode = "";

var vailBarcodes = [];
var subjectsJson = "";
$(function() {
	subjectsJson = synchronousAjaxCall(mainUrl
			+ "/study/clinical/vialRackCollection/" + $("#studyName").val());
	if (subjectsJson != null && subjectsJson != "undefined"
			&& subjectsJson != "") {
		data = subjectsJson;
		sampleTimePointCollectedData = data.sampleTimePointCollectedData;
		samplecollectedData = data.samplecollectedData;
		debugger;
		timePointsMap = data.timePointsMap;
		console.log(data)
	}

});

function clearData() {
	deefreeser = "";
	deefreeserId = "";
	$("#deefreeDataTd").html("");
	$("#vialRackInfo").html("");
	$("#sampleTimePointCollectedData").html("");
	$("#samplecollectedData").html("");
	$("#vialDatInfoTable").html("");
	$("#barcodeMsg").html();
	vailBarcodes = [];
	vialDatInfoTable = [];
	$('#example2').hide();
	rackBarcode="";
	aliquedUniq ="";
	timePointToAllow = "";
}

$("#barcode").on("input",function(e) {
	var barcode = $("#barcode").val();
	$("#barcodeMsg").html("");
	var lastChar = barcode.substr(barcode.length - 1);
	if (lastChar == 'n') {
		$("#barcode").focus();
		$("#barcode").val("");
		var shellno = barcode.substr(0, barcode.length - 1).split('a');
		var prefix = barcode.substr(0, 2);
		if (prefix == "07") {// Rack
			rackScanTime = runningTimeWithSeconds;
			deefreeserId = shellno[1];
			rackBarcode = barcode;
			$("#deefreeDataTd").html(shellno[1]);
			$("#vialRackInfo tbody").html("");
			$("#sampleTimePointCollectedData").html("");
			$("#samplecollectedData").html("");
			$("#vialDatInfoTable").html("");
			$("#barcodeMsg").html();

		} else if (prefix == "05") { // vial
			if(rackBarcode != ""){
				var rackSplit = rackBarcode.substr(0,rackBarcode.length - 1).split('a');
				var vialSplit = barcode.substr(0,barcode.length - 1).split('a');
				var dataLength = Object.keys(subjectsJson.storageMap).length;
				var storage = null;
				if(dataLength > 0){
					var periodStorage = subjectsJson.storageMap[vialSplit[1]];
					if(periodStorage != undefined){
						var rackStore = periodStorage[rackSplit[1]];
						if(rackStore != undefined){
							storage = rackStore[vialSplit[4]];
						}
					}
				}
				if(duplicatecheck(barcode, vailBarcodes, "barcodeMsg")){
					var vialScanTime = runningTimeWithSeconds;
					var vialBarcodeSplit = barcode.substr(0,barcode.length - 1).split('a');
					aliquot = vialBarcodeSplit[5].substr(0, 2);
					if (checkSimileTimePointVials(barcode)) {
						vailBarcodes.push(barcode)
						vialDatInfoTable.push(barcode + "_"+ vialScanTime);
						$("#vialRackInfo").append("<tr id=\""
												+ barcode
												+ "\"><td>"
												+ vialBarcodeSplit[3]
												+ "</td><td>"
												+ aliquot
												+ "</td><td>"
												+ samplecollectedData[vialBarcodeSplit[4]].sign+samplecollectedData[vialBarcodeSplit[4]].timePoint
												+ "</td><td><input type=\"button\" value=\"Remove\" class=\"btn btn-warning btn-sm\" onclick=\"removeVial('"
												+ barcode
												+ "')\"/></td></tr>");
						$('#example2').show();
					} else {
						$("#barcodeMsg").html("Allow Same Time Point :"+timePointToAllow+" Subject Vials");
					}
				}
			}else $('#barcodeMsg').html("Scan Rack Barcode First");
		} else {
			$("#barcodeMsg").html("In-valied Barcode");
		}
	}
});
var timePointToAllow = "";
function checkSimileTimePointVials(vacBarcode) {
	var vacBarcodeSplit = vacBarcode.substr(0, vacBarcode.length - 1).split('a');
	if (timePointToAllow == "") {
		timePointToAllow = timePointsMap[vacBarcodeSplit[4]];
		aliquedUniq = aliquot;
		return true;
	} else {
		if (timePointToAllow == timePointsMap[vacBarcodeSplit[4]] && aliquedUniq == aliquot) {
			return true;
		} else {
			return false;
		}
	}
}
function saveVialData() {
	debugger;
	var vialRackDto = [];

	$.ajax({
		url : $("#mainUrl").val() + '/administration/getCsrfToken',
		type : 'GET',
		success : function(data) {
			vialRackDto.push({
				name : data.parameterName,
				value : data.token
			});
			vialRackDto.push({
				name : "studyId",
				value : $("#studyName").val()
			});
			vialRackDto.push({
				name : "rackId",
				value : deefreeserId
			});
			vialRackDto.push({
				name : "rackScanTime",
				value : rackScanTime
			});
			vialRackDto.push({
				name : "timePoint",
				value : timePointToAllow
			});
			vialRackDto.push({
				name : "vialDataList",
				value : vialDatInfoTable
			});
			vialRackDto.push({
				name : "aliqut",
				value : aliquedUniq
			});
			
			$.ajax({
				url : $("#mainUrl").val() + '/study/clinical/vialRackSave',
				type : 'POST',
				data : vialRackDto,
				success : function(e) {
					debugger;
					vailBarcodes = [];
					console.log(e.success)
					if (e.Success === 'true' || e.Success === true) {
						clearData();
						displayMessage("success", e.Message)
						collectedDataMap[e.key] = e.value;
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

function removeVial(barcode) {
	$("#" + barcode).remove();
	vailBarcodes = $.grep(vailBarcodes, function(key, value){
		return key != barcode;
	});
	var flag = true;
	$.each(vailBarcodes, function(key, value) {
		if (value != "") {
			flag = false;
			return false;
		}
	});
	if (flag) {
		timePointncheck = "";
		timePointToAllow = "";
		aliquedUniq = "";
	}
}