<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
.str {
	color: red;
	padding-left: 10px;
	font-weight: bold;
}
</style>
<script>
	var sampleColMap = new Map();
	var mealColMap = new Map();
	var vitalColMap = new Map();
	var doseColMap = new Map();
	var skinSenColMap = new Map();
	var skinAdhColMap = new Map();
	var ecgColMap = new Map();
	var otherColMap = new Map();
	var treatmentsArr = [];
	var interval = "";
	$(document).ready(function() { 
		interval = '${dinfDto.doseInfo.dsdifferenceBetweenSubjects}'
		/* if(interval != null && interval != "" && interval != "undefined")
			$('#dsdifferenceBetweenSubjects').val(interval); */
		dsdifferenceBetweenSubjectsValidation('dsdifferenceBetweenSubjects');
	});
</script>

</head>
<body>
	
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Dosing Information</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<div id="pageMsg" style="color: red; font-size: medium; font-weight: bold; text-align: center;"></div>
					<c:url value="/dosingInfo/saveDosinginfo" var="saveData"></c:url>
					 <form:form action="${saveData}" method="post" id="formSubmit">
					<input type="hidden" name="projectId" id="projectId" value="${dinfDto.project.projectId}" />
					<input type="hidden" value="${dinfDto.project.projectNo}" id="projectNo" name="projectNo">
					<input type="hidden" name="samplesTpVals" id="samplesTpVals"/>
					<input type="hidden" name="doseTpVals" id="doseTpVals" />
					<input type="hidden" name="mealsTpVal" id="mealsTpVal" />
					<input type="hidden" name="vitalsTpVal" id="vitalsTpVal"/>
					<input type="hidden" name="skinSenTpVals" id="skinSenTpVals"/>
					<input type="hidden" name="ecgTpVals" id="ecgTpVals"/>
					<input type="hidden" name="skinAdhTpVals" id="skinAdhTpVals"/>
					<input type="hidden" name="ohterTpVals" id="ohterTpVals"/>
					<input type="hidden" name="treatmentNamesList" id="treatmentNamesList"/>
					<table style="width: 70%; font-size: 15px;">
						<tr>
							<td style="width: 50px;"><label for="project"class=" col-form-label" style="color: #212529;"><spring:message code="label.randomizationProjectNo"></spring:message></label></td>
							<td style="width: 200px;"><label id="project" class=" col-form-label" >${dinfDto.project.projectNo}</label>
								
								</td>
						</tr>
					</table>
						<c:choose>
							<c:when test="${not empty dinfDto.doseInfo }">
								<table style="width: 70%; font-size: 15px;">
									<tr>
										<td style="width: 30%;"><label for="dosingDate"class=" col-form-label" style="color: #212529;"><spring:message code="crf.dosingDate"></spring:message></label><span class="str">*</span></td>
										<td style="width: 20%;">
											<input type="date" id="dosingDate" name="dosingDate" class="form-control" value="${dinfDto.doseInfo.doseDateStr}" onblur="dosingDateValidation('dosingDate', 'dosingDateMsg')">
										<div id="dosingDateMsg" style="color: red;"></div>
										</td>
										
										<td></td>
										<td style="width: 30%; padding-left: 35px;"><label
											for="dosingTime" class=" col-form-label" onblur="dosingTimeValidation('dosingTime', 'doseTimeMsg')"
											style="color: #212529;"><spring:message code="crf.doseTime"></spring:message></label><span class="str">*</span></td>
										<td style="width: 20%;">
											<input type="time" id="dosingTime"  name="dosingTime" value="${dinfDto.doseInfo.doseTimeStr}" class="form-control">
											<div id="doseTimeMsg" style="color: red;"></div>
										</td>
									</tr>
		
									<tr>
										<td style="width: 30%;"><label
											for="dsdifferenceBetweenSubjects" class=" col-form-label"
											style="color: #212529;"><spring:message code="crf.intervel"></spring:message></label><span class="str">*</span></td>
										<td style="width: 20%;">
											<input type="text" onkeyup="dsdifferenceBetweenSubjectsValidation('dsdifferenceBetweenSubjects')"
											id="dsdifferenceBetweenSubjects" value="${dinfDto.doseInfo.dsdifferenceBetweenSubjects}"
											name="dsdifferenceBetweenSubjects" class="form-control">
											<div id="diffMsg" style="color: red;"></div>
											</td>
										<td><label style="text-align: left;"><spring:message code="crf.min"></spring:message></label></td>
		
										<td style="width: 30%; padding-left: 35px;"><label
											for="noOfStations" class=" col-form-label"
											style="color: #212529;"><spring:message code="crf.stations"></spring:message></label><span
											class="str">*</span></td>
										<td><input type="text" id="noOfStations" value="${dinfDto.doseInfo.noOfStations}" onkeyup="noOfStationsValidation('noOfStations', 'noOfStationsMsg')"
											name="noOfStations" data-texttype="NUMERIC" maxlength="4"
											autocomplete="off"
											Class="form-control autosave reviewElement validate"
											data-validate="required" class="form-control"
											style="width: 100%;">
											<div id="noOfStationsMsg" style="color: red;"></div>
										</td>
									</tr>
								</table>
							</c:when>
							<c:otherwise>
								<table style="width: 70%; font-size: 15px;">
									<tr>
										<td style="width: 30%;"><label for="dosingDate"class=" col-form-label" style="color: #212529;"><spring:message code="crf.dosingDate"></spring:message></label><span class="str">*</span></td>
										<td style="width: 20%;"><input type="date" id="dosingDate" name="dosingDate" class="form-control" onkeyup="dosingDateValidation('dosingDate')">
										<div id="dosingDateMsg" style="color: red;"></div>
										</td>
										
										<td></td>
										<td style="width: 30%; padding-left: 35px;"><label
											for="dosingTime" class=" col-form-label" onkeyup="dosingTimeValidation('dosingTime')"
											style="color: #212529;"><spring:message code="crf.doseTime"></spring:message></label><span class="str">*</span></td>
										<td style="width: 20%;"><input type="time" id="dosingTime"
											name="dosingTime" class="form-control">
											<div id="doseTimeMsg" style="color: red;"></div>
										</td>
									</tr>
		
									<tr>
										<td style="width: 30%;"><label
											for="dsdifferenceBetweenSubjects" class=" col-form-label"
											style="color: #212529;"><spring:message code="crf.intervel"></spring:message></label><span class="str">*</span></td>
										<td style="width: 20%;"><input type="text" onkeyup="dsdifferenceBetweenSubjectsValidation('dsdifferenceBetweenSubjects')"
											id="dsdifferenceBetweenSubjects"
											name="dsdifferenceBetweenSubjects" class="form-control">
											<div id="diffMsg" style="color: red;"></div>
										</td>
										<td><label style="text-align: left;"><spring:message code="crf.min"></spring:message></label></td>
		
										<td style="width: 30%; padding-left: 35px;"><label
											for="noOfStations" class=" col-form-label"
											style="color: #212529;"><spring:message code="crf.stations"></spring:message></label><span
											class="str">*</span>
											
										</td>
										<td><input type="text" id="noOfStations" onkeyup="noOfStationsValidation('noOfStations')"
											name="noOfStations" data-texttype="NUMERIC" maxlength="4"
											autocomplete="off"
											Class="form-control autosave reviewElement validate"
											data-validate="required" class="form-control"
											style="width: 100%;">
											<div id="noOfStationsMsg" style="color: red;"></div>
										</td>
									</tr>
								</table>
							</c:otherwise>
						</c:choose>
					</form:form>
					<!-- Dosing Collection  -->
					<table style="width: 100%; font-size: 15px;">
						<c:set var="itemVal" value="other"></c:set>
						<c:if test="${dinfDto.trwDoseFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						<c:if test="${dinfDto.trwSampleFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						<c:if test="${dinfDto.trwMealsFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						<c:if test="${dinfDto.trwVitalFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						<c:if test="${dinfDto.trwSkinFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						<c:if test="${dinfDto.trwSkinAdhFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						<c:if test="${dinfDto.trwEcgFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						<c:if test="${dinfDto.trwOtherFlag eq false}">
							<c:set var="itemVal" value="AllTreatments"></c:set>
						</c:if>
						
						<c:forEach items="${dinfDto.timePointsMap}" var="map">
							<tr>
								<td colspan="6"><h1 class='page-subheading'>${map.key}</h1></td>
							</tr>
							<c:forEach items="${map.value}" var="tpMap">
						        <c:set value="${1}" var="colCount"></c:set>
								<c:forEach items="${tpMap.value}" var="tp" varStatus="st">								
									<c:if test="${st.count eq 1}">
									 		<c:choose>
									       		<c:when test="${itemVal eq 'AllTreatments'}">
									       			<tr align="center">
								        				<th colspan="6"><spring:message code="lable.allStationsText"></spring:message></th>
								        			</tr>
								        		</c:when>
									       		<c:otherwise>
									       			<tr align="center">
								        				<th colspan="6">${tp.treatmentName} <spring:message code="label.stationsText"></spring:message></th>
								        			</tr>
								        		</c:otherwise>
										    </c:choose>
										    <tr>
										       <c:if test="${tpMap.value.size() >=1}">
							        				<td><spring:message code="label.centrifugeTimepoint"></spring:message></td>
							        				<td><spring:message code="label.intervelSubjects"></spring:message></td>
						        				</c:if>
						        				<c:if test="${tpMap.value.size() >=2}">
							        				<td><spring:message code="label.centrifugeTimepoint"></spring:message></td>
							        				<td><spring:message code="label.intervelSubjects"></spring:message></td>
						        				</c:if>
						        				<c:if test="${tpMap.value.size() >=3}">
							        				<td><spring:message code="label.centrifugeTimepoint"></spring:message></td>
							        				<td><spring:message code="label.intervelSubjects"></spring:message></td>
						        				</c:if>
								        	</tr>
								        </c:if>
								        <c:choose>
										    <c:when test="${colCount eq 1}">
										    	<tr>
										    		<td>
										    			${tp.tpVal}
										    			<script type="text/javascript">
														    var tpStr = '${tp.typeStr}';
														    if(tpStr == "dose"){
														    	var doseVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				doseColMap.set(doseVal, '${tp.tpVal}');
														    }else if(tpStr == "meal"){
														    	var mealVal = '${st.count}'+"_"+'${tp.treatmentCode}';
												    			mealColMap.set(mealVal, '${tp.tpVal}');
														    }else if(tpStr == "samp"){
														    	  var sampVal = '${st.count}'+"_"+'${tp.treatmentCode}';
												    			  sampleColMap.set(sampVal,'${tp.tpVal}');
														    }else if(tpStr == "vital"){
														    	 var vitalVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 vitalColMap.set(vitalVal,'${tp.tpVal}');
														    }else if(tpStr == "skinsen"){
														    	 var skinSenVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 skinSenColMap.set(skinSenVal,'${tp.tpVal}');
															  }else if(tpStr == "skinAdh"){
														    	var skinAdhVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 skinAdhColMap.set(skinAdhVal,'${tp.tpVal}');
														    }else if(tpStr == "ecg"){
														    	var ecgVal = '${st.count}'+"_"+'${tp.treatmentCode}';
										    				 	ecgColMap.set(ecgVal,'${tp.tpVal}');
														    }else if(tpStr == "other"){
														    	var otherVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 otherColMap.set(otherVal,'${tp.tpVal}');
														    }
														    var trcodeAndName = '${tp.treatmentCode}' +"@@@"+'${tp.treatmentName}';
														    if(treatmentsArr.indexOf(trcodeAndName) == -1)
														    	treatmentsArr.push(trcodeAndName);
														</script>
										    		</td>
											    	<td>
														<c:choose>
															<c:when test="${tp.intravelBetweenSubjecs != 0}">
																<input type="text" class="form-control" name="${tp.typeStr}_${st.count}_${tp.treatmentCode}" id="${tp.typeStr}_${st.count}_${tp.treatmentCode}" style="max-width:50%;" value="${tp.intravelBetweenSubjecs}">
															</c:when>
															<c:otherwise>
																<input type="text" class="form-control" name="${tp.typeStr}_${st.count}_${tp.treatmentCode}" id="${tp.typeStr}_${st.count}_${tp.treatmentCode}" style="max-width:50%;" value="">
															</c:otherwise>
														</c:choose>
													</td>
													<c:set value="${colCount+1}" var="colCount"></c:set>
										    </c:when>
										    <c:otherwise>
										    	<td>${tp.tpVal}</td>
													<td>
														<c:choose>
															<c:when test="${tp.intravelBetweenSubjecs != 0}">
																<input type="text" class="form-control" name="${tp.typeStr}_${st.count}_${tp.treatmentCode}" id="${tp.typeStr}_${st.count}_${tp.treatmentCode}" style="max-width:50%;"  value="${tp.intravelBetweenSubjecs}">
															</c:when>
															<c:otherwise>
																<input type="text" class="form-control" name="${tp.typeStr}_${st.count}_${tp.treatmentCode}" id="${tp.typeStr}_${st.count}_${tp.treatmentCode}" style="max-width:50%;" value="">
															</c:otherwise>
														</c:choose>
														<script type="text/javascript">
														    var tpStr = '${tp.typeStr}';
														    if(tpStr == "dose"){
														    	var doseVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				doseColMap.set(doseVal, '${tp.tpVal}');
														    }else if(tpStr == "meal"){
														    	var mealVal = '${st.count}'+"_"+'${tp.treatmentCode}';
												    			mealColMap.set(mealVal, '${tp.tpVal}');
														    }else if(tpStr == "samp"){
														    	  var sampVal = '${st.count}'+"_"+'${tp.treatmentCode}';
												    			  sampleColMap.set(sampVal,'${tp.tpVal}');
														    }else if(tpStr == "vital"){
														    	 var vitalVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 vitalColMap.set(vitalVal,'${tp.tpVal}');
														    }else if(tpStr == "skinsen"){
														    	 var skinSenVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 skinSenColMap.set(skinSenVal,'${tp.tpVal}');
															  }else if(tpStr == "skinAdh"){
														    	var skinAdhVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 skinAdhColMap.set(skinAdhVal,'${tp.tpVal}');
														    }else if(tpStr == "ecg"){
														    	var ecgVal = '${st.count}'+"_"+'${tp.treatmentCode}';
										    				 	ecgColMap.set(ecgVal,'${tp.tpVal}');
														    }else if(tpStr == "other"){
														    	var otherVal = '${st.count}'+"_"+'${tp.treatmentCode}';
											    				 otherColMap.set(otherVal,'${tp.tpVal}');
														    }
														    var trcodeAndName = '${tp.treatmentCode}' +"@@@"+'${tp.treatmentName}';
														    if(treatmentsArr.indexOf(trcodeAndName) == -1)
														    	treatmentsArr.push(trcodeAndName);
														</script>
													</td>
													<c:choose>
														<c:when test="${colCount eq 3}">
															<c:set value="${1}" var="colCount"></c:set>
															</tr>
														</c:when>
														<c:otherwise>
															<c:set value="${colCount+1}" var="colCount"></c:set>
														</c:otherwise>
													</c:choose>
										    </c:otherwise>
										 </c:choose>
							    </c:forEach>
							</c:forEach>
						</c:forEach>
					</table>
					<table style="width: 100%; font-size: 15px;">
						<tr>
							<td colspan="4" style="text-align: center">
							<input type="button" value="submit" id="submit" class="btn btn-primary">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="Generate File" id="generatePdfButton" class="btn btn-primary">	
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
	</div>
<c:url value="/dosingInfo/generateTimePointsPdf" var="genratePdf"></c:url>
<form:form action="${genratePdf}" method="GET" id="pdfForm">
	<input type="hidden" name="projectId" id="projectId" value="${dinfDto.project.projectId}" />
</form:form>
</body>

<script type="text/javascript">
function dosingDateValidation(id, messageId){
	var doseDateFlag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "undefined"){
		doseDateFlag = true;
		$('#'+messageId).html("");
	}else{
		doseDateFlag = false;
		$('#'+messageId).html("Required Field.");
	}
	return doseDateFlag;
}
function dosingTimeValidation(id, messageId){
	var doseTimeFlag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "undefined"){
		doseTimeFlag = true;
		$('#'+messageId).html("");
	}else{
		doseTimeFlag = false;
		$('#'+messageId).html("Required Field.");
	}
	return doseTimeFlag;
}
function noOfStationsValidation(id, messageId){
	var noOfStationsFlag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "undefined"){
		noOfStationsFlag = true;
		$('#'+messageId).html("");
	}else{
		noOfStationsFlag = false;
		$('#'+messageId).html("Required Field.");
	}
	return noOfStationsFlag;
}
function dsdifferenceBetweenSubjectsValidation(id){
	debugger;
	var diffFlag = false;
	var value = $('#'+id).val();
// 	alert("interval value is :"+value);
	if(value != null && value != "" && value != "undefined"){
		if(interval != value){
			for (var key of doseColMap.keys()) {
				$('#dose_'+key).val(value);
			}
			for (var key of sampleColMap.keys()) {
				$('#samp_'+key).val(value);
			}
			for (var key of mealColMap.keys()) {
				$('#meal_'+key).val(value);
			}
			for (var key of vitalColMap.keys()) {
				$('#vital_'+key).val(value);
			}	
			for (var key of skinSenColMap.keys()) {
				$('#skinsen_'+key).val(value);
			}
			for (var key of skinAdhColMap.keys()) {
				$('#skinAdh_'+key).val(value);
			}
			for (var key of ecgColMap.keys()) {
				$('#ecg_'+key).val(value);
			}
			for (var key of otherColMap.keys()) {
				$('#other_'+key).val(value);
			}
		}
		diffFlag = true;
		$('#diffMsg').html("");
	}else{
		$('#diffMsg').html("Required Field.");
		diffFlag = false;
	}
	return diffFlag;
}
var sampleTpVals = [];
var doseTpVals = [];
var mealsTpVals = [];
var vitalsTpVals = [];
var skinSenTpVals = [];
var skinAdhTpVals = [];
var ecgTpVals = [];
var otherTpVals = [];
$('#submit').click(function(){
	debugger;
	for (var key of doseColMap.keys()) {
        var tpVal = $('#dose_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	doseTpVals.push(doseColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	for (var key of sampleColMap.keys()) {
        var tpVal = $('#samp_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	sampleTpVals.push(sampleColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	for (var key of mealColMap.keys()) {
        var tpVal = $('#meal_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	mealsTpVals.push(mealColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	for (var key of vitalColMap.keys()) {
        var tpVal = $('#vital_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	vitalsTpVals.push(vitalColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	for (var key of skinSenColMap.keys()) {
        var tpVal = $('#skinsen_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	skinSenTpVals.push(skinSenColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	for (var key of skinAdhColMap.keys()) {
        var tpVal = $('#skinAdh_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	skinAdhTpVals.push(skinAdhColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	for (var key of ecgColMap.keys()) {
        var tpVal = $('#ecg_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	ecgTpVals.push(ecgColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	for (var key of otherColMap.keys()) {
        var tpVal = $('#other_'+key).val();
        if(tpVal != null && tpVal != "" && tpVal != "undefined"){
        	var tempArr = key.split("_");
        	otherTpVals.push(otherColMap.get(key)+"@@"+tpVal+"@@"+tempArr[1]);
        }
        
    }
	var dateFlag = dosingDateValidation('dosingDate', 'dosingDateMsg');
	var timeFlag = dosingTimeValidation('dosingTime', 'doseTimeMsg');
	var subIntVal = $('#dsdifferenceBetweenSubjects').val();
// 	var subIntrFlag = dsdifferenceBetweenSubjectsValidation('dsdifferenceBetweenSubjects');
	var subIntrFlag = false;
	if(subIntVal == null || subIntVal == "" || subIntVal == "undefined"){
		subIntrFlag = false;
		$('#diffMsg').html("Required Field.");
	}else{
		subIntrFlag = true;
		$('#diffMsg').html("");
	}
	var noOfStFlag = noOfStationsValidation('noOfStations', 'noOfStationsMsg');
// 	alert(dateFlag +"::"+ timeFlag +"::"+ subIntrFlag +"::"+ noOfStFlag);
	 if(dateFlag && timeFlag && subIntrFlag && noOfStFlag){
		 $('#samplesTpVals').val("");
		 $('#doseTpVals').val("");
		 $('#mealsTpVal').val("");
		 $('#vitalsTpVal').val("");
		 $('#skinSenTpVals').val("");
		 $('#ecgTpVals').val("");
		 $('#skinAdhTpVals').val("");
		 $('#ohterTpVals').val("");
		 
		 
		if(sampleTpVals.length > 0)
		 	$('#samplesTpVals').val(sampleTpVals);
		 else $('#samplesTpVals').val(0);
		 if(doseTpVals.length > 0)
		 	$('#doseTpVals').val(doseTpVals);
		 else $('#doseTpVals').val(0);
		 if(mealsTpVals.length > 0)
			 $('#mealsTpVal').val(mealsTpVals);
		 else $('#mealsTpVal').val(0);
		 if(vitalsTpVals.length > 0)
		 	$('#vitalsTpVal').val(vitalsTpVals);
		 else $('#vitalsTpVal').val(0);
		 if(skinSenTpVals.length > 0)
			 	$('#skinSenTpVals').val(skinSenTpVals);
		 else $('#skinSenTpVals').val(0);
		 if(ecgTpVals.length > 0)
			 	$('#ecgTpVals').val(ecgTpVals);
		 else $('#ecgTpVals').val(0);
		 if(skinAdhTpVals.length > 0)
			 	$('#skinAdhTpVals').val(skinAdhTpVals);
		 else $('#skinAdhTpVals').val(0);
		 if(otherTpVals.length > 0)
			 	$('#ohterTpVals').val(otherTpVals);
		 else $('#ohterTpVals').val(0);
		 $('#treatmentNamesList').val(treatmentsArr);
		 var formData =  $('#formSubmit').serialize();
		 $.ajax({
			 url: $("#mainUrl").val() + '/dosingInfo/saveDosinginfo',
			 type:'POST',
			 data: formData,
			 success:function(data){
				 if(data.Success === 'true' || data.Success === true)
				 	displayMessage("success",data.Message);
				 else displayMessage("error",data.Message);
			 }
		 });
	} 
});

$('#generatePdfButton').click(function(){
	$('#pdfForm').submit();
});
</script>



</html>