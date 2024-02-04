<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Custom Activity Parameters</title>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.cusActParmFormTitle"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>

					<c:url value="/customActParams/saveCustomActivityParamData"
						var="saveCustomActParmData"></c:url>
					<form:form action="${saveCustomActParmData}" method="POST"
						id="customParmForm">
						<div style="width: 75%; margin-left: 10%;">
							<div class="form-group row">
								<label for="activity" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.cusActParmAct"></spring:message></label>
								<div class="col-sm-5">
										<select name="activity" id="activity" class="form-control validate"
											onchange="actFunction('activity', 'activityMsg')">
											<option value=" ">----<spring:message code="label.ruleSelect"></spring:message>----</option>
											<c:forEach items="${actList}" var="act">
												<option value="${act.id}">${act.name}</option>
											</c:forEach>
										</select>
								</div>
							</div>
							<div class="form-group row">
								<label for="actparm" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.cusActParmParm"></spring:message></label>
								<div class="col-sm-5">
										<select name="actparm" id="actparm" class="form-control validate"
											onchange="actparmFunction('actparm', 'actparmMsg')">
											<option value=" ">----
												<spring:message code="label.ruleSelect"></spring:message>----
											</option>
										</select>
								</div>
							</div>
							<div class="form-group row">
								<label for="parameterValue" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.cusparameterValue"></spring:message></label>
								<div class="col-sm-5">
										<input type="text" name="parameterValue" id="parameterValue" maxlength="500"
											class="form-control validate" />
								</div>
							</div>
							<div class="form-group row" >
								<div align="center" class="col-sm-9"><input type="button" value="<spring:message code="label.submit"></spring:message>" id="submitId"
									class="btn btn-danger btn-md" onclick="submitFunction()"></div>
							</div>
						</div>
					</form:form>

					<div>
						<table class="table table-striped table-bordered">
							<tr>
								<th style="text-align: center; padding-right: 5%"><spring:message code="label.cusActParmList"></spring:message></th>
									
							</tr>
						</table>

						<table id="customActTab"
							class="table table-striped table-bordered nowrap"
							style="width: 100%" >
							<thead>
								<tr>
									<th><spring:message code="label.cusActParmAct"></spring:message></th>
									<th><spring:message code="label.cusActParmParm"></spring:message></th>
									<th><spring:message code="label.cusparameterValue"></spring:message></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${ccList}"  var="cap">
							<tr>
							<td>${cap.activity.name}</td>
							<td>${cap.parameter.parameterName}</td>
							<td>${cap.parameterValue}</td>
							</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>


<script type="text/javascript">
$('#submitId').prop("disabled", false);
var actVal ="";
function actFunction(id, messageId){
		debugger;
	var flag = false;
	var value = $('#'+id).val();
	if(value == null || value == " " || value == "undefined"){
		actVal = "";
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
		$('#actparm').empty();
		$('#actparm').append('<option value=" ">--Select--</option>');
		$('#actparmMsg').html("");
	}else{
		if(actVal != value){
			$('#actparm').empty();
			$('#actparm').append('<option value=" ">--Select--</option>');
			actVal = value;
		}
		$('#'+messageId).html("");
		var actParamVal = $('#actparm').val();
		if(actParamVal == null || actParamVal == " " || actParamVal == "undefined"){
			$('#actparm').empty();
			$('#actparm').append('<option value=" ">--Select--</option>');
			var reuslt2 = synchronousAjaxCall(mainUrl+"/rules/getActivityParameters/"+value);
			var d = JSON.parse(reuslt2);
			for(var i=0; i<d.length; i++){
				$('#actparm').append('<option value='+d[i].parameterId+' id='+d[i].parameterId+'>'+d[i].parameterName+'</option>');
			}
		}
		flag=true;
		$('#'+id).attr("data-isvalid","true");
		checkElementValidation($('#'+id));
	}
	return flag;
}
function actparmFunction(id, messageId){
	debugger;
	var flag = false;
	$('#'+messageId).html("");
	var value = $('#'+id).val();
	if(value == null || value == " " || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}else{
		var reuslt3 = synchronousAjaxCall(mainUrl+"/customActParams/checkActivityAndParameterExistingOrNot/"+value);
	    if(reuslt3=="undefined"){
	    	$('#'+id).attr("data-errormessage","Required Field..!");
			$('#'+id).attr("data-isvalid","false");
			flag=false;
	    }else{	
		if(reuslt3=="Yes"){
			$('#'+id).attr("data-isvalid","true");
			flag=true;
		}else{
			$('#'+id).attr("data-errormessage","Parameter Already Exists...!");
			$('#'+id).attr("data-isvalid","false");
			flag=false;
		}
	    }
	}
	checkElementValidation($('#'+id));
	return flag;
} 

function submitFunction(){
	$('#submitId').prop("disabled", true);
   if(validateElements($("#customParmForm"))){
	    $(".loader").show(); 
		$('#customParmForm').submit();
   }else{
	   $('#submitId').prop("disabled", false);
   }
}


</script>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#addBtn').removeAttr('disabled');
        var table = $('#customActTab').DataTable({
            searchBuilder: true,
            "language": {
                "lengthMenu": "${showLabel} _MENU_ ${entriesLabel}",
        		 "search": "${searchLabel}:",
        		 "zeroRecords": "${noDtaAvlLabel}",
        	     "info": "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
        	     "infoEmpty": "${noRcdsLabel}",
        	     "infoFiltered": "(${filterLabel} _MAX_ ${totRcdsLabel})",
        	     "paginate": {
        	         "previous": " ${prevPgLabel}",
        	        	 "next": "${nextLabel}"
        	       }
        		 }
        });
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
</body>
</html>