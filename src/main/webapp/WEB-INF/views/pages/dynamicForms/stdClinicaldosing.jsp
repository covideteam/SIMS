<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<script src='/SIMS/static/js/cpu/devidation.js'></script>
<link href="<c:url value="/static/sweetalert/sweetalert2.css"/>" rel="stylesheet">
	<script src="<c:url value='/static/sweetalert/sweetalert2.js'/>"></script>
<script src='/SIMS/static/js/cpu/dosing.js'></script>

<script type="text/javascript">
 $('#subjectReplaceTr').hide();
</script>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_content">
					<br>
		<input type="hidden" name="subject" id="subject" /> <input
		type="hidden" name="subjectTime" id="subjectTime" /> <input
		type="hidden" name="sachet" id="sachet" /> <input type="hidden"
		name="sachetTime" id="sachetTime" /> <input type="hidden"
		name="collectionTime" id="collectionTime" /> <input type="hidden"
		name="radioButtonValues" id="radioButtonValues" value="" /> <input
		type="hidden" id="appropriatecount" value="0" />
		<input type="hidden" name="selectVal" id="selectVal" value="<spring:message code="label.ruleSelect"></spring:message>" />
		<input type="hidden" name="standByBarcode" id="standByBarcode"  value=""/> 
		<input type="hidden" name="replacedSubjectNo" id="replacedSubjectNo" value="0"/> 
		<input type="hidden" name="timeDeviation" id="timeDeviation" value="false"/> 
		<input type="hidden" name="timeDeviationTime" id="timeDeviationTime" value="0"/> 
		<input type="hidden" name="timeDeviationCode" id="timeDeviationCode" value="0"/> 
		<input type="hidden" name="criteriaDeviation" id="criteriaDeviation" value="false"/> 
		<input type="hidden" name="criteriaDeviationTime" id="criteriaDeviationTime" value="0"/> 
		<input type="hidden" name="criteriaDeviationTimeCode" id="criteriaDeviationTimeCode" value="0"/> 
		
		
		<div class="form-group row">
			<label for="barcoded" class="col-sm-3 col-form-label" style="color: #212529;">Scan Barcode</label>
			<div class="col-sm-5">
				<input type="text" name="barcode" id="barcode" class="form-control" style="width:90%;display:inline;"/> 
			 	<input type="hidden" id="timePintDataAndTime" value="" />
			 	<a class="fa fa-camera neo-qrcodescanner" style="margin-left:5px;font-size: 20px;"></a>
				<div id="barcodeMsg" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group row" id="subjectReplaceTr">
				<label for="subjectReplaceDiv" class="col-sm-3 col-form-label" style="color: #212529;">Select Subject to Replace</label>
				<div class="col-sm-5">
					<div id="subjectReplaceTr">
						<select name="subjectReplaceDiv" id="subjectReplaceDiv" class="form-control" onchange="selectBoxValidation('subjectReplaceDiv', 'subjectReplaceDivMsg')">
								<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
						</select>
						<div id="subjectReplaceDivMsg" style="color: red;"></div>
					</div>
			</div>
		</div>
		<div class="form-group row">
				<label for="subjectMsg" class="col-sm-3 col-form-label" style="color: #212529;">Subject</label>
				<div class="col-sm-5">
					<div id="subjectMsg"></div>
			</div>
		</div>
		<div class="form-group row">
				<label for="sachetMsg" class="col-sm-3 col-form-label" style="color: #212529;">IP</label>
				<div class="col-sm-5">
					<div id="sachetMsg"></div>
			</div>
		</div>
		<div id="doseDetailsDiv" style="display:none;"></div>
		<div id="collectionTimeDiv">
			<div class="form-group row">
				<label for="collectionTimeTr" class="col-sm-3 col-form-label" style="color: #212529;">Dosed Time</label>
				<div class="col-sm-5">
					<div id="collectionTimeTr"></div>
			</div>
		</div>
		</div>
		
		<div class="form-group row">
				<div class="col-sm-12">
					<div id="appropriateBox">
						<div id="appropriateBox1"></div>
					</div>
			</div>
		</div>
		<div class="form-group row">
				<label for="message" class="col-sm-3 col-form-label" style="color: #212529;">Comment</label>
				<div class="col-sm-5">
					<select name="message" id="message" class="form-control" onchange="messageValidation('message', 'commentsMsg')">
					<option value="-1">-Select-</option>
					<c:forEach items="${doseComments}" var="c">
					 	<c:if test="${c.code ne 'FEDCANDTPD' and c.code ne 'FASTCDANDTPD' and c.code ne 'FASTANDFEDCD' and c.code ne 'FASTANDFEDCDANDTD' and c.code ne 'FEDCD'
					 	       and c.code ne 'FASTCD'  and c.code ne 'TPD' and c.code ne 'TPS' and c.code ne 'TPSKIP' and c.code ne 'TPSKIPANDTP'}">
							<option value="${c.id}">${c.message}</option>
						</c:if>
					</c:forEach>
				</select>
				<div id="commentsMsg" style="color: red;"></div>
			</div>
		</div>
		<div class="form-group row">
				<div class="col-sm-10" align="center">
					<input type="button" value="Submit" id="save_btn" class="btn btn-primary" onclick="submitForm()" /> 
					<!-- &nbsp;<input type="button" value="Clear" class="btn btn-primary" onclick="clearForm()" /> -->
			</div>
		</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>