<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User  Information</title>
<link rel="stylesheet" href="<c:url value="/static/datepickertheme/jquery-ui.css"/>">

<script type="text/javascript">
	$(document).ready(function() {
		$('#specificAudit').DataTable();
		$('.dataTables_length').addClass('bs-select');
	});
</script>
</head>
<body oncontextmenu="return false;">
		<div class="card">
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
					<h2><spring:message code="label.userLog.Titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
	      <table style="width: 100%;">
				<tr>
					<th><spring:message code="label.userLog.FormDate"></spring:message></th>
					<td> <input type="date" name="fdate" id="fdate" class="form-control">
						<!-- <script>
							$(function() {
								$("#fdate").datepicker({
									dateFormat : "yy-mm-dd",
									changeMonth : true,
									// minDate: 0,
									changeYear : true
								});
							});
						</script> -->
					</td>
					<th><spring:message code="label.userLog.ToDate"></spring:message></th>
					<td><input type="date" name="tdate" id="tdate" class="form-control">
						<!-- <script>
							$(function() {
								$("#tdate").datepicker({
									dateFormat : "yy-mm-dd",
									changeMonth : true,
									// minDate: 0,
									changeYear : true
								});
							});
						</script> -->
					</td>
					<th><spring:message code="label.userLog.User"></spring:message><th>
					<td><select onchange="userCheck(this.value)" name="userName" id="userName" class="form-control">
								<option value="-1">--Select--</option>
								<c:forEach items="${userList}" var="us">
									<option value="${us.username}">${us.username}</option>
								</c:forEach>
						</select></td>
				</tr>
				<tr><td colspan="7" align="center"><span style="color: red;" id="message"></span></td></tr>
				<tr><td colspan="7" align="center"><input type="button" value="<spring:message code="label.userLog.Submit"></spring:message>" class="btn btn-danger btn-md	"  onclick="fromValidation('fdate', 'tdate')">
				</td>
				</tr>
			</table>
       <div id="userDetails"></div>
       </div>
       </div>
       </div>
       </div>
       </div>
       
</body>
<script type="text/javascript">
	
	function fromValidation(fdate, tdate) {
		var fromdate = $('#' + fdate).val();
		var todate = $('#' + tdate).val();
		var userName = $('#userName').val();
		$('#message').html("");
		//alert("tredt");
		if (fromdate != "" && todate != "") {
			$('#message').html("")
			var url = mainUrl + "/auditlog/userLoingAndLogoutData/" +fromdate+"/"+todate+"/"+userName;
			var result = synchronousAjaxCall(url);
			//alert(result);
			if(result !="" || result !="undefined")
				$('#userDetails').html(result);
		} else {
			$('#message').html("From Date And To Date Required Fields");
		}

	}
</script>
</html>