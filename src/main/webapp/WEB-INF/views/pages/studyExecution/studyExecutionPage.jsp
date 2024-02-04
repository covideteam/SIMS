<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Study Execution Page</title>
</head>
<body>
	<input type='hidden' id="pageType" value="${screenType}">
	<form:form id="frmServer">
			
	</form:form>
	<form id="frmActivityForm">
		<div class="row">
			<div class="col-md-12 col-sm-12 ">
				<div class="x_panel">
					<div class="x_title">
						<h2>
							<spring:message code="label.seTitle"></spring:message>
						</h2>
						<div class="clearfix"></div>
					</div>
					<div class="x_content">
						<!-- <div id="qr-reader" style="width:500px"></div>
    					<div id="qr-reader-results"></div> -->
						<div style="padding: 10px;">
							<input type="hidden" name="groupId" id="groupId" value="1"
										class="exclude" /> 
							<div class="row">
								<div class="col-lg-4 col-md-4 col-sm-12">
									<div class="form-group form-inline">     
										<label style='width:40%;'><spring:message code="label.crfData.study"></spring:message></label>
										<select name="studyId" id="studyName"
											class="form-control validate exclude" data-validate="required" style='width:60%;'>
												<option value="">-----
													<spring:message code="label.sdSelect"></spring:message>-----
												</option>
												<c:forEach items="${smList}" var="sm">
													<option value="${sm.id}">${sm.projectNo}</option>
												</c:forEach>
										</select>
										<div id="studyNameMsg" style="color: red;"></div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-12">
									<div class="form-group form-inline">   
										<label style='width:40%;'><spring:message code="label.actName"></spring:message></label>
										<select name="activityId"
											id="crfName" class="form-control validate exclude"
											data-validate="required" style='width:60%;'>
												<option value="">----
													<spring:message code="label.sdSelect"></spring:message>----
												</option>
										</select>
										<div id="activityMsg" style="color: red;"></div>
									</div>
								</div>
								<div class="col-lg-4 col-md-4 col-sm-12">
									<div class="form-group form-inline">   
										<c:if test="${screenType == 'activity'}">
											<label style='width:40%;'><span class='studyExcutionSubjectNumber'><spring:message
														code="label.sevolunteer"></spring:message></span>
											</label>
											<input type="text" id="subName"
												name="subName" style="width:50%;"
												class="form-control studyExcutionSubjectNumber exclude-element reset-element" />
												<div id="subNameMsg" style="color: red;"></div>
											<a class="fa fa-camera neo-qrcodescanner studyExcutionSubjectNumber" style="margin-left:5px;font-size: 20px;"></a>
										</c:if>
									</div>
								</div>
							</div>
							<table style="width: 100%;">
								<tr>
									<td style='width: 200px;'>
										<!-- Present static value . After add group value  --> 
									
									</td>
									<td style='width: 100px;'>
									
									</td>
									<td style='width: 200px;'>
									</td>

									
								</tr>
								<tr style="border-collapse: separate; border-spacing: 0 1em;">
									<td colspan="6">
										<div id="activityDiv" style="margin-top: 15px;"></div>
									</td>
								</tr>
							</table>
						</div>
						<div id="crfFormDiv" style="display: none;"></div>
						<div id="crfReviewDiv" style="display: none;">
							<table class="table table-striped table-bordered nowrap"
								id="tblSubjects">
								<thead>
									<tr>
										<td>Subject Number/Vol ID</td>
										<td>Done By</td>
										<td>Done Date</td>
										<td>Action</td>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			loadJS('/SIMS/static/js/studyExecution.js');
		}, false);
		
		document.addEventListener('DOMContentLoaded', function() {
			loadJS('/SIMS/static/js/qrcodereader.js');
		}, false);
	</script>
</body>
</html>