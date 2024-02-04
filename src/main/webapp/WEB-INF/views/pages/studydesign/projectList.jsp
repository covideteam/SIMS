<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags"%>
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

<script src='/SIMS/static/js/projects.js'></script>     

		    
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2> Studies</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
			  <a  style='float:right;' href='#' onclick="getPopupFunction()" ><button type="button" class="btn btn-info">Clone</button></a>
              <a  style='float:right;' href='<c:url value="/studydesign/studyDesignForm"/>' ><button type="button" class="btn btn-info">Add</button></a>
              
           
            <table id="projectlist"  class="table table-striped table-bordered nowrap" style="width: 100%;">
                <thead>
	                <tr>
	                	<th class="center"><sping:message code="label.projectNumber"></sping:message></th>
		   				<th class="center"><sping:message code="label.createdBy"></sping:message></th>
		   				<th class="center"><sping:message code="label.createdOn"></sping:message></th>
		   				<th class="center"><sping:message code="label.action"></sping:message></th>
		   			</tr>
                </thead>
                <tbody>
                <c:forEach items="${plist}" var="pj">
	   				<tr>
	   					<td>${pj.projectNo}</td>
	   					<td>${pj.createdBy}</td>
	   					<td class="center"><fmt:formatDate value="${pj.createdOn}" pattern="${dateFormat}" /></td>
	   					<td class="center">
	   						<a class='fa fa-pencil edit' data-url='<c:url value="/studydesign/projectEdit"/>' data-id='${pj.projectId}'></a>
	   						<a class="fa fa-file-pdf-o" onclick="gnerateBlankPdf('${pj.projectId}')" target="_blank"></a>
	   						<a  href='<c:url value="/dosingInfo/dosinginfoForm/${pj.projectId}"/>'><i class="fa fa-file-text" style="margin-left:5px" title="Dosing Info"></i></a>
	   					</td>
	   				</tr>
	   			</c:forEach> 
                </tbody>
              </table>
             </div>
            <!-- /.card-header -->
           </div>
          </div>
          <!-- Edit Popup  Starts-->
                 <c:url value="/delegation/deligationStatusChange" var="deldelg"/>
               <sf:form action="${deldelg}" method="post"  id="delformsumit" >
              		  <input type="hidden" name="statusuwsId" id="statusuwsId" >
              		  <input type="hidden" name="statusuwsval" id="statusuwsval" >
               </sf:form>

		 <div class="modal fade" id="editsoluntionPreparationModal" tabindex="-1" role="dialog" aria-labelledby="soluntionPreparationModalLabel" aria-hidden="true">
		    <div class="modal-dialog" id="modal-dialog_id" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h5 class="modal-title" id="ModalLabel"><b><sping:message code="label.projectCloning.Titel"></sping:message></b></h5>
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
		                </button>
		            </div>
		            <div id="updatePage"></div>
		             <div class="modal-footer">
		                  <input type="button" class="btn btn-primary btn-md" value="Submit" onclick="submitFunctionData()">
		                  <button type="button" class="btn btn-secondary ds-ml-5" data-dismiss="modal"><sping:message code="label.delclose"></sping:message></button>
		              </div>
		        </div>
		    </div>
		</div>



<!-- Edit Popup End  -->
 <input type="hidden" value="<sping:message code="label.projects"></sping:message>" id="hidPageTitle"/>
 <c:url value="/projectCrf/generateBlankCrf" var="pdfFrom"></c:url>
 	<sf:form action="${pdfFrom}" method="GET" id="pdfGenForm" target="_blank">
	    <input type="hidden" name="projectNo" id="projectNo">
	</sf:form>
	
	<c:url value="/dosingInfo/dosinginfoForm" var="doseinfoform"></c:url>
 	<sf:form action="${doseinfoform}" method="get" id="doseinfoformid">
	    <input type="hidden" name="projectId" id="doseFormProjectNo">
	</sf:form>
 <script>
 function dosinginfoFormPage(projectId){
	debugger;
		if (projectId != null && projectId != ""&& projectId != "undefined") {
				$('#doseFormProjectNo').val(projectId);
				$('#doseinfoformid').submit();
			}
		}

		function gnerateBlankPdf(projectId) {
			if (projectId != null && projectId != ""
					&& projectId != "undefined") {
				$('#projectNo').val(projectId);
				$('#pdfGenForm').submit();
			}

		}
		function getPopupFunction() {
			debugger;
			var mainUrl = $('#mainUrl').val();
			var result = synchronousAjaxCall(mainUrl
					+ "/studydesign/getProjectListPopup");
			if (result != null && result != "" && result != "undefined") {
				$('#updatePage').html(result);
				$("#editsoluntionPreparationModal").modal('show');
			}
		}
	</script>