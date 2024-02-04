<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CPU Execution</title>
</head>
<body>
<form id="frmActivityForm">
		<div class="card-header">
			<h3 class="card-title"><spring:message code="label.cpuExecution"></spring:message>  </h3>
		</div>
		<div style="padding:10px;">
	    	<table style="width: 100%;">
	    		<tr>
    				<td style="width:120px"><spring:message code="label.projects"></spring:message></td>
   					<td style="width:200px">
   					<!-- Present static value . After add group value  -->
   					    <input type="hidden" name="groupId" id="groupId" value="1" class="exclude"/>
   						<select name="studyId" id="studyName" class="form-control validate exclude" data-validate="required">
		    				<option value="">-----Select-----</option>
		    				<c:forEach items="${smList}" var="sm">
								<option value="${sm.id}">${sm.projectNo}</option>
							</c:forEach>
						</select>
						<div id="studyNameMsg" style="color: red;"></div>
					</td>
					<td style="width:120px"><spring:message code="label.actName"></spring:message></td>
   					<td>
						<select name="cpuActivityType" id="cpuActivityType" class="form-control validate exclude" >
		    				<option value="-1">-----Select-----</option>
		    				<option value="BLOODSAMPLECOLLECTION">Blood Sample Collection</option>
		    				<option value="SAMPLESEPARATION">Sample Separation</option>
		    				<option value="VITALCOLLECTION">Vital Collection</option>
		    				<option value="MEALCOLLECTION">Meal Collection</option>
		    				<option value="ECG">ECG</option>
		    				<option value="DOSING">Dosing</option>
						</select>
					</td>

				</tr>
		    	<tr>
		    		<td colspan="6">
		    			<div id="activityDiv" style="margin-top:15px;">
		    			</div>
		    		</td>
		    	</tr>
			 </table>
		</div>
 </form>
<script src='/SIMS/static/js/cpu/cpuactivity.js'></script>
</body>
</html>