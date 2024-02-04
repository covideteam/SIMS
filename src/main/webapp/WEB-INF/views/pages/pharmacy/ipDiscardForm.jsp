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
td{
height:45px;
}
</style>
</head>
<body>

	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.ipDiscard.titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/pharmacyData/saveIPDiscard" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" modelAttribute="ippojo">
        	
        	<table  style="width: 100%;font-size: 15px;height:70%"><!-- class="table table-hover table-hover" --> 
        	<tr>
        	 <td> 
        	   <label for="projectno" class="col-form-label" style="color: #212529;"><spring:message code="label.ipDiscard.projectNo"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="projectId.projectId" id="projectno" onchange="projectIdValidation('projectno','projectnoMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	              <c:forEach items="${plist}" var="list">
        	              <option value="${list.projectId}">${list.projectNo}</option>
        	             </c:forEach>
        	  </form:select>
                      <div id="projectnoMsg" style="color: red;"></div>
        	</td> 
        	<td> 
        	   <label for="typeOfProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.ipDiscard.typeOfProduct"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="typeOfProduct" id="typeOfProduct" onchange="typeOfProductValidation('typeOfProduct','typeOfProductMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</form:option>
        	             <form:option value="Test"><spring:message code="label.ipDiscard.test"></spring:message></form:option> 
        	             <form:option value="Reference"><spring:message code="label.ipDiscard.reference"></spring:message></form:option>
        	  </form:select>
              <div id="typeOfProductMsg" style="color: red;"></div>
        	</td>
        	<td><label for="randomizationCode" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.randomaizationcode"></spring:message></label></td>
        	<td> <div>
        	  <form:input type="text"  path="randamizationCode" id="randomizationCode" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="randomizationCodeMsg" style="color: red;"></div>
                  </div> </td>
        	</tr>
        	<tr>
        	<td> 
        	   <label for="purpose" class="col-form-label" style="color: #212529;"><spring:message code="label.ipDiscard.purpose"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="purpose" id="purpose" onchange="purposeValidation('purpose','purposeMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	             <option value="1"><spring:message code="label.ipDiscard.discard"></spring:message></option>
        	             <option value="1"><spring:message code="label.ipDiscard.return"></spring:message></option>
        	             <option value="1"><spring:message code="label.ipDiscard.otherProj"></spring:message></option>
        	  </form:select>
              <div id="purposeMsg" style="color: red;"></div>
        	</td>
        	
        	</tr>
        	<tr>
        	<td><label for="workingAreaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.ipDiscard.workingAreaClean"></spring:message></label></td>
        	<td><input type="radio" name="workingAreaClean" id="workingAreaClean_Yes"value="Yes" onclick="workingAreaCleanValidation()" required><label for="workingAreaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="workingAreaClean" id="workingAreaClean_No" value="No" onclick="workingAreaCleanValidation()"><label for="workingAreaClean_No"><spring:message code="label.gaNa"></spring:message></label>
        	<div id="workingAreaCleanMsg" style="color: red;"></div>
        	</td>
        	
        	<td colspan="2"><label for="requiredDocumentsAvailable" class="col-form-label" style="color: #212529;"><spring:message code="label.ipDiscard.requiredDocuments"></spring:message></label></td>
        	<td><input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_yes"value="Yes" onclick="requiredDocumentsAvailableValidation()" required><label for="requiredDocumentsAvailable_yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_no" value="No" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_no"><spring:message code="label.gaNo"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_na" value="NA" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_na"><spring:message code="label.gaNa"></spring:message></label>
        		<div id="requiredDocumentsAvailableMsg" style="color: red;"></div>
        	</td>
        	</tr><tr>
        	<td><label for="areaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.ipDiscard.areaClear"></spring:message></label></td>
        	<td><input type="radio" name="areaClean" id="areaClean_Yes"value="Yes" onclick="areaCleanValidation()" required><label for="areaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="areaClean" id="areaClean_No" value="No" onclick="areaCleanValidation()"><label for="areaClean_No"><spring:message code="label.gaNa"></spring:message></label>
        	    <div id="areaCleanMsg" style="color: red;"></div>
        	</td>
        	</tr> 
        	<tr>
        	<td><label for="projectNoToBeTransfered" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.projectNoToBeTransfered"></spring:message></label></td>
        	<td> 
        	 <form:select  path="projectIdTran.projectId" id="projectIdTran" onchange="projectNoTransValidation('projectIdTran','projectNoToBeTransferedMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	              <c:forEach items="${plist}" var="list">
        	              <option value="${list.projectId}">${list.projectNo}</option>
        	             </c:forEach>
        	  </form:select>
              <div id="projectNoToBeTransferedMsg" style="color: red;"></div>
        	</td>
        	 <td><label for="ifOtherProj" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.ifOtherProj"></spring:message></label></td>
        	</tr>
        	<tr>
        	<td><label for="quantity" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.quantity"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="quantity" id="quantity" data-texttype="NUMERIC" maxlength="3"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="quantityMsg" style="color: red;"></div>
            </td>
            <td><label for="quantityVerified" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.quantityVerified"></spring:message></label></td>
            <td><input type="radio" name="quantityVerified" id="quantityVerified_Yes"value="Yes" onclick="quantityVerifiedValidation()" required><label for="quantityVerified_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="quantityVerified" id="quantityVerified_No" value="No" onclick="quantityVerifiedValidation()"><label for="quantityVerified_No"><spring:message code="label.gaNa"></spring:message></label>
        	    <div id="quantityVerifiedMsg" style="color: red;"></div>
        	</td>
        	</tr>
        	<tr>
        	<td><label for="noOfUnitsTransferred" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.noOfUnitsTransferred"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="noOfUnitsTransferred" id="noOfUnitsTransferred" data-texttype="NUMERIC" maxlength="3"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="noOfUnitsTransferredMsg" style="color: red;"></div>
            </td>
            <td><label for="quantityVerified" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.ifOtherProj"></spring:message></label></td>
        	</tr>
        	<tr>
        	<td><label for="comments" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.comments"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="comments" id="comments" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="commentsMsg" style="color: red;"></div>
            </td>
        	</tr>
        	<tr>
        	<td><label for="attachRecordIfAny" class= "col-form-label" style ="color:#212529;"><spring:message code="label.ipDiscard.attachRecord"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="attachRecordIfAny" id="attachRecordIfAny" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="attachRecordMsg" style="color: red;"></div>
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
        	function projectIdValidation(id, message){
          		 debugger;
          		 var projFlag = false;
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
        		 var tpFlag = false;
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
        	
        	
        	function purposeValidation(id, message){
       		 debugger;
       		var pFlag = false;
       		var value = $('#'+id).val();
       		if(value == null || value == "" || value == "undefined"||value==-1){
       			$('#'+message).html('${validationText}');
       			pFlag = false;
       		}else{
       			$('#'+message).html("");
       			pFlag = true;
       		}
       		return pFlag;
       	}
        	
        	function workingAreaCleanValidation(){
        		
        		var wAFlag = false;
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
        		
        		var rDAFlag = false;
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
        		var aRCFlag = false;;
        		
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
           	
           	function projectNoTransValidation(id, message){
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
        	
              function quantityVerifiedValidation(){
        		
        		var valueFlag = false;
        		if($('#quantityVerified_Yes').prop("checked") ||$('#quantityVerified_No').prop("checked")){
        			$('#quantityVerifiedMsg').html("");
        			valueFlag = true;
        		}else{
        			$('#quantityVerifiedMsg').html('${validationText}');
        			valueFlag = false;
        		}
        		//condition();
        		return valueFlag;
        	}
        	
        	function submitFunctionData(){
        		var valueFlag= quantityVerifiedValidation();
        		var aRCFlag = areaCleanValidation();
        		var rDAFlag = requiredDocumentsAvailableValidation();
        		var wAFlag = workingAreaCleanValidation();
        		var projFlag = projectIdValidation('projectno','projectnoMsg');
        		var tpFlag = typeOfProductValidation('typeOfProduct','typeOfProductMsg');
        		var pFlag = purposeValidation('purpose','purposeMsg');
        		var projFlag = projectNoTransValidation('projectIdTran','projectNoToBeTransferedMsg');
        		debugger;
        		if(validation.validate($('#saveForm')[0])&&valueFlag&&projFlag&&rDAFlag&&wAFlag&&aRCFlag&&tpFlag&&projFlag&&pFlag){
        			//alert("okk working");
        			$('#saveForm').submit();
        		}
            }
    		</script>
</body>
</html>