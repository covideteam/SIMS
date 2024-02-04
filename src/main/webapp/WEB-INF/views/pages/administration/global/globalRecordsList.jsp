<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Global Values List</title>
</head>
<body oncontextmenu="return false;">
<table class="table table-bordered table-striped">
	
		<thead>
			<tr>
				<th>Name</th>
				<th>Language Wise Values</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${gvList.gvMap}" var="gv">
				<tr>
					<td>${gv.value.name}</td>
					<td>
						<table class="table table-bordered table-striped">
							<c:forEach items="${gvList.lsMap[gv.value.id]}" var="val">
								<tr>
									<td>${val.name}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</c:forEach>
		</tbody>
</table>
</body>
</html>