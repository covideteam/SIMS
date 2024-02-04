<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
					<li class="nav-item has-treeview">
					    <a href="#" class="nav-link">
							<i class="nav-icon fas fa-copy"></i>
								CPU Execution <i class="fas fa-angle-left right"></i> <span
									class="badge badge-info right">4</span>
					    </a>
						<ul class="nav nav-treeview">
							<c:if test="${dosing eq 'yes'}">
								<li class="nav-item"><a href='<c:url value="/study/clinical/stdClinicaldosing"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Dosing</a></li>
								<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalDoseSuperwise('periodDoseData')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Dose Data</a></li>
							</c:if>
							<c:if test="${sampleCollection eq 'yes'}">
								<li class="nav-item"><a href='<c:url value="/study/clinical/stdClinicalSampleCollection"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Collection</a></li>
							</c:if>
							<li class="nav-item"><a href="javascript:void(0);" onclick="centrifugationCall('centrifugation')" class="nav-link"> 
								<i class="far fa-circle nav-icon"></i>Centrifuge</a></li>
							<li class="nav-item"><a href='<c:url value="/study/clinical/stdClinicalSampleSaperation"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Separation</a></li>
							<c:if test="${vitalCollection eq 'yes'}">
<%-- 								<li class="nav-item"><a href='<c:url value="/study/clinical/stdClinicalVitalCollection"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Vital Collection</a></li> --%>
								<li class="nav-item"><a href='<c:url value="/study/clinical/vitalCollection"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Vital Collection</a></li>
								<li class="nav-item"><a href='<c:url value="/study/clinical/ecgCollection"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>ECG Collection</a></li>
							</c:if>
							<c:if test="${mealsCollection eq 'yes'}">
<%-- 								<li class="nav-item"><a href='<c:url value="/study/clinical/stdClinicalMeaslCollection"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Meals Collection </a></li> --%>
								<li class="nav-item"><a href='<c:url value="/study/clinical/measlCollection"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Meals Collection</a></li>
							</c:if>
							
							<li class="nav-item"><a href='<c:url value="/study/clinical/withdrawal"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Withdrawal</a></li>
							<li class="nav-item"><a href='<c:url value="/study/clinical/subjectReplacement"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Subject Replacement</a></li>
						</ul>
					</li>
					<c:if test="${userName ne 'CPU Activity' }">
					<li class="nav-item has-treeview">
					    <a href="#" class="nav-link">
							<i class="nav-icon fas fa-copy"></i>
							Clinical Report<i class="fas fa-angle-left right"></i> 
							<span class="badge badge-info right">4</span>
					    </a>
						<ul class="nav nav-treeview">
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('dosing')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Dosing</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('sampleCollection')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Schedule</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('sampleSeparation')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Saperation</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('vitalCollection')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Vital Schedule</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('mealCollection')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Meals Schedule</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('ecgCollection')" class="nav-link"> <i class="far fa-circle nav-icon"></i>ECG Schedule</a></li>
						</ul>
					</li>
					</c:if>
					<li class="nav-item has-treeview">
					    <a href="#" class="nav-link">
							<i class="nav-icon fas fa-copy"></i>
							Dash board<i class="fas fa-angle-left right"></i> 
							<span class="badge badge-info right">4</span>
					    </a>
						<ul class="nav nav-treeview">
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('dosingDashBord')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Dosing</a></li>
							<c:if test="${sampleCollection eq 'yes'}">
								<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('sampleCollectionDashBord')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Collection</a></li>
							</c:if>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('sampleSapartionDashBord')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Separation</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('vitalCollectionDashBord')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Vital Collection</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('mealCollectionDashBord')" class="nav-link"> <i class="far fa-circle nav-icon"></i>Meals Collection</a></li>
							<li class="nav-item"><a href="javascript:void(0);" onclick="clinicalReport('ecgCollectionDashBord')" class="nav-link"> <i class="far fa-circle nav-icon"></i>ECG Times</a></li>
<%-- 							<li class="nav-item"><a href='<c:url value="/study/clinical/stdClinicalSampleCollectionErrors"/>' class="nav-link"> <i class="far fa-circle nav-icon"></i>Sample Collection Errors</a></li> --%>
							
						</ul>
					</li>
				</ul>
			</nav>
			<!-- /.sidebar-menu -->
		</div>
		<!-- /.sidebar -->
	</aside>

</body>
<script type="text/javascript">
	function clinicalReport(type){
		var result = synchronousAjaxCall(mainUrl+"/clinicalReport/stdClinicalReport/"+type);
		if(result != '' || result != 'undefined'){
			$("#clinicalBody").html(result);
		}
	}
	function clinicalDoseSuperwise(type){
		var result = synchronousAjaxCall(mainUrl+"/study/clinical/periodDoseData/"+type);
		if(result != '' || result != 'undefined'){
			$("#clinicalBody").html(result);
		}
			
	}
	
	function centrifugationCall(type){
		var result = synchronousAjaxCall(mainUrl+"/study/clinical/"+type);
		if(result != '' || result != 'undefined'){
			$("#clinicalBody").html(result);
		}
			
	}
	
	
</script>
</html>