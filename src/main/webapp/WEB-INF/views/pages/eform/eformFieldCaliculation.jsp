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
                <h3 class="card-title">Calculation file Upload</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              <c:url value="/eformConfiguration/eformuploadxml" var="crfuploadUrl" />
				<sf:form action="${crfuploadUrl}" method="POST"
					modelAttribute="crfpojo" id="crfupload" enctype="multipart/form-data">
                <div class="card-body">
                  <div class="form-group">
                    <label for="exampleInputFile">Upload XML File</label>
                    <div class="input-group">
                      <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="exampleInputFile">
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
              <h3 class="card-title">All E-Forms Elements Calculation Info</h3>
            </div>
            <!-- /.card-header -->
            <div class="card-body">
              <table id="example1" class="table table-bordered table-striped" style="width: 100%;">
                <thead>
                <tr>
                	<th>EForm NAME</th>
					<th>Result Filed</th>
					<th>Calculation</th>
					<th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="data">
					<tr>
						<td>${data.crf.name }</td>
						<td>${data.resultField }</td>
						<td>${data.caliculation }</td>
						<td><c:if test="${data.status eq 'active'}">
								<a href='<c:url value="/eformConfiguration/eformChangeStatus/${data.id}" />'
									class="btn btn-primary">ACTIVE</a>
							</c:if>
							<c:if test="${data.status ne 'active'}">
								<a href='<c:url value="/eformConfiguration/eformChangeStatus/${data.id}" />'
									class="btn btn-primary">NOT ACTIVE</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
                </tbody>
                <tfoot>
                <tr>
					<th>EForm NAME</th>
					<th>Result Filed</th>
					<th>Calculation</th>
					<th>Status</th>
                </tr>
                </tfoot>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
</body>
</html>