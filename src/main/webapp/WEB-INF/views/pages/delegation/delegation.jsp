<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
   <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<style>
body {
    color: #404E67;
    background: #F5F7FA;
    font-family: 'Open Sans', sans-serif;
}
.table-wrapper {
    width: 1200px;
    margin: 10px auto;
    background: #fff;
    padding: 10px;	
    box-shadow: 0 1px 1px rgba(0,0,0,.05);
}

table.dataTable thead .sorting:after,
table.dataTable thead .sorting:before,
table.dataTable thead .sorting_asc:after,
table.dataTable thead .sorting_asc:before,
table.dataTable thead .sorting_asc_disabled:after,
table.dataTable thead .sorting_asc_disabled:before,
table.dataTable thead .sorting_desc:after,
table.dataTable thead .sorting_desc:before,
table.dataTable thead .sorting_desc_disabled:after,
table.dataTable thead .sorting_desc_disabled:before {
  bottom: .5em;
}
    
.table-title {
    padding-bottom: 10px;
    margin: 0 0 10px;
}
.table-title h2 {
    margin: 6px 0 0;
    font-size: 22px;
}
.table-title .add-new {
    float: right;
    height: 30px;
    font-weight: bold;
    font-size: 12px;
    text-shadow: none;
    min-width: 100px;
    border-radius: 50px;
    line-height: 13px;
}
.table-title .add-new i {
    margin-right: 4px;
}
table.table {
    table-layout: fixed;
}
table.table tr th, table.table tr td {
    border-color: #e9e9e9;
}
table.table th i {
    font-size: 13px;
    margin: 0 5px;
    cursor: pointer;
}
table.table th:last-child {
    width: 170px;
}
table.table td a {
    cursor: pointer;
    display: inline-block;
    margin: 0 5px;
    min-width: 24px;
}    
table.table td a.add {
    color: #27C46B;
}
table.table td a.edit {
    color: #FFC107;
}
table.table td a.delete {
    color: #E34724;
}
table.table td i {
    font-size: 19px;
}
table.table td a.add i {
    font-size: 24px;
    margin-right: -1px;
    position: relative;
    top: 3px;
}    
table.table .form-control {
    height: 32px;
    line-height: 32px;
    box-shadow: none;
    border-radius: 2px;
}
table.table .form-control.error {
    border-color: #f50000;
}
table.table td .add {
    display: none;
}

  .toggle.ios, .toggle-on.ios, .toggle-off.ios { border-radius: 20rem; }
  .toggle.ios .toggle-handle { border-radius: 20rem; }

</style>




	<div oncontextmenu="return false;">
        <div >
            <div class="card-header" style="margin-bottom: 10px;">
               <div class="card-title" style="color:gray; font-size: large; font-weight: bold;"><spring:message code="label.delstudy"></spring:message> <b><spring:message code="label.delactivity"></spring:message></b> <spring:message code="label.delduty"></spring:message> <b><spring:message code="label.deldelegation"></spring:message></b></div>
             	
             <%-- <div class="row">
                    <div class="col-sm-8"><h2><spring:message code="label.delstudy"></spring:message> <b><spring:message code="label.delactivity"></spring:message></b> <spring:message code="label.delduty"></spring:message> <b><spring:message code="label.deldelegation"></spring:message></b></h2></div>
                    <div class="col-sm-4">
                    </div>
                </div> --%>
                
             <button type="button" id="addbtn" class="btn btn-info add-new" style="text-align: left;"><i class="fa fa-plus"></i> <spring:message code="label.deladdnew"></spring:message></button>
            </div>
            <table id="dtBasicExample" class="table table-striped table-bordered table-sm" style="width:100%;">
           
                <thead>
                    <tr>
                        
                        <th><spring:message code="label.delprojNO"></spring:message></th>
                        <th><spring:message code="label.delrole"></spring:message></th>
                        <th><spring:message code="label.delempname"></spring:message></th>
                        <th><spring:message code="label.deldelgBy"></spring:message></th>
                        <th><spring:message code="label.deldelgdate"></spring:message></th>
                        <th><spring:message code="label.delmodifiedby"></spring:message></th>
                        <th><spring:message code="label.delmodifiedon"></spring:message></th>
                        <th><spring:message code="label.delaction"></spring:message></th>
                    </tr>
                </thead>
                <tbody>
                
                    <c:forEach items="${delDto.usmList}" var="usm" >
                    	<tr>
                    	
                    		<td>${usm.study.projectNo}</td>
                    		<c:set value="" var="roleVal"></c:set>
                    		<c:forEach items="${usm.rolesList}" var="role">
                    		    <c:choose>
                    		    	<c:when test="${roleVal eq ''}">
                    		    		<c:set value="${delDto.rolesMap[role]} " var="roleVal"></c:set>
                    		    	</c:when>
                    		    	<c:otherwise>
                    		    		<c:set value="${roleVal},${delDto.rolesMap[role]} " var="roleVal"></c:set>
                    		    	</c:otherwise>
                    		    </c:choose>
                    			
                    		</c:forEach>
                    		<td>
                    			<%-- <table>
                    				<tr>
                    					<c:forEach items="${usm.rolesList}" var="role">
                    						<td>${delDto.rolesMap[role]}</td>
                    					</c:forEach>
                    				</tr>
                    				
                    				
                    			</table> --%>
                    			${roleVal}                			 
                    		</td>
                    		<td>${usm.userId.fullName}</td>
                    		
                    		<td>${usm.createdBy}</td>
                    		<td><fmt:formatDate value="${usm.createdOn}" pattern="${dateFormat}" /></td>
                    		<td>${usm.updatedBy}</td>
                    		<td><fmt:formatDate value="${usm.updatedOn}" pattern="${dateFormat}" /></td>
                    		<td>
                    		<table ><tr><td>
                    		<div>
                    		    
                    		          <a class="edit" title="Edit" id="editbtn" data-toggle="tooltip" onclick="deligationUpdat('${usm.id}')"><i class="fa fa-pencil"></i></a>
                    		   
                    		    </div>
                    		</td>
                    		<td>
                    		<div class="custom-control custom-switch">
                       		
                       		      <c:choose>
                       		    	<c:when test="${usm.status eq true}">
                       		    		 <input type="checkbox" class="custom-control-input" data-id="${usm.status}" id="${usm.id}"  name="checkbox" title="Active" checked> <label class='custom-control-label' for="${usm.id}"></label>
                       		    	</c:when>
                       		    	<c:otherwise>
                       		    			 <input type="checkbox" class="custom-control-input" data-id="${usm.status}" id="${usm.id}" name="checkbox" title="Inactive"><label class='custom-control-label' for="${usm.id}"></label>
                       		    	</c:otherwise>
                       		    </c:choose>      
                       		  	
   
                                 </div>
                    		</td>
                    		</tr></table>
                              </td>
                    	</tr>
                    </c:forEach>
                </tbody>
            </table>
           
        
   
    <div class="modal fade" id="soluntionPreparationModal" tabindex="-1" role="dialog" aria-labelledby="soluntionPreparationModalLabel" aria-hidden="true">
    <div class="modal-dialog" id="modal-dialog_id" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" ><b><spring:message code="label.deldtydel"></spring:message></b></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <c:url value="/delegation/saveDelegation" var="savedel"/>
            <form:form action="${savedel}" method="post"  id="formsumit" >
                <div class="modal-body">
                    
                        <div class="form-group">
                            <table class="table table-hover  list-table table-center">
                            <tr>
                                    <td>
                                <label>Project Number</label>
	 	  		                    </td>
                                   <td>
                                        <select id="study" class="form-control" name="study" onchange="studyValidation('study','studyMsg')" required>
                                            <option value="0">Select</option>
                                            <c:forEach items="${delDto.smList}" var="study">
                                            
                    						   <option value="${study.id}">${study.projectNo}</option>
                    						
                    					    </c:forEach>
                                            
                                        </select>
                                        <div id="studyMsg" style="color: red;"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label><spring:message code="label.delusr"></spring:message></label></td>
                                    <td>
                                        <select id="user" class="form-control" name="user" onchange="userValidation('user','userMsg')" required>
                                        <option value="0">Select</option>
                                           <c:forEach items="${delDto.usersList}" var="user">
                                            
                    						   <option value="${user.id}">${user.username}</option>
                    						
                    					    </c:forEach>
                                        </select>
                                        <div id="userMsg" style="color: red;"></div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <label><spring:message code="label.delrls"></spring:message></label>
                                    </td>
                                    <td>
                                        <select id="studyRoles" style="height:80px" class="form-control" name="studyRoles" multiple onchange="stdRlValidation('studyRoles','studyRolesMsg')" required>
                                        
                                            <c:forEach items="${delDto.getRoleList()}" var="role">
                                            
                    						   <option value="${role.getId()}">${role.getRole()}</option>
                    						
                    					    </c:forEach>
                                            
                                        </select>
                                        <div id="studyRolesMsg" style="color: red;"></div>
                                    </td>
                                </tr>
                                
                            </table>
                        </div>
                </div>
                </form:form>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="btnSubmit"><spring:message code="label.deladd"></spring:message></button>
                     
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="label.delclose"></spring:message></button>
                    
                </div>
        </div>
    </div>
    </div>
  </div>

<!-- Edit Popup  Starts-->
                 <c:url value="/delegation/deligationStatusChange" var="deldelg"/>
               <form:form action="${deldelg}" method="post"  id="delformsumit" >
              		  <input type="hidden" name="statusuwsId" id="statusuwsId" >
              		  <input type="hidden" name="statusuwsval" id="statusuwsval" >
               </form:form>

 <div class="modal fade" id="editsoluntionPreparationModal" tabindex="-1" role="dialog" aria-labelledby="soluntionPreparationModalLabel" aria-hidden="true">
    <div class="modal-dialog" id="modal-dialog_id" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="ModalLabel"><b><spring:message code="label.delupdtDel"></spring:message></b></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div id="updatePage"></div>
             <div class="modal-footer">
             
                  <button type="button" class="btn btn-primary" id="btnupdateSubmit" ><spring:message code="label.delupd"></spring:message></button>
                  <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="label.delclose"></spring:message></button>
              </div>
        </div>
    </div>
</div>



<!-- Edit Popup End  -->
    
     
</div>     



<script type="text/javascript">


$(document).ready(function () {
	  //$('#dtBasicExample').DataTable();
	 // $('.dataTables_length').addClass('bs-select');
	  var table = $('#dtBasicExample').DataTable({
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
	});

	function studyValidation(id, messageId){
		debugger;
		var stdvalFlag = false;
		var value = $('#'+id).val();
		if(value == "0"||value == null || value == "" || value == "undefined"){
			/* $('#'+messageId).html("Required Field."); */
			$('#'+messageId).html('${validationText}');
			stdvalFlag = false;
		}else{
			$('#'+messageId).html("");
			stdvalFlag = true;
		}
		return stdvalFlag;	
	}
	
	function userValidation(id, messageId){
		debugger;
		var stdvalFlag = false;
		var value = $('#'+id).val();
		if(value == "0" || value == null || value == "" || value == "undefined"){
			/* $('#'+messageId).html("Required Field."); */
			$('#'+messageId).html('${validationText}');
			stdvalFlag = false;
		}else{
			$('#'+messageId).html("");
			stdvalFlag = true;
		}
		return stdvalFlag;	
	}
	
	
	function stdRlValidation(id, messageId){
		debugger;
		var stdvalFlag = false;
		var value = $('#'+id).val();
		if(value == "0" || value == null || value == "" || value == "undefined"){
			/* $('#'+messageId).html("Required Field."); */
			$('#'+messageId).html('${validationText}');
			stdvalFlag = false;
		}else{
			$('#'+messageId).html("");
			stdvalFlag = true;
		}
		return stdvalFlag;	
	}
	
	
$(document).on("click", "#addbtn", function (e) {
	
	 $("#soluntionPreparationModal").modal('show');
	 

});

$(document).on("click", "#btnSubmit ", function (e) {
	
	debugger;
	var studyflag = studyValidation('study','studyMsg');
	var userFlag = userValidation('user','userMsg');
	var stdRolesFlag = stdRlValidation('studyRoles','studyRolesMsg');

			//alert(" ctFlag:"+studyflag+" pnameFlag:"+userFlag+" orderflag:"+stdRolesFlag);
			
			if(studyflag && userFlag && stdRolesFlag){
				
				 listofRoles = $("#studyRoles").val();
				 listofStudies =  $("#study").val();
				 listofUsers = $("#user").val();
			     $("#formsumit").submit();
			    /*  $.alert({
					    title: 'Alert!',
					    content: 'Submitted successfully!',
					}); */
			}
	 
	 
	 
});

function studyupValidation(id, messageId){
	debugger;
	var stdvalFlag = false;
	var value = $('#'+id).val();
	if(value == "0"||value == null || value == "" || value == "undefined"){
		/* $('#'+messageId).html("Required Field."); */
		$('#'+messageId).html('${validationText}');
		stdvalFlag = false;
	}else{
		$('#'+messageId).html("");
		stdvalFlag = true;
	}
	return stdvalFlag;	
}

function stduserValidation(id, messageId){
	debugger;
	var stdvalFlag = false;
	var value = $('#'+id).val();
	if(value == "0" || value == null || value == "" || value == "undefined"){
		/* $('#'+messageId).html("Required Field."); */
		$('#'+messageId).html('${validationText}');
		stdvalFlag = false;
	}else{
		$('#'+messageId).html("");
		stdvalFlag = true;
	}
	return stdvalFlag;	
}


function stdRldelValidation(id, messageId){
	debugger;
	var stdvalFlag = false;
	var value = $('#'+id).val();
	if(value == "0" || value == null || value == "" || value == "undefined"){
		/* $('#'+messageId).html("Required Field."); */
		$('#'+messageId).html('${validationText}');
		stdvalFlag = false;
	}else{
		$('#'+messageId).html("");
		stdvalFlag = true;
	}
	return stdvalFlag;	
}


$(document).on("click", "#btnupdateSubmit", function (e) {
	
	var stdupFlag = studyupValidation('studyM','studyMMsg');
	var stdusrFlag = stduserValidation('userid','userdelMsg');
	var stdrlFlag = stdRldelValidation('stdRolesU','stdRolesUMsg')
	
	debugger;
	//alert(" ctFlag:"+stdupFlag+" pnameFlag:"+stdusrFlag+" orderflag:"+stdrlFlag);
	if(stdupFlag && stdusrFlag && stdrlFlag){
		
	studyM = $("#studyM").val();
	
	userid = $("#userid").val();
	
	stdRolesU = $("#stdRolesU").val();
	
	$("#uwsStudy").val(studyM);
	
	$("#uwsuserId").val(userid);
	
	$("#uswRoles").val(stdRolesU);
	
	
	$("#updateformsumit").submit();
	
	
	 $.alert({
		    title: 'Alert!',
		    content: 'Updated successfully!',
		});
	
	}
	
});


function deligationUpdat(uwsId){
	debugger;
	var mainUrl = $('#mainUrl').val();
	//alert(mainUrl);
//    var value = $('#'+uwsId).val();
   var result = synchronousAjaxCall(mainUrl+"/delegation/getDelegationDetailsForUpdate/"+uwsId);
	if(result != null && result != "" && result != "undefined"){
		$('#updatePage').html(result);
		 
		 $("#editsoluntionPreparationModal").modal('show');
		 
	}
}

$(document).on("click","input[type='checkbox']", function(e){
	debugger;
	var mainUrl = $('#mainUrl').val();
	var statusval = $(this).attr("data-id");
	var id=$(this).attr("id");
	var tr = $(this).closest("tr");
	
	var value = $(this).is(':checked');	
	$("#statusuwsval").val(value);
	$("#statusuwsId").val(id);
	var statusid = $("#statusuwsId").val();
	var statusVal = $('#statusuwsval').val();
	if((statusid != null && statusid != "" && statusid != "undefined") && (statusVal != null && statusVal != "" && statusVal != "undefined")){
		 $("#delformsumit").submit();
		 var status = "";
		 if(statusVal == true)
		    status = "Activated Successfully...!";
		 else status = "Status In-active Process Done.";
		 $.alert({
			    title: 'Alert!',
			    content: status,
			});
		 $(tr).remove();
	}
}); 



</script>



