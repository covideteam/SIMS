<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Creation</title>
<style>
/* Style all input fields */
/*  input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
} */

/* Style the submit button */
input[type=submit] {
  background-color: #04AA6D;
  color: white;
}

/* Style the container for inputs */
.container {
  background-color: #f1f1f1;
  /* padding: 20px; */
}

/* The message box is shown when the user clicks on the password field */
#passwordMsg {
  display:none;
 /*  background: #f1f1f1;
  color: #000; */
  position: relative;
  padding: 20px;
  margin-top: 10px;
}

#passwordMsg p {
  padding: 0px;
  font-size: 13px;
  margin-top: 0;
  margin-bottom: 5px;
}

/* Add a green text color and a checkmark when the requirements are right */
.valid {
  color: green;
}

.valid:before {
  position: relative;
  /* left: -35px; */
  /* content: "✔"; */
}

/* Add a red text color and an "x" when the requirements are wrong */
.invalid {
  color: red;
}

.invalid:before {
  position: relative;
  left: -35px;
 /*  content: "✖"; */
}
  
</style> 
</head>
<body oncontextmenu="return false;">
	   <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.userCrTitle"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
<!--         <div style="margin-left: 25%; width: 65%;"> -->
        	<c:url value="/user/saveUser" var="saveUser"></c:url>
        	<form:form action="${saveUser}" method="POST" id="saveForm" modelAttribute="user">
        	
        	
        	<div style="width: 60%; margin-left: 20%;">
             <!-- class="table table-hover table-hover" -->
                <div class="form-group row">
                    <label for="userName"  class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCrName"></spring:message></label>
                    <div class="col-sm-5">
                            <form:input type="text" minlength="1" maxlength="20" path="username" id="userName" onblur="userNameValidation('userName', 'userNameMsg')" autocomplete="off" cssClass="form-control validate" />
                            <!-- <div id="userNameMsg" style="color: red;"></div> -->
                    </div>
                </div>
                <div class="form-group row">
                    <label for="password" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCrPassword"></spring:message></label>
                    <div class="col-sm-5">
                            <form:password path="password" id="psw" onblur="userPasswordValidation('psw','passwordMsg')" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" autocomplete="off" cssClass="form-control validate" />
                         <div id="passwordMsg" class="psw"  style="color:red;top: 20%;padding:0px;width:120%">
				        	 <ul style="float:left;margin-top:10px;">
				                <li id="letter" class="invalid"><b>A Lowercase letter</b></li>
				                <li id="capital" class="invalid"><b>An Uppercase letter</b></li>
				                </ul>
				                <ul style="float:right;margin-top:10px;margin-right: 15%;">
				                <li id="number" class="invalid"><b>A Number .</b></li>
				                <li id="length" class="invalid"><b>Minimum 8 characters.</b></li>
            				</ul>
			            </div>
                    </div>
                </div>
                 <div class="form-group row">
                    <label for="ConfirmPassword" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCNFPassword"></spring:message></label>
                    <div class="col-sm-5">
                            <input type="password" id="confirmPassword" onblur="confirmPasswordValidation('confirmPassword','cnfpasswordMsg')" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" autocomplete="off" Class="form-control validate">
                            <div id="cnfpasswordMsg" style="color:red;"></div>
                    </div>
               </div>
                <div class="form-group row">
                    <label for="fullName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCrFullName"></spring:message></label>
                    <div class="col-sm-5">
                            <form:input path="fullName" id="fullName" maxlength="70" onblur="fullNameValidation('fullName', 'fullNameMsg')" autocomplete="off" cssClass="form-control validate" />
                            <div id="fullNameMsg" style="color:red;"></div>
                    </div>
                </div>
               <div class="form-group row">
                    <label for="Email" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userEmail"></spring:message></label>
                     <div class="col-sm-5">
                            <form:input type="email" path="email" id="email" onblur="emailValidation('email', 'emailMsg')" autocomplete="off" cssClass="form-control validate" pattern=".+@globex\.com" size="30" required="required" />
                            <div id="emailMsg" style="color:red;"></div>
                     </div>
               </div>
               <div class="form-group row">
                  <label for="roleVal" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCrRole"></spring:message></label>
                    <div class="col-sm-5">
                        <form:select path="role.id" id="roleVal" onchange="roleValidation('roleVal', 'roleValMsg')" cssClass="form-control validate">
                            <option value="-1">----<spring:message code="label.sdSelect"></spring:message>----</option>
                            <c:forEach items="${rolesList}" var="roleVal">
                                <c:choose>
                                    <c:when test="${roleVal.role eq 'ADMIN' and userRole eq 'SUPERADMIN'}">
                                        <option value="${roleVal.id}">${roleVal.role}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${roleVal.role ne 'SUPERADMIN' and roleVal.role ne 'ADMIN' and userRole eq 'ADMIN'}">
                                                <option value="${roleVal.id}">${roleVal.role}</option>
                                            </c:when>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <%--
                            <form:options items="${rolesList}" itemLabel="role" itemValue="id" />
                            </c:if> --%>
                        </form:select>
                        <!-- <div id="roleValMsg" style="color: red;"></div> -->
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-10" align="center">
                    <input type="button" id="submitId" value="<spring:message code="label.roleCrSubmit"></spring:message>" class="btn btn-danger btn-md"  onclick="submitForm()">
                    </div>
                </div> 
           </div>
        	<table style="float:right;margin-top:10px;width:30%">
        	<tr><td style="width:175px" rowspan="4" >
        	
        	</td></tr>
        	</table>
        </form:form>
       <%--  <h4 style="text-align:center;padding-top:20px;"><spring:message code="label.gvListTitle"></spring:message>Users List</h4> --%>
         <div class="dt-buttons btn-group flex-wrap">
         	<a href="<c:url value="/user/exportUserList"/>" target="_blank">
         		<button class="btn btn-secondary buttons-pdf buttons-html5" tabindex="0" aria-controls="specificAudit1" type="button" style="position: absolute; top: 5px; background-color: rgb(42, 63, 84);" id="exportPdf"><span class="fa fa-file-pdf-o"></span> 	</button> 
         	</a>
         </div>
         <table  class="table table-striped table-bordered">
						<tr>
							<th style="text-align: center;"><spring:message code="label.users"></spring:message></th>
							</tr>
						</table>
         <table id="userListTab" class="table table-striped table-bordered nowrap" style="width: 100%;"  >
        <thead>
        <tr>
        <th><spring:message code="label.userCrName"></spring:message></th>
        <th><spring:message code="label.userCrFullName"></spring:message></th>
        <th><spring:message code="label.userCrRole"></spring:message></th>
        <th><spring:message code="label.createdBy"></spring:message></th>
        <th><spring:message code="label.createdOn"></spring:message></th>
        <th><spring:message code="label.user_action"></spring:message></th>
        </tr>
        </thead>
        <tbody>
	        <c:forEach items="${userList}" var="user">
		        <tr>
		        <%-- <td ><a href="#"  onclick="getUserdetailsFunction('${user.username}')">${user.username}</a></td> --%>
		        <td>${user.username}</td>
		        <td>${user.fullName}</td>
		        <td>${user.role.role}</td>
		        <td>${user.createdBy.username}</td>
		        <td><fmt:formatDate value="${user.createdOn}" pattern="${dateFormat}" /></td>
		         <td><a href='<c:url value="/user/userUpdate/${user.id}"/>'><i class='fa fa-edit'  style='color:#23175e;font-size:15px;padding-left:10px'></i></a>
		           <a href='#' onclick="confirmalertFunction('${user.id}','${user.username}')"><i class='fa fa-trash-o' style='color:#23175e;font-size:15px;padding-left:10px'></i></a>
		           <a href='<c:url value="/uad/resetPassword/${user.id}"/>'><span style='color:#23175e;font-size:8px;padding-left:40px;' title="<spring:message code="label.resetPSWTitle"></spring:message>" class="fa-passwd-reset fa-stack">
					  <i class="fa fa-undo fa-stack-2x"></i>
					  <i class="fa fa-lock fa-stack-1x"></i>
					</span></a>
		          </td>
		        </tr>
	        </c:forEach>
        </tbody>
      </table>
         <!-- Links Pop Up -->
<!-- 	      		<div id="links" class="modal fade" tabindex="-3"  role="document"> -->
 					<div id="links" class="modal modal-fullscreen" tabindex="-1" role="dialog" aria-hidden="true">
			      
			            <div class="modal-content">
<!-- 						<div class="insmod"> -->
			                <div class="modal-header" >
			                    <div class="card-title"  style="color: white; font-size: large; font-weight: bold; text-align: center;">User Search</div>
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			                </div>
			                <div class="modal-body">
			               </div>
			                <div class="modal-footer">
			                    <button type="button" class="btn btn-danger" data-dismiss="modal" id="coloseBtn">Cancel</button>
			                </div>
			            </div>
			        </div>
        </div>
       
<!-- Links Pop up -->
   </div>
  </div>
  </div>
         <c:url value="/user/userAccountDisable" var="saveData"></c:url>
         <form:form action="${saveData}" method="POST" id="acountdisable">
        	         <input type="hidden" name="useridval" id="useridval">
         </form:form>
   
   
   <script type="text/javascript">
   $(document).bind("contextmenu",function(e){
	   return false;
	     });
    var nameFlag = false;
    var pswdFlag = false;
    var roleFlag = false;
    var userIdFlag = false;
   var  emailFlag = false;
   var cNFpswdFlag = false;
   
   var password_Msg = document.getElementById("passwordMsg").textContent.trim();
    
    $(function(){
       $('#userName').keydown(function(er){
                	
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
    
   // space key=32 , DOT key = 190 , shift key = er.shiftKey
   
   
   $(function(){
       $('#fullName').keydown(function(er){
    	   
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
                    	/* $("#userNameMsg").html(""); */
                    }
                }
             }); 
        });
   
   $(function(){
       $('#psw').keydown(function(er){
    	   
                if(er.altKey||er.ctrlKey)
                {
                er.preventDefault();
                }
                
             }); 
        });
   
   $(function(){
       $('#confirmPassword').keydown(function(er){
    	   
                if(er.altKey||er.ctrlKey)
                {
                er.preventDefault();
                }
                
             }); 
        });
   
    
   	 /* function fullNameValidation(id, message){
   		 debugger;
   		var value = $('#'+id).val().trim();
   		if(value == null || value == "" || value == "undefined"){
   			// $('#'+message).html("Required Field");
   			$('#'+message).html('${validationText}');
   			nameFlag = false;
   		}else{
   			$('#'+message).html("");
   			nameFlag = true;
   		}
   		return nameFlag;
   	}  */
   	 
  
   	
   	function emailValidation(id, message){
   		 debugger;
   		var value = $('#'+id).val();
   		if(value == null || value == "" || value == "undefined"){
   			/* $('#'+message).html("Required Field"); */
   			/* $('#'+message).html('${validationText}'); */
   			$('#'+id).attr("data-errormessage","Required Field..!");
   		    $('#'+id).attr("data-isvalid","false");
   			emailFlag = false;
   		}else{
   			/* $('#'+message).html(""); */
   			$('#'+id).attr("data-isvalid","true");
   			emailFlag = true;
   		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)){
   			
   			emailFlag = true;
   			/* $('#'+message).html(""); */
   			$('#'+id).attr("data-isvalid","true");
   	       }else{
   	    	$('#'+id).attr("data-errormessage","invalid email address!.!");
   		    $('#'+id).attr("data-isvalid","false");
   	    	/* $('#'+message).html("invalid email address!"); */
   	        emailFlag = false;
   		   }
   		}
   		checkElementValidation($('#'+id));
   		return emailFlag;
   	} 
   	
   	function userNameValidation(id, message){
   		debugger;
   		var value = $('#'+id).val();
   		if(value == null || value == "" || value == "undefined"){
   			/* $('#'+message).html("Required Field"); */
   			/* $('#'+message).html('${validationText}'); */
   			$('#'+id).attr("data-errormessage","Required Field..!");
   		    $('#'+id).attr("data-isvalid","false");
   			userIdFlag = false;
   		}else{
   			var url = mainUrl+"/user/checkUserIdDuplication/"+value;
   			var result = synchronousAjaxCall(url);
			if(result == "" && result != "undefined"){
	   			/* $('#'+message).html(""); */
	   			$('#'+id).attr("data-isvalid","true");
	   			userIdFlag = true;
			}else{
				if(result == "undefined"){
					$('#'+id).attr("data-errormessage","Internal Issue Please Check..!");
		   		    $('#'+id).attr("data-isvalid","false");
					/* $('#'+message).html("Internal Issue Please Check."); */
	   				userIdFlag = false;
				}else{
					$('#'+id).attr("data-errormessage",result);
		   		    $('#'+id).attr("data-isvalid","false");
					/* $('#'+message).html(result); */
	   				userIdFlag = false;
				}
			}
   		}
   		checkElementValidation($('#'+id));
   		return userIdFlag;
   	}
   	
     	
   	
   	function userPasswordValidation(id, message){
   		debugger;
   		var value = $('#'+id).val();
   		var lowerCaseLetters = /[a-z]/g;
   		var upperCaseLetters = /[A-Z]/g;
   		var numbers = /[0-9]/g;
   		
   		if(value == null || value == "" || value == "undefined"){
   			debugger;
   			//$('#'+message).html("Required Field");
   			/* $('#'+id).attr("data-errormessage","Required Field..!"); */
		    $('#'+id).attr("data-isvalid","false");
   			/* $('#'+message).css('display', 'block'); */
   			/* $('#'+message).style.display = "block"; */
   			pswdFlag = false;
   		}else if((value.length >= 8) && (value.match(lowerCaseLetters)) && (value.match(upperCaseLetters)) && (value.match(numbers)) ){
   			/* $('#'+message).html(""); */
   			
   			$('#'+id).attr("data-isvalid","true");
   			$(".psw").fadeOut('slow');
   			pswdFlag = true;
   		}else{
   			/* $('#'+id).attr("data-errormessage",password_Msg); */
		    /* $('#'+id).attr("data-isvalid","false"); */
   			/* $('#'+message).css('display', 'block'); */
   			pswdFlag = false;
   		}
   		checkElementValidation($('#'+id));
   		return pswdFlag;
   	}
   	
   	function confirmPasswordValidation(id, message){
   		debugger;
   		var value = $('#'+id).val();
   		var lowerCaseLetters = /[a-z]/g;
   		var upperCaseLetters = /[A-Z]/g;
   		var numbers = /[0-9]/g;
   		
   		var password = $("#psw").val();
   		
   		if(value == null || value == "" || value == "undefined"){
   			debugger;
   			/* $('#'+message).html('${validationText}'); */
   			
   			$('#'+id).attr("data-errormessage","Required Field..!");
		    $('#'+id).attr("data-isvalid","false");
   			cNFpswdFlag = false;
   			
   		}else if(password==value){
   			$('#'+id).attr("data-isvalid","true");
   			/* $('#'+message).html(""); */
   			cNFpswdFlag = true;
   			
   		}else{
   			$('#'+id).attr("data-errormessage","Confirm Password Is Not Matched..!");
		    $('#'+id).attr("data-isvalid","false");
   			/* $('#'+message).html("Confirm Password Is Not Matched"); */
   			cNFpswdFlag = false;
   		}
   		checkElementValidation($('#'+id));
   		return cNFpswdFlag;
   			
   			/* if((value.length >= 8) && (value.match(lowerCaseLetters)) && (value.match(upperCaseLetters)) && (value.match(numbers)) ){
   			$('#'+message).html("");
   			pswdFlag = true;
   		}else{
   			$('#'+message).html("Not Validated!");
   			cNFpswdFlag = false;
   		}	 */
   		//return cNFpswdFlag;
   	}
   	
	/* function roleValidation(id, message){
		debugger;
   		var value = $('#'+id).val();
   		if(value == null || value == "-1" || value == "undefined"){
   		//$('#'+message).html("Required Field"); 
   			$('#'+message).html('${validationText}');
   			roleFlag = false;
   		}else{
   			$('#'+message).html("");
   			roleFlag = true;
   		}
   		return roleFlag;
   	} */
	
	   
	   /* document.getElementById("passwordMsg").style.display = "none"; */
   	   
	   var myInput = document.getElementById("psw");
	   var letter = document.getElementById("letter");
	   var capital = document.getElementById("capital");
	   var number = document.getElementById("number");
	   var length = document.getElementById("length");
	  
	   // When the user clicks on the password field, show the message box
	   myInput.onfocus = function () {
		   $(".psw").fadeOut('slow');
		   
		  /*  $(".error").show(); */
		   /* $("#passwordMsg").find('.error').css('display','block'); */
	      /* document.getElementById("passwordMsg").style.display = "block"; */ 
	       /* $(".psw").parent().find('.error').css('display','none');
		   $(".psw").fadeIn('slow'); */
	   }
	   
	   // When the user clicks outside of the password field, hide the message box
	   myInput.onblur = function () {	
		   $(".psw").fadeOut('slow');
		   
		  /*  $(".error").hide(); */
		   /* $("#passwordMsg").find('.error').css('display','none'); */
	      /* document.getElementById("passwordMsg").style.display = "none"; */
	   } 
         // When the user starts to type something inside the password field
	
	   /* var pswdstrFlag = false; */
   	   myInput.onkeyup = function() {
   		
   		/* $("#passwordMsg").find('.error').css('display','block'); */
   		$(".psw").parent().find('.error').css('display','none');
   		$(".psw").fadeIn('slow');
		   debugger;
	     // Validate lowercase letters
	     var lowerCaseLetters = /[a-z]/g;
	     if(myInput.value.match(lowerCaseLetters)) {  
	       letter.classList.remove("invalid");
	       letter.classList.add("valid");
	     } else {
	       letter.classList.remove("valid");
	       letter.classList.add("invalid");
	       
	       
	     }
	     
	     // Validate capital letters
	     var upperCaseLetters = /[A-Z]/g;
	     if(myInput.value.match(upperCaseLetters)) {  
	       capital.classList.remove("invalid");
	       capital.classList.add("valid");
	      
	      
	     } else {
	       capital.classList.remove("valid");
	       capital.classList.add("invalid");
	       
	      
	     }

	     // Validate numbers
	     var numbers = /[0-9]/g;
	     if(myInput.value.match(numbers)) {  
	       number.classList.remove("invalid");
	       number.classList.add("valid");
	       
	       
	     } else {
	       number.classList.remove("valid");
	       number.classList.add("invalid");
	       
	      
	     }
	     
	     // Validate length
	     if(myInput.value.length >= 8) {
	       length.classList.remove("invalid");
	       length.classList.add("valid");
	       
	      
	     } else {
	       length.classList.remove("valid");
	       length.classList.add("invalid");
	      
	      
	     }
	     
	     
// 	   }
	
}

	$("#psw").on('change',function(){
		if(($(".valid").length==4)&&($("#psw").val()!="")){
			$('#psw').attr("data-isvalid","true");
		 }else{
			 $('#psw').attr("data-errormessage",password_Msg);
			 $('#psw').attr("data-isvalid","false");
		 }
		 checkElementValidation($('#psw'));
	});
	
	function submitForm(){
		debugger;
				
		var userFlag =  userNameValidation('userName', 'userNameMsg');
		var userpasswordFlag = userPasswordValidation('psw','passwordMsg');
		/* var fullnameFlag = fullNameValidation('fullName', 'fullNameMsg'); */
		/* var roleFlag = roleValidation('roleVal', 'roleValMsg'); */
		var emailFlag = emailValidation('email', 'emailMsg');
		var cnfPassword = confirmPasswordValidation('confirmPassword','cnfpasswordMsg');
	
		
  // 		alert("Flag values :"+userIdFlag+"::"+nameFlag +"::"+ pswdFlag +"::"+ emailFlag +"::"+ roleFlag +"::"+ deptFlag);
   
       // alert(  "pswdFlag :"+ pswdFlag);
		
        if(validateElements($("#saveForm"))){
			
			$(".loader").show();
			$('#saveForm').submit();
			/* validatePassword(); */
			
		}
	}
	
	function getUserdetailsFunction(userName) {
		var url = mainUrl + "/logindata/userLogInfoPageOne/" + userName;
		var result = synchronousAjaxCall(url);
		var body = result;
	    $("#links .modal-body").html(body);
   		$("#links").modal("show");
	}
	
	
   </script>
   <script type="text/javascript">
   /*  $(document).ready(function() {
    	$('#submitId').removeAttr('disabled');
        var table = $('#userCreation').DataTable({
            searchBuilder: true
        });
//         table.searchBuilder.container().prependTo(table.table().container());
    }); */
    </script>
    
    <script type="text/javascript">
    
	    $(document).ready(function() {
	    	 var table = $('#userListTab').DataTable({
	             searchBuilder: true,
	             scrollX: true,
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
	    	 
	         /*$('#userListTab')
	         .on( 'init.dt', function () {
	           $(".buttons-pdf").css({ 'position':'absolute','top':'5px','background-color':'#2A3F54' });
	           var span = $(".buttons-pdf").find('span');
	          $(span).addClass("fa fa-file-pdf-o");
	        })
	        .dataTable({
	        	searchBuilder: true,
	            dom: 'Bfrtip',
	            responsive: true,
	            pageLength: 10,
	            lengthMenu: [0, 5, 10, 20, 50, 100, 200, 500],

	            /* buttons: [
	                'pdf'
	            ] 

	        });*/
	    });
	</script>
 	<script type="text/javascript">
	function confirmalertFunction(value,user){
		//alert(value);
		if(value!=""){
			
			$.confirm({
			    title: 'Alert!',
			    //icon: 'glyphicon glyphicon-heart',
			    icon: 'fa fa-warning',
			    type: 'red',
			    typeAnimated: true,
			    boxWidth: '30%',
			    content: ' Are you sure you want to close this account',
			    useBootstrap: false,
			    buttons: {
			       Ok: function(){
				    	 $('#useridval').val(value);
		   				 $('#acountdisable').submit(); 
		   			 /* $.alert({
						    title: 'Alert!',
						    icon: 'fa fa-warning',
						    useBootstrap: false,
						    boxWidth: '30%',
						    content: ' '+user+' Account deleted!',
						}); */
		   				
			       },Cancel: function () {
			    	   
			        }
					    
			    }
			});
			
			}
		}
		
	</script> 
	</body>
	
</html>