    var messages = { PROJREQ: 'Enter project number',FILLALLTHEFIELDS: 'All the fields are mandatory',RESTITLE:'Select restrictions title',RESFOR:'Select restrictions for' };
    var isControlDataLoaded = false;var isValid = true;var disrepancies = [];var actionType = "";
    var yesNo=[];var windowPeriodSign=[];var windowPeriodDuration=[];var studyDesignArray=[];var loadFormData = false;
    var storageConditions = [];var matrix = [];var defaultActivityParameters = [];
    function filterDiscrepencies(jsonArray, value){
    	return jsonArray.filter(function (el) {
			  return el.projectsDetails.projectDetailsId.toString() === value.toString();
		});
    }

    function checkTimepointInputKey(event, ele) {
    	var key = event.charCode || event.keyCode || 0;
        var pos = event.target.selectionStart;
        if (event.shiftKey && event.keyCode === 9) {
            return;
        }
        if (event.shiftKey) {
            event.preventDefault(); return false;
        }
        else if ((key !== 16 && (
            key === 8 ||
            key === 9 ||
            key === 13 ||
            key === 46 ||
            key === 190 ||
            key === 188 ||
            key === 189 ||
            (key >= 35 && key <= 40) ||
            (key >= 48 && key <= 57) ||
            (key >= 96 && key <= 105)))) {
            console.log("");
        } else {
            event.preventDefault();
        }
    }
    
    function checkTimeformatInputKey(event, ele) {
    	var key = event.charCode || event.keyCode || 0;
        var pos = event.target.selectionStart;
        if (event.shiftKey && event.keyCode === 9) {
            return;
        }
        else if (event.shiftKey && event.keyCode === 186) {
            return;
        }
        
        if (event.shiftKey) {
            event.preventDefault(); return false;
        }
        else if ((key !== 16 && (
            key === 8 ||
            key === 9 ||
            key === 13 ||
            key === 46 ||
            key === 186 ||
            (key >= 35 && key <= 40) ||
            (key >= 48 && key <= 57) ||
            (key >= 96 && key <= 105)))) {
            console.log("");
        } else {
            event.preventDefault();
        }
    }
    
    function deactivateDataByType(type,tableName,subRowNumber){
    	var formData =$('#frmData').serializeArray();
		formData.push({name: "projectId",value: $("input[name='projectId']").val()});
        formData.push({name: "type",value : type});	
        formData.push({name: "rowNumber",value : 1});	
        formData.push({name: "subRowNumber",value : subRowNumber});	
        formData.push({name: "activeandinactive",value : 1});
        formData.push({name: "fieldOrder",value : 1});
        formData.push({name: "treatmentNo",value : 1});
		
    	$.ajax({
    		url: $("#mainUrl").val() + '/studydesign/deactivateTypeData',
    		type:'POST',
    		data: formData,
    		success:function(d){
    			if(tableName !== ""){
	    			$("#" + tableName + " tbody").empty();
	    			$("#" + tableName + " tbody").attr("data-defaultrow",'1');
	    			$("#" + tableName + " tbody").append("<tr><td colspan='" + $("#" + tableName + " thead tr td").length + "' style='text-align:center;'>No Data Found</td></tr>");
    			}
    		}
    	});
    	
    }
    
    function checkTimepoint(event, ele) {
    	var value = $(ele).val();
    	var commaSeperatedValues = value.split(',');
    	var newTimepointValue= "";
    	if(commaSeperatedValues.length > parseInt($(ele).data("notp"))){
    		$(ele).val("");
    		displayMessage('info', "No. of timepoints should be not be more than " + $(ele).data("notp"));
    		return false;
    	}
    	
    	/*var prefixZero = $("#ddlPrefixZero").val();
    	if($.trim(prefixZero).length === 0){
    		$(ele).val("");
    		displayMessage('info', "Select from Prefix '0'");
    		return false;
    	}*/
    	
    	var isValid = true;
    	for(var cs=0;cs<commaSeperatedValues.length;cs++){
    		if($.trim(commaSeperatedValues[cs]).length > 0){
    			var cValues = commaSeperatedValues[cs].split('.');
    			if(cValues.length > 2){
    				displayMessage('info', "Some of the timepoints(s) are invalid. Please check before adding the timepoint(s)");
    				isValid = false;break;
    			}
    			
    			if(cValues.length > 1 && ($.trim(cValues[1]).length === 0 || $.trim(cValues[0]).length === 0 || cValues[0] === "-" || cValues[1] === "-")){
    				displayMessage('info', "Timepoints should be in the format X.X or XX.XX or XXX.XXX etc.");
    				isValid = false;break;
    			}
    			if(cValues.length === 1){
    				displayMessage('info', "Timepoints should be in the format X.X or XX.XX or XXX.XXX etc.");
    				isValid = false;break;
    			}
    			
        		/*var newValue = "";
        		
        		if(cValues[0].indexOf("-") !== -1){
        			var newValueWidthOutSign = cValues[0].replace('-','');
        			if(newValueWidthOutSign.length > 2){
	        			newValue =newValueWidthOutSign.substring(0, 3);
	        		}
        			else if(prefixZero === "YES"){
        				if(newValueWidthOutSign.length === 2){
    	        			newValue = "0" + newValueWidthOutSign;
    	        		}
    	        		else if(newValueWidthOutSign.length === 1){
    	        			newValue = "00" + newValueWidthOutSign;
    	        		}
    	        		else if(newValueWidthOutSign.length === 0){
    	        			newValue = "000";
    	        		}
        			}
	        		else {
	        			if(newValueWidthOutSign.length === 1){
		        			newValue = "0" + newValueWidthOutSign;
		        		}
		        		else if(newValueWidthOutSign.length === 0){
		        			newValue = "00";
		        		}
	        		}
        			newValue =  '-' + newValue;
        		}
        		else{
        			if(cValues[0].length > 2){
	        			newValue = cValues[0].substring(0, 3);
	        		}
        			else if(prefixZero === "YES"){
        				if(cValues[0].length === 1){
    	        			newValue = "0" + cValues[0];
    	        		}
    	        		else if(cValues[0].length === 0){
    	        			newValue = "00";
    	        		}
        			}
        			else{
        				if(cValues[0].length === 1){
    	        			newValue = "0" + cValues[0];
    	        		}
    	        		else if(cValues[0].length === 0){
    	        			newValue = "00";
    	        		}
        			}
        		}
        		
        		if(cValues.length === 1){
        			if(prefixZero === "YES"){
        				newValue = newValue + "." + "000";
        			}
        			else{
        				newValue = newValue + "." + "00";
        			}
        		}
        		else{
        			if(cValues[1].length > 2){
        				newValue = newValue + "." + cValues[1].substring(0, 3);
            		}
        			else if(prefixZero === "YES"){
        				if(cValues[1].length === 2){
                 			newValue = newValue + "." + cValues[1] + "0";
                 		}
                 		else if(cValues[1].length === 1){
                 			newValue =newValue + "." + cValues[1] + "00" ;
                 		}
                 		else if(cValues[1].length === 0){
                 			newValue = newValue + "." + "000";
                 		}
        			}
            		else{
            			if(cValues[1].length === 2){
                 			newValue = newValue + "." + cValues[1];
                 		}
            			else if(cValues[1].length === 1){
                 			newValue =newValue + "." + cValues[1] + "0" ;
                 		}
                 		else if(cValues[1].length === 0){
                 			newValue = newValue + "." + "00";
                 		}
            		}
        		}
        		
        		if(newTimepointValue===""){
        			newTimepointValue = newTimepointValue + newValue;
        		}
        		else{
        			newTimepointValue = newTimepointValue +","+ newValue;
        		}*/
    		}
    	}
    	//$(ele).val(newTimepointValue);
    	if($(ele).attr("data-clearonerror") === "YES" && !isValid){
    		$(ele).val('');
    	}
    	
    	return isValid;
    }
    
    function checkTimeformat(event, ele) {
    	var value = $(ele).val();
    	var colonSeperatedValues = value.split(':');
    	if(colonSeperatedValues.length !== 2){
    		displayMessage('info', "Time should be in the format HH:MM");
    		$(ele).val("");
    		return false;
    	}
    	
    	if(colonSeperatedValues[1].length !== 2){
    		displayMessage('info', "Time should be in the format HH:MM");
    		$(ele).val("");
    		return false;
    	}
    	if(parseInt(colonSeperatedValues[1]) > 59){
    		displayMessage('info', "Minutes should not be more than 59");
    		$(ele).val("");
    		return false;
    	}
    	var newValue = "";
    	if(colonSeperatedValues[0].length < 2) {
    		newValue = "0" + colonSeperatedValues[0];
    		newValue = newValue  +":" + colonSeperatedValues[1];
    		$(ele).val(newValue);
    	}
    	return true;
    }
    
    function getActivityParamters(acivityId,elementId){
    	$.ajax({
    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + acivityId,
    		type:'GET',
    		success:function(data){
    			$("#" + elementId).empty();
    			for(var i=0;i<data.parameterDto.length;i++){
    				$("#" + elementId).append("<option value='" + data.parameterDto[i]['parameterId'] + "'>" + data.parameterDto[i]['parameterName'] + "</option>");
    			}
    		}
    	});
    }
    
    function validateComments(){
    	var isValid = true;
    	for(var c=0;c<comments.length;c++){
    		if($.trim(comments[c]['response']).length === 0){
    			displayMessage('warning', 'Add respose to all comments');
    			isValid = false;break;
    		}
    	}
    	return isValid;
    }
    
    function submitProject(){
    	if(!validateComments()){
    		return;
    	}
    	
    	$(".loader").show();
		var formData =$('#frmData').serializeArray();
		formData.push({name: "projectId",value: $("input[name='projectId']").val()});
        formData.push({name: "actionName",value: "SUBMIT"});
        formData.push({name: "projectsDetailsId",value: "0"});
        formData.push({name: "roleid",value: "0"});
		  		                        
		$.ajax({
         	url: $("#mainUrl").val() + '/studydesign/submitDraft',
         	type:'POST',
         	data: formData,
         	success:function(e){
         		$(".dynaformremove").remove();
         		if(e.Success == false && e.Message !== undefined){
         			$(".loader").hide();
         			displayMessage('error', e.Message);
         		}
         		else{
         			$("#frmData").attr("method",'POST');
             		$('#frmData').attr('action', $("#mainUrl").val() + '/studydesign/studyDesignPage');
             		$('#frmData').submit();	
             		displayMessage('success', e.Message);
         		}
         		$("input[name='projectId']").val(e.ProjectId);
         	},
         	error:function(er){
         		debugger;
         	}
        });
    }
    
    /*form validations*/
    function validateStorageData(){
    	var isValid = true;
    	if(!$("[name='isStorageDifferent']").is(":checked")){
    		displayMessage('info', "Please select if storage is different under Sample Processing and Storage");
    		isValid = false;
    	}
    	else if($("[name='isStorageDifferent']:checked").val() === "YES") {
    		$.each($("#tblStorageDifferent input,select"), function(index,ele){
    			if($.trim($(ele).val()).length === 0){
    				displayMessage('info', "Please fill all the fields in storage section under Sample Processing and Storage");
    				isValid = false;
    				return false;
    			}
    		});
    	}
    	return isValid;
    }
    
    function validateExclusionCriteria(){
    	isValid = true;
    	if($("#exclusionTable").data("defaultrow").toString() === "1"){
    		isValid = false;
    	}
    	return isValid;
    }
    
    function validateInclusionCriteria(){
    	var isValid = true;
    	if($("#inclusionTable").data("defaultrow").toString() === "1"){
    		isValid = false;
    	}
    	return isValid;
    }
    
    function validateRestrictions(){
    	var isValid = true;
    	if($("#restrictionstable").data("defaultrow").toString() === "1"){
    		isValid = false;
    	}
    	return isValid;
    }
    
    function validateMeals(){
    	var isValid = true;
    	if($("#Mealsinfo").data("defaultrow").toString() === "1"){
    		isValid = false;
    	}

    	if(!isValid){
    		$.confirm({
    		    title: 'Confirm!',
    		    content: 'Meal timepoints not added. Do you wish to continue?',
    		    buttons: {
    		        yes: function () {
    		        	validateActivities();
    		        },
    		        no: function () {
    		        	
    		        }
    		    }
    		});
    	}
    	else{
    		validateActivities();
    	}
    }
    
    function validateVitals(){
    	var isValid = true;
    	if($("#preAndPostTable").data("defaultrow").toString() === "1"){
    		isValid = false;
    	}

    	if(!isValid){
    		$.confirm({
    		    title: 'Confirm!',
    		    content: 'Vital timepoints not added. Do you wish to continue?',
    		    buttons: {
    		        yes: function () {
    		        	validateMeals();
    		        },
    		        no: function () {
    		        	
    		        }
    		    }
    		});
    	}
    	else{
    		validateMeals();
    	}
    }
    
    function validateSampleCollection(){
    	var isValid = true;
    	if($("#tsplconditionwisedata").data("defaultrow").toString() === "1"){
    		isValid = false;
    	}

    	if(!isValid){
    		$.confirm({
    		    title: 'Confirm!',
    		    content: 'Sample timepoints not added. Do you wish to continue?',
    		    buttons: {
    		        yes: function () {
    		        	validateVitals();
    		        },
    		        no: function () {
    		        	
    		        }
    		    }
    		});
    	}
    	else{
    		validateVitals();
    	}
    }
    
    function validateDosingParameters(){
    	var isValid = true;
    	if($("#dosingParameterList").data("defaultrow").toString() === "1"){
    		displayMessage('error', "Please add dosing parameters");
			isValid = false;
    	}
    	/*else if($("#treatmentwisedose").val() == "YES"){
    		$.each($("#treatmentwisedose tbody tr"),function(rowIndex,row){
    			
    		});
    	}*/

    	if(!isValid){
    		$.confirm({
    		    title: 'Confirm!',
    		    content: 'Dosing parameters not added. Do you wish to continue?',
    		    buttons: {
    		        yes: function () {
    		        	validateSampleCollection();
    		        },
    		        no: function () {
    		        	
    		        }
    		    }
    		});
    	}
    	else{
    		validateSampleCollection();
    	}
    }
    
    function validateActivities(){
    	var activities = ""; var isValid = true;
		var isExclusionCriteriaValid = validateExclusionCriteria();
		if(!isExclusionCriteriaValid){
			activities = "Exclusion criteria";
			isValid = false;
		}
		
		var isInclusionCriteriaValid = validateInclusionCriteria();
		if(!isInclusionCriteriaValid){
			if(activities.length === 0){
				activities = "Inclusion criteria";	
			}
			else if(!isExclusionCriteriaValid){
				activities = "Exclusion, Inclusion criteria";
			}
			isValid = false;
		}
		
		var isRestrictionsCriteriaValid = validateRestrictions();
		if(!isRestrictionsCriteriaValid){
			if(activities.length === 0){
				activities = "Restrictions not addded, do you wish to continue.";	
			}
			else if(!isExclusionCriteriaValid && !isInclusionCriteriaValid){
				activities = "Exclusion, inclusion criteria and restrictions are not added. Do you wish to continue.";
			}
			else if(!isInclusionCriteriaValid){
				activities = "Inclusion criteria and restrictions are not added. Do you wish to continue.";
			}
			else if(!isExclusionCriteriaValid){
				activities = "Exclusion criteria and restrictions are not added. Do you wish to continue.";
			}
			isValid = false;
		}
		
		if(!isValid){
			$.confirm({
    		    title: 'Confirm!',
    		    content: activities,
    		    buttons: {
    		        yes: function () {
    		        	submitProject();
    		        },
    		        no: function () {
    		        	isValid = false;
    		        }
    		    }
    		});
		}
		else{
			submitProject();
		}
    }
    
    setTimeout(function(){
    	commentsCallBack = function(){
        	$(".loader").show();
        	var formData =$('#frmData').serializeArray();
        	formData.push({name:"comment",value: $("#taCommentsInput").val()});
            formData.push({name: "projectId",value: $('[name="projectId"]').val()});
    		
    		$.ajax({
    			url: $("#mainUrl").val() + '/studydesign/discrepancy',
    			data: formData,
    			type:'POST',
    			success:function(data){
    				addComment(data.CommentId, $("#taCommentsInput").val());
    				$("#taCommentsInput").val("");
    				$(".loader").hide();
    			}
    		});
        };
    },2000);
   
    
    function vitalsTreatmentChange(){
    	var tmntspctmpt = $("#tmntspctmpt").val();
        $("#vitalTreatment").empty();
        const select = document.getElementById("vitalTreatment");
        if(select !== null && select !== undefined){
	        if(tmntspctmpt === ''){
	        	select.insertAdjacentHTML('beforeend', '<option value="">Select</option>');
	        }
	        else{
		        if (tmntspctmpt == "YES") {
		            var number = $("#nooftreatments").val();
		            select.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
		            for (var i = 0; i < number; i++) {
		                select.insertAdjacentHTML('beforeend', '<option value=' + (i + 1) + '>Treatment' + (i + 1) + '</option>');
		            }
		        }
		        else if (tmntspctmpt == "NO") {
		            select.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
		        } 
		        else {
		            select.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
		        }
	        }
        }
    }
    
    function treatmentSpecificMeals(){
    	var teatmentSpecificMeals = $("#teatmentSpecificMealTimepoints").val();
        var nooftreatments = $("#nooftreatments").val();
        $("#mTPTreatmentvalue").empty();
        const select = document.getElementById("mTPTreatmentvalue");
        if(select!==null && select!== undefined){
	        if (teatmentSpecificMeals == "YES") {
	            var number = $("#nooftreatments").val();  
	            select.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
	            for (var i = 0; i < number; i++) {
	                select.insertAdjacentHTML('beforeend', '<option value=' + (i + 1) + '>Treatment' + (i + 1) + '</option>');
	            }
	        } else if (teatmentSpecificMeals == "NO") {
	            select.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
	        } else {
	        	select.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
	        }
        }
    }
    
    function deactivateData(fieldName, fieldValue, rowNumber, subRowNumber, sectionName,async,fieldOrder,displayValue){
    	 var formData =$('#frmData').serializeArray();
    	 formData.push({name:"rowNumber",value: rowNumber});
         formData.push({name: "subRowNumber",value: subRowNumber});
         formData.push({name: "fieldValue",value: fieldValue});
         formData.push({name: "fieldName",value: fieldName});
         formData.push({name: "projectNo", value: $("input[name='projectNumber']").val()});
         formData.push({name: "type",value: sectionName});
         formData.push({name: "fieldOrder",value: fieldOrder});
         formData.push({name: "displayValue",value: displayValue});
         formData.push({name: "projectId",value: $("input[name='projectId']").val()});
         
		 $.ajax({
	      	url:$("#mainUrl").val() + '/studydesign/deactivateProjectDetails',
	      	type:'POST',
	      	data: formData,
	      	success:function(e){
	      		
	      	},
	      	error:function(er){
	      		debugger;
	      	}
	     });
    }
    
    function commentResponseCallback(id,response){
    	 var formData =$('#frmData').serializeArray();
    	 formData.push({name: "studyDiscrepancyId",value: id});
    	 formData.push({name: "comments",value: response});
    	
    	$.ajax({
    		url:'/SIMS/studydesign/discrepancyModification',
    		type:'POST',
    		data: formData,
    		success:function(data){
    			
    		}
    	});
    }
    
    function commentDeleteCallback(id){
	   	var formData =$('#frmData').serializeArray();
	   	formData.push({name: "commentId",value: id});
	   	
	   	$.ajax({
	   		url:'/SIMS/studydesign/deleteComment',
	   		type:'POST',
	   		data: formData,
	   		success:function(data){
	   			
	   		}
	   	});
   }
    
    function saveData(fieldName, fieldValue, rowNumber, subRowNumber, sectionName,async,fieldOrder,displayValue,treatmentNo){
    	 var formData =$('#frmData').serializeArray();
    	 if (rowNumber !== undefined && rowNumber !== null) {
             formData.push({name:"rowNumber",value: rowNumber});
             formData.push({name: "subRowNumber",value: subRowNumber});
         }
         else {
        	 formData.push({name: "rowNumber",value: 0});
             formData.push({name: "subRowNumber",value :0});
         }
    	 
    	 if ($.trim($("input[name='projectId']").val()).length > 0) {
    		 formData.push({name: "projectId",value: $("input[name='projectId']").val()});
         }
         
		  formData.push({name: "fieldValue",value: fieldValue});
		  formData.push({name: "fieldName",value: fieldName});
		  formData.push({name: "projectNo", value: $("input[name='projectNumber']").val()});
		  formData.push({name: "type",value: sectionName});
		  if(fieldOrder !==undefined && fieldOrder !== null){
			  formData.push({name: "fieldOrder",value: fieldOrder});
		  }
		  if(displayValue !==undefined && displayValue !== null){
			  formData.push({name: "displayValue",value: displayValue});
		  }
		  if(treatmentNo !==undefined && treatmentNo !== null){
			  formData.push({name: "treatmentNo",value: treatmentNo});
		  }
        	  
          
         $.ajax({
         	url:$("#mainUrl").val() + '/studydesign/autoSave',
         	type:'POST',
         	data: formData,
         	async: async,
         	success:function(e){
         		$(".dynaformremove").remove();
         		if(e.Message !== undefined && e.Success === false){
         			$("input[name='" + fieldName + "']").val('');
         			displayMessage('error', e.Message);
         		}
         		else{
         			$("input[name='projectId']").val(e.ProjectId);	
         		}
         		
         		
         		/*if(fieldName === "projectNumber"){
    				getProjectDetails();
    			}*/
         	},
         	error:function(er){
         		debugger;
         	}
         });
    }
    
    function autoSaveData(fieldName, fieldValue, rowNumber, subRowNumber, sectionName,async, fieldOrder,displayValue,treatment) {
    	if ($.trim($("input[name='projectNumber']").val()).length === 0) {
    		displayMessage('info', messages.PROJREQ);
            return;
        }
    	saveData(fieldName, fieldValue, rowNumber, subRowNumber, sectionName, async, fieldOrder,displayValue,treatment);
    }
    
    function autoSaveElementData(element,fieldName, fieldValue, rowNumber, subRowNumber, sectionName,async, fieldOrder,displayValue,treatment) {
    	if ($.trim($("input[name='projectNumber']").val()).length === 0) {
    		displayMessage('info', messages.PROJREQ);
    		$(element).val('');
            return;
        }
    	saveData(fieldName, fieldValue, rowNumber, subRowNumber, sectionName, async, fieldOrder,displayValue,treatment);
    }
    
    function showDiscripencyDialog(fieldName,type,rowNumber,subRowNumber){
		$(".loader").show();
		var formData =$('#frmData').serializeArray();
		var projectId = $("input[name='projectId']").val();
		$("#desProjectId").val(projectId);
		$("#desRowNumber").val(rowNumber);
		$("#desSubRowNumber").val(subRowNumber);
		$("#desFieldName").val(fieldName);
		$("#desType").val(type);
		$("#txtComments,#txtResponse").val('');
		$("#formResponse,#btnBack,#btnSaveDiscrepancyResponse").hide();
		$("#tblComments").show();

		formData.push({name: "rowNumber",value: rowNumber});
        formData.push({name: "subRowNumber",value : subRowNumber});	
		
		
   	 	formData.push({name: "projectId",value: projectId });
        formData.push({name: "fieldName",value: fieldName });
        formData.push({name: "type",value: type });
        
		$.ajax({
			url: $("#mainUrl").val() + '/studydesign/discrepencies',
			data: formData,
			type:'POST',
			success:function(data){
				var tableRows = "";
				$("#tblComments tbody").empty();
				for(var d = 0;d<data.length;d++){
					if(actionType === 'createupdate'){
						if(data[d].responseSubmitted){
							tableRows = tableRows + "<tr data-id='" + data[d].id + "'><td>" + data[d].comments + "</td><td>" + data[d].commentedBy + "</td><td>" + data[d].commentedOn + "</td><td>" + data[d].response + "</td><td>" + data[d].respondedBy + "</td><td>" + data[d].respondedOn + "</td><td></td></tr>";	
						}
						else{
							tableRows = tableRows + "<tr data-id='" + data[d].id + "'><td>" + data[d].comments + "</td><td>" + data[d].commentedBy + "</td><td>" + data[d].commentedOn + "</td><td>" + data[d].response + "</td><td>" + data[d].respondedBy + "</td><td>" + data[d].respondedOn + "</td><td><a class='fa fa-pencil edit'/></td></tr>";	
						}
					}
					else{
						if($.trim(data[d].response).length === 0){
							tableRows = tableRows + "<tr data-id='" + data[d].id + "'><td>" + data[d].comments + "</td><td>" + data[d].commentedBy + "</td><td>" + data[d].commentedOn + "</td><td>" + data[d].response + "</td><td>" + data[d].respondedBy + "</td><td>" + data[d].respondedOn + "</td><td><a class='fa fa-trash delete'/></td></tr>";
						}
						else{
							tableRows = tableRows + "<tr data-id='" + data[d].id + "'><td>" + data[d].comments + "</td><td>" + data[d].commentedBy + "</td><td>" + data[d].commentedOn + "</td><td>" + data[d].response + "</td><td>" + data[d].respondedBy + "</td><td>" + data[d].respondedOn + "</td><td></td></tr>";
						}
					}
				}
				$("#tblComments tbody").append(tableRows);
				$("#discripencyModal").modal('toggle');
				$(".loader").hide();
			}
		});
	}
    
    /* Code for Treatment information */
    function sampleTreatments(){
    	var samptmpdp = $("#samptmpdp").val();
        $("#sampleTreatmentValue_1").empty();
        $("#sampleTreatmentValue_2").empty();

        const sampleTreatmentValue_1 = document.getElementById("sampleTreatmentValue_1");
        const sampleTreatmentValue_2 = document.getElementById("sampleTreatmentValue_2");

        if (samptmpdp == "YES") {
            var number = $("#nooftreatments").val();
            sampleTreatmentValue_1.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
            sampleTreatmentValue_2.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");

            for (var i = 0; i < number; i++) {
                sampleTreatmentValue_1.insertAdjacentHTML('beforeend', '<option value=' + (i + 1) + '>Treatment' + (i + 1) + '</option>');
                sampleTreatmentValue_2.insertAdjacentHTML('beforeend', '<option value=' + (i + 1) + '>Treatment' + (i + 1) + '</option>');
            }
        }
        else if (samptmpdp == "NO") {
            sampleTreatmentValue_1.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
            sampleTreatmentValue_2.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
        }
        else {
        	sampleTreatmentValue_1.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
            sampleTreatmentValue_2.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
        }
    }
    
    function generateTreatmentWiseInformation(dosetype, studyDesign,nooftrtmnts) {
    	var noofTreatments = 0;
        if(nooftrtmnts!==undefined&&nooftrtmnts!==null){
        	noofTreatments = parseInt(nooftrtmnts);
        }
      
        var tableData = "";
        var tableHeader = "";
        var totalRows = $("#treatmentsinfo tbody tr").length;
        tableHeader = "<tr><td style='width:90px;'>Treatment</td><td style='width:130px;'>Name</td><td style='width:160px;'>Randomization Code</td>"
			+"<td style='width:110px;'>Strength</td><td style='width:110px;'>Dose</td><td style='width:120px;'>Treatment Type</td><td style='width:300px;'>Amount of Water consumed with the dose</td><td style='width:170px;'>No. of Sachet Labels</td></tr>";
        
        if(totalRows > noofTreatments){
        	for(var rowNumber =  totalRows;rowNumber >= noofTreatments;rowNumber--){
        		$("#treatmentsinfo tbody").find("tr:eq("+ rowNumber +")").remove();
        	}
        }
        else{
        	if(noofTreatments === totalRows){
        		$("[name='treatmentType']").empty();
        		$("[name='treatmentType']").append("<option  value=''>Select</option>");
				if(studyDesign === "FAST" || studyDesign === "FED"){
					for(var d=0;d<studyDesignArray.length;d++){
				    	if(studyDesignArray[d].code === studyDesign){
				    		$("[name='treatmentType']").append("<option  value='" + studyDesignArray[d].code + "'>" + studyDesignArray[d].fieldValue + "</option>");
				    	}
				    }
				}
				else{
					for(var d=0;d<studyDesignArray.length;d++){
				    	if(studyDesignArray[d].code === 'FAST' || studyDesignArray[d].code === 'FED'){
				    		$("[name='treatmentType']").append("<option  value='" + studyDesignArray[d].code + "'>" + studyDesignArray[d].fieldValue + "</option>");
				    	}
				    }
				}
        	}
        	else{
	        	for (var i = totalRows; i < noofTreatments; i++) {
	            	tableData = tableData + "<tr>"
	                    + "<td style='width:90px;'><input type='number' data-fieldorder='0' style='text-align:center' data-rownumber='0' data-subrownumber='" 
	                    + (i + 1) + "' class='form-control autosave reviewElement' value=" + (i + 1) + " id='trtmntNo' disabled/></td>";
	
	                tableData = tableData + "<td style='width:130px;'><input type='text' data-fieldorder='1' class='form-control validate reviewElement' data-validate='required' id='trtmntName' name='treatmentName' data-rownumber='0' data-subrownumber='" + (i + 1) + "'/></td>"
	                    + "<td style='width:160px;'><input type='text' data-fieldorder='2' class='form-control autosave validate reviewElement' data-validate='required' id='randomizationCode' name='randomizationCode' data-rownumber='0' data-subrownumber='" + (i + 1) + "'/></td>"
	                    + "<td style='width:110px;'><input type='text' data-fieldorder='3' class='form-control autosave validate reviewElement' data-validate='required' id='strength' name='strength' data-rownumber='0' data-subrownumber='" + (i + 1) + "'/></td>"
	                    + "<td style='width:110px;'><input type='text' data-fieldorder='4' class='form-control autosave validate reviewElement' data-validate='required' id='dose' name='dose' data-rownumber='0' data-subrownumber='" + (i + 1) + "'/></td>"
	                    + "<td style='width:120px;'><select id='treatmentType' data-fieldorder='5' class='form-control autosave validate reviewElement' data-validate='required' name='treatmentType' data-rownumber='0' data-subrownumber='" + (i + 1) + "'>";
	                
	                tableData = tableData + "<option value=''>Select</option>";
	                if(studyDesign === "FAST" || studyDesign === "FED"){
	                	for(var d=0;d<studyDesignArray.length;d++){
	                    	if(studyDesignArray[d].code === studyDesign){
	                    		tableData = tableData + "<option  value=" + studyDesignArray[d].code + ">" + studyDesignArray[d].fieldValue + "</option>";
	                    	}
	                    }
	            	}
	                else{
	                	for(var d=0;d<studyDesignArray.length;d++){
	                    	if(studyDesignArray[d].code === 'FAST' || studyDesignArray[d].code === 'FED'){
	                    		tableData = tableData + "<option  value=" + studyDesignArray[d].code + ">" + studyDesignArray[d].fieldValue + "</option>";
	                    	}
	                    }
	                }
	
	                tableData = tableData + "</select></div></td>"
	                    + "<td style='width:300px;'><input type='text' data-fieldorder='6' class='form-control autosave validate reviewElement' data-validate='required' id='amntofwtcon' name='amountOfWaterConsumedWiththeDose' data-rownumber='0' data-subrownumber='" + (i + 1) + "'/></td>"
	                    + "<td style='width:170px;'><input type='text' data-fieldorder='6' style='text-align:center' data-texttype='NUMERIC' data-min='1' data-max='50' maxlength='2' name='noOfSachetLabels' data-rownumber='0' data-subrownumber='" + (i + 1) + "' class='form-control noofsachetlabels validate reviewElement' data-validate='required' data-treatment='" + (i + 1) + "' value='" + 1 + "' id='nofsachetlabels' "
	                    + "/></td></tr>";
	                
	                saveData("TREATMENT", (i + 1), 0, (i+1), "TreatmentWiseInformation", true, 0, (i + 1));
	            }
        	}
        }
        $("#treatmentsinfo thead").empty();
        $("#treatmentsinfo thead").append(tableHeader);
        $("#treatmentsinfo tbody").append(tableData);
        
        
        if (!$("#treatmentWiseInformationMain").is(":visible") && nooftrtmnts > 0 && dosetype !== 'Select' && studyDesign !== 'Select') {
            setTimeout(function () {
                $("#treatmentWiseInformationMain").show('slow');
            }, 100);
        }
    }
    
    function buildDosingTableHeader(studyDesign,i){
    	var data = "<thead><tr style='text-align:center;'><td style='width:150px;'>Timepoint</td><td colspan='2' style='width:200px;'>Fasting Criteria(HH:MM)</td>";
		/*FED CRITERIA*/
		if(studyDesign == "FED" || studyDesign == "FASTANDFED"){
			data = data + "<td colspan='2'>Fed Criteria(HH:MM)</td>";
        }
		data = data + "<td colspan='3'>Window Period</td>";
		data = data + "</tr></thead>";
		return data;
    }
    
    function buildDosingRow(studyDesign,i,rowNumber){
    	
    	 var data = "";
    	 data = data + "<tr class='dosingrow'><td style='width:100px;'><input type='text' name='doseTimePoint' data-clearonerror='YES' class='form-control autosave timepoint-format validate' data-validate='required' data-notp='1'" + 
 	 					" data-rownumber='" + rowNumber + "' data-subrownumber='" + rowNumber + "' data-fieldorder='1'/></td>";
   		 
		 /*START FASTING CRATERIA*/
		 data = data + "<td style='width:100px;'><select name='fastingCriteriaSign' class='form-control autosave validate' data-validate='required' data-rownumber='" + rowNumber + "'" + 
 	 					"data-subrownumber='" + rowNumber + "' data-fieldorder='2'><option value=''>Select</option>";
		 
		 for(var wc =0;wc < windowPeriodSign.length; wc++){
      		data = data + "<option value='"+ windowPeriodSign[wc].code +"'>"+ windowPeriodSign[wc].fieldValue +"</option>";
   	 	 }
      	
      	 data = data  + "</select></td>";
      	 
      	data = data  + "<td style='width:100px;'><input type='text' name='fastingCriteria' maxlength='5' class='form-control autosave validate time-format' data-validate='required' data-rownumber='" + rowNumber + "' data-subrownumber='" + rowNumber + "' data-fieldorder='3'/></td>";
      	 /*END FASTING CRATERIA*/
      	 
      	 /*START FED CRITERIA*/
		 if(studyDesign == "FED" || studyDesign == "FASTANDFED"){
			 data = data  +  "<td style='width:100px;'><select name='fedCriteriaSign' class='form-control autosave validate' data-validate='required' data-rownumber='" + rowNumber + "' data-subrownumber='" + rowNumber + "' data-fieldorder='4'>"+
             "<option value=''>Select</option>";
			     for(var wc =0;wc < windowPeriodSign.length; wc++){
					data = data + "<option value='"+ windowPeriodSign[wc].code +"'>"+ windowPeriodSign[wc].fieldValue +"</option>";
			 	 }
			 	 data = data  +  "</select></td>";
			 	 data = data  +	"<td style='width:100px;'><input name='fedCriteria' type='text' class='form-control autosave validate time-format' data-validate='required' data-rownumber='" + rowNumber + "'" +
			 	 		" data-subrownumber='" + rowNumber + "' data-fieldorder='5'/></td>";
		 }
		 /*END FED CRITERIA*/
		 
		 data = data + "<td style='width:100px;'><select name='dosingWindowPeriodSign' class='form-control autosave validate' data-validate='required' data-rownumber='" + rowNumber + "'" + 
					"data-subrownumber='" + rowNumber + "' data-fieldorder='7'><option value=''>Select</option>";
		 for(var wc =0;wc < windowPeriodSign.length; wc++){
   	 		data = data + "<option value='"+ windowPeriodSign[wc].code +"'>"+ windowPeriodSign[wc].fieldValue +"</option>";
   	 	 }
		 data = data + "</select></td>";
		 data = data + "<td style='width:100px;'><input type='text' name='dosingWindowPeriod' data-texttype='NUMERIC' data-min='1' maxlength='4'  class='form-control autosave autosave validate' data-validate='required' data-rownumber='" + rowNumber + "'" + 
					"data-subrownumber='" + rowNumber + "' data-fieldorder='8'/></td>";
		 data = data + "<td style='width:100px;'><select name='dosingWindowPeriodTime' class='form-control autosave validate' data-validate='required' data-rownumber='" + rowNumber + "'" + 
					"data-subrownumber='" + rowNumber + "' data-fieldorder='9'><option value=''>Select</option>";
		 
		 for(var wc =0;wc < windowPeriodDuration.length; wc++){
  	 		data = data + "<option value='"+ windowPeriodDuration[wc].code +"'>"+ windowPeriodDuration[wc].fieldValue +"</option>";
  	 	 }
  	 	
		 data = data + "</select></td>";
		 data = data + "</tr>";
		 return data;
    }
    
    function generateDosingTimepoints(dosetype, studyDesign) {
    	
    	var nooftrtmnts = $("#nooftreatments").val();
    	if(dosetype !== undefined && studyDesign !== undefined  && nooftrtmnts !== undefined && nooftrtmnts !== null){
			 if (!$("#doseTreatmentTimepoints").is(":visible") 
					 && dosetype !== 'Select' && studyDesign !== 'Select') {
		            setTimeout(function () {
		                $("#dosingTimepoints").show('slow');
		            }, 100);
		        }
		}
    	
    	
         $("#doseTreatmentTimepoints").empty();
         $("#treatmentvalue").empty();
         var data = "";
        
    	 var number = 1;var z = 1;var i = 1;
    	 if (dosetype == "SINGLE") {
    		 $("#timepointSpecificParameterDose").prop( "disabled", true);
    		 $("#isThereanyDifferenceInTheNoOfDosings").hide();
    		 data = "<table data-dosingnumber='0'><thead><tr style='text-align:center;'><td>Timepoint</td><td colspan='2'>Fasting Criteria(HH:MM)</td>";
    		 
    		 /*FED CRITERIA*/
    		 if(studyDesign == "FED" || studyDesign == "FASTANDFED"){
            	 data = data + "<td colspan='2'>Fed Criteria(HH:MM)</td>";
             }
    		 data = data + "<td colspan='3'>Window Period</td>";
    		 data = data + "</tr></thead><tbody>";
    		 
    		 data = data + buildDosingRow(studyDesign, i,1);
    		 data = data + "<tbody></table>";
    		 
            $("#doseTreatmentTimepoints").append(data);
         }
    	 
    	 if (dosetype === "MULTIPLE" || dosetype === "SINGLEANDMULTIPLE") {
    		 $("#timepointSpecificParameterDose").prop( "disabled", false);
             $("#isThereanyDifferenceInTheNoOfDosings").show();
             var isThereDifferenceInDosing = $("#differenceinDosings").val();

             if (isThereDifferenceInDosing == "") {
                 $("#doseTreatmentTimepoints").empty();
             } else if (isThereDifferenceInDosing == "YES") {
            	 for (i = 0; i < nooftrtmnts; i++) {
                     $("#doseTreatmentTimepoints").append("<div style='padding:3px;'><div><h4 class='page-subHeading'>Treatment " + (i + 1) + "</h4></div> <table>" +
                         "<tr><td>No of Dosings<input type='text' class='form-control noofdosings' data-clearonerror='YES' data-texttype='NUMERIC' data-min='1' data-max='50' maxlength='2' " +
                         "data-rownumber='" + (i + 1) + "' data-subrownumber='" + (i) + "' data-dosingnumber='" + (i + 1) + "' name='noOfDosings' data-treatment='" + (i + 1) + "'/><td></tr></table>" +
                         "</div><div data-dosingnumber='" + (i + 1) + "'></div>");
            	 }
             } else if (isThereDifferenceInDosing == "NO") {
            	 $("#doseTreatmentTimepoints").append("<div style='padding:3px;'><div><h4 class='page-subHeading'>Treatment</h4></div> <table>" +
                         "<tr><td>No of Dosings<input type='text' class='form-control noofdosings' data-clearonerror='YES' data-texttype='NUMERIC' data-min='1' data-max='50' maxlength='2' " +
                         "data-rownumber='" + (i + 1) + "' data-subrownumber='" + (i) + "' data-dosingnumber='0' name='noOfDosings' data-treatment='0'/><td></tr></table>" +
                         "</div><div data-dosingnumber='0'></div>");
             }
         }
    }

    function dosingParameterTreatment(){
    	var treatmentwisedose = $("#treatmentwisedose").val();
        $("#dosingTreatmentvalue").empty();
        const select = document.getElementById("dosingTreatmentvalue");
        select.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
        if (treatmentwisedose == "YES") {
            var number = $("#nooftreatments").val();
            
            for (var i = 0; i < number; i++) {
                select.insertAdjacentHTML('beforeend', '<option value="' + (i+1) + '">Treatment' + (i+1) + '</option>');
            }
            $("#timepointSpecificParameterDose").val("");
			$("#timepointSpecificParameterDose").prop("disabled","");
        } else if (treatmentwisedose == "NO") {
            select.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
            $("#timepointSpecificParameterDose").val("");
			$("#timepointSpecificParameterDose").prop("disabled","disabled");
        } else {
        	select.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
        }
    }
    
    function checkCentrifugation(ele){
    	if($(ele).attr("name") === "centrifugationApplicable"){
			if($(ele).val() === "YES"){
	    		$("#divCentrifugationApplicable").show("slow");
	    		$("#centrifugationApplicableTo,#speed,#ProcessTime,#temparaturealiquote,#allowedtime").addClass("validate");
	    		$("#centrifugationApplicableTo,#speed,#ProcessTime,#temparaturealiquote,#allowedtime").attr("data-validate","required");
	    	}
	    	else if($(ele).val() === "NO"){
	    		$("#centrifugationApplicableTo,#speed,#ProcessTime,#temparaturealiquote,#allowedtime").removeClass("validate");
	    		$("#centrifugationApplicableTo,#speed,#ProcessTime,#temparaturealiquote,#allowedtime").attr("data-validate","");
	    		$("#divCentrifugationApplicable").hide("slow");
	    		$("#centrifugationApplicableTo,#speed,#ProcessTime,#temparaturealiquote,#allowedtime").val('');
	    		deactivateData('centrifugationApplicableTo', '', 0, 1, "sampleProcessing", true, 0,'');
	    		deactivateData('centrifugationSpeed', '', 0, 1, "sampleProcessing", true, 0,'');
	    		deactivateData('centrifugationProcessTime', '', 0, 1, "sampleProcessing", true, 0,'');
	    		deactivateData('centrifugationTemparature', '', 0, 1, "sampleProcessing", true, 0,'');
	    		deactivateData('centrifugationAllowedTime', '', 0, 1, "sampleProcessing", true, 0,'');
	    	}
    	}
	}
    
    function checkSkinSentivity(ele){
    	if($(ele).attr("name") === "SkinSensitivityApplicable"){
    		if($(ele).val() === "YES"){
				$("[name='SkinSensivityTreatmentSpecificTimepoints']").addClass("validate");
    			$("[name='SkinSensivityTreatmentSpecificTimepoints']").attr("data-validate",'required');
	    		$("#SkinSensitivityData").show("slow");
	    	}
	    	else{
	    		$("[name='SkinSensivityTreatmentSpecificTimepoints']").removeClass("validate");
        		$("[name='SkinSensivityTreatmentSpecificTimepoints']").removeClass("validate-error");
    			$("[name='SkinSensivityTreatmentSpecificTimepoints']").attr("data-validate",'');
	    		$("#SkinSensitivityData").hide("slow");
	    		
	    		/*$.each($("#tdCentrifugationApplicable").find("input[type='checkbox']"),function(index,ele){
	    			$(this).prop("checked",false);
	    			deactivateData($(this).attr('name'), '', 0, 0, "sampleProcessing", true, 0,'');	
	    		});*/
	    	}
    	}
	}
    
    function skinSentivityTreatments(ele){
    	if($(ele).attr("name") === "SkinSensivityTreatmentSpecificTimepoints"){
    		$("#SkinSensivityTreatment").empty();
        	var noofTreatments = $("#nooftreatments").val();
			if($(ele).val() === "YES"){
				for(var i = 0; i < noofTreatments; i++){
					$("#SkinSensivityTreatment").append("<option value='" + (i + 1) + "'>Treatment" + (i + 1) + "</option>");	
				}
	    	}
	    	else{
	    		$("#SkinSensivityTreatment").append("<option value='0'>Treatment</option>");
	    	}
    	}
	}
    
    function skinAdhesionTreatments(ele){
    	if($(ele).attr("name") === "SkinAdhesionTreatmentSpecificTimepoints"){
    		$("#SkinAdhesionTreatment").empty();
        	var noofTreatments = $("#nooftreatments").val();
			if($(ele).val() === "YES"){
				for(var i = 0; i < noofTreatments; i++){
					$("#SkinAdhesionTreatment").append("<option value='" + (i + 1) + "'>Treatment" + (i + 1) + "</option>");	
				}
	    	}
	    	else{
	    		$("#SkinAdhesionTreatment").append("<option value='0'>Treatment</option>");
	    	}
    	}
	}
    
    function ecgTreatments(ele){
    	if($(ele).attr("name") === "EcgTreatmentSpecificTimepoints"){
    		$("#EcgTreatment").empty();
        	var noofTreatments = $("#nooftreatments").val();
			if($(ele).val() === "YES"){
				for(var i = 0; i < noofTreatments; i++){
					$("#EcgTreatment").append("<option value='" + (i + 1) + "'>Treatment" + (i + 1) + "</option>");	
				}
	    	}
	    	else{
	    		$("#EcgTreatment").append("<option value='0'>Treatment</option>");
	    	}
    	}
	}
    
    function checkSkinAdhesion(ele){
    	if($(ele).attr("name") === "SkinAdhesionApplicable"){
    		if($(ele).val() === "YES"){
				$("[name='SkinAdhesionTreatmentSpecificTimepoints']").addClass("validate");
    			$("[name='SkinAdhesionTreatmentSpecificTimepoints']").attr("data-validate",'required');
	    		$("#divSkinAdhesionData").show("slow");
	    	}
	    	else{
	    		$("[name='SkinAdhesionTreatmentSpecificTimepoints']").removeClass("validate");
        		$("[name='SkinAdhesionTreatmentSpecificTimepoints']").removeClass("validate-error");
    			$("[name='SkinAdhesionTreatmentSpecificTimepoints']").attr("data-validate",'');
	    		$("#divSkinAdhesionData").hide("slow");
	    	}
    	}
	}
    
    function checkMatrixDifference(ele){
    	if($(ele).attr("name") === "isMatrixDifferent"){
    		var data="";
    		$("#aliquotes tbody").empty();
    		var vialsArray = [];
    		if($("#tsplconditionwisedata").data('defaultrow')===0){
    			$.each($("#tsplconditionwisedata tbody tr"),function(ind,ele){
        			vialsArray.push(parseInt($(ele).find("td:eq(5)").text()));
        		});
    		}
    		
    		var noofAliquots = Math.max.apply(Math, vialsArray);
	    	if($(ele).val() === "YES" && vialsArray.length > 0){
	    		for(var a=0;a < noofAliquots;a++){
		    		data = data + "<tr><td>Aliquot " + (a + 1) + "</td><td><input class='autosave form-control reviewElement validate' data-validate='required' type='text' data-texttype='NUMERIC' maxlength='3' name='aliquotVolume' data-rownumber='1' data-subrownumber='" + a + "'/></td>"+
		    			"<td><select class='autosave form-control reviewElement validate' data-validate='required' name='aliquotMatrix' data-rownumber='1' data-subrownumber='" + a + "'><option value=''>Select</option>";
		    		for(var i = 0; i < matrix.length; i++){
		    			data = data + "<option value='" + matrix[i]['id'] + "'>" + matrix[i]['name'] + "</option>";
		    		}
		    		data = data + "</select></td></tr>";
	    		}
	    		$("#aliquotes tbody").append(data);
	    		$("#divAliquotMatrix").show('slow');
	    	}
	    	else if($(ele).val() === "NO"){
	    		data = data + "<tr><td>Aliquot</td><td><input class='autosave form-control reviewElement validate' data-validate='required' type='text' data-texttype='NUMERIC' maxlength='3' name='aliquotVolume' data-rownumber='1' data-subrownumber='0'/></td>"+
	    						"<td><select class='autosave form-control reviewElement validate' data-validate='required' name='aliquotMatrix' data-rownumber='1' data-subrownumber='0'><option value=''>Select</option>";
	    		
	    		for(var i = 0; i < matrix.length; i++){
	    			data = data + "<option value='" + matrix[i]['id'] + "'>" + matrix[i]['name'] + "</option>";
	    		}
	    		
	    		data = data + "</select></td></tr>";
	    		$("#aliquotes tbody").append(data);
	    		$("#divAliquotMatrix").show('slow');
	    	}
	    	
    	}
    }
    
    function checkTreatmentSpecificRestrictions(ele){
    	if($(ele).attr("name") === "TreatmentSpecificRestrictionsComplaince"){
    		$("#resTreatment").empty();
    		var nooftreatments = $("#nooftreatments").val();
    		$("#resTreatment").append("<option value=''>Select</option>");
    		if($(ele).val() === "YES"){
    			$("#divRestrictionsComplaince").show('slow');
    			for(var i = 0;i < nooftreatments; i++){
    				$("#resTreatment").append("<option value='" + (i + 1) + "'>Treatment " + (i + 1) + "</option>");
    			}
    		}
    		else if($(ele).val() === "NO"){
    			$("#resTreatment").append("<option value='0'>Treatment</option>");
    			$("#divRestrictionsComplaince").show('slow');
    		}
    	}
    }
        
    function checkStorageDifferent(ele){
    	if($(ele).attr("name") === "isStorageDifferent"){
    		var data="";
    		$("#tblStorageDifferent tbody").empty();
    		var vialsArray = [];
    		if($("#tsplconditionwisedata").data('defaultrow')===0){
    			$.each($("#tsplconditionwisedata tbody tr"),function(ind,ele){
        			vialsArray.push(parseInt($(ele).find("td:eq(5)").text()));
        		});
    		}
    		
    		var noofAliquots = Math.max.apply(Math, vialsArray);
	    	if($(ele).val() === "YES" && vialsArray.length > 0){
	    		for(var a=0;a<noofAliquots;a++){
		    		data = data + "<tr><td>Aliquot " + (a + 1) + "</td><td><select class='autosave form-control reviewElement' name='storageCondition' data-rownumber='1' data-subrownumber='" + a + "'><option value=''>Select</option>";
		    		for(var i = 0; i < storageConditions.length; i++){
		    			data = data + "<option value='" + storageConditions[i]['id'] + "'>" + storageConditions[i]['name'] + "</option>";
		    		}
		    		data = data + "</select></td><td><input class='autosave form-control reviewElement' type='text' data-texttype='NUMERIC' maxlength='3' name='storageAllowedTime' data-rownumber='1' data-subrownumber='" + a + "'/></td>" +
		    			   "<td><input class='autosave form-control reviewElement' type='text' data-texttype='NUMERIC' maxlength='3' name='storageTemperature' data-rownumber='1' data-subrownumber='" + a + "'/></td>" + 
		    			   "<td><select class='autosave form-control reviewElement' name='storageTimepointCondition' data-rownumber='1' data-subrownumber='" + a + "'><option value=''>Select</option>";
		    		
		    		for(var i = 0; i < matrix.length; i++){
		    			data = data + "<option value='" + matrix[i]['id'] + "'>" + matrix[i]['name'] + "</option>";
		    		}
		    		
		    		data = data + "</select></td><td></td></tr>";
	    		}
	    		$("#tblStorageDifferent tbody").append(data);
	    		$("#tblStorageDifferent").show('slow');
	    	}
	    	else if($(ele).val() === "NO"){
	    		data = data + "<tr><td>Aliquot</td><td><select class='autosave form-control reviewElement' name='storageCondition' data-rownumber='1' data-subrownumber='0'><option value=''>Select</option>";
	    		for(var i = 0; i < storageConditions.length; i++){
	    			data = data + "<option value='" + storageConditions[i]['id'] + "'>" + storageConditions[i]['name'] + "</option>";
	    		}
	    		data = data + "</select></td><td><input class='autosave form-control reviewElement' type='text' data-texttype='NUMERIC' maxlength='3' name='storageAllowedTime' data-rownumber='1' data-subrownumber='0'/></td>" +
	    			   "<td><input class='autosave form-control reviewElement' type='text' data-texttype='NUMERIC' maxlength='3' name='storageTemperature' data-rownumber='1' data-subrownumber='0'/></td>" + 
	    			   "<td><select class='autosave form-control reviewElement' name='storageTimepointCondition' data-rownumber='1' data-subrownumber='0'><option value=''>Select</option>";
	    		
	    		for(var i = 0; i < matrix.length; i++){
	    			data = data + "<option value='" + matrix[i]['id'] + "'>" + matrix[i]['name'] + "</option>";
	    		}
	    		
	    		data = data + "</select></td><td></td></tr>";
	    		$("#tblStorageDifferent tbody").append(data);
	    		$("#tblStorageDifferent").show('slow');
	    	}
	    	
    	}
    }
    
    function checkEcg(ele){
    	if($(ele).attr("name") === "EcgApplicable"){
    		if($(ele).val() === "YES"){
    			$("[name='EcgTreatmentSpecificTimepoints']").addClass("validate");
    			$("[name='EcgTreatmentSpecificTimepoints']").attr("data-validate",'required');
        		$("#divEcgApplicable").show("slow");	
        	}
        	else{
        		$("[name='EcgTreatmentSpecificTimepoints']").removeClass("validate");
        		$("[name='EcgTreatmentSpecificTimepoints']").removeClass("validate-error");
    			$("[name='EcgTreatmentSpecificTimepoints']").attr("data-validate",'');
        		$("#divEcgApplicable").hide("slow");
        	}
    	}
	}
    
    function buildDosingForm(ele){
    	var number = 0;
    	if($.trim($(ele).val()).length > 0){
    		number = parseInt($(ele).val());
    	}
        var treatmentNumber = parseInt($(ele).attr("data-dosingnumber"));
        var z = 1;var data = "";
        var studyDesign = $("#studyDesign").val();
        var rowIndexes = [];
        var table = $("div[data-dosingnumber='" + treatmentNumber + "']").find("table").length;
        var noofRows = $("div[data-dosingnumber='" + treatmentNumber + "']").find("table tbody tr").length;
        if($("div[data-dosingnumber='" + treatmentNumber + "']").find("table").length > 0 
        	&& noofRows> number ){
        	
        	var table = $("div[data-dosingnumber='" + treatmentNumber + "']").find("table");
        	$.each($(table).find("tbody tr"),function(index,rowElement){
        		if(index > (number-1)){
        			rowIndexes.push(index);
        		}
        	});
        	for(var i = rowIndexes.length;i >= 0;i--){
        		$(table).find("tbody tr:eq('" + rowIndexes[i] + "')").remove();
        		
        		
        	}
        }
        else{
        	var noofExistingRows = $(".dosingrow").length + 1;
        	if(table === 0){
	        	data = "<table>" + buildDosingTableHeader(studyDesign, i);
	            data = data + "<tbody>";
        	}
        	for (var i = noofRows ; i < number; i++) {
            	data = data + buildDosingRow(studyDesign, i,noofExistingRows); 
            	z++;noofExistingRows++;
            }
        	
        	if(table === 0){
        		data = data + "</tbody></table>";
        		$("div[data-dosingnumber='" + treatmentNumber + "']").append(data);
        	}
        	else{
        		var tableBody = $("div[data-dosingnumber='" + treatmentNumber + "']").find("table tbody");
        		$(tableBody).append(data);	
        	}
            	
        }
        
    }
    
    function generateReviewElement(projectDetailsId,fieldName,fieldValue){
    	/* var fieldDiscrepancies = filterDiscrepencies(disrepancies,projectDetailsId);
		 if(actionType === "review"){
			 if($.trim(fieldValue).length > 0){
				 return "<a class='fa fa-flag table-flag red' data-fieldName='" + fieldName + "'></a>";
			 }
		 }
		 else if(fieldDiscrepancies.length > 0){
			 fieldDiscrepancies = filterJsonArray(fieldDiscrepancies,'responseSubmitted',false);
			 if(fieldDiscrepancies.length > 0 && $.trim(fieldValue).length > 0){
				 if($("[name='" + fieldName + "']").closest(".reviewFormTable").length > 0){
					 return "<a  class='fa fa-flag flag-studydesign red'></a>";	 
				 }
				 else{
					 return "<a class='fa fa-flag table-flag red' data-fieldName='" + fieldName + "'></a>";
				 }
			 }
		 }*/
		 return "";
    }
    
    function buildDosingTimepoints(d){
    	var dosetype = $("#doseType").val();
		var studyDesign = $("#studyDesign").val();
		var nooftrtmnts = $("#nooftreatments").val();
		
	    generateTreatmentWiseInformation(dosetype, studyDesign, nooftrtmnts);
	   
	    if (!$("#dosingTimepoints").is(":visible") && dosetype !== 'Select' && studyDesign !== 'Select') {
			setTimeout(function () {
			    	$("#dosingTimepoints").show('slow');
			        }, 100);
		}
	    
		var dosingTimepoints = filterJsonArray(d,'type','DosingTimepoints');
		$.each(dosingTimepoints, function(index,obj){
			if(obj.fieldName === "IsthereAnyDifferenceinDosings"){
				$("[name='" + obj.fieldName + "']").val(obj.fieldValue);	
				$("[name='" + obj.fieldName + "']").attr('data-old', obj.fieldValue);
			}
		});
	 
		generateDosingTimepoints(dosetype, studyDesign);
		dosingTimepoints = filterJsonArray(d,'type','DosingTimepoints');
		$.each(dosingTimepoints, function(index,obj){
			if(obj.fieldName === "noOfDosings"){
				$("[name='" + obj.fieldName + "'][data-rownumber='" + obj.rowNo + "']").val(obj.fieldValue);
				buildDosingForm($("[name='" + obj.fieldName + "'][data-rownumber='" + obj.rowNo + "']"));
			}
		});
		
		$.each(dosingTimepoints, function(index,obj){
			$("[name='" + obj.fieldName + "'][data-rownumber='" + obj.rowNo + "'][data-subrownumber='" + obj.subRowNo + "']").val(obj.fieldValue);
		});
    }
    
    function fillFormData(projectData){
    	var subRowNumber = 0;
    	disrepancies = projectData.studyDiscrepancies;
    	var projectParameters = projectData.parameters;
    	var d = projectData.projectDetails;
    	if(isControlDataLoaded){
    		var parameter = "";
    		var treatment = "";
    		actionType = $("[name='projectReview']").val();
    		var studyInformation = filterJsonArray(d,'type','StudyInformation');
			$.each(studyInformation, function(index,obj){
				if($("[name='" + obj.fieldName + "']").is("select")){
					if(obj.fieldValue !== undefined && obj.fieldValue !== null){
						$("[name='" + obj.fieldName + "']").val(obj.fieldValue);
					}
				}
				else{
					$("[name='" + obj.fieldName + "']").val(obj.fieldValue);	
				}
				$("[name='" + obj.fieldName + "']").attr('data-old', obj.fieldValue);
				var elementTag = generateReviewElement(obj.projectDetailsId,obj.fieldName,obj.fieldValue);
				if($.trim(elementTag).length > 0){
					$("[name='" + obj.fieldName + "']").closest('td').append(elementTag);
				}
			});
			
			var timepointsInformation = filterJsonArray(d,'type','TimepointsInformation');
			$.each(timepointsInformation, function(index,obj){
				if($("[name='" + obj.fieldName + "']").is("select")){
					if(obj.fieldValue !== undefined && obj.fieldValue !== null){
						$("[name='" + obj.fieldName + "']").val(obj.fieldValue);
					}
				}
			});
			
			var dosetype = $("#doseType").val();
	        var studyDesign = $("#studyDesign").val();
	        var nooftrtmnts = $("#nooftreatments").val();
        
	        buildDosingTimepoints(d);
			
			var dosignParameters = filterJsonArray(d,'type','DosingParameters','rowNo',0,'subRowNo',0);
			$.each(dosignParameters, function(index,obj){
				if($("[name='" + obj.fieldName + "']").is("select")){
					if(obj.fieldValue !== undefined && obj.fieldValue !== null){
						$("[name='" + obj.fieldName + "']").val(obj.fieldValue);
						checkDoseparameterTimepoints($("[name='" + obj.fieldName + "']"));
					}
				}
				else{
					$("[name='" + obj.fieldName + "']").val(obj.fieldValue);	
				}
				$("[name='" + obj.fieldName + "']").attr('data-old', obj.fieldValue);
				var elementTag = generateReviewElement(obj.projectDetailsId,obj.fieldName,obj.fieldValue);
				if($.trim(elementTag).length > 0){
					$("[name='" + obj.fieldName + "']").closest('td').append(elementTag);
				}
			});
			
			dosignParameters = filterJsonArray(d,'type','DosingParameters');
			var rowIndexes = []
			var tableRows = "";
			for(var ri=0; ri < dosignParameters.length;ri++ ){
				if(rowIndexes.indexOf(dosignParameters[ri].subRowNo)=== -1){
					rowIndexes.push(dosignParameters[ri].subRowNo);
				}
			}
			
			rowIndexes.sort();
			var isTreatmentAssigned = false;
			var isTreatmentDescAssigned = false;
			var rowsAssignedCount = 0;
			$("#dosingParameterList tbody").empty();

			for(var ri=0; ri < rowIndexes.length;ri++ ){
				isRowAssigned = 0;rowsAssignedCount=0;
				var dosignParameter = filterJsonArray(d,'type','DosingParameters','subRowNo',rowIndexes[ri]);
				tableRows = "";
				isTreatmentAssigned = false;isTreatmentDescAssigned = false;
				for(var dp=0; dp <dosignParameter.length; dp++ ){
					var elementTag = generateReviewElement(dosignParameter[dp].projectDetailsId,dosignParameter[dp].fieldName,dosignParameter[dp].fieldValue);
					if(dosignParameter[dp]["fieldName"] === "TREATMENT"){
    					tableRows = tableRows + "<td data-treat='" + dosignParameter[dp]["fieldValue"] + "'>" + dosignParameter[dp]["displayValue"];
    					if($.trim(elementTag).length > 0){
    						tableRows = tableRows + elementTag;
    					}
    					tableRows = tableRows + "</td>";
    					rowsAssignedCount++;
    				}
    				else if(dosignParameter[dp]["fieldName"] === "PARAMETERDESC"){
    					tableRows = tableRows + "<td data-param='" + dosignParameter[dp]["fieldValue"] + "'>" + dosignParameter[dp]["displayValue"];
    					if($.trim(elementTag).length > 0){
    						tableRows = tableRows + elementTag;
    					}
    					tableRows = tableRows + "</td>";
    					rowsAssignedCount++;
    				}	
    				// Uncomment this if timepoint wise dosing paramters
    				/*else if(dosignParameter[dp]["fieldName"] === "TIMEPOINT"){
    					tableRows = tableRows + "<td data-tp='" + dosignParameter[dp]["displayValue"] + "'>" + dosignParameter[dp]["displayValue"]  + "</td>";
    					rowsAssignedCount++;
    				}*/	
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 2){
				// Uncomment this if timepoint wise dosing paramters
				//if(rowsAssignedCount === 3){
					$("#dosingParameterList tbody").append("<tr data-subrowno='" + ri + "'>"+tableRows);
				}
			}

			if($("#dosingParameterList tbody tr").length > 0){
				$("#dosingParameterList").attr("data-defaultrow", "0");
			}
			else{
				$("#dosingParameterList tbody").append("<tr><td colspan='3' style='text-align:center;'>No Data Found</td></tr>");
			}
			
			var infomration = filterJsonArray(d,'type','TreatmentWiseInformation');
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "'][data-subrownumber='" + obj.subRowNo + "']").val(obj.fieldValue);
				var elementTag = generateReviewElement(obj.projectDetailsId,obj.fieldName,obj.fieldValue);
				if($.trim(elementTag).length > 0){
					$("[name='" + obj.fieldName + "'][data-subrownumber='" + obj.subRowNo + "']").closest('td').append(elementTag);
				}
			});
			
			infomration = filterJsonArray(d,'type','mealsTimePoints');
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "']").val(obj.fieldValue);
				var elementTag = generateReviewElement(obj.projectDetailsId,obj.fieldName,obj.fieldValue);
				if($.trim(elementTag).length > 0){
					$("[name='" + obj.fieldName + "']").closest('td').append(elementTag);
				}
			});
			
			infomration = filterJsonArray(d,'type','vitalTimePoins','rowNo',0,'subRowNo',0);
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "']").val(obj.fieldValue);
				var elementTag = generateReviewElement(obj.projectDetailsId,obj.fieldName,obj.fieldValue);
				if($.trim(elementTag).length > 0){
					$("[name='" + obj.fieldName + "']").closest('td').append(elementTag);
				}
			});
			
			infomration = filterJsonArray(d,'type','ecgTimePoins','rowNo',0);
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']").attr('checked', 'checked');	
				checkEcg($("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']"));
				ecgTreatments($("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']"));
			});
			
			infomration = filterJsonArray(d,'type','SkinSensivity','rowNo',0);
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']").attr('checked', 'checked');	
				checkSkinSentivity($("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']"));
				skinSentivityTreatments($("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']"));
			});
			
			
			
			infomration = filterJsonArray(d,'type','SkinAdhesion','rowNo',0);
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']").attr('checked', 'checked');
				checkSkinAdhesion($("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']"));
				skinAdhesionTreatments($("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']"));
			});
			
			
			infomration = filterJsonArray(d,'type','SkinAdhesion','rowNo',0);
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "'][value='" + obj.fieldValue + "']").attr('checked', 'checked');
				checkEcg($("[name='" + obj.fieldName + "']"));
				ecgTreatments($("[name='" + obj.fieldName + "']"));
			});
			
			dosingParameterTreatment();
			
			var sampleTimepoints = filterJsonArray(d,'type','sampleTimePoins','rowNo',0,'subRowNo',0);
			$.each(sampleTimepoints, function(index,obj){
				if($("[name='" + obj.fieldName + "']").is("select")){
					if(obj.fieldValue !== undefined && obj.fieldValue !== null){
						$("[name='" + obj.fieldName + "']").val(obj.fieldValue);
					}
				}
				else{
					$("[name='" + obj.fieldName + "']").val(obj.fieldValue);	
				}
			});
			
			sampleTreatments();
			
			sampleTimepoints = filterJsonArray(d,'type','sampleInformation','rowNo',1);
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(rowIndexes.indexOf(sampleTimepoints[ri].subRowNo)=== -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}
			
			rowIndexes.sort();
			var windowPeriod="";
			var windowPeriodCount=0;
			$("#tsplconditionwisedata tbody").empty();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0; windowPeriodCount = 0;windowPeriod="";
				sampleTimepoints = filterJsonArray(d,'type','sampleInformation','rowNo',1,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT" ||sampleTimepoints[dp]["fieldName"] === "LIGHTCONDITION" ||sampleTimepoints[dp]["fieldName"] === "VOLUMNOFBLOOD"
						 	||sampleTimepoints[dp]["fieldName"] === "TYPEOFVACUTAINERUSED" ||sampleTimepoints[dp]["fieldName"] === "BUFFER"){
						 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
						 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
						 rowsAssignedCount++;
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 5){
					$("#tsplconditionwisedata tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "'>"+tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#tsplconditionwisedata tbody tr").length === 0){
				$("#tsplconditionwisedata tbody").append("<tr><td colspan='6' style='text-align:center;'>No Data Found</td></tr>");
				$("#tsplconditionwisedata").attr("data-srnumber", "0");
			}
			else{
				$("#tsplconditionwisedata").attr("data-defaultrow", "0");
				$("#tsplconditionwisedata").attr("data-srnumber", subRowNumber);
			}
			
			
			sampleTimepoints = filterJsonArray(d,'type','sampleTimePoins','rowNo',1);
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(rowIndexes.indexOf(sampleTimepoints[ri].subRowNo)=== -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}
			
			rowIndexes.sort();
			var windowPeriod="";
			var windowPeriodCount=0;
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;windowPeriodCount = 0;windowPeriod="";
				sampleTimepoints = filterJsonArray(d,'type','sampleTimePoins','rowNo',1,'subRowNo',rowIndexes[ri]);
				tableRows = "";
			
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT" ||sampleTimepoints[dp]["fieldName"] === "NOOFVACUTAINERS" 
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN" ||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD" 
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTYPE"
						 		||sampleTimepoints[dp]["fieldName"] === "TIMEPOINT"	
						 	||sampleTimepoints[dp]["fieldName"] === "NOOFVAILSFORSAMPLESEPARATION"){
						 
						 var timPointsStr = "";
						 if(sampleTimepoints[dp]["fieldName"] === "TIMEPOINT"){
							 timePointsStr = sampleTimepoints[dp]["displayValue"];
							 var tpArr = timePointsStr.split(",");
							 var tpStr = "";
							 if(tpArr.length > 0 && tpArr.length > 5){
								 var ct = 1;
								 for(var t=0; t<tpArr.length; t++){
									 if(tpStr === ""){
										 tpStr = tpArr[t];
										 ct++;
									 }else{
										 if(ct <=4){
											 tpStr = tpStr+","+tpArr[t]; 
											 ct++;
										 }else{
											 ct = 1;
											 tpStr = tpStr+", \n"+tpArr[t]; 
										 }
									 }
								 }
								 tableRows = tableRows + "<td>" + tpStr;	
								 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
								 tableRows = tableRows + "</td>";		
								 rowsAssignedCount++;
							 }else{
								 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
								 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
								 tableRows = tableRows + "</td>";		
								 rowsAssignedCount++;
							 }
							 
						 }else{
							 if(sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN" ||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD" 
								 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTYPE"){
									 windowPeriod = windowPeriod + " " + sampleTimepoints[dp]["displayValue"];
									 windowPeriodCount++;
								 }
								 else{
									 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
									 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
									 tableRows = tableRows + "</td>";		
									 rowsAssignedCount++;
								 }
						 }
						 
						 if(windowPeriodCount === 3){
							 tableRows = tableRows + "<td>" + windowPeriod;	
							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";	
							 rowsAssignedCount++;
							 windowPeriodCount=0;
						 }
					}
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 5){
					$("#sampletimepointTreatmentslisttable tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "'>"+tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#sampletimepointTreatmentslisttable tbody tr").length > 1){
				$("#sampletimepointTreatmentslisttable").attr("data-srnumber", "0");
				$("#sampletimepointTreatmentslisttable").attr("data-defaultrow", "0");
				$("#sampletimepointTreatmentslisttable tbody").find("tr:eq(0)").remove();
			}
			else{
				$("#sampletimepointTreatmentslisttable").attr("data-defaultrow", "1");
				$("#sampletimepointTreatmentslisttable").attr("data-srnumber", subRowNumber);
			}
			
			
			infomration = filterJsonArray(d,'type','restrictionsComplainceMonitoring','rowNo',0);
			$.each(infomration, function(index,obj){
				if($("[name='" + obj.fieldName + "']").attr("type") === "radio"){
					$("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]").attr('checked', 'checked');
					checkTreatmentSpecificRestrictions("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]");
				}
			});
			
			sampleTimepoints = filterJsonArray(d,'type','restrictionsComplainceMonitoring');
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(sampleTimepoints[ri].rowNo > 0 && rowIndexes.indexOf(sampleTimepoints[ri].subRowNo) === -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}
			$("#restrictionstable tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				sampleTimepoints = filterJsonArray(d,'type','restrictionsComplainceMonitoring','subRowNo',rowIndexes[ri]);
				tableRows = "";
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "RESTRICTIONACTIVITY"
						 	|| sampleTimepoints[dp]["fieldName"] === "TREATMENT"
						 	||sampleTimepoints[dp]["fieldName"] === "PARAMETER"){
						 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
						 if(sampleTimepoints[dp]["fieldName"] === "PARAMETER"){
							 parameter =  sampleTimepoints[dp]["fieldValue"];
						 }
						 
						 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT"){
							 treatment =  sampleTimepoints[dp]["fieldValue"];
						 }
						 
						 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
						 tableRows = tableRows + "</td>";	
						 rowsAssignedCount++;
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 3){
					$("#restrictionstable tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "' data-pid='" + parameter +"' data-treat='" + treatment +"'>" + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#restrictionstable tbody tr").length === 0){
				$("#restrictionstable").attr("data-srnumber", "0");
				$("#restrictionstable tbody").append("<tr><td colspan='4' style='text-align:center;'>No Data Found</td></tr>");
			}
			else{
				$("#restrictionstable").attr("data-defaultrow", "0");
				$("#restrictionstable").attr("data-srnumber", subRowNumber);
			}
			
			sampleTimepoints = filterJsonArray(d,'type','mealsTimePointInformation','rowNo',1);
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(rowIndexes.indexOf(sampleTimepoints[ri].subRowNo)=== -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}
			$("#Mealsinfo tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				sampleTimepoints = filterJsonArray(d,'type','mealsTimePointInformation','rowNo',1,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				windowPeriod=""; windowPeriodCount=0;
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT"
						 	||sampleTimepoints[dp]["fieldName"] === "MEALSTTYPE"
						 	||sampleTimepoints[dp]["fieldName"] === "COMPLETIONTIME"
						 	||sampleTimepoints[dp]["fieldName"] === "COMPLETIONTYPE"
						 	||sampleTimepoints[dp]["fieldName"] === "TIMEPOINT"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD"){
						
						 if(sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD" 
 						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN"){
 							 windowPeriod = windowPeriod + " " + sampleTimepoints[dp]["displayValue"];
 							 windowPeriodCount++;
 						 }
 						 else{
 							 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";	
 							 rowsAssignedCount++;windowPeriodCount=0;
 						 }
 						 
 						 if(windowPeriodCount === 2){
 							 tableRows = tableRows + "<td>" + windowPeriod;	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";	 
 							 rowsAssignedCount++;
 						 }
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 6){
					$("#Mealsinfo tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "'>" + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#Mealsinfo tbody tr").length === 0){
				$("#Mealsinfo").attr("data-srnumber", "0");
				$("#Mealsinfo tbody").append("<tr><td colspan='7' style='text-align:center;'>No Data Found</td></tr>");
			}
			else{
				$("#Mealsinfo").attr("data-defaultrow", "0");
				$("#Mealsinfo").attr("data-srnumber", subRowNumber);
			}
			
			
			subRowNumber = 0;
			sampleTimepoints = filterJsonArray(d,'type','vitalTimepointInformation','rowNo',1);
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(rowIndexes.indexOf(sampleTimepoints[ri].subRowNo)=== -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}
			
			$("#preAndPostTable tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				sampleTimepoints = filterJsonArray(d,'type','vitalTimepointInformation','rowNo',1,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				windowPeriod=""; windowPeriodCount=0;
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT"
						 	||sampleTimepoints[dp]["fieldName"] === "TOTALTIMEPOINTS"
						 	||sampleTimepoints[dp]["fieldName"] === "VITALPOSITION"
						 	||sampleTimepoints[dp]["fieldName"] === "TIMEPOINT"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTYPE"
						 	||sampleTimepoints[dp]["fieldName"] === "ORTHOSTATIC"
						 	||sampleTimepoints[dp]["fieldName"] === "TESTNAMES"
						 	||sampleTimepoints[dp]["fieldName"] === "PARAMETER"){
						 
						 if(sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN" 
 						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD"
 						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTYPE"){
 							 windowPeriod = windowPeriod + " " + sampleTimepoints[dp]["displayValue"];
 							 windowPeriodCount++;
 						 }
 						 else{
 							 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";
 							 rowsAssignedCount++;windowPeriodCount=0;
 						 }
 						 
 						 if(windowPeriodCount === 3){
 							 tableRows = tableRows + "<td>" + windowPeriod;	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";	
 							 rowsAssignedCount++;
 						 }
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 7){
					$("#preAndPostTable tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "'>" + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#preAndPostTable tbody tr").length === 0){
				$("#preAndPostTable").attr("data-srnumber", "0");
				$("#preAndPostTable tbody").append("<tr><td colspan='8' style='text-align:center;'>No Data Found</td></tr>");
			}
			else{
				$("#preAndPostTable").attr("data-defaultrow", "0");
				$("#preAndPostTable").attr("data-srnumber", subRowNumber);
			}
			
			sampleTimepoints = filterJsonArray(d,'type','SkinSensivity','rowNo',1);
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(rowIndexes.indexOf(sampleTimepoints[ri].subRowNo)=== -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}

			rowIndexes.sort();
			$("#tblSkinSensivity tbody").empty();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				sampleTimepoints = filterJsonArray(d,'type','SkinSensivity','rowNo',1,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				windowPeriod=""; windowPeriodCount=0;
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT"
						 	||sampleTimepoints[dp]["fieldName"] === "PARAMETERS"
						 	||sampleTimepoints[dp]["fieldName"] === "TIMEPOINT"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODVALUE"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTIME"){
						 if(sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN" 
 						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODVALUE"
 						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTIME"){
 							 windowPeriod = windowPeriod + " " + sampleTimepoints[dp]["displayValue"];
 							 windowPeriodCount++;
 						 }
 						 else{
 							 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
 							 tableRows = tableRows + "</td>";	
 							 rowsAssignedCount++;windowPeriodCount=0;
 						 }
 						 
 						 if(windowPeriodCount === 3){
 							 tableRows = tableRows + "<td>" + windowPeriod;	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";	
 							 rowsAssignedCount++;
 						 }
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 4){
					$("#tblSkinSensivity tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "'>" + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#tblSkinSensivity tbody tr").length > 1){
				$("#tblSkinSensivity").attr("data-defaultrow", "0");
				$("#tblSkinSensivity").attr("data-srnumber", "0");
				$("#tblSkinSensivity tbody").find("tr:eq(0)").remove();
			}
			else{
				$("#tblSkinSensivity").attr("data-srnumber", subRowNumber);
				$("#tblSkinSensivity tbody").append("<tr><td colspan='5' style='text-align:center;'>No Data Found</td></tr>");
			}
			
			sampleTimepoints = filterJsonArray(d,'type','ecgTimePoins','rowNo',1);
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(rowIndexes.indexOf(sampleTimepoints[ri].subRowNo)=== -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}
			$("#tblEcg tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				sampleTimepoints = filterJsonArray(d,'type','ecgTimePoins','rowNo',1,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				windowPeriod=""; windowPeriodCount=0;
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT"
						 	||sampleTimepoints[dp]["fieldName"] === "TIMEPOINT"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTIME"){
						 if(sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN" 
						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIOD"
						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTIME"){
							 windowPeriod = windowPeriod + " " + sampleTimepoints[dp]["displayValue"];
							 windowPeriodCount++;
						 }
						 else{
							 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
 							 tableRows = tableRows + "</td>";	
							 rowsAssignedCount++;windowPeriodCount=0;
						 }
						 
						 if(windowPeriodCount === 3){
							 tableRows = tableRows + "<td>" + windowPeriod;	
							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";	
							 rowsAssignedCount++;
						 }
					 }
				}
				
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 3){
					$("#tblEcg tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "'>" + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#tblEcg tbody tr").length > 1){
				$("#tblEcg").attr("data-defaultrow", "0");
				$("#tblEcg").attr("data-srnumber", "0");
				$("#tblEcg tbody").find("tr:eq(0)").remove();
			}
			else{
				$("#tblEcg tbody").append("<tr><td colspan='4' style='text-align:center;'>No Data Found</td></tr>");
				$("#tblEcg").attr("data-srnumber", subRowNumber);
			}
			
			sampleTimepoints = filterJsonArray(d,'type','SkinAdhesion','rowNo',1);
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < sampleTimepoints.length;ri++ ){
				if(rowIndexes.indexOf(sampleTimepoints[ri].subRowNo) === -1){
					rowIndexes.push(sampleTimepoints[ri].subRowNo);
				}
			}

			$("#tblSkinAdhesion tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				sampleTimepoints = filterJsonArray(d,'type','SkinAdhesion','rowNo',1,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				windowPeriod=""; windowPeriodCount=0;
				for(var dp=0; dp <sampleTimepoints.length; dp++ ){
					 if(sampleTimepoints[dp]["fieldName"] === "TREATMENT"
						 	||sampleTimepoints[dp]["fieldName"] === "PARAMETERS"
						 	||sampleTimepoints[dp]["fieldName"] === "TIMEPOINT"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODVALUE"
						 	||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTIME"){
						 if(sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODSIGN" 
 						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODVALUE"
 						 		||sampleTimepoints[dp]["fieldName"] === "WINDOWPERIODTIME"){
 							 windowPeriod = windowPeriod + " " + sampleTimepoints[dp]["displayValue"];
 							 windowPeriodCount++;
 						 }
 						 else{
 							 tableRows = tableRows + "<td>" + sampleTimepoints[dp]["displayValue"];	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
 							 tableRows = tableRows + "</td>";	
 							 rowsAssignedCount++;windowPeriodCount=0;
 						 }
 						 
 						 if(windowPeriodCount === 3){
 							 tableRows = tableRows + "<td>" + windowPeriod;	
 							 tableRows = tableRows + generateReviewElement(sampleTimepoints[dp]['projectDetailsId'],sampleTimepoints[dp]["fieldName"],sampleTimepoints[dp]["fieldValue"]);
							 tableRows = tableRows + "</td>";	
 							 rowsAssignedCount++;
 						 }
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 4){
					$("#tblSkinAdhesion tbody").append("<tr data-rowno='1' data-subrowno='" + rowIndexes[ri] + "'>" + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#tblSkinAdhesion tbody tr").length > 1){
				$("#tblSkinAdhesion").attr("data-defaultrow", "0");
				$("#tblSkinAdhesion").attr("data-srnumber", "0");
				$("#tblSkinAdhesion tbody").find("tr:eq(0)").remove();
			}
			else{
				$("#tblSkinAdhesion tbody").append("<tr><td colspan='5' style='text-align:center;'>No Data Found</td></tr>");
				$("#tblSkinAdhesion").attr("data-srnumber", subRowNumber);
			}
			
			var criterias = filterJsonArray(d,'type','inclusionCriteria');
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < criterias.length;ri++ ){
				if(rowIndexes.indexOf(criterias[ri].subRowNo)=== -1){
					rowIndexes.push(criterias[ri].subRowNo);
				}
			}

			$("#inclusionTable tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				criterias = filterJsonArray(d,'type','inclusionCriteria','rowNo',0,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				for(var dp=0; dp <criterias.length; dp++ ){
					 if(criterias[dp]["fieldName"] === "PARAMETER"
						 	||criterias[dp]["fieldName"] === "GENDER"){
						 tableRows = tableRows + "<td>" + criterias[dp]["displayValue"];	
						 if(criterias[dp]["fieldName"] === "PARAMETER"){
							 parameter =  criterias[dp]["fieldValue"];
						 }
						 tableRows = tableRows + generateReviewElement(criterias[dp]['projectDetailsId'],criterias[dp]["fieldName"],criterias[dp]["fieldValue"]);
						 tableRows = tableRows + "</td>";	
						 rowsAssignedCount++;
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 2){
					$("#inclusionTable tbody").append("<tr data-rowno='0' data-subrowno='" + rowIndexes[ri] + "' data-pid='" + parameter + "'>"  + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#inclusionTable tbody tr").length > 1){
				$("#inclusionTable").attr("data-defaultrow", "0");
				$("#inclusionTable").attr("data-srnumber", "0");
				$("#inclusionTable tbody").find("tr:eq(0)").remove();
			}
			else{
				$("#inclusionTable").attr("data-srnumber", subRowNumber);
				$("#inclusionTable tbody").append("<tr><td colspan='3' style='text-align:center;'>No Data Found</td></tr>");
			}
			
			criterias = filterJsonArray(d,'type','exclusionCriteria');
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < criterias.length;ri++ ){
				if(rowIndexes.indexOf(criterias[ri].subRowNo)=== -1){
					rowIndexes.push(criterias[ri].subRowNo);
				}
			}
			
			$("#exclusionTable tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				criterias = filterJsonArray(d,'type','exclusionCriteria','rowNo',0,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				for(var dp=0; dp <criterias.length; dp++ ){
					 
					 if(criterias[dp]["fieldName"] === "PARAMETER"
						 	||criterias[dp]["fieldName"] === "GENDER"){
						 tableRows = tableRows + "<td>" + criterias[dp]["displayValue"]; 
						 if(criterias[dp]["fieldName"] === "PARAMETER"){
							 parameter =  criterias[dp]["fieldValue"];
						 }
						 
						 tableRows = tableRows + generateReviewElement(criterias[dp]['projectDetailsId'],criterias[dp]["fieldName"],criterias[dp]["fieldValue"]);
						 tableRows = tableRows + "</td>";	
							 rowsAssignedCount++;
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 2){
					$("#exclusionTable tbody").append("<tr data-rowno='0' data-subrowno='" + rowIndexes[ri] + "' data-pid='" + parameter + "'>"  + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#exclusionTable tbody tr").length > 1){
				$("#exclusionTable").attr("data-defaultrow", "0");
				$("#exclusionTable").attr("data-srnumber", "0");
				$("#exclusionTable tbody").find("tr:eq(0)").remove();
			}
			else{
				$("#exclusionTable").attr("data-srnumber", subRowNumber);
				$("#exclusionTable tbody").append("<tr><td colspan='3' style='text-align:center;'>No Data Found</td></tr>");
			}
			
			criterias = filterJsonArray(d,'type','OtherActivity');
			rowIndexes = []
			tableRows = "";
			for(var ri=0; ri < criterias.length;ri++ ){
				if(rowIndexes.indexOf(criterias[ri].subRowNo)=== -1){
					rowIndexes.push(criterias[ri].subRowNo);
				}
			}
			$("#tblOtherActivity tbody").empty();
			rowIndexes.sort();
			for(var ri=0; ri < rowIndexes.length;ri++ ){
				rowsAssignedCount = 0;
				criterias = filterJsonArray(d,'type','OtherActivity','rowNo',0,'subRowNo',rowIndexes[ri]);
				tableRows = "";
				for(var dp=0; dp <criterias.length; dp++ ){
					 if(criterias[dp]["fieldName"] === "ACTIVITY"
						 	||criterias[dp]["fieldName"] === "TREATMENT"
						 	||criterias[dp]["fieldName"] === "TIMEPOINT"
						 	||criterias[dp]["fieldName"] === "PARAMETER"){
						 tableRows = tableRows + "<td>" + criterias[dp]["displayValue"]; 
						 if(criterias[dp]["fieldName"] === "PARAMETER"){
							 parameter =  criterias[dp]["fieldValue"];
						 }
						 
						 tableRows = tableRows + generateReviewElement(criterias[dp]['projectDetailsId'],criterias[dp]["fieldName"],criterias[dp]["fieldValue"]);
						 tableRows = tableRows + "</td>";	
							 rowsAssignedCount++;
					 }
    			}
				tableRows += "<td><a class='fa fa-trash delete' title='Delete'></a></td></tr>";
				if(rowsAssignedCount === 4){
					$("#tblOtherActivity tbody").append("<tr data-rowno='0' data-subrowno='" + rowIndexes[ri] + "' data-pid='" + parameter + "'>"  + tableRows);
					subRowNumber = rowIndexes[ri];
				}
			}
			
			if($("#tblOtherActivity tbody tr").length > 1){
				$("#tblOtherActivity").attr("data-defaultrow", "0");
				$("#tblOtherActivity").attr("data-srnumber", "0");
				$("#tblOtherActivity tbody").find("tr:eq(0)").remove();
			}
			else{
				$("#tblOtherActivity").attr("data-srnumber", subRowNumber);
				$("#tblOtherActivity tbody").append("<tr><td colspan='5' style='text-align:center;'>No Data Found</td></tr>");
			}
			
			infomration = filterJsonArray(d,'type','sampleProcessing','rowNo',0);
			$.each(infomration, function(index,obj){
				if($("[name='" + obj.fieldName + "']").attr("type") === "radio"){
					$("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]").attr('checked', 'checked');
					checkCentrifugation("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]");
					checkStorageDifferent("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]");
					checkMatrixDifference("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]");
				}
				else if($("[name='" + obj.fieldName + "']").attr("type") === "checkbox"){
					$("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]").attr('checked', 'checked');
				}
				else{
					$("[name='" + obj.fieldName + "']").val(obj.fieldValue);	
				}
			});
			
			infomration = filterJsonArray(d,'type','sampleProcessing','rowNo',1);
			$.each(infomration, function(index,obj){
				$("[name='" + obj.fieldName + "'][data-subrownumber='" + obj.subRowNo + "']").val(obj.fieldValue);
			});
			
			infomration = filterJsonArray(d,'type','skinSensivityTimePoint');
			$.each(infomration, function(index,obj){
				if($("[name='" + obj.fieldName + "']").attr("type") === "radio" || $("[name='" + obj.fieldName + "']").attr("type") === "checkbox"){
					$("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]").attr('checked', 'checked');
				}
				else{
					$("[name='" + obj.fieldName + "']").val(obj.fieldValue);	
				}
			});
			
			infomration = filterJsonArray(d,'type','skinAdhesionTimePoint');
			$.each(infomration, function(index,obj){
				if($("[name='" + obj.fieldName + "']").attr("type") === "radio" || $("[name='" + obj.fieldName + "']").attr("type") === "checkbox"){
					$("[name='" + obj.fieldName + "'][value=" + obj.fieldValue + "]").attr('checked', 'checked');
				}
				else{
					$("[name='" + obj.fieldName + "']").val(obj.fieldValue);	
				}
			});
			
			infomration = filterJsonArray(d,'type','PROCESSING','fieldName','CONDITIONS','rowNo',0,'subRowNo','0');
			$.each(infomration, function(index,obj){
				var pValues = obj.fieldValue.toString().split(',');
				for(var fv = 0; fv < pValues.length ; fv++){
					$("[name='cen_" + pValues[fv] +"']").prop("checked", true);
				}
			});
			
			
			infomration = filterJsonArray(d,'type','CENTRIFUGATION','fieldName','CONDITIONS','rowNo',0,'subRowNo','0');
			$.each(infomration, function(index,obj){
				var pValues = obj.fieldValue.toString().split(',');
				for(var fv = 0; fv < pValues.length ; fv++){
					$("[name='pro_" + pValues[fv] +"']").prop("checked", true);
				}
			});
			
			if($("#tblDefaultActivities").attr("data-defaultrow").toString() === "1"){
        		$("#tblDefaultActivities tbody").empty();	
        		$("#tblDefaultActivities").attr("data-defaultrow", 0);	
        	}

			
			/*Populate default activities */
			infomration = filterJsonArray(d,'type','DefaultActivity');
			$.each(infomration, function(index,obj){
				var activityName = "",parameterName = "";
				$("#ddlDefaultActivities > option").each(function() {
				    if( this.value === obj['displayValue'].toString()){
				    	activityName = this.text;
				    }
				});
				
				for(var i=0;i<projectParameters.length;i++){
					if(projectParameters[i]["id"].toString() === obj['fieldValue'].toString()){
						parameterName = projectParameters[i]["description"];break;
					}
				}

				$("#tblDefaultActivities tbody").append("<tr data-row='" + obj['rowNo'] + "' data-subRowNo='" + obj['subRowNo'] + "' data-aid='" + obj['displayValue'] + "' data-pid='" + obj['fieldValue'] + "'><td>" + activityName
						+ "</td><td>" + parameterName  + "</td><td><a class='fa fa-trash delete' title='Deactivate'></a></td></tr>");
			});
			
			$.each($("[name='doseTimePoint']"), function(index,obj){
				if($.trim($(obj).val()).length > 0){
					$(obj).attr("data-validdosetimepoint",'true');		
				}
				else{
					$(obj).attr("data-validdosetimepoint",'false');
				}
			});
						
			if(actionType === "review"){
				commentsDraggable(true);
			}
			else if(disrepancies.length > 0){
				commentsDraggable(false);
			}
			loadComments(disrepancies);
			
			vitalsTreatmentChange();
			treatmentSpecificMeals();
			$(".loader").hide();
		}
    }
    
    function checkDoseparameterTimepoints(ele){
    	if($(ele).attr('name') === "TimepointSpecificParameterDose"){
    		if($(ele).val() === "NO"){
    			$("#dosingTimepointParameters").prop("disabled",'disabled');
    		}
    		else if($(ele).val() === "YES"){
    			$("#dosingTimepointParameters").prop("disabled",'');
    		}
    	}
    }
    
    function verifyDosing(){
	    var dosetype = $("#doseType").val();
	    var nooftrtmnts = $("#nooftreatments").val();
	    var studyDesign = $("#studyDesign").val();
	    var nofsachetlabels = $("#nofsachetlabels").val();
	    generateTreatmentWiseInformation(dosetype, studyDesign,nooftrtmnts);
	    generateDosingTimepoints(dosetype, studyDesign);
    }
    
    function getProjectDetails(){
    	$.ajax({
    		url: $("#mainUrl").val() + '/studydesign/projectInformation/'+$("input[name='projectId']").val(),
    		type:'GET',
    		contentType:"application/json; charset=utf-8",
    		dataType: 'json',
    		success:function(d){
    			fillFormData(d);
    		},
    		error:function(er){
    			debugger;
    		}
    	});
    }
    
    $(document).ready(function () {
    	
    	toggleSideBar();
    	var isRequestSent = false;
    	var defaultActivityInterval = setInterval(function(){
    		if(defaultActivityInterval !== null){
    			if(loadFormData === true){
    				if($.trim($("input[name='projectId']").val()).length > 0 && isRequestSent === false){
    					isRequestSent = true;
    					getProjectDetails();
    					clearInterval(defaultActivityInterval);defaultActivityInterval=null;
    				}
    				else{
        				clearInterval(defaultActivityInterval);defaultActivityInterval=null;	
        			}
	    		}
    		}
    	}, 1000);
    	
    	$.ajax({
    		url: $("#mainUrl").val() + '/globalAtivity/otherActivities/',
    		type:'GET',
    		success:function(data){
    			for(var i=0;i<data.length;i++){
    				$("#otherActivity").append("<option value='" + data[i]['activityId'] + "'>" + data[i]['activityName'] + "</option>");
    			}
    		}
    	});
    	
    	$.ajax({
    		url: $("#mainUrl").val() + '/globalAtivity/defalutActivities/',
    		type:'GET',
    		success:function(data){
    			var aIds = "";
    			for(var i=0;i<data.length;i++){
    				if(aIds === ""){
    					aIds = data[i]['activityId'];
    				}
    				else{
    					aIds = aIds + ',' + data[i]['activityId'];
    				}
    				$("#ddlDefaultActivities").append("<option value='" + data[i]['activityId'] + "'>" + data[i]['activityName'] + "</option>");
    			}
    			
    			$.ajax({
    	    		url: $("#mainUrl").val() + '/parameters/getParameters?aids=' + aIds,
    	    		type:'GET',
    	    		success:function(params){
    	    			defaultActivityParameters = params;
    	    		}
    	    	});
    		}
    	});
    	
    	$("#btnBack").click(function(e){
    		$(this).hide();
    		$("#btnSaveDiscrepancyResponse").hide();
    		$("#formResponse").hide('slow',function(e){
    			$("#tblComments").show('slow');
    		});
    	});
    	
    	$("#ddlDefaultActivities").change(function(e){
    		if($(this).val() !== ""){
    			var aid = $(this).val();
    			getActivityParamters($(this).val(), 'ddlDefaultActivityParameter');
    			$.each($("#tblDefaultActivities tbody tr"),function(index,ele){
    				if($(ele).data("aid").toString() !== aid.toString()){
    					$(ele).hide();
    				}
    				else{
    					$(ele).show();
    				}
    			});
    		}
    		else{
    			$("#tblDefaultActivities tbody").empty();
    		}
    	});
    	
    	$(document).on("click","#tblComments .edit",function(e){
    		var aEdit = $(this);
    		$("#tblComments").hide('slow',function(e){
    			$("#formResponse,#btnBack,#btnSaveDiscrepancyResponse").show('slow');
    			var comment = $(aEdit).closest("tr").find("td:eq(0)").text();
        		$("#lblComment").text(comment);
        		$("#hidDiscrepancyId").val($(aEdit).closest("tr").attr("data-id"));
    		});
    	});
    	
    	$("#btnDefaultActivities").click(function(e){
    		isValid = true;
        	if($("#ddlDefaultActivities").val()===""){
        		$("#ddlDefaultActivities").addClass('validate validate-error');
        		$("#ddlDefaultActivities").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if($.trim($("#ddlDefaultActivityParameter").val())===0){
        		$("#ddlDefaultActivityParameter").addClass('validate validate-error');
        		$("#ddlDefaultActivityParameter").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if(!isValid){
        		return;
        	}
        	
        	$.each($("#tblDefaultActivities tbody tr"),function(index,ele){
        		if($(ele).attr("data-aid") === $("#ddlDefaultActivities").val()
        					&& $(ele).attr("data-pid") === $("#ddlDefaultActivityParameter").val()){
        			isValid = false;
        			return false;
        		}
        	});
        	
        	if(!isValid)
        	{
        		displayMessage('info', 'Activity and parameter already exists');
        	}
        	else{
        		var formData =$('#frmData').serializeArray();
        		$.ajax({
        			url: $("#mainUrl").val() + '/studydesign/addActivityParameter/' + $("[name='projectId']").val() + "/" + $("#ddlDefaultActivities").val() + '/' + $("#ddlDefaultActivityParameter").val(),
        			type:'POST',
        			data: formData,
        			success: function(data){
        				$("#tblDefaultActivities tbody").append("<tr data-subrowno='" + data + "' data-row='" + data + "' data-aid='" + $("#ddlDefaultActivities").val() + "' data-id='" +  $("#ddlDefaultActivityParameter").val() + "'><td>" + $("#ddlDefaultActivities option:selected").text() + "</td><td>" + $("#ddlDefaultActivityParameter option:selected").text() + "</td><td><a class='fa fa-trash delete'></a></td></tr>");		
        			}
        		});
	        }
        });
    	
    	$("#btnSaveDescrepancy").click(function(e){
    		if($.trim($("#txtComments").val()).length === 0){
    			displayMessage('info', "Enter comments");
    			return;
    		}
    		$(".loader").show();
    		var formData =$($(this).closest("form")).serializeArray();
    		
    		$.ajax({
    			url: $("#mainUrl").val() + '/studydesign/discrepancy',
    			data: formData,
    			type:'POST',
    			success:function(data){
    				$(".loader").hide();
    				$("#txtComments").val("");
    			}
    		});
    	});
    	
    	$("#btnSaveDiscrepancyResponse").click(function(e){
    		if($.trim($("#txtResponse").val()).length === 0){
    			displayMessage('info', "Enter response");
    			return;
    		}
    		$(".loader").show();
    		var formData =$($(this).closest("form")).serializeArray();
    		
    		$.ajax({
    			url: $("#mainUrl").val() + '/studydesign/discrepancyModification',
    			data: formData,
    			type:'POST',
    			success:function(data){
    				$(".loader").hide();
    			}
    		});
    	});
    	
    	$(document).on("click",".table-flag",function(e){
    		var tr = $(this).closest("tr");
    		showDiscripencyDialog($(this).attr("data-fieldName"),$(this).closest("table").attr("data-name"),$(tr).attr("data-rowno"),$(tr).attr("data-subrowno"));
    	});
    	
    	$(document).on("click",".reviewFormTable .flag-studydesign",function(e){
    		var element = $(this).prev();
    		if($(element).attr("data-rownumber") === undefined || $(element).attr("data-rownumber") === null){
    			showDiscripencyDialog($(element).attr("name"),$(this).closest(".parent").attr("data-name"),0,0);
    		}
    		else{
    			showDiscripencyDialog($(element).attr("name"),$(this).closest(".parent").attr("data-name"),$(element).attr("data-rownumber"),$(element).attr("data-subrownumber"));
    		}
    	});
    	
    	$(document).on("keydown",".timepoint-format",function(e){
    		checkTimepointInputKey(e,$(this));
    	});
    	
    	
    	$("#timepointSpecificParameterDose").change(function(e){
    		checkDoseparameterTimepoints($(this));
    	});
    	
    	$("#dosingTreatmentvalue").change(function(e){
    		$("#dosingTimepointParameters").empty();
    		$("#dosingTimepointParameters").append("<option value=''>Select</option>");
    		if($(this).val() === "0"){
    			$("#dosingTimepointParameters").val("");
    			$("#dosingTimepointParameters").prop("disabled","disabled");
    		}
    		else if($("#timepointSpecificParameterDose").val() === "YES"){
    			var dosingDiv = $(document).find("[data-dosingnumber='" + $(this).val() + "']");
    			var timepoints = $(dosingDiv).find("[name='doseTimePoint']");
    			var isFilled = true;
    			$.each(timepoints,function(index,ele){
    				if($.trim($(ele).val()).length === 0){
    					isFilled = false;
    				}
    				else{
    					$("#dosingTimepointParameters").append("<option value='" + $(ele).val() + "'>" + $(ele).val() + "</option>");
    				}
    			});
    			if(!isFilled){
    				displayMessage("info","Fill dose timpoints");
    				$("#dosingTimepointParameters").attr("data-isfilled","false");
    			}
    			else{
    				$("#dosingTimepointParameters").attr("data-isfilled","true");
    			}
    		}
    	});
    	
    	$(document).on("keydown",".time-format",function(e){
    		checkTimeformatInputKey(e,$(this));
    	});
    	
    	$(document).on("blur","[name='doseTimePoint'],[name='noOfDosings'],[name='noOfTreatments']",function(e){
    		$("#dosingTreatmentvalue").val('');
    		$("#dosingTimepointParameters").empty();
    		$("#dosingTimepointParameters").append("<option value=''>Select</option>");
    	});
    	
    	$(document).on("blur",".timepoint-format",function(e){
    		if(checkTimepoint(e,$(this)) && $(this).hasClass("autosave")){
    			var treatmentNoVal = $("#dosingTreatmentvalue").val();
    			if($(this).closest(".parent").data("name") === "DosingTimepoints"){
    				if($("#doseType").val() === "SINGLE"){
    					$(this).attr("data-validdosetimepoint",'true');
    					var timpointVal = $(this).closest(".parent").data("name");
    					autoSaveElementData($(this), $(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),"0");
    				}
    				else if($("#doseType").val() !== ""){
    					if($.trim($(this).val()).length > 0){
    						var doseTimepoints = [];
        					$.each($(this).closest("tbody").find("tr"),function(rIndex,rowElement){
        						var doseTimepoint  = $(rowElement).find("[name='doseTimePoint']");
        						if($.trim($(doseTimepoint).val()).length > 0){
        							doseTimepoints.push($(doseTimepoint).val());	
        						}
        					});
        					
        					let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
        		         	var duplicateDoseTimepoints = findDuplicates(doseTimepoints);
        					if(duplicateDoseTimepoints.length === 0){
        						$(this).attr("data-validdosetimepoint",'true');
        						var timpointVal = $(this).closest(".parent").data("name");
        						autoSaveElementData($(this), $(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),$(this).closest('[data-dosingnumber]').data("dosingnumber"));	
        					}
        					else{
        						displayMessage('info', "Duplicate dose timepoint found please check");
        						$(this).val("");
        					}	
    					}
    					else{
    						$(this).attr("data-validdosetimepoint",'false');
    						displayMessage('info', "Enter dose timepoint(s)");
    						$(this).val("");
    					}
    				}
    			}
    			else{
    				autoSaveElementData($(this), $(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),$("#dosingTreatmentvalue").val());	
    			}
    		}
    	});
    	
    	$(document).on("blur",".time-format",function(e){
    		if(checkTimeformat(e,$(this)) && $(this).hasClass("autosave")){
    			var treatmentNoVal = $("#dosingTreatmentvalue").val();
    			if($(this).closest(".parent").data("name") === "DosingTimepoints"){
    				if($("#doseType").val() === "SINGLE"){
    					if($.trim($(this).val()).length > 0){
    						autoSaveElementData($(this), $(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),"0");
    					}
    					else{
    						$(this).attr("data-validdosetimepoint",'false');
    						displayMessage('info', "Enter dose timepoint");
    						$(this).val("");
    					}
    				}
    				else if($("#doseType").val() !== ""){
    					var doseTimepointElement = $(this).closest("tr").find("[name='doseTimePoint']");
    					if($(doseTimepointElement).data("validdosetimepoint") === true){
    						autoSaveElementData($(this), $(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),$(this).closest('[data-dosingnumber]').data("dosingnumber"));	
    					}
    					else{
    						$(this).val("");
    						displayMessage('info', "Please enter dose timepoint");	
    					}
    				}
    			}
    			else{
    				autoSaveElementData($(this), $(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),$("#dosingTreatmentvalue").val());	
    			}
    			//autoSaveData($(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,  $(this).data("fieldorder"));
    		}
    	});
    	
    	$("#restrictionstable,#exclusionTable,#inclusionTable,#dosingParameterList").on("click",".delete",function(e){
    		var row = $(this).closest("tr");
    		var table = $(this).closest("table");
    		var subRow = $(this).closest("tr").data("subrowno");
    		$.confirm({
    		    title: 'Confirm!',
    		    content: 'Do you want to delete the record',
    		    buttons: {
    		        confirm: function () {
    		        	var formData =$('#frmData').serializeArray();
    		        	formData.push({name:"projectId",value: $("[name='projectId']").val()});
    		        	formData.push({name:"projectNo",value: $("[name='projectNumber']").val()});
    		        	formData.push({name:"rowNumber",value: subRow });
    		            formData.push({name: "subRowNumber",value: subRow });
    		            formData.push({name: "type",value: $(table).data("name")});
    		            $.ajax({
    		             	url:$("#mainUrl").val() + '/studydesign/deactivateProjectDetails',
    		             	type:'POST',
    		             	data: formData,
    		             	success:function(e){
    		             		$(row).remove();
    		             	},
    		             	error:function(er){
    		             		debugger;
    		             	}
    		             });
    		        },
    		        cancel: function () {
    		         
    		        }
    		    }
    		});
    	});
    	
    	$("#tsplconditionwisedata,#sampletimepointTreatmentslisttable,#tblDefaultActivities,#preAndPostTable,#Mealsinfo").on("click",".delete",function(e){
    		var row = $(this).closest("tr");
    		var table = $(this).closest("table");
    		var subRow = $(this).closest("tr").attr("data-subrowno");
    		$.confirm({
    		    title: 'Confirm!',
    		    content: 'Do you want to delete the record',
    		    buttons: {
    		        confirm: function () {
    		        	var formData =$('#frmData').serializeArray();
    		        	formData.push({name:"projectId",value: $("[name='projectId']").val()});
    		        	formData.push({name:"projectNo",value: $("[name='projectNumber']").val()});
    		        	formData.push({name:"rowNumber",value: subRow });
    		            formData.push({name: "subRowNumber",value: subRow });
    		            formData.push({name: "type",value: $(table).data("name")});
    		            $.ajax({
    		             	url:$("#mainUrl").val() + '/studydesign/deactivateProjectDetails',
    		             	type:'POST',
    		             	data: formData,
    		             	success:function(e){
    		             		$(row).remove();
    		             		var tbody = $(table).find("tbody");
    		             		var thead = $(table).find("thead");
    		             		var rowsCount = $(tbody).find("tr").length;
    		             		if(rowsCount === 0){
    		             			$(table).attr("data-defaultrow", "1");	
    		             			$(tbody).append("<td colspan='" + $(thead).find("td").length + "' class='center'>No Data Found</td>");
    		             		}
    		             	},
    		             	error:function(er){
    		             		debugger;
    		             	}
    		             });
    		        },
    		        cancel: function () {
    		         
    		        }
    		    }
    		});
    	});
    	
    	$.ajax({
    		url: $("#mainUrl").val() + '/languages/getAll',
    		type:'GET',
    		success:function(data){
    			for(var i=0;i<data.length;i++){
    				$("#ddlExclusionLanguage,#ddlInclusionLanguage").append("<option value='"+ data[i].id +"'>"+ data[i].language +"</option>");
    			}
    		},
    		error:function(d){
    			debugger;
    		}
    	});
    	
    	$.ajax({
    		url: $("#mainUrl").val() + '/globalAtivity/getGlobalActivity/RestrictionCompliance' ,
    		type:'GET',
    		success:function(data){
    			for(var i=0;i<data.length;i++){
    				$("#resActivity").append("<option value='" + data[i]['id'] + "'>" + data[i]['name'] + "</option>");
    			}
    		}
    	});
    	
    	$.ajax({
    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + $("[name='InclusionCriteria']").val(),
    		type:'GET',
    		success:function(data){
    			for(var i=0;i<data.parameterDto.length;i++){
    				$("#icParameter").append("<option value='" + data.parameterDto[i]['parameterId'] + "'>" + data.parameterDto[i]['parameterName'] + "</option>");
    			}
    		}
    	});
    	
    	$.ajax({
    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + $("[name='ExclusionCriteria']").val(),
    		type:'GET',
    		success:function(data){
    			for(var i=0;i<data.parameterDto.length;i++){
    				$("#ecParameter").append("<option value='" + data.parameterDto[i]['parameterId'] + "'>" + data.parameterDto[i]['parameterName'] + "</option>");
    			}
    		}
    	});
    	
    	$.ajax({
    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + $("[name='DosingCollection']").val(),
    		type:'GET',
    		success:function(data){
    			$("#parameterdesc").append("<option value=''>Select</option>");
    			for(var i=0;i<data.parameterDto.length;i++){
    				$("#parameterdesc").append("<option value='" + data.parameterDto[i]['parameterId'] + "'>" + data.parameterDto[i]['parameterName'] + "</option>");
    			}
    		}
    	});
    	
    	var vitalvalue = $("[name='StudyExecutionVitals']").val();
    	
    	$.ajax({
//    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + $("[name='StudyExecutionVitals']").val(),
    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + $("[name='StudyExecutionVitals']").val(),
    		type:'GET',
    		success:function(data){
    			$("#vitalsParameters").empty();
    			for(var i=0;i<data.parameterDto.length;i++){
    				$("#vitalsParameters").append("<option value='" + data.parameterDto[i]['parameterId'] + "'>" + data.parameterDto[i]['parameterName'] + "</option>");
    			}
    			$('#vitalsParameters').multiselect({
    				maxPlaceholderWidth: 200
    	        });
    		}
    	});
    	
    	//Uncomment this to enable SkinSensivity during study design
    	/*if($("[name='SkinSensivity']").val()=== undefined){
    		displayMessage('error', "Skin sensivity activity does not exists. Please contact administrator");
    	}*/
    	$.ajax({
    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + $("[name='SkinSensivity']").val(),
    		type:'GET',
    		success:function(data){
    			$("#SkinSensivityParameters").empty();
    			for(var i=0;i<data.parameterDto.length;i++){
    				$("#SkinSensivityParameters").append("<option value='" + data.parameterDto[i]['parameterId'] + "'>" + data.parameterDto[i]['parameterName'] + "</option>");
    			}
    			$('#SkinSensivityParameters').multiselect();
    		}
    	});
    	
    	//Uncomment this to enable SkinAdhesion during study design
    	/*if($("[name='SkinAdhesion']").val()=== undefined){
    		displayMessage('error', "Skin adhsion activity does not exists. Please contact administrator");
    	}*/
    	$.ajax({
    		url: $("#mainUrl").val() + '/studyActivity/getActivityParameters/' + $("[name='SkinAdhesion']").val(),
    		type:'GET',
    		success:function(data){
    			$("#SkinAdhesionParameters").empty();
    			for(var i=0;i<data.parameterDto.length;i++){
    				$("#SkinAdhesionParameters").append("<option value='" + data.parameterDto[i]['parameterId'] + "'>" + data.parameterDto[i]['parameterName'] + "</option>");
    			}
    			$('#SkinAdhesionParameters').multiselect();
    		}
    	});
    	
    	$("#resActivity").change(function(e){
    		$("#resParameter").empty();
    		$("#resParameter").append("<option value=''>Select</option>");
    		if($(this).val() !== ""){
    			getActivityParamters($('#resActivity').val(),'resParameter');
    		}
    	});
    	
        $("#tdCentrifugationApplicable").on("click","[name='centrifugationApplicable']",function(d){
        	checkCentrifugation($(this));
        });
        
        $("#tdSkinAdhesionApplicable").on("click","[name='SkinAdhesionApplicable']",function(d){
        	checkSkinAdhesion($(this));
        });
        
        $("#tdSkinSensitivityApplicable").on("click","[name='SkinSensitivityApplicable']",function(d){
        	checkSkinSentivity($(this));
        });
        
        $("#tdSkinAdhesionTreatmentSpecificTimepoints").on("click","[name='SkinAdhesionTreatmentSpecificTimepoints']",function(d){
        	if($("#tblSkinAdhesion").attr("data-defaultrow").toString() !== "1"){
    			var element = $(this);
    			$.confirm({
        		    title: 'Confirm!',
        		    content: 'Changing treatment specific timepoint value will delete the Skin Adhesion data. Do you wish to continue?',
        		    buttons: {
        		        Yes: function () {
        		        	$(element).attr('data-old',$(element).val());
        		        	deactivateDataByType("SkinAdhesion","tblSkinAdhesion",5);
        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
        		        	skinAdhesionTreatments($(element));
        		        },
        		        No: function () {
        		        	$(element).val($(element).data('old'));
        		        }
        		    }
        		});
    		}
    		else{
    			$(this).attr('data-old',$(this).val());
    			skinAdhesionTreatments($(this));
        		autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
        	}
        });
        
        $("#tdSkinSensivityTreatmentSpecificTimepoints").on("click","[name='SkinSensivityTreatmentSpecificTimepoints']",function(d){
        	if($("#tblSkinSensivity").attr("data-defaultrow").toString() !== "1"){
    			var element = $(this);
    			$.confirm({
        		    title: 'Confirm!',
        		    content: 'Changing treatment specific timepoint value will delete the Skin Sensitivity data. Do you wish to continue?',
        		    buttons: {
        		        Yes: function () {
        		        	$(element).attr('data-old',$(element).val());
        		        	deactivateDataByType("SkinSensivity","tblSkinSensivity",5);
        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
        		        	skinSentivityTreatments($(element));
        		        },
        		        No: function () {
        		        	$(element).val($(element).data('old'));
        		        }
        		    }
        		});
    		}
    		else{
    			$(this).attr('data-old',$(this).val());
    			skinSentivityTreatments($(this));
        		autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
        	}
        });
        
        $("#tdEcgTreatmentSpecificTimepoints").on("click","[name='EcgTreatmentSpecificTimepoints']",function(d){
        	if($("#tblEcg").attr("data-defaultrow").toString() !== "1"){
    			var element = $(this);
    			$.confirm({
        		    title: 'Confirm!',
        		    content: 'Changing treatment specific timepoint value will delete the ECG data. Do you wish to continue?',
        		    buttons: {
        		        Yes: function () {
        		        	$(element).attr('data-old',$(element).val());
        		        	deactivateDataByType("ecgTimePoins","tblEcg",5);
        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
        		        	ecgTreatments($(element));
        		        },
        		        No: function () {
        		        	$(element).val($(element).data('old'));
        		        }
        		    }
        		});
    		}
    		else{
    			$(this).attr('data-old',$(this).val());
    			ecgTreatments($(this));
        		autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
        	}
        });
        
        
        $("#btnSkinSensivity").click(function(e){
        	isValid = true;
        	if($("#SkinSensivityTreatment").val()===""){
        		$("#SkinSensivityTreatment").addClass('validate validate-error');
        		$("#SkinSensivityTreatment").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if($.trim($("#SkinSensivityTimePoint").val())===0){
        		$("#SkinSensivityTimePoint").addClass('validate validate-error');
        		$("#SkinSensivityTimePoint").attr('data-validate','required');
        		isValid = false;
        	}

        	if($("#ssWindowperiodSign").val()===""){
        		$("#ssWindowperiodSign").addClass('validate validate-error');
        		$("#ssWindowperiodSign").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if($("#ssWindowperiodValue").val()===""){
        		$("#ssWindowperiodValue").addClass('validate validate-error');
        		$("#ssWindowperiodValue").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	var parameters = $('#SkinSensivityParameters option:selected').toArray().map(item => item.text).join();
        	
        	if($.trim(parameters).length === 0){
        		$("#SkinSensivityParameters").addClass('validate validate-error');
        		$("#SkinSensivityParameters").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if($.trim($("#ssWindowPeriodTime").val())===0){
        		$("#ssWindowPeriodTime").addClass('validate validate-error');
        		$("#ssWindowPeriodTime").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if(!isValid){
        		return;
        	}
        	
        	var timePoints = $("#SkinSensivityTimePoint").val();
         	var timePointsArray = timePoints.split(',');
         	let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
         	var duplicateTimepoints = findDuplicates(timePointsArray);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
         	
         	var allTimePoints = timePoints.split(',');
         	$.each($("#tblSkinSensivity tbody tr"),function(index,ele){
         		if($(ele).find("td:eq(0)").text() === $("#SkinSensivityTreatment").val() ||  $("#SkinSensivityTreatment").val() === "0"){
         			allTimePoints.push.apply(allTimePoints, $(ele).find("td:eq(2)").text().split(','));
         		}
         	});
         	
         	duplicateTimepoints = findDuplicates(allTimePoints);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
        	
        	
         	autoSaveElementData($("#SkinSensivityTreatment"), 'TREATMENT',  $("#SkinSensivityTreatment").val(), 1, ($("#tblSkinSensivity tbody tr").length + 1), "SkinSensivity", true, 0, $("#SkinSensivityTreatment").val());
         	autoSaveElementData($("#SkinSensivityParameters"), 'PARAMETERS',  $("#SkinSensivityParameters").val(), 1, ($("#tblSkinSensivity tbody tr").length + 1), "SkinSensivity", true, 1, parameters);
         	autoSaveElementData($("#SkinSensivityTimePoint"), 'TIMEPOINT',  $("#SkinSensivityTimePoint").val(), 1, ($("#tblSkinSensivity tbody tr").length + 1), "SkinSensivity", true, 2, $("#SkinSensivityTimePoint").val());
         	autoSaveElementData($("#ssWindowperiodSign"), 'WINDOWPERIODSIGN',  $("#ssWindowperiodSign").val(), 1, ($("#tblSkinSensivity tbody tr").length + 1), "SkinSensivity", true, 3, $("#ssWindowperiodSign option:selected").text());
         	autoSaveElementData($("#ssWindowperiodValue"), 'WINDOWPERIODVALUE',  $("#ssWindowperiodValue").val(), 1, ($("#tblSkinSensivity tbody tr").length + 1), "SkinSensivity", true, 4, $("#ssWindowperiodValue").val());
         	autoSaveElementData($("#ssWindowPeriodTime"), 'WINDOWPERIODTIME',  $("#ssWindowPeriodTime").val(), 1, ($("#tblSkinSensivity tbody tr").length + 1), "SkinSensivity", true, 5, $("#ssWindowPeriodTime option:selected").text());
            
            if($("#tblSkinSensivity").attr("data-defaultrow").toString() === "1"){
        		$("#tblSkinSensivity tbody").empty();	
        		$("#tblSkinSensivity").attr("data-defaultrow", 0);	
        	}
            
            $("#tblSkinSensivity tbody").append("<tr data-subrowno='" +  ($("#tblSkinSensivity tbody tr").length + 1) + "'><td>" + $("#SkinSensivityTreatment option:selected").text() + "</td><td>" + parameters + "</td><td>" + $("#SkinSensivityTimePoint").val() + "</td><td>" + 
            		$("#ssWindowperiodSign option:selected").text() + "" + $("#ssWindowperiodValue").val() + " " + $("#ssWindowPeriodTime option:selected").text() +"</td><td><a class='fa fa-trash delete' title='delete'></a></td></tr>");
            
            $('#SkinSensivityParameters').multiselect( 'reset' );
            $("#ssWindowPeriodTime,#ssWindowperiodValue,#ssWindowperiodSign,#SkinSensivityTimePoint").val("");
        });
        
        $("#btnSkinAdhesion").click(function(e){
        	isValid = true;
        	if($("#SkinAdhesionTreatment").val()===""){
        		$("#SkinAdhesionTreatment").addClass('validate validate-error');
        		$("#SkinAdhesionTreatment").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if($.trim($("#SkinAdhesionTimePoint").val())===0){
        		$("#SkinAdhesionTimePoint").addClass('validate validate-error');
        		$("#SkinAdhesionTimePoint").attr('data-validate','required');
        		isValid = false;
        	}

        	if($("#saWindowperiodSign").val()===""){
        		$("#saWindowperiodSign").addClass('validate validate-error');
        		$("#saWindowperiodSign").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if($("#saWindowperiodValue").val()===""){
        		$("#saWindowperiodValue").addClass('validate validate-error');
        		$("#saWindowperiodValue").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	var parameters = $('#SkinAdhesionParameters option:selected').toArray().map(item => item.text).join();
        	
        	if($.trim(parameters).length === 0){
        		$("#SkinAdhesionParameters").addClass('validate validate-error');
        		$("#SkinAdhesionParameters").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if($.trim($("#saWindowPeriodTime").val())===0){
        		$("#saWindowPeriodTime").addClass('validate validate-error');
        		$("#saWindowPeriodTime").attr('data-validate','required');
        		isValid = false;
        	}
        	
        	if(!isValid){
        		return;
        	}
        	
        	var timePoints = $("#SkinAdhesionTimePoint").val();
         	var timePointsArray = timePoints.split(',');
         	let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
         	var duplicateTimepoints = findDuplicates(timePointsArray);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
         	
         	var allTimePoints = timePoints.split(',');
         	$.each($("#tblSkinAdhesion tbody tr"),function(index,ele){
         		if($(ele).find("td:eq(0)").text() === $("#SkinAdhesionTreatment").val() ||  $("#SkinAdhesionTreatment").val() === "0"){
         			allTimePoints.push.apply(allTimePoints, $(ele).find("td:eq(2)").text().split(','));
         		}
         	});
         	
         	duplicateTimepoints = findDuplicates(allTimePoints);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
        	
        	if($("#tblSkinAdhesion").attr("data-defaultrow").toString() === "1"){
         		$("#tblSkinAdhesion tbody").empty();	
         		$("#tblSkinAdhesion").attr("data-defaultrow", 0);	
         	}
        	
            autoSaveData('TREATMENT',  $("#SkinAdhesionTreatment").val(), 1, ($("#tblSkinAdhesion tbody tr").length + 1), "SkinAdhesion", true, 0, $("#SkinAdhesionTreatment").val());
            autoSaveData('PARAMETERS',  $("#SkinAdhesionParameters").val(), 1, ($("#tblSkinAdhesion tbody tr").length + 1), "SkinAdhesion", true, 1, parameters);
            autoSaveData('TIMEPOINT',  $("#SkinAdhesionTimePoint").val(), 1, ($("#tblSkinAdhesion tbody tr").length + 1), "SkinAdhesion", true, 2, $("#SkinAdhesionTimePoint").val());
            autoSaveData('WINDOWPERIODSIGN',  $("#saWindowperiodSign").val(), 1, ($("#tblSkinAdhesion tbody tr").length + 1), "SkinAdhesion", true, 3, $("#saWindowperiodSign option:selected").text());
            autoSaveData('WINDOWPERIODVALUE',  $("#saWindowperiodValue").val(), 1, ($("#tblSkinAdhesion tbody tr").length + 1), "SkinAdhesion", true, 4, $("#saWindowperiodValue").val());
            autoSaveData('WINDOWPERIODTIME',  $("#saWindowPeriodTime").val(), 1, ($("#tblSkinAdhesion tbody tr").length + 1), "SkinAdhesion", true, 5, $("#saWindowPeriodTime option:selected").text());
            
            $("#tblSkinAdhesion tbody").append("<tr data-subrowno='" +  ($("#tblSkinAdhesion tbody tr").length + 1) + "'><td>" + $("#SkinAdhesionTreatment option:selected").text() + "</td><td>" + parameters + "</td><td>" + $("#SkinAdhesionTimePoint").val() + "</td><td>" + 
            		$("#saWindowperiodSign option:selected").text() + "" + $("#saWindowperiodValue").val() + " " + $("#saWindowPeriodTime option:selected").text() +"</td><td><a class='fa fa-trash delete' title='delete'></a></td></tr>");
            
            $('#SkinAdhesionParameters').multiselect( 'reset' );
            $("#saWindowperiodValue,#saWindowperiodSign,#SkinAdhesionTimePoint,#saWindowPeriodTime").val('');
        });
        
        $("#otherTimepointSpecific").change(function(e){
        	$("#otherTimepoint").val('');
        	if($(this).val() === "YES"){
        		$("#otherTimepoint").prop("disabled", "");
        	}
        	else if($(this).val() === "NO"){
        		$("#otherTimepoint").prop("disabled", "disabled");
        	}
        });
        
        $("#otherTreatmentSpecific").change(function(e){
        	$("#otherTreatment").empty();
	    	$("#otherTreatment").append("<option value=''>Select</option>");
	    	if($(this).val() === "YES"){
	    		var noofTreatments = $("#nooftreatments").val();
	    		for(var t=0;t<noofTreatments;t++){
	    			$("#otherTreatment").append("<option value='" + (t + 1) + "'>Treatment" + (t + 1) + "</option>");
	    		}
	    	}
	    	else if($(this).val() === "NO"){
	    		$("#otherTreatment").append("<option value='0'>Treatment</option>");
	    	}
        });
        
    	$.ajax({
    		url: $("#mainUrl").val() + '/studydesignStaticData/studyStaticFieldValues',
    		type:'GET',
    		success:function(e){
    			var studyDesignData = JSON.parse(e);
    			var data = filterJsonArray(studyDesignData,'fieldName','STUDYDESIGN');
    			studyDesignArray = filterJsonArray(studyDesignData,'fieldName','STUDYDESIGN');
    			
    			$("#studyDesign").empty();
    			$("#studyDesign").append("<option value=''>Select</option>");
    			var i=0;
    			for(i=0;i<data.length;i++){
    				$("#studyDesign").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','MASKING');
    			$("#masking").empty();
    			$("#masking").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#masking").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','DOSAGEFORM');
    			$("#dosageForm").empty();
    			$("#dosageForm").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#dosageForm").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','DOSETYPE');
    			$("#doseType").empty();
    			$("#doseType").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#doseType").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','GENDER');
    			$("#ecGender,#IcGender").empty();
    			$("#ecGender,#IcGender").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#ecGender,#IcGender").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','TREATMENTSPECIFICSAMPLETIMEPOINTS');
    			yesNo = filterJsonArray(studyDesignData,'fieldName','TREATMENTSPECIFICSAMPLETIMEPOINTS');
    			$("#trtmntspcsmpltmpnts,#treatmentwisedose,#samptmpdp,#tmntspctmpt,#teatmentSpecificMealTimepoints,#ecgtTeatmentEiseDose,#buffer,#mealCompletionTimeApplicable,#otherTreatmentSpecific,#otherTimepointSpecific,#timepointSpecificParameterDose,#ddlPrefixZero").empty();
    			$("#trtmntspcsmpltmpnts,#treatmentwisedose,#samptmpdp,#tmntspctmpt,#teatmentSpecificMealTimepoints,#ecgtTeatmentEiseDose,#buffer,#mealCompletionTimeApplicable,#otherTreatmentSpecific,#otherTimepointSpecific,#timepointSpecificParameterDose,#ddlPrefixZero").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#trtmntspcsmpltmpnts,#treatmentwisedose,#samptmpdp,#tmntspctmpt,#teatmentSpecificMealTimepoints,#ecgtTeatmentEiseDose,#buffer,#mealCompletionTimeApplicable,#otherTreatmentSpecific,#otherTimepointSpecific,#timepointSpecificParameterDose,#ddlPrefixZero").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','TIMEPOINTTYPE');
    			$("#restrictionsFor").empty();
    			$("#restrictionsFor").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#restrictionsFor").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','TREATMENTWISENOOFDOSING');
    			$("#tdCentrifugationApplicable").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdCentrifugationApplicable").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='centrifugationApplicable' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#differenceinDosings").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#differenceinDosings").append("<option value='"+ data[i].code +"'>" + data[i].fieldValue + "</option>");
    			}
    			
    			$("#divIsMatrixDifferent").empty();
    			for(i=0;i<data.length;i++){
    				$("#divIsMatrixDifferent").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='isMatrixDifferent' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdOrthostaticPosition").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdOrthostaticPosition").append("<label class='label ml-5'><input type='radio' class='mr-5 reviewElement' name='isOrthostaticPosition' value='"+ data[i].code +"' data-text='" + data[i].fieldValue + "'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#divIsStorageDifferent").empty();
    			for(i=0;i<data.length;i++){
    				$("#divIsStorageDifferent").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='isStorageDifferent' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdEcgApplicable").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdEcgApplicable").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='EcgApplicable' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdEcgTreatmentSpecificTimepoints").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdEcgTreatmentSpecificTimepoints").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='EcgTreatmentSpecificTimepoints' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdSkinAdhesionApplicable").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdSkinAdhesionApplicable").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='SkinAdhesionApplicable' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdSkinSensitivityApplicable").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdSkinSensitivityApplicable").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='SkinSensitivityApplicable' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdSkinAdhesionTreatmentSpecificTimepoints").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdSkinAdhesionTreatmentSpecificTimepoints").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='SkinAdhesionTreatmentSpecificTimepoints' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdSkinSensivityTreatmentSpecificTimepoints").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdSkinSensivityTreatmentSpecificTimepoints").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='SkinSensivityTreatmentSpecificTimepoints' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			$("#tdTreatmentSpecificRestrictionsComplaince").empty();
    			for(i=0;i<data.length;i++){
    				$("#tdTreatmentSpecificRestrictionsComplaince").append("<label class='label ml-5'><input type='radio' class='mr-5 autosave reviewElement' name='TreatmentSpecificRestrictionsComplaince' value='"+ data[i].code +"'/>" + data[i].fieldValue + "</label>");
    			}
    			
    			isControlDataLoaded = true;
    			
    			windowPeriodSign = filterJsonArray(studyDesignData,'fieldName','WINDOWPERIODSIGN');
    			windowPeriodDuration = filterJsonArray(studyDesignData,'fieldName','WINDOWPERIODTYPE');
    			
    			$("#windowperiod,#vitalsWindowPeriodSign,#mealWindowPeriodSign,#saWindowperiodSign,#ssWindowperiodSign,#ecgSign").empty();
    			$("#windowperiod,#vitalsWindowPeriodSign,#mealWindowPeriodSign,#saWindowperiodSign,#ssWindowperiodSign,#ecgSign").append("<option value=''>Select</option>");
    			for(i=0;i<windowPeriodSign.length;i++){
    				$("#windowperiod,#vitalsWindowPeriodSign,#mealWindowPeriodSign,#saWindowperiodSign,#ssWindowperiodSign,#ecgSign").append("<option value='"+ windowPeriodSign[i].code +"'>"+ windowPeriodSign[i].fieldValue +"</option>");
    			}
    			
    			$("#windowPeriodTime,#vitalsWindowPeriodTime,#saWindowPeriodTime,#ssWindowPeriodTime,#ecgWindowPeriodTime").empty();
    			$("#windowPeriodTime,#vitalsWindowPeriodTime,#saWindowPeriodTime,#ssWindowPeriodTime,#ecgWindowPeriodTime").append("<option value=''>Select</option>");
    			for(i=0;i<windowPeriodDuration.length;i++){
    				$("#windowPeriodTime,#vitalsWindowPeriodTime,#saWindowPeriodTime,#ssWindowPeriodTime,#ecgWindowPeriodTime").append("<option value='"+ windowPeriodDuration[i].code +"'>"+ windowPeriodDuration[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','VITALPOSITION');
    			$("#vitalsPosition,#othostaticPosition,#EcgPosition").empty();
    			$("#vitalsPosition,#othostaticPosition,#EcgPosition").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#vitalsPosition,#othostaticPosition,#EcgPosition").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			data = filterJsonArray(studyDesignData,'fieldName','MEALSTTYPE');
    			$("#MealsType").empty();
    			$("#MealsType").append("<option value=''>Select</option>");
    			for(i=0;i<data.length;i++){
    				$("#MealsType").append("<option value='"+ data[i].code +"'>"+ data[i].fieldValue +"</option>");
    			}
    			
    			$.ajax({
	        		url: $("#mainUrl").val() + '/condition/getconditions',
	        		type:'GET',
	        		success:function(conditions){
	        			for(i=0;i<conditions.length;i++){
	        				if(conditions[i].sujectWithdrawActivity.dropDown === "storage"){
	        					storageConditions.push({ 'name': conditions[i].sujectWithdrawActivity.name,'id': conditions[i].sujectWithdrawActivity.id });
	        				}
	        				else if(conditions[i].sujectWithdrawActivity.dropDown === "processing"){
	        					$("#divProcessingConditions").append("<label class='label ml-5'><input class='pro_autosave' name='pro_" + conditions[i].sujectWithdrawActivity.id + "' type='checkbox' value='"+ conditions[i].sujectWithdrawActivity.id +"'/>" + conditions[i].sujectWithdrawActivity.name + "</label>");
	        				}
	        				else if(conditions[i].sujectWithdrawActivity.dropDown === "centrifugation"){
	        					$("#divCentrifugationConditions").append("<label class='label ml-5'><input class='cen_autosave' name='pro_" + conditions[i].sujectWithdrawActivity.id + "' type='checkbox' value='"+ conditions[i].sujectWithdrawActivity.id +"'/>" + conditions[i].sujectWithdrawActivity.name + "</label>");
	        				}
	        				else if(conditions[i].sujectWithdrawActivity.dropDown === "matrix"){
	        					matrix.push({ 'name': conditions[i].sujectWithdrawActivity.name,'id': conditions[i].sujectWithdrawActivity.id });
	        				}
	        			}
	        			 
	        			loadFormData = true;
	        			
	        		},
	        		error:function(er){
	        			debugger;
	        		}
	        	});
    		}
    	});
    	
    	$("#btnSubmit").click(function(e){
    		if(validation.validate($(this).closest("form")[0])){
	    		validateDosingParameters();
	    	}
    	});
    	
    	$("#btnProjectDesignBack").click(function(e){
    		$.confirm({
			    title: 'Confirm!',
			    content: 'Do you wish to go back?',
			    buttons: {
			        Yes: function () {
			        	$(".loader").show();
			        	$("#frmData").attr('action',$("#mainUrl").val() + "/studydesign/studyDesignPage");
             			$("#frmData").attr('method','POST');
             			$("#frmData").submit();	
			        },
			        No: function () {
			        	
			        }
			    }
			});
    	});
    	
    	$("#btnSendComments").click(function(e){
    		var isValid = false;
    		for(var c = 0;c < comments.length; c++){
    			if($.trim(comments[c]["comment"]).length > 0 && $.trim(comments[c]["response"]).length === 0 ){
    				isValid = true;break;
    			}
    		}
    		
    		if(!isValid){
    			displayMessage('warning', 'Add comments to send comments');
    			return;
    		}
    		    		
    		$(".loader").show();
    		var formData =$('#frmData').serializeArray();
    		formData.push({name: "projectId",value: $("input[name='projectId']").val()});
            formData.push({name: "actionName",value: "SUBMIT"});
            
            $.ajax({
             	url:$("#mainUrl").val() + '/studydesign/sendComments',
             	type:'POST',
             	data: formData,
             	success:function(e){
             		$(".loader").hide();
             		if(e.success || e.success === 'true'){
             			$("#frmData").attr('action', $("#mainUrl").val() + "/studydesign/projectApprovalList");
             			$("#frmData").attr('method', "POST");
             			$("#frmData").submit();	
             			displayMessage('success', e.message);
             		}
             		else{
             			if(e.message !== undefined){
             				displayMessage('error', e.message);
                 		}
                 	}
             	},
             	error:function(er){
             		displayMessage('error', er);
             	}
             });
    	});
    	
    	$(document).on("change","[name='randomizationCode']",function(e){
    		var element = $(this);var isValid=true;
    		$.each($("[name='randomizationCode']"),function(index,ele){
    			if($(element).attr("data-subrownumber") !== $(ele).attr("data-subrownumber")){
    				if($(ele).val() === $(element).val()){
    					displayMessage('info', 'Randomization code already exists');
    					$(element).val("");isValid=false;
    					return false;
    				}
    			}
    		});
    		
    		if(isValid){
    			autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(), $(this).closest("tr").find("#trtmntNo").val());
    		}
    	});
    	
    	$(document).on("change","[name='treatmentName']",function(e){
    		var element = $(this);var isValid=true;
    		$.each($("[name='treatmentName']"),function(index,ele){
    			if($(element).attr("data-subrownumber") !== $(ele).attr("data-subrownumber")){
    				if($(ele).val() === $(element).val()){
    					displayMessage('info', 'Product name already exists');
    					$(element).val("");isValid=false;
    					return false;
    				}
    			}
    		});
    		
    		if(isValid){
    			autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(), $(this).closest("tr").find("#trtmntNo").val());
    		}
    	});
    	
    	$("#btnAcceptComments").click(function(e){
    		var isValid = false;
    		debugger;
    		for(var c = 0;c < comments.length; c++){
    			if($.trim(comments[c]["comment"]).length > 0 && $.trim(comments[c]["response"]).length === 0 ){
    				isValid = true;break;
    			}
    		}
    		
    		if(isValid){
    			displayMessage('warning', 'Project can not be accepted as the comments are added');
    			return;
    		}
    		
    		$(".loader").show();
    		var formData =$('#frmData').serializeArray();
    		formData.push({name: "projectId",value: $("input[name='projectId']").val()});
            formData.push({name: "actionName",value: "ACCEPT"});
            formData.push({name: "projectsDetailsId",value: "0"});
            formData.push({name: "roleid",value: "0"});
    		$.ajax({
             	url:$("#mainUrl").val() + '/studydesign/approvalDraft',
             	type:'POST',
             	data: formData,
             	success:function(e){
             		$(".loader").hide();
             		if(e.Success){
             			var f = document.createElement("form");
             			f.setAttribute('method',"get");
             			f.setAttribute('action', $("#mainUrl").val() + "/studydesign/projectApprovalList");
             			$("body").append(f);
             			f.submit();	
             			displayMessage('success', e.Message);
             		}
             		else{
             			if(e.Message !== undefined){
             				displayMessage('error', e.Message);
                 		}
                 	}
             	},
             	error:function(er){
             		displayMessage('error', er.Message);
             	}
             });
    	});
    	
    	$("#isThereanyDifferenceInTheNoOfDosings").hide();

        $("#btnDosingParametersSubmit").click(function (e) {
        	e.preventDefault();
        	isValid = true;
        	if($("#dosingTreatmentvalue").val() === ""){
        		$("#dosingTreatmentvalue").addClass('validate validate-error');
        		$("#dosingTreatmentvalue").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#parameterdesc").val()).length === 0){
        		$("#parameterdesc").addClass('validate validate-error');
        		$("#parameterdesc").attr('data-validate','required');
                return;
	   		}
        	
        	/* Uncommet below code study design requires timepoint specific dose parameters */
        	/* if($("#timepointSpecificParameterDose").val() === "YES" &&
        			$.trim($("#dosingTimepointParameters").val()).length === 0){
        		$("#dosingTimepointParameters").addClass('validate validate-error');
        		$("#dosingTimepointParameters").attr('data-validate','required');
                return;
	   		}*/
        	
        	if(!isValid){
        		return;
	   		}
        	        	
        	if($("#dosingTreatmentvalue").val())
        	
        	if($("#dosingParameterList").attr("data-defaultrow").toString() === "1"){
        		$("#dosingParameterList tbody").empty();	
        		$("#dosingParameterList").attr("data-defaultrow", "0");
        	}
        	
        	var doseTreatmentValue = $("#dosingTreatmentvalue").val();
        	var doseTimepointValue = $("#dosingTimepointParameters").val();
        	var doseParamter = $("#parameterdesc").val();
        	var treatment = "";
        	var timepoint = "";
        	var parameter = "";
        	var isAlreadyExists = false;
        	$.each($("#dosingParameterList tbody tr"),function(index,tr){
        		treatment = $(tr).find("td:eq(0)").attr("data-treat");
        		//Uncomment below line for timepoint specific dosing parameters
        		//timepoint = $(tr).find("td:eq(1)").attr("data-tp");
        		//parameter = $(tr).find("td:eq(2)").attr("data-param");
        		
        		//Remove below line for timepoint specific dosing parameters
        		parameter = $(tr).find("td:eq(1)").attr("data-param");
        		if(treatment === doseTreatmentValue 
        				//Uncomment below line for timepoint specific dosing parameters
        				//&& doseTimepointValue === timepoint 
        				&& parameter === doseParamter){
        			isAlreadyExists = true;
        			return false;
        		}
        	});
        	
        	if(isAlreadyExists){
        		displayMessage('info', 'Dosing parameter already added');
        		return;
        	}
        	
            //Check for Row Number
            var treatmentNumber = 1;
            if(parseInt( $("#dosingTreatmentvalue").val()) > 0 ){
            	treatmentNumber = parseInt($("#dosingTreatmentvalue").val());
            }

            autoSaveData('TREATMENT', $("#dosingTreatmentvalue").val(), ($("#dosingParameterList tbody tr").length + 1), ($("#dosingParameterList tbody tr").length + 1), "DosingParameters", true, 0, treatmentNumber,$("#dosingTreatmentvalue").val());
            
            // Uncomment this if timepoint wise dosing paramters
            //autoSaveData('TIMEPOINT', $("#dosingTimepointParameters").val(), ($("#dosingParameterList tbody tr").length + 1), ($("#dosingParameterList tbody tr").length + 1), "DosingParameters", true, 1, $("#dosingTimepointParameters").val(),treatmentNumber);
            autoSaveData('PARAMETERDESC', $("#parameterdesc").val(), ($("#dosingParameterList tbody tr").length + 1), ($("#dosingParameterList tbody tr").length + 1), "DosingParameters", true, 2, $("#parameterdesc option:selected").text(),$("#dosingTreatmentvalue").val());
            
            $("#dosingParameterList tbody").append("<tr data-subrowno='" + ($("#dosingParameterList tbody tr").length + 1) 
            		+ "'><td data-treat='" + doseTreatmentValue + "'>" + treatmentNumber + "</td>" 
            
            		// Uncomment this if timepoint wise dosing paramters
            		//+ "<td data-tp='" + $("#dosingTimepointParameters").val() + "'>" + $("#dosingTimepointParameters").val() + "</td>" 
            		+ "<td data-param='" + $("#parameterdesc").val() + "'>" + $("#parameterdesc option:selected").text() + "</td><td><a class='fa fa-trash delete' title='Delete'></a></td></tr >");
            
            $("#dosingTreatmentvalue,#parameterdesc").removeClass('validate-error');
            $("#parameterdesc").val('');
            if($("#dosingTreatmentvalue").val() !== "0"){
            	$("#dosingTreatmentvalue").val('');	
            }
        });

        $(document).on("click", "#btnSampleInformation", function (e) {
        	e.preventDefault();
        	if($("[name='projectReview']").val().toString().toLowerCase() === "true"){
        		
        	}
        	
        	isValid = true;
        	if($("#sampleTreatmentValue_1").val() === ""){
        		$("#sampleTreatmentValue_1").addClass('validate validate-error');
        		$("#sampleTreatmentValue_1").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#bloodvolume").val()).length === 0){
        		$("#bloodvolume").addClass('validate validate-error');
        		$("#bloodvolume").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#typeofvacutainerused").val()).length === 0){
        		$("#typeofvacutainerused").addClass('validate validate-error');
        		$("#typeofvacutainerused").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#buffer").val()).length === 0){
        		$("#buffer").addClass('validate validate-error');
        		$("#buffer").attr('data-validate','required');
                return;
	   		}
        	        	
        	if(!isValid){
        		return;
	   		}
        	
        	var treatmentNumber = 1;
            if(parseInt( $("#sampleTreatmentValue_1").val()) > 0 ){
            	treatmentNumber = parseInt($("#sampleTreatmentValue_1").val());
            }
            
            isValid = true;
            
        	$.each($("#tsplconditionwisedata tbody tr"),function(index,tr){
        		if(treatmentNumber.toString() === $(tr).find("td:eq(0)").text().toString() 
        				&& $("#splcondition option:selected").text().toString() === $(tr).find("td:eq(1)").text().toString()
        				&& $("#bloodvolume").val().toString() === $(tr).find("td:eq(2)").text().toString()
        				&& $("#typeofvacutainerused").val().toString() === $(tr).find("td:eq(3)").text().toString()
        				&& $("#buffer option:selected").text().toString() === $(tr).find("td:eq(4)").text().toString()){
        			isValid = false;
        			return false;
        		}
        	});
        	
        	if(!isValid){
        		displayMessage('info', "Duplicate record found. Please check.");    
        		return;
	   		}
        	
        	if($("#tsplconditionwisedata").attr("data-defaultrow").toString() === "1"){
        		$("#tsplconditionwisedata tbody").empty();	
        		$("#tsplconditionwisedata").attr("data-defaultrow", 0);	
        	}
        	
        	var subRowNumber = $("#tsplconditionwisedata").attr("data-srnumber").toString();
            subRowNumber = parseInt(subRowNumber) + 1;
            $("#tsplconditionwisedata").attr("data-srnumber", subRowNumber);
        	
            var data = {
                sampleTreatmentValue_1: $("#sampleTreatmentValue_1").val(),
                splcondition: $("#splcondition").val(), bloodvolume: $("#bloodvolume").val(),
                typeofvacutainerused: $("#typeofvacutainerused").val(), buffer: $("#buffer option:selected").text(),
                noofvialsss: $("#noofvialsss").val()
            };

            autoSaveData('TREATMENT',  $("#sampleTreatmentValue_1").val(), 1, subRowNumber, "sampleInformation", true, 0, treatmentNumber,$("#sampleTreatmentValue_1").val());
            autoSaveData('LIGHTCONDITION', data.splcondition, 1, subRowNumber, "sampleInformation", true, 2, $("#splcondition option:selected").text(),$("#sampleTreatmentValue_1").val());
            autoSaveData('VOLUMNOFBLOOD', data.bloodvolume, 1, subRowNumber, "sampleInformation", true, 3, $("#bloodvolume").val(),$("#sampleTreatmentValue_1").val());
            autoSaveData('TYPEOFVACUTAINERUSED', data.typeofvacutainerused, 1, subRowNumber, "sampleInformation", true, 4,$("#typeofvacutainerused").val(),$("#sampleTreatmentValue_1").val());
            autoSaveData('BUFFER', data.buffer, 1, subRowNumber, "sampleInformation", true, 5, $("#buffer option:selected").text(),$("#sampleTreatmentValue_1").val());
            
            
            $("#tsplconditionwisedata tbody").append("<tr><td>" + treatmentNumber + "</td><td>" + data.splcondition + "</td><td>" + data.bloodvolume + "</td><td>" + data.typeofvacutainerused + "</td><td>" + data.buffer + "</td><td><a class='fa fa-trash delete' title='delete'></a></td></tr>");
            $("#bloodvolume").val(' '); $("#typeofvacutainerused").val(' '); $("#buffer").val(' ');
            $("#bloodvolume,#typeofvacutainerused,#buffer").removeClass('validate validate-error');
            $("#bloodvolume,#typeofvacutainerused,#buffer").attr('data-validate','');
        });


        $(document).on("click", "#sampletimepointsbtn", function (e) {
        	e.preventDefault();
        	isValid = true;
        	if($("#sampleTreatmentValue_2").val() === ""){
        		$("#sampleTreatmentValue_2").addClass('validate validate-error');
        		$("#sampleTreatmentValue_2").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#timepoints").val()).length === 0){
        		$("#timepoints").addClass('validate validate-error');
        		$("#timepoints").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#noofvacutainers").val()).length === 0){
        		$("#noofvacutainers").addClass('validate validate-error');
        		$("#noofvacutainers").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#windowperiod").val()).length === 0){
        		$("#windowperiod").addClass('validate validate-error');
        		$("#windowperiod").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#windowperiod_value").val()).length === 0){
        		$("#windowperiod_value").addClass('validate validate-error');
        		$("#windowperiod_value").attr('data-validate','required');
                return;
	   		}
        	

        	if($.trim($("#noofvialsss").val()).length === 0){
        		$("#noofvialsss").addClass('validate validate-error');
        		$("#noofvialsss").attr('data-validate','required');
                return;
	   		}
        	
        	isValid = checkTimepoint(null, $("#timepoints"));
        	
        	if(!isValid){
        		return;
	   		}
        	
        	
        	
        	var timePoints = $("#timepoints").val();
        	var timePointsArray = timePoints.split(',');
        	let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
        	var duplicateTimepoints = findDuplicates(timePointsArray);
        	if(duplicateTimepoints.length > 0){
        		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
        		return;
        	}
        	
        	var allTimePoints = timePoints.split(',');
        	
        	$.each($("#sampletimepointTreatmentslisttable tbody tr"),function(index,ele){
        		if($(ele).find("td:eq(0)").text() === $("#sampleTreatmentValue_2").val() ||  $("#sampleTreatmentValue_2").val() === "0"){
        			allTimePoints.push.apply(allTimePoints, $(ele).find("td:eq(1)").text().split(','));
        		}
        	});
        	
        	duplicateTimepoints = findDuplicates(allTimePoints);
        	if(duplicateTimepoints.length > 0){
        		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
        		return;
        	}
        	    
        	if($("#sampletimepointTreatmentslisttable").attr("data-defaultrow").toString() === "1"){
        		$("#sampletimepointTreatmentslisttable tbody").empty();	
        		$("#sampletimepointTreatmentslisttable").attr("data-defaultrow", "0");	
        	}
        	
        	var treatmentNumber = 1;
            if(parseInt( $("#sampleTreatmentValue_2").val()) > 0 ){
            	treatmentNumber = parseInt($("#sampleTreatmentValue_2").val());
            }
        	
            var subRowNumber = $("#sampletimepointTreatmentslisttable").attr("data-srnumber").toString();
            subRowNumber = parseInt(subRowNumber) + 1;
            $("#sampletimepointTreatmentslisttable").attr("data-srnumber", subRowNumber);
            
            var data = { sampleTreatmentValue_2: $("#sampleTreatmentValue_2").val(), 
            		Timepoints: $("#timepoints").val(), NoofVacutainers: $("#noofvacutainers").val(), 
            		WindowPeriod: $("#windowperiod").val(), windowperiod_value: $("#windowperiod_value").val() };
            
            autoSaveData('TREATMENT', $("#sampleTreatmentValue_2").val(), 1, subRowNumber, "sampleTimePoins",true,0,treatmentNumber,$("#sampleTreatmentValue_2").val());
            autoSaveData('TIMEPOINT', $("#timepoints").val(), 1, subRowNumber, "sampleTimePoins",true,1, $("#timepoints").val(),$("#sampleTreatmentValue_2").val().toString().replace(/,/g,', '));
            autoSaveData('NOOFVACUTAINERS', data.NoofVacutainers, 1, subRowNumber, "sampleTimePoins",true, 2, data.NoofVacutainers,$("#sampleTreatmentValue_2").val());
            autoSaveData('WINDOWPERIODSIGN', data.WindowPeriod, 1, subRowNumber, "sampleTimePoins",true,3, $("#windowperiod option:selected").text(),$("#sampleTreatmentValue_2").val());
            autoSaveData('WINDOWPERIOD', data.windowperiod_value, 1, subRowNumber, "sampleTimePoins",true,4, data.windowperiod_value,$("#sampleTreatmentValue_2").val());
            autoSaveData('WINDOWPERIODTYPE', $("#windowPeriodTime").val(), 1, subRowNumber, "sampleTimePoins",true,5, $("#windowPeriodTime option:selected").text(),$("#sampleTreatmentValue_2").val());
            autoSaveData('NOOFVAILSFORSAMPLESEPARATION', $("#noofvialsss").val(), 1, subRowNumber, "sampleTimePoins", true, 6, $("#noofvialsss").val(),$("#sampleTreatmentValue_2").val());
            
            $("#sampletimepointTreatmentslisttable tbody").append("<tr data-rowno='" + subRowNumber + "' data-subrowno='" + subRowNumber + "'><td>" + treatmentNumber + "</td><td>" + $("#timepoints").val().toString().replace(/,/g,', ') + "</td><td>" + data.NoofVacutainers + "</td><td>" + $("#windowperiod option:selected").text() + " " + data.windowperiod_value + " " + $("#windowPeriodTime option:selected").text() + "</td><td>" + $("#noofvialsss").val() +  "</td><td><a  class='fa fa-trash delete' title='Delete'></a></td></tr>");
            $("#timepoints,#noofvacutainers,#windowperiod_value,#noofvialsss").val('');
            
            $("#timepoints,#noofvacutainers,#windowperiod_value,#noofvialsss").removeClass('validate validate-error');
            $("#timepoints,#noofvacutainers,#windowperiod_value,#noofvialsss").attr('data-validate','');
        });


        $(document).on("click", "#vitalsPreAndPostBtn", function (e) {
        	e.preventDefault();
        	isValid = true;
            if($("#tmntspctmpt").val() === ""){
        		$("#tmntspctmpt").addClass('validate validate-error');
        		$("#tmntspctmpt").attr('data-validate','required');
        		isValid = false;
	   		}
            
            if($("#vitalTreatment").val() === ""){
        		$("#vitalTreatment").addClass('validate validate-error');
        		$("#vitalTreatment").attr('data-validate','required');
        		isValid = false;
	   		}
                        
            if(!isValid){
        		return;
	   		}
        	
        	if($("#vitalsPosition").val() === ""){
        		$("#vitalsPosition").addClass('validate validate-error');
        		$("#vitalsPosition").attr('data-validate','required');
        		isValid = false;
	   		}
        	
        	if($.trim($("#vitalsTimepoints").val()).length === 0){
        		$("#vitalsTimepoints").addClass('validate validate-error');
        		$("#vitalsTimepoints").attr('data-validate','required');
        		isValid = false;
	   		}
        	
        	if($.trim($("#vitalsWindowPeriodSign").val()).length === 0){
        		$("#vitalsWindowPeriodSign").addClass('validate validate-error');
        		$("#vitalsWindowPeriodSign").attr('data-validate','required');
        		isValid = false;
	   		}
        	
        	if($.trim($("#vitalsWindowPeriod").val()).length === 0){
        		$("#vitalsWindowPeriod").addClass('validate validate-error');
        		$("#vitalsWindowPeriod").attr('data-validate','required');
        		isValid = false;
	   		}
        	
        	if($.trim($("#vitalsParameters").val()).length === 0){
        		$("#vitalsParameters").addClass('validate validate-error');
        		$("#vitalsParameters").attr('data-validate','required');
        		isValid = false;
	   		}
        	
        	if($("[name='isOrthostaticPosition']:checked").val() === undefined || $("[name='isortho']:checked").val() === null){
        		$("[name='isOrthostaticPosition']").addClass('validate validate-error');
        		$("[name='isOrthostaticPosition']").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	isValid = checkTimepoint(null, $("#vitalsTimepoints"));
        	
        	if(!isValid){
        		return;
	   		}
        	
            if($("[name='isOrthostaticPosition']:checked").val() === "YES" && $("#othostaticPosition").val() === ""){
            	$("#othostaticPosition").addClass('validate validate-error');
        		$("#othostaticPosition").attr('data-validate','required');
                return;
    		}
            
            var timePoints = $("#vitalsTimepoints").val();
        	var timePointsArray = timePoints.split(',');
        	let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
        	var duplicateTimepoints = findDuplicates(timePointsArray);
        	if(duplicateTimepoints.length > 0){
        		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
        		return;
        	}
        	
        	var allTimePoints = timePoints.split(',');
        	$.each($("#preAndPostTable tbody tr"),function(index,ele){
        		if($(ele).find("td:eq(0)").text() === $("#vitalTreatment").val() ||  $("#vitalTreatment").val() === "0"){
        			allTimePoints.push.apply(allTimePoints, $(ele).find("td:eq(2)").text().split(','));
        		}
        	});
        	
        	duplicateTimepoints = findDuplicates(allTimePoints);
        	if(duplicateTimepoints.length > 0){
        		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
        		return;
        	}
            
            
        	if($("#preAndPostTable").attr("data-defaultrow").toString() === "1"){
        		$("#preAndPostTable tbody").empty();	
        		$("#preAndPostTable").attr("data-defaultrow", 0);	
        	}
        	
        	var treatmentNumber = 1;
            if(parseInt( $("#vitalTreatment").val()) > 0 ){
            	treatmentNumber = parseInt($("#vitalTreatment").val());
            }
            
            var subRowNumber = $("#preAndPostTable").attr("data-srnumber").toString();
            subRowNumber = parseInt(subRowNumber) + 1;
            $("#preAndPostTable").attr("data-srnumber", subRowNumber);
            
            var parameters = $('#vitalsParameters option:selected').toArray().map(item => item.text).join();
        	
            autoSaveData('TREATMENT', $("#vitalTreatment").val(), 1, subRowNumber, "vitalTimepointInformation",true,1,treatmentNumber, $("#vitalTreatment").val());
            autoSaveData('VITALPOSITION', $("#vitalsPosition").val(), 1, subRowNumber, "vitalTimepointInformation",true,3,$("#vitalsPosition option:selected").text(), $("#vitalTreatment").val());
            autoSaveData('TIMEPOINT', $("#vitalsTimepoints").val(), 1, subRowNumber, "vitalTimepointInformation",true,4,$("#vitalsTimepoints").val(), $("#vitalTreatment").val());
            autoSaveData('PARAMETER', $("#vitalsParameters").val(), 1, subRowNumber, "vitalTimepointInformation",true,5,parameters, $("#vitalTreatment").val());
            /*autoSaveData('WINDOWPERIODTYPE',  $("#vitalsWindowPeriodSign").val(), 1, ($("#preAndPostTable tbody tr").length + 1), "vitalTimepointInformation",true,6, $("#vitalsWindowPeriodSign option:selected").text());
            autoSaveData('WINDOWPERIOD',  $("#vitalsWindowPeriod").val(), 1, ($("#preAndPostTable tbody tr").length + 1), "vitalTimepointInformation",true,7,  $("#vitalsWindowPeriod").val());
            autoSaveData('WINDOWPERIODVALUE',  $("#vitalsWindowPeriodTime").val(), 1, ($("#preAndPostTable tbody tr").length + 1), "vitalTimepointInformation",true,8,  $("#vitalsWindowPeriodTime option:selected").text());*/
            autoSaveData('WINDOWPERIODSIGN',  $("#vitalsWindowPeriodSign").val(), 1, subRowNumber, "vitalTimepointInformation",true,6, $("#vitalsWindowPeriodSign option:selected").text(), $("#vitalTreatment").val());
            autoSaveData('WINDOWPERIOD',  $("#vitalsWindowPeriod").val(), 1, subRowNumber, "vitalTimepointInformation",true,7,  $("#vitalsWindowPeriod").val(), $("#vitalTreatment").val());
            autoSaveData('WINDOWPERIODTYPE',  $("#vitalsWindowPeriodTime").val(), 1, subRowNumber, "vitalTimepointInformation",true,8,  $("#vitalsWindowPeriodTime option:selected").text(), $("#vitalTreatment").val());
            
            autoSaveData('ORTHOSTATIC',  $("[name='isOrthostaticPosition']:checked").val(), 1, subRowNumber, "vitalTimepointInformation",true,9,  $("[name='isOrthostaticPosition']:checked").data("text"), $("#vitalTreatment").val());

            var orthostaticposition =  "";
            if($("#othostaticPosition").val() !== ""){
            	orthostaticposition = $("#othostaticPosition option:selected").text();
            }
            autoSaveData('TESTNAMES', orthostaticposition, 1, ($("#preAndPostTable tbody tr").length + 1), "vitalTimepointInformation",true,10, orthostaticposition, $("#vitalTreatment").val());
            
            $("#preAndPostTable tbody").append("<tr data-rowno='" + subRowNumber + "' data-subrowno='" + subRowNumber + "'><td>" 
            			+ treatmentNumber + "</td><td> " + $("#vitalsPosition option:selected").text() + "</td><td> " + $("#vitalsTimepoints").val() 
            			+ "</td><td> " + parameters
            			+ "</td><td> " + $("#vitalsWindowPeriodSign option:selected").text() + " " + $("#vitalsWindowPeriod").val()
            			+ " " + $("#vitalsWindowPeriodTime option:selected").text()
            			+ "</td><td> " + $("[name='isOrthostaticPosition']:checked").data("text") + "</td><td> " + orthostaticposition
            			+ "</td><td><a class='fa fa-trash delete' title='Delete'></a></td></tr>");
            $("[name='isOrthostaticPosition']").prop('checked', false);
            $('#vitalsParameters').multiselect( 'reset' );
            $("#vitalsPosition,#vitalsTimepoints,#vitalsParameters,#vitalsWindowPeriodSign,#vitalsWindowPeriod,#vitalsWindowPeriodTime,#othostaticPosition").val('');
            $("[name='isOrthostaticPosition'],#vitalsPosition,#vitalsTimepoints,#vitalsParameters,#vitalsWindowPeriodSign,#vitalsWindowPeriod,#vitalsWindowPeriodTime,#othostaticPosition").removeClass('validate validate-error');
            $("[name='isOrthostaticPosition'],#vitalsPosition,#vitalsTimepoints,#vitalsParameters,#vitalsWindowPeriodSign,#vitalsWindowPeriod,#vitalsWindowPeriodTime,#othostaticPosition").attr('data-validate','');
        });


        $(document).on("click", "#testparBtn", function (e) {
            $("#testParameterRangesTabletr").empty();
            var data = { testName: $("#testName").val(), rangeFrom: $("#rangeFrom").val(), toRange: $("#toRange").val() };
            autoSaveData('TESTNAMES', data.testName, 0, ($("#testParameterRangesTable tbody tr").length + 1), "vitalTimepointInformation", data.testName);
            autoSaveData('SAMPLETIMPOINTSTREATMENTSPECIFIC', data.rangeFrom, 0, ($("#testParameterRangesTable tbody tr").length + 1), "vitalTimepointInformation", data.rangeFrom);
            autoSaveData('SAMPLETIMPOINTSTREATMENTSPECIFIC', data.toRange, 0, ($("#testParameterRangesTable tbody tr").length + 1), "vitalTimepointInformation", data.toRange);
            $("#testParameterRangesTable tbody").append("<tr><td>" + data.testName + "</td><td>" + data.rangeFrom + " to " + data.toRange + "</td><td><a href='#'><span class='glyphicon glyphicon-trash' title='Delete'></span></a></td></tr >");

            $("#rangeFrom").val(' '); $("#toRange").val(' ');

        });

        $("#mealsTimepointbtnadd").click(function (e) {
        	e.preventDefault();
        	isValid = true;
        	if($("#teatmentSpecificMealTimepoints").val() === ""){
        		$("#teatmentSpecificMealTimepoints").addClass('validate validate-error');
        		$("#teatmentSpecificMealTimepoints").attr('data-validate','required');
                return;
	   		}
        	
        	if($.trim($("#mTPTreatmentvalue").val()).length === 0){
        		$("#mTPTreatmentvalue").addClass('validate validate-error');
        		$("#mTPTreatmentvalue").attr('data-validate','required');
                return;
	   		}
        	        	
        	if($("#mealCompletionTimeApplicable").val() === 'YES' && $.trim($("#mealCompletionTimeValue").val()).length === 0){
        		$("#mealCompletionTimeValue").addClass('validate validate-error');
        		$("#mealCompletionTimeValue").attr('data-validate','required');
                return;
        	}
        	
        	if($.trim($("#mealTimePoint").val()).length === 0){
        		$("#mealTimePoint").addClass('validate validate-error');
        		$("#mealTimePoint").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#MealsType").val() === ""){
        		$("#MealsType").addClass('validate validate-error');
        		$("#MealsType").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#mealWindowPeriodSign").val() === ""){
        		$("#mealWindowPeriodSign").addClass('validate validate-error');
        		$("#mealWindowPeriodSign").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($.trim($("#mealWindowPeriodValue").val()).length === 0){
        		$("#mealWindowPeriodValue").addClass('validate validate-error');
        		$("#mealWindowPeriodValue").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#mealCompletionTimeApplicable").val() === ""){
        		$("#mealCompletionTimeApplicable").addClass('validate validate-error');
        		$("#mealCompletionTimeApplicable").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	isValid = checkTimepoint(null, $("#mealTimePoint"));
        	
        	if(!isValid){
        		return;
	   		}
        	

        	var timePoints = $("#mealTimePoint").val();
        	var timePointsArray = timePoints.split(',');
        	let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
        	var duplicateTimepoints = findDuplicates(timePointsArray);
        	if(duplicateTimepoints.length > 0){
        		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
        		return;
        	}
        	
        	var allTimePoints = timePoints.split(',');
        	$.each($("#Mealsinfo tbody tr"),function(index,ele){
        		if($(ele).find("td:eq(0)").text() === $("#mTPTreatmentvalue").val() ||  $("#mTPTreatmentvalue").val() === "0"){
        			allTimePoints.push.apply(allTimePoints, $(ele).find("td:eq(1)").text().split(','));
        		}
        	});
        	        	
        	duplicateTimepoints = findDuplicates(allTimePoints);
        	if(duplicateTimepoints.length > 0){
        		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
        		return;
        	}
        	
        	var treatmentNumber = 1;
            if(parseInt( $("#mTPTreatmentvalue").val()) > 0 ){
            	treatmentNumber = parseInt($("#mTPTreatmentvalue").val());
            }
	       	
	       	if($("#Mealsinfo").attr("data-defaultrow").toString() === "1"){
	       		$("#Mealsinfo tbody").empty();	
	       		$("#Mealsinfo").attr("data-defaultrow", 0);	
	       	}
	       	
            var data = { TreatmentValue: $('#mTPTreatmentvalue').val(), 
            		TimePoint: $('#mealTimePoint').val(), 
            		MealsType: $('#MealsType').val(), 
            		WindowPeriod: $('#mealWindowPeriodSign').val(), 
            		windowPeriod_Value: $("#mealWindowPeriodValue").val(), 
            		CompletionTime: $('#mealCompletionTimeApplicable').val(), 
            		completionTime_Value: $("#mealCompletionTimeValue").val() }
            
            var subRowNumber = $("#Mealsinfo").attr("data-srnumber").toString();
            subRowNumber = parseInt(subRowNumber) + 1;
            
            autoSaveData('TREATMENT', $("#mTPTreatmentvalue").val(), 1, subRowNumber, "mealsTimePointInformation",true, 0, treatmentNumber, $("#mTPTreatmentvalue").val());
            autoSaveData('TIMEPOINT', data.TimePoint, 1, subRowNumber, "mealsTimePointInformation",true, 3,$('#mealTimePoint').val(), $("#mTPTreatmentvalue").val());
            autoSaveData('MEALSTTYPE', data.MealsType, 1, subRowNumber, "mealsTimePointInformation",true, 4,$('#MealsType option:selected').text(), $("#mTPTreatmentvalue").val());
            
            autoSaveData('WINDOWPERIODSIGN', data.WindowPeriod, 1, subRowNumber, "mealsTimePointInformation",true, 5,$('#mealWindowPeriodSign option:selected').text(), $("#mTPTreatmentvalue").val());
            autoSaveData('WINDOWPERIOD', data.windowPeriod_Value, 1, subRowNumber, "mealsTimePointInformation",true, 6,$("#mealWindowPeriodValue").val(), $("#mTPTreatmentvalue").val());
            autoSaveData('COMPLETIONTYPE', data.CompletionTime, 1, subRowNumber, "mealsTimePointInformation",true, 7,$("#mealCompletionTimeApplicable option:selected").text(), $("#mTPTreatmentvalue").val());
            autoSaveData('COMPLETIONTIME', data.completionTime_Value, 1, subRowNumber, "mealsTimePointInformation",true, 8, $('#mealCompletionTimeValue').val(), $("#mTPTreatmentvalue").val());
            
            $("#Mealsinfo").attr("data-srnumber", subRowNumber);	
            
            $("#Mealsinfo tbody").append("<tr data-subrowno='" + subRowNumber + "'><td style='text-align:center'>" + treatmentNumber
            			+ "</td><td style='text-align:center'> " + data.TimePoint 
            			+ "</td><td style='text-align:center'>" + $('#MealsType option:selected').text()
            			+ "</td><td style='text-align:center'>" + $('#mealWindowPeriodSign option:selected').text() + "" + data.windowPeriod_Value 
            			+ "</td><td style='text-align:center'>" + $('#mealCompletionTimeApplicable option:selected').text() + "</td>"
            			+ "<td>" + data.completionTime_Value + "</td><td><a class='fa fa-trash delete' title='Delete'></a></td></tr>");
            $('#TimePoint,#mealTimePoint,#MealsType,#mealWindowPeriodSign,#mealWindowPeriodValue,#mealCompletionTimeApplicable,#mealCompletionTimeValue').val('');
            
            $("#TimePoint,#mealTimePoint,#MealsType,#mealWindowPeriodSign,#mealWindowPeriodValue,#mealCompletionTimeApplicable,#mealCompletionTimeValue").removeClass('validate validate-error');
            $("#TimePoint,#mealTimePoint,#MealsType,#mealWindowPeriodSign,#mealWindowPeriodValue,#mealCompletionTimeApplicable,#mealCompletionTimeValue").attr('data-validate','');
        });

        $(document).on("click", "#restrictionsbtn", function (e) {
        	isValid = true;
        	e.preventDefault();
        	if($("#resTreatment").val() === ""){
        		$("#resTreatment").addClass('validate validate-error');
        		$("#resTreatment").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#resParameter").val() === ""){
        		$("#resParameter").addClass('validate validate-error');
        		$("#resParameter").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#resActivity").val() === ""){
        		$("#resActivity").addClass('validate validate-error');
        		$("#resActivity").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if(!isValid){
        		return;
	   		}
        	
        	var treatmentNumber = 1;
            if(parseInt( $("#resTreatment").val()) > 0 ){
            	treatmentNumber = parseInt($("#resTreatment").val());
            }
        	
        	$.each($("#restrictionstable tbody tr"),function(index,ele){
        		if($(ele).attr("data-pid") === $("#resParameter").val() && 
        		   $(ele).attr("data-treat") === $("#resTreatment").val()){
        			isValid=false;
        			displayMessage('info', "Parameter already added");
        			return false;
        		}
        	});
        	
        	if(!isValid){
        		return;
	   		}
	       	
	       	if($("#restrictionstable").attr("data-defaultrow").toString() === "1"){
	       		$("#restrictionstable tbody").empty();	
	       		$("#restrictionstable").attr("data-defaultrow", 0);	
	       	}
	       	
	        var subRowNumber = $("#restrictionstable").attr("data-srnumber").toString();
            subRowNumber = parseInt(subRowNumber) + 1;
            $("#restrictionstable").attr("data-srnumber", subRowNumber);
	       	
	       	autoSaveData('TREATMENT', $("#resTreatment").val(), subRowNumber,  subRowNumber, "restrictionsComplainceMonitoring", true, 0, treatmentNumber, $("#resTreatment").val());
            autoSaveData('RESTRICTIONACTIVITY', $("#resActivity").val(), subRowNumber, subRowNumber, "restrictionsComplainceMonitoring", true, 1, $("#resActivity option:selected").text(), $("#resTreatment").val());
            autoSaveData('PARAMETER', $("#resParameter").val(), subRowNumber, subRowNumber, "restrictionsComplainceMonitoring", true, 2,$("#resParameter option:selected").text(), $("#resTreatment").val());

            $("#restrictionstable tbody").append("<tr data-subrowno='" + subRowNumber + "' data-pid='" + $("#resParameter").val() + "' data-treat='" + $("#resTreatment").val() +"'><td>" + treatmentNumber + "</td><td>" + $("#resActivity option:selected").text() + "</td><td>" + $("#resParameter option:selected").text() + "</td><td><a class='fa fa-trash delete' title='delete'></a></td></tr >");
            $("#resActivity,#resParameter").val('');
            $("#resActivity,#resParameter").removeClass('validate validate-error');
            $("#resActivity,#resParameter").attr('data-validate','');
        });
        
        $(document).on("click","[name='isOrthostaticPosition']",function(e){
        	$("#othostaticPosition").val('');
        	if($(this).val() === "YES"){
        		$("#othostaticPosition").prop("disabled",false);
        	}
        	else{
        		$("#othostaticPosition").prop("disabled",true);
        	}
        });
        
        $("#mealCompletionTimeApplicable").change(function(e){
        	$("#mealCompletionTimeValue").val('');
        	if($(this).val() === "YES"){
        		$("#mealCompletionTimeValue").prop("disabled",false);
        	}
        	else{
        		$("#mealCompletionTimeValue").prop("disabled",true);
        	}
        });
        
        $("#inclusioncriteriabtn").click(function (e) {
        	e.preventDefault();
        	isValid = true;
        	if($("#icParameter").val() === ""){
        		$("#icParameter").addClass('validate validate-error');
        		$("#icParameter").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#icGender").val() === ""){
        		$("#icGender").addClass('validate validate-error');
        		$("#icGender").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if(!isValid){
        		return;
	   		}
        	
        	$.each($("#inclusionTable tbody tr"),function(index,ele){
        		if($(ele).attr("data-pid") === $("#icParameter").val()){
        			isValid=false;
        			displayMessage('info',"Parameter already added");
        			return false;
        		}
        	});
        	
        	if(!isValid){
        		return;
	   		}
        	
        	if($("#inclusionTable").attr("data-defaultrow").toString() === "1"){
        		$("#inclusionTable tbody").empty();	
        		$("#inclusionTable").attr("data-defaultrow", 0);	
        	}

        	 var subRowNumber = $("#inclusionTable").attr("data-srnumber").toString();
             subRowNumber = parseInt(subRowNumber) + 1;
             $("#inclusionTable").attr("data-srnumber", subRowNumber);
        	
            autoSaveData('PARAMETER', $("#icParameter").val(), 0, subRowNumber, "inclusionCriteria",true,1,$("#icParameter option:selected").text());
            autoSaveData('GENDER', $("#icGender").val(), 0, subRowNumber, "inclusionCriteria",true,2,$("#icGender option:selected").text());
            $("#inclusionTable tbody").append("<tr data-subrowno='" +  subRowNumber + "' data-pid='" + $("#icParameter").val() + "'><td>" + $("#icParameter option:selected").text() + "</td><td>" +  $("#icGender option:selected").text() + "</td><td><a class='fa fa-trash delete' title='delete'></a></td></tr>");

            $("#icParameter,#icGender").val('');
            $("#icParameter,#icGender").removeClass('validate validate-error');
    		$("#icParameter,#icGender").attr('data-validate','');
        });

        $("#exclusionCriteriaBtn").click(function (e) {
        	e.preventDefault();
        	isValid = true;
        	if($("#ecParameter").val() === ""){
        		$("#ecParameter").addClass('validate validate-error');
        		$("#ecParameter").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#ecGender").val() === ""){
        		$("#ecGender").addClass('validate validate-error');
        		$("#ecGender").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if(!isValid){
        		return;
	   		}
        	
        	$.each($("#exclusionTable tbody tr"),function(index,ele){
        		if($(ele).attr("data-pid") === $("#ecParameter").val()){
        			isValid=false;
        			displayMessage('info',"Parameter already added");
        			return false;
        		}
        	});
        	
        	if(!isValid){
        		return;
	   		}
        	
        	if($("#exclusionTable").attr("data-defaultrow").toString() === "1"){
        		$("#exclusionTable tbody").empty();	
        		$("#exclusionTable").attr("data-defaultrow", 0);	
        	}
        	
        	 var subRowNumber = $("#exclusionTable").attr("data-srnumber").toString();
             subRowNumber = parseInt(subRowNumber) + 1;
             $("#exclusionTable").attr("data-srnumber", subRowNumber);
        	
            autoSaveData('PARAMETER', $("#ecParameter").val(), 0, subRowNumber, "exclusionCriteria",true, 1,$("#ecParameter option:selected").text());
            autoSaveData('GENDER', $("#ecGender").val(), 0, subRowNumber, "exclusionCriteria",true,2,$("#ecGender option:selected").text());
            $("#exclusionTable tbody").append("<tr data-subrowno='" +  subRowNumber + "' data-pid='" + $("#ecParameter").val() + "'><td>" + $("#ecParameter option:selected").text() + "</td><td>" + $("#ecGender option:selected").text() + "</td><td><a class='fa fa-trash delete' title='delete'></a></td></tr>");
            $("#ecParameter,#ecGender").val('');
            $("#ecParameter,#ecGender").removeClass('validate validate-error');
    		$("#ecParameter,#ecGender").attr('data-validate','');
        });
        
        $("#otherActivity").change(function(e){
        	$("#otherTreatmentSpecific,#otherTreatmentSpecific").prop('checked',false);
        	$("#otherParameter").empty();
    		$("#otherParameter").append("<option value=''>Select</option>");
    		if($(this).val() !== ""){
    			getActivityParamters($(this).val(),'otherParameter');
    		}
    	});
        
        $("#btnOtherActivity").click(function (e) {
        	e.preventDefault();
        	isValid = true;
        	if($("#otherActivity").val() === ""){
        		$("#otherActivity").addClass('validate validate-error');
        		$("#otherActivity").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#otherTreatmentSpecific").val() === ""){
        		$("#otherTreatmentSpecific").addClass('validate validate-error');
        		$("#otherTreatmentSpecific").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#otherTimepointSpecific").val() === ""){
        		$("#otherTimepointSpecific").addClass('validate validate-error');
        		$("#otherTimepointSpecific").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#otherTreatment").val() === ""){
        		$("#otherTreatment").addClass('validate validate-error');
        		$("#otherTreatment").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#otherTimepointSpecific").val()  === 'YES' && $("#otherTimepoint").val() === ""){
        		$("#otherTimepoint").addClass('validate validate-error');
        		$("#otherTimepoint").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#otherParameter").val() === ""){
        		$("#otherParameter").addClass('validate validate-error');
        		$("#otherParameter").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if(!isValid){
        		return;
	   		}
        	
        	$.each($("#tblOtherActivity tbody tr"),function(index,ele){
        		if($(ele).attr("data-pid") === $("#ecParameter").val()){
        			isValid=false;
        			displayMessage('info',"Parameter already added");
        			return false;
        		}
        	});
        	
        	if(!isValid){
        		return;
	   		}
        	
        	var timePoints = $("#otherTimepoint").val();
         	var timePointsArray = timePoints.split(',');
         	let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
         	var duplicateTimepoints = findDuplicates(timePointsArray);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
         	
         	var allTimePoints = timePoints.split(',');
         	$.each($("#tblOtherActivity tbody tr"),function(index,ele){
         		if($(ele).find("td:eq(0)").text() === $("#otherActivity").val() && ($(ele).find("td:eq(1)").text() === $("#otherTreatment").val() ||  $("#otherTreatment").val() === "0")){
         			allTimePoints.push.apply(allTimePoints, $(ele).find("td:eq(1)").text().split(','));
         		}
         	});
         	
         	duplicateTimepoints = findDuplicates(allTimePoints);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
        	
        	if($("#tblOtherActivity").attr("data-defaultrow").toString() === "1"){
        		$("#tblOtherActivity tbody").empty();	
        		$("#tblOtherActivity").attr("data-defaultrow", 0);	
        	}
        	
        	var treatmentNumber = 1;
            if(parseInt( $("#otherTreatment").val()) > 0 ){
            	treatmentNumber = parseInt($("#otherTreatment").val());
            }
        	
        	autoSaveData('ACTIVITY', $("#otherActivity").val(), 0, ($("#tblOtherActivity tbody tr").length + 1), "OtherActivity",true, 1,$("#otherActivity option:selected").text(),$("#otherTreatment").val());
        	autoSaveData('TREATMENT', $("#otherTreatment").val(), 0, ($("#tblOtherActivity tbody tr").length + 1), "OtherActivity",true, 2,$("#otherTreatment option:selected").text(),$("#otherTreatment").val());
        	autoSaveData('TIMEPOINT', $("#otherTimepoint").val(), 0, ($("#tblOtherActivity tbody tr").length + 1), "OtherActivity",true,3,$("#otherTimepoint").val(),$("#otherTreatment").val());
        	autoSaveData('PARAMETER', $("#otherParameter").val(), 0, ($("#tblOtherActivity tbody tr").length + 1), "OtherActivity",true, 4,$("#otherParameter option:selected").text(),$("#otherTreatment").val());
            
            $("#tblOtherActivity tbody").append("<tr data-subrowno='" +  ($("#tblOtherActivity tbody tr").length + 1) + "'><td>" + $("#otherActivity option:selected").text() 
            		+ "</td><td>" + $("#otherTreatment option:selected").text() + "</td><td>" + $("#otherTimepoint").val() + "</td><td>" + $("#otherParameter option:selected").text() + "</td><td><a class='fa fa-trash delete' title='delete'></a></td></tr>");
            $("#otherTreatment,#otherTimepoint,#otherParameter").val('');
            $("#otherTreatment,#otherTimepoint,#otherParameter").removeClass('validate validate-error');
            $("#otherTreatment,#otherTimepoint,#otherParameter").attr('data-validate','');
    	});

        $(document).on("click", "#btnEcg", function (e) {
        	e.preventDefault();
        	isValid = true;
        	if($("[name='EcgTreatmentSpecificTimepoints']").val() === ""){
        		$("[name='EcgTreatmentSpecificTimepoints']").addClass('validate validate-error');
        		$("[name='EcgTreatmentSpecificTimepoints']").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#EcgTreatment").val() === ""){
        		$("#EcgTreatment").addClass('validate validate-error');
        		$("#EcgTreatment").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#EcgTimePoint").val() === ""){
        		$("#EcgTimePoint").addClass('validate validate-error');
        		$("#EcgTimePoint").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#ecgSign").val() === ""){
        		$("#ecgSign").addClass('validate validate-error');
        		$("#ecgSign").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#ecgWindowperiodValue").val() === ""){
        		$("#ecgWindowperiodValue").addClass('validate validate-error');
        		$("#ecgWindowperiodValue").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if($("#ecgWindowPeriodTime").val() === ""){
        		$("#ecgWindowPeriodTime").addClass('validate validate-error');
        		$("#ecgWindowPeriodTime").attr('data-validate','required');
        		isValid = false;
 	   		}
        	
        	if(!isValid){
        		return;
	   		}
        	
        	var timePoints = $("#EcgTimePoint").val();
         	var timePointsArray = timePoints.split(',');
         	let findDuplicates = arr => arr.filter((item, index) => arr.indexOf(item) != index);
         	var duplicateTimepoints = findDuplicates(timePointsArray);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
         	
         	var allTimePoints = timePoints.split(',');
         	$.each($("#tblEcg tbody tr"),function(index,ele){
         		if($(ele).find("td:eq(0)").text() === $("#EcgTreatment").val() ||  $("#EcgTreatment").val() === "0"){
         			allTimePoints.push.apply(allTimePoints, $(ele).find("td:eq(2)").text().split(','));
         		}
         	});
         	
         	duplicateTimepoints = findDuplicates(allTimePoints);
         	if(duplicateTimepoints.length > 0){
         		displayMessage('info', "Some of the timepoint(s) already added. Please check.");        		
         		return;
         	}
        	
        	var treatmentNumber = 1;
            if(parseInt( $("#EcgTreatment").val()) > 0 ){
            	treatmentNumber = parseInt($("#EcgTreatment").val());
            }
        	
        	if($("#tblEcg").attr("data-defaultrow").toString() === "1"){
        		$("#tblEcg tbody").empty();	
        		$("#tblEcg").attr("data-defaultrow", 0);	
        	}
            
            autoSaveData('TREATMENT', $("#EcgTreatment").val(), 1, ($("#tblEcg tbody tr").length + 1), "ecgTimePoins",true, 0,$("#EcgTreatment").val());
            autoSaveData('TIMEPOINT', $("#EcgTimePoint").val(), 1, ($("#tblEcg tbody tr").length + 1), "ecgTimePoins",true, 2,$('#EcgTimePoint').val(),$("#EcgTreatment").val());
            
            autoSaveData('WINDOWPERIODSIGN', $('#ecgSign').val(), 1, ($("#tblEcg tbody tr").length + 1), "ecgTimePoins",true, 3,$('#ecgSign option:selected').text(),$("#EcgTreatment").val());
            autoSaveData('WINDOWPERIOD', $("#ecgWindowperiodValue").val(), 1, ($("#tblEcg tbody tr").length + 1), "ecgTimePoins",true, 4,$("#ecgWindowperiodValue").val(),$("#EcgTreatment").val());
            autoSaveData('WINDOWPERIODTIME', $("#ecgWindowPeriodTime").val(), 1, ($("#tblEcg tbody tr").length + 1), "ecgTimePoins",true, 5,$('#ecgWindowPeriodTime option:selected').text(),$("#EcgTreatment").val());
            
            $("#tblEcg tbody").append("<tr><td> " + treatmentNumber + " </td><td> " + $('#EcgTimePoint').val() + "</td><td>" + $('#ecgSign option:selected').text() + "" + $("#ecgWindowperiodValue").val() + "" + $('#ecgWindowPeriodTime option:selected').text() + "</td><td><a class='fa fa-trash delete'></a></td></tr>");
            $("#EcgTimePoint,#ecgSign,#ecgWindowperiodValue,#ecgWindowPeriodTime").val('');
            $("#EcgTimePoint,#ecgSign,#ecgWindowperiodValue,#ecgWindowPeriodTime").removeClass('validate validate-error');
            $("#EcgTimePoint,#ecgSign,#ecgWindowperiodValue,#ecgWindowPeriodTime").attr('data-validate','');
        });


        $(document).on("change", '.noofdosings', function (e) {
        	var ele = $(this);
        	var dosingInterval = setInterval(function(){
        		if(dosingInterval!==null){
	        		if($(ele).data("isupdated").toString() === "1"){
	        			buildDosingForm($(ele));	
	        			autoSaveData('noOfDosings', $(ele).val(), $(ele).data("rownumber"), $(ele).data("subrownumber"), "DosingTimepoints",true, 0,$(ele).val(),$(ele).data("treatment"));
	        			clearInterval(dosingInterval); dosingInterval = null;
	        		}
        		}
        	},200);
        });



	$(document).on("change", '.autosave', function (e) {
		if(!$(this).hasClass("timepoint-format")&& !$(this).hasClass("time-format")){
			var fieldOrder = 0;
			if($(this).data("fieldorder") !== undefined && $(this).data("fieldorder") !== null){
				fieldOrder = $(this).data("fieldorder");
			}
			
			if($(this).closest(".parent").data("name") === "DosingTimepoints" && $(this).attr("id") !== "differenceinDosings"){
				if($("#doseType").val() === "SINGLE"){
					if($.trim($(this).val()).length > 0){
						autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),"0");
					}
					else{
						$(this).attr("data-validdosetimepoint",'false');
						displayMessage('info', "Enter dose timepoint");
						$(this).val("");
					}
				}
				else if($("#doseType").val() !== ""){
					var doseTimepointElement = $(this).closest("tr").find("[name='doseTimePoint']");
					if($(doseTimepointElement).data("validdosetimepoint") === true){
						autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true,null, $(this).val(),$(this).closest('[data-dosingnumber]').data("dosingnumber"));	
					}
					else{
						$(this).val("");
						displayMessage('info', "Please enter dose timepoint");	
					}
				}
			}
			else{
				autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, fieldOrder,$(this).val(),null);	
			}
		}
	});
	
	$(document).on("change", '.cen_autosave', function (e) {
		var values = "";
		$.each($(".cen_autosave:checked"),function(index,ele){
			if(values!==""){
				values = values + ',' + $(ele).val();
			}
			else{
				values = $(ele).val();
			}
		});
		autoSaveData("CONDITIONS", values, 0, 0, "CENTRIFUGATION", true, 0,values);
	});
	
	$(document).on("change", '.pro_autosave', function (e) {
		var values = "";
		$.each($(".pro_autosave:checked"),function(index,ele){
			if(values!==""){
				values = values + ',' + $(ele).val();
			}
			else{
				values = $(ele).val();
			}
		});
		autoSaveData("CONDITIONS", values, 0, 0, "PROCESSING", true, 0,values);
	});
	
	$(document).on("click", "[name='isStorageDifferent']", function (e) {
		checkStorageDifferent($(this));
	});
	
	$(document).on("click", "[name='TreatmentSpecificRestrictionsComplaince']", function (e) {
		if($("#restrictionstable").attr("data-defaultrow").toString() !== "1"){
			$.confirm({
			    title: 'Confirm!',
			    content: 'Changing treatment specific timepoint value will delete the Restrictions data. Do you wish to continue?',
			    buttons: {
			        Yes: function () {
			        	$(element).attr('data-old',$(element).val());
			        	deactivateDataByType("sampleTimePoins","restrictionstable",6);
			        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
			        	checkTreatmentSpecificRestrictions($(element));
			        },
			        No: function () {
			        	$(element).val($(element).data('old'));
			        }
			    }
			});
		}
		else{
			$(this).attr('data-old',$(this).val());
			checkTreatmentSpecificRestrictions($(this));
			autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
		}
	});
	
	
	
	$(document).on("click", "[name='isMatrixDifferent']", function (e) {
		checkMatrixDifference($(this));
	});	
	
	$("#doseType").change(function (e) {
		var element = $(this);
		if($(this).val() !== $(this).attr("data-old") && $(this).attr("data-old") !== undefined){
			$.confirm({
			    title: 'Confirm!',
			    content: 'Changing Dose Type value will delete the dose timepoints. Do you wish to continue?',
			    buttons: {
			        Yes: function () {
			        	$(element).attr('data-old',$(element).val());
			        	deactivateDataByType("DosingTimepoints","",0);
			        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), 0, 0, $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
			        	verifyDosing();
			        },
			        No: function () {
			        	$(element).val($(element).data('old'));
			        }
			    }
			});
		}
		else{
			autoSaveElementData($(element),$(element).attr("name"), $(element).val(), 0, 0, $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
			verifyDosing();
		}
    });

	$("#studyDesign").change(function (e) {
		var element = $(this);
		if($(this).val() !== $(this).attr("data-old") && $(this).attr("data-old") !== undefined){
			$.confirm({
			    title: 'Confirm!',
			    content: 'Changing Project Design will delete the dose timepoints. Do you wish to continue?',
			    buttons: {
			        Yes: function () {
			        	$(element).attr('data-old',$(element).val());
			        	deactivateDataByType("DosingTimepoints","",0);
			        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), 0, 0, $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
			        	verifyDosing();
			        },
			        No: function () {
			        	$(element).val($(element).data('old'));
			        }
			    }
			});
		}
		else{
			autoSaveElementData($(element),$(element).attr("name"), $(element).val(), 0, 0, $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
			verifyDosing();
		}
    });
	
	

        /* code for No of Sachet Labels*/
		$(document).on("change", '.noofsachetlabels', function (e) {
			var element = $(this);
			setTimeout(function(){
				var noofsachetlabels = parseInt($(element).val());
	            var treatmentno = $(element).data("treatment");
	            autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true,null, $(element).val(), $(element).closest("tr").find("#trtmntNo").val());
	    		var treatmentSachetLabelsTable = $("#sachetLabelCode").find("[data-treatment='" + treatmentno + "']");
	            if(noofsachetlabels === 1 || $.trim($(element).val()).length === 0){
	            	$(treatmentSachetLabelsTable).remove();
	            }
	            else{
		            if (treatmentSachetLabelsTable.length > 0) {
		
		                var tableBody = $(treatmentSachetLabelsTable).find("tbody");
		                var tableRows = $(tableBody).find("tr");
		
		                if (tableRows.length < noofsachetlabels) {
		                    data = " ";
		                    var z = tableRows.length + 1;
		                    for (var i = tableRows.length; i < noofsachetlabels; i++) {
		                        data = data + "<tr style='height:40px;'><td>Sachet Label " + z + " Code</td><td style='width:220px;'><input type='text' id='sachetlabelcode' class='form-control autosave' data-rownumber='" + i + "' data-subrownumber='" + (i + 1) + "' /></td></tr>";
		                        z++;
		                    }
		                    $(tableBody).append(data);
		                }
		                else {
		                    $.each($(tableRows), function (index, ele) {
		                        if (index >= noofsachetlabels) {
		                            $(ele).remove();
		                        }
		                    });
		                }
		            }
		            else {
		                var data = "<table data-treatment='" + treatmentno + "'><thead><tr></tr><tr><td><label>Treatment " + treatmentno + " Sachet Label Code</label></td></tr></thead><tbody>";
		                var z = 1;
		                for (var i = 0; i < noofsachetlabels; i++) {
		                    data = data + "<tr style='height:40px;'><td>Sachet Label " + z + "  Code</td><td style='width:220px;'><input type='text' id='sachetlabelcode' class='form-control autosave' data-rownumber='" + i + "' data-subrownumber='" + (i + 1) + "' /></td></tr>";
		                    z++;
		                }
		                data = data + "</tbody></table>";
		                $("#sachetLabelCode").append(data);
		            }
	            }
			},100);
        });

        $("#nooftreatments").change(function (e) {
        	
        	var element = $(this);
        	if($.trim($("#nooftreatments").val()).length === 0){
        		displayMessage('info','Enter no. of treatments.');
        	}
        	else if($("#nooftreatments").val().toString() !== $("#nooftreatments").attr('data-old').toString()){
        		if(parseInt($("#nooftreatments").val()) < parseInt($("#nooftreatments").attr('data-old'))){
        			$.confirm({
	        		    title: 'Confirm!',
	        		    content: 'Changing no. of treatments will delete treatment data above ' + $("#nooftreatments").val() +'. Do you wish to continue?',
	        		    buttons: {
	        		        Yes: function () {
	        		        	$(".loader").show();
	        		        	var oldNoofTreatments = parseInt($("#nooftreatments").attr('data-old'));
	        		        	var newNoofTreatments = parseInt($("#nooftreatments").val());
	        		        	
	        		        	$(element).attr('data-old',$(element).val());
	        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
	        		        	
	        		        	var formData =$('#frmData').serializeArray();
        		        		formData.push({name: "projectId",value: $("input[name='projectId']").val()});
        		                formData.push({name: "type",value : ''});	
        		                formData.push({name: "rowNumber",value : oldNoofTreatments});	
        		                formData.push({name: "subRowNumber",value : newNoofTreatments});	
        		                formData.push({name: "activeandinactive",value : 1});
        		                formData.push({name: "fieldOrder",value : 1});
        		                formData.push({name: "treatmentNo",value : 0});
        		                
        		        		$.ajax({
	        		        		url: $("#mainUrl").val() + '/studydesign/deactivateTreatment/',
	        		        		type:'POST',
	        		        		data:formData,
	        		        		success: function(d){
	        		        			getProjectDetails();
	        		        		}
	        		        	});
	        		        },
	        		        No: function () {
	        		        	$(element).val($(element).data('old'));
	        		        }
	        		    }
	        		});
        		}
        		else{
        			$(element).attr('data-old',$(element).val());
		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
        			verifyDosing();
        			dosingParameterTreatment();	
        			vitalsTreatmentChange();
        			sampleTreatments();
        			treatmentSpecificMeals();
        		}
        	}
        	else{
        		$(element).attr('data-old',$(element).val());
	        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
    			verifyDosing();
    			dosingParameterTreatment();	
    			vitalsTreatmentChange();
    			sampleTreatments();
    			treatmentSpecificMeals();
        	}
        });
        
        
        $("#treatmentwisedose,#nooftreatments").change(function (e) {
        	if($(this).attr("id") === "treatmentwisedose"){
        		var element = $(this);
        		if($("#dosingParameterList").attr("data-defaultrow").toString() !== "1"){
        			$.confirm({
	        		    title: 'Confirm!',
	        		    content: 'Changing treatment specific timepoint value will delete the dosing parameters. Do you wish to continue?',
	        		    buttons: {
	        		        Yes: function () {
	        		        	$(element).attr('data-old',$(element).val());
	        		        	deactivateDataByType("DosingParameters","dosingParameterList",3);
	        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
	        		        	dosingParameterTreatment();	
	        		        },
	        		        No: function () {
	        		        	$(element).val($(element).data('old'));
	        		        }
	        		    }
	        		});
        		}
        		else{
        			$(this).attr('data-old',$(this).val());
            		dosingParameterTreatment();
            		autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
            	}
        	}
        });

        $("#tmntspctmpt,#nooftreatments").change(function (e) {
        	if($(this).attr('name') == 'teatmentSpecificVitalTimepoints'){
        		if($("#preAndPostTable").attr("data-defaultrow").toString() !== "1"){
        			$.confirm({
	        		    title: 'Confirm!',
	        		    content: 'Changing treatment specific timepoint value will delete the vitals data. Do you wish to continue?',
	        		    buttons: {
	        		        Yes: function () {
	        		        	$(element).attr('data-old',$(element).val());
	        		        	deactivateDataByType("vitalTimepointInformation","preAndPostTable",8);
	        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
	        		        	vitalsTreatmentChange();
	        		        },
	        		        No: function () {
	        		        	$(element).val($(element).data('old'));
	        		        }
	        		    }
	        		});
        		}
        		else{
        			$(this).attr('data-old',$(this).val());
        			vitalsTreatmentChange();
            		autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
            	}
        	}
        });

        $(document).on("change", "#samptmpdp,#nooftreatments", function (e) {
        	if($(this).attr('name') == 'treatmentSpecificTimepoint'){
        		var element = $(this);
        		if($("#tsplconditionwisedata").attr("data-defaultrow").toString() !== "1"
        				|| $("#sampletimepointTreatmentslisttable").attr("data-defaultrow").toString() !== "1"){
        			$.confirm({
	        		    title: 'Confirm!',
	        		    content: 'Changing treatment specific timepoint value will delete the samples data. Do you wish to continue?',
	        		    buttons: {
	        		        Yes: function () {
	        		        	$(element).attr('data-old',$(element).val());
	        		        	deactivateDataByType("sampleTimePoins","sampletimepointTreatmentslisttable",6);
	        		        	deactivateDataByType("sampleInformation","tsplconditionwisedata",6);
	        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
	        		        	sampleTreatments();
	        		        },
	        		        No: function () {
	        		        	$(element).val($(element).data('old'));
	        		        }
	        		    }
	        		});
        		}
        		else{
        			$(this).attr('data-old',$(this).val());
        			sampleTreatments();
            		autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
            	}
        	}
        });

        $("#differenceinDosings").change(function (e) {
        	var element = $(this);
    		if($(this).val() !== $(this).attr("data-old")){
    			$.confirm({
    			    title: 'Confirm!',
    			    content: 'Changing Is there any difference in the no. of dosings will delete the dose timepoints. Do you wish to continue?',
    			    buttons: {
    			        Yes: function () {
    			        	$(element).attr('data-old',$(element).val());
    			        	deactivateDataByType("DosingTimepoints","",0);
    			        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), 0, 0, $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
    			        	verifyDosing();
    			        },
    			        No: function () {
    			        	$(element).val($(element).data('old'));
    			        }
    			    }
    			});
    		}
    		else{
    			autoSaveElementData($(element),$(element).attr("name"), $(element).val(), 0, 0, $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
    			verifyDosing();
    		}
		});

        $("#teatmentSpecificMealTimepoints,#nooftreatments").change(function (e) {
        	if($(this).attr('name') == 'teatmentSpecificMealTimepoints'){
        		if($("#Mealsinfo").attr("data-defaultrow").toString() !== "1"){
        			$.confirm({
	        		    title: 'Confirm!',
	        		    content: 'Changing treatment specific timepoint value will delete the meals data. Do you wish to continue?',
	        		    buttons: {
	        		        Yes: function () {
	        		        	$(element).attr('data-old',$(element).val());
	        		        	deactivateDataByType("mealsTimePointInformation","Mealsinfo",7);
	        		        	autoSaveElementData($(element),$(element).attr("name"), $(element).val(), $(element).data("rownumber"), $(element).data("subrownumber"), $(element).closest(".parent").data("name"), true, 0,$(element).val(),null);
	        		        	treatmentSpecificMeals();
	        		        },
	        		        No: function () {
	        		        	$(element).val($(element).data('old'));
	        		        }
	        		    }
	        		});
        		}
        		else{
        			$(this).attr('data-old',$(this).val());
        			treatmentSpecificMeals();
            		autoSaveElementData($(this),$(this).attr("name"), $(this).val(), $(this).data("rownumber"), $(this).data("subrownumber"), $(this).closest(".parent").data("name"), true, 0,$(this).val(),null);
            	}
        	}
        });

        $("#remove").click(function (e) {

            $("#mealsdata").remove();
        });

        $("#noofvials").change(function (e) {
            $("#aliquotes").empty();
            var number = $("#noofvials").val(); var z = 1;
            for (var i = 1; i <= number; i++) {
                $("#aliquotes").append("<table><tr style='height:20px'><td style='text-align:center;padding-top: 10px;'><label style='width:170px;'>Aliquot " + z + " Volume</label></td><td><div style='width:170px;padding-top:10px'><input type='text' class='form-control autosave' id='Vail1vol' placeholder='approx 15 ml'/></input></div></td><td style='text-align:center;padding-top: 10px;'><label style='width:170px;padding-left:10px'>Aliquot " + z + " Matrix</label></td><td><div style='width:170px;padding-left:10px;padding-top:10px'><select  class='form-control autosave'><option value='Select'>Select</option><option value='Serum'>Serum</option><option value='Plasma'>Plasma</option><option value='WholeBlood'>Whole Blood</option><option value='Others'>Others</option></select></div></td></tr></table>");
                z++;
            }
        });

        $(document).on("change", "#ecgtTeatmentEiseDose,#nooftreatments", function (e) {
            var ecgtTeatmentEiseDose = $("#ecgtTeatmentEiseDose").val();
            $("#ecgTreatmentType").empty();
            const select = document.getElementById("ecgTreatmentType");
            if(select===null)
            	return;
            if (ecgtTeatmentEiseDose == "YES") {
                var number = $("#nooftreatments").val();
                select.insertAdjacentHTML('beforeend', "<option value=''>Select</option>");
                for (var i = 0; i < number; i++) {
                    select.insertAdjacentHTML('beforeend', '<option value=' + (i+1) + '>Treatment' + (i+1) + '</option>');
                }
            }

            if (ecgtTeatmentEiseDose == "NO") {
                select.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
            } else {
            	select.insertAdjacentHTML('beforeend', '<option value="0">Treatment</option>');
            }
		});

        $("#tdEcgApplicable").on("click","[name='EcgApplicable']",function (e) {
        	checkEcg($(this));
        });
    });