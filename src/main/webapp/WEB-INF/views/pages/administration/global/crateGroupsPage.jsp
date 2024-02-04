<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Groups Creation Page</title>
<script type="text/javascript">
	var lanArr = [];
	 $(function(){
		  $(".alert").show('medium');
		  setTimeout(function(){
		    $(".alert").hide('medium');
		  }, 5000);
		});
	$(".close").click(function(){
	    $(".alert").hide('medium');
	});
</script>
<style type="text/css">
.alert{
  display:none;
  position: fixed;
  bottom: 0px;
  right: 0px;
  .fa {
    margin-right:.5em;
  }
}
</style>
</head>
<body oncontextmenu="return false;">

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
					<h2><spring:message code="label.ggsLink"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<c:url value="/groups/saveGlobalGroup" var="saveGroup" />
					<form:form action="${saveGroup}" method="post" id="groupsForm">
						<div style="width: 75%; margin-left: 10%;">
							<div class="form-group row">
    							<label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;">${ggDto.outlanMap['name']}</label>
    							<div class="col-sm-5">
    								<input type="text" name="valueName" id="valueName" class="form-control validate" maxlength="255"
										onkeyup="vlaueNameValidation('valueName', 'valueNameMsg')">
								</div>
							</div>
							<c:forEach items="${inLagList}" var="inl">
								<div class="form-group row">
									<c:forEach items="${ggDto.lanMap[inl.id]}" var="lan">
										<c:if test="${lan.key eq 'langName'}">
											<label for="value_${inl.id}" class="col-sm-3 col-form-label" style="color: #212529;">${lan.value}</label>
										</c:if>
										<c:if test="${lan.key eq 'label'}">
											<div class="col-sm-5">
											    <input type="hidden" name="lanId" id="lanId" value="${inl.id}">
   												<input type="text" name="pvalue" id="value_${inl.id}" placeholder="${lan.value}" class="form-control validate" maxlength="255">
												<script type="text/javascript">
													lanArr.push('${inl.id}');
												</script>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</c:forEach>
							
							<div class="form-group row">
    							<label for="groupOrder" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ggOrder"></spring:message></label>
    							<div class="col-sm-5">
    								<input type="number" name="order" class="form-control validate" id="groupOrder" min="0" max="10000" onkeypress="return (event.charCode !=8 && event.charCode ==0 || (event.charCode >= 48 && event.charCode <= 57))"
										onkeyup="requiredGroupOrderValidation('groupOrder','groupOrderMsg')">
								</div>
							</div>
							<div class="form-group row">
    							<label for="type" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ggType"></spring:message></label>
    							<div class="col-sm-5">
    								<select class="form-control validate" name="type" id="type" onchange="requiredtypeValidation('type','typeMsg')">
										<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
										<option value="table"><spring:message code="label.ggTable"></spring:message></option>
										<option value="form"><spring:message code="label.ggForm"></spring:message></option>
									</select>
								</div>
							</div>
							<div id="noofrowsshow">
							<div class="form-group row">
    							<label for="type" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ggRows"></spring:message></label>
    							<div class="col-sm-5">
    								<input type="text" data-texttype='NUMERIC' maxlength='2' name="noofrows"  id="noofrowsId" class="form-control validate" onkeyup="noofrowsFunction('noofrowsId','noofrowsIdMsg')" >
									<div id="noofrowsIdMsg" style="color: red;"></div>
								</div>
							</div>
							</div>
							<div id="noofcolumsshow">
							<div class="form-group row">
    							<label for="type" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ggColums"></spring:message></label>
    							<div class="col-sm-5">
    								<input type="text" data-texttype='NUMERIC' maxlength='1'  name="noofcolums" id="noofcolumsId" class="form-control validate" onkeyup="noofcolumsFunction('noofcolumsId','noofcolumsIdMsg')" >
									<div id="noofcolumsIdMsg" style="color: red;"></div>
								</div>
							</div>
							</div>
							
							<div class="form-group row">
    							<div class="col-sm-10" align="center">
    								<input type="button" value="${ggDto.outlanMap['Add']}" class="btn btn-danger btn-sm" id="addBtn">
								</div>
							</div>
						</div>
					</form:form>
					<div id="resultDiv"></div>
				<div>
				<c:if test="${not empty ggDto}">
					<div>
					<%-- <h4 style="text-align:center;padding-top:10px;padding-right:10%"><spring:message code="label.gggList"></spring:message></h4> --%>
						
						
						<table  class="table table-striped table-bordered">
						<tr>
							<th style="text-align: center;padding-right:10%"><spring:message code="label.ggsLink"></spring:message></th>
							</tr>
						</table>
						</div>	
							<table id="createGroup" class="table table-striped table-bordered nowrap"
								style="width: 100%;">	
									<thead>
										<tr>
											<th>${ggDto.outlanMap['name']}</th>
												<c:forEach items="${ggDto.lanMap[langId]}" var="lan">
													<c:if test="${lan.key eq 'langName'}">
														<th>${lan.value}</th>
													</c:if>
												</c:forEach>
											<th><spring:message code="label.ggType"></spring:message></th>
											<th><spring:message code="label.ggOrder"></spring:message></th>
											<th><spring:message code="label.ggRows"></spring:message></th>
											<th><spring:message code="label.ggColums"></spring:message></th>
										</tr>
										</thead>
										<tbody>
										<c:forEach items="${ggDto.ggMap}" var="gv1" varStatus="st">
											<tr>
												<td>${gv1.value.name}</td>
												<c:forEach items="${ggDto.lsMap[gv1.value.id]}" var="val2">
												<c:if test="${langId eq val2.inlagId.id}">
													<td>${val2.name}</td>
												</c:if>
												</c:forEach>
												<td>${gv1.value.type}</td>
												<td>${gv1.value.orderNo}</td>
												<td>${gv1.value.noofrows}</td>
												<td>${gv1.value.noofcolums}</td>
											</tr>
										</c:forEach>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>

	
<script type="text/javascript">
$('#addBtn').prop("disabled", false);
$("#groupOrder").on('keypress', function(e) {
   
        if($(this).val() > 10000){
          $(this).val('10000');
          /* alert("maximum 999"); */
          return false;
        }
      });

$(document).ready(function (e){
	$('#noofrowsshow').hide();
	$('#noofcolumsshow').hide();
});

function vlaueNameValidation(id, messageId){
	debugger;
	var valueNameFlag = false;
	var value = $('#'+id).val().trim();
	if(value == null || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		valueNameFlag = false;
	}else{
		var url=mainUrl+'/groups/groupsNameCheckExitOrNot/'+value;
		var result=synchronousAjaxCall(url).trim();
		if(result=="Yes"){
			$('#'+id).attr("data-errormessage","Name Already Exists...!");
			$('#'+id).attr("data-isvalid","false");
			valueNameFlag=false;
		}else{
			$('#'+id).attr("data-isvalid","true");
			valueNameFlag=true;
		}
	}
	checkElementValidation($('#'+id));
	return valueNameFlag;
		
}

function requiredGroupOrderValidation(id, messageId){
	debugger;
	var groupOrderFlag = false;
	var value = $('#'+id).val().trim();
	if(value == null || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		groupOrderFlag = false;
	}else{
		var regex = /^[0-9][0-9]*$/;
		if(regex.test(value)){
			if(0 < parseInt(value) && 10000 >= parseInt(value)){
				$('#'+id).attr("data-isvalid","true");
				 groupOrderFlag = true;
			}else{
				 if(parseInt(value) > 10000)
					 $('#'+id).val(10000);
				 $('#'+id).attr("data-isvalid","true");
				 groupOrderFlag = true;
			}
		}
	}
	checkElementValidation($('#'+id));
	return groupOrderFlag;
}

function requiredtypeValidation(id, messageId){
	debugger;
	var typeFlag = false;
	var value = $('#'+id).val();
	
	if(value == null || value == "-1" || value == "undefined"){
		$('#noofrowsshow').hide();
		$('#noofcolumsshow').hide();
		$('#noofrowsId').val("");
		$('#noofcolumsId').val("");
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		typeFlag = false;
	}else if(value=="table"){
		$('#noofrowsId').val("");
		$('#noofcolumsId').val("");
		var rowVal = $('#noofrowsId').val();
		var colVal = $('#noofcolumsId').val();
		$('#noofrowsshow').show();
		$('#noofcolumsshow').show();
		if(rowVal=="" && colVal == ""){
			typeFlag = false;
			$('#noofrowsId').attr("data-errormessage","Required Field..!");
			$('#noofrowsId').attr("data-isvalid","false");
			checkElementValidation($('#noofrowsId'));
			$('#noofcolumsId').attr("data-errormessage","Required Field..!");
			$('#noofcolumsId').attr("data-isvalid","false");
			checkElementValidation($('#noofcolumsId'));
		}else{
			typeFlag = false;
			$('#noofrowsId').attr("data-isvalid","true");
			checkElementValidation($('#noofrowsId'));
			$('#noofcolumsId').attr("data-isvalid","true");
			checkElementValidation($('#noofcolumsId'));
		}
	}else{
		$('#noofrowsshow').hide();
		$('#noofcolumsshow').hide();
		$('#noofrowsId').val("0");
		$('#noofcolumsId').val("0");
	}
		
		typeFlag = true;
		checkElementValidation($('#'+id));
		return typeFlag;
	}
	
	

function noofrowsFunction(id, messageId){
	debugger;
	var flag = false;
	var value = $('#'+id).val().trim();
	//alert(value);
	if(value == null || value == "-1" || value == ""){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}else{
		 if(value > 30){
			 $('#'+id).attr("data-errormessage","please enter less than 30..!");
				$('#'+id).attr("data-isvalid","false");
	            flag = false;
	        }else{
	        	flag = true;
	        	$('#'+id).attr("data-isvalid","true");
	        }
	}
	checkElementValidation($('#'+id));
	return flag;
}

function noofcolumsFunction(id, messageId){
	debugger;
	var flag = false;
	var value = $('#'+id).val().trim();
	if(value == null || value == "-1" || value == ""){ 
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}else{
		 if(value > 6){
			 $('#'+id).attr("data-errormessage","please enter less than 6..!");
			 $('#'+id).attr("data-isvalid","false");
	            flag = false;
	        }else{
	        	flag = true;
	        	$('#'+id).attr("data-isvalid","true");
	        }
	}
	checkElementValidation($('#'+id));
	return flag;
		
}

$('#addBtn').click(function(){
	debugger;	
	$('#addBtn').prop("disabled", true);
	if(validateElements($("#groupsForm"))){
		debugger;
		$(".loader").show(); 
		$('#groupsForm').submit();
	}else{
		$('#addBtn').prop("disabled", false);
	}
});
</script>

 <script type="text/javascript">
    $(document).ready(function() {
    	//$('#addBtn').removeAttr('disabled');
        var table = $('#createGroup').DataTable({
            searchBuilder: true,
           
        });
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
  
</body>
</html>