<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<script type="text/javascript">
	var iteamValues = [];
	var quntityValues = [];
	var totalcalValues = [];
	var unitsValues = [];
	$('#secionTwoDiv').css("display", "none");
	$('#sectionThree3Div').css("display", "none");
	
</script>
<head>
<meta charset="ISO-8859-1">
<title>Study Group</title>
</head>
<body>
	<c:url value="/mealDiet/saveMealDietPlan" var="saveUrl"></c:url>
	<form:form action="${saveUrl}" id="saveFormVal" method="POST" modelAttribute="mdplan">
			<input type="hidden" name="iteamValuesId" id="iteamValuesId">
			<input type="hidden" name="quntityValuesId" id="quntityValuesId">
			<input type="hidden" name="totalcalValuesId" id="totalcalValuesId">
			<input type="hidden" name="unitsValuesId" id="unitsValuesId">
			<div class="row">
				<div class="col-sm-12">
					<table style="margin-left: 10%; font-size: 13px; width:80%;">
					<tr>
						<td style="width: 15%;"><spring:message code="label.mealDiet.title"></spring:message></td>
						<td style="width: 25%;"><form:input type="text" path="mealTitle" pattern="/^-?\d+\.?\d*$/" onKeyPress="if(this.value.length==200) return false;"
								id="mealTitleid" cssClass="form-control input-sm validate"
								onchange="mealTitleidFunction('mealTitleid','mealTitleidMsg')" />
							<div id="mealTitleidMsg" style="color: red;"></div>
						</td>
						<td><spring:message code="label.mealDiet.mealType"></spring:message></td>
						<td>
							<form:select path="mealType.id" id="mealTypeId"  onchange="mealTypeIdFunction('mealTypeId','mealTypeIdMsg')" cssClass="form-control input-sm validate">
								<form:option value="-1">----<spring:message
										code="label.mealDiet.Select"></spring:message>----</form:option>
								<c:forEach items="${fsdata}" var="list">
									<form:option value="${list.id}">${list.fieldValue}</form:option>
								</c:forEach>
							</form:select>
							<div id="mealTypeIdMsg" style="color: red;"></div>
						</td>
					</tr>
					</table>
				</div>
			</div>
			<br/>
			<div class="row">
				<div class="col-sm-12">
					<div id="secionTwoDiv">
						<table  style="font-size: 13px; width: 100%;">
							<tr>
								<td><spring:message code="label.mealDiet.itemName"></spring:message></td>
								<td><input type="text" name="itemName"
										id="itemNameid" class="form-control input-sm validate" pattern="/^-?\d+\.?\d*$/" onKeyPress="if(this.value.length==200) return false;"
										onchange="itemNameidFunction('itemNameid','itemNameidMsg')" />
									<div id="itemNameidMsg" style="color: red;"></div>
								</td>
								<td><spring:message code="label.mealDiet.quantity"></spring:message></td>
								<td>
									<input type="text"  name="itemQuantity" data-texttype="DECIMAL" data-deimalpoint="2" pattern="/^-?\d+\.?\d*$/" onKeyPress="if(this.value.length==6) return false;"
										id="itemQuantityid" class="form-control input-sm validate"
										onchange="itemQuantityidFunction('itemQuantityid','itemQuantityidMsg')" />
									<div id="itemQuantityidMsg" style="color: red;"></div>
								</td>
								<td><spring:message code="label.mealDiet.Units"></spring:message></td>
								<td>
									<select name="units" id="unitsId"  class="form-control validate" onchange="unitsIdFunction('unitsId','unitsIdMsg')">
										 <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
										<c:forEach items="${units}" var="list">
											<option value="${list.unitsCode}">${list.unitsCode}</option>
										</c:forEach>
									</select>
									<div id="unitsIdMsg" style="color: red;"></div>
								</td>
								<td><%-- <spring:message code="label.mealDiet.totalCalories"></spring:message> --%>Calories</td>
								<td>
									<input type="text"  name="totalCalories" data-texttype="DECIMAL" data-deimalpoint="2" pattern="/^-?\d+\.?\d*$/" onKeyPress="if(this.value.length==6) return false;"
										id="totalCaloriesid" class="form-control input-sm validate"
										onchange="totalCaloriesidFunction('totalCaloriesid','totalCaloriesidMsg')" />
									<div id="totalCaloriesidMsg" style="color: red;"></div>
								</td>
								<td>
									<button type="button" onclick="addDataFuntion()" class="btn btn-primary" id="btnupdateSubmit">
										<spring:message code="label.mealDiet.add"></spring:message></button>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<br/>	
			<div class="row">
				<div class="col-sm-12">
					<div id="sectionThree3Div">
						<div id="itemaData" style="font-size: 13px; color: red; text-align: center;"></div>
						<table id="example2" class="table table-striped table-bordered" style="font-size: 13px;"
							>
							<thead>
								<tr>
									<th><spring:message code="label.mealDiet.iteam"></spring:message></th>
									<th><spring:message code="label.mealDiet.quantity"></spring:message></th>
									<th><spring:message code="label.mealDiet.Units"></spring:message></th>
									<th><%-- <spring:message code="label.mealDiet.totalCalories"></spring:message> --%>Calories</th>
									<th><spring:message code="label.mealDiet.action"></spring:message></th>
								</tr>
							</thead>
							<tbody>
								<tr></tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</form:form>
</body>
<script type="text/javascript">
$('#btnupdateSubmit').prop("disabled", false);
		var addno = 0;
		function addDataFuntion() {
			debugger;
			var itemva = $('#itemNameid').val();
			var itemqava = $('#itemQuantityid').val();
			var totalva = $('#totalCaloriesid').val();
			var univ = $('#unitsId').val();
			
			var flag1=mealTitleidFunction('mealTitleid','mealTitleidMsg');
			var flag2=mealTypeIdFunction('mealTypeId','mealTypeIdMsg');
			var flag3=itemNameidFunction('itemNameid','itemNameidMsg');
			var flag4=itemQuantityidFunction('itemQuantityid','itemQuantityidMsg');
			var flag5=totalCaloriesidFunction('totalCaloriesid','totalCaloriesidMsg');
			var flag8=unitsIdFunction('unitsId','unitsIdMsg');
			
			if(flag1&& flag2&& flag3 && flag4 && flag5 && flag8){
			if (itemva != '' && itemva != 'undefined' && itemqava != '' && itemqava != 'undefined' && totalva != '' && totalva != 'undefined') {
				$('#itemaData').html("");
				addno++;
				var markup = '<tr id="AddRow'+addno+'">'
						+ '<td>'
						+ itemva
						+' <input name="itemva'+addno+'" value="'+itemva+'" id="itemva'+addno+'" type="hidden" class="form-control input-sm"></td>'
						+ '<td>'
						+ itemqava
						+' <input name="itemqava'+addno+'" value="'+itemqava+'" id="itemqava'+addno+'" type="hidden" class="form-control input-sm"></td>'
						+ '<td>'
						+ univ
						+' <input name="itemqava'+addno+'" value="'+univ+'" id="univ'+addno+'" type="hidden" class="form-control input-sm"></td>'
						+'<td>'
						+ totalva
						+' <input name="totalva'+addno+'" value="'+totalva+'" id="totalva'+addno+'" type="hidden" class="form-control input-sm"></td>'
						+ '<td><a class="fa fa-trash delete fa-icon" title="Delete" onclick="removeTableRowstest('
						+ addno + ')"></a></td></tr>';
				$("#example2 tbody").append(markup);
				 $('#itemNameid').val("");
				 $('#itemQuantityid').val("");
				 $('#totalCaloriesid').val("");
				 $('#unitsId').val("-1");
			} 
		  }
			if(addno > 0)
				$('#sectionThree3Div').show();
		}
		function removeTableRowstest(count) {
			debugger;
			$('#AddRow' + count).remove();
			addno--;
		}
		function submitDataFuntion() {
			$('#btnupdateSubmit').prop("disabled", true);
			debugger;
			for (var i = 1; i <= addno; i++) {
				iteamValues.push($('#itemva' + i).val());
				quntityValues.push($('#itemqava' + i).val());
				totalcalValues.push($('#totalva' + i).val());
				unitsValues.push($('#univ' + i).val());
			}
			$('#iteamValuesId').val(iteamValues);
			$('#quntityValuesId').val(quntityValues);
			$('#totalcalValuesId').val(totalcalValues);
			$('#unitsValuesId').val(unitsValues);
			var flag6=mealTitleidFunction('mealTitleid','mealTitleidMsg');
			var flag7=mealTypeIdFunction('mealTypeId','mealTypeIdMsg');
			const item1 = iteamValues.length;
			const item2 = quntityValues.length;
			const item3 = totalcalValues.length;
			const item4 = unitsValues.length;
			
			if(flag6&& flag7 && item1>0 &&item2>0 &&item3>0 &&item4>0 ){
				$('#saveFormVal').submit();
			}else {
				$('#btnupdateSubmit').prop("disabled", false);
			  $('#itemaData').html("Add menu items");
			}
			
			
		}
		var mealTitleFlag = false;
		var mealTypeFlag = false;
		function mealTitleidFunction(id, message){
			debugger;
			var flagv=false;
			$('#'+message).html("");
	  		var value = $('#'+id).val();
	  		if(value == null || value == "" ||value == "-1" || value == "undefined"){
	  			$('#'+message).html('${validationText}');
	  			flagv = false;
	  			$('#secionTwoDiv').css("display", "none");
	  			mealTitleFlag = false;
	  		}else{
	  			var urlVal=mainUrl+"/mealDiet/checkmealTitleExit/"+value;
	  			var result2=synchronousAjaxCall(urlVal);
	  			if(result2 == "Yes"){
	  				flagv = true;
	  				mealTitleFlag = true;
	  				if(mealTitleFlag && mealTypeFlag)
	  					$('#secionTwoDiv').show();
	  				else 
	  					$('#secionTwoDiv').css("display", "none");
	  			}else{
	  				$('#'+message).html('${mealtitleexists}');
		  			flagv = false;
		  			$('#secionTwoDiv').css("display", "none");
		  			mealTitleFlag = false;
	  			}
	  			
	  		}
		return flagv;
		}
		function mealTypeIdFunction(id, message){
			debugger;
			var flagv=false;
			$('#'+message).html("");
	  		var value = $('#'+id).val();
	  		if(value == null || value == "" ||value == "-1" || value == "undefined"){
	  			$('#'+message).html('${validationText}');
	  			flagv = false;
	  			$('#secionTwoDiv').css("display", "none");
	  			mealTypeFlag = false;
	  		}else{
	  			flagv = true;
	  			if(mealTitleFlag)
	  				$('#secionTwoDiv').show();
	  			else $('#secionTwoDiv').css("display", "none");
	  			mealTypeFlag = true;
	  		}
		return flagv;
		}
		function unitsIdFunction(id, message){
			debugger;
			var flagv=false;
			$('#'+message).html("");
	  		var value = $('#'+id).val();
	  		if(value == null || value == "" ||value == "-1" || value == "undefined"){
	  			$('#'+message).html('${validationText}');
	  			flagv = false;
	  		}else{
	  			flagv = true;
	  		}
		return flagv;
		}
		
		function itemNameidFunction(id, message){
			debugger;
			var flagv=false;
			$('#'+message).html("");
	  		var value = $('#'+id).val();
	  		if(value == null || value == "" ||value == "-1" || value == "undefined"){
	  			$('#'+message).html('${validationText}');
	  			flagv = false;
	  		}else{
	  			flagv = true;
	  		}
		return flagv;
		}
		function itemQuantityidFunction(id, message){
			debugger;
			var flagv=false;
			$('#'+message).html("");
	  		var value = $('#'+id).val();
	  		if(value == null || value == "" ||value == "-1" || value == "undefined"){
	  			$('#'+message).html('${validationText}');
	  			flagv = false;
	  		}else{
	  			flagv=true;
	  		}
	  		return flagv;
		
		}
		function totalCaloriesidFunction(id, message){
			debugger;
			var flagv=false;
			$('#'+message).html("");
	  		var value = $('#'+id).val();
	  		/* var mv = value.match(/^\d{0,2}(?:\.\d{0,2}){0,1}$/); */
	  		const words = value.split('.');
	  		if(value == null || value == "" ||value == "-1" || value == "undefined"){
	  			$('#'+message).html('${validationText}');
	  			flagv = false;
	  		}else{
	  			flagv=true;
	  		}
	  		return flagv;
		
		}
		
		
	</script>

</html>