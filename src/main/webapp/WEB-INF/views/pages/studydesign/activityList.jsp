<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Activity List</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"><i class="fa fa-wrench"></i></a>
							<ul class="dropdown-menu" role="menu">
								<li><a class="dropdown-item" href="#">Settings 1</a></li>
								<li><a class="dropdown-item" href="#">Settings 2</a></li>
							</ul></li>
						<li><a class="close-link"><i class="fa fa-close"></i></a></li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>

					<div style="text-align: right;">
						<a href='<c:url value="/activity/getActivityForm"/>'><button
								type="button" class="btn btn-info">Add</button></a>
					</div>
					<table id="activityList" class="table table-striped table-bordered nowrap"
						style="width: 100%;">
						<thead>
							<tr>
								<th>Language</th>
								<th>Activity Name</th>
								<th>Activity Desc</th>
								<th>Status</th>
								<th>Update</th>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${alist}" var="ac">
								<tr>
									<td>${ac.language}</td>
									<td><a
										href='<c:url value="/activity/getActivityParameterForm/${ac.id}"/> '>
											${ac.activityName}</a></td>
									<td>${ac.activityDesc}</td>
									<td><a
										href='<c:url value="/activity/changeStatus/${ac.id}/${ac.status}"/> '><button
												type="button" class="btn btn-primary btn-sm">${ac.status}</button></a></td>
									<td><a
										href='<c:url value="/activity/dynamicActivityUpdate/${ac.id}"/> '><button
												type="button" class="btn btn-primary btn-sm">Update</button></a></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>Language</th>
								<th>Activity Name</th>
								<th>Activity Desc</th>
								<th>Status</th>
								<th>Update</th>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>

		<!-- /.card-body -->
          </div>
          
	<script type="text/javascript">
    $(document).ready(function() {
        var table = $('#activityList').DataTable({
            searchBuilder: true
        });
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
</body>
</html>