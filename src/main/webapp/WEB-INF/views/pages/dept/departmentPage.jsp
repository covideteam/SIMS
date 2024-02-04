<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Department Creation</title>
</head>
<body oncontextmenu="return false;">
	<div class="card" style="width: 65%; margin-left: 18%;">
		<div class="card-header">
               <div class="card-title" style="color:white; font-size: large; font-weight: bold; text-align: center;">Department Creation</div>
        </div>
         <div style="margin-left: 25%; width: 65%;">
            <c:url value="/department/saveDepartment" var="saveDept"></c:url>
        	<form:form action="${saveDept}" method="POST" id="deptForm" modelAttribute="dept">
        		<table class="table table-bordered table-striped" style="width: 65%;">
					<tr>
						<th> Name :</th>
						<td>
							<form:input path="deptName" id="deptName" cssClass="form-control" onblur="departmentNameValidation('deptName', 'deptNameMsg')"/>
							<div  id="deptNameMsg" style="color: red;"></div>
						</td>
					</tr>    
					<tr>
						<th> Description :</th>
						<td>
							<form:input path="deptDesc" id="deptDesc" cssClass="form-control" onblur="departmentDescValidation('deptDesc', 'deptDescMsg')"/>
							<div  id="deptDescMsg" style="color: red;"></div>
						</td>
					</tr>  
					<tr>
	        			<td align="center" colspan="2"><input type="button" value="Submit" class="btn btn-danger btn-md"  onclick="submitForm()"></td>
	        		</tr>  	
        		</table>
        	</form:form>
       </div>
    </div>
    <script type="text/javascript">
    var deptNamFlag = false;
    var deptDescFlag = false;
    function departmentNameValidation(id, message){
   		var value = $('#'+id).val();
   		if(value == null || value == "" || value == "undefined"){
   			/* $('#'+message).html("Required Field"); */
   			$('#'+message).html('${validationText}');
   			deptNamFlag = false;
   		}else{
   			var url = mainUrl+"/department/checkDeptDuplication/"+value;
   			var result = synchronousAjaxCall(url);
   			if(result == "" && result != "undefined"){
				$('#'+message).html("");
	   			deptNamFlag = true;
			}else{
				if(result == "undefined"){
					$('#'+message).html("Internal Issue Please Check.");
					deptNamFlag = false;
				}else{
					$('#'+message).html(result);
					deptNamFlag = false;
				}
			}
   			
   		}
   		return deptNamFlag;
   	}
    function departmentDescValidation(id, message){
   		var value = $('#'+id).val();
   		if(value == null || value == "" || value == "undefined"){
   			/* $('#'+message).html("Required Field"); */
   				$('#'+message).html('${validationText}');
   			deptDescFlag = false;
   		}else{
   			$('#'+message).html("");
   			deptDescFlag = true;
   		}
   		return deptDescFlag;
   	}
    function submitForm(){
		alert("Flag values :"+deptNamFlag+"::"+deptDescFlag);
		if(deptNamFlag && deptDescFlag){
			$('#deptForm').submit();
		}
	}
    </script>
</body>
</html>