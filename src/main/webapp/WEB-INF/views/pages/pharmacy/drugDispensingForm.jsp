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
					<h2><spring:message code="label.studyDrugDispensing.Title"></spring:message> </h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/studyDrugDispensing/saveStudyDrugDispensing" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" modelAttribute="dispensing">
        	<form:input type="hidden" path="id"></form:input>
        	<form:input type="hidden" path="projectId.projectId"></form:input>
        	<form:input type="hidden" path="periodId.id"></form:input>
        	<form:input type="hidden" path="typeOfProduct"></form:input>
        	<form:input type="hidden" path="applicableRegulationId.id"></form:input>
        	<form:input type="hidden" path="strengthUnitId.id"></form:input>
        	<form:input type="hidden" path="workingAreaClean"></form:input>
        	<form:input type="hidden" path="requiredDocumentsAvailable"></form:input>
        	<form:input type="hidden" path="areaClean"></form:input>
        	
        	<table  style="width: 100%;height:60%;"><!-- class="table table-hover table-hover" --> 
        	<tr>
        	 <td> 
        	   <label for="projectno" class="col-form-label"  style="color: #212529;"><spring:message code="label.drungreception.projectno"></spring:message></label>
        	</td>
        	<td> <%-- ${dispensing.projectId.projectNo} --%>
        	   <form:select  path="projectId.projectId" id="projectno" disabled="true"  onblur="projectIdValidation('projectno','projectnoMsg')"   Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="1">----<spring:message code="label.ruleSelect"></spring:message>----</form:option>
        	              <c:forEach items="${plist}" var="list">
        	              <form:option value="${list.projectId}">${list.projectNo}</form:option>
        	             </c:forEach>
        	  </form:select> 
                    
        	</td> 
        	<td> 
        	<label for="period" class="col-form-label" style="color: #212529;"><spring:message code="label.period"></spring:message></label>
        	</td>
        	<td> 
        	<input type="text" name="periodVal" value="${dispensing.periodId.periodName}" readonly="true" class="form-control" />
        	 <%--  <form:select  path="periodId.id" id="periodId" disabled="true" onblur="periodValidation('periodId','periodMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="1">---Select---</option>
        	              <c:forEach items="${plist}" var="list">
        	              <option value="${list.projectId}">${list.projectNo}</option>
        	             </c:forEach>
        	  </form:select> --%>
              <div id="periodMsg" style="color: red;"></div> 
        	</td>
        	<td><label for="randomizationCode" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.randomaizationcode"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="randamizationCode" id="randomizationCode" readonly="true" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="randomizationCodeMsg" style="color: red;"></div>
                  </td>
        	</tr>
        	
        	 <tr>
        	<td><label for="workingAreaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.workingAreaClean"></spring:message></label></td>
        	<td><input type="radio" name="workingAreaClean" id="workingAreaClean_Yes"value="Yes" disabled="disabled" onclick="workingAreaCleanValidation()" checked="checked"><label for="workingAreaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="workingAreaClean" id="workingAreaClean_No" value="No" disabled="disabled" onclick="workingAreaCleanValidation()"><label for="workingAreaClean_No"><spring:message code="label.gaNo"></spring:message></label>
        	<div id="workingAreaCleanMsg" style="color: red;"></div>
        	</td>
        	
        	<td colspan="2"><label for="requiredDocumentsAvailable" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.requiredDocuments"></spring:message></label></td>
        	<td><input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_yes" disabled="disabled" value="Yes" checked="checked" onclick="requiredDocumentsAvailableValidation()" required><label for="requiredDocumentsAvailable_yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_no" disabled="disabled" value="No" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_no"><spring:message code="label.gaNo"></spring:message></label>
        		<input type="radio" name="requiredDocumentsAvailable" id="requiredDocumentsAvailable_na" disabled="disabled" value="NA" onclick="requiredDocumentsAvailableValidation()"><label for="requiredDocumentsAvailable_na"><spring:message code="label.coverPage2.N/A"></spring:message></label>
        		<div id="requiredDocumentsAvailableMsg" style="color: red;"></div>
        	</td>
        	</tr><tr>
        	<td><label for="areaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.areaClear"></spring:message></label></td>
        	<td><input type="radio" name="areaClean" id="areaClean_Yes"value="Yes" disabled="disabled" onclick="areaCleanValidation()" checked="checked" required><label for="areaClean_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="areaClean" id="areaClean_No" value="No" disabled="disabled" onclick="areaCleanValidation()"><label for="areaClean_No"><spring:message code="label.gaNo"></spring:message></label>
        	    <div id="areaCleanMsg" style="color: red;"></div>
        	</td>
        	<td> 
        	   <label for="typeOfProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.typeOfProduct"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="typeOfProduct" id="typeOfProduct" disabled="true" onblur="typeOfProductValidation('typeOfProduct','typeOfProductMsg')" Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</form:option>
        	             <form:option value="Test"><spring:message code="label.ipDiscard.test"></spring:message></form:option> 
        	             <form:option value="Reference"><spring:message code="label.ipDiscard.reference"></spring:message></form:option>
        	  </form:select>
                      <span id="typeOfProductMsg" style="color: red;"></span>
        	</td> 
        	</tr> 
        	<tr>
        	
<%--         	<td colspan="3"><b><label for="note" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.note_reviewFields"></spring:message></label></b></td>
 --%>        	</tr>
        	
        	<tr class="acCon">
        	<td><label for="applicableRegulationId" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.applicationRegulation"></spring:message></label></td>
        	
        	<td> 
        	  <form:select  path="applicableRegulationId.id" id="applicableRegulationId" disabled="true" onblur="applicableRegulationValidation('applicableRegulationId','applicableRegulationIdMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	            <form:option value="1">----<spring:message code="label.ruleSelect"></spring:message>----</form:option>
        	              <c:forEach items="${regList}" var="list">
        	              <form:option value="${list.id}">${list.name}</form:option>
        	             </c:forEach>
        	  </form:select>
                      <div id="applicableRegulationIdMsg" style="color: red;"></div>
        	</td> 
        	<td><label for="sponsorStudyCode" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.sponsorStudyCode"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="sponsorStudyCode" id="sponsorStudyCode" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="sponsorStudyCodeMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td><label for="dinstinctiveTradeName" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.distinctive"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="dinstinctiveTradeName" id="dinstinctiveTradeName" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="dinstinctiveTradeNameMsg" style="color: red;"></div> 
        	</td>
        	<td><label for="genericName" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.genericName"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="genericName" id="genericName" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="genericNameMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	
        	<tr class="acCon">
        	<td><label for="pharmaceuticalForm" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.pharmaceuticalForm"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="pharmaceuticalForm" id="pharmaceuticalForm" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="dinstinctiveTradeNameMsg" style="color: red;"></div> 
        	</td>
        	<td><label for="strength" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.strength"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="strength" id="strength" readonly="true" data-texttype="NUMERIC" maxlength="3" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="genericNameMsg" style="color: red;"></div> 
        	</td>
        	<td>
        	<form:select  path="strengthUnitId.id" id="strengthUnitId" disabled="true" onblur="strengthUnitValidation('strengthUnitId','strengthUnitMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</form:option>
        	             <c:forEach items="${unitList}" var="list">
        	              <form:option value="${list.id}">${list.unitsCode}</form:option>
        	             </c:forEach>
        	  </form:select>
        	   <div id="strengthUnitMsg" style="color: red;"></div>
        	</td>
        	</tr>
        	
        	<tr class="acCon">
        	<td><label for="specialCondition" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.specialCondition"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="specialCondition" id="specialCondition" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="specialConditionMsg" style="color: red;"></div> 
        	</td>
        	<td><label for="batchLotNumber" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.batch"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="batchLotNumber" id="batchLotNumber" readonly="true" data-texttype="NUMERIC" maxlength="3" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="genericNameMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	
        	<tr class="acCon">
        	<td><label for="manufacturingDate" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.manufacturingDate"></spring:message></label></td>
        	<td> 
        	  <form:input type="date"  path="manufacturingDate" id="manufacturingDate" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="specialConditionMsg" style="color: red;"></div> 
        	</td>
        	</tr><tr class="acCon">
        	<td><label for="storageConditions" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.storageConditions"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="storageConditions" id="storageConditions" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="genericNameMsg" style="color: red;"></div> 
        	</td>
        	<td><label for="storageDegreesCelsius" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.degreesCelsius"></spring:message></label></td>
        	
        	<td> <form:input type="text"  path="storageDegreesCelsius" id="storageDegreesCelsius" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	 <td><label for="storageDegreesFahranneit" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.degreesFahrenheat"></spring:message></label></td>
        	
        	<td> <form:input type="text"  path="storageDegreesFahranneit" id="storageDegreesFahranneit" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	
        	</tr>
        	
        	<tr class="acCon">
        	<td><label for="expirationRatestDate" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.expiration"></spring:message></label></td>
        	<td> 
        	  <form:input type="date"  path="expirationRatestDate" id="expirationRatestDate" readonly="true"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="expirationRatestDateMsg" style="color: red;"></div> 
        	</td>
        	<td><label for="dose" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.dose"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="dose" id="dose"  autocomplete="off" data-texttype="NUMERIC" maxlength="3" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="doseMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td><label for="methodOfDispensing" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.methodOfDispensing"></spring:message></label></td>
            <td colspan="5" >
            <form:textarea id="methodOfDispensing" path="methodOfDispensing" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required" style="height:90px" ></form:textarea>
            </td>
        	</tr>
        	
        	<tr class="acCon">
        	<td colspan="2"><label for="methodOfDispensingAttachIfAny" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.methodattachIfAny"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="methodOfDispensingAttachIfAny" id="methodOfDispensingAttachIfAny"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="methodOfDispensingAttachIfAnyMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td ><label for="rhOfTheArea" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.rHOfTheArea"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="rhOfTheArea" id="rhOfTheArea"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="rhOfTheAreaMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td ><label for="noOfSubjects" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.noOfSubjects"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="noOfSubjects" id="noOfSubjects" data-texttype="NUMERIC" maxlength="3"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="noOfSubjectsMsg" style="color: red;"></div> 
        	</td>
        	<td ><label for="subjectsForUnitsDispensed" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.noOfUnitsDispensed"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="subjectsForUnitsDispensed" id="subjectsForUnitsDispensed" data-texttype="NUMERIC" maxlength="3" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="subjectsForUnitsDispensedMsg" style="color: red;"></div> 
        	</td>
        	<td>
        	<form:select  path="subjectsUnitId.id" id="subjectsUnitId" onblur="subjectsUnitValidation('subjectsUnitId','subjectsUnitMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	              <c:forEach items="${unitList}" var="list">
        	              <option value="${list.id}">${list.unitsCode}</option>
        	             </c:forEach>
        	  </form:select>
        	  <div id="subjectsUnitMsg" style="color: red;"></div>
        	</td>
        	
        	</tr>
        	<tr class="acCon">
        	<td ><label for="noOfStandBy" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.noOfStandBy"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="noOfStandBy" id="noOfStandBy" data-texttype="NUMERIC" maxlength="3"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="noOfStandByMsg" style="color: red;"></div> 
        	</td>
        	<td ><label for="standByForUnitsDispensed" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.noOfUnitsDispensed"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="standByForUnitsDispensed" id="standByForUnitsDispensed" data-texttype="NUMERIC" maxlength="3" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="standByForUnitsDispensedMsg" style="color: red;"></div> 
        	</td>
        	<td>
        	<form:select  path="standByUnitId.id" id="standByUnitId" onblur="standByUnitValidation('standByUnitId','standByUnitMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	              <c:forEach items="${unitList}" var="list">
        	              <option value="${list.id}">${list.unitsCode}</option>
        	             </c:forEach>
        	  </form:select>
        	  <div id="standByUnitMsg" style="color: red;"></div>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td></td><td></td>
        	<td ><label for="noOfUnits" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.noOfUnits"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="noOfUnits" id="noOfUnits"  autocomplete="off" data-texttype="NUMERIC" maxlength="3" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="noOfUnitsMsg" style="color: red;"></div> 
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td></td><td></td>
        	<td colspan="4"><label for="noteofDispansing" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.noteofDispansing"></spring:message></label></td>
        	
        	</tr>
        	
        	<tr class="acCon">
        	<td><label for="subjectNumbers" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.subjectNumbers"></spring:message></label></td>
            <td colspan="5" >
            <form:textarea id="subjectNumbers" path="subjectNumbers" placeholder="Scan dispensing container to display subject numbers"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required" style="height:90px" ></form:textarea>
            </td>
        	</tr>
        	
        	<tr class="acCon">
        	<td><label for="comments" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.comments"></spring:message></label></td>
            <td colspan="5" >
            <form:textarea id="comments" path="comments" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required" style="height:90px" ></form:textarea>
            </td>
        	</tr>
        	
        	<tr class="acCon">
        	<td colspan="2"><label for="dispensingCompleted" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.dispensing"></spring:message></label></td>
        	<td><input type="radio" name="dispensingCompleted" id="dispensingCompleted_Yes"value="Yes" onclick="dispensingCompletedValidation()" required><label for="dispensingCompleted_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="dispensingCompleted" id="dispensingCompleted_No" value="No" onclick="dispensingCompletedValidation()"><label for="dispensingCompleted_No"><spring:message code="label.gaNo"></spring:message></label>
        	<div id="dispensingCompletedMsg" style="color: red;"></div>
        	</td>
        	</tr> 
        	<tr class="acCon">
        	<td colspan="2"><label for="noOfBoxes" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.noOfBoxes"></spring:message></label></td>
        	<td > 
        	  <form:input type="text"  path="noOfBoxes" id="noOfBoxes"  autocomplete="off" data-texttype="NUMERIC" maxlength="3" Class="form-control autosave reviewElement validate" data-validate="required"/>
              <div id="noOfBoxesMsg" style="color: red;"></div> 
        	</td>
        	<td colspan="4"><label for="label" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.unitStorageLabel"></spring:message></label></td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"><label for="toBeStoredInPharmacy" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.storedInPharmacy"></spring:message></label></td>
        	<td><input type="radio" name="toBeStoredInPharmacy" id="toBeStoredInPharmacy_Yes"value="Yes" onclick="toBeStoredInPharmacyValidation()" required><label for="toBeStoredInPharmacy_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="toBeStoredInPharmacy" id="toBeStoredInPharmacy_No" value="No" onclick="toBeStoredInPharmacyValidation()"><label for="toBeStoredInPharmacy_No"><spring:message code="label.gaNo"></spring:message></label>
        	    <div id="toBeStoredInPharmacyMsg" style="color: red;"></div>
        	</td>
        	</tr> 
        	<tr class="acCon">
        	<td colspan="2"><label for="proceedForAnotherProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.storedInPharmacy"></spring:message></label></td>
        	<td><input type="radio" name="proceedForAnotherProduct" id="proceedForAnotherProduct_Yes"value="Yes" onclick="proceedForAnotherProductValidation()" required><label for="proceedForAnotherProduct_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="proceedForAnotherProduct" id="proceedForAnotherProduct_No" value="No" onclick="proceedForAnotherProductValidation()"><label for="proceedForAnotherProduct_No"><spring:message code="label.gaNo"></spring:message></label>
        	    <div id="proceedForAnotherProductMsg" style="color: red;"></div>
        	</td>
        	</tr> 
        	
        	
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
        	$(".acCon").show();
        	
        	$("#noOfBoxes").on('blur', function(e) {
        	    debugger;
        	        if($(this).val() >= 10){
        	          $(this).val('10');
        	          return false;
        	        }
        	      });
        	
        	
        	function condition(){
        		debugger;
        		
        		if($('#workingAreaClean_Yes').prop("checked")==true&&$('#requiredDocumentsAvailable_yes').prop("checked")==true&&$('#areaClean_Yes').prop("checked")==true){
        			$(".acCon").show();
        		}else{
        			$(".acCon").hide();
        		}
        	};
        	
        	function projectIdValidation(id, message){
          		 debugger;
          		var value = $('#'+id).val();
          		if(value == null || value == "" || value == "undefined"||value==-1){
          			$('#'+message).html('${validationText}');
          			projFlag = true;
          		}else{
          			$('#'+message).html("");
          			projFlag = true;
          		}
          		
          		return projFlag;
          	} 
        	
        	function periodValidation(id, message){
          		 debugger;
          		var value = $('#'+id).val();
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
        		condition();
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
        		condition();
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
        		condition();
        		return aRCFlag;
        	}
            
            function applicableRegulationValidation(id, message){
          		 debugger;
          		var value = $('#'+id).val();
          		if(value == null || value == "" || value == "undefined"||value==-1){
          			$('#'+message).html('${validationText}');
          			aRFlag = false;
          		}else{
          			$('#'+message).html("");
          			aRFlag = true;
          		}
          		return aRFlag;
          	}   
            
            function strengthUnitValidation(id, message){
         		 debugger;
         		 var strengthFlag = false;
         		var value = $('#'+id).val();
         		if(value == null || value == "" || value == "undefined"||value==-1){
         			$('#'+message).html('${validationText}');
         			strengthFlag = false;
         		}else{
         			$('#'+message).html("");
         			strengthFlag = true;
         		}
         		return strengthFlag;
         	}
            function subjectsUnitValidation(id, message){
        		 debugger;
        		 var subFlag = false;
        		var value = $('#'+id).val();
        		if(value == null || value == "" || value == "undefined"||value==-1){
        			$('#'+message).html('${validationText}');
        			subFlag = false;
        		}else{
        			$('#'+message).html("");
        			subFlag = true;
        		}
        		return subFlag;
        	}
            function standByUnitValidation(id, message){
       		 debugger;
       		var standByFlag = false;
       		var value = $('#'+id).val();
       		if(value == null || value == "" || value == "undefined"||value==-1){
       			$('#'+message).html('${validationText}');
       			standByFlag = false;
       		}else{
       			$('#'+message).html("");
       			standByFlag = true;
       		}
       		return standByFlag;
       	}
            
              function dispensingCompletedValidation(){
        		
        		var valFlag = false;
        		if($('#dispensingCompleted_Yes').prop("checked") ||$('#dispensingCompleted_No').prop("checked")){
        			$('#dispensingCompletedMsg').html("");
        			valFlag = true;
        		}else{
        			$('#dispensingCompletedMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
              
			  function toBeStoredInPharmacyValidation(){
				
				var valFlag = false;
				if($('#toBeStoredInPharmacy_Yes').prop("checked") ||$('#toBeStoredInPharmacy_No').prop("checked")){
					$('#toBeStoredInPharmacyMsg').html("");
					valFlag = true;
				}else{
					$('#toBeStoredInPharmacyMsg').html('${validationText}');
					valFlag = false;
				}
				return valFlag;
			}
			  
			function proceedForAnotherProductValidation(){
				
				var valFlag = false;
				if($('#proceedForAnotherProduct_Yes').prop("checked") ||$('#proceedForAnotherProduct_No').prop("checked")){
					$('#proceedForAnotherProductMsg').html("");
					valFlag = true;
				}else{
					$('#proceedForAnotherProductMsg').html('${validationText}');
					valFlag = false;
				}
				return valFlag;
			}
        	
        	function submitFunctionData(){
        		//var projectIdFlag = projectIdValidation('projectno','projectnoMsg');
        		var projectIdFlag =true;
        		//var periodFlag = periodValidation('periodId','periodMsg');
        		var periodFlag =true;
        		var typeOfProductFlag = typeOfProductValidation('typeOfProduct','typeOfProductMsg');
        		var workingAreaCleanFlag = workingAreaCleanValidation();
        		var rDAFlag = requiredDocumentsAvailableValidation();
        		var areaFlag = areaCleanValidation();
        		debugger;
        		if($('#workingAreaClean_Yes').prop("checked")==true&&$('#requiredDocumentsAvailable_yes').prop("checked")==true&&$('#areaClean_Yes').prop("checked")==true){
        			
            		var sUFlag = strengthUnitValidation('strengthUnitId','strengthUnitMsg');
            		var aRFlag  = applicableRegulationValidation('applicableRegulationId','applicableRegulationIdMsg');
            		var subUFlag = subjectsUnitValidation('subjectsUnitId','subjectsUnitMsg');
                    var stanfByFlag = standByUnitValidation('standByUnitId','standByUnitMsg');
                    var dsCFlag = dispensingCompletedValidation();
                    var toBSFlag = toBeStoredInPharmacyValidation();
                    var prFFlag = proceedForAnotherProductValidation();
                    if(validation.validate($('#saveForm')[0])&& projectIdFlag && periodFlag && typeOfProductFlag && workingAreaCleanFlag && rDAFlag &&sUFlag&&areaFlag&&aRFlag&&subUFlag&&stanfByFlag&&dsCFlag&&toBSFlag&&prFFlag){
            			//alert("okk working" + projectIdFlag);
            			$('#saveForm').submit();
            		}
        		
        		}else{
        		$.confirm({
    			    title: 'Alert!',
    			    //icon: 'glyphicon glyphicon-heart',
    			    icon: 'fa fa-warning',
    			    type: 'red',
    			    typeAnimated: true,
    			    boxWidth: '30%',
    			    content: ' Select all as yes...',
    			    useBootstrap: false,
    			    buttons: {
    			       Ok: function(){
    				    	/*  $('#useridval').val(value);
    		   				 $('#acountdisable').submit(); */ 
    		   			 /* $.alert({
    						    title: 'Alert!',
    						    icon: 'fa fa-warning',
    						    useBootstrap: false,
    						    boxWidth: '30%',
    						    content: ' '+user+' Account deleted!',
    						}); */
    		   				
    			       },Cancel: function () {
    			    	   
    			        }
    					    
    			    }
    			});
        		}
        		
            }
    		</script>
    		
</body>
</html>