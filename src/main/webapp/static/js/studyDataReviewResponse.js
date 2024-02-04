var commentId = 0;
$(document).on("click",".sr-dis",function(){
	parameterId = $(this).attr("data-id");
	for(var p=0;p<parametersData.length;p++){
		if(parametersData[p].parameters.studyActivityDataParameterId.toString() === parameterId){
			$("#tdParameterName").text(parametersData[p].parameters.parameterName);
			$("#txtOldValue").val(parametersData[p].parameters.value);
			if(parametersData[p].controlType.contentCode === "DP"){
				$("#tdParameterControl").append("<input type='text' name='field_" + parameterId + "' id='field_" + parameterId + "' class='form-control' value='" + parametersData[p].parameters.value + "'/>");
				loadDatePicker("field_" + parameterId);
			}
			else if(parametersData[p].controlType.contentCode === "TB" || parametersData[p].controlType.contentCode === "TA"){
				$("#tdParameterControl").append("<input type='text' name='field_" + parameterId + "' id='field_" + parameterId + "' class='form-control' value='" + parametersData[p].parameters.value + "'/>");
			}
			else if(parametersData[p].controlType.contentCode === "RB"){
				$("#tdParameterControl").empty();
				var rbData = "<div>";
				for(var rb=0;rb<parametersData[p].controlType.valuesDto.length;rb++){
					if(parametersData[p].parameters.value === parametersData[p].controlType.valuesDto[rb].valueId.toString()){
						rbData = rbData + "<label><input type='radio' name='field_" + parameterId + "' id='field_" + parameterId + "' value='" + parametersData[p].parameters.value + "' checked>" + parametersData[p].controlType.valuesDto[rb].valueName + "</label>";
					}
					else{
						rbData = rbData + "<label><input type='radio' name='field_" + parameterId + "' id='field_" + parameterId + "' value='" + parametersData[p].parameters.value + "'>" + parametersData[p].controlType.valuesDto[rb].valueName + "</label>";
					}	
				}
				rbData = rbData + "</div>";
				$("#tdParameterControl").append(rbData);
			}
		}
	}
	$("#tblComments tbody").empty();
	var commentsData = "";
	for(var c=0;c<comments.length;c++){
		if(comments[c].parameterId == parameterId){
			if(comments[c].response !== undefined && comments[c].response !== null){
				commentsData = commentsData + "<tr data-id='" + comments[c].commentId + "'><td>" + comments[c].comment + "</td><td>" + comments[c].createdBy + "</td><td>" + comments[c].createdOn +  "</td><td>" + comments[c].response;
				if(comments[c].respondedBy===null){
					commentsData = commentsData + "</td><td></td><td>";
				}
				else{
					commentsData = commentsData + "</td><td>" + comments[c].respondedBy + "</td><td>";
				}
				if(comments[c].respondedBy===null){
					commentsData = commentsData + "</td>";
				}
				else{
					commentsData = commentsData +  comments[c].respondedOn + "</td>";
				}
				commentsData = commentsData +  "<td><a data-id='" + comments[c].commentId + "' class='fa fa-pencil edit'></a></td></tr>";	
			}
			else{
				commentsData = commentsData + "<tr data-id='" + comments[c].commentId + "'><td>" + comments[c].comment + "</td><td>" + comments[c].createdBy + "</td><td>" + comments[c].createdOn +  "</td><td></td><td></td><td></td><td><a data-id='" + comments[c].commentId + "' class='fa fa-pencil edit'></a></td></tr>";
			}
		}
	}
	
	$("#tblComments tbody").append(commentsData);
	$("#divResponse,#btnSaveDiscrepancyResponse,#btnBack").hide();
	$("#tblComments,#btnSaveDiscrepancy").show();
	
	$("#discripencyModal").modal('toggle');
});

$(document).on("click","#tblComments .edit",function(){
	commentId = $(this).attr("data-id");
	var response = $(this).closest("tr").find("td:eq(3)").text();
	if(response !== undefined && response !== null && $.trim(response).length > 0){
		$("#taResponse").val(response);
	}
	
	$("#spComment").text($(this).closest("tr").find("td:eq(0)").text());
	$("#tblComments").hide('slow',function(e){
		$("#divResponse,#btnSaveDiscrepancyResponse,#btnBack").show('slow');
		$("#btnSaveDiscrepancy,#divParameter").hide();
	});
});

$(document).on("click","#btnBack",function(e){
	e.preventDefault();
	commentId = $(this).attr("data-id");
	$("#btnSaveDiscrepancyResponse,#btnBack").hide();
	$("#divResponse").hide('slow',function(e){
		$("#tblComments").show('slow');
		$("#btnSaveDiscrepancy,#divParameter").show();
	});
});

$(document).on("click","#btnSaveDiscrepancyResponse",function(e){
	e.preventDefault();
	if($.trim($("#taResponse").val()).length === 0){
		displayMessage("error", "Enter response");	
		return;
	}
	for(var c=0;c<comments.length;c++){
		if(comments[c].commentId == commentId){
			comments[c].response = $("#taResponse").val();
		}
	}
	
	var row = $("#tblComments tbody").find("tr[data-id='" + commentId + "']");
	$(row).find("td:eq(3)").text($("#taResponse").val());
	$(row).find("td:eq(4)").text($("#navbarDropdown").text());
	$(row).find("td:eq(5)").text(getDisplayServerDate());
	$("#taResponse").val("");
	
	$("#btnSaveDiscrepancyResponse,#btnBack").hide();
	$("#divResponse").hide('slow',function(e){
		$("#tblComments").show('slow');
		$("#btnSaveDiscrepancy,#divParameter").show();
	});
});

$(document).on("click","#btnSaveResponse",function(e){
	e.preventDefault();
	var isResponseAdded = true;
	for(var c=0;c<comments.length;c++){
		if($.trim(comments[c].response).length === 0){
			isResponseAdded = false;
		}
	}
	
	if(!isResponseAdded){
		displayMessage("error", "Enter response to all comments");	
		return;
	}
	
	$("#divResponse").hide('slow',function(e){
		$("#tblComments").show('slow');
		$("#btnSaveDiscrepancy,#divParameter").show();
	});
});

