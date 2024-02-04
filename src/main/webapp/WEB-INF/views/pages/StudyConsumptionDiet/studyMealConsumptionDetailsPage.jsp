<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Study Meal Consumption Diet Details</title>
<script type="text/javascript">
 var itemIdsArr = [];
 var totalQuantityArr = {};
 var totalCaloriesArr = {};
 var itemIdValArr = {};
</script>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.study.meal.consumption.diet"></spring:message></h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div class='row' style="margin-right: 40%;">
						<div class='col-sm-6'>
							<spring:message code="label.crfData.study"></spring:message> &nbsp;&nbsp;&nbsp;
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
				    <div class="col-md-12" id="mealConsumptionData">
					
			      	</div>
				</div>
			</div>
		</div>
	</div>
	<!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Meals Diet Items</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="tpItemsData"></div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>
 	<c:url value="/subMealCon/saveStudyMealConsumptionDietData" var="saveMealConsumptionDietData"></c:url>
	<form:form action="${saveMealConsumptionDietData}" method="POST" id="mealConsumptionDietDataFrom">
		<input type="hidden" name="projectId" id="projectId">
		<input type="hidden" name="mealDietConfigData" id="mealDietConfigData">
		<input type="hidden" name="mealId" id="mealId">
		<input type="hidden" name="totalLeftQuantity" id="totalLeftQuantity" value="0">
	    <input type="hidden" name="totalLeftCalories" id="totalLeftCalories" value="0">
						
	</form:form>
	<script type="text/javascript">
	    var smcData = null;
	    function projectNameValidation(id){
		debugger;
		 var value= $('#'+id).val();
		  if(value != null && value != "" && value != "undefined"){
			  $('#projectId').val(value);
			  $("#"+id).attr('data-isvalid','true');
				asynchronousAjaxCallBack(mainUrl+ "/subMealCon/getStudyRelatedMealsConsumptionDietDetails/" + $("#projectName").val(), studyMealConsumptionDietFunction);
		  }else{
			  $("#"+id).attr('data-errormessage','Select Project.');
			  $("#"+id).attr('data-isvalid','false');
		  }
		  checkElementValidation($("#"+id));
		}
		
		function studyMealConsumptionDietFunction(data){
			$(".loader").hide();
			smcData = data;
		  showMealConsumptionData();
		}
		
		function showMealConsumptionData(){
			var htmlStr = '<div class="row"> <div class="col-sm-12">'
	          +'<table class="table" style="font-size:13px; font-family:"Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;" id="mealConsumptionTable">'
	          +'<thead><tr align="center"><th>Subject Number</th>'
	          +'<th>Period</th>'
	          +'<th>Time Point</th>'
	          +'<th>Action</th>'
	          +'</tr></thead>'
	          +'<tbody>';
	          if(smcData != null && smcData != "undefined"){
	        	  if(smcData.subMealTpDataMap.length > 0){
		        	  for(var i=0; i<smcData.subMealTpDataMap.length; i++){
		        		  htmlStr +='<tr align="center">'
		        		          +'<td>'+smcData.subMealTpDataMap[i].subjectNo+'</td>'
		        		          +'<td>'+smcData.subMealTpDataMap[i].period+'</td>'
		        		          +'<td>'+smcData.subMealTpDataMap[i].sign+smcData.subMealTpDataMap[i].timePoint+'</td>'
		        		          +'<td><i class="fa fa-file" style="color:blue;" data-toggle="modal" data-target="#myModal" onclick="showTimepointItemsFunction('+"'"+smcData.subMealTpDataMap[i].mealId+"'"+', '+"'"+smcData.subMealTpDataMap[i].id+"'"+')"></i></td>'
		        		          +'</tr>';
		        	  }
		          } 
	          }
	          htmlStr += '</tbody></table></div></div>';
			 $('#mealConsumptionData').html(htmlStr);
			 var table = $('#mealConsumptionTable').DataTable({
			        searchBuilder: true,
			        "language": {
			            "lengthMenu": "${showLabel} _MENU_ ${entriesLabel}",
			    		 "search": "${searchLabel}:",
			    		 "zeroRecords": "${noDtaAvlLabel}",
			    	     "info": "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
			    	     "infoEmpty": "${noRcdsLabel}",
			    	     "infoFiltered": "(${filterLabel} _MAX_ ${totRcdsLabel})",
			    	     "paginate": {
			    	         "previous": " ${prevPgLabel}",
			    	        	 "next": "${nextLabel}"
			    	       }
			    		 }
			    });
		}
		function showTimepointItemsFunction(mealId, subjectMealId){
			$('#myModal').modal({backdrop: 'static', keyboard: false}, 'show');
			itemIdsArr =[];
			$('#totalLeftQuantity').val("");
			$('#totalLeftCalories').val("");
			$('#mealId').val(subjectMealId);
			var dietLength = Object.entries(smcData.mealDietPlanIdsMap).length;
			if(dietLength > 0){
				var dietPlanArr = smcData.mealDietPlanIdsMap[mealId];
				if(dietPlanArr.length > 0){
					for(var j=0; j<dietPlanArr.length; j++){
						var dietItemArr = smcData.mealDietItemsMap[dietPlanArr[j]];
						if(dietItemArr != null && dietItemArr != "undefined"){
							var htmlStr = '<div class="row"><div class="col-sm-12"><div id="tableMsg" style="color:red; font-weight:bold; font-size: medium; text-align: center;"></div></div></div>'
								  +'<div class="row">'
							      +' <div class="col-sm-12">'
						          +'<table class="table" style="font-size:13px; font-family:"Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;" id="mealsDietItemsTable">'
						          +'<thead><tr align="center"><th>Item Name</th>'
						          +'<th>Quantity(g/mL)</th>'
						          +'<th>Total Calories</th>'
						          +'<th>Left Quantity</th>'
						          +'<th>Left Quantity \n Calories</th>'
						          +'</tr></thead>'
						          +'<tbody>';
						    var textFlag = false;
							if(dietItemArr.length > 0){
								textFlag = true;
								$.each(dietItemArr, function( index, value ) {
									htmlStr +='<tr align="center">'
				        		          +'<td>'+value.itemName+'</td>'
				        		          +'<td>'+value.quantity+'</td>'
				        		          +'<td>'+value.totalCalries+'</td>'
				        		          +'<td><input type="number" style="width:50px;" min="0" name="leftQuantity" id="leftQuantity_'+value.id+'" onblur="validateLeftQuantity('+"'"+value.id+"'"+','+"'"+value.quantity+"'"+', '+"'"+value.totalCalries+"'"+')"><div id="leftQuantity_'+value.id+'_Msg" style="color:red;"></div></td>'
				        		          +'<td><div id="leftCalriesDiv_'+value.id+'"></div></td>'
				        		          +'</tr>';
									itemIdsArr.push(value.id);
									totalQuantityArr[value.id] = value.quantity;
									totalCaloriesArr[value.id] = value.totalCalries;
								});
							}
							htmlStr += '</tbody></table></div></div>';
							if(textFlag)
								htmlStr += '<br/><br/><div class="row"><div class="col-sm-12"><table style="width:100%;"><tr><td style="text-align:right; width:50%;">Total Left Quantity : </td><td style="text-align:left;"><div id="totalLeftQuantityDiv"></div></td></tr></table></div></div>';
							htmlStr += '<br/><div class="row"><div class="col-sm-12"><table style="width:100%;"><tr align="center"><td colspan="5"><input type="button" value="Submit" id="save_btn" class="btn btn-primary btn-md" onclick="submitMealConsumptionItemsData()"></td></tr></table></div></div>';
							$('#tpItemsData').html(htmlStr);
							var table = $('#mealsDietItemsTable').DataTable({
						        searchBuilder: true,
						        "language": {
						            "lengthMenu": "${showLabel} _MENU_ ${entriesLabel}",
						    		 "search": "${searchLabel}:",
						    		 "zeroRecords": "${noDtaAvlLabel}",
						    	     "info": "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
						    	     "infoEmpty": "${noRcdsLabel}",
						    	     "infoFiltered": "(${filterLabel} _MAX_ ${totRcdsLabel})",
						    	     "paginate": {
						    	         "previous": " ${prevPgLabel}",
						    	        	 "next": "${nextLabel}"
						    	       }
						    		 }
						    });
						}
					}
				}
			}
			
		}
		
		function validateLeftQuantity(itemId, itemQuantity, itemCalories){
			var flag = false;
			var value = $('#leftQuantity_'+itemId).val();
			if(value == null || value == "" || value == "undefined"){
				$('#leftQuantity_'+itemId+'_Msg').html("Required Field.");
				flag = false;
				$('#leftCalriesDiv_'+itemId).html("");
// 				itemIdValArr.splice(parseInt(itemId), 1);
          		delete itemIdValArr[itemId];
				calculateTotalCalories();
			}else{
				if(parseInt(value) <= parseInt(itemQuantity)){
					$('#leftQuantity_'+itemId+'_Msg').html("");
					flag = true;
					var calories = parseFloat(itemCalories)/parseInt(itemQuantity);
					if(calories != null && calories != "" && calories != "undefined"){
						var leftCalories = parseFloat(calories) * parseInt(value);
						$('#leftCalriesDiv_'+itemId).html(parseFloat(leftCalories).toFixed(2));
//	 					calculateTotalCalories(itemId, value);
						itemIdValArr[itemId] = value;
						calculateTotalCalories();
					}
				}else{
					$('#leftQuantity_'+itemId+'_Msg').html("Left Quantity Should be Less than (OR) Equal to Quantity");
					flag = false;
				}
				
			}
		}
		
	function calculateTotalCalories(){
		var itemIdValArrLength = Object.keys(itemIdValArr).length;
		$('#totalLeftQuantity').val("");
		$('#totalLeftCalories').val("");
		if(itemIdValArrLength > 0){
			for (const [key, value] of Object.entries(itemIdValArr)) {
				if(value != null && value != "" && value != "undefined"){
					var totalQuantityVal =  $('#totalLeftQuantity').val();
					if(totalQuantityVal != null && totalQuantityVal != "" && totalQuantityVal != "undefined")
						totalQuantityVal = parseInt(totalQuantityVal)+ parseInt(value);
					else totalQuantityVal = parseInt(value);
					var totalCaloriesVal = $('#totalLeftCalories').val();
					var oneCaloriesVal = parseFloat(totalCaloriesArr[key]) / parseInt(totalQuantityArr[key]);
					if(totalCaloriesVal != null && totalCaloriesVal != "" && totalCaloriesVal != "undefined")
						totalCaloriesVal = parseFloat(parseFloat(totalCaloriesVal) + parseFloat(parseInt(value) * oneCaloriesVal)).toFixed(2);
					else 
						totalCaloriesVal = parseFloat((parseInt(value) * oneCaloriesVal)).toFixed(2);
					$('#totalLeftQuantity').val(totalQuantityVal);
					$('#totalLeftCalories').val(totalCaloriesVal);
				}
			}
		}
		var leftQuantityItemsTotal = $('#totalLeftQuantity').val();
		$('#totalLeftQuantityDiv').html(leftQuantityItemsTotal);
	}
	var mealsConsumptionDataArr = [];
	function submitMealConsumptionItemsData(){
// 		$('#save_btn').attr("disabled", true);
		mealsConsumptionDataArr = [];
		var flag = true;
		$('#mealDietConfigData').val("");
		if(itemIdsArr != null && itemIdsArr.length > 0){
			var itemIdValArrLength = Object.keys(itemIdValArr).length;
			if(itemIdValArrLength > 0){
				$.each(itemIdsArr, function( index, value ) {
					  var itemVal = itemIdValArr[value];
					  if(itemVal == null || itemVal == "" || itemVal == "undefined"){
						  $('#leftQuantity_'+value+'_Msg').html("Required Field.");
						  flag = false;
					  }else {
						  var oneCaloriesVal = parseFloat(totalCaloriesArr[value]) / parseInt(totalQuantityArr[value]);
						  var caloriesData = parseFloat((parseInt(itemVal) * oneCaloriesVal)).toFixed(2);
						  var str = value+"@@@"+itemVal+"@@@"+caloriesData;
						  mealsConsumptionDataArr.push(str);
						  $('#leftQuantity_'+value+'_Msg').html("");
					  }
				});
			}else
				flag = false;
		}else
			flag = false;
		if(flag == false){
// 			$('#save_btn').attr("disabled", true);
			$('#tableMsg').html("Required All Items Left Quantity Data. Please Check And Try Again.");
			validateLeftQuantityFunction();
		}else 
			$('#tableMsg').html("");
		if(flag){
			debugger;
			if(mealsConsumptionDataArr.length > 0){
				$('#mealDietConfigData').val(mealsConsumptionDataArr);
// 				$('#mealConsumptionDietDataFrom').submit();
                debugger;
				var stdMealConsumptionDietData = [];
				stdMealConsumptionDietData.push({
					name: "projectId",
					value: $("#projectId").val()
				});
				stdMealConsumptionDietData.push({
					name : "mealId",
					value : $('#mealId').val()
				});
				stdMealConsumptionDietData.push({
					name : "mealDietConfigData",
					value : $('#mealDietConfigData').val()
				});
				stdMealConsumptionDietData.push({
					name : "totalLeftCalories",
					value : $('#totalLeftCalories').val()
				});
				
// 				$('#mealConsumptionDietDataFrom').serialize() 
				var form = $('#mealConsumptionDietDataFrom').serializeArray();
				form.push(stdMealConsumptionDietData);
				var saveData = $.ajax({
					url : $("#mainUrl").val()+ '/subMealCon/saveStudyMealConsumptionDietData',
					type : 'POST',
					data : form,
				    success: function(e) {
				    	if (e.msgFlag === true){
				    		$.growl.notice({ message: e.mealsMsg});
// 				    		displayMessage("success", e.mealsMsg);
							location.reload(true);
						}else {
							$.growl.error({ message: msg});
							setTimeout(location.reload(), 4000); // time in milliseconds
// 							displayMessage("error", e.mealsMsg);
						}
					}
				});
				saveData.error(function() { 
					$.growl.error({ message: "Something went wrong"});
// 					displayMessage("error", "Something went wrong"); 
				});
			}
		}
	}
	function validateLeftQuantityFunction(){
		$.each(itemIdsArr, function( index, value ) {
			  var itemVal = itemIdValArr[value];
			  if(itemVal == null || itemVal == "" || itemVal == "undefined"){
				  $('#leftQuantity_'+value+'_Msg').html("Required Field.");
				  flag = false;
			  }else  $('#leftQuantity_'+value+'_Msg').html("");
		});
	}
	</script>
	
</body>
</html>