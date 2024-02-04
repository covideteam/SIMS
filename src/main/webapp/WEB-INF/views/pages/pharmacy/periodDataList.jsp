<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${sgpList}" var="sl">
	 <option value="${sl.id}">${sl.periodName}</option>
</c:forEach>