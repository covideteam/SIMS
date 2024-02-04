<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Instrument</title>

</head>
<body oncontextmenu="return false;">
		<div class="card">
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
					<h2><spring:message code="label.instrument.FormTitel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
	
	<c:url value="/instrument/saveInstrumentForm" var="saveUrl" />
	<form:form action="${saveUrl}" modelAttribute="instrument" method="post" id="submitid" >
	      <table class="table table-bordered table-striped" style="width: 100%;">
	            <tr>
	                 <th><spring:message code="label.instrument.Model"></spring:message></th>
	                 <td><form:select  path="instrumentModel.id" id="modelid" onchange="modelFunction('modelid','modelidMsg')" class="form-control">
								<option value="-1">--<spring:message code="label.sdSelect"></spring:message>--</option>
								 <c:forEach items="${modelList}" var="ml">
									<option value="${ml.id}">${ml.instrumentModel}</option>
								</c:forEach>
					</form:select>
					<span style="color: red;" id="modelidMsg"></span></td>
					<th><spring:message code="label.instrument.Type"></spring:message></th>
	                 <td><form:select  path="instrumentType.id" id="type" onchange="typeFunction('type','typeMsg')" class="form-control">
								<option value="-1">--<spring:message code="label.sdSelect"></spring:message>--</option>
								 <c:forEach items="${typeList}" var="tl">
									<option value="${tl.id}">${tl.instrumentType}</option>
								</c:forEach>
					</form:select>
					<span style="color: red;" id="typeMsg"></span></td>
					</tr>
					<tr>
					<th><spring:message code="label.instrument.InstrumentNo"></spring:message> </th>
					<td><form:input type="text" path="instrumentNo" id="instrumentNoId" class="form-control" onchange="instrumentNoFunction('instrumentNoId','instrumentNoIdMsg')"/>
					<span style="color: red;" id="instrumentNoIdMsg"></span>
					</td>
					
					<th><spring:message code="label.instrument.ManufacturingDate"></spring:message> </th>
					<td><input type="date" name="manDate" id="manufacturingDateId" class="form-control datepicker" onchange="manufacturingDateIdFunction('manufacturingDateId','manufacturingDateIdMsg')">
					 <!-- <script>
							$(function() {
								$("#manufacturingDateId").datepicker({
									dateFormat : "yy-mm-dd",
									changeMonth : true,
									// minDate: 0,
									changeYear : true
								});
							});
						</script> -->
					<span style="color: red;" id="manufacturingDateIdMsg"></span>
					</td>
					</tr>
					<tr>
					<th><spring:message code="label.instrument.CalibrationDate"></spring:message> </th>
					<td><input type="date" name="calDate" id="calibrationDateId" class="form-control datepicker" onchange="calibrationDateIdFunction('calibrationDateId','calibrationDateIdMsg')">
					<!-- <script>
							$(function() {
								$("#calibrationDateId").datepicker({
									dateFormat : "yy-mm-dd",
									changeMonth : true,
									// minDate: 0,
									changeYear : true
								});
							});
							
						</script> -->
						<span style="color: red;" id="calibrationDateIdMsg"></span>
					</td>
					<th><spring:message code="label.instrument.CalibrationFrequency"></spring:message></th>
	                 <td><form:select  path="calibrationFrequency" id="calibrationFrequencyId" onchange="calibrationFrequencyIdFunction('calibrationFrequencyId','calibrationFrequencyIdMsg')" class="form-control">
								<option value="-1">--<spring:message code="label.sdSelect"></spring:message>--</option>
								<option value="3month"><spring:message code="label.threeMonth"></spring:message></option>
								<option value="6month"><spring:message code="label.sixMonth"></spring:message></option>
								<option value="1year"><spring:message code="label.oneYear"></spring:message></option>
								<option value="2year"><spring:message code="label.twoYear"></spring:message></option>
								
					</form:select>
					<span style="color: red;" id="calibrationFrequencyIdMsg"></span></td>
				</tr>
				<tr><td colspan="14" align="center"><input type="button" id="addBtn" value="<spring:message code="label.activityLog.Submit"></spring:message>" class="btn btn-danger btn-md	"  onclick="fromValidation()">
				</td>
				</tr>
			</table>
			</form:form>
       <div id="userDetails"></div>
       </div>
       </div>
       </div>
       </div>
       </div>
       
</body>
<script type="text/javascript">
$('#addBtn').prop("disabled", false);
    function modelFunction(id,messageId){
    	var valFlag=false;
    	var value=$('#'+id).val();
    	//alert(value);
    	if(value!=""&&value!="undefined" && value!="-1"){
    		$('#'+messageId).html("");
    		valFlag = true;
    	}else{
    		/* $('#'+messageId).html("Required Field."); */
    		$('#'+messageId).html('${validationText}');
    		valFlag = false;
    	}
    	return valFlag;
    }
    function typeFunction(id,messageId){
    	var valFlag=false;
    	var value=$('#'+id).val();
    	if(value!=""&&value!="undefined" && value!="-1"){
    		$('#'+messageId).html("");
    		valFlag = true;
    	}else{
    		/* $('#'+messageId).html("Required Field."); */
    		$('#'+messageId).html('${validationText}');
    		valFlag = false;
    	}
    	return valFlag;
    }
    function instrumentNoFunction(id,messageId){
    	var valFlag=false;
    	var value=$('#'+id).val();
    	if(value!=""&&value!="undefined"){
    		$('#'+messageId).html("");
    		var url = mainUrl + "/instrument/instrumentNoExitOrNot/" +value;
    		var result=synchronousAjaxCall(url);
    		//alert(result);
    		if(result!=""&&result!="undefined"){
    			if(result!="Yes"){
    				valFlag = true;
    			}else{
    				$('#'+messageId).html("Instrument No Already Exists");
    				valFlag = false;
    			}
    		}else{
    			valFlag = false;
    			$('#'+messageId).html("Instrument No Already Exists");
    		}
    		
    	}else{
    		/* $('#'+messageId).html("Required Field."); */
    		$('#'+messageId).html('${validationText}');
    		valFlag = false;
    	}
    	return valFlag;
    }
    function manufacturingDateIdFunction(id,messageId){
    	var valFlag=false;
    	var value=$('#'+id).val();
    	if(value!=""&&value!="undefined"){
    		$('#'+messageId).html("");
    		valFlag = true;
    	}else{
    		/* $('#'+messageId).html("Required Field."); */
    		$('#'+messageId).html('${validationText}');
    		valFlag = false;
    	}
    	return valFlag;
    }
    function calibrationDateIdFunction(id,messageId){
    	var valFlag=false;
    	var value=$('#'+id).val();
    	if(value!=""&&value!="undefined"){
    		$('#'+messageId).html("");
    		valFlag = true;
    	}else{
    		/* $('#'+messageId).html("Required Field."); */
    		$('#'+messageId).html('${validationText}');
    		valFlag = false;
    	}
    	return valFlag;
    }
    function calibrationFrequencyIdFunction(id,messageId){
    	var valFlag=false;
    	var value=$('#'+id).val();
    	if(value!=""&&value!="undefined" && value!="-1"){
    		$('#'+messageId).html("");
    		valFlag = true;
    	}else{
    		/* $('#'+messageId).html("Required Field."); */
    		$('#'+messageId).html('${validationText}');
    		valFlag = false;
    	}
    	return valFlag;
    }
    
	function fromValidation() {
		$('#addBtn').prop("disabled", true);
		var model=modelFunction('modelid','modelidMsg');
		 var type=typeFunction('type','typeMsg');
		var intno=instrumentNoFunction('instrumentNoId','instrumentNoIdMsg');
		var manDate=manufacturingDateIdFunction('manufacturingDateId','manufacturingDateIdMsg');
		var calDate=calibrationDateIdFunction('calibrationDateId','calibrationDateIdMsg');
		var calFreq=calibrationFrequencyIdFunction('calibrationFrequencyId','calibrationFrequencyIdMsg'); 
		//alert(model);
		// alert(model+":"+type+":"+intno+":"+manDate+":"+calDate+":"+calFreq); 
		if(model&&type&&intno&&manDate&&calDate&&calFreq){
			$('#submitid').submit();
		}else{
			$('#addBtn').prop("disabled", true);
		}
		
		
		
	} 
    
</script>
</html>