<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	 <div id="submitMsg" style="color: red; font-weight: bold; text-align: center;"></div>
     <table id="table1" style="width: 100%;margin-top:10px;">
       <tr>
       	   <td style="padding-left:10px;width:200px;">Study</td>
           <td>
           		<select name="exitingprojectVal" id="exitingprojectVal" class="form-control"  >
                    <option value="-1">---Select---</option>
                    <c:forEach items="${projectList}" var="li">
                        <option value="${li.projectId}">${li.projectNo}</option>
                    </c:forEach>
           		</select>
           </td>
       </tr>
        <tr>
           <td style="padding-left:10px;width:200px;">Study Number</td>
           <td >
          <input type="text" name="newProjectNoVal" id="newProjectNoVal" onchange="checkExitOrNotProjectName('newProjectNoVal','newProMsg')"  class="form-control"  >
          <div id="newProMsg" style="color: red;"></div> </td>
       </tr>
     </table>
    <c:url value="/studydesign/saveProjectClone" var="form"></c:url>
 	<sf:form action="${form}" method="GET" id="submitData" >
	    <input type="hidden" name="exitingproject" id="exitingproject">
	    <input type="hidden" name="newProjectNo" id="newProjectNo">
	</sf:form>
<script type="text/javascript">

function checkExitOrNotProjectName(id,messageId){
	debugger;
	 $('#submitMsg').html("");
	var flag=false;
	var prono=$('#'+id).val();
	if(prono!=""){
		var mainUrl = $('#mainUrl').val();
		var result = synchronousAjaxCall(mainUrl+"/studydesign/checkExitOrNotProjectName/"+prono);
		if(result != "No" && result != "" && result != "undefined"){
			 $('#'+messageId).html("");
			 flag=true;
		 }else{
			 $('#'+messageId).html("Project Already Exit.");
		 }
		}
	return flag;
}
function submitFunctionData(){
	  debugger;
	  var exitData=$('#exitingprojectVal').val();
	  var newData=$('#newProjectNoVal').val();
	  $('#exitingproject').val(exitData);
	  $('#newProjectNo').val(newData);
	  var chek=checkExitOrNotProjectName('newProjectNoVal','newProMsg');
	  if(exitData!="-1" && newData!=""  && chek){
		  $('#submitData').submit();
	  }else{
		  $('#submitMsg').html("All Fields are Required");
	  }
	}
</script>

</body>
</html>