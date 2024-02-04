<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

    
   
</head>
<body>
			<div class="row">
					<div class="col-md-12 col-sm-12 ">
						<div class="x_panel">
							<div class="x_title">
								<h2>Randomization Approval</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
									</li>
									<li class="dropdown">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-wrench"></i></a>
										<ul class="dropdown-menu" role="menu">
											<li><a class="dropdown-item" href="#">Settings 1</a>
											</li>
											<li><a class="dropdown-item" href="#">Settings 2</a>
											</li>
										</ul>
									</li>
									<li><a class="close-link"><i class="fa fa-close"></i></a>
									</li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content">
								<br>
									<table id="randomizationapproval" class="table table-bordered table-striped" style="width: 100%;">
						                <thead>
						                     <tr>
							                	<th><spring:message code="label.randomizationSubjectNo"></spring:message></th>
							                      <%--  <c:forEach var = "i" begin = "1" end = "${psize+1}">
											        <th></th>
											       </c:forEach> --%>
											       <c:forEach items="${periodname}" var="p">
												        <th>${p}</th>
												   </c:forEach>
						                	 </tr>
						                </thead>
						                <tbody>
						                    <c:forEach items="${ramList}" var="ram">
							   				  <tr>
							   					<td>${ram.subjectNo}</td>
							   					<c:forEach items="${ram.randomizationCode}" var="ramd">
							   					<td>${ramd}</td>
							   					</c:forEach>
							   				 </tr>
							   			   </c:forEach> 
						                </tbody>
						               
									</table>
								</div>
							</div>
									
									<table id="usercomments" class="table table-bordered table-striped" style="width: 100%;">
						                <thead>
						                <tr>
						                 <th><spring:message code="label.randomizationSno"></spring:message></th> 
						                <th><spring:message code="label.randomizationUser"></spring:message></th>
						                <th><spring:message code="label.randomizationComment"></spring:message></th>
						                <th><spring:message code="label.randomizationStatus"></spring:message></th>
						                <th><spring:message code="label.randomizationCreatedOn"></spring:message></th>
						                </tr>
						                </thead>
						                <tbody>
						                <c:forEach items="${rraList}" var="ram" varStatus="st">
							   				<tr>
							   			        <td>${st.count}</td> 
							   					<td>${ram.user.username}</td>
							   					<td>${ram.comment}</td>
							   					<td>${ram.status}</td>
							   					<td><fmt:formatDate value="${ram.date}" pattern="${dateFormat}" /></td>
							   				</tr>
							   			</c:forEach> 
						                </tbody>
						               </table>
									<c:url value="/randomization/randomizationApproval" var="formsumit1" />
							        <sf:form action="${formsumit1}" method="POST"  id="formsumit" >
							         <input type="hidden" name="studyid" id="studyid" value="${studyid}">
							         
							         <input type="hidden" name="approvalType" id="approvalType" >
							         <input type="hidden" name="commentsval" id="commentsval" >
							         <input type="hidden" name="reviewState" id="reviewState" value="${rrspojo.id}" >
							        </sf:form>
									
									
								</div>
					</div>
	<script type="text/javascript">
	function sendCommentsFuntion(){
		var comm=$("#commentsId").val();
		if(comm!=""){
			$("#commentsval").val(comm);
			$("#approvalType").val("SENDCOMMENTS");
			$('#formsumit').submit();
		}else{
			/* $("#commentsMsg").html("Required Field"); */
			$('#commentsMsg').html('${validationText}');
		}
	}
	function approvalFuntion(){
		//alert("sdf");
		var comm=$("#commentsId").val();
		//alert("comm"+comm);
		if(comm!=""){
			$("#commentsval").val(comm);
			$("#approvalType").val("APPROVAL");
			$('#formsumit').submit();
		}else{
			/* $("#commentsMsg").html("Required Field"); */
			$('#commentsMsg').html('${validationText}');
		}
	}
	
	</script>
	<script type="text/javascript">
    $(document).ready(function() {
       /*  var table = $('#randomizationapproval').DataTable({
            searchBuilder: true
        }); */
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
    
    
     <script type="text/javascript">
    $(document).ready(function() {
        var table = $('#usercomments').DataTable({
            searchBuilder: true
        });
        var table = $('#randomizationapproval').DataTable({
            searchBuilder: true
        });
//         table.searchBuilder.container().prependTo(table.table().container());
    });
    </script>
	
</body>
</html>