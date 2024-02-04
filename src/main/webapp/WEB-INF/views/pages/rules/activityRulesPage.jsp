<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Activity Rules</title>
<script type="text/javascript">

$(function (){
	
	//$('#ruleTypeTr').hide();
	/* var lanArr = [];
	var lanSize = '${inLagList.size()}';
	for(var t=1; t<=lanSize; t++){
		$('#messageTr_'+t).hide();
	} */
	/* $('#staticanddynamicTr').hide();
	$('#destTr').hide();
	$('#sourceParamTr').hide();
	$('#destParamTr').hide();
// 	$('#messageTr').hide();
	$('#conditionTr').hide();
	$('#valueTr1').hide();
	$('#valueTr2').hide();
	$('#chkTr').hide();
	$('#parConditonTr').hide();
	$('#multParamTr').hide();
	$('#rangeTr').hide();
	$('#ruleValConTr').hide();
	$('#destMultiTr').hide();
	$('#chkTr2').hide();
	$('#selectBoxTr').hide();
	$('#dependentTr').hide();
	$('#depconditionTr').hide();
	$('#paramActionTr').hide();
	$('#depParamTr').hide();
	$('#depchkTr').hide();
	$('#depchkTr2').hide();
	$('#depselectBoxTr').hide();
	$('#depvalueTr1').hide();
	$('#depvalueTr2').hide();
	$('#deparamconditionTr').hide();
	$('#tbConditionTr').hide();
	$('#destTr').hide();
	 */
	var activity = '${selectedActivity}';
	if(activity != null && activity != "" && activity != "undefined"){
		var result = synchronousAjaxCall(mainUrl+ "/rules/getActivityRuleList/" + activity);
		$('#activityList').html(result);
	}
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
<div class="row">
	<div class="col-md-12 col-sm-12 ">
		<div class="x_panel">
			<div class="x_title">
				<h2><spring:message code="label.ruleSLink"></spring:message></h2>
				<ul class="nav navbar-right panel_toolbox">
					<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
					</li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<br>
				<c:url value="/rules/saveActivityRule" var="saveRule" />
				<form:form action="${saveRule}" method="post" id="rulesForm">
				<!-- End -->
				<input type="hidden" name="controlType" id="controlType">
				<input type="hidden" name="controlType2" id="controlType2">
				<input type="hidden" name="lanSize" id="lanSize" value="${inLagList.size()}">
				<input type="hidden" name="selectVal" id="selectVal" value="<spring:message code="label.ruleSelect"></spring:message>" />
				<input type="hidden" name="paramType" id="paramType" />
				<input type="hidden" name="depparamType" id="depparamType" />
				<input type="hidden" name="sbglobalId" id="sbglobalId" />
				<input type="hidden" name="lanId" id="lanId" value="${inl.id}">
				<input type="hidden" name="depActivities" id="depActivities">
				<input type="hidden" name="selectedActivity" id="selectedActivity" >
				
				<div style="width: 75%; margin-left: 10%;">
					<div class="form-group row elementCheck">
    					<label for="sourceActivity" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSourceActivity"></spring:message> :</label>
 						<div class="col-sm-5">
 							<select name="sourceActivity" id="sourceActivity" class="form-control validate" onchange="sourceActivityValidation('sourceActivity', 'sourceActivityMsg')">
								<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								<c:forEach items="${actList}" var="sa">
									<option value="${sa.globalActivity.id}">${sa.name}</option>
								</c:forEach>
							</select>
						</div>
					 </div>
					 <div id="ruleTypeTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="ruleType" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleType"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="ruleType" id="ruleType" class="form-control validate" onchange="ruleTypeValidation('ruleType', 'ruleTypeMsg')">
									<option value="0">---<spring:message code="label.ruleSelect"></spring:message>---</option>
<%-- 									<c:forEach items="${actList}" var="sa"> --%>
										<option value="con"><spring:message code="label.ruleCondition"></spring:message></option>
										<option value="req"><spring:message code="label.ruleValidation"></spring:message></option>
										<option value="Dep"><spring:message code="label.ruleDependent"></spring:message></option>
										<option value="DepAct"><spring:message code="label.ruleDeptAct"></spring:message></option>
<%-- 									</c:forEach> --%>
								</select>
							</div>
						</div>
					</div>
					<div id="dependentTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="applyRuleFor" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleActType"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="applyRuleFor" id="applyRuleFor" class="form-control validate" onchange="applyRuleForValidation('applyRuleFor', 'applyRuleForMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
									<option value="sameAct"><spring:message code="label.ruleSameAct"></spring:message></option>
									<option value="differentAct"><spring:message code="label.ruleDiffAct"></spring:message></option>
								</select>
							</div>
						</div>
					</div>
					<div id="destTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="destActivity" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleDestActivity"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="destActivity" id="destActivity" class="form-control validate" onchange="destActivityValidation('destActivity', 'destActivityMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="destParamTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="destRuleParam" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSourceActParam"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="destRuleParam" id="destRuleParam" class="form-control validate" onchange="destRuleParamParamValidation('destRuleParam', 'destRuleParamMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="ruleValConTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="validationCon" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleValCon"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="validationCon" id="validationCon" class="form-control validate" onchange="validationConValidation('validationCon', 'validationConMsg')">
										<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
										<option value="NOTEMPTY"><spring:message code="label.ruleNotEmpty"></spring:message></option>
										<option value="NUMERIC"><spring:message code="label.ruleNumerics"></spring:message></option>
										<option value="ALHABETS"><spring:message code="label.ruleCharacters"></spring:message></option>
										<option value="DISABLED"><spring:message code="label.ruledesabled"></spring:message></option>
										<option value="ALPHANUMERIC"><spring:message code="label.ruleBoth"></spring:message></option>
								</select>
							</div>
						</div>
					</div>
					<div id="conditionTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="validationCon" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleCondition"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="conditionVal" id="conditionVal" class="form-control validate" onchange="conditionValValidation('conditionVal', 'conditionValMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
									<option value="gt" id="gt"><spring:message code="label.ruleGraterThan"></spring:message></option>
									<option value="lt" id="lt"><spring:message code="label.ruleLessThan"></spring:message></option>
									<option value="eq" id="eq"><spring:message code="label.ruleEqual"></spring:message></option>
									<option value="ne" id="ne"><spring:message code="label.ruleNotEqual"></spring:message></option>
									<option value="ge" id="ge"><spring:message code="label.ruleGtEqual"></spring:message></option>
									<option value="le" id="le"><spring:message code="label.ruleLtEqual"></spring:message></option>
									<option value="ltAndgt" id="ltAndgt"><spring:message code="label.ruleLAndG"></spring:message></option>
									<option value="leAndge" id="leAndge"><spring:message code="label.ruleLEAndGE"></spring:message></option>
								 </select>
							</div>
						</div>
					</div>
					<div id="sourceParamTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="sourceRuleParam" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSourceActParam"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="sourceRuleParam" id="sourceRuleParam" class="form-control validate" onchange="sourceRuleParamValidation('sourceRuleParam', 'sourceRuleParamMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="deparamconditionTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="deparamconditionVal" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleCondition"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="deparamconditionVal" id="deparamconditionVal" class="form-control validation" onchange="depParamConditionValValidation('deparamconditionVal', 'deparamconditionValMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
									<option value="gt" id="gt"><spring:message code="label.ruleGraterThan"></spring:message></option>
									<option value="lt" id="lt"><spring:message code="label.ruleLessThan"></spring:message></option>
									<option value="eq" id="eq"><spring:message code="label.ruleEqual"></spring:message></option>
									<option value="ne" id="ne"><spring:message code="label.ruleNotEqual"></spring:message></option>
									<option value="ge" id="ge"><spring:message code="label.ruleGtEqual"></spring:message></option>
									<option value="le" id="le"><spring:message code="label.ruleLtEqual"></spring:message></option>
									<option value="ltAndgt" id="ltAndgt"><spring:message code="label.ruleLAndG"></spring:message></option>
									<option value="leAndge" id="leAndge"><spring:message code="label.ruleLEAndGE"></spring:message></option>
								 </select>
							</div>
						</div>
					</div>
					<div id="chkTr" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="chkcontainer" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSelectVaue"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="hidden" name="checkedVal" id="checkedVal">  
								<input type="hidden" name="checkedLspId" id="checkedLspId">
								<div id="chkcontainer"></div>
							</div>
						</div>
					</div>
					<div id="chkTr2" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="chkcontainer2" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSelectVaue"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="hidden" name="checkedVal2" id="checkedVal2">  
								<input type="hidden" name="checkedLspId2" id="checkedLspId2">
								<div id="chkcontainer2"></div>
								<div id="chkContainer2Msg" style="color: red;"></div>
							</div>
						</div>
					</div>
					<div id="selectBoxTr" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="selectboxVal" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSelectVaue"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<select name="selectboxVal" id="selectboxVal" class="form-control validate" onchange="selectBoxValidation('selectboxVal', 'selectboxValMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="valueTr1" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="valueOne" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.rulesVal1"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="text" name="valueOne" id="valueOne" class="form-control formfield validate" onkeyup="valueOneValidation('valueOne', 'valueOneMsg')">
							</div>
						</div>
					</div>
					<div id="valueTr2" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="valueTwo" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.rulesVal2"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="text" name="valueTwo" id="valueTwo" class="form-control formfield validate" onkeyup="valueTwoValidation('valueTwo', 'valueTwoMsg')">
							</div>
						</div>
					</div>
					<div id="depconditionTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="depconditionVal" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleCondition"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="depconditionVal" id="depconditionVal" class="form-control validate" onchange="dependentConditionValValidation('depconditionVal', 'depconditionValMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
									<option value="eq" id="eq"><spring:message code="label.ruleEqual"></spring:message></option>
									<option value="ne" id="ne"><spring:message code="label.ruleNotEqual"></spring:message></option>
									</select>
							</div>
						</div>
					</div>
					<div id="tbConditionTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="tbCondition" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleCondition"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="tbCondition" id="tbCondition" class="form-control validate" onchange="tbConditionValValidation('tbCondition', 'tbConditionMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
									<option value="true"><spring:message code="label.ruleTbTrue"></spring:message></option>
									<option value="false"><spring:message code="label.ruleTbFalse"></spring:message></option>
									</select>
							</div>
						</div>
					</div>
					<div id="paramActionTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="paramAction" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleparCon"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="paramAction" id="paramAction" class="form-control validate" onchange="paramActionValidation('paramAction', 'paramActionMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
									<option value="show"><spring:message code="label.ruleShow"></spring:message></option>
									<option value="hide"><spring:message code="label.ruleHide"></spring:message></option>
									<option value="enable"><spring:message code="label.ruleVisible"></spring:message></option>
									<option value="disable"><spring:message code="label.ruleDisable"></spring:message></option>
									<option value="setvalue"><spring:message code="label.ruleSetValue"></spring:message></option>
								</select>
							</div>
						</div>
					</div>
					<div id="destMultiTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="destActivityMultipule" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSelectDeptActs"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="destActivityMultipule" id="destActivityMultipule" class="form-control validate" multiple="multiple" onchange="destActivityMultipleValidation('destActivityMultipule', 'destActivityMultipuleMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="multParamTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="multiParam" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleParamSB"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="multiParam" id="multiParam" multiple="multiple" class="form-control validate" onchange="multiParamValidation('multiParam', 'multiParamMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="depParamTr" class="row_hd elementCheck">
						<div class="form-group row">
   							<label for="depParameter" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.cusActParmParm"></spring:message> :</label>
   							<div class="col-sm-5">
 								<select name="depParameter" id="depParameter" class="form-control validate" onchange="depParameterValidation('depParameter', 'depParameterMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="depchkTr" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="depchkcontainer" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSelectVaue"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="hidden" name="depcheckedVal" id="depcheckedVal">  
								<input type="hidden" name="depcheckedLspId" id="depcheckedLspId">
								<div id="depchkcontainer"></div>
							</div>
						</div>
					</div>
					<div id="depchkTr2" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="depchkcontainer2" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSelectVaue"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="hidden" name="depcheckedVal2" id="depcheckedVal2">  
								<input type="hidden" name="depcheckedLspId2" id="depcheckedLspId2">
								<div id="depchkcontainer2"></div>
							</div>
						</div>
					</div>	
					<div id="depselectBoxTr" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="depselectboxVal" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleSelectVaue"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<select name="depselectboxVal" id="depselectboxVal" class="form-control validate" onchange="dependentParamselectBoxValidation('depselectboxVal', 'depselectboxValMsg')">
									<option value="0">----<spring:message code="label.ruleSelect"></spring:message>----</option>
								</select>
							</div>
						</div>
					</div>
					<div id="depvalueTr1" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="depvalueOne" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.rulesVal1"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="text" name="depvalueOne" id="depvalueOne" class="form-control formfield validate" onkeyup="depValueOneValidation('depvalueOne', 'depvalueOneMsg')">
							</div>
						</div>
					</div>
					<div id="depvalueTr2" class="row_hd elementCheck">
						<div class="form-group row">
							<label for="depvalueTwo" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.rulesVal2"></spring:message> :</label>
   							<div class="col-sm-5">
	 							<input type="text" name="depvalueTwo" id="depvalueTwo" class="form-control formfield validate" onkeyup="depValueTwoValidation('depvalueTwo', 'depvalueTwoMsg')">
							</div>
						</div>
					</div>
					<c:forEach items="${inLagList}" var="inl" varStatus="st">
						<div id="messageTr_${st.count}" class="row_hd elementCheck">
							<div class="form-group row">
   							<label for="ruleMsg" class="col-sm-3 col-form-label" style="color: #212529;"><spring:message code="label.ruleMsg"></spring:message> ${inl.language} :</label>
	   							<div class="col-sm-5">
	   								<input type="text" name="ruleMsg" id="ruleMsg_${inl.id}"  class="form-control formfield validate" onkeyup="ruleMsgValidation('ruleMsg_${inl.id}', 'ruleMsg_${inl.id}_Msg')">
									<%-- <div id="value_${inl.id}_Msg" style="color: red;"></div> --%>
									<script type="text/javascript">
									   var count = '${st.count}';
									   if(count == 1)
											lanArr = [];
										lanArr.push('${inl.id}');
									    var lanid = $('#lanId').val();
									    if(lanid == null || lanid == "" || lanid == "undefined")
									    	$('#lanId').val('${inl.id}');
									    else $('#lanId').val(lanid+","+'${inl.id}');
									</script>
									<%-- <div id="ruleMsg_${inl.id}_Msg" style="color: red;"></div> --%>
								</div>
   							</div>
						</div>
					</c:forEach>
					<div class="form-group row">
  					   <div class="col-sm-10" align="center">
  							<input type="button" value="<spring:message code="label.submit"></spring:message>" id="submitId" class="btn btn-primary btn-sm" onclick="submitRulesForm()">
						</div>
					</div>
				</div>
				</form:form>
				<div id="activityList"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">

/* code for restricted space at all fields  */
/* $(function() {
    $('.formfield').on('keypress', function(e) {
        if (e.which == 32)
            return false;
    });
});
 */
 
 
 
 $('.row_hd').hide();
function hideAllConditions(){
	debugger;
	var flag = false;
	
	$('#ruleType').val(0);
	$('#validationCon').val(0);
	$('#sourceRuleParam').val(0);
	$('#validationCon').val(0);
	$('#destActivity').val(0);
	$('#rangeFrom').val(0);
	$('#toRange').val("");
	$('#destRuleParam').val(0);
	$('#paramAction').val(0);
	$('#multiParam').val(0);
	$('#valueOne').val("");
	$('#valueTwo').val("");
	$('#ruleMsg').val("");
	$('#staticanddynamic').val(0);
	$('#destActivityMultipule').empty();
	$('#sourceRuleParam').val(0);
	$('#selectboxVal').val(0);
	$('#applyRuleFor').val(0);
	$('#paramAction').val(0);
	$('#depParameter').val(0);
	$('#deparamconditionVal').val(0);
	$('#tbCondition').val(0);
	
	$('#ruleTypeTr').hide();
	$('#sourceParamTr').hide();
	$('#destMultiTr').hide();
	$('#conditionTr').hide();
	$('#valueTr1').hide();
	$('#valueTr2').hide();
	$('#destParamTr').hide();
	$('#chkTr').hide();
	$('#parConditonTr').hide();
	$('#multParamTr').hide();
	$('#rangeTr').hide();
	$('#ruleType').val("0");
	$('#ruleValConTr').hide();
	$('#chkTr2').hide();
	$('#selectBoxTr').hide();
	$('#dependentTr').hide();
	$('#depconditionTr').hide();
	$('#paramActionTr').hide();
	$('#multParamTr').hide();
	$('#depParamTr').hide();
	$('#deparamconditionTr').hide();
	$('#tbConditionTr').hide();
	$('#depchkTr').hide();
	$('#depchkcontainer').empty();
	$('#depchkTr2').hide();
	$('#depchkcontainer2').empty();
	$('#depselectBoxTr').hide();
	$('#depselectboxVal').val(0);
	$('#sourceRuleParam').val(0);
	$('#depconditionTr').hide();
	$('#depconditionVal').val(0);
	$('#paramActionTr').hide();
	$('#paramAction').val(0);
	$('#destMultiTr').hide();
	$('#destActivityMultipule').val(0);
	/* var lsize = $('#lanSize').val();
	if(lsize != null && lsize != "" && lsize != "undefined"){
		for(var s=1; s<=lsize; s++){
			$('#messageTr_'+s).hide();
		}
	} */
	$('.msr_tr').hide();
	
	return flag;
}
var actVal = "";
var rtypeVal = "";
function sourceActivityValidation(id, messageId){
	debugger;
	var sourceFlag = false;
	var flg = false;
	$('#activityList').empty();
	var value = $('#'+id).val();
	if(value != null && value != "0" && value != "undefined"){
		$('#selectedActivity').val(value);
		flg = true;
		var result = synchronousAjaxCall(mainUrl+ "/rules/getActivityRuleList/" + value);
		$('#activityList').html(result);
		if(actVal != value){
			sourceFlag = hideAllConditions();
			
			$('#ruleTypeTr').show();
			actVal = value;
			sourceFlag = ruleTypeValidation('ruleType', 'ruleTypeMsg');
		}else{
			$('#ruleTypeTr').show();
			sourceFlag = ruleTypeValidation('ruleType', 'ruleTypeMsg');
		}
	}else{
		flg = true;
		sourceFlag = hideAllConditions();
		
	}
	if(!flg){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		$('#'+id).attr("data-isvalid","true");
	}
	checkElementValidation($('#'+id));
	return sourceFlag;
}

var inruleVal = "";
function ruleTypeValidation(id, messageId){
	var ruleTypeFlag = false;
	var flg = false;
	var value = $('#'+id).val();
	if(value == null || value == "0" || value == "" || value == "undefined" ){
		flg = false;
		inruleVal ="";
		$('#ruleValConTr').hide();
		$('#validationCon').val(0);
		$('#sourceParamTr').hide();
		$('#sourceRuleParam').val(0);
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
			}
		}
		$('#conditionTr').hide();
		$('#conditionVal').val(0);
		$('#chkTr').hide();
		$('#chkcontainer').empty();
		$('#chkTr2').hide();
		$('#chkcontainer2').empty();
		$('#valueTr2').hide();
		$('#valueTwo').val("");
		$('#valueTr1').hide();
		$('#valueOne').val("");
		$('#selectBoxTr').hide();
		$('#destMultiTr').hide();
		$('#destActivityMultipule').val(0);
		$('#dependentTr').hide();
		$('#applyRuleFor').val(0);
		$('#depconditionTr').hide();
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#deparamconditionTr').hide();
		$('#deparamconditionVal').val(0);
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#tbConditionTr').hide();
		$('#tbCondition').val(0);
		$('#depchkTr').hide();
		$('#depcheckedVal').val("");
		$('#depcheckedLspId').val("");
		$('#depcheckedVal2').val("");
		$('#depcheckedLspId2').val("");
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depchkTr2').hide();
		$('#depchkcontainer2').empty();
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		$('#destTr').hide();
		$('#destActivity').val(0);
		$('#destParamTr').hide();
		$('#destRuleParam').val();
		$('#destTr').hide();
		$('#destActivity').val(0);
		/* $('#'+messageId).html("Required Field."); */
		/* $('#'+messageId).html('${validationText}'); */
		ruleTypeFlag = false;
	}else{
		flg = true;
		if(inruleVal == "")
			inruleVal = value;
		if(inruleVal != value && inruleVal != ""){
			ruleTypeFlag = hideAllConditions();
			$('#destTr').hide();
			$('#destActivity').val(0);
			inruleVal = value;
			$('#ruleTypeTr').show();
			$('#ruleType').val(inruleVal);
		}
		/* $('#'+messageId).html(""); */
		if(value == "req"){
			$('#conditionTr').show();
			$('#conditionVal').val(0);
			$('#ruleValConTr').show();
			$('#chkTr').hide();
			$('#chkcontainer').empty();
			$('#chkTr2').hide();
			$('#chkcontainer2').empty();
			$('#selectBoxTr').hide();
			$('#destMultiTr').hide();
			$('#destActivityMultipule').val(0);
			$('#depconditionTr').hide();
			$('#paramActionTr').hide();
			$('#paramAction').val(0);
			$('#multParamTr').hide();
			$('#multiParam').val(0);
			$('#depParamTr').hide();
			$('#depParameter').val(0);
			$('#deparamconditionTr').hide();
			$('#deparamconditionVal').val(0);
			$('#depvalueTr1').hide();
			$('#depvalueOne').val("");
			$('#valueTr1').hide();
			$('#valueOne').val("");
			$('#valueTr2').hide();
			$('#valueTwo').val("");
			$('#tbConditionTr').hide();
			$('#tbCondition').val(0);
			$('#depchkTr').hide();
			$('#depcheckedVal').val("");
			$('#depcheckedLspId').val("");
			$('#depcheckedVal2').val("");
			$('#depcheckedLspId2').val("");
			$('#depselectBoxTr').hide();
			$('#depselectboxVal').val(0);
			ruleTypeFlag = validationConValidation('validationCon', 'validationConMsg');
		}else if(value == "con"){
			$('#ruleValConTr').hide();
			$('#validationCon').val(0);
			$('#sourceParamTr').hide();
			$('#sourceRuleParam').val(0);
			$('#depvalueTr1').hide();
			$('#depvalueOne').val("");
			var lsize = $('#lanSize').val();
			$('#deparamconditionTr').hide();
			$('#deparamconditionVal').val(0);
			if(lsize != null && lsize != "" && lsize != "undefined"){
				for(var s=1; s<=lsize; s++){
					$('#messageTr_'+s).hide();
				}
			}
			$('#conditionTr').show();
			$('#destMultiTr').hide();
			$('#destActivityMultipule').val(0);
			$('#dependentTr').hide();
			$('#applyRuleFor').val(0);
			$('#depconditionTr').hide();
			$('#paramActionTr').hide();
			$('#paramAction').val(0);
			$('#multParamTr').hide();
			$('#multiParam').val(0);
			$('#depParamTr').hide();
			$('#depParameter').val(0);
			$('#valueTr1').hide();
			$('#valueOne').val("");
			$('#valueTr2').hide();
			$('#valueTwo').val("");
			$('#tbConditionTr').hide();
			$('#tbCondition').val(0);
			$('#depchkTr').hide();
			$('#depcheckedVal').val("");
			$('#depcheckedLspId').val("");
			$('#depcheckedVal2').val("");
			$('#depcheckedLspId2').val("");
			$('#depselectBoxTr').hide();
			$('#depselectboxVal').val(0);
			ruleTypeFlag = conditionValValidation('conditionVal', 'conditionValMsg');
			
		}else if(value == "Dep"){
			$('#ruleValConTr').hide();
			$('#validationCon').val(0);
			$('#chkTr').hide();
			$('#chkcontainer').empty();
			$('#chkTr2').hide();
			$('#chkcontainer2').empty();
			$('#selectBoxTr').hide();
			var lsize = $('#lanSize').val();
			if(lsize != null && lsize != "" && lsize != "undefined"){
				for(var s=1; s<=lsize; s++){
					$('#messageTr_'+s).hide();
				}
			}
			$('#conditionTr').hide();
			$('#conditionVal').val(0);
			$('#destMultiTr').hide();
			$('#destActivityMultipule').val(0);
			$('#dependentTr').show();
			ruleTypeFlag = applyRuleForValidation('applyRuleFor', 'applyRuleForMsg');
		}else if(value == "DepAct"){
			$('#ruleValConTr').hide();
			$('#validationCon').val(0);
			var lsize = $('#lanSize').val();
			if(lsize != null && lsize != "" && lsize != "undefined"){
				for(var s=1; s<=lsize; s++){
					$('#messageTr_'+s).hide();
				}
			}
			$('#conditionTr').hide();
			$('#conditionVal').val(0);
			$('#chkTr').hide();
			$('#chkcontainer').empty();
			$('#chkTr2').hide();
			$('#chkcontainer2').empty();
			$('#selectBoxTr').hide();
			$('#valueTr2').hide();
			$('#valueTwo').val("");
			$('#valueTr1').hide();
			$('#valueOne').val("");
			$('#destMultiTr').show();
			$('#dependentTr').hide();
			$('#applyRuleFor').val(0);
			$('#multParamTr').hide();
			$('#multiParam').val(0);
			$('#depParamTr').hide();
			$('#depParameter').val(0);
			$('#deparamconditionTr').hide();
			$('#deparamconditionVal').val(0);
			$('#depvalueTr1').hide();
			$('#depvalueOne').val("");
			$('#tbConditionTr').hide();
			$('#tbCondition').val(0);
			$('#depchkTr').hide();
			$('#depcheckedVal').val("");
			$('#depcheckedLspId').val("");
			$('#depcheckedVal2').val("");
			$('#depcheckedLspId2').val("");
			$('#depselectBoxTr').hide();
			$('#depselectboxVal').val(0);
			ruleTypeFlag = destActivityMultipleValidation('destActivityMultipule', 'destActivityMultipuleMsg');
		}
		
	}
	if(!flg){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		$('#'+id).attr("data-isvalid","true");
	}
	checkElementValidation($('#'+id));
	return ruleTypeFlag;		
			
}
function validationConValidation(id, messageId){
	var validateFlag = false;
	var flg=false;
	var value = $('#'+id).val();
	if(value != null && value != "0" && value != "undefined"){
		flg=true;
		/* $('#'+messageId).html(""); */
		$('#sourceParamTr').show();
		$('#chkTr').hide();
		$('#chkcontainer').empty();
		$('#chkTr2').hide();
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#conditionTr').hide();
		$('#conditionVal').val(0);
		$('#destMultiTr').hide();
		$('#destActivityMultipule').val(0);
		$('#dependentTr').hide();
		$('#applyRuleFor').val(0);
		$('#depconditionTr').hide();
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#deparamconditionTr').hide();
		$('#deparamconditionVal').val(0);
		$('#sourceRuleParam').val(0);
		$('#messageTr_1').hide();
		$('#messageTr_2').hide();
		var activityId = $('#sourceActivity').val();
	    var sourceParamVal = $('#sourceRuleParam').val();
	    if(sourceParamVal == null || sourceParamVal == "0" || sourceParamVal == "undefined"){
			if(activityId != null && activityId != "" && activityId != "undefined"){
				$('#sourceRuleParam').empty();
				var reuslt = synchronousAjaxCall(mainUrl+"/rules/getActivityParameters/"+activityId);
				var d = JSON.parse(reuslt);
				var selectVal = $('#selectVal').val();
				$('#sourceRuleParam').append('<option value="0">---'+selectVal+'---</option>');
				for(var i=0; i<d.length; i++){
					$('#sourceRuleParam').append('<option value='+d[i].parameterId+'>'+d[i].parameterName+'</option>');
				}
			}
		}
	    validateFlag = sourceRuleParamValidation('sourceRuleParam', 'sourceRuleParamMsg');
	}else{
		flg=false;
		/* $('#'+messageId).html("Required Field"); */
		/* $('#'+messageId).html('${validationText}'); */
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
			}
		}
		$('#sourceRuleParam').val(0);	
		$('#sourceParamTr').hide();
		$('#chkTr').hide();
		$('#chkcontainer').empty();
		$('#chkTr2').hide();
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#conditionTr').hide();
		$('#conditionVal').val(0);
		$('#valueTr2').hide();
		$('#valueTwo').val("");
		$('#valueTr1').hide();
		$('#valueOne').val("");
		$('#destMultiTr').hide();
		$('#destActivityMultipule').val(0);
		$('#dependentTr').hide();
		$('#applyRuleFor').val(0);
		$('#depconditionTr').hide();
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#deparamconditionTr').hide();
		$('#deparamconditionVal').val(0);
		$('#selectboxVal').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depchkTr2').hide();
		$('#depchkcontainer2').empty();
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		validateFlag = false;
	}
	if(!flg){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		$('#'+id).attr("data-isvalid","true");
	}
	checkElementValidation($('#'+id));
	return validateFlag;
}
var gvalArr = [];
var depgvalArr = [];


function sourceRuleParamValidation(id, messageId){
	debugger;
	var sourceParamFlag = false;
	var flg = false;
    // 	var values = $('#sourceRuleParam').val();
	var value = $('#'+id).val();
	if(value != null && value != "0" && value != "undefined"){
		flg = true;
		$('#deparamconditionVal').val(0);
		$('#valueOne').val("");
		$('#valueTwo').val("");
		$('#valueTr1').hide();
		$('#valueTr2').hide();
		$('#depconditionVal').val(0);
		$('#depconditionTr').hide();
		$('#paramAction').val(0);
		$('#paramActionTr').hide();
		$('#depParameter').val(0);
		$('#depParamTr').hide();
		$('#multiParam').val(0);
		$('#multParamTr').hide();
		$('#tbConditionTr').hide();
		$('#ruleMsg_1').val("");
		$('#ruleMsg_2').val("");
		
		/* $('#'+messageId).html(""); */
		var ruleVal = $('#ruleType').val();
		if(ruleVal == "req"){
			var lsize = $('#lanSize').val();
			if(lsize != null && lsize != "" && lsize != "undefined"){
				for(var s=1; s<=lsize; s++){
					$('#messageTr_'+s).show();
				}
			}
			if(lanArr.length > 0){
			    var lflag = true;
				for(var r=0; r<lanArr.length; r++){
					var rFlag = ruleMsgValidation('ruleMsg_'+lanArr[r],'ruleMsg_'+lanArr[r]+'_Msg');
					if(rFlag == false)
						lflag = false;
				}
				if(lflag)
					sourceParamFlag = true;
				else sourceParamFlag = false;
			}
		}else if(ruleVal == "con"){
			$('#paramType').val("");
			var spVal = $('#sourceRuleParam').val();
			if(spVal != null && spVal != "" && spVal != "undefined"){
				var paramResult = synchronousAjaxCall(mainUrl+"/rules/getActivityParameterType/"+spVal);
				if(paramResult != null && paramResult != "" && paramResult !="undefined"){
					var ctype = paramResult.controlType;
// 					var ctype = "SB";
					$('#paramType').val(ctype);
					if(ctype == "RB" || ctype == "CB" || ctype == "SB"){
						$('#valueTr2').hide();
						$('#valueTwo').val("");
						$('#valueTr1').hide();
						$('#valueOne').val("");
						if(ctype == "RB"){
							gvalArr = [];
							$('#chkTr').show();
							$('#chkTr2').hide();
							$('#rangeTr').hide();
							$('#chkcontainer').empty();
							$('#selectBoxTr').hide();
							$('#selectboxVal').val(0);
							$('#destMultiTr').hide();
							$('#destActivityMultipule').val(0);
							$('#dependentTr').hide();
							$('#applyRuleFor').val(0);
							$('#multParamTr').hide();
							$('#multiParam').val(0);
							$('#depParamTr').hide();
							$('#depParameter').val(0);
							$('#deparamconditionTr').hide();
							$('#deparamconditionVal').val(0);
							var messageId = "'"+"chkContainerMsg"+"'";
							var assignVal = "'"+"checkedVal"+"'";
							var assignlspIdVal = "'"+"checkedLspId"+"'";
							var valId = "";
							var gvStr = "";
							for (var i = 0; i < paramResult.gvdList.length; i++) {
								valId = paramResult.gvdList[i].valueId;
								gvStr += '<input type="radio" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
									+ paramResult.gvdList[i].valueName
									+ "@@"
									+ paramResult.gvdList[i].valueId
									+ '" onclick="globalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
									+ paramResult.gvdList[i].valueName
							        +'&nbsp;&nbsp;&nbsp;';
								  
								/* $('#chkcontainer')
										.append('<input type="radio" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
												+ paramResult.gvdList[i].valueName
												+ "@@"
												+ paramResult.gvdList[i].valueId
												+ '" onclick="globalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
												+ paramResult.gvdList[i].valueName)
										//  .append('<label for="chk_'+v[i].parameterId+'">'+v[i].valueName+'</label></div>')
										.append('&nbsp;&nbsp;&nbsp;'); */
								// 	.append('<br>');
								gvalArr.push(valId);
// 								sourceParamFlag = globalValsValidation('checkedVal','checkedLspId', 'chkContainerMsg');
							}
							if(gvStr != ''){
								$('#chkcontainer').append(gvStr);
								sourceParamFlag = globalValsValidation('checkedVal','checkedLspId', 'chkContainerMsg');
							}else sourceParamFlag = false;
							
						}else if(ctype == "CB"){
							gvalArr = [];
							$('#chkTr').hide();
							$('#chkTr2').show();
							$('#rangeTr').hide();
							$('#chkcontainer2').empty();
							$('#selectBoxTr').hide();
							$('#selectboxVal').val(0);
							$('#destMultiTr').hide();
							$('#destActivityMultipule').val(0);
							$('#dependentTr').hide();
							$('#applyRuleFor').val(0);
							$('#multParamTr').hide();
							$('#multiParam').val(0);
							$('#depParamTr').hide();
							$('#depParameter').val(0);
							$('#deparamconditionTr').hide();
							$('#deparamconditionVal').val(0);
							var messageId ="'"+"chkContainer2Msg"+"'";
							var valId = "";
							var assignVal = "'"+"checkedVal2"+"'";
							var assignlspIdVal = "'"+"checkedLspId2"+"'";
							var gvStr = "";
							for (var i = 0; i < paramResult.gvdList.length; i++) {
								valId = paramResult.gvdList[i].valueId;
								gvStr +='<input type="checkbox" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
								+ paramResult.gvdList[i].valueName
								+ "@@"
								+ paramResult.gvdList[i].valueId
								+ '" onclick="globalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
								+ paramResult.gvdList[i].valueName;
								+ '&nbsp;&nbsp;&nbsp;';
								/* $('#chkcontainer2')
										.append('<input type="checkbox" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
												+ paramResult.gvdList[i].valueName
												+ "@@"
												+ paramResult.gvdList[i].valueId
												+ '" onclick="globalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
												+ paramResult.gvdList[i].valueName)
										//  .append('<label for="chk_'+v[i].parameterId+'">'+v[i].valueName+'</label></div>')
										.append('&nbsp;&nbsp;&nbsp;'); */
								// 	.append('<br>');
								gvalArr.push(valId);
// 								sourceParamFlag = globalValsValidation('checkedVal2','checkedLspId2', 'chkContainer2Msg');
							}
							if(gvStr != ""){
								$('#chkcontainer2').append(gvStr);
								sourceParamFlag = globalValsValidation('checkedVal2','checkedLspId2', 'chkContainer2Msg');
							}else sourceParamFlag = false;
						}else{
							gvalArr = [];
							$('#chkTr').hide();
							$('#chkcontainer').empty();
							$('#chkTr2').hide();
							$('#rangeTr').hide();
							$('#chkcontainer2').empty();
							$('#selectBoxTr').show();
							$('#selectboxVal').empty();
							$('#destMultiTr').hide();
							$('#destActivityMultipule').val(0);
							$('#dependentTr').hide();
							$('#applyRuleFor').val(0);
							$('#multParamTr').hide();
							$('#multiParam').val(0);
							$('#depParamTr').hide();
							$('#depParameter').val(0);
							$('#deparamconditionTr').hide();
							$('#deparamconditionVal').val(0);
							var valId = "";
							var selectVal = $('#selectVal').val();
							$('#selectboxVal').append('<option value="0">---'+selectVal+'---</option>');
							var sbStr = '';
							for (var i = 0; i < paramResult.gvdList.length; i++) {
								valId = paramResult.gvdList[i].valueId;
								sbStr +='<option value='+valId+'>'+paramResult.gvdList[i].valueName+'</option>';
// 								$('#selectboxVal').append('<option value='+valId+'>'+paramResult.gvdList[i].valueName+'</option>');
								gvalArr.push(valId);
							}
							if(sbStr != ""){
								$('#selectboxVal').append(sbStr);
								sourceParamFlag = selectBoxValidation('selectboxVal', 'selectboxValMsg');
							}else sourceParamFlag = false;
						}
					}else{
						$('#chkTr').hide();
						$('#chkcontainer').empty();
						$('#chkTr2').hide();
						$('#rangeTr').hide();
						$('#chkcontainer2').empty();
						$('#selectBoxTr').hide();
						$('#selectboxVal').empty();
						$('#destMultiTr').hide();
						$('#destActivityMultipule').val(0);
						$('#dependentTr').hide();
						$('#applyRuleFor').val(0);
						$('#multParamTr').hide();
						$('#multiParam').val(0);
						$('#depParamTr').hide();
						$('#depParameter').val(0);
						var condVal = $('#conditionVal').val();
						if(condVal == "gt" || condVal == "lt" || condVal == "eq" || condVal == "ne"){
							$('#valueTr2').hide();
							$('#valueTwo').val("");
							$('#valueTr1').show();
							$('#valueOne').val("");
							sourceParamFlag = valueOneValidation('valueOne', 'valueOneMsg');
						}else if(condVal == "ge" || condVal == "le" || condVal == "ltAndgt" || condVal == "leAndge" ){
							$('#valueTr2').show();
							$('#valueTwo').val("");
							$('#valueTr1').show();
							$('#valueOne').val("");
							var valFlag1 = valueOneValidation('valueOne', 'valueOneMsg');
							var valFlag2 = valueTwoValidation('valueTwo', 'valueTwoMsg');
							if(valFlag1 && valFlag2)
								sourceParamFlag = true;
						}
					}
				}
			}
		}else if(ruleVal == "Dep"){
			$('#paramType').val("");
			var applyRuleForVal = $('#applyRuleFor').val();
			var spVal = "";
			if(applyRuleForVal == "sameAct"){
// 				spVal = $('#sourceRuleParam').val();
				spVal = value;
			}else spVal = $('#destRuleParam').val();
			
			if(spVal != null && spVal != "" && spVal != "undefined"){
				var paramResult = synchronousAjaxCall(mainUrl+"/rules/getActivityParameterType/"+spVal);
				if(paramResult != null && paramResult != "" && paramResult !="undefined"){
					var ctype = paramResult.controlType;
// 					var ctype = "SB";
              		$('#paramType').val(ctype);
					if(ctype == "RB" || ctype == "CB" || ctype == "SB"){
						$('#valueTr2').hide();
						$('#valueTwo').val("");
						$('#valueTr1').hide();
						$('#valueOne').val("");
						$('#deparamconditionTr').hide();
						$('#deparamconditionVal').val(0);
						if(ctype == "RB"){
							gvalArr = [];
							$('#chkTr').show();
							$('#chkTr2').hide();
							$('#rangeTr').hide();
							$('#chkcontainer').empty();
							$('#selectBoxTr').hide();
							$('#selectboxVal').val(0);
							$('#destMultiTr').hide();
							$('#destActivityMultipule').val(0);
							var lsize = $('#lanSize').val();
							if(lsize != null && lsize != "" && lsize != "undefined"){
								for(var s=1; s<=lsize; s++){
									$('#messageTr_'+s).hide();
								}
							}
							var messageId = "'"+"chkContainerMsg"+"'";
							var assignVal = "'"+"checkedVal"+"'";
							var assignlspIdVal = "'"+"checkedLspId"+"'";
							var valId = "";
							var gvStr ='';
							for (var i = 0; i < paramResult.gvdList.length; i++) {
								valId = paramResult.gvdList[i].valueId;
								gvStr += '<input type="radio" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
									  + paramResult.gvdList[i].valueName
									  + "@@"
								      + paramResult.gvdList[i].valueId
								      + '" onclick="dependentglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
								      + paramResult.gvdList[i].valueName
								      +'&nbsp;&nbsp;&nbsp;';
								/* $('#chkcontainer')
										.append('<input type="radio" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
												+ paramResult.gvdList[i].valueName
												+ "@@"
												+ paramResult.gvdList[i].valueId
												+ '" onclick="dependentglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
												+ paramResult.gvdList[i].valueName)
										//  .append('<label for="chk_'+v[i].parameterId+'">'+v[i].valueName+'</label></div>')
										.append('&nbsp;&nbsp;&nbsp;'); */
								// 	.append('<br>');
								gvalArr.push(valId);
// 								sourceParamFlag = dependentglobalValsValidation('checkedVal','checkedLspId', 'chkContainerMsg');
							}
							if(gvStr != ''){
								$('#chkcontainer').append(gvStr);
								sourceParamFlag = dependentglobalValsValidation('checkedVal','checkedLspId', 'chkContainerMsg');
							}else sourceParamFlag = false;
						}else if(ctype == "CB"){
							gvalArr = [];
							$('#chkTr').hide();
							$('#chkTr2').show();
							$('#rangeTr').hide();
							$('#chkcontainer2').empty();
							$('#selectBoxTr').hide();
							$('#selectboxVal').val(0);
							$('#destMultiTr').hide();
							$('#destActivityMultipule').val(0);
							var lsize = $('#lanSize').val();
							if(lsize != null && lsize != "" && lsize != "undefined"){
								for(var s=1; s<=lsize; s++){
									$('#messageTr_'+s).hide();
								}
							}
							var messageId ="'"+"chkContainer2Msg"+"'";
							var valId = "";
							var assignVal = "'"+"checkedVal2"+"'";
							var assignlspIdVal = "'"+"checkedLspId2"+"'";
							var gvStr = '';
							for (var i = 0; i < paramResult.gvdList.length; i++) {
								valId = paramResult.gvdList[i].valueId;
								gvStr +='<input type="checkbox" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
								      + paramResult.gvdList[i].valueName
								      + "@@"
								      + paramResult.gvdList[i].valueId
								      + '" onclick="dependentglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
								      + paramResult.gvdList[i].valueName
								      +'&nbsp;&nbsp;&nbsp;';
								/* $('#chkcontainer2')
										.append('<input type="checkbox" id="chk_'+paramResult.gvdList[i].valueId+'" name="chk_'+paramResult.lsvparamId+'" value="'
												+ paramResult.gvdList[i].valueName
												+ "@@"
												+ paramResult.gvdList[i].valueId
												+ '" onclick="dependentglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
												+ paramResult.gvdList[i].valueName)
										//  .append('<label for="chk_'+v[i].parameterId+'">'+v[i].valueName+'</label></div>')
										.append('&nbsp;&nbsp;&nbsp;'); */
								// 	.append('<br>');
								gvalArr.push(valId);
// 								sourceParamFlag = dependentglobalValsValidation('checkedVal2','checkedLspId2', 'chkContainer2Msg');
							}
							if(gvStr != ''){
								 $('#chkcontainer2').append(gvStr);
								sourceParamFlag = dependentglobalValsValidation('checkedVal2','checkedLspId2', 'chkContainer2Msg');
							}else sourceParamFlag = false;
						}else{
							gvalArr = [];
							$('#chkTr').hide();
							$('#chkcontainer').empty();
							$('#chkTr2').hide();
							$('#rangeTr').hide();
							$('#chkcontainer2').empty();
							$('#selectBoxTr').show();
							$('#selectboxVal').empty();
							$('#destMultiTr').hide();
							$('#destActivityMultipule').val(0);
							$('#deparamconditionTr').hide();
							$('#deparamconditionVal').val(0);
							var lsize = $('#lanSize').val();
							if(lsize != null && lsize != "" && lsize != "undefined"){
								for(var s=1; s<=lsize; s++){
									$('#messageTr_'+s).hide();
								}
							}
							var valId = "";
							var selectVal = $('#selectVal').val();
							var sbStr ='';
							$('#selectboxVal').append('<option value="0">---'+selectVal+'---</option>');
							for (var i = 0; i < paramResult.gvdList.length; i++) {
								valId = paramResult.gvdList[i].valueId;
								sbStr += '<option value='+valId+'>'+paramResult.gvdList[i].valueName+'</option>';
// 								$('#selectboxVal').append('<option value='+valId+'>'+paramResult.gvdList[i].valueName+'</option>');
								gvalArr.push(valId);
							}
							if(sbStr != ''){
								$('#selectboxVal').append(sbStr);
								sourceParamFlag = dependentselectBoxValidation('selectboxVal', 'selectboxValMsg');
							}sourceParamFlag = false;
						}
					}else{
						$('#conditionTr').hide();
						$('#chkTr').hide();
						$('#chkcontainer').empty();
						$('#chkTr2').hide();
						$('#rangeTr').hide();
						$('#chkcontainer2').empty();
						$('#selectBoxTr').hide();
						$('#selectboxVal').empty();
						$('#destMultiTr').hide();
						$('#destActivityMultipule').val(0);
						$('#deparamconditionTr').show();
						$('#deparamconditionVal').val(0);
						var lsize = $('#lanSize').val();
						if(lsize != null && lsize != "" && lsize != "undefined"){
							for(var s=1; s<=lsize; s++){
								$('#messageTr_'+s).hide();
							}
						}
						sourceParamFlag = depParamConditionValValidation('deparamconditionVal', 'deparamconditionValMsg');
					}
				}
			}
		}
	}else{
		flg = false;
		/* $('#'+messageId).html("Required Field."); */
		/* $('#'+messageId).html('${validationText}'); */
		sourceParamFlag = false;
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
			}
		}
		if(lanArr.length > 0){
		    var lflag = true;
			for(var r=0; r<lanArr.length; r++){
				 $('#ruleMsg_'+lanArr[r]).val("");
			}
		}
		$('#chkTr').hide();
		$('#chkcontainer').empty();
		$('#chkTr2').hide();
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#selectboxVal').val(0);
		$('#valueTr2').hide();
		$('#valueTwo').val("");
		$('#valueTr1').hide();
		$('#valueOne').val("");
		$('#destMultiTr').hide();
		$('#destActivityMultipule').val(0);
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depconditionTr').hide();
		$('#depconditionVal').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#deparamconditionTr').hide();
		$('#deparamconditionVal').val(0);
		$('#paramActionTr').hide();
		$('#paramAction').val("0");
		$('#depchkTr').hide();
		$('#depcheckedVal').val("");
		$('#depcheckedLspId').val("");
		$('#depcheckedVal2').val("");
		$('#depcheckedLspId2').val("");
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		$('#tbConditionTr').hide();
		$('#tbCondition').val(0);
		$('#chkTr2').hide();	
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#selectboxVal').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depchkTr2').hide();
		$('#depchkcontainer2').empty();
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
	}
	
	if(!flg){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		$('#'+id).attr("data-isvalid","true");
	}
	checkElementValidation($('#'+id));
	return sourceParamFlag;
}

function ruleMsgValidation(id, messageId){
    debugger;
   var ruleMsgFlag = false;
   var value = $('#'+id).val().trim();
   if(value != null && value != "" && value != "undefined"){
	   $('#'+id).attr("data-isvalid","true");
       ruleMsgFlag = true;
   }else{
	   $('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
       ruleMsgFlag = false;
   }
   return ruleMsgFlag;
}
var condition = "";
function conditionValValidation(id, messageId) {
	var conditionFlag = false;
	var flg = false;
	var value = $('#' + id).val();
	if(condition == "" || condition != value){
		$('#sourceRuleParam').val(0);
		condition = value;
	}
	if (value != null && value != "0" && value != "undefined") {
		flg = true;
		/* $('#' + messageId).html(""); */
		conditionFlag = true;
		$('#sourceParamTr').show();
		var activityId = $('#sourceActivity').val();
	    var sourceParamVal = $('#sourceRuleParam').val();
	    if(sourceParamVal == null || sourceParamVal == "0" || sourceParamVal == "undefined"){
			if(activityId != null && activityId != "" && activityId != "undefined"){
				$('#sourceRuleParam').empty();
				var reuslt = synchronousAjaxCall(mainUrl+"/rules/getActivityParameters/"+activityId);
				var d = JSON.parse(reuslt);
				var selectVal = $('#selectVal').val();
				$('#sourceRuleParam').append('<option value="0">---'+selectVal+'---</option>');
				var conditionVal = $('#conditionVal').val();
				if(conditionVal == "eq" || conditionVal == "ne"){
					for(var i=0; i<d.length; i++){
						$('#sourceRuleParam').append('<option value='+d[i].parameterId+'>'+d[i].parameterName+'</option>');
					}
				}else{
					for(var i=0; i<d.length; i++){
						if(d[i].controlType.controlType != "RB" && d[i].controlType.controlType != "CB" && d[i].controlType.controlType != "SB"){
							$('#sourceRuleParam').append('<option value='+d[i].parameterId+'>'+d[i].parameterName+'</option>');
						}
					}
				}
			}
		}
		conditionFlag = sourceRuleParamValidation('sourceRuleParam', 'sourceRuleParamMsg')
	
	}else{
		flg = false;
		$('#sourceParamTr').hide();
		$('#sourceRuleParam').val(0);
		$('#chkTr').hide();
		$('#chkcontainer').empty();
		$('#chkTr2').hide();
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#destMultiTr').hide();
		$('#destActivityMultipule').val(0);
		$('#dependentTr').hide();
		$('#applyRuleFor').val(0);
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#chkTr2').hide();	
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#selectboxVal').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depchkTr2').hide();
		$('#depchkcontainer2').empty();
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		$('#valueTr1').hide();
		$('#valueOne').val("");
		$('#valueTr2').hide();
		$('#valueTwo').val("");
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
				$('#ruleMsg_'+s).val("");
			}
		}
		/* $('#'+ messageId).html("Required Field."); */
		/* $('#'+messageId).html('${validationText}'); */
		conditionFlag = false;
	}	
	if(!flg){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}else{
		$('#'+id).attr("data-isvalid","true");
	}
	checkElementValidation($('#'+id));
	return conditionFlag;
}
function globalValsValidation(asignVal,asignIdVal, messageId) {
	$('#checkedVal').val("0");
	var gvFlag = false;
	var flag = false;
	
	for (var j = 0; j < gvalArr.length; j++) {
		if ($("#chk_" + gvalArr[j]).prop("checked")) {
			var chkVal = $("#chk_" + gvalArr[j]).val();
			if(chkVal != null && chkVal != "" && chkVal != "undefined"){
				var chkArr = chkVal.split("@@");
				$('#'+asignVal).val(chkArr[0]);
				$('#'+asignIdVal).val(chkArr[1]);
				var lsize = $('#lanSize').val();
				if(lsize != null && lsize != "" && lsize != "undefined"){
					for(var s=1; s<=lsize; s++){
						$('#messageTr_'+s).show();
					}
				}
				if(lanArr.length > 0){
				    var lflag = true;
					for(var r=0; r<lanArr.length; r++){
						var rFlag = ruleMsgValidation('ruleMsg_'+lanArr[r],'ruleMsg_'+lanArr[r]+'_Msg');
						if(rFlag == false)
							lflag = false;
					}
					if(lflag)
						gvFlag = true;
					else gvFlag = false;
				}
			}else{
				$('#'+asignVal).val("0");
				$('#'+asignIdVal).val("0");
			}
			flag = true;
		}
	}
	if (flag) {
		gvFlag = true;
		$('#'+id).attr("data-isvalid","true");
	} else {
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
			}
		}
		if(lanArr.length > 0){
		    var lflag = true;
			for(var r=0; r<lanArr.length; r++){
				$('#ruleMsg_'+lanArr[r]).val("");
			}
		}
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		gvFlag = false;
	}
	checkElementValidation($('#'+id));
	return gvFlag;
}

function selectBoxValidation(id, messageId){
	debugger;
	var sbFlag = false;
	var value = $('#'+id).val();
	if(value == null || value == "0" || value == "undefined"){
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
			}
		}
		if(lanArr.length > 0){
		    var lflag = true;
			for(var r=0; r<lanArr.length; r++){
				$('#ruleMsg_'+lanArr[r]).val("");
			}
		}
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		sbFlag = false;
	}else{
		$('#'+id).attr("data-isvalid","true");
		sbFlag = true;
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).show();
			}
		}
		if(lanArr.length > 0){
		    var lflag = true;
			for(var r=0; r<lanArr.length; r++){
				var rFlag = ruleMsgValidation('ruleMsg_'+lanArr[r],'ruleMsg_'+lanArr[r]+'_Msg');
				if(rFlag == false)
					lflag = false;
			}
			if(lflag)
				sbFlag = true;
			else sbFlag = false;
		}
	}
	
	checkElementValidation($('#'+id));
	return sbFlag;
}
function valueOneValidation(id, messageId) {
	var valOneFlag = false;
	var value = $('#' + id).val().trim();
	if (value != null && value != "" && value !="" && value != "undefined") {
		flg = true;
		$('#'+id).attr("data-isvalid","true");
		var lsize = $('#lanSize').val();
		var ruleTypeVal = $('#ruleType').val();
		if(ruleTypeVal.trim() != "Dep"){
			if(lsize != null && lsize != "" && lsize != "undefined"){
				for(var s=1; s<=lsize; s++){
					$('#messageTr_'+s).show();
				}
			}
			if(lanArr.length > 0){
			    var lflag = true;
				for(var r=0; r<lanArr.length; r++){
					var rFlag = ruleMsgValidation('ruleMsg_'+lanArr[r],'ruleMsg_'+lanArr[r]+'_Msg');
					if(rFlag == false)
						lflag = false;
				}
				if(lflag)
					valOneFlag = true;
				else valOneFlag = false;
			}
		}else{
			var condVal = $('#deparamconditionVal').val();
			if(condVal == "gt" || condVal == "lt" || condVal == "eq" || condVal == "ne"){
				$('#depconditionTr').show();
				$('#depconditionVal').val(0);
				
			}else if(condVal == "ge" || condVal == "le" || condVal == "ltAndgt" || condVal == "leAndge" ){
				$('#depconditionTr').hide();
				$('#depconditionVal').val(0);
			}
		}
	} else {
		flg = false;
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
			}
		}
		if(lanArr.length > 0){
		    var lflag = true;
			for(var r=0; r<lanArr.length; r++){
				$('#ruleMsg_'+lanArr[r]).val("");
			}
		}
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		valOneFlag = false;
		$('#depconditionTr').hide();
		$('#depconditionVal').val(0);
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#tbConditionTr').hide();
		$('#tbCondition').val(0);
		$('#tbConditionTr').hide();
		$('#tbCondition').val(0);
	}
	
	checkElementValidation($('#'+id));
	return valOneFlag;
}
function valueTwoValidation(id, messageId) {
	var valeTwoFlag = false;
	var value = $('#' + id).val().trim();
	if (value != null && value != "" && value !="" && value != "undefined") {
		$('#'+id).attr("data-isvalid","true");
		/* var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).show();
			}
		}
		if(lanArr.length > 0){
		    var lflag = true;
			for(var r=0; r<lanArr.length; r++){
				var rFlag = ruleMsgValidation('ruleMsg_'+lanArr[r],'ruleMsg_'+lanArr[r]+'_Msg');
				if(rFlag == false)
					lflag = false;
			}
			if(lflag)
				valeTwoFlag = true;
			else valeTwoFlag = false;
		} */
		var ruleTypeVal = $('#ruleType').val();
		if(ruleTypeVal.trim() == "Dep"){
			$('#tbConditionTr').show();
			$('#tbCondition').val(0);
		}
	} else {
		var lsize = $('#lanSize').val();
		if(lsize != null && lsize != "" && lsize != "undefined"){
			for(var s=1; s<=lsize; s++){
				$('#messageTr_'+s).hide();
			}
		}
		if(lanArr.length > 0){
		    var lflag = true;
			for(var r=0; r<lanArr.length; r++){
				$('#ruleMsg_'+lanArr[r]).val("");
			}
		}
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		valeTwoFlag = false;
		$('#tbConditionTr').hide();
		$('#tbCondition').val(0);
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#depParamTr').hide();
		$('#depParameter').val(0);
	}
	
	checkElementValidation($('#'+id));
	return valeTwoFlag;
}
function dependentglobalValsValidation(asignVal,asignIdVal, messageId) {
	debugger;
	$('#checkedVal').val("0");
	var gvFlag = false;
	var flag = false;
	for (var j = 0; j < gvalArr.length; j++) {
		if ($("#chk_" + gvalArr[j]).prop("checked")) {
			var chkVal = $("#chk_" + gvalArr[j]).val();
			if(chkVal != null && chkVal != "" && chkVal != "undefined"){
				var chkArr = chkVal.split("@@");
				$('#'+asignVal).val(chkArr[0]);
				$('#'+asignIdVal).val(chkArr[1]);
				$('#depconditionTr').show();
				gvFlag = dependentConditionValValidation('depconditionVal', 'depconditionValMsg');
			}else{
				$('#'+asignVal).val("0");
				$('#'+asignIdVal).val("0");
			}
			flag = true;
		}
	}
	if (flag) {
		gvFlag = true;
		$('#'+messageId).attr("data-isvalid","true");
	} else {
		$('#'+messageId).attr("data-errormessage","Required Field..!");
		$('#'+messageId).attr("data-isvalid","false");
		gvFlag = false;
	}
	return gvFlag;
}
function dependentselectBoxValidation(id, messageId){
	var sbFlag = false;
	var value = $('#'+id).val();
	if(value == null || value == "0" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		sbFlag = false;
	}else{
		$('#'+id).attr("data-isvalid","true");
		sbFlag = true;
	}
	return sbFlag;
}
function dependentValueOneValidation(id, messageId) {
	var valOneFlag = false;
	var value = $('#' + id).val();
	if (value != null && value != "" && value !="" && value != "undefined") {
		$('#'+id).attr("data-isvalid","true");
		valOneFlag = true;
	} else {
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		valOneFlag = false;
	}
	return valOneFlag;
}
function dependentValueTwoValidation(id, messageId) {
	var valeTwoFlag = false;
	var value = $('#' + id).val();
	if (value != null && value != "" && value !="" && value != "undefined") {
		$('#'+id).attr("data-isvalid","true");
		valeTwoFlag = true;
	} else {
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		valeTwoFlag = false;
	}
	return valeTwoFlag;
}
function dependentConditionValValidation(id, messageId){
	
	var flag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "0" && value != "undefined"){
		$('#'+id).attr("data-isvalid","true");
		$('#paramAction').val(0);
		$('#multParamTr').hide();
		$('#paramActionTr').show();
		flag = paramActionValidation('paramAction', 'paramActionMsg');
	}else{
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#depchkTr').hide();
		$('#depcheckedVal').val("");
		$('#depcheckedLspId').val("");
		$('#depcheckedVal2').val("");
		$('#depcheckedLspId2').val("");
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;	
	}
	
	checkElementValidation($('#'+id));
	return flag;
}

function paramActionValidation(id, messageId){
	
	
	var flg = false;
	var flag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "0" && value != "undefined"){
		flg = true;
		var activityId = "";
		var applyForVal = $('#applyRuleFor').val();
		if(applyForVal != null && applyForVal != "" && applyForVal != "0" && applyForVal != "undefined"){
			if(applyForVal != "sameAct")
				activityId = $('#destActivity').val();
			else
				activityId = $('#sourceActivity').val();
		}
		$('#'+id).attr("data-isvalid","true");
		if(value != "setvalue"){
			$('#multiParam').val(0);
			$('#multParamTr').show();
			$('#depParameter').val(0);
			$('#depParamTr').hide();
			$('#depvalueOne').val("");
			$('#depvalueTr1').hide();
			var multiParamVal = $('#multiParam').val();
			if(multiParamVal == null || multiParamVal == "0" || multiParamVal == "" || multiParamVal == "undefined"){
				$('#multiParam').empty();
				var mulParam = synchronousAjaxCall(mainUrl+ "/rules/getActivityParameters/" + activityId);
				var d = JSON.parse(mulParam);
				var selectVal = $('#selectVal').val();
				$('#multiParam').append('<option value="0">---'+selectVal+'---</option>');
				var sourceParamId = $('#sourceRuleParam').val();
				for (var i = 0; i < d.length; i++) {
					if (sourceParamId != d[i].parameterId)
						$('#multiParam').append(
								'<option value='+d[i].parameterId+' id='+d[i].parameterId+'>'
										+ d[i].parameterName + '</option>');
				}
			}
			flag = multiParamValidation('multiParam', 'multiParamMsg');
		}else{
			$('#multParamTr').hide();
			$('#multiParam').val(0);
			$('#depParamTr').show();
			var depParamVal = $('#depParameter').val();
			var souceParamVal = $('#sourceRuleParam').val();
			if(depParamVal == null || depParamVal == "0" || depParamVal == "" || depParamVal == "undefined"){
				$('#depParameter').empty();
				var depResult = synchronousAjaxCall(mainUrl+"/rules/getActivityParameters/"+activityId);
				var d = JSON.parse(depResult);
				var selectVal = $('#selectVal').val();
				$('#depParameter').append('<option value="0">---'+selectVal+'---</option>');
				for(var i=0; i<d.length; i++){
					if(souceParamVal != d[i].parameterId)
						$('#depParameter').append('<option value='+d[i].parameterId+' id='+d[i].parameterId+'>'+d[i].parameterName+'</option>');
				}
			}
			flag = depParameterValidation('depParameter', 'depParameterMsg');
		}
	}else{
		flg = false;
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#depchkTr').hide();
		$('#depcheckedVal').val("");
		$('#depcheckedLspId').val("");
		$('#depcheckedVal2').val("");
		$('#depcheckedLspId2').val("");
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		$('#depchkTr').hide();
		$('#depvalueOne').val("");
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;	
	}
	
	checkElementValidation($('#'+id));
	return flag;
}

function depParameterValidation(id, messageId){
	debugger;
	var flag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "0" && value !="undefined"){
		$('#'+id).attr("data-isvalid","true");
		$('#depparamType').val("");
		var spVal = $('#depParameter').val();
		if(spVal != null && spVal != "" && spVal != "undefined"){
			var paramResult = synchronousAjaxCall(mainUrl+"/rules/getActivityParameterType/"+spVal);
			if(paramResult != null && paramResult != "" && paramResult !="undefined"){
				var ctype = paramResult.controlType;
//					var ctype = "SB";
				$('#depparamType').val(ctype);
				if(ctype == "RB" || ctype == "CB" || ctype == "SB"){
					$('#depvalueTr2').hide();
					$('#depvalueTwo').val("");
					$('#depvalueTr1').hide();
					$('#depvalueOne').val("");
					if(ctype == "RB"){
						depgvalArr = [];
						$('#depchkTr').show();
						$('#depchkTr2').hide();
						$('#depchkcontainer').empty();
						$('#depselectBoxTr').hide();
						$('#depselectboxVal').val(0);
						$('#destMultiTr').hide();
						$('#destActivityMultipule').val(0);
						var lsize = $('#lanSize').val();
						if(lsize != null && lsize != "" && lsize != "undefined"){
							for(var s=1; s<=lsize; s++){
								$('#messageTr_'+s).hide();
							}
						}
						var messageId = "'"+"depchkContainerMsg"+"'";
						var assignVal = "'"+"depcheckedVal"+"'";
						var assignlspIdVal = "'"+"depcheckedLspId"+"'";
						var valId = "";
						var gvStr ='';
						for (var i = 0; i < paramResult.gvdList.length; i++) {
							valId = paramResult.gvdList[i].valueId;
							gvStr +='<input type="radio" id="depchk_'+paramResult.gvdList[i].valueId+'" name="depchk_'+paramResult.lsvparamId+'" value="'
								  + paramResult.gvdList[i].valueName
							      + "@@"
							      + paramResult.gvdList[i].valueId
							      + '" onclick="dependentParamglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
							      + paramResult.gvdList[i].valueName
							      +'&nbsp;&nbsp;&nbsp;';
							      
							/* $('#depchkcontainer')
									.append('<input type="radio" id="depchk_'+paramResult.gvdList[i].valueId+'" name="depchk_'+paramResult.lsvparamId+'" value="'
											+ paramResult.gvdList[i].valueName
											+ "@@"
											+ paramResult.gvdList[i].valueId
											+ '" onclick="dependentParamglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
											+ paramResult.gvdList[i].valueName)
									//  .append('<label for="chk_'+v[i].parameterId+'">'+v[i].valueName+'</label></div>')
									.append('&nbsp;&nbsp;&nbsp;'); */
							// 	.append('<br>');
							depgvalArr.push(valId);
// 							flag = dependentParamglobalValsValidation('depcheckedVal','depcheckedLspId', 'depchkContainerMsg');
						}
						if(gvStr != ''){
							$('#depchkcontainer').append(gvStr);
							flag = dependentParamglobalValsValidation('depcheckedVal','depcheckedLspId', 'depchkContainerMsg');
						}else flag = false;
					}else if(ctype == "CB"){
						depgvalArr = [];
						$('#depchkTr').hide();
						$('#depchkTr2').show();
						$('#depchkcontainer2').empty();
						$('#depselectBoxTr').hide();
						$('#depselectboxVal').val(0);
						$('#destMultiTr').hide();
						$('#destActivityMultipule').val(0);
						var lsize = $('#lanSize').val();
						if(lsize != null && lsize != "" && lsize != "undefined"){
							for(var s=1; s<=lsize; s++){
								$('#messageTr_'+s).hide();
							}
						}
						var messageId ="'"+"depchkContainer2Msg"+"'";
						var valId = "";
						var assignVal = "'"+"depcheckedVal2"+"'";
						var assignlspIdVal = "'"+"depcheckedLspId2"+"'";
						var gvStr = '';
						for (var i = 0; i < paramResult.gvdList.length; i++) {
							valId = paramResult.gvdList[i].valueId;
							gvStr += '<input type="checkbox" id="depchk_'+paramResult.gvdList[i].valueId+'" name="depchk_'+paramResult.lsvparamId+'" value="'
							      + paramResult.gvdList[i].valueName
							      + "@@"
							      + paramResult.gvdList[i].valueId
							      + '" onclick="dependentglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
							      + paramResult.gvdList[i].valueName
							      +'&nbsp;&nbsp;&nbsp;';
							      
							  /*  $('#depchkcontainer2')
									.append('<input type="checkbox" id="depchk_'+paramResult.gvdList[i].valueId+'" name="depchk_'+paramResult.lsvparamId+'" value="'
											+ paramResult.gvdList[i].valueName
											+ "@@"
											+ paramResult.gvdList[i].valueId
											+ '" onclick="dependentglobalValsValidation('+assignVal+","+assignlspIdVal+","+messageId+')" class="validate">'
											+ paramResult.gvdList[i].valueName)
									//  .append('<label for="chk_'+v[i].parameterId+'">'+v[i].valueName+'</label></div>')
									.append('&nbsp;&nbsp;&nbsp;'); */
							// 	.append('<br>');
							depgvalArr.push(valId);
// 							flag = dependentglobalValsValidation('depcheckedVal2','depcheckedLspId2', 'depchkContainer2Msg');
						}
						if(gvStr != ''){
							$('#depchkcontainer2').append(gvStr);
							flag = dependentglobalValsValidation('depcheckedVal2','depcheckedLspId2', 'depchkContainer2Msg');
						}else flag = false;
					}else{
						depgvalArr = [];
						$('#depchkTr').hide();
						$('#depchkcontainer').empty();
						$('#depchkTr2').hide();
						$('#depchkcontainer2').empty();
						$('#depselectBoxTr').show();
						$('#depselectboxVal').empty();
						$('#destMultiTr').hide();
						$('#destActivityMultipule').val(0);
						var lsize = $('#lanSize').val();
						if(lsize != null && lsize != "" && lsize != "undefined"){
							for(var s=1; s<=lsize; s++){
								$('#messageTr_'+s).hide();
							}
						}
						var valId = "";
						var selectVal = $('#selectVal').val();
						$('#selectboxVal').append('<option value="0">---'+selectVal+'---</option>');
						var sbStr ='';
						for (var i = 0; i < paramResult.gvdList.length; i++) {
							valId = paramResult.gvdList[i].valueId;
							sbStr += '<option value='+valId+'>'+paramResult.gvdList[i].valueName+'</option>';
// 							$('#depselectboxVal').append('<option value='+valId+'>'+paramResult.gvdList[i].valueName+'</option>');
							depgvalArr.push(valId);
						}
// 						flag = dependentParamselectBoxValidation('depselectboxVal', 'depselectboxValMsg');
					}
					if(sbStr != ''){
						$('#depselectboxVal').append(sbStr);
						flag = dependentParamselectBoxValidation('depselectboxVal', 'depselectboxValMsg');
					}else flag = false;
				}else{
					$('#depchkTr').hide();
					$('#depchkcontainer').empty();
					$('#conditionTr').hide();
					$('#destMultiTr').hide();
					$('#destActivityMultipule').val(0);
					var lsize = $('#lanSize').val();
					if(lsize != null && lsize != "" && lsize != "undefined"){
						for(var s=1; s<=lsize; s++){
							$('#messageTr_'+s).hide();
						}
					}
					var condVal = $('#depconditionVal').val();
					if(condVal == "gt" || condVal == "lt" || condVal == "eq" || condVal == "ne"){
						$('#depvalueTr2').hide();
						$('#depvalueTwo').val("");
						$('#depvalueTr1').show();
						$('#depvalueOne').val("");
						flag = depValueOneValidation('depvalueOne', 'depvalueOneMsg');
					}else if(condVal == "ge" || condVal == "le" || condVal == "ltAndgt" || condVal == "leAndge" ){
						$('#depvalueTr2').show();
						$('#depvalueTwo').val("");
						$('#depvalueTr1').show();
						$('#depvalueOne').val("");
						var valFlag1 = depValueOneValidation('depvalueOne', 'depvalueOneMsg');
						var valFlag2 = depValueTwoValidation('depvalueTwo', 'depvalueTwoMsg');
						if(valFlag1 && valFlag2)
							flag = true;
					}
					var tbCondition = $('#tbCondition').val();
					if(tbCondition != null && tbCondition != "0" && tbCondition != "" && tbCondition != "undefined"){
						$('#depvalueTr2').hide();
						$('#depvalueTwo').val("");
						$('#depvalueTr1').show();
						$('#depvalueOne').val("");
						flag = depValueOneValidation('depvalueOne', 'depvalueOneMsg');
					}
				}
			}
		}
	}else{
		$('#depchkTr').hide();
		$('#depcheckedVal').val("");
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}
	
	checkElementValidation($('#'+id));
}
function dependentParamglobalValsValidation(asignVal, asignIdVal, messageId){
	$('#depcheckedVal').val("0");
	var gvFlag = false;
	var flag = false;
	for (var j = 0; j < depgvalArr.length; j++) {
		if ($("#depchk_" + depgvalArr[j]).prop("checked")) {
			var chkVal = $("#depchk_" + depgvalArr[j]).val();
			if(chkVal != null && chkVal != "" && chkVal != "undefined"){
				var chkArr = chkVal.split("@@");
				$('#'+asignVal).val(chkArr[0]);
				$('#'+asignIdVal).val(chkArr[1]);
			}else{
				$('#'+asignVal).val("0");
				$('#'+asignIdVal).val("0");
			}
			flag = true;
		}
	}
	if (flag) {
		gvFlag = true;
		$('#'+id).attr("data-isvalid","true");
	} else {
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		gvFlag = false;
	}
	return gvFlag;
}
function depParamConditionValValidation(id, messageId){
	var falg = false;
	var flg = false;
	var value = $('#'+id).val();
	if(value == null || value == "" || value == "0" || value == "undefined"){
		flag = false;
		flg = false;
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		$('#valueTr1').hide();
		$('#valueOne').val("");
		$('#valueTr2').hide();
		$('#valueTwo').val("");
		$('#depconditionTr').hide();
		$('#depconditionVal').val(0);
		$('#depconditionTr').hide();
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#tbConditionTr').hide();
		$('#tbCondition').val();
		$("#multParamTr").hide();
		$('#multiParam').val(0);
		
	}else{
		flg = true;
		$('#'+id).attr("data-isvalid","true");
		var condVal = $('#deparamconditionVal').val();
		if(condVal == "gt" || condVal == "lt" || condVal == "eq" || condVal == "ne"){
			$('#valueTr2').hide();
			$('#valueTwo').val("");
			$('#valueTr1').show();
			$('#valueOne').val("");
			flag = valueOneValidation('valueOne', 'valueOneMsg');
		}else if(condVal == "ge" || condVal == "le" || condVal == "ltAndgt" || condVal == "leAndge" ){
			$('#valueTr2').show();
			$('#valueTwo').val("");
			$('#valueTr1').show();
			$('#valueOne').val("");
			var valFlag1 = valueOneValidation('valueOne', 'valueOneMsg');
			var valFlag2 = valueTwoValidation('valueTwo', 'valueTwoMsg');
			if(valFlag1 && valFlag2)
				flag = true;
		}
	}
	
	checkElementValidation($('#'+id));
}


function tbConditionValValidation(id, messageId){
	debugger;
	var flag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "0" && value != "undefined"){
		$('#'+id).attr("data-isvalid","true");
		$('#paramActionTr').show();
		$('#paramAction').val(0);
		$('#multiParam').val(0);
		$('#multParamTr').hide();
		$('#depParameter').val(0);
		$('#depParamTr').hide();
		flag = true;
	}else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#multParamTr').hide();
		$('#multiParam').val(0);
	}
	
	checkElementValidation($('#'+id));
}
function depValueOneValidation(id, messageId){
	var flag = false;
	var value = $('#'+id).val();
	if(value != null &&  value != "" && value != "0" && value != "undefined"){
		$('#'+id).attr("data-isvalid","true");
		flag = true;
	}else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}
	return flag;
}
function depValueTwoValidation(id, messageId){
	var flag = false;
	var value = $('#'+id).val();
	if(value != null &&  value != "" && value != "0" && value != "undefined"){
		$('#'+id).attr("data-isvalid","true");
		flag = true;
	}else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}
	return flag;
}
function destActivityValidation(id, messageId){
	var destFlag = false;
	var value = $('#'+id).val();
	if(value == null || value == "0" || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		destFlag = false;
		$('#destParamTr').hide();
		$('#destRuleParam').val(0);
		$('#deparamconditionTr').hide();
		$('#deparamconditionVal').val(0);
		$('#valueTr1').hide();
		$('#valueOne').val("");
		$('#valueTr2').hide();
		$('#valueTwo').val("");
		$('#tbConditionTr').hide();
		$('#tbCondition').val(0);
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#depvalueTr1').hide();
		$('#depvalueOne').val("");
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#chkTr').hide();
		$('#chkcontainer').empty();
		$('#chkTr2').hide();
		$('#chkcontainer2').empty();
		$('#depconditionTr').hide();
		$('#depconditionVal').val(0);
		$('#chkTr2').hide();	
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#selectboxVal').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depchkTr2').hide();
		$('#depchkcontainer2').empty();
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		var activityId = $('#sourceActivity').val();
		var desactVal = $('#destActivity').val();
		if(activityId != null && activityId != "" && activityId != "undefined"){
			var actResult = synchronousAjaxCall(mainUrl+ "/rules/getdestActivitiesList/" + activityId);
			if(actResult != null && actResult != "" && actResult != "undefined"){
				if(desactVal == null || desactVal == "" || desactVal == "0" || desactVal == "undefined"){
					$('#destActivity').empty();
					var selectVal = $('#selectVal').val();
					var d = JSON.parse(actResult);
						$('#destActivity').append('<option value="0">---'+selectVal+'---</option>');
					for(var i=0; i<d.length; i++){
						$('#destActivity').append('<option value='+d[i].id+'>'+d[i].name+'</option>');
					}
				}
			}
		}
	}else{
		$('#'+id).attr("data-isvalid","true");
		destFlag = true;
		$('#destParamTr').show();
		destFlag = destRuleParamParamValidation('destRuleParam', 'destRuleParamMsg');
	}
	checkElementValidation($('#'+id));
	return destFlag;
}
function destRuleParamParamValidation(id, messageId){
	var flag = false;
	var value = $('#'+id).val();
	if(value == null || value == "" || value == "0" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		var activityId = $('#sourceActivity').val();
	    var destRuleParam = $('#destRuleParam').val();
	    if(destRuleParam == null || destRuleParam == "0" ||  destRuleParam == "" || destRuleParam == "undefined"){
			if(activityId != null && activityId != "" && activityId != "undefined"){
				$('#destRuleParam').empty();
				var reuslt = synchronousAjaxCall(mainUrl+"/rules/getActivityParameters/"+activityId);
				var d = JSON.parse(reuslt);
				var selectVal = $('#selectVal').val();
				$('#destRuleParam').append('<option value="0">---'+selectVal+'---</option>');
				for(var i=0; i<d.length; i++){
					$('#destRuleParam').append('<option value='+d[i].parameterId+'>'+d[i].parameterName+'</option>');
				}
			}
		}
	    flag = sourceRuleParamValidation(id, messageId);
	}else{
		$('#'+id).attr("data-isvalid","true");
		flag = sourceRuleParamValidation(id, messageId);
		$('#depvalueTr1').hide();
		$('#depvalueOne').val(0);
	}
	
	checkElementValidation($('#'+id));
	return flag;
}
</script>
<script type="text/javascript">
function checkDropDownCondition(id, messageId){
	var flag = false;
	var value = $('#'+id).val();
	if(value == null || value == "0" || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}else{
		$('#'+id).attr("data-isvalid","true");
		flag = true;
	}
	return flag;
}
function texboxCondition(id, messageId){
	var tbFlag = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "0" && value !="undefined"){
		$('#'+id).attr("data-isvalid","true");
		tbFlag = true;
	}else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		tbFlag = false;
	}
	return tbFlag;
}
function selectBoxConditionCheck(id, messageId){
	var flag = false;
	$('#sbglobalId').val("");
	var value = $('#'+id).val();
	if(value == null && value != "" && value != "0" && value != "undefined"){
		$('#'+id).attr("data-isvalid","true");
		flag = true;
		$('#sbglobalId').val(value);
	}else{
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
	}
	return flag;
}
function gvValuesCheck(asignVal,asignIdVal, messageId) {
	var flag = false;
	for (var j = 0; j < gvalArr.length; j++) {
		if ($("#chk_" + gvalArr[j]).prop("checked")) {
			var chkVal = $("#chk_" + gvalArr[j]).val();
			if(chkVal != null && chkVal != "" && chkVal != "undefined"){
				var chkArr = chkVal.split("@@");
				$('#'+asignVal).val(chkArr[0]);
				$('#'+asignIdVal).val(chkArr[1]);
			}else{
				$('#'+asignVal).val("0");
				$('#'+asignIdVal).val("0");
			}
			flag = true;
		}
	}
	if (flag) {
		$('#'+messageId).attr("data-isvalid","true");
	} else {
		$('#'+messageId).attr("data-errormessage","Required Field..!");
		$('#'+messageId).attr("data-isvalid","false");
	}
	return flag;
}
function depgvValuesCheck(asignVal,asignIdVal, messageId) {
	var flag = false;
	for (var j = 0; j < depgvalArr.length; j++) {
		if ($("#depchk_" + depgvalArr[j]).prop("checked")) {
			var chkVal = $("#depchk_" + depgvalArr[j]).val();
			if(chkVal != null && chkVal != "" && chkVal != "undefined"){
				var chkArr = chkVal.split("@@");
				$('#'+asignVal).val(chkArr[0]);
				$('#'+asignIdVal).val(chkArr[1]);
			}else{
				$('#'+asignVal).val("0");
				$('#'+asignIdVal).val("0");
			}
			flag = true;
		}
	}
	if (flag) {
		$('#'+id).attr("data-isvalid","true");
	} else {
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
	}
	return flag;
}

function multiParamValidation(id, messageId) {
	var multiParFlag = false;
	var value = $('#' + id).val();
	if (value != null && value != "0" && value != "" && value != "undefined") {
		$('#'+id).attr("data-isvalid","true");
		multiParFlag = true;
	} else {
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		multiParFlag = false;
	}
	return multiParFlag;
}
function destActivityMultipleValidation(id, messageId){
	$('#depActivities').val("");
	var flag = false;
	var value = $('#'+id).val();
	if(value == null || value == "0" || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = false;
		var activityId = $('#sourceActivity').val();
		var muldact = synchronousAjaxCall(mainUrl+ "/rules/getdestActivitiesList/" + activityId);
		//alert(muldact);
		if(muldact != null && muldact != "" && muldact != "undefined"){
			$('#destActivityMultipule').empty();
			var selectVal = $('#selectVal').val();
			var d = JSON.parse(muldact);
				$('#destActivityMultipule').append('<option value="0">---'+selectVal+'---</option>');
			for(var i=0; i<d.length; i++){
				$('#destActivityMultipule').append('<option value='+d[i].id+'>'+d[i].name+'</option>');
			}
		}
	}else{
		$('#'+id).attr("data-isvalid","true");
		flag = true;
		$('#depActivities').val(value);
	}
	
	return flag;
}
var appRVal = "";
function applyRuleForValidation(id, messageId){
	var flag = false;
	var flg = false;
	var value = $('#'+id).val();
	if(value != null && value != "" && value != "0" && value !="undefined"){
		flg = true;
		$('#'+id).attr("data-isvalid","true");
		if(appRVal != value && appRVal != ""){
			if(value == "sameAct"){
				$('#sourceParamTr').show();
				$('#sourceRuleParam').val(0);
			}else{
				$('#destTr').show();
				$('#destActivity').val(0);
			}
			appRVal = value;
		}
		if(appRVal == "")
			appRVal = value;
		
		if(value == "sameAct"){
			$('#depvalueTr1').hide();
			$('#depvalueOne').val("");
			$('#depvalueTr2').hide();
			$('#depvalueTwo').val("");
			$('#destTr').hide();
			$('#destActivity').val(0);
			$('#destParamTr').hide();
			$('#destRuleParam').val(0);
			$('#sourceParamTr').show();
			var activityId = $('#sourceActivity').val();
		    var sourceParamVal = $('#sourceRuleParam').val();
		    if(sourceParamVal == null || sourceParamVal == "0" || sourceParamVal == "undefined"){
				if(activityId != null && activityId != "" && activityId != "undefined"){
					$('#sourceRuleParam').empty();
					var reuslt = synchronousAjaxCall(mainUrl+"/rules/getActivityParameters/"+activityId);
					var d = JSON.parse(reuslt);
					var selectVal = $('#selectVal').val();
					$('#sourceRuleParam').append('<option value="0">---'+selectVal+'---</option>');
					for(var i=0; i<d.length; i++){
						$('#sourceRuleParam').append('<option value='+d[i].parameterId+'>'+d[i].parameterName+'</option>');
					}
				}
			}
		    flag = sourceRuleParamValidation('sourceRuleParam', 'sourceRuleParamMsg');
		}else{
			$('#deparamconditionTr').hide();
			$('#deparamconditionVal').val(0);
			$('#sourceParamTr').hide();
			$('#sourceRuleParam').val(0);
			$('#destMultiTr').hide();
			$('#destActivityMultipule').val(0);
			$('#multParamTr').hide();
			$('#multiParam').val(0);
			$('#chkTr2').hide();
			$('#chkcontainer2').empty();
			$('#checkedVal2').val("");
			$('#checkedLspId2').val("");
			$('#chkTr').hide();
			$('#chkcontainer').empty();
			$('#checkedVal').val("");
			$('#checkedLspId').val("");
			$('#selectBoxTr').hide();
			$('#selectboxVal').val(0);
			$('#valueTr1').hide();
			$('#valueOne').val(0);
			$('#valueTr2').hide();
			$('#valueTwo').val(0);
			$('#depconditionTr').hide();
			$('#depconditionVal').val(0);
			$('#depParamTr').hide();
			$('#depParameter').val(0);
			$('#depParamTr').hide();
			$('#depParameter').val(0);
			$('#paramActionTr').hide();
			$('#paramAction').val("0");
			$('#depchkTr').hide();
			$('#depcheckedVal').val("");
			$('#depcheckedLspId').val("");
			$('#depcheckedVal2').val("");
			$('#depcheckedLspId2').val("");
			$('#depselectBoxTr').hide();
			$('#depselectboxVal').val(0);
			$('#depvalueTr1').hide();
			$('#depvalueOne').val("");
			$('#depvalueTr2').hide();
			$('#depvalueTwo').val("");
			$('#paramActionTr').hide();
			$('#paramAction').val("0");
			$('#tbConditionTr').hide();
			$('#tbCondition').val("0");
			$('#chkTr2').hide();	
			$('#chkcontainer2').empty();
			$('#selectBoxTr').hide();
			$('#selectboxVal').val(0);
			$('#depchkTr').hide();
			$('#depchkcontainer').empty();
			$('#depchkTr2').hide();
			$('#depchkcontainer2').empty();
			$('#depselectBoxTr').hide();
			$('#depselectboxVal').val(0);
			$('#destTr').show();
			flag = destActivityValidation('destActivity', 'destActivityMsg');
		}
		
	}else{
		flg = false;
		$('#sourceParamTr').hide();
		$('#sourceRuleParam').val(0);
		$('#depconditionTr').hide();
		$('#depconditionVal').val(0);
		$('#valueTr1').hide();
		$('#valueOne').val("");
		$('#valueTr2').hide();
		$('#valueTwo').val("");
		$('#chkTr').hide();
		$('#chkcontainer').empty();
		$('#paramActionTr').hide();
		$('#paramAction').val(0);
		$('#destTr').hide();
		$('#destActivity').val(0);
		$('#chkTr2').hide();	
		$('#chkcontainer2').empty();
		$('#selectBoxTr').hide();
		$('#selectboxVal').val(0);
		$('#depchkTr').hide();
		$('#depchkcontainer').empty();
		$('#depchkTr2').hide();
		$('#depchkcontainer2').empty();
		$('#depselectBoxTr').hide();
		$('#depselectboxVal').val(0);
		$('#destParamTr').hide();
		$('#destRuleParam').val(0);
		$('#multParamTr').hide();
		$('#multiParam').val(0);
		$('#depParamTr').hide();
		$('#depParameter').val(0);
		$('#deparamconditionTr').hide();
		$('#deparamconditionVal').val(0);
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		flag = true;
	}
	
	return flag;
}
</script>
<script type="text/javascript">
$('#submitId').prop("disabled", false);
function submitRulesForm() {
	debugger;
// 	$('#submitId').attr('disabled','disabled');
	var ruleVal = $('#ruleType').val();
	var actFlag = checkDropDownCondition('sourceActivity','sourceActivityMsg');
	if(ruleVal != null && ruleVal != "" && ruleVal != "0" && ruleVal != "undefined"){
		var finalFlag = false;
		if(ruleVal == "req"){
			var valFlag = checkDropDownCondition('validationCon', 'validationConMsg');
			var spFlag = checkDropDownCondition('sourceRuleParam', 'sourceRuleParamMsg');
			var texboxFlag = false;
			if(lanArr.length > 0){
			    var lflag = true;
				for(var r=0; r<lanArr.length; r++){
					var rFlag = texboxCondition('ruleMsg_'+lanArr[r],'ruleMsg_'+lanArr[r]+'_Msg');
					if(rFlag == false)
						lflag = false;
				}
				if(lflag)
					texboxFlag = true;
				else texboxFlag = false;
			}
// 			alert("Validation Flags : "+actFlag +"::"+ valFlag +"::"+ spFlag +"::"+ texboxFlag);
			if(actFlag && valFlag && spFlag && texboxFlag)
				finalFlag = true;
		}else if(ruleVal == "con"){
			var condVal = checkDropDownCondition('conditionVal', 'conditionValMsg');
			var spFlag = checkDropDownCondition('sourceRuleParam', 'sourceRuleParamMsg');
			var paramTypeVal = $('#paramType').val();
			var gvrdbFlag = false;
			var gvchkFlag = false;
			var gvSbFlag = false;
			var tbFalg = false;
			var messageFlag = false;
			if(paramTypeVal != null && paramTypeVal != "" && paramTypeVal != "undefined"){
				debugger;
				if(paramTypeVal == "RB"){
					gvrdbFlag = gvValuesCheck('checkedVal','checkedLspId', 'chkContainerMsg');
					gvchkFlag = true;
					tbFalg = true;
					gvSbFlag = true;
				}else if(paramTypeVal == "CB"){
					gvchkFlag = gvValuesCheck('checkedVal2','checkedLspId2', 'chkContainer2Msg');
					gvrdbFlag = true;
					tbFalg = true;
					gvSbFlag = true;
				}else if(paramTypeVal == "SB"){
					debugger;
					gvSbFlag = checkDropDownCondition('selectboxVal', 'selectboxValMsg');
					gvrdbFlag = true;
					gvchkFlag = true;
					tbFalg = true;
				}else{
					var conditionVal = $('#conditionVal').val();
					if(conditionVal != null && conditionVal != "" && conditionVal != "0" && conditionVal != "undefined"){
						gvrdbFlag = true;
						gvchkFlag = true;
						gvSbFlag = true;
						if(conditionVal == "lt" || conditionVal == "gt" || conditionVal == "eq" || conditionVal == "ne"){
							tbFalg = texboxCondition('valueOne', 'valueOneMsg');
						}else{
						   var flag1 = texboxCondition('valueOne', 'valueOneMsg');
							var flag2 = texboxCondition('valueTwo', 'valueTwoMsg');
							if(flag1 && flag2)
								tbFalg = true;
							else tbFalg = false;
						}
					}
				}
				if(lanArr.length > 0){
				    var lflag = true;
					for(var r=0; r<lanArr.length; r++){
						var rFlag = texboxCondition('ruleMsg_'+lanArr[r],'ruleMsg_'+lanArr[r]+'_Msg');
						if(rFlag == false)
							lflag = false;
					}
					if(lflag)
						messageFlag = true;
					else messageFlag = false;
				}
			}
			if(actFlag && condVal && spFlag && gvrdbFlag && gvchkFlag && gvSbFlag && tbFalg && messageFlag)
				finalFlag = true;
		}else if(ruleVal == "DepAct"){
			var destMultiActFlag = checkDropDownCondition('destActivityMultipule', 'destActivityMultipuleMsg');
			if(actFlag && destMultiActFlag)
				finalFlag = true;
		}else if(ruleVal == "Dep"){
			var applyRuleForVal = $('#applyRuleFor').val();
			if(applyRuleForVal == "differentAct"){
				var destActVal = $('#destActivity').val();
				var destFlag = false;
				var destRuleParam = false;
				if(destActVal == null || destActVal == "" || destActVal == "0" || destActVal == "undefined"){
					destFlag = checkDropDownCondition('destActivity', 'destActivityMsg');
				}else destFlag = true;
				var destParamVal = $('#destRuleParam').val();
				if(destParamVal == null || destParamVal == "" || destParamVal == "0" || destParamVal == "undefined"){
					destRuleParam = checkDropDownCondition('destRuleParam', 'destRuleParamMsg');
				}else{
					var gvrdbFlag = false;
					var gvchkFlag = false;
					var gvSbFlag = false;
					var tbFlag = false;
					var actionFlag = false;
					var tbConditionFlag = false;
					var rbConditionFlag = false;
					var paramTypeVal = $('#paramType').val();
					if(paramTypeVal != null && paramTypeVal != "" && paramTypeVal != "undefined"){
						if(paramTypeVal == "RB"){
							gvrdbFlag = gvValuesCheck('checkedVal','checkedLspId', 'chkContainerMsg');
							gvchkFlag = true;
							tbFlag = true;
							gvSbFlag = true;
							tbConditionFlag = true;
							rbConditionFlag = checkDropDownCondition('depconditionVal', 'depconditionValMsg');
						}else if(paramTypeVal == "CB"){
							gvchkFlag = gvValuesCheck('checkedVal2','checkedLspId2', 'chkContainer2Msg');
							gvrdbFlag = true;
							tbFlag = true;
							gvSbFlag = true;
							tbConditionFlag = true;
						}else if(paramTypeVal == "SB"){
							gvSbFlag = selectBoxConditionCheck('selectboxVal', 'selectboxValMsg');
							gvrdbFlag = true;
							gvchkFlag = true;
							tbFlag = true;
						    tbConditionFlag = true;
						    rbConditionFlag = checkDropDownCondition('depconditionVal', 'depconditionValMsg');
						}else{
							$('#destActivityMultipule').val(0);
                           var conditionVal = $('#deparamconditionVal').val();
							if(conditionVal == null || conditionVal == "" || conditionVal == "0" || conditionVal == "undefined"){
								rbConditionFlag = checkDropDownCondition('deparamconditionVal', 'deparamconditionValMsg');
							}else{
								rbConditionFlag = true;
								gvrdbFlag = true;
								gvchkFlag = true;
								gvSbFlag = true;
								if(conditionVal == "lt" || conditionVal == "gt" || conditionVal == "eq" || conditionVal == "ne"){
									tbFlag = texboxCondition('valueOne', 'valueOneMsg');
									tbConditionFlag = checkDropDownCondition('depconditionVal', 'depconditionValMsg')
								}else{
								   var flag1 = texboxCondition('valueOne', 'valueOneMsg');
									var flag2 = texboxCondition('valueTwo', 'valueTwoMsg');
									if(flag1 && flag2)
										tbFlag = true;
									tbConditionFlag = checkDropDownCondition('tbCondition', 'tbConditionMsg');
								}
							}
						}
					}
					var actionVal = $('#paramAction').val();
					if(actionVal == null || actionVal == "" || actionVal == "0" || actionVal == "undefined"){
						actionFlag = checkDropDownCondition('paramAction', 'paramActionMsg');
					}else actionFlag = true;
					if(gvrdbFlag && gvchkFlag && gvSbFlag && tbFlag && rbConditionFlag && actionFlag && tbConditionFlag){
						if(actionVal != "setvalue"){
							var multParamVal = $('#multiParam').val();
							if(multParamVal != null && multParamVal != "" && multParamVal != "0" && multParamVal != "undefined"){
								destRuleParam = checkDropDownCondition('multiParam', 'multiParamMsg');
							}else destRuleParam = true;
						}else{
							var depParamVal = $('#depParameter').val();
							var depparamTypeVal = $('#depparamType').val();
							var dpFlag = false;
							var depgvSbFlag = false;
							var depgvrdbFlag = false;
							var depgvchkFlag = false;
							var deptbFalg = false;
							if(depParamVal == null || depParamVal == "" || depParamVal == "0" || depParamVal == "undefined"){
								dpFlag = checkDropDownCondition('depParameter', 'depParameterMsg');
							}else dpFlag = true;
							if(depparamTypeVal != null && depparamTypeVal != "" && depparamTypeVal != "undefined"){
								if(depparamTypeVal == "RB"){
									depgvrdbFlag = depgvValuesCheck('depcheckedVal','depcheckedLspId','depchkContainerMsg');
									depgvchkFlag = true;
									deptbFalg = true;
									depgvSbFlag = true;
								}else if(depparamTypeVal == "CB"){
									depgvchkFlag = depgvValuesCheck('depcheckedVal2','depcheckedLspId2','depchkContainer2Msg');
									depgvrdbFlag = true;
									deptbFalg = true;
									depgvSbFlag = true;
								}else if(depparamTypeVal == "SB"){
									depgvSbFlag = selectBoxConditionCheck('depselectboxVal', 'depselectboxValMsg');
									depgvrdbFlag = true;
									depgvchkFlag = true;
									deptbFalg = true;
								}else{
									depgvSbFlag = true;
									depgvrdbFlag = true;
									depgvchkFlag = true;
									deptbFalg = true;
									deptbFalg = texboxCondition('depvalueOne', 'depvalueOneMsg');
								}
							}
							if(dpFlag && depgvSbFlag && depgvrdbFlag && depgvchkFlag && deptbFalg)
								destRuleParam = true;
						}
					}
				}
				
				if(destFlag  && destRuleParam)
					finalFlag = true;
			}else if(applyRuleForVal == "sameAct"){
				var sourceParamFlag = false;
				var parmActionFlag = false;
				var sourceParamVal = $('#sourceRuleParam').val();
				if(sourceParamVal == null || sourceParamVal == "" || sourceParamVal == "0" || sourceParamVal == "undefined"){
					sourceParamFlag = checkDropDownCondition('sourceRuleParam', 'sourceRuleParamMsg');
				}else{
					var gvrdbFlag = false;
					var gvchkFlag = false;
					var tbFalg = false;
					var tbConditionFlag = false;
					var paramFlag = false;
					var rbconFlag = false;
					var paramTypeVal = $('#paramType').val();
					if(paramTypeVal != null && paramTypeVal != "" && paramTypeVal != "undefined"){
						if(paramTypeVal == "RB"){
							gvrdbFlag = gvValuesCheck('checkedVal','checkedLspId', 'chkContainerMsg');
							gvchkFlag = true;
							tbFalg = true;
							gvSbFlag = true;
							tbConditionFlag = true;
							var rbconVal = $('#depconditionVal').val();
							if(rbconVal == null || rbconVal == "" || rbconVal == "0" || rbconVal == "" || rbconVal == "undefined"){
								rbconFlag = checkDropDownCondition('depconditionVal', 'depconditionValMsg');
							}else rbconFlag = true;
						}else if(paramTypeVal == "CB"){
							gvchkFlag = gvValuesCheck('checkedVal2','checkedLspId2', 'chkContainer2Msg');
							gvrdbFlag = true;
							tbFalg = true;
							gvSbFlag = true;
							tbConditionFlag = true;
							var rbconVal = $('#depconditionVal').val();
							if(rbconVal == null || rbconVal == "" || rbconVal == "0" || rbconVal == "" || rbconVal == "undefined"){
								rbconFlag = checkDropDownCondition('depconditionVal', 'depconditionValMsg');
							}else rbconFlag = true;
						}else if(paramTypeVal == "SB"){
							gvSbFlag = selectBoxConditionCheck('selectboxVal', 'selectboxValMsg');
							gvrdbFlag = true;
							gvchkFlag = true;
							tbFalg = true;
						    tbConditionFlag = true;
						    rbconFlag = true;
						}else{
							$('#destActivityMultipule').val(0);
							var conditionVal = $('#deparamconditionVal').val();
							
							if(conditionVal != null && conditionVal != "" && conditionVal != "0" && conditionVal != "undefined"){
								gvrdbFlag = true;
								gvchkFlag = true;
								gvSbFlag = true;
								rbconFlag = true;
								if(conditionVal == "lt" || conditionVal == "gt" || conditionVal == "eq" || conditionVal == "ne"){
									tbFalg = texboxCondition('valueOne', 'valueOneMsg');
									tbConditionFlag = checkDropDownCondition('depconditionVal', 'depconditionValMsg');
								}else{
								   var flag1 = texboxCondition('valueOne', 'valueOneMsg');
									var flag2 = texboxCondition('valueTwo', 'valueTwoMsg');
									if(flag1 && flag2)
										tbFalg = true;
									else tbFalg = false;
									tbConditionFlag = checkDropDownCondition('tbCondition', 'tbConditionMsg');
								}
							}
						}
					}
					if(rbconFlag && gvrdbFlag && gvchkFlag && tbFalg && tbConditionFlag)
						sourceParamFlag = true;
					
				}
				var parmActVal = $('#paramAction').val();
				if(parmActVal == null || parmActVal == "" || parmActVal == "0" || parmActVal == "undefined"){
					parmActionFlag = checkDropDownCondition('paramAction', 'paramActionMsg');
				}else{
					if(parmActVal != "setvalue"){
						var multParamVal = $('#multiParam').val();
						if(multParamVal == null && multParamVal == "" || multParamVal == "0" || multParamVal == "undefined"){
							parmActionFlag = checkDropDownCondition('multiParam', 'multiParamMsg');
						}else parmActionFlag = true;
					}else if(parmActVal == "setvalue"){
						var depparamTypeVal = $('#depparamType').val();
						var depgvSbFlag = false;
						var depgvrdbFlag = false;
						var depgvchkFlag = false;
						var deptbFalg = false;
						if(depparamTypeVal != null && depparamTypeVal != "" && depparamTypeVal != "undefined"){
							if(depparamTypeVal == "RB"){
								depgvrdbFlag = depgvValuesCheck('depcheckedVal','depcheckedLspId','depchkContainerMsg');
								depgvchkFlag = true;
								deptbFalg = true;
								depgvSbFlag = true;
							}else if(depparamTypeVal == "CB"){
								depgvchkFlag = depgvValuesCheck('depcheckedVal2','depcheckedLspId2','depchkContainer2Msg');
								depgvrdbFlag = true;
								deptbFalg = true;
								depgvSbFlag = true;
							}else if(depparamTypeVal == "SB"){
								depgvSbFlag = selectBoxConditionCheck('depselectboxVal', 'depselectboxValMsg');
								depgvrdbFlag = true;
								depgvchkFlag = true;
								deptbFalg = true;
							}else{
								depgvSbFlag = true;
								depgvrdbFlag = true;
								depgvchkFlag = true;
								deptbFalg = true;
								deptbFalg = texboxCondition('depvalueOne', 'depvalueOneMsg');
							}
						}
						if(depgvSbFlag && depgvrdbFlag && depgvchkFlag && deptbFalg)
							parmActionFlag = true;
					}
				}
				if(sourceParamFlag && parmActionFlag){
					finalFlag = true;	
				}
			}
		}
		
	}
	
	if(validateElements($("#rulesForm"))){
		 $('#submitId').attr('disabled','disabled');
		 $(".loader").show();
		$('#rulesForm').submit();
	}else{
		$('#submitId').prop("disabled", false);
	}
}




</script>
</body>
</html>