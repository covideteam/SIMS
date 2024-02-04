<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
<table class="table table-bordered table-striped" style="width: 100%;">
<tr>
<td align="center" ><input type="button" value="Check1" class="btn btn-danger btn-md"  onclick="checkJson()">
</td></tr>
</table>
</body>
<h1 class='page-subheading'>Study Information</h1>
            <table>
                <tr style='height:80px;'>
                    <td><div style='width:170px;'><label>Project No</label><input type="text" class="form-control autosave" name="projectNumber" /></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>Sponsor Code</label><input type="text" class="form-control autosave" name="sponsorCode" id="projNo" /></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>Study Type</label><select id="studyType" name="studyType" class='form-control'><option value='Select'>Select</option><option value='Fast'>Fast</option><option value='FED'>Fed</option><option value='FF'>Fast&Fed</option></select></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>Study Design</label><select id="studyDesign" name="studyDesign" class='form-control'><option value='Select'>Select</option><option value='Open Label'>Open Label</option></select></div></td>

                    <td><div style='width:170px;padding-left:10px;'><label>No of Periods</label><input type="text" name="noOfPeriods" class="form-control" id="noOfPeriods" /></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>Washout Period(Days)</label><input type="number" name="washoutPeriod" class="form-control" id="washoutPer" /></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>Dosage Form</label><select id="dosageForm" name="dosageForm" class='form-control'><option value='Select'>Select</option><option value='Injection'>Injection</option><option value='Tablet'>Tablet</option></select></div></td>
                    <td><div style='width:200px;padding-left:10px;'><label>Treatment Code on Sachet</label><select id="trtmntcdonscht" name="treatmentCode" class='form-control'><option class='text-center' value='Select'>Select</option><option value='Show'>Show</option><option value='Hide'>Hide</option></select></div></td>

                </tr>



                <tr style='height:80px;'>
                    <td><div style='width:170px;'><label>Pre Dose Housing(Hr)</label><input type="number" min="0" name="preDoseHousing" class="form-control" id="preDoseHousing" /></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>Post Dose Housing(Hr)</label><input type="number" min="0" name="postDoseHousing" class="form-control" id="postDoseHousing" /></div></td>
                    <td colspan="2"><div style='width:300px;padding-left:10px;'><label>Treatment Specific Sample Timepoints</label><select name="treatmentSpecificSampleTimepoints" id="trtmntspcsmpltmpnts" class='form-control'><option value='Select'>Select</option><option value='Yes'>Yes</option><option value='No'>No</option></select></div></td>


                    <td><div style='width:170px;'><label>No of Subjects</label><input type="number" name="noOfSubjects" class="form-control" id="noOfSubjects" /></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>No of Stand By's</label><input type="number" name="noOfStandBys" min="0" class="form-control" id="noofStandBy_s" /></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>Dose Type</label><select id="doseType" name="doseType" class='form-control'><option value='Select'>Select</option><option value='Single'>Single</option><option value='Multiple'>Multiple</option></select></div></td>
                    <td><div style='width:170px;padding-left:10px;'><label>No of Treatments</label><input type="number" name="noOfTreatments" min="0" class="form-control" id="nooftreatments" /></div></td>
                </tr>

                <tr style='height:80px;'>
                    <td colspan="4"><div style='width:100%;'><label>Study Title</label><textarea style="height:90px;" id=studyTitle class="form-control" name="StudyTitle"></textarea></div></td>
                </tr>
            </table>


<script type="text/javascript">

/* $(document).on("change", '.autosave', function (e) {
	

}) */

function checkJson(){
	var myObj = { projectNo:"Project5",
			        projectId:"",
			        fieldName:"data1asa3wss", 
					fieldValue:"data1asa3wss",
					rowNumber:4,
					subRowNumber:4,
					type:"gtrhgswsd"};
	//alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/autoSave/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}
function checkCommentJson(){
	var myObj = { projectsDetailsId:1,
			      comments:"change value"};
	//alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/discrepancy/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}
function checkModificationJson(){
	var myObj = { studyDiscrepancyId:1,
			      comments:"change value",
			      newVal:"data1as1"};
	//alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/discrepancyModification/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}
function checkSDCloseJson(){
	var myObj = { studyDiscrepancyId:1};
	//alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/discrepancySDClose/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}
function checkSDReOpenJson(){
	var myObj = { studyDiscrepancyId:11,
			      comments:"change value"};
	//alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/discrepancyReOpen/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}
function discrepancyReOpen(){
	var myObj = { studyDiscrepancyId:1,
			      comments:"change value"};
	alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/discrepancyReOpen/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}
function submitDraft(){
	var myObj = { projectId:4,
			      actionName:"SUBMIT"};
	alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/submitDraft/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}
function approvalDraft(){
	var myObj = { projectId:4,
			      roleid:4,
			      actionName:"ACCEPT"};
	alert("myObj:"+JSON.stringify(myObj));
	$("#jsonString").val(JSON.stringify(myObj));
	var jsonData=(JSON.stringify(myObj));
	//alert(jsonData);
	if(jsonData!=""){
		var result = synchronousAjaxCall(mainUrl+"/studydesign/approvalDraft/"+jsonData);
		alert("result:"+result);
		if(result != '' || result != 'undefined'){
			$('#'+messageId).html(result);
		}
	}
 
}


</script>
</html>