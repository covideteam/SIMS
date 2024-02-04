<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Barcode Lable Print Page</title>
</head>
<body>
<form id="frmActivityForm">
				<div class="row">
				<div class="col-md-12 col-sm-12 ">
					<div class="x_panel">
						<div class="x_title">
							<h2><spring:message code="label.barcodePageTitle"></spring:message></h2>
							<ul class="nav navbar-right panel_toolbox">
								<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
								</li>
							</ul>
							<div class="clearfix"></div>
						</div>
						<div class="x_content">
							<br>
			    	<div style="width: 60%;">
			    		<div class="form-group row">
		    				<label for="role" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.projects"></spring:message></label>
		   					<div class="col-sm-3">
		   					<!-- Present static value . After add group value  -->
		   					    <input type="hidden" name="groupId" id="groupId" value="1" class="exclude"/>
		   						<select name="studyId" id="studyName" class="form-control validate exclude" data-validate="required">
				    				<option value="">----<spring:message code="label.sdSelect"></spring:message>----</option>
				    				<c:forEach items="${smList}" var="sm">
										<option value="${sm.id}">${sm.projectNo}</option>
									</c:forEach>
								</select>
								<!-- <div id="studyNameMsg" style="color: red;"></div> -->
							</div>
			   					
			   						<label for="role" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.actName"></spring:message></label>
									<div class="col-sm-3">
									<select name="barcodeType" id="barcodeType" class="form-control validate top exclude" >
					    				<option value="-1">----<spring:message code="label.sdSelect"></spring:message>----</option>
					    				<option value="SUBJECT"><spring:message code="label.barcodeSubject"></spring:message></option>
					    				<option value="VACUTAINER"><spring:message code="label.barcodeVacutainer"></spring:message></option>
					    				<option value="VIAL"><spring:message code="label.barcodeVial"></spring:message></option>
					    				<option value="SACHET"><spring:message code="label.barcodeSachet"></spring:message></option>
					    				<%-- <option value="ECG"><spring:message code="label.coverPage2.ECG"></spring:message></option> --%>
									</select>
								</div>
							</div>
				    		<div id="activityDiv" style="margin-top:15px;"></div>
				 </div>
			 </div>
		 </div>
	 </div>
 </div>
 </form>
<script src='/SIMS/static/js/barcodeLabelPrint.js'></script>
</body>
</html>