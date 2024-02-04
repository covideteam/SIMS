<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Study Group</title>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>
						<spring:message code="label.study.studyGroups"></spring:message>
					</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<c:url value="/studyGroups/saveStudyGroups" var="saveUrl"></c:url>
					<div class="form-container">
						<form:form action="${saveUrl}" id="saveForm" method="POST"
							modelAttribute="stugs">
							<table
								style="width: 50%; float: left; height: 10%; margin-left: 20%; font-size: 13px;">
								<!-- class="table table-hover table-hover" -->
								<tr>
									<td><spring:message code="label.study.project"></spring:message></td>
									<td><form:select path="study.id" id="projectid"
											onchange="projectidFunction('projectid','projectidMsg')"
											cssClass="form-control input-sm validate">
											<form:option value="-1">----<spring:message
													code="label.ruleSelect"></spring:message>----</form:option>
											<c:forEach items="${stuList}" var="list">
												<form:option value="${list.id}">${list.projectNo}</form:option>
											</c:forEach>
										</form:select>
										<div id="projectidMsg" style="color: red;"></div></td>

								</tr>
							</table>


						</form:form>
						<div id="studygroupdata"></div>
					</div>
					<div></div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="createPreparationModal" tabindex="-1"
		role="dialog" aria-labelledby="createPreparationModal"
		aria-hidden="true">
		<div class="modal-dialog" id="modal-dialog_id" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="ModalLabel">
						<b>Study Group</b>
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div id="createPage"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">
						<spring:message code="label.study.Close"></spring:message>
					</button>
					<button type="button" onclick="submitFormVal()"
						class="btn btn-primary" id="btnupdateSubmit">
						<spring:message code="label.study.Submit"></spring:message>
					</button>

				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="updatePreparationModal" tabindex="-1"
		role="dialog" aria-labelledby="updatePreparationModal"
		aria-hidden="true">
		<div class="modal-dialog" id="modal-dialog_id" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="ModalLabel">
						<b><spring:message code="label.study.studyGroupUpdate"></spring:message></b>
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div id="updatePage"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">
						<spring:message code="label.study.Close"></spring:message>
					</button>
					<button type="button" onclick="updateFormVal()"
						class="btn btn-primary" id="btnupdateSubmit">
						<spring:message code="label.study.Update"></spring:message>
					</button>

				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
	
	
	function projectidFunction(id, message){
		debugger;
		var flagv=false;
  		var value = $('#'+id).val();
  		$('#studygroupdata').html("");
  		if(value == null || value == "" ||value == "-1" || value == "undefined"){
  			$('#'+message).html('${validationText}');
  			flagv = false;
  		}else{
  			var url=mainUrl+"/studyGroups/getstudyGroupsListWithStuddyId/"+value;
  			var result=synchronousAjaxCall(url);
  			$('#studygroupdata').html(result);
  			$('#'+message).html("");
  			flagv = true;
  		}
  		return flagv;
  	} 
	function formFunction(id) {
		   $('#studygroupcreation').html("");
			var url=mainUrl+"/studyGroups/studyGroupsCreation/"+id;
			var result=synchronousAjaxCall(url);
			if(result != null && result != "" && result != "undefined"){
				$('#createPage').html(result);
				 
				 $("#createPreparationModal").modal('show');
				 
			}
			
	}
	function formUpdateFunction(id) {
	    debugger;
		$('#studygroupcreation').html("");
		var url=mainUrl+"/studyGroups/studyGroupsUpdate/"+id;
		var urlVal=mainUrl+"/studyGroups/studyGroupsUpdateCheckValidation/"+id;
		var result=synchronousAjaxCall(url);
		var result2=synchronousAjaxCall(urlVal);
		if(result2 == "Yes"){
		if(result != null && result != "" && result != "undefined"){
			$('#updatePage').html(result);
			$("#updatePreparationModal").modal('show');
			 
		}
		}else{
			swal.fire('Group Update Prevented Beacause Already Group Exits');
		}
	}
	
	</script>
</body>
</html>