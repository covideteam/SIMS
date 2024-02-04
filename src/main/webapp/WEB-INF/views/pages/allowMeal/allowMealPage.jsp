<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
     <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Allow Meals</title>
<style type="text/css">
#allowMealsDiv {
	  display: none;
	}
#deviationCommentsDiv {
  display: none;
}

</style>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.allowmeals.sideMenu"></spring:message></h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div class='row' style="margin-right: 40%;">
						<div class='col-sm-6'>
							<spring:message code="label.allowmeals.project"></spring:message> &nbsp;&nbsp;&nbsp;
						</div>
						<div class='col-sm-6'>
							<select name="projectName" id="projectName" class="form-control validate" onchange="projectNameValidation('projectName')">
			    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
			    				<c:forEach items="${smList}" var ="sm">
									<option value="${sm.id}">${sm.projectNo}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				   <div class="col-md-12" id="allowMealsDiv">
						<table class="table table-bordered table-striped" style="width: 100%;" id="allowMealDataTable">
							<thead>
								<tr>
									<th><spring:message code="label.allowmeals.period"></spring:message></th>
									<th><spring:message code="label.allowmeals.timePoint"></spring:message></th>
									<th><spring:message code="label.allowmeals.subjects"></spring:message></th>
									<th><spring:message code="label.allowmeals.allowTime"></spring:message></th>
									<th style="font-size: 20px;"><a onclick="createAllowMealFunction()">+</a></th>
								</tr>
							</thead>
						<tbody id="allowMealRecordsData">
						
						</tbody>
					 </table>
			      </div>
				</div>
			</div>
		</div>
	</div>
	<!-- The Modal -->
  <div class="modal fade" id="myModal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Allow Meal</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="allowMealsForm"></div>
        
        <!-- Modal footer -->
       <!--  <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
        </div> -->
        
      </div>
    </div>
    </div>
    <c:url value="/allowMeal/saveAllowMealsRecord" var="saveAllowMeals"></c:url>
	<form:form action="${saveAllowMeals}" method="POST" id="allowMealsRecordDataForm">
		<input type="hidden" name="projectId" id="projectVal">
		<input type="hidden" name="periodId" id="periodVal">
		<input type="hidden" name="mealId" id="mealIdVal">
		<input type="hidden" name="subjects" id="subjectsVal" >
	    <input type="hidden" name="allowedTime" id="allowedTimeVal">
	    <input type="hidden" name="reason" id="reasonVal">
						
	</form:form>
    <script type="text/javascript">
    var allowMealsData = null;
    function projectNameValidation(id){
		debugger;
		 var value= $('#'+id).val();
		  if(value != null && value != "" && value != "undefined"){
			  $('#projectVal').val(value);
			  $("#"+id).attr('data-isvalid','true');
			  allowMealsData = synchronousAjaxCall(mainUrl+ "/allowMeal/getAllowMealsRecords/" + $("#projectName").val());
			  showAllowMealRecordsData();
		  }else{
			  $("#"+id).attr('data-errormessage','Select Project.');
			  $("#"+id).attr('data-isvalid','false');
		  }
	}
		
	function showAllowMealRecordsData(){
			var htmlStr = "";
			$('#allowMealRecordsData').empty();
			 $('#allowMealDataTable').dataTable().fnClearTable();
			$('#allowMealDataTable').dataTable().fnDraw();
			$('#allowMealDataTable').dataTable().fnDestroy(); 
			if(allowMealsData != null && allowMealsData != undefined){
				if(allowMealsData.allowMealsList != null && allowMealsData.allowMealsList != "undefined"){
					for(var i=0; i<allowMealsData.allowMealsList.length; i++){
						htmlStr +='<tr>'
						 +'<td>'+allowMealsData.allowMealsList[i].periodName+'</td>'
						 +'<td>'+allowMealsData.allowMealsList[i].tpSign+allowMealsData.allowMealsList[i].timePoint+'</td>'
						 +'<td>'+allowMealsData.allowMealsList[i].subjects+'</td>'
						 +'<td>'+allowMealsData.allowMealsList[i].allowedTime+'</td>'
						 +'<td><a class="fa fa-trash delete" title="Delete" style="font-size:15px;" onclick="deleteAllowMealsRecord('+allowMealsData.allowMealsList[i].id+')"></a></td>'
						 +'</tr>';
					}
				}
				if(htmlStr != ""){
					$('#allowMealRecordsData').append(htmlStr);
					$('#allowMealDataTable').DataTable();
					$('#allowMealsDiv').show();
				}else {
					$('#allowMealDataTable').DataTable();
					$('#allowMealsDiv').show();
				}
			}
		}
	    function createAllowMealFunction(){
	    	debugger;
	    	$('#allowMealsForm').empty();
	    	var htmlStr = '<div class="row" style="padding-bottom:10px;">'
	    	               +'<div class="col-sm-4" style="margin-top:10px;"> Period </div>'
	    	               +'<div class="col-sm-8">'
	    	               +'<select name="period" id="period" onchange="periodValidation()" class="form-control">'
	    	                 +'<option value="-1">----Select----</option>';
			    	     if(allowMealsData.spmList != undefined && allowMealsData.spmList.length > 0){
			    	    	 for(var i=0; i<allowMealsData.spmList.length; i++){
			    	    		 htmlStr += '<option value="'+allowMealsData.spmList[i].id+'">'+allowMealsData.spmList[i].periodName+'</option>';
			    	    	 }
			    	    	 
			    	     }
	    	     htmlStr +='</select>'
	    	             +'<div id="periodMsg" style="color:red;"></div></div></div>'
	    	             +'<div class="row" style="padding-bottom:10px;">'
	    	             +'<div class="col-sm-4" style="margin-top:10px;"> Timepoint </div>'
	    	             +'<div class="col-sm-8">'
	    	             +'<select name="timePoint" id="timePoint" onchange="timePointValidation()" class="form-control">'
    	                 +'<option value="-1">----Select----</option>';
				    	     if(allowMealsData.mealsList != undefined && allowMealsData.mealsList.length > 0){
				    	    	 for(var i=0; i<allowMealsData.mealsList.length; i++){
				    	    		 htmlStr += '<option value="'+allowMealsData.mealsList[i].id+'">'+allowMealsData.mealsList[i].tpSign+allowMealsData.mealsList[i].timePoint+'</option>';
				    	    	 }
				    	    	 
				    	     }
				 htmlStr +='</select>'
			    	        +'<div id="timePointMsg" style="color:red;"></div></div></div>'
			    	        +'<div class="row" style="padding-bottom:10px;">'
		    	             +'<div class="col-sm-4" style="margin-top:10px;"> Subject </div>'
		    	             +'<div class="col-sm-8">'
		    	             +'<select name="subject" id="subject" onchange="subjectValidation()" class="form-control" multiple>'
	    	                 +'<option value="-1">----Select----</option>';
					    	     if(allowMealsData.subjectList != undefined && allowMealsData.subjectList.length > 0){
					    	    	 htmlStr +='<option value="All">ALL</option>';
					    	    	 for(var i=0; i<allowMealsData.subjectList.length; i++){
					    	    		 htmlStr += '<option value="'+allowMealsData.subjectList[i].subjectNo+'">'+allowMealsData.subjectList[i].subjectNo+'</option>';
					    	    	 }
					    	    	 
					    	     }
					 htmlStr +='</select>'
				    	        +'<div id="subjectMsg" style="color:red;"></div></div></div>'
				    	        +'<div class="row" style="padding-bottom:10px;">'
				    	        +'<div class="col-sm-4"> All Before (Mins)</div>'
				    	        +'<div class="col-sm-8">'
				    	        +'<input type="number" name="allowedTime" id="allowedTime" class="form-control" onblur="allowedTimeValidation()">'
				    	        +'<div id="allowedTimeMsg" style="color:red;"></div></div></div>'
				    	        +'<div class="row" style="padding-bottom:10px;">'
				    	        +'<div class="col-sm-4"> Reason</div>'
				    	        +'<div class="col-sm-8">'
				    	        +'<textarea  name="reason" id="reason" onblur="reasonValidation()" class="form-control" style="width:100%;" cols="5" rows="5"></textarea>'
				    	        +'<div id="reasonMsg" style="color:red;"></div></div></div>'
				    	        +'<div class="row" style="padding-bottom:10px;">'
				    	        +'<div class="col-sm-12" style="text-align:center;">'
				    	        +'<input type="button" value="Submit" class="btn btn-primary btn-md" id="save_btn" onclick="submitAllowMealsForm()">'
				    	        +'</div></div>';
	    	if(htmlStr != ''){
	    		$('#allowMealsForm').append(htmlStr);
	    		$('#myModal').modal('show');
	    	}
	    	
	    }
    </script>
   <script type="text/javascript">
      function periodValidation(){
    	  var flag = false;
    	  var value = $('#period').val();
    	  if(value != null && value != "-1" && value != undefined){
    		  $('#periodVal').val(value);
    		  $("#periodMsg").html("");
    		  flag = true;
    	  }else{
    		  $('#periodVal').val("");
    		  $("#periodMsg").html('Required Field.');
			  flag = false;
			  
    	  }
    	  return flag;
      }
      function timePointValidation(){
    	  var flag = false;
    	  var value = $('#timePoint').val();
    	  if(value != null && value != "-1" && value != undefined){
    		  $('#mealIdVal').val(value);
    		  $("#timePointMsg").html("");
    		  flag = true;
    	  }else{
    		  $('#mealIdVal').val("");
    		  $("#timePointMsg").html('Required Field.');
    		  flag = false;
		  }
    	  return flag;
      }
      function subjectValidation(){
    	  var flag = false;
    	  var value = $('#subject').val();
    	  if(value != null && value != "-1" && value != undefined){
    		  $('#subjectsVal').val(value);
    		  $("#subjectMsg").html("");
    		  flag = true;
    	  }else{
    		  $('#subjectsVal').val("");
    		  $("#subjectMsg").html('Required Field.');
			  flag = false;
		  }
    	  return flag;
      }
      function allowedTimeValidation(){
    	  var flag = false;
    	  var value = $('#allowedTime').val();
    	  if(value != null && value != "" && value != undefined){
    		  $('#allowedTimeVal').val(value);
    		  $("#allowedTimeMsg").html("");
    		  flag = true;
    	  }else{
    		  $('#allowedTimeVal').val("");
    		  $("#allowedTimeMsg").html('Required Field.');
			  flag = false;
		  }
    	  return flag;
      }
      function reasonValidation(){
    	  var flag = false;
    	  var value = $('#reason').val();
    	  if(value != null && value != "" && value != undefined){
    		  $('#reasonVal').val(value);
    		  $("#reasonMsg").html("");
    		  flag = true;
    	  }else{
    		  $("#reasonMsg").html('Required Field.');
			  flag = false;
			  $('#reasonVal').val("");
    	  }
    	  return flag;
      }
      function submitAllowMealsForm(){
    	  debugger;
    	  var allowMealsDataArr = [];
    	  var periodFlag = periodValidation();
    	  var timePointFlag = timePointValidation();
    	  var subjectFlag = subjectValidation();
    	  var allowedTimeFlag = allowedTimeValidation();
    	  var reasonFlag = reasonValidation();
//     	  alert("Flags are :"+periodFlag +"::"+ timePointFlag +"::"+ subjectFlag +"::"+ allowedTimeFlag +"::"+ reasonFlag)
    	  if(periodFlag && timePointFlag && subjectFlag && allowedTimeFlag && reasonFlag){
    		  allowMealsDataArr.push({
					name: "projectId",
					value: $('#projectVal').val()
			  });
    		  allowMealsDataArr.push({
					name: "periodId",
					value: $('#periodVal').val()
			  });
    		  allowMealsDataArr.push({
					name: "subjects",
					value: $('#mealIdVal').val()
			  });
    		  allowMealsDataArr.push({
					name: "mealIdVal",
					value: $('#subjectsVal').val()
			  });
    		  allowMealsDataArr.push({
					name: "allowedTime",
					value: $('#allowedTimeVal').val()
			  });
    		  allowMealsDataArr.push({
					name: "reason",
					value: $('#reasonVal').val()
			  });
    		 $('#save_btn').removeAttr("disabled");
//     		 $('#allowMealsRecordDataForm').serialize() 
             debugger;
				var form = $('#allowMealsRecordDataForm').serializeArray();
// 				form.push(allowMealsDataArr);
				 var saveData = $.ajax({
					url : $("#mainUrl").val()+ '/allowMeal/saveAllowMealsRecord',
					type : 'POST',
					data : form,
				    success: function(e) {
				    	if (e.msgFlag === true){
				    	//	$.growl.notice({ message: e.mealsMsg});
				    	    if(e.mealsMsg == "Data Already Exists."){
				    	    	bs4Toast.primary('Error:', e.mealsMsg);
				    	    }else 
				    			bs4Toast.success('Success:', e.mealsMsg);
// 							location.reload(true);
				    		allowMealsData = synchronousAjaxCall(mainUrl+ "/allowMeal/getAllowMealsRecords/" + $("#projectName").val());
							showAllowMealRecordsData();
							$('#myModal').modal('hide');
						}else {
// 							$.growl.error({ message: msg});
							bs4Toast.error('Error:', e.mealsMsg);
							setTimeout(location.reload(), 4000); // time in milliseconds
							 $('#save_btn').removeAttr("disabled");
						}
					}
				});
				saveData.error(function() { 
// 					$.growl.error({ message: "Something went wrong"});
					bs4Toast.primary('Error:', "Something went wrong");
					 $('#save_btn').removeAttr("disabled");
				}); 
    	  }else{
    		  $('#save_btn').removeAttr("disabled");
    	  }
    	  
      }
      function deleteAllowMealsRecord(deleteId){
    	  var finalResult = synchronousAjaxCall(mainUrl+ "/allowMeal/inactiveAllowMealRecord/" +deleteId);
    	  debugger;
    	  if (finalResult.msgFlag === true){
    		  bs4Toast.success('Success:', finalResult.mealsMsg);
    		  allowMealsData = synchronousAjaxCall(mainUrl+ "/allowMeal/getAllowMealsRecords/" + $("#projectName").val());
			  showAllowMealRecordsData();
  	     }else{
			  bs4Toast.primary('Error:', finalResult.mealsMsg);
    	  }
	  }
   </script> 
</body>
</html>