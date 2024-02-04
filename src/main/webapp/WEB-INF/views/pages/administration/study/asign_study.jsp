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
	<div class="card">
		<div class="card-header">
			<h3 class="card-title">Assign Study</h3>
		</div>
		<div class="card-body">
			<c:url value="/administration/asignStudySave" var="asignStudySave" />
			<form:form action="${asignStudySave}" method="post" id="submitform">
				<input type="hidden" name="cserIds" id="cserIds" />
				<input type="hidden" name="cserIdsRemove" id="cserIdsRemove" />
				<tablea>
					<thead>
						<tr>
							<th>Select Study</th>
							<th><select onchange="userStuyds(this.value)" name="studyid" id="studyid">
								<option value="-1">--Select--</option>
								<c:forEach items="${userStudys}" var="us">
									<option value="${us.id}">${us.projectNo}</option>
								</c:forEach>
							</select></th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
				<div id="userStudysAssing">
				
				</div>
			</form:form>
		</div>

	</div>
</body>
<script type="text/javascript">
	function userStuyds(studyId){
		if(studyId != -1){
			var url = mainUrl+'/administration/userStudys/'+studyId;
   			var result = synchronousAjaxCall(url);
   			$("#userStudysAssing").html(result);
		}
	}
	
</script>
</html>