<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Groups Creation Page</title>
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
  * {
  box-sizing: border-box;
}
}

#myTable td{
	
    width: 200px;
    padding: 10px;
}

#myTable td div{
    background: #13547a !important;
    height: 80px;
}

#tabledata{
	color:black;
}

#tabledata td{
	padding:5px;
}

#tabledata td div{
	background: lightgray;
	padding:5px;
}

</style>
</head>
<body oncontextmenu="return false;">

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
					<h2>
						<spring:message code="label.groupDesignActivity.LinkName"></spring:message>
					</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
					<input type="hidden" name="paramsize" id="paramsize">
					<c:url value="/groups/saveGlobalGroupDesignActivity"
						var="saveGroup" />
					<form:form action="${saveGroup}" method="post" id="groupDesignForm">
						<input type="hidden" name="rowcount" id="rowcountid">
						<input type="hidden" name="columscount" id="columscountid">
						<div style="width: 75%; margin-left: 10%;">
							<div class="form-group row">
								<label for="activityName" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.groupDesignActivity.Activity"></spring:message></label>
								<div class="col-sm-5">
									<select name="activityName" id="activityNameId"
										class="form-control validate"
										onchange="activityFunction('activityNameId','activityNameIdMsg')">
										<option value="-1">---<spring:message
										code="label.sdSelect"></spring:message>---</option>
										<c:forEach items="${gaList}" var="act">
											<option value="${act.id}">${act.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group row">
								<label for="groupName" class="col-sm-3 col-form-label"
									style="color: #212529;"><spring:message
										code="label.groupDesignActivity.Group"></spring:message></label>
								<div class="col-sm-5">
									<select name="groupName" id="groupNameId" class="form-control validate"
										onchange="groupNameFunction('groupNameId','groupNameIdMsg')">
										<option value="-1">---<spring:message
										code="label.sdSelect"></spring:message>---</option>
									</select>
								</div>
							</div>
							
							<div class="form-group row">
								<div class="col-sm-10" align="center">
									<input type="button" value="<spring:message
										code="label.submit"></spring:message>"
										class="btn btn-danger btn-sm" id="addBtn"
										onclick="submitFunction()">
									<!-- <div id="submitMsg" style="color: red;"></div> -->
								</div>
							</div>
							
							<table style="display: inline-block; float:right; width:78%;"  id="myTable"></table>
							<table style="display: inline-block;  float: left; width:18%; " id="tabledata"></table>
							<div  hidden="true" id="hidenTable"></div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<div><textarea hidden="true"  maxlength='500'  id="data1"></textarea></div>
	<div><textarea hidden="true"  maxlength='500'  id="data2"></textarea></div>
	

<script type="text/javascript">

 function activityFunction(id,messageId){
	//var data=$('#'+id).val();
	$("#myTable").empty();
	$('#tabledata').empty(); 
	var url = mainUrl+'/groups/getGroupListBaseonActivityParameter/'+$('#'+id).val();
		var result = synchronousAjaxCall(url);
		if(result !="" || result !="undefined"){
	 		$('#groupNameId').empty(); 
	 		$('#groupNameId').append('<option value="-1">--Select--</option>');
			$('#groupNameId').append(result); 
	 	}
	
} 
function groupNameFunction(id,messageId){
	if($('#'+id).val()!="-1"){
	    $('#submitMsg').html("");
	    
	    var actid=$('#activityNameId').val();
		var url = mainUrl+'/groups/getGroupRowsAndColumsCount/'+$('#'+id).val();
		var result = synchronousAjaxCall(url);
	//	alert(result);
		var count = result.split(",");
		//$('#rowcountid').val(count[0]);
	   // $('#columscountid').val(count[1]); 
	    $('#rowcountid').val(count[1]);
	    $('#columscountid').val(count[0]); 
	    
	    var url1 = mainUrl+'/groups/getGroupParamList/'+$('#'+id).val()+"/"+actid;
		var result = synchronousAjaxCall(url1);
		var url2 = mainUrl+'/groups/getGroupParamIdsList/'+$('#'+id).val()+"/"+actid;
		var resultIds = synchronousAjaxCall(url2);
		//alert(result);
		if(result !="" && result !="undefined" && resultIds!="" &&resultIds!="undefined"){
			//alert();
	 		$('#myTable').empty(); 
	 		$('#tabledata').empty(); 
			var value=result.split(",");
			var valueid=resultIds.split(",");
			var parsize=value.length;
			$('#paramsize').val(parsize);
			
			$('#tabledata').empty(); 
			for(var i=0; i<value.length; i++){
				for(var i=0; i<valueid.length; i++){
				$('#tabledata').append("<tr><td><div id="+valueid[i]+" draggable='true' class='dragable fielddragable' value=" + valueid[i] + ">" + value[i] + "</div></td></tr>");
			  }			
			}

			var tableData = "";
			var hiddenData = "";
		    for (j = 0; j < parseInt($("#columscountid").val()); j++) {
		    	tableData = tableData + "<tr>"
		    	hiddenData = hiddenData + "<tr>"
		      for (k = 0; k < parseInt($("#rowcountid").val()); k++) {
		    	   tableData = tableData + "<td><div id='" + j + "" + k + "' class='droppable fielddroppable' data-col='" + k + "' data-row='" + j + "'>" + j + "</div></td>";
		    	   hiddenData += "<td><input type='text'  maxlength='200' size='20'   name="+j+''+k+'N'+"  id="+j+''+k+'N'+"></td>";
		      }
		    	tableData = tableData + "</tr>"
		    	hiddenData = hiddenData + "</tr>"
			}

		    $("#myTable").empty();
		    $("#myTable").append(tableData);
		    
		    $("#hidenTable").empty();
		    $("#hidenTable").append(hiddenData);
		    
		    initiateDragDropFunctionality(); 
	 	}
	}else{
		$('#tabledata').empty(); 
		$("#myTable").empty();
	    $("#hidenTable").empty();
	} 
}
function functionParameter(id,messageId){
	/* debugger;
	var value=$('#'+id).val();
	
	$('#tabledata').empty(); 
	for(var i=0; i<value.length; i++){
		$('#tabledata').append("<tr><td><div id="+i+" draggable='true' class='dragable fielddragable' value=" + i + ">" + value[i] + "</div></td></tr>");
	}

	var tableData = "";
	var hiddenData = "";
    for (j = 0; j < parseInt($("#columscountid").val()); j++) {
    	tableData = tableData + "<tr>"
    	hiddenData = hiddenData + "<tr>"
      for (k = 0; k < parseInt($("#rowcountid").val()); k++) {
    	   tableData = tableData + "<td><div id='" + j + "" + k + "' class='droppable fielddroppable' data-col='" + k + "' data-row='" + j + "'>" + j + "</div></td>";
    	   hiddenData += "<td><input type='text'  maxlength='200' size='20'  value="+j+''+k+'N'+"  name="+j+''+k+'N'+"  id="+j+''+k+'N'+"></td>";
      }
    	tableData = tableData + "</tr>"
    	hiddenData = hiddenData + "</tr>"
	}

    $("#myTable").empty();
    $("#myTable").append(tableData);
    
    $("#hidenTable").empty();
    $("#hidenTable").append(hiddenData);
    
    initiateDragDropFunctionality(); */
	
}
function submitFunction(){
	$('#addBtn').prop("disabled", true);
	debugger;
	$('#addBtn').prop("disabled", false);
	$('#activityNameIdMsg').html("");
	$('#groupNameIdMsg').html("");
	
	$('#submitMsg').html("");
	var act=$('#activityNameId').val();
	var group=$('#groupNameId').val();
	var flag=true;
	var flag2=true;
	var flag3=true;
	
	
	if(act!="" &&act!="-1" &&group!="" &&group!="-1"){
	
	//ACT == Static Data( Separated Value )
	const array = [];  
	const arrayValidation = [];  
	for (j = 0; j < parseInt($("#columscountid").val()); j++) {
	     for (k = 0; k < parseInt($("#rowcountid").val()); k++) {
	    	  var data=+j+""+k+"N";
	    	  var valdata= $('#'+data).val();
	    	  //alert("sss:"+valdata);
	    	  if(valdata.trim()!="" && valdata!="undefined"){
    		  if (array.includes(valdata)) {
    			  flag3=false;
    			}else{
    				//alert("chh");
    				arrayValidation.push(valdata);
    			}
	    	  }
	    	  if(valdata=="" || valdata=="undefined"){
	    		  flag=false; 
	    	  }else{
	    		  array.push(valdata);
	    	  }
	    	
	        }
	     } 
	       
	        var valdata= $('#paramsize').val();
	
			if(flag3){
				if(!(arrayValidation.length==valdata)){ 
					$(function(){
				    	bs4Toast.warning('warning', 'Add All Parameter..');
				    });
					/* $('#submitMsg').html("Add All Parameter."); */
				}
			}else{
				$(function(){
			    	bs4Toast.warning('warning', 'Add All Parameter..');
			    });
				    /* $('#submitMsg').html("Add All Parameter.."); */
			}
    }
	
	if(validateElements($("#groupDesignForm"))){
		
		$(".loader").show();
		$('#groupDesignForm').submit();
	}else{
		$('#addBtn').prop("disabled", false);
	}
}
/* document.addEventListener('dragstart', function (event) {
	debugger;
	  $(".dragable").draggable({
		    revert: 'invalid',
		    start: function (event, ui) {
		    },
		    stop: function () {
		        $(this).draggable('option', 'revert', 'invalid');
		        $.ajax({
		            url: '/Fields/UpdateFieldTableData',
		            type: 'POST',
		            data: {
		                'fieldId': $(this).attr("id"), 'rowNumber': $(this).attr("data-row"),
		                'columnNumber': $(this).attr("data-col"),
		                'colSpan': 1,
		                'rowSpan': 1
		            },
		            success: function (e) {

		            }
		        });

		    }
		});
  }); */
  
  var finalval = "";
   function initiateDragDropFunctionality(){
	  // alert("hygjh");
	   $(".dragable").draggable({
		    revert: 'invalid',
		    start: function (event, ui) {
		     $('#data1').val($(this).attr("id"));
		    },
		   
		});

		$(".droppable").droppable({
		    greedy: true,
		    tolerance: 'touch',
		    drop: function (event, ui) {
		        var $this = $(this);
		        $('#data2').val($(event['target']).attr("id"));
		        let da =$(event['target']).attr("id");
		        da+="N";
		        $('#data3').val($('#data1').val());
		        $('#'+da).val($('#data1').val());
		    }
		});
		
		
   }
  
</script>
  
</body>
</html>