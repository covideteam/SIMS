$("#barcode").focus();

var shelfs = {};
var racks = {}; // key-period,aliquot value-list of RackWithVials
var subjects = {};
var rackWiseSubects = {};
var vialdata = {};
$(document).ready(
		function() {
			// debugger;
			var segregationJson = synchronousAjaxCall(mainUrl
					+ "/study/clinical/fechtSegregationDetails/"
					+ $("#studyName").val());
			if (segregationJson != null && segregationJson != "undefined"
					&& segregationJson != "") {
				shelfs = segregationJson.shelfs;
				racks = segregationJson.racks;
				subjects = segregationJson.subjects;
				rackWiseSubects = segregationJson.rackWiseSubects;
				vialdata = segregationJson.vialdata;
				console.log(segregationJson);
			}
		});

var scanedVials = [];
var scanedVialsTimes = {};
$("#barcode")
		.on(
				'input',
				function(e) {
					var barcode = $("#barcode").val();
					$("#barcodeMsg").html("");
					var lastChar = barcode.substr(barcode.length - 1);
					if (lastChar == 'n') {
						$("#barcode").focus();
						$("#barcode").val("");

						var prefix = barcode.substr(0, 2);
						if (prefix == "05") {// instrument
							debugger;
							console.log(barcode);
							var scanTime = runningTimeWithSeconds;
							var vial = barcode.substr(0, barcode.length - 1)
									.split('a');
							// debugger;
							console.log(barcode);
							if (checkVial(barcode)) {
								var key = vial[1] + "," + vial[2] + ","
										+ vial[4] + "," + parseInt(vial[5])
										+ "," + vial[3];
								var subject = subjects[key];
								var row = "<tr id=\"" + barcode + "\"><td>"
										+ subject.subjectNo + "</td>";
								var periodId = $("#period").val();
								var aliquot = $("#aliquot").val();
								key = periodId + "," + aliquot;
								debugger;
								var selectedRacks = racks[key];
								$.each(selectedRacks, function(k, rack) {
									row += "<td>" + rack.timepoint.sign
											+ rack.timepoint.timePoint
											+ "</td>";
									return false;
								});
								row += "<td>A" + $("#aliquot").val() + "</td>";
								row += "<td><input type=\"button\" value=\"Delete\" /></td></tr>";
								$("#dataTable").append(row);
								scanedVials.push(barcode);
								scanedVialsTimes[barcode] = scanTime;
							}
						}
					}
				});

function checkVial(barcode) {
	var vial = barcode.substr(0, barcode.length - 1).split('a');
	var key = vial[1] + "," + vial[2] + "," + vial[4] + "," + parseInt(vial[5])
			+ "," + vial[3];
	var subjs = subjects[key];

	if (subjs != undefined) {
		// if (true) {
		var aliquot = parseInt($("#aliquot").val());
		if (vial[5] == aliquot) {
			return true;
		} else {
			$("#barcodeMsg").html("Aliquot Not matched");
			return false;
		}
	} else {
		$("#barcodeMsg").html("In-Valied Barcode");
		return false;
	}
}
function checksubjectSelections() {
	$("#allSubjectMsg").html("");
	if ($('input[name="allSubject"]:checked').length = 0) {
		if ($.trim($('input[name="subject"]').val()).length == 0) {
			$("#allSubjectMsg").html("Required Field");
			return false;
		}
	}
	return true;
}

function checkFrom() {
	var flag = true;
	if ($("#cleanArea").val() == -1) {
		$("#cleanAreaMsg").html("Required Field");
		flag = false;
	}
	if ($("#dataLogger").val() == -1) {
		$("#dataLoggerMsg").html("Required Field");
		flag = false;
	}
	if (!checksubjectSelections()) {
		flag = false;
	}
	if (!checkPeriodAndAliquot()) {
		flag = false;
	}
	return flag;

}
function checkPeriodAndAliquot() {
	$("#periodMsg").html("");
	$("#aliquotMsg").html("");
	debugger
	var periodId = $("#period").val();
	var aliquot = $("#aliquot").val();
	var flag = true;
	if (periodId == -1 && aliquot == -1) {
		$("#periodMsg").html("Required Field");
		$("#aliquotMsg").html("Required Field");
		flag = false;
	} else if (periodId == -1) {
		$("#periodMsg").html("Required Field");
		flag = false;
	} else if (aliquot == -1) {
		$("#aliquotMsg").html("Required Field");
		flag = false;
	}
	return flag;
}
function showRacks() {
	$("#rackTable").html("");
	if (checkPeriodAndAliquot()) {
		var key = $("#period").val() + "," + $("#aliquot").val();
		var selectedRacks = racks[key];
		// debugger;
		$.each(selectedRacks, function(k, rack) {
			var rackNo = rack.reack.rackNo;
			var timePoint = rack.timepoint.sign + rack.timepoint.timePoint;
			// var subject = rack.subject.subjectNo;
			var row = "<tr><td>" + rackNo + "</td>";
			row += "<td>" + timePoint + "</td>";
			var rsubjects = rackWiseSubects[rack.id];
			var subjectsList = [];
			$.each(rsubjects, function(index, sub) {
				subjectsList.push(sub.subjectNo);
			});
			row += "<td>" + subjectsList + "</td></tr>";
			$("#rackTable").append(row);
		});
	}
}

// function printSubjectSamplesContainter() {
// var centrifugeEndTime = runningTimeWithSeconds;
// if (checkFrom()) {
// if (scanedVials.length > 0) {
// $("#studyId").val($("#studyName").val());
// $("#periodIdb").val($("#periodId").val());
// $("#cleanAreab").val($("#cleanArea").val());
// $("#dataLoggerb").val($("#dataLogger").val());
// $("#aliquotb").val($("#aliquot").val());
// $("#allSubject").val($("#allSubjectb").val());
// $("#subjectb").val($("#subject").val());
// $("#studyId").val($("#studyName").val());
// $("#centrifugeEndTime").val($("#centrifugeEndTime").val());
// $.each(scanedVials, function(k, v) {
// $("#formFields").append(
// "<input type=\"hidden\" name=\"scanedVials\" value=\""
// + v + "\"/>");
// });
// $.each(scanedVialsTimes, function(k, v) {
// $("#formFields").append(
// "<input type=\"hidden\" name=\"scanedVials\" value=\""
// + (k + "_" + v) + "\"/>");
// });
// $("#form").submit();
// resetPage();
// }
// }
//
// }

function resetPage(){
	$("#periodId").val(-1);
	$("#cleanArea").val(-1);
	$("#dataLogger").val(-1);
	$("#aliquot").val(-1);
	$("#allSubjectb").val(-1);
	$("#subject").val("");
	scanedVialsTimes = {};
	scanedVials = [];
}

function printSubjectSamplesContainter() {
	debugger;
	var centrifugeEndTime = runningTimeWithSeconds;
	if (checkFrom()) {
		if (scanedVials.length > 0) {
			$("#centrifugeEndTime").val(centrifugeEndTime);
			var scanedVialsTimesData = [];
			$.each(scanedVialsTimes, function(k, v) {
				scanedVialsTimesData.push(k + "_" + v);
			});
			
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
								value : $("#studyName").val()
							});
// debugger;
							centrifugationDate.push({
								name : "periodId",
								value : $("#period").val()
							});
							centrifugationDate.push({
								name : "cleanArea",
								value : $("#cleanArea").val()
							});
							centrifugationDate.push({
								name : "dataLogger",
								value : $("#dataLogger").val()
							});
							centrifugationDate.push({
								name : "aliquot",
								value : $("#aliquot").val()
							});
							centrifugationDate.push({
								name : "allSubjectb",
								value : $("#allSubjectb").val()
							});
							centrifugationDate.push({
								name : "subject",
								value :$("#subject").val()
							});
							centrifugationDate.push({
								name : "scanedVials",
								value : scanedVials
							});
							centrifugationDate.push({
								name : "scanedVialsTimes",
								value : scanedVialsTimesData
							});
							

							$
									.ajax({
										url : $("#mainUrl").val()
												+ '/study/clinical/seggrigationSave',
										type : 'POST',
										data : centrifugationDate,
										success : function(data) {
											console.clear()
											console.log(data)
											var blob = new Blob([data], { type: 'application/pdf' });
										    var link = document.createElement('a');
										    link.href = URL.createObjectURL(blob);
										    link.download = 'file.pdf';
										    document.body.appendChild(link);
										    link.click();
										    document.body.removeChild(link);
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

}
