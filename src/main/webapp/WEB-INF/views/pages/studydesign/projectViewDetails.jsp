<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2>Project No : <label> ${ppojo.projectNo}</label></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
              <table id="example" class="table table-bordered table-striped" style="width: 100%;">
                <thead>
                <tr>
                	<th>Field Name</th>
                	<th>Field Value</th>
                	<th>Type</th>
                	<th>Row No</th>
                	<th>Sub Row No</th>
	   				<th>Created By</th>
	   				<th>Created On</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pdlist.projectDetails}" var="pdj">
	   				<tr>
	   					<td>${pdj.fieldName}</td>
	   					 <td>${pdj.fieldValue}</td>
	   					<td>${pdj.type}</td>
	   					<td>${pdj.rowNo}</td>
	   					<td>${pdj.subRowNo}</td>
	   					<td>${pdj.createdBy}</td> 
	   					<td><fmt:formatDate value="${pdj.createdOn}" pattern="${dateFormat}" /></td>
	   				</tr>
	   			</c:forEach> 
                </tbody>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
          </div>
          </div>
          