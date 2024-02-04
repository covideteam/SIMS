<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

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
					<h2>Randomization</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<c:url value="/randomization/saveUploadRandomization" var="dataUrl"></c:url>
					<form:form action="${dataUrl}" method="POST" id="fsubmit"
						enctype="multipart/form-data">
						<div style="width:100%;">
							<table class="table table-border table-striped" style="width: 100%;">
								<tr>
									<td><spring:message code="label.randomizationUploadFile"></spring:message>
									</td>
									<td><input type="file" id="filea" name="file"
										onchange="uploadFileFunction()">
									<td>
									<td><spring:message
											code="label.randomizationSelectProject"></spring:message></td>
									<td><select id="projectNoVal" name="projectNoVal"
										class="form-control"
										onchange="selectProjectFunction('projectNoVal','projectNoMsg')">
											<option value="-1">---Select---</option>
											<c:forEach items="${proListShow}" var="list">
												<option value="${list.projectId}">${list.projectNo}</option>
											</c:forEach>
									</select> <span id="projectNoMsg" style="color: red;"></span></td>
									<td><spring:message code="label.randomizationUpComments"></spring:message>
									</td>
									<td><textarea name="commentsf" id="commentsfid"
											onclick="commentsFunction('commentsfid','commentsfidMsg')"></textarea>
										<span style="color: red;" id="commentsfidMsg"></span></td>
								</tr>
								<tr style="text-align: center;">
									<td><span id="fileMsg" style="color: red;"></span></td>
								</tr>
								<c:choose>
									<c:when test="${firstRow eq userRole}">
										<tr align="center">
											<td colspan="8"><input type="button" id="addBtn"
												value="<spring:message code="label.randomizationSubmit"></spring:message> "
												class="btn btn-danger btn-md" onclick="submitFuntion()">
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="7" style="color: red;" align="center">File
												Upload Role Not Match</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
					</form:form>
					<table class="table table-border table-striped">
        <thead>
	        <tr>
	       <th><spring:message code="label.randomizationProjectNo"></spring:message></th>
	         <th><spring:message code="label.randomizationCreatedBy"></spring:message></th>
	          <th><spring:message code="label.randomizationCreatedOn"></spring:message></th>
	        </tr>
        </thead>
        <tbody>
        <c:forEach items="${proList}" var="list">
        <tr>
           <td><a href='<c:url value="/randomization/randomizationFileView/${list.projectId}"/>'>${list.projectNo}</a></td>
           <td>${list.createdBy}</td>
            <td><fmt:formatDate value="${list.createdOn}" pattern="${dateFormat}" /></td>
        <tr>
        </c:forEach>
        </tbody>
        
        </table>
				</div>
			</div>
		</div>
	</div>

	
        



<script type="text/javascript">
$('#addBtn').prop("disabled", false);
function uploadFileFunction(){
	$("#fileMsg").html("");
	var vidFileLength = $("#filea")[0].files.length;
	if(vidFileLength === 0){
		 $("#fileMsg").html("No file selected.");
	}else{
		 var vidFileName = $("#filea").val();
		 var ext = vidFileName.split('.').pop();
	      if(ext == "xlsx" || ext == "xls" ||ext=="pdf"){
	    	  $("#fileMsg").html("");
	      }else{
	    	  $("#fileMsg").html("Only Alowed Pdf Or Excel Files");
	      }
	}
}
function selectProjectFunction(id, message){
	var dataFlag=false;
	var value = $("#"+id).val();
//	alert(id);
		if(value == null || value == "-1"|| value == " " || value == "undefined"){
			/* $('#'+message).html("Required Field"); */
			$('#'+message).html('${validationText}');
			dataFlag = false;
		}else{
			$('#'+message).html("");
			dataFlag = true;
		}
		return dataFlag;
	
}
function commentsFunction(id, message){
	var dataFlag=false;
	var value = $("#"+id).val();
//	alert(id);
		if(value == null || value == "-1"|| value == " " || value == "undefined"){
			/* $('#'+message).html("Required Field"); */
			$('#'+message).html('${validationText}');
			dataFlag = false;
		}else{
			$('#'+message).html("");
			dataFlag = true;
		}
		return dataFlag;
	
}


function submitFuntion(){
	$('#addBtn').prop("disabled", true);
	var vidFileLength = $("#filea")[0].files.length;
	if(vidFileLength === 0){
	   // alert("No file selected.");
	    $("#fileMsg").html("No file selected.");
	}else{
		 var vidFileName = $("#filea").val();
		 var ext = vidFileName.split('.').pop();
	      if(ext == "xlsx" || ext == "xls" ||ext=="pdf"){
	    	 $("#fileMsg").html("");
	    	 $("#projectNoMsg").html(" ");
	    	 var flag1= selectProjectFunction('projectNoVal','projectNoMsg');
	    	 var flag2= commentsFunction('commentsfid','commentsfidMsg');
	    	 
	    	// alert(flag1);
	    	 if(flag1&& flag2){
	    		 $("#fsubmit").submit();
	    	 }else{
	    		 $('#addBtn').prop("disabled", false);
	    		/*  $("#projectNoMsg").html("Required Field"); */
	    		 $('#projectNoMsg').html('${validationText}');
	    	 }
	      }else{
	    	  $("#fileMsg").html("Only Alowed Pdf Or Excel Files");
	      }
	}
}

</script>
</body>
</html>