<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags"%>
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
					<h2>Dosing Activity</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>

				
						<c:url value="/dosingActivity/dosingActivityForStudySave"
							var="dataUrl"></c:url>
						<form:form action="${dataUrl}" method="POST" id="fsubmit">
							<input type="hidden" name="proid" id="proid">
							
							<div style="width: 85%; margin-left: 10%;">
							
									<div class="form-group row">
										<label for="project" class="col-sm-3 col-form-label" style="color: #212529;">Select Project</label>
										<div class="col-sm-5"><select name="project" id="project"
											class="form-control"
											onchange="projectFunction('project', 'projectMsg')">
												<option value="-1">----Select----</option>
												<c:forEach items="${sml}" var="sml">
													<option value="${sml.id}">${sml.projectNo}</option>
												</c:forEach>
										</select>
											<div id="projectMsg" style="color: red;"></div></div>
									</div>
									
									<div class="form-group row">
										<label for="dosing" class="col-sm-3 col-form-label" style="color: #212529;">Dosing Activity Completed</label>
										<div class="col-sm-5"><input type="radio" name="dosing" id="dosing_yes"
											value="Yes" onclick="dosingValidation()">Yes <input
											type="radio" name="dosing" id="dosing_no" value="No"
											onclick="dosingValidation()">No</div>
									</div>
									<div class="form-group row">
										<label for="ifno" class="col-sm-3 col-form-label" style="color: #212529;">If no,further dosing activity schedule</label>
										<div class="col-sm-5"><input type="radio" name="ifno" id="ifno_yes"
											value="Yes" onclick="ifnoValidation()">Yes <input
											type="radio" name="ifno" id="ifno_no" value="No"
											onclick="ifnoValidation()">No</div>
									</div>

									<div class="form-group row">
										<div class="col-sm-10" align="center"><input type="button" value="Submit"
											class="btn btn-danger btn-sm" id="submitId" onclick="subFuntion()"
											id="addBtn">
											<div id="subMsg" style="color: red;"></div></div>

									</div>
									<div class="form-group row">
										<div class="col-sm-5" id="dta"></div>
									</div>
								</div>
							
						</form:form>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
function projectFunction(id,messageId){
 var flag=true;
 
    var val=$('#'+id).val();
  // alert(val);
	if(val!="-1" &&val!="undefined"){
		$('#proid').val(val);
		var url = mainUrl+'/dosingActivity/getSujectListOfStudy/'+val;
		var result = synchronousAjaxCall(url).trim();
	//	alert(result);
		$('#dta').html(result);
		$('#'+messageId).html("");
		flag=true;
	}else{
     	$('#'+messageId).html("Requeired Field");
		flag = false;
	}
	return flag;
}

function subFuntion(){
	$('#submitId').attr('disabled','disabled');
	if($('#dosing_no').prop("checked") && $('#ifno_yes').prop("checked")){
		$('#subMsg').html("");
		var data=$('#project').val();
		$('#proid').val(data);
		var check=projectFunction('project', 'projectMsg');
		if(check){
			//alert($('#subjectSize').val());
			//alert($('#projeSize').val());
			if($('#projeSize').val()< $('#subjectSize').val()){
				$(".loader").show();
				$('#fsubmit').submit();
			}else{
				$('#subMsg').html("Subject Count Out Of Range");
				$('#submitId').removeAttr('disabled');
			}
		}
	}else{
		$('#subMsg').html("Data not satisfy");
		$('#submitId').removeAttr('disabled');
	}
}

$(document).ready(function() {
	$('#submitId').removeAttr('disabled');
//     table.searchBuilder.container().prependTo(table.table().container());
});

</script>




</html>