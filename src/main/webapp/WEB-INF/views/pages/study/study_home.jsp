<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
      <div class="container-fluid">
        <div class="row">
          <!-- /.col-md-6 -->
          <c:if test="${crfModule eq 'yes'}">
          	<div class="col-lg-6">
	            <div class="card card-primary card-outline">
	              <div class="card-header">
	                <h5 class="m-0">Build Study</h5>
	              </div>
	              <div class="card-body">
	<!--                 <h6 class="card-title">Before going to execute study we have to build study.</h6> -->
		
	<!--                 <p class="card-text">With supporting text below as a natural lead-in to additional content.</p> -->
					<a href='<c:url value="/buildStdyCrf/uploadStudyCrf"/>' class="btn btn-primary" id = "upload">Upload Study E-Form</a><br/><br/>
					<a href='<c:url value="/buildStdyCrf/crfFieldCaliculation"/>' class="btn btn-primary" id = "uploadE">Upload E-Form calculation</a>&nbsp;&nbsp;
					<a href='<c:url value="/buildStdyCrf/crfRule"/>' class="btn btn-primary" id = "rulecr">E-Form Rule</a><br/><br/>
					<a href='<c:url value="/buildStdyCrf/crfDateComparison"/>' class="btn btn-primary">E-Form Date Comparison</a><br/><br/>
					<a href='<c:url value="/buildStdyCrf/configureCrfsToStudy"/>' class="btn btn-primary" id = "configeform">Config E-Form</a>&nbsp;&nbsp;
					<a href='<c:url value="/buildStdyCrf/configureCrfsToPeriod"/>' class="btn btn-primary">Config E-Form to Phases</a><br/><br/>
					<a href='<c:url value="/study/volunteerCreation"/>' class="btn btn-primary">Volunteer Creation</a>&nbsp;&nbsp;
					<a href='<c:url value="/administration/asignStudy"/>' class="btn btn-primary">Asign Study</a>
	              </div>
	            </div>
	          </div>
          </c:if>
          
          <div class="col-lg-6">
          	<c:if test="${pkSampelManagement eq 'yes'}">
          		<div class="card card-primary card-outline">
	              <div class="card-header">
	                <h5 class="m-0">Clinical</h5>
	              </div>
	              <div class="card-body">
	                <a href='<c:url value="/study/clinical/stdClinicalHome"/>' class="btn btn-primary">Clinical Info</a>&nbsp;&nbsp;
	                <a href='<c:url value="/study/clinical/sampleSchedule"/>' class="btn btn-primary">Sample Schedule</a>&nbsp;&nbsp;
	                <c:if test="${vitalCollection eq 'yes'}">
	                	<a href='<c:url value="/study/clinical/vitalSchedule"/>' class="btn btn-primary">Vital Schedule</a><br/><br/>
	                </c:if>
	                <c:if test="${mealsCollection eq 'yes'}">
	                	<a href='<c:url value="/study/clinical/mealsSchedule"/>' class="btn btn-primary">Meals Schedule</a><br/><br/>
	                </c:if>
	                <c:if test="${crfModule eq 'no'}">
	                	<a href='<c:url value="/study/volunteerCreation"/>' class="btn btn-primary">Volunteer Creation</a>&nbsp;&nbsp;<br/><br/>
	                </c:if>
	                <c:if test="${dosing eq 'yes'}">
		                <a href='<c:url value="/study/clinical/stdSachetBarcodes"/>' class="btn btn-primary">Sachet Barcodes</a>&nbsp;&nbsp;
		            </c:if>
	                <a href='<c:url value="/study/clinical/stdVacutainerBarcodes"/>' class="btn btn-primary">Vacutiner Barcodes</a>&nbsp;&nbsp;
	                <a href='<c:url value="/study/clinical/stdVialBarcodes"/>' class="btn btn-primary">Vial Barcodes</a><br/><br/>
	                <c:if test="${sampleCollection eq 'yes' or vitalCollection eq 'yes' or  mealsCollection eq 'yes' or dosing eq 'yes'}">
	                	<a href='<c:url value="/study/clinical/stdSubjectBarcodes"/>' class="btn btn-primary">Subject Barcodes</a>&nbsp;&nbsp;
	                </c:if>
<%-- 	                <a href='<c:url value="/study/clinical/labTechnicianBarcodes"/>' class="btn btn-primary">Lab Technician</a> --%>
<!-- 	                <br/><br/> -->
	                <c:if test="${crfModule eq 'no'}">
						<a href='<c:url value="/administration/asignStudy"/>' class="btn btn-primary">Asign Study</a>
	                </c:if>
	              </div>
	            </div>
          	</c:if>
            
            
            <div class="card card-primary card-outline">
              <div class="card-header">
                <h5 class="m-0">Study Execution</h5>
              </div>
              <div class="card-body">
<!--                 <h6 class="card-title">Special title treatment</h6> -->
				<c:if test="${crfModule eq 'yes'}">
<!--                 <p class="card-text">With supporting text below as a natural lead-in to additional content.</p> -->
                <a href='<c:url value="/period/subjectMatrix"/>' class="btn btn-primary">Subject Matrix</a>&nbsp;&nbsp;				
				</c:if>
                <a href='<c:url value="/studyexe/subjectEnrollment"/>' class="btn btn-primary">Subject Enrollment</a>
              </div>
            </div>
          </div>
          <!-- /.col-md-6 -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->