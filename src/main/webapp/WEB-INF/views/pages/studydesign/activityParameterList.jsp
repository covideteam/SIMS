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
					<h2>Activity Parameter List</h2>
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
					<table id="activityParameter" class="table table-striped table-bordered nowrap"
						style="width: 100%;">
						<thead>
							<tr>
								<th>Language</th>
								<th>Activity Name</th>
								<th>Parameter</th>
								<th>Type</th>
								<th>Data One</th>
								<th>Data Two</th>
								<!-- <th>Update</th> -->

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${dadlist}" var="dadlist">
								<tr>
									<td>${dadlist.activity.language}</td>
									<td>${dadlist.activity.activityName}</td>
									<td>${dadlist.parameterName}</td>
									<td>${dadlist.type}</td>
									<td>${dadlist.dataone}</td>
									<td>${dadlist.datatwo}</td>
									<%-- <td><a href='<c:url value="/activity/dynamicActivityDetailsUpdate/${dadlist.id}"/> ' ><button type="button" class="btn btn-primary btn-sm">Update</button></a></td>  --%>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<th>Language</th>
								<th>Activity Name</th>
								<th>Parameter</th>
								<th>Type</th>
								<th>Data One</th>
								<th>Data Two</th>
								<!-- <th>Update</th> -->
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
        var table = $('#activityParameter').DataTable({
            searchBuilder: true
        });
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
</body>
</html>