<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags"%>
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
	<div class="card" style="width: 206%;">
	<input type="hidden" name="projeSize" id="projeSize" value="${size}" >
	<input type="hidden" name="subjectSize" id="subjectSize" value="${subjectSize}" >
              <table class="table table-bordered table-striped" style="width: 100%;">
                <thead>
                <tr>
                	<th>Subject No</th>
                	<th>Created By</th>
                	<th>Created On</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${ssList}" var="ssp">
	   				<tr>
	   					<td>${ssp.subjectNo}</td>
	   					<td>${ssp.createdBy}</td>
	   					<td><fmt:formatDate value="${ssp.createdOn}" pattern="${dateFormat}" /></td>
	   				</tr>
	   			</c:forEach> 
                </tbody>
                
              </table>
            </div>
            <!-- /.card-body -->
          <!-- /.card -->
</body>
</html>