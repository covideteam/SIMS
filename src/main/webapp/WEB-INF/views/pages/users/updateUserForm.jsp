<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.user.updateUser"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
        	<c:url value="/user/userUpdateData" var="updateUser"></c:url>
        	<form:form action="${updateUser}" method="POST" id="updateForm" modelAttribute="um">
        	<form:hidden path="id"/>
        		<div style="width: 85%; margin-left: 15%;">
        	      <div class="form-group row">
        			<label for="userName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCrName"></spring:message></label>
        			<div class="col-sm-5">
       					<form:input path="username" id="userName" readonly="true" cssClass="form-control"/>
        				<div id="userNameMsg" style="color: red;"></div>
        			</div>
        		</div>
        		<div class="form-group row">
        			<label for="fullName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCrFullName"></spring:message></label>
        			<div class="col-sm-5">
       					<form:input path="fullName" id="fullName" readonly="true" cssClass="form-control"/>
        				<div id="fullNameMsg" style="color: red;"></div>
        			</div>
        		</div>
        		<div class="form-group row">
        			<label for="roleVal" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.userCrRole"></spring:message></label>
        			<div class="col-sm-5">
        				<form:select path="role.id" id="roleVal" value="${um.role.role}" onchange="roleValidation('roleVal', 'roleValMsg')" cssClass="form-control">
        					<option value="-1">----Select----</option>
        					<form:options items="${rolesList}" itemLabel="role" itemValue="id"/>
        				</form:select>
        				<div id="roleValMsg" style="color: red;"></div>
        			</div>
        		</div>
        		
        		<div>
        		 <div class="col-sm-2"></div>
        		</div>
        		<div>
        			<div align="center" class="col-sm-6"><input type="button" value="<spring:message code="label.userUpdate"></spring:message>" class="btn btn-danger btn-md"  onclick="submitForm()"></div>
        		</div>
        	</div>
        	</form:form>
        </div>
        </div>
        </div>
       
        
        <script type="text/javascript">
        function roleValidation(id, messageId){
    		var valFlag = false;
    		var value = $('#'+id).val();
    		if(value == null || value == "-1" || value == "undefined"){
    			/* $('#'+messageId).html("Required Field."); */
    			$('#'+messageId).html('${validationText}');
    			valFlag = false;
    		}else{
    			$('#'+messageId).html("");
    			valFlag = true;
    		}
    		return valFlag;
    	}
        function submitForm(){
        	var rolec=roleValidation('roleVal', 'roleValMsg');
        	if(rolec){
        		$('#updateForm').submit();
        	}
    			
    		
    	}
        
        </script>
</body>
</html>