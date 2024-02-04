<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags"%>
</head>
<body oncontextmenu="return false;">


<div class="card">
		 <div style="text-align:right;"></div>
            <div class="card-header">
              <h3 class="card-title" style='float:left;'>Study Activity Details</h3>
            </div>
            <!-- /.card-header -->
            <div class="card-body">
            <table   class="table table-bordered table-striped" style="width: 100%;">
                <tr><th>Study Activity Data</th><td>${saData.id}</td>
                <th>Status</th><td>${saData.status}</td>
                <th>Created By</th><td><td>${saData.createdBy}</td>
	   			<th>Created By</th><td><fmt:formatDate value="${saData.createdOn}" pattern="${dateFormat}" /></td>
               </tr> 
               <tr>
               <td style="text-align: center;" colspan="8">
              <a href='<c:url value="/defaultActivity/studyActivityDataApproval/${saData.id}"/>' ><button type="button" class="btn btn-info">Approval</button></a>
              <a href='<c:url value="/defaultActivity/studyActivityDataComments/${saData.id}"/>' ><button type="button" class="btn btn-info">Send Comments</button></a>
              </td>
              </tr>
              </table>
              <table   class="table table-bordered table-striped" style="width: 100%;">
                <thead>
	                <tr>
	                	<th>Value</th>
	                	<th>Comments</th>
	                	<th>Created By</th>
	                	<th>Created On</th>
	                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sadDetails}" var="sd">
	   				<tr>
	   				    <td>${sd.value}</td>
	   					<td>${sd.comments}</td>
	   					<td>${sd.createdBy}</td>
	   					<td><fmt:formatDate value="${sd.createdOn}" pattern="${dateFormat}" /></td>
	   				</tr>
	   			</c:forEach> 
                </tbody>
              </table>
            </div>
            <!-- /.card-body -->
          </div>

</body>
</html>