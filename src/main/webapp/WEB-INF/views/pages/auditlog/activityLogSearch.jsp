<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User  Information</title>

</head>
<body oncontextmenu="return false;">
	<div class="card">
      <c:if test="${not empty pageMessage}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('success', '${pageMessage}');
	   		</script>
	   </c:if>
	   <c:if test="${not empty pageError}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('error', '${pageError}');
	   		</script>
	   </c:if>
	
	 <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.activityLog.Titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
	      <table  style="width: 100%;">
				<tr>
					<th><spring:message code="label.activityLog.FormDate"></spring:message> </th>
					<td> <input type="date" name="fdate" id="fdate" class="form-control">
						<!-- <script>
							$(function() {
								$("#fdate").datepicker({
									dateFormat : "yy-mm-dd",
									changeMonth : true,
									// minDate: 0,
									changeYear : true
								});
							});
						</script> -->
					</td>
					<th><spring:message code="label.activityLog.ToDate"></spring:message> </th>
					<td><input type="date" name="tdate" id="tdate" class="form-control">
						<!-- <script>
							$(function() {
								$("#tdate").datepicker({
									dateFormat : "yy-mm-dd",
									changeMonth : true,
									// minDate: 0,
									changeYear : true
								});
							});
						</script> -->
					</td>
					<th><spring:message code="label.activityLog.Study"></spring:message><th>
					<td><select  name="study" id="studyid" onchange="studyFunction('studyid','studyidMsg')" class="form-control">
								<option value="-1">--Select--</option>
								 <c:forEach items="${studyList}" var="sl">
									<option value="${sl.id}">${sl.projectNo}</option>
								</c:forEach>
							</select></td>
					<th><spring:message code="label.activityLog.SubjectId"></spring:message><th>
					<td><select  name="subject" id="subjectid" class="form-control">
								<option value="-1">--Select--</option>
								<%-- <c:forEach items="${userList}" var="us">
									<option value="${us.username}">${us.username}</option>
								</c:forEach> --%>
							</select></td>
					<th><spring:message code="label.activityLog.VolunteerId"></spring:message><th>
					<td><select  name="volunteer" id="volunteerid" class="form-control">
								<option value="-1">--Select--</option>
								<%-- <c:forEach items="${userList}" var="us">
									<option value="${us.username}">${us.username}</option>
								</c:forEach> --%>
							</select></td>
				</tr>
				<tr><td colspan="14" align="center"><span style="color: red;" id="message"></span></td></tr>
				<tr><td colspan="14" align="center"><input type="button" value="<spring:message code="label.activityLog.Submit"></spring:message>" class="btn btn-danger btn-md	"  onclick="fromValidation('fdate', 'tdate')">
				</td>
				</tr>
			</table>
       <div id="userDetails"></div>
       </div>
       </div>
       </div>
       </div>
       </div>
</body>
<script type="text/javascript">

/* $(document).ready(function () {
	  //$('#dtBasicExample').DataTable();
	 // $('.dataTables_length').addClass('bs-select');
	  var table = $('#userDetails').DataTable({
        searchBuilder: true,
        "language": {
            "lengthMenu": "${showLabel} _MENU_ ${entriesLabel}",
    		 "search": "${searchLabel}:",
    		 "zeroRecords": "${noDtaAvlLabel}",
    	     "info": "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
    	     "infoEmpty": "${noRcdsLabel}",
    	     "infoFiltered": "(${filterLabel} _MAX_ ${totRcdsLabel})",
    	     "paginate": {
    	         "previous": " ${prevPgLabel}",
    	        	 "next": "${nextLabel}"
    	       }
    		 }
    });
	}); */
	
	function fromValidation(fdate, tdate) {
		var fromdate = $('#' + fdate).val();
		var todate = $('#' + tdate).val();
		var volunteer = $('#volunteerid').val();
		var studyid = $('#studyid').val();
		var subjectid = $('#subjectid').val(); 
		if (fromdate != "" && todate != "") {
			$('#message').html("");
			var url = mainUrl +"/auditlog/activityLoginData/"+fromdate+"/"+todate+"/"+volunteer+"/"+studyid+"/"+subjectid;
			var result = synchronousAjaxCall(url);
			if(result !="" || result !="undefined")
				$('#userDetails').html(result);
		} else {
			$('#message').html("From Date And To Date Required Fields");
		}

	} 
	
	 
	function studyFunction(id,message){
		var studyid=$('#'+id).val();
		//alert(studyid);
		 
		var url1=mainUrl+"/auditlog/getSubjetDataWithStudyId/"+studyid;
		var result1=synchronousAjaxCall(url1);
		if(result1 !="" || result1 !="undefined"){
			$('#subjectid').empty(); 
	 		$('#subjectid').append('<option value="-1">--Select--</option>');
			$('#subjectid').append(result1); 
		} 
		var url2=mainUrl+"/auditlog/getVolunteerDataWithStudyId/"+studyid;
	    var result2=synchronousAjaxCall(url2); 
	 	if(result2 !="" || result2 !="undefined"){
	 		$('#volunteerid').empty(); 
	 		$('#volunteerid').append('<option value="-1">--Select--</option>');
			$('#volunteerid').append(result2); 
	 	}
	}
    
</script>
</html>