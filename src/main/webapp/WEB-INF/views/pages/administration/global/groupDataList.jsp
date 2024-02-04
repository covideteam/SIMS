<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${ggList}" var="sl">
	 <option value="${sl.id}">${sl.name}</option>
</c:forEach>