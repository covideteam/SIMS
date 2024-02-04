var stdName = false;
var crfName = false;
var subName = false;
var activityStartTime = "";
/*
 * get study activities configured during study design
 * */
function getActivities(studyId){
	$.ajax({
	    url:"/SIMS/studyExe/getStudyActivities/"+studyId,
		type:'GET',
		success:function(data){
			var d = data.actdList;
			$('#crfName').empty();
			$('#crfName').append("<option value=''>----Select----</option>");
			for(var v = 0;v < d.length;v++){
				$('#crfName').append("<option data-ss='" + d[v].showSubject + "' value='" + d[v].activityId + "' data-said='" + d[v].studyActivityId + "' data-get='" + d[v].getUrl + "' data-post='" + d[v].postUrl + "'>" +  d[v].activityName + "</option>");
			}
		}
	});	
}

setTimeout(function(){
	$("#subName").attr("readonly", false); 
}, 100);

function verifyLock(volunteer){
	$.ajax({
 		url:$("#mainUrl").val() +'/administration/getCsrfToken',
 		type:'GET',
 		success:function(data){
 			 var formData = $('#frmActivityForm .exclude').serializeArray();
 			 formData.push({name: data.parameterName ,value: data.token});
 			 formData.push({name: "activityName" ,value: $("#crfName option:selected").text()});
 			 formData.push({name: "subjectId" ,value: $("#subName").attr("data-value")});
 			 $.ajax({
 			 	url: $("#mainUrl").val() + '/activityLocked/saveActivityLockingData',
 				type:'POST',
 				data: formData,
 				success:function(data){
 					$(".loader").hide();
 					if(!data.success){
 	            		showMessage(data.message);
 	            	}
 	            	else{
 	            		if(typeof studyExecutionCallback !== 'undefined' && studyExecutionCallback !== null){
 	            			if(volunteer.length > 0){
 	            				studyExecutionCallback(volunteer[0]);	
 	            			}
 	            			else{
 	            				studyExecutionCallback(volunteer);	
 	            			}
 	            		}
 	            		$(".tdPeriodNumber").text(volunteer[0].periodNumber);
 	            		$("[control-type='ST']").val(getServerDate());
 	            		activityStartTime = data.date;
 	            		$("[data-tp]").hide();
 	            		$("[data-tp='" + (volunteer[0].periodId + "" + volunteer[0].treatmentId) + "']").show();
 	            	}
 				},
 				error:function(er){
 					$(".loader").hide();
 				 	console.log(er);
 				}
 			 });
 		},
 		error:function(er){
 			console.log(er);
 		}
 	});
}

$(document).on("click",".neo-qrcodescanner",function(e){
	if($("#studyName").val() === "" || $("#crfName").val() === ""){
		showMessage("Select project and activity");
		$("#subName").val('');
		return;
	}
	$(".qrCodeModal").show();
	startQrCodeScanner();
});

onQrCodeScanned = function(qrCode){
	if($("#barcode").length > 0){
		$("#barcode").val(qrCode);
		if(typeof onBarcodeScanned !== 'undefined' && onBarcodeScanned !== null && onBarcodeScanned !== undefined){
			onBarcodeScanned();
		}
	}
	else{
		getVolunteerData(qrCode, null);	
	}
}

$("#studyName").change(function(e){
	if($(this).val() === ""){
		$('#crfName option:not(:first)').remove();
	}else{
		getActivities($(this).val());
	}
	$('#subName').val("");
	$("#activityDiv").empty();
});

function getVolunteerData(term,response){
	var isSubjectScanned = false;
	if($.trim(term).length > 0 && term.startsWith("02") && term.endsWith("n")){
		isSubjectScanned = true;
		term = term.split('a')[1];
		var project = term.split('a')[2].replace('n','');
		if($("#studyName").val() === "" || $("#crfName").val() === ""){
    		showMessage("Select project and activity");
    		$("#subName").val('');
    		return;
    	}
		else if(project !== $("#studyName").val()){
			showMessage("Barcode scanned is of different project.");
    		$("#subName").val('');
    		return;
		}
		
		
		$(".loader").show();
		$("#subName").val('');
	}

	$.ajax({
        url: $("#mainUrl").val() + "/studyExe/getStudyVolunteers/" + $("#studyName").val() + "/" + $("#crfName").val() + "/" + term +"/" + isSubjectScanned,
        dataType: "json",
        success: function (data) {
        	volunteerData = data;
        	clearActivityForm();
        	if(isSubjectScanned){
        		$("#tdPeriodNumber").text(volunteerData[0].periodNumber);
        		$("#subName").val(volunteerData[0].vounteerNo);
            	$("#subName").attr("data-label",volunteerData[0].vounteerNo);
            	$("#subName").attr("data-value",volunteerData[0].volId);
            	$("#subName").attr("data-period",volunteerData[0].periodId);
                $("#subName").attr("data-treatment",volunteerData[0].treatmentId);
                verifyLock(volunteerData);
        	}
        	else{
        		response($.map(data, function (obj, key) {
            		return {
                    	label: obj.vounteerNo,
                        value: obj.volId
                    };
                }));
        	}
        }
    });
}
     
	
$("#subName").on("blur",function(e){
	if(typeof studyExecutionCallback !== 'undefined' && studyExecutionCallback !== null){
		studyExecutionCallback(null);
	}
});
$("#subName").autocomplete({
    source: function (request, response) {
    	if($("#studyName").val() === "" || $("#crfName").val() === ""){
    		showMessage("Select project and activity");
    		$("#subName").val('');
    		return;
    	}
    	else if($.trim(request.term).length === 0){
    		volunteerData = [];
    		return;
    	}
    	getVolunteerData(request.term,response);
    	
    },
    minLength: 0,
    autoFocus: true,
    select: function (event, ui) {
    	$(".loader").show();
    	$(this).val(ui.item.label);
    	$(this).attr("data-label",ui.item.label);
    	$(this).attr("data-value",ui.item.value);
    	var volunteer = filterJsonArray(volunteerData,"volId",ui.item.value);
        $(this).attr("data-period",volunteer[0].periodId);
        $(this).attr("data-treatment",volunteer[0].treatmentId);
        verifyLock(volunteer);
        return false;
    },
    focus: function (event, ui) {
        return false;
    },
    change: function (event, ui) {
        
    }
});
var isActivityLoading = false;
var currentActivityId = "";
$("#crfName").change(function(e){
//	debugger;
//	if(!isActivityLoading){
		if($(this).val() === ""){
			$("#activityDiv,#crfFormDiv").empty();
			$("#studyExcutionSubjectNumber,#subName,.studyExcutionSubjectNumber").hide();
			$("#subNameMsg").empty();
			return;
		}
		currentActivityId = $("#crfName").val();
		isActivityLoading = true;
		debugger;
		if($("#crfName").find(':selected').attr('data-ss') == true || $("#crfName").find(':selected').attr('data-ss') == 'true'){
			$(".studyExcutionSubjectNumber").show();
		}
		else{
			$(".studyExcutionSubjectNumber").hide();
		}
		$('#subName').val("");
		$(".loader").show();
		$("#activityDiv").empty();
		$("#subName").val('');$("#subName").attr('data-value','');
		var urlValue = $("#crfName").find(':selected').attr('data-get');
		if(urlValue != null && urlValue != undefined && urlValue.search("cpuActivity") != -1){
			if($("#studyName").val() != -1){
	//			cpuActivity/BLOODSAMPLECOLLECTION
	//			cpuActivity/SAMPLESEPARATION
	//			cpuActivity/VITALCOLLECTION
	//			cpuActivity/MEALCOLLECTION
	//			cpuActivity/ECG
				var cpuurl = urlValue.split("/");
				var screenTypeVal = $('#pageType').val();
				if(screenTypeVal == "review" || screenTypeVal == "responsetoreview"){
					var result = synchronousAjaxCall(mainUrl+"/reviewData/getActivityDataForReview/" + $("#studyName").val() + "/" + $("#crfName").val() +"/"+screenTypeVal);
					if(result != null && result != "" && result != undefined){
						buildReviewDataList(result, screenTypeVal);
						isActivityLoading = false;
					}
				}else{
					$.ajax({
						url : $("#mainUrl").val()
								+ "/study/clinical/cpuActivity/"
								+ $("#studyName").val() + "/"
								+ cpuurl[1],
						type : 'GET',
						success : function(d) {
							$("#activityDiv").append(d);
							isActivityLoading = false;
						}
					});
				}
				
			}else{
				$("#studyNameMsg").html("Required Field");
			}
		}else{
				$('#cpuActReviewDiv').empty();
				$('#crfReviewDiv').empty();
				if($('#pageType').val() === "review" || $('#pageType').val() === "responsetoreview"){
					if(urlValue != null && urlValue != undefined && urlValue.search("dosingCollection") != -1){
						var result = synchronousAjaxCall(mainUrl+"/reviewData/getActivityDataForReview/" + $("#studyName").val() + "/" + $("#crfName").val() +"/"+$('#pageType').val());
						if(result != null && result != "" && result != undefined){
							buildReviewDataList(result, $('#pageType').val());
						}
						
					}else{
						$('#cpuActReviewDiv').empty();
						$('#crfReviewDiv').empty();
						getSubjectsForReview();
					}
					$(".loader").hide();
					isActivityLoading = false;
				}
				else{
					if($(this).find(':selected').attr('data-get') === null || $(this).find(':selected').attr('data-get') === 'null' || $(this).find(':selected').attr('data-get') === "0"){
						$.ajax({
				    		url: $("#mainUrl").val()+"/studyActivity/studyExecutionActivityDetails/" + $("#studyName").val() + "/" + $("#crfName").val(),
				    		type:'GET',
				    		success:function(d){ 
				    			isActivityLoading = false;
	//			    			getActivityVolunteers();
				    			buildForm(d);
				    			debugger;
				    			if(d.gpdDtoList[0].showSubjectField == true){
				    				$(".studyExcutionSubjectNumber").show();
				    			}
				    			else{
				    				$(".studyExcutionSubjectNumber").hide();
				    			}
				    			$(".loader").hide();	
				    		}
				    	});
					}else{
						$.ajax({
				    		url: $("#mainUrl").val()+ "/" + $(this).find(':selected').attr('data-get')+"/"+$("#studyName").val(),
				    		type:'GET',
				    		success:function(d){
				    			isActivityLoading = false;
	//			    			getActivityVolunteers();
				    			$("#activityDiv").append(d);
				    			$(".loader").hide();	
				    		}
				    	});
					}
				}
		}
		/*}
	else{
		showMessage("Loading activity. Please wait.");
		$("#crfName").val(currentActivityId);
	}*/
});

function studyNameFunction(id, messageId){
	  var value = $('#'+id).val();
	  $('#crfFormDiv').empty();
	  if(value == null || value == "" || value == "undefined"){
		  $('#'+messageId).html("Required Field.");
		  stdName = false;
	  }else{
		  var result = synchronousAjaxCall(mainUrl+"/studyExe/getStudyLevelActivites/"+value);
		  if(result != '' || result != 'undefined'){
		 	 $('#activityDiv').html(result);
		 	 $('#'+messageId).html("");
		 	 stdName = true;
		  }
	  }
  }

  function crfNameFucntion(id, messageId){
	  var value = $('#'+id).val();
	  $('#subName').val("");
	  $('#crfFormDiv').empty();
	  if(value == null || value == "" || value == "undefined"){
		  $('#'+messageId).html("Required Field.");
		  crfName = false;
	  }else{
	 	 $('#'+messageId).html("");
	 	 crfName = true;
	  }
  }
  
  function subNameFucntion(id, messageId){
	  var value = $('#'+id).val();
	  if(value == null || value == "" || value == "undefined"){
		  $('#'+messageId).html("Required Field.");
		  subName = false;
	  }else{
		  $('#'+messageId).html("");
		  subName = true;
		  var studyVal = $('#studyName').val();
		  var crfIdVal = $('#crfName').val();
		  var subVal = $('#subName').val();
		  var result = synchronousAjaxCall(mainUrl+"/dataEtry/crfDataEntryView/"+studyVal+"/"+crfIdVal+"/"+subVal);
		  if(result != '' || result != 'undefined'){
			  $('#resultMessage').empty();
			  $('#crfFormDiv').html(result);
		  }
	 }
  }