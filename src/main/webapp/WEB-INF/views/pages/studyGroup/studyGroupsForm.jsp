<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<jsp:useBean id="now" class="java.util.Date" />
	<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="formattedDate" />
	<div>
		<table id="form1" class="table table-striped table-bordered nowrap"
			style="width: 100%">
			<thead>
				<tr>
					<th><spring:message code="label.study.groupName"></spring:message></th>
					<th><spring:message code="label.study.noofSubjects"></spring:message></th>
					<th><spring:message code="label.study.noofStandbys"></spring:message></th>
					<th><a style="color: white;" href='#'
						onclick="formFunction('${proidfin}')"><i class="fa fa-plus"></i></a></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${sglist}" var="studyg" varStatus="st">
					<tr>
						<td>G${st.count}</td>
						<td>${studyg.noofSubjects}</td>
						<td>${studyg.noofStandbys}<fmt:formatDate
								value="${studyg.createdOn}" pattern="yyyy-MM-dd" var="creatData" /></td>
						<c:choose>
						 <c:when test="${userRole eq 'SUPERADMIN' || userRole eq 'ADMIN'}">
						   <td><a
									href='#' onclick="formUpdateFunction('${studyg.id}')"><i
										class='fa fa-edit'
										style='color: #23175e; font-size: 15px; padding-left: 10px'></i></a>
									<a href='<c:url value="/studyGroups/studyGroupsStatusChnage/${studyg.id}"/>'><i
										class='fa fa-trash-o'
										style='color: #23175e; font-size: 15px; padding-left: 10px'></i></a></td>
						</c:when>
						 <c:otherwise>
						    <c:choose>
								<c:when test="${creatData eq formattedDate}">
								<td><a
									href='#' onclick="formUpdateFunction('${studyg.id}')"><i
										class='fa fa-edit'
										style='color: #23175e; font-size: 15px; padding-left: 10px'></i></a>
									<a href='<c:url value="/studyGroups/studyGroupsStatusChnage/${studyg.id}"/>'
									><i
										class='fa fa-trash-o'
										style='color: #23175e; font-size: 15px; padding-left: 10px'></i></a></td>
							</c:when>
							</c:choose>
							</c:otherwise>
						</c:choose>


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var table = $('#form1')
								.DataTable(
										{
											searchBuilder : true,
											"language" : {
												"lengthMenu" : "${showLabel} _MENU_ ${entriesLabel}",
												"search" : "${searchLabel}:",
												"zeroRecords" : "${noDtaAvlLabel}",
												"info" : "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
												"infoEmpty" : "${noRcdsLabel}",
												"infoFiltered" : "(${filterLabel} _MAX_ ${totRcdsLabel})",
												"paginate" : {
													"previous" : " ${prevPgLabel}",
													"next" : "${nextLabel}"
												}
											}
										});
						//     table.searchBuilder.container().prependTo(table.table().container());
					});
</script>
</html>