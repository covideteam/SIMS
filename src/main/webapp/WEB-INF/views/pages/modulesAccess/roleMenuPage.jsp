<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Roles Wise Menu</title>
</head>
<body oncontextmenu="return false;">
<div class="card">
	
    <div class="container-fluid">
        <div class="row">
          <div class="col-md-10">
			<div class="card" style="margin-left: 23%;">
              <div class="card-header" style="background: #123347;">
                <h3 class="card-title" style="color:white; text-align: center;">Roles Wise Modules</h3>
              </div>
              <div class="card-body p-0">
                <table  border="2" style="width: 100%;background:#CDD59C">
                	<c:set value="${0}" var="count" scope="request"></c:set>
                   	 <c:forEach items="${rolesList}" var="rlist">
                   	 <c:choose>
                   	 		<c:when test="${count eq 0}">
                   	 			<tr></tr>
                  	 				<td><a href="#" style="color: #105978; font-size: medium; font-weight: bold;" onclick="getModules('${rlist.id}')">${rlist.role}</a></td>
              						<c:set value="${count}" var="count"></c:set>
                   	 		</c:when>
                   	 		<c:otherwise>
                   	 			<td><a href="#" style="color: #105978; font-size: medium; font-weight: bold;" onclick="getModules('${rlist.id}')">${rlist.role}</a></td>
              					<c:set value="${count}" var="count"></c:set>
               	 					
               	 			</c:otherwise>
                   	 </c:choose>
                   	</c:forEach>
               </table>
              </div>
            </div>
          </div>
          
        </div>
       </div>
       <div id="rolewiseResult"></div>
  </div>             
     <script type="text/javascript">
     	function getModules(rolId){
     		var url = mainUrl+'/modulesAccess/viewRolewiseModules/'+rolId;
     		var result = synchronousAjaxCall(url);
			$('#rolewiseResult').html(result);
     	}
     	
     	function submitInactiveLink(linkId){
			if(linkId != "" && linkId != "undefined"){
				var roleId = $('#roleId').val();
				var url = mainUrl+'/modulesAccess/inactiveSideMenu/'+roleId+'/'+linkId;
				var result = synchronousAjaxCall(url);
				$('#rolewiseResult').html(result);
				
	     		
			}
			
			
		}
     	function submitSelectedLinks(){
     		alert("idsArrAdd2:"+idsArrAdd);
     		if(idsArr.length == 0){
				$('#linksMsg').html("Select Atleast One Link");
			}else{
				var roleId = $('#roleId').val();
				var url = mainUrl+'/modulesAccess/AddSubLinksToRole/'+roleId+'/'+idsArr;
				var result = synchronousAjaxCall(url);
				if(idsArrAdd.length==0){
					idsArrAdd.push(0);
				}
				if(idsArrUpdate.length==0){
					idsArrUpdate.push(0);
				}
				if(idsArrReview.length==0){
					idsArrReview.push(0);
				}
				if(idsArrForth.length==0){
					idsArrForth.push(0);
				}
				var url = mainUrl+'/modulesAccess/AddSubLinksToRole/'+roleId+'/'+idsArr+'/'+idsArrAdd+'/'+idsArrUpdate+'/'+idsArrReview+'/'+idsArrForth;
				var result = synchronousAjaxCall(url);
				$("#modal .close").click();
				$("#links").modal("hide");
				$('#rolewiseResult').html(result);
				
				
			}
		}
     </script>  
    
</body>
</html>