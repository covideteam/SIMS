var volunteerData = [];var validationRules = [];var dependentRules = [];var activityRules=[];var dependentActivities = [];
$(document).on("keydown","[control-type='DP']",function(e){
	var keyId = event.keyCode;
	if(keyId !== 9 && keyId !== 8 && keyId !== 46){
		e.preventDefault();	 
	}
});

function loadDatePicker(name){
	$("[name='" + name + "']").datepicker({
		  changeMonth: true,
		  changeYear: true,
		  dateFormat: 'd MM y'
    });
}

function unlockRecord(){
	 var formData = $('#frmActivityForm .exclude').serializeArray();
	 var csrfToken = getCsrfToken();
	 formData.push({name: csrfToken.parameterName ,value: csrfToken.token});
	 formData.push({name: "activityName" ,value: $("#crfName option:selected").text()});
	 formData.push({name: "subjectId" ,value: $("#subName").attr("data-value")});
	 $.ajax({
		 url: $("#mainUrl").val() + '/activityLocked/deleteActivityLockingData',
		 type:'POST',
		 data: formData,
		 success:function(data){
			 
		 }
	 });
 }
 function sendSaveRequest(formData){
	 var csrfToken = getCsrfToken();
	 formData.push({name: csrfToken.parameterName ,value: csrfToken.token});
	 formData.push({name: "studyActivityId" ,value: $("#crfName").find(':selected').attr('data-said')});
	 formData.push({name: "activitySartDate" ,value: activityStartTime});
	 var url = $("#mainUrl").val() + '/studyActivity/saveStudyActivityFromsData';
	 if($("#crfName option:selected").data("post") !== 0 &&
			 $("#crfName option:selected").data("post") !== 'null'&&
			 $("#crfName option:selected").data("post") !== null){
		 url = $("#mainUrl").val() + $("#crfName option:selected").data("post");
	 }
	 $.ajax({
	 	url: url,
		type:'POST',
		data: formData,
		success:function(e){
			if(e.type === 'Success'){
				unlockRecord();
				$('#subName').val("");
				displayMessage("success",e.message);	
				clearActivityForm();
				$(".group-parent").hide();
				$(".reset-element").val('');
			}else{
				displayMessage("error",e.message);
			}
			$(".loader").hide();
		},
		error:function(e){
			displayMessage("error",e.message);
			$(".loader").hide();
		}
	 });
 }

function loadForm(activityId){
	$.ajax({
		url:$("#mainUrl").val() +'/studyActivity/studyDesignActivityDetails/' + activityId,
		type:'GET',
		success:function(d){
			buildForm(d);
		}
	});
}

$(document).on('click','#frmActivityForm .form-submit',function(e){
	e.preventDefault();
	if(!$("#subName").hasClass("validate")){
		$("#subName").addClass("validate");
		$("#subName").attr("data-validate",'required');	
	}
	
	var form = $(this).closest("form");
	if(validation.validate($(form)[0])){
		$(".loader").show();
		saveDynamicForm();
	}
});

$(document).on('click',"[control-type='RB']",function(e){
	var val = $(this).val();
	var dependencies = filterJsonArray(dependentRules,'sourceParameterId', $(this).attr('name').split('_')[1]);
	for(var d = 0;d < dependencies.length; d++){
		if(dependencies[d].condition === "eq" &&  val === dependencies[d].controlTypeLspId.toString()){
			if(dependencies[d].paramterAction === "disable"){
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").prop("disabled", "disabled");
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").val('');
			}
			else if(dependencies[d].paramterAction === "enable"){
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").prop("disabled", "");
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").val('');
			}
			else if(dependencies[d].paramterAction === "setvalue"){
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").prop("disabled", "");
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").val('');
			}
		}
	}
});

$(document).on('change',"[control-type='TB'],[control-type='TA']",function(e){
	var val = $(this).val();
	var dependencies = filterJsonArray(dependentRules,'sourceParameterId', $(this).attr('name').split('_')[1]);
	for(var d = 0;d < dependencies.length; d++){
		if(dependencies[d].condition === "eq" &&  val === dependencies[d].controlTypeLspId.toString()){
			if(dependencies[d].paramterAction === "disable"){
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").prop("disabled", "disabled");
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").val('');
			}
			else if(dependencies[d].paramterAction === "enable"){
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").prop("disabled", "");
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").val('');
			}
			else if(dependencies[d].paramterAction === "setvalue"){
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").prop("disabled", "");
				$("[name='field_" + dependencies[d].enableOrDiableParameterIds + "']").val('');
			}
		}
	}
	
	validations = filterJsonArray(activityRules,'sourceParameterId', $(this).attr('name').split('_')[1]);
	for(var d = 0;d < validations.length; d++){
		if(validations[d].condition === "ltAndgt"){
			if(parseInt(val) < parseInt(validations[d].firstValue) || parseInt(val) > parseInt(validations[d].secondValue)){
				displayMessage("error", "Value is out of range(" + validations[d].firstValue + "-" + validations[d].secondValue + ")");
				$(this).attr("data-valid", false);
				$(this).addClass('validate-error');
			}
		}
		else if(validations[d].condition === "leAndge"){
			if(parseInt(val) <= parseInt(validations[d].firstValue) || parseInt(val) >= parseInt(validations[d].secondValue)){
				displayMessage("error", "Value is out of range(" + validations[d].firstValue + "-" + validations[d].secondValue + ")");
				$(this).attr("data-valid", false);
				$(this).addClass('validate-error');
			}
		}
		else if(validations[d].condition === "gt"){
			if(parseInt(val) > parseInt(validations[d].firstValue)){
				displayMessage("error", "Value is greater than (" + validations[d].firstValue + ")");
				$(this).attr("data-valid", false);
				$(this).addClass('validate-error');
			}
		}
		else if(validations[d].condition === "lt"){
			if(parseInt(val) < parseInt(validations[d].firstValue)){
				displayMessage("error", "Value is less than (" + validations[d].firstValue + ")");
				$(this).attr("data-valid", false);
				$(this).addClass('validate-error');
			}
		}
		else if(validations[d].condition === "eq"){
			if(parseInt(val) === parseInt(validations[d].firstValue)){
				displayMessage("error", "Value is equal (" + validations[d].firstValue + ")");
				$(this).attr("data-valid", false);
				$(this).addClass('validate-error');
			}
		}
		else if(validations[d].condition === "ge"){
			if(parseInt(val) >= parseInt(validations[d].firstValue)){
				displayMessage("error", "Value is greater than or equal (" + validations[d].firstValue + ")");
				$(this).attr("data-valid", false);
				$(this).addClass('validate-error');
			}
		}
		else if(validations[d].condition === "le"){
			if(parseInt(val) >= parseInt(validations[d].firstValue)){
				displayMessage("error", "Value is less than or equal (" + validations[d].firstValue + ")");
				$(this).attr("data-valid", false);
				$(this).addClass('validate-error');
			}
		}
	}
});

if($("#pageType").val() === "activity"){
	$("#crfFormDiv").show();
}
else{
	$("#crfReviewDiv").show();
}	
function clearActivityForm(){
	$.each($(".dynamic_element"),function(index,ele){
		if($(ele).attr("type") === "radio" || $(ele).attr("type") === "checkbox"){
			$(ele).prop('checked',false);
		}
		else{
			$(ele).val('');		
		}
	});
}

function clearFields(parentId){
	$.each($("#" + parentId).find("input,select,textarea"),function(index,ele){
		if($(ele).attr("type") === "radio" || $(ele).attr("type") === "checkbox"){
			$(ele).prop('checked',false);
		}
		else{
			$(ele).val('');		
		}
	});
}

function saveDynamicForm(){
	 $("[control-type='ET']").val(getServerDate());
	 var formData = $('#frmActivityForm .exclude').serializeArray();
	 var parameterValues = "";
	 var subjectNumber = $("#subName").val();
	 var period = $("#subName").data("period");
	 var treatment= $("#subName").data("treatment");
	 var rowCount = 0;
	 $.each($("[data-tp='" + (period + "" + treatment) + "']").find('input,textarea,select'),function(index,element){
		 if(!$(element).hasClass('exclude') && !$(element).hasClass('exclude-element') 
				 && $(element).attr("name") !== "_csrf"){
			 if($(element).attr("type") === 'radio'){
				 if($(element).is(":checked")){
					 formData.push({name: 'pmDto[' + rowCount + '].parameterId' ,value:  $(element).attr('name').toString().split('_')[1]});
					 formData.push({name: 'pmDto[' + rowCount + '].parameterValue' ,value:  $(element).val()});
					 rowCount++;
				}
			 }
			 //else if($.trim($(element).val()).length > 0){
			 else{
				 formData.push({name: 'pmDto[' + rowCount + '].parameterId' ,value:  $(element).attr('name').toString().split('_')[1]});
				 formData.push({name: 'pmDto[' + rowCount + '].parameterValue' ,value:  $(element).val()});
				 rowCount++;
			 }
		 }
	 });
	 formData.push({name: "studyActivityId" ,value: $("[data-tp='" + (period + "" + treatment) + "']").attr("data-said").toString()});
	 
	 var volunteer = filterJsonArray(volunteerData, "vounteerNo", $("#subName").val());
	 formData.push({name: "volId" ,value: $("#subName").attr("data-value")});
	/* formData.push({name: "type" ,value: volunteer[0].type.toString()});*/
	 sendSaveRequest(formData);
}

function buildForm(activity) {
	debugger;
	if(activity.rules!==null){
		dependentRules = activity.rules.dependentRulesList;
		activityRules = activity.rules.crdtoList;
		dependentActivities= activity.rules.dependentActList;
		validationRules = activity.rules.valRulesList;	
	}
	
    var groupArray = [];
    var sortedGroupsArray = [];
    var sortGroupOrderArray = [];
    var paramSortedArray = [];
    var isExists = false;
    var subjects = "";var pm = 0;
    var formData = "";
    var hiddenFields = "";
    var script = "";
    var groupOrder = 0;
    var groupParameters = {}
    for(var treatmentActivity = 0;treatmentActivity < activity.gpdDtoList.length; treatmentActivity++){
	debugger;
    	hiddenFields = "";
    	formData = formData + "<div class='group-parent' data-said='" + activity.gpdDtoList[treatmentActivity].studyAtivityId  + "' data-tp='" + (activity.gpdDtoList[treatmentActivity].periodId + "" + activity.gpdDtoList[treatmentActivity].treatmentId) + "' style='display:none;' data-treatment='" + activity.gpdDtoList[treatmentActivity].treatmentId + "' data-period='" + activity.gpdDtoList[treatmentActivity].periodId + "'>";
    	formData = formData + "<table class='covide-form-table'><tr style='font-size: 16px; color: #1ABB9C;'><td style='width:100px;'>Period: </td><td style='width:100px;'><span class='tdPeriodNumber'></span></td></tr></table>";
    	groupArray = [];sortedGroupsArray = [];sortGroupOrderArray = [];paramSortedArray = [];
    	if(activity.gpdDtoList[treatmentActivity].parameterDto != null && activity.gpdDtoList[treatmentActivity].parameterDto != undefined){
    		for (pm = 0; pm < activity.gpdDtoList[treatmentActivity].parameterDto.length; pm++) {
    	        isExists = false;
    	        for (var ga = 0; ga < sortedGroupsArray.length; ga++) {
    	            if (activity.gpdDtoList[treatmentActivity].parameterDto[pm].group.groupId === sortedGroupsArray[ga].groupId) {
    	                isExists = true;
    	            }
    	        }
    	        if (!isExists) {
    	        	if(activity.gpdDtoList[treatmentActivity].parameterDto[pm].group.orderNo === undefined || activity.gpdDtoList[treatmentActivity].parameterDto[pm].group.orderNo === null|| activity.gpdDtoList[treatmentActivity].parameterDto[pm].group.orderNo === 0){
    	        		groupOrder = 9999999999;
    	        	}
    	        	else{
    	        		groupOrder = activity.gpdDtoList[treatmentActivity].parameterDto[pm].group.orderNo;
    	        	}
    	        	sortedGroupsArray.push({ groupId: activity.gpdDtoList[treatmentActivity].parameterDto[pm].group.groupId, groupName: activity.gpdDtoList[treatmentActivity].parameterDto[pm].group.groupName,order: groupOrder});
    	        	if(sortGroupOrderArray.indexOf(groupOrder) === -1)
    	        		sortGroupOrderArray.push(parseInt(groupOrder));
    	        }
    	    }
    	}
	    var cv = 0;var val = 0;
	    sortGroupOrderArray.sort(function(a, b){return a - b});
	    for(var sgoa = 0;sgoa < sortGroupOrderArray.length; sgoa++){
	    	for(var sga = 0;sga < sortedGroupsArray.length; sga++){
		    	if(sortGroupOrderArray[sgoa] === sortedGroupsArray[sga].order){
		    		groupArray.push(sortedGroupsArray[sga]);
		    	}
		    }
	    }
	    if(activity.gpdDtoList[treatmentActivity].parameterDto != null && activity.gpdDtoList[treatmentActivity].parameterDto != undefined){
	    	for(var po = 0;po < activity.gpdDtoList[treatmentActivity].parameterDto.length;po++){
		    	if(activity.gpdDtoList[treatmentActivity].parameterDto[po].orderNo === undefined || activity.gpdDtoList[treatmentActivity].parameterDto[po].orderNo === null|| activity.gpdDtoList[treatmentActivity].parameterDto[po].orderNo === 0){
	        		groupOrder = 9999999999;
	        	}
	        	else{
	        		groupOrder = activity.gpdDtoList[treatmentActivity].parameterDto[po].orderNo;
	        	}
		    	if(paramSortedArray.indexOf(parseInt(groupOrder)) === -1){
		    		paramSortedArray.push(parseInt(groupOrder));
		    	}
		    }
	    }
	    groupParameters = {}
	    paramSortedArray.sort(function(a, b){return a - b});
	    for(var psa = 0;psa < paramSortedArray.length; psa++){
	    	for(var po = 0;po < activity.gpdDtoList[treatmentActivity].parameterDto.length;po++){
	    		if(activity.gpdDtoList[treatmentActivity].parameterDto[po].orderNo === paramSortedArray[psa] || activity.gpdDtoList[treatmentActivity].parameterDto[po].orderNo === 9999999999){
	    			var groupParameter=groupParameters[activity.gpdDtoList[treatmentActivity].parameterDto[po].group.groupId];
			    	if(groupParameter!==null && groupParameter !== undefined){
			    		groupParameters[activity.gpdDtoList[treatmentActivity].parameterDto[po].group.groupId].push(activity.gpdDtoList[treatmentActivity].parameterDto[po]);
			    	}
			    	else{
			    		groupParameters[activity.gpdDtoList[treatmentActivity].parameterDto[po].group.groupId] = [];
			    		groupParameters[activity.gpdDtoList[treatmentActivity].parameterDto[po].group.groupId].push(activity.gpdDtoList[treatmentActivity].parameterDto[po]);
			    	}
	    		}
		    }
	    }
	    
	    formData = formData + "<table class='covide-form-table'>";
	    for (var g = 0; g < groupArray.length; g++) {
	        if ($.trim(groupArray[g].groupName).length > 0) {
	        	formData = formData + "<tr><td colspan='2'><h5>" + groupArray[g].groupName + "</h5></td><tr>";
	        }
	        
	        var classSet = false;
	        var parameters = groupParameters[groupArray[g].groupId];
	        for (pm = 0; pm < parameters.length; pm++) {
            	classSet = false;
            	var validation = filterJsonArray(validationRules,'sourceParamId',parameters[pm].parameterId);
            	if (parameters[pm].controlType.contentCode === "TB" || parameters[pm].controlType.contentCode === "AC") {
                    formData = formData + "<tr><td>" + parameters[pm].parameterName + "</td><td>";
                    formData = formData + "<input type='text' control-type='" + parameters[pm].controlType.contentCode + "' name='field_" + parameters[pm].parameterId + "'";
                    for(val = 0 ;val < validation.length; val++){
                    	if(validation[val].condition === "NOTEMPTY"){
                    		classSet = true;
                    		formData = formData + " class='form-control dynamic_element validate' data-validate='required' ";
                    	}
                    	else if(validation[val].condition === "DISABLED"){
                    		formData = formData + " disabled";
                    	}
                    	else {
                    		formData = formData + " data-texttype='" + validation[val].condition + "'";
                    	}
                    }
                    if(!classSet){
                    	formData = formData + " class='form-control dynamic_element' ";
                    }
                    
                    formData = formData + "/></td><tr>";
                }
                else if (parameters[pm].controlType.contentCode === "TA") {
                    formData = formData + "<tr><td>" + parameters[pm].parameterName + "</td><td>";
                    formData = formData + "<textarea class='form-control dynamic_element' control-type='TA' name='field_" + parameters[pm].parameterId + "'></textarea></td><tr>";
                }
                else if (parameters[pm].controlType.contentCode === "RB") {
                	formData = formData + "<tr><td>" + parameters[pm].parameterName + "</td><td style='min-width:200px;'>";
                	parameters[pm].controlType.valuesDto.sort(function(a,b){
                	    return a.order - b.order;
                	    }
                	);
                	
                    for (cv = 0; cv < parameters[pm].controlType.valuesDto.length; cv++) {
                        formData = formData + "<label class='label ds-ml-5'><input type='radio' control-type='RB' name='field_" + parameters[pm].parameterId + "' value='" + parameters[pm].controlType.valuesDto[cv].valueId + "' ";
                        for(val = 0 ;val < validation.length; val++){
                        	if(validation[val].condition === "NOTEMPTY"){
                        		classSet = true; 
                        		formData = formData + " class='dynamic_element validate' data-validate='required' ";
                        	}
                        	else if(validation[val].condition === "DISABLED"){
                        		formData = formData + " disabled";
                        	}
                        }
                        if(!classSet){
                        	formData = formData + " class='dynamic_element' ";
                        }
                        formData = formData + "/>" + parameters[pm].controlType.valuesDto[cv].valueName + "</label>";
                    }
                    formData = formData + "</td></tr>";
                }
                else if (parameters[pm].controlType.contentCode === "SB") {
                    formData = formData + "<tr><td>" + parameters[pm].parameterName + "</td><td><select class='form-control dynamic_element' control-type='RB' name='field_" + activity.gpdDtoList[treatmentActivity].parameterDto[pm].parameterId + "'>";
                    formData = formData + "<option value=''>Select</option>";
                    for (cv = 0; cv < parameters[pm].controlType.valuesDto.length; cv++) {
                        formData = formData + "<option value='" + parameters[pm].valueId + "'>" + parameters[pm].valueName + "</option>";
                    }
                    formData = formData + "</select></td></tr>";
                }
                else if (parameters[pm].controlType.contentCode === "CB") {
                	formData = formData + "<tr><td>" + parameters[pm].parameterName + "</td><td>";
                    formData = formData + "<label class='label'><input class='dynamic_element' type='checkbox' control-type='CB' name='field_" + parameters[pm].parameterId + "' value='" + parameters[pm].controlType.valuesDto[0].valueId + "'/>" + parameters[pm].controlType.valuesDto[0].valueName + "</label>";
                    formData = formData + "</td></tr>";
                }
                else if (parameters[pm].controlType.contentCode === "DP") {
                	formData = formData + "<tr><td>" + parameters[pm].parameterName + "</td><td>";
                    formData = formData + "<input type='text' class='form-control dynamic_element' control-type='DP' name='field_" + parameters[pm].parameterId + "'/></td><tr>";
                    script = script + "loadDatePicker('field_" + parameters[pm].parameterId + "');";
                }
                else if (parameters[pm].controlType.contentCode === "TP") {
                	formData = formData + "<tr><td>" + parameters[pm].parameterName + "</td><td>";
                    formData = formData + "<input type='time' class='form-control dynamic_element' control-type='TP' name='field_" + parameters[pm].parameterId + "'/></td><tr>";
                }
                else if (parameters[pm].controlType.contentCode === "ST") {
                	hiddenFields = hiddenFields + "<input class='dynamic_element' type='hidden' control-type='ST' name='field_" + parameters[pm].parameterId + "'/>";
                }
                else if (parameters[pm].controlType.contentCode === "ET") {
                	hiddenFields = hiddenFields + "<input class='dynamic_element' type='hidden' control-type='ET' name='field_" + parameters[pm].parameterId + "'/>";
                }
                else if (parameters[pm].controlType.contentCode === "LABEL") {
                	formData = formData + "<label>" + parameters[pm].parameterName + "</label>";
                }
	        }
	    }
	    formData = formData + "<tr><td></td></tr><tr><td style='text-align:center;' colspan='2'><button class='btn btn-primary form-submit'>Submit</button></td></tr>";
	    formData = formData + "</table>";
	    formData = formData + hiddenFields + "</div>";
	    formData = formData + "<script>" + script + "</script>";
	}
   
    $("#activityDiv").empty();
    $("#activityDiv").append(formData);
}

function buildReviewForm(activity) {
	validationRules = activity.rulesDetails.valRulesList;
	dependentRules = activity.rulesDetails.dependentRulesList;
	activityRules = activity.rulesDetails.crdtoList;
    var groupArray = [];
    var isExists = false;
    for (var pm = 0; pm < activity.parameterDto.length; pm++) {
        isExists = false;
        for (var ga = 0; ga < groupArray.length; ga++) {
            if (activity.parameterDto[pm].group.groupId === groupArray[ga].groupId) {
                isExists = true;
            }
        }
        if (!isExists) {
            groupArray.push({ groupId: activity.parameterDto[pm].group.groupId, groupName: activity.parameterDto[pm].group.groupName });
        }
    }

    var formData = "";
    var cv = 0;var val = 0;
    for (var g = 0; g < groupArray.length; g++) {
        if ($.trim(groupArray[g].groupName).length > 0) {
            formData = formData + "<div><h2>" + groupArray[g].groupName + "</h2></div>";
        }
        formData = formData + "<table class='ds-form-table'>";
        var classSet = false;
        for (pm = 0; pm < activity.parameterDto.length; pm++) {
            if (activity.parameterDto[pm].group.groupId === groupArray[g].groupId) {
            	classSet = false;
            	var validation = filterJsonArray( validationRules,'sourceParamId',activity.parameterDto[pm].parameterId);
            	if (activity.parameterDto[pm].controlType.contentCode === "TB" || activity.parameterDto[pm].controlType.contentCode === "AC" || activity.parameterDto[pm].controlType.contentCode === "DP") {
                    formData = formData + "<tr><td>" + activity.parameterDto[pm].parameterName + "</td><td>";
                    formData = formData + "<input type='text' control-type='" + activity.parameterDto[pm].controlType.contentCode + "' name='field_" + activity.parameterDto[pm].parameterId + "'";
                    for(val = 0 ;val < validation.length; val++){
                    	if(validation[val].condition === "NOTEMPTY"){
                    		classSet = true;
                    		formData = formData + " class='form-control dynamic_element validate' data-validate='required' ";
                    	}
                    	else if(validation[val].condition === "DISABLED"){
                    		formData = formData + " disabled";
                    	}
                    	else {
                    		formData = formData + " data-texttype='" + validation[val].condition + "'";
                    	}
                    }
                    if(!classSet){
                    	formData = formData + " class='form-control dynamic_element' ";
                    }
                    
                    formData = formData + "/></td><tr>";
                }
                else if (activity.parameterDto[pm].controlType.contentCode === "TA") {
                    formData = formData + "<tr><td>" + activity.parameterDto[pm].parameterName + "</td><td>";
                    formData = formData + "<textarea class='form-control dynamic_element' control-type='TA' name='field_" + activity.parameterDto[pm].parameterId + "'></textarea></td><tr>";
                }
                else if (activity.parameterDto[pm].controlType.contentCode === "RB") {
                    formData = formData + "<tr><td>" + activity.parameterDto[pm].parameterName + "</td><td>";
                    for (cv = 0; cv < activity.parameterDto[pm].controlType.valuesDto.length; cv++) {
                        formData = formData + "<label class='label'><input type='radio' control-type='RB' name='field_" + activity.parameterDto[pm].parameterId + "' value='" + activity.parameterDto[pm].controlType.valuesDto[cv].valueId + "' ";
                        for(val = 0 ;val < validation.length; val++){
                        	if(validation[val].condition === "NOTEMPTY"){
                        		classSet = true;
                        		formData = formData + " class='dynamic_element validate' data-validate='required' ";
                        	}
                        	else if(validation[val].condition === "DISABLED"){
                        		formData = formData + " disabled";
                        	}
                        }
                        if(!classSet){
                        	formData = formData + " class='dynamic_element' ";
                        }
                        formData = formData + "/>" + activity.parameterDto[pm].controlType.valuesDto[cv].valueName + "</label>";
                    }
                    formData = formData + "</td></tr>";
                }
                else if (activity.parameterDto[pm].controlType.contentCode === "SB") {
                    formData = formData + "<tr><td>" + activity.parameterDto[pm].parameterName + "</td><td>";
                    for (cv = 0; cv < activity.parameterDto[pm].controlType.valuesDto.length; cv++) {
                        formData = formData + "<label class='label'><input class='dynamic_element' type='radio' control-type='RB' name='field_" + activity.parameterDto[pm].parameterId + "' value='" + activity.parameterDto[pm].valueId + "'/>" + activity.parameterDto[pm].valueName + "</label>";
                    }
                    formData = formData + "</td></tr>";
                }
            }
        }
        formData = formData + "</table>";
    }
    $("#activityDiv").empty();
    $("#activityDiv").append(formData);
    $("#activityDiv").append("<div style='text-align:center;' class='col-lg-12 mgn-t20'><button id='btnSubmit' class='btn btn-primary'>Submit</button></div>");
}
//buildForm(str);
var cpuReviewData = "";
var dsrdMapVals = "";
var actCode = "";
var discripancyType ="";
function buildReviewDataList(reviewData, reviewType){
	cpuReviewData = reviewData;
	discripancyType = reviewType;
	$('#cpuActReviewDiv').empty();
	$('#reviewDataTab').dataTable().fnClearTable();
    $('#reviewDataTab').dataTable().fnDraw();
    $('#reviewDataTab').dataTable().fnDestroy();
	var htmlStr = '<table class="table table-bordered table-striped" id="reviewDataTab">'
				  +'<thead>'
				  +'<th>Subject No.</th>'
				  +'<th>Timepoint</th>'
				  +'<th>Period</th>'
				  +'<th>Created By</th>'
				  +'<th>Created On</th>'
				  +'</thead>';
	debugger;
	if(reviewData != undefined){
		var sfdDtoListArr = [];
		var bodyStr = '<tbody>';
		if(reviewData.sfdDtoList != undefined && reviewData.sfdDtoList.length > 0){
			if(reviewType == "review")
				sfdDtoListArr = reviewData.sfdDtoList;
			else{
				for(var p=0; p<reviewData.sfdDtoList.length > 0; p++){
					var drdActMap = reviewData.drdActMap[$("#crfName").val()];
					if(drdActMap != null && drdActMap != undefined){
						var discrArr = drdActMap[reviewData.sfdDtoList[p].id];
						if(discrArr != null && discrArr != undefined){
							sfdDtoListArr.push(reviewData.sfdDtoList[p]);
						}
					}
				}
			}
			for(var i=0; i<sfdDtoListArr.length > 0; i++){
				var createdOn = new Date(sfdDtoListArr[i].createdOn);
				createdOn = $.datepicker.formatDate('yy-mm-dd', createdOn);
				debugger;
				dsrdMapVals = reviewData.drdActMap;
				bodyStr += '<tr onclick="showRecordDetails('+sfdDtoListArr[i].id+','+"'"+reviewData.activityCode+"'"+','+"'"+$("#crfName").val()+"'"+')">'
					    +'<td>'+sfdDtoListArr[i].subjectNo+'</td>'
					    +'<td>'+sfdDtoListArr[i].timePoint+'</td>'
					    +'<td>'+sfdDtoListArr[i].period+'</td>'
					    +'<td>'+sfdDtoListArr[i].createdBy+'</td>'
					    +'<td>'+createdOn+'</td>'
					    +'</tr>';
			}
		}
		
		bodyStr +='</tbody></table>';
		$('#cpuActReviewDiv').append(htmlStr+bodyStr);
		$('#reviewDataTab').DataTable();
		$('#crfReviewDiv').hide();
		$('#cpuActReviewDiv').show();
	}else{
		hrmlStr += +'<tbody></tbody></table>';
		$('#cpuActReviewDiv').append(htmlStr);
		$('#reviewDataTab').DataTable();
		$('#crfReviewDiv').hide();
		$('#cpuActReviewDiv').show();
	}
}
var reocrdId = "";
function showRecordDetails(id, activityCode, activityId){
	$('#cpuActivityDataDiv').empty();
	$('#crfReviewDiv').empty();
	$('#cpuDataTitle').empty();
	actCode = activityCode
	var fianlLabelArr = [];
	if(cpuReviewData != "" && cpuReviewData != undefined){
		debugger;
		var cpuData = "";
		var htmlStr = '';
		var flagVal = $('#startFlagVal').val();
		var selectArr = [];
		var dataArr = [];
		var flagVal2 = $("#startFlagVal").val();
		var parameterId = 0;
		var index = 0;
		var labelkey = "";
		if(activityCode == "MealsCollection"){
			selectArr.push('Yes');
			selectArr.push('No');
			cpuData = cpuReviewData.mealsDataMap[id];
			reocrdId = cpuData.mealsDataId;
			fianlLabelArr = [];
			var labelArr = ['Start_Time_'+id, 'End_Time_'+id, 'Consumption_'+id, 'Comments_'+id];
			if(discripancyType != "review"){
				for(var f=0; f<labelArr.length; f++){
					var actMap = dsrdMapVals[activityId];
					if(actMap != undefined){
						if(actMap[reocrdId] != undefined){
							if(actMap[reocrdId][labelArr[f]] != undefined)
								fianlLabelArr.push(labelArr[f]);
						}
					}
				}
			}else fianlLabelArr = labelArr;
			if(cpuData != undefined){
				if(fianlLabelArr.indexOf('Start_Time_'+id) != -1){
					labelkey = "Start_Time_"+id;
					htmlStr += '<tr><td style="width:20%;">Start Time :</td>'
					       +'<td style="width:10%;"><input type="time" size="3" class="form-control" value="'+cpuData.startTime+'" readonly="readonly">'+'</td>'
					       +'<td><img id="'+cpuData.mealsDataId+'_0_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+cpuData.mealsDataId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+','+"'time'"+','+"'"+cpuData.startTime+"'"+')"></td></tr>';
					index++;   
				}
				if(fianlLabelArr.indexOf('End_Time_'+id) != -1){
					labelkey = "End_Time_"+id;
					htmlStr +='<tr><td>End Time :</td>'
					       +'<td style="width:10%;"><input type="time" size="3" class="form-control" value="'+cpuData.endTime+'" readonly="readonly">'+'</td>'
					       +'<td><img id="'+cpuData.mealsDataId+'_1_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+cpuData.mealsDataId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+', '+"'time'"+','+"'"+cpuData.endTime+"'"+')"></td></tr>';
					index++;     
				}
				if(fianlLabelArr.indexOf('Consumption_'+id) != -1){
					labelkey = "Consumption_"+id;
					htmlStr +='<tr><td>Consumption :</td>'
					       +'<td style="width:10%;"><input type="text" size="3" class="form-control" value="'+cpuData.consumption+'" readonly="readonly">'+'</td>'
					       +'<td><img id="'+cpuData.mealsDataId+'_2_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+cpuData.mealsDataId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+', '+"'text'"+','+"'"+cpuData.consumption+"'"+')"></td></tr>';
					index++;   
				}
				if(fianlLabelArr.indexOf('Comments_'+id) != -1){
					labelkey = "Comments_"+id;
					htmlStr +='<tr><td>Comments :</td>'
					       +'<td style="width:10%;"><input type="text" size="3" class="form-control" value="'+cpuData.comments+'" readonly="readonly">'+'</td>'
					       +'<td><img id="'+cpuData.mealsDataId+'_3_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+cpuData.mealsDataId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+','+"'textarea'"+','+"'"+cpuData.comments+"'"+')"></td></tr>';
					index++;   
				}
						  
					      
				/*$('#cpuActivityDataDiv').append(htmlStr);
				$('#cpuDataTitle').append("Meals Data");
				$('#myModal').modal('show');*/
				$('#crfReviewDiv').append(htmlStr);
				$("#cpuActReviewDiv").hide('slow',function(){
			    	$("#crfReviewDiv").show('slow');	
			    });
				generateFlagVariations(fianlLabelArr, reocrdId, activityId);
			}
		}else if(activityCode == "SampleCollection"){
			cpuData = cpuReviewData.samplesDataMap[id];
			reocrdId = cpuData.sampleDataId;
			var parameterId =0;
			var labelArr = ['actualTime_'+id];
			fianlLabelArr = [];
			if(discripancyType != "review"){
				var actMap = dsrdMapVals[activityId];
				if(actMap != undefined){
					if(actMap[reocrdId] != undefined){
						if(actMap[reocrdId]['actualTime_'+id] != undefined)
							fianlLabelArr.push('actualTime_'+id);
					}
				}
			}else fianlLabelArr = labelArr;
			if(cpuData != undefined){
				if(fianlLabelArr.indexOf('actualTime_'+id) != -1){
					labelkey = "actualTime_"+id;
					htmlStr += '<tr><td style="width:20%;">Collection Time :</td>'
					       +'<td style="width:10%;"><input type="time" size="3" class="form-control" value="'+cpuData.collectionTime+'" readonly="readonly">'+'</td>'
					       +'<td><img id="'+reocrdId+'_0_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+reocrdId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+', '+"'time'"+','+"'"+cpuData.collectionTime+"'"+')"></td></tr>';
					index++;
				}
				$('#crfReviewDiv').append(htmlStr);
				$("#cpuActReviewDiv").hide('slow',function(){
			    	$("#crfReviewDiv").show('slow');	
			    });
				generateFlagVariations(fianlLabelArr, reocrdId, activityId);
			}
		}else if(activityCode == "StudyExecutionVitals" || activityCode == "DosingCollection"){
			var labelArr = [];
			var parameterId=0;
			fianlLabelArr = [];
			if(activityCode == "StudyExecutionVitals"){
				labelArr.push('Start_Time_'+id);
				labelArr.push('End_Time_'+id);
				cpuData = cpuReviewData.vitalDataMap[id];
				reocrdId = cpuData.vitalDataId;
				dataArr = cpuReviewData.vitalDataMap[id].parameterDto;
				if(dataArr != null && dataArr != undefined){
					for(var k=0; k<dataArr.length; k++){
						labelArr.push(reocrdId+"_"+dataArr[k].parameterId);
					}
				}
				if(discripancyType != "review"){
					for(var f=0; f<labelArr.length; f++){
						var actMap = dsrdMapVals[activityId];
						if(actMap != undefined){
							if(actMap[reocrdId] != undefined){
								if(actMap[reocrdId][labelArr[f]] != undefined){
									fianlLabelArr.push(labelArr[f]);
								}
							}
						}
					}
				}else
					fianlLabelArr = labelArr;
				if(cpuData != undefined){
					if(fianlLabelArr.indexOf('Start_Time_'+id) != -1){
						labelkey = "Start_Time_"+id;
						htmlStr += '<tr><td style="width:20%;">Start Time :</td>'
						       +'<td style="width:10%;"><input type="time" size="3" class="form-control" value="'+cpuData.startTime+'" readonly="readonly">'+'</td>'
						       +'<td><img id="'+reocrdId+'_0_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+reocrdId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+','+"'time'"+','+"'"+cpuData.startTime+"'"+')"></td></tr>';
						index++;      
					}
					if(fianlLabelArr.indexOf('End_Time_'+id) != -1){
						labelkey = "End_Time_"+id;
						htmlStr += '<tr><td>End Time :</td>'
							   +'<td style="width:10%;"><input type="time" size="3" class="form-control" value="'+cpuData.endTime+'" readonly="readonly">'+'</td>'
							   +'<td><img id="'+reocrdId+'_1_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+reocrdId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+','+"'time'"+','+"'"+cpuData.endTime+"'"+')"></td></tr>';
						index++;
					}
				}
				
			}else if(activityCode == "DosingCollection"){
				labelArr = [];
				fianlLabelArr = [];
				labelArr.push('actualTime_'+id);
				cpuData = cpuReviewData.doseDataMap[id];
				reocrdId = cpuData.dosingDataId;
				dataArr = cpuReviewData.doseDataMap[id].parameterDto;
				if(dataArr != null && dataArr != undefined){
					for(var k=0; k<dataArr.length; k++){
						labelArr.push(reocrdId+"_"+dataArr[k].parameterId);
					}
				}
				if(discripancyType != "review"){
					for(var f=0; f<labelArr.length; f++){
						var actMap = dsrdMapVals[activityId];
						if(actMap != undefined){
							if(actMap[reocrdId] != undefined){
								if(actMap[reocrdId][labelArr[f]] != undefined)
									fianlLabelArr.push(labelArr[f]);
							}
						}
					}
				}else 
					fianlLabelArr = labelArr;
				if(cpuData != undefined){
					if(fianlLabelArr.indexOf('actualTime_'+id) != -1){
						labelkey = "actualTime_"+id;
						htmlStr += '<tr><td style="width:20%;">Dosed Time :</td>'
						       +'<td style="width:10%;"><input type="time" size="3" class="form-control" value="'+cpuData.dosingTime+'" readonly="readonly">'+'</td>'
						       +'<td><img id="'+reocrdId+'_0_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+labelkey+"'"+','+"'"+reocrdId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+','+"'time'"+','+"'"+cpuData.dosingTime+"'"+')"></td></tr>';
						index++;
					}
				}
			}
			if(dataArr != null && dataArr != undefined){
				for(var k=0; k<dataArr.length; k++){
//					labelArr.push(reocrdId+"_"+dataArr[k].parameterId);
					parameterId = reocrdId+"_"+dataArr[k].parameterId;
					if(fianlLabelArr.indexOf(parameterId) != -1){
						htmlStr +='<tr><td>'+dataArr[k].parameterName+'</td> <td>';
						if(dataArr[k].controlType.contentCode == "RB" || dataArr[k].controlType.contentCode == "CB" || dataArr[k].controlType.contentCode == "SB"){
							if(dataArr[k].controlType.contentCode == "RB"){
								selectVals = dataArr[k].controlType.valuesDto;
								if(selectVals.length > 0){
							       	 for(var j=0; j<selectVals.length; j++){
							       		 if(cpuReviewData.paramDataMap[dataArr[k].parameterId].globalValId == selectVals[j].valueId)
							       			htmlStr += '<input type="radio" readonly="readonly" name="param_'+dataArr[k].parameterId+'" id="param_'+dataArr[k].parameterId+'" checked value="'+selectVals[j].valueName+'">'+selectVals[j].valueName+'&nbsp&nbsp&nbsp';
							       		 else 	
							       			htmlStr += '<input type="radio" readonly="readonly"  name="param_'+dataArr[k].parameterId+'" id="param_'+dataArr[k].parameterId+'"  value="'+selectVals[j].valueName+'">'+selectVals[j].valueName+' &nbsp;&nbsp; '
							       	 }	 
						         }
								
							}else if(dataArr[k].controlType.contentCode == "CB"){
								if(selectVals.length > 0){
							       	 for(var j=0; j<selectVals.length; j++){
							       		 if(cpuReviewData.paramDataMap[dataArr[k].parameterId].globalValId == selectVals[j].valueId)
							       			htmlStr += '<input type="checkbox" readonly="readonly"  name="param_'+dataArr[k].parameterId+'" id="param_'+dataArr[k].parameterId+'" checked value="'+selectVals[j].valueName+'">'+selectVals[j].valueName+'&nbsp&nbsp&nbsp';
							       		 else 	
							       			htmlStr += '<input type="checkbox" readonly="readonly" name="param_'+dataArr[k].parameterId+'" id="param_'+dataArr[k].parameterId+'"  value="'+selectVals[j].valueName+'">'+selectVals[j].valueName+' &nbsp;&nbsp; '
							       	 }	 
						         }
							}else if(dataArr[k].controlType.contentCode == "SB"){
								htmlStr += '<td><select  class="form-control" readonly="readonly" name="param_'+dataArr[k].parameterId+'" id="param_'+dataArr[k].parameterId+'">'
								  +'<option value="'+dataVal+'">'+dataVal+'</option></select> &nbsp;&nbsp; ';
//					              +'<option value="value="-1">---Select---</option>';
					              if(selectVals.length > 0){
					            	 for(var j=0; j<selectVals.length; j++){
					            		 if(cpuReviewData.paramDataMap[dataArr[k].parameterId].globalValId == selectVals[j].valueId){
					            			 htmlStr += '<option value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+'</option>'
					            		 }
					            	 }
					              }
					              htmlStr += '</select>';
							}
							htmlStr += '</td><td><img id="'+reocrdId+'_'+index+'_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+parameterId+"'"+','+"'"+reocrdId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+', '+"'Radio'"+','+"'"+index+"'"+')"></td></tr>';
							index++;
						}else{
							if(dataArr[k].controlType.contentCode == "TB"){
								debugger;
								htmlStr += '<input type="text" readonly="readonly" name="param_'+dataArr[k].parameterId+'" id="param_'+dataArr[k].parameterId+'" class="form-control" value="'+cpuReviewData.paramDataMap[dataArr[k].parameterId].paramterValue+'">'
										+'<td><img id="'+reocrdId+'_'+index+'_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+parameterId+"'"+','+"'"+reocrdId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+', '+"'Radio'"+','+"'"+index+"'"+')"></td></tr>';
								index++;
							}else if(dataArr[k].controlType.contentCode == "TA"){
								htmlStr += '<textarea readonly="readonly" rows="5" cols="50" class="form-control" name="param_'+dataArr[k].parameterId+'" id="param_'+dataArr[k].parameterId+'">'+cpuReviewData.paramDataMap[dataArr[k].parameterId].paramterValue+'</textarea>'
										+'<td><img id="'+reocrdId+'_'+index+'_image" alt="RaisDiscrepancy" src="'+flagVal+'" onclick="riseDescripency('+"'"+parameterId+"'"+','+"'"+reocrdId+"'"+','+"'"+parameterId+"'"+','+"'"+index+"'"+', '+"'Radio'"+','+"'"+index+"'"+')"></td></tr>';
								index++;
							}
						}
					}
				}
			}
			
			$('#crfReviewDiv').append(htmlStr);
			$("#cpuActReviewDiv").hide('slow',function(){
		    	$("#crfReviewDiv").show('slow');	
		    });
			generateFlagVariations(fianlLabelArr, reocrdId, activityId);	
		}
	
	}
}
function generateFlagVariations(lbArr, dataActId, actId){
	for(var j=0; j<lbArr.length; j++){
		generateFlagsBasedOnLabel(lbArr[j], j, dataActId, actId);
	}
	
}
function generateFlagsBasedOnLabel(label, index, dataActivityId, actid){
	var desArr = null
	var objectKeyLength = Object.entries(dsrdMapVals).length;
	if(objectKeyLength > 0){
		var actIdMap = dsrdMapVals[actid];
		if(actIdMap != null && actIdMap != undefined){
			var dataActMap = actIdMap[dataActivityId];
			if(dataActMap != null && dataActMap != undefined){
				desArr = dataActMap[label];
			}
		}
	}
	var commentsFlag = false;
	var responseDateFlag = true;
	debugger;
	if(desArr != null && desArr != undefined){
		for(var i=0; i<desArr.length; i++){
			if(desArr[i].commentedOn != null && desArr[i].commentedOn != undefined)
				commentsFlag = true;
			
			if(desArr[i].responseGivenDate == null || desArr[i].responseGivenDate == undefined)
				responseDateFlag = false;
		}
//		alert(label +": "+commentsFlag+"::"+responseDateFlag);
		if(commentsFlag && responseDateFlag == false){
			var flagVal = $('#inProgressVal').val();
			$('#'+reocrdId+'_'+index+'_image').attr('src',flagVal);
		}else if(commentsFlag && responseDateFlag){
			//Green color
			var flagVal = $('#completedVal').val();
			$('#'+reocrdId+'_'+index+'_image').attr('src',flagVal);
		}else{
			//Normal flag
			var flagVal = $('#startFlagVal').val();
			$('#'+reocrdId+'_'+index+'_image').attr('src',flagVal);
		}
	}
}
function riseDescripency(label, dataVal, parameterid, index, fieldType, fieldValue){
	debugger;
	$('#staticFromResponse').hide();
	var actId = $("#crfName").val();
	$("#hiddenDetails").empty();
	$("#tblComments tbody").empty();
	var desArr =[];
	debugger;
	var dsrMap = dsrdMapVals[actId];
	if(dsrMap != null && dsrMap != undefined){
		desArr = dsrMap[dataVal][label];
	}
	
	var actType = "";
	if(actCode == "MealsCollection")
		actType = "Meals";
	else if(actCode == "SampleCollection")
		actType = "Samples";
	else if(actCode == "StudyExecutionVitals")
		actType = "Vitals";
	else if(actCode == "DosingCollection")
		actType = "dosing";
	var htmlHideStr = '<input type="hidden" name="fieldName" id="fieldName" value="'+label+'">'
				    +'<input type="hidden" name="activityDataId" id="activityDataId" value="'+dataVal+'">'
				    +'<input type="hidden" name="indexVal" id="indexVal" value="'+index+'">'
				    +'<input type="hidden" name="actName" id="actName" value="'+actType+'">'
				    +'<input type="hidden" name="parameterId" id="parameterId" value="'+parameterid+'">';
	var htmlStr = '';
	var responseDateFlag = true;
	var commentsFlag = false;
	if(desArr != null && desArr != undefined){
		for(var i=0; i<desArr.length; i++){
			var createdOn = "";
			if(desArr[i].commentedOn != null && desArr[i].commentedOn != undefined){
				createdOn = new Date(desArr[i].commentedOn);
				var time  = createdOn.getHours()+":"+createdOn.getMinutes()+":"+createdOn.getSeconds();
				createdOn = $.datepicker.formatDate('dd MM yy', createdOn);
         		createdOn = createdOn +" "+time;
         		commentsFlag = true;
			}
			var responseOn = "";
			if(desArr[i].responseGivenDate != null && desArr[i].responseGivenDate != undefined){
				responseOn = new Date(desArr[i].responseGivenDate);
				var time  = responseOn.getHours()+":"+responseOn.getMinutes()+":"+responseOn.getSeconds();
				responseOn = $.datepicker.formatDate('dd MM yy', responseOn);
				responseOn = responseOn +" "+time;
			}else 
				responseDateFlag = false;
			
			var commentedBy = "";
			if(desArr[i].commentedBy != null && desArr[i].commentedBy  != undefined)
				commentedBy = desArr[i].commentedBy.fullName;
			var responseGivenBy = "";
			if(desArr[i].responseGivenBy != null && desArr[i].responseGivenBy  != undefined)
				responseGivenBy = desArr[i].responseGivenBy.fullName;
			var response = "";
			if(desArr[i].response != null && desArr[i].response != undefined)
				response = desArr[i].response;
			htmlStr += '<tr>'
			    +'<td>'+desArr[i].comments+'</td>'
			    +'<td>'+commentedBy+'</td>'
			    +'<td>'+createdOn+'</td>'
			    +'<td>'+response+'</td>'
			    +'<td>'+responseGivenBy+'</td>'
			    +'<td>'+responseOn+'</td>';
			if(discripancyType == "review"){
				htmlStr += '<td><a class="fa fa-trash delete" style="pointer-events: none;"></a></td></tr>';
			}else{
				if(responseOn == "" || responseOn == null || responseOn == undefined){
					htmlStr += '<td><a  class="fa fa-edit" onclick="responedToDiscrepancy('+"'"+label+"'"+','+"'"+dataVal+"'"+','+"'"+desArr[i].id+"'"+','+"'"+actType+"'"+', '+"'"+fieldType+"'"+','+"'"+fieldValue+"'"+')"></a></td></tr>';
				}else
					htmlStr += '<td><a  class="fa fa-edit" style="pointer-events: none;"></a></td></tr>';
				
			}
			
		}
	}
	if(commentsFlag && responseDateFlag == false){
		var flagVal = $('#inProgressVal').val();
		$('#'+reocrdId+'_'+index+'_image').attr('src',flagVal);
	}else if(commentsFlag && responseDateFlag){
		//Green color
		var flagVal = $('#completedVal').val();
		$('#'+reocrdId+'_'+index+'_image').attr('src',flagVal);
	}else{
		//Normal flag
		var flagVal = $('#startFlagVal').val();
		$('#'+reocrdId+'_'+index+'_image').attr('src',flagVal);
	}
	
	$("#hiddenDetails").append(htmlHideStr);
	if(htmlStr != ""){
		$("#tblComments tbody").append(htmlStr);
		$("#tblComments").show();
	}
	$('#discripencyModal').modal('show');
}
function responedToDiscrepancy(label, dataId,discrepancyId, type, filedType, filedVal){
	$("#tblComments").hide();
	$('#staticFromResponse').empty("");
	debugger;
	var str = '<table class="table table-striped"><tr>';
	var actId = $("#crfName").val();
	var paramId = "";
	var cpuData = "";
	var dataArr = [];
	var disPojo = "";
	var desArr = dsrdMapVals[actId][dataId][label];
	if(desArr != null && desArr != undefined){
		for(var i=0; i<desArr.length; i++){
			if(desArr[i].id == discrepancyId){
				disPojo = desArr[i];
				if(type == "Meals"){
					cpuData = cpuReviewData.mealsDataMap[dataId];
					paramId = 0;
				}else if(type == "Samples"){
					cpuData = cpuReviewData.samplesDataMap[dataId];
					paramId = 0;
				}else if(type == "Vitals"){
					cpuData = cpuReviewData.vitalDataMap[dataId];
					paramId = 0;
					dataArr = cpuReviewData.vitalDataMap[dataId].parameterDto;
				}else if(type == "dosing"){
					cpuData = cpuReviewData.doseDataMap[dataId];
					dataArr = cpuReviewData.doseDataMap[dataId].parameterDto;
					paramId = 0;
				}
				
				var createdOn = "";
				if(desArr[i].commentedOn != null && desArr[i].commentedOn != undefined){
					createdOn = new Date(desArr[i].commentedOn);
					var time  = createdOn.getHours()+":"+createdOn.getMinutes()+":"+createdOn.getSeconds();
					createdOn = $.datepicker.formatDate('dd MM yy', createdOn);
	         		createdOn = createdOn +" "+time;
	         	}
				
				var commentedBy = "";
				if(desArr[i].commentedBy != null && desArr[i].commentedBy  != undefined)
					commentedBy = desArr[i].commentedBy.fullName;
				
				str += '<td>Comments : </td>'
					+'<td>'+desArr[i].comments+'</td>'
					+'<td>Commented By : </td>'
					+'<td>'+commentedBy+'</td>'
					+'<td>Commented On : </td>'
					+'<td>'+createdOn+'</td></tr>';
			}
			
		}
	}
	var ptype ="";
	if(filedType == "Radio"){
		if(dataArr != null && dataArr != undefined){
			str += '<tr><td colspan="6"><table class="table table-striped"><tr><th colspan="3" style="text-align:center;">Current Value</th><th colspan="3" style="text-align:center;">New Value</th></tr>';
			for(var k=0; k<dataArr.length; k++){
				var parameterId = dataId+"_"+dataArr[k].parameterId;
				var disParamId = "";
				var globalValId = "";
				var parameterVal = "";
				if(type == "Vitals"){
					if(disPojo.subVitalParmData != null && disPojo.subVitalParmData != undefined){
						 disParamId = disPojo.subVitalParmData.parameter.id;
						 if(disPojo.subVitalParmData.globalValue != null && disPojo.subVitalParmData.globalValue != undefined)
							 globalValId = disPojo.subVitalParmData.globalValue.id;
						 else parameterVal = disPojo.subVitalParmData.parameterValue;
					}
					
				}else if(type == "dosing"){
					if(disPojo.dosingParamId != null && disPojo.dosingParamId != undefined){
						disParamId = disPojo.dosingParamId.parameter.id;
						if(disPojo.dosingParamId.globalValue != null && disPojo.dosingParamId.globalValue != undefined)
							 globalValId = disPojo.dosingParamId.globalValue.id;
						 else parameterVal = disPojo.dosingParamId.parameterValue;
					}
				}
				
				
				if(parameterId == dataId+"_"+disParamId){
					str +='<tr><td>'+dataArr[k].parameterName+'</td>';
					var str2 ='<td>'+dataArr[k].parameterName+'</td>';
					if(dataArr[k].controlType.contentCode == "RB" || dataArr[k].controlType.contentCode == "CB" || dataArr[k].controlType.contentCode == "SB"){
						ptype = dataArr[k].controlType.contentCode;
						if(dataArr[k].controlType.contentCode == "RB"){
							selectVals = dataArr[k].controlType.valuesDto;
							if(selectVals.length > 0){
								 str += '<td colspan="2">';
								 str2 += '<td colspan="2">';
						       	 for(var j=0; j<selectVals.length; j++){
						       		 if(cpuReviewData.paramDataMap[dataArr[k].parameterId].globalValId == selectVals[j].valueId){
						       			str += '<input type="radio" readonly="readonly" name="currentVal" id="currentVal" checked value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+'&nbsp&nbsp&nbsp';
						       			str2 += '<input type="radio" readonly="readonly" name="newVal" id="newVal"  value="'+selectVals[j].valueId+'" >'+selectVals[j].valueName+'&nbsp&nbsp&nbsp';
						       		 }else {	
						       			str += '<input type="radio" readonly="readonly"  name="currentVal" id="currentVal"  value="'+selectVals[j].valueId+'" disabled>'+selectVals[j].valueName+' &nbsp;&nbsp; '
						       			str2 += '<input type="radio" readonly="readonly"  name="newVal" id="newVal"  value="'+selectVals[j].valueId+'" >'+selectVals[j].valueName+' &nbsp;&nbsp; '
							       	 }
						       	}
						       	str +='</td>';
						       	str2 +='</td>';
					         }
							str += str2+'</tr>';
						}else if(dataArr[k].controlType.contentCode == "CB"){
							if(selectVals.length > 0){
								str += '<td colspan="2">';
								 str2 += '<td colspan="2">';
						       	 for(var j=0; j<selectVals.length; j++){
						       		 if(cpuReviewData.paramDataMap[dataArr[k].parameterId].globalValId == selectVals[j].valueId){
						       			str += '<input type="checkbox" readonly="readonly"  name="currentVal" id="currentVal" checked value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+'&nbsp&nbsp&nbsp';
						       			str2 += '<input type="checkbox" readonly="readonly"  name="newVal" id="newVal" value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+'&nbsp&nbsp&nbsp';
								     }else {	
						       			str += '<input type="checkbox" readonly="readonly" name="currentVal" id="currentVal"  value="'+selectVals[j].valueId+'" disabled>'+selectVals[j].valueName+' &nbsp;&nbsp; '
						       			str2 += '<input type="checkbox" readonly="readonly" name="newVal" id="newVal"  value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+' &nbsp;&nbsp; '
									 }
								 }
						       	str +='</td>';
						       	str2 +='</td>';
					         }
							str +='</td>';
					       	str2 +='</td>';
					       	str += str2+'</tr>';
						}else if(dataArr[k].controlType.contentCode == "SB"){
							str += '<td colspan="2">';
							 str2 += '<td colspan="2">';
							str += '<select  class="form-control" readonly="readonly" name="currentVal" id="currentVal">'
							str2 += '<select  class="form-control" name="newVal" id="newVal">'
				              +'<option value="value="-1">---Select---</option>';
				            
							if(selectVals.length > 0){
				            	 for(var j=0; j<selectVals.length; j++){
				            		 if(cpuReviewData.paramDataMap[dataArr[k].parameterId].globalValId == selectVals[j].valueId){
				            			 str += '<option value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+'</option>'
				            			 str2 += '<option value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+'</option>'
				            		 }else{
				            			 str2 += '<option value="'+selectVals[j].valueId+'">'+selectVals[j].valueName+'</option>' 
				            		 }
				            	 }
				              }
				              str += '</select></td>';
				              str2 += '</select></td>';
				              str += str2+'</tr>';
						}
					}else{
						str += '<td colspan="2">';
						 str2 += '<td colspan="2">';
						if(dataArr[k].controlType.contentCode == "TB"){
							debugger;
							str += '<input type="text" readonly="readonly" name="currentVal" id="currentVal" class="form-control" value="'+cpuReviewData.paramDataMap[dataArr[k].parameterId].paramterValue+'">';
							str2 += '<input type="text"  name="newVal" id="newVal" class="form-control" value="">';
							
						}else if(dataArr[k].controlType.contentCode == "TA"){
							str += '<textarea readonly="readonly" rows="5" cols="50" class="form-control" name="currentVal" id="currentVal">'+cpuReviewData.paramDataMap[dataArr[k].parameterId].paramterValue+'</textarea>';
							str2 += '<textarea  rows="5" cols="50" class="form-control" name="newVal" id="newVal"></textarea>';
						}
						str +='</td>';
				       	str2 +='</td>';
				       	str += str2+'</tr>';
					}
				}
			}
			str += '</tr>';
		}
	}else{
		var tempArr = label.split("_");
		str += '<tr><td colspan="6"><table class="table table-striped"><tr><th colspan="3" style="text-align:center;">Current Value</th><th colspan="3" style="text-align:center;">New Value</th></tr>';
		str +='<tr><td>'+tempArr[0]+'</td><td colspan="2">';
		var str2 ='<td>'+tempArr[0]+'</td><td colspan="2">';
		if(filedType == "time"){
			 str += '<input type="time" readonly="readonly" class="form-control" name="currentVal" id="currentVal" value="'+filedVal+'">'
	         str2 += '<input type="time"  name="newVal" id="newVal" class="form-control" value=""></td>';
	
		}else if(filedType == "text"){
			 str += '<input type="text" readonly="readonly" class="form-control" name="currentVal" id="currentVal" value="'+filedVal+'">'
	         str2 += '<input type="text" name="newVal" id="newVal" class="form-control" value=""></td>';
	
		}else if(filedType == "textarea"){
			str += '<textarea rows="5" cols="50" class="form-control" name="crrentVal" id="currentVal">'+filedVal+'</textarea>'
			str2 += '<textarea rows="5" cols="50" name="newVal" id="newVal" class="form-control"></textarea>';
		}
		str +='</td>';
       	str2 +='</td>';
       	str += str2+'</tr>';
		str += '</tr>';
	}
	str += '<tr><td>Response :</td><td colspan="5"><textarea  rows="5" cols="50" class="form-control" name="desResponse" id="desResponse"></textarea><div id="responseMsg" style="color:red;"></div></td></tr>'
		+'<tr align="center"><td colspan="6"><input type="button" value="Submit" class="btn btn-primary btn-md" onclick="submitResponse('+"'"+discrepancyId+"'"+', '+"'"+ptype+"'"+', '+"'"+type+"'"+')"></td></tr>'
	$('#staticFromResponse').append(str);
	$('#staticFromResponse').show();
}
function submitResponse(desId, paramControl, actType){
	debugger;
	var currentVal = "";
	var newValue = "";
	var responseVal = $('#desResponse').val();
	if(paramControl == "RB" || paramControl == "CB"){
		currentVal = $("input[name='currentVal']:checked").val();
		newValue = $("input[name='newVal']:checked").val();
	}else if(paramControl == "SB"){
		currentVal = $('select[name="currentVal"]').find(":selected").val();
		newValue = $('select[name="newVal"]').find(":selected").val();
	}else{
		currentVal = $("input[name=currentVal]").val();
		newValue = $("input[name=newVal]").val();
	}
	if(currentVal == "" || currentVal == undefined)
		currentVal = $('#currentVal').val();
	if(newValue == "" || newValue == undefined)
		newValue = $('#newVal').val();
	
	if(responseVal == null || responseVal == "" || responseVal == undefined){
		$('#responseMsg').html("Required Field.");
	}else{
		$('#responseMsg').html("");
		if(newValue == "")
			newValue = "undefined";
		var url = mainUrl+"/reviewData/saveDiscepencyResponseDetails/" + desId + "/" + currentVal +"/"+newValue+"/"+responseVal+"/"+actType;
//		alert(url);
		var result = synchronousAjaxCall(url);
//		alert(result);
		if(result != null && result != "" && result != undefined){
			if(result.mealsMsg == "success")
				displayMessage("success"," Discrepancy Closed. Successfully....!");
			else displayMessage("error","Discrepancy Closing Failed. Please try again.");
			$('#discripencyModal').modal('hide');
			var result = synchronousAjaxCall(mainUrl+"/reviewData/getActivityDataForReview/" + $("#studyName").val() + "/" + $("#crfName").val() +"/"+"responsetoreview");
			if(result != null && result != "" && result != undefined){
				buildReviewDataList(result, screenTypeVal);
				isActivityLoading = false;
			}
		}
	}
}
