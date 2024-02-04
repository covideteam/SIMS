<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
<div id="clinicalBody">


      <div class="container-fluid">
        <div class="row">
	        	 <!-- /.col-md-6 -->
		          <div class="col-lg-6">
		            <div class="card card-primary card-outline">
		              <div class="card-header">
		                <h5 class="m-0">CPU Execution</h5>
		              </div>
		              <div class="card-body">
		<!--                <h6 class="card-title">Before going to execute study we have to build study.</h6> -->
								<a href='<c:url value="/study/clinical/stdClinicaldosing"/>' class="btn btn-primary">Dosing</a>&nbsp;&nbsp;
								<a href="javascript:void(0);" onclick="clinicalDoseSuperwise('periodDoseData')" class="btn btn-primary">Dose Data</a>&nbsp;&nbsp;
								<a href='<c:url value="/study/clinical/stdClinicalSampleCollection"/>' class="btn btn-primary">Sample Collection</a><br/><br/>
								<a href='<c:url value="/study/clinical/stdClinicalSampleSaperation"/>' class="btn btn-primary">Sample Separation</a>&nbsp;&nbsp;
								<a href='<c:url value="/study/clinical/measlCollection"/>' class="btn btn-primary">Meals Collection</a><br/><br/>
								<a href='<c:url value="/study/clinical/vitalCollection"/>' class="btn btn-primary">Vital Collection</a>&nbsp;&nbsp;
								<a href='<c:url value="/study/clinical/ecgCollection"/>' class="btn btn-primary">ECG Collection</a><br/><br/>
		              </div>
		            </div>
		          </div>
		          <!-- /.col-md-6 -->
		          <c:if test="${userName ne 'CPU Activity' }">
			          <div class="col-lg-6">
			            <div class="card card-primary card-outline">
			              <div class="card-header">
			                <h5 class="m-0">Clinical Report</h5>
			              </div>
			              <div class="card-body">
			                	<a href="javascript:void(0);" onclick="clinicalReport('dosing')" class="btn btn-primary">Dosing</a>&nbsp;&nbsp;
			                	<a href="javascript:void(0);" onclick="clinicalReport('sampleCollection')"  class="btn btn-primary">Sample Collection</a><br/><br/>
			                	<a href="javascript:void(0);" onclick="clinicalReport('sampleSeparation')" class="btn btn-primary">Sample Separation</a>&nbsp;&nbsp;
				                <a href="javascript:void(0);" onclick="clinicalReport('vitalCollection')" class="btn btn-primary">Vital Schedule</a><br/><br/>
			     	            <a href="javascript:void(0);" onclick="clinicalReport('mealCollection')" class="btn btn-primary">Meals Schedule</a>&nbsp;&nbsp;
			     	            <a href="javascript:void(0);" onclick="clinicalReport('ecgCollection')" class="btn btn-primary">ECG Schedule</a><br/><br/>
			              </div>
			            </div>
			          </div>
		          </c:if>
		           <!-- /.col-md-6 -->
<%-- 		          <c:if test="${userName ne 'CPU Activity' }"> --%>
			          <div class="col-lg-6">
			            <div class="card card-primary card-outline">
			              <div class="card-header">
			                <h5 class="m-0">Clinical Dash board</h5>
			              </div>
			              <div class="card-body">
			                <a href="javascript:void(0);" onclick="clinicalReport('dosingDashBord')" class="btn btn-primary">Dosing</a>&nbsp;&nbsp;
			                <a href="javascript:void(0);" onclick="clinicalReport('sampleCollectionDashBord')" class="btn btn-primary">Sample Schedule</a><br/><br/>
			                <a href="javascript:void(0);" onclick="clinicalReport('sampleSapartionDashBord')" class="btn btn-primary">Sample Separation</a>&nbsp;&nbsp;
			                <a href="javascript:void(0);" onclick="clinicalReport('mealCollectionDashBord')" class="btn btn-primary">Meal Collection</a><br/><br/>
			                <a href="javascript:void(0);" onclick="clinicalReport('ecgCollectionDashBord')" class="btn btn-primary">ECG Times</a>&nbsp;&nbsp;
							<a href="javascript:void(0);" onclick="clinicalReport('vitalCollectionDashBord')" class="btn btn-primary">Vital Collection</a><br/><br/>	
			              </div>
			            </div>
			          </div>
<%-- 		          </c:if> --%>
	        
        
         
          
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
</div>
</body>
</html>