$("#btnSaveDescrepancy").click(function(e){
	if($.trim($("#txtComments").val()).length === 0){
		displayMessage("error","Enter comments");	
	}
	else{
		var randomNumber = getRandomNumber();
		comments.push({ commentId: randomNumber,parameterId:parameterId,comment:$("#txtComments").val(), isTemp : true,createdBy: $("#navbarDropdown").text() });
		
		$("#tblComments tbody").append("<tr><td>" + $("#txtComments").val() + "</td><td>" + $("#navbarDropdown").text() + "</td><td>" + getDisplayServerDateAndTime() +  "</td><td></td><td></td><td></td><td><a data-id='" + randomNumber + "' class='fa fa-trash delete'></a></td></tr>");
		if(comments.length > 0){
			$('#descrSubmitTr').remove();
			$("#tblComments tbody").append("<tr id='descrSubmitTr'><td colspan='7' align='center'><input type='button' value='Submit' id='descrSubmit' onclick='submitDescripency()' class='btn btn-primary btn-md'></td></tr>");
		}
		$("#txtComments").val("");
	}
});
function modelClosingFunction(){
//	alert("comments Function");
	comments = [];
}
$(document).on("click","#tblComments .delete",function(e){
	var commentId = $(this).attr("data-id");
	$(this).closest("tr").remove();
	var index = -1;
	for(var c=0;c<comments.length;c++){
		if(comments[c].commentId.toString() === commentId.toString()){
			index = c;break;	
		}
	}
	
	if(index > -1){
		comments.splice(index,1);
	}
	if(comments.length == 0)
		$('#descrSubmitTr').remove();
});

$(document).on("click",".sendcomments",function(e){
	e.preventDefault();
	var isCommentsAdded=false;
	for(var c =0; c<comments.length;c++){
		if(comments[c].isTemp){
			isCommentsAdded=true;break;
		}
	}
	
	if(!isCommentsAdded){
		displayMessage("error","Add comments");	
	}
	else{
		saveStudyActivityReviewData($("#mainUrl").val() + '/actvityReview/sendComments');
	}
});

function saveStudyActivityReviewData(url){
	$(".loader").show();
	var formData = $('#frmActivityForm').serializeArray();
	var rowCount = 0;
	for(var i=0;i<comments.length;i++){
		formData.push({name: 'comments[' + rowCount + '].comment' ,value:  comments[i].comment});
		formData.push({name: 'comments[' + rowCount + '].parameterId' ,value:  comments[i].parameterId});
		if(!comments[i].isTemp){
			formData.push({name: 'comments[' + rowCount + '].commentId' ,value:  comments[i].commentId});
		}
	}
	
	$.ajax({
	 	url: url,
		type:'POST',
		data: formData,
		success:function(d){
			if(!d.result){
				displayMessage("error",d.message);	
				$(".loader").hide();
			}
			else{
				displayMessage("success",d.message);
				getSubjectsForReview();
				$("#activityDiv").hide('slow',function(){
			    	$("#crfReviewDiv").show('slow');	
			    });
			}
		},
		error:function(er){
		 	debugger;
		}
	});
}

$(document).on("click",".accept",function(e){
	e.preventDefault();
	var isCommentsAdded=false;
	for(var c =0; c<comments.length;c++){
		if(comments[i].isTemp){
			isCommentsAdded=true;break;
		}
	}
	var url = $("#mainUrl").val() + '/actvityReview/accept';
	if(isCommentsAdded){
		$.confirm({
		    title: 'Confirm!',
		    content: 'Comments are added to some of the parameters. Do you wish to accept the data.',
		    buttons: {
		        Yes: function () {
		        	saveStudyActivityReviewData(url);
		        },
		        No: function () {
		            
		        }
		    }
		});
	}
	else{
		saveStudyActivityReviewData(url);
	}
});

$(document).on("click",".sr-dis",function(){
	parameterId = $(this).attr("data-id");
	$("#discripencyModal").modal('toggle');
});
function submitDescripency(){
	var activityId = $('#crfName').val();
	var studyId = $('#studyName').val();
	var fieldName = $('#fieldName').val();
	var activityDataId = $('#activityDataId').val();
	var actName = $('#actName').val();
	var indexVal = $('#indexVal').val();
	var parameterId = $('#parameterId').val();
	var descComments = [];
	var comFlag = false;
	var resFlag = false;
	if(comments.length > 0){
		for(var i=0; i<comments.length; i++){
			descComments.push(comments[i].comment);
		}
	}
	if(descComments.length > 0){
			var url = mainUrl+"/reviewData/saveStaticDiscrepancy/" + studyId + "/" + activityId +"/"+actName+"/"+activityDataId+"/"+fieldName+"/"+descComments+"/"+parameterId;
//			alert(url)
			var result = synchronousAjaxCall(url);
			if(result != null && result != "" && result != undefined){
				if(result.mealsMsg == "success")
					displayMessage("success","Discrepancy Opend Successfully...!");
				else 
					displayMessage("error",  "Discrepancy Opening Failed. Please try again");
			}
			comFlag = true;
			//Flag changing purpose
			if(comFlag){
				var flagVal = $('#inProgressVal').val();
				$('#'+activityDataId+'_'+indexVal+'_image').attr('src',flagVal);
			}
			$('#discripencyModal').modal('hide');
//			generateFlagsBasedOnLabel(fieldName, indexVal);
		}
		
//	}
};