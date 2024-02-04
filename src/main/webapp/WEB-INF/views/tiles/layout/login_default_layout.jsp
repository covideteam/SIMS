<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><tiles:getAsString name="title" /></title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
  <link rel="stylesheet" href='<c:url value="/static/css/login.css"/>'>
  <link rel="stylesheet" href='<c:url value="/static/css/main.css"/>'>
  <link rel="stylesheet" href='<c:url value="/static/confirmjs/jquery-confirm.min.css"/>'>
  <link rel="stylesheet" href="<c:url value='/static/vendors/bootstrap/dist/css/bootstrap.min.css'/>"/>
  <%-- <link rel="stylesheet" href="<c:url value='/static/admintltcss/dist/css/adminlte.css'/>"/> --%>
  
</head>  
<body oncontextmenu="return false;">
	<tiles:insertAttribute name="body" /> 
	
	<div class="loader">
	  <span class="loader__element"></span>
	  <span class="loader__element"></span>
	  <span class="loader__element"></span>
	</div>
<!-- jQuery 3 -->
<!-- jQuery -->
<script src="<c:url value='/static/vendors/jquery/dist/jquery.min.js'/>"></script>
<!-- Bootstrap -->
<script src="<c:url value='/static/vendors/bootstrap/dist/js/bootstrap.bundle.min.js'/>"></script>
<script src='<c:url value="/static/confirmjs/jquery-confirm.min.js"/>'></script>
<script src='<c:url value="/static/js/login.js"/>'></script>
<script src='<c:url value="/static/js/main.js"/>'></script>
<script type="text/javascript">
function resetFunction(){
	window.location.reload()
}
</script>
</body>
</html>