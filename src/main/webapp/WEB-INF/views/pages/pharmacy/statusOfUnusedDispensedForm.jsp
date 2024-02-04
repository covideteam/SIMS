<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
td{
width:190px;
}
</style>
</head>
<body>
	   
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.statusOfUnusedDispensed.titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/pharmacyData/saveStatusOfUnusedDispensed" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" modelAttribute="soupojo">
        	
        	<table  style="width: 80%;font-size: 15px;height:70%"><!-- class="table table-hover table-hover" --> 
        	<tr>
        	 <td> 
        	   <label for="projectno" class="col-form-label" style="color: #212529;"><spring:message code="label.statusOfUnusedDispensed.projectNo"></spring:message></label>
        	</td>
        	<td> 
        	  <form:select  path="projectId.projectId" id="projectno" onchange="projectIdValidation('projectno','projectnoMsg')"  Class="form-control autosave reviewElement validate" data-validate="required">
        	             <option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
        	              <c:forEach items="${plist}" var="list">
        	              <option value="${list.projectId}">${list.projectNo}</option>
        	             </c:forEach>
        	  </form:select>
                      <div id="projectnoMsg" style="color: red;"></div>
        	</td> 
        	<td><label for="sealedCode_Yes" class="col-form-label" style="color: #212529;"><spring:message code="label.statusOfUnusedDispensed.bioWaste"></spring:message></label></td>
        	<td><input type="radio" name="bioWaste" id="sealedCode_Yes"value="Yes" onclick="bioWasteValidation()" required><label for="sealedCode_Yes"><spring:message code="label.gaYes"></spring:message></label>
        		<input type="radio" name="bioWaste" id="sealedCode_No" value="No" onclick="bioWasteValidation()"><label for="sealedCode_No"><spring:message code="label.gaNo"></spring:message></label>
        		<input type="radio" name="bioWaste" id="sealedCode_Na" value="NA" onclick="bioWasteValidation()"><label for="sealedCode_Na"><spring:message code="label.gaNa"></spring:message></label>
        	 <div id="bioWasteMsg" style="color: red;"></div>
        	</td>
        	</tr>
        	<tr>
        	<td><label for="reason" class="col-form-label" style="color: #212529;"><spring:message code="label.statusOfUnusedDispensed.reason"></spring:message></label></td>
        	<td><form:input path="reason" autocomplete="off" Class="form-control autosave reviewElement validate" data-validate="required"  />
        		
        	</td>
        	</tr>
        	<tr>
 				<td colspan="6" style="text-align: center;"><input type="button" value="<spring:message code="label.submit"></spring:message>" onclick="submitFunctionData()" class="btn btn-danger btn-sm" id="addBtn"></td>
			</tr>
        	</table>
        	</form:form>
        	</div>
        	</div>
        	</div>
        	</div>
        	
        	<script type="text/javascript">
        	var projFlag = false;
        	function projectIdValidation(id, message){
        		 debugger;
        		var value = $('#'+id).val();
        		if(value == null || value == "" || value == "undefined"||value==-1){
        			$('#'+message).html('${validationText}');
        			projFlag = false;
        		}else{
        			$('#'+message).html("");
        			projFlag = true;
        		}
        		
        		return projFlag;
        	} 
        	
              function bioWasteValidation(){
        		
        		if($('#sealedCode_Yes').prop("checked") ||$('#sealedCode_No').prop("checked")||$('#sealedCode_Na').prop("checked")){
        			$('#bioWasteMsg').html("");
        			rDAFlag = true;
        		}else{
        			$('#bioWasteMsg').html('${validationText}');
        			rDAFlag = false;
        		}
        		//condition();
        		return rDAFlag;
        	}
        	
        	function submitFunctionData(){
        		var bioWaste = bioWasteValidation();
        		var projectIdFlag = projectIdValidation('projectno','projectnoMsg');
        		debugger;
        		if(validation.validate($('#saveForm')[0])&&bioWaste&&projectIdFlag){
        			//alert("okk working");
        			$('#saveForm').submit();
        		}
            }
    		</script>
</body>
</html>