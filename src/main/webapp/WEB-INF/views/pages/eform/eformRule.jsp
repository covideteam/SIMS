<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


		<div class="card" oncontextmenu="return false;">
            <div class="card-header">
              <h3 class="card-title">EForm Rules</h3>
            </div>
            <!-- /.card-header -->
            <div class="card-body">
              <a href='<c:url value="/eformConfiguration/ruleCreation"/>' class="btn btn-primary"><strong>Create E-Form Rule</strong></a>
              <table id="example1" class="table table-bordered table-striped" style="width: 100%;">
                <thead>
                <tr>
                	<th>NAME</th>
					<th>DESC</th>
					<th>E-Form</th>
					<th>Section Element</th>
					<th>Group Element</th>
					<th>Comparison</th>
					<th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${eformRules}" var="rule">
					<tr>
						<td>${rule.name }</td>
						<td>${rule.ruleDesc }</td>
						<td>${rule.crf.name }</td>
						<td>${rule.secEle.name }</td>
						<td>${rule.groupEle.name }</td>
						<td>
							<c:if test="${rule.compareWith == 'Other CRF Field'}">
								Other CRF Field
							</c:if>
							<c:if test="${rule.compareWith != 'Other CRF Field'}">
								Given Value
							</c:if>
						</td>
						<td>
							<c:if test="${rule.active}">
								<a href='<c:url value="/eformConfiguration/crfRuleChangeStatus/${rule.id}" />' class="btn btn-primary">ACTIVE</a>
							</c:if> 
							<c:if test="${rule.active eq false}">
								<a href='<c:url value="/eformConfiguration/crfRuleChangeStatus/${rule.id}" />' class="btn btn-primary">NOT ACTIVE</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
                </tbody>
                <tfoot>
                <tr>
					<th>NAME</th>
					<th>DESC</th>
					<th>E-Form</th>
					<th>Section Element</th>
					<th>Group Element</th>
					<th>Comparison</th>
					<th>Status</th>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
          
