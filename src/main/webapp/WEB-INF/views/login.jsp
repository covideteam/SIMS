<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="login_wrapper covide-login-wrapper" >
        <div class="animate form login_form" style="border-color: blue;">
   <h5 class="login-box-msg" style='text-align:center;'><b>SIMS</b></h5>
	  <c:url var="loginUrl" value="/login" />
    <form id="frmLogin" action="${loginUrl}" method="post" autocomplete="off">
    <c:if test="${param.error != null}">
		<div class="alert alert-danger" style="height: 40px;" align="center">
			<p>Invalid username or password.</p>
		</div>
	</c:if>
	
	<c:if test="${param.logout != null}">
		<div class="alert alert-success" style="height: 40px;" align="center">
			<p>Logged out successfully.</p>
		</div>
	</c:if>
      <div class="form-group has-feedback">
        <input type="text" name="username" class="form-control" placeholder="Username" autocomplete="off" autofocus="autofocus">
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="password" class="form-control" placeholder="Password">
      </div>
      <input type="hidden" name="language" id="language" value="0"/>
<!--        <div class="form-group has-feedback"> -->
<!--          <select name="language" id="language" class="form-control"> -->
<!--         	<option value="0">Language</option> -->
<%--         	<c:forEach items="${inlagList}" var="lang"> --%>
<%--         		<option value="${lang.langCode}##${lang.countryCode}">${lang.language}</option> --%>
<%--         	</c:forEach> --%>
<!--         </select> -->
<!--       </div> -->
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      <div style="text-align:center;">
      	<button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
      </div>
    </form>

</div>
</div>




