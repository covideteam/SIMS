
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
	<script type="text/javascript">
		$(document).ready(function() {
			$('#saveid').hide();
			$('#rejectid').hide();
		});
	</script>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>
						<spring:message code="label.defaultAct"></spring:message>
					</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>

					<c:url value="/defaultActivity/defaultActivitySave" var="dataUrl"></c:url>
					<form:form action="${dataUrl}" method="POST" id="fsubmit">
						<input type="hidden" name="actid" id="actid">
						<input type="hidden" name="paramid" id="paramid">
						<input type="hidden" name="phaseid" id="phaseid">
						<input type="hidden" name="tableNameVal" id="tableNameId">
						<input type="hidden" name="subjectAllotmentVal"
							id="subjectAllotmentId">
						<div style="width: 75%; margin-left: 10%;">
							<div class="form-group row">
								<label for="activity" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.gaActivity"></spring:message></label>
								<div class="col-sm-10">
									<select name="activity" id="activity"
										class="form-control validate"
										onchange="activityFunctionCheckExitOrNot('activity', 'activityMsg')">
										<option value="-1">---
											<spring:message code="label.sdSelect"></spring:message>---
										</option>
										<c:forEach items="${activity}" var="act">
											<option value="${act.id}">${act.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group row">
								<label for="parameter" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.gaParameter"></spring:message></label>
								<div class="col-sm-10">
									<select name="parameter" id="parameter"
										class="form-control validate" multiple="multiple" size="20">
										<option value="-1">---
											<spring:message code="label.sdSelect"></spring:message>---
										</option>
										<c:forEach items="${parameter}" var="par">
											<option value="${par.id}">${par.parameterName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group row">
								<label for="phase" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.gaPhase"></spring:message></label>
								<div class="col-sm-10">
									<select name="phase" id="phase" class="form-control validate">
										<option value="-1">---
											<spring:message code="label.sdSelect"></spring:message>---
										</option>
										<c:forEach items="${phase}" var="pha">
											<option value="${pha.id}">${pha.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>


							<div class="form-group row">
								<label for="tableData" class="col-sm-3 col-form-label"
									style="color: #212529;">Table Name</label>
								<div class="col-sm-10">

									<input class="validate" type="radio" name="tableData"
										id="reporting1" value="Reporting"
										onclick="tableNameFunction('reporting1')"> Reporting
									<input class="validate" type="radio" name="tableData"
										id="reporting2" value="StudySubjects"
										onclick="tableNameFunction('reporting2')"> Study
									Subjects
								</div>
							</div>

							<div class="form-group row">
								<label for="subjectData" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.gaSubbAllotment"></spring:message></label>
								<div class="col-sm-10">
									<input class="validate" type="radio" name="subjectData"
										id="subAllotment1" value="Yes"
										onclick="subjectAllotmentFunction('subAllotment1')">
									<spring:message code="label.gaYes"></spring:message>

									<input class="validate" type="radio" name="subjectData"
										id="subAllotment2" value="No"
										onclick="subjectAllotmentFunction('subAllotment2')">
									<spring:message code="label.gaNo"></spring:message>
								</div>
							</div>
							<div class="form-group row">
								<label for="getUrl" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.gaGetUrl"></spring:message></label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="getUrl"
										name="getUrl" />

								</div>
							</div>
							<div class="form-group row">
								<label for="submitControls" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.gaSubmitControl"></spring:message></label>
								<div class="col-sm-10">
									<select name="submitControls" id="submitControls"
										class="form-control validate"
										onchange="saveAndRejectFuntion('submitControls')" multiple
										required="required">
										<option value="-1">---
											<spring:message code="label.sdSelect"></spring:message>---
										</option>
										<option value="Save"><spring:message
												code="label.save"></spring:message></option>
										<option value="Reject"><spring:message
												code="label.reject"></spring:message></option>
									</select>
								</div>
							</div>

							<c:if test="${userRole eq 'SUPERADMIN'}">
								<div id="saveid" class="form-group row">
									<label for="saveUrl" class="col-sm-3 col-form-label"
										style="color: #212529;"><spring:message
											code="label.saveUrl"></spring:message></label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="saveUrl"
											name="saveUrl" maxlength="500"
											onclick="getSaveFuntion('saveUrl','saveUrlMsg')" />
									</div>
								</div>
							</c:if>

							<c:if test="${userRole eq 'SUPERADMIN'}">
								<div class="form-group row" id="rejectid">
									<label for="rejectUrl" class="col-sm-3 col-form-label"
										style="color: #212529;"><spring:message
											code="label.rejectUrl"></spring:message></label>
									<div class="col-sm-10">
										<input type="text" class="form-control" id="rejectUrl"
											maxlength="500" name="rejectUrl"
											onclick="rejectUrlFunction('rejectUrl','rejectUrlMsg')" />
									</div>
								</div>
							</c:if>
							<c:if test="${userRole ne 'SUPERADMIN'}">
								<div>
									<div>
										<input type="hidden" name="saveUrl"></input>
									</div>
									<div>
										<input type="hidden" name="rejectUrl"></input>
									</div>
								</div>
							</c:if>
							<div class="form-group row">
								<div class="col-sm-10" align="center">
									<input type="button" class="btn btn-danger btn-md"
										id="submitId"
										value="<spring:message code="label.submit"></spring:message>"
										onclick="formSubmit()">
								</div>
							</div>
						</div>
					</form:form>



					<!-- <h4 style="text-align:center;padding-top:20px;padding-right:11%">Default Activity List</h4> -->
					<table class="table table-striped table-bordered">
						<tr>
							<th style="text-align: center; padding-right: 11%"><spring:message
									code="label.defaultAct"></spring:message></th>
						</tr>
					</table>
					<!--  <div>
						<h4
							style="text-align: left; margin: 15px; padding: 5px; color: white; background-color: #007399;">Default
							Activity List</h4>
					</div>-->

					<table id="defaultActivity"
						class="table table-striped table-bordered " style="width: 100%;">
						<thead>
							<tr>
								<th class="text-center"><spring:message
										code="label.gaActivity"></spring:message></th>
								<th class="text-center"><spring:message
										code="label.gaParameter"></spring:message></th>
								<th class="text-center"><spring:message
										code="label.gaPhase"></spring:message></th>
								<th class="text-center"><spring:message
										code="label.gaSubbAllotment"></spring:message></th>
								<th class="text-center"><spring:message
										code="label.gaSubmitControl"></spring:message></th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${daList}" var="ac">
								<tr>
									<td>${ac.activity.name}</td>
									<td>
										<table>
											<c:forEach items="${dapMap[ac.id]}" var="dap">

												<tr>
													<td>${dap.parameter.parameterName}</td>
												</tr>
											</c:forEach>
										</table>
									</td>
									<td>${ac.studyPhases.name}</td>
									<td>${ac.subjectAllotment}</td>
									<td>${ac.submitControls}</td>
<!-- 									<td><a href="#" -->
<%-- 										onclick="displayeDefaultActivetyStatusChangePopup('${ac.id}', '${ac.activeStatus}')"> --%>
<%-- 											<c:choose> --%>
<%-- 												<c:when test="${ac.activeStatus}"> --%>
<!-- 												Active		 -->
<%-- 											</c:when> --%>
<%-- 												<c:otherwise> --%>
<!-- 												In-Active -->
<%-- 											</c:otherwise> --%>
<%-- 											</c:choose> --%>
<!-- 									</a></td> -->

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>




	<script type="text/javascript">
		$('#submitId').prop("disabled", false);
		function saveAndRejectFuntion(id) {
			var valuedata = $('#' + id).val();
			//alert(valuedata);
			if (valuedata == "Save,Reject") {
				$('#saveid').show();
				$('#rejectid').show();
			}
			if (valuedata == "Save") {
				$('#saveid').show();
				$('#rejectid').hide();
			}
			if (valuedata == "-1") {
				$('#saveid').hide();
				$('#rejectid').hide();
			}
		}

		function activityFunctionCheckExitOrNot(id, message) {
			var flag = false;
			var value = $('#' + id).val();
			if (value != null && value != "-1" && value != "undefined") {
				var url = mainUrl + '/defaultActivity/activityCheckExitOrNot/'
						+ value;
				var result = synchronousAjaxCall(url).trim();
				if (result == "Yes") {
					$('#' + id).attr("data-isvalid", "true");
					flag = true;
				} else {
					$('#' + id).attr("data-errormessage",
							"Activity Already Exists..!");
					$('#' + id).attr("data-isvalid", "false");
					flag = false;
				}
			} else {
				$('#' + id).attr("data-errormessage", "Required Field..!");
				$('#' + id).attr("data-isvalid", "false");
			}
			checkElementValidation($('#' + id));
			return flag;
		}

		function tableNameFunction(id) {
			var flag = false;
			var vlaue = "";
			var repo1Flag = false;
			var repo2Flag = false;
			if ($('#reporting1').prop("checked")) {
				$('#tableNameVal').val("Reporting");
				repo1Flag = true;
			}
			if ($('#reporting2').prop("checked")) {
				$('#tableNameVal').val("StudySubjects");
				repo2Flag = true;
			}
			if (repo1Flag || repo2Flag) {
				flag = true;
				$('#' + id).attr("data-isvalid", "true");
			} else {
				$('#' + id).attr("data-errormessage", "Required Field..!");
				$('#' + id).attr("data-isvalid", "false");
				flag = false;
				$('#tableNameId').val("");
			}
			checkElementValidation($('#' + id));
			return flag;
		}
		// Data Not Mandatary 
		//Agnai change Data Mandetary
		function subjectAllotmentFunction(id) {
			debugger;
			var falg = false;
			var data1Flag = false;
			var data2Flag = false;
			var dataVlaue2 = "";
			$('#subjectAllotment').val("");
			var vlaue = "";
			if ($('#subAllotment1').prop("checked")) {
				data1Flag = true;
				dataVlaue2 = "Yes";
			}
			if ($('#subAllotment2').prop("checked")) {
				data2Flag = true;
				dataVlaue2 = "No";
			}
			if (data1Flag || data2Flag) {
				$('#subAllotmentMsg').html("");
				$('#subjectAllotmentId').val(dataVlaue2);
				$('#' + id).attr("data-isvalid", "true");
				falg = true;
			} else {
				// $('#subAllotmentMsg').html("Required Field");
				$('#' + id).attr("data-errormessage", "Required Field..!");
				$('#' + id).attr("data-isvalid", "false");
				$('#subjectAllotmentId').val("");
			}
			checkElementValidation($('#' + id));
			return falg;
		}

		function formSubmit() {
			debugger;

			$('#submitId').prop("disabled", true);
			var chekExitOrNot = activityFunctionCheckExitOrNot('activity',
					'activityMsg');

			if (chekExitOrNot) {
				$('#activityMsg').html("");

				var scFlag = false;
				var submitControls = $('#submitControls').val();
				if (submitControls == "Save") {
					$('#submitControls').attr("data-isvalid", "true");
					scFlag = true;
				} else if (submitControls == "Reject") {
					scFlag = false;
					$('#submitControls').attr("data-isvalid", "false");
					/* $.alert({
					    title: 'Alert!',
					    content: 'please select atleast save or save & Reject',
					    type:'red',
					    typeanimated:'true'
					}); */
					$('#submitControls').attr("data-errormessage",
							"please select atleast save or save & Reject.!");
				} else {
					if (submitControls != null && submitControls != ""
							&& submitControls != "undefined")
						$('#submitControls').attr("data-isvalid", "true");
					scFlag = true;
				}
				checkElementValidation($('#submitControls'));
			}

			if (validateElements($("#fsubmit"))) {
				$(".loader").show();
				$('#actid').val($('#activity').val());
				$('#paramid').val($('#parameter').val());
				$('#phaseid').val($('#phase').val());
				$('#fsubmit').submit();
			} else {
				$('#submitId').prop("disabled", false);
			}

		}
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#submitId').removeAttr('disabled');
							var table = $('#defaultActivity')
									.DataTable(
											{
												searchBuilder : true,
												"language" : {
													"lengthMenu" : "${showLabel} _MENU_ ${entriesLabel}",
													"search" : "${searchLabel}:",
													"zeroRecords" : "${noDtaAvlLabel}",
													"info" : "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
													"infoEmpty" : "${noRcdsLabel}",
													"infoFiltered" : "(${filterLabel} _MAX_ ${totRcdsLabel})",
													"paginate" : {
														"previous" : " ${prevPgLabel}",
														"next" : "${nextLabel}"
													}
												}
											});
							//         table.searchBuilder.container().prependTo(table.table().container());
						});
	</script>

	
</body>
</html>