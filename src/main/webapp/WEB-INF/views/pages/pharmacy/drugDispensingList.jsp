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
					<h2><sping:message code="label.studyDrugDispensing.Title"></sping:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
            <a  style='float:right;' href='<c:url value="/studyDrugDispensing/drugDispensingFormOne"/>' ><button type="button" class="btn btn-info"><sping:message code="label.addBt"></sping:message></button></a>
              
            <table id="drugList"  class="table table-striped table-bordered nowrap" style="width: 100%;">
                <thead>
	                <tr>
	                	<th><sping:message code="label.drungreception.projectno"></sping:message></th>
		   				<th><sping:message code="label.drungreception.sponsorStudyCode"></sping:message></th>
		   				<th><sping:message code="label.drugreception.randomaizationcode"></sping:message></th>
		   				<th><sping:message code="label.action"></sping:message></th>
		   				<!-- <th>View</th> -->
	                </tr>
                </thead>
                <tbody>
                <c:forEach items="${dispennsinglist}" var="list">
	   				<tr>
	   					<td>${list.projectId.projectNo}</td>
	   					<td>${list.sponsorStudyCode}</td>
	   					<td>${list.randamizationCode}</td>
	   					<c:choose>
	   					 <c:when test="${list.finalStatus eq 'Completed' }">
	   					 <td><sping:message code="label.completed"></sping:message></td>
	   					 </c:when>
	   					 <c:otherwise>
	   					 <td><a class="fa fa-edit" style='color:#23175e;font-size:15px;padding-left:10px' title="<sping:message code="label.user.edit"></sping:message>" href='<c:url value="/studyDrugDispensing/drugDispensingForm/${list.id}"/>'></a></td>
	   					 </c:otherwise>
	   					 </c:choose>
	   					<%--  <td><a href='<c:url value="/studyDrugDispensing/drugDispensingViewData/${list.id}"/>'>View</a></td> --%>
	   				
	   				</tr>
	   			</c:forEach> 
                </tbody>
              </table>
              </div>
            <!-- /.card-header -->
           </div>
          </div>
         



