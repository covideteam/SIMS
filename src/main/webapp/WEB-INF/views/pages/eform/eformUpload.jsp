<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
		<!-- general form elements -->
            <div class="card card-primary">
              <div class="card-header">
                <h3 class="card-title">Upload EForm</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              <c:url value="/eformConfiguration/eformupload" var="eformuploadUrl" />
              <sf:form action="${eformuploadUrl}" method="POST"
				modelAttribute="eformpojo" id="eformupload" enctype="multipart/form-data" role="form">
                <div class="card-body">
                  <div class="form-group">
                    <label for="exampleInputFile">Upload EForm</label>
                    <div class="input-group">
                      <div class="custom-file">
                        <input type="file" name="file"  id="exampleInputFile">
                        <label class="custom-file-label" for="exampleInputFile">Choose file</label>
                      </div>
                    </div>
                  </div>
                  <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Check me out</label>
                  </div>
                </div>
                <!-- /.card-body -->

                <div class="card-footer">
                  
                  <button type="submit" class="btn btn-primary" onmousemove="test()" id="formSubmitBtn">Submit</button>
                </div>
              </sf:form>
            </div>
            <!-- /.card -->	
            
            
		<div class="card">
            <div class="card-header">
              <h3 class="card-title">All E-Forms</h3>
            </div>
            <!-- /.card-header -->
            <div class="card-body">
              <table id="example1" class="table table-bordered table-striped" style="width: 100%;">
                <thead>
                <tr>
                	<th>EForm NAME</th>
					<th>EForm Title</th>
					<th>Type</th>
					<th>Gender</th>
					<th>View</th>
					<th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${eformList}" var="eform">
					<tr>
						<td>${eform.name }</td>
						<td>${eform.title }</td>
						<td>${eform.type }</td>
						<td>${eform.gender }</td>
						 <td><a href='<c:url value="/eformConfiguration/viewEform/${eform.id}/lib" />' class="btn btn-primary">Click</a></td>
						<td><c:if test="${eform.active}">
								<a href='<c:url value="/eformConfiguration/eformChangeStatus/${eform.id}" />'
									class="btn btn-primary">ACTIVE</a>
							</c:if>
							<c:if test="${eform.active eq false}">
								<a href='<c:url value="/eformConfiguration/eformChangeStatus/${eform.id}" />'
									class="btn btn-primary">NOT ACTIVE</a>
							</c:if>
						</td> 
					</tr>
				</c:forEach>
                </tbody>
                <tfoot>
                <tr>
					<th>EForm NAME</th>
					<th>EForm Title</th>
					<th>Type</th>
					<th>Gender</th>
					<th>View</th>
					<th>Status</th>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
          
</body>


<script type="text/javascript">
	$('#formSubmitBtn').click(function() {
		$('#eformupload').submit();
	});
</script>
</html>