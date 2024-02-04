<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
<link rel="stylesheet" href="<c:url value="/static/conformalert/jquery-confirm.min.css"/>">
<script src="<c:url value="/static/conformalert/jquery-confirm.min2.js"/>"></script>
<link rel="stylesheet" href='<c:url value="/static/breadCrumbs/breadCrumbs.css" />'/>
<head>
 

<script type="text/javascript">
$(document).ready(function() {
    // show the alert
    setTimeout(function() {
        $("#success").hide();
    }, 2000);
    setTimeout(function() {
        $("#failed").hide();
    }, 2000);
});


</script>
<style>
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
<body>
	<c:if test="${messageOfPage != null}" >
	<div class="card" id="success" style="background-color: rgba(0, 128, 0, 0.9); border-radius: 20px; border: 3px solid #f3fff0; width: 50%; margin-left: 25%;">
		<h5 class="card-header info-color white-text text-center py-2">
	       <strong style="color: white;">${messageOfPage}</strong>
	     </h5>
	 </div>
</c:if>
<c:if test="${errorMsgOfPage != null}">
 <div class="card" id="failed" style="background-color: rgba(255, 0, 0, 0.9); border-radius: 20px; border: 3px solid #f3fff0; width: 50%; margin-left: 25%;">
		<h5 class="card-header info-color white-text text-center py-2">
	       <strong style="color: white;" >${errorMsgOfPage}</strong>
	     </h5>
</div>
</c:if>

	  <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.changePSWTitle"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
				
				
		<%-- <div class="container">  
		    <ol class="breadcrumb arr-bread">  
		        <li> <a href = "<c:url value="/dashboard/"/>" > DashBoard </a> </li>  
		        <li> <a href = "<c:url value="/administration/"/>"> Administration </a> </li>  
		        <li class = "active"><span>Change Password</span></li>  
		    </ol>  
		</div>   --%>
		<c:url value="/uad/savechangepwd" var="changePwdUrl" />
		<form:form action="${changePwdUrl}" method="get" id="myform" autocomplete="off">
			<input type="hidden" name="tranPwd" id="tranPwd">
			<c:choose>
				<c:when test="${not empty user.tranPassword}">
					<input type="hidden" name="trnPass" id="trnPass" value="Yes">
				</c:when>
				<c:otherwise>
					<input type="hidden" name="trnPass" id="trnPass" value="No">
				</c:otherwise>
			</c:choose>
			<div style="margin-left:12%;">
			<div style="width: 40%;float:left;margin-left:24%;">
				<div class="form-group row">
<%-- 				<label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userName"></spring:message></label>
 --%>					<input type="hidden" value="${user.id}" name="userId" id="userId" class="form-control"/>
						<%-- <div class="col-sm-5">
							<select name="userId" id="userId" onchange="userIdValidation('userId', 'userIdMsg')" class="form-control">
								<option value="-1">-----Select-----</option>
								<option value="${user.id}">${user.username}</option>
								<c:forEach items="${userList}" var="us">
									 <option value="${us.id}">${us.username}</option>
								</c:forEach>
							</select>
						   <div id="userIdMsg" style="color: red;"></div>
					   </div> --%>
				</div>
				<div class="form-group row">
					<label for="oldPwd" class="col-sm-5 col-form-label" style="color: #212529;"><spring:message code="label.oldPSW"></spring:message></label>
					<div class="col-sm-7">
						<div><input type="password" name="oldPwd" id="oldPwd" onblur="oldPwdValidation('oldPwd', 'oldPwdMsg')" autocomplete="off" class="form-control"></div>
						<div id="oldPwdMsg" style="color: red;"></div>
					</div>
				</div>
				<div class="form-group row">
					<label for="newPwd" class="col-sm-5 col-form-label" style="color: #212529;"><spring:message code="label.newPSW"></spring:message></label>
					<div class="col-sm-7">
						<div><input type="password" name="newPwd" id="newPwd" onblur="newPwdValidation('newPwd', 'passwordMsg')" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" autocomplete="off" class="form-control"></div>
						 <div id="newPwdMsg" style="color: red;"></div>
					</div>
				</div>
				<div class="form-group row">
					<label for="newconPwd" class="col-sm-5 col-form-label" style="color: #212529;"><spring:message code="label.reNewPSW"></spring:message></label>
					<div class="col-sm-7">
						<div><input type="password" name="newconPwd" id="newconPwd" onblur="newconPwdValidation('newconPwd', 'newconPwdMsg')" autocomplete="off" class="form-control"></div>
						<div id="newconPwdMsg" style="color: red;"></div>
					</div>
				</div>
				<%-- <div class="form-group row">
					<label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.reason"></spring:message></label>
					<div class="col-sm-5">
						<div><input type="text" name="reason" id="reason" onblur="reasonValidation('reason', 'reasonMsg')" class="form-control"></div>
						<div id="reasonMsg" style="color: red;"></div>
					</div>
				</div> --%>
				<div class="form-group row">
					<div class="col-sm-10" align="center">
						<input type="button" value="Change" id="changeButton" class="btn btn-danger btn-md" onclick="submtFomrm()">
					</div>
				</div>
			</div>
			<table style="float:right;margin-right:6%;width:30%">
        	<tr><td style="width:175px" rowspan="4" >
        	<div id="passwordMsg" style="color: red;">
        	<p id="letter" class="invalid"><b><spring:message code="label.lowerCase"></spring:message></b></p>
        	<p id="capital" class="invalid"><b><spring:message code="label.upperCase"></spring:message></b></p>
        	<p id="number" class="invalid"><b><spring:message code="label.number"></spring:message></b></p>
        	<p id="length" class="invalid"><b><spring:message code="label.minCharacters"></spring:message></b></p>
                  </div>
        	</td></tr>
        	</table>
        	</div>
		</form:form>
   </div>
   <%-- <c:url value="/administration/sidemenuList" var="sidemenuList" />
	     	<form:form action="${sidemenuList}" method="GET" id="form">
			<table>
			<tr>
			<td>MenuList</td>
			<td>
			<select>
			<option>---Select--</option>
			<c:forEach items="${pojo.appMenu}" var="appList">
			<option  value="${appList.name}" id="appMenu">${appList.name}</option>
			</c:forEach>
			</select>
			</td>
			</tr>
			<tr>
			<td>SideMenuList</td>
			<td>
			<select>
			<option>---Select--</option>
			<c:forEach items="${pojo.sideMenu}" var="sideMenuList">
			<option  value="${sideMenuList.name}" id="sideMenu">${sideMenuList.name}</option>
			</c:forEach>
			</select>
			</td>
			</tr>
			<tr><td><input type="submit" value="submit"></td></tr>
			</table>
			</form:form> --%>
   </div>
  </div>
  </div>
	
<script type="text/javascript">

 $(function(){
    $('#newPwd').keydown(function(er){
 	   
             if(er.altKey||er.ctrlKey)
             {
             er.preventDefault();
             }
             
          }); 
     });
     
 $(function(){
	    $('#newconPwd').keydown(function(er){
	 	   
	             if(er.altKey||er.ctrlKey)
	             {
	             er.preventDefault();
	             }
	             
	          }); 
	     });
     
 document.getElementById("passwordMsg").style.display = "none";

var myInput = document.getElementById("newPwd");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

// When the user clicks on the password field, show the message box
myInput.onfocus = function () {
  document.getElementById("passwordMsg").style.display = "block";
}

// When the user clicks outside of the password field, hide the message box
/*  myInput.onblur = function () {	
  document.getElementById("passwordMsg").style.display = "none";
} */  
  // When the user starts to type something inside the password field

/* var pswdstrFlag = false; */
   myInput.onkeyup = function() {
	  
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

}

	/* function userIdValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value =="-1" || value == "undefined"){
			$('#'+messageId).html("Please Select User.");
			flag = false;
		}else{
			$('#'+messageId).html("");
			flag = true;
		}
		return flag;
	} */
	function oldPwdValidation(id, messageId){
		debugger;
		var flag = false;
		var value = $('#'+id).val();
		if(value =="" || value == "undefined"){
			$('#'+messageId).html("Please Enter Old Password.");
			flag = false;
		}else{
			var userId = $('#userId').val();
			if(userId == "-1"){
				$('#'+id).val("");
				flag = false;
			}else{
				var url = mainUrl+"/uad/oldPwdCheck/"+userId+"/"+value;
				var result= synchronousAjaxCall(url);
				if(result == "true"){
					$('#'+messageId).html("");
					flag = true;
				}else{
					$('#'+messageId).html("Invalid Password.");
					flag = false;
				}
			}
		}
		return flag;
	}
	
	function newPwdValidation(id, messageId){
		debugger;
		var flag = false;
		var value = $('#'+id).val();
		//document.getElementById("passwordMsg").style.display = "none";
		$('#passwordMsg').css('display','block');
		//alert(value);
		var lowerCaseLetters = /[a-z]/g;
   		var upperCaseLetters = /[A-Z]/g;
   		var numbers = /[0-9]/g;
		var oldPSW =  $('#oldPwd').val();
		var newconPwd = $('#newconPwd').val();
		if(value == null || value == "" || value == "undefined"){
   			debugger;
   			
   			$('#passwordMsg').css('display', 'block');
   			$('#newPwdMsg').html("");
   			/* $('#'+message).style.display = "block"; */
   			flag = false;
   			
   		}else if((value.length >= 8) && (value.match(lowerCaseLetters)) && (value.match(upperCaseLetters)) && (value.match(numbers)) ){
   			$('#'+messageId).css('display', 'block');
   			pswdFlag = true;
		   		 if(oldPSW != value){
		   			$('#passwordMsg').css('display', 'block');
		   			flag = true;
			   		   if(value == newconPwd||newconPwd ==""){
			   			$('#newPwdMsg').html("");
			   			flag = true;
			   		  }	else{
			   			$('#newPwdMsg').html("New password and Confirm password must be same!..");
			   			$('#passwordMsg').css('display', 'block');
			   			flag = false;
			   		  }
		   		}else{
		   			$('#newPwdMsg').html("Old password and New password must not be same!");
		   			$('#passwordMsg').css('display', 'block');
		   			flag = false;
		   		}
   		}else{
   			$('#passwordMsg').css('display', 'block');
   			flag = false;
   		}
		 
		return flag;
	}
	
	function newconPwdValidation(id, messageId){
		debugger;
		var flag = false;
		var newPwd = $('#newPwd').val();
		var value = $('#'+id).val();
		if(value =="" || value == "undefined"){
			$('#'+messageId).html("Please Enter Confirm Password.");
			flag = false;
		}else if(newPwd == value){
			$('#'+messageId).html("");
			//$('#newPwdMsg').html("");
			var oldPSW =  $('#oldPwd').val();
			 if(oldPSW != newPwd){
				 $('#newPwdMsg').html("");
		   			flag = true;
			 }else{
				 $('#newPwdMsg').html("Old password and New password must not be same!");
				 $('#passwordMsg').css('display', 'block');
		   			flag = false;
			 }
			
			}else {
				$('#'+messageId).html("Confirm password and New password must be same!..");
				$('#passwordMsg').css('display', 'block');
				flag = false;
			}
		return flag;
	}
	/* function reasonValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value =="" || value == "undefined"){
			$('#'+messageId).html("Please Enter Reason.");
			flag = false;
		}else{
			$('#'+messageId).html("");
			flag = true;
		}
		return flag;
	} */
	function transPwdValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value =="" || value == "undefined"){
			/* $('#'+messageId).html("Required Field."); */
			$('#'+messageId).html('${validationText}');
			flag = false;
		}else{
			$('#'+messageId).html("");
			flag = true;
		}
		return flag;
	}
	</script>
	<script type="text/javascript">
		 function submtFomrm(){
			 debugger;
			 /* var Pswflag = newPwdValidation('newPwd', 'passwordMsg'); */
			 $('#submitId').removeAttr('disabled');
			var trnVal ="No";
			if(trnVal == "Yes"){
			 $('#tranPwd').val("");
			 var id ="'transPwd"+"'";
			 var messageId = "'transPwdMsg"+"'";
			 $.confirm({
				    title: 'Confirmation',
				    content: 'Do You Want to Change Transaction Password',
				    type: 'orange',
				    typeAnimated: true,
				    buttons: {
				        tryAgain: {
				            text: 'OK',
				            btnClass: 'btn-red',
				            action: function(){
				            	$.confirm({
				            	    title: '',
				            	    content: '<table class="table table-bordered table-strapied"><tr>'+
				            	    			'<td>Enter Transaction Password :</td>'+
				            	    			'<td><input type="text" name="transPwd" id="transPwd" onblur="transPwdValidation('+id+', '+messageId+')" class ="form-control"><div id="transPwdMsg" style="color:red;"></div></td>'+
				            	    			'</tr></table>',
				            	    type: 'orange',
				            	    typeAnimated: true,
				            	    buttons: {
				            	        tryAgain: {
				            	            text: 'Submit',
				            	            btnClass: 'btn-red',
				            	            action: function(){
				            	            	var transVal = $('#transPwd').val();
				            	            	var url = mainUrl+"/administration/passwordMatchWithPasswordSettings/"+transVal;
				            	    			var result= synchronousAjaxCall(url);
				            	    			if(result =="Matched"){
				            	    				$('#transPwdMsg').html("");
				            	    				$('#tranPwd').val(transVal);
				            	    				//var userId = userIdValidation('userId', 'userIdMsg');
				            	    				var oldPwd = oldPwdValidation('oldPwd', 'oldPwdMsg');
				            	    				var newPwd = newPwdValidation('newPwd', 'passwordMsg');
				            	    				var newConPwd = newconPwdValidation('newconPwd', 'newconPwdMsg');
				            	    				//var reason = reasonValidation('reason', 'reasonMsg');
				            	    			//	alert("flags are : "+userId+"::"+oldPwd+"::"+newPwd+"::"+newConPwd+"::"+reason);
				            	    				if(userId && oldPwd && newPwd && newConPwd && reason)
				            	    					$('#myform').submit();
				            	    				    $(".loader").show();
				            	    			}else{
				            	    				$('#transPwdMsg').html(result);
				            	    				$('#tranPwd').val("");
				            	    				$('#submitId').removeAttr('disabled');
				            	    				return false;
				            	    			}
				            	            }
				            	        },
				            	        close: function () {
				            	        	$('#tranPwd').val("0");
				            	        	$('#tranPwd').val("0");
				           				// var userId = userIdValidation('userId', 'userIdMsg');
				           				 var oldPwd = oldPwdValidation('oldPwd', 'oldPwdMsg');
				           				 var newPwd = newPwdValidation('newPwd', 'passwordMsg');
				           				 var newConPwd = newconPwdValidation('newconPwd', 'newconPwdMsg');
				           				// var reason = reasonValidation('reason', 'reasonMsg');
				           				// alert("flags are : "+userId+"::"+oldPwd+"::"+newPwd+"::"+newConPwd+"::"+reason);
				           				 if(userId && oldPwd && newPwd && newConPwd && reason){
				           					$('#myform').submit();
				           				    $(".loader").show();
				           				 }
				           				 else
				           				    $('#submitId').removeAttr('disabled');
				            	        }
				            	    }
				            	});
				            }
				        },
				        close: function () {
				        	$('#tranPwd').val("0");
				        	$('#tranPwd').val("0");
							// var userId = userIdValidation('userId', 'userIdMsg');
							 var oldPwd = oldPwdValidation('oldPwd', 'oldPwdMsg');
							 var newPwd = newPwdValidation('newPwd', 'passwordMsg');
							 var newConPwd = newconPwdValidation('newconPwd', 'newconPwdMsg');
							// var reason = reasonValidation('reason', 'reasonMsg');
							// alert("flags are : "+userId+"::"+oldPwd+"::"+newPwd+"::"+newConPwd+"::"+reason);
							 if(userId && oldPwd && newPwd && newConPwd && reason){
								$('#myform').submit();
							    $(".loader").show();
							 }
							    else
							    	$('#submitId').removeAttr('disabled');
            	       }
				    }
				});
			 }else{
				 $('#tranPwd').val("0");
				 //var userId = userIdValidation('userId', 'userIdMsg');
				 var oldPwd = oldPwdValidation('oldPwd', 'oldPwdMsg');
				 var newPwd = newPwdValidation('newPwd', 'passwordMsg');
				 var newConPwd = newconPwdValidation('newconPwd', 'newconPwdMsg');
				// var reason = reasonValidation('reason', 'reasonMsg');
				// alert("flags are : "+userId+"::"+oldPwd+"::"+newPwd+"::"+newConPwd+"::"+reason);
				 if(oldPwd && newPwd && newConPwd){
					$('#myform').submit();
				    $(".loader").show();
				 }
				  else
					  $('#submitId').removeAttr('disabled');
					  
			 }
		} 
		 
		 $(document).ready(function() {
		    	$('#submitId').removeAttr('disabled');
		    });
	</script>
</body>
</html>