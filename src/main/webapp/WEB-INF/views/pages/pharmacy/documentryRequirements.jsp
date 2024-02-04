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
</head>
<body oncontextmenu="return false;">
<c:if test="${not empty pageMessage}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('success', '${pageMessage}');
	   		</script>
	   </c:if>
	   <c:if test="${not empty pageError}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('error', '${pageError}');
	   		</script>
	   </c:if>
	   
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.documentryRequirements.Titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/documentryRequ/saveDocumentryRequ" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" modelAttribute="document">
        	
        	<table class="table table-hover table-hover" ><!-- class="table table-hover table-hover" --> 
        	<tr>
        	<td > 
        	<label for="peCertificatie" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.peCertificatie"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="peCertificatie" id="peCertificate_yes" value="Yes" onclick="peCertificateValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="peCertificatie" id="peCertificate_no" value="No" onclick="peCertificateValidation()"><spring:message code="label.gaNo"></spring:message>
			  
			  <div id="peCertificateMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="documentAvailable" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.ifyes,docavailable"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="documentAvailable" id="documentAvailable_yes" value="Yes" onclick="documentAvailableValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="documentAvailable" id="documentAvailable_no" value="No" onclick="documentAvailableValidation()"><spring:message code="label.gaNo"></spring:message>
			 
			  <div id="documentAvailableMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="assay" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.assay"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="assay" id="assay_yes" value="Yes" onclick="assayValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="assay" id="assay_no" value="No" onclick="assayValidation()"><spring:message code="label.gaNo"></spring:message>
			  
			  <div id="assayMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="assayReason" id="assayReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="uniformityOfTheDosage" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.uniformityoftheDosageUnits"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="uniformityOfTheDosage" id="dosageUnitsExpressed_yes" value="Yes" onclick="dosageUnitsExpressedValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="uniformityOfTheDosage" id="dosageUnitsExpressed_no" value="No" onclick="dosageUnitsExpressedValidation()"><spring:message code="label.gaNo"></spring:message>
			 
			  <div id="dosageUnitsExpressedMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="uniformityOfTheDosageReason" id="uniformityOfTheDosageReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	
        	<tr>
        	<td > 
        	<label for="dissolution" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.dissoulution"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="dissolution" id="dissolution_yes" value="Yes" onclick="dissolutionValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="dissolution" id="dissolution_no" value="No" onclick="dissolutionValidation()"><spring:message code="label.gaNo"></spring:message>
			 
			  <div id="dissolutionMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="dissolutionReason" id="dissolutionReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="endorsedBySanitary" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.endorsedbySanitaryResponsibleorEquivalent"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="endorsedBySanitary" id="endorsedbySanitaryResponse_yes" value="Yes" onclick="endorsedbySanitaryResponseValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="endorsedBySanitary" id="endorsedbySanitaryResponse_no" value="No" onclick="endorsedbySanitaryResponseValidation()"><spring:message code="label.gaNo"></spring:message>
			   <input type="radio" name="endorsedBySanitary" id="endorsedbySanitaryResponse_na" value="NA" onclick="endorsedbySanitaryResponseValidation()"><spring:message code="label.gaNa"></spring:message>
			 
			  <div id="endorsedbySanitaryResponseMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="endorsedBySanitaryReason" id="endorsedBySanitaryReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="productIsMoreThenPercentage" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.differenceoftheassayofthetestproductandreferenceproduct"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="productIsMoreThenPercentage" id="diffofAssayofTestandReference_yes" value="Yes" onclick="diffofAssayofTestandReferenceValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="productIsMoreThenPercentage" id="diffofAssayofTestandReference_no" value="No" onclick="diffofAssayofTestandReferenceValidation()"><spring:message code="label.gaNo"></spring:message>
			   <input type="radio" name="productIsMoreThenPercentage" id="diffofAssayofTestandReference_na" value="NA" onclick="diffofAssayofTestandReferenceValidation()"><spring:message code="label.gaNa"></spring:message>
			 
			  <div id="diffofAssayofTestandReferenceMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="productIsMoreThenPercentageReason" id="productIsMoreThenPercentageReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	
        	<tr>
        	<td > 
        	<label for="presentationLetterOfDrugs" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.presentaionLetterofDrugs"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="presentationLetterOfDrugs" id="letterofDrugs_yes" value="Yes" onclick="letterofDrugsValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="presentationLetterOfDrugs" id="letterofDrugs_no" value="No" onclick="letterofDrugsValidation()"><spring:message code="label.gaNo"></spring:message>
			 
			  <div id="letterofDrugsMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="presentationLetterOfDrugsReason" id="presentationLetterOfDrugsReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="letterOfGoodManufacturing" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.letterofGoodManufacturingPracticesoftheTestProduct"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="letterOfGoodManufacturing" id="manufacturePracticeofTestProducts_yes" value="Yes" onclick="manufacturePracticeofTestProductsValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="letterOfGoodManufacturing" id="manufacturePracticeofTestProducts_no" value="No" onclick="manufacturePracticeofTestProductsValidation()"><spring:message code="label.gaNo"></spring:message>
			   <input type="radio" name="letterOfGoodManufacturing" id="manufacturePracticeofTestProducts_na" value="NA" onclick="manufacturePracticeofTestProductsValidation()"><spring:message code="label.gaNa"></spring:message>
			 
			  <div id="manufacturePracticeofTestProductsMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="letterOfGoodManufacturingReason" id="letterOfGoodManufacturingReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="letterOfQualitative" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.letterofQuantitativeandQualitativeFormulaoftheTest"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="letterOfQualitative" id="qualitativeQuantitative_yes" value="Yes" onclick="qualitativeQuantitativeValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="letterOfQualitative" id="qualitativeQuantitative_no" value="No" onclick="qualitativeQuantitativeValidation()"><spring:message code="label.gaNo"></spring:message>
			   <input type="radio" name="letterOfQualitative" id="qualitativeQuantitative_na" value="NA" onclick="qualitativeQuantitativeValidation()"><spring:message code="label.gaNa"></spring:message>
			 
			  <div id="qualitativeQuantitativeMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="letterOfQualitativeReason" id="letterOfQualitativeReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="theReferenceProductIsTheIndicated" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theReferenceProductistheIndicatedbySanitoryAuthority"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="theReferenceProductIsTheIndicated" id="referenceProductbySanitory_yes" value="Yes" onclick="referenceProductbySanitoryValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="theReferenceProductIsTheIndicated" id="referenceProductbySanitory_no" value="No" onclick="referenceProductbySanitoryValidation()"><spring:message code="label.gaNo"></spring:message>
			   <input type="radio" name="theReferenceProductIsTheIndicated" id="referenceProductbySanitory_na" value="NA" onclick="referenceProductbySanitoryValidation()"><spring:message code="label.gaNa"></spring:message>
			 
			  <div id="referenceProductbySanitoryMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theReferenceProductIsTheIndicatedReason" id="theReferenceProductIsTheIndicatedReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="referenceProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.referenceProductPurchaseInvoice"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="referenceProduct" id="purchaseInvoice_yes" value="Yes" onclick="purchaseInvoiceValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="referenceProduct" id="purchaseInvoice_no" value="No" onclick="purchaseInvoiceValidation()"><spring:message code="label.gaNo"></spring:message>
			   <input type="radio" name="referenceProduct" id="purchaseInvoice_na" value="NA" onclick="purchaseInvoiceValidation()"><spring:message code="label.gaNa"></spring:message>
			 
			  <div id="purchaseInvoiceMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="referenceProductReason" id="referenceProductReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="theExpirationDate" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theExpirationDataCoverstheExecutionoftheStudy"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="theExpirationDate" id="expirationDate_yes" value="Yes" onclick="expirationDateValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="theExpirationDate" id="expirationDate_no" value="No" onclick="expirationDateValidation()"><spring:message code="label.gaNo"></spring:message>
			  
			  <div id="expirationDateMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theExpirationDateReason" id="theExpirationDateReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="theQuantityOfTheDrug" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theQuantityofTheDrugisSufficienttoPerformTheStudy"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="theQuantityOfTheDrug" id="quantityoftheDrug_yes" value="Yes" onclick="quantityoftheDrugValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="theQuantityOfTheDrug" id="quantityoftheDrug_no" value="No" onclick="quantityoftheDrugValidation()"><spring:message code="label.gaNo"></spring:message>
			 
			  <div id="quantityoftheDrugMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theQuantityOfTheDrugReason" id="theQuantityOfTheDrugReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="theBatchLotNumber" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.theBatchLotNumberConsistentthroughtheDocument"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="theBatchLotNumber" id="batchLotnumConstant_yes" value="Yes" onclick="batchLotnumConstantValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="theBatchLotNumber" id="batchLotnumConstant_no" value="No" onclick="batchLotnumConstantValidation()"><spring:message code="label.gaNo"></spring:message>
			   <div id="batchLotnumConstantMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="theBatchLotNumberReason" id="theBatchLotNumberReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="transferLetter" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.transferLetterinCaseofControlledMedication"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="transferLetter" id="transferLetter_yes" value="Yes" onclick="transferLetterValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="transferLetter" id="transferLetter_no" value="No" onclick="transferLetterValidation()"><spring:message code="label.gaNo"></spring:message>
			   <input type="radio" name="transferLetter" id="transferLetter_na" value="NA" onclick="transferLetterValidation()"><spring:message code="label.gaNa"></spring:message>
			 
			  <div id="transferLetterMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="transferLetterReason" id="transferLetterReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
        	<td > 
        	<label for="proceedForAnotherProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.documentryRequirements.proceedForAnotherProduct"></spring:message></label>
        	</td>
        	<td> 
        	 <div>
        	  <input type="radio" name="proceedForAnotherProduct" id="productforanotherproduct_yes" value="Yes" onclick="productforanotherproductValidation()" required><spring:message code="label.gaYes"></spring:message>
			  <input type="radio" name="proceedForAnotherProduct" id="productforanotherproduct_no" value="No" onclick="productforanotherproductValidation()"><spring:message code="label.gaNo"></spring:message>
			   
			  <div id="productforanotherproductMsg" style="color: red;"></div>
			</div>
        	</td>
        	<td>
        	 <form:input type="text"  path="proceedForAnotherProductReason" id="referenceProductReason"  autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"/>
        	</td>
        	</tr>
        	<tr>
 				<td colspan="4" style="text-align: center;"><input type="button" value="Submit" onclick="submitFunction()" class="btn btn-danger btn-sm" id="addBtn"></td>
			</tr>
        	</table>
        	</form:form>
        	</div>
        	</div>
        	</div>
        	</div>
        	
        	<script type="text/javascript">
        	
        	function peCertificateValidation(){
        		//alert("clicked")
        		var valFlag = false;
        		$('#peCertificateMsg').html("");
        		if($('#peCertificate_yes').prop("checked") ||$('#peCertificate_no').prop("checked")){
        			valFlag = true;
        		}else{
        			$('#peCertificateMsg').html("Required Field.");
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
        			$('#documentAvailableMsg').html("Required Field.");
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
        			$('#assayMsg').html("Required Field.");
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
        			$('#dosageUnitsExpressedMsg').html("Required Field.");
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
        			$('#dissolutionMsg').html("Required Field.");
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
        			$('#endorsedbySanitaryResponseMsg').html("Required Field.");
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
        			$('#diffofAssayofTestandReferenceMsg').html("Required Field.");
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
        			$('#letterofDrugsMsg').html("Required Field.");
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
        			$('#manufacturePracticeofTestProductsMsg').html("Required Field.");
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
        			$('#qualitativeQuantitativeMsg').html("Required Field.");
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
        			$('#referenceProductbySanitoryMsg').html("Required Field.");
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
        			$('#purchaseInvoiceMsg').html("Required Field.");
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
        			$('#expirationDateMsg').html("Required Field.");
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
        			$('#quantityoftheDrugMsg').html("Required Field.");
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
        			$('#batchLotnumConstantMsg').html("Required Field.");
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
        			$('#transferLetterMsg').html("Required Field.");
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
        			$('#productforanotherproductMsg').html("Required Field.");
        			valFlag = false;
        		}
        		return valFlag;
        	}
     
        	</script>
        	<script type="text/javascript">
        	
        	function submitFunction(){
        		//alert("trfhy");
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
        		var purchaseinvoice = purchaseInvoiceValidation
        		var expirationdate = expirationDateValidation();
        		var quantity = quantityoftheDrugValidation();
        		var batchlotnum = batchLotnumConstantValidation();
        		var transferletter = transferLetterValidation();
        		var prodforanotherproduct = productforanotherproductValidation();  
        		
        		if(validation.validate($('#saveForm')[0])){
        			$('#saveForm').submit(); 
        		}
        		
        	}
        	
        	</script>
</body>
</html>