<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:url value="/barcodelabels/stdSubjectBarcodePrint" var="stdSubjectBarcodePrint" />
<sf:form action="${stdSubjectBarcodePrint}" method="POST"  id="formsumit" >
<input type="hidden" name="type" id="type"/>
<!--  dummy periodId not use-->
<input type="hidden" name="periodId" id="periodId" value="01"/>
<input type="hidden" name="volListsize" id="volListsize" value="${volList.size()}"/>
				<div class="row">
						<div class="col-md-12 col-sm-12 ">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.bacodepage.title"></spring:message></h2>
									<ul class="nav navbar-right panel_toolbox">
										<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
										</li>
										<li class="dropdown"></li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<br>
								<table id="example1" class="table table-bordered table-striped" style="width: 100%;">
							       <tr>
										<td style="width: 20%;"><spring:message code="label.bacodepage.subjects"></spring:message> :</td>
										<td style="width: 50%;">
											<select name="subject" id="subject" class="form-control">
												<c:forEach items="${subjectNosMap }" var="vol">
													<option value="${vol.key}">${vol.value}</option>
												</c:forEach>	
											</select>
										</td>
										<td><input type="button" value="Print Bar-Code" class="btn btn-primary" onclick="singleSubjectBarocode('Single')"/></td>
										<td><input type="button" value="Print All Subject Bar-Code" class="btn btn-primary" onclick="singleSubjectBarocode('All')"/></td>
										<!-- <td><input type="button" value="Print All Subject Bar-Code" class="btn btn-primary" onclick="submitAllBarocode('All')"/></td> -->
									</tr>
							</table>
	                   </div>
	              </div>
	          </div>               
          </div>
</sf:form>
 
</body>
<script type="text/javascript">
function submitForm(){
	if($("#volListsize").val() >0){
		$("#type").val("0");
		$("#formsumit").submit();			
	}else
		alert("Volunteers Not avilable");

}


</script>

</html>