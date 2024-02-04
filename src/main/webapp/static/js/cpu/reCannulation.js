$("#barcode").on("input",function(e) {
	var crfName = $('#crfName').val();
	var studyId = $("#studyName").val();
	if(crfName != null && crfName != "" && crfName != undefined){
		var barcode = $("#barcode").val();
		$("#barcodeMsg").html("");
		var lastChar = barcode.substr(barcode.length - 1);
		if (lastChar == 'n') {
			$("#barcode").focus();
			$("#barcode").val("");
			var subBarcode = barcode.substr(0,barcode.length - 1).split('a');
			var prefix = barcode.substr(0, 2);
			debugger;
			if (prefix == "02") {
				if(parseInt(subBarcode[2]) == studyId) {
					asynchronousAjaxCallBack($("#mainUrl").val()+ "/reCannula/getRecannulationData/" + studyId+"/"+subBarcode[1], recalBackFunction);
				}else{ 
					$("#barcodeMsg").html("Subject Barcode does not belongs selcted Study");
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
});
var periodId = "";
var subjectId = "";
var sampleValue = "";
var commentsFlag = false;
function recalBackFunction(data){
	if(data != null && data != undefined){
		debugger;
		var subject = data.subject;
		periodId = data.spm.id;
		if(subject != null && subject != undefined){
			debugger;
			subjectId = subject.subjectId;
			var htmlStr = '<table class="table table-bordered" style="width: 100%;">'
						+'<tr>'
						+'<td style="width:25%;">Sample Time Point : </td>'
						+'<td style="width:45%;">'
							+'<div style="width:45%;"><select name="sampleId" id="sampleId" class="form-control">';
			if((data.samplesList != null && data.samplesList != undefined) || (data.subjectSamplesList != null && data.subjectSamplesList != undefined)){
				if(data.subjectSamplesList != null && data.subjectSamplesList.length > 0 && data.subjectSamplesList != undefined){
					commentsFlag = true;
					$.each(data.subjectSamplesList, function(key, value) {
						if(data.subjectSamplesList.length == 1){
							sampleValue = value.sampleTpId;
						}else htmlStr += '<option value="">----Select----</option>';
						htmlStr += '<option value="'+value.sampleTpId+'">'+value.sign+value.timePoint+'</option>';	
						
					});
					
				}else{
					if(data.samplesList != null && data.samplesList != undefined){
						htmlStr += '<option value="">----Select----</option>';
						$.each(data.samplesList, function(key, value) {
							htmlStr += '<option value="'+value.tpId+'">'+value.sing+value.timePoint+'</option>'		
						});
					}
				}
				htmlStr += '</select></div>'
						+'<div id="sampleIdMsg" style="color:red;"></div>'
						+'</td>'
						+'</tr>';
				htmlStr += '<tr>'
					     +'<td>Subject Number     : </td>'
					     +'<td>'+subject.subjectNo+'</td>'
					     +'</tr>'
					     +'<tr>'
					     +'<td>Period             :</td>'
					     +'<td>'+data.spm.periodName+'</td>'
					     +'</tr>'
					     +'<tr>'
					     +'<td>Re Cannulation     :</td>'
					     +'<td>'
					     +'<input type="radio" name="recannulation" id="recannulation_yes" value="Yes" onchange="recannulationValidation()"> &nbsp;Yes'
					     +'&nbsp;&nbsp;&nbsp;&nbsp;'
					     +'<input type="radio" name="recannulation" id="recannulation_no" value="No" onchange="recannulationValidation()"> &nbsp;No'
					     +'<div id="recannulationMsg" style="color:red;"></div>'
					     +'</td>'
					     +'</tr>'
					     +'<tr>'
					     +'<td>Cannule Removed    :</td>'
					     +'<td>'
					     +'<input type="radio" name="cannuleRemove" id="cannuleRemove_yes" value="Yes" onchange="cannuleRemoveValidation()"> &nbsp;Yes'
					     +'&nbsp;&nbsp;&nbsp;&nbsp;'
					     +'<input type="radio" name="cannuleRemove" id="cannuleRemove_no" value="No" onchange="cannuleRemoveValidation()"> &nbsp;No'
					     +'<div id="cannuleRemoveMsg" style="color:red;"></div>'
					     +'</td>'
					     +'</tr>'
					     +'<tr>'
					     +'<td>Reason              :</td>'
					     +'<td>'
					     +'<textarea rows="5" cols="35" name="reason" id="reason" style="width: 80%;"></textarea>'
					     +'<div id="reasonMsg" style="color:red;"></div>'
					     +'</td>'
					     +'</tr>'
					     +'<tr align="center">'
					     +'<td colspan="2"><input type="button" value="Submit" class="btn btn-primary btn-md" onclick="submitRecannulationForm()"></td>'
					     +'</tr>';
			   $('#reCannulaDataDiv').html(htmlStr);
			   $('#reCannulaDataDiv').show();
			}else $('#barcodeMsg').html("Subject Data Not Avaliable.");
		}else $('#barcodeMsg').html("Subject Not Exists.");
	}
}
var recannulationValue = "";
var cannuleRemoveValue = "";
function recannulationValidation(){
	recannulationValue = "";
	var flag = false;
	var yesflag = false;
	var noflag = false;
	if ($("#recannulation_yes").prop("checked")) {
		yesflag = true;
		recannulationValue = $("#recannulation_yes").val();
	}
	if ($("#recannulation_no").prop("checked")) {
		noflag = true;
		recannulationValue = $("#recannulation_no").val();
	}
	
	if(yesflag || noflag){
		flag = true;
		$('#recannulationMsg').html("");
	}else{
		flag = false;
		$('#recannulationMsg').html("Required Field.");
	}
	return flag;	
	
}
function cannuleRemoveValidation(){
	cannuleRemoveValue = "";
	var flag = false;
	var yesflag = false;
	var noflag = false;
	if ($("#cannuleRemove_yes").prop("checked")) {
		yesflag = true;
		cannuleRemoveValue = $("#cannuleRemove_yes").val();
	}
	if ($("#cannuleRemove_no").prop("checked")) {
		noflag = true;
		cannuleRemoveValue = $("#cannuleRemove_no").val();
	}
	
	if(yesflag || noflag){
		flag = true;
		$('#cannuleRemoveMsg').html("");
	}else{
		flag = false;
		$('#cannuleRemoveMsg').html("Required Field.");
	}
	return flag;	
	
}
function submitRecannulationForm(){
	var reCannuleFlag = recannulationValidation();
	var cannuleRemoveFlag = cannuleRemoveValidation();
	var comFlag = false;
	var commentsVal = $('#reason').val();
	if(commentsFlag){
		commentsVal = $('#reason').val();
		if(commentsVal == null || commentsVal == "" || commentsVal == undefined){
			comFlag = false;
			$('#reasonMsg').html("Required Field.");
		}else{
			$('#reasonMsg').html("");
			comFlag = true;
		}
	}else comFlag = true;
	if(reCannuleFlag && cannuleRemoveFlag && comFlag){
		var reCannulationData = [];
		var studyIdVal = $("#studyName").val();
		var sampleIdVal = $('#sampleId').val();
		$.ajax({url : $("#mainUrl").val() + '/administration/getCsrfToken',
					type : 'GET',
					success : function(res) {
						reCannulationData.push({
							name : res.parameterName,
							value : res.token
						});
						reCannulationData.push({
							name : "studyId",
							value : parseInt(studyIdVal)
						});
						if(recannulationValue == "Yes"){
							reCannulationData.push({
								name : "recannula",
								value : true
							});
						}else{
							reCannulationData.push({
								name : "recannula",
								value : false
							});
						}
						if(cannuleRemoveValue == "Yes"){
							reCannulationData.push({
								name : "cannulaRemoved",
								value : true
							});
						}else{
							reCannulationData.push({
								name : "cannulaRemoved",
								value : false
							});
						}
						reCannulationData.push({
							name : "periodId",
							value : periodId
						});
						reCannulationData.push({
							name : "subjectId",
							value : subjectId
						});
						if(sampleIdVal != null && sampleIdVal != "" && sampleIdVal != undefined){
							reCannulationData.push({
								name : "sampleId",
								value : parseInt(sampleIdVal)
							});
						}
						if(commentsVal != null && commentsVal != "" && commentsVal != undefined){
//							alert("comments val : "+commentsVal);
							reCannulationData.push({
								name : "reason",
								value : commentsVal
							});
						}
//						alert("url is :"+$("#mainUrl").val()+ '/reCannula/saveRecannulationData');
						$.ajax({
									url : $("#mainUrl").val()+ '/reCannula/saveRecannulationData',
									type : 'POST',
									data : reCannulationData,
									success : function(e) {
										//debugger;
										if (e.msgFlag === true){ 
											displayMessage("success", e.mealsMsg);
											$('#reCannulaDataDiv').empty();
											$('#crfName').val("");
										}else 
											displayMessage("error", e.mealsMsg);
//										$('#save_btn').removeAttr("disabled");
									},
									error : function(er) {
//										$('#save_btn').removeAttr("disabled");
										debugger;
//										alert("error happened", er);
										$('#crfName').val("");
									}
								});
					},
					error : function(er) {
						//debugger;
					}
				});
	}
}