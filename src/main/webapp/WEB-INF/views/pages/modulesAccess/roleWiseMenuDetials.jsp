<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Roles wise Menu Details</title>
<style type="text/css">
.insmod {
	display: none; 
	position: fixed;
	z-index: 1;
	padding-top: 100px; 
	left: 0;
	top: 0;
	width: 100%; 
	height: 100%;
	overflow: auto; 
	background-color: rgb(0, 0, 0); 
	background-color: rgba(0, 0, 0, 0.4); 
}
</style>
</head>
<body oncontextmenu="return false;">
	 <input type="hidden" name="roleId" id="roleId" value="${roleId}">
	 <c:url value="/modulesAccess/saveSideMenuLinks" var="saveData"></c:url>
      <form:form action="${saveData}" method="POST">
          <c:forEach items="${menusMap}" var="entry">
          <c:set var="string1" value="${entry.key}"/>
          </c:forEach>
			 <div  style="background: #2a3f54; color:white;height:40px">
      			 <a class="card-link" style="color:white;margin: 15px;"  data-toggle="collapse" href="#${string1}">${string1}</a>
			 </div>
			<div id="listData"></div>
		  </form:form>
      
			<div id="links" class="modal fade" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			    <div class="modal-dialog">
			         <div class="modal-content" style="width:160%;margin-left: -100px;">
			            <div class="modal-header" style="background: #2a3f54; text-align: center;">
			                <div align="center" style="color: white;font-weight: bold;">All Links</div>
			                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			            </div>
			            <div class="modal-body" >
			           </div>
			            <div class="modal-footer">
			                
			            </div>
			        </div>
			       
			    </div>
			</div>
	<script type="text/javascript">
	
	
		function showAllLinks(){
			debugger;
			//alert("check1");
			var roleId = $("#roleId").val();
			var url = mainUrl+'/modulesAccess/getAllModuleLinksRole/'+roleId;
			var result = synchronousAjaxCall(url);
		    var body = result;
		    $("#listData").html(body);
		}
	
$(document).ready(function() {
	debugger;
	
	showAllLinks();
    $('#linksList').DataTable({
    	"scrollX": true
    });
} );
</script>
</body>
</html>