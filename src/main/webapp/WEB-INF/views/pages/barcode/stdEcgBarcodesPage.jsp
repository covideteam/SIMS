<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ecg Barcodes Page</title>
</head>
<body>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.bacodepage.SachetTitle"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-wrench"></i></a>
							<ul class="dropdown-menu" role="menu"></ul>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		<table id="example1" class="table table-bordered table-striped" style="width: 100%;">
			<tr>
				<td style="width: 20%;"><spring:message code="label.bacodepage.Period"></spring:message> :</td>
				<td style="width: 50%;">
					<select name="period" class="form-control" id="period">
						<c:forEach items="${spmList }" var="p">
							<option value="${p.id}">Period${p.periodNo}</option>
						</c:forEach>	
					</select> 
				</td>
				<td><input type="button" value='<spring:message code="label.bacodepage.pringBarcode"></spring:message>' class="btn btn-primary" onclick="stdEcgBarcodesPrint()"/></td>
			</tr>
		</table>
         </div>
      </div>
  </div>
</div>
</body>
</html>