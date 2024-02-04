<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
     <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Deligation Update Page</title>
</head>
<body oncontextmenu="return false;">
 	<c:url value="/delegation/updateDelegationDetails" var="savedel"/>
            <form:form action="${savedel}" method="post"  id="updateformsumit">
                <input type="hidden" name="uwsStudy" id="uwsStudy">
                 <input type="hidden" name="uwsuserId" id="uwsuserId">
                 <input type="hidden" name="uswRoles" id="uswRoles">
                 <input type="hidden" id="usstatus" name="usstatus">
                  <input type="hidden" name="updateuwsId" id="updateuwsId" value="${uwsam.id}">
                <div class="modal-body">
                    <input type="hidden" id="status" name="status" value="false">
                        <div class="form-group">
                            <table class="table table-hover  list-table table-center">
                            <tr>
                                    <td>
                                        <label><spring:message code="label.delstd"></spring:message></label>
                                    </td>
                                   <td>
                                        <select id="studyM" class="form-control" name="studyMaster" onchange="studyupValidation('studyM','studyMMsg')">
                                            <option value="0">Select</option>
                                            <c:forEach items="${delDto.smList}" var="study">
                                               <option value="${study.id}">${study.projectNo}</option>
                    						</c:forEach>
                    					</select>
                    					<script type="text/javascript">
                    					    var studyId = '${uwsam.study.id}';
                    					    $('#studyM').val(studyId);
                    					</script>
                    					<div id="studyMMsg" style="color: red;"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><label><spring:message code="label.delusr"></spring:message></label></td>
                                    <td>
                                        <select id="userid" class="form-control" name="user" onchange="stduserValidation('userid','userdelMsg')" required>
                                        <option value="0">Select</option>
                                           <c:forEach items="${delDto.usersList}" var="user">
                                               <option value="${user.id}">${user.username}</option>
                    						</c:forEach>
                                        </select>
                                        <div id="userdelMsg" style="color: red;"></div>
                                        
                                        <script type="text/javascript">
                    					    var userId = '${uwsam.userId.id}';
                    						$('#userid').val(userId);
                    					</script>
                    					
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                       <label><spring:message code="label.delrls"></spring:message></label>
                                    </td>
                                    <td>
                                        <select id="stdRolesU" style="height:80px" class="form-control" name="stdRolesU" multiple onchange="stdRldelValidation('stdRolesU','stdRolesUMsg')">
                                            <c:forEach items="${delDto.getRoleList()}" var="role">
                                        	   <option value="${role.getId()}">${role.getRole()}</option>
                    					    </c:forEach>
                                        </select>
                                        <script type="text/javascript">
                    					    var stdRoles = '${uwsam.studyRoles}';
                    					    var stdRArr = stdRoles.split(",");
                    					 	$("select[name=stdRolesU]").val(stdRArr);

                    					</script>
                    					<div id="stdRolesUMsg" style="color: red;"></div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                </div>
                </form:form>
                
</body>


</html>