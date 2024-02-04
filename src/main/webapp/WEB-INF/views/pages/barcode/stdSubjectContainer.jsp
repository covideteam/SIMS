<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:url value="/barcodelabels/subjectContainerBarCodePrint" var="stdPrint" />
<sf:form action="${stdPrint}" method="POST"  id="formsumit" >
	      <input type="hidden" name="jsonVal" id="jsonVal" >
				<div class="row">
						<div class="col-md-12 col-sm-12 ">
							<div class="x_panel">
								<div class="x_title">
									<h2><spring:message code="label.subcontainerbarcodes"></spring:message></h2>
									<ul class="nav navbar-right panel_toolbox">
										<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
										</li>
										
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<br>
					<table id="example1" class="table table-bordered " style="width: 100%;">
						<%-- <tr>
							<td style="width: 50%;">Select Subject</td>
							<td style="width: 50%;">
								<select name="subject" id="subject" class="form-control">
									<c:forEach items="${volList }" var="vol">
										<option value="${vol.id}">${vol.subjectNo}</option>
									</c:forEach>	
								</select>
							</td>
						</tr> --%>
						<tr align="center">
							<td><input type="button" value="<spring:message code="label.printBarcode"></spring:message>" class="btn btn-primary" onclick="submitForm()"/></td>
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
	var myObj = { projectPk:1,
			      projectLabel:"Project1",
			      periodNo:1, 
			      periodLabel:"1",
			      subjectNo:4,
			      subjectLabel:"4",
			      aliquotPk:5,
			      aliquotLabel:"5",
			      noofSampleNo:2,
			      noofSampleLabel:"2"};
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	$('#jsonVal').val(jsonData);
	$(formsumit).submit();
	
 
}


</script>

</html>