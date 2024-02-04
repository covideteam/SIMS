<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${rand}" var="sl">
	 <option value="${sl}">${sl}</option>
</c:forEach>