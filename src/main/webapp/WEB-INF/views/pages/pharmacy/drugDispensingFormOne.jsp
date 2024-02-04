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
::-webkit-input-placeholder {
  text-align: center;
  padding-top:30px;
  font-size:12px;
}

:-moz-placeholder {
  text-align: center;
}
td{
width:13%;

}
.acCon{
height:45px;
}
input[type=date] {
width:83%
}
textarea{
margin-top:10px;
}
</style>
</head>
<body>
	   
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.studyDrugDispensingForm.Title"></spring:message> </h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/studyDrugDispensing/saveStudyDrugDispensingApproval" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" modelAttribute="dispensing">
        	
        	<table  style="width: 100%;height:60%;"><!-- class="table table-hover table-hover" --> 
        	<tr>
        	 <td> 
        	   <label for="projectno" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.projectno"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="projectId.projectId" id="projectno" onchange="projectIdValidation('projectno','projectnoMsg'),projectIdValidationForPeriod('projectno','projectnoMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	              <c:forEach items="${plist}" var="list">
        	              <option value="${list.projectId}">${list.projectNo}</option>
        	             </c:forEach>
        	  </form:select>
                      <div id="projectnoMsg" style="color: red;"></div>
        	</td> 
        	<td> 
        	<label for="period" class="col-form-label" style="color: #212529;"><spring:message code="label.period"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="periodId.id" id="periodId" onchange="periodValidation('periodId','periodMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	             <%--  <c:forEach items="${plist}" var="list">
        	              <option value="${list.projectId}">${list.projectNo}</option>
        	             </c:forEach> --%>
        	  </form:select>
              <div id="periodMsg" style="color: red;"></div> 
        	</td>
        	<td><label for="randomizationCode" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.randomaizationcode"></spring:message></label></td>
        	<td> 
        	
        	 <form:select  path="randamizationCode" id="randamizationCode" onchange="randamizationCodeValidation('randamizationCode','randamizationCodeMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	  </form:select>
        	          <div id="randamizationCodeMsg" style="color: red;"></div>
                  </td>
        	</tr>
        	
        	 <tr>
        	<td><label for="workingAreaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.workingAreaClean"></spring:message></label></td>
        	<td><input type="radio" name="workingAreaClean" id="workingAreaClean_Yes"value="Yes" onclick="workingAreaCleanValidation()" required><label for="workingAreaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="workingAreaClean" id="workingAreaClean_No" value="No" onclick="workingAreaCleanValidation()"><label for="workingAreaClean_No"><spring:message code="label.gaNo"></spring:message></label>
        	<div id="workingAreaCleanMsg" style="color: red;"></div>
        	</td>
        	
        	<td colspan="2"><label for="requiredDocumentsAvailable" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.requiredDocuments"></spring:message></label></td>
        	<td><input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_yes"value="Yes" onclick="requiredDocumentsAvailableValidation()" required><label for="requiredDocumentsAvailable_yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_no" value="No" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_no"><spring:message code="label.gaNo"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_na" value="NA" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_na"><spring:message code="label.coverPage2.N/A"></spring:message></label>
        		<div id="requiredDocumentsAvailableMsg" style="color: red;"></div>
        	</td>
        	</tr><tr>
        	<td><label for="areaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.areaClear"></spring:message></label></td>
        	<td><input type="radio" name="areaClean" id="areaClean_Yes"value="Yes" onclick="areaCleanValidation()" required><label for="areaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="areaClean" id="areaClean_No" value="No" onclick="areaCleanValidation()"><label for="areaClean_No"><spring:message code="label.gaNo"></spring:message></label>
        	    <div id="areaCleanMsg" style="color: red;"></div>
        	</td>
        	<td> 
        	   <label for="typeOfProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.typeOfProduct"></spring:message></label>
        	</td>
        	<td> 
        	  <select  name="typeOfProduct" id="typeOfProduct" onchange="typeOfProductValidation('typeOfProduct','typeOfProductMsg')" class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	             <option value="Test"><spring:message code="label.ipDiscard.test"></spring:message></option> 
        	             <option value="Reference"><spring:message code="label.ipDiscard.reference"></spring:message></option>
        	  </select>
                      <span id="typeOfProductMsg" style="color: red;"></span>
        	</td> 
        	</tr> 
        	<tr>

        	<tr>
 				<td colspan="8" style="text-align: center;"><input type="button" value="<spring:message code="label.submit"></spring:message>" onclick="submitFunctionData()" class="btn btn-danger btn-sm" id="addBtn"></td>
			</tr>
        	</table>
        	</form:form>
        	</div>
        	</div>
        	</div>
        	</div>
        	
        	
        	<script type="text/javascript">
        	var projFlag = false;
        	var periodFlag = false;
        	var tpFlag = false;
        	var wAFlag = false;
        	var rDAFlag = false;
        	var aRCFlag = false;
        	var aRFlag = false;
        	
        	
        	
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
        	function projectIdValidationForPeriod(id, message){
         		 debugger;
         		var value = $('#'+id).val();
         		if(value == null || value == "" || value == "undefined"||value==-1){
         			$('#'+message).html('${validationText}');
         			projFlag = false;
         		}else{
         			var url = mainUrl+'/pharmacyData/getPeriodDataBasedonProjectId/'+$('#'+id).val();
         				var result = synchronousAjaxCall(url);
         				//alert(result);
         				if(result !="" || result !="undefined"){
         			 		$('#periodId').empty(); 
         			 		$('#periodId').append('<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>');
         					$('#periodId').append(result); 
         			 }
         				var url = mainUrl+'/pharmacyData/getRandomizationDataBasedonProjectId/'+$('#'+id).val();
         				var result = synchronousAjaxCall(url);
         				//alert(result);
         				if(result !="" || result !="undefined"){
         			 		$('#randamizationCode').empty(); 
         			 		$('#randamizationCode').append('<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>');
         					$('#randamizationCode').append(result); 
         			 }
         			$('#'+message).html("");
         			projFlag = true;
         		}
         		
         		return projFlag;
         	} 
        	
        	function periodValidation(id, message){
          		 debugger;
          		var value = $('#'+id).val();
          		//alert(value);
          		if(value == null || value == "" || value == "undefined"||value==-1){
          			$('#'+message).html('${validationText}');
          			periodFlag = false;
          		}else{
          			$('#'+message).html("");
          			periodFlag = true;
          		}
          		
          		return periodFlag;
          	} 
        	function randamizationCodeValidation(id, message){
         		 debugger;
         		var value = $('#'+id).val();
         		//alert(value);
         		if(value == null || value == "" || value == "undefined"||value==-1){
         			$('#'+message).html('${validationText}');
         			periodFlag = false;
         		}else{
         			$('#'+message).html("");
         			periodFlag = true;
         		}
         		
         		return periodFlag;
         	} 
        	
        	function typeOfProductValidation(id, message){
          		 debugger;
          		var value = $('#'+id).val();
          		if(value == null || value == "" || value == "undefined"||value==-1){
          			$('#'+message).html('${validationText}');
          			tpFlag = false;
          		}else{
          			$('#'+message).html("");
          			tpFlag = true;
          		}
          		return tpFlag;
          	} 
        	
           function workingAreaCleanValidation(){
        		
        		
        		if($('#workingAreaClean_Yes').prop("checked") ||$('#workingAreaClean_No').prop("checked")){
        			$('#workingAreaCleanMsg').html("");
        			wAFlag = true;
        		}else{
        			$('#workingAreaCleanMsg').html('${validationText}');
        			wAFlag = false;
        		}
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
        		return aRCFlag;
        	}
            
          
        	
        	function submitFunctionData(){
        		debugger;
        		var projectIdFlag = projectIdValidation('projectno','projectnoMsg');
        		var periodFlag = periodValidation('periodId','periodMsg');
        		var typeOfProductFlag = typeOfProductValidation('typeOfProduct','typeOfProductMsg');
        		var workingAreaCleanFlag = workingAreaCleanValidation();
        		var rDAFlag = requiredDocumentsAvailableValidation();
        		var areaFlag = areaCleanValidation();
        		var randCode=randamizationCodeValidation('randamizationCode','randamizationCodeMsg');
        		
                    if(validation.validate($('#saveForm')[0])&& projectIdFlag && periodFlag && typeOfProductFlag && workingAreaCleanFlag && rDAFlag&&areaFlag&&randCode){
            			//alert("okk working" + projectIdFlag);
            			$('#saveForm').submit();
            		}
        		
        		}
    		</script>
    		
</body>
</html>