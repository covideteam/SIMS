<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
		<!-- general form elements -->
            <div class="card card-primary">
              <div class="card-header">
                <h3 class="card-title">Upload E-Form</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              <c:url value="/buildStdyCrf/crfupload" var="crfuploadUrl" />
              <sf:form action="${crfuploadUrl}" method="POST"
				modelAttribute="crfpojo" id="crfupload" enctype="multipart/form-data" role="form">
                <div class="card-body">
                  <div class="form-group">
                    <div class="input-group">
                      <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="exampleInputFile">
                        <label class="custom-file-label" for="exampleInputFile">Choose Excel File</label>
                      </div>
                    </div>
                  </div>
                </div>
                <c:if test="${study.studyStatus eq 'Design' or study.studyStatus eq 'Available' }">
	                <!-- /.card-body -->
	                <div class="card-footer">
	                  <button type="submit" class="btn btn-primary" onmousemove="test()" id="formSubmitBtn">Submit</button>
	                </div>
                </c:if>
              </sf:form>
            </div>
            <!-- /.card -->	
            
            
		<div class="card">
            <div class="card-header">
            </div>
            <!-- /.card-header -->
            <div class="card-body">
              <table id="example1" class="table table-bordered table-striped">
                <thead>
                <tr>
                	<th>CRF NAME</th>
					<th>CRF Title</th>
					<th>Type</th>
					<th>Gender</th>
					<th>Version</th>
					<th>View</th>
					<th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${crfList}" var="crf">
					<tr>
						<td>${crf.name }</td>
						<td>${crf.title }</td>
						<td>${crf.type }</td>
						<td>${crf.gender }</td>
						<td>${crf.version }</td>
						<td><a href='<c:url value="/admini/crf/viewCrf/${crf.id}/study" />' class="btn btn-primary">Click</a></td>
						<td><c:if test="${crf.active}">
								<c:if test="${study.studyStatus eq 'Design' or study.studyStatus eq 'Available' }">
									<a href='<c:url value="/buildStdyCrf/crfChangeStatus/${crf.id}" />'
										class="btn btn-primary">ACTIVE</a>
								</c:if>	
								<c:if test="${study.studyStatus eq 'Frozen' or study.studyStatus eq 'Locked' }">
									<a href='#' class="btn btn-primary">ACTIVE</a>
								</c:if>	
							</c:if>
							<c:if test="${crf.active eq false}">
								<c:if test="${study.studyStatus eq 'Design' or study.studyStatus eq 'Available' }">
									<a href='<c:url value="/buildStdyCrf/crfChangeStatus/${crf.id}" />'
										class="btn btn-primary">NOT ACTIVE</a>
								</c:if>	
								<c:if test="${study.studyStatus eq 'Frozen' or study.studyStatus eq 'Locked' }">
										<a href='#' class="btn btn-primary">NOT ACTIVE</a>
								</c:if>
							</c:if>
						</td>
					</tr>
				</c:forEach>
                </tbody>
                <tfoot>
                <tr>
					<th>CRF NAME</th>
					<th>CRF Title</th>
					<th>Type</th>
					<th>Gender</th>
					<th>Version</th>
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
		$('#crfupload').submit();
	});
</script>
