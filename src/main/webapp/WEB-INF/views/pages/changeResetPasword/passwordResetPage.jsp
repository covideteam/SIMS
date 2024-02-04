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
<title>Password Reset</title>
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
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.resetPSWTitle"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
		<c:url value="/uad/saveResetPassword" var="savePwdUrl" />
		<form:form action="${savePwdUrl}" method="POST" id="myform" >
		<div style="margin-left:12%;">
			<div style="width: 40%;float:left;margin-left:24%;">
				<div class="form-group row">
				<label for="valueName" class="col-sm-4 col-form-label" style="color: #212529;"><spring:message code="label.userName"></spring:message></label>
				
				   <div class="col-sm-7">
				    <input type="hidden" value="${user.id}" name="username" id="username" class="form-control"/>
				   <input type="text" value="${user.username}" name="data" id="data" class="form-control" readonly/>
					<%-- <select  name="username" id="username" class="form-control" >
							<option value="${user.id}" >${user.username}</option>
					</select> --%>
					<div id="usernameMsg" style="color:red; "></div>
				   </div>
			   </div>
			   <div class="form-group row">
				<label for="valueName" class="col-sm-4 col-form-label" style="color: #212529;"><spring:message code="label.entrPSW"></spring:message></label>
				<div class="col-sm-7"><input type="password" name="resetPassword" id ="resetPassword" onblur="resetPasswordValidation('resetPwd', 'passwordMsg')" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" autocomplete="off" class="form-control">
			</div>
			</div>	
			<div class="form-group row">
					<label for="valueName" class="col-sm-4 col-form-label" style="color: #212529;"><spring:message code="label.reNewPSW"></spring:message></label>
					<div class="col-sm-7">
						<div><input type="password" name="newconPwd" id="newconPwd" onblur="newconPwdValidation('newconPwd', 'newconPwdMsg')" class="form-control"></div>
						<div id="newconPwdMsg" style="color: red;"></div>
					</div>
				</div>
			<div class="form-group row">
				<label for="valueName" class="col-sm-4 col-form-label" style="color: #212529;"><spring:message code="label.reason"></spring:message></label>
				<div class="col-sm-7"><input type="text" name="reason" id ="reason" onblur="resonValidation('reason', 'reasonMsg')" class="form-control"><div id="reasonMsg" style="color: red;"></div>
			</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-10" align="center">
	                  <input class="btn btn-danger btn-md" type="button" value="Reset Password" onclick="restPassword()"></input>
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
   </div>
  </div>
  </div>
</body>
<script type="text/javascript">


$(function(){
    $('#resetPassword').keydown(function(er){
 	   
             if(er.altKey||er.ctrlKey)
             {
             er.preventDefault();
             }
             
          }); 
     });
     
     
     
document.getElementById("passwordMsg").style.display = "none";

var myInput = document.getElementById("resetPassword");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

// When the user clicks on the password field, show the message box
myInput.onfocus = function () {
  document.getElementById("passwordMsg").style.display = "block";
}

// When the user clicks outside of the password field, hide the message box
myInput.onblur = function () {	
  document.getElementById("passwordMsg").style.display = "none";
} 
  // When the user starts to type something inside the password field

/* var pswdstrFlag = false; */
   myInput.onkeyup = function() {
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
}

   function resetPasswordValidation(id, messageId){
		debugger;
		var flag = false;
		var value = $('#'+id).val();
		if(value =="" || value == "undefined"||value==null){
			$('#'+messageId).html("Please Enter Password.");
			flag = false;
		}else{
			
	   		var lowerCaseLetters = /[a-z]/g;
	   		var upperCaseLetters = /[A-Z]/g;
	   		var numbers = /[0-9]/g;
	   		
	   	    if((value.length >= 8) && (value.match(lowerCaseLetters)) && (value.match(upperCaseLetters)) && (value.match(numbers)) ){
	   			$('#'+messageId).html("");
	   			flag = true;
	   		}else{
	   			$('#'+messageId).html("contain at least one number, one uppercase , lowercase letter and at least 8 or more characters");
	   			flag = false;
	   		}
		}
		return flag;
	}

   function newconPwdValidation(id, messageId){
		var flag = false;
		var value = $('#'+id).val();
		if(value =="" || value == "undefined"){
			$('#'+messageId).html("Please Enter Confirm Password.");
			flag = false;
		}else{
			var newPwd = $('#resetPassword').val();
			if(newPwd == value){
				$('#'+messageId).html("");
				flag = true;
			}else {
				$('#'+messageId).html("Confirm Password Is Not Matched.");
				flag = false;
			}
		}
		return flag;
	}
   
   function resonValidation(id, messageId){
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
	}
   
  
function restPassword(){
	
	var user = $("[name='username']").val();
	var newPwdflag = newconPwdValidation('newconPwd', 'newconPwdMsg');
	var reasonFlag = resonValidation('reason', 'reasonMsg');

	if(user != "-1"){
		var reason = $("#reason").val();
		$("#usernameMsg").html("");
		//alert(newPwdflag +" "+reasonFlag);
		if(newPwdflag && reasonFlag){
			document.forms["myform"].submit();
		}else{
			//$("#reasonMsg").html("Please Enter Reason");
			return false;
		}	
	}else{
		$("#usernameMsg").html("Please Select User");
		$("#reasonMsg").val("");
	}
}
</script>
</html>