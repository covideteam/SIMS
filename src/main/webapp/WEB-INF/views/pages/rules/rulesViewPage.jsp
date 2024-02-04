<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

</head>
<body oncontextmenu="return false;">
  <ul class="nav nav-tabs">
    <li class='nav-item'>
      <a class="nav-link active" data-toggle="tab" href="#home"><spring:message code="label.ruleValidation"></spring:message></a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#menu1"><spring:message code="label.ruleCondition"></spring:message></a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#menu2"><spring:message code="label.ruleDependent"></spring:message></a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#menu3"><spring:message code="label.ruleSelectDeptActs"></spring:message></a>
    </li>
  </ul>

  <!-- Tab panes -->
  <div style="height: 400px; overflow: auto;">
	<div class="tab-content">
		<div id="home" class='tab-pane container fade in show active'>
			<br>
			<table class="table table-bordered table-striped"
				style="width: 100%;">
				<thead>
					<tr style="position: sticky; top: 3%; height: 95%; background: #fff; font-size: 13px;">
						<th><spring:message code="label.ruleSourceActivity"></spring:message></th>
						<th><spring:message code="label.ruleValCon"></spring:message></th>
						<th><spring:message code="label.ruleSourceActParam"></spring:message></th>
						<th><spring:message code="label.ruleTabMsg"></spring:message></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rules.valRulesList}" var="rule">
						<tr style="font-size: 13px;">
							<td>${rule.activityName}</td>
							<td>
								<c:if test="${rule.condition eq 'NOTEMPTY'}"><spring:message code="label.ruleNotEmpty"></spring:message></c:if>
								<c:if test="${rule.condition eq 'NUMERIC'}"><spring:message code="label.ruleNumerics"></spring:message></c:if>
								<c:if test="${rule.condition eq 'ALHABETS'}"><spring:message code="label.ruleCharacters"></spring:message></c:if>
								<c:if test="${rule.condition eq 'DISABLED'}"><spring:message code="label.ruledesabled"></spring:message></c:if>
								<c:if test="${rule.condition eq 'ALPHANUMERIC'}"><spring:message code="label.ruleBoth"></spring:message></c:if>
<%-- 								${rule.condition} --%>
							</td>
							<td>${rule.sourceParamName}</td>
							<td>${rule.message}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="menu1" class='tab-pane container fade'>
    	<table class="table table-bordered table-striped" style="width: 100%;">
    		<thead>
    			<tr style=" position: sticky; top: 3%; height:95%; background: #fff; font-size: 13px;">
    				<th><spring:message code="label.ruleSourceActivity"></spring:message></th>
    				<th><spring:message code="label.ruleValCon"></spring:message></th>
    				<th><spring:message code="label.ruleSourceActParam"></spring:message></th>
    				<th><spring:message code="label.rulesValue"></spring:message></th>
    				<th><spring:message code="label.rulesVal1"></spring:message></th>
    				<th><spring:message code="label.rulesVal2"></spring:message></th>
    				<th><spring:message code="label.ruleTabMsg"></spring:message></th>
    			</tr>
			</thead>
    		<tbody>
    			<c:forEach items="${rules.crdtoList}" var="rule">
    				<tr style="font-size: 13px;">
    					<td>${rule.sourceActivityName}</td>
    					<td>
    						<c:if test="${rule.condition eq 'gt'}"><spring:message code="label.ruleGraterThan"></spring:message></c:if>
							<c:if test="${rule.condition eq 'lt'}"><spring:message code="label.ruleLessThan"></spring:message></c:if>
							<c:if test="${rule.condition eq 'eq'}"><spring:message code="label.ruleEqual"></spring:message></c:if>
							<c:if test="${rule.condition eq 'ne'}"><spring:message code="label.ruleNotEqual"></spring:message></c:if>
							<c:if test="${rule.condition eq 'le'}"><spring:message code="label.ruleLtEqual"></spring:message></c:if>
							<c:if test="${rule.condition eq 'ge'}"><spring:message code="label.ruleGtEqual"></spring:message></c:if>
							<c:if test="${rule.condition eq 'ltAndgt'}"><spring:message code="label.ruleLAndG"></spring:message></c:if>
							<c:if test="${rule.condition eq 'leAndge'}"><spring:message code="label.ruleLEAndGE"></spring:message></c:if>
<%--     						${rule.condition} --%>
    					</td>
    					<td>${rule.sourceParameterName}</td>
    					<td>${rule.lsgvName }</td>
    					<td>${rule.firstValue}</td>
    					<td>${rule.secondValue}</td>
    					<td>${rule.lsMessage}</td>
    				</tr>
    			</c:forEach>
    		</tbody>
    	</table>
     </div>
     <div id="menu2" class='tab-pane container fade'><br>
    		<ul class="nav nav-tabs">
			    <li class='nav-item'>
			      <a class="nav-link active" data-toggle="tab" href="#SameAct"><spring:message code="label.ruleSameAct"></spring:message></a>
			    </li>
			    <li class="nav-item">
			      <a class="nav-link" data-toggle="tab" href="#diffAct"><spring:message code="label.ruleDeptAct"></spring:message></a>
			    </li>
	   		</ul>
     		 <div class="tab-content">
					<div id="SameAct" class='tab-pane container fade in show active'>
						<table class="table table-bordered table-striped" style="width: 100%;">
				    		<thead>
				    			<tr style=" position: sticky; top: 3%; height:95%; background: #fff; font-size: 11px;">
				    				<th><spring:message code="label.ruleSourceActivity"></spring:message></th>
    								<th><spring:message code="label.ruleSourceActParam"></spring:message></th>
				    				<th><spring:message code="label.rulesValue"></spring:message></th>
				    				<th><spring:message code="label.ruleValCon"></spring:message></th>
				    				<th><spring:message code="label.rulesVal1"></spring:message></th>
    								<th><spring:message code="label.rulesVal2"></spring:message></th>
				    				<th><spring:message code="label.rulesValue"></spring:message></th>
				    				<th><spring:message code="label.ruleValCon"></spring:message></th>
				    				<th><spring:message code="label.ruleparCon"></spring:message></th>
				    				<th><spring:message code="label.ruleDesParam"></spring:message></th>
				    				<th><spring:message code="label.ruleDesParamVal"></spring:message></th>
				    			</tr>
							</thead>
				    		<tbody>
				    			<c:forEach items="${rules.dependentRulesList}" var="rule">
				    			   <c:if test="${rule.dependentApplyFor eq 'sameAct'}">
				    			   	<tr style="font-size: 11px;">
				    					<td>${rule.sourceActivityName}</td>
				    					<td>${rule.sourceParameterName}</td>
				    					 <c:choose>
		    								<c:when test="${rule.controlType eq 'RB' or rule.controlType eq 'CB' or rule.controlType eq 'SB'}">
		    									<td>${rule.controlTypeVal}</td>
		    									<td>
		    										<c:if test="${rule.condition eq 'eq'}"><spring:message code="label.ruleEqual"></spring:message></c:if>
	    											<c:if test="${rule.condition eq 'ne'}"><spring:message code="label.ruleNotEqual"></spring:message></c:if>
<%-- 		    										${rule.condition} --%>
		    									</td>
		    									<td></td>
		    									<td></td>
		    									<td></td>
		    									<td>
		    										<c:if test="${rule.destParameterCondition eq 'eq'}"><spring:message code="label.ruleEqual"></spring:message></c:if>
	    											<c:if test="${rule.destParameterCondition eq 'ne'}"><spring:message code="label.ruleNotEqual"></spring:message></c:if>
<%-- 		    										${rule.destParameterCondition} --%>
		    									</td>
		    									<td>${rule.paramterAction}</td>
		    									<c:choose>
		    										<c:when test="${rule.paramterAction ne 'setvalue'}">
		    											<td>${rule.enableOrDiableParameterNames}
		    												<%-- <table>
		    													<c:forEach items="${rule. }">
		    													
		    													</c:forEach>
		    												</table> --%>
		    											</td>
		    										</c:when>
		    										<c:otherwise>
		    											<td>${rule.destinationParameterName}</td>
		    											<c:choose>
		    												<c:when test="${rule.destinationcontrolType eq 'RB' or rule.destinationcontrolType eq 'CB' or rule.destinationcontrolType eq 'SB'}">
		    													<td>${rule.destinationcontrolTypeVal}</td>
		    												</c:when>
		    												<c:otherwise>
		    													<td>${rule.destParamTbVal}</td>
		    												</c:otherwise>
		    											</c:choose>
		    										</c:otherwise>
		    									</c:choose>
		    								</c:when>
		    								<c:otherwise>
		    									<td></td>
		    								   <c:choose>
		    								   		<c:when test="${rule.condition eq 'le' or rule.condition eq 'ge' or  rule.condition eq 'ltAndgt' or rule.condition eq 'leAndge'}">
			    										<td>
			    											<c:if test="${rule.condition eq 'le'}"><spring:message code="label.ruleLtEqual"></spring:message></c:if>
			    											<c:if test="${rule.condition eq 'ge'}"><spring:message code="label.ruleGtEqual"></spring:message></c:if>
			    											<c:if test="${rule.condition eq 'ltAndgt'}"><spring:message code="label.ruleLAndG"></spring:message></c:if>
			    											<c:if test="${rule.condition eq 'leAndge'}"><spring:message code="label.ruleLEAndGE"></spring:message></c:if>
			    										</td>
			    										<td>${rule.fromRange}</td>
			    										<td>${rule.toRange}</td>
			    										<td></td>
			    										<td>
			    											<c:if test="${rule.destParameterCondition eq 'true'}"><spring:message code="label.ruleTbTrue"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'false'}"><spring:message code="label.ruleTbFalse"></spring:message></c:if>
<%-- 			    											${rule.destParameterCondition} --%>
			    										</td>
			    										<td>${rule.paramterAction}</td>
			    										<c:choose>
				    										<c:when test="${rule.paramterAction ne 'setvalue'}">
				    											<td>${rule.enableOrDiableParameterNames}
				    												<%-- <table>
				    													<c:forEach items="${rule. }">
				    													
				    													</c:forEach>
				    												</table> --%>
				    											</td>
				    										</c:when>
				    										<c:otherwise>
				    											<td>${rule.destinationParameterName}</td>
				    											<c:choose>
				    												<c:when test="${rule.destinationcontrolType eq 'RB' or rule.destinationcontrolType eq 'CB' or rule.destinationcontrolType eq 'SB'}">
				    													<td>${rule.destinationcontrolTypeVal}</td>
				    												</c:when>
				    												<c:otherwise>
				    													<td>${rule.destParamTbVal}</td>
				    												</c:otherwise>
				    											</c:choose>
				    										</c:otherwise>
				    									</c:choose>
			    									</c:when>
			    									<c:otherwise>
			    										<td></td>
			    										<td></td>
				    									<td>${rule.fromRange}</td>
				    									<td>${rule.toRange}</td>
				    									<td></td>
				    									<td>
				    										<c:if test="${rule.destParameterCondition eq 'lt'}"><spring:message code="label.ruleLessThan"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'gt'}"><spring:message code="label.ruleGraterThan"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'eq'}"><spring:message code="label.ruleEqual"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'ne'}"><spring:message code="label.ruleNotEqual"></spring:message></c:if>
<%-- 				    										${rule.destParameterCondition} --%>
				    									</td>
				    									<td>${rule.paramterAction}</td>
				    									<c:choose>
				    										<c:when test="${rule.paramterAction ne 'setvalue'}">
				    											<td>${rule.enableOrDiableParameterNames}
				    												<%-- <table>
				    													<c:forEach items="${rule. }">
				    													
				    													</c:forEach>
				    												</table> --%>
				    											</td>
				    										</c:when>
				    										<c:otherwise>
				    											<td>${rule.destinationParameterName}</td>
				    											<c:choose>
				    												<c:when test="${rule.destinationcontrolType eq 'RB' or rule.destinationcontrolType eq 'CB' or rule.destinationcontrolType eq 'SB'}">
				    													<td>${rule.destinationcontrolTypeVal}</td>
				    												</c:when>
				    												<c:otherwise>
				    													<td>${rule.destParamTbVal}</td>
				    												</c:otherwise>
				    											</c:choose>
				    										</c:otherwise>
				    									</c:choose>
				    								</c:otherwise>
		    								   </c:choose>
		    								</c:otherwise>
		    							</c:choose>
				    				</tr>
				    			   </c:if>
				    			</c:forEach>
				    		</tbody>
				    	</table>
					</div>
					<div id="diffAct" class='tab-pane container fade'><br>
						<table class="table table-bordered table-striped" style="width: 100%;">
				    		<thead>
				    			<tr style=" position: sticky; top: 3%; height:95%; background: #fff; font-size: 11px;">
				    				<th><spring:message code="label.ruleSourceActivity"></spring:message></th>
				    				<th><spring:message code="label.ruleDestActivity"></spring:message></th>
				    				<th><spring:message code="label.ruleSourceActParam"></spring:message></th>
				    				<th><spring:message code="label.rulesValue"></spring:message></th>
				    				<th><spring:message code="label.ruleValCon"></spring:message></th>
				    				<th><spring:message code="label.rulesVal1"></spring:message></th>
    								<th><spring:message code="label.rulesVal2"></spring:message></th>
				    				<th><spring:message code="label.rulesValue"></spring:message></th>
				    				<th><spring:message code="label.ruleValCon"></spring:message></th>
				    				<th><spring:message code="label.ruleparCon"></spring:message></th>
				    				<th><spring:message code="label.ruleDesParam"></spring:message></th>
				    				<th><spring:message code="label.ruleDesParamVal"></spring:message></th>
				    			</tr>
							</thead>
				    		<tbody>
				    			<c:forEach items="${rules.dependentRulesList}" var="rule">
				    				<c:if test="${rule.dependentApplyFor eq 'differentAct'}">
				    				 <tr style="font-size: 13px;">
				    					<td>${rule.sourceActivityName}</td>
				    					<td>${rule.destinationActivityName}</td>
		    							<td>${rule.sourceParameterName}</td>
		    							<c:choose>
		    								<c:when test="${rule.controlType eq 'RB' or rule.controlType eq 'CB' or rule.controlType eq 'SB'}">
		    									<td>${rule.controlTypeVal}</td>
		    									<td>
		    										<c:if test="${rule.condition eq 'eq'}"><spring:message code="label.ruleEqual"></spring:message></c:if>
	    											<c:if test="${rule.condition eq 'ne'}"><spring:message code="label.ruleNotEqual"></spring:message></c:if>
	    										</td>
<%-- 		    									<td>${rule.condition}</td> --%>
		    									<td></td>
		    									<td></td>
		    									<td></td>
		    									<td>
		    										<c:if test="${rule.destParameterCondition eq 'eq'}"><spring:message code="label.ruleEqual"></spring:message></c:if>
	    											<c:if test="${rule.destParameterCondition eq 'ne'}"><spring:message code="label.ruleNotEqual"></spring:message></c:if>
<%-- 		    										${rule.destParameterCondition} --%>
		    									</td>
		    									<td>${rule.paramterAction}</td>
		    									<c:choose>
		    										<c:when test="${rule.paramterAction ne 'setvalue'}">
		    											<td>${rule.enableOrDiableParameterNames}
		    												<%-- <table>
		    													<c:forEach items="${rule. }">
		    													
		    													</c:forEach>
		    												</table> --%>
		    											</td>
		    										</c:when>
		    										<c:otherwise>
		    											<td>${rule.destinationParameterName}</td>
		    											<c:choose>
		    												<c:when test="${rule.destinationcontrolType eq 'RB' or rule.destinationcontrolType eq 'CB' or rule.destinationcontrolType eq 'SB'}">
		    													<td>${rule.destinationcontrolTypeVal}</td>
		    												</c:when>
		    												<c:otherwise>
		    													<td>${rule.destParamTbVal}</td>
		    												</c:otherwise>
		    											</c:choose>
		    										</c:otherwise>
		    									</c:choose>
		    								</c:when>
		    								<c:otherwise>
		    									<td></td>
		    								   <c:choose>
		    								   		<c:when test="${rule.condition eq 'le' or rule.condition eq 'ge' or  rule.condition eq 'ltAndgt' or rule.condition eq 'leAndge'}">
			    										<td>
			    											<c:if test="${rule.condition eq 'le'}"><spring:message code="label.ruleLtEqual"></spring:message></c:if>
			    											<c:if test="${rule.condition eq 'ge'}"><spring:message code="label.ruleGtEqual"></spring:message></c:if>
			    											<c:if test="${rule.condition eq 'ltAndgt'}"><spring:message code="label.ruleLAndG"></spring:message></c:if>
			    											<c:if test="${rule.condition eq 'leAndge'}"><spring:message code="label.ruleLEAndGE"></spring:message></c:if>
			    										</td>
			    										<td>${rule.fromRange}</td>
			    										<td>${rule.toRange}</td>
			    										<td></td>
			    										<td>
			    											<c:if test="${rule.destParameterCondition eq 'true'}"><spring:message code="label.ruleTbTrue"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'false'}"><spring:message code="label.ruleTbFalse"></spring:message></c:if>
<%-- 			    											${rule.destParameterCondition} --%>
			    										</td>
			    										<td>${rule.paramterAction}</td>
			    										<c:choose>
				    										<c:when test="${rule.paramterAction ne 'setvalue'}">
				    											<td>${rule.enableOrDiableParameterNames}
				    												<%-- <table>
				    													<c:forEach items="${rule. }">
				    													
				    													</c:forEach>
				    												</table> --%>
				    											</td>
				    										</c:when>
				    										<c:otherwise>
				    											<td>${rule.destinationParameterName}</td>
				    											<c:choose>
				    												<c:when test="${rule.destinationcontrolType eq 'RB' or rule.destinationcontrolType eq 'CB' or rule.destinationcontrolType eq 'SB'}">
				    													<td>${rule.destinationcontrolTypeVal}</td>
				    												</c:when>
				    												<c:otherwise>
				    													<td>${rule.destParamTbVal}</td>
				    												</c:otherwise>
				    											</c:choose>
				    										</c:otherwise>
				    									</c:choose>
			    									</c:when>
			    									<c:otherwise>
			    										<td></td>
			    										<td></td>
				    									<td>${rule.fromRange}</td>
				    									<td>
				    										<c:if test="${rule.destParameterCondition eq 'lt'}"><spring:message code="label.ruleLessThan"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'gt'}"><spring:message code="label.ruleGraterThan"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'eq'}"><spring:message code="label.ruleEqual"></spring:message></c:if>
			    											<c:if test="${rule.destParameterCondition eq 'ne'}"><spring:message code="label.ruleNotEqual"></spring:message></c:if>
<%-- 				    										${rule.destParameterCondition} --%>
				    									</td>
				    									<td>${rule.paramterAction}</td>
				    									<c:choose>
				    										<c:when test="${rule.paramterAction ne 'setvalue'}">
				    											<td>${rule.enableOrDiableParameterNames}
				    												<%-- <table>
				    													<c:forEach items="${rule. }">
				    													
				    													</c:forEach>
				    												</table> --%>
				    											</td>
				    										</c:when>
				    										<c:otherwise>
				    											<td>${rule.destinationParameterName}</td>
				    											<c:choose>
				    												<c:when test="${rule.destinationcontrolType eq 'RB' or rule.destinationcontrolType eq 'CB' or rule.destinationcontrolType eq 'SB'}">
				    													<td>${rule.destinationcontrolTypeVal}</td>
				    												</c:when>
				    												<c:otherwise>
				    													<td>${rule.destParamTbVal}</td>
				    												</c:otherwise>
				    											</c:choose>
				    										</c:otherwise>
				    									</c:choose>
				    								</c:otherwise>
		    								   </c:choose>
		    								</c:otherwise>
		    							</c:choose>
				    				</tr>
				    				</c:if>
				    			</c:forEach>
				    		</tbody>
				    	</table>
					</div>
			</div>
     </div>
      <div id="menu3" class='tab-pane container fade'>
    	<table class="table table-bordered table-striped" style="width: 100%;">
    		<thead>
    			<tr style=" position: sticky; top: 3%; height:95%; background: #fff; font-size: 13px;">
    				<th><spring:message code="label.ruleSourceActivity"></spring:message></th>
    				<th><spring:message code="label.ruleSelectDeptActs"></spring:message></th>
    			</tr>
			</thead>
    		<tbody>
    			<c:forEach items="${rules.dependentActList}" var="rule">
    				<tr style="font-size: 13px;">
    					<td style="color: #212529;">${rule.activityName}</td>
    					<td style="color: #212529;">
    						<table>
    						    <c:set value="1" var="count"></c:set>
   								<c:forEach items="${rule.dependentActList}" var="act">
   								   <c:choose>
	   								   	<c:when test="${count eq 1}">
		    								<tr style="font-size: 13px;">
		    								<td style="color: #212529;">${act.actName}</td>
		    								<c:set value="${count+1}" var="count"></c:set>
		    							</c:when>
		    							<c:otherwise>
		    								<td style="color: #212529;">${act.actName}</td>
		    								<c:if test="${count eq 2}">
		    									</tr>
		    									<c:set value="1" var="count"></c:set>
		    								</c:if>
		    							</c:otherwise>
   								   </c:choose>
   								</c:forEach>
	    					</table>
    					</td>
    				</tr>
    			</c:forEach>
    		</tbody>
    	</table>
     </div>
	</div>
  </div>
 </body>
 </html>