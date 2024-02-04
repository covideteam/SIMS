<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags"%>


		<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><sping:message code="label.ProjList"></sping:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
		
            <!-- /.card-header -->
            
		              <table id="projectlist"  class="table table-striped table-bordered">
		                <thead>
			                <tr>
			                	<th><sping:message code="label.projectNumber"></sping:message></th>
				   				<th><sping:message code="label.createdBy"></sping:message></th>
				   				<th><sping:message code="label.createdOn"></sping:message></th>
				   				<th><sping:message code="label.action"></sping:message></th>
			                </tr>
		                </thead>
		                <tbody>
		                <c:forEach items="${plist}" var="pj">
			   				<tr>
			   					<td><a href='<c:url value="/studydesign/projectView/${pj.projectId}/${1}"/>' >${pj.projectNo}</a></td>
			   					<td>${pj.createdBy}</td>
			   					<%--  <td>${pj.createdOn}</td>  --%>
			   					<td><fmt:formatDate value="${pj.createdOn}" pattern="${dateFormat}" /></td>
			   					<td>
			   						<%-- <a class='fa fa-pencil edit' href='<c:url value="/studydesign/projectEdit/${pj.projectId}"/>' data-id='${pj.projectId}'></a> --%>
			   						<a class='fa fa-file review' href='<c:url value="/studydesign/projectReview/${pj.projectId}"/>' data-id='${pj.projectId}' style="margin-left:10px;"></a>
			   						<a class="fa fa-file-pdf-o" onclick="gnerateBlankPdf('${pj.projectId}')" target="_blank"></a>
			   					</td>
			   				</tr>
			   			</c:forEach> 
		                </tbody>
		              </table>
		            </div>
		            <!-- /.card-body -->
		          </div>
		          <!-- /.card -->
		          <input type="hidden" value="<sping:message code="label.projects"></sping:message>" id="hidPageTitle"/>
          </div>
           <c:url value="/projectCrf/generateBlankCrf" var="pdfFrom"></c:url>
		 	<sf:form action="${pdfFrom}" method="GET" id="pdfGenForm" target="_blank">
			    <input type="hidden" name="projectNo" id="projectNo">
			</sf:form>
      </div>

 <script type="text/javascript">
    $(document).ready(function() {
        var table = $('#projectlist').DataTable({
        	
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
    <script>
  function gnerateBlankPdf(projectId){
	  if(projectId != null && projectId != "" && projectId != "undefined"){
		  $('#projectNo').val(projectId);
		  $('#pdfGenForm').submit();
	  }
		  
  }
 </script>

          