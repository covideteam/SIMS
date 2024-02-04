<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
      <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
					<h2><spring:message code="label.Trigger.Titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
			<c:url value="/triggerController/saveTriggerCall" var="saveTrigger"></c:url>
        	<form:form action="${saveTrigger}" method="POST" id="saveTrigger" >
        	
        	</form:form>
	   <div align="center">
	   <input type="button" value="<spring:message code="label.Trigger.Get"></spring:message>" class="btn btn-danger btn-md	"  onclick="triggerFunction()">
	   </div>
	   </div>
	   </div>
	   </div>
	   </div>
	   
	   <script type="text/javascript">
		  function  triggerFunction(){
			  $('#saveTrigger').submit();
		  }
	   
	   </script>
	   
	   
</body>
</html>