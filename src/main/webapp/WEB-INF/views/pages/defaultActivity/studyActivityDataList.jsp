<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags"%>


		 <div style="text-align:right;"></div>
<div class="row" oncontextmenu="return false;">
	<div class="col-md-12 col-sm-12 ">
		<div class="x_panel">
			<div class="x_title">
				<h2>Study Activity Data</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
					</li>
					
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<br>

				<table id="example1" class="table table-bordered table-striped"
					style="width: 100%;">
					<thead>
						<tr>
							<th>VolunteerId</th>
							<th>PDF</th>
							<th>Created By</th>
							<th>Created On</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${studyActList}" var="sa">
							<tr>
								<td><a
									href='<c:url value="/defaultActivity/studyActivityDataDetails/${sa.id}"/>'>${sa.id}</a></td>
								<td><a
									href='<c:url value="/pdfReport/studyActivityPDFReport/${sa.id}"/>'
									target="_blank">PDF</a></td>
								<td>${sa.createdBy}</td>
								<td><fmt:formatDate value="${sa.createdOn}"
										pattern="${dateFormat}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- /.card-body -->
  
          <!-- /.card -->
          <input type="hidden" value="<sping:message code="label.projects"></sping:message>" id="hidPageTitle"/>