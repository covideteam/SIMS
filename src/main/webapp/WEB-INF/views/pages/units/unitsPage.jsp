<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Units</title>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.unit.units"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
						<c:url value="/units/saveUnits" var="saveUnits"></c:url>
						<div class="form-container">
						<form:form action="${saveUnits}" id="saveUnitsForm" method="POST" modelAttribute="unitEntity">
						  <div class="row">
							 <div class="col-md-3 label">
					          <label for="unitsCode" class="col-sm-4" style="color: #212529;"><spring:message code="label.unit.units"></spring:message></label>
                             </div>
                              <div class="col-md-3">
                              <form:input path="unitsCode" id="unitsCode" cssClass="form-control input-sm validate" onblur="unitCodeFunction('unitsCode')" maxlength="255"/>
					              </div>
					          </div>
					          <div class="form-group row">
									<div class="col-sm-10" align="center">
									<input type="button" onclick="submitForm()" value='<spring:message code="label.submit"></spring:message>' class="btn btn-danger btn-sm" id="addBtn">
									
							  </div>
							</div>
						</form:form>
					</div>
					<div>
						<table class="table table-striped table-bordered">
							<tr>
								<th style="text-align: center; padding-right: 15%"><spring:message code="label.unit.units"></spring:message></th>
							</tr>
						</table>

						<table id="unitsCodeForm"
							class="table table-striped table-bordered nowrap"
							style="width: 100%">
							<thead>
								<tr>
									<th><spring:message code="label.unit.units"></spring:message></th>
									<th><spring:message code="label.createdBy"></spring:message></th>
									<th><spring:message code="label.createdOn"></spring:message></th>
								</tr>
							</thead>
							<tbody>
						<c:forEach items="${unitList}" var="uni">
							<tr>
							<td>${uni.unitsCode}</td>
							<td>${uni.createdBy}</td>
							 <td><fmt:formatDate value="${uni.createdOn}" pattern="${dateFormat}" /></td>
							</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
		</div>
	</div>
</div>

    <script src='/SIMS/static/Tooltips/Tooltip.js'></script>
    
	<script type="text/javascript">
	$('#addBtn').prop("disabled", false);
	$(document).ready(function() {
		
        var table = $('#unitsCodeForm').DataTable({
            searchBuilder: true,
        });
    });
	
	function unitCodeFunction(id){
		debugger;
		var flag = false;
		var value = $('#'+id).val().trim();
		if(value == null || value =="" || value == "undefined"){
			 $('#'+id).attr("data-errormessage"," Required Field..!");
			$('#'+id).attr("data-isvalid",false); 
			
		}else{
			if(value.indexOf("/") != -1){
			    value = value.replace("/", "@@@");
			}
			var url = mainUrl+'/units/checkUnitsName/'+value;
			var result = synchronousAjaxCall(url);
			if(result == 'yes'){
				$('#'+id).attr("data-errormessage", $('#'+id).val() + " already exists.");
				$('#'+id).attr("data-isvalid",false);
				//$('#'+id).val("");
			}else{
				$('#'+id).attr("data-isvalid",true);
			}
		}
		checkElementValidation($('#'+id));
		return flag;
	}
	
	
	function submitForm(){
		debugger;
		var flag=true;
		$('#addBtn').prop("disabled", true);
		/* var unit = validateForm('unitsCode','text'); */
        var dsFlag = true;
        
		if(validateElements($("#saveUnitsForm"))){
			
			$("#saveUnitsForm").submit();
		}else{
			$('#addBtn').prop("disabled", false);
		}
	}
	
	</script>
</body>
</html>