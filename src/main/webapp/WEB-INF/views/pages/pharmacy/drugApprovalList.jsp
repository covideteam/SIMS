<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">

        $(document).ready(function() {
             var table = $('#drugList').DataTable({
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
        });
</script>
		    
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2> <sping:message code="label.drugreception.approvalTitel"></sping:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
<!-- 			  <a  style='float:right;' href='#' onclick="getPopupFunction()" ><button type="button" class="btn btn-info">Clone</button></a>
 -->             
            <table id="drugList"  class="table table-striped table-bordered nowrap" style="width: 100%;">
                <thead>
	                <tr>
	                	<th><sping:message code="label.drungreception.projectno"></sping:message></th>
		   				<th><sping:message code="label.drungreception.sponsorStudyCode"></sping:message></th>
		   				<th><sping:message code="label.drugreception.randomaizationcode"></sping:message></th>
		   				<th><sping:message code="label.drugreception.drugProductType"></sping:message></th>
		   				<%-- <th><sping:message code="label.drugreception.approval"></sping:message></th> --%>
		   				<th><sping:message code="label.action"></sping:message></th>
	                </tr>
                </thead>
                <tbody>
                <c:forEach items="${druglist}" var="dl">
	   				<tr>
	   					<td>${dl.projectId.projectNo}</td>
	   					<td>${dl.sponsorStudyCode}</td>
	   					<td>${dl.randamizationCode}</td>
	   					<td>${dl.drungProductType}</td>
	   					<%-- <c:choose>
	   					 <c:when test="${dl.reviewerStatus eq 'InApproval'}">
	   					 <td><sping:message code="label.drugreception.approval"></sping:message></td>
	   					 </c:when>
	   					 <c:otherwise>
	   					 <td>Completed</td>
	   					 </c:otherwise>
	   					</c:choose> --%>
	   					<c:choose>
	   					 <c:when test="${dl.reviewerStatus eq 'InApproval'}">
	   					 <td><a class="fa fa-edit" title="Approval" style='color:#23175e;font-size:15px;padding-left:10px' href='<c:url value="/drugReception/approvalDrugReception/${dl.id}" />'></a></td>
	   					 </c:when>
	   					 <c:otherwise>
	   					 <td><sping:message code="label.completed"></sping:message></td>
	   					 </c:otherwise>
	   					</c:choose>
	   				</tr>
	   			</c:forEach> 
                </tbody>
              </table>
             </div>
            <!-- /.card-header -->
           </div>
          </div>
         



