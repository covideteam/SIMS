<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <li class="nav-item">
      	<a class="nav-link white"><b style='width:200px;'>Server Time: <p id="servertime" style='display:inline'>00:00:00</p></b></a>
      </li>
      <!-- Notifications Dropdown Menu -->
      <li class="nav-item dropdown">
        <a class="nav-link" data-toggle="dropdown" href="#">
          <i class="far fa-user"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          <div class="dropdown-divider"></div>	
          <a href="#" class="dropdown-item">
             User : ${userName}
            <span class="float-right text-muted text-sm"></span>
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
             Role : ${userRole}
            <span class="float-right text-muted text-sm"></span>
          </a>
          <div class="dropdown-divider"></div>
           <c:if test="${userRole ne 'ADMIN'}">
           <div class="dropdown-divider"></div>

          </c:if>
          <a href='<c:url value="/administration/changePassword"/>' class="btn btn-danger btn-sm"> Change Password </a>&nbsp;&nbsp;&nbsp;
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link fa fa-sign-out white" href='<c:url value="/logout"/>'>
         
        </a>
      </li>
    </ul>
