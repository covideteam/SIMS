t<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<body>
	<!-- Main Sidebar Container -->
	<aside class="main-sidebar sidebar-dark-primary elevation-4">
		<!-- Brand Logo -->
		<a href="#" class="brand-link"> <img
			src='<c:url value="/static/admintltcss/dist/img/Covide.png"/>'
			alt="Covide" class="brand-image img-circle elevation-3"
			style="opacity: .8"> <span class="brand-text font-weight-light">Covide</span>
		</a>

		<!-- Sidebar -->
		<div class="sidebar">
			<!-- Sidebar user panel (optional) -->
			<div class="user-panel mt-3 pb-3 mb-3 d-flex">
				<div class="image">
					<img
						src='<c:url value="/static/admintltcss/dist/img/person-male.png"/>'
						class="img-circle elevation-2" alt="User Image">
				</div>
				<div class="info">
					<a href="#" class="d-block">${userName}</a>
				</div>
			</div>

			<!-- Sidebar Menu -->
			<nav class="mt-2">
				<ul class="nav nav-pills nav-sidebar flex-column"
					data-widget="treeview" role="menu" data-accordion="false">
					<!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
					
<!-- 					<li class="nav-item"> -->
<%-- 						<a href='<c:url value="/extractData/"/>' --%>
<!-- 						class="nav-link"> <i class="nav-icon fas fa-th"></i> -->
<!-- 							<p> -->
<!-- 								Extract Data<span class="right badge badge-danger">New</span> -->
<!-- 							</p> -->
<!-- 					</a></li> -->
					<c:if test="${crfModule eq 'yes'}">
						<li class="nav-item has-treeview">
						    <a href="#" class="nav-link">
								<i class="nav-icon fas fa-copy"></i>
									Build Study <i class="fas fa-angle-left right"></i> <span
										class="badge badge-info right">8</span>
						    </a>
							<ul class="nav nav-treeview">
								<li class="nav-item">
									<a href='<c:url value="/buildStdyCrf/uploadStudyCrf"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
											Upload Study E-Form</a></li>
								<li class="nav-item">
									<a href='<c:url value="/buildStdyCrf/configureCrfsToStudy"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
											Config E-Form</a></li>
								<li class="nav-item">
									<a href='<c:url value="/buildStdyCrf/crfFieldCaliculation"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
											Upload E-Form calculation</a></li>
								<li class="nav-item">
									<a href='<c:url value="/buildStdyCrf/crfRule"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
											E-Form Rule</a></li>	
								<li class="nav-item">
									<a href='<c:url value="/buildStdyCrf/crfDateComparison"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
											E-Form Date Comparison</a></li>	
										
								
								<li class="nav-item">
									<a href='<c:url value="/buildStdyCrf/configureCrfsToPeriod"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
											Config E-Form to Phases</a></li>
								<li class="nav-item">
								<a href='<c:url value="/study/volunteerCreation"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
										Volunteer Creation</a></li>
								<li class="nav-item">
								<a href='<c:url value="/administration/asignStudy"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
										Asign Study</a></li>		
						
							</ul>
						</li>
					</c:if>
					
					<c:if test="${pkSampelManagement eq 'yes'}">
						<li class="nav-item has-treeview">
						    <a href="#" class="nav-link">
								<i class="nav-icon fas fa-copy"></i>
									Clinical<i class="fas fa-angle-left right"></i> <span
										class="badge badge-info right">8</span>
						    </a>
							<ul class="nav nav-treeview">
								<li class="nav-item">
									<a href='<c:url value="/study/clinical/stdClinicalHome"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Clinical Info</a></li>
								<li class="nav-item">
									<a href='<c:url value="/study/clinical/sampleSchedule"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Schedule</a></li>
								<c:if test="${vitalCollection eq 'yes'}">
									<li class="nav-item">
									<a href='<c:url value="/study/clinical/vitalSchedule"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Vital Schedule</a></li>
								</c:if>
								<c:if test="${mealsCollection eq 'yes'}">
								<li class="nav-item">
									<a href='<c:url value="/study/clinical/mealsSchedule"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Meals Schedule</a></li>
								</c:if>
								<c:if test="${crfModule eq 'no'}">
										<li class="nav-item">
										<a href='<c:url value="/study/volunteerCreation"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
												Volunteer Creation</a></li>		
								</c:if>
								<c:if test="${dosing eq 'yes'}">
									<li class="nav-item">
									<a href='<c:url value="/study/clinical/stdSachetBarcodes"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Sachet Barcodes</a></li>
								</c:if>
								<li class="nav-item">
									<a href='<c:url value="/study/clinical/stdVacutainerBarcodes"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Vacutiner Barcodes</a></li>
								<li class="nav-item">
								<a href='<c:url value="/study/clinical/stdVialBarcodes"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Vial Barcodes</a></li>
								<li class="nav-item">
									<a href='<c:url value="/study/clinical/stdSubjectBarcodes"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Subject Barcodes</a></li>		
<!-- 								<li class="nav-item"> -->
<%-- 									<a href='<c:url value="/study/clinical/labTechnicianBarcodes"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Lab Technician</a></li> --%>
								
								<c:if test="${crfModule eq 'no'}">
									<li class="nav-item">
										<a href='<c:url value="/administration/asignStudy"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
												Asign Study</a></li>
								</c:if>
							</ul>
						</li>					
					</c:if>
					

					<li class="nav-item has-treeview">
					    <a href="#" class="nav-link">
							<i class="nav-icon fas fa-copy"></i>
							Study Execution<i class="fas fa-angle-left right"></i> 
							<span class="badge badge-info right">1</span>
					    </a>
						<ul class="nav nav-treeview">
							<li class="nav-item">
								<a href='<c:url value="/study/reporting"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
										Reporting</a></li>
							<li class="nav-item">
								<a href='<c:url value="/study/admissionProcess"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
										Admission Process</a></li>
							<c:if test="${crfModule eq 'yes'}">
							<li class="nav-item">
								<a href='<c:url value="/period/subjectMatrix"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
										Subject Matrix</a></li>
							</c:if>
							<c:if test="${userRole ne 'Study Director' and userRole ne 'Monitor'}">
								<li class="nav-item">
								<a href='<c:url value="/studyexe/subjectEnrollment"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>
										Subject Enrollment</a></li>
										<li class="nav-item">
<%-- 								<a href='<c:url value="/studyexe/subjectWithDrown"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i> --%>
<!-- 										Subject withdrawal</a></li> -->
							</c:if>
							
						</ul>
					</li>
					<li class="nav-item">
						<a href='<c:url value="/reports/pdfReportsSelection"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Pdf Reports</a>
					</li>
					
				</ul>
			</nav>
			<!-- /.sidebar-menu -->
		</div>
		<!-- /.sidebar -->
	</aside>

</body>
</html>