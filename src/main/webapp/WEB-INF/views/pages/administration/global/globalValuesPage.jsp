<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Global Values Page</title>
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
	<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.mlLink"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<c:url value="/values/saveGlobalValues" var="saveValues" />
					<form:form action="${saveValues}" method="post" id="valuesForm">
					<%-- <input type="text" value="${showLabel}" id="show">
					<input type="text" value="${entriesLabel}" id="entries"> --%>
						<div style="width: 75%; margin-left: 10%;">
							<div class="form-group row">
    							<label for="valueName" class="col-sm-3 col-form-label" style="color: #212529;">${gvDto.outLanMap['name']}</label>
    							<div class="col-sm-5">
    								<input type="text" name="valueName" id="valueNameId" class="form-control validate" maxlength="255"
										onkeyup="vlaueNameValidation('valueNameId', 'valueNameMsg')">
								</div>
							</div>
							<c:forEach items="${inLagList}" var="inl">
									<div class="form-group row">
										<c:forEach items="${gvDto.lanMap[inl.id]}" var="lan">
											<c:if test="${lan.key eq 'langName'}">
    											<label for="pvalue_${inl.id}" class="col-sm-3 col-form-label" style="color: #212529;">${lan.value}</label>
    										</c:if>
    										<c:if test="${lan.key eq 'label'}">
    											<div class="col-sm-5">
    											    <input type="hidden" name="lanId" id="lanId" value="${inl.id}">
	    											<input type="text" name="pvalue" id="value_${inl.id}" placeholder="${lan.value}"class="form-control validate" maxlength="255">
														<script type="text/javascript">
															lanArr.push('${inl.id}');
														</script>
												</div>
    										   </c:if>
    										</c:forEach>
    									</div>	
							</c:forEach>
							<div class="form-group row">
    							<label for="orderNo" class="col-sm-3 col-form-label " style="color: #212529;"><spring:message code="label.order"></spring:message></label>
    							<div class="col-sm-5">
    								<input type="number" name="orderNo" id="orderNo" class="form-control validate" min="0" max="10000" maxlength="10000"  onkeypress=" return(event.charCode !=8 && event.charCode ==0 || (event.charCode >= 48 && event.charCode <= 57))"
										onkeyup="orderNoValidation('orderNo', 'orderNoMsg')" onkeydown="validateForm('orderNo', 'text')">
										<div id="orderNoMsg" style="color: red;"></div>
								</div>
							</div>
							<div class="form-group row">
    							<div class="col-sm-10" align="center">
    								<input type="button" value="${gvDto.outLanMap['Add']}" class="btn btn-danger btn-sm" id="addBtn">
								</div>
							</div>
						</div>
					</form:form>
			<div id="resultDiv"></div>
	    <div> 
	   <%-- <h4 style="text-align:center;padding-top:20px;padding-right:15%"><spring:message code="label.gvListTitle"></spring:message></h4> --%>
	   
	   <table  class="table table-striped table-bordered">
			<tr>
				<th style="text-align: center;padding-right:15%"><spring:message code="label.gvlink"></spring:message></th>
			</tr>
		</table>
	  	<table id="globalValues" class="table table-striped table-bordered nowrap" style="width: 100%;">
		
				<c:forEach items="${gvDto.gvMap}" var="gv" varStatus="st">
				   <c:if test="${st.count eq 1}">
				   		<c:set value="${gvDto.lsMap[gv.value.id]}" var="lsv"></c:set>
			<thead>
				   <tr>
					 <th>${gvDto.outLanMap['name']}</th>
					   <c:forEach items="${lsv}" var="ls">
							 <c:forEach items="${gvDto.lanMap[ls.inlagId.id]}" var="lan">
								    <c:if test="${lan.key eq 'langName' and langId eq ls.inlagId.id}">
										<th>${lan.value}</th>
									</c:if>
							</c:forEach>
						</c:forEach>
				<th><spring:message code="label.order"></spring:message></th>
					</tr>
					</thead>
					<tbody>
				   </c:if>
				   <tr>
					<td>${gv.value.name}</td>
						<c:forEach items="${gvDto.lsMap[gv.value.id]}" var="val">
								<td>${val.name}</td>
								<td>${val.globalValId.orderNo}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
	</table>
</div>
			</div>
		</div>
	</div>
</div>
<script src='/SIMS/static/Tooltips/Tooltip.js'></script>
<script type="text/javascript">
$('#addBtn').prop("disabled", false);
$("#orderNo").on('keypress', function(e) {
    var value = $(this).val();
    
        if(value > 10000){
          $(this).val('10000');
          
          return false;
        }
      });


function vlaueNameValidation(id,messageId){
	debugger;
	var valueNameFlag = false;
	var value = $('#'+id).val().trim();
	if(value == null || value=="" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		valueNameFlag = false;
	}else{
		var url=mainUrl+'/values/valueCheckExitOrNot/'+value;
		var result=synchronousAjaxCall(url).trim();
		if(result=="Yes"){
			$('#'+id).attr("data-errormessage","Name already exists.");
			$('#'+id).attr("data-isvalid","false");
			valueNameFlag=false;
		}else{
			$('#'+id).attr("data-isvalid","true");
			valueNameFlag=true;
		}
	}
	checkElementValidation($('#'+id));
	return valueNameFlag;
}

function orderNoValidation(id, messageId){
	debugger;
	var orderFlag = false;
	var value = $('#'+id).val().trim();
	if(value == null || value=="" || value == "undefined"){
		$('#'+id).attr("data-errormessage","Required Field..!");
		$('#'+id).attr("data-isvalid","false");
		orderFlag = false;
	}else{
		var regex = /^[0-9][0-9]*$/;
		if(regex.test(value)){
			if(0 < parseInt(value) && 10000 >= parseInt(value)){
				$('#'+id).attr("data-isvalid","true");
				 orderFlag = true;
			}else{
				 if(parseInt(value) > 10000)
					 $('#'+id).val(10000);
				 $('#'+id).attr("data-isvalid","true");
				 orderFlag = true;
			}
		}
	}
	checkElementValidation($('#'+id));
	return orderFlag;
}

$('#addBtn').click(function(){
	debugger;
	$('#addBtn').prop("disabled", true);
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
    		debugger;
           var table = $('#globalValues').DataTable({
        	
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
</body>
</html>