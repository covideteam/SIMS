<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">

</style>
</head>
<body>
   
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.iPRetention.title"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/pharmacyData/saveIPRetention" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" modelAttribute="ippojo">
        	
        	<table  style="width: 100%;font-size: 15px;height:70%"><!-- class="table table-hover table-hover" --> 
        	<tr>
        	 <td> 
        	   <label for="projectno" class="col-form-label" style="color: #212529;"><spring:message code="label.dispensedIPHandover.projectNo"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="projectId.projectId" id="projectno" onchange="projectIdValidation('projectno','projectnoMsg')" Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	              <c:forEach items="${plist}" var="list">
        	              <option value="${list.projectId}">${list.projectNo}</option>
        	             </c:forEach>
        	  </form:select>
                      <div id="projectnoMsg" style="color: red;"></div>
        	</td> 
        	<td> 
        	   <label for="typeOfProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.ipRetention.typeOfProduct"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="typeOfProduct" id="typeOfProduct" onchange="typeOfProductValidation('typeOfProduct','typeOfProductMsg')" Class="form-control autosave reviewElement validate" data-validate="required">
        	            <form:option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</form:option>
        	             <form:option value="Test"><spring:message code="label.ipDiscard.test"></spring:message></form:option> 
        	             <form:option value="Reference"><spring:message code="label.ipDiscard.reference"></spring:message></form:option>
        	  </form:select>
                      <div id="typeOfProductMsg" style="color: red;"></div>
        	</td>
        	<td> <label for="randamizationCode" class="col-form-label" style="color: #212529;"><spring:message code="label.ipRetention.randamizationCode"></spring:message></label>
        	</td>
        	<td> 
        	  <form:input type="text"  path="randamizationCode" id="randamizationCode"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="randamizationCodeMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	<tr>
        	<td><label for="workingAreaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.ipRetention.workingAreaClean"></spring:message></label></td>
        	<td><input type="radio" name="workingAreaClean" id="workingAreaClean_Yes"value="Yes" onclick="workingAreaCleanValidation()" required><label for="workingAreaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="workingAreaClean" id="workingAreaClean_No" value="No" onclick="workingAreaCleanValidation()"><label for="workingAreaClean_No"><spring:message code="label.gaNo"></spring:message></label>
        	<div id="workingAreaCleanMsg" style="color: red;"></div>
        	</td>
        	
        	<td ><label for="requiredDocumentsAvailable" class="col-form-label" style="color: #212529;"><spring:message code="label.ipRetention.requiredDocuments"></spring:message></label></td>
        	<td><input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_yes"value="Yes" onclick="requiredDocumentsAvailableValidation()" required><label for="requiredDocumentsAvailable_yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_no" value="No" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_no"><spring:message code="label.gaNo"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_na" value="N/A" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_na"><spring:message code="label.gaNa"></spring:message></label>
        		<div id="requiredDocumentsAvailableMsg" style="color: red;"></div>
        	</td>
        	</tr>
        	<tr>
        	<td><label for="areaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.ipRetention.areaClear"></spring:message></label></td>
        	<td><input type="radio" name="areaClean" id="areaClean_Yes"value="Yes" onclick="areaCleanValidation()" required><label for="areaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="areaClean" id="areaClean_No" value="No" onclick="areaCleanValidation()"><label for="areaClean_No"><spring:message code="label.gaNa"></spring:message></label>
        	    <div id="areaCleanMsg" style="color: red;"></div>
        	</td>
        	</tr>
        	<tr>
        	<td > <label for="totalUndispensedUnits" class="col-form-label" style="color: #212529;"><spring:message code="label.ipRetention.totalUndispensedUnits"></spring:message></label>
        	</td>
        	<td> 
        	  <form:input type="text"  path="totalUndispensedUnits" id="totalUndispensedUnits"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="totalUndispensedUnitsMsg" style="color: red;"></div> 
        	</td>
        	
        	<td > <label for="totalDispensed" class="col-form-label " style="color: #212529;"><spring:message code="label.ipRetention.totalDispensed"></spring:message></label>
        	</td>
        	<td> 
        	  <form:input type="text"  path="totalDispensed" id="totalDispensed"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="totalDispensedMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	<tr>
        	<td > <label for="grandTotal" class="col-form-label" style="color: #212529;"><spring:message code="label.ipRetention.grandTotal"></spring:message></label>
        	</td>
        	<td> 
        	  <form:input type="text"  path="grandTotal" id="grandTotal"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="grandTotalMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	
        	
        	
        	<tr>
 				<td colspan="6" style="text-align: center;"><input type="button" value="<spring:message code="label.submit"></spring:message>" onclick="submitFunctionData()" class="btn btn-danger btn-sm" id="addBtn"></td>
			</tr>
			
        	</table>
        	</form:form>
        	</div>
        	</div>
        	</div>
        	</div>
        	
        	<script type="text/javascript">
        	var valueFlag = false;
        	var wAFlag = false;
        	var rDAFlag = false;
        	var aRCFlag = false;
        	function projectIdValidation(id, message){
         		 debugger;
         		var value = $('#'+id).val();
         		if(value == null || value == "" || value == "undefined"||value==-1){
         			$('#'+message).html('${validationText}');
         			projFlag = false;
         		}else{
         			$('#'+message).html("");
         			projFlag = true;
         		}
         		
         		return projFlag;
         	} 
        	
        	function typeOfProductValidation(id, message){
        		 debugger;
        		var value = $('#'+id).val();
        		if(value == null || value == "" || value == "undefined"||value==-1){
        			$('#'+message).html('${validationText}');
        			valueFlag = false;
        		}else{
        			$('#'+message).html("");
        			valueFlag = true;
        		}
        		
        		return valueFlag;
        	} 
        	
               function workingAreaCleanValidation(){
        		
        		
        		if($('#workingAreaClean_Yes').prop("checked") ||$('#workingAreaClean_No').prop("checked")){
        			$('#workingAreaCleanMsg').html("");
        			wAFlag = true;
        		}else{
        			$('#workingAreaCleanMsg').html('${validationText}');
        			wAFlag = false;
        		}
        		//condition();
        		return wAFlag;
        	}
        	
        	function requiredDocumentsAvailableValidation(){
        		
        		
        		if($('#requiredDocumentsAvailable_yes').prop("checked") ||$('#requiredDocumentsAvailable_no').prop("checked")||$('#requiredDocumentsAvailable_na').prop("checked")){
        			$('#requiredDocumentsAvailableMsg').html("");
        			rDAFlag = true;
        		}else{
        			$('#requiredDocumentsAvailableMsg').html('${validationText}');
        			rDAFlag = false;
        		}
        		//condition();
        		return rDAFlag;
        	}
              function areaCleanValidation(){
        		
        		
        		if($('#areaClean_Yes').prop("checked") ||$('#areaClean_No').prop("checked")){
        			$('#areaCleanMsg').html("");
        			aRCFlag = true;
        		}else{
        			$('#areaCleanMsg').html('${validationText}');
        			aRCFlag = false;
        		}
        		//condition();
        		return aRCFlag;
        	}
        	
        	function submitFunctionData(){
        		var projectIdFlag = projectIdValidation('projectno','projectnoMsg');
        		var tpFlag = typeOfProductValidation('typeOfProduct','typeOfProductMsg');
        		var workingAreaCleanFlag = workingAreaCleanValidation();
        		var rDAFlag = requiredDocumentsAvailableValidation();
        		var areaFlag = areaCleanValidation();
        		debugger;
        		if(validation.validate($('#saveForm')[0])&&projectIdFlag&&tpFlag&&workingAreaCleanFlag&&rDAFlag&&areaFlag){
        			//alert("okk working");
        			$('#saveForm').submit();
        		}
            }
    		</script>
</body>
</html>