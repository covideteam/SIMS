<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
    <script type="text/javascript">
    $(document).ready(function() {
        var table = $('#randomizationTab').DataTable({
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
<body>
      <c:if test="${not empty pageMessage}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('success', '${pageMessage}');
	   		</script>
	   </c:if>
	   <c:if test="${not empty pageError}">
	   		<script type="text/javascript">
	   		    debugger;
	   		 displayMessage('error', '${pageError}');
	   		</script>
	   </c:if>
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.randomizationApprovaTitle"></spring:message></h2>
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
      				  <table id="randomizationTab"  class="table table-bordered nowrap">
		                <thead>
			                <tr>
			                	<tr>
			                		
							          <th><spring:message code="label.randomizationProjectNo"></spring:message></th>
							          <th><spring:message code="label.randomizationCreatedBy"></spring:message></th>
								     <th><spring:message code="label.randomizationCreatedOn"></spring:message></th>
					       		 </tr>
			            </thead>
		               	<tbody>
 					        <c:forEach items="${proList}" var="pro">
                                <tr>
						          <td>
						          		<a href='<c:url value="/randomization/randomizationApprovalView/${pro.projectId}"/>'>${pro.projectNo}</a>
						          </td>
						           <td>${pro.createdBy}</td>
						           <td><fmt:formatDate value="${pro.createdOn}" pattern="${dateFormat}" /></td>
						        </tr>
					        </c:forEach> 
						   </tbody>
		              </table>
		             </div>
                 </div>
             </div>
         </div>
</body>
</html>