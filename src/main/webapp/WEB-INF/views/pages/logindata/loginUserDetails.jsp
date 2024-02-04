<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
 <!-- Datatable Dependency start -->
   <!--  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/jszip-2.5.0/dt-1.10.20/b-1.6.1/b-colvis-1.6.1/b-html5-1.6.1/b-print-1.6.1/r-2.2.3/datatables.min.css" />
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/v/bs4/jszip-2.5.0/dt-1.10.20/b-1.6.1/b-colvis-1.6.1/b-html5-1.6.1/b-print-1.6.1/r-2.2.3/datatables.min.js"></script> -->
 
    <!-- Datatable Dependency end -->
<meta charset="ISO-8859-1">
<title> User Details</title>
<!-- 'copy', 'csv', 'excel', 'pdf', 'print' -->
</head>
<body oncontextmenu="return false;">
<!-- <div class="card"> -->

	   <table class="table table-bordered table-striped" id="specificAudit1" style="width: 100%;">
               <thead >
			<tr>
				<th><spring:message code="label.userLog.Sno"></spring:message></th>
				<th><spring:message code="label.userLog.UserName"></spring:message></th>
				<th><spring:message code="label.userLog.LoginDate"></spring:message></th>
				<th><spring:message code="label.userLog.LogoutDate"></spring:message></th>
				<th><spring:message code="label.userLog.UserRole"></spring:message></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${uaList}"  varStatus="st" var="ua">
    			<tr>
    				<td>${st.count}</td>
    				<td>${ua.userName}</td>
    				<td><fmt:formatDate pattern="${dateTimeFormatSeconds}" value="${ua.loginDate}"/></td>
    				<td><fmt:formatDate pattern="${dateTimeFormatSeconds}" value="${ua.logoutDate}"/></td>
    				<td>${ua.userRole}</td>
    			</tr>
	    	</c:forEach>
	</tbody>
                
  </table>
  <script type="text/javascript">
	    $(document).ready(function() {
	    	$('#specificAudit1')
	        .on( 'init.dt', function () {
	           $(".buttons-pdf").css({ 'position':'absolute','top':'5px','background-color':'#2A3F54' });
	           var span = $(".buttons-pdf").find('span');
	           $(span).empty();$(span).addClass("fa fa-file-pdf-o");
	        })
	        .dataTable({
	        	
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
			      		 },

	            dom: 'Bfrtip',
	            responsive: true,
	            pageLength: 10,
	           // lengthMenu: [0, 5, 10, 20, 50, 100, 200, 500],

	            buttons: [
	                'pdf'
	            ]

	        });
	        /* $('#specificAudit1').DataTable({

	            dom: 'Bfrtip',
	            responsive: true,
	            pageLength: 10,
	           // lengthMenu: [0, 5, 10, 20, 50, 100, 200, 500],

	            buttons: [
	                'pdf'
	            ]

	        }).on( 'init.dt', function () {
	            alert( 'Table redrawn' );
	        } ); */
	        
	    });
	</script>
</body>
</html>