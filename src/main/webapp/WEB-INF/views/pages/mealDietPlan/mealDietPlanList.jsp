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
	<div class="x_title">
					<h2>
						<spring:message code="label.mealDiet.dietPlan"></spring:message>
					</h2>
					
					<div class="clearfix"></div>
				</div>
		<table id="form1" class="table table-striped table-bordered nowrap"
			style="width: 100%">
			<thead>
				<tr>
					<th><spring:message code="label.mealDiet.title"></spring:message></th>
					<th><spring:message code="label.mealDiet.mealType"></spring:message></th>
					<th><a style="color: white;" href='#'
						onclick="formFunction()"><i class="fa fa-plus"></i></a></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="list" varStatus="st">
					<tr>
						<td>${list.mealTitle}</td>
						<td>${list.mealType.fieldValue}</td>
						<td><a href='#' onclick="formViewFunction('${list.id}')"><i
										class='fa fa-file'
										style='color: #23175e; font-size: 15px; padding-left: 10px'></i></a>
									</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="modal fade" id="createPreparationModal" tabindex="-1"
		role="dialog" aria-labelledby="createPreparationModal"
		aria-hidden="true">
		<div class="modal-dialog modal-xl" id="modal-dialog_id" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="ModalLabel">
						<b><spring:message code="label.mealDiet.dietPlanTitle"></spring:message></b>
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div id="createPage"></div>
				<div class="modal-footer">
				   <button type="button" onclick="submitDataFuntion()"
						class="btn btn-primary" id="btnupdateSubmit">
						<spring:message code="label.mealDiet.submit"></spring:message>
					</button>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">
						<spring:message code="label.mealDiet.cancel"></spring:message>
					</button>
					

				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="viewPreparationModal" tabindex="-1"
		role="dialog" aria-labelledby="updatePreparationModal"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" id="modal-dialog_id" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="ModalLabel">
						<b>Diet Plan Details</b>
					</h5>
					<!-- <button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button> -->
				</div>
				<div id="viewPage"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Cancel
					</button>
				</div>
			</div>
		</div>
	</div>
</body>


<script type="text/javascript">
function formFunction() {
    debugger;
	var url=mainUrl+"mealDiet/getMealDietForm";
	var result=synchronousAjaxCall(url);
	if(result != null && result != "" && result != "undefined"){
		$('#createPage').html(result);
		 $("#createPreparationModal").modal('show');
	}
	
}
function formViewFunction(id) {
    debugger;
	var url=mainUrl+"/mealDiet/mealDietView/"+id;
	var result=synchronousAjaxCall(url);
	if(result != null && result != "" && result != "undefined"){
		$('#viewPage').html(result);
		$("#viewPreparationModal").modal('show');
	}else{
		swal.fire('Data Not Found!');
	}
}
</script>
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