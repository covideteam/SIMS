<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Parameter Creation</title>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#valuesTr').hide();
    	 $('#activityTr').hide();
        var table = $('#parametersPage').DataTable({
        	
            searchBuilder: true,
            "language": {
                "lengthMenu": "${showLabel} _MENU_ ${entriesLabel}",
        		 "search": "${searchLabel}:",
        		 "zeroRecords": "${noDtaAvlLabel}",
        	     "info": "${showingPgsLabel} _PAGE_ ${ofLabel} _PAGES_ & _MAX_ ${entriesLabel}",
        	     "infoEmpty": "${noRcdsLabel}",
        	     "infoFiltered": "(${filterLabel} _MAX_ ${totRcdsLabel})",
        	     "paginate": {
        	         "previous": " ${prevPgLabel}",
        	        	 "next": "${nextLabel}"
        	       }
        		 }
        });
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
</head>
<body oncontextmenu="return false;">
     <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.gpsLink"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<c:url value="/parameters/saveGlobalParameter" var="saveParameter" />
					<input type="hidden" name="langVals" id="langVals">
					<form:form action="${saveParameter}" method="post" id="parameterForm">
						<input type="hidden" name="ctVals" id="ctVals">
						<input type="hidden" name="bindActVal" id="bindActVal">
						<input type="hidden" name="paramMandatoryVal" id="paramMandatoryVal" />
						<div style="width: 75%; margin-left: 10%;">
							<div class="form-group row">
    							<label for="parameterName" class="col-sm-3 col-form-label" style="color: #212529;">${gpDto.outlanMap['name']}</label>
    							<div class="col-sm-5">
    								<input type="text" name="parameterName" id="parameterName" class="form-control validate" maxlength="500"
										onkeyup="parameterNameValidation('parameterName', 'parameterNameMsg')">
								</div>
							</div>
							<c:forEach items="${inLagList}" var="inl">
								<div class="form-group row">
									<c:forEach items="${gpDto.lanMap[inl.id]}" var="lan">
										<c:if test="${lan.key eq 'langName'}">
											<label for="value_${inl.id}" class="col-sm-3 col-form-label" style="color: #212529;">${lan.value}</label>
    									</c:if>
    									<c:if test="${lan.key eq 'label'}">
    										<div class="col-sm-5">
    											<input type="text" name="pvalue" id="value_${inl.id}" placeholder="${lan.value}" class="form-control validate"
												maxlength="500"	>
												<input type="hidden" name="lanId" id="lanId" value="${inl.id}"> 
												<script type="text/javascript">
												    var lang = $('#langVals').val();
												    if(lang != null && lang != "" && lang != "undefined")
												    	$('#langVals').val(lang+","+'${inl.id}');
												    else $('#langVals').val('${inl.id}');
												</script>
											</div>
    									</c:if>
    								</c:forEach>
								</div>
							</c:forEach>
							<c:choose>
								<c:when test="${userRole eq 'SUPERADMIN' }">
									<div class="form-group row">
										<label for="globalParameter" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.controlName"></spring:message></label> 
							  			<div class="col-sm-5"><input type="text" name="controlName"class="form-control" id="controlNameId" maxlength="500"></div>
									</div>
								</c:when>
								<c:otherwise>
									<input type="hidden" name="controlName"class="form-control" id="controlNameId" maxlength="500">
								</c:otherwise>
							</c:choose>
							<div class="form-group row">
    							<label for="groupName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.group"></spring:message></label>
    							<div class="col-sm-5">
    								<select name="groupName" id="groupName" class="form-control " >
										<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
										<c:forEach items="${gpDto.ggList}" var="g">
											<option value="${g.id}">${g.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group row">
    							<label for="controlType" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.controlType"></spring:message></label>
    							<div class="col-sm-5">
    								<select name="controlType" id="controlType" class="form-control validate" onchange="controlTypeValidation('controlType', 'controlTypeMsg')">
											<option value="">----<spring:message code="label.ruleSelect"></spring:message>----</option>
											<c:forEach items="${gpDto.controlType}" var="c">
												<option value="${c.code}">${c.name}</option>
											</c:forEach>
									</select>
								</div>
							</div>
							<div id="valuesTr">
								<div class="form-group row">
	    							<label for="controlValues" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.values"></spring:message></label>
	    							<div class="col-sm-5">
	    								<select name="controlValues" id="controlValues" class="form-control"  multiple="multiple">
											<option value="0">---<spring:message code="label.ruleSelect"></spring:message>---</option>
											<c:forEach items="${gpDto.valsList}" var="v">
												<option value="${v.id}">${v.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="form-group row">
    							<label for="bindAct" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.bindAct"></spring:message></label>
    							<div class="col-sm-5">
    								    <input type="radio" name="bindAct" id="bindAct_yes" value="Yes" onclick="bindActValidation('bindAct_yes')" required class="validate">
    									<spring:message code="label.gaYes"></spring:message>
										<input type="radio" name="bindAct" id="bindAct_no" value="No" onclick="bindActValidation('bindAct_no')" class="validate">
										<spring:message code="label.gaNo"></spring:message>
								</div>
							</div>
							<div id="activityTr">
								<div class="form-group row">
    								<label for="activityName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gactivity"></spring:message></label>
    								<div class="col-sm-5">
    									<select name="activityName" id="activityName" class="form-control validate"	required>
											<option value="0">---<spring:message code="label.ruleSelect"></spring:message>---</option>
											<c:forEach items="${gpDto.gaList}" var="a">
												<option value="${a.id}">${a.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="form-group row">
								<label for="phase" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaPhase"></spring:message></label>
								<div class="col-sm-5">
									<select name="phase" id="phase" class="form-control validate"  multiple="multiple">
										<option value="-1">---<spring:message code="label.sdSelect"></spring:message>---</option>
										<c:forEach items="${phase}" var="pha">
											<option value="${pha.id}">${pha.name}</option>
										</c:forEach>
								</select>
								</div>
							</div>
							<div id="unitsTr">
								<div class="form-group row">
    								<label for="unitsId" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.unit.units"></spring:message></label>
    								<div class="col-sm-5">
    									<select name="unitsId" id="unitsId" class="form-control" 
												required>
											<option value="0">---<spring:message code="label.ruleSelect"></spring:message>---</option>
											<c:forEach items="${gpDto.umList}" var="unit">
												<option value="${unit.id}">${unit.unitsCode}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="form-group row">
    							<label for="order" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.order"></spring:message></label>
    							<div class="col-sm-5">
    								<input type="number" name="order" class="form-control validate" id="order" min="0" max="10000" onkeypress="return (event.charCode !=8 && event.charCode ==0 || (event.charCode >= 48 && event.charCode <= 57))"
										onkeyup="orderValidation('order','orderMsg')" required>
								</div>
							</div>
							
							<div class="form-group row">
    							<label for="paramMandatory" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gpMandatory"></spring:message></label>
    							<div class="col-sm-5">
    								    <input type="radio" name="paramMandatory" id="paramMandatory_yes" value="Yes" onclick="paramMandatoryValidation('paramMandatory_yes')" required class="validate">
    									<spring:message code="label.gaYes"></spring:message>
										<input type="radio" name="paramMandatory" id="paramMandatory_no" value="No" onclick="paramMandatoryValidation('paramMandatory_no')" class="validate">
										<spring:message code="label.gaNo"></spring:message>
								</div>
							</div>
							<div class="form-group row">
    							<div class="col-sm-10" align="center">
    								<input type="button" value="${gpDto.outlanMap['Add']}" class="btn btn-danger btn-sm" id="addBtn">
								</div>
							</div>
						</div>
					</form:form>
					
					<div id="resultDiv"></div>
					<c:if test="${not empty gpDto}">
						<table  class="table table-striped table-bordered">
							<tr>
								<th style="text-align: center;padding-right:11%"><spring:message code="label.gpTitle"></spring:message></th>
							</tr>
						</table>
						<div style="overflow-x:auto;">
				    	<table id="parametersPage" class="table table-striped table-bordered" style="width:100%">
				    		<thead>
				    			<tr>
									<c:set value="${gpDto.inlag.id}" var="lsv"></c:set>
								 	<th>${gpDto.outlanMap['name']}</th>
									 <c:forEach items="${gpDto.lanMap[lsv]}" var="lan">
									 	 <c:if test="${lan.key eq 'langName'and langId eq lsv}">
											<th style="width:200px">${lan.value}</th>
										</c:if>
									</c:forEach>
									 <c:if test="${userRole eq 'SUPERADMIN'}">
										<th><spring:message code="label.controlName"></spring:message></th>
									</c:if>
									<th><spring:message code="label.group"></spring:message></th>
									<th><spring:message code="label.controlType"></spring:message></th>
									<th><spring:message code="label.values"></spring:message></th>
									<th><spring:message code="label.bindAct"></spring:message></th>
									<th><spring:message code="label.actName"></spring:message></th>
									<th><spring:message code="label.order"></spring:message></th>
									<th><spring:message code="label.unit.units"></spring:message></th>
									<th><spring:message code="label.gpMandatory"></spring:message></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${gpDto.ggMap}" var="gv1" varStatus="st">
									 <c:set value="${gpDto.inlag.id}" var="lsv1"></c:set>
							  		 <tr>
							 			<td>${gv1.value.parameterName}</td> 
										<c:forEach items="${gpDto.lsMap[gv1.value.id]}" var="val">
			                                      <td>${val.name}</td>
			                            </c:forEach>
			                            <c:if test="${userRole eq 'SUPERADMIN'}">
			                            	<td>${gv1.value.controlName}</td>
			                            </c:if>
         								<td>${gv1.value.groups.name}</td>
										<td>${gv1.value.contentType.name}</td>
										<td>
											<table class="table table-bordered table-striped covide-table">
												<c:forEach items="${gpDto.pctvMap[gv1.value.id]}" var="vsMap">
													<c:forEach items="${vsMap.value}" var="v">
													     <c:if test="${vsMap.key eq gv1.value.contentType.id}">
															<tr>
																<td>${v.name}</td>
															</tr>
														</c:if>
													</c:forEach>
												</c:forEach>
											</table>
										</td>
											<c:choose>
												<c:when test="${gv1.value.bindActivity eq true}">
													 <td>Yes</td>
												</c:when>
												<c:otherwise>
														<td>No</td>
												</c:otherwise>
											</c:choose>
										 <td>
											<c:choose>
												<c:when test="${gv1.value.bindActivity eq true}">
													${gv1.value.activity.name}
												</c:when>
												<c:otherwise><spring:message code="label.gptext"></spring:message></c:otherwise>
											</c:choose>
										</td>
										<td>${gv1.value.orderNo}</td>
			                             <td>${gv1.value.unitsId.unitsCode}</td>
		                             	<c:choose>
											<c:when test="${gv1.value.mandatory eq true}">
												 <td>Yes</td>
											</c:when>
											<c:otherwise>
													<td>No</td>
											</c:otherwise>
										</c:choose>
									</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					</c:if>
				</div>
			</div>

			
</div>
</div>

<script type="text/javascript">
$('#addBtn').prop("disabled", false);
function parameterNameValidation(id, messageId){
	debugger;
	var value = $.trim($('#'+id).val());
	if(value == null || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		var url=mainUrl+'/parameters/parameterNameCheckExitOrNot/'+value;
		var result=synchronousAjaxCall(url).trim();
		if(result=="Yes"){
			$('#'+id).attr("data-isvalid","true");
		}else{
			$('#'+id).attr("data-errormessage","Name Already Exists..!");
			$('#'+id).attr("data-isvalid","false");
		}
	}
	checkElementValidation($('#'+id));
}


function orderValidation(id, messageId){
	var value = $('#'+id).val();
	if(value == null || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		var regex = /^[0-9][0-9]*$/;
		if(regex.test(value)){
			$('#'+messageId).html("");
			if(0 < parseInt(value) && 10000 >= parseInt(value)){
			}else{
				 if(parseInt(value) > 10000)
					 $('#'+id).val(10000);	 
			}
		}
		$('#'+id).attr("data-isvalid","true");
	}
	checkElementValidation($('#'+id));	
}


function controlTypeValidation(id, messageId){
	debugger;
	var value = $('#'+id).val();
	if(value == null || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		$('#valuesTr').hide();
		$('#controlValues').removeClass('validate');
	}else{
		if(value == "RB" || value == "CB" || value == "SB"){
			$('#valuesTr').show();
			$('#controlValues').addClass('validate');
			checkElementValidation($('#controlValues'));
		}else{
			$('#controlValues').removeClass('validate');
			$('#controlValues').val(0);
			$('#valuesTr').hide();
			$('#'+id).attr("data-isvalid","true");
			
		}
	}
	checkElementValidation($('#'+id));	
}

function bindActValidation(id){
   debugger;
   var flag = false;
   var yesFlag = false;
   var noFlag = false;
   var actValflag=false;
   $('#bindActVal').val("");
   var vlaue ="";
   if($('#bindAct_yes').prop("checked")){
	   $('#activityTr').show();
	   $('#activityName').addClass('validate');
	   value = "Yes";
	   yesFlag = true;
	}else {
	     yesFlag = false;
	}
		
	if($('#bindAct_no').prop("checked")){
	   $('#activityNameMsg').html("");
	   $('#activityName').val(0);
	   $('#activityName').removeClass('validate');
	   $('#activityTr').hide();
	   value = "No";
	    noFlag = true;
	}else{
		noFlag = false;
	}
   
   if(yesFlag || noFlag){
	   $('#bindActVal').val(value);
	   $('#'+id).attr("data-isvalid","true");
   }else{
	   $('#'+id).attr("data-errormessage","Select Bind Activity..!");
	   $('#'+id).attr("data-isvalid","false");
	   flag = false;
   }
   checkElementValidation($('#'+id));
   return flag;
}
function paramMandatoryValidation(id){
	   debugger;
	   var flag = false;
	   var yesFlag = false;
	   var noFlag = false;
	   $('#paramMandatoryVal').val("");
	   var vlaue ="";
	   if($('#paramMandatory_yes').prop("checked")){
		   value = "Yes";
		   yesFlag = true;
		}else {
		     yesFlag = false;
		}
			
		if($('#paramMandatory_no').prop("checked")){
			value = "No";
		    noFlag = true;
		}else{
			noFlag = false;
		}
	   
	   if(yesFlag || noFlag){
		   $('#'+id).attr("data-isvalid","true");
		   $('#paramMandatoryVal').val(value);
	   }else{
		   $('#'+id).attr("data-isvalid","false");
		   flag = false;
	   }
	   checkElementValidation($('#'+id));
	   return flag;
	}

$('#addBtn').click(function(){
	
	$('#addBtn').prop("disabled", true);
	debugger;
	if(validateElements($("#parameterForm"))){
		$(".loader").show(); 
		$('#parameterForm').submit();
		
	}else{
		$('#addBtn').prop("disabled", false);
	}
});
</script>
</body>
</html>