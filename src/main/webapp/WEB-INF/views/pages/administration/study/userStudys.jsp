<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body oncontextmenu="return false;">
	<input type="hidden" name="cserIds" id="cserIds" />
	<input type="hidden" name="cserIdsRemove" id="cserIdsRemove" />
	<table id="example1" class="table table-bordered table-striped">
		<thead>
			<tr>
				<th>SlNo</th>
				<th>EMP ID</th>
				<th>Full Name</th>
				<th>Role</th>
				<th>Study</th>
				<th>Comments</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${allLoginUsers}" var="user" varStatus="st">
				<tr>
					<td>${st.count}</td>
					<td>${user.username}</td>
					<td>${user.fullName}</td>
					<td>${user.role.roleDesc}</td>
					<th style="text-align: center;"><c:choose>
							<c:when test="${activedUsers.contains(user.username)}">
								<label class="checkbox-inline"><input type="checkbox"
									name="userInfo" value="${user.id}" checked="checked" />Assign</label>
							</c:when>
							<c:otherwise>
								<label class="checkbox-inline"><input type="checkbox"
									name="userInfo" value="${user.id}" />Assign</label>
							</c:otherwise>
						</c:choose> <label class="checkbox-inline"><input type="checkbox"
							name="userInfoRemove" value="${user.id}" />Remove</label></th>
					<th><input type="text" class="form-control input-sm"
						name="${user.id}_comment"></th>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<th>SlNo</th>
				<th>EMP ID</th>
				<th>Full Name</th>
				<th>Role</th>
				<th>Assign Study</th>
				<th>Comments</th>
			</tr>
		</tfoot>
	</table>
<%-- 	<c:if test="${userRole ne 'Study Director' and userRole ne 'Monitor'}"> --%>
		<div style="text-align: center">
			<input type="button" value="Save" id="formSubmitBtn"
				onclick="formSubmit()" class="btn btn-primary">
		</div>
<%-- 	</c:if> --%>
</body>
<script type="text/javascript">
function formSubmit(){
	var favoritestudy = [];
	$.each($("input[name='userInfo']:checked"), function() {
		favoritestudy.push($(this).val());
	});
	$("#cserIds").val(favoritestudy);
	favoritestudy = []
	$.each($("input[name='userInfoRemove']:checked"), function() {
		favoritestudy.push($(this).val());
	});
	$("#cserIdsRemove").val(favoritestudy);
	$("#submitform").submit();
}
</script>
</html>