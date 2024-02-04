<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${volList}" var="vl">
<option value="${vl.id}">${vl.volunteerId}</option>
</c:forEach>