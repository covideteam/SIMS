<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
	<section class="content-header" id="site-content">  
<!-- 		<h3></h3> -->
		<c:if test="${pageMessage != null && pageMessge != ''}">
			<div class="alert alert-success" role="alert" style="text-align: center; width: 70%; margin-left: 15%" align="center">
        		<a href="#" class="close" data-dismiss="alert" style="text-decoration: none;">&times;</a>
        		<strong><spring:message code="label.success"></spring:message> </strong>${pageMessage}
 		    </div>
		</c:if>
		<c:if test="${pageError != null && pageError != ''}">
			<div class="alert alert-danger" role="alert" style="text-align: center; width: 70%; margin-left: 15%" align="center">
  				<a href="#" class="close" data-dismiss="alert" style="text-decoration: none;">&times;</a>
        		<strong><spring:message code="label.failed"></spring:message></strong>${pageError}
       		</div>
		</c:if>
		<c:if test="${pageWarning != null && pageWarning != '' }">
			<div class="alert alert-warning" role="alert" style="text-align: center; width: 70%; margin-left: 15%" align="center">
  				<a href="#" class="close" data-dismiss="alert" style="text-decoration: none;">&times;</a>
        		<strong><spring:message code="label.failed"></spring:message></strong>${pageWarning}
       		</div>
       	</c:if>
<!-- 		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		</ol> -->
	</section>
</body>
</html>