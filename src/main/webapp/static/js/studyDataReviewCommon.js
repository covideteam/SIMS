var comments = [];
var parameterId = 0;
var parametersData = [];
var subjectData="";
function getSubjectsForReview(){
	var url = $("#mainUrl").val() + "/actvityReview/getStudyActivityDataForReview/" + $("#studyName").val() + "/" + $("#crfName").val();
	$.ajax({
		url: url,
		type:'GET',
		success:function(d){
			$("#crfReviewDiv").show();
			$("#tblSubjects tbody").empty();
			for(var a=0;a<d.length;a++){
				var number="";
				if(d[a]["subjectNumber"] === null || d[a]["subjectNumber"] === undefined){
					number=d[a]["registrationNumber"];
				}
				else{
					number=d[a]["subjectNumber"];
				}
				var rowData = "<tr><td>" + number + "</td><td>" + d[a]["createdBy"] + "</td><td>" + d[a]["createdOn"] 
				+ "</td><td><a data-id='" + d[a]["activityDataId"] + "' class='fa fa-file view'></a></td></tr>";
				$("#tblSubjects tbody").append(rowData);
			}
			$(".loader").hide();	
		}
	});
}

$(document).on("click",".back",function(e){
	e.preventDefault();
	$("#activityDiv").hide('slow',function(){
    	$("#crfReviewDiv").show('slow');	
    });
});

$(document).on("click","#tblSubjects .view",function(e){
	$(".loader").show();
	$("#studyActivityDataId").val($(this).attr("data-id"));
	$.ajax({
		url: $("#mainUrl").val()+"/actvityReview/getActivityDataDetails/" + $(this).attr("data-id"),
		type:'GET',
		success:function(d){
			subjectData = d;
			buildActivityDataReviewForm(d);
			$(".loader").hide();	
		}
	});
});

function createFlag(data){
	var flagType = 0;
	for(var d=0;d<data.comments.length;d++){
		if($.trim(data.comments[d].response).length === 0){
			flagType = 1;
		}
		comments.push({ commentId: data.comments[d].commentId, parameterId:data.parameters.studyActivityDataParameterId,
			comment:data.comments[d].comment, isTemp : false,createdBy: data.comments[d].commentedBy,
			createdOn: data.comments[d].commentedOn, respondedBy: data.comments[d].respondedBy,
			respondedOn: data.comments[d].respondedOn, response:data.comments[d].response});
	}
	
	if(flagType === 0 && $("#pageType").val() === "review"){
		return "<a data-id='" + data.parameters.studyActivityDataParameterId  + "' class='fa fa-flag sr-dis red'></a>";	
	}
	else if(flagType === 0){
		return "";
	}
	return "<a data-id='" + data.parameters.studyActivityDataParameterId  + "' class='fa fa-flag sr-dis red'></a>";
}

function buildControl(){
	
}

function buildActivityDataReviewForm(reviewData){
	parametersData = reviewData;
	comments = []; $("#tblComments tbody").empty();
	var groupArray = [];
	var groupOrder = [];
	var formData = "<table class='ds-form-table'>";
    var isExists = false;
    var isFieldExistsWithoutGroup = false;
    for (var pm = 0; pm < reviewData.length; pm++) {
        isExists = false;
        for (var ga = 0; ga < groupArray.length; ga++) {
            if (reviewData[pm].group !== null && reviewData[pm].group !== undefined && reviewData[pm].group.groupId === groupArray[ga].groupId) {
                isExists = true;
            }
        }
        if(reviewData[pm].group === null || reviewData[pm].group === undefined){
        	isFieldExistsWithoutGroup = true;	
        }
        else if (reviewData[pm].group !== null && reviewData[pm].group !== undefined && !isExists) {
            groupArray.push({ groupId: reviewData[pm].group.groupId, groupName: reviewData[pm].group.groupName,groupOrder: reviewData[pm].group.orderNo});
            groupOrder.push(reviewData[pm].group.orderNo);
        }
    }
    groupOrder.push(-1);
    groupOrder.sort();
    if(groupOrder.length > 0){
    	debugger;
	    for (var go = 0; go < groupOrder.length; go++) {
	    	var group = filterJsonArray(groupArray,'groupOrder',groupOrder[go]);
	    	for(var gd = 0;gd < reviewData.length; gd++){
	    		if(groupOrder[go] === -1 && (reviewData[gd].group === null || reviewData[gd].group === undefined)){
	    			formData = formData + "<tr data-id='" + reviewData[gd].parameters.parameterId + "'><td>" + reviewData[gd].parameters.parameterName + "</td><td style='min-width:200px;'>";
	    			
	    			if(reviewData[gd].controlType.contentCode === "RB"){
	    				for(var par = 0; par < reviewData[gd].controlType.valuesDto.length;par++){
		    				if(reviewData[gd].controlType.valuesDto[par].valueId.toString() === reviewData[gd].parameters.value){
		    					formData = formData + reviewData[gd].controlType.valuesDto[par].valueName;
		    				}
		    			}
	    			}
	    			else{
	    				formData = formData + reviewData[gd].parameters.value;
	    			}
	    			formData = formData + "</td><td style='width:20px;'>" + createFlag(reviewData[gd]) + "</td></tr>";	
	    		}
	    		else if(group.length > 0 && group !==null && group !== undefined){
	    			if(reviewData[gd].group !== null && reviewData[gd].group !== undefined){
		    			if(reviewData[gd].group.groupId === group[0].groupId){
			    			formData = formData + "<tr data-id='" + reviewData[gd].parameters.parameterId + "'><td>" + reviewData[gd].parameters.parameterName + "</td><td style='min-width:200px;'>";
			    			if(reviewData[gd].controlType.contentCode === "RB"){
			    				for(var par = 0; par < reviewData[gd].controlType.valuesDto.length;par++){
				    				if(reviewData[gd].controlType.valuesDto[par].valueId.toString() === reviewData[gd].parameters.value){
				    					formData = formData + reviewData[gd].controlType.valuesDto[par].valueName;
				    				}
				    			}
			    			}
			    			else{
			    				formData = formData + reviewData[gd].parameters.value;
			    			}
			                formData = formData + "</td><td style='width:20px;'>" + createFlag(reviewData[gd]) + "</td></tr>";	
		    			}
	    			}
	    		}
	    	}
	    }
    }
    else{
    	for(var gd = 0;gd < reviewData.length; gd++){
    		formData = formData + "<tr data-id='" + reviewData[gd].parameters.parameterId + "'><td>" + reviewData[gd].parameters.parameterName + "</td><td style='min-width:200px;'>";
    		if(reviewData[gd].controlType.contentCode === "RB"){
				for(var par = 0; par < reviewData[gd].controlType.valuesDto.length;par++){
    				if(reviewData[gd].controlType.valuesDto[par].valueId.toString() === reviewData[gd].parameters.value){
    					formData = formData + reviewData[gd].controlType.valuesDto[par].valueName;
    				}
    			}
			}
			else{
				formData = formData + reviewData[gd].parameters.value;
			}
            formData = formData + "</td><td style='width:20px;'>" + createFlag(reviewData[gd]) + "</td></tr>";	
    	}
    }
    
    formData = formData + "</table>";
    if($("#pageType").val() === "review"){
    	formData = formData + "<div style='text-align:center;' class='mt-15'><button class='btn btn-info back'>Back</button><button class='btn btn-info sendcomments'>Send Comments</button><button class='btn btn-info accept'>Accept</button></div>";	
    }
    else{
    	formData = formData + "<div style='text-align:center;' class='mt-15'><button class='btn btn-info back'>Back</button><button id='btnSaveResponse' class='btn btn-info submit'>Submit</button></div>";
    }
    
    $("#crfReviewDiv").hide('slow',function(){
    	$("#activityDiv").show('slow');	
    });
    $("#activityDiv").empty();
    $("#activityDiv").append(formData);
}