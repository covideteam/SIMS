<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Instrument</title>

</head>
<body oncontextmenu="return false;">
	  <div class="card">
      <c:if test="${not empty pageMessage}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('success', '${pageMessage}');
	   		</script>
	   </c:if>
	   <c:if test="${not empty pageError}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('error', '${pageError}');
	   		</script>
	   </c:if>
	
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.deepfreezer.hedding"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
	<c:url value="/instrument/saveDeepfreserShelfs" var="saveUrlShelf" />
	<form:form action="${saveUrlShelf}"  method="post" id="submitidShelf" >
	      <table class="table table-bordered table-striped" style="width: 100%;">
	      	<tr><th colspan="4">Shelf Barcodes</th></tr>
	               <tr>
	                  <th><spring:message code="label.deepfreezer.instrument"></spring:message></th>
	                  <td><select  name="instrument" id="instrumentIdShelf" onchange="instrumentFunction('instrumentIdShelf','instrumentIdShelfMsg')" class="form-control">
						<option value="-1">--<spring:message code="label.sdSelect"></spring:message>--</option>
						 <c:forEach items="${instru}" var="instr">
							<option value="${instr.id}">${instr.instrumentNo}</option>
						</c:forEach>
				    	</select>
				    	<span style="color: red;" id="instrumentIdShelfMsg"></span>
				      </td>
						<th><spring:message code="label.deepfreezer.shelfCount"></spring:message> </th>
						<td><input type="number" name="rackCount" id="rackCountIdShelf" class="form-control" onchange="rackCountFunction('rackCountIdShelf','rackCountIdShelfMsg')"/>
						<span style="color: red;" id="rackCountIdShelfMsg"></span>
					   </td>
					 </tr>
				 	<tr><td colspan="14" align="center"><input type="button" value="<spring:message code="label.deepfreezer.submit"></spring:message>" class="btn btn-danger btn-md	"  onclick="fromValidationShelf()">
					    </td>
				    </tr>
			</table>
			</form:form>
			
	<c:url value="/instrument/saveDeepfreser" var="saveUrl" />
	<form:form action="${saveUrl}"  method="post" id="submitid" >
	      <table class="table table-bordered table-striped" style="width: 100%;">
	               <tr><th colspan="4">Rachk's Barcodes</th></tr>
	               <tr>
	                  <th><spring:message code="label.deepfreezer.instrument"></spring:message></th>
	                  <td><select  name="instrument" id="instrumentId" onchange="instrumentFunction('instrumentId','instrumentMsg')" class="form-control">
						<option value="-1">--<spring:message code="label.sdSelect"></spring:message>--</option>
						 <c:forEach items="${instru}" var="instr">
							<option value="${instr.id}">${instr.instrumentNo}</option>
						</c:forEach>
				    	</select>
				    	<span style="color: red;" id="instrumentMsg"></span>
				      </td>
						<th><spring:message code="label.deepfreezer.rackCount"></spring:message> </th>
						<td><input type="number" name="rackCount" id="rackCountId" class="form-control" onchange="rackCountFunction('rackCountId','rackCountMsg')"/>
						<span style="color: red;" id="rackCountMsg"></span>
					   </td>
					 </tr>
				 	<tr><td colspan="14" align="center"><input type="button" id="addBtn" value="<spring:message code="label.deepfreezer.submit"></spring:message>" class="btn btn-danger btn-md	"  onclick="fromValidation()">
					    </td>
				    </tr>
			</table>
			</form:form>
       <div id="userDetails"></div>
       </div>
       </div>
       </div>
       </div>
       </div>
       
</body>
<script type="text/javascript">
$('#addBtn').prop("disabled", false);
   function instrumentFunction(id, message){
	   var flag1=false;
		var value = $('#'+id).val();
		if(value == "" || value == "-1" || value == "undefined"){
			/* $('#'+message).html("Required Field"); */
			$('#'+message).html('${validationText}');
			flag1 = false;
		}else{
			$('#'+message).html("");
			flag1 = true;
		}
		return flag1;
	}
   function rackCountFunction(id, message){
	   var flag2=false;
		var value = $('#'+id).val();
		if(value == "" || value == "-1" || value == "undefined"){
			/* $('#'+message).html("Required Field"); */
			$('#'+message).html('${validationText}');
			flag2 = false;
		}else{
			$('#'+message).html("");
			flag2 = true;
		}
		return flag2;
	}
	function fromValidation() {
	       var flag1=instrumentFunction('instrumentId','instrumentMsg');
	       var flag2=rackCountFunction('rackCountId','rackCountMsg');
	       if(flag1 && flag2){
	         $('#submitid').submit();
	       }	
	} 
	
	function fromValidationShelf() {
		$('#addBtn').prop("disabled", true);
	       var flag1=instrumentFunction('instrumentIdShelf','instrumentIdShelfMsg');
	       var flag2=rackCountFunction('rackCountIdShelf','rackCountIdShelfMsg');
	       if(flag1 && flag2){
	         $('#submitidShelf').submit();
	       }else{
	    	   $('#addBtn').prop("disabled", false);
	       }	
	} 
    
</script>
</html>