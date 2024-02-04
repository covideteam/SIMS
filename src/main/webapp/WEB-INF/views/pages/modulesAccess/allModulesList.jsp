<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Links List</title>
<script type="text/javascript">
    var allSubLinksIds = [];
	var menuNames = [];
	var msLinks = [];
	var idsArr = [];
	var idsArrAdd = [];
	var idsArrUpdate = [];
	var idsArrReview = [];
	var idsArrForth = [];
	var menuIdsmap = new Map();

	
</script>

<style type="text/css">
/* .expand-collapse-icon {
	font-size: 10px;
	width: 50px;
	height: 20px;
	position: relative;
	display: inline-block;
} */

/* .expand-collapse-icon::before, .expand-collapse-icon::after {
	content: "";
	position: absolute;
	width: 10px;
	height: 3px;
	top: calc(( 1em/ 6)- .08em);
	background-color: white;
	transition: 0.3s ease-in-out all;
	border-radius: 3em;
}

.expand-collapse-icon::after {
	transform: rotate(90deg);
}

.collapsed.expand-collapse-icon::after {
	transform: rotate(180deg);
}

.collapsed.expand-collapse-icon::before {
	transform: rotate(90deg) scale(0);
} */

.panel-heading .accordion-toggle:after {
    /* symbol for "opening" panels */
    font-family: 'Glyphicons Halflings';  /* essential for enabling glyphicon */
    content: "\e114";    /* adjust as needed, taken from bootstrap.css */
    float: right;        /* adjust as needed */
    color: white;  
          /* adjust as needed */
}
.panel-heading .accordion-toggle.collapsed:after {
    /* symbol for "collapsed" panels */
    content: "\e080";    /* adjust as needed, taken from bootstrap.css */
    marigin-right:30px; 
}

</style>


</head>
<body oncontextmenu="return false;">

	<input type="hidden" name="roleId" id="roleId" value="${roleId}">
		<div class="x_pannel">
		<c:forEach items="${map}" var="entry">
			<c:set var="string1" value="${entry.key}" />
			<c:set var="string2" value="${fn:replace(string1,' ', '')}" />
			
			<table class="table-bordered" style="width: 100%;">
			<tr>
				<td>
				
					<div class="x_title" style="background: rgb(128, 128, 128); border: 1%;   margin-top: 10px;">
					<div class="panel-heading">
						<c:set var="string1" value="${entry.key}" />
						<c:set var="string2" value="${fn:replace(string1,' ', '')}" />
						<a class="card-link clickcollapse"	style="color: white; margin: 10px"	href="#${string2}"> ${entry.key}</a>
						<!-- <button class="navbar-toggler btn" type="button"
							data-toggle="collapse" data-target="#${string2}"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation"
							style="float: right; color: white">
							<span class='expand-collapse-icon clickcollapse'></span>
						</button> -->
						 <a class="accordion-toggle" data-target="#${string2}" data-toggle="collapse" ></a>
					    </div>
					</div>
					
				</td>
				</tr>
				<tr>
				<td>
					<div id="${string2}" class="show x_content">
						<table style="width: 100%;">
							<tr>
								<td style="text-align: left;">
									<div align="left" style="width: 50%;">
										<input type="checkbox" name="menuCheckbox" class="checkAll"
											id="menuCheckbox_${string2}" title="Select All"
											onclick="mainMenuSublinksValidation('${entry.key}')">
											<b><spring:message code="label.moduleaccess.sidemenulist"></spring:message></b>
									</div>
								</td>
								<td colspan="4"><div style="text-align: center;"><b><spring:message code="label.moduleaccess.action"></spring:message></b></div></td> 
							</tr>
							<tr><td colspan="5"></td></tr>
							<tr><td colspan="5"></td></tr>
							<c:forEach items="${entry.value}" var="sm">
								<tr>
									<td style="width: 40%;">
										<c:choose>
											<c:when test="${sm.status eq 'Active'}">
												<input type="checkbox" name="smlink" id="smLink_${sm.id}"
													class="stscheck" value="${sm.id}" data-id="${sm.id}"
													checked>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="smlink" id="smLink_${sm.id}"
													class="stscheck" value="${sm.id}" data-id="${sm.id}">
											</c:otherwise>
										</c:choose> ${sm.name}
										<script type="text/javascript">
										var menuName = '${entry.key}';
										var finalName = menuName.replace(/\s/g,
												'');
										if (menuNames.indexOf(finalName) != -1) {
											var index = menuNames
													.indexOf(finalName);
											var linksStr = msLinks[index];
											linksStr = linksStr + ","
													+ '${sm.id}';
											msLinks[index] = linksStr;
										} else {
											menuNames.push(finalName);
											var index = menuNames
													.indexOf(finalName);
											msLinks[index] = '${sm.id}';
										}
									</script>
									</td>
									<td>
										<c:choose>
											<c:when test="${sm.statusAdd eq 'Active'}">
												<input type="checkbox" name="smlink" id="smLinkAdd_${sm.id}"
													class="addstscheck" value="${sm.id}" data-id="${sm.id}"
													checked>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="smlink" id="smLinkAdd_${sm.id}"
													class="addstscheck" value="${sm.id}" data-id="${sm.id}">
											</c:otherwise>
										</c:choose> <spring:message code="label.moduleaccess.add"></spring:message>
									</td>
									<td>
										<c:choose>
											<c:when test="${sm.statusUpdate eq 'Active'}">
												<input type="checkbox" name="smlink"
													id="smLinkUpdate_${sm.id}" class="upstscheck"
													value="${sm.id}" data-id="${sm.id}" checked>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="smlink"
													id="smLinkUpdate_${sm.id}" class="upstscheck"
													value="${sm.id}" data-id="${sm.id}">
											</c:otherwise>
										</c:choose> <spring:message code="label.moduleaccess.update"></spring:message>
								  </td>
									<td>
										<c:choose>
											<c:when test="${sm.statusReview eq 'Active'}">
												<input type="checkbox" name="smlink"
													id="smLinkReview_${sm.id}" class="rvstscheck"
													value="${sm.id}" data-id="${sm.id}" checked>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="smlink"
													id="smLinkReview_${sm.id}" class="rvstscheck"
													value="${sm.id}" data-id="${sm.id}">
											</c:otherwise>
										</c:choose> <spring:message code="label.moduleaccess.review"></spring:message>
									</td>
									<td>
										<c:choose>
											<c:when test="${sm.statusDeactive eq 'Active'}">
												<input type="checkbox" name="smlink"
													id="smLinkForth_${sm.id}" class="daastscheck"
													value="${sm.id}" data-id="${sm.id}" checked>
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="smlink"
													id="smLinkForth_${sm.id}" class="daastscheck"
													value="${sm.id}" data-id="${sm.id}">
											</c:otherwise>
										</c:choose> <spring:message code="label.moduleaccess.deactivate"></spring:message>
									</td>	
								</tr>
							</c:forEach>
						</table>
					</div>
				</td>
			</tr>
			</table>
		</c:forEach>
		</div>
		<table class="table table-bordered">
			<tr align="center">
				<td colspan="5"><input type="button" value="<spring:message code="label.submit"></spring:message>" id="submitId"
					class="btn btn-danger btn-md"
					onclick="submitSelectedLinks('${roleId}')">
					<div id="submitMsg" style="color: red;"></div></td>
			</tr>
		</table>

	<c:forEach items="${map}" var="menu">
		<c:forEach items="${menu.value}" var="smenu">
			<script type="text/javascript">
				debugger;
				allSubLinksIds.push('${smenu.id}');
				var mapStr = "";
				if (menuIdsmap.size > 0) {
					mapStr = menuIdsmap.get('${smenu.appMenu.id}');
					if (mapStr != null && mapStr != "")
						mapStr = mapStr + "," + '${smenu.id}';
					else
						mapStr = '${smenu.id}';
					menuIdsmap.set('${smenu.appMenu.id}', mapStr);
				} else {
					mapStr = '${smenu.id}';
					menuIdsmap.set('${smenu.appMenu.id}', mapStr);
				}
			</script>
		</c:forEach>
	</c:forEach>
	<script type="text/javascript">
	
	
	$(document).ready(function () {
		 $('.clickcollapse').click(function() {
			    debugger;
			    $(this).toggleClass('collapsed');    
			    
			  });

	});
		
		 function mainMenuSublinksValidation(name){
			 debugger;
		 
			var mName = name.replace(/\s/g, '');
			if($('#menuCheckbox_'+mName).prop("checked") == true){
				var index = menuNames.indexOf(mName);
				var linkStr = msLinks[index];
				/* alert(linkStr); */
				if(linkStr != null && linkStr !="" && linkStr !="undefined"){
					var temp = linkStr.split(",");
					if(temp.length > 0){
						for(var j=0; j<temp.length; j++){
							$('#smLink_'+temp[j]).prop('checked', true);
							idsArr.push($('#smLink_'+temp[j]).val());
						}
					}
				}
			}else{
				var index = menuNames.indexOf(mName);
				var linkStr = msLinks[index];
				if(linkStr != null && linkStr !="" && linkStr !="undefined"){
					var temp = linkStr.split(",");
					if(temp.length > 0){
						for(var j=0; j<temp.length; j++){
							$('#smLink_'+temp[j]).prop('checked', false);
							var index = idsArr.indexOf($('#smLink_'+temp[j]).val());
					        if(index != -1){
					        	var removed = idsArr.splice(index,1);
					        }
						}
					}
				}
			}
			
			
		} 
	</script>
</body>
</html>