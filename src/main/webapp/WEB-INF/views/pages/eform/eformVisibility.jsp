<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


		<div class="card" oncontextmenu="return false;">
            <div class="card-header">
              <h3 class="card-title">EForm Visibility</h3>
            </div>
            <!-- /.card-header -->
            <div class="card-body">
            	<c:if test="${userRole ne 'Study Director' and userRole ne 'Monitor'}">
              		<a href='<c:url value="/eformConfiguration/visibilityCreation"/>' class="btn btn-primary"><strong>Create E-Form Visibility</strong></a>
              	</c:if>
              <table id="example1" class="table table-bordered table-striped" style="width: 100%">
                <thead>
                <tr>
                	<th>NAME</th>
					<th>DESC</th>
					<th>E-Form</th>
					<th>Section Element/Group Element</th>
					<th>Action</th>
					<th>Value</th>
					<th>Target Crf</th>
					<th>Section/Group</th>
					<th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="rule">
					<tr>
						<td>${rule.name }</td>
						<td>${rule.desc }</td>
						<td>${rule.crf.name }</td>
						<td>${rule.secEle.name } ${rule.groupEle.name }</td>
						<td>${rule.action }</td>
						<td>${rule.filedValue }</td>
						<td>${rule.targetCrf.name }</td>
						<td>${rule.section.name } ${rule.group.name }</td>
						<td>
						<c:choose>
							<c:when test="${userRole ne 'Study Director' and userRole ne 'Monitor'}">	
									<c:if test="${rule.status}">
										<a href='<c:url value="/eformConfiguration/eformVisibilityChangeStatus/${rule.id}" />' class="btn btn-primary">ACTIVE</a>
									</c:if> 
									<c:if test="${rule.status eq false}">
										<a href='<c:url value="/eformConfiguration/eformVisibilityChangeStatus/${rule.id}" />' class="btn btn-primary">NOT ACTIVE</a>
									</c:if>
							</c:when>
							<c:otherwise>
									<c:if test="${rule.status}">ACTIVE</c:if> 
									<c:if test="${rule.status eq false}">NOT ACTIVE</c:if>
							</c:otherwise>
						</c:choose>
						</td>
						
					</tr>
				</c:forEach>
                </tbody>
                <tfoot>
                <tr>
					<th>NAME</th>
					<th>DESC</th>
					<th>E-Form</th>
					<th>Section Element/Group Element</th>
					<th>Action</th>
					<th>Value</th>
					<th>Target Crf</th>
					<th>Section/Group</th>
					<th>Status</th>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
          
