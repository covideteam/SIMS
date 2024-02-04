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

<style>
td{
width:20%;
}
.acCon{
height:45px;
}
input[type=checkbox] {
margin-right: 6px
}
</style>
</head>
<body>
 
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.durgreception.durgReceptionTitelForm"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/drugReception/updateDrugReception" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" modelAttribute="drug">
        	<form:hidden path="id" />
        	<!--  Data view-->
        	<input type="hidden" name="prodectContains" id="prodectContainsId" value="${drug.theLabelOfTheDrugProductContains}">
        	<input type="hidden" name="shipData" id="shipDataId" value="${drug.shippingCoditions}">
        	<input type="hidden" name="conSleData" id="conSleDataId" value="${drug.containerSleCondition}">
        	<input type="hidden" name="primaryData" id="primaryDataId" value="${drug.primaryContainerCondition}">
        	<input type="hidden" name="secondCondData" id="secondCondDataId" value="${drug.secondaryContainerCondition}">
        	
        	
        	
        	<!--  Data view-->
        	<table class="form-group row"><!-- class="table table-hover table-hover" --> 
        	<tr>
        	<td > 
        	<label for="projectSelect" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.projectAvailable"></spring:message></label>
        	</td>
        	<td> 
        	 
        	  <form:radiobutton  path="projectAvailable" id="projectAvailable_yes" value="Yes" onclick="projectAvailableValidation()"></form:radiobutton><label for="projectAvailable_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton  path="projectAvailable" id="projectAvailable_no" value="No" onclick="projectAvailableValidation()"></form:radiobutton><label for="projectAvailable_no"><spring:message code="label.gaNo"></spring:message></label>
			 
			  <div id="projectAvailableMsg" style="color: red;"></div>
			
        	</td>
        	</tr>
        	<tr>
        	 <td> 
        	   <label for="projectno" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.projectno"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="projectId.projectId" id="projectno" onchange="projectnoValidation('projectno','projectnoMsg'),projectnoValidationRandamization('projectno','projectnoMsg')" Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">---Select---</form:option>
        	              <c:forEach items="${plist}" var="list">
        	              <form:option value="${list.projectId}">${list.projectNo}</form:option>
        	             </c:forEach>
        	  </form:select>
                      <div id="projectnoMsg" style="color: red;"></div>
              
        	</td> 
        	<td > 
        	<label for="sponsorStudyCode" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.sponsorStudyCode"></spring:message></label>
        	</td>
        	<td> 
        	 
        	  <form:input type="text"  path="sponsorStudyCode" id="sponsorStudyCode" readonly="true"  autocomplete="off" Class="form-control " data-validate="required"/>
                      <div id="sponsorStudyCodeMsg" style="color: red;"></div>
                 
        	</td>
        	</tr>
        	<tr>
        	<td> 
        	<label for="workingAreaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.workingAreaClean"></spring:message></label>
        	</td>
        	<td> 
        	
        	   <form:radiobutton  path="workingAreaClean" id="workingAreaClean_yes" value="Yes" onclick="workingAreaCleanValidation()" ></form:radiobutton><label for="workingAreaClean_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton  path="workingAreaClean" id="workingAreaClean_no" value="No" onclick="workingAreaCleanValidation()"></form:radiobutton><label for="workingAreaClean_no"><spring:message code="label.gaNo"></spring:message></label>
			    <div id="workingAreaCleanMsg" style="color: red;"></div>
			
        	</td>
        	<td > 
        	<label for="requiredDocumentsAvailable" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.requiredDocumentsAvailable"></spring:message></label>
        	</td>
        	<td> 
        	 
        	   <form:radiobutton  path="requiredDocumentsAvailable" id="requiredDocumentsAvailable_yes" value="Yes" onclick="requiredDocumentsAvailableValidation()"></form:radiobutton><label for="requiredDocumentsAvailable_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton  path="requiredDocumentsAvailable" id="requiredDocumentsAvailable_no" value="No" onclick="requiredDocumentsAvailableValidation()"></form:radiobutton><label for="requiredDocumentsAvailable_no"><spring:message code="label.gaNo"></spring:message></label>
			  <form:radiobutton  path="requiredDocumentsAvailable" id="requiredDocumentsAvailable_na" value="NA" onclick="requiredDocumentsAvailableValidation()"></form:radiobutton><label for="requiredDocumentsAvailable_na"><spring:message code="label.gaNa"></spring:message></label>
			
			  <div id="requiredDocumentsAvailableMsg" style="color: red;"></div>
			
        	</td>
        	</tr>
        	<tr>
        	<td> 
        	<label for="workingAreaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.areaClean"></spring:message></label>
        	</td>
        	<td> 
        	
        	   <form:radiobutton  path="areaClean" id="areaClean_yes" value="Yes" onclick="areaCleanValidation()" ></form:radiobutton><label for="areaClean_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton  path="areaClean" id="areaClean_no" value="No" onclick="areaCleanValidation()"></form:radiobutton><label for="areaClean_no"><spring:message code="label.gaNo"></spring:message></label>
			    <div id="areaCleanMsg" style="color: red;"></div>
        	</td>
        	
        	</tr>
        	  <tr class="acCon">  <td><label for="applicableRegulation" class="col-form-label" style="color: #212529;"><spring:message code="label.drungreception.applicableRegulation"></spring:message></label>
        	</td>
        	<td> 
        	  
        	  <form:select  path="applicableRegulationId.id" id="applicableRegulation" onchange="applicableRegulationValidation('applicableRegulation','applicableRegulationMsg')" Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">---Select---</form:option>
        	              <c:forEach items="${regList}" var="list">
        	              <form:option value="${list.id}">${list.code}</form:option>
        	             </c:forEach>
        	  </form:select>
                      <div id="applicableRegulationMsg" style="color: red;"></div>
             
        	</td> 
        	<td><label for="drugProductType" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.drugProductType"></spring:message></label></td>
        	<td> 
        	 
        	  <form:select  path="drungProductType" id="drugProductType" onchange="drungProductTypeValidation('drugProductType','drugProductTypeMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">---Select---</form:option>
        	             <form:option value="Test">Test</form:option> 
        	             <form:option value="Reference">Reference</form:option>
        	  </form:select>
                      <div id="drugProductTypeMsg" style="color: red;"></div>
             
        	</td>
        	</tr> 
        	 
        	 <tr class="acCon"><td><label for="randomizationCode" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.randomaizationcode"></spring:message></label></td>
        	<td> 
        	  <form:select  path="randamizationCode" id="randamizationCode" onchange="randomizationCodeValidation('randamizationCode','randamizationCodeMsg')"  Class="form-control autosave reviewElement" data-validate="required">
        	             <option value="-1">---Select---</option>
        	  </form:select>
        	         <div id="randamizationCodeMsg" style="color: red;"></div>
                  </td>
                  <td> </td>
        	</tr> 
        	<tr class="acCon"><td><label for="distinctive/TradeName" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.distinctive/tradeName"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="dinstinctiveTradeName" id="dinstinctiveTradeName"   autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="dinstinctiveTradeNameMsg" style="color: red;"></div>
                   </td>
                  <td><label for="genericName" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.genericName"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="genericName" id="genericName" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="genericNameMsg" style="color: red;"></div>
                   </td>
        	</tr> 
            <tr class="acCon"><td><label for="pharmaceuticalForm" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.pharmaceuticalForm"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="pharmaceuticalForm" id="pharmaceuticalForm" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      
                  </td>
                  </tr><tr class="acCon">
                  <td><label for="strength" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.strength"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="strength" id="strength"   autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      
                   </td>
                 <td> 
        	 
        	  <form:select  path="strengthUnit.id" id="strengthUnit" onchange="strengthUnitValidation('strengthUnit','strengthMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">---Select---</form:option>
        	              <c:forEach items="${unitList}" var="list">
        	              <form:option value="${list.id}">${list.unitsCode}</form:option>
        	             </c:forEach>
        	  </form:select>
                      <div id="strengthMsg" style="color: red;"></div>
            
        	</td>
        	</tr>
        	
        	 <tr class="acCon"><td><label for="batch/LotNumber" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drungreception.batch"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="batchLotNumber" id="batchLotNumber"   autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="batchLotNumberMsg" style="color: red;"></div>
                  </td>
                  <td><label for="manufacturingDate" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.manufacturingDate"></spring:message></label></td>
        	<td>
        	  <form:input type="date"  path="manufacturingDate" id="manufacturingDate" style=" width: 83%" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="manufacturingDateMsg" style="color: red;"></div>
                  </td>
            </tr>
            
            <tr class="acCon"><td><label for="storageConditions" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.storageConditions"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="storageConditions" id="storageConditions" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="storageConditionsMsg" style="color: red;"></div>
                 </td>
                    <td><label for="degreesCelsius" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.degreesCelsius"></spring:message></label></td>
                <td> 
        	  <form:input type="text"  path="storageDegreesCelsius" id="degreesCelsius"  autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="degreesCelsiusMsg" style="color: red;"></div>
                 </td> </tr>
                  
                  <tr class="acCon"><td><label for="degreesFahrenhiet" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.degreesFahrenhiet"></spring:message></label></td>
              <td> 
        	  <form:input type="text"  path="storageDegreesFahranneit" id="degreesFahrenheit" autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="degreesFahrenhietMsg" style="color: red;"></div>
                   </td>   
                  </tr>   
                  
             <tr class="acCon"><td><label for="expirationRetestDate" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.expirationRetestDate"></spring:message></label></td>
        	<td> 
        	  <form:input type="date"  path="expirationRatestDate" id="expirationReTestDate" style="width: 83%" autocomplete="off" Class="form-control autosave reviewElement validate"  data-validate="required"/>
                      <div id="expirationReTestDateMsg" style="color: red;"></div>
                  </td> 
                  
                  </tr>   
                  <tr class="acCon"><td colspan="2"><b><label for="containersRecievedStatus"  class= "col-form-label"style ="color:#212529; colspan:2;"><spring:message code="label.drugreception.containersRecievedStatus"></spring:message></label></b></td>
                  </tr>
                  <tr class="acCon"><td><label for="totalNUmber" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.totalNumber"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="noofContainersReceived" id="totalNumber" autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="totalNumberMsg" style="color: red;"></div>
                   </td>
                  <td><label for="numberSealOpened" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.numberSealOpened"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="noofSealOpenedContainers" id="numSealOpened" autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="num(sealopened)Msg" style="color: red;"></div>
                   </td>
                  </tr><tr class="acCon">
                   <td ><label for="numberSealClosed" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.numberSealClosed"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="noofSealClosedContainers" id="numSealClosed" autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="num(sealClosed)Msg" style="color: red;"></div>
                   </td> 
                  </tr>
                  
             <tr class="acCon">
                  <td><label for="numberofContainersOpened" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.numberofContainersOpened"></spring:message></label></td>
        	<td>
        	  <form:input type="text"  path="noofContainersOpened" id="numofContainersOpened" autocomplete="off" cssClass="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="numofContainersOpenedMsg" style="color: red;"></div>
                   </td>
           <td><label for="numberofContainersNotOpened" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.numberofContainersNotOpened"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="noofContainersNotOpened" id="numofContainersNotOpened" autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="numofContainersNotOpenedMsg" style="color: red;"></div>
                   </td> 
                  
        	</tr> 
        	<tr class="acCon">
                  <td ><label for="quantityRecievedInUnits" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.quantityRecievedInUnits"></spring:message></label></td>
                  <td> 
        	  <form:input type="text"  path="quantityReceivedInUnits" id="quantityRecievedInUnits" autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="quantityRecievedInUnitsMsg" style="color: red;"></div>
                   </td>
                  <td><label for="noofUnitsAsPerProtocol" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.noofUnitsAsPerProtocol"></spring:message></label></td>
        	      <td> 
        	  <form:input type="text"  path="noofUnitsAsPerProtocol" id="noofUnitsAsPerProtocol" autocomplete="off" Class="form-control autosave reviewElement validate" data-texttype="NUMERIC" maxlength="3" data-validate="required"/>
                      <div id="noofUnitsAsPerProtocolMsg" style="color: red;"></div>
                   </td>
        	</tr>
        	<tr class="acCon"><td colspan="5"><label for="indicates" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.indicates"></spring:message></label></td>
        	</tr>
        	
        	<tr class="acCon"> <td><label for="productDescription" class= "col-form-label"  style="color:#212529;colspan:3; text-align:center; "><spring:message code ="label.drugreception.productDescription"></spring:message></label></tr>
        	 
        	  <tr class="acCon"><td><label for="pharmaceuticalForm" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.pharmaceuticalForm"></spring:message></label></td>
        	<td> 
        	  
        	  <form:select  path="productPharmaceuticalForm" id="pharmaceuticalForm" onchange="pharmaceuticalFormValidation('pharmaceuticalForm','pharmaceuticalFormMsg')" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required">
        	             <form:option value="-1">---Select---</form:option>
        	             <form:option value="Product">Product</form:option> 
        	             <form:option value="Reference">Reference</form:option>
        	  </form:select>
                      <div id="pharmaceuticalFormMsg" style="color: red;"></div>
             
        	</td>
                  <td><label for="texture/Finish" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.texture/Finish"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="textureFinish" id="textureFinish" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="textureFinishMsg" style="color: red;"></div>
                   </td>
                  </tr>
                  
                  <tr class="acCon"><td><label for="shape" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.shape"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="shape" id="shape"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="shapeMsg" style="color: red;"></div>
                   </td>
                  <td><label for="brand/Identification" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.brand/Identification"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="brandIdentification" id="brandIdentification" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="brandIdentificationMsg" style="color: red;"></div>
                  </td>
        	</tr>
        	
        	<tr class="acCon"><td><label for="colour" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.colour"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="productColor" id="colour" autocomplete="off" cssClass="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="colorMsg" style="color: red;"></div>
                   </td>
                  <td><label for="other" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.other"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="productOther" id="productOther" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="productOtherMsg" style="color: red;"></div>
                   </td>  
        	</tr>
        	
        	<tr class="acCon">
        	 <td> 
        	<label for="unitsUsed" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.unitsUsed"></spring:message></label>
        	</td>
        	<td> 
        	
        	  <form:radiobutton  path="unitsUsed" id="unitsUsed_yes" value="Yes" onclick="unitsUsedValidation()" ></form:radiobutton><label for="unitsUsed_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton  path="unitsUsed" id="unitsUsed_no" value="No" onclick="unitsUsedValidation()"></form:radiobutton><label for="unitsUsed_no"><spring:message code="label.gaNo"></spring:message></label>
			  <div id="unitsUsedMsg" style="color: red;"></div>
			
        	</td> 
            <td><label for="ifYes,No.ofUnits" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.ifYes,No.ofUnits"></spring:message></label></td>
          
        	<td> 
        	  <form:input type="text"  path="noOfUnits" id="noOfUnits" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="noOfUnitsMsg" style="color: red;"></div>
                   </td>  
                  </tr> 
                  <tr class="acCon">
                  <td><label for="comments" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.comments"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="productDescriptionComments" id="comments" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="commentsMsg" style="color: red;"></div>
                   </td></tr>
                  
              <tr class="acCon"> 
              <td rowspan="2"><label for="theLabelOfTheDrugProductContains" data-validate="required"  style="color:#212529; "><spring:message code ="label.drugreception.theLabelOfTheDrugProductContains"></spring:message></label>
                
                <td colspan="2" class="col-sm-13"> 
        	 
        	 <input type="checkbox" name="theLabelOfTheDrugProductContains" id="nameAddressandPhoneNumberoftheSponsororMainContact" value="Name Address and Phone Number of the Sponsor or Main Contact" onclick="theLabelOfTheDrugProductContainsValidation()"  required><label class="col-form-label" for="nameAddressandPhoneNumberoftheSponsororMainContact" style="color:#212529; "><spring:message code="label.drugreception.name,AddressandPhoneNumberoftheSponsororMainContact"></spring:message></label>
			
        	 </td>
			 <td> 
			  <input type="checkbox" name="theLabelOfTheDrugProductContains" id="batchLotNumberCH" value="Batch/LotNumber" onclick="theLabelOfTheDrugProductContainsValidation()" required><label for="batchLotNumberCH" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.batchLotNumber"></spring:message></label>
        	 </td>
        	 </tr>
        	 <tr class="acCon">
        	 <td colspan="2">
			  <input type="checkbox" name="theLabelOfTheDrugProductContains" id="pharmaceuticalFormandRouteofAdministration" value="Parmaceutical Form and Route of Administration" onclick="theLabelOfTheDrugProductContainsValidation()" required><label class="col-form-label" for="pharmaceuticalFormandRouteofAdministration" style="color:#212529; "><spring:message code="label.drugreception.pharmaceuticalFormandRouteofAdministration"></spring:message></label>
        	 </td>
        	  <td> 
			  <input type="checkbox" name="theLabelOfTheDrugProductContains" id="expirationRetestdateCH" value="ExpirationRetestDate" onclick="theLabelOfTheDrugProductContainsValidation()" required><label class="col-form-label" for="expirationRetestdateCH" style="color:#212529; "><spring:message code="label.drugreception.expirationRetestDate"></spring:message></label>
        	 </td>
        	 </tr>
        	 <tr class="acCon">
        	 <td></td>
        	 <td colspan="2">
			  <input type="checkbox" name="theLabelOfTheDrugProductContains" id="labelExclusiveforclinicaltrails" value="labelExclusiveforclinicaltrails.(ExceptDissolutionProfiles)" onclick="theLabelOfTheDrugProductContainsValidation()" required><label class="col-form-label" for="labelExclusiveforclinicaltrails" style="color:#212529; "><spring:message code="label.drugreception.labelExclusiveforclinicaltrails(ExceptDissolutionProfiles)"></spring:message></label>
			 </td>
			 <td>
			  <input type="checkbox" name="theLabelOfTheDrugProductContains" id="storageCondition" value="StorageCondition" onclick="theLabelOfTheDrugProductContainsValidation()" required><label for="storageCondition" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.storageCondition"></spring:message></label>
			  	  
        	 </td>
                </tr> 
                <tr class="acCon"><td colspan="6" style="text-align:center"><div id="theLabelOfTheDrugProductContainsMsg" style="color:red;"></div></td></tr>
               <tr class="acCon">
        	<td> 
        	<label for="parcelCourierReceipt" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.parcel/CourierReceipt"></spring:message></label>
        	</td>
        	<td> 
        	
        	   <form:radiobutton  path="parcelCourierReceipt" id="parcelCourierReceiptyes" value="Yes" onclick="parcelCourierReceiptValidation()" ></form:radiobutton><label for="parcelCourierReceiptyes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton  path="parcelCourierReceipt" id="parcelCourierReceiptno" value="No" onclick="parcelCourierReceiptValidation()"></form:radiobutton><label for="parcelCourierReceiptno"><spring:message code="label.gaNo"></spring:message></label>
			    <div id="parcel_CourierReceiptMsg" style="color: red;"></div>
			
        	</td>
               <td><label for="wayBillNumber" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.wayBillNumber"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="wayBillNumber" id="wayBillNumber"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="wayBillNumberMsg" style="color: red;"></div>
                   </td></tr>
                  <tr class="acCon">
                  <td> 
        	<label for="dataLogger" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.dataLogger"></spring:message></label>
        	</td>
        	<td> 
        	 
        	  <form:radiobutton  path="dataLogger" id="dataLoggeryes" value="Yes" onclick="dataLoggerValidation()"></form:radiobutton><label for="dataLoggeryes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton  path="dataLogger" id="dataLoggerno" value="No" onclick="dataLoggerValidation()"></form:radiobutton><label for="dataLoggerno"><spring:message code="label.gaNo"></spring:message></label>
			    <div id="dataLoggerMsg" style="color: red;"></div>
			
        	</td>
        	<td><label for="dataLoggerCode" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.dataLoggerCode"></spring:message></label></td>
        	<td> 
        	  <form:input type="text"  path="dataLoggerCode" id="dataLoggerCode" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="dataLoggerCodeMsg" style="color: red;"></div>
                  </td></tr>
                  
                  <tr class="acCon">
                  <td> 
        	<label for="shippingConditions" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.shippingConditions"></spring:message></label>
        	</td>
        	<td> 
        	
        	  <input type="checkbox" name="shippingCoditions" id="shippingCoditionsyes" value="Good" onclick="shippingCoditionsValidation()" required><label class="col-form-label" for="shippingCoditionsyes" style="color:#212529; "><spring:message code="label.drugreception.good"></spring:message></label>
			  <input type="checkbox" name="shippingCoditions" id="shippingCoditionsno" value="Damaged" onclick="shippingCoditionsValidation()"><label class="col-form-label" for="shippingCoditionsno" style="color:#212529; "><spring:message code="label.drugreception.damaged"></spring:message></label>
			     <div id="shippingConditionsMsg" style="color: red;"></div>
			
        	</td>
        	
        	</tr>
        	
        	 <tr class="acCon">
                  <td> 
        	<label for="containerSealCondition" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.containersealCondition"></spring:message></label>
        	</td>
        	
        	<td colspan="2"> 
        	<label>
        	   <input type="checkbox" name="containerSleCondition" id="containerSealConditionIntact" value="Intact" onclick="containerSealConditionValidation()" required ><label  for="containerSealConditionIntact" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.intact"></spring:message></label>
			  <input type="checkbox" name="containerSleCondition" id="containerSealConditionNoseal" value="NoSeal" onclick="containerSealConditionValidation()"><label  for="containerSealConditionNoseal" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.noSeal"></spring:message></label>
			  <input type="checkbox" name="containerSleCondition" id="containerSealConditionTampered" value="DtachedSealTamperd" onclick="containerSealConditionValidation()"><label for="containerSealConditionTampered" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.detachedSealTampered"></spring:message></label>
			</label>
			   <div id="containerSealConditionsMsg" style="color: red;"></div>
			
        	</td></tr>
        	
        	<tr class="acCon">
                  <td> 
        	<label for="primaryContainerCondition" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.primaryContainerCondition"></spring:message></label>
        	</td>
        	<td> 
        	
        	   <input type="checkbox" name="primaryContainerCondition" id="primaryContaineryes" value="Good" onclick="primaryContainerCoditionsValidation()" required><label for="primaryContaineryes" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.good"></spring:message></label>
			   <input type="checkbox" name="primaryContainerCondition" id="primaryContainerno" value="Damaged" onclick="primaryContainerCoditionsValidation()"><label for="primaryContainerno" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.damaged"></spring:message></label>
			    <div id="primaryContainerConditionsMsg" style="color: red;"></div>
			
        	</td></tr>
        	<tr class="acCon">
                  <td> 
        	<label for="secondaryContainerCondition" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.secondaryContainerCondition"></spring:message></label>
        	</td>
        	<td> 
        	  <input type="checkbox" name="secondaryContainerCondition" id="secondaryContainerCoditionsyes" value="Good" onclick="secondaryContainerCoditionsValidation()"><label for="secondaryContainerCoditionsyes" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.good"></spring:message></label>
			  <input type="checkbox" name="secondaryContainerCondition" id="secondaryContainerCoditionsno" value="Damaged" onclick="secondaryContainerCoditionsValidation()"><label for="secondaryContainerCoditionsno" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.damaged"></spring:message></label>
			  <input type="checkbox" name="secondaryContainerCondition" id="secondaryContainerCoditionsNA" value="NA" onclick="secondaryContainerCoditionsValidation()"><label for="secondaryContainerCoditionsNA" class="col-form-label" style="color:#212529; "><spring:message code="label.drugreception.na"></spring:message></label>
			  <div id="secondaryContainerConditionsMsg" style="color: red;"></div>
			
        	</td>
        	</tr>
        	
        	 <tr class="acCon">
                  <td> 
        	<label for="productComments" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.comments"></spring:message></label>
        	</td>
        	<td>
        	 <textarea id="productComments"  name="productContainerComments" id="productDescriptionComments" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required">${drug.productContainerComments}</textarea>
                      <div id="dataLoggerCodeMsg" style="color: red;"></div>
                   </td></tr>
        	<tr class="acCon">
                  <td> 
        	<label for="noofBoxes" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.noofBoxes"></spring:message></label>
        	</td>
        	<td> 
        	  <form:input type="text"  path="noofBoxsLabel" id="noofBoxsLabel" autocomplete="off" min="0" max="10" maxlength="10" data-texttype="NUMERIC" Class="form-control autosave reviewElement validate" data-validate="required"/>
                      <div id="noofBoxsLabelMsg" style="color: red;"></div>
                   </td>
            	<td colspan="3"><label for="labelDescriptionStorageContainer(Box)LabelsBarcodeforEachIPContainer" class= "col-form-label" style ="color:#212529;"><spring:message code="label.drugreception.labelDescriptionStorageContainer(Box)LabelsBarcodeforEachIPContainer"></spring:message></label></td>

        	</tr>
        	<!-- Documentry Requirements   -->
        	
        	  <tr class="acCon">
                  <td> 
        	<label for="quarantineRequired" class="col-form-label" style="color: #212529;"><spring:message code="label.drugreception.qurantineRequired"></spring:message></label>
        	</td>
        	<td> 
        	
        	  <form:radiobutton path="qurantineRequired" id="quarantineRequired_yes" value="Yes" onclick="quarantineRequiredValidation()" ></form:radiobutton><label for="quarantineRequired_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="qurantineRequired" id="quarantineRequired_no" value="No" onclick="quarantineRequiredValidation()"></form:radiobutton><label for="quarantineRequired_no"><spring:message code="label.gaNo"></spring:message></label>
			    <div id="quarantineRequiredMsg" style="color: red;"></div>
			
        	</td>
        	</tr>
        	<form:form  modelAttribute="document">
        	<form:hidden path="id" value="${document.id}"/>
        	<!-- Documentry Requirements   -->
        	<tr class="acCon"><td colspan="2"><b><label for="containersRecievedStatus"  class= "col-form-label"style ="color:#212529; colspan:2;"><spring:message code="label.documentaryRequirements"></spring:message></label></b></td>
                  </tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="peCertificatie" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.peCertificatie"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="peCertificatie" id="peCertificate_yes" value="Yes" onclick="peCertificateValidation()"></form:radiobutton><label for="peCertificate_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="peCertificatie" id="peCertificate_no" value="No" onclick="peCertificateValidation()"></form:radiobutton><label for="peCertificate_no"><spring:message code="label.gaNo"></spring:message></label>
			  
			  <div id="peCertificateMsg" style="color: red;"></div>
			</div>
        	</td>
        	 <td>
        	</td>
          	</tr>
          	
          	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="documentAvailable" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.ifyes,docavailable"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="documentAvailable" id="documentAvailable_yes" value="Yes" onclick="documentAvailableValidation()"></form:radiobutton><label for="documentAvailable_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="documentAvailable" id="documentAvailable_no" value="No" onclick="documentAvailableValidation()"></form:radiobutton><label for="documentAvailable_no"><spring:message code="label.gaNo"></spring:message></label>
			 
			  <div id="documentAvailableMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="assay" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.assay"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="assay" id="assay_yes" value="Yes" onclick="assayValidation()"></form:radiobutton><label for="assay_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="assay" id="assay_no" value="No" onclick="assayValidation()"></form:radiobutton><label for="assay_no"><spring:message code="label.gaNo"></spring:message></label>
			  
			  <div id="assayMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="assayReason" id="assayReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr> 
            <tr class="acCon">
        	<td colspan="2"> 
        	<label for="uniformityOfTheDosage" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.uniformityoftheDosageUnits"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="uniformityOfTheDosage" id="dosageUnitsExpressed_yes" value="Yes" onclick="dosageUnitsExpressedValidation()"></form:radiobutton><label for="dosageUnitsExpressed_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="uniformityOfTheDosage" id="dosageUnitsExpressed_no" value="No" onclick="dosageUnitsExpressedValidation()"></form:radiobutton><label for="dosageUnitsExpressed_no"><spring:message code="label.gaNo"></spring:message></label>
			 
			  <div id="dosageUnitsExpressedMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="uniformityOfTheDosageReason" id="uniformityOfTheDosageReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr> 
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="dissolution" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.dissoulution"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="dissolution" id="dissolution_yes" value="Yes" onclick="dissolutionValidation()"></form:radiobutton><label for="dissolution_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="dissolution" id="dissolution_no" value="No" onclick="dissolutionValidation()"></form:radiobutton><label for="dissolution_no"><spring:message code="label.gaNo"></spring:message></label>
			 
			  <div id="dissolutionMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="dissolutionReason" id="dissolutionReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="endorsedBySanitary" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.endorsedbySanitaryResponsibleorEquivalent"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="endorsedBySanitary" id="endorsedbySanitaryResponse_yes" value="Yes" onclick="endorsedbySanitaryResponseValidation()"></form:radiobutton><label for="endorsedbySanitaryResponse_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="endorsedBySanitary" id="endorsedbySanitaryResponse_no" value="No" onclick="endorsedbySanitaryResponseValidation()"></form:radiobutton><label for="endorsedbySanitaryResponse_no"><spring:message code="label.gaNo"></spring:message></label>
			   <form:radiobutton path="endorsedBySanitary" id="endorsedbySanitaryResponse_na" value="NA" onclick="endorsedbySanitaryResponseValidation()"></form:radiobutton><label for="endorsedbySanitaryResponse_na"><spring:message code="label.gaNa"></spring:message></label>
			 
			  <div id="endorsedbySanitaryResponseMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="endorsedBySanitaryReason" id="endorsedBySanitaryReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="productIsMoreThenPercentage" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.differenceoftheassayofthetestproductandreferenceproduct"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="productIsMoreThenPercentage" id="diffofAssayofTestandReference_yes" value="Yes" onclick="diffofAssayofTestandReferenceValidation()"></form:radiobutton><label for="diffofAssayofTestandReference_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="productIsMoreThenPercentage" id="diffofAssayofTestandReference_no" value="No" onclick="diffofAssayofTestandReferenceValidation()"></form:radiobutton><label for="diffofAssayofTestandReference_no"><spring:message code="label.gaNo"></spring:message></label>
			   <form:radiobutton path="productIsMoreThenPercentage" id="diffofAssayofTestandReference_na" value="NA" onclick="diffofAssayofTestandReferenceValidation()"></form:radiobutton><label for="diffofAssayofTestandReference_na"><spring:message code="label.gaNa"></spring:message></label>
			 
			  <div id="diffofAssayofTestandReferenceMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="productIsMoreThenPercentageReason" id="productIsMoreThenPercentageReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td> 
        	</tr>
        	 <tr class="acCon">
        	<td colspan="2"> 
        	<label for="presentationLetterOfDrugs" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.presentaionLetterofDrugs"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="presentationLetterOfDrugs" id="letterofDrugs_yes" value="Yes" onclick="letterofDrugsValidation()" ></form:radiobutton><label for="letterofDrugs_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="presentationLetterOfDrugs" id="letterofDrugs_no" value="No" onclick="letterofDrugsValidation()"></form:radiobutton><label for="letterofDrugs_no"><spring:message code="label.gaNo"></spring:message></label>
			  <div id="letterofDrugsMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="presentationLetterOfDrugsReason" id="presentationLetterOfDrugsReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="letterOfGoodManufacturing" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.letterofGoodManufacturingPracticesoftheTestProduct"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="letterOfGoodManufacturing" id="manufacturePracticeofTestProducts_yes" value="Yes" onclick="manufacturePracticeofTestProductsValidation()" ></form:radiobutton><label for="manufacturePracticeofTestProducts_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="letterOfGoodManufacturing" id="manufacturePracticeofTestProducts_no" value="No" onclick="manufacturePracticeofTestProductsValidation()"></form:radiobutton><label for="manufacturePracticeofTestProducts_no"><spring:message code="label.gaNo"></spring:message></label>
			   <form:radiobutton path="letterOfGoodManufacturing" id="manufacturePracticeofTestProducts_na" value="NA" onclick="manufacturePracticeofTestProductsValidation()"></form:radiobutton><label for="manufacturePracticeofTestProducts_na"><spring:message code="label.gaNa"></spring:message></label>
			 
			  <div id="manufacturePracticeofTestProductsMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="letterOfGoodManufacturingReason" id="letterOfGoodManufacturingReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        
        	<td colspan="2"> 
        	<label for="letterOfQualitative" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.letterofQuantitativeandQualitativeFormulaoftheTest"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="letterOfQualitative" id="qualitativeQuantitative_yes" value="Yes" onclick="qualitativeQuantitativeValidation()"></form:radiobutton><label for="qualitativeQuantitative_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="letterOfQualitative" id="qualitativeQuantitative_no" value="No" onclick="qualitativeQuantitativeValidation()"></form:radiobutton><label for="qualitativeQuantitative_no"><spring:message code="label.gaNo"></spring:message></label>
			   <form:radiobutton path="letterOfQualitative" id="qualitativeQuantitative_na" value="NA" onclick="qualitativeQuantitativeValidation()"></form:radiobutton><label for="qualitativeQuantitative_na"><spring:message code="label.gaNa"></spring:message></label>
			 
			  <div id="qualitativeQuantitativeMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="letterOfQualitativeReason" id="letterOfQualitativeReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="theReferenceProductIsTheIndicated" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theReferenceProductistheIndicatedbySanitoryAuthority"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="theReferenceProductIsTheIndicated" id="referenceProductbySanitory_yes" value="Yes" onclick="referenceProductbySanitoryValidation()" ></form:radiobutton><label for="referenceProductbySanitory_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="theReferenceProductIsTheIndicated" id="referenceProductbySanitory_no" value="No" onclick="referenceProductbySanitoryValidation()"></form:radiobutton><label for="referenceProductbySanitory_no"><spring:message code="label.gaNo"></spring:message></label>
			   <form:radiobutton path="theReferenceProductIsTheIndicated" id="referenceProductbySanitory_na" value="NA" onclick="referenceProductbySanitoryValidation()"></form:radiobutton><label for="referenceProductbySanitory_na"><spring:message code="label.gaNa"></spring:message></label>
			 
			  <div id="referenceProductbySanitoryMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theReferenceProductIsTheIndicatedReason" id="theReferenceProductIsTheIndicatedReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="referenceProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.referenceProductPurchaseInvoice"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="referenceProduct" id="purchaseInvoice_yes" value="Yes" onclick="purchaseInvoiceValidation()" ></form:radiobutton><label for="purchaseInvoice_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="referenceProduct" id="purchaseInvoice_no" value="No" onclick="purchaseInvoiceValidation()"></form:radiobutton><label for="purchaseInvoice_no"><spring:message code="label.gaNo"></spring:message></label>
			   <form:radiobutton path="referenceProduct" id="purchaseInvoice_na" value="NA" onclick="purchaseInvoiceValidation()"></form:radiobutton><label for="purchaseInvoice_na"><spring:message code="label.gaNa"></spring:message></label>
			 
			  <div id="purchaseInvoiceMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="referenceProductReason" id="referenceProductReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="theExpirationDate" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theExpirationDataCoverstheExecutionoftheStudy"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="theExpirationDate" id="expirationDate_yes" value="Yes" onclick="expirationDateValidation()" ></form:radiobutton><label for="expirationDate_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="theExpirationDate" id="expirationDate_no" value="No" onclick="expirationDateValidation()"></form:radiobutton><label for="expirationDate_no"><spring:message code="label.gaNo"></spring:message></label>
			  
			  <div id="expirationDateMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theExpirationDateReason" id="theExpirationDateReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="theQuantityOfTheDrug" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theQuantityofTheDrugisSufficienttoPerformTheStudy"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="theQuantityOfTheDrug" id="quantityoftheDrug_yes" value="Yes" onclick="quantityoftheDrugValidation()" ></form:radiobutton><label for="quantityoftheDrug_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="theQuantityOfTheDrug" id="quantityoftheDrug_no" value="No" onclick="quantityoftheDrugValidation()"></form:radiobutton><label for="quantityoftheDrug_no"><spring:message code="label.gaNo"></spring:message></label>
			 
			  <div id="quantityoftheDrugMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theQuantityOfTheDrugReason" id="theQuantityOfTheDrugReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="theBatchLotNumber" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theBatchLotNumberConsistentthroughtheDocument"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="theBatchLotNumber" id="batchLotnumConstant_yes" value="Yes" onclick="batchLotnumConstantValidation()" ></form:radiobutton><label for="batchLotnumConstant_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="theBatchLotNumber" id="batchLotnumConstant_no" value="No" onclick="batchLotnumConstantValidation()"></form:radiobutton><label for="batchLotnumConstant_no"><spring:message code="label.gaNo"></spring:message></label>
			   <div id="batchLotnumConstantMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theBatchLotNumberReason" id="theBatchLotNumberReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="transferLetter" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.transferLetterinCaseofControlledMedication"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="transferLetter" id="transferLetter_yes" value="Yes" onclick="transferLetterValidation()"></form:radiobutton><label for="transferLetter_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="transferLetter" id="transferLetter_no" value="No" onclick="transferLetterValidation()"></form:radiobutton><label for="transferLetter_no"><spring:message code="label.gaNo"></spring:message></label>
			   <form:radiobutton path="transferLetter" id="transferLetter_na" value="NA" onclick="transferLetterValidation()"></form:radiobutton><label for="transferLetter_na"><spring:message code="label.gaNa"></spring:message></label>
			 
			  <div id="transferLetterMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="transferLetterReason" id="transferLetterReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr class="acCon">
        	<td colspan="2"> 
        	<label for="proceedForAnotherProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.proceedForAnotherProduct"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <form:radiobutton path="proceedForAnotherProduct" id="productforanotherproduct_yes" value="Yes" onclick="productforanotherproductValidation()" ></form:radiobutton><label for="productforanotherproduct_yes"><spring:message code="label.gaYes"></spring:message></label>
			  <form:radiobutton path="proceedForAnotherProduct" id="productforanotherproduct_no" value="No" onclick="productforanotherproductValidation()"></form:radiobutton><label for="productforanotherproduct_no"><spring:message code="label.gaNo"></spring:message></label>
			   
			  <div id="productforanotherproductMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="proceedForAnotherProductReason" id="referenceProductReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	</form:form>
        	<tr>
 				<td colspan="4" style="text-align: center;"><input type="button" value="<spring:message code="label.submit"></spring:message>" onclick="submitFunctionData()" class="btn btn-danger btn-sm" id="addBtn"></td>
			</tr>
        	</table>
        	</form:form>
        	</div>
        	</div>
        	</div>
        	</div>
        	
        <script type="text/javascript">
        var PrjNoFlag = false;
        var RandCdvalFlag = false;
          $(document).ready(function(){
        	  var pcdata=$('#prodectContainsId').val();
        	  var arr1 = pcdata.split(',');
        	  for (index = 0; index < arr1.length; index++){
        	        var valueone=arr1[index];
        	        if(valueone=="Name Address and Phone Number of the Sponsor or Main Contact"){
        	        	$("#nameAddressandPhoneNumberoftheSponsororMainContact").prop( "checked", true );
        	        }
        	        if(valueone=="Batch/LotNumber"){
        	        	$("#batchLotNumberCH").prop( "checked", true );
        	        }
        	        if(valueone=="Parmaceutical Form and Route of Administration"){
        	        	$("#pharmaceuticalFormandRouteofAdministration").prop( "checked", true );
        	        }
        	        if(valueone=="ExpirationRetestDate"){
        	        	$("#expirationRetestdateCH").prop( "checked", true );
        	        }
        	        if(valueone=="labelExclusiveforclinicaltrails.(ExceptDissolutionProfiles)"){
        	        	$("#labelExclusiveforclinicaltrails").prop( "checked", true );
        	        }
        	        if(valueone=="StorageCondition"){
        	        	$("#storageCondition").prop( "checked", true );
        	        }
        	        
        	        /* if($('#projectAvailable_no').prop("checked")){
         				
         				$('#projectno').prop("disabled", true);
         				$('#randamizationCode').prop("disabled", true);
         			} */
        	        
        	   }
        	  var shipData=$('#shipDataId').val();
        	  var arr2 = shipData.split(',');
        	  for (index2 = 0; index2 < arr2.length; index2++){
        	        var valuetwo=arr2[index2];
        	       // alert(valuetwo);
        	        if(valuetwo=="Good"){
        	        	$("#shippingCoditionsyes").prop( "checked", true );
        	        }
        	        if(valuetwo=="Damaged"){
        	        	$("#shippingCoditionsno").prop( "checked", true );
        	        }
        	   }
        	  var conSleData=$('#conSleDataId').val();
        	  var arr3 = conSleData.split(',');
        	  for (index3 = 0; index3 < arr3.length; index3++){
        	        var value3=arr3[index3];
        	        if(value3=="Intact"){
        	        	$("#containerSealConditionIntact").prop( "checked", true );
        	        }
        	        if(value3=="NoSeal"){
        	        	$("#containerSealConditionNoseal").prop( "checked", true );
        	        }
        	        if(value3=="DtachedSealTamperd"){
        	        	$("#containerSealConditionTampered").prop( "checked", true );
        	        }
        	        
        	   }
        	  var primaryData=$('#primaryDataId').val();
        	  var arr4 = primaryData.split(',');
        	  for (index4 = 0; index4 < arr4.length; index4++){
        	        var value4=arr4[index4];
        	        if(value4=="Good"){
        	        	$("#primaryContaineryes").prop( "checked", true );
        	        }
        	        if(value4=="Damaged"){
        	        	$("#primaryContainerno").prop( "checked", true );
        	        }
        	   }
        	  var secondCondData=$('#secondCondDataId').val();
        	  var arr5 = secondCondData.split(',');
        	  for (index5 = 0; index5 < arr5.length; index5++){
        	        var value5=arr5[index5];
        	        if(value5=="Good"){
        	        	$("#secondaryContainerCoditionsyes").prop( "checked", true );
        	        }
        	        if(value5=="Damaged"){
        	        	$("#secondaryContainerCoditionsno").prop( "checked", true );
        	        }
        	        if(value5=="NA"){
        	        	$("#secondaryContainerCoditionsNA").prop( "checked", true );
        	        }
        	   }
        	  
          });
        
        $(".acCon").show();
        $("#noofBoxsLabel").on('blur', function(e) {
    	    debugger;
    	        if($(this).val() >= 10){
    	          $(this).val('10');
    	          //$('#noofBoxsLabelMsg').html("Please Enter >=10 only!......");
    	          return false;
    	          
    	        }
    	      });
        
        /* $("#noofBoxsLabel").on('change', function(e) {
    	    debugger;
    	        if($(this).val() >= 10){
    	          $(this).val('10');
    	          return false;
    	        }
    	      }); */
    	      function projectnoValidation(id, message){
          		 debugger;
          		
          		$('#sponsorStudyCode').val(result);
          		var value = $('#'+id).val();
          		if(value == null || value == "" || value == "undefined"||value==-1){
          			$('#'+message).html('${validationText}');
          			PrjNoFlag = false;
          		}else{
          			var url=mainUrl+"/pharmacyData/getSponsorCodeWithProjectId/"+value;
          			var result=synchronousAjaxCall(url);
          			if(result !="" || result!="undefined"){
          				$('#sponsorStudyCode').val(result);
          			}
          			$('#'+message).html("");
          			PrjNoFlag = true;
          		}
          		return PrjNoFlag;
          	}
        	
        	
               function workingAreaCleanValidation(){
        		
        		var valFlag = false;
        		if($('#workingAreaClean_yes').prop("checked") ||$('#workingAreaClean_no').prop("checked")){
        			$('#workingAreaCleanMsg').html("");
        			valFlag = true;
        		}else{
        			$('#workingAreaCleanMsg').html('${validationText}');
        			valFlag = false;
        		}
        		condition();
        		return valFlag;
        	}
               
               function areaCleanValidation(){
           		
           		var valFlag = false;
           		if($('#areaClean_yes').prop("checked") ||$('#areaClean_no').prop("checked")){
           			$('#areaCleanMsg').html("");
           			valFlag = true;
           		}else{
           			$('#areaCleanMsg').html('${validationText}');
           			valFlag = false;
           		}
           		condition();
           		return valFlag;
           	}
            function requiredDocumentsAvailableValidation(){
        		
        		var valFlag = false;
        		if($('#requiredDocumentsAvailable_yes').prop("checked") ||$('#requiredDocumentsAvailable_no').prop("checked")||$('#requiredDocumentsAvailable_na').prop("checked")){
        			$('#requiredDocumentsAvailableMsg').html("");
        			valFlag = true;
        		}else{
        			$('#requiredDocumentsAvailableMsg').html('${validationText}');
        			valFlag = false;
        		}
        		condition();
        		return valFlag;
        	}
        	
        	
        	function applicableRegulationValidation(id, message){
        		 debugger;
        		 var Flag = false;
        		var value = $('#'+id).val();
        		if(value == null || value == "" || value == "undefined"||value==-1){
        			$('#'+message).html('${validationText}');
        			Flag = false;
        		}else{
        			$('#'+message).html("");
        			Flag = true;
        		}
        		return Flag;
        	}
        	
        	function drungProductTypeValidation(id, message){
         		 debugger;
         		 var Flag = false;
         		var value = $('#'+id).val();
         		if(value == null || value == "" || value == "undefined"||value==-1){
         			$('#'+message).html('${validationText}');
         			Flag = false;
         		}else{
         			$('#'+message).html("");
         			Flag = true;
         		}
         		return Flag;
         	}
        	
        	function strengthUnitValidation(id, message){
         		 debugger;
         		 var Flag = false;
         		var value = $('#'+id).val();
         		if(value == null || value == "" || value == "undefined"||value==-1){
         			$('#'+message).html('${validationText}');
         			Flag = false;
         		}else{
         			$('#'+message).html("");
         			Flag = true;
         		}
         		return Flag;
         	}
        	
        	function pharmaceuticalFormValidation(id, message){
         		 debugger;
         		 var Flag = false;
         		var value = $('#'+id).val();
         		if(value == null || value == "" || value == "undefined"||value==-1){
         			$('#'+message).html('${validationText}');
         			Flag = false;
         		}else{
         			$('#'+message).html("");
         			Flag = true;
         		}
         		return Flag;
         	}
        	
        	
        	
            function unitsUsedValidation(){
        		
        		var valFlag = false;
        		if($('#unitsUsed_yes').prop("checked") ||$('#unitsUsed_no').prop("checked")){
        			$('#unitsUsedMsg').html("");
        			valFlag = true;
        		}else{
        			$('#unitsUsedMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
        	
        	
            function parcelCourierReceiptValidation(){
        		
        		var valFlag = false;
        		if($('#parcelCourierReceiptyes').prop("checked") ||$('#parcelCourierReceiptno').prop("checked")){
        			$('#parcel_CourierReceiptMsg').html("");
        			valFlag = true;
        		}else{
        			$('#parcel_CourierReceiptMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
              function dataLoggerValidation(){
        		
        		var valFlag = false;
        		if($('#dataLoggeryes').prop("checked") ||$('#dataLoggerno').prop("checked")){
        			$('#dataLoggerMsg').html("");
        			valFlag = true;
        		}else{
        			$('#dataLoggerMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
           function shippingCoditionsValidation(){
        		
        		var valFlag = false;
        		if($('#shippingCoditionsyes').prop("checked") ||$('#shippingCoditionsno').prop("checked")){
        			$('#shippingConditionsMsg').html("");
        			valFlag = true;
        		}else{
        			$('#shippingConditionsMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
             function containerSealConditionValidation(){
        		
        		var valFlag = false;
        		if($('#containerSealConditionIntact').prop("checked") ||$('#containerSealConditionNoseal').prop("checked")||$('#containerSealConditionTampered').prop("checked")){
        			$('#containerSealConditionsMsg').html("");
        			valFlag = true;
        		}else{
        			$('#containerSealConditionsMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
              function primaryContainerCoditionsValidation(){
        		
        		var valFlag = false;
        		if($('#primaryContaineryes').prop("checked") ||$('#primaryContainerno').prop("checked")){
        			$('#primaryContainerConditionsMsg').html("");
        			valFlag = true;
        		}else{
        			$('#primaryContainerConditionsMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
               function secondaryContainerCoditionsValidation(){
        		
        		var valFlag = false;
        		if($('#secondaryContainerCoditionsyes').prop("checked") ||$('#secondaryContainerCoditionsno').prop("checked")||$('#secondaryContainerCoditionsNA').prop("checked")){
        			$('#secondaryContainerConditionsMsg').html("");
        			valFlag = true;
        		}else{
        			$('#secondaryContainerConditionsMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
			function quarantineRequiredValidation(){
			        		
        		var valFlag = false;
        		if($('#quarantineRequired_yes').prop("checked") ||$('#quarantineRequired_no').prop("checked")){
        			$('#quarantineRequiredMsg').html("");
        			valFlag = true;
        		}else{
        			$('#quarantineRequiredMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
        	
			
			function theLabelOfTheDrugProductContainsValidation(){
        		
        		var valFlag = false;
        		if($('#nameAddressandPhoneNumberoftheSponsororMainContact').prop("checked") ||$('#batchLotNumberCH').prop("checked")||$('#pharmaceuticalFormandRouteofAdministration').prop("checked")||$('#labelExclusiveforclinicaltrails').prop("checked")||$('#storageCondition').prop("checked")||$('#expirationRetestdateCH').prop("checked")){
        			$('#theLabelOfTheDrugProductContainsMsg').html("");
        			valFlag = true;
        		}else{
        			$('#theLabelOfTheDrugProductContainsMsg').html('${validationText}');
        			valFlag = false;
        		}
        		return valFlag;
        	}
			
			 function condition(){
        		debugger;
        		
        		/* if($('#workingAreaClean_yes').prop("checked")==true&&$('#requiredDocumentsAvailable_yes').prop("checked")==true&&$('#areaClean_yes').prop("checked")==true){
        			$(".acCon").show();
        		}else{
        			$(".acCon").hide();
        		} */
        	} 
			 
			 
			 function peCertificateValidation(){
	        		//alert("clicked")
	        		var valFlag = false;
	        		$('#peCertificateMsg').html("");
	        		if($('#peCertificate_yes').prop("checked") ||$('#peCertificate_no').prop("checked")){
	        			valFlag = true;
	        		}else{
	        			$('#peCertificateMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	
	        	function documentAvailableValidation(){
	        	
	        		var valFlag = false;
	        		$('#documentAvailableMsg').html("");
	        		if($('#documentAvailable_yes').prop("checked") ||$('#documentAvailable_no').prop("checked")){
	        			valFlag = true;
	        		}else{
	        			$('#documentAvailableMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function assayValidation(){
	        		
	        		var valFlag = false;
	        		$('#assayMsg').html("");
	        		if($('#assay_yes').prop("checked") ||$('#assay_no').prop("checked")){
	        			valFlag = true;
	        		}else{
	        			$('#assayMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function dosageUnitsExpressedValidation(){
	        	
	        		var valFlag = false;
	        		$('#dosageUnitsExpressedMsg').html("");
	        		if($('#dosageUnitsExpressed_yes').prop("checked") ||$('#dosageUnitsExpressed_no').prop("checked")){
	        			valFlag = true;
	        		}else{
	        			$('#dosageUnitsExpressedMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function dissolutionValidation(){
	        		
	        		var valFlag = false;
	        		$('#dissolutionMsg').html("");
	        		if($('#dissolution_yes').prop("checked") ||$('#dissolution_no').prop("checked")){
	        			valFlag = true;
	        		}else{
	        			$('#dissolutionMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function endorsedbySanitaryResponseValidation(){
	        	
	        		var valFlag = false;
	        		$('#endorsedbySanitaryResponseMsg').html("");
	        		if($('#endorsedbySanitaryResponse_yes').prop("checked") ||$('#endorsedbySanitaryResponse_no').prop("checked")||$('#endorsedbySanitaryResponse_na').prop("checked")){
	        			valFlag = true;
	        		}else{
	        			$('#endorsedbySanitaryResponseMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function diffofAssayofTestandReferenceValidation(){
	        		
	        		var valFlag = false;
	        		$('#diffofAssayofTestandReferenceMsg').html("");
	        		if($('#diffofAssayofTestandReference_yes').prop("checked") ||$('#diffofAssayofTestandReference_no').prop("checked")||$('#diffofAssayofTestandReference_na').prop("checked")){
	        			valFlag = true;
	        		}else{
	        			$('#diffofAssayofTestandReferenceMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function letterofDrugsValidation(){
	        		
	        		var valFlag = false;
	        		$('#letterofDrugsMsg').html("");
	        		if($('#letterofDrugs_yes').prop("checked") ||$('#letterofDrugs_no').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#letterofDrugsMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function manufacturePracticeofTestProductsValidation(){
	        		
	        		var valFlag = false;
	        		$('#manufacturePracticeofTestProductsMsg').html("");
	        		if($('#manufacturePracticeofTestProducts_yes').prop("checked") ||$('#manufacturePracticeofTestProducts_no').prop("checked")||$('#manufacturePracticeofTestProducts_na').prop("checked")){
	        		
	        			valFlag = true;
	        		}else{
	        			$('#manufacturePracticeofTestProductsMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function qualitativeQuantitativeValidation(){
	        		
	        		var valFlag = false;
	        		$('#qualitativeQuantitativeMsg').html("");
	        		if($('#qualitativeQuantitative_yes').prop("checked") ||$('#qualitativeQuantitative_no').prop("checked")||$('#qualitativeQuantitative_na').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#qualitativeQuantitativeMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function referenceProductbySanitoryValidation(){
	        	
	        		var valFlag = false;
	        		$('#referenceProductbySanitoryMsg').html("");
	        		if($('#referenceProductbySanitory_yes').prop("checked") ||$('#referenceProductbySanitory_no').prop("checked")||$('#referenceProductbySanitory_na').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#referenceProductbySanitoryMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function purchaseInvoiceValidation(){
	        		
	        		var valFlag = false;
	        		$('#purchaseInvoiceMsg').html("");
	        		if($('#purchaseInvoice_yes').prop("checked") ||$('#purchaseInvoice_no').prop("checked")||$('#purchaseInvoice_na').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#purchaseInvoiceMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function expirationDateValidation(){
	        		
	        		var valFlag = false;
	        		$('#expirationDateMsg').html("");
	        		if($('#expirationDate_yes').prop("checked") ||$('#expirationDate_no').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#expirationDateMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function quantityoftheDrugValidation(){
	        	
	        		var valFlag = false;
	        		$('#quantityoftheDrugMsg').html("");
	        		if($('#quantityoftheDrug_yes').prop("checked") ||$('#quantityoftheDrug_no').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#quantityoftheDrugMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function batchLotnumConstantValidation(){
	        		//alert("okk");
	        		var valFlag = false;
	        		$('#batchLotnumConstantMsg').html("");
	        		if($('#batchLotnumConstant_yes').prop("checked") ||$('#batchLotnumConstant_no').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#batchLotnumConstantMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function transferLetterValidation(){
	        		
	        		var valFlag = false;
	        		$('#transferLetterMsg').html("");
	        		if($('#transferLetter_yes').prop("checked") ||$('#transferLetter_no').prop("checked")||$('#transferLetter_na').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#transferLetterMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	        	function productforanotherproductValidation(){
	        		//alert("ok")
	        		var valFlag = false;
	        		$('#productforanotherproductMsg').html("");
	        		if($('#productforanotherproduct_yes').prop("checked") ||$('#productforanotherproduct_no').prop("checked")){
	        			
	        			valFlag = true;
	        		}else{
	        			$('#productforanotherproductMsg').html("${validationText}");
	        			valFlag = false;
	        		}
	        		return valFlag;
	        	}
	           /* function projectAvailableValidation(){
	         		var valFlag = false;
	         		if($('#projectAvailable_yes').prop("checked") ||$('#projectAvailable_no').prop("checked")){
	         			$('#projectAvailableMsg').html("");
	         			valFlag = true;
	         		}else{
	         			$('#projectAvailableMsg').html('${validationText}');
	         			valFlag = false;
	         		}
	         		return valFlag;
	         	} */
	           
	           function projectAvailableValidation(){
	        	   debugger;
	         		var valFlag = false;
	         		if($('#projectAvailable_yes').prop("checked") ||$('#projectAvailable_no').prop("checked")){
	         			$('#projectAvailableMsg').html("");
	         			if($('#projectAvailable_no').prop("checked")){
	         				$('#projectno').val("-1");
	         				$('#randamizationCode').val("-1");
	         				$('#sponsorStudyCode').val("");
	         				/* $('#projectno').prop("disabled", true);
	         				$('#randamizationCode').prop("disabled", true); */
	         			}/* else{
	         				$('#projectno').prop("disabled", false);
	         				$('#randamizationCode').prop("disabled", false);
	         			} */
	         			valFlag = true;
	         		}else{
	         			$('#projectAvailableMsg').html('${validationText}');
	         			valFlag = false;
	         		}
	         		return valFlag;
	         	}
	        	 
        	  function randomizationCodeValidation(id, message){
         		 
         		var value = $('#'+id).val();
         		//alert(value);
         		if(value!= null && value!= "" && value!="undefined" &&value!="-1"){
         			//alert("ytrfdhg");
         			$('#'+message).html("");
         			RandCdvalFlag = true;
         		}else{
         			//alert("else")
         			$('#randamizationCodeMsg').html('${validationText}');
         			RandCdvalFlag = false;
         		}
         		return RandCdvalFlag;
         	 }
        	function projectnoValidationRandamization(id, message){
  	    	    var value = $('#'+id).val();
  	    	   // alert(value);
  	    	    if(value!= null || value!= "" || value!= ""||value!="-1"){
         			var url=mainUrl+"/pharmacyData/getRandamizationCodeWithProjectIdForDrugReception/"+value;
         			//alert(url);
         			var result=synchronousAjaxCall(url);
         			//alert(result);
         			if(result !="" || result!="undefined"){
         				$('#randamizationCode').empty(); 
    			 		$('#randamizationCode').append('<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>');
    					$('#randamizationCode').append(result); 
         			}
  	    	    }
         	}
        	
        	function submitFunctionData(){
        		$('#addBtn').attr('disabled','disabled');
        		validation.validate($('#saveForm')[0])
        		var areaFlaf =  areaCleanValidation();
        		var wAFlag = workingAreaCleanValidation();
        		var rDaFlag = requiredDocumentsAvailableValidation();
        		if($('#projectAvailable_yes').prop("checked")){
        			var projFlag = projectnoValidation('projectno','projectnoMsg');
        			var rancode =randomizationCodeValidation('randamizationCode','randamizationCodeMsg');
        		}else{
        			var projFlag = true;
        			var rancode = true;
        			$("#projectnoMsg").html("");
        			$("#randamizationCodeMsg").html("");
        		}
        		
        		
        		if($('#workingAreaClean_yes').prop("checked")==true&&$('#requiredDocumentsAvailable_yes').prop("checked")==true&&$('#areaClean_yes').prop("checked")==true){
        		
        		var aRFlag = applicableRegulationValidation('applicableRegulation','applicableRegulationMsg');
        		var dPTFlag = drungProductTypeValidation('drugProductType','drugProductTypeMsg');
        		var sUFlag = strengthUnitValidation('strengthUnit','strengthMsg');
        		var phrFlag = pharmaceuticalFormValidation('pharmaceuticalForm','pharmaceuticalFormMsg');
        		
        		var qRFlag = quarantineRequiredValidation()
        		var pCFLag = primaryContainerCoditionsValidation()
        		var uUFlag = unitsUsedValidation();
        		var pCRFlag = parcelCourierReceiptValidation();
        		var dLFlag = dataLoggerValidation();
        		var sCCFlag = secondaryContainerCoditionsValidation();
        		var cSCFlag = containerSealConditionValidation();
        		var sCFlag = shippingCoditionsValidation();
        		var lDPFlag = theLabelOfTheDrugProductContainsValidation();
        		
        		var peCertificate = peCertificateValidation();
        		var docAvailable = documentAvailableValidation();
        		var assay = assayValidation();
        		var dosageunits = dosageUnitsExpressedValidation();
        		var dissoultion = dissolutionValidation();
        		var endorsedsanitary = endorsedbySanitaryResponseValidation();
        		var diffAssayofTest = diffofAssayofTestandReferenceValidation();
        		var letterofdrug = letterofDrugsValidation();
        		var manufacturepractice = manufacturePracticeofTestProductsValidation();
        		var qualitaitvevalidation = qualitativeQuantitativeValidation();        		
        		var refproductysanitory = referenceProductbySanitoryValidation();
        		var purchaseinvoice = purchaseInvoiceValidation();
        		var expirationdate = expirationDateValidation();
        		var quantity = quantityoftheDrugValidation();
        		var batchlotnum = batchLotnumConstantValidation();
        		var transferletter = transferLetterValidation();
        		var prodforanotherproduct = productforanotherproductValidation();  
        		
        		debugger;
        		if(validation.validate($('#saveForm')[0])&&projFlag&&aRFlag&&dPTFlag&&sUFlag&&phrFlag&&wAFlag&&rDaFlag&&qRFlag&&pCFLag&&uUFlag&&pCRFlag&&dLFlag&&sCCFlag&&cSCFlag&&sCFlag&&areaFlaf
        				&&peCertificate&&docAvailable&&assay&&dosageunits&&dissoultion&&endorsedsanitary&&diffAssayofTest&&letterofdrug&&manufacturepractice&&qualitaitvevalidation&&refproductysanitory
        				&&purchaseinvoice&&expirationdate&&quantity&&batchlotnum&&transferletter&&prodforanotherproduct&&rancode){
        			//alert("okk working");
        			if($('#projectAvailable_yes').prop("checked")){
           			 if(PrjNoFlag ==true&& RandCdvalFlag ==true){
           				 $(".loader").show();
           			     $('#saveForm').submit();
           			    
           			 }else{
           				 $("#projectnoMsg").html('${validationText}');
           				 $("#randamizationCodeMsg").html('${validationText}');
           			 }
           			}else{
           				$(".loader").show();
           				$('#saveForm').submit();
           			}
        		 }
        		     $('#addBtn').removeAttr('disabled');
        		}
        		
        		else{
        			
        			$('#addBtn').removeAttr('disabled');
        			$.confirm({
        			    title: '<spring:message code="label.alert"></spring:message>',
        			    //icon: 'glyphicon glyphicon-heart',
        			    icon: 'fa fa-warning',
        			    type: 'red',
        			    typeAnimated: true,
        			    boxWidth: '30%',
        			    content: ' <spring:message code="label.drugListAlrtmsg"></spring:message>',
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