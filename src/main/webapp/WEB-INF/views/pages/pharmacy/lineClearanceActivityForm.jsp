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
width:200px;
}
</style>
</head>
<body>

	   
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.lineClearanceActivity.titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/pharmacyData/saveLineClearanceActivity" var="saveData"></c:url>
        	<form:form action="${saveData}" method="POST" id="saveForm" >
        	<input type="hidden" name="dataid" value="${sdd.id}">
        	<table  style="width: 70%;font-size: 15px;height:70%"><!-- class="table table-hover table-hover" --> 
        	<tr>
        	 <td> 
        	   <label for="projectno" class="col-form-label" style="color: #212529;"><spring:message code="label.lineClearanceActivity.projectNo"></spring:message></label>
        	</td>
        	<th> 
        	 ${sdd.projectId.projectNo}
        	</th> 
        	<td> 
        	<label for="period" class="col-form-label" style="color: #212529;"><spring:message code="label.lineClearanceActivity.period"></spring:message></label>
        	</td>
        	<th> 
        	   ${sdd.periodId.periodName}
        	</th>
        	 <td> 
        	   <label for="typeOfProduct" class="col-form-label" style="color: #212529;"><spring:message code="label.lineClearanceActivity.typeOfProduct"></spring:message></label>
        	</td>
        	<th> 
        	  ${sdd.typeOfProduct}
        	</th> 
        
        	</tr>
        	
        	 <tr>
        	<td><label for="workingAreaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.lineClearanceActivity.workingAreaClean"></spring:message></label></td>
        	<th> ${sdd.workingAreaClean}
        	</th> 
        	
        	<td><label for="randomizationCode" class= "col-form-label" style ="color:#212529;"><spring:message code="label.lineClearanceActivity.randomaizationcode"></spring:message></label></td>
        	<th>  ${sdd.randamizationCode} </th>
        	<td><label for="areaClean" class="col-form-label" style="color: #212529;"><spring:message code="label.lineClearanceActivity.areaClear"></spring:message></label></td>
        	<th> ${sdd.areaClean}
        	</th>
        	</tr> 
        	<tr>
 				<td colspan="6" style="text-align: center;"><input type="button" value="<spring:message code="label.button.accept"></spring:message>" onclick="submitFunctionData()" class="btn btn-danger btn-sm" id="addBtn"></td>
			</tr>
        	</table>
        	</form:form>
        	</div>
        	</div>
        	</div>
        	</div>
        	
        	<script type="text/javascript">
        	
       	
        	function submitFunctionData(){
        			$('#saveForm').submit();
            }
    		</script>
</body>
</html>