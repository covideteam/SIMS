<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="card" >
		<div class="card-header">
               <div class="card-title" style="color:white; font-size: large; font-weight: bold; text-align: center;">Activity</div>
        </div>	
        
          <div>
        	<c:url value="/activity/saveActivity" var="saveactivity"></c:url>
        	<form:form action="${saveactivity}" method="POST" id="saveForm" modelAttribute="at">
        	<input type="hidden" name="countval" id="count">
        		<table class="table table-bordered table-striped" style="width: 100%;">
        		<tr>
        		<th>Language<th>
        			<select name="language" id="language" class="form-control" >
        			  <option value="-1">---select---</option>
			        	<c:forEach items="${inlagList}" var="lang">
			        		<option value="${lang.language}">${lang.language}</option>
			        	</c:forEach>
					</select>
        				
        		</tr>
        		<tr>
        			<th>Activity Name :</th>
        			<td >
        				<input name="activityName" id="activityName"  onblur="activityNameValidation('activityName', 'activityNameMsg')"  class="form-control"/>
        				<div id="activityNameMsg" style="color: red;"></div>
        			</td>
        		</tr>
        		<tr>
        			<th>Activity Desc :</th>
        			<td>
        				<textarea name="activityDesc" id="activityDesc" onblur="activityDescValidation('activityDesc', 'activityDescMsg')"  class="form-control"></textarea>
        				<div id="activityDescMsg" style="color: red;"></div>
        			</td>
        		
        		</tr>
        		<tr>
        			<td align="center" colspan="2" ><input type="button" value="Add" class="btn btn-danger btn-md"  onclick="addData()">
       			<div id="dataMsg" style="color: red;"></div></td>
        		</tr>
       			</table>
        		
        		<div style="width: 100%; margin-left: 5%;" id=dataval></div>
        		<table  style="width: 100%;">
        		<tr  align="center">
        			<td>
        			  <input type="button" value="Submit" class="btn btn-danger btn-md"  onclick="submitForm()">
        			</td>
        		</tr>
        	</table>
        	</form:form>
        </div>
        
</div>

<script type="text/javascript">

var bioRowId = 0;
function addData(){
	bioRowId ++;
	var lang=$('#language').val();
	var name=$('#activityName').val();
	var desc=$('#activityDesc').val();
	
	if(lang!="-1" && name!="" && desc!=""){
	
	var markup ='<table  class="table table-bordered table-striped" style="width: 100%;"><tr id="AddRow'+bioRowId+'">'+
	'<td> Language  &nbsp;&nbsp;</td>'+
	'<td><input name="lang'+bioRowId+'" id="lang'+bioRowId+'" type="text" readonly="readonly" class="form-control" value="'+lang+'"></td>'+
	 '<td>Activity Name  &nbsp;&nbsp; </td>'+
	'<td><input name="name'+bioRowId+'" id="name'+bioRowId+'" type="text" readonly="readonly" class="form-control" value="'+name+'"></td>'+
	 '<td>Activity Desc &nbsp;&nbsp; </td>'+
	'<td><textarea name="desc'+bioRowId+'" id="desc'+bioRowId+'" readonly="readonly" class="form-control" cols="20" row="10" >'+desc+'</textarea></td></tr></table>';
	$("#dataval").append(markup);
	$("#dataMsg").html("");
	$('#language').val("-1");
	$('#activityName').val("");
	$('#activityDesc').val("");
	}else{
		$("#dataMsg").html("All Required Field.");
	}

}
function submitForm(){
	
	if(bioRowId>0){
		
	$('#count').val(bioRowId);
	$('#saveForm').submit();
	}
}
</script>
</body>
</html>