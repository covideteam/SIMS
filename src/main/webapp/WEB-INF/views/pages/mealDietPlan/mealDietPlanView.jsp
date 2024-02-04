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
	<div>
	<table style="width: 100%" class="table table-striped table-bordered nowrap">
	<tr><th><spring:message code="label.mealDiet.mealtitle"></spring:message></th><td>${mdplan.mealTitle}</td> 
	<th><spring:message code="label.mealDiet.mealType"></spring:message></th><td>${mdplan.mealType.fieldValue}</td>
	</tr>
	</table>
		<table id="form2" class="table table-striped table-bordered nowrap"
			style="width: 100%">
			<thead>
				<tr>
					<th><spring:message code="label.mealDiet.iteam"></spring:message></th>
					<th><spring:message code="label.mealDiet.quantity"></spring:message></th>
					<th><spring:message code="label.mealDiet.Units"></spring:message></th>
					<th><%-- <spring:message code="label.mealDiet.totalCalories"></spring:message> --%>Calories</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mdplanList}" var="list" varStatus="st">
					<tr>
						<td>${list.itemName}</td>
						<td>${list.itemQuantity}</td>
						<td>${list.units.unitsCode}</td>
						<td>${list.totalCalories}</td>
						
						

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
						var table = $('#form2')
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