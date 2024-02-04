<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Study Review</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/css/bootstrap-timepicker.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-timepicker/0.5.2/js/bootstrap-timepicker.min.js"></script>
<style type="text/css">

.swal-wide{
    width:950px !important;
    
}
</style>
</head>
<body>
<input type='hidden' id="pageType" value="${screenType}">
<input type='hidden' id="startFlagVal" value="${pageContext.request.contextPath}/static/images/reviewBeginFlag.png">
<input type='hidden' id="inProgressVal" value="${pageContext.request.contextPath}/static/images/red-flag.png">
<input type='hidden' id="completedVal" value="${pageContext.request.contextPath}/static/images/greenFlag.png">

<c:if test="${screenType == 'review'}">
	<div id="discripencyModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document" style="width:80%;max-width:80%;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Discrepancies</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<table style="width:100%;">
	      		<tr><td style="width:80px;">Comment</td><td><input type="text" class="form-control" id="txtComments" name="comments"/></td><td style="width:80px;"><button type="button" class="btn btn-primary" id="btnSaveDescrepancy">Add</button></td></tr>
	      	</table>
	       	<table id="hiddenDetails"></table>
	       	<table id="tblComments" class="ds-table ds-table-align-header-center ds-table-border mt-5">
	       		<thead><tr><td>Comment</td><td>Commented By</td><td>Commented Date</td><td>Response</td><td>Response Given By</td><td>Response Given Date</td><td>Action</td></tr></thead>
	       		<tbody>
	       				
	       		</tbody>
	       	</table>
	      </div>
	      <div class="modal-footer">
	      	<button type="button" id="btnBack" class="btn btn-secondary" style='display:none;'>Back</button>
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
</c:if>

<c:if test="${screenType == 'responsetoreview'}">
	<div id="discripencyModal" class="modal" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document" style="width:80%;max-width:80%;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Discrepancies</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	      	<div id="divParameter">
		      	<table>
		      		<tr>
		      			<td style="width:150px;" id="tdParameterName">
		      			<td>
		      			<td id="tdParameterControl">
		      				
		      			<td>
		      		</tr>
		      	</table>
		      	<table>
		      		<tr>
		      			<td style="width:150px;">Reason for Change</td>
		      			<td style="width:200px;">
		      				<input type="text" class="form-control" id="txtReasonforChange"/>
		      				<input type="hidden" class="form-control" id="txtOldValue"/>
		      			</td>
		      		</tr>
		      		
		      	</table>
	      	</div>
	      	<table id="tblComments" class="ds-table ds-table-align-header-center ds-table-border mt-5">
	       		<thead><tr><td>Comment</td><td>Commented By</td><td>Commented Date</td><td>Response</td><td>Response Given By</td><td>Response Given Date</td><td></td></tr></thead>
	       		<tbody>
	       				
	       		</tbody>
	       	</table>
	       	<div id="divResponse" style="display:none;">
	       		<table>
	       			<tr>
	       				<td style="width:150px;">Comment <td>
	       				<td><span id="spComment"></span><td>
	       			</tr>
	       			<tr>
	       				<td>Response <td>
	       				<td><textarea id="taResponse" class="form-control" style="width:500px;height:100px;"></textarea><td>
	       			</tr>
	       		</table>
	       	</div>
	       	<div id="staticFromResponse" style="display: none;">
	       	
	       	</div>
	      </div>
	      <div class="modal-footer">
	      	<button type="button" id="btnBack" class="btn btn-secondary" style='display:none;'>Back</button>
	      	<button type="button" class="btn btn-primary" id="btnSaveDiscrepancy" style='display:none;'>Save</button>
	        <button type="button" class="btn btn-primary" id="btnSaveDiscrepancyResponse" style='display:none;'>Save</button>
	        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="modelClosingFunction()">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
</c:if>


<form:form id="frmActivityForm">
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.srTitle"></spring:message></h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div style="padding:10px;">
				    	<table style="width: 100%;">
				    		<tr>
			    				<td style="width:120px"><spring:message code="label.projects"></spring:message></td>
			   					<td style="width:200px">
			   					<!-- Present static value . After add group value  -->
			   					    <input type="hidden" name="groupId" id="groupId" value="1" class="exclude"/>
			   					    <input type="hidden" name="studyActivityDataId" id="studyActivityDataId"/>
			   						<select name="studyId" id="studyName" class="form-control validate exclude" data-validate="required">
					    				<option value="">----<spring:message code="label.sdSelect"></spring:message>----</option>
					    				<c:forEach items="${smList}" var="sm">
											<option value="${sm.id}">${sm.projectNo}</option>
										</c:forEach>
									</select>
									<div id="studyNameMsg" style="color: red;"></div>
								</td>
								<td style="width:120px"><spring:message code="label.actName"></spring:message></td>
			   					<td>
									<select name="activityId" id="crfName" class="form-control validate exclude" data-validate="required">
										<option value="">----<spring:message code="label.sdSelect"></spring:message>----</option>
									</select>
								</td>
							</tr>
					    </table>
					</div>
					<div id="activityDiv" style="display:none;padding:10px;">
					</div>
			 		<div id="crfReviewDiv" style="display:none;padding:10px;">
			 			<table id="tblSubjects" class="ds-table">
			 				<thead>
			 					<tr>
			 						<td>
			 						 	<spring:message code="label.sdSubNo"></spring:message>
			 						</td>
			 						<td>
			 						 	<spring:message code="label.sdDoneBy"></spring:message>
			 						</td>
			 						<td>
			 						 	<spring:message code="label.sdDoneDate"></spring:message>
			 						</td>
			 						<td>
			 							<spring:message code="label.sdAction"></spring:message>
			 						</td>
			 					</tr>
			 				</thead>
			 				<tbody>
			 				
			 				</tbody>
			 			</table>
			 		</div>
			 		<div id="cpuActReviewDiv" style="display: none;"></div>
			 		
			         </div>
				</div>
			</div>
		</div>
</form:form>
<!--The  Modal For Cpu Activities Review -->
					  <div class="modal fade" id="myModal">
					    <div class="modal-dialog modal-xl">
					      <div class="modal-content">
					      
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title" id="cpuDataTitle"></h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        
					        <!-- Modal body -->
					        <div class="modal-body">
					        	<div id="newValMessage" style="color: red; font-size: small; font-weight: bold; text-align: center;"></div>
					        	<table class="table table-bordered table-striped" style="font-size: 14px; vertical-align: middle;" id="cpuActivityDataDiv"></table>
					        	<%-- <div class="col-md-12" id="deviationCommentsDiv">
										<table class="table table-bordered table-striped" style="width: 100%;" id="deviationCommentsTable">
											<thead>
												<tr>
													<th><spring:message code="label.devTable.comments"></spring:message></th>
													<th><spring:message code="label.devTable.commentedBy"></spring:message></th>
													<th><spring:message code="label.devTable.commentedOn"></spring:message></th>
												</tr>
											</thead>
										<tbody id="deviationCommentsData"></tbody>
									 </table>
							      </div> --%>
					         </div>
					        
					        <!-- Modal footer -->
					        <%-- <div class="modal-footer">
					          <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="label.devTable.close"></spring:message></button>
					        </div> --%>
					        
					      </div>
					    </div>
					  </div>
<script>

/* <c:if test="${screenType == 'review'}">
	document.addEventListener('DOMContentLoaded', function() {
		  loadJS('/SIMS/static/js/studyExecution.js');
		  loadJS('/SIMS/static/js/studyDataReviewCommon.js');
		  loadJS('/SIMS/static/js/studyDataReview.js');
	}, false);
</c:if>

<c:if test="${screenType == 'responsetoreview'}">
	document.addEventListener('DOMContentLoaded', function() {
		  loadJS('/SIMS/static/js/studyExecution.js');
		  loadJS('/SIMS/static/js/studyDataReviewCommon.js');
		  loadJS('/SIMS/static/js/studyDataReviewResponse.js');
	}, false);
</c:if> */
   var screenTypeVal = '${screenType}';
   if(screenTypeVal == 'review'){
		document.addEventListener('DOMContentLoaded', function() {
			  loadJS('/SIMS/static/js/studyExecution.js');
			  loadJS('/SIMS/static/js/studyDataReviewCommon.js');
			  loadJS('/SIMS/static/js/studyDataReview.js');
		}, false);
	}else if(screenTypeVal == "responsetoreview"){
		document.addEventListener('DOMContentLoaded', function() {
			  loadJS('/SIMS/static/js/studyExecution.js');
			  loadJS('/SIMS/static/js/studyDataReviewCommon.js');
			  loadJS('/SIMS/static/js/studyDataReviewResponse.js');
		}, false);
	}
</script>
<script>
        $('#newVal').timepicker({
            uiLibrary: 'bootstrap4'
        });
    </script>
</body>
</html>