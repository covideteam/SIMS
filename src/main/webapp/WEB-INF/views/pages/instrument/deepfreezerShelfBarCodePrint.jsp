<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<script type="text/javascript">
    $(document).ready(function() {
        var table = $('#example1').DataTable({
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
<div class="row" oncontextmenu="return false;">
	<div class="col-md-12 col-sm-12 ">
		<div class="x_panel">
			<div class="x_title">
				<h2><spring:message code="label.deepfreezershelf.printHedding"></spring:message></h2>
				<ul class="nav navbar-right panel_toolbox">
					<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
					</li>
					
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content">
				<br>

				<table id="example1" class="table table-bordered table-striped"
					style="width: 100%;">
					<thead>
						<tr>
							<th><spring:message code="label.randomizationSno"></spring:message></th>
							<th><spring:message code="label.deepfreezer.InstrumentNo"></spring:message></th>
							<th><spring:message code="label.deepfreezershelf.rackNo"></spring:message></th>
							<th><spring:message code="label.deepfreezer.CreatedBy"></spring:message></th>
						 	<th><spring:message code="label.deepfreezer.CreatedOn"></spring:message></th>
						 	<th><spring:message code="label.downloadBarcode"></spring:message></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="li" varStatus="st">
							<tr>
								<td>${st.count}</td>
								<td>${li.instrument.instrumentNo}</td>
								<td>${li.shelfNo}</td>
							    <td>${li.createdBy}</td>
								<td><fmt:formatDate value="${li.createdOn}" pattern="${dateFormat}"  /></td> 
								<td align="center"><a href='<c:url value="/barcodelabels/deepfreezerShelfBarCodePrint/${li.id}"/>'><i class="fa fa-barcode" style="font-size: 15px; font-weight: bold;"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
