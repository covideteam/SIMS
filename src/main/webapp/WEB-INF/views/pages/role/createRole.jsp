<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Creation</title>
</head>
<body oncontextmenu="return false;">

	 
	   
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.roloCrTitle"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>

        <!-- <div style="margin-left: 10%; width: 65%;"> -->
        	<c:url value="/userRole/saveRole" var="saveRole"></c:url>
        	<form:form action="${saveRole}" method="POST" id="saveForm" modelAttribute="roleMaster">
        	<input type="hidden" name="roleval" id="roleval" value="${userRole}">
        		<div style="width: 60%; margin-left: 20%;">
        	      <div class="form-group row">
        			<label for="role" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.roleCrName"></spring:message></label>
        			<div class="col-sm-5">
       					<form:input path="role" id="role" onchange="roleNameValidation('role', 'roleNameMsg')" autocomplete="false" cssClass="form-control validate"/>
        				<div id="roleNameMsg" style="color: red;"></div>
        			</div>
        		</div>
        		
        		<div class="form-group row">
        			<label for="roleDesc" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.roleCrDesc"></spring:message></label>
        			<div class="col-sm-5">
       					<form:input path="roleDesc" id="roleDesc"  autocomplete="false" cssClass="form-control validate"/>
        				<div id="roleDescMsg" style="color: red;"></div>
        			</div>
        		</div>
        		
        		<div class="form-group row">
        			<div align="center" class="col-sm-10"><input type="button" id="submitId" value="<spring:message code="label.roleCrSubmit"></spring:message>" class="btn btn-danger btn-md"  onclick="submitForm()"></div>
        		</div> 
        	</div>
        	</form:form>
        	<!-- </div> -->
        	
        	<!-- <h4 style="text-align:center;padding-top:20px;padding-right:15%">Role List</h4> -->
        	
       <table  class="table table-striped table-bordered">
		<tr>
			<th style="text-align: center;padding-right:15%"><spring:message code="label.delrls"></spring:message></th>
		</tr>
        </table>
        	
        	 <table id="createRole" class="table table-striped table-bordered nowrap"  >
       
        
        <thead>
        <tr>
        <th><spring:message code="label.roloName"></spring:message></th>
        <th><spring:message code="label.roleCrDesc"></spring:message></th>
        <th><spring:message code="label.roloCreatedBy"></spring:message></th>
        <th><spring:message code="label.roloCreatedOn"></spring:message></th>
        <c:if test="${userRole eq 'SUPERADMIN'}">
         <th><spring:message code="label.roloAction"></spring:message></th>
        </c:if>
        </tr>
        </thead>
        <tbody>
	        <c:forEach items="${allRoles}" var="role">
		        <tr>
		        <td>${role.role}</td>
		        <td>${role.roleDesc}</td>
		        <td>${role.createdBy.username}</td>
		        <td><fmt:formatDate value="${role.createdOn}" pattern="${dateFormat}" /></td>
		        <c:if test="${userRole eq 'SUPERADMIN'}">
		       <td> <a href='#' onclick="confirmalertFunction('${role.id}','${role.role}')"><i class='fa fa-trash-o' style='color:#23175e;font-size:15px;padding-left:10px'></i></a></td>
		        </c:if>
		        </tr>
	        </c:forEach>
        </tbody>
        
        </table>
        
         </div>
            </div>
              </div>
                 </div>
        
           <c:url value="/userRole/roleDisable" var="saveData"></c:url>
         <form:form action="${saveData}" method="POST" id="roledisable">
        	         <input type="hidden" name="rolevalDis" id="rolevalDis">
         </form:form>
        

   <script type="text/javascript">
   $('#submitId').prop("disabled", false);
   var rolenameFlag = false;
   var roledeFlag = false;
   
   /* function roleDescValidation(id, message){
  		var value = $('#'+id).val();
  		if(value == null || value == "" || value == "undefined"){
  			// $('#'+message).html("Required Field"); 
  			$('#'+message).html('${validationText}');
  			roledeFlag = false;
  		}else{
  			$('#'+message).html("");
  			roledeFlag = true;
  		}
  		return roledeFlag;
  	}  */
   
   
   $(function(){
       $('#roleDesc').keydown(function(er){
                	
                if(er.altKey||er.ctrlKey)
                {
                er.preventDefault();
                }
                else { 
                	var key=er.keyCode;
                    if(!((key==8)||(key==9)||(key==46)||(key>=65 && key<=90)||(key==190)))
                    {
                         er.preventDefault();
                         //$("#userNameMsg").html("Please Enter only Alphabets and dot(.) only");
                         //alert("please enter only alphabets")
                    }
                }
             }); 
        });
   function roleNameValidation(id, message){
  		var value = $('#'+id).val();
  		//alert(value);
  		if(value == null || value == "" || value == "undefined"){
  			/* $('#'+message).html("Required Field"); */
  			/* $('#'+message).html('${validationText}'); */
  			$('#'+id).attr("data-errormessage","Required Field..!");
   		    $('#'+id).attr("data-isvalid","false");
  			rolenameFlag = false;
  		}else{
  			var url = mainUrl+"/userRole/checkRoleDuplication/"+value;
  			var result = synchronousAjaxCall(url);
			if(result == "" && result != "undefined"){
	   			/* $('#'+message).html(""); */
	   			$('#'+id).attr("data-isvalid","true");
	   			rolenameFlag = true;
			}else{
				if(result == "undefined"){
					$('#'+id).attr("data-errormessage","Internal Issue Please Check..!");
		   		    $('#'+id).attr("data-isvalid","false");
					/* $('#'+message).html("Internal Issue Please Check."); */
					rolenameFlag = false;
				}else{
					$('#'+id).attr("data-errormessage",result);
		   		    $('#'+id).attr("data-isvalid","false");
					/* $('#'+message).html(result); */
					rolenameFlag = false;
				}
			}
  		}
  		return rolenameFlag;
  	}
   
   function confirmalertFunction(roleval,role){
		//alert(value);
		if(roleval!=""){
			debugger;
			var url = mainUrl+"/userRole/checkUsersIsAvelableInThisRole/"+roleval;
  			var result = synchronousAjaxCall(url);
  			if(result=="Yes"){
			$.confirm({
			    title: 'Alert!',
			    //icon: 'glyphicon glyphicon-heart',
			    type: 'red',
			    typeAnimated: true,
			    boxWidth: '30%',
			    content: ' Are you sure you want to close this role',
			    useBootstrap: false,
			    buttons: {
			       Ok: function(){
				    	 $('#rolevalDis').val(roleval);
		   				$('#roledisable').submit(); 
		   			/*  $.alert({
						    title: 'Alert!',
						    icon: 'fa fa-warning',
						    useBootstrap: false,
						    boxWidth: '30%',
						    content: ' '+user+' Role deleted!',
						}); */
		   				
			       },Cancel: function () {
			    	   
			        }
					    
			    }
			});
  			}else{
  				$.confirm("Role can't be deactivated. Please change user(s) with same role to another role before deactivation");

  			}
			}
		}
   
   function submitForm(){
	   $('#submitId').prop("disabled", true);
	   
	   var roleNameFlag = roleNameValidation('role', 'roleNameMsg');
	   
	   /* var roleDescFlag = roleDescValidation('roleDesc', 'roleDescMsg'); */
	   
	   
//		alert("Flag values :"+userIdFlag+"::"+nameFlag +"::"+ pswdFlag +"::"+ emailFlag +"::"+ roleFlag +"::"+ deptFlag);
		if(validateElements($("#saveForm"))){
			$(".loader").show(); 
			$('#saveForm').submit();
		}else{
			$('#submitId').prop("disabled", false);
		}
	}
   </script>
   
<script type="text/javascript">
    $(document).ready(function() {
        var table = $('#createRole').DataTable({
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
    
    $(function(){
        $('#role').keydown(function(er){
                 	
                 if(er.altKey||er.ctrlKey)
                 {
                 er.preventDefault();
                 }
                 else
                 { 
                 var key=er.keyCode;
                 if(!((key==8)||(key==9)||(key==32)||(key==46)||(key>=65 && key<=90)||(key==190)))
                     {
                          er.preventDefault();
                          //$("#userNameMsg").html("Please Enter only Alphabets and dot(.) only");
                          //alert("please enter only alphabets")
                     }else{
                     	//$("#userNameMsg").html("");
                     }
                 }
              }); 
         });
    </script>
  
</body>
</html>