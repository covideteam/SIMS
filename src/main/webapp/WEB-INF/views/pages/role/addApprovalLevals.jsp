<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
		<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.workFlow"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>	
        <div style=" width: 75%; margin-left: 10%;">
        	<div id="promptMsg" style="color: red; font-weight: bold; font-size: medium; text-align: center;"></div>
            <c:url value="/userRole/saveApprovalLevels" var="saveLevels"></c:url>
        	<form:form action="${saveLevels}" method="POST" id="submitBt" modelAttribute="drs" >
        		<div style="width: 100%;">
        		 <div class="form-group row">
        		 <label for="action" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.workFlow"></spring:message></label>
        			
        			<div class="col-sm-5">
       					<form:select path="action" id="action"  cssClass="form-control validate" onchange="actionTypeValidation('action')">
        					<option value="-1">----<spring:message code="label.sdSelect"></spring:message>----</option>
        					<option value="SUBMIT"><spring:message code="label.workFlowSubmit"></spring:message></option>
        					<option value="APPROVAL"><spring:message code="label.workFlowApproval"></spring:message></option>
        					<option value="SENDCOMMENTS"><spring:message code="label.workFlowSendComents"></spring:message></option>
        				</form:select>
        			</div>
        		</div>
        		<div class="form-group row">
        		    <label for="workName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.workFlowName"></spring:message></label>
        			
	       			<div class="col-sm-5">
	       					<form:select path="name" id="workName"  autocomplete="false" cssClass="form-control validate" onchange="workNameValidation('workName')">
	       					    <option value="-1">----<spring:message code="label.sdSelect"></spring:message>----</option>
	        					<option value="StudyDesign"><spring:message code="label.workFlowStudyDesign"></spring:message></option>
	        					<option value="Randomization"><spring:message code="label.randomizationTitle"></spring:message></option>
	        					<option value="DrugReception"><spring:message code="label.drugReceptionTitle"></spring:message></option>
	        					<option value="DrugDispensing"><spring:message code="label.drugDispensingTitle"></spring:message></option>
	        					<option value="StudyDataReview"><spring:message code="label.studyDataReview"></spring:message></option>
	        					<option value="DiviationsReview"><spring:message code="label.deviationsDropVal"></spring:message></option>
	       					</form:select>
	        		</div>
	        	</div>
	        	<div class="form-group row">
	        		<label for="formRole" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.formRole"></spring:message></label>
        			<div class="col-sm-5">
       					<form:select path="fromRole.id" id="formRole" cssClass="form-control " onchange = "formRoleValidation('formRole')">
        					<option value="-1">----<spring:message code="label.sdSelect"></spring:message>----</option>
        					<form:options items="${rolesList}" itemLabel="role" itemValue="id"/>
        				</form:select>
        			</div>
        	  </div>
        	  <div class="form-group row">
            		<label for="toRole" class="col-sm-3 col-form-label" style="color: #212529;">To Role</label>
        			<div class="col-sm-5">
       					<form:select path="toRole.id" id="toRole" onchange="toRoleValidation('toRole', 'toRoleMsg')" cssClass="form-control">
        					<option value="-1">----<spring:message code="label.sdSelect"></spring:message>----</option>
        					<form:options items="${rolesList}" itemLabel="role" itemValue="id"/>
        				</form:select>
        			</div>
        	</div>
        		<div class="form-group row">
        		  <div class="col-sm-10" align="center">
        			<input type="button" id="submitId" value="<spring:message code="label.roleCrSubmit"></spring:message>" class="btn btn-danger btn-md"  onclick="submitFormData()">
        	    	</div>
        		</div> 
        	</div>
        	</form:form>
        </div>
        <%-- <h4 style="text-align:center"><spring:message code="label.gvListTitle"></spring:message>Approval Level List</h4> --%>
        <table  class="table table-striped table-bordered">
		<tr>
			<th style="text-align: center;"><spring:message code="label.approvalLevels"></spring:message></th>
		</tr>
        </table>
        
        <table id="addApprovalLevels" class="table table-striped table-bordered nowrap" style="width: 100%;" >
		<thead>
			<tr>
			    <th><spring:message code="label.randomizationSno"></spring:message></th>
				<th><spring:message code="label.workFlowName"></spring:message></th>
				<th><spring:message code="label.actionName"></spring:message></th>
				<th><spring:message code="label.formRole"></spring:message></th>
				<th><spring:message code="label.toRole"></spring:message></th>
				<th><spring:message code="label.createdBy"></spring:message></th>
				<th><spring:message code="label.createdOn"></spring:message></th>
			</tr>
		</thead>
		
		<tbody>
	        <c:forEach items="${drsList}" var="drdata" varStatus="st">
		        <tr>
		        <td>${st.count}</td>
		        <td>${drdata.name}</td>
		        <td>${drdata.action}</td>
		        <td>${drdata.fromRole.role}</td> 
		        <td>${drdata.toRole.role}</td> 
		        <td>${drdata.createdBy}</td>
		        <td><fmt:formatDate value="${drdata.createdOn}" pattern="${dateFormat}" /></td>
		        </tr>
	        </c:forEach>
         </tbody>
        
         </table>
       </div>
     </div>
  </div>
</div>
 
        
         
        
  <script type="text/javascript">
 
 function actionTypeValidation(id,message){
	 var flag=false;
	 var value=$('#'+id).val();
	 if(value!="" && value!="-1" &&value!="undefined"){
		 $('#'+message).html("");
		 flag=true;
		 $('#'+id).attr("data-isvalid","true");
	 }else{
		 flag=false;
		 /* $('#'+message).html("Required Field"); */
		 $('#'+id).attr("data-errormessage","Required Field..!");
		 $('#'+id).attr("data-isvalid","false");
		 /* $('#'+message).html('${validationText}'); */
	 }
	 checkElementValidation($('#'+id));
	 return flag;
 }
 function workNameValidation(id,message){
	 var flag=false;
	 var value=$('#'+id).val();
	 if(value !="" && value!="-1" &&value!="undefined"){
		 /* $('#'+message).html(""); */
		 flag=true;
		 $('#'+id).attr("data-isvalid","true");
	 }else{
		 flag=false;
		 /* $('#'+message).html('${validationText}'); */
		 $('#'+id).attr("data-errormessage","Required Field..!");
		 $('#'+id).attr("data-isvalid","false");
	 }
	 checkElementValidation($('#'+id));
	 return flag;
 }
 function formRoleValidation(id,message){
	 debugger;
	 var flag=false;
	 debugger;
	 var value=$('#'+id).val();
	 var workName=$('#workName').val();
	 var action=$('#action').val();
	 if((value!="" && value !="-1" && value!="undefined")){
		 var url = mainUrl+"/userRole/checkApprovalSubmitValidation/"+value+"/"+workName+"/"+action;
		 var result = synchronousAjaxCall(url);
		 if(result=="Yes"){
			flag=true;
			/* $('#promptMsg').html(""); */
			$('#'+id).attr("data-isvalid","true");
		 }else{
			 var msg = '${workFlowReocrdExistMsg}';
			 /* $(function(){
			    	bs4Toast.warning('warning', '${workFlowReocrdExistMsg}');
			  }); */
			 $('#'+id).attr("data-errormessage",msg);
			 $('#'+id).attr("data-isvalid","false");
			 /* $('#promptMsg').html('${workFlowReocrdExistMsg}'); */
		 }
	 }else{
		  if(workName == "DiviationsReview" || workName == "StudyDataReview"){
			  flag = true;
			  $('#'+id).attr("data-isvalid","true");
			 /*  $('#'+message).html(''); */
		  }else{
			 flag=false;
			 $('#'+id).attr("data-errormessage","Required Field..!");
			 $('#'+id).attr("data-isvalid","false");
			/*  $('#'+message).html('${validationText}'); */
		  }
	 }
	 checkElementValidation($('#'+id));
	 return flag;
 }
 function toRoleValidation(id,message){
	 debugger;
	 var flag=true;
	 var workName = $('#workName').val();
	 var actionVal = $('#action').val();
	 var fromRoleVal = $('#formRole').val();
	 var toRoleVal = $('#'+id).val();
	 if(toRoleVal == null || toRoleVal =="-1" || toRoleVal == "undefined"){
		 var actionVal = $('#action').val();
		 if(actionVal != null && actionVal != "-1" && actionVal != "undefined"){
			  if(actionVal == "SUBMIT"){
				  flag=false;
				  /* $('#'+message).html('${validationText}'); */
				     $('#'+id).attr("data-errormessage","Required Field..!");
					 $('#'+id).attr("data-isvalid","false");
			  }else{
				  /* $('#'+message).html(''); */
				  $('#'+id).attr("data-isvalid","true");
				  flag = checkWorkFlowRecord(workName, actionVal, fromRoleVal, toRoleVal);
			  }
		 }else{
			 if(workName == null || workName == "-1" || workName == "undefined"){
				 $('#workName').attr("data-errormessage","Required Field..!");
				 $('#workName').attr("data-isvalid","false");
				 /* $('#workNameMsg').html('${validationText}'); */
				 flag=false;
			 }else{
				 $('#workName').attr("data-isvalid","true");
				 /* $('#workNameMsg').html(''); */
				 flag=true;
			 }
			 if(actionVal == null || actionVal == "-1" || actionVal == "undefined"){
				 /* $('#actionMsg').html('${validationText}'); */ 
				 $('#action').attr("data-errormessage","Required Field..!");
				 $('#action').attr("data-isvalid","false");
				 flag=false;
			 }else{
				 $('#action').attr("data-isvalid","true");
				 /* $('#actionMsg').html(''); */ 
				 flag=true;
			 }
		 }
		 
	 }else{
		 flag = checkWorkFlowRecord(workName, actionVal, fromRoleVal, toRoleVal);
	 }
	 checkElementValidation($('#'+id));
	 checkElementValidation($('#workName'));
	 checkElementValidation($('#action'));
	 return flag;
 }
 function checkWorkFlowRecord(name, action, frole, torole){
	 debugger;
	 var flag = false;
	 if((name != null && name != "-1" && name != "undefined") && 
				(action != null && action != "-1" && action != "undefined") &&
				(torole != null && torole != "-1" && torole != "undefined")){
		 var url = mainUrl+"/userRole/checkWrokFlowRecordStatus/"+name+"/"+action+"/"+frole+"/"+torole;
		 var result = synchronousAjaxCall(url);
		 if(result != null && result != "" && result != "undefined"){
			 /* $(function(){
			    	bs4Toast.warning('warning', result);
			  }); */
			 $('#toRole').attr("data-errormessage",result);
			 $('#toRole').attr("data-isvalid","false");
			 /* $('#promptMsg').html(result); */
			 flag = false;
		 }else {
			 $('#toRole').attr("data-isvalid","true");
			 /* $('#promptMsg').html(""); */
			 flag = true;
		 }
	 }else{
		 if(name == null || name == "-1" || name == "undefined"){
			 /* $('#workNameMsg').html('${validationText}'); */
			 $('#workName').attr("data-errormessage","Required Field..!");
			 $('#workName').attr("data-isvalid","false");
			 flag=false;
		 }else{
			 $('#workName').attr("data-isvalid","true");
			 /* $('#workNameMsg').html(''); */
			 flag=true; 
		 }
		 if(action == null || action == "-1" || action == "undefined"){
			 /* $('#actionMsg').html('${validationText}'); */ 
			 $('#action').attr("data-errormessage","Required Field..!");
			 $('#action').attr("data-isvalid","false");
			 flag=false;
		 }else{
			 flag=true;
			 $('#action').attr("data-isvalid","true");
			 /* $('#actionMsg').html(''); */ 
		 }
		 
	 }
	 checkElementValidation($('#workName'));
	 checkElementValidation($('#action'));
	return flag;
 }
   function submitFormData(){
	   $('#submitId').attr('disabled','disabled');
	  var action=actionTypeValidation('action','actionMsg');
	  var name=workNameValidation('workName','workNameMsg');
	  var formdata=formRoleValidation('formRole','formRoleMsg');
	  var todata=toRoleValidation('toRole','toRoleMsg');
	  var deviationFlag = false;
	  if(name){
		  var workName = $('#workName').val();
		  if(workName != null && workName != "-1" && workName != "undefined"){
			  var actionVal = $('#action').val();
			  if(actionVal != null && actionVal != "-1" && actionVal != "undefined"){
				  if(actionVal == "SUBMIT"){
					  if(workName == "DiviationsReview" || workName == "StudyDataReview"){
							var fromRoleVal = $('#formRole').val();
							if(fromRoleVal == null || fromRoleVal == "-1" || fromRoleVal == "undefined"){
								var toRoleVal = $('#toRole').val();
								/* $('#formRoleMsg').html(''); */
								if(toRoleVal == null || toRoleVal == "-1" || toRoleVal == "undefined"){
									deviationFlag = false;
									$('#toRole').attr("data-errormessage","Required Field..!");
									 $('#toRole').attr("data-isvalid","false");
									/* $('#toRoleMsg').html('${validationText}'); */
								}else{
									deviationFlag = true;
									$('#toRole').attr("data-isvalid","true");
									/* $('#toRoleMsg').html(''); */
								}
							}else{
								deviationFlag = false;
								$('#formRole').attr("data-errormessage",'${deviationSubmitMsg}');
								$('#formRole').attr("data-isvalid","false");
								/* $('#formRoleMsg').html('${deviationSubmitMsg}'); */
							}
							
					   }else deviationFlag = true;
				  }else deviationFlag = true;
			  }else deviationFlag = true;
		  }else deviationFlag = true;
		  checkElementValidation($('#formRole'));
		  checkElementValidation($('#toRole'));
	  }
	  if(action && name && formdata && todata && deviationFlag){
		 // alert("trdtfhg");
		 $(".loader").show(); 
		  $('#submitBt').submit();
	  }else{
		  $('#submitId').removeAttr('disabled');
	  }
		
   }
   </script>
   
<script type="text/javascript">
    $(document).ready(function() {
    	$('#submitId').removeAttr('disabled');
        var table = $('#addApprovalLevels').DataTable({
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
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
   
</body>
</html>