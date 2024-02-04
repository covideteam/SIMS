<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
					<h2> <spring:message code="label.ipDiscard.titel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
            <a  style='float:right;' href='<c:url value="/pharmacyData/ipDiscardForm"/>' ><button type="button" class="btn btn-info"><spring:message code="label.addBt"></spring:message></button></a>
              
            <table id="drugList"  class="table table-striped table-bordered nowrap" style="width: 100%;">
                <thead>
	                <tr>
	                	<th><spring:message code="label.advCover.proNo"></spring:message></th>
	                </tr>
                </thead>
                <tbody>
                <c:forEach items="${dataList}" var="list">
	   				<tr>
	   					<td>${list.projectId.projectNo}</td>
	   				
	   				</tr>
	   			</c:forEach> 
                </tbody>
              </table>
              </div>
            <!-- /.card-header -->
           </div>
          </div>
         



