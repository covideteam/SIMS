<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="card" >
		<div class="card-header">
               <div class="card-title" style="color:white; font-size: large; font-weight: bold; text-align: center;">Dynamic Activity Update</div>
        </div>	
        
          <div>
        	<c:url value="/activity/updateActivity" var="updateactivity"></c:url>
        	<form:form action="${updateactivity}" method="POST" id="updateactivity" modelAttribute="atpojo">
        	<form:hidden path="id" value="${idval}" />
        	<table class="table table-bordered table-striped" style="width: 100%;">
        		<tr>
        		<tr>
        			<th>Language :</th>
        			<td >
        				<form:input path="language" id="language" readonly="true" onblur="activityNameValidation('activityName', 'activityNameMsg')"  class="form-control"/>
        				<div id="activityNameMsg" style="color: red;"></div>
        			</td>
        		</tr>
        		<tr>
        			<th>Activity Name :</th>
        			<td >
        				<form:input path="activityName" id="activityName" readonly="true"  onblur="activityNameValidation('activityName', 'activityNameMsg')"  class="form-control"/>
        				<div id="activityNameMsg" style="color: red;"></div>
        			</td>
        		</tr>
        		<tr>
        			<th>Activity Desc :</th>
        			<td>
        				<form:input path="activityDesc" id="activityDesc" onblur="activityDescValidation('activityDesc', 'activityDescMsg')"  class="form-control"/>
        				<div id="activityDescMsg" style="color: red;"></div>
        			</td>
        		
        		</tr>
        		
        		 <tr >
        			<td colspan="2" align="center">
        			  <input type="button" value="Submit" class="btn btn-danger btn-md"  onclick="submitForm()">
        			</td>
        		</tr>
        	</table>
        	</form:form>
        </div>
        
</div>

<script type="text/javascript">
function submitForm(){
	alert("sdfsd");
	$('#updateactivity').submit();
}
</script>
</body>
</html>