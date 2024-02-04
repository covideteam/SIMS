    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Activity Creation Page</title>

        <script type="text/javascript">
	var lanArr = [];
	var actCode = [];
	$(function (){
		$('#activityTr').hide();
		$('#valuesTr').hide();
	});
        </script>
        <style type="text/css">
            .alert {
                display: none;
                position: fixed;
                bottom: 0px;
                right: 0px;
            }
        </style>
    </head>
    <body oncontextmenu="return false;">
        <div class="row">
            <div class="col-md-12 col-sm-12 ">
                <div class="x_panel">
                    <div class="x_title">
                        <h2><spring:message code="label.gasLink"></spring:message></h2>
                        <ul class="nav navbar-right panel_toolbox">
                            <li>
                                <a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                            </li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="x_content">
                        <br>
                        <c:url value="/globalAtivity/saveGlobalActivity" var="saveActivity" />
                        <input type="hidden" name="role" id="roleval" value="${userRole}">
                        <form:form action="${saveActivity}" method="post" id="activityForm">
                            <input type="hidden" name="activitycode" id="activitycode">
                            <input type="hidden" name="headdingVal" id="headdingVal">
                            <input type="hidden" name="showInPDFVal" id="showInPDFVal">
                            <input type="hidden" name="subjectsInputVal" id="subjectsInputVal">
                            <input type="hidden" name="eligibleForNextProcessVal" id="eligibleForNextProcessVal">
                            
                            <input type="hidden" name="actGroup" id="actGroup">
                            <div style="width: 75%; margin-left: 10%;">
                                <div class="form-group row">
                                    <label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;">${gaDto.outlanMap['name']}</label>
                                    <div class="col-sm-5">
                                        <input type="text" name="valueName" id="valueName" class="form-control validate" maxlength="255"
                                               onkeyup="vlaueNameValidation('valueName', 'valueNameMsg')">
                                    </div>
                                </div>
                                <c:forEach items="${inLagList}" var="inl">
                                    <div class="form-group row">
                                        <c:forEach items="${gaDto.lanMap[inl.id]}" var="lan">
                                            <c:if test="${lan.key eq 'langName'}">
                                                <label for="value_${inl.id}" class="col-sm-3 col-form-label" style="color: #212529;">${lan.value}</label>
                                            </c:if>
                                            <c:if test="${lan.key eq 'label'}">
                                                <div class="col-sm-5">
                                                    <input type="text" name="pvalue" id="value_${inl.id}" placeholder="${lan.value}" class="form-control validate" maxlength="255">
                                                    <input type="hidden" name="lanId" id="lanId" value="${inl.id}">

                                                    <script type="text/javascript">
												    lanArr.push('${inl.id}');
                                                    </script>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${userRole eq 'SUPERADMIN'}">
                                        <div class="form-group row">
                                            <label for="activityCode" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaActivityCode"></spring:message></label>
                                            <div class="col-sm-5">
                                                <input type="text" name="activityCode" id="activityCode" class="form-control" maxlength="500" />
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="activityCode" id="activityCode" value="" class="form-control validate" maxlength="500" />
                                    </c:otherwise>
                                </c:choose>

                                <div class="form-group row">
                                    <label for="allowMultiple" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaAllowMultipule"></spring:message></label>
                                    <div class="col-sm-5">
                                        <input type="radio" name="allowMultiple" id="allowMultipleYes" value="Yes" class="validate">
                                        &nbsp;<spring:message code="label.gaYes"></spring:message> &nbsp;&nbsp;&nbsp;
                                        <input type="radio" name="allowMultiple" id="allowMultipleNo"  value="No" class="validate">
                                        &nbsp;<spring:message code="label.gaNo"></spring:message>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaAssigntoRole"></spring:message></label>
                                    <div class="col-sm-5">
                                        <select name="roleName" id="roleName" class="form-control validate" multiple="multiple">
                                            <c:forEach items="${gaDto.rolesList}" var="rol">
                                                <option value="${rol.id}">${rol.role}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaGroupAct"></spring:message></label>
                                    <div class="col-sm-5">
                                        <select name="actGroupval" id="actGroupval" class="form-control"
                                                onchange="actGroupValidation('actGroupval', 'actGroupvalMsg')">
                                            <option value="">----<spring:message code="label.ruleSelect"></spring:message>----</option>
                                            <c:forEach items="${gaDto.gbList}" var="gb">
                                                <option value="${gb.globlgroupId.id}">${gb.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="orderNo" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.order"></spring:message></label>
                                    <div class="col-sm-5">
                                        <input type="number" name="orderNo" id="orderNo" class="form-control validate" onkeyup="orderNoValidation('orderNo', 'orderNoMsg')" min="0" max="999" onkeypress="return (event.charCode !=8 && event.charCode ==0 || (event.charCode >= 48 && event.charCode <= 57))">
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="headRadio" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaShowHeadding"></spring:message></label>
                                    <div class="col-sm-5">
                                        <input type="radio" name="headRadio" id="headRadio_Yes" class="validate"
                                               onchange="headdingRadioValidation('headRadio_Yes')">&nbsp;<spring:message code="label.gaYes"></spring:message> &nbsp;&nbsp;&nbsp; <input type="radio" name="headRadio" id="headRadio_No"
                                               onchange="headdingRadioValidation('headRadio_No')">&nbsp;<spring:message code="label.gaNo"></spring:message>
                                    </div>
                                </div>


                                <div class="form-group row">
                                    <label for="sIPRadio" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaShowInPdf"></spring:message></label>
                                    <div class="col-sm-5">
                                        <input type="radio" name="sIPRadio" id="sIPRadio_Yes" class="validate"
                                               onchange="sIPRadioValidation('sIPRadio_Yes')">&nbsp;<spring:message code="label.gaYes"></spring:message> &nbsp;&nbsp;&nbsp; <input type="radio" name="sIPRadio" id="sIPRadio_No"
                                               onchange="sIPRadioValidation('sIPRadio_No')">&nbsp;<spring:message code="label.gaNo"></spring:message>
                                    </div>
                                </div>


                                <c:if test="${userRole eq 'SUPERADMIN'}">
                                    <div class="form-group row">
                                        <label for="subjectsInput" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gasubjectInput"></spring:message></label>
                                        <div class="col-sm-5">
                                            <input type="radio" name="subjectsInput" id="subjectsInput_Yes" class="validate"
                                                   onchange="subjectsInputValidation('subjectsInput_Yes')">&nbsp;<spring:message code="label.gaSubInputShow"></spring:message> &nbsp;<input type="radio" name="subjectsInput" id="subjectsInput_No"
                                                   onchange="subjectsInputValidation('subjectsInput_No')">&nbsp;<spring:message code="label.gaSubInputHide"></spring:message>
                                        </div>
                                    </div>
                                </c:if>
                                
									<div class="form-group row">
									<label for="subjectsInput" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.gaEligibleForNextProcess"></spring:message></label>
									<div class="col-sm-5">
									<input type="radio" name="eligibleForNextProcess" id="eligibleForNextProcess_Yes"
									                                        onchange="eligibleForNextProcessValidation('eligibleForNextProcess_Yes')" class="validate">&nbsp;<spring:message
									                                            code="label.gaYes"></spring:message> &nbsp;&nbsp;&nbsp;&nbsp; <input
									                                        type="radio" name="eligibleForNextProcess" id="eligibleForNextProcess_No"
									                                        onchange="eligibleForNextProcessValidation('eligibleForNextProcess_No')" class="validate">&nbsp;<spring:message
									                                            code="label.gaNo"></spring:message>
									
									</div>
									</div>

                                <div class="form-group row">
                                    <div class="col-sm-10" align="center">
                                        <input type="button" value="${gaDto.outlanMap['Add']}" class="btn btn-danger btn-sm" id="addBtn">
                                    </div>
                                </div>
                            </div>
                        </form:form>
                        <div id="resultDiv"></div>
                        <div>
                            <c:if test="${not empty gaDto}">
                                <table class="table table-striped table-bordered">
                                    <tr>
                                        <th style="text-align: center;padding-right:10%"><spring:message code="label.gaListTitle"></spring:message></th>
                                    </tr>
                                </table>
                                    <table id="activityPage" class="table table-striped table-bordered " style="width: 100%;">
                                        <thead>
                                            <c:set value="${gaDto.inlag.id}" var="lsv"></c:set>
                                            <tr>
                                                <th>${gaDto.outlanMap['name']}</th>
                                                <c:forEach items="${gaDto.lanMap[lsv]}" var="lan">
                                                    <c:if test="${lan.key eq 'langName' and langId eq lsv}">
                                                        <th>${lan.value}</th>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${userRole eq 'SUPERADMIN'}">
                                                    <th><spring:message code="label.gaActivityCode"></spring:message></th>
                                                </c:if>
                                                <th><spring:message code="label.gaAllowMultipule"></spring:message></th>
                                                <th><spring:message code="label.gaAssigntoRole"></spring:message></th>
                                                <th><spring:message code="label.order"></spring:message></th>
                                                <th><spring:message code="label.gaShowHeadding"></spring:message></th>
                                                <th><spring:message code="label.gaShowInPdf"></spring:message></th>
                                                <th><spring:message code="label.action"></spring:message></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${gaDto.ggMap}" var="gv1" varStatus="st">
                                                <c:set value="${gaDto.inlag.id}" var="lsv1"></c:set>
                                                <tr>
                                                    <td>${gv1.value.name}</td>
                                                    <c:forEach items="${gaDto.lsMap[gv1.value.id]}" var="val">
                                                        <c:if test="${langId eq lsv1}">
                                                            <td>${val.name}</td>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:if test="${userRole eq 'SUPERADMIN'}">
                                                        <td>${gv1.value.activityCode}</td>
                                                        <script>
                                                        actCode.push('${gv1.value.activityCode}');
                                                        </script>
                                                    </c:if>
                                                    <td>${gv1.value.allowMultiple}</td>
                                                    <td>
                                                        <table>
                                                            <tr>
                                                                <c:forEach items="${gaDto.gauserIdMap[gv1.value.id]}"
                                                                           var="role">
                                                                    <td>${gaDto.roleMap[role]}</td>
                                                                </c:forEach>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td>${gv1.value.orderNo}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${gv1.value.headding eq true}">Yes</c:when>
                                                            <c:otherwise>No</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${gv1.value.showInPDF eq true}">Yes</c:when>
                                                            <c:otherwise>No</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td align="center">
                                                    	<a onclick="updateActivityDetails('${gv1.value.id}')"><i class='fa fa-edit' data-toggle="modal" data-target="#myModal" style="color:blue; font-weight: bold; font-size : 15px;"></i></a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                            </c:if>
                            <div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
 <c:url value="/activityUpdate/saveUpdatedActivityDetails" var="updateActivityDetails"></c:url>
	<form:form action="${updateActivityDetails}" method="POST" id="activityUpdateForm">
		<!-- <input type="hidden" name="updateActId" id="updateActId">
		<input type="hidden" name="updateActOrderNo" id="updateActOrderNo">
		<input type="hidden" name="updateActRoles" id="updateActRoles"> -->
		<input type="hidden" name="activityId" id="updateActId">
		<input type="hidden" name="orderNo" id="updateActOrderNo">
		<input type="hidden" name="roleIds" id="updateActRoles">
	</form:form>
<!-- The Modal -->
  <div class="modal fade" id="myModal">
<!--     <div class="modal-dialog modal-xl"> -->
 	<div class="modal-dialog modal-md">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Activity Update</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body" id="updateActivity"></div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>

<script type="text/javascript">
$('#addBtn').prop("disabled", false);
function eligibleForNextProcessValidation(id){
    debugger;
    var subInputFlag = false;
    var flag1 = false;
    var flag2 = false;
    if($('#eligibleForNextProcess_Yes').prop("checked")){
        flag1 = true;
        $('#eligibleForNextProcessVal').val("Yes");
        var value = $('#eligibleForNextProcessVal').val();
    }
    if($('#eligibleForNextProcess_No').prop("checked")){
        flag2 = true;
        $('#eligibleForNextProcessVal').val("No");
    }
    if(flag1 || flag2){
        subInputFlag = true;
        $('#'+id).attr("data-isvalid","true");
    }
    else{
    	$('#'+id).attr("data-errormessage","Required Field..!");
    	$('#'+id).attr("data-isvalid","false");
        subInputFlag = false;
    }
    checkElementValidation($('#'+id));
    return subInputFlag;
}

function vlaueNameValidation(id, messageId){
	debugger;
	var value = $('#'+id).val().trim();
	if(value == null || value == "" || value == "undefined"){
	$('#'+id).attr("data-errormessage","Required Field..!");
	$('#'+id).attr("data-isvalid","false");
	}else{
		var resVal = synchronousAjaxCall(mainUrl+"/globalAtivity/checkActivityDuplication/"+value);
		if(resVal != null && resVal != "undefined" && resVal != ""){
			$('#'+id).attr("data-errormessage", "Name Already Exists...!");
			$('#'+id).attr("data-isvalid","false");
		}else{
			$('#'+id).attr("data-isvalid","true");
		}
	}
	checkElementValidation($('#'+id));

}

$("#orderNo").on('keypress', function(e) {
    debugger;
        if($(this).val() > 10000){
          $(this).val('10000');
          /* alert("maximum 999"); */
          return false;
        }
      });

function orderNoValidation(id, messageId){
	var value = $('#'+id).val().trim();
	if(value == null || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		var regex = /^[0-9][0-9]*$/;
		if(regex.test(value)){
			if(0 < parseInt(value) && 10000 >= parseInt(value)){
				$('#'+id).attr("data-isvalid","true");
			}else{
				 if(parseInt(value) > 10000)
					 $('#'+id).val(10000);
				 $('#'+id).attr("data-isvalid","true");
			}
		}
	}
	checkElementValidation($('#'+id));
}

function headdingRadioValidation(id){
	var flag1 = false;
	var flag2 = false;
	if($('#headRadio_Yes').prop("checked")){
		flag1 = true;
		$('#headdingVal').val("Yes");
	}
	if($('#headRadio_No').prop("checked")){
		flag2 = true;
		$('#headdingVal').val("No");
	}
	if(flag1 || flag2)
	    $('#'+id).attr("data-isvalid","true");
	else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}
	checkElementValidation($('#'+id));
}

function sIPRadioValidation(id){
	var flag1 = false;
	var flag2 = false;
	if($('#sIPRadio_Yes').prop("checked")){
		flag1 = true;
		$('#showInPDFVal').val("Yes");
	}
	if($('#sIPRadio_No').prop("checked")){
		flag2 = true;
		$('#showInPDFVal').val("No");
	}
	if(flag1 || flag2)
		$('#'+id).attr("data-isvalid","true");
	else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}
	checkElementValidation($('#'+id));
}



function subjectsInputValidation(id){
	debugger;
	var flag1 = false;
	var flag2 = false;
	if($('#subjectsInput_Yes').prop("checked")){
		flag1 = true;
		$('#subjectsInputVal').val("Yes");
		var value = $('#subjectsInputVal').val();
	}
	if($('#subjectsInput_No').prop("checked")){
		flag2 = true;
		$('#subjectsInputVal').val("No");
	}
	if(flag1 || flag2)
		$('#'+id).attr("data-isvalid","true");
	else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}
	checkElementValidation($('#'+id));
}



//written code for checking Duplicate ActivityCode.....!
var actCodeVal = actCode;
$(document).ready(function() {
	$("#activityCode").on('blur',function(e){
		debugger;
		var activityCode =$("#activityCode").val();
	
		if(activityCode != ""){
			$(actCodeVal).each(function(index, element){
				  //alert("Iteration: " + element)
				  debugger;
				  if(element == activityCode){
					  debugger;
					  $("#activityCode").val("");
					  bs4Toast.warning('warning', 'Activity Code Already Exists..!');
					  return false;
				  }
			});
		}
			
	});
});



$('#addBtn').click(function(){
	debugger;
	$('#addBtn').prop("disabled", false);
	var activityCode =$("#activityCode").val();
	if(activityCode==null || activityCode=="" || activityCode=="undefined")
	 	$("#activitycode").val("0");
	else
		$("#activitycode").val(activityCode);

        var actGroupVal = $('#actGroupval').val();
        if(actGroupVal == "")
    	$('#actGroup').val("0");

		if(validateElements($("#activityForm"))){
			$(".loader").show();
			$('#activityForm').submit();
		}else{
			$('#addBtn').prop("disabled", false);
		}
});
 </script>

 <script type="text/javascript">
    $(document).ready(function() {
        var table = $('#activityPage').DataTable({
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
//  table.searchBuilder.container().prependTo(table.table().container());
    });
    
    var ordersNoArr = null;
    function updateActivityDetails(gaId){
    	$(".loader").show();
    	$('#myModal').modal({backdrop: 'static', keyboard: false}, 'show');
    	asynchronousAjaxCallBack(mainUrl+ "/activityUpdate/getActivityDetails/" +gaId, getActivityDetails);
		$('#updateActId').val(gaId);
    }
    function getActivityDetails(data){
		$(".loader").hide();
		var htmlStr = '';
		if(data != null && data != "undefined"){
			ordersNoArr = data.ordersNoList;
			htmlStr += '<div class="row"><div class="col-sm-12">'
			        +'<div class="row">'
			        +'<div class="col-sm-3">Roles :</div>'
			        +'<div class="col-sm-9"><select name="updateRole" id="updateRole" class="form-control" onchange="updateRoleValidation()" multiple="multiple">'
			        +'<option value="'+"-1"+'">----Select----</option>';
			        if(data.rolesList != null && data.rolesList != "undefined"){
			        	if(data.rolesList.length > 0){
			        		$.each(data.rolesList, function( index, value ) {
			        			htmlStr += '<option value="'+value.roleId+'">'+value.roleName+'</option>';
			        		});
			        	}
			        }
			 htmlStr +='</select>'
			 		 +'<div id="updateRoleMsg" style="color:red"></div>'
			 		 +'</div></div>'
			         +'<div class="row" style="padding-top: 2%">'
			         +'<div class="col-sm-3"> Order No.</div>'
			         +'<div class="col-sm-9">'
			         +'<input type="number" name=updateOrerder id="updateOrder" style="width:25%;" value="'+data.orderNo+'" class="form-control" onblur="updateOrderValidation()"></div>'
			         +'<div id="updateOrderMsg" style="color:red;"></div>'
			         +'</div>'
			         +'<div class="row" style="padding-top: 2%">'
			         +'<div class="col-sm-12" style="text-align:center;">'
			         +'<input type="button" value="Update" class="btn btn-primary btn-sm" onclick="updateActivity()">'
			         +'</div></div>'
			         +'</div></div>';
			 
			
		}
		if(htmlStr != ""){
			$('#updateActivity').html(htmlStr);
			if(data != null && data != "undefined"){
				if(data.existingRoles != null && data.existingRoles != "" && data.existingRoles != "undefined"){
					 var existsRoleArr = data.existingRoles.split(",");
					 $.each(existsRoleArr, function( index, roleVal ) {
						 $('select option[value=' + roleVal + ']').attr('selected', true);
					 });
				 }
			}
		}
			
		
	}
    function updateRoleValidation(){
    	var value = $('#updateRole').val();
    	var flag = false;
    	if(value != null && value != "-1" && value != "undefined"){
    		$('#updateActRoles').val(value);
    		$('#updateRoleMsg').html("");
    		flag = true;
    	}else{
    		$('#updateRoleMsg').html("Required Field.");
    		flag = false;
    	}
    	return flag;
    }
    function updateOrderValidation(){
    	var value = $('#updateOrder').val();
    	var flag = false;
    	if(value != null && value != "" && value != "undefined"){
    		if(value == "0"){
    			$('#updateOrderMsg').html("Order Number Zero Not Allowed.");
    			flag = false;
    		}else{
    			if(ordersNoArr != null && ordersNoArr != "undefined"){
    				if(ordersNoArr.length > 0){
    					if(ordersNoArr.indexOf(parseInt(value)) != -1){
    						var indexVal = ordersNoArr[ordersNoArr.indexOf(parseInt(value))];
    						if(indexVal == parseInt(value)){
    							$('#updateActOrderNo').val(value);
        						$('#updateOrderMsg').html("");
        	        			flag = true;
    						}else{
    							$('#updateOrderMsg').html(value+ " Order Number Already Exists.");
        			    		flag = false;	
    						}
    					}else{
    						$('#updateActOrderNo').val(value);
    						$('#updateOrderMsg').html("");
    	        			flag = true;
    					}
        			}
    			}else{
    				$('#updateActOrderNo').val(value);
    				$('#updateOrderMsg').html("");
        			flag = true;
    			}
    		}
    	}else{
    		$('#updateOrderMsg').html("Required Field.");
    		flag = false;
    	}
    	return flag;
    }
    
    function updateActivity(){
    	var roleFlag = updateRoleValidation();
    	var orderFlag = updateOrderValidation();
//     	alert(roleFlag+"::"+orderFlag);
        if(roleFlag && orderFlag){
        	/* var updateActData = [];
        	updateActData.push({
				name: "activityId",
				value: $('#updateActId').val()
			});
        	updateActData.push({
				name : "orderNo",
				value : $('#updateActOrderNo').val()
			});
        	updateActData.push({
				name : "roleIds",
				value : $('#updateActRoles').val()
			}); */
			var form = $('#activityUpdateForm').serializeArray();
// 			form.push(updateActData);
			debugger;
			var saveData = $.ajax({
				url : $("#mainUrl").val()+ '/activityUpdate/saveUpdatedActivityData',
				type : 'POST',
				data : form,
			    success: function(result) {
			    	debugger;
			    	if (result.msgFlag === true){
			    		$.growl.notice({ message: result.mealsMsg});
//				    		displayMessage("success", e.mealsMsg);
						setTimeout(function(){
							   window.location.reload();
							}, 1000);
// 						location.reload(true);
					}else {
						$.growl.error({ message: result.mealsMsg});
//							displayMessage("error", e.mealsMsg);
					}
				}
			});
			saveData.error(function() { 
				$.growl.error({ message: "Something went wrong"});
//					displayMessage("error", "Something went wrong"); 
			});
        }
    	
    }
        </script>
    </body>
</html>










