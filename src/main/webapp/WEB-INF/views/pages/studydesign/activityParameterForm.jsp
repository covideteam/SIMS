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
               <div class="card-title" style="color:white; font-size: large; font-weight: bold; text-align: center;">Activity Parameter</div>
        </div>	
        
          <div>
        	<c:url value="/activity/saveActivityParameterForm" var="saveactivity"></c:url>
        	<form:form action="${saveactivity}" method="POST" id="saveForm">
        	<input type="hidden" name="countval" id="count">
        	<input type="hidden" name="actid" id="actid" value="${atpojo.id}">
        		<table class="table table-bordered table-striped" style="width: 100%;">
        		<tr><th>Language</th><td>${atpojo.language} </td>
        		<th>Activity Name</th><td>${atpojo.activityName} </td></tr>
        		<%-- <tr>
        		<th>Language<th>
        			<select name="language" id="language" class="form-control">
        			  <option value="-1">---select---</option>
			        	<c:forEach items="${inlagList}" var="lang">
			        		<option value="${lang.id}">${lang.language}</option>
			        	</c:forEach>
					</select>
        				
        		</tr>
        		<tr>
        			<th>Activity<th>
        			<select name="activity" id="activity" class="form-control" >
        			  <option value="-1">---select---</option>
			        	<c:forEach items="${alistdata}" var="act">
			        		<option value="${act.activityName}">${act.activityName}</option>
			        	</c:forEach>
					</select>
        		</tr> --%>
        		<tr>
        		<th>Type<th>
        			<select name="type" id="type"  onchange="myFunction('type')"  class="form-control">
        			  <option value="-1">---select---</option>
					  <option value="Test">Test</option>
					  <option value="Radio">Radio</option>
					  <option value="CheckBox">CheckBox</option>
					</select>
        		<th>Parameter Name :</th>
        			<td >
        				<textarea name="parameterName" id="parameterName"  onblur="parameterNameValidation('parameterName', 'parameterNameMsg')"  class="form-control"></textarea>
        				<div id="parameterNameMsg" style="color: red;"></div>
        			</td>
        		</tr>
       			</table>
        		<div id="idshow1" style="display:none; width: 100%; margin-left: 25%;"> 
	        		  <table>
	        			<tr>
	        			<th>Form Rang : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
	        			<td>
	       					<input name="from" id="from" type="number"  class="form-control" />
	        				<div id="fromMsg" style="color: red;"></div>
	        			</td>
	        			<th>To Rang: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
	        			<td>
	       					<input name="to" id="to" type="number"  class="form-control" />
	        				<div id="toMsg" style="color: red;"></div>
	        			</td>
	        		   </tr>
	        		   </table>
        		   </div>
        		    <div id="idshow2"  style="display:none; width: 75%; margin-left: 35%;"> 
	        		    <table>
	        			<tr>
	        			<th>Content : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
	        			<td>
	       					<input name="content" id="content" type="text"  class="form-control" />
	        				<div id="fromMsg" style="color: red;"></div>
	        			</td>
	        			</tr>
	        			</table>
        			</div>
        		<div align="center"><input type="button" value="Add" class="btn btn-danger btn-md"  onclick="addData()">
        		<div id="dataMsg" style="color: red;"></div></div>
       			<div id="dataMsg" style="color: red;"></div>
        		<div style="width: 75%; margin-left: 15%;" id=dataval></div>
        		
        		<br>
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

function myFunction(id) {
	var val = $('#'+id).val();
	//alert(val);
		if(val=="Test"){
		 $("#idshow1").show();
		 $("#idshow2").hide();
		}else{
		 $("#idshow2").show();
		 $("#idshow1").hide();
		}
}

var bioRowId = 0;
function addData(){
	bioRowId ++;
	var flag=false;
	var param=$('#parameterName').val();
	var typev=$('#type').val();
	if(typev=="Test"){
		var data1=$('#from').val();
		var data2=$('#to').val();
		if(data2!=""){
			flag=true;
		}else{
			flag=false;
		}
	}else{
		var data1=$('#content').val();
		var data2="";
		flag=true;
	}
	
	if( param!=""&& typev!="-1" && data1!="" && flag){
	
	var markup ='<table  class="table table-bordered table-striped"><tr id="AddRow'+bioRowId+'">'+
	 '<td>Parameter Name &nbsp;&nbsp; </td>'+
	 '<td><textarea name="param'+bioRowId+'"  readonly="readonly" class="form-control" cols="20" row="10" >'+param+'</textarea></td>'+
	'<td>Type &nbsp;&nbsp; </td>'+
	'<td><input name="typeval'+bioRowId+'" type="text" readonly="readonly" class="form-control input-sm" value="'+typev+'"></td>'+
	 '<td>Data1 &nbsp;&nbsp; </td>'+
	'<td><input name="dataone'+bioRowId+'" type="text" readonly="readonly" class="form-control input-sm" value="'+data1+'"></td>'+
	 '<td>Data2 &nbsp;&nbsp; </td>'+
	'<td><input name="datatwo'+bioRowId+'" type="text" readonly="readonly" class="form-control input-sm" value="'+data2+'"></td></tr></table>';
	$("#dataval").append(markup);
	$("#dataMsg").html("");
	$('#type').val("-1");
	$('#parameterName').val("");
	$('#from').val("");
	$('#to').val("");
	$('#content').val("");
	$("#idshow2").hide();
	$("#idshow1").hide();
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