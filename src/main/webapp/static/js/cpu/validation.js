//var dataSubjects = [];
var subjectDoseTimes = {};
var doseTimePointId = 0;
function checkFunction(){
	debugger;
	console.log("checkFunction");
}
function checkSubjectBarcode(subjectBarcode) {
	debugger;
	$("#subjectMsg").html("");
	var flag = 1;
	var subjectBarcodeSplit = subjectBarcode.substr(0,
			subjectBarcode.length - 1).split('a');
	$.each(dataSubjects, function(k, subject) {
		debugger;
		if (subject.subjectNo == subjectBarcodeSplit[1]) {
			if (subject.subjectStatus == 'DropOut') {
				flag = 2;
				return false;
			} else {
				if (subject.stdSubjectId != undefined) {
					flag = 3;
					return false;
				}
			}
			flag = 0;
			return false;
		}
	});
	if (flag == 1)
		$("#subjectMsg").html("In-valied Subject Barcode");
	else if (flag == 2)
		$("#subjectMsg").html("Subject Has Drouped");
	else if (flag == 3)
		$("#subjectMsg").html("Subject Has Replaced");
	if (flag == 0)
		return true;
	else
		return false;
}
function checkDosingStatus(subject, periodPk) {
	var key = periodPk + "," + subject;
	var doseStatus = false;
	debugger;
	$.each(subjectDoseTimes, function(k, v) {
		if (k == key) {
			doseStatus = true;
			return false;
		}
	});
	return doseStatus;
}

function duplicatecheck(barcode, barcodesArray, messageId){
	$("#"+messageId).html("");
	var flag = true;
	$.each(barcodesArray, function(k, v) {
		if (v == barcode) {
			flag = false;
			$("#"+messageId).html("Duplicate Barcode");
			return false;	
		}
	});
	return flag;
}
