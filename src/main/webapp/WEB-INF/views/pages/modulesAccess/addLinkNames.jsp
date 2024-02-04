<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
<script>
var lanArr = [];
</script>

 
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.addLinkForm"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>		
					<c:url value="/modulesAccess/saveAddLinkNames" var="data"></c:url>
					<form:form action="${data}" method="POST" id="submitform">
					<input type="hidden" name="lagLinks" id="lagLinks">
					
					<div style="width: 75%; margin-left: 10%;">
						<div class="form-group row">
						   <label for="sideMenuId" class="col-sm-3 col-form-label" style="color: #212529;">${mDto.outLanMap['sideMenuName']}</label>
								<div class="col-sm-5">
									<select name="sideMenu" id="sideMenuId" class="form-control validate" onchange="selectSideMenuFuntion('sideMenuId','sideMenuIdMsg')" >
										<option value="-1">---<spring:message code="label.sdSelect"></spring:message>---</option>
										<c:forEach items="${amList}" var="side">
										<option value="${side.id }">${side.name}</option>
										</c:forEach>
									</select>
								</div>
						</div>
						<div class="form-group row">
								<label for="linkNameId" class="col-sm-3 col-form-label"
									style="color: #212529;">${mDto.outLanMap['name']}</label>
								<div class="col-sm-5">
									<input type="text" name="linkName" id="linkNameId" class="form-control validate" onchange="linkNameIdFunction('linkNameId','linkNameIdMsg')">
								</div>
						</div>
					<c:forEach items="${inLagList}" var="inl">
						<div class="form-group row">
							<c:forEach items="${mDto.lanMap[inl.id]}" var="lan">
							   <c:if test="${lan.key eq 'langName'}">
								 <label for="value_${inl.id}" class="col-sm-3 col-form-label" style="color: #212529;">${lan.value}</label>	
							   </c:if>
							   <c:if test="${lan.key eq 'label'}">
									<div class="col-sm-5">
									<input type="text" name="lvalue" id="value_${inl.id}" placeholder="${lan.value}" class="form-control validate"  onblur="valueValidationFunction('value_${inl.id}', 'value_${inl.id}_Msg')">
									<input type="hidden" name="lanId" id="lanId" value="${inl.id}">
									<script type="text/javascript">
										lanArr.push('${inl.id}');
									</script>
								    </div>						
								</c:if>
							</c:forEach>
					    </div>
					</c:forEach>	
					  <div class="form-group row">
							<div class="col-sm-10" align="center">
								<input type="button" value="<spring:message	code="label.submit"></spring:message>"	class="btn btn-danger btn-sm" id="sbmtForm"	onclick="submitForm()">
							</div>
					  </div>
			      </div>
					
		   </form:form>
	
	     </div>
	  </div>
   </div>
</div>
	
	<script type="text/javascript">
	
	function linkNameIdFunction(id,message){
		var nameFlag=false;
		var value = $('#'+id).val().trim();
   		if(value == null || value == "" || value == "undefined"){
   			$('#'+id).attr("data-errormessage","Required Field..!");
   			$('#'+id).attr("data-isvalid","false");
   			nameFlag = false;
   		}else{
   			//alert(value);
   			var url = mainUrl+"/modulesAccess/checkNamedDuplication/"+value;
   			var result = synchronousAjaxCall(url);
			if(result == "" && result != "undefined"){
				$('#'+id).attr("data-isvalid","true");
	   			nameFlag = true;
			}else{
				if(result == "undefined"){
					$('#'+id).attr("data-errormessage","Internal Issue Please Check..!");
		   			$('#'+id).attr("data-isvalid","false");
					nameFlag = false;
				}else{
					$('#'+id).attr("data-errormessage",result);
		   			$('#'+id).attr("data-isvalid","false");
					/* $('#'+message).html(result); */
					nameFlag = false;
				}
			}
   		}
   		checkElementValidation($('#'+id));
   		return nameFlag;
	}
	
	
	function valueValidationFunction(id, messageId){
		var valFlag = false;
		var value = $('#'+id).val().trim();
		if(value == null || value == "" || value == "undefined"){
			$('#'+id).attr("data-errormessage","Required Field..!");
   			$('#'+id).attr("data-isvalid","false");
			valFlag = false;
		}else{
			$('#'+id).attr("data-isvalid","true");
			valFlag = true;
		}
		checkElementValidation($('#'+id));
		return valFlag;
	}
	function selectSideMenuFuntion(id, messageId){
		var valFlag = false;
		var value = $('#'+id).val();
		if(value == null || value == "-1" || value == "undefined"){
			$('#'+id).attr("data-errormessage","Required Field..!");
   			$('#'+id).attr("data-isvalid","false");
			valFlag = false;
		}else{
			$('#'+id).attr("data-isvalid","true");
			valFlag = true;
		}
		checkElementValidation($('#'+id));
		return valFlag;
	}
	
	function submitForm(){
		//$('#sbmtForm').attr('disabled','disabled');
		debugger;
		var flag=linkNameIdFunction('linkNameId','linkNameIdMsg');
		var flag2=selectSideMenuFuntion('sideMenuId','sideMenuIdMsg');
		
		if(lanArr.length > 0){
			//alert(lanArr.length);
			for(var i=0; i<lanArr.length; i++){
				var valflag = valueValidationFunction('value_'+lanArr[i],'value_'+lanArr[i]+'_Msg');
				var linkVal = $('#lagLinks').val();
				var value = $('#value_'+lanArr[i]).val();
				//alert(value);
				if(value != null && value != "" && value != "undefinded")
					value = lanArr[i]+"@@"+value;
				if(linkVal == null || linkVal == "" || linkVal == "undefinde")
					
					$('#lagLinks').val(value);
				else $('#lagLinks').val(linkVal+","+value);
			}
		}
		
		if(validateElements($("#submitform"))){
			$(".loader").show(); 
			$('#submitform').submit();
		}
	}
	
	
	
	 
	</script>
	
</body>
</html>