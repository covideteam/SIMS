<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
</head>
<body oncontextmenu="return false;">
<div class="card" >
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
		<div class="card-header">
			<h3 class="card-title">Change Password</h3>
		</div>
	<div>
	<c:url value="/administration/updatePassword" var="formsumit1" />
	<sf:form action="${formsumit1}" method="POST"  id="formsumit" >

	<!-- /.card-header -->
	<div class="card-body">
		<table class="table table-bordered table-striped" style="width: 65%;">
			<tr>
				<th>New Password :</th>
				<td>
					<input type="password" class="form-control" name="pws1" id="pws1" /><font color="red" id="pws1msg"></font>
				</td>
			</tr>
			<tr>
				<th>Repeat Password :</th>
				<td><input type="password" class="form-control" name="pws2" id="pws2"/><font color="red" id="pws2msg"></font></td>
			</tr>
			<tr>
				<td/>
				<td>
					<input type="button" value="Update" class="btn btn-danger btn-md" onclick="submitForm()"/>
				</td>
			</tr>
		</table>
	</div>
	</sf:form>
	</div>
</div>
<script>
function submitForm(){
	$("#pws1msg").html("");
	$("#pws2msg").html("");
	var flag = true;
	if($("#pws1").val().trim() == ''){
		flag = false;
		/* $("#pws1msg").html("Required Field"); */
		$('#pws1msg').html('${validationText}');
	}
	if($("#pws2").val().trim() == ''){
		flag = false;
		/* $("#pws2msg").html("Required Field"); */
		$('#pws2msg').html('${validationText}');
	}
	if(flag){
		if($("#pws1").val().trim() != $("#pws2").val().trim()){
			$("#pws2msg").html("Password Not Matched");
			$("#pws1").val("");
			$("#pws2").val("");
			flag = false;
		}
		if(flag)
			$("#formsumit").submit();	
	}
	
}
</script>
</body>
</html>