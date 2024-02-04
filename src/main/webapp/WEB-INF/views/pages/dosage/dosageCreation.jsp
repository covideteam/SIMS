<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Creation</title>
</head>
<body oncontextmenu="return false;">
	<div class="card" style="width: 65%; margin-left: 18%;">
		<div class="card-header">
               <div class="card-title" style="color:white; font-size: large; font-weight: bold; text-align: center;">New Dosage</div>
        </div>	
         <div style="margin-left: 25%; width: 65%;">
        	<c:url value="/dosageCon/saveDosage" var="saveDosage"></c:url>
        	<form:form action="${saveDosage}" method="POST" id="saveDosageid" modelAttribute="df">
        		<table class="table table-bordered table-striped" style="width: 65%;">
        		<tr>
        			<th>Dosage Id :</th>
        			<td>
        				<div>
        					<form:input path="dosageid" id="dosageid" onblur="dosageidValidation('dosageid','dosageidMsg')" autocomplete="false" cssClass="form-control"/>
        				</div>
        				<div id="dosageidMsg" style="color: red;"></div>
        			</td>
        		</tr>
        		<tr>
        			<th>Dosage Name :</th>
        			<td>
        				<div >
        					<form:input path="dosagename" id="dosageName" onblur="dosageNameValidation('dosageName','dosageNameMsg')" cssClass="form-control"/>
        				</div>
        				<div id="dosageNameMsg" style="color: red;"></div>
        			</td>
        		</tr>
        		<tr>
        		 <td colspan="2"></td>
        		</tr>
        		<tr>
	        			<td align="center" colspan="2"><input type="button" value="Submit" class="btn btn-danger btn-md"  onclick="submitForm()"></td>
	        		</tr>  	
        	</table>
        	</form:form>
        </div>
   </div>
   <script type="text/javascript">
    var doidFlag = false;
    var nameFlag = false;
   	function dosageidValidation(id, messageId){
   		$('#'+messageId).html("");
   		var value = $('#'+id).val();
   		//alert("workvalue:"+value);
   		if(value =="" || value == "undefined"){
   			/* $('#'+messageId).html("Required Field."); */
   			$('#'+messageId).html('${validationText}');
   			doidFlag = false;
   		}else{
   			doidFlag = true;
   		}
   		if(doidFlag){
   			var url = mainUrl+'/dosageCon/checkDosageId/'+$('#'+id).val();
   			var result = synchronousAjaxCall(url).trim();
   			if(result == 'no'){
   				doidFlag = true;
   			}else{
   				$('#'+messageId).html("'" + $('#'+id).val() + "'  - Dosage Id alredy Avilable.");
   				$('#'+id).val("");
   				doidFlag = false;
   			}
   		}
   		return doidFlag;
   	}
   	function dosageNameValidation(id, message){
   		var value = $('#'+id).val();
   		if(value == null || value == "" || value == "undefined"){
   			/* $('#'+message).html("Required Field"); */
   			$('#'+messageId).html('${validationText}');
   			nameFlag = false;
   		}else{
   			$('#'+message).html("");
   			nameFlag = true;
   		}
   		return nameFlag;
   	}
   	
	function submitForm(){
// 		alert("Flag values :"+nameFlag +"::"+ pswdFlag +"::"+ emailFlag +"::"+ roleFlag +"::"+ deptFlag);
		if(doidFlag  && nameFlag){
			$('#saveDosageid').submit();
		}
	}
   </script>
</body>
</html>