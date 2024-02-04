<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body oncontextmenu="return false;">
<div class="row">
		<div class="col-md-12 col-sm-12 ">
			<div class="x_panel">
				<div class="x_title">
					<h2><spring:message code="label.rolewiseModuleTitel"></spring:message></h2>
					<ul class="nav navbar-right panel_toolbox">
						<li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						
						
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<br>
                <table style="width: 100%;" style="background: #2a3f54;" class="table table-bordered table-striped">
                	<c:set value="${0}" var="count" scope="request"></c:set>
                	<tr>
                	<td>Role</td>
                	<td>
                	 <select name="rolename" id="rolenameid" class="form-control" onchange="getModuleAccessSelect('rolenameid')">
                	 <option value="-1">---<spring:message code="label.sdSelect"></spring:message>---</option>
                   	 <c:forEach items="${roleslist}" var="rlist">
                      <option value="${rlist.id}">${rlist.role}</option>
                   	</c:forEach>
                   	</select>
                   	</td>
                   	</tr>
               </table>
                <div id="rolewiseResult"></div>
              </div>
            </div>
           
          </div>
       </div>
      
       <c:url value="/modulesAccess/saveAllSubLinksIds" var="saveData"></c:url>
      <form:form action="${saveData}" method="POST" id="dataform">
      <input type="hidden" id="finalstr" name="finalstr">
      <input type="hidden" name="roleIdVal" id="roleIdVal">
      </form:form>
     <script type="text/javascript">
     	function getModules(rolId){
     		var url = mainUrl+'/modulesAccess/viewRolewiseModules/'+rolId;
     		var result = synchronousAjaxCall(url);
			$('#rolewiseResult').html(result);
     	}
     	function getModuleAccessSelect(id){
     		var id = $('#'+id).val();
     		if(id!="-1"){
     			var url = mainUrl+'/modulesAccess/viewRolewiseModules/'+id;
         		var result = synchronousAjaxCall(url);
    			$('#rolewiseResult').html(result);
     		}else{
     			$('#rolewiseResult').html("");
     		}
     		
     	}
     	
     	function submitInactiveLink(linkId){
			if(linkId != "" && linkId != "undefined"){
				var roleId = $('#roleId').val();
				var url = mainUrl+'/modulesAccess/inactiveSideMenu/'+roleId+'/'+linkId;
				var result = synchronousAjaxCall(url);
				$('#rolewiseResult').html(result);
			}
		}
     	
     	
     	 var finalArray = [];
     	function submitSelectedLinks(roleIdVal){
     		//$('#submitId').attr('disabled','disabled');
     		 finalArray = [];
     		 $('#roleIdVal').val(roleIdVal);
     		for (let [key, value] of menuIdsmap.entries()) {
     			var menuIdArr = value.split(",");
     			for(var i=0;i<menuIdArr.length;i++){
      			   var str = "";
      			   debugger;
      			   if($('#smLink_'+menuIdArr[i]).is(":checked")){
      				   str = key+"@@@"+menuIdArr[i];
      				   
      				   if($('#smLinkAdd_'+menuIdArr[i]).is(":checked")){
      					   str = str+"@@"+"active";
      				   }else{
      					   str = str+"@@"+"inactive";
      				   }
      				   if($('#smLinkUpdate_'+menuIdArr[i]).is(":checked")){
     					   str = str+"@@"+"active";
     				   }else{
     					   str = str+"@@"+"inactive";
     				   }
      				   if($('#smLinkReview_'+menuIdArr[i]).is(":checked")){
   					  	 	str = str+"@@"+"active";
   				       }else{
   					  		 str = str+"@@"+"inactive";
   				      }
      				  if($('#smLinkForth_'+menuIdArr[i]).is(":checked")){
    					   		str = str+"@@"+"active";
    				      }else{
    					   		str = str+"@@"+"inactive";
    				     } 
      				  finalArray.push(str);
      		      }
      		   }
     		}
     		//alert(finalArray);
     		$("#finalstr").val(finalArray);
     		//$(".loader").show();
     		$('#submitMsg').html("");
     		if(finalArray!=""){
     			$('#submitId').attr('disabled','disabled');
     			$("#dataform").submit();
     		}else{
     			$('#submitMsg').html('Please Select at least One Module');
     		}
     		
     	}
     	
     	 /* $(document).ready(function() {
         	$('#submitId').removeAttr('disabled');
         }); */
 
     </script>  
    
</body>
</html>