<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:url value="/eformConfiguration/eformVisibilitySave" var="crfRuleSave" />
		<sf:form action="${crfRuleSave}" method="POST"  id="crfSave" enctype="multipart/form-data" oncontextmenu="return false;">
			<input type="hidden" name="newRows" id="newRows"/> 
              <div class="card-header">
                <h3 class="card-title">EForm Visibility Creation</h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                  <div class="row">
                    <div class="col-sm-4">
                      <!-- text input -->
                      <div class="form-group">
                        <label>Name</label>
                        <input class="form-control" placeholder="Enter ..." type="text" name="name" id="name" onchange="checktheVisibilityName(this.value)"/>
						<font color="red" id="namemsg"></font>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <!-- textarea -->
                      <div class="form-group">
                        <label>Desc</label>
                        <textarea class="form-control" rows="3" placeholder="Enter ..." name="desc" id="desc"></textarea>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <div class="form-group">
                      	<label>Activity With :</label>
                      	<div class="form-check">
                          <input type="radio" name="compareWith" value="Show" checked="checked" class="form-check-input">
                          <label class="form-check-label">Show</label>
                        </div>
                        <div class="form-check">
                          <input type="radio" name="compareWith" value="Hide"  class="form-check-input">
                          <label class="form-check-label">Hide</label>
                        </div>
                      </div>
                    </div>
                    
                  </div>
                  <div class="row">
                  	<div class="col-sm-4">
                      <!-- select -->
                      <div class="form-group">
                        <label>Select E-Form</label>
                        <select class="form-control" name="crfid" id="crfid" onchange="crfIdChange(this.value)">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="crf" items="${crfs}">
								<option value="${crf.id}">${crf.name}</option>
							</c:forEach>
						</select>
						<font color="red" id="crfidmsg"></font>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <!-- select -->
                      <div class="form-group">
                        <label>Select Section Element</label>
                        <div id="secEleIdTd">
                        <select name="secEleId" id="secEleId"  class="form-control">
                        	<option value="-1" selected="selected">--Select--</option></select></div>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <!-- select -->
                      <div class="form-group">
                        <label>Select Group Element</label>
                        <div id="groupEleIdTd">
                        <select name="groupEleId" id="groupEleId"  class="form-control">
                        	<option value="-1" selected="selected">--Select--</option></select></div>
                        	<font color="red" id="secEleIdmsg"></font>
					  </div>
                    </div>
                    
                  </div>
                  <div class="row">
                  	<div class="col-sm-4">
                      <div class="form-group">
                        <label>Value : </label>
                        <input class="form-control" placeholder="Enter ..." type="text" name="fieldValue" id="fieldValue" />
                        <font color="red" id="fieldValuemsg"></font>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <!-- select -->
                      <div class="form-group">
                        <label>Condition</label>
                        <div>
	                        <select name="condition1" id="condition1"  class="form-control">
	                        	<option value="eq" selected="selected">=</option>
								<option value="ne">!=</option>
								<option value="le">Less</option>
								<option value="leq">Less and eq</option>
								<option value="gt">Grater</option>
								<option value="gte">grater and eq</option>
	                        </select>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                  	<div class="col-sm-4">
                      <div class="form-group">
                      	<label>Select E-Form</label>
                        <select class="form-control" name="crfidAction" id="crfidAction" onchange="crfIdChangeTarget(this.value)">
							<option value="-1" selected="selected">--Select--</option>
							<c:forEach var="crf" items="${crfs}">
								<option value="${crf.id}">${crf.name}</option>
							</c:forEach>
						</select>
						<font color="red" id="crfidActiondmsg"></font>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <!-- select -->
                      <div class="form-group">
                        <label>Select Section</label>
                        <div id="secIdTdAction">
                        <select name="secIdAction" id="secIdAction"  class="form-control">
                        	<option value="-1" selected="selected">--Select--</option></select></div>
                      </div>
                    </div>
                    <div class="col-sm-4">
                      <!-- select -->
                      <div class="form-group">
                        <label>Select Group</label>
                        <div id="groupIdTdAction">
                        <select name="groupIdAction" id="groupIdAction"  class="form-control">
                        	<option value="-1" selected="selected">--Select--</option></select></div>
                        	<font color="red" id="secIdActionmsg"></font>
					  </div>
                    </div>
                  </div>				
	    		
              </div>
              <!-- /.card-body -->  
			<table id="tableId" class="table table-bordered table-striped">
	    		<tfoot>
	    			<tr>
	    				<th colspan="6" style="text-align: center;">
	    					<input type="button" value="Save" id="save" onclick="saveAction()"
	    						class="btn btn-primary" style="width:60px;"/>
	    					<a href='<c:url value="/admini/crfRule/" />' class="btn btn-primary">Exit</a>
	    				</th>
	    			</tr>
	    		</tfoot>
	    	</table>
		</sf:form>
		

<script type="text/javascript">
function checktheVisibilityName(name){
	$("#namemsg").html("");
	name = name.trim();
	if(name == ''){
		/* $("#namemsg").html("Required Field"); */
		$('#namemsg').html('${validationText}');
	}else{
		var result = synchronousAjaxCall(mainUrl+"/admini/crfRule/checktheVisibilityName/"+name);
		if(result == '' || result == 'undefined'){
			if(result.trim() != 'ok'){
				$("#namemsg").html(result);	
				$("#name").val("");
			}
		}
	}
}
function crfIdChange(id){
	if(id == -1){
		$('#secEleIdTd').html('<select name="secEleId" id="secEleId"><option value="-1" selected="selected">--Select--</option></select><font color="red" id="secEleIdmsg"></font>');
		$('#groupEleIdTd').html('<select name="groupEleId" id="groupEleId"><option value="-1" selected="selected">--Select--</option></select>');	
	}else{
		var result = synchronousAjaxCall(mainUrl+"/eformConfiguration/crfSectionElements/"+id);
		if(result != '' || result != 'undefined'){
			$('#secEleIdTd').html(result);
		}
		result = synchronousAjaxCall(mainUrl+"/eformConfiguration/groupEleSelectForVisibility/"+id);
		if(result != '' || result != 'undefined'){
			$('#groupEleIdTd').html(result);
		}
	}
}
function crfIdChangeTarget(id){
	if(id == -1){
		$('#secIdTdAction').html('<select name="secIdAction" id="secIdAction"><option value="-1" selected="selected">--Select--</option></select>');
		$('#groupIdTdAction').html('<select name="groupIdAction" id="groupIdAction"><option value="-1" selected="selected">--Select--</option></select>');
	}else{
		var result = synchronousAjaxCall(mainUrl+"/eformConfiguration/crfSections/"+id);
		if(result != '' || result != 'undefined'){
			$('#secIdTdAction').html(result);
		}
		result = synchronousAjaxCall(mainUrl+"/eformConfiguration/crfGroups/"+id);
		if(result != '' || result != 'undefined'){
			$('#groupIdTdAction').html(result);
		}
	}
}
function secEleSelection(id, value){
	if(value != -1){
		$("#groupEleId").val(-1);
	}
}
function groupEleSelection(id, value){
	if(value != -1){
		$("#secEleId").val(-1);
	}
}
function sectionSelection(id, value){
	if(value != -1){
		$("#groupIdAction").val(-1);
	}
}
function groupSelection(id, value){
	if(value != -1){
		$("#secIdAction").val(-1);
	}
}

</script>

<script type="text/javascript">
function saveAction(){
	var flag = validation();
	if(flag == 0){
		$('#crfSave').submit();
	}
}
function validation(){
	var flag = 0;
	$("#namemsg").html("");
	var value = $("#name").val().trim();
	if(value == ''){
		alert(value)
		/* $("#namemsg").html("Required Field"); */
		$('#namemsg').html('${validationText}');
		flag ++;
	}
	value = $("#fieldValue").val().trim();
	$("#fieldValuemsg").html("");
	if(value == ''){
		/* $("#fieldValuemsg").html("Required Field");  */
		$('#fieldValuemsg').html('${validationText}');
		flag ++;
	}
	value = $("#crfid").val();
	$("#crfidmsg").html("");
	if(value == -1){
		/* $("#crfidmsg").html("Required Field");  */
		$('#crfidmsg').html('${validationText}');
		flag ++;
	}
	var value1 = $("#secEleId").val();
	var value2 = $("#groupEleId").val();
	$("#secEleIdmsg").html("");
	if(value1 == -1 && value2 == -1){
		$("#secEleIdmsg").html("Section Or Group element Required");	flag ++;	
	}
	value = $("#crfidAction").val();
	$("#crfidActionmsg").html("");
	if(value == -1){
		/* $("#crfidActionmsg").html("Required Field");  */
		$('#crfidActionmsg').html('${validationText}');
		flag ++;
	}
// 	alert("1")
	var value1 = $("#secIdAction").val();
// 	alert("3");
	var value2 = $("#groupIdAction").val();
// 	alert("4");
	$("#secIdActionmsg").html("");
	if(value1 == -1 && value2 == -1){
		$("#crfidActiondmsg").html("Section Or Group Required"); flag ++;	
	}
	
	return flag;
}
</script>