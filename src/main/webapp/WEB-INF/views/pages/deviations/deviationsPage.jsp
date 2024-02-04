<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Deviations Page</title>
<style type="text/css">
#deviationDiv {
	  display: none;
	}
#deviationCommentsDiv {
  display: none;
}

</style>
</head>
<body>
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.deviationSideMenu"></spring:message></h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
				    <input type="hidden" name="project" id="project" value="<spring:message code="label.crfData.study"></spring:message>">
				    <input type="hidden" name="activity" id="activity" value="<spring:message code="label.devTable.activity"></spring:message>">
				    <input type="hidden" name="timePoint" id="timePoint" value="<spring:message code="label.devTable.timePoint"></spring:message>">
				    <input type="hidden" name="subjNo" id="subjNo" value="<spring:message code="label.devTable.subjNo"></spring:message>">
				    <input type="hidden" name="period" id="period" value="<spring:message code="label.devTable.period"></spring:message>">
				    <input type="hidden" name="deviation" id="deviation" value="<spring:message code="label.devTable.deviation"></spring:message>">
				    <input type="hidden" name="createdBy" id="createdBy" value="<spring:message code="label.devTable.createdBy"></spring:message>">
				    <input type="hidden" name="createdOn" id="createdOn" value="<spring:message code="label.devTable.createdOn"></spring:message>">
				    <input type="hidden" name="comments" id="comments" value="<spring:message code="label.devTable.comments"></spring:message>">
				    <input type="hidden" name="sendComments" id="sendComments" value="<spring:message code="label.devTable.sendComments"></spring:message>">
				    <input type="hidden" name="approve" id="approve" value="<spring:message code="label.devTable.approve"></spring:message>">
				    <input type="hidden" name="submit" id="submit" value="<spring:message code="label.devTable.submit"></spring:message>">
				    <input type="hidden" name="dateFormat" id="dateFormat" value="${dateFormat}">
					<table style="width: 100%;">
			    		<tr>
			    			<td style="text-align: right;"><spring:message code="label.crfData.study"></spring:message> &nbsp;&nbsp;&nbsp;</td>
		    				<td>
		    					<div style="width: 45%;">
			   						<select name="projectName" id="projectName" class="form-control" onchange="projectNameValidation('projectName', 'projectNameMsg')">
					    				<option value="">-----<spring:message code="label.sdSelect"></spring:message>-----</option>
					    				<c:forEach items="${smList}" var="sm">
											<option value="${sm.id}">${sm.projectNo}</option>
										</c:forEach>
									</select>
								</div>
								<div id="projectNameMsg" style="color: red;"></div>
							</td>
						</tr>
		 			</table>
		 			<div class="col-md-12" id="deviationDiv">
						<table class="table table-bordered table-striped" style="width: 100%;" id="deviationTable">
							<thead>
								<tr>
									<th><spring:message code="label.devTable.activity"></spring:message></th>
									<th><spring:message code="label.devTable.timePoint"></spring:message></th>
									<th><spring:message code="label.devTable.subjNo"></spring:message></th>
									<th><spring:message code="label.devTable.period"></spring:message></th>
									<th><spring:message code="label.devTable.deviation"></spring:message></th>
									<th><spring:message code="label.devTable.createdBy"></spring:message></th>
									<th><spring:message code="label.devTable.createdOn"></spring:message></th>
								</tr>
							</thead>
						<tbody id="deviationData">
						
						</tbody>
					 </table>
			      </div>
				</div>
			</div>
		</div>
<!-- The Modal -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title"><spring:message code="label.devTable.deviationDetails"></spring:message></h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" style="width: 100%;">
          	<table class="table table-bordered table-striped" style="width: 100%; font-size: 14px; vertical-align: middle;" id="deviationDataDetails"></table>
        	<div class="col-md-12" id="deviationCommentsDiv">
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
		      </div>
         </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <c:choose>
          <c:when test="${type eq 'Submit'}">
           <input type="button" value="Submit" class="btn btn-primary btn-md" onclick="deviationDetailsSavingFuntion('Approve')">
          </c:when>
          <c:otherwise>
          <input type="button" value="Submit" class="btn btn-primary btn-md" onclick="deviationDetailsSavingFuntion('Approve')">
          <input type="button" value="Sent Comments" class="btn btn-primary btn-md" onclick="deviationDetailsSavingFuntion('SentComments')">
          </c:otherwise>
          </c:choose>
          <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="label.devTable.close"></spring:message></button>
        </div>
        
      </div>
    </div>
  </div>
	</div>
	<script type="text/javascript">
	var result = "";
		function projectNameValidation(id, messageId){
			var value = $('#'+id).val();
			var htmlStr = "";
			$('#deviationData').empty();
			$('#deviationTable').dataTable().fnClearTable();
			$('#deviationTable').dataTable().fnDraw();
			$('#deviationTable').dataTable().fnDestroy();
			if(value != null && value != "" && value != "undefined"){
				result = synchronousAjaxCall(mainUrl+ "/deviations/getDeviationRecords/" + value+"/"+'${type}');
				if(result != null && result != "" && result != "undefined"){
					if(result.subDevList != null && result.subDevList != "undefined"){
						for(var i=0; i<result.subDevList.length; i++){
							var createdOn = new Date(result.subDevList[i].createdOn);
							var dateFormat = $('#dateFormat').val();
							if(dateFormat != null && dateFormat != "" && dateFormat != "undefined"){
								dateFormat = dateFormat.replace('MMM', 'M' );
								dateFormat = dateFormat.replace('yyyy', 'yy' );
								createdOn = $.datepicker.formatDate(dateFormat, createdOn);
							}else createdOn = $.datepicker.formatDate('yy-mm-dd', createdOn);
							htmlStr +='<tr onclick="showDeviationRecordDetails('+result.subDevList[i].id+')">'
							 +'<td>'+result.subDevList[i].activity.name+'</td>'
							 +'<td>'+result.subDevList[i].timePoint+'</td>'
							 +'<td>'+result.subDevList[i].subject.subjectNo+'</td>'
							 +'<td>'+result.subDevList[i].period+'</td>'
							 +'<td>'+result.subDevList[i].devMsgId.message+'</td>'
							 +'<td>'+result.subDevList[i].createdBy.fullName+'</td>'
							 +'<td>'+createdOn+'</td>'
							 +'</tr>';
						}
					}
				}
				if(htmlStr != ""){
					$('#deviationData').append(htmlStr);
					$('#deviationTable').DataTable();
					$('#deviationDiv').show();
				}else {
					$('#deviationTable').DataTable();
					$('#deviationDiv').show();
				}
				
				
			}
		}
		var deviationId = "";
		function showDeviationRecordDetails(id){
			debugger;
			deviationId = id;
			var htmlStr = "";
			$('#deviationCommentsData').empty();
			$('#deviationDataDetails').empty();
			$('#deviationCommentsTable').dataTable().fnClearTable();
		    $('#deviationCommentsTable').dataTable().fnDraw();
		    $('#deviationCommentsTable').dataTable().fnDestroy();
			if(result != null && result != "" && result != "undefined"){
				if(result.subDevList != null && result.subDevList != "undefined"){
					for(var i=0; i<result.subDevList.length; i++){
						if(result.subDevList[i].id == id){
							var createdOn = new Date(result.subDevList[i].createdOn);
							var dateFormat = $('#dateFormat').val();
							if(dateFormat != null && dateFormat != "" && dateFormat != "undefined"){
								dateFormat = dateFormat.replace('MMM', 'M' );
								dateFormat = dateFormat.replace('yyyy', 'yy' );
								createdOn = $.datepicker.formatDate(dateFormat, createdOn);
							}else createdOn = $.datepicker.formatDate('yy-MM-dd', createdOn);
							htmlStr +='<tr><td>'+$('#project').val()+' :</td>'
								 +'<td>'+result.study.projectNo+'</td>'
								 +'<td>'+$('#period').val()+' :</td>'
								 +'<td>'+result.subDevList[i].period+'</td>'
								 +'<td>'+$('#subjNo').val()+' :</td>'
								 +'<td>'+result.subDevList[i].subject.subjectNo+'</td>'
								 +'<td>'+$('#timePoint').val()+' :</td>'
								 +'<td>'+result.subDevList[i].timePoint+'</td></tr>'
								 +'<tr><td>'+$('#activity').val()+' :</td>'
								 +'<td>'+result.subDevList[i].activity.name+'</td>'
								 +'<td>'+$('#deviation').val()+' :</td>'
								 +'<td>'+result.subDevList[i].devMsgId.message+'</td>'
								 +'<td>'+$('#createdBy').val()+' :</td>'
								 +'<td>'+result.subDevList[i].createdBy.fullName+'</td>'
								 +'<td>'+$('#createdOn').val()+' :</td>'
								 +'<td>'+createdOn+'</td>'
								 +'</tr>'
								 +'<tr><td>'+$('#comments').val()+' :</td>'
								 +'<td colspan="7"><textarea rows="5" cols="100" name="deviationComments" id="deviationComments" onblur="deviationCommentsValidation()" class="form-control" style="width:75%;"></textarea><div id="devMsg" style="color:red;"></div></td></tr>';
							/* if('${type}' == "Submit"){
								htmlStr +='<tr align="center"><td colspan="8"><input type="button" value="'+$('#submit').val()+'" class="btn btn-primary btn-md" onclick="deviationDetailsSavingFuntion('+"'Approve'"+')"></td></tr>';
							}else{
								htmlStr +='<tr align="center"><td colspan="8"><input type="button" value="'+$('#approve').val()+'" class="btn btn-primary btn-md" onclick="deviationDetailsSavingFuntion('+"'Approve'"+')">'
								 +'&nbsp;&nbsp;&nbsp;<input type="button" value="'+$('#sendComments').val()+'" class="btn btn-primary btn-md" onclick="deviationDetailsSavingFuntion('+"'SentComments'"+')"></td></tr>';
							} */
								
						}
					}
				}
				
				var detailsStr = "";
				if(result.ssdrdList.length > 0){
					for(var j=0; j<result.ssdrdList.length; j++){
						if(result.ssdrdList[j].ssdevation.id == id){
							var createdOn = new Date(result.ssdrdList[j].commentedOn);
							var dateFormat = $('#dateFormat').val();
							if(dateFormat != null && dateFormat != "" && dateFormat != "undefined"){
								dateFormat = dateFormat.replace('MMM', 'M' );
								dateFormat = dateFormat.replace('yyyy', 'yy' );
								createdOn = $.datepicker.formatDate(dateFormat, createdOn);
							}else
								createdOn = $.datepicker.formatDate('yy-mm-dd', createdOn);
							detailsStr +='<tr><td>'+result.ssdrdList[j].comments+'</td>'
											+'<td>'+result.ssdrdList[j].commentedBy.fullName+'</td>'
											+'<td>'+createdOn+'</td></tr>';
						}
					}
					if(detailsStr != ""){
						$('#deviationCommentsData').append(detailsStr);
						$('#deviationCommentsTable').DataTable();
						$('#deviationCommentsDiv').show();
						
					}
				}
			}
			$('#deviationDataDetails').append(htmlStr);
			$('#myModal').modal('show');
		}
		function deviationCommentsValidation(){
			var flag = false;
			var value = $('#deviationComments').val();
			if(value == null || value == "" || value == "undefined"){
				flag = false;
				$('#devMsg').html('${validationText}');
			}else{
				flag = true;
				$('#devMsg').html('');
			}
			return flag;
		}
		function deviationDetailsSavingFuntion(submitType){
			var devFalg = deviationCommentsValidation();
			if(devFalg){
				var commentsVal = $('#deviationComments').val();
				if(deviationId != "" && (commentsVal != null && commentsVal != "" && commentsVal != "undefined")){
// 					alert("url is :"+mainUrl+ "/deviations/saveDeviationRecord/" +deviationId+"/"+'${type}'+"/"+commentsVal);
					var finalResult = synchronousAjaxCall(mainUrl+ "/deviations/saveDeviationRecord/" +deviationId+"/"+'${type}'+"/"+commentsVal+"/"+submitType);
					if(finalResult != null && finalResult != "" && finalResult != "undefined"){
						if(finalResult == "Success"){
							Swal.fire({
							    title: "",
							    text: '${devSuccessMsg}',
							    icon: "success"
							}).then(function() {
								$('#myModal').modal('hide');
								projectNameValidation('projectName', 'projectNameMsg');
								$('#deviationTable').DataTable().destroy();
								  $('#deviationTable').DataTable().draw();
// 								location.reload(true);
							});
						}else{
							Swal.fire({
							    title: "",
							    text: finalResult,
							    icon: "error"
							}).then(function() {
							
							});
						}
					}
				}
				
			}
			
		}
	</script>
</body>
</html>