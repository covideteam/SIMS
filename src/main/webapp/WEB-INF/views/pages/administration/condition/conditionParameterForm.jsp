<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Condition Parameter</title>
<script type="text/javascript">
 var lanArr = [];
 $(function(){
	  $(".alert").show('medium');
	  setTimeout(function(){
	    $(".alert").hide('medium');
	  }, 5000);
	});
$(".close").click(function(){
    $(".alert").hide('medium');
});
</script>
<style type="text/css">
.alert{
  display:none;
  position: fixed;
  bottom: 0px;
  right: 0px;
  .fa {
    margin-right:.5em;
  }
}
</style>
</head>
<body oncontextmenu="return false;">

	  <%-- <c:if test="${not empty pageMessage}">
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
	   </c:if> --%>

	       <div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.swaListTitle"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<c:url value="/condition/saveConditionForm" var="saveValues" />
					<form:form action="${saveValues}" method="post" id="valuesForm">
						<div style="width: 75%; margin-left: 10%;">
							<div class="form-group row" id="vluesTab">
									<label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;">${swaDto.outLanMap['name']}</label>
									<div class="col-sm-5"><input type="text" name="valueName" id="valueName"  class="form-control validate"
											onkeyup="vlaueNameValidation('valueName', 'valueNameMsg')">
								    </div>
								</div>
								<c:forEach items="${inLagList}" var="inl">
									<div class="form-group row">
										<c:forEach items="${swaDto.lanMap[inl.id]}" var="lan">
											<c:if test="${lan.key eq 'langName'}">
												<label for="pvalue" class="col-sm-3 col-form-label" style="color: #212529;">${lan.value}</label>
											</c:if>
											<c:if test="${lan.key eq 'label'}">
												<div class="col-sm-5"><input type="text" name="pvalue" id="value_${inl.id}" 
														placeholder="${lan.value}" class="form-control validate">
													<input type="hidden" name="lanId" id="lanId"
													value="${inl.id}">
													 <script
														type="text/javascript">
													lanArr.push('${inl.id}');
												</script></div>
											</c:if>
										</c:forEach>
									</div>
								</c:forEach>
								<div  class="form-group row">
									<label for="dropDown" class="col-sm-3 col-form-label" style="color: #212529;">${swaDto.outLanMap['drop']}</label>
									<div class="col-sm-5"><select name="dropDown" id="dropDown"
										class="form-control validate">
											<option value="-1">----<spring:message code="label.ruleSelect"></spring:message>----</option>
											<option value="storage"><spring:message code="label.storage"></spring:message></option>
											<option value="centrifugation"><spring:message code="label.centrifugation"></spring:message></option>
											<option value="processing"><spring:message code="label.processing"></spring:message></option>
											<option value="matrix"><spring:message code="label.matrix"></spring:message></option>
									</select>
									</div>
								</div>
								
								<div class="form-group row">
									<div  class="col-sm-10" align="center"><input type="button"
										value="${swaDto.outLanMap['Add']}"
										class="btn btn-danger btn-sm" id="addBtn"></div>
								</div>
						</div>
					</form:form>
					
					<div id="resultDiv"></div>
					<c:if test="${not empty swaDto}">
						<table  class="table table-striped table-bordered">
							<tr>
								<th style="text-align: center;" colspan="${swaDto.lanMap.size()+3}">${swaDto.outLanMap['swaListTitle']}</th>
							</tr>
						</table>
							<table  id="conditionParameter" class="table table-striped table-bordered">
				    	 <thead>
			    	 		   <c:set value="${swaDto.inlag.id}" var="lsv"></c:set>
							   <tr>
								   <th>${swaDto.outLanMap['name']}</th>
										 <c:forEach items="${swaDto.lanMap[lsv]}" var="lan">
											 
										        <c:if test="${lan.key eq 'langName' and langId eq lsv}">
													<th>${lan.value}</th>
												</c:if>
											
										</c:forEach>
									 <th>${swaDto.outLanMap['drop']}</th>
									<th>${swaDto.outLanMap['swastatus']}</th>
								</tr>
						   </thead>
						   <tbody>
						    <c:forEach items="${swaDto.swaMap}" var="swa" varStatus="sta">
							   <tr>
							  		<td>${swa.value.name}</td>
									<c:forEach items="${swaDto.lsMap[swa.value.id]}" var="val">
									<c:if test="${langId eq val.inlagId.id}">
											<td>${val.name}</td>
									</c:if>
									</c:forEach>
									<td>${swa.value.dropDown}</td>
									<%-- <td><a href='<c:url value="/condition/changeStatus/${swa.value.id}/${swa.value.activeAndInactive}"/> ' ><button type="button" class="btn btn-primary btn-sm">${swa.value.activeAndInactive}</button></a></td>  --%>
								<td><a href='#' onclick="confirmalertFunction( '${swa.value.id}','${swa.value.activeAndInactive}')"> <i class='fa fa-trash' data-toggle="tooltip" data-placement="bottom" title="${swa.value.activeAndInactive}" > </i></a></td> 
								
								</tr>
							</c:forEach> 
							</tbody>
				          </table> 
					</c:if>
				</div>
			</div>
		</div>
      <div>
   </div>
</div>
		<c:url value="/condition/changeStatus" var="saveData"></c:url>
         <form:form action="${saveData}" method="GET" id="changestatus">
         <input type="hidden" name="dataid" id="dataid">
         <input type="hidden" name="status" id="status">
         </form:form>
		
<script type="text/javascript">

$('#addBtn').prop("disabled", false);
function confirmalertFunction(dataid,status){
	debugger

	if(status!=""){
		
		$.confirm({
		    title: 'Alert!',
		    //icon: 'glyphicon glyphicon-heart',
		    type: 'red',
		    typeAnimated: true,
		    boxWidth: '30%',
		    content: ' Are you sure you want to change the status!',
		    useBootstrap: false,
		    buttons: {
		       Ok: function(){
			    	 $('#dataid').val(dataid);
			    	 $('#status').val(status);
	   				$('#changestatus').submit(); 
	   			
	   				
		       },Close: function () {
		    	   
		        }
				    
		    }
		});
		}
	}


function vlaueNameValidation(id, messageId){
	var valueNameFlag = false;
	$('#'+messageId).html("");
	var value = $('#'+id).val().trim();
	if(value == null || value == "" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		valueNameFlag = false;
	}else{
		var url=mainUrl+'/condition/conditionNameCheckExitOrNot/'+value;
		var result=synchronousAjaxCall(url).trim();
		$('#'+id).attr("data-isvalid","true");
		if(result=="Yes"){
			$('#'+id).attr("data-isvalid","true");
			valueNameFlag=true;
		}else{
			$('#'+id).attr("data-errormessage","Name Already Exists..!");
			$('#'+id).attr("data-isvalid","false");
			valueNameFlag=false;
		}
	}
	checkElementValidation($('#'+id));
	return valueNameFlag;
		
}
 
$('#addBtn').click(function(){
	
	$('#addBtn').prop("disabled", true);
	debugger;
	if(validateElements($("#valuesForm"))){
		$(".loader").show();
		$('#valuesForm').submit();
	}else{
		$('#addBtn').prop("disabled", false);
	}
});
</script>

   <script type="text/javascript">
    $(document).ready(function() {
    	$('#addBtn').removeAttr('disabled');
        var table = $('#conditionParameter').DataTable({
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

</body>
</html>